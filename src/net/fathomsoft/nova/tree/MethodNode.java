package net.fathomsoft.nova.tree;

import java.util.ArrayList;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * DeclarationNode extension that represents the declaration of a method
 * node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:10:53 PM
 * @version	v0.2.12 Jun 1, 2014 at 7:28:35 PM
 */
public class MethodNode extends InstanceDeclarationNode
{
//	private boolean					externalType;
	
	private String					types[];
	
	private ArrayList<MethodNode>	overridingMethods;
	
	/**
	 * Instantiate and initialize default data.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public MethodNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		overridingMethods = new ArrayList<MethodNode>();
		
		ParameterListNode parameterList = new ParameterListNode(this, null);
		ScopeNode         scopeNode     = new ScopeNode(this, null);
		
		super.addChild(parameterList);
		super.addChild(scopeNode);
	}
	
	/**
	 * The the ParameterListNode that represents the parameters of the
	 * method.<br>
	 * For example:<br>
	 * <blockquote><pre>
	 * public void methodName(int age, String name);</pre></blockquote>
	 * In the previous statement, the data within the parenthesis are the
	 * parameters of the method. The ParameterListNode returned by this
	 * method would contain a node for each of the parameter specified, in
	 * the correct order from left to right.
	 * 
	 * @return The ParameterListNode that represents the parameters of the
	 * 		method.
	 */
	public ParameterListNode getParameterListNode()
	{
		return (ParameterListNode)getChild(0);
	}
	
	/**
	 * Get the ParameterNode that the given index represents. The
	 * parameters are ordered from left to right, 0 being the first.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void run(int a, int b, int c)
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * If you were to call getParameterNode(2) on the method
	 * above, you would receive the c ParameterNode.
	 * 
	 * @param parameterIndex The index parameter to get.
	 * @return The ParameterNode at the given index.
	 */
	public ParameterNode getParameterNode(int parameterIndex)
	{
		return getParameterListNode().getParameterNode(parameterIndex);
	}
	
//	/**
//	 * Get whether or not the specified method returns an external type.
//	 * For more details on what an external type looks like, see
//	 * {@link net.fathomsoft.nova.tree.variables.VariableNode#setExternal(boolean)}
//	 * 
//	 * @return Whether or not the specified method returns an external
//	 * 		type.
//	 */
//	public boolean isExternalType()
//	{
//		return externalType;
//	}
//	
//	/**
//	 * Set whether or not the specified method returns an external type.
//	 * For more details on what an external type looks like, see
//	 * {@link net.fathomsoft.nova.tree.variables.VariableNode#setExternal(boolean)}
//	 * 
//	 * @param externalType Whether or not the specified method returns an
//	 * 		external type.
//	 */
//	public void setExternalType(boolean externalType)
//	{
//		this.externalType = externalType;
//	}
	
