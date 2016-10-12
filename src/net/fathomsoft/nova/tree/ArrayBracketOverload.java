package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * {@link IValue} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class ArrayBracketOverload extends IValue
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ArrayBracketOverload(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public LocalDeclaration getIndexValue()
	{
		return (LocalDeclaration)getChild(0);
	}
	
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	@Override
	public void addChild(Node node)
	{
		if (getNumChildren() == getNumDefaultChildren())
		{
			getParentClass().addChild(node);
		}
		else
		{
			super.addChild(node);
		}
	}
	
	/**
	 * Decode the given statement into a {@link ArrayBracketOverload} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li></li>
	 * 	<li></li>
	 * 	<li></li>
	 * </ul>
	 *
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link ArrayBracketOverload} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link ArrayBracketOverload}.
	 */
	public static ArrayBracketOverload decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.startsWith("this["))
		{
			int end = StringUtils.findEndingMatch(statement, "this".length(), '[', ']');
			
			if (end > 1)
			{
				ArrayBracketOverload n = new ArrayBracketOverload(parent, location);
				
				String contents = statement.substring("this[".length(), end).trim();
				
				LocalDeclaration indexValue = LocalDeclaration.decodeStatement(n, contents, location.asNew(), require);
				
				n.addChild(indexValue);
				
				String rest  = statement.substring(end + 1).trim();
				
				if (rest.length() > 0 && !rest.startsWith("->"))
				{
					SyntaxMessage.error("Invalid array bracket overload", n);
				}
				
				String type = rest.substring(2).trim();
				
				n.setType(type);
				
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ArrayBracketOverload clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ArrayBracketOverload node = new ArrayBracketOverload(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ArrayBracketOverload cloneTo(ArrayBracketOverload node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link ArrayBracketOverload} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ArrayBracketOverload cloneTo(ArrayBracketOverload node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link ArrayBracketOverload} class type to make sure everything
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