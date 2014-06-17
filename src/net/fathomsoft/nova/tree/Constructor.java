package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * MethodNode extension that represents the declaration of a Constructor
 * node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:50:47 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Constructor extends Method
{
	/**
	 * Create a ConstructorNode and initialize default values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#TreeNode(Node, Location)
	 */
	public Constructor(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
//		setDataType(VariableNode.POINTER);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateJavaSource()
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
			Node child = getChild(i);
			
			if (child instanceof ParameterList == false)
			{
				builder.append(child.generateJavaSource());
			}
		}
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isVisibilityValid())
		{
			if (getVisibility() == InstanceDeclaration.PRIVATE)
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
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCSourceSignature()).append('\n');
		
		builder.append('{').append('\n');
		
		ClassDeclaration classDeclaration = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class, true);
		
		if (classDeclaration.containsNonStaticData())
		{
			builder.append("CCLASS_NEW(").append(getName()).append(", ").append(Method.getObjectReferenceIdentifier());
			
			if (!classDeclaration.containsNonStaticPrivateData())
			{
				builder.append(",");
			}
			
			builder.append(");");
		}
		else
		{
			builder.append(getName()).append('*').append(' ').append(Method.getObjectReferenceIdentifier()).append(" = NULL").append(';');
		}
		
		builder.append('\n').append('\n');
		
//		builder.append(generateMethodAssignments()).append('\n');
		
		builder.append(generateFieldDefaultAssignments());
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child != getParameterListNode())
			{
				builder.append(child.generateCSource());
			}
		}
		
		builder.append('\n').append("return ").append(Method.getObjectReferenceIdentifier()).append(';').append('\n');
		
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
		
		ClassDeclaration classDeclaration = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class);
		
		InstanceFieldList fields = classDeclaration.getFieldListNode().getPublicFieldListNode();
		
		for (int i = 0; i < fields.getNumChildren(); i++)
		{
			Variable child = (Variable)fields.getChild(i);
			
			if (!child.isExternal())
			{
				builder.append(child.generateUseOutput()).append(" = ").append(Variable.getNullText()).append(';').append('\n');
			}
		}
		
		InstanceFieldList privateFields = classDeclaration.getFieldListNode().getPrivateFieldListNode();
		
		for (int i = 0; i < privateFields.getNumChildren(); i++)
		{
			Variable child = (Variable)privateFields.getChild(i);
			
			if (!child.isExternal())
			{
				builder.append(child.generateUseOutput()).append(" = ").append(Variable.getNullText()).append(';').append('\n');
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
		
		ClassDeclaration classDeclaration = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class);
		
		MethodList methods = classDeclaration.getMethodListNode();
		
		for (int i = 0; i < methods.getNumChildren(); i++)
		{
			Method method = (Method)methods.getChild(i);
			
			builder.append(Method.getObjectReferenceIdentifier()).append("->").append(method.getName()).append(" = ").append(method.generateMethodName()).append(';').append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Method#generateCSourcePrototype()
	 */
	public String generateCSourcePrototype()
	{
		return generateCSourceSignature().concat(";");
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Method#generateCSourceSignature()
	 */
	public String generateCSourceSignature()
	{
		StringBuilder builder = new StringBuilder();

		ClassDeclaration classDeclaration = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class);
		
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		builder.append(getType()).append('*');
		
		builder.append(' ');
		
		builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append('_').append(classDeclaration.getName()).append('_');
		
		builder.append(classDeclaration.getName()).append('(');
		
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
	public static Constructor decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		int firstParenthIndex = statement.indexOf('(');
		int lastParenthIndex  = statement.lastIndexOf(')');
		
		if (firstParenthIndex >= 0)
		{
			Constructor n = new Constructor(parent, location);
			
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
					Parameter param = Parameter.decodeStatement(parent, parameters[i], location, require, false);
					
					if (param == null)
					{
						SyntaxMessage.error("Incorrect parameter definition", n);
					}
					
					n.getParameterListNode().addChild(param);
				}
			}
			
			n.iterateWords(statement);
			
			ClassDeclaration classDeclaration = (ClassDeclaration)parent.getAncestorOfType(ClassDeclaration.class, true);
			
			if (classDeclaration.getName().equals(n.getName()))
			{
				n.setType(n.getName());
				
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, int, net.fathomsoft.nova.util.Bounds, int, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		setAttribute(word, wordNumber);
		
		setName(word);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Constructor clone(Node temporaryParent, Location locationIn)
	{
		Constructor node = new Constructor(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ConstructorNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Constructor cloneTo(Constructor node)
	{
		super.cloneTo(node);
		
		return node;
	}
}