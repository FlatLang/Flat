package flat.validator.exceptionhandling;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.Node;
import flat.tree.SyntaxTree;
import flat.util.Location;

import java.util.ArrayList;

public class Try extends ExceptionHandler
{
	public ArrayList<java.lang.Exception>	exceptions;
	
	public static final String IDENTIFIER = "try";
	
	/**
	 * Instantiate and initialize default data.
	 * 
	 * @see Node#Node(Node, Location)
	 */
	public Try(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		exceptions = new ArrayList<>();
	}
	
	/**
	 * Add the specified exception code to the list of exceptions that
	 * this try exception handling block catches.
	 * 
	 * @param caught The type of exception code that is caught.
	 */
	public void addCaughtException(java.lang.Exception caught)
	{
		if (!exceptions.contains(caught))
		{
			exceptions.add(caught);
		}
		else
		{
			SyntaxMessage.error("Exception of type '" + caught.type.getName() + "' already caught", this);
		}
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
	
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray)
	{
		builder.append("try");
		
		if (outputChildren)
		{
			builder.append(" ");
			getScope().generateFlatInput(builder, true);
		}
		
		return builder;
	}
	
	/**
	 * Check for a finally node following the try statement. If one does
	 * not exist, add a default one.
	 * 
	 * @param phase The phase that the node is being validated in.
	 * @see Node#validate(int)
	 */
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
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public Try clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Try node = new Try(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public Try cloneTo(Try node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Try} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Try cloneTo(Try node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.exceptions = new ArrayList<java.lang.Exception>();
		
		for (java.lang.Exception c : exceptions)
		{
			node.exceptions.add(c);
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