package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.exceptionhandling.Throw;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

public class ElseStatement extends ControlStatement
{
	public static final String IDENTIFIER = "else";
	
	/**
	 * Instantiate a new ElseStatement and initialize the default
	 * values.
	 * 
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public ElseStatement(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public NodeValidator getDecodedParent()
	{
		if (getInlineStatement() != null)//instanceof IfStatement)
		{
			return getInlineStatement();
		}
		
		return super.getDecodedParent();
	}
	
	public NodeValidator getInlineStatement()
	{
		if (getNumChildren() >= 2)
		{
			return getChild(1);
		}
		
		return null;
	}
	
	@Override
	public boolean pendingScopeFragment(NodeValidator nodeValidator)
	{
		if (getInlineStatement() != null)
		{
			return getInlineStatement().pendingScopeFragment(nodeValidator);
		}
		
		return super.pendingScopeFragment(nodeValidator);
	}
	
	/**
	 * Decode the given statement into a ElseStatement instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>else</li>
	 * 	<li>else if (!person.canWalk() &amp;&amp; !person.isVegetable())</li>
	 * 	<li>else doSomethingInOneLine()</li>
	 * 	<li>else counter++</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ElseStatement instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ElseStatement.
	 */
	public static ElseStatement decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		if (StringUtils.startsWithWord(statement, IDENTIFIER))
		{
			ElseStatement n = new ElseStatement(parent, location);
			
			int end = StringUtils.findNextNonWhitespaceIndex(statement, IDENTIFIER.length() + 1);
			
			if (end < 0)
			{
				end = statement.length();
			}
			
			String ending = statement.substring(end);
			
			Location newLocation = location.asNew();
			newLocation.setBounds(location.getStart() + end, location.getStart() + statement.length());
			
			if (ending.length() > 0)
			{
				NodeValidator contents = SyntaxTree.decodeScopeContents(parent, ending, newLocation, require);
				
				if (contents != null)
				{
					n.addChild(contents, n);
				}
				else
				{
					SyntaxMessage.queryError("Unable to decode syntax '" + ending + "'", n, require);
					
					return null;
				}
			}
			
			return n;
		}
		
		return null;
	}

	public IfStatement getIfElseChainStart() {
		NodeValidator prev = getPreviousNode();

		if (prev instanceof ValueValidator) {
			ValueValidator returned = ((ValueValidator)prev).getReturnedNode();

			if (returned instanceof IfStatement) {
				if (returned.getParent() instanceof ElseStatement) {
					return ((ElseStatement)returned.getParent()).getIfElseChainStart();
				} else {
					return (IfStatement) returned;
				}
			} else if (returned instanceof ElseStatement) {
				return ((ElseStatement)returned).getIfElseChainStart();
			}
		}

		return null;
	}

	@Override
	public void onStackPopped(NodeValidator popped) {
		IfStatement start = getIfElseChainStart();

		if (start != null && start.getParent() instanceof AssignmentValidator) {
			AssignmentValidator assignmentValidator = (AssignmentValidator) start.getParent();
			VariableDeclaration declaration = assignmentValidator.getAssignedNode().getDeclaration();

			NodeValidator lastChild;

			if (getInlineStatement() != null) {
				lastChild = getInlineStatement().getScope().getLastChild();
			} else {
				lastChild = getScope().getLastVisibleChild();
			}

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
	
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append(IDENTIFIER).append(' ');
		
		if (getDecodedParent() != this)
		{
			getDecodedParent().generateFlatInput(builder, outputChildren);
		}
		else if (outputChildren)
		{
			getScope().generateFlatInput(builder, true);
		}
		
		return builder;
	}
	
	public ValidationResult validate(int phase)
	{
		if (getInlineStatement() != null && getInlineStatement() instanceof IfStatement == false)
		{
			getScope().addChild(getInlineStatement());
		}
		
		return super.validate(phase);
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public ElseStatement clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ElseStatement node = new ElseStatement(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public ElseStatement cloneTo(ElseStatement node)
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
	public ElseStatement cloneTo(ElseStatement node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the ElseStatement class type to make sure everything
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
