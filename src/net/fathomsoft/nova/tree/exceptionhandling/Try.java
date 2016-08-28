package net.fathomsoft.nova.tree.exceptionhandling;

import java.util.ArrayList;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.AccessorMethod;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;

/**
 * ExceptionHandler extension that represents the declaration of a
 * try node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 4:01:38 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Try extends ExceptionHandler
{
	private ArrayList<Integer>	codes;
	
	public static final String IDENTIFIER = "try";
	
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
		if (!codes.contains(code))
		{
			codes.add(code);
		}
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
		if (statement.equals(IDENTIFIER))
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
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
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
					
					return result;
				}
			}
		}
		
		return result;
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
			
			builder.append("novaEnv.nova_exception_ExceptionData.addCode(").append(variableName).append(", ").append(variableName).append(", ").append(code).append(");").append('\n');
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Try clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Try node = new Try(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Try cloneTo(Try node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Try} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Try cloneTo(Try node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
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
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}