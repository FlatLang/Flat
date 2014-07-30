package net.fathomsoft.nova.tree;

import java.util.regex.Pattern;

import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Regex;

/**
 * MethodDeclaration extension that represents the declaration of a
 * method node type that does not contain a defined body.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.19 Jul 26, 2014 at 12:30:24 AM
 * @version	v0.2.21 Jul 30, 2014 at 1:45:00 PM
 */
public class BodylessMethodDeclaration extends MethodDeclaration
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public BodylessMethodDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.CallableMethod#isVirtual()
	 */
	@Override
	public boolean isVirtual()
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#containsBody()
	 */
	@Override
	public boolean containsBody()
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getScope()
	 */
	@Override
	public Scope getScope()
	{
		return null;
	}
	
	/**
	 * Find the String representing the signature of the bodyless
	 * method that is currently being decoded from the given
	 * statement String.
	 * 
	 * @param statement The String containing the method signature.
	 * @return The signature for the bodyless method to decode.
	 */
	public static String findMethodSignature(String statement, Pattern pattern)
	{
		int paren = statement.indexOf('(');
		
		if (paren < 0)
		{
			return null;
		}
		
		String signature = NovaMethodDeclaration.findMethodSignature(statement);
		Bounds bounds    = Regex.boundsOf(signature, pattern);
		
		if (!bounds.isValid())
		{
			return null;
		}
		
		paren -= bounds.length();
		
		statement = statement.substring(0, bounds.getStart()) + statement.substring(bounds.getEnd(), statement.length());
		
		return statement;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public BodylessMethodDeclaration clone(Node temporaryParent, Location locationIn)
	{
		BodylessMethodDeclaration node = new BodylessMethodDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link BodylessMethodDeclaration} with the data that is in
	 * the specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public BodylessMethodDeclaration cloneTo(BodylessMethodDeclaration node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the BodylessMethodDeclaration class type to make sure everything
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