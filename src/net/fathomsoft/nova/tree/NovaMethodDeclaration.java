package net.fathomsoft.nova.tree;

import java.util.ArrayList;
import java.util.regex.Pattern;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.MethodList.SearchFilter;
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
 * @since	v0.2.21 Jul 30, 2014 at 1:45:00 PM
 * @version	v0.2.30 Sep 2, 2014 at 7:58:20 PM
 */
public class NovaMethodDeclaration extends MethodDeclaration implements ScopeAncestor
{
	private int	uniqueID, overloadID;
	
	private ArrayList<NovaMethodDeclaration>	overridingMethods;
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclaration#InstanceDeclaration(Node, Location)
	 */
	public NovaMethodDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		uniqueID          = 0;
		overloadID        = -1;
		overridingMethods = new ArrayList<NovaMethodDeclaration>();
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
	 * @see net.fathomsoft.nova.tree.ParameterList#getParameter(int)
	 */
	public Parameter getParameter(int parameterIndex)
	{
		return (Parameter)getParameterList().getParameter(parameterIndex);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ParameterList#getParameter(String)
	 * 
	 * @param parameterName The name of the parameter to find.
	 * @return The Parameter with the given name.
	 */
	public Parameter getParameter(String parameterName)
	{
		return (Parameter)getParameterList().getParameter(parameterName);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ScopeAncestor#generateUniqueID()
	 */
	@Override
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
	 * Get whether or not the method overrides another method or is
	 * overridden by another method.
	 * 
	 * @return Whether or not the method is virtual.
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
	public NovaMethodDeclaration getOverriddenMethod()
	{
		ClassDeclaration extension = getParentClass().getExtendedClass();
		
		if (extension == null)
		{
			return null;
		}
		
		SearchFilter filter = new SearchFilter();
		filter.checkStatic(isStatic());
		
		return (NovaMethodDeclaration)extension.getMethod(getName(), getParameterList().getTypes());
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
	public NovaMethodDeclaration[] getOverridingMethod()
	{
		return overridingMethods.toArray(new NovaMethodDeclaration[0]);
	}
	
	/**
	 * Get whether or not a Method instance overrides the given
	 * method.
	 * 
	 * @param overridingMethod The Method to check.
	 */
	private boolean containsOverridingMethod(NovaMethodDeclaration overridingMethod)
	{
		for (int i = 0; i < overridingMethods.size(); i++)
		{
			NovaMethodDeclaration methodDeclaration = overridingMethods.get(i);
			
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
	private void addOverridingMethod(NovaMethodDeclaration overridingMethod)
	{
		this.overridingMethods.add(overridingMethod);
	}
	
	/**
	 * Set the overload IDs for all of the given methods.
	 * 
	 * @param methods The methods to generate overload IDs for.
	 */
	private void setOverloadIDs(MethodDeclaration methods[])
	{
		ArrayList<NovaMethodDeclaration> list = new ArrayList<NovaMethodDeclaration>();
		
		int max = -1;
		
		for (MethodDeclaration m : methods)
		{
			if (m == this)
			{
				continue;
			}
			
			if (!m.isExternal())
			{
				NovaMethodDeclaration method = (NovaMethodDeclaration)m;
				
				if (method.overloadID < 0)
				{
					if (method.getParentClass() == getParentClass())
					{
						if (SyntaxUtils.areSameTypes(getParameterList().getTypes(), method.getParameterList().getTypes()))
						{
							SyntaxMessage.error("Duplicate method '" + getName() + "'", this);
						}
						
						list.add(method);
					}
					else if (method.isVirtual())
					{
						list.add(method);
					}
				}
				else if (max < method.overloadID)
				{
					max = method.overloadID;
				}
			}
		}
		
		if (getOverriddenMethod() != null)
		{
			overloadID = getOverriddenMethod().overloadID;
		}
		else if (list.size() > 0)
		{
			overloadID = ++max;
			
		}
		
		max = overloadID;
		
		for (NovaMethodDeclaration method : list)
		{
			method.overloadID = ++max;
		}
	}
	
	/**
	 * Get the identifier for the virtual abstract method in the vtable.
	 * 
	 * @return The identifier for the virtual method in the vtable.
	 */
	public StringBuilder generateCVirtualMethodName()
	{
		return generateCVirtualMethodName(new StringBuilder());
	}
	
	/**
	 * Get the identifier for the virtual abstract method in the vtable.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The identifier for the virtual method in the vtable.
	 */
	public StringBuilder generateCVirtualMethodName(StringBuilder builder)
	{
		builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append("_virtual_");
		
		if (overloadID >= 0)
		{
			builder.append(overloadID).append('_');
		}
		
		return builder.append(getName());
	}
	
	/**
	 * Generate the identifier that will be used to call the method.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The updated StringBuilder.
	 */
	public StringBuilder generateCMethodCall(StringBuilder builder)
	{
		if (isVirtual())
		{
			return generateCVirtualMethodName(builder);
		}
		
		return super.generateCMethodCall(builder);
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
	public static NovaMethodDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (validateMethodDeclaration(statement))
		{
			NovaMethodDeclaration n = new NovaMethodDeclaration(parent, location);
			
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
	 * Find the String representing the signature of the bodyless
	 * method that is currently being decoded from the given
	 * statement String.
	 * 
	 * @param statement The String containing the method signature.
	 * @return The signature for the bodyless method to decode.
	 */
	public static String findMethodSignature(String statement)
	{
		int paren = statement.indexOf('(');
		
		if (paren < 0)
		{
			return null;
		}
		
		int end = StringUtils.findNextNonWhitespaceIndex(statement, paren - 1, -1) + 1;
		int ret = StringUtils.findEndingMatch(statement, end, '(', ')');
		
		return statement.substring(0, end) + statement.substring(ret + 1);
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
		String parameterList = bounds.extractString(statement);
		
		return decodeParameters(parameterList, require);
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
	public boolean decodeSignature(String statement, boolean require)
	{
		String signature = findMethodSignature(statement);
		MethodData data  = (MethodData)iterateWords(signature, Patterns.IDENTIFIER_BOUNDARIES);
		
		if (data.error != null)
		{
			return SyntaxMessage.queryError(data.error, this, require);
		}
		
		return true;
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
	 * @see net.fathomsoft.nova.tree.Node#iterateWords(java.lang.String, java.util.regex.Pattern, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public ExtraData iterateWords(String statement, Pattern pattern, ExtraData extra)
	{
		MethodData data = new MethodData(statement);
		
		return (MethodData)super.iterateWords(statement, pattern, data);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, net.fathomsoft.nova.util.Bounds, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		MethodData data = (MethodData)extra;
		
		if (data.error != null || !setAttribute(word, extra.getWordNumber()))
		{
			if (leftDelimiter.equals("->"))
			{
				setType(word, true, false);
				
				checkArray(data.signature, bounds.getEnd(), rightDelimiter);
			}
			else if (extra.isLastWord() || rightDelimiter.equals("->"))
			{
				setName(word);
			}
			else
			{
				data.error = "Unknown method definition";
				
				return;
			}
		}
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
		NovaMethodDeclaration methodDeclaration = getOverriddenMethod();
		
		if (methodDeclaration != null && methodDeclaration.isStatic() == isStatic())
		{
			if (!containsOverridingMethod(methodDeclaration))
			{
				methodDeclaration.overridingMethods.add(this);
			}
		}
		
		if (overloadID < 0)
		{
			SearchFilter filter = new SearchFilter();
			filter.checkConstructors = false;
			
			MethodDeclaration methods[] = getParentClass().getMethods(getName(), filter);
			
			if (methods.length > 1)
			{
				setOverloadIDs(methods);
			}
		}
		
		getParameterList().validate(phase);
		
		return super.validate(phase);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public NovaMethodDeclaration clone(Node temporaryParent, Location locationIn)
	{
		NovaMethodDeclaration node = new NovaMethodDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link NovaMethodDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public NovaMethodDeclaration cloneTo(NovaMethodDeclaration node)
	{
		super.cloneTo(node);
		
		for (NovaMethodDeclaration child : overridingMethods)
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
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}