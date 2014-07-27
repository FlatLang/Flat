package net.fathomsoft.nova.tree;

import java.util.ArrayList;
import java.util.regex.Pattern;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Declaration extension that represents the declaration of a method
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:10:53 PM
 * @version	v0.2.19 Jul 26, 2014 at 12:30:24 AM
 */
public class MethodDeclaration extends InstanceDeclaration implements CallableMethod
{
	private int		uniqueID, overloadID;
	
	private ArrayList<MethodDeclaration>	overridingMethods;
	
	/**
	 * Instantiate and initialize default data.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public MethodDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		uniqueID          = 0;
		overloadID        = -1;
		overridingMethods = new ArrayList<MethodDeclaration>();
		
		ParameterList<Parameter> parameterList = new ParameterList<Parameter>(this, new Location(locationIn));
		Scope         scope     = new Scope(this, new Location(locationIn));
		
		setScope(scope);
		addChild(parameterList, this);
	}
	
	/**
	 * Get whether or not the specified MethodDeclaration contains a Body.
	 * 
	 * @return Whether or not the specified MethodDeclaration contains a
	 * 		Body.
	 */
	public boolean containsBody()
	{
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 2;
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
	 * Get a unique integer used for differentiating methods that are
	 * overloaded.
	 * 
	 * @return A unique identifier for overloaded methods.
	 */
	public int getOverloadID()
	{
		return overloadID;
	}
	
	/**
	 * Set a unique integer used for differentiating methods that are
	 * overloaded.
	 * 
	 * @param id A unique identifier for overloaded methods.
	 */
	public void setOverloadID(int id)
	{
		this.overloadID = id;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getScope()
	 */
	@Override
	public Scope getScope()
	{
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
	@Override
	public ParameterList<Parameter> getParameterList()
	{
		return (ParameterList<Parameter>)getChild(1);
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
		return (Parameter)getParameterList().getParameter(parameterIndex);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.CallableMethod#isInstance()
	 */
	@Override
	public boolean isInstance()
	{
		return !isStatic();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableDeclaration#isExternal()
	 */
	@Override
	public boolean isExternal()
	{
		// If the MethodDeclaration is decoding for the
		// ExternalMethodDeclaration.
		if (getParent() instanceof ExternalMethodDeclaration)
		{
			return true;
		}
		
		return super.isExternal();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableDeclaration#setExternal(boolean)
	 */
	@Override
	public void setExternal(boolean external)
	{
		super.setExternal(external);
		
		setStatic(true);
		setVisibility(PUBLIC);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.CallableMethod#isVirtual()
	 */
	@Override
	public boolean isVirtual()
	{
		return isOverridden() || doesOverride();
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
	public MethodDeclaration getOverriddenMethod()
	{
		ClassDeclaration extension = getParentClass().getExtendedClass();
		
		if (extension == null)
		{
			return null;
		}
		
		return extension.getMethod(getName(), getParameterList().getTypes());
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
	public MethodDeclaration[] getOverridingMethods()
	{
		return overridingMethods.toArray(new MethodDeclaration[0]);
	}
	
	/**
	 * Get whether or not a Method instance overrides the given
	 * method.
	 * 
	 * @param overridingMethod The Method to check.
	 */
	private boolean containsOverridingMethod(MethodDeclaration overridingMethod)
	{
		for (int i = 0; i < overridingMethods.size(); i++)
		{
			MethodDeclaration methodDeclaration = overridingMethods.get(i);
			
			if (methodDeclaration == overridingMethod)
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
	private void addOverridingMethod(MethodDeclaration overridingMethod)
	{
		overridingMethods.add(overridingMethod);
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
		MethodDeclaration methodDeclaration = getOverriddenMethod();
		
		if (methodDeclaration != null)
		{
			// TODO: Why do I need to check this?
			if (!methodDeclaration.containsOverridingMethod(this))
			{
				methodDeclaration.addOverridingMethod(this);
			}
		}
		
		if (overloadID < 0)
		{
			MethodDeclaration methods[] = getParentClass().getMethods(getName());
			
			if (methods.length > 1)
			{
				setOverloadIDs(methods);
			}
		}
		
		getParameterList().validate(phase);
		
		return super.validate(phase);
	}
	
	private void setOverloadIDs(MethodDeclaration methods[])
	{
		ArrayList<MethodDeclaration> list = new ArrayList<MethodDeclaration>();
		
		int max = -1;
		
		for (MethodDeclaration method : methods)
		{
			if (method.overloadID < 0)
			{
				list.add(method);
			}
			else if (max > method.overloadID)
			{
				max = overloadID;
			}
		}
		
		overloadID = ++max;
		
		for (MethodDeclaration method : list)
		{
			method.overloadID = ++max;
		}
	}
	
	/**
	 * Generate a Method with the given parent and location for
	 * temporary use.
	 * 
	 * @param parent The node to set as the Method's parent.
	 * @param locationIn The location to set as the Method's location.
	 * @return The generated temporary Method.
	 */
	public static MethodDeclaration generateTemporaryMethod(Node parent, Location locationIn)
	{
		MethodDeclaration methodDeclaration = new MethodDeclaration(parent, locationIn);
		methodDeclaration.setType("void");
		
		return methodDeclaration;
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
	 * In essence, this method is just {@link #generateCSourceSignature(StringBuilder)}
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
		generateCModifiersSource(builder);
		
		builder.append(' ');
		
		generateCSourceName(builder).append('(');
		
		getParameterList().generateCSource(builder);
		
		builder.append(')');
		
		return builder;
	}
	
	public StringBuilder generateCVirtualMethodName()
	{
		return generateCVirtualMethodName(new StringBuilder());
	}
	
	public StringBuilder generateCVirtualMethodName(StringBuilder builder)
	{
		builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append("_virtual_");
		
		if (overloadID >= 0)
		{
			builder.append(overloadID).append('_');
		}
		
		return builder.append(getName());
	}
	
	public StringBuilder generateCMethodCall(Identifier reference)
	{
		return generateCMethodCall(new StringBuilder(), reference);
	}
	
	public StringBuilder generateCMethodCall(StringBuilder builder, Identifier reference)
	{
		if (isVirtual())
		{
//			reference.generate
			
			return generateCVirtualMethodName(builder);
		}
		
		return generateCSourceName(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCSourceName(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceName(StringBuilder builder)
	{
		if (overloadID == -1)
		{
			return super.generateCSourceName(builder);
		}
		
		return super.generateCSourceName(builder, overloadID + "");
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
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Method.
	 */
	public static MethodDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (validateMethodDeclaration(statement))
		{
			MethodDeclaration n = new MethodDeclaration(parent, location);
			
			// Bounds of the data within the parentheses.
			Bounds bounds = SyntaxUtils.findInnerParenthesesBounds(n, statement);
			
			if (n.decodeSignature(statement, require) && n.validateDeclaration(statement, bounds, require))
			{
				n.checkExternalType();
				
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Validate that the given statement is a method declaration.
	 * 
	 * @param statement The statement to validate.
	 * @return Whether or not the given statement is a valid method
	 * 		declaration.
	 */
	private static boolean validateMethodDeclaration(String statement)
	{
		int firstParenthIndex = statement.indexOf('(');
		
		return firstParenthIndex >= 0 && !Regex.startsWith(statement, Patterns.EXTERNAL);
	}
	
	/**
	 * Decode the method signature.<br>
	 * <br>
	 * For example: "<u><code>public static void main</code></u>"
	 * 
	 * @param statement The statement to decode the signature from.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the signature was successfully decoded.
	 */
	private boolean decodeSignature(String statement, boolean require)
	{
		int parenthesisIndex = statement.indexOf('(');
		
		int end = StringUtils.findNextNonWhitespaceIndex(statement, parenthesisIndex - 1, -1) + 1;
		
		String signature = statement.substring(0, end);
		
		MethodData data = (MethodData)iterateWords(signature, Patterns.IDENTIFIER_BOUNDARIES);
		
		if (data.error != null)
		{
			return SyntaxMessage.queryError(data.error, this, require);
		}
		
		return true;
	}
	
	/**
	 * Validate that the method declaration is correct. Make sure that a
	 * return type is specified, its parameters are declared correctly,
	 * and other issues.
	 * 
	 * @param statement The statement containing the method declaration.
	 * @param bounds The Bounds of the parameters.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the declaration is valid.
	 */
	private boolean validateDeclaration(String statement, Bounds bounds, boolean require)
	{
		if (getType() == null)
		{
			return false;
		}
		
		String parameterList = statement.substring(bounds.getStart(), bounds.getEnd());
		
		return decodeParameters(parameterList, require);
	}
	
	/**
	 * Check to see if the return type of the method is an external type.
	 */
	private void checkExternalType()
	{
		if (getParentClass().containsExternalType(getType()))
		{
			setDataType(IValue.POINTER);
		}
	}
	
	/**
	 * Decode the given parameters that were declared for the Method.
	 * 
	 * @param parameterList The statement that contains the parameters
	 * 		separated by commas.
	 * @param require Whether or not a successful decode is required.
	 * @return Whether or not the parameters were decoded correctly.
	 */
	public boolean decodeParameters(String parameterList, boolean require)
	{
		if (parameterList.length() <= 0)
		{
			return true;
		}
		
		String parameters[] = StringUtils.splitCommas(parameterList);
		
		Location location = new Location(getLocationIn());
		
		for (int i = 0; i < parameters.length; i++)
		{
			if (parameters[i].length() > 0)
			{
				Parameter param = Parameter.decodeStatement(this, parameters[i], location, require);
				
				if (param == null)
				{
					return SyntaxMessage.queryError("Incorrect parameter definition", this, require);
				}
				
				getParameterList().addChild(param);
			}
			else
			{
				SyntaxMessage.error("Expected a parameter definition", this);
			}
		}
		
		return true;
	}
	
	public boolean areCompatibleParameterTypes(Value ... types)
	{
		if (types.length != getParameterList().getNumVisibleChildren())
		{
			return false;
		}
		
		for (int i = 0; i < types.length; i++)
		{
			if (!SyntaxUtils.isTypeCompatible(getParameterList().getParameter(i), types[i]))
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#iterateWords(java.lang.String, java.util.regex.Pattern, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public ExtraData iterateWords(String statement, Pattern pattern, ExtraData extra)
	{
		MethodData data = new MethodData(statement);
		
		return (MethodData)super.iterateWords(statement, pattern, data);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, int, net.fathomsoft.nova.util.Bounds, int, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		MethodData data = (MethodData)extra;
		
		if (data.error != null || !setAttribute(word, wordNumber))
		{
			if (wordNumber == numWords - 1)
			{
				setName(word);
			}
			else if (wordNumber == numWords - 2)
			{
				setType(word, true, false);
				
				checkArray(data.signature, bounds.getEnd(), rightDelimiter);
			}
			else
			{
				data.error = "Unknown method definition";
				
				return;
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public MethodDeclaration clone(Node temporaryParent, Location locationIn)
	{
		MethodDeclaration node = new MethodDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link MethodDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodDeclaration cloneTo(MethodDeclaration node)
	{
		super.cloneTo(node);
		
		for (MethodDeclaration child : overridingMethods)
		{
			node.overridingMethods.add(child.clone(node, child.getLocationIn()));
		}
		
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
		private String	signature;
		
		public MethodData(String signature)
		{
			this.signature = signature;
		}
	}
	
	/**
	 * Test the MethodDeclaration class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test()
	{
		
		
		return null;
	}
}