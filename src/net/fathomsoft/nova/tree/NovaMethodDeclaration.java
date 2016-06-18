package net.fathomsoft.nova.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.error.UnimplementedOperationException;
import net.fathomsoft.nova.tree.MethodList.SearchFilter;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.tree.generics.GenericTypeParameterDeclaration;
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
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class NovaMethodDeclaration extends MethodDeclaration implements ScopeAncestor
{
	private int	uniqueID, overloadID;
	
	private String[] types;
	
	private ArrayList<NovaMethodDeclaration>	overridingMethods;
	
	private static HashMap<Integer, Scope> scopes = new HashMap<>(); 
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclaration#InstanceDeclaration(Node, Location)
	 */
	public NovaMethodDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		uniqueID          = 0;
		overloadID        = -1;
		types             = new String[0];
		overridingMethods = new ArrayList<NovaMethodDeclaration>();
		
		NovaParameterList parameters = new NovaParameterList(this, locationIn.asNew());
		
		replace(super.getParameterList(), parameters);
		
		GenericTypeParameterDeclaration methodParams = new GenericTypeParameterDeclaration(this, locationIn.asNew());
		addChild(methodParams, this);
	}
	
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	public GenericTypeParameterDeclaration getMethodGenericTypeParameterDeclaration()
	{
		if (getNumChildren() > super.getNumDefaultChildren())// && super.getChild(super.getNumDefaultChildren() + 0) instanceof GenericTypeParameterDeclaration)
		{
			return (GenericTypeParameterDeclaration)super.getChild(super.getNumDefaultChildren() + 0);
		}
		
		return null;
	}
	
	public boolean containsGenericTypeParameter(String name)
	{
		return getGenericTypeParameter(name) != null;
	}
	
	public GenericTypeParameter getGenericTypeParameter(String name)
	{
		GenericTypeParameterDeclaration decl = getMethodGenericTypeParameterDeclaration();
		
		if (decl != null)
		{
			return decl.getParameter(name);
		}
		
		return null;
	}
	
	public GenericCompatible getContext()
	{
		if (isInstance())
		{
			return getParentClass();
		}
		
		return null;
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
	public int generateUniqueID(Scope scope)
	{
		int id = ++uniqueID;
		
		scopes.put(id, scope);
		
		return id;
	}
	
	public Scope getScope(int id)
	{
		return scopes.get(id);
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
		Nova.debuggingBreakpoint(getFileDeclaration().getName().equals("Exception"));
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
	
	public NovaMethodDeclaration getRootDeclaration()
	{
		if (doesOverride())
		{
			return getOverriddenMethod().getRootDeclaration();
		}
		
		return this;
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
		SearchFilter filter = new SearchFilter();
		filter.checkStatic(isStatic());
		filter.checkProperties = true;
		
		return getOverriddenMethod(filter);
	}
	
	public NovaMethodDeclaration getOverriddenMethod(SearchFilter filter)
	{
		ClassDeclaration extension = getParentClass().getExtendedClass();
		
		if (extension != null)
		{
			NovaMethodDeclaration method = (NovaMethodDeclaration)extension.getMethod(getContext(), getName(), filter, getParameterList().getTypes());
			
			if (method != null)
			{
				return method;
			}
		}
		
		for (Interface inter : getParentClass().getImplementedClasses())
		{
			NovaMethodDeclaration method = (NovaMethodDeclaration)inter.getMethod(getContext(), getName(), filter, getParameterList().getTypes());
			
			if (method != null)
			{
				return method;
			}
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
	public NovaMethodDeclaration[] getOverridingMethods()
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
						
//						list.add(method);
					}
//					else if (method.isVirtual())
//					{
						list.add(method);
//					}
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
	
	public boolean isOverloaded()
	{
		return overloadID != -1;
	}
	
	@Override
	public StringBuilder generateCSourceNativeName(StringBuilder builder, boolean declaration)
	{
		super.generateCSourceNativeName(builder, declaration);
		
		if (!declaration && isOverloaded())
		{
			for (Parameter param : getParameterList())
			{
				builder.append('_');
				
				String location = null;
				
				if (param.isExternalType())
				{
					location = param.getType();
				}
				else
				{
					ClassDeclaration clazz = param.getTypeClass();
					
					if (clazz != null)
					{
						location = clazz.getFileDeclaration().getPackage().getLocation().replace('/', '_');
						
						if (location.length() > 0)
						{
							location += '_';
						}
						
						location += clazz.getName();
					}
					else
					{
						location = "void";
					}
				}
				
				builder.append('_');
				
				if (param.isArray())
				{
					builder.append("Array" + param.getArrayDimensions() + "d_");
				}
				
				builder.append(location);
			}
		}
		
		return builder;
	}
	
	@Override
	public NovaParameterList getParameterList()
	{
		return (NovaParameterList)super.getParameterList();
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
		return generateCSourceName(builder, "virtual");
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
	 * @see net.fathomsoft.nova.tree.Identifier#generateCSourceName(java.lang.StringBuilder, String)
	 */
	@Override
	public StringBuilder generateCSourceName(StringBuilder builder, String uniquePrefix)
	{
		return generateCSourceName(builder, uniquePrefix, true);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCSourceName(java.lang.StringBuilder, String)
	 */
	public StringBuilder generateCSourceName(StringBuilder builder, String uniquePrefix, boolean outputOverload)
	{
		if (overloadID == -1)
		{
			return super.generateCSourceName(builder, uniquePrefix);
		}
		
		if (uniquePrefix == null)
		{
			uniquePrefix = "";
		}
		if (outputOverload)
		{
			uniquePrefix += overloadID;
		}
		
		return super.generateCSourceName(builder, uniquePrefix);
	}

	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return generateNovaInput(builder, outputChildren, true);
	}
	
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren, boolean outputType)
	{
		builder.append(getVisibilityText());
		
		if (!isInstance())
		{
			builder.append(' ').append(getStaticText());
		}
		
		builder.append(' ').append(getName());
		
		builder.append('(');
		getParameterList().generateNovaInput(builder, true);
		builder.append(')');
		
		if (outputType && getType() != null)
		{
			builder.append(" -> ").append(getType());
		}
		
		builder.append('\n');
		
		if (outputChildren)
		{
			getScope().generateNovaInput(builder, true);
		}
		
		return builder;
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
				
				if (parent.getParentClass(true) instanceof Interface)
				{
					if (n.getVisibility() != PRIVATE)
					{
						SyntaxMessage.error("Interface functions cannot have visibility modifiers", n);
					}
					
					n.setVisibility(PUBLIC);
				}
				
				return n;
			}
		}
		
		return null;
	}
	
	private void checkOverrides()
	{
		NovaMethodDeclaration methodDeclaration = getOverriddenMethod();
		
		if (methodDeclaration != null)
		{
			if (!containsOverridingMethod(methodDeclaration))
			{
				methodDeclaration.overridingMethods.add(this);
			}
		}
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
		int endBound = StringUtils.findNextCharacter(statement, StringUtils.SYMBOLS_CHARS, firstParenthIndex - 1, -1);
		int startBound = 0;
		//Bounds bounds = StringUtils.findNextWordBounds(statement, firstParenthIndex, -1);
		
		if (endBound >= 0)
		{
			if (statement.charAt(endBound) != GENERIC_END_CHAR)
			{
				return false;
			}
			
			int stack = 1;
			
			for (startBound = endBound - 1; startBound >= 0; startBound--)
			{
				if (statement.charAt(startBound) == GENERIC_START_CHAR)
				{
					stack--;
				}
				else if (statement.charAt(startBound) == GENERIC_END_CHAR)
				{
					stack++;
				}
				
				if (stack == 0)
				{
					break;
				}
			}
			
			if (stack != 0)
			{
				return false;
			}
		}
		else
		{
			endBound = 0;
		}
		
		if (StringUtils.findNextCharacter(statement.substring(0, startBound) + statement.substring(endBound), StringUtils.SYMBOLS_CHARS, 0, 1) < firstParenthIndex - (endBound - startBound) - 1)
		{
			return false;
		}
		
		return firstParenthIndex >= 0 && !StringUtils.findNextWord(statement).equals(ExternalMethodDeclaration.PREFIX);
	}
	
	public boolean isUserMade()
	{
		return true;
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
	 * For example: "<u><code>public static calculateArea -> Int</code></u>"
	 * 
	 * @param statement The statement to decode the signature from.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the signature was successfully decoded.
	 */
	public boolean decodeSignature(String statement, boolean require)
	{
		String signature = findMethodSignature(statement);
		MethodData data  = new MethodData(signature);
		
		GenericTypeParameterDeclaration.searchGenerics(signature, data);
		
		iterateWords(signature, Patterns.IDENTIFIER_BOUNDARIES, data, require);
		
		if (data.error != null)
		{
			return SyntaxMessage.queryError(data.error, this, require);
		}
		
		int returnIndex = signature.indexOf("->");
		
		while (data.getGenericsRemaining() > 0)
		{
			Bounds bounds = data.getSkipBounds(data.getGenericsRemaining() - 1);

			String arg = bounds.extractString(signature);
			
			arg = arg.substring(GENERIC_START.length(), arg.length() - GENERIC_END.length());
			
			if (bounds.getStart() > returnIndex)
			{
				Arrays.stream(StringUtils.splitCommas(arg)).forEach(x -> addGenericTypeArgumentName(x));
			}
			else
			{
				getMethodGenericTypeParameterDeclaration().decodeGenericTypeParameters(arg);
			}
			
			data.decrementGenericsRemaining();
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
		if (parameterList.length() > 0)
		{
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
		}
		
		for (String type : types)
		{
			getParameterList().addReturnParameter(type);
		}
		
		return true;
	}
	
	public int getNumReturnValues()
	{
		if (getType() == null)
		{
			return 0;
		}
		
		return 1 + getParameterList().getNumReturnParameters();
	}
	
	public Value[] getTypes()
	{
		Value[] types = new Value[getNumReturnValues()];
		
		types[0] = getTypeClass();
		
		Value[] ret = getParameterList().getReturnTypes();
		
		for (int i = 0; i < ret.length; i++)
		{
			types[i + 1] = ret[i];
		}
		
		return types;
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
			if (leftDelimiter.equals("->") || (getType() != null && leftDelimiter.equals(",")))
			{
				addType(word);
				
				if (rightDelimiter.startsWith(GENERIC_START) && rightDelimiter.endsWith(GENERIC_END))
				{
					addGenericTypeArgumentName(rightDelimiter.substring(GENERIC_START.length(), rightDelimiter.length() - GENERIC_END.length()));
				}
				
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
	
	public void addType(String type)
	{
		if (getType() == null)
		{
			setType(type, true, false);
		}
		else
		{
			String[] temp = new String[types.length + 1];
			System.arraycopy(types, 0, temp, 0, types.length);
			
			temp[types.length] = type;
			
			types = temp;
		}
	}
	
	public String generateNovaClosureReference(Identifier context)
	{
		String reference = "";
		
		reference += /*context.getName() + "." +*/ getName();
		
		return reference;
	}
	
	public String generateNovaSignature()
	{
		String signature = "";
		
		if (getVisibility() != 0)
		{
			signature += getVisibilityText() + " ";
		}
		if (isStatic())
		{
			signature += getStaticText() + " ";
		}
		
		signature += getName() + getMethodGenericTypeParameterDeclaration().generateNovaInput();
		signature += '(' + getParameterList().generateNovaInput().toString() + ')';
		
		if (getType() != null)
		{
			signature += " -> " + getType();
		}
		
		signature += getGenericTypeArgumentList().generateNovaInput();
		
		return signature;
	}
	
	/**
	 * Validate the parameters of the method header.
	 * 
	 * @param phase The phase that the node is being validated in.
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			//TODO: Is this necessary?
			getParameterList().validate(phase);
			
			checkOverrides();
		}
		else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
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
		}
		else if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (getParentClass().isPrimitiveType())
			{
				setVisibility(PUBLIC);
			}
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public NovaMethodDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		NovaMethodDeclaration node = new NovaMethodDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public NovaMethodDeclaration cloneTo(NovaMethodDeclaration node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link NovaMethodDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public NovaMethodDeclaration cloneTo(NovaMethodDeclaration node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		node.overloadID = overloadID;
		node.uniqueID   = uniqueID;
		
		for (NovaMethodDeclaration child : overridingMethods)
		{
			node.overridingMethods.add((NovaMethodDeclaration)child.clone(node, child.getLocationIn()));
		}
		
		node.types = new String[types.length];
		
		int i = 0;
		
		for (String type : types)
		{
			node.types[i++] = type;
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
	private static class MethodData extends DeclarationData
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
	
	public String toString()
	{
		return generateNovaSignature();
	}
}