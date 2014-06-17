package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.Array;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * ValueNode extension that represents the declaration of an
 * instantiation node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Apr 3, 2014 at 7:53:35 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Instantiation extends Identifier
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#TreeNode(Node, Location)
	 */
	public Instantiation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the node represents the type of instance that is being
	 * instantiated. For example, a method call or an array
	 * initialization node.
	 * 
	 * @return The node that represents the type of instance that is
	 * 		being instantiated.
	 */
	public Identifier getIdentifierNode()
	{
		return (Identifier)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#addChild(Node)
	 */
	@Override
	public void addChild(Node child)
	{
		if (getNumChildren() == 0)
		{
			super.addChild(child);
		}
		else
		{
			getIdentifierNode().addChild(child);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#getAccessedNode()
	 */
	public Identifier getAccessedNode()
	{
		if (getNumChildren() < 1)
		{
			return null;
		}
		
		return getIdentifierNode().getAccessedNode();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment() + ";\n";
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		if (isSpecialFragment())
		{
			return generateSpecialFragment();
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateCSourceFragment());
		}
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into an InstantiationNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * Instantiations always begin with the 'new' keyword.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>new Person("Joe")</li>
	 * 	<li>new Armadillo()</li>
	 * 	<li>new String("asdf")</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		InstantiationNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a InstantiationNode.
	 */
	public static Instantiation decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (SyntaxUtils.isInstantiation(statement))
		{
			Instantiation n = new Instantiation(parent, location);
			
			int startIndex  = Regex.indexOf(statement, Patterns.POST_INSTANTIATION);
			
			String action   = statement.substring(startIndex);
			
			Location newLoc = new Location();
			newLoc.setLineNumber(location.getLineNumber());
			newLoc.setBounds(location.getStart() + startIndex, location.getStart() + statement.length());
			
			Identifier child = null;
			
			if (SyntaxUtils.isMethodCall(action))
			{
				MethodCall methodCall = MethodCall.decodeStatement(parent, action, newLoc, require, scope);
				
				if (methodCall == null)
				{
					return null;
				}
				
				child = methodCall;
			}
			else if (SyntaxUtils.isArrayInitialization(action))
			{
				child = Array.decodeStatement(parent, action, newLoc, require, scope);
			}
			
			if (child == null)
			{
				SyntaxMessage.error("Unable to parse instantiation of '" + action + "'", n);
			}
			
			n.setName(child.getName());
			n.setType(child.getType());
			n.addChild(child);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("new ").append(getIdentifierNode().generateNovaInput());
		
//		if (outputChildren)
//		{
//			IdentifierNode accessed = getAccessedNode();
//			
//			if (accessed != null)
//			{
//				builder.append('.').append(accessed.generateNovaInput());
//			}
//		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Instantiation clone(Node temporaryParent, Location locationIn)
	{
		Instantiation node = new Instantiation(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given InstantiationNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Instantiation cloneTo(Instantiation node)
	{
		super.cloneTo(node);
		
		return node;
	}
}