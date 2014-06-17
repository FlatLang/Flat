package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;

/**
 * Node extension that represents an external statement.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.1 Apr 27, 2014 at 11:14:20 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ExternalStatement extends Node
{
	private String	data;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ExternalStatement(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Set the data that the external statement contains.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * external
	 * {
	 * 	int j;
	 * 	Thread* instance;
	 * 	
	 * 	instance = (Thread*)malloc(sizeof(Thread));
	 * }</pre></blockquote>
	 * The data in the external statement node above is:
	 * <blockquote><pre>
	 * int j;
	 * Thread* instance;
	 * 
	 * instance = (Thread*)malloc(sizeof(Thread));</pre></blockquote>
	 * 
	 * @param data The data to set.
	 */
	public void setData(String data)
	{
		data = StringUtils.removeTypeAfterChar(data, '\n', '\t');
		
		this.data = data;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return generateCSource();
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment() + '\n';
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return data;
	}
	
	/**
	 * Decode the given statement into a ExternalStatementNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>external unsigned int i = 32;</li>
	 * 	<li>external { multiple; lines; of c code; }</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ExternalStatementNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ExternalStatementNode.
	 */
	public static ExternalStatement decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (Regex.matches(statement, Patterns.EXTERNAL))
		{
			ExternalStatement n = new ExternalStatement(parent, location);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public ExternalStatement clone(Node temporaryParent, Location locationIn)
	{
		ExternalStatement node = new ExternalStatement(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ExternalStatementNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExternalStatement cloneTo(ExternalStatement node)
	{
		super.cloneTo(node);
		
		node.data = data;
		
		return node;
	}
}