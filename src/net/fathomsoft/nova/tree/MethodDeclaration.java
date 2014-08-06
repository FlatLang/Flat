package net.fathomsoft.nova.tree;

import java.util.regex.Pattern;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * InstanceDeclaration extension that represents the declaration of a method
 * node type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:10:53 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public abstract class MethodDeclaration extends InstanceDeclaration implements CallableMethod
{
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclaration#InstanceDeclaration(Node, Location)
	 */
	public MethodDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ParameterList<Value> parameterList = new ParameterList<Value>(this, locationIn.asNew());
		
		addChild(parameterList, this);
	}
	
	/**
	 * Get whether or not the specified MethodDeclaration contains a body
	 * or not. The default is false.
	 * 
	 * @return Whether or not the specified MethodDeclaration contains a
	 * 		body.
	 */
	public boolean containsBody()
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
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
	public ParameterList<Value> getParameterList()
	{
		return (ParameterList<Value>)getChild(super.getNumDefaultChildren());
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
	public Value getParameter(int parameterIndex)
	{
		return getParameterList().getParameter(parameterIndex);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		return generateCHeaderFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeaderFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeaderFragment(StringBuilder builder)
	{
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		return builder;
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
	 * @param builder The StringBuilder to append the data to.
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
	 * @param builder The StringBuilder to append the data to.
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
	
	/**
	 * Find the String representing the signature of the bodyless
	 * method that is currently being decoded from the given
	 * statement String.
	 * 
	 * @param statement The String containing the method signature.
	 * @param pattern The Pattern to remove from the statement.
	 * @return The signature for the bodyless method to decode.
	 */
	public static String findMethodSignature(String statement, Pattern pattern)
	{
		int paren = statement.indexOf('(');
		
		if (paren < 0)
		{
			return null;
		}
		
		String signature = NovaMethodDeclaration.findMethodSignature(statement);
		Bounds bounds    = Regex.boundsOf(signature, pattern);
		
		if (!bounds.isValid())
		{
			return null;
		}
		
		paren -= bounds.length();
		
		return statement.substring(0, bounds.getStart()) + statement.substring(bounds.getEnd(), statement.length());
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
	
	/**
	 * Check to see if the given types are compatible with the specified
	 * MethodDeclaration's parameters.
	 * 
	 * @param types The types that are being tested against the
	 * 		parameters.
	 * @return Whether or not the types are compatible with the
	 * 		parameters.
	 */
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
	 * Fill the given {@link MethodDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodDeclaration cloneTo(MethodDeclaration node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the MethodDeclaration class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}