package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.TreeNode.ExtraData;
import net.fathomsoft.nova.tree.variables.InstanceFieldListNode;
import net.fathomsoft.nova.tree.variables.VariableNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * MethodNode extension that represents the declaration of a Constructor
 * node type. See {@link #decodeStatement(TreeNode, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:50:47 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ConstructorNode extends MethodNode
{
	/**
	 * Create a ConstructorNode and initialize default values.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ConstructorNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
//		setDataType(VariableNode.POINTER);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isVisibilityValid())
		{
			builder.append(getVisibilityText()).append(' ');
		}
		if (isStatic())
		{
			SyntaxMessage.error("Constructor cannot be static", this);
		}
		if (isConstant())
		{
			SyntaxMessage.error("Constructor cannot be const", this);
		}
		
		if (isReference())
		{
			SyntaxMessage.error("Constructor cannot return a reference", this);
		}
		else if (isPointer())
		{
			SyntaxMessage.error("Constructor cannot return a pointer", this);
		}
		
		builder.append(getName()).append('(');
		
		builder.append(getParameterListNode().generateJavaSource());
		
		builder.append(')').append('\n').append('{').append('\n');
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof ParameterListNode == false)
			{
				builder.append(child.generateJavaSource());
			}
		}
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isVisibilityValid())
		{
			if (getVisibility() == InstanceDeclarationNode.PRIVATE)
			{
				return "";
			}
		}
//		if (isStatic())
//		{
//			SyntaxError.outputNewError("Constructor cannot be static", getLocationIn());
//			
//			return null;
//		}
		if (isConstant())
		{
			SyntaxMessage.error("Constructor cannot be const", this);
		}
		
		if (isReference())
		{
			SyntaxMessage.error("Constructor cannot return a reference", this);
		}
//		else if (isPointer())
//		{
//			SyntaxError.outputNewError("Constructor cannot return a pointer", getLocationIn());
//			
//			return null;
//		}
		
		builder.append(generateCSourcePrototype()).append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCSourceSignature()).append('\n');
		
		builder.append('{').append('\n');
		
		ClassNode classNode = (ClassNode)getAncestorOfType(ClassNode.class, true);
		
		if (classNode.containsNonStaticData())
		{
			builder.append("CCLASS_NEW(").append(getName()).append(", ").append(MethodNode.getObjectReferenceIdentifier());
			
			if (!classNode.containsNonStaticPrivateData())
			{
				builder.append(",");
			}
			
			builder.append(");");
		}
		else
		{
			builder.append(getName()).append('*').append(' ').append(MethodNode.getObjectReferenceIdentifier()).append(" = NULL").append(';');
		}
		
		builder.append('\n').append('\n');
		
//		builder.append(generateMethodAssignments()).append('\n');
		
		builder.append(generateFieldDefaultAssignments());
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getParameterListNode())
			{
				builder.append(child.generateCSource());
			}
		}
		
		builder.append('\n').append("return ").append(MethodNode.getObjectReferenceIdentifier()).append(';').append('\n');
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * This method returns a String that contains the code needed to
	 * assign the default null value to each uninitialized/uninstantiated
	 * field variables.
	 * 
	 * @return A String containing the code needed to assign default values
	 * 		to each uninitialized/uninstantiated field.
	 */
	private String generateFieldDefaultAssignments()
	{
		StringBuilder builder = new StringBuilder();
		
		ClassNode classNode = (ClassNode)getAncestorOfType(ClassNode.class);
		
		InstanceFieldListNode fields = classNode.getFieldListNode().getPublicFieldListNode();
		
		for (int i = 0; i < fields.getNumChildren(); i++)
		{
			VariableNode child = (VariableNode)fields.getChild(i);
			
			if (!child.isExternal())
			{
				builder.append(child.generateUseOutput()).append(" = ").append(VariableNode.getNullText()).append(';').append('\n');
			}
		}
		
		InstanceFieldListNode privateFields = classNode.getFieldListNode().getPrivateFieldListNode();
		
		for (int i = 0; i < privateFields.getNumChildren(); i++)
		{
			VariableNode child = (VariableNode)privateFields.getChild(i);
			
			if (!child.isExternal())
			{
				builder.append(child.generateUseOutput()).append(" = ").append(VariableNode.getNullText()).append(';').append('\n');
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * This method returns a String that contains the code needed to
	 * assign the c method pointers to the respective methods in the
	 * class.
	 * 
	 * @return A String containing the code needed to assign the methods.
	 */
	private String generateMethodAssignments()
	{
		StringBuilder builder = new StringBuilder();
		
		ClassNode classNode = (ClassNode)getAncestorOfType(ClassNode.class);
		
		MethodListNode methods = classNode.getMethodListNode();
		
		for (int i = 0; i < methods.getNumChildren(); i++)
		{
			MethodNode method = (MethodNode)methods.getChild(i);
			
			builder.append(MethodNode.getObjectReferenceIdentifier()).append("->").append(method.getName()).append(" = ").append(method.generateMethodName()).append(';').append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodNode#generateCSourcePrototype()
	 */
	public String generateCSourcePrototype()
	{
		return generateCSourceSignature().concat(";");
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodNode#generateCSourceSignature()
	 */
	public String generateCSourceSignature()
	{
		StringBuilder builder = new StringBuilder();

		ClassNode classNode = (ClassNode)getAncestorOfType(ClassNode.class);
		
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		builder.append(getType()).append('*');
		
		builder.append(' ');
		
		builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append('_').append(classNode.getName()).append('_');
		
		builder.append(classNode.getName()).append('(');
		
		builder.append(getParameterListNode().generateCSource());
		
		builder.append(')');
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a ConstructorNode instance, if
	 * possible. If it is not possible, this method returns null. A
	 * constructor must have the same name as the class that it is
	 * within. Constructors also do not have a return value.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public ClassName()</li>
	 * 	<li>private ClassName(int numChildren, String name)</li>
	 * 	<li>public ClassName(TreeNode parent, Location location)</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ConstructorNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ConstructorNode.
	 */
	public static ConstructorNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean scope)
	{
		int firstParenthIndex = statement.indexOf('(');
		int lastParenthIndex  = statement.lastIndexOf(')');
		
		if (firstParenthIndex >= 0)
		{
			ConstructorNode n = new ConstructorNode(parent, location);
			
			// TODO: make better check for last parenth. Take a count of each of the starting parenthesis and
			// subtract the ending ones from the number.
			if (lastParenthIndex < 0)
			{
				SyntaxMessage.error("Expected a ')' ending parenthesis", n);
			}
			
			String parameterList = statement.substring(firstParenthIndex + 1, lastParenthIndex);
			
			String parameters[]  = StringUtils.splitCommas(parameterList);
			
			statement = statement.substring(0, firstParenthIndex);
			
			for (int i = 0; i < parameters.length; i++)
			{
				if (parameters[i].length() > 0)
				{
					ParameterNode param = ParameterNode.decodeStatement(parent, parameters[i], location, require, false);
					
					if (param == null)
					{
						SyntaxMessage.error("Incorrect parameter definition", n);
					}
					
					n.getParameterListNode().addChild(param);
				}
			}
			
			n.iterateWords(statement);
			
			ClassNode classNode = (ClassNode)parent.getAncestorOfType(ClassNode.class, true);
			
			if (classNode.getName().equals(n.getName()))
			{
				n.setType(n.getName());
				
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#interactWord(java.lang.String, int, net.fathomsoft.nova.util.Bounds, int, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.TreeNode.ExtraData)
	 */
	@Override
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		setAttribute(word, wordNumber);
		
		setName(word);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
	 */
	@Override
	public ConstructorNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ConstructorNode node = new ConstructorNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ConstructorNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ConstructorNode cloneTo(ConstructorNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}