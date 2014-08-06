package net.fathomsoft.nova.tree.exceptionhandling;

import java.util.ArrayList;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.BodyMethodDeclaration;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * ExceptionHandler extension that represents the declaration of a
 * try node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 4:01:38 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
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
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		builder.append("TRY").append('\n');
		builder.append('{').append('\n');
		generateExceptionCodes(builder).append('\n');
		
		getScope().generateCSource(builder);
		
		builder.append('}').append('\n');
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a Try instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * The only correct input is "try"
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Try instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Try.
	 */
	public static Try decodeStatement(Node parent, String statement, Location location, boolean require)
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
		if (phase != SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			return this;
		}
		
		Node parent = getParent();
		
		for (int i = 0; i < parent.getNumChildren(); i++)
		{
			Node child = parent.getChild(i);
			
			if (child == this)
			{
				if (++i < parent.getNumChildren())
				{
					child = parent.getChild(i);
				}
				
				while (child instanceof Catch)
				{
					if (++i < parent.getNumChildren())
					{
						child = parent.getChild(i);
					}
					else
					{
						child = null;
					}
				}
				
				if (!(child instanceof Finally))
				{
					Finally finallyNode = new Finally(parent, null);
					parent.addChild(i, finallyNode);
				}
				
				return this;
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
	private StringBuilder generateExceptionCodes(StringBuilder builder)
	{
		String variableName = Exception.EXCEPTION_DATA_IDENTIFIER;
		
		for (int i = 0; i < codes.size(); i++)
		{
			int code = codes.get(i);
			
			builder.append("nova_ExceptionData_addCode(").append(variableName).append(", ").append(variableName).append(", ").append(code).append(");").append('\n');
		}
		
		return builder;
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
	 * Fill the given {@link Try} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Try cloneTo(Try node)
	{
		super.cloneTo(node);
		
		node.codes = new ArrayList<Integer>();
		
		for (Integer c : codes)
		{
			node.codes.add(c);
		}
		
		return node;
	}
	
	/**
	 * Test the Try class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(Nova controller, ClassDeclaration clazz, BodyMethodDeclaration method)
	{
		
		
		return null;
	}
}