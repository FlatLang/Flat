package flat.tree;

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

/**
 * Node extension that represents the declaration of an "if statement"
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 *
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:57:13 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class IfStatement extends ControlStatement
{
	public static final String IDENTIFIER = "if";

	/**
	 * Instantiate a new IfStatement and initialize the default
	 * values.
	 */
	public IfStatement(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}

	/**
	 * @see Node#getNumDefaultChildren()
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
	public Value getCondition()
	{
		return getNumChildren() > super.getNumDefaultChildren() ? (Value)getChild(super.getNumDefaultChildren() + 0) : null;
	}

	/**
	 * @see Node#generateFlatInput(StringBuilder, boolean)
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

	public static IfStatement generateDefault(Node parent, Location location)
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
	public static IfStatement decodeStatement(Node parent, String statement, Location location, boolean require)
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

		Value condition = SyntaxTree.decodeValue(this, contents, newLoc, require);

		if (condition == null)
		{
			return false;
		}

		addChild(condition, this);

		if (!"Bool".equals(condition.getReturnedNode().getType())) {
			condition.replaceWithNullCheck();
		} else if (condition.getReturnedNode().isPointer()) {
			condition.replaceWithNullCheck();
		}

		return true;
	}

	private void forEachIfStatementExpression(Node parent, Consumer<Value> func) {
		Node lastChild = getScope().getLastVisibleChild();

		if (lastChild instanceof Value == false) {
			SyntaxMessage.error("Invalid value '" + lastChild.generateFlatInput() + "' for expression", this);
			return;
		}

		func.accept((Value) lastChild);

		for (int i = parent.getIndex() + 1; i < parent.getParent().getNumChildren(); i++) {
			Node child = parent.getParent().getChild(i);

			if (child instanceof ElseStatement) {
				ElseStatement elseStatement = (ElseStatement) child;

				if (elseStatement.getInlitestatement() != null) {
					child = elseStatement.getInlitestatement();
				}

				Node value = child.getScope().getLastVisibleChild();

				if (value instanceof Value == false) {
					SyntaxMessage.error("Invalid value '" + value.generateFlatInput() + "' for expression", this);
					return;
				}

				func.accept((Value) value);
			} else {
				break;
			}
		}
	}

	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);

		if (result.skipValidation())
		{
			return result;
		}

		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (getParent() instanceof Assignment) {
				Assignment assignment = (Assignment) getParent();
				VariableDeclaration declaration = assignment.getAssignedNode().getDeclaration();

				forEachIfStatementExpression(assignment, (value) -> {
					if (value instanceof Throw) {
						return;
					}

					Assignment replacement = Assignment.generateDefault(value.getScope() != null ? value.getScope() : value.getParent(), value.getLocationIn());
					Variable variable = declaration.generateUsableVariable(replacement, value.getLocationIn());
					variable.setProperty("userMade", false);
					replacement.getAssigneeNodes().addChild(variable);
					value.replaceWith(replacement);

					replacement.addChild(value);
				});

				assignment.replaceWith(this);
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
	public void onStackPopped(Node popped) {
		IfStatement start = getIfElseChainStart();

		if (start == null) {
			SyntaxMessage.error("Could not find if statement start", this);
		}
		if (start.getParent() instanceof Assignment) {
			Assignment assignment = (Assignment) start.getParent();
			VariableDeclaration declaration = assignment.getAssignedNode().getDeclaration();

			Node lastChild = getScope().getLastVisibleChild();

			if (lastChild instanceof Throw) {

			} else if (lastChild instanceof Value == false) {
				SyntaxMessage.error("Invalid value '" + lastChild.generateFlatInput() + "' for expression", this);
			} else if (declaration.getType() == null) {
				declaration.setType(((Value) lastChild).getReturnedNode());
			} else {
				Value common = SyntaxUtils.getTypeInCommon(((Value) lastChild).getReturnedNode(), declaration);

				declaration.setType(common);
			}
		}

		super.onStackPopped(popped);
	}

	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public IfStatement clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		IfStatement node = new IfStatement(temporaryParent, locationIn);

		return cloneTo(node, cloneChildren, cloneAnnotations);
	}

	/**
	 * @see Node#cloneTo(Node)
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
