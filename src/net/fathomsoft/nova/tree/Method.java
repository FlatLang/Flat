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
 * Declaration extension that represents the declaration of a method
 * node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:10:53 PM
 * @version	v0.2.14 Jun 18, 2014 at 10:11:40 PM
 */
public class Method extends InstanceDeclaration
{
//	private boolean					externalType;
	
	private String					types[];
	
	private ArrayList<Method>	overridingMethods;
	
	private int						uniqueID = 0;
	
	/**
	 * Instantiate and initialize default data.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Method(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		overridingMethods = new ArrayList<Method>();
		
		ParameterList parameterList = new ParameterList(this, null);
		Scope         scope     = new Scope(this, null);
		
		setScope(scope);
		addChild(parameterList, this);
	}
	
	/**
	 * Get a unique integer used for differentiating local variables
	 * within the method.
	 * 
	 * @return A unique identifier for local variables.
	 */
	public int generateUniqueID()
	{
		return ++uniqueID;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getScope()
	 */
	@Override
	public Scope getScope()
	{
		if (isExternal())
		{
			return null;
		}
		
		return (Scope)getChild(0);
	}
	
	/**
	 * The the ParameterList that represents the parameters of the
	 * method.<br>
	 * For example:<br>
	 * <blockquote><pre>
	 * public void methodName(int age, String name);</pre></blockquote>
	 * In the previous statement, the data within the parenthesis are the
	 * parameters of the method. The ParameterList returned by this
	 * method would contain a node for each of the parameter specified, in
	 * the correct order from left to right.
	 * 
	 * @return The ParameterList that represents the parameters of the
	 * 		method.
	 */
	public ParameterList getParameterList()
	{
		return (ParameterList)getChild(1);
	}
	
	/**
	 * Get the Parameter that the given index represents. The
	 * parameters are ordered from left to right, 0 being the first.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void run(int a, int b, int c)
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * If you were to call getParameter(2) on the method
	 * above, you would receive the c Parameter.
	 * 
	 * @param parameterIndex The index parameter to get.
	 * @return The Parameter at the given index.
	 */
	public Parameter getParameter(int parameterIndex)
	{
		return getParameterList().getParameter(parameterIndex);
	}
	
//	/**
//	 * Get whether or not the specified method returns an external type.
//	 * For more details on what an external type looks like, see
//	 * {@link net.fathomsoft.nova.tree.variables.Variable#setExternal(boolean)}
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
//	 * {@link net.fathomsoft.nova.tree.variables.Variable#setExternal(boolean)}
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
		return this instanceof Constructor == false && !isStatic();
	}

