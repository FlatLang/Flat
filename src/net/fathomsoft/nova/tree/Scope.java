package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.variables.VariableList;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents a scope of code. In essence, a
 * collection of statements within a pair of curly braces.<br>
 * <br>
 * For example:
 * <blockquote><pre>
 * {
 * 	...
 * 
 * 	// Statements within here...
 * 
 * 	...
 * }</pre></blockquote>
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Apr 5, 2014 at 10:54:20 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Scope extends Node
{
	private int	id;
	
	/**
	 * Instantiate and initialize the default values.
	 * 
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Scope(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		VariableList variablesNode = new VariableList(this, locationIn);
		
		super.addChild(variablesNode);
		
		Method method = getParent().getMethod();
		
		id = method.generateUniqueID();
	}
	
	/**
	 * Get the VariableList that contains all of the variables
	 * that have been declared within this Scope.
	 * 
	 * @return The VariableList instance.
	 */
	public VariableList getVariableList()
	{
		return (VariableList)getChild(0);
	}
	
	/**
	 * Get the id of the scope used for variable name differentiation.
	 * 
	 * @return The id of the scope instance.
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#addChild(Node)
	 */
	@Override
	public void addChild(Node child)
	{
		if (child instanceof Variable)
		{
			Variable var = (Variable)child;
			
			if (var.isDeclaration())
			{
				if (child instanceof LocalDeclaration)
				{
					LocalDeclaration declaration = (LocalDeclaration)child;
					
					if (declaration.getScopeID() == 0)
					{
						declaration.setScopeID(getID());
					}
				}
				
//				Method method = (Method)var.getAncestorOfType(Method.class);
//				
//				method.getScope().getVariableList().addChild(var);
				getVariableList().addChild(var);
				
				return;
			}
		}
//		else if (child instanceof ExternalType)
//		{
////			getVariableList().addChild(child.getChild(0).clone());
//			
//			return;
//		}
		
		super.addChild(child);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		if (getNumChildren() <= 1)
		{
			if (getParent() instanceof Method == false)
			{
				if (getParent() instanceof Loop)
				{
					return ";";
				}
				
				return "";
			}
		}
		
		StringBuilder builder = new StringBuilder();
		
		builder.append('{').append('\n');
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			builder.append(child.generateCSource());
		}
		
//		VariableList variables = getVariableList();
//		
//		builder.append(variables.generateFreeVariablesOutput());
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Scope clone(Node temporaryParent, Location locationIn)
	{
		Scope node = new Scope(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Scope with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Scope cloneTo(Scope node)
	{
		super.cloneTo(node);
		
		return node;
	}
}