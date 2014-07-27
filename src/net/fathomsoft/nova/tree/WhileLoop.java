package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Loop extension that represents the declaration of a "while loop"
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:55:59 PM
 * @version	v0.2.19 Jul 26, 2014 at 12:30:24 AM
 */
public class WhileLoop extends Loop
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public WhileLoop(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 1;
	}
	
	/**
	 * Get the Node that describes the condition section of the while
	 * loop. For instance: "while (i < 10)" the contents between the
	 * parenthesis is the condition.
	 * 
	 * @return The Node instance that describes the condition section
	 * 		of the while loop.
	 */
	public Node getCondition()
	{
		return getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		Node condition = getCondition();
		
		builder.append("while (").append(condition.generateCSourceFragment()).append(')').append('\n');
		
		getScope().generateCSource(builder);
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a WhileLoop instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>while (currentNode != null)</li>
	 * 	<li>while (true)</li>
	 * 	<li>while (number.isEven())</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		WhileLoop instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a WhileLoop.
	 */
	public static WhileLoop decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (StringUtils.findNextWord(statement).equals("while"))
		{
			WhileLoop n   = new WhileLoop(parent, location);
			
			Bounds bounds = SyntaxUtils.findInnerParenthesesBounds(n, statement);
			
			if (bounds.isValid())
			{
				Location newLoc = location.asNew();
				newLoc.addBounds(bounds.getStart(), bounds.getEnd());
				
				String contents = statement.substring(bounds.getStart(), bounds.getEnd());
				
				if (n.decodeCondition(contents, newLoc) && n.decodeScopeFragment(statement, bounds))
				{
					return n;
				}
				
				SyntaxMessage.error("Could not decode while loop condition '" + contents + "'", n);
			}
			else
			{
				SyntaxMessage.error("While loop missing condition", n);
			}
		}
		
		return null;
	}
	
	/**
	 * Decode the given condition of the if statement.
	 * 
	 * @param contents The condition expression to decode.
	 * @param location The location of the condition in the source code.
	 * @return Whether or not the condition decoded successfully.
	 */
	private boolean decodeCondition(String contents, Location location)
	{
		Value condition = BinaryOperation.decodeStatement(this, contents, location, true);
		
		if (condition == null)
		{
			condition = SyntaxTree.getUsableExistingNode(this, contents, location);
			
			if (condition == null)
			{
				condition = Literal.decodeStatement(this, contents, location, true, true);
				
				if (condition == null)
				{
					condition = checkMethodCallCondition(contents, location);
					
					if (condition == null)
					{
						return false;
					}
				}
			}
		}
		
		addChild(condition, this);
		
		return true;
	}
	
	private MethodCall checkMethodCallCondition(String contents, Location location)
	{
		MethodCall call = MethodCall.decodeStatement(this, contents, location, false);
		
		if (call != null && call.getTypeClassName().equals("Bool"))
		{
			return call;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public WhileLoop clone(Node temporaryParent, Location locationIn)
	{
		WhileLoop node = new WhileLoop(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link WhileLoop} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public WhileLoop cloneTo(WhileLoop node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the WhileLoop class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test()
	{
		
		
		return null;
	}
}