package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.exceptionhandling.ExceptionNode;
import net.fathomsoft.nova.tree.variables.FieldNode;
import net.fathomsoft.nova.tree.variables.InstanceFieldListNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * MethodNode extension that represents the declaration of a Destructor
 * node type. See {@link #decodeStatement(TreeNode, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:50:43 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class DestructorNode extends MethodNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public DestructorNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
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
				SyntaxMessage.error("Destructor must be public", this);
			}
		}
		
		if (isConstant())
		{
			SyntaxMessage.error("Destructor cannot be const", this);
		}
		
		if (isReference())
		{
			SyntaxMessage.error("Destructor cannot return a reference", this);
		}
		
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
		
		builder.append(generateCSourceSignature()).append('\n').append('{').append('\n');

		builder.append(nullChecker()).append('\n');
		
		builder.append(deleteData()).append('\n');
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getParameterListNode())
			{
				builder.append(child.generateCSource());
			}
		}
		
		builder.append("NOVA_FREE(").append('*').append(MethodNode.getObjectReferenceIdentifier()).append(");").append('\n');
		
		//builder.append('*').append(MethodNode.getObjectReferenceIdentifier()).append(" = NULL;").append('\n');
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * Generate the code needed to check if a variable is null before
	 * trying to free its members.
	 * 
	 * @return The code needed to check whether a variable is null or not.
	 */
	private String nullChecker()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("if (!*").append(MethodNode.getObjectReferenceIdentifier()).append(')').append('\n');
		builder.append('{').append('\n');
		builder.append("return;").append('\n');
		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * Generate the code needed to delete each member of the class.
	 * 
	 * @return The code needed to delete each member of the class.
	 */
	private String deleteData()
	{
		StringBuilder builder   = new StringBuilder();
		
		ClassNode     classNode = (ClassNode)getAncestorOfType(ClassNode.class);
		
		InstanceFieldListNode privateFields = classNode.getFieldListNode().getPrivateFieldListNode();
		
		for (int i = 0; i < privateFields.getNumChildren(); i++)
		{
			FieldNode field = (FieldNode)privateFields.getChild(i);

			builder.append(generateFreeFieldSource(field)).append('\n');
		}
		
		if (classNode.containsNonStaticPrivateData())
		{
			builder.append(generateFreeMemberSource("prv")).append('\n');
		}
		
		InstanceFieldListNode publicFields = classNode.getFieldListNode().getPublicFieldListNode();
		
		for (int i = 0; i < publicFields.getNumChildren(); i++)
		{
			FieldNode field = (FieldNode)publicFields.getChild(i);
			
			builder.append(field.generateFreeOutput());
		}
		
		return builder.toString();
	}
	
	/**
	 * Generate a String for the code used to free memory of an allocated
	 * field variable located within the current class.
	 * 
	 * @param field The node that contains the information of the field.
	 * @return The generated String for the code.
	 */
	private String generateFreeFieldSource(FieldNode field)
	{
		StringBuilder builder = new StringBuilder();
		
		if (field.isPrimitiveType() || field.isExternalType())
		{
			if (!field.isPrimitive())
			{//builder.append("printf(\"Before. " + field.generateVariableUseOutput(true) + ": %p\", " + field.generateVariableUseOutput(true) + ");");
				//builder.append("NOVA_FREE(").append(field.generateVariableUseOutput(true)).append(");");builder.append("printf(\"Aft2.\");");
			}
		}
		else
		{
			builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append("_del_").append(field.getType()).append('(').append('&').append(field.generateUseOutput(true)).append(", ").append(ExceptionNode.EXCEPTION_DATA_IDENTIFIER).append(");");
		}
		
		return builder.toString();
	}
	
	/**
	 * Generate a String for the code used to free memory of an allocated
	 * member variable with the given name.
	 * 
	 * @param name The name of the variable to delete.
	 * @return The generated String for the code.
	 */
	private String generateFreeMemberSource(String name)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("NOVA_FREE((*").append(MethodNode.getObjectReferenceIdentifier()).append(")->").append(name).append(");");
		
		//builder.append("(*").append(MethodNode.getObjectReferenceIdentifier()).append(")->").append(name).append(" = NULL;");
		
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
		
		builder.append(getType());
		builder.append(' ');
		builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append("_del_");
		builder.append(classNode.getName()).append('(');
		
		//builder.append(classNode.getName()).append('*').append(' ').append(MethodNode.getObjectReferenceIdentifier());
		builder.append(getParameterListNode().generateCSource());
		
		builder.append(')');
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a DestructorNode instance, if
	 * possible. If it is not possible, this method returns null. A
	 * destructor must have the same name as the class that it is
	 * within preceded by a tilde (A tilde is a '~' located above the tab
	 * key). Destructors also do not have a return value and are always
	 * private. They never accept parameters, because they are never
	 * called programmatically.<br>
	 * <br>
	 * The only acceptable syntax input includes: "private ~ClassName()"
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		DestructorNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a DestructorNode.
	 */
	public static DestructorNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean scope)
	{
		int firstParenthIndex = statement.indexOf('(');
		int lastParenthIndex  = statement.lastIndexOf(')');
		
		if (firstParenthIndex >= 0)
		{
			String signature = statement.substring(0, firstParenthIndex);
			
			DestructorNode n = new DestructorNode(parent, location);
			
			// TODO: make better check for last parenth. Take a count of each of the starting parenthesis and
			// subtract the ending ones from the number.
			if (lastParenthIndex < 0)
			{
				SyntaxMessage.error("Expected a ')' ending parenthesis", n);
			}
			
			String parameterList = statement.substring(firstParenthIndex + 1, lastParenthIndex);
			
			String parameters[]  = StringUtils.splitCommas(parameterList);
			
			n.iterateWords(signature);
			
			ClassNode classNode = (ClassNode)parent.getAncestorOfType(ClassNode.class, true);
			
			if (classNode.getName().equals(n.getName()))
			{
				if (n.getName() == null)
				{
					return null;
				}
				
				if (parameters.length > 0)
				{
					SyntaxMessage.error("Destructors cannot have any parameters", n);
				}
				
				n.setLocationIn(location);
				n.setType("void");
				
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
		
		if (wordNumber == numWords - 1)
		{
			if (bounds.getStart() > 0)
			{
				if (leftDelimiter.equals("~"))
				{
					setName(word);
				}
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
	 */
	@Override
	public DestructorNode clone(TreeNode temporaryParent, Location locationIn)
	{
		DestructorNode node = new DestructorNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given DestructorNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public DestructorNode cloneTo(DestructorNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}