package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclarationList;
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
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class Scope extends Node
{
	private int	id, localVariableID;
	
	/**
	 * Instantiate and initialize the default values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Scope(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		VariableDeclarationList variablesNode  = new VariableDeclarationList(this, locationIn);
		
		addChild(variablesNode, this);
		
		id = getParentMethod().generateUniqueID();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 2;
	}
	
	/**
	 * Get the VariableList that contains all of the variables
	 * that have been declared within this Scope.
	 * 
	 * @return The VariableDeclarationList instance.
	 */
	public VariableDeclarationList getVariableList()
	{
		return (VariableDeclarationList)getChild(super.getNumDefaultChildren() + 0);
	}
	
	/**
	 * Register a local variable to take the place of the virtual method
	 * call so that the method call is not called twice.
	 * 
	 * @param virtual The method call to convert into a local variable.
	 * @return The newly generated local variable representing the old
	 * 		virtual method call.
	 */
	public Variable registerLocalVariable(MethodCall virtual)
	{
		String     value;
		Node       base;
		String     decl;
		Assignment assignment;
		Variable   variable;
		
		value      = virtual.getRootReferenceNode(true).generateNovaInputUntil(virtual).toString();
		base       = virtual.getBaseNode();
		decl       = virtual.getType() + " nova_local_" + localVariableID++ + " = " + value;
		assignment = Assignment.decodeStatement(this, decl, getLocationIn(), true, true);
		variable   = (Variable)assignment.getAssigneeNode();
		
		variable.setForceOriginalName(true);
		
		base.getParent().addChildBefore(base, assignment);
		
		return variable.clone(this, getLocationIn());
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
		if (child instanceof LocalDeclaration)
		{
			LocalDeclaration var = (LocalDeclaration)child;
			
			setVariableScopeID(var);
			
			getVariableList().addChild(var);
		}
		else
		{
			super.addChild(child);
		}
	}
	
	/**
	 * Set the scope ID of the given variable if it is a valid
	 * LocalDeclaration and its scope ID has not already been set.
	 * 
	 * @param var The LocalDeclaration to set the scope ID of.
	 */
	private void setVariableScopeID(LocalDeclaration declaration)
	{
		if (declaration.getScopeID() == 0)
		{
			declaration.setScopeID(getID());
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSource(builder, true);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 * 
	 * @param braces Whether or not to output the braces as well.
	 */
	public StringBuilder generateCSource(StringBuilder builder, boolean braces)
	{
		if (getNumChildren() <= 1)
		{
			if (getParent() instanceof Loop)
			{
				// Insert the semicolon before the new line.
				return builder.insert(builder.length() - 1, ";");
			}
		}
		
		if (braces)
		{
			builder.append('{').append('\n');
		}
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			getChild(i).generateCSource(builder);
		}
		
		if (braces)
		{
			builder.append('}').append('\n');
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append('{').append('\n');
		
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			getVisibleChild(i).generateNovaInput(builder).append('\n');
		}
		
		builder.append('}').append('\n');
		
		return builder;
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
	 * Fill the given {@link Scope} with the data that is in the
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
	
	/**
	 * Test the Scope class type to make sure everything
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