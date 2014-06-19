package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * Node that holds the LocalVariables that a method contains.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 9, 2014 at 4:19:57 PM
 * @version	v0.2.14 Jun 18, 2014 at 10:11:40 PM
 */
public class VariableList extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public VariableList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get whether or not there is a Variable within the list with
	 * the given name.
	 * 
	 * @param variableName The name of the variable to search for.
	 * @return Whether or not there is a Variable within the list with
	 * 		the given name.
	 */
	public boolean containsVariable(String variableName)
	{
		return getVariable(variableName) != null;
	}
	
	/**
	 * Get the Variable from the list with the given name, if it
	 * exists.
	 * 
	 * @param variableName The name of the variable to get.
	 * @return The Variable with the given name.
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
	public StringBuilder generateFreeVariablesOutput(StringBuilder builder)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Variable variable = (Variable)getChild(i);
			
			variable.generateFreeOutput(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		return builder;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			child.generateCSource(builder);
		}
		
		if (getNumChildren() > 0)
		{
			builder.append('\n');
		}
		
		return builder;
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
	 * Fill the given VariableList with the data that is in the
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