	/**
	 * Get whether or not a call to the method would need to pass a
	 * reference to itself to the method as an argument.
	 * 
	 * @return Whether or not a method call needs to pass a reference.
	 */
	public boolean needsReference()
	{
		return this instanceof ConstructorNode == false && !isStatic();
	}

	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableNode#setExternal(boolean)
	 */
	@Override
	public void setExternal(boolean external)
	{
		super.setExternal(external);
		
		setStatic(true);
		setVisibility(PUBLIC);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#getScopeNode()
	 */
	@Override
	public ScopeNode getScopeNode()
	{
		if (isExternal())
		{
			return null;
		}
		
		return (ScopeNode)getChild(1);
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
//		if (child instanceof LocalVariableNode)
//		{
//			getLocalVariableListNode().addChild(child);
//		}
//		else
//		{
//			super.addChild(child);
//		}
		getScopeNode().addChild(child);
	}
	
	/**
	 * Get whether or not the specified MethodNode has overridden a method
	 * from a super class
	 * 
	 * @return Whether or not the specified MethodNode has overridden a
	 * 		method from a super class.
	 */
	public boolean doesOverride()
	{
		return getOverriddenMethod() != null;
	}
	
	/**
	 * Get the MethodNode instance that this MethodNode overrides, if one
	 * exists.
	 * 
	 * @return The MethodNode instance that this MethodNode overrides, if
	 * 		one exists.
	 */
	public MethodNode getOverriddenMethod()
	{
		ClassNode clazz     = getClassNode();
		
		ClassNode extension = clazz.getExtendedClass();
		
		if (extension != null)
		{
			MethodNode method = extension.getMethod(getName());
			
			return method;
		}
		
		return null;
	}
	
	/**
	 * Get whether or not the specified MethodNode has been overridden by
	 * a sub class.
	 * 
	 * @return Whether or not the specified MethodNode has been
	 * 		overridden.
	 */
	public boolean isOverridden()
	{
		return overridingMethods.size() > 0;
	}
	
	/**
	 * Get the MethodNode instance that overrides this MethodNode, if
	 * any exists.
	 * 
	 * @return The MethodNode instance that overrides this MethodNode, if
	 * 		any exists.
	 */
	public MethodNode[] getOverridingMethods()
	{
		return overridingMethods.toArray(new MethodNode[0]);
	}
	
	/**
	 * Add a MethodNode instance that overrides this MethodNode.
	 * 
	 * @param overridingMethod The MethodNode instance that overrides
	 * 		this MethodNode.
	 */
	private void addOverridingMethod(MethodNode overridingMethod)
	{
		overridingMethods.add(overridingMethod);
	}
	
	/**
	 * Get the name of the identifier for the Object reference that
	 * the method is using.
	 * 
	 * @return The name of the identifier for the Object reference that
	 * 		the method is using.
	 */
	public static String getObjectReferenceIdentifier()
	{
		return ParameterListNode.OBJECT_REFERENCE_IDENTIFIER;
	}
	
	/**
	 * Set the types that the specified method will return.
	 * 
	 * @param types The types that the Method returns.
	 */
	public void setTypes(String types[])
	{
		this.types = types;
	}
	
	/**
	 * Validate the parameters of the method header.
	 */
	public void validate()
	{
		MethodNode method = getOverriddenMethod();
		
		if (method != null)
		{
			method.addOverridingMethod(this);
		}
		
		getParameterListNode().validate();
	}
	
	/**
	 * Generate a MethodNode with the given parent and location for
	 * temporary use.
	 * 
	 * @param parent The node to set as the MethodNode's parent.
	 * @param locationIn The location to set as the MethodNode's location.
	 * @return The generated temporary MethodNode.
	 */
	public static MethodNode generateTemporaryMethod(TreeNode parent, Location locationIn)
	{
		MethodNode method = new MethodNode(parent, locationIn);
		
		return method;
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
			builder.append(getStaticText()).append(' ');
		}
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
		
		if (isArray())
		{
			builder.append(getArrayText());
		}
		
		builder.append(' ').append(getName()).append('(');

		ParameterListNode parameterList = getParameterListNode();
		
		builder.append(parameterList.generateJavaSource());
		
		builder.append(')').append('\n').append('{').append('\n');
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != parameterList)
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
//			SyntaxError.outputNewError("Static methods are not supported in the C implementation yet", getLocationIn());
//			
//			return null;
//		}
		if (isConstant())
		{
			SyntaxMessage.error("Const methods are not supported in the C implementation yet", this);
//			builder.append(getConstantText()).append(' ');
		}
		
		/*builder.append("FUNC(");
		
		builder.append(getType());
		
		if (isArray())
		{
			builder.append(getArrayText());
		}
		if (!isPrimitiveType())
		{
			builder.append('*');
		}
		
		builder.append(", ");
		
		builder.append(getName()).append(", ");

		ParameterListNode parameterList = getParameterListNode();
		
		builder.append(parameterList.generateCHeaderOutput());
		
		builder.append(");").append('\n');*/
		
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
		
