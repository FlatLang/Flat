package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * Node that holds the LocalVariableNodes that a method contains.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 9, 2014 at 4:19:57 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class VariableList extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#TreeNode(Node, Location)
	 */
	public VariableList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get whether or not there is a VaraibleNode within the list with
	 * the given name.
	 * 
	 * @param variableName The name of the variable to search for.
	 * @return Whether or not there is a VaraibleNode within the list with
	 * 		the given name.
	 */
	public boolean containsVariable(String variableName)
	{
		return getVariable(variableName) != null;
	}
	
	/**
	 * Get the VariableNode from the list with the given name, if it
	 * exists.
	 * 
	 * @param variableName The name of the variable to get.
	 * @return The VariableNode with the given name.
	 */
	public Variable getVariable(String variableName)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Variable variable = (Variable)getChild(i);
			
			if (variable.getName().equals(variableName))
			{
				return variable;
			}
		}
		
		return null;
	}
	
	/**
	 * Generate the output needed to free the variables after they are
	 * finished with.
	 * 
	 * @return The String output of the variables being freed.
	 */
	public String generateFreeVariablesOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Variable variable = (Variable)getChild(i);
			
			builder.append(variable.generateFreeOutput());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
		}
		
		if (getNumChildren() > 0)
		{
			builder.append('\n');
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return "";
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			builder.append(child.generateCSource());
		}
		
		if (getNumChildren() > 0)
		{
			builder.append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public VariableList clone(Node temporaryParent, Location locationIn)
	{
		VariableList node = new VariableList(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given VariableListNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VariableList cloneTo(VariableList node)
	{
		super.cloneTo(node);
		
		return node;
	}
}