package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.MethodList.SearchFilter;
import net.fathomsoft.nova.tree.annotations.Annotation;
import net.fathomsoft.nova.tree.annotations.OverrideAnnotation;
import net.fathomsoft.nova.tree.annotations.PublicAnnotation;
import net.fathomsoft.nova.tree.annotations.RequireGenericTypeAnnotation;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.tree.generics.GenericTypeParameterList;
import net.fathomsoft.nova.tree.variables.ObjectReference;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Declaration extension that represents the declaration of a method
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.21 Jul 30, 2014 at 1:45:00 PM
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class NovaMethodDeclaration extends MethodDeclaration implements ScopeAncestor, ClosureCompatible
{
	public boolean usedShorthandAction;
	
	public int	uniqueID, overloadID;
	
	public String shorthandAction;
	
	private String[] types;
	
	public NovaMethodDeclaration overridenMethod;
	private ArrayList<NovaMethodDeclaration> overridingMethods, primitiveOverloads;
	public ArrayList<NovaMethodDeclaration> correspondingPrimitiveOverloads;
	
	public NovaMethodDeclaration genericOverload;
	
	private static HashMap<Integer, Scope> scopes = new HashMap<>();
	
	public VirtualMethodDeclaration virtualMethod;
	
	public ObjectReference objectReference;
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclaration#InstanceDeclaration(Node, Location)
	 */
	public NovaMethodDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		uniqueID           = 0;
		overloadID         = -1;
		types              = new String[0];
		overridingMethods  = new ArrayList<>();
		primitiveOverloads = new ArrayList<>();
		correspondingPrimitiveOverloads = new ArrayList<>();
		
		NovaParameterList parameters = new NovaParameterList(this, locationIn.asNew());
		
		replace(super.getParameterList(), parameters);
		
		GenericTypeParameterList methodParams = new GenericTypeParameterList(this, locationIn.asNew());
		addChild(methodParams, this);
	}
	
	public int[] getDistancesFrom(ParameterList other)
	{
		return getParameterList().getDistancesFrom(other);
	}
	
	public boolean isSuperCallFrom(ClassDeclaration clazz)
	{
		if (clazz.getExtendedClassDeclaration() == getDeclaringClass())
		{
			SearchFilter filter = new SearchFilter();
			filter.checkAncestor = false;
			
			return clazz.getMethod((GenericCompatible)null, getName(), filter, getParameterList().getTypes(), true) != null;
		}
		
		return false;
	}
	
	@Override
	public ClosureDeclaration getClosureDeclaration()
	{
		return type instanceof FunctionType ? ((FunctionType)type).closure : null;
	}
	
	@Override
	public ObjectReference getObjectReference()
	{
		return getObjectReference(false);
	}
	
	public ObjectReference getObjectReference(boolean force)
	{
		if ((objectReference == null || force) && isInstance())
		{
			objectReference = new ObjectReference(this);
		}
		
		return objectReference;
	}
	
	public VirtualMethodDeclaration getVirtualMethod()
	{
		return virtualMethod == null && doesOverride() ? getOverriddenMethod().getVirtualMethod() : virtualMethod;
	}
	
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	public GenericTypeParameterList getMethodGenericTypeParameterDeclaration()
	{
		if (getNumChildren() > super.getNumDefaultChildren() && super.getChild(super.getNumDefaultChildren() + 0) instanceof GenericTypeParameterList)
		{
			return (GenericTypeParameterList)super.getChild(super.getNumDefaultChildren() + 0);
		}
		
		return null;
	}
	
	public boolean containsGenericTypeParameter(String name)
	{
		return getGenericTypeParameter(name) != null;
	}
	
	public GenericTypeParameter getGenericTypeParameter(String name)
	{
		GenericTypeParameterList decl = getMethodGenericTypeParameterDeclaration();
		
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
		return getParameterList().getParameter(parameterIndex);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ParameterList#getParameter(String)
	 * 
	 * @param parameterName The name of the parameter to find.
	 * @return The Parameter with the given name.
	 */
	public Parameter getParameter(String parameterName)
	{
		return getParameterList().getParameter(parameterName);
	}
	
	@Override
	public int getUniqueID()
	{
		return ++uniqueID;
	}
	
	@Override
	public HashMap<Integer, Scope> getScopes()
	{
		return scopes;
	}
	
	/**
	 * Get a unique integer used for differentiating methods that are
	 * overloaded.
	 * 
	 * @return A unique identifier for overloaded methods.
	 */
	public int getOverloadID()
	{
		return overloadID < 0 && doesOverride() ? getOverriddenMethod().getOverloadID() : overloadID;
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
	
	@Override
	public VariableDeclaration searchVariable(Node parent, Scope scope, String name, boolean checkAncestors)
	{
		VariableDeclaration var = super.searchVariable(parent, scope, name, checkAncestors);
		
		if (var != null)
		{
			return var;
		}
		
		Parameter parameter = getParameter(name);
		
		if (parameter != null && parameter != parent)
		{
			getParameterList().validateAccess(parameter, parent);
			
			return parameter;
		}
		
		return null;
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
		return overridenMethod != null;
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
		return overridenMethod;
	}
	
	private NovaMethodDeclaration getOverriddenMethod(SearchFilter filter)
	{
		if (getVisibility() == PRIVATE && (this instanceof PropertyMethod == false || getParentClass().getField(getName()) == null || !getParentClass().getField(getName()).isPropertyTrue("inheritedFromTrait")))
		{
			return null;
		}
		
		ClassDeclaration extension = getParentClass().getExtendedClassDeclaration();
		
		if (extension != null)
		{
			NovaMethodDeclaration method = (NovaMethodDeclaration)extension.getMethod(getContext(), getName(), filter, getParameterList().getTypes());
			
			if (method != null && method != this && SyntaxUtils.isTypeCompatible(this, method, this))
			{
				return method;
			}
		}
		
		for (Trait inter : getParentClass().getImplementedInterfaces())
		{
			NovaMethodDeclaration method = (NovaMethodDeclaration)inter.getMethod(getContext(), getName(), filter, getParameterList().getTypes());
			
			if (method != null && method != this && SyntaxUtils.isTypeCompatible(this, method, this))
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
	public boolean containsOverridingMethod(NovaMethodDeclaration overridingMethod)
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
	public void setOverloadIDs(MethodDeclaration methods[])
	{
		ArrayList<NovaMethodDeclaration> list = new ArrayList<>();
		
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
				
				if (SyntaxUtils.areSameTypes(getParameterList().getTypes(), method.getParameterList().getTypes()))
				{
					if (method.getParentClass() == getParentClass())
					{
						method.getParentClass();
						getParentClass();
						SyntaxMessage.error("Duplicate method '" + getName() + "'", this);
					}
					
					continue;
				}
				
				if (method.overloadID < 0)
				{
					list.add(method);
				}
				else if (max < method.overloadID)
				{
					max = method.overloadID;
				}
			}
		}
		
		/*if (overridenMethod != null)
		{
			overloadID = getOverriddenMethod().overloadID;
		}
		else */if (list.size() > 0)
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
	
	public void addDefaultParameterInitializations()
	{
		for (Parameter p : getParameterList().getOptionalParameters())
		{
			DefaultParameterInitialization init = new DefaultParameterInitialization(this, getLocationIn(), p);
			
			addChild(init);
		}
	}
	
	@Override
	public NovaParameterList getParameterList()
	{
		return (NovaParameterList)super.getParameterList();
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
			builder.append(" -> ").append(getTypeObject().toNova());
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
				
				if (parent.getParentClass(true) instanceof Trait)
				{
					/*if (n.getVisibility() != PRIVATE)
					{
						SyntaxMessage.error("Trait functions cannot have visibility modifiers", n);
					}
					
					n.setVisibility(PUBLIC);*/
				}
				
				// Temporary workaround for crappy closure declaration implementation
				// Won't make this mistake in the rewrite.
				if (parent.getFileDeclaration().containsPendingAnnotationOfType(PublicAnnotation.class))
				{
					n.setVisibility(PUBLIC);
				}
				
				return n;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean canAccess()
	{
		return false;
	}
	
	public boolean checkOverrides()
	{
		if (overridenMethod == null)
		{
			SearchFilter filter = new SearchFilter();
			filter.checkStatic(isStatic());
			filter.checkProperties = true;
			filter.allowMoreParameters = false;
			filter.requireExactMatch = true;
			filter.defaultGeneric = false;
			filter.requireEqualParameterCount = true;
			
			overridenMethod = getOverriddenMethod(filter);
		}
		
		if (overridenMethod != null)
		{
			if (!containsOverridingMethod(overridenMethod))
			{
				overridenMethod.overridingMethods.add(this);
			}
			
			return true;
		}
		
		return false;
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
	
	/**
	 * Check to see if the return type of the method is an external type.
	 */
	private void checkExternalType()
	{
//		if (getParentClass().containsExternalType(getType()))
//		{
//			setDataType(IValue.POINTER);
//		}
	}
	
	public NovaMethodDeclaration getExistingConvertedPrimitiveMethod(Value[] args)
	{
		return getExistingConvertedPrimitiveMethod(args, getMethodGenericTypeParameterDeclaration().getTypes());
	}
	
	public NovaMethodDeclaration getExistingConvertedPrimitiveMethod(Value[] args, Value[] methodArgs)
	{
		for (NovaMethodDeclaration converted : primitiveOverloads)
		{
			NovaParameterList params = converted.getParameterList();
			
			if (params.getNumParameters() == args.length)
			{
				boolean compatible = true;
				
				for (int i = 0; i < args.length; i++)
				{
					Parameter param = params.getParameter(i);
					Value arg = args[i].getReturnedNode();
					
					if (arg.getDataType() != param.getDataType() ||
						arg.getTypeClass() == null || !arg.getTypeClass().isOfType(param.getTypeClass()))
					{
						compatible = false;
					}
					else if ((genericOverload != null && genericOverload.getParameter(i).isGenericType()) &&
						arg.isPrimitive() && param.isPrimitive() && !arg.getType().equals(param.getType()))
					{
						compatible = false;
					}
				}
				
				GenericTypeParameterList genParams = converted.getMethodGenericTypeParameterDeclaration();
				
				if (genParams.getNumVisibleChildren() != methodArgs.length)
				{
					compatible = false;
				}
				else
				{
					for (int i = 0; i < methodArgs.length; i++)
					{
						Value param = genParams.getVisibleChild(i);
						Value arg = methodArgs[i].getReturnedNode();
						
						if (arg.getDataType() != param.getDataType() ||
							arg.getTypeClass() == null || !arg.getTypeClass().isOfType(param.getTypeClass()))
						{
							compatible = false;
						}
						else if (arg.isPrimitive() && param.isPrimitive() && !arg.getType().equals(param.getType()))
						{
							compatible = false;
						}
					}
				}
				
				if (compatible)
				{
					return converted;
				}
			}
		}
		
		for (Parameter param : getParameterList())
		{
			if (param.isGenericType())
			{
				return null;
			}
		}
		
//		RequireGenericTypeAnnotation require = (RequireGenericTypeAnnotation)getAnnotationOfType(RequireGenericTypeAnnotation.class, false, false);
//		
//		if (require != null)
//		{
//			if (!SyntaxUtils.areTypesCompatible(new GenericCompatible[] { this }, require.getGenericTypeParameterDeclaration().getTypes(), types))
//			{
//				return null;
//			}
//		}
		
		return null;
	}
	
	public NovaMethodDeclaration checkConvertToClass(ClassDeclaration type)
	{
		return checkConvertToClass(type, getMethodGenericTypeParameterDeclaration().getTypes());
	}
	
	public NovaMethodDeclaration checkConvertToClass(ClassDeclaration type, Value[] methodTypes)
	{
		if (getParentClass() != type)
		{
			if (getExistingConvertedPrimitiveMethod(type.primitiveOverloadTypes) == null)
			{
				return convertToClass(type, type.primitiveOverloadTypes, methodTypes);
			}
		}
		
		return null;
	}
	
	public NovaMethodDeclaration convertToClass(ClassDeclaration parent, final Value[] types, final Value[] methodTypes)
	{
		ClassDeclaration referenceClass = getParentClass();

		NovaMethodDeclaration clone = clone(parent, getLocationIn(), false, true);
		clone.overridenMethod = null;
		clone.overridingMethods = new ArrayList<>();
		clone.setProperty("userMade", false);
		clone.removeAnnotationOfType(OverrideAnnotation.class, false, false);
		clone.removeAnnotationOfType(RequireGenericTypeAnnotation.class, false, false);
		getGenericTypeArgumentList().cloneChildrenTo(clone.getGenericTypeArgumentList());
//		clone.setName("zca_" + clone.getName());
		
		GenericTypeParameterList methodParams = clone.getMethodGenericTypeParameterDeclaration();
		
		getMethodGenericTypeParameterDeclaration().cloneChildrenTo(methodParams);
		
		for (int i = 0; i < methodTypes.length; i++)
		{
			GenericTypeParameter param;
			
			if (i >= methodParams.getNumVisibleChildren())
			{
				param = new GenericTypeParameter(clone, Location.INVALID);
			}
			else
			{
				param = methodParams.getVisibleChild(i);
			}
			
			param.setDefaultType(methodTypes[i].getType());
		}
		
		RequireGenericTypeAnnotation require = (RequireGenericTypeAnnotation)getAnnotationOfType(RequireGenericTypeAnnotation.class, false, false);
		
		if (require != null)
		{
			if (!SyntaxUtils.areTypesCompatible(new GenericCompatible[] { this }, require.getGenericTypeParameterDeclaration().getTypes(), types))
			{
				return null;
			}
		}
		
		if (getGenericTypeArgumentList().getNumVisibleChildren() > 0)
		{
			clone.getGenericTypeArgumentList().replaceWith(getGenericTypeArgumentList().clone(clone.getGenericTypeArgumentList(), getLocationIn(), true, true));
		}
		
		ParameterList<Parameter> originalParameterList = getParameterList();
		ParameterList<Parameter> parameterList = originalParameterList.clone(clone, getLocationIn(), true, true);
		
		clone.getParameterList().replaceWith(parameterList);

		boolean changed = false;
		
		for (int i = 0; i < parameterList.getNumParameters(); i++)
		{
			Parameter param = parameterList.getParameter(i);
			
			changed |= referenceClass.replacePrimitiveGenerics(types, originalParameterList.getParameter(i), param);
		}
		
		referenceClass.replacePrimitiveGenerics(types, this, clone);
		
		clone.getParameterList().getReferenceParameter().setType(parent);
		clone.getObjectReference(true);
		
		clone.usedShorthandAction = false;
		clone.shorthandAction = null;
		
		for (int i = 0; i < parameterList.getNumParameters(); i++)
		{
			changed |= parent.replacePrimitiveGenerics(types, originalParameterList.getParameter(i), parameterList.getParameter(i));
		}
		
		if (clone instanceof Constructor)
		{
			clone.setName(parent.getName());
			clone.setType(parent);
			
			changed = true;
		}

//		if (changed)
		{
			SearchFilter filter = new SearchFilter();
			filter.requireExactMatch = true;
			filter.checkInterfaces = false;
			filter.checkAncestor = false;
			filter.defaultGeneric = true;
			filter.checkProperties = true;
			
			MethodDeclaration[] found = parent.getMethods(new GenericCompatible[] { parent }, clone.getName(), filter, clone.getParameterList().getTypes(), false);
			
			if (found.length == 0)// && (clone instanceof PropertyMethod == false || parent.getField(clone.getName(), false) == null))
			{
				parent.addChild(clone);
				
				clone.genericOverload = this;
				
				correspondingPrimitiveOverloads.add(clone);
				
				for (Parameter p : clone.getParameterList().getOptionalParameters())
				{
					DefaultParameterInitialization init = new DefaultParameterInitialization(clone, p.getLocationIn(), p);
					
					clone.addChild(init);
				}
				
				clone.onAfterDecoded();
				
				return clone;
			}
		}
		
		return null;
	}
	
	public NovaMethodDeclaration convertPrimitiveMethod(MethodCall call, Value returnType, Node addTo, Value[] types, Value[] methodTypes, ArrayList<Value[]> closureTypes)
	{
		NovaMethodDeclaration method = clone(getParent(), getLocationIn(), false, true);
		method.setProperty("userMade", false);
		method.overridenMethod = null;
		method.overridingMethods = new ArrayList<>();
		
		if (returnType == call)
		{
			getGenericTypeArgumentList().cloneChildrenTo(method.getGenericTypeArgumentList());
		}
		else
		{
			method.setType(returnType);
		}
		
		GenericTypeParameterList methodParams = method.getMethodGenericTypeParameterDeclaration();
		
		getMethodGenericTypeParameterDeclaration().cloneChildrenTo(methodParams);
		
		for (int i = 0; i < methodTypes.length; i++)
		{
			GenericTypeParameter param;
			
			if (i >= methodParams.getNumVisibleChildren())
			{
				param = new GenericTypeParameter(method, Location.INVALID);
			}
			else
			{
				param = methodParams.getVisibleChild(i);
			}
			
			if (methodTypes[i].isGenericType())
			{
				param.setDefaultType(methodTypes[i].getGenericTypeParameter().getDefaultType());
			}
			else
			{
				param.setDefaultType(methodTypes[i].getType());
			}
		}
		
		addTo = addTo == null ? /*call.getReferenceNode().toValue().getTypeClass()*/getParent() : addTo;
		
		int closureIndex = 0;
		
		if (isInstance())// && call.isPrimitiveOverload())
		{
			addTo = moveToClass(method, call);
		}
		
		for (int i = 0; i < types.length; i++)
		{
			Parameter param = getParameter(i).clone(method.getParameterList(), getLocationIn(), true, true);

			if (param != types[i])
			{
				param.setType(types[i]);
			}
			if (param instanceof ClosureDeclaration)
			{
				Parameter original = getParameter(i);
				ClosureDeclaration closure = (ClosureDeclaration)param;
				
				closure.register();
				
				Value[] closureValues = closureTypes.get(closureIndex++);
				
				for (int n = 0; n < closureValues.length; n++)
				{
					closure.getParameterList().getParameter(n).setType(closureValues[n]);
					
					for (Variable v : original.references)
					{
						if (v instanceof MethodCall)
						{
							MethodCall c = (MethodCall)v;
							
							Value val = c.getArgumentList().getVisibleChild(n);
							
							if (val instanceof Variable)
							{
								VariableDeclaration decl = ((Variable)val).declaration;
								
								if (decl instanceof ReferenceParameter || decl instanceof InstanceDeclaration && decl.getParentClass() == getParentClass())
								{
									addTo = moveToClass(method, call);
								}
							}
						}
					}
				}
			}

			method.getParameterList().addChild(param);
		}
		
		for (int i = types.length; i < getParameterList().getNumParameters(); i++)
		{
			method.getParameterList().addChild(getParameter(i).clone(method.getParameterList(), getLocationIn(), true, true));
		}
		
		primitiveOverloads.add(method);
		method.genericOverload = this;
		
		addTo.addChild(method);
		
		return method;
	}
	
	private ClassDeclaration moveToClass(NovaMethodDeclaration original, Variable var)
	{
		ClassDeclaration newClass = var.getDeclaringClass();
		
		if (!newClass.isPrimitiveOverload() && newClass != getDeclaringClass() && newClass.isOfType(getDeclaringClass()))
		{
			newClass = getDeclaringClass();
		}
		
		Parameter ref = original.getParameterList().getReferenceParameter();
		
		ref.getGenericTypeArgumentList().slaughterEveryLastVisibleChild();
		ref.setType(newClass);
		
		return newClass;
	}
	
	public NovaMethodDeclaration getConvertedPrimitiveMethod(MethodCall call)
	{
		Value[] args = call.getArgumentList().getArgumentsInOrder();
		Value[] methodArgs = call.getMethodGenericTypeArgumentList().getTypes();
		
		NovaMethodDeclaration existing = getExistingConvertedPrimitiveMethod(args, methodArgs);
		
		if (existing != null)
		{
			return existing;
		}
		
		Accessible ref = call.getReferenceNode();
		Value[] argTypes = ref.toValue().getGenericTypeArgumentList() != null ? ref.toValue().getGenericTypeArgumentList().getTypes() : null;
		
		NovaParameterList parameters = getParameterList();
		
		Value[] types = new Value[args.length];
		Value[] methodTypes = new Value[methodArgs.length];
		ArrayList<Value[]> closureTypes = new ArrayList<>();
		
		boolean isPrimitive = false;
		
		for (int i = 0; i < args.length; i++)
		{
			Value arg = args[i].getReturnedNode();
			Value param = parameters.getParameter(i);
			
			isPrimitive |= checkType(argTypes, types, i, arg, param, closureTypes);
		}
		
//		RequireGenericTypeAnnotation require = (RequireGenericTypeAnnotation)getAnnotationOfType(RequireGenericTypeAnnotation.class, false, false);
//		
//		if (require != null)
//		{
//			for (GenericTypeParameter param : require.getGenericTypeParameterDeclaration())
//			{
//				isPrimitive |= SyntaxUtils.isPrimitiveType(param.getDefaultType());
//			}
//		}
		
		GenericTypeParameterList methodGenParams = getMethodGenericTypeParameterDeclaration();
		
		for (int i = 0; i < Math.min(methodArgs.length, methodGenParams.getNumVisibleChildren()); i++)
		{
			Value arg = methodArgs[i].getReturnedNode();
			Value param = methodGenParams.getVisibleChild(i);
			
			isPrimitive |= checkType(methodArgs, methodTypes, i, arg, param, closureTypes);
		}
		
		Value returnType = call;
		Node addTo = isInstance() ? ref.toValue().getTypeClass() : call.getInferredDeclaration().getParentClass();//call.getReferenceNode().toValue().getTypeClass();//getParent();
		
		if (call.getType() != null && call.getGenericTypeArgumentList().getNumVisibleChildren() > 0)
		{
			String s = generateNovaType(call).toString();

			GenericTypeArgument arg = new GenericTypeArgument(this, Location.INVALID);
			arg.setType(s);

			if (arg.convertToPrimitiveType())
			{
				returnType = arg;
//				addTo = arg.getTypeClass();
			}
			
//			isPrimitive |= ClassDeclaration.replacePrimitiveGenerics(getMethodGenericTypeParameterDeclaration(), methodArgs, call, call, true);
		}
		
		if (isPrimitive)
		{
			return convertPrimitiveMethod(call, returnType, addTo, types, methodTypes, closureTypes);
		}
		
		return null;
	}
	
	public boolean checkType(Value[] argTypes, Value[] types, int i, Value arg, Value param, ArrayList<Value[]> closureTypes)
	{
		boolean isPrimitive = false;
		
		if (arg instanceof DefaultArgument == false &&
			arg.getType() != null && (arg.isPrimitive() && !param.isPrimitive() ||
				!param.isGenericType() && param.getTypeClass() != null && !param.getTypeClass().isPrimitiveOverload() &&
					arg.getTypeClass() != null && arg.getTypeClass().isPrimitiveOverload()))
		{
			types[i] = arg;
			
			isPrimitive = true;
		}
		else
		{
			types[i] = param;
		}
		
		if (param instanceof ClosureDeclaration)
		{
			Variable varg = (Variable)arg;
			ClosureDeclaration closure = (ClosureDeclaration)param;
			
			if (varg.declaration != null)
			{
				CallableMethod callable = null;
				
				if (varg.declaration.isFunctionType())
				{
					callable = ((FunctionType)varg.declaration.getTypeObject()).closure;
				}
				else
				{
					callable = (CallableMethod)varg.declaration.getOriginalDeclaration();
				}
				
				ParameterList aParams = callable.getParameterList();
				ParameterList<Value> cParams = closure.getParameterList();
				
				Value[] closureValues = new Value[Math.min(aParams.getNumParameters(), cParams.getNumParameters())];
				
				for (int n = 0; n < closureValues.length; n++)
				{
					Value aarg = aParams.getParameter(n);
					Value aparam = cParams.getParameter(n);
					
					if (aarg.isPrimitive() && !aparam.isPrimitive())
					{
						closureValues[n] = aarg;
						
						isPrimitive = true;
					}
					else
					{
						if (aarg.getGenericTypeArgumentList().getNumVisibleChildren() > 0)
						{
							closureValues[n] = (Value)aarg.clone(aarg.getParent(), aarg.getLocationIn(), true, true);
							
							if (getParentClass().replacePrimitiveGenerics(argTypes, aparam, closureValues[n]))
							{
								isPrimitive = true;
							}
							else
							{
								closureValues[n] = aparam;
							}
						}
						else
						{
							closureValues[n] = aparam;
						}
					}
				}
				
				closureTypes.add(closureValues);
			}
		}
		
		return isPrimitive;
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
	
	private int findShorthandActionOperator(String statement)
	{
		return SyntaxUtils.findStringInBaseScope(statement, "=>");
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
		int shorthand = findShorthandActionOperator(statement);
		
		if (shorthand > 0)
		{
			shorthandAction = statement.substring(shorthand + 2).trim();
			
			statement = statement.substring(0, shorthand).trim();
			
			usedShorthandAction = true;
		}
		
		String signature = findMethodSignature(statement);
		MethodData data  = new MethodData(signature);
		
		String returnType = null;
		int returnIndex = signature.indexOf("->");
		
		if (returnIndex > 0) {
			returnType = signature.substring(returnIndex + 2).trim();
			signature = signature.substring(0, returnIndex).trim();
		}
		
		GenericTypeParameterList.searchGenerics(signature, data);
		
		iterateWords(signature, Patterns.IDENTIFIER_BOUNDARIES, data, require);
		
		if (data.error != null)
		{
			return SyntaxMessage.queryError(data.error, this, require);
		}
		
		while (data.getGenericsRemaining() > 0)
		{
			Bounds bounds = data.getSkipBounds(data.getGenericsRemaining() - 1);
			
			String arg = bounds.extractString(signature);
			
			arg = arg.substring(GENERIC_START.length(), arg.length() - GENERIC_END.length());
			
			getMethodGenericTypeParameterDeclaration().decodeMethodGenericTypeParameters(arg);
			
			data.decrementGenericsRemaining();
		}
		
		setReturnType(returnType);
		
		return getName() != null;
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
			String parameters[] = StringUtils.splitCommas(parameterList, 1);
			
			Location location = new Location(getLocationIn());
			
			for (int i = 0; i < parameters.length; i++)
			{
				if (parameters[i].length() > 0)
				{
					ArrayList<Annotation> annotations = new ArrayList<>();

					Annotation a = Annotation.decodeStatement(this, parameters[i], location, require);
					
					while (a != null)
					{
						parameters[i] = Annotation.getFragment(parameters[i]);

						annotations.add(a);

						a = Annotation.decodeStatement(this, parameters[i], location, require);
					}
					
					Parameter param = Parameter.decodeStatement(this, parameters[i], location, require, isUserMade(), isUserMade() || getParentClass().isPropertyTrue("functionMap"));
					
					if (param == null)
					{
						return SyntaxMessage.queryError("Incorrect parameter definition", this, require);
					}

					for (Annotation anno : annotations)
					{
						param.addAnnotation(anno);
					}
					
					param.onAfterDecoded();
					
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
		
		if (data.error != null || extra.isLastWord() || !setAttribute(word, extra.getWordNumber()))
		{
			if (leftDelimiter.equals("->") || (getType() != null && leftDelimiter.equals(",")))
			{
				addType(word);
				
				if (rightDelimiter.startsWith(GENERIC_START) && rightDelimiter.endsWith(GENERIC_END))
				{
					addGenericTypeArgumentName(rightDelimiter.substring(GENERIC_START.length(), rightDelimiter.length() - GENERIC_END.length()));
				}
				
				if (!checkArray(data.signature, bounds.getEnd(), rightDelimiter, extra.require))
				{
					extra.error = "Could not parse array brackets";
				}
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
		
		if (getTypeObject() != null)
		{
			signature += " -> " + getTypeObject().toNova();
		}
		
		signature += getGenericTypeArgumentList().generateNovaInput();
		
		return signature;
	}
	
	public boolean isVirtualMethodDeclaration()
	{
		return isOverridden() && !doesOverride();
	}
	
	public void searchVirtualMethodDeclaration()
	{
		if (virtualMethod == null && isVirtualMethodDeclaration())
		{
			virtualMethod = new VirtualMethodDeclaration(getParentClass(), getLocationIn().asNew());
			virtualMethod.base = this;
			
			this.cloneTo(virtualMethod, false, true);
			virtualMethod.objectReference = new ObjectReference(virtualMethod);
			
//			getParentClass().addChild(virtualMethod);
		}
	}
	
	public void decodeShorthandAction()
	{
		if (shorthandAction != null && getProgram().decodeShorthandActions)
		{
			String action = shorthandAction;
			
			shorthandAction = null;
			
			boolean containedType = getType() != null;
			
			Node contents = SyntaxTree.decodeScopeContents(this, action, getLocationIn(), true);
			
			if (contents instanceof Value)
			{
				contents = inferShorthandActionType(action, (Value)contents);
			}
			
			addChild(contents);
			
			if (!containedType && getType() != null)
			{
				for (NovaMethodDeclaration m : correspondingPrimitiveOverloads)
				{
					GenericTypeArgument value = new GenericTypeArgument(this, Location.INVALID);
					value.setType(this);
					value.parent = m;
					value.onAfterDecoded();
					
					getParentClass().replacePrimitiveGenerics(m.getParentClass().primitiveOverloadTypes, this, value);
					
					m.setType(value);
				}
			}
		}
	}
	
	public void throwInferTypeError()
	{
		SyntaxMessage.error("Unable to infer return value of arrow binding for function '" + getName() + "'. Please add an explicit return type to any functions that the arrow binding returns.", this);
	}
	
	public Value inferShorthandActionType(String action, Value contents)
	{
		boolean setType = getType(false) == null;
		
		Value returned = contents.getReturnedNode();
		
		if (returned instanceof Closure)
		{
			Closure closure = (Closure)returned;
			
			if (setType)
			{
				if (closure.declarations.length == 1)
				{
					setType(closure.declarations[0].getFunctionReferenceType());
				}
				else if (closure.declarations.length > 1)
				{
					SyntaxMessage.error("Ambiguous function '" + action + "'. Explicitly specify the type of function that is returned by function '" + getName() + "' to resolve this", this);
				}
				else
				{
					SyntaxMessage.error("Could not find function '" + action + "'", this);
				}
			}
			
			closure.findDeclaration();
		}
		
		if (returned instanceof Closure == false && returned.getType() == null)
		{
			if (returned instanceof MethodCall)
			{
				NovaMethodDeclaration method = ((MethodCall)returned).getNovaMethod();
				
				if (method != null && method.shorthandAction != null)
				{
					throwInferTypeError();
				}
			}
		}
		else
		{
			if (returned instanceof Closure == false && setType)
			{
				setType(returned);//returned.getNovaTypeValue(returned));
			}
			
			Return r = new Return(this, getLocationIn());
			r.getReturnValues().addChild(contents);
			
			contents = r;
		}
		
		return contents;
	}
	
	public boolean overrides(NovaMethodDeclaration other)
	{
		NovaMethodDeclaration method = overridenMethod;
		
		while (method != null)
		{
			if (method == other)
			{
				return true;
			}
			
			method = method.overridenMethod;
		}
		
		return false;
	}
	
	public void updateGenericParameters()
	{
		if (overridenMethod != null)
		{
			overridenMethod.updateGenericParameters();
			
			NovaParameterList params = overridenMethod.getParameterList();
			
			for (int i = 0; i < params.getNumParameters(); i++)
			{
				getParameterList().getParameter(i).updateGenericParameter(params.getParameter(i));
			}
			
			if (overridenMethod.getType() != null)
			{
				setDataType(overridenMethod.getDataType());
			}
			
			if (getScope().getNumVisibleChildren() > 0)
			{
				Node n = getScope().getVisibleChild(0);
				
				if (n instanceof Return)
				{
					Return r = (Return)n;
					
					if (r.getValueNode().getReturnedNode().isPrimitive() && !isPrimitive())
					{
						r.getValueNode().replaceWithAutoboxedValue();
					}
				}
				else if (n instanceof Assignment)
				{
					Assignment assign = (Assignment)n;
					
					if (getTypeObject() != null && assign.getAssignedNodeValue().getReturnedNode().isPrimitive() && !isPrimitive())
					{
						assign.getAssignmentNode().replaceWithUnboxedValue();
					}
				}
			}
		}
	}
	
	public MethodCall[] getReferencesIncludingOverrides()
	{
		ArrayList<MethodCall> refs = new ArrayList<>();
		
		for (Variable v : references)
		{
			refs.add((MethodCall)v);
		}
		
		for (NovaMethodDeclaration method : getOverridingMethods())
		{
			MethodCall[] references = method.getReferencesIncludingOverrides();
			
			for (MethodCall m : references)
			{
				refs.add(m);
			}
		}
		
		return refs.toArray(new MethodCall[0]);
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
		
		if (objectReference == null && isInstance())
		{
			objectReference = new ObjectReference(this);
		}
		
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			//TODO: Is this necessary?
			getParameterList().validate(phase);
			
			if (genericOverload == null || !genericOverload.usedShorthandAction)
			{
				checkOverrides();
			}
		}
		else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (overloadID < 0)
			{
				SearchFilter filter = new SearchFilter();
				filter.checkConstructors = false;
				filter.checkAncestor = false;
				filter.checkStatic(isStatic());
				
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
			
			if (this instanceof VirtualMethodDeclaration == false && virtualMethod != null)
			{
				virtualMethod.validate(phase);
			}
		}
		
		return result;
	}
	
	public boolean isPrimitiveOverload()
	{
		return genericOverload != null || getParentClass().isPrimitiveOverload();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public NovaMethodDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		NovaMethodDeclaration node = new NovaMethodDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public NovaMethodDeclaration cloneTo(NovaMethodDeclaration node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link NovaMethodDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public NovaMethodDeclaration cloneTo(NovaMethodDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.objectReference = objectReference;
		node.overloadID = overloadID;
		node.uniqueID   = uniqueID;
		node.shorthandAction = shorthandAction;
		node.usedShorthandAction = usedShorthandAction;
		node.virtualMethod = virtualMethod;
		node.overridenMethod = overridenMethod;
		node.genericOverload = genericOverload;
		
		for (NovaMethodDeclaration child : overridingMethods)
		{
			node.overridingMethods.add(child);//(NovaMethodDeclaration)child.clone(node, child.getLocationIn()));
		}
		
		node.types = new String[types.length];
		
		int i = 0;
		
		for (String type : types)
		{
			node.types[i++] = type;
		}
		
		return node;
	}
	
	@Override
	public void onReplaced(Node parent, Node replacement)
	{
		for (NovaMethodDeclaration overriding : overridingMethods)
		{
			overriding.overridenMethod = (NovaMethodDeclaration)replacement;
		}
		
		super.onReplaced(parent, replacement);
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