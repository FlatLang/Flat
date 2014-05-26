package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.ClassNode;
import net.fathomsoft.nova.tree.IdentifierNode;
import net.fathomsoft.nova.tree.InstanceDeclarationNode;
import net.fathomsoft.nova.tree.LocalDeclarationNode;
import net.fathomsoft.nova.tree.MethodNode;
import net.fathomsoft.nova.tree.ProgramNode;
import net.fathomsoft.nova.tree.ReturnNode;
import net.fathomsoft.nova.tree.ScopeNode;
import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.tree.ValueNode;
import net.fathomsoft.nova.tree.exceptionhandling.ExceptionNode;

/**
 * ModifierNode extension that represents the declaration of a variable
 * node type. Harnesses the needed information of each variable, such as
 * whether or not it is constant, external, or an array, and its type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:02:42 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class VariableNode extends IdentifierNode
{
	private boolean				referenceVal;
	private boolean				pointerVal;
	private boolean				constantVal, external, forceOriginal;
	
	private static final String	NULL_TEXT	= "0";
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 */
	public VariableNode(TreeNode temporaryParent)
	{
		super(temporaryParent);
	}
	
	/**
	 * Get whether or not the identifier is a reference.
	 * 
	 * @return Whether or not the identifier is a reference.
	 */
	public boolean isReference()
	{
		return referenceVal;
	}
	
	/**
	 * Set whether or not the identifier is a reference.
	 * 
	 * @param referenceVal Whether or not the identifier is a reference.
	 */
	public void setReference(boolean referenceVal)
	{
		this.referenceVal = referenceVal;
	}
	
	/**
	 * Get whether or not the identifier is a pointer.
	 * 
	 * @return Whether or not the identifier is a pointer.
	 */
	public boolean isPointer()
	{
		return pointerVal;
	}
	
	/**
	 * Set whether or not the identifier is a pointer.
	 * 
	 * @param pointerVal Whether or not the identifier is a pointer.
	 */
	public void setPointer(boolean pointerVal)
	{
		this.pointerVal = pointerVal;
	}
	
	/**
	 * Get whether or not the variable is external. For more information
	 * on external variables, see {@link #setExternal(boolean)}.
	 * 
	 * @return Whether or not the variable is external.
	 */
	public boolean isExternal()
	{
		return external;
	}
	
	/**
	 * Set whether or not the variable is external. A variable is external
	 * if it is referenced from a language outside of Nova. For example,
	 * a variable from the C language. Furthermore, a variable is external
	 * if it begins with an externally imported C file's name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * import "externalFile.h";
	 * 
	 * ...
	 * 
	 * public static void main(String args[])
	 * {
	 *	// This is the external variable declaration.
	 * 	externalFile.externalType varName;
	 * 	
	 * 	// This is the external variable assignment.
	 * 	varName = externalFile.variableInstance;
	 * }</pre></blockquote>
	 * In this example, 'externalFile' is the C header file that is
	 * imported. 'variableInstance' is the name of a variable that
	 * is contained within the imported header file.<br>
	 * 
	 * @param external Whether or not the variable will be external.
	 */
	public void setExternal(boolean external)
	{
		this.external = external;
		
		forceOriginal = true;
	}
	
	/**
	 * Get whether or not the variable's value is constant. To
	 * see more detail, look at {@link #setConstant(boolean)}.
	 * 
	 * @return Whether or not the variable's value is constant.
	 */
	public boolean isConstant()
	{
		return constantVal;
	}
	
	/**
	 * Get the C equivalent of the 'constant' keyword.
	 * 
	 * @return The C equivalent of the 'constant' keyword.
	 */
	public String getConstantText()
	{
		return "const";
	}
	
	/**
	 * Set whether or not the variable's value is constant.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private constant int MAX_PEOPLE = 10;</pre></blockquote>
	 * This variable is constant, as defined by the 'constant' keyword.
	 * 
	 * @param constVal Whether or not the variable's value
	 * 		is constant.
	 */
	public void setConstant(boolean constVal)
	{
		this.constantVal = constVal;
	}
	
	/**
	 * Set the name of the Variable.
	 * 
	 * @see net.fathomsoft.nova.tree.IdentifierNode#setName(java.lang.String)
	 * 
	 * @param name The String to set as the new name.
	 */
	public void setName(String name)
	{
		setName(name, false);
	}
	
	/**
	 * Set the name of the Variable. You specify whether or not you want
	 * the output in the C language to be the original to the given name,
	 * or if it will differentiate it. 
	 * 
	 * @param name The String to set as the new name.
	 * @param forceOriginal Whether or not the name will be output in the
	 * 		c code verbatim.
	 */
	public void setName(String name, boolean forceOriginal)
	{
		this.forceOriginal = forceOriginal;
		
		super.setName(name);
	}
	
	/**
	 * Set a specified attribute to true.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private static int index;</pre></blockquote>
	 * <u><code>private</code></u> sets the visibility of the declaration
	 * to private. <u><code>static</code></u> sets the variable as static.
	 * 
	 * @param attribute The attribute to set true.
	 */
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	/**
	 * Set a specified attribute to true.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private static int index;</pre></blockquote>
	 * <u><code>private</code></u> is the first attribute (index: 0) that
	 * sets the visibility of the declaration to private.
	 * <u><code>static</code></u> is the second attribute (index: 1) that
	 * sets the variable as static.
	 * 
	 * @param attribute The attribute to set true.
	 * @param argNum The index of the attribute in the order that it
	 * 		came in.
	 */
	public boolean setAttribute(String attribute, int argNum)
	{
		if (attribute.equals("constant"))
		{
			setConstant(true);
		}
		else if (attribute.equals("external"))
		{
			setExternal(true);
		}
		else
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get the text that represents the java 'null' in the C language.
	 * 
	 * @return The text that represents the java 'null' in the C language.
	 */
	public static String getNullText()
	{
		return NULL_TEXT;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		builder.append(getType());

		if (isReference())
		{
			builder.append('&');
		}
		else if (isPointer())
		{
			builder.append('*');
		}
		
		builder.append(' ').append(getName()).append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * Generate the representation of when the variable is being used, in
	 * action, rather than being declared.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * Person p;
	 * p.getName();</pre></blockquote>
	 * The first line shows the declaration of the Variable. The second
	 * line demonstrates a "variable use" for the "p" variable.
	 * Essentially, the "variable use" output is exactly what it says,
	 * what the variable looks like when it is being used to do something.
	 * 
	 * @return What the variable looks like when it is being used to do
	 * 		something.
	 */
	public String generateUseOutput()
	{
		return generateUseOutput(false);
	}
	
	/**
	 * Generate the representation of when the variable is being used, in
	 * action, rather than being declared.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * Person p;
	 * p.getName();</pre></blockquote>
	 * The first line shows the declaration of the Variable. The second
	 * line demonstrates a "variable use" for the "p" variable.
	 * Essentially, the "variable use" output is exactly what it says,
	 * what the variable looks like when it is being used to do something.
	 * 
	 * @param pointer Whether or not the variable is to be accessed by a
	 * 		pointer.
	 * @return What the variable looks like when it is being used to do
	 * 		something.
	 */
	public String generateUseOutput(boolean pointer)
	{
		StringBuilder builder = new StringBuilder();
		
		if (isPointer())
		{
			builder.append('*');
		}
		else if (isReference())
		{
			builder.append('&');
		}
		
		FieldNode field = null;
		
		TreeNode parent = getParent();
		
		if (parent instanceof ArrayAccessNode || parent instanceof ArrayNode)
		{
			VariableNode node = getExistingNode(parent.getParent(), getName());
			
			if (node instanceof FieldNode)
			{
				field = (FieldNode)node;
			}
		}
		else if (this instanceof FieldNode)
		{
			field = (FieldNode)this;
		}
		
		if (field != null)
		{
			if (!field.isStatic())
			{
				ValueNode ref = getReferenceNode();
				
				if (ref.isContainingClass(this))
				{
					if (pointer)
					{
						builder.append('(').append('*');
					}
					
					builder.append(MethodNode.getObjectReferenceIdentifier());
					
					if (pointer)
					{
						builder.append(')');
					}
					
					builder.append("->");
			
					if (field.getVisibility() == FieldNode.PRIVATE)
					{
						builder.append("prv").append("->");
					}
				}
			}
		}
		
		builder.append(generateCSourceName());
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return generateCSource();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		if (!isDeclaration())
		{
			return generateCSourceFragment() + ";\n";
		}
		
		StringBuilder builder = new StringBuilder();
		
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		builder.append(generateCTypeOutput());

		if (isReference())
		{
			builder.append('&');
		}
		else if (isPointer())
		{
			builder.append('*');
		}
		if (isArray())
		{
			builder.append(getArrayText());
		}
		if (!isPrimitiveType() && !isExternal())
		{
			builder.append('*');
		}
		
		builder.append(' ').append(generateCSourceFragment());
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		if (isSpecialFragment())
		{
			return generateSpecialFragment();
		}
		
		return generateUseOutput() + generateChildrenCSourceFragment();
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 * 
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public String generateCSourceName()
	{
		String name = getName();
		
		if (forceOriginal || isExternal())
		{
			return name;
		}
		
		ClassNode clazz = getDeclaringClassNode();
		
		if (this instanceof InstanceDeclarationNode)
		{
			InstanceDeclarationNode node = (InstanceDeclarationNode)this;
			
			if (node.isStatic())
			{
				return "static_" + Nova.LANGUAGE_NAME.toLowerCase() + "_" + clazz.generateUniquePrefix() + "_" + name;
			}
		}
		
		VariableNode existing = getExistingNode(getParent(), name);
		
		String str = Nova.LANGUAGE_NAME.toLowerCase() + "_" + clazz.generateUniquePrefix() + "_" + name;
		
		if (this instanceof FieldNode == false && existing instanceof LocalVariableNode)
		{
			ScopeNode scopeNode = TreeNode.getAncestorWithScope(existing.getParent()).getScopeNode();
			
			str += "_" + scopeNode.getID();
		}
		
		return str;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
//		if (isSpecialFragment())
//		{
//			return getChild(0).generateNovaInput();
//		}
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(getName());
		
		if (outputChildren)
		{
			IdentifierNode accessed = getAccessedNode();
			
			if (accessed != null)
			{
				builder.append('.').append(accessed.generateNovaInput());
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Generate a String for the code used to free memory of the
	 * specified variable.
	 * 
	 * @return The generated String for the code.
	 */
	public String generateFreeOutput()
	{
		if (isConstant())
		{
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		
		if (isPrimitiveType() || isExternal())
		{
			if (!isPrimitive())
			{
				builder.append("free(").append(generateUseOutput(true)).append(");\n");
			}
		}
		else
		{
			builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append("_del_").append(getType()).append('(').append('&').append(generateUseOutput(true)).append(", ").append(ExceptionNode.EXCEPTION_DATA_IDENTIFIER).append(");\n");
		}
		
		return builder.toString();
	}
	
	/**
	 * Get the ClassNode instance that declared this variable.
	 * 
	 * @return The ClassNode instance that declared this variable.
	 */
	public ClassNode getDeclaringClassNode()
	{
		return getDeclaringClassNode(getParent().getProgramNode());
	}
	
	/**
	 * Get the ClassNode instance that declared this variable.
	 * 
	 * @param programNode The ProgramNode to search for the Class in.
	 * @return The ClassNode instance that declared this variable.
	 */
	public ClassNode getDeclaringClassNode(ProgramNode programNode)
	{
		VariableNode var = getDeclaration(programNode);
		
		return var.getClassNode();
	}
	
	/**
	 * Get he Instance/LocalDeclarationNode that declares the
	 * specified variable.
	 * 
	 * @return The Instance/LocalDeclarationNode that declares the
	 * 		specified variable.
	 */
	public VariableNode getDeclaration()
	{
		return getDeclaration(getProgramNode());
	}
	
	/**
	 * Get he Instance/LocalDeclarationNode that declares the
	 * specified variable.
	 * 
	 * @param programNode The ProgramNode to search for the declaration
	 * 		in.
	 * @return The Instance/LocalDeclarationNode that declares the
	 * 		specified variable.
	 */
	public VariableNode getDeclaration(ProgramNode programNode)
	{
		TreeNode parent = getParent();
		
		if (this instanceof MethodNode)
		{
			return getClassNode();
		}
		if (isAccessed())
		{
			ValueNode value = (ValueNode)parent;
			
			ClassNode clazz = programNode.getClass(value.getType());
			
			return clazz.getField(getName());
		}
		// If the 'this.' part of the variable access was auto-removed.
		else if (this instanceof FieldNode)
		{
			return getClassNode().getField(getName());
		}
		
		return getExistingNode(parent, getName());
	}
	
	/**
	 * Get whether this specified variable node was accessed through
	 * the dot operator of another value node.
	 * 
	 * @return Whether or not the variable was accessed.
	 */
	public boolean isAccessed()
	{
		TreeNode parent = getParent();
		
		return parent instanceof ValueNode && !parent.containsScope() && parent instanceof ReturnNode == false && parent instanceof ArrayAccessNode == false && parent instanceof ArrayNode == false;
	}
	
	/**
	 * Get whether or not the VariableNode instance represents a
	 * declaration of a local variable.
	 * 
	 * @return Whether or not the instance represents a local variable
	 * 		declaration.
	 */
	public boolean isDeclaration()
	{
		return this instanceof LocalDeclarationNode;
	}
	
	/**
	 * Get whether or not the specified variable is being used as an
	 * action.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * //Scenario 1
	 * TreeNode node;
	 * 
	 * //Scenario 2
	 * node.getParent();</pre></blockquote>
	 * <ul>
	 * 	<li>
	 * 		In scenario 1, the variable is a declaration, NOT an active
	 * 		variable.
	 * 	</li>
	 * 	<li>
	 * 		In scenario 2, the variable is an active variable because it is
	 * 		being used to access the "<code>getParent()</code>" method.
	 * 	</li>
	 * <ul>
	 * 
	 * @return Whether or not the specified variable is being used as an
	 * 		action.
	 */
	public boolean isActiveVariable()
	{
		return this instanceof LocalVariableNode || this instanceof FieldNode;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public VariableNode clone(TreeNode temporaryParent)
	{
		VariableNode node = new VariableNode(temporaryParent);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given VariableNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VariableNode cloneTo(VariableNode node)
	{
		super.cloneTo(node);
		
		node.constantVal     = constantVal;
		node.external        = external;
		node.referenceVal    = referenceVal;
		node.pointerVal      = pointerVal;
		node.forceOriginal   = forceOriginal;
		
		return node;
	}
}