	/**
	 * @see net.fathomsoft.nova.tree.variables.Variable#setExternal(boolean)
	 */
	@Override
	public void setExternal(boolean external)
	{
		super.setExternal(external);
		
		setStatic(true);
		setVisibility(PUBLIC);
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#addChild(Node)
	 */
	@Override
	public void addChild(Node child)
	{
//		if (child instanceof LocalVariable)
//		{
//			getLocalVariableList().addChild(child);
//		}
//		else
//		{
//			super.addChild(child);
//		}
		getScope().addChild(child);
	}
	
	/**
	 * Get whether or not the specified Method has overridden a method
	 * from a super class
	 * 
	 * @return Whether or not the specified Method has overridden a
	 * 		method from a super class.
	 */
	public boolean doesOverride()
	{
		return getOverriddenMethod() != null;
	}
	
	/**
	 * Get the Method instance that this Method overrides, if one
	 * exists.
	 * 
	 * @return The Method instance that this Method overrides, if
	 * 		one exists.
	 */
	public Method getOverriddenMethod()
	{
		ClassDeclaration clazz     = getClassDeclaration();
		
		ClassDeclaration extension = clazz.getExtendedClass();
		
		if (extension != null)
		{
			Method method = extension.getMethod(getName());
			
			return method;
		}
		
		return null;
	}
	
	/**
	 * Get whether or not the specified Method has been overridden by
	 * a sub class.
	 * 
	 * @return Whether or not the specified Method has been
	 * 		overridden.
	 */
	public boolean isOverridden()
	{
		return overridingMethods.size() > 0;
	}
	
	/**
	 * Get the Method instance that overrides this Method, if
	 * any exists.
	 * 
	 * @return The Method instance that overrides this Method, if
	 * 		any exists.
	 */
	public Method[] getOverridingMethods()
	{
		return overridingMethods.toArray(new Method[0]);
	}
	
	/**
	 * Get whether or not a Method instance overrides the given
	 * method.
	 * 
	 * @param overridingMethod The Method to check.
	 */
	private boolean containsOverridingMethod(Method overridingMethod)
	{
		for (int i = 0; i < overridingMethods.size(); i++)
		{
			Method method = overridingMethods.get(i);
			
			if (method.getName().equals(overridingMethod.getName()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Add a Method instance that overrides this Method.
	 * 
	 * @param overridingMethod The Method instance that overrides
	 * 		this Method.
	 */
	private void addOverridingMethod(Method overridingMethod)
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
		return ParameterList.OBJECT_REFERENCE_IDENTIFIER;
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
	 * 
	 * @param phase The phase that the node is being validated in.
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public Node validate(int phase)
	{
		Method method = getOverriddenMethod();
		
		if (method != null)
		{
			if (!method.containsOverridingMethod(this))
			{
				method.addOverridingMethod(this);
			}
		}
		
		getParameterList().validate(phase);
		
		return this;
	}
	
	/**
	 * Generate a Method with the given parent and location for
	 * temporary use.
	 * 
	 * @param parent The node to set as the Method's parent.
	 * @param locationIn The location to set as the Method's location.
	 * @return The generated temporary Method.
	 */
	public static Method generateTemporaryMethod(Node parent, Location locationIn)
	{
		Method method = new Method(parent, locationIn);
		
		return method;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		if (isVisibilityValid())
		{
			if (getVisibility() == InstanceDeclaration.PRIVATE)
			{
				return builder;
			}
		}
		if (isConstant())
		{
			SyntaxMessage.error("Const methods are not supported in the C implementation yet", this);
		}
		
		generateCSourcePrototype(builder).append('\n');
		
		return builder;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		generateCSourceSignature(builder).append('\n');
		
		return getScope().generateCSource(builder);
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
	public StringBuilder generateCSourcePrototype(StringBuilder builder)
	{
		return generateCSourceSignature(builder).append(";");
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
	public StringBuilder generateCSourceSignature(StringBuilder builder)
	{
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		generateCTypeOutput(builder);
		
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
		
		getParameterList().generateCSource(builder);
		
		builder.append(')');
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#getAccessedNode()
	 */
	public Identifier getAccessedNode()
	{
		if (getNumChildren() <= 2)
		{
			return null;
		}
		
		return (Identifier)getChild(2);
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
		
		ClassDeclaration clazz = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class);
		
		return Nova.LANGUAGE_NAME.toLowerCase() + "_" + clazz.generateUniquePrefix() + "_" + getName();
	}
	
	/**
	 * Decode the given statement into a Method instance, if
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
	 * 		Method instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Method.
	 */
	public static Method decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		int firstParenthIndex = Regex.indexOf(statement, '(', new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
		
		if (firstParenthIndex >= 0)
		{
			int lastParenthIndex   = StringUtils.findEndingMatch(statement, firstParenthIndex, '(', ')');//Regex.lastIndexOf(statement, ')', new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
			
			String signature       = statement.substring(0, firstParenthIndex);
			String returnStatement = statement.substring(lastParenthIndex + 1);
			
			returnStatement        = StringUtils.trimSurroundingWhitespace(returnStatement);
			
			Method n = new Method(parent, location);
			
			// TODO: make better check for last parenth. Take a count of each of the starting parenthesis and
			// subtract the ending ones from the number.
			if (lastParenthIndex < 0)
			{
				SyntaxMessage.error("Expected a ')' ending parenthesis", n);
			}
			
			String parameterList = statement.substring(firstParenthIndex + 1, lastParenthIndex);
			
			String parameters[]  = StringUtils.splitCommas(parameterList);
			
			MethodData data = new MethodData(signature, location);
			
			try
			{
				n.iterateWords(signature, Patterns.IDENTIFIER_BOUNDARIES, data);
				n.iterateWords(returnStatement, Patterns.IDENTIFIER_BOUNDARIES, data);
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
			
			if (data.types.size() <= 0)
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
					Parameter param = Parameter.decodeStatement(n, parameters[i], location, require, false);
					
					if (param == null)
					{
						SyntaxMessage.error("Incorrect parameter definition", n);
					}
					
					n.getParameterList().addChild(param);
				}
			}
			
			ClassDeclaration clazz = n.getClassDeclaration();
			
			if (clazz.containsExternalType(n.getType()))
			{
				n.setDataType(Value.POINTER);
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, int, net.fathomsoft.nova.util.Bounds, int, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		MethodData data = (MethodData)extra;
		
//		if (!returnCycle[0])
//		{
//			if (word.equals("returns"))
//			{
//				returnCycle[0] = true;
//				
//				return;
//			}
//		}
		
		if (!data.returnCycle)
		{
			if (wordNumber == numWords - 1)
			{
				setName(word);
			}
			else if (wordNumber == numWords - 2)
			{
				setType(word);
				
				// If it is an array declaration.
				if (Regex.matches(data.signature, bounds.getEnd(), Patterns.EMPTY_ARRAY_BRACKETS))
				{
					int dimensions = SyntaxUtils.calculateArrayDimensions(data.signature, false);
					
					setArrayDimensions(dimensions);
				}
			}
			else if (!setAttribute(word, wordNumber))
			{
				Location newLoc = new Location(data.location);
				newLoc.setBounds(bounds.getStart(), bounds.getEnd());
				
				SyntaxMessage.error("Unknown method definition", this, newLoc);
			}
		}
//		else
//		{
//			if (!leftDelimiter.equals(",") && wordNumber > 2 || !StringUtils.containsOnly(word, StringUtils.WHITESPACE) && wordNumber == 1)
//			{
//				SyntaxMessage.error("Unknown delimiter '" + leftDelimiter + "'", this);
//			}
//			
//			types.add(word);
//		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Method clone(Node temporaryParent, Location locationIn)
	{
		Method node = new Method(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Method with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Method cloneTo(Method node)
	{
		super.cloneTo(node);
		
		for (Method child : overridingMethods)
		{
			node.overridingMethods.add(child.clone(node, child.getLocationIn()));
		}
		
//		node.types = new String[types.length];
//		
//		for (int i = 0; i < types.length; i++)
//		{
//			String type = types[i];
//			
//			node.types[i] = type;
//		}
		
		return node;
	}
	
	/**
	 * Implementation of the ExtraData for this class.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.13 Jun 11, 2014 at 8:31:46 PM
	 * @version	v0.2.13 Jun 11, 2014 at 8:31:46 PM
	 */
	private static class MethodData extends ExtraData
	{
		private boolean				returnCycle;
		
		private Location			location;
		
		private String				signature;
		
		private ArrayList<String>	types;
		
		public MethodData(String signature, Location location)
		{
			this.signature = signature;
			this.location  = location;
			
			types = new ArrayList<String>();
		}
	}
}