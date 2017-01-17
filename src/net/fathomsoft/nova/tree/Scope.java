package net.fathomsoft.nova.tree;

import javafx.util.Pair;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.tree.variables.VariableDeclarationList;
import net.fathomsoft.nova.tree.variables.VirtualLocalDeclaration;
import net.fathomsoft.nova.util.Location;

import java.util.ArrayList;

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
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class Scope extends Node
{
	private int localVariableID;
	
	public int	id;
	
	private ArrayList<Listener> listeners;

	private ArrayList<Pair<LocalDeclaration, Value>> assignedImplicitVariables;
	
	/**
	 * Instantiate and initialize the default values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Scope(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		listeners = new ArrayList<>();
		
		VariableDeclarationList variablesNode = new VariableDeclarationList(this, locationIn);
		
		addChild(variablesNode, this);
		
		id = getNextScopeAncestor(false).generateUniqueID(this);
	}
	
	public DefaultParameterInitialization[] getDefaultParameterInitializations()
	{
		ArrayList<DefaultParameterInitialization> list = new ArrayList<>();
		
		boolean started = false;
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node n = getChild(i);
			
			if (n instanceof DefaultParameterInitialization)
			{
				started = true;
				
				list.add((DefaultParameterInitialization)n);
			}
			else if (started)
			{
				break;
			}
		}
		
		return list.toArray(new DefaultParameterInitialization[0]);
	}
	
	private static class Triple<A, B, C>
	{
		A a;
		B b;
		C c;
		
		public Triple(A a, B b, C c)
		{
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}
	
	public void addImplicitVariableAssignment(LocalDeclaration var, Value type, Value reference)
	{
		if (assignedImplicitVariables == null)
		{
			assignedImplicitVariables = new ArrayList<>();
		}

		assignedImplicitVariables.add(new Triple<>(var, type, reference));
	}

	@Override
	public void onStackPopped()
	{
		if (assignedImplicitVariables != null)
		{
			assignedImplicitVariables.stream().forEach(x -> x.getKey().setType(x.getValue()));
		}

		super.onStackPopped();
	}
	
	public Node getFirstStatement()
	{
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			if (getVisibleChild(i) instanceof DefaultParameterInitialization == false)
			{
				return getVisibleChild(i);
			}
		}
		
		return null;
	}
	
	@Override
	public Node getNearestScopeAncestor()
	{
		return getParent();
	}
	
	@Override
	public ArrayList<Listener> getListeners()
	{
		return listeners;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	@Override
	public boolean isEmpty()
	{
		if (getNumVisibleChildren() <= 0)
		{
			return true;
		}
		
		return super.isEmpty();
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
	public Variable registerLocalVariable(Value virtual)
	{
		return registerLocalVariable(virtual, true);
	}
	
	/**
	 * Register a local variable to take the place of the virtual method
	 * call so that the method call is not called twice.
	 * 
	 * @param virtual The method call to convert into a local variable.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The newly generated local variable representing the old
	 * 		virtual method call.
	 */
	public Variable registerLocalVariable(Value virtual, boolean require)
	{
		return registerLocalVariable(virtual, virtual.getBaseNode(), require);
	}
	
	public Variable registerLocalVariable(Value virtual, Node addBefore, boolean require)
	{
		// make non circular references
		if (virtual instanceof Accessible)
		{
			Accessible accessible = (Accessible)virtual;
			
			virtual = (Value)accessible.getRootReferenceNode(true);
			
			Value clone   = (Value)virtual.clone(virtual.getParent(), virtual.getLocationIn());
			
			Value current = (virtual);//.getAccessedNode();
			Value cloneC  = (clone);//.getAccessedNode();
			
			while (current != accessible && current != null)
			{
				current = ((Accessible)current).getAccessedNode();
				cloneC  = ((Accessible)cloneC).getAccessedNode();
			}
			
			if (cloneC != null)
			{
				((Accessible)cloneC).setAccessedNode(null);
			}
			
			virtual = clone;
		}
		
		Value returned = virtual.getReturnedNode();
		
		String type = returned.generateNovaType(returned).toString();
		
		Scope scope = null;
		
		if (getParentMethod() != null)
		{
			scope = getParentMethod().getScope();
		}
		else if (getAncestorOfType(StaticBlock.class) != null)
		{
			scope = getAncestorOfType(StaticBlock.class).getScope();
		}
		else
		{
			SyntaxMessage.error("Error trying to create local variable", this);
		}
		
		//Nova.debuggingBreakpoint(addBefore.getParentClass().getName().equals("Node") && getParentMethod().getName().equals("inorder"));
		String     decl   = type + " nova_local_" + scope.localVariableID++ + " = null";
		Assignment assign = Assignment.decodeStatement(addBefore.getParent(), decl, getLocationIn(), require, true, null, virtual, false);
		
		if (assign == null)
		{
			return null;
		}
		
		Variable assignee = (Variable)assign.getAssigneeNode();
		VariableDeclaration assigneeDecl = assignee.getDeclaration();
		
		assigneeDecl.addDefaultGenericTypeArguments();
		
		VirtualLocalDeclaration localDecl = new VirtualLocalDeclaration(assigneeDecl.getParent(), assigneeDecl.getLocationIn());
		assigneeDecl.cloneTo(localDecl);
		localDecl.setReference(returned instanceof Accessible ? (Value)((Accessible)returned).getReferenceNode() : returned);
		localDecl.setType(localDecl.getType(), require);
		
		assign.setAssigneeNode(localDecl.generateUsableVariable(assignee.getParent(), assignee.getLocationIn()));
		
		assigneeDecl.getParent().replace(assigneeDecl, localDecl);
		
		Variable var = (Variable)assign.getAssigneeNode();
		
		var.setForceOriginalName(true);
		//var.getDeclaration().validateType();
		
		addBefore.getParent().addChildBefore(addBefore, assign);
		
		return (Variable)var.clone(this, getLocationIn());
	}
	
	public Variable createLocalVariable(Value type)
	{
		LocalDeclaration decl = new LocalDeclaration(type.getParent(), type.getLocationIn());
		
		decl.setName("nova_local_" + getParentMethod().getScope().localVariableID++);
		decl.setForceOriginalName(true);
		decl.setType(type.getReturnedNode().getNovaTypeValue(type.getReturnedNode()));
		
		addChild(decl);
		
		return decl.generateUsableVariable(type.getParent(), type.getLocationIn());
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
	 * @param declaration The LocalDeclaration to set the scope ID of.
	 */
	private void setVariableScopeID(LocalDeclaration declaration)
	{
		if (declaration.getScopeID() == 0)
		{
			declaration.setScopeID(getID());
		}
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
	
	public static Scope generateEmptyScope(Node parent, Location location)
	{
		return new Scope(parent, location);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Scope clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Scope node = new Scope(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Scope cloneTo(Scope node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Scope} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Scope cloneTo(Scope node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.id = id;
		
		return node;
	}
	
	/**
	 * Test the Scope class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		Scope s = new Scope(context.method, Location.INVALID);
		
		if (s.getNumChildren() != s.getNumDefaultChildren())
		{
			return "Incorrect number of default children.";
		}
		
		return null;
	}
}