		builder.append(getScopeNode().generateCSource());
//		builder.append('{').append('\n');
		
//		ParameterListNode parameterList = getParameterListNode();
//		
//		for (int i = 0; i < getNumChildren(); i++)
//		{
//			TreeNode child = getChild(i);
//			
//			if (child != parameterList)
//			{
//				builder.append(child.generateCSourceOutput());
//			}
//		}
//		
//		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * Generate the C prototype for the method header.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void test()
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * will output as "<code>static void test();</code>"<br>
	 * <br>
	 * In essence, this method is just {@link #generateCSourceSignature()}
	 * with a semi-colon attached to the end.
	 * 
	 * @return The C prototype for the method header.
	 */
	public String generateCSourcePrototype()
	{
		return generateCSourceSignature() + ";";
	}
	
	/**
	 * Generate the method signature that will appear in the c source
	 * output.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void test()
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * will output as "<code>static void test()</code>"
	 * 
	 * @return The method signature in the C language.
	 */
	public String generateCSourceSignature()
	{
		StringBuilder builder = new StringBuilder();
		
//		builder.append("static ");
		
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
		else if (isArray())
		{
			builder.append(getArrayText());
		}
		if (!isPrimitiveType() && !isExternalType())
		{
			builder.append('*');
		}
		
		builder.append(' ');
		
		builder.append(generateMethodName()).append('(');
		
		builder.append(getParameterListNode().generateCSource());
		
		builder.append(')');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ValueNode#getAccessedNode()
	 */
	public IdentifierNode getAccessedNode()
	{
		if (getNumChildren() <= 2)
		{
			return null;
		}
		
		return (IdentifierNode)getChild(2);
	}
	
	/**
	 * Generate the C Source output for a method name.
	 * 
	 * @return The C output for a method name.
	 */
	public String generateMethodName()
	{
		return generateCSourceName();
	}
	
	/**
	 * Get the name of the method that is output to the C source file.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * package "this/is/my/package";
	 * 
	 * public class Test
	 * {
	 * 	public void methodName()
	 * 	{
	 * 		...
	 * 	}
	 * }</pre></blockquote>
	 * The output for "<code>methodName()</code>" would look like:
	 * "<code>this_is_my_package_Test_methodName()</code>"
	 * 
	 * @return The name of the method that is output to the C source file.
	 */
	public String generateCSourceName()
	{
		if (isExternal())
		{
			return getName();
		}
		
		ClassNode clazz = (ClassNode)getAncestorOfType(ClassNode.class);
		
		return Nova.LANGUAGE_NAME.toLowerCase() + "_" + clazz.generateUniquePrefix() + "_" + getName();
	}
	
	/**
	 * Decode the given statement into a MethodNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public Person findPerson(String name, int age)</li>
	 * 	<li>private int calculateArea(int width, int height)</li>
	 * 	<li>public void doNothing()</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		MethodNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a MethodNode.
	 */
	public static MethodNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean scope)
	{
		int firstParenthIndex = Regex.indexOf(statement, '(', new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
		
		if (firstParenthIndex >= 0)
		{
			int lastParenthIndex   = StringUtils.findEndingMatch(statement, firstParenthIndex, '(', ')');//Regex.lastIndexOf(statement, ')', new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
			
			final String signature = statement.substring(0, firstParenthIndex);
			String returnStatement = statement.substring(lastParenthIndex + 1);
			
			returnStatement        = StringUtils.trimSurroundingWhitespace(returnStatement);
			
			final Location finalLocation  = location;
			
			final boolean  returnCycle[]  = new boolean[1];
			
			final ArrayList<String> types = new ArrayList<String>();
			
			MethodNode n = new MethodNode(parent, location)
			{
				public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter)
				{
//					if (!returnCycle[0])
//					{
//						if (word.equals("returns"))
//						{
//							returnCycle[0] = true;
//							
//							return;
//						}
//					}
					
					if (!returnCycle[0])
					{
						if (wordNumber == numWords - 1)
						{
							setName(word);
						}
						else if (wordNumber == numWords - 2)
						{
							setType(word);
							
							// If it is an array declaration.
							if (Regex.matches(signature, bounds.getEnd(), Patterns.EMPTY_ARRAY_BRACKETS))
							{
								int dimensions = SyntaxUtils.calculateArrayDimensions(signature, false);
								
								setArrayDimensions(dimensions);
							}
						}
						else if (!setAttribute(word, wordNumber))
						{
							Location newLoc = new Location(finalLocation);
							newLoc.setBounds(bounds.getStart(), bounds.getEnd());
							
							SyntaxMessage.error("Unknown method definition", this, newLoc);
						}
					}
//					else
//					{
//						if (!leftDelimiter.equals(",") && wordNumber > 2 || !StringUtils.containsOnly(word, StringUtils.WHITESPACE) && wordNumber == 1)
//						{
//							SyntaxMessage.error("Unknown delimiter '" + leftDelimiter + "'", this);
//						}
//						
//						types.add(word);
//					}
				}
			};
			
			// TODO: make better check for last parenth. Take a count of each of the starting parenthesis and
			// subtract the ending ones from the number.
			if (lastParenthIndex < 0)
			{
				SyntaxMessage.error("Expected a ')' ending parenthesis", n);
			}
			
			String parameterList = statement.substring(firstParenthIndex + 1, lastParenthIndex);
			
			String parameters[]  = StringUtils.splitCommas(parameterList);
			
			try
			{
				n.iterateWords(signature, Patterns.IDENTIFIER_BOUNDARIES);
				n.iterateWords(returnStatement, Patterns.IDENTIFIER_BOUNDARIES);
			}
			catch (SyntaxErrorException e)
			{
				if (!require)
				{
					return null;
				}
				
				throw e;
			}
			
			if (n.isExternal() && scope)
			{
				SyntaxMessage.error("External method declarations cannot have a body", n);
			}
			
			if (types.size() <= 0)
			{
//				if (!require)
//				{
//					return null;
//				}
//				
//				SyntaxMessage.error("The method must have a return type", n);
			}
			
//			n.setTypes(types.toArray(new String[0]));
			
			for (int i = 0; i < parameters.length; i++)
			{
				if (parameters[i].length() > 0)
				{
					ParameterNode param = ParameterNode.decodeStatement(n, parameters[i], location, require, false);
					
					if (param == null)
					{
						SyntaxMessage.error("Incorrect parameter definition", n);
					}
					
					n.getParameterListNode().addChild(param);
				}
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public MethodNode clone(TreeNode temporaryParent, Location locationIn)
	{
		MethodNode node = new MethodNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given MethodNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodNode cloneTo(MethodNode node)
	{
		super.cloneTo(node);
		
		for (MethodNode child : overridingMethods)
		{
			node.overridingMethods.add(child.clone(null, child.getLocationIn()));
		}
		
		node.types = new String[types.length];
		
		for (int i = 0; i < types.length; i++)
		{
			String type = types[i];
			
			node.types[i] = type;
		}
		
		return node;
	}
}
