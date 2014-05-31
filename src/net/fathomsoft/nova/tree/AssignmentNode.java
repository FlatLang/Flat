package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.FieldNode;
import net.fathomsoft.nova.tree.variables.VariableNode;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * TreeNode extension that contains information describing a variable
 * assignment.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:19:44 PM
 * @version	v0.2.11 May 31, 2014 at 1:19:11 PM
 */
public class AssignmentNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public AssignmentNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}

	/**
	 * Get the node that stores the variable that is having its value
	 * assigned. In other words, the left hand value of the equation.
	 * For instance, in the statement: "int j = 35" int j is the left
	 * hand value of the equation.<br>
	 * <br>
	 * This does not return a VariableNode type because an ArrayAccessNode
	 * is also a valid left hand value to an equation.
	 * 
	 * @return The TreeNode that holds the value of the variable that
	 * 		is to be assigned.
	 */
	public VariableNode getVariableNode()
	{
		return (VariableNode)getChild(0);
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getChild(0).generateJavaSource()).append(" = ");
		
		for (int i = 1; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
		}
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCSourceFragment());
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getVariableNode().generateCSourceFragment());
		
		builder.append(" = ");
		
		TreeNode child = getChild(1);
		
		builder.append(child.generateCSourceFragment());
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput(boolean)
	 */
	public String generateNovaInput(boolean outputChildren)
	{
		return getVariableNode().generateNovaInput(outputChildren) + " = " + getChild(1).generateNovaInput(outputChildren);
	}
	
	/**
	 * Decode the given statement into an AssignmentNode if possible. If
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
	 * @param statement The statement to decode into an AssignmentNode.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The new AssignmentNode if it decodes properly. If not,
	 * 		it returns null.
	 */
	public static AssignmentNode decodeStatement(TreeNode parent, String statement, Location location, boolean require)
	{
		return decodeStatement(parent, statement, location, require, true);
	}
	
	/**
	 * Decode the given statement into an AssignmentNode if possible. If
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
	 * @param statement The statement to decode into an AssignmentNode.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param addDeclaration Whether or not to add the declaration to the
	 * 		nearest scope, if the left hand value of the equation is a
	 * 		variable declaration.
	 * @return The new AssignmentNode if it decodes properly. If not,
	 * 		it returns null.
	 */
	public static AssignmentNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean addDeclaration)
	{
		if (!SyntaxUtils.isVariableAssignment(statement))
		{
			return null;
		}
		
		int  equalsIndex = SyntaxUtils.findCharInBaseScope(statement, '=');
		int     endIndex = StringUtils.findNextNonWhitespaceIndex(statement, equalsIndex - 1, -1) + 1;
		
		String  variable = statement.substring(0, endIndex);
		
		Location  varLoc = new Location(location);
		varLoc.getBounds().setEnd(varLoc.getStart() + endIndex);
		
		VariableNode varNode = (VariableNode)decodeScopeContents(parent, variable, varLoc);
		
		if (varNode == null)
		{
			SyntaxMessage.error("Undeclared variable '" + variable + "'", parent, location);
		}
		
		IdentifierNode id = varNode.getLastAccessedNode();
		
		if (id instanceof VariableNode)
		{
			VariableNode accessed = (VariableNode)id;
			
			if (accessed != null && accessed.isAccessed() && accessed instanceof FieldNode)
			{
				FieldNode field = (FieldNode)accessed.getDeclaration();
				
				if (field.getVisibility() == FieldNode.VISIBLE)
				{
					ClassNode declaringClass = field.getDeclaringClassNode();
					ClassNode thisClass      = (ClassNode)parent.getAncestorOfType(ClassNode.class, true);
					
					if (declaringClass != thisClass)
					{
						SyntaxMessage.error("The value of the field '" + field.getName() + "' cannot be modified", accessed);
					}
				}
			}
		}
		
		varNode.setLocationIn(varLoc);
		
		AssignmentNode n = new AssignmentNode(parent, location);
		
		if (addDeclaration)
		{
			if (varNode instanceof VariableNode)
			{
				VariableNode var = (VariableNode)varNode;
				
				if (var.isDeclaration())
				{
					TreeNode scope = getAncestorWithScope(parent);
					
					if (scope != null)
					{
						scope.getScopeNode().addChild(varNode);
						
						varNode = var.clone(n, location);
					}
				}
			}
		}
		
		n.addChild(varNode);
		
		int rhsIndex    = StringUtils.findNextNonWhitespaceIndex(statement, equalsIndex + 1);
		
		// Right-hand side of the equation.
		String rhs      = statement.substring(rhsIndex);
		
		Location newLoc = new Location(location);
		newLoc.setBounds(location.getStart() + rhsIndex, location.getStart() + statement.length());
		
		TreeNode  child = decodeRightHandSide(parent, rhs, newLoc, require);
		
		if (child == null)
		{
			return null;
		}
		
		n.addChild(child);
		
		return n;
	}
	
	/**
	 * Decode the right hand side of an assignment into an TreeNode if
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
	 * @param rhs The right hand side to decode into an AssignmentNode.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The new TreeNode if it decodes properly. If not,
	 * 		it returns null.
	 */
	public static TreeNode decodeRightHandSide(TreeNode parent, String rhs, Location location, boolean require)
	{
		TreeNode child = decodeScopeContents(parent, rhs, location, require);
		
		if (child == null)
		{
			if (SyntaxUtils.isExternal(parent.getFileNode(), rhs))
			{
				rhs = rhs.substring(rhs.indexOf('.') + 1);
			}
			
			LiteralNode node = LiteralNode.decodeStatement(parent, rhs, location, require);
			
			child = node;
		}
		
		return child;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public AssignmentNode clone(TreeNode temporaryParent, Location locationIn)
	{
		AssignmentNode node = new AssignmentNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given AssignmentNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public AssignmentNode cloneTo(AssignmentNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}