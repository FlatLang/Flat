package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.exceptionhandling.Throw;
import flat.tree.variables.Variable;
import flat.tree.variables.VariableDeclaration;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import java.util.function.Consumer;

public class IfStatement extends ControlStatement
{
	public static final String IDENTIFIER = "if";
	
	/**
	 * Instantiate a new IfStatement and initialize the default
	 * values.
	 */
	public IfStatement(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see NodeValidator#getNumDefaultChildren()
	 */
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 1;
	}
	
	/**
	 * Get the ArgumentList that contains the condition for the if
	 * statement.
	 * 
	 * @return The ArgumentList instance.
	 */
	public ValueValidator getCondition()
	{
		return getNumChildren() > super.getNumDefaultChildren() ? (ValueValidator)getChild(super.getNumDefaultChildren() + 0) : null;
	}
	
	/**
	 * @see NodeValidator#generateFlatInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		if (getCondition() == null) {
			return builder;
		}

		builder.append("if (").append(getCondition().generateFlatInput()).append(')');
		
		if (outputChildren)
		{
			builder.append('\n').append(getScope().generateFlatInput());
		}
		
		return builder;
	}
	
	public static IfStatement generateDefault(NodeValidator parent, Location location)
	{
		return decodeStatement(parent, "if (false)", location, true);
	}
	
	/**
	 * Decode the given statement into a IfStatement instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>if (index &gt;= array.size())</li>
	 * 	<li>if (getParent().isAlive())</li>
	 * 	<li>if (!person.canWalk() &amp;&amp; !person.isVegetable())</li>
	 * 	<li>if ((age + 2 &gt;= 21 &amp;&amp; gender == "male") || gender == "female")</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		IfStatement instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a IfStatement.
	 */
	public static IfStatement decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		if (StringUtils.startsWithWord(statement, IDENTIFIER))
		{
			IfStatement n = new IfStatement(parent, location);
			
			Bounds bounds = SyntaxUtils.findInnerParenthesesBounds(n, statement);
			
			if (bounds.getStart() >= 0)
			{
				String contents = statement.substring(bounds.getStart(), bounds.getEnd());
				
				if (n.decodeCondition(contents, bounds, require) && n.decodeScopeFragment(statement, bounds))
				{
					return n;
				}
			}
			else
			{
				SyntaxMessage.error("If statement missing condition", n);
			}
		}
		
		return null;
	}
	
	/**
	 * Decode the condition within the given contents argument.
	 * 
	 * @param contents The condition to decode. 
	 * @param bounds The bounds of the condition.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the condition decoded correctly.
	 */
	private boolean decodeCondition(String contents, Bounds bounds, boolean require)
	{
		Location newLoc = new Location(getLocationIn());
		newLoc.addBounds(bounds.getStart(), bounds.getEnd());
		
		ValueValidator condition = SyntaxTree.decodeValue(this, contents, newLoc, require);
		
		if (condition == null)
		{
			return false;
		}
		
		addChild(condition, this);
		
		if (!"Bool".equals(condition.getReturnedNode().getType()))
		{
			condition.replaceWithNullCheck();
		}
		
		return true;
	}

	private void forEachIfStatementExpression(NodeValidator parent, Consumer<ValueValidator> func) {
		NodeValidator lastChild = getScope().getLastVisibleChild();

		if (lastChild instanceof ValueValidator == false) {
			SyntaxMessage.error("Invalid value '" + lastChild.generateFlatInput() + "' for expression", this);
			return;
		}

		func.accept((ValueValidator) lastChild);

		for (int i = parent.getIndex() + 1; i < parent.getParent().getNumChildren(); i++) {
			NodeValidator child = parent.getParent().getChild(i);

			if (child instanceof ElseStatement) {
				ElseStatement elseStatement = (ElseStatement) child;

				if (elseStatement.getInlineStatement() != null) {
					child = elseStatement.getInlineStatement();
				}

				NodeValidator value = child.getScope().getLastVisibleChild();

				if (value instanceof ValueValidator == false) {
					SyntaxMessage.error("Invalid value '" + value.generateFlatInput() + "' for expression", this);
					return;
				}

				func.accept((ValueValidator) value);
			} else {
				break;
			}
		}
	}

	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);

		if (result.skipValidation())
		{
			return result;
		}

		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (getParent() instanceof AssignmentValidator) {
				AssignmentValidator assignmentValidator = (AssignmentValidator) getParent();
				VariableDeclaration declaration = assignmentValidator.getAssignedNode().getDeclaration();

				forEachIfStatementExpression(assignmentValidator, (value) -> {
					if (value instanceof Throw) {
						return;
					}

					AssignmentValidator replacement = AssignmentValidator.generateDefault(value.getScope(), value.getLocationIn());
					Variable variable = declaration.generateUsableVariable(replacement, value.getLocationIn());
					variable.setProperty("userMade", false);
					replacement.getAssigneeNodes().addChild(variable);
					value.replaceWith(replacement);

					replacement.addChild(value);
				});

				assignmentValidator.replaceWith(this);
			} else if (getParent().getParent() instanceof Return) {
				Return r = (Return) getParent().getParent();

				forEachIfStatementExpression(r, (value) -> {
					if (value instanceof Throw || value instanceof Return) {
						return;
					}

					Return replacement = new Return(value.getParent(), value.getLocationIn());
					replacement.setProperty("userMade", false);
					value.replaceWith(replacement);

					replacement.getReturnValues().addChild(value);
				});

				r.replaceWith(this);
			}
		}

		return result;
	}

	public IfStatement getIfElseChainStart() {
		if (getParent() instanceof ElseStatement) {
			return ((ElseStatement)getParent()).getIfElseChainStart();
		}

		return this;
	}

	@Override
	public void onStackPopped(NodeValidator popped) {
		IfStatement start = getIfElseChainStart();

		if (start.getParent() instanceof AssignmentValidator) {
			AssignmentValidator assignmentValidator = (AssignmentValidator) start.getParent();
			VariableDeclaration declaration = assignmentValidator.getAssignedNode().getDeclaration();

			NodeValidator lastChild = getScope().getLastVisibleChild();

			if (lastChild instanceof Throw) {

			} else if (lastChild instanceof ValueValidator == false) {
				SyntaxMessage.error("Invalid value '" + lastChild.generateFlatInput() + "' for expression", this);
			} else if (declaration.getType() == null) {
				declaration.setType(((ValueValidator) lastChild).getReturnedNode());
			} else {
				ValueValidator common = SyntaxUtils.getTypeInCommon(((ValueValidator) lastChild).getReturnedNode(), declaration);

				declaration.setType(common);
			}
		}

		super.onStackPopped(popped);
	}

	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public IfStatement clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		IfStatement node = new IfStatement(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public IfStatement cloneTo(IfStatement node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link IfStatement} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public IfStatement cloneTo(IfStatement node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the IfStatement class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}
