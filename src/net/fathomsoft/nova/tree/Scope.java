package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.lambda.LambdaMethodDeclaration;
import net.fathomsoft.nova.tree.variables.*;
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
	public int localVariableID;
	
	public int	id;
	
	private ArrayList<Listener> listeners;

	private ArrayList<Triple<LocalDeclaration, Value, Value>> assignedImplicitVariables;
	
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
		TypeList<DefaultParameterInitialization> defaultParameterInitializations = new TypeList<DefaultParameterInitialization>(this, locationIn) {
			@Override
			public void addChild(int index, Node node, Node toNode, boolean detach) {


				super.addChild(index, node, toNode, detach);
			}
		};
		TypeList<ClosureVariableAssignment> closureVariableAssignmentList = new TypeList<>(this, locationIn);

		addChild(variablesNode, this);
		addChild(defaultParameterInitializations);
		addChild(closureVariableAssignmentList);

		id = getNextScopeAncestor(false).generateUniqueID(this);
	}
	
	public String getUniqueName(String value)
	{
		return getUniqueName(value, false);
	}
	
	public String getUniqueName(String value, boolean localOnly)
	{
		VariableDeclaration var = null;
		
		while ((var = searchVariable(this, this, value, true)) != null && (!localOnly || var instanceof LocalDeclaration))
		{
			value += "_";
		}
		
		return value;
	}
	
	public ClassDeclaration[] getDependencies()
	{
		ArrayList<ClassDeclaration> list = new ArrayList<>();
		
		addVariableDependencies(list, getChildrenOfType(Variable.class));
		
		Node[] calls = getChildrenOfType(MethodCall.class);
		
		for (Node n : calls)
		{
			MethodCall call = (MethodCall)n;
			
			NovaMethodDeclaration method = call.getNovaMethod();
			
			if (method != null && method.containsScope())
			{
				ClassDeclaration[] cs = method.getScope().getDependencies();
				
				for (ClassDeclaration c : cs)
				{
					if (!list.contains(c) && c != getParentClass())
					{
						list.add(c);
					}
				}
				
				if (method instanceof Constructor && ((Constructor)method).initMethod != null)
				{
					cs = ((Constructor)method).initMethod.getScope().getDependencies();
					
					for (ClassDeclaration c : cs)
					{
						if (!list.contains(c) && c != getParentClass())
						{
							list.add(c);
						}
					}
				}
			}
		}
		
		Node[] inits = getChildrenOfType(DefaultParameterInitialization.class);
		
		for (Node n : inits)
		{
			DefaultParameterInitialization init = (DefaultParameterInitialization)n;
			
			if (init.parameter.defaultValue != null)
			{
				addVariableDependencies(list, init.parameter.defaultValue.getChildrenOfType(Variable.class, true));
			}
		}
		
		return list.toArray(new ClassDeclaration[0]);
	}
	
	private void addVariableDependencies(ArrayList<ClassDeclaration> list, Node[] vars)
	{
		for (Node n : vars)
		{
			Variable v = (Variable)n;
			
			if (v.declaration instanceof InstanceDeclaration)
			{
				InstanceDeclaration i = (InstanceDeclaration)v.declaration;
				
				if (i.isStatic())
				{
					ClassDeclaration c = i.getParentClass();
					
					if (!list.contains(c) && c != getParentClass())
					{
						list.add(c);
					}
				}
			}
		}
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
	public void onStackPopped(Node parent)
	{
		if (assignedImplicitVariables != null)
		{
			assignedImplicitVariables.stream().forEach(x ->
			{
				byte prev = x.a.getDataType();
				
				x.a.setType(x.b);
				
				if (x.a.getDataType() > prev && x.c.getDataType() >= prev)
				{
					x.a.setDataType(prev);
				}
			});
		}

		super.onStackPopped(parent);
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
		return super.getNumDefaultChildren() + 3;
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

	public TypeList<DefaultParameterInitialization> getDefaultParameterInitializations()
	{
		return (TypeList<DefaultParameterInitialization>)getChild(super.getNumDefaultChildren() + 1);
	}

	public TypeList<ClosureVariableAssignment> getClosureVariableAssignmentList()
	{
		return (TypeList<ClosureVariableAssignment>)getChild(super.getNumDefaultChildren() + 2);
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
		
		String localName = "nova_local_" + scope.localVariableID++;
		
		//Nova.debuggingBreakpoint(addBefore.getParentClass().getName().equals("Node") && getParentMethod().getName().equals("inorder"));
		String     decl   = "var " + type + " " + localName + " = " + virtual.getDefaultLiteralValue();
		
		if (type.contains("("))
		{
			decl = "var " + localName + type.substring(type.indexOf('(')) + " = null";
		}
		
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
		localDecl.setType(assigneeDecl);
		
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
//		decl.setProperty("userMade", false);
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
		
		getVariableList().generateNovaInput(builder);
		
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			if (getVisibleChild(i).isUserMade(false))
			{
				getVisibleChild(i).generateNovaInput(builder).append('\n');
			}
		}
		
		builder.append('}').append('\n');
		
		return builder;
	}
	
	public void extractLambdas()
	{
		Node[] nodes = getChildrenOfType(Closure.class);
		
		for (Node n : nodes)
		{
			Closure c = (Closure)n;
			VariableDeclaration d = c.declaration;
			
			if (d instanceof LambdaMethodDeclaration)
			{
				LambdaMethodDeclaration lambda = (LambdaMethodDeclaration)d;
				
				Literal replacement = new Literal(c.getParent(), Location.INVALID);
				
				String params = "";
				
				for (int i = 0; i < lambda.getParameterList().getNumParameters(); i++)
				{
					if (i > 0)
					{
						params += ", ";
					}
					
					params += lambda.getParameterList().getParameter(i).getName();
				}
				
				replacement.setValue("(" + params + ") => " + c.getDeclaration().getScope().generateNovaInput().toString());
				
				c.replaceWith(replacement);
			}
		}
	}
	
	public void extractArrayInitializers()
	{
		Node[] nodes = getChildrenOfType(MethodCall.class);
		
		for (Node n : nodes)
		{
			MethodCall c = (MethodCall)n;
			VariableDeclaration d = c.declaration;
			
			if (d.containsProperty("array"))
			{
				Array a = (Array)d.getProperty("array");
				
				Literal replacement = new Literal(c.getParent(), Location.INVALID);
				
				replacement.setValue(a.initializer);
				
				c.replaceWith(replacement);
			}
		}
	}
	
	public String getNovaContents()
	{
		String code = generateNovaInput().toString().trim();
		
		return code.substring(1, code.length() - 1).trim();
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