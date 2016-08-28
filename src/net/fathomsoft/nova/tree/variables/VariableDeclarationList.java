package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.util.Location;

/**
 * Node that holds the LocalVariables that a method contains.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 9, 2014 at 4:19:57 PM
 * @version	v0.2.34 Oct 1, 2014 at 9:51:33 PM
 */
public class VariableDeclarationList extends List
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public VariableDeclarationList(Node temporaryParent, Location locationIn)
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
	public boolean containsVariable(String variableName, Scope scope)
	{
		return getVariable(variableName, scope) != null;
	}
	
	/**
	 * Get the Variable from the list with the given name, if it
	 * exists.
	 * 
	 * @param variableName The name of the variable to get.
	 * @return The Variable with the given name.
	 */
	public LocalDeclaration getVariable(String variableName, Scope scope)
	{
		LocalDeclaration valid = null;
		
		for (int i = getNumChildren() - 1; i >= 0; i--)
		{
			LocalDeclaration variable = (LocalDeclaration)getChild(i);
			
			/*Scope declScope = variable.getParentScopeAncestor().getScope(variable.getScopeID());
			
			if (declScope == null)
			{
				declScope = variable.getAncestorWithScope().getScope();
			}*/
			
			if (variable.getName().equals(variableName) && scope.getID() >= variable.getScopeID())//declScope.isAncestorOf(scope, true))
			{
				valid = variable;
			}
		}
		
		return valid;
	}
	
	public void removeChildWithName(String variableName)
	{
		removeChildWithName(variableName, getAncestorWithScope().getScope());
	}
	
	public void removeChildWithName(String variableName, Scope scope)
	{
		VariableDeclaration var = getVariable(variableName, scope);
		
		if (var != null)
		{
			removeChild(var);
		}
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
			VariableDeclaration variable = (VariableDeclaration)getChild(i);
			
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
			LocalDeclaration child = (LocalDeclaration)getChild(i);
			
			child.generateCDeclarationFragment(builder).append(" = ");
			child.generateCDefaultValue(builder);
			
			builder.append(";\n");
		}
		
		if (getNumChildren() > 0)
		{
			builder.append('\n');
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}
			
			getChild(i).generateNovaInput(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public VariableDeclarationList clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		VariableDeclarationList node = new VariableDeclarationList(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public VariableDeclarationList cloneTo(VariableDeclarationList node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link VariableDeclarationList} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VariableDeclarationList cloneTo(VariableDeclarationList node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the VariableDeclarationList class type to make sure everything
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