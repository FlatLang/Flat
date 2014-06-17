package net.fathomsoft.nova.tree.exceptionhandling;

import java.util.ArrayList;

import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * ExceptionHandlingNode extension that represents the declaration of a
 * try node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 4:01:38 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Try extends ExceptionHandler
{
	private ArrayList<Integer>	codes;
	
	/**
	 * Instantiate and initialize default data.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Try(Node temporaryParent, Location locationIn)
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
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
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
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a TryNode.
	 */
	public static Try decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (Regex.matches(statement, 0, Patterns.TRY))
		{
			Try n = new Try(parent, location);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * Check for a finally node following the try statement. If one does
	 * not exist, add a default one.
	 * 
	 * @param phase The phase that the node is being validated in.
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public Node validate(int phase)
	{
		Node parent = getParent();
		
		FinallyNode finallyNode = null;
		
		for (int i = 0; i < parent.getNumChildren() && finallyNode == null; i++)
		{
			Node child = parent.getChild(i);
			
			if (child == this)
			{
				i++;
				
				int     insertIndex = -1;
				
				while (i < parent.getNumChildren() && insertIndex == -1)
				{
					child = parent.getChild(i);
					
					// If the current child is a catch node.
					if (child instanceof Catch == false)
					{
						// If there already is a finally node.
						if (child instanceof FinallyNode)
						{
							insertIndex = -2;
						}
						// If there was not finally node.
						else
						{
							insertIndex = i;
						}
					}
					
					i++;
				}
				
				// If there does not already exist a finally node.
				if (insertIndex != -2)
				{
					if (insertIndex < 0)
					{
						insertIndex = i;
					}
					
					finallyNode = new FinallyNode(parent, null);
					parent.addChild(insertIndex, finallyNode);
				}
			}
		}
		
		return this;
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
			
		String variableName = Exception.EXCEPTION_DATA_IDENTIFIER;
		
		for (int i = 0; i < codes.size(); i++)
		{
			int code = codes.get(i);
			
			builder.append("nova_ExceptionData_addCode(").append(variableName).append(", ").append(variableName).append(", ").append(code).append(");").append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Try clone(Node temporaryParent, Location locationIn)
	{
		Try node = new Try(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given TryNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Try cloneTo(Try node)
	{
		super.cloneTo(node);
		
		return node;
	}
}