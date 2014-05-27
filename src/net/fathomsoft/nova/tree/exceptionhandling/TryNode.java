package net.fathomsoft.nova.tree.exceptionhandling;

import java.util.ArrayList;

import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * ExceptionHandlingNode extension that represents the declaration of a
 * try node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 4:01:38 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class TryNode extends ExceptionHandlingNode
{
	private ArrayList<Integer>	codes;
	
	/**
	 * Instantiate and initialize default data.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public TryNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		codes = new ArrayList<Integer>();
	}
	
	/**
	 * Add the specified exception code to the list of exceptions that
	 * this try exception handling block catches.
	 * 
	 * @param code The type of exception code that is caught.
	 */
	public void addExceptionCode(int code)
	{
		codes.add(code);
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("TRY").append('\n');
		builder.append('{').append('\n');
		builder.append(generateExceptionCodes()).append('\n');
		
		builder.append(getScopeNode().generateCSource());
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a TryNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * The only correct input is "try"
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		TryNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a TryNode.
	 */
	public static TryNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.matches(statement, 0, Patterns.TRY))
		{
			TryNode n = new TryNode(parent, location);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * Generate a String that adds all of the exception codes that this
	 * try node catches to the exception data instance.
	 * 
	 * @return The generated C language String.
	 */
	private String generateExceptionCodes()
	{
		StringBuilder builder = new StringBuilder();
			
		String variableName = ExceptionNode.EXCEPTION_DATA_IDENTIFIER;
		
		for (int i = 0; i < codes.size(); i++)
		{
			int code = codes.get(i);
			
			builder.append("nova_ExceptionData_addCode(").append(variableName).append(", ").append(variableName).append(", ").append(code).append(");").append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public TryNode clone(TreeNode temporaryParent, Location locationIn)
	{
		TryNode node = new TryNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given TryNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public TryNode cloneTo(TryNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}