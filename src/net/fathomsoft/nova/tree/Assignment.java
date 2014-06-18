package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.Field;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Node extension that contains information describing a variable
 * assignment.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:19:44 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Assignment extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Assignment(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the node that is having its value modified. In other words,
	 * the left hand value of the equation. For instance, in the
	 * statement: "<code>int j = 35<code>" <u><code>int j</code></u> is
	 * the left hand value of the equation.
	 * 
	 * @return The node that represents the variable that is being
	 * 		assigned.
	 */
	public Variable getAssigneeNode()
	{
		return (Variable)getChild(0);
	}
	
	/**
	 * Get the node that is being used to set the value of the assignee
	 * node. In other words, the right hand value of the equation. For
	 * instance, in the statement: "<code>int j = 35<code>"
	 * <u><code>35</code></u> is the right hand value of the equation.
	 * 
	 * @return The node that represents the value that the assignee
	 * 		variable is being assigned to.
	 */
	public Value getAssignment()
	{
		return (Value)getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getAssigneeNode().generateJavaSource()).append(" = ").append(getAssignment().generateJavaSource());
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment() + ";\n";
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return getAssigneeNode().generateCSourceFragment() + " = " + getAssignment().generateCSourceFragment();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(boolean)
	 */
	public String generateNovaInput(boolean outputChildren)
	{
		return getAssigneeNode().generateNovaInput(outputChildren) + " = " + getAssignment().generateNovaInput(outputChildren);
	}
	
	/**
	 * Decode the given statement into an Assignment if possible. If
	 * it is not possible, then null is returned.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>int variableName = 45</li>
	 * 	<li>personsName = "Bob"</li>
	 * 	<li>Person myPeep = new Person(54)</li>
	 * 	<li>area = width * height / 2</li>
	 * 	<li>int newSize = array.getSize() + 5</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to decode into an Assignment.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The new Assignment if it decodes properly. If not,
	 * 		it returns null.
	 */
	public static Assignment decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		return decodeStatement(parent, statement, location, require, scope, true);
	}
	
	/**
	 * Decode the given statement into an Assignment if possible. If
	 * it is not possible, then null is returned.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>int variableName = 45</li>
	 * 	<li>personsName = "Bob"</li>
	 * 	<li>Person myPeep = new Person(54)</li>
	 * 	<li>area = width * height / 2</li>
	 * 	<li>int newSize = array.getSize() + 5</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to decode into an Assignment.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @param addDeclaration Whether or not to add the declaration to the
	 * 		nearest scope, if the left hand value of the equation is a
	 * 		variable declaration.
	 * @return The new Assignment if it decodes properly. If not,
	 * 		it returns null.
	 */
	public static Assignment decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope, boolean addDeclaration)
	{
		if (!SyntaxUtils.isVariableAssignment(statement))
		{
			return null;
		}
		
		Assignment n     = new Assignment(parent, location);
		
		int      equalsIndex = SyntaxUtils.findCharInBaseScope(statement, '=');
		int      endIndex    = StringUtils.findNextNonWhitespaceIndex(statement, equalsIndex - 1, -1) + 1;
		
		String   variable    = statement.substring(0, endIndex);
		
		Location varLoc      = new Location(location);
		varLoc.getBounds().setEnd(varLoc.getStart() + endIndex);
		
		Variable varNode = (Variable)SyntaxTree.decodeScopeContents(n, variable, varLoc, scope);
		
		if (varNode == null)
		{
			SyntaxMessage.error("Undeclared variable '" + variable + "'", parent, location);
		}
		
		n.validateAuthorization(varNode);
		
		if (addDeclaration)
		{
			varNode = n.addDeclaration(varNode);
		}
		
		n.addChild(varNode);
		
		int rhsIndex    = StringUtils.findNextNonWhitespaceIndex(statement, equalsIndex + 1);
		
		// Right-hand side of the equation.
		String rhs      = statement.substring(rhsIndex);
		
		Location newLoc = new Location(location);
		newLoc.setBounds(location.getStart() + rhsIndex, location.getStart() + statement.length());
		
		Node  child = decodeRightHandSide(n, rhs, newLoc, require, scope);
		
		if (child == null)
		{
			return null;
		}
		
		n.addChild(child);
		
		return n;
	}
	
	/**
	 * Validate that the assignment is authorized to assign a value
	 * to the given variable in the assignments location. The variable
	 * is not authorized to be modified under the following condition:<br>
	 * <u>The variable cannot be modified if it is <b>private</b> or
	 * <b>visible</b> and is not contained within the same class as the
	 * assignment.</u>
	 * 
	 * @param var The Variable to validate.
	 */
	private void validateAuthorization(Variable var)
	{
		Identifier id = var.getLastAccessedNode();
		
		if (id instanceof Variable)
		{
			Variable accessed = (Variable)id;
			
			if (accessed != null && accessed.isAccessed() && accessed instanceof Field)
			{
				Field field = (Field)accessed.getDeclaration();
				
				if (field.getVisibility() == Field.VISIBLE)
				{
					ClassDeclaration declaringClass = field.getDeclaringClassDeclaration();
					ClassDeclaration thisClass      = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class);
					
					if (declaringClass != thisClass)
					{
						SyntaxMessage.error("The value of the field '" + field.getName() + "' cannot be modified", accessed);
					}
				}
			}
		}
	}
	
	/**
	 * Add the variable declaration to the parent scope if the assignment
	 * was also a declaration.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * int i = 42;
	 * 
	 * // Scenario 2
	 * i = 43;</pre></blockquote>
	 * Scenario 1 includes a declaration and therefore, this method would
	 * add that declaration to its parent scope and return a clone of the
	 * declared Variable. On the other hand, scenario 2 does not
	 * include a declaration and therefore simply does nothing in this
	 * method (Returns the given Variable).
	 * 
	 * @param parent The parent of the assignment node.
	 * @param var The Variable to check whether or not declares a
	 * 		variable.
	 * @return If a variable is declared, this returns a clone of the
	 * 		declared variable. If not, this simply returns the given
	 * 		Variable instance.
	 */
	private Variable addDeclaration(Variable var)
	{
		if (var.isDeclaration())
		{
			Node scopeNode = getParent().getAncestorWithScope();
			
			if (scopeNode != null)
			{
				scopeNode.getScope().addChild(var);
				
				Location newLoc = new Location(getLocationIn());
				
				return var.clone(this, newLoc);
			}
		}
		
		return var;
	}
	
	/**
	 * Decode the right hand side of an assignment into an Node if
	 * possible. If it is not possible, then null is returned. The right
	 * hand side of an assignment must represent a value.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>45</li>
	 * 	<li>"Bob"</li>
	 * 	<li>new Person(54)</li>
	 * 	<li>width * height / 2</li>
	 * 	<li>array.getSize() + 5</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param rhs The right hand side to decode into an Assignment.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The new Node if it decodes properly. If not,
	 * 		it returns null.
	 */
	public static Node decodeRightHandSide(Node parent, String rhs, Location location, boolean require, boolean scope)
	{
		Node child = SyntaxTree.decodeScopeContents(parent, rhs, location, require);
		
		if (child == null)
		{
			Literal node = Literal.decodeStatement(parent, rhs, location, require, false);
			
			child = node;
		}
		
		return child;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Assignment clone(Node temporaryParent, Location locationIn)
	{
		Assignment node = new Assignment(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Assignment with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Assignment cloneTo(Assignment node)
	{
		super.cloneTo(node);
		
		return node;
	}
}