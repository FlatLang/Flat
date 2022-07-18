package org.flatlang.tree;

import org.flatlang.Flat;
import org.flatlang.TestContext;
import org.flatlang.ValidationResult;
import org.flatlang.error.SyntaxErrorException;
import org.flatlang.error.SyntaxMessage;
import org.flatlang.tree.MethodList.SearchFilter;
import org.flatlang.tree.generics.GenericTypeArgument;
import org.flatlang.tree.generics.GenericTypeArgumentList;
import org.flatlang.tree.generics.GenericTypeParameter;
import org.flatlang.tree.generics.GenericTypeParameterList;
import org.flatlang.tree.lambda.LambdaExpression;
import org.flatlang.tree.variables.*;
import org.flatlang.util.*;
import org.flatlang.util.Bounds;

import java.util.Arrays;
import java.util.regex.Matcher;

/**
 * Value extension that represents the declaration of a method
 * call node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:04:31 PM
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class MethodCall extends Variable
{
	/**
	 * Instantiate a new MethodCall and initialize the default values.
	 * 
	 * @see Node#Node(Node, Location)
	 */
	public MethodCall(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		MethodCallArgumentList arguments = new MethodCallArgumentList(this, new Location(locationIn));
		GenericTypeArgumentList genericArguments = new GenericTypeArgumentList(this, locationIn.asNew());
		//GenericTypeArgumentList implementation = new GenericTypeArgumentList(this, locationIn.asNew());
		
		addChild(arguments);
		addChild(genericArguments);
		//addChild(implementation);
	}
	
	@Override
	public ClassDeclaration getDeclaringClass()
	{
		if (getFileDeclaration().getImportList().getAbsoluteClassLocation(getName()) != null)
		{
			return SyntaxUtils.getImportedClass(getFileDeclaration(), getName());
		}
		
		return ((Value)getReferenceNode()).getTypeClass(false);
	}
	
	public boolean isPrimitiveOverload()
	{
		return getFlatMethod() != null && getFlatMethod().isPrimitiveOverload();
	}
	
	@Override
	public ClassDeclaration getTypeClass(boolean checkCast, boolean defaultGenericType)
	{
		if (isPrimitiveOverload() && getFlatMethod().genericOverload != null && getFlatMethod().genericOverload.getFileDeclaration() != null)
		{
			ClassDeclaration d = getFlatMethod().genericOverload.getFileDeclaration().getClassDeclaration(getType());
			
			if (d != null)
			{
				return d;
			}
		}
		
		return super.getTypeClass(checkCast, defaultGenericType);
	}
	
	/**
	 * @see Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 2;
	}
	
	/**
	 * The the Node that represents the arguments to the method call.
	 * For example:<br>
	 * <blockquote><pre>
	 * methodName(5, "Arg2", 3 * n);</pre></blockquote>
	 * In the previous statement, the data within the parenthesis are the
	 * arguments passed to the method. The Argument returned by this
	 * method would contain a node for each of the arguments passed, in
	 * the correct order from left to right.
	 * 
	 * @return The Node that represents the arguments to the method
	 * 		call.
	 */
	public MethodCallArgumentList getArgumentList()
	{
		return (MethodCallArgumentList)getChild(super.getNumDefaultChildren() + 0);
	}
	
	public GenericTypeArgumentList getMethodGenericTypeArgumentList()
	{
		return (GenericTypeArgumentList)getChild(super.getNumDefaultChildren() + 1);
	}
	
	@Override
	public GenericTypeArgumentList getGenericTypeArgumentList()
	{
		if (this.isChainNavigation()) {
			return getReferenceNode().toValue().getGenericTypeArgumentList();
		}

		if (getParent() instanceof Instantiation)
		{
//			return ((Instantiation)getParent()).getGenericTypeArgumentList();
		}
		
		return super.getGenericTypeArgumentList();
	}
	
	public CallableMethod getCallableMethodBase()
	{
		return getCallableMethodBase(false);
	}
	
	public CallableMethod getCallableMethodBase(boolean requireVirtual)
	{
		CallableMethod callable = (CallableMethod)getMethodDeclaration();
		
		if (callable.isVirtual() && ((FlatMethodDeclaration)callable).getVirtualMethod() != null)
		{
			if (requireVirtual || !isVirtualTypeKnown())
			{
				FlatMethodDeclaration flatMethod = (FlatMethodDeclaration)callable;
				
				if (parent instanceof Super)
				{
					flatMethod = (FlatMethodDeclaration)callable;
				}
				else
				{
					flatMethod = flatMethod.getVirtualMethod();
				}
				
				return flatMethod;
			}
			else if (callable instanceof BodyMethodDeclaration == false)
			{
				return ((FlatMethodDeclaration)callable).getVirtualMethod();
			}
		}
		
		return callable;
	}
	
	public boolean containsPrimitiveGenericParameters()
	{
		CallableMethod callable = getInferredDeclaration();
		
		if (callable instanceof BodyMethodDeclaration)
		{
			VirtualMethodDeclaration virtual = ((FlatMethodDeclaration)callable).getVirtualMethod();
			
			if (virtual != null)
			{
				FlatParameterList params = virtual.getParameterList();
				
				for (int i = 0; i < Math.min(getArgumentList().getNumVisibleChildren(), params.getNumParameters()); i++)
				{
					if (params.getParameter(i).isGenericType() && !((Value)getArgumentList().getVisibleChild(i)).isGenericType())
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	@Override
	public boolean isInstance()
	{
		return declaration instanceof Constructor || super.isInstance();
	}
	
	/**
	 * @see Value#isVirtualTypeKnown()
	 */
	@Override
	public boolean isVirtualTypeKnown()
	{
		return !isVirtual() || getMethodDeclaration() instanceof FlatMethodDeclaration == false || getParent() instanceof Instantiation || super.isVirtualTypeKnown() || getParentClass().isPropertyTrue("functionMap");// || containsPrimitiveGenericParameters();//(!getName().equals("toString") && (getParent() instanceof Instantiation || (getMethodDeclaration() instanceof FlatMethodDeclaration && !((FlatMethodDeclaration)getMethodDeclaration()).isOverridden()) || super.isVirtualTypeKnown())) || getParentClass().isPropertyTrue("functionMap");
	}
	
	public boolean isVirtual()
	{
		if (getDeclaration() instanceof FlatMethodDeclaration)
		{
			return ((FlatMethodDeclaration)getDeclaration()).isVirtual();
		}
		
		return false;
	}
	
	/**
	 * Get whether or not the specified Node is used within an
	 * external context.
	 * 
	 * @return Whether or not the specified Node is used within an
	 * 		external context.
	 */
	public boolean isWithinExternalContext()
	{
		CallableMethod method = null;
		
		if (isDecoding())
		{
			ClassDeclaration declaring = getDeclaringClass();
			
			if (declaring != null)
			{
				MethodDeclaration methods[] = declaring.getMethods(getName());
				
				if (methods.length > 0)
				{
					method = methods[0];
				}
			}
		}
		else
		{
			method = getInferredDeclaration();
		}
		
		return method != null && method.isExternal() || super.isWithinExternalContext();
	}
	
	@Override
	public boolean isConstant()
	{
		return false;
	}
	
	@Override
	public boolean isConsistent()
	{
		return false;
	}
	
	/**
	 * @see Node#isSpecial()
	 */
	@Override
	public boolean isSpecial()
	{
		/*if (getInferredDeclaration().isVirtual())
		{
			if (isVirtualTypeKnown())//isAccessed() && ((Value)getAccessingNode()).isVirtualTypeKnown())
			{
				return true;
			}
			
			return false;
		}*/
		
		return true;
	}
	
	/**
	 * Get the Method instance that this MethodCall is calling in the
	 * form of a CallableMethod.
	 * 
	 * @return The Method instance that this MethodCall is
	 * 		calling.
	 */
	public CallableMethod getInferredDeclaration()
	{
		return getMethodDeclaration() instanceof CallableMethod ? (CallableMethod)getMethodDeclaration() : null;
	}
	
	public CallableMethod getCallableDeclaration()
	{
		CallableMethod method = getInferredDeclaration();
		
		if (method != null && method.isVirtual() && !isVirtualTypeKnown())
		{
			if (!isAccessed() || getAccessingNode() instanceof Super == false)
			{
				VirtualMethodDeclaration virtual = ((FlatMethodDeclaration)method).getVirtualMethod();
				
				if (virtual != null)
				{
					return virtual;
				}
			}
		}
		
		return method;
	}
	
	@Override
	public GenericCompatible getReferenceContext()
	{
		GenericCompatible context = super.getReferenceContext();
		
		if (context instanceof Instantiation)
		{
			return this;
		}
		
		return context;
	}
	
//	public boolean isPure(BodyMethodDeclaration context, boolean allowInstanceModification)
//	{
//		if (getInferredDeclaration() instanceof BodyMethodDeclaration)
//		{
//			BodyMethodDeclaration bodyFunc = (BodyMethodDeclaration)getInferredDeclaration();
//			
//			if (!bodyFunc.checkPure(true))//!bodyFunc.isPure())
//			{
//				if (getReferenceNode() instanceof Variable)
//				{
//					Variable ref = (Variable)getReferenceNode();
//					
////					if (ref.isFinal())
////					{
////						return bodyFunc.checkPure(true);
////					}
////					else if (allowInstanceModification)
////					{
////						if (ref.getDeclaration().getParentMethod() == context)
////						{
////							return bodyFunc.checkPure(true);
////						}
////					}
////
////					return false;
//					
////					return bodyFunc.checkPure(true);
//				}
////				else if (bodyFunc.isStatic())
////				{
////					return bodyFunc.checkPure(false);
////				}
//				
//				return false;
//			}
//		}
//		else
//		{
//			return getCallableDeclaration().checkPure(this, allowInstanceModification);
//		}
//		
//		return true;
//	}
	
	public Value getArgument(String name)
	{
		Value[] values = getArgumentList().getArgumentsInOrder();
		
		int index = getCallableDeclaration().getParameterList().getVisibleParameterIndex(name);
		
		if (index >= 0 && index < values.length)
		{
			return values[index];
		}
		
		return null;
	}
	
	/**
	 * Get the Method instance that this MethodCall is calling.
	 * 
	 * @return The Method instance that this MethodCall is
	 * 		calling.
	 */
	private VariableDeclaration searchMethodDeclaration()
	{
		return searchMethodDeclaration(getName());
	}
	
	private VariableDeclaration searchMethodDeclaration(String name)
	{
		ClosureDeclaration closure = searchClosureDeclaration(name);
		
		if (closure != null)
		{
			return closure;
		}
		
		// If constructor
		if (getFileDeclaration().getImportList().getAbsoluteClassLocation(name) != null)
		{
			setName(getFileDeclaration().getImportedClass(getDeclaringClass(), name).getName());
			
			return getDeclaringClass().getMethod(getContext(), name, getArgumentList());
		}
		if (getParent() instanceof Super)
		{
			return ((Super)getParent()).getTypeClass().getMethod((GenericCompatible)null, name, getArgumentList().getTypes());
		}
		
		Pair<ClassDeclaration, SearchFilter>[] classes = getDeclaringClasses();
		
		if (classes == null || classes.length == 0)
		{
			return null;
		}
		
		for (Pair<ClassDeclaration, SearchFilter> clazz : classes)
		{
			if (clazz.a == null)
			{
				getDeclaringClasses();
				SyntaxMessage.error("Could not find declaring class for '" + name + "'", this);
			}
			
			MethodDeclaration method = clazz.a.getMethod(getContext(), name, clazz.b, getArgumentList());
			
			if (method != null)
			{
				return method;
			}
		}
		
		for (Pair<ClassDeclaration, SearchFilter> clazz : classes)
		{
			FieldDeclaration field = clazz.a.getField(name);
			
			if (field != null)
			{
				if (field.isGenericType())
				{
					GenericTypeParameter param = field.getGenericTypeParameter();
					
					GenericTypeArgument arg = param.getCorrespondingArgument(this);
					
					if (arg != null && arg.getTypeObject() instanceof FunctionType)
					{
						ClosureVariable v = new ClosureVariable(parent, getLocationIn());
						v.setDeclaration(field);
						v.setType(arg.generateFlatType(arg).toString());
						
						return v.type.closure;//((FunctionType)arg.getTypeObject()).closure;
					}
				}
				else if (field.getTypeObject() instanceof FunctionType)
				{
	//				ClosureVariable v = new ClosureVariable(parent, getLocationIn());
	//				v.setDeclaration(field);
	//				v.setType(field.generateFlatType(field).toString());
					
					return ((FunctionType)field.getTypeObject()).closure;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Get the Method instance that this MethodCall is calling.
	 * 
	 * @return The Method instance that this MethodCall is
	 * 		calling.
	 */
	public VariableDeclaration getMethodDeclaration()
	{
		return getDeclaration();
	}
	
	private ClosureDeclaration searchClosureDeclaration(String name)
	{
		if (getParentMethod() == null)
		{
			return null;
		}
		
		VariableDeclaration closure = SyntaxTree.findDeclaration(parent, name);//searchVariable(getParent(), getAncestorWithScope().getScope(), name);
		
		closure = closure != null ? closure.getOriginalDeclaration() : null;
		
		//Parameter param = getParentMethod().getParameter(name);
		
		if (closure instanceof ClosureDeclaration)
		{
			return (ClosureDeclaration)closure;
		}
		else if (closure != null && closure.getTypeObject() instanceof FunctionType)
		{
			return ((FunctionType)closure.getTypeObject()).closure;
		}
		
		return null;
	}

	private ClosureDeclaration getClosureDeclaration()
	{
		VariableDeclaration declaration = getDeclaration();
		
		if (declaration instanceof ClosureDeclaration)
		{
			return (ClosureDeclaration)declaration;
		}
		
		return null;
	}
	
	public boolean isClosureCall()
	{
		return getClosureDeclaration() != null;
	}
	
	public FlatMethodDeclaration getFlatMethod()
	{
		return getCallableDeclaration() instanceof FlatMethodDeclaration ? (FlatMethodDeclaration)getCallableDeclaration() : null;
	}
	
	public boolean isSuperCall()
	{
		return (getParent() instanceof Accessible == false || !((Accessible)getParent()).doesAccess()) && getFlatMethod() != null && getFlatMethod().isSuperCallFrom(getParentClass());
	}
	
	/**
	 * Get the name of the object reference identifier for the given
	 * MethodCall's method node. Static methods return
	 * "__static__ClassName" and non-static methods return "this".
	 * The call cannot be that of an external method.
	 * 
	 * @return The name of the object reference identifier.
	 */
	public String getObjectReferenceIdentifier()
	{
		return getObjectReferenceIdentifier(getInferredDeclaration());
	}
	
	/**
	 * Get the Value that the method was called with for the given
	 * MethodCall's method node, if it was not called with a specific
	 * object. Static methods return "__static__ClassName" and non-static
	 * methods return "this". The call cannot be that of an external
	 * method.
	 * 
	 * @return The Value that the method was called with.
	 */
	public Value getObjectReferenceValue()
	{
		return getObjectReferenceNode(getInferredDeclaration());
	}
	
	/**
	 * @see Identifier#getReferenceNode()
	 */
	@Override
	public Accessible getReferenceNode()
	{
		/*if (getFlatMethod() instanceof ArrayAccessorMethod)
		{
			Accessible a = super.getReferenceNode();//.getReferenceNode()
			
			while (a instanceof MethodCall && ((MethodCall)a).getFlatMethod() instanceof ArrayAccessorMethod)
			{
				a = a.getReferenceNode();
			}
			
			return a.getReferenceNode();
		}*/
		
		Accessible ref = super.getReferenceNode();
		
		if (ref.getParent() instanceof Instantiation)
		{
			return (Instantiation)ref.getParent();
		}
		
		return ref;
	}
	
	/*public Accessible getAccessingNode()
	{
		Accessible a = super.getAccessingNode();
		
		if (a instanceof Instantiation)
		{
			return null;
		}
		
		return a;
	}*/
	
	/**
	 * Get the Parameter that the given argument represents.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void run(int a, int b, int c)
	 * {
	 * 	...
	 * }
	 * 
	 * run(432, 1, 5);</pre></blockquote>
	 * If you were to call getCorrespondingParameter(1) on the method
	 * call above, you would receive the b Parameter.
	 * 
	 * @param argument The argument to get the corresponding parameter
	 * 		from.
	 * @return The Parameter that represents the given argument.
	 */
	public Value getCorrespondingParameter(Value argument)
	{
		Value[] args = getArgumentList().getArgumentsInOrder();
		
		for (int i = 0; i < args.length; i++)
		{
			if (args[i] == argument)
			{
				return getCorrespondingParameter(i);
			}
		}
		
		return null;
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
	 * }
	 * 
	 * run(432, 1, 5);</pre></blockquote>
	 * If you were to call getCorrespondingParameter(2) on the method
	 * call above, you would receive the c Parameter.
	 * 
	 * @param argIndex The index of the argument to get the corresponding
	 * 		parameter from.
	 * @return The Parameter at the given index.
	 */
	public Value getCorrespondingParameter(int argIndex)
	{
		return getInferredDeclaration().getParameterList().getParameter(argIndex);
	}
	
	@Override
	public String generateGenericType()
	{
		StringBuilder builder = new StringBuilder();
		
		GenericTypeArgumentList args = getGenericTypeArgumentList();
		
		if (args.getNumVisibleChildren() > 0)
		{
			builder.append(GenericCompatible.GENERIC_START);
			
			for (int i = 0; i < args.getNumVisibleChildren(); i++)
			{
				if (i > 0)
				{
					builder.append(", ");
				}
				
				getIntelligentGenericTypeArgument(i).generateFlatInput(builder);
			}
			
			builder.append(GenericCompatible.GENERIC_END);
		}
		else
		{
			return super.generateGenericType();
		}
		
		return builder.toString();
	}
	
	@Override
	public byte getDataType(boolean checkGeneric)
	{
		return getDataType(checkGeneric, true);
	}

	public byte getDataType(boolean checkGeneric, boolean checkCast)
	{
		/*if (isPrimitiveGenericType())
		{
			return Value.POINTER;
		}*/
		
		return super.getDataType(checkGeneric, checkCast);
	}
	
	@Override
	public Accessible getCArgumentReferenceContext()
	{
		return this;
	}
	
	@Override
	public GenericTypeParameterList getGenericTypeParameterDeclaration()
	{
		GenericCompatible gen = getGenericCompatibleDeclaration(false);
		
		if (gen == null || !(gen instanceof Value))
		{
			return null;
		}
		
		return ((Value)gen).getGenericTypeParameterDeclaration();
	}
	
	public GenericCompatible getGenericCompatibleDeclaration()
	{
		return getGenericCompatibleDeclaration(true);
	}
	
	public Variable getGenericCompatible()
	{
		return getGenericCompatible(true);
	}
	
	/**
	 * 
	 * 
	 * @param throwException Whether or not to throw an exception if a
	 * 		generic type cannot be found.
	 * @return The 
	 */
	private GenericCompatible getGenericCompatibleDeclaration(boolean throwException)
	{
		return getGenericCompatible(throwException).getDeclaration();
	}
	
	private Variable getGenericCompatible(boolean throwException)
	{
		Node last = getLastAncestorOfType(new Class[] { MethodCallArgumentList.class, Variable.class, Instantiation.class }, false);
		
		if (last instanceof Variable)
		{
			Variable var = (Variable)last;
			
			if (var.getDeclaration() instanceof MutatorMethod)
			{
				return var;//.getDeclaringClass().getField(var.getName());
			}
		}
		
		Assignment assign = null;
		
		if (getParent() instanceof Instantiation && getParent().getParent() instanceof Assignment)
		{
			assign = (Assignment)getParent().getParent();
		}
		if (assign != null)
		{
			Value val = assign.getAssigneeNode();
			
			if (val instanceof Variable)
			{
				return ((Variable)val);//.getDeclaration();
			}
		}
		
		Accessible identifier = getReferenceNode();
		
		if (identifier instanceof Variable)
		{
			/*VariableDeclaration decl = ((Variable)identifier).getDeclaration();
			
			if (decl instanceof Parameter && ((Parameter)decl).isObjectReference())
			{
				return decl.getTypeClass();
			}*/
			
			return (Variable)identifier;//decl;
		}
		
		if (throwException)
		{
			SyntaxMessage.error("Unable to determine generic type declaration for method call '" + getName() + "'", this);
		}
		
		return null;
	}
	
	@Override
	public String getGenericReturnType(boolean checkCast)
	{
		GenericTypeParameter param = getGenericTypeParameter();
		GenericTypeArgument arg = param.getCorrespondingArgument(this/*.getReferenceNode()*/.toValue());
		
		if (arg == null || arg.isGenericType())
		{
			return param.getDefaultType();
		}
		
		return arg.getType();
		
		/*VariableDeclaration method  = getMethodDeclaration();
		GenericCompatible   generic = getGenericCompatibleDeclaration();
		
		if (method.isGenericType())
		{
			GenericTypeArgument arg = generic.getGenericTypeArgumentInstance(method.getType(), this, false);
			
			if (arg != null)
			{
				return arg.getGenericReturnType();
			}
			else if (getParentMethod().containsGenericTypeParameter(method.getType()))
			{
				return getParentMethod().getGenericTypeParameter(method.getType()).getDefaultType();
			}
			else
			{
				SyntaxMessage.error("Invalid generic type '" + method.getType() + "'", this);
			}
		}
		
		return super.getGenericReturnType();*/
	}
	
	public Value[] getTypes()
	{
		return getMethodDeclaration().getTypes();
	}
	
	@Override
	public GenericCompatible getContext()
	{
		return getReferenceNode().getContext();
	}
	
	@Override
	public Value getOriginalGenericType()
	{
		VariableDeclaration decl = getMethodDeclaration();
		
		if (decl instanceof FlatMethodDeclaration)
		{
			FlatMethodDeclaration method = (FlatMethodDeclaration)decl;
			
			if (method.doesOverride())
			{
				VirtualMethodDeclaration virtual = method.getVirtualMethod();
				
				if (virtual.isGenericType() || virtual.getGenericTypeArgumentList().getNumVisibleChildren() > 0)
				{
					return virtual;
				}
			}
		}
		
		return super.getOriginalGenericType();
	}
	
	/**
	 * Decode the given statement into a MethodCall instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * To determine whether or not a method is called externally,
	 * refer to {@link #isExternal()} for more details on what an
	 * external call looks like.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>methodName(5, varName, methodThatReturnsAValue(), "arg1")</li>
	 * 	<li>externalFile.cFunctionName()</li>
	 * 	<li>methodName('q', 5 * (2 / 3))</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		MethodCall instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a MethodCall.
	 */
	public static MethodCall decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		return decodeStatement(parent, statement, location, require, true);
	}
	
	/**
	 * Decode the given statement into a MethodCall instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * To determine whether or not a method is called externally,
	 * refer to {@link #isExternal()} for more details on what an
	 * external call looks like.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>methodName(5, varName, methodThatReturnsAValue(), "arg1")</li>
	 * 	<li>externalFile.cFunctionName()</li>
	 * 	<li>methodName('q', 5 * (2 / 3))</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		MethodCall instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param validateAccess Whether or not to check if method call can be
	 * 		accessed legally.
	 * @return The generated node, if it was possible to translated it
	 * 		into a MethodCall.
	 */
	public static MethodCall decodeStatement(Node parent, String statement, Location location, boolean require, boolean validateAccess)
	{
		return decodeStatement(parent, statement, location, require, validateAccess, null);
	}
	
	/**
	 * Decode the given statement into a MethodCall instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * To determine whether or not a method is called externally,
	 * refer to {@link #isExternal()} for more details on what an
	 * external call looks like.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>methodName(5, varName, methodThatReturnsAValue(), "arg1")</li>
	 * 	<li>externalFile.cFunctionName()</li>
	 * 	<li>methodName('q', 5 * (2 / 3))</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		MethodCall instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param validateAccess Whether or not to check if method call can be
	 * 		accessed legally.
	 * @return The generated node, if it was possible to translated it
	 * 		into a MethodCall.
	 */
	public static MethodCall decodeStatement(Node parent, String statement, Location location, boolean require, boolean validateAccess, CallableMethod callableMethod)
	{
		SyntaxUtils.StatementLiteralNameData[] statementLiteralNameData = SyntaxUtils.getStatementLiteralNameData(statement);

		for (SyntaxUtils.StatementLiteralNameData data : statementLiteralNameData) {
			statement = statement.replace('`' + data.literalNameData.literalName + '`', data.literalNameData.validName);
		}

		String[][] modifierData = SyntaxTree.getPrecedingModifiers(statement, parent, location, 0, 1);

		if (modifierData != null) {
			statement = modifierData[0][0];
		}

		boolean await = false;

		if (statement.endsWith("$")) {
			await = true;
			statement = statement.substring(0, statement.length() - 1);
		}

		if (SyntaxUtils.isMethodCall(statement))
		{
			MethodCall n  = new MethodCall(parent, location);

			if (await) {
				n.parseModifier("await");
			}

			if (modifierData != null) {
				if (!Arrays.stream(modifierData[1]).allMatch(n::parseModifier)) {
					return null;
				}
			}
			
			Bounds genericBounds = StringUtils.findContentBoundsWithin(statement, VariableDeclaration.GENERIC_START, VariableDeclaration.GENERIC_END, 0, false, '-');
			
			if (genericBounds.isValid())
			{
				String params = genericBounds.extractString(statement);
				genericBounds = StringUtils.findContentBoundsWithin(statement, VariableDeclaration.GENERIC_START, VariableDeclaration.GENERIC_END, 0, true, '-');
				
				statement = genericBounds.trimString(statement);
				
				n.decodeGenericTypeArguments(params, n.getMethodGenericTypeArgumentList());
			}
			
			Bounds bounds = SyntaxUtils.findInnerParenthesesBounds(n, statement);

			MethodData data = new MethodData();
			
			if (!n.decodeMethodName(statement, bounds, data, require))
			{
				return null;
			}
			
			VariableDeclaration temp = null;
			
			if (callableMethod == null)
			{
				temp = new VariableDeclaration(null, Location.INVALID);
				temp.setName(data.name);
				n.setDeclaration(temp);
			}
			else
			{
				n.setDeclaration((VariableDeclaration)callableMethod);
			}
			
			boolean skipArgumentChecks = callableMethod != null && (callableMethod instanceof PropertyMethod || parent.getParentClass().isPropertyTrue("functionMap"));

			if (!n.decodeArguments(statement, bounds, require) || !n.deduceMethodCallGenericArguments())
			{
				return null;
			}
			
			if (callableMethod == null)
			{
				callableMethod = (CallableMethod)n.searchMethodDeclaration(data.name);
			}
			
			n.setDeclaration((VariableDeclaration)callableMethod);
			
			CallableMethod method = n.getInferredDeclaration();
			
			if (method == null)
			{
				if (require)
				{
					n.searchMethodDeclaration(data.name);
					SyntaxMessage.error("Undeclared method '" + temp.getName() + "'", n);
				}
				
				return null;
			}
			
			n.setMethodCallGenericArgumentTypes();
			n.addDefaultGenericTypeArguments();
			
			if (!skipArgumentChecks && !n.validateArguments(n.getFileDeclaration(), n.getLocationIn(), require))
			{
				n.validateArguments(n.getFileDeclaration(), n.getLocationIn(), require);
				SyntaxMessage.queryError("Invalid arguments passed in function call '" + statement + "'", n, require);
				
				return null;
			}
			
			if (validateAccess)
			{
				n.validateAccess(method);
			}
			
			for (int i = 0; i < n.getArgumentList().getNumVisibleChildren(); i++)
			{
				n.getArgumentList().getVisibleChild(i).onAfterDecoded();
			}
			
			n.parseGenericTypeArguments();
			
			return n;
		}
		
		return null;
	}
	
	public void parseGenericTypeArguments()
	{
		String type = generateFlatType(this).toString();
		
		int index = type.indexOf('<');
		
		if (index > 0)
		{
			importFlatType(getFileDeclaration(), this);
			
			genericTypeArgumentList = new GenericTypeArgumentList(this, Location.INVALID);
			decodeGenericTypeArguments(type.substring(index + 1, type.length() - 1), genericTypeArgumentList);
		}
	}
	
	@Override
	public GenericTypeArgument getGenericTypeArgumentFromParameter(GenericTypeParameter type)
	{
		if (getFlatMethod() != null)
		{
			GenericTypeParameterList params = getFlatMethod().getMethodGenericTypeParameterDeclaration();
			
			int index = params.getParameterIndex(type.getName());
			
			if (index >= 0 && index < getMethodGenericTypeArgumentList().getNumVisibleChildren())
			{
				return getMethodGenericTypeArgumentList().getVisibleChild(index);
			}
		}
		
		return super.getGenericTypeArgumentFromParameter(type);
	}
	
	@Override
	public GenericTypeParameter getGenericTypeParameter(boolean checkArray)
	{
		if (getFlatMethod() != null)
		{
			GenericTypeParameterList params = getFlatMethod().getMethodGenericTypeParameterDeclaration();
			
			if (params.containsParameter(getType()))
			{
				return params.getParameter(getType());
			}
		}
		
		return super.getGenericTypeParameter(checkArray);
	}
	
	/**
	 * Decode the name of the method call. Check that it is all up to
	 * flying squid.
	 * 
	 * @param statement The method call statement.
	 * @param bounds The bounds of the arguments.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return Whether or not the method name decoded correctly.
	 */
	private boolean decodeMethodName(String statement, Bounds bounds, MethodData data, boolean require)
	{
		// Parenthesis index
		int    parenIndex = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getStart() - 1, -1);
		int    nameEnd    = StringUtils.findNextNonWhitespaceIndex(statement, parenIndex - 1, -1) + 1;
		
		String methodCall = statement.substring(0, nameEnd);
		
		try
		{
			String error = iterateWords(methodCall, Patterns.IDENTIFIER_BOUNDARIES, data, require).error;
			
			if (error != null)
			{
				return false;
			}
		}
		catch (SyntaxErrorException e)
		{
			if (!require)
			{
				return false;
			}
			
			throw e;
		}
		
		return true;
	}
	
	/**
	 * Make sure that the method call is authorized to be made. Check if
	 * the method is visible and that it is referenced in the correct
	 * context. (e.g. Accessing a non-static method within a static
	 * context)
	 * 
	 * @param method The MethodDeclaration that this method call is
	 * 		calling.
	 */
	private void validateAccess(CallableMethod method)
	{
		if (method instanceof ClosureDeclaration)
		{
			return;
		}
		
		if (!SyntaxUtils.isVisible(getParentClass(), ((MethodDeclaration)method)))
		{
			SyntaxUtils.isVisible(getParentClass(), ((MethodDeclaration)method));
			SyntaxMessage.error("Method '" + method.getName() + "' of class '" + method.getParentClass().getClassLocation() + "' is not visible from class '" + getParentClass().getClassLocation() + "'", this);
		}
		if (isAccessedWithinStaticContext() && !method.isStatic())
		{
			isAccessedWithinStaticContext();
			SyntaxMessage.error("Method '" + method.getName() + "' cannot be called from within a static context", this);
		}
	}
	
	/**
	 * Decode all of the given arguments to the method call. Return
	 * true if it was successfully decoded.
	 * 
	 * @param statement The method call statement.
	 * @param bounds The bounds of the arguments.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return Whether or not the arguments decoded successfully.
	 */
	public boolean decodeArguments(String statement, Bounds bounds, boolean require)
	{
		String argumentList = statement.substring(bounds.getStart(), bounds.getEnd());
		
		if (argumentList.length() <= 0)
		{
			return true;
		}
		
		String arguments[] = StringUtils.splitCommas(argumentList, 1);
		
		Location argsLocation = new Location(getLocationIn());
		argsLocation.addBounds(bounds.getStart(), bounds.getEnd());
		
		return addArguments(arguments, argsLocation, require);
	}
	
	public static class Pair<A, B>
	{
		public A a;
		public B b;
		
		public Pair() {}
		
		public Pair(A a, B b)
		{
			this.a = a;
			this.b = b;
		}
	}
	
	private Pair<GenericTypeParameter, Value> recursiveGenericParamSearch(Value parameter, Value corresponding, GenericTypeParameter target)
	{
		GenericTypeParameter param = parameter.getGenericTypeParameter();
		
		if (param != null && param.getType().equals(target.getType()))
		{
			Value required = parameter;
			
			if (required instanceof GenericTypeArgument)
			{
				required = ((GenericTypeArgument)required).getTangibleNode();
				
				corresponding = SyntaxUtils.performWalk(corresponding, corresponding.getTypeClass(), required.getTypeClass(), param);
			}
			
			return new Pair<>(param, corresponding);
		}
		else
		{
			GenericTypeArgumentList params = parameter.getGenericTypeArgumentList();
//			GenericTypeArgumentList args = corresponding.getGenericTypeArgumentList();
			
			// TODO: needs to accommodate for multi-param arg thing e.g. pairilize<A, Out>(ValueDistance<A, A> other, ...)
			for (int i = 0; i < /*Math.min(args.getNumVisibleChildren(), */params.getNumVisibleChildren()/*)*/; i++)
			{
//				Value arg = args.getVisibleChild(i);
				
				Pair<GenericTypeParameter, Value> p = recursiveGenericParamSearch(params.getVisibleChild(i), /*arg*/corresponding, target);
				
				if (p != null)
				{
					return p;
				}
			}
		}
		
		return null;
	}
	
	public boolean finishParsingLambdas()
	{
		for (Value arg : getArgumentList())
		{
			if (arg instanceof LambdaExpression)
			{
				if (((LambdaExpression)arg).generateClosure() == null)
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void setMethodCallGenericArgumentTypes()
	{
		FlatMethodDeclaration flatMethod = getFlatMethod();
		
		if (flatMethod != null)
		{
			GenericTypeParameterList genParams = flatMethod.getMethodGenericTypeParameterDeclaration();
			FlatParameterList params = flatMethod.getParameterList();
			
			Value[] args = getArgumentList().getArgumentsInOrder();
			Value[] types = genParams.getTypes();
			
			// Find least common denominator type for nth method generic type argument
			// e.g.   public reduce<Out>(func(Out, Type, Int, List) -> Out, Out initialValue) -> Out
			//        
			//        let x = reduce({ _.hashCodeLong + 5 }, "asdf")
			//        
			//        when searching for Out's type, it checks lambda's return type of Int and
			//        the type of initialValue which is String. The common type between those is Object.
			for (int n = 0; n < genParams.getNumVisibleChildren(); n++)
			{
				Value common = types[n];
				
				for (int i = 0; i < Math.min(args.length, params.getNumParameters()); i++)
				{
					if (args[i] instanceof LambdaExpression == false)
					{
						Pair<GenericTypeParameter, Value> pair = recursiveGenericParamSearch(params.getParameter(i), args[i], genParams.getParameter(n));
						
						if (pair != null)
						{
							if (common == types[n])
							{
								common = pair.b.getReturnedNode();
								common = common instanceof ClosureVariable ? ((FunctionType)common.getTypeObject()).closure : common;
							}
							else
							{
								common = SyntaxUtils.getValueInCommon(common, pair.b.getReturnedNode());
							}
						}
					}
				}
				
				if (common != types[n])
				{
					if (getMethodGenericTypeArgumentList().getNumVisibleChildren() <= n)
					{
						GenericTypeArgument arg = new GenericTypeArgument(getMethodGenericTypeArgumentList(), Location.INVALID);
						arg.setType(common);
						
						getMethodGenericTypeArgumentList().addChild(arg);
					}
					else
					{
						GenericTypeArgument arg = getMethodGenericTypeArgumentList().getVisibleChild(n);
						arg.setType(SyntaxUtils.getValueInCommon(arg, common));
					}
				}
			}
		}
	}
	
	public boolean deduceMethodCallGenericArguments()
	{
		setMethodCallGenericArgumentTypes();
		
		if (!finishParsingLambdas())
		{
			return false;
		}
		
		setMethodCallGenericArgumentTypes();
		
		return true;
	}
	
	/**
	 * @see Node#interactWord(java.lang.String, Bounds, java.lang.String, java.lang.String, Node.ExtraData)
	 */
	@Override
	public boolean interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		MethodData data = (MethodData)extra;
		
		if (extra.isLastWord() && leftDelimiter.length() == 0)
		{
			data.name = word;
		}
		else
		{
			String characters = null;
			
			if (leftDelimiter.length() > 0)
			{
				characters = leftDelimiter;
			}
			else if (rightDelimiter.length() > 0)
			{
				characters = rightDelimiter;
			}
			else
			{
				SyntaxMessage.error("Could not decode method name", this);
			}
			
			extra.error = "Unknown characters '" + characters + "'";
			
			SyntaxMessage.queryError(extra.error, this, extra.require);
		}

		return true;
	}
	
	/**
	 * Check to see if the arguments passed to the method call
	 * fulfills the required parameters of the method declaration.
	 * 
	 * @param fileDeclaration The FileDeclaration that this method call is within.
	 * @param location The location of the arguments that are being
	 * 		passed.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return True if the method call's arguments fulfill the
	 * 		requirements of the Method declaration's parameters.
	 */
	private boolean validateArguments(FileDeclaration fileDeclaration, Location location, boolean require)
	{
		CallableMethod methodDeclaration = getInferredDeclaration();
		
		if (methodDeclaration == null)
		{
			return SyntaxMessage.queryError("Incompatible arguments for method call '" + getName() + "'", this, require);
		}
		
		ParameterList parameters = methodDeclaration.getParameterList();
		MethodCallArgumentList arguments = getArgumentList();
		
		int argCount = arguments.getNumVisibleChildren();
		
		if (argCount < parameters.getNumRequiredParameters())
		{
			SyntaxMessage.error("Too few arguments to method call '" + getName() + "'", this);
		}
		else if (argCount > parameters.getNumVisibleChildren())
		{
			SyntaxMessage.error("Too many arguments to method call '" + getName() + "'", this);
		}

		if (parameters.containsNamedParameters()) {
			int startIndex = arguments.getFirstArgumentNameIndex() + 1;

			if (startIndex == 0) {
				startIndex = parameters.getFirstNamedParameterIndex();
			}

			for (
				int i = startIndex;
				i < arguments.getNumVisibleChildren();
				i++
			) {
				if (arguments.getArgumentName(i) == null) {
					Value arg = arguments.getVisibleChild(i);

					if (arg instanceof Variable) {
						Variable v = (Variable) arg;

						if (!v.doesAccess()) {
							arguments.setArgumentName(i, v.getName());
						}
					}
				}
			}
		}
		
		Value[] order = getArgumentList().getArgumentsInOrder();
		int position = 0;
		
		for (int i = 0; i < order.length; i++)
		{
			if (parameters.getParameter(i) instanceof Parameter)
			{
				Parameter param = (Parameter)parameters.getParameter(i);
				Value arg = order[i];
				
				if (arg instanceof DefaultArgument == false && getArgumentList().getArgumentName(position++) == null)
				{
					if (param.requireNamed)
					{
						SyntaxMessage.error("Argument '" + arg.generateFlatInput() + "' requires name '" + param.getName() + "'. E.g.: '" + param.getName() + ": " + arg.generateFlatInput() + "'", arg);
					}
				}
			}
		}
		
		if (!methodDeclaration.areCompatibleParameterTypes(this, methodDeclaration instanceof FlatMethodDeclaration ? arguments.getTypes((FlatMethodDeclaration)methodDeclaration) : arguments.getTypes()))
		{
			return false;
		}
		
		checkPrimitiveMethodConversion(methodDeclaration);
		
		return true;
	}
	
	private void checkPrimitiveMethodConversion(CallableMethod methodDeclaration)
	{
		if (!Flat.PRIMITIVE_OVERLOADS) {
			return;
		}
		if (methodDeclaration instanceof FlatMethodDeclaration)
		{
			FlatMethodDeclaration method = (FlatMethodDeclaration)methodDeclaration;
			
			FlatMethodDeclaration converted = method.getConvertedPrimitiveMethod(this);
			
			if (converted != null)
			{
				GenericTypeParameterList convertedParams = converted.getMethodGenericTypeParameterDeclaration();
				GenericTypeParameterList currentParams = method.getMethodGenericTypeParameterDeclaration();
				
				if (converted instanceof Constructor)
				{
					convertedParams = converted.getParentClass().getGenericTypeParameterDeclaration();
					currentParams = method.getParentClass().getGenericTypeParameterDeclaration();
				}
					
				declaration = converted;
				
				GenericTypeArgumentList args = getMethodGenericTypeArgumentList();
				
				if (convertedParams.getNumVisibleChildren() == 0)
				{
					args.slaughterEveryLastVisibleChild();
				}
				else if (convertedParams.getNumParameters() != args.getNumVisibleChildren())
				{
					GenericTypeArgumentList newArgs = new GenericTypeArgumentList(this, args.getLocationIn());
					
					int position = 0;
					
					for (int i = 0; i < currentParams.getNumParameters() && position < convertedParams.getNumParameters(); i++)
					{
						if (currentParams.getVisibleChild(i).getType().equals(convertedParams.getVisibleChild(position).getType()))
						{
							if (args.getNumVisibleChildren() > i) {
								newArgs.addChild(args.getVisibleChild(i));
								position++;
							}
						}
					}
					
					args.replaceWith(newArgs);
				}
			}
		}
	}
	
	/**
	 * Decode and add the arguments given within the array into Nodes that
	 * are translatable into C.
	 * 
	 * @param arguments The arguments to decode and then add.
	 * @param location The location of the method call in the source code.
	 */
	private boolean addArguments(String arguments[], Location location, boolean require)
	{
		MethodCallArgumentList parent = getArgumentList();
		
		boolean requiresName;
		Value previous = null;
		
		for (int i = 0; i < arguments.length; i++)
		{
			String argument = arguments[i];
			
			requiresName = false;
			Matcher named = Patterns.NAMED_ARGUMENT.matcher(argument);
			boolean usedNamedArgs = parent.getFirstArgumentNameIndex() >= 0;
			
			if (named.find() && named.start() == 0)
			{
				String name = named.group();
				name = name.substring(0, name.length() - 1).trim();
				
				parent.setArgumentName(i, name);
				
				argument = argument.substring(named.group().length()).trim();
			}
			else if (previous != null && usedNamedArgs)
			{
				requiresName = true;
			}
			
			if (argument.length() > 0)
			{
				Node arg = LambdaExpression.decodeStatement(parent, argument, location, false);
				
				if (arg == null)
				{
					arg = Literal.decodeStatement(parent, argument, location, true, true);
					
					if (arg == null)
					{
						arg = TernaryOperation.decodeStatement(parent, argument, location, false);
					
						if (arg == null)
						{
							arg = BinaryOperation.decodeStatement(parent, argument, location, false);
							
							if (arg == null)
							{
								arg = SyntaxTree.decodeScopeContents(parent, argument, location, false);
								
								if (arg == null)
								{
									arg = Array.decodeStatement(parent, argument, location, false);
									
									if (parent.isWithinExternalContext())
									{
										arg = Literal.decodeStatement(parent, argument, location, true);
									}
									
									if (arg == null)
									{
										if (require)
										{
											validateCharacters(parent, argument, location);
											
											SyntaxMessage.error("Could not decode argument '" + argument + "'", parent, location);
										}
										
										return false;
									}
								}
							}
						}
					}
				}
				
				//arg = new MethodCallArgument(parent, location, (Value)arg);
				
				parent.addChild(arg);
				previous = (Value)arg;
				
				if (arg instanceof Assignment)
				{
					arg.onAfterDecoded();
				}
				if (usedNamedArgs && getArgumentList().getArgumentName(i) == null && arg instanceof Variable) {
					Variable v = (Variable)arg;

					if (!v.doesAccess()) {
						requiresName = false;
						parent.setArgumentName(i, v.getName());
					}
				}
				
				if (requiresName)
				{
					SyntaxMessage.error("Once a named argument is used, all of the following arguments must be named as well.", arg);
				}
			}
			else
			{
				SyntaxMessage.error("Expected an argument definition", this);
			}
		}
		
		return true;
	}
	
	/**
	 * Validate that the characters in the given argument are valid
	 * identifier-type characters with no symbols.
	 * 
	 * @param parent The MethodCallArgumentList Node that the argument
	 * 		was being added to.
	 * @param argument The argument String to validate.
	 * @param location The location of the argument in the source text.
	 */
	private void validateCharacters(Node parent, String argument, Location location)
	{
		String prefix  = StringUtils.findGroupedSymbols(argument, 0);
		String postfix = StringUtils.findGroupedSymbols(argument, argument.length() - 1, -1);
		
		if (prefix.length() > 0)
		{
			SyntaxMessage.error("Unknown symbol" + (prefix.length() > 1 ? "s" : "") + " '" + prefix + "'", parent, location);
		}
		else if (postfix.length() > 0)
		{
			SyntaxMessage.error("Unknown symbol" + (postfix.length() > 1 ? "s" : "") + " '" + postfix + "'", parent, location);
		}
	}
	
	/**
	 * Get the Flat input representation of this Method Call node.
	 * That is, the String that the decoder method decoded to attain
	 * this node.
	 * 
	 * @param outputChildren Whether or not to output the children of the
	 * 		children of the Node as well.
	 * @return The String representing the method call in Flat syntax.
	 */
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		if (getAnnotations() != null) {
			getAnnotations().forEach(annotation -> {
				annotation.generateFlatInput(builder).append(" ");
			});
		}

		builder.append(getName());
		
		if (declaration instanceof AccessorMethod)
		{
			
		}
		else if (declaration instanceof MutatorMethod)
		{
			builder.append(" = ").append(getArgumentList().generateFlatInput());
		}
		else
		{
			GenericTypeArgumentList args = getMethodGenericTypeArgumentList();
			
			if (args.getNumVisibleChildren() > 0)
			{
				builder.append("<");
				
				for (int i = 0; i < args.getNumVisibleChildren(); i++)
				{
					if (i > 0)
					{
						builder.append(", ");
					}
					
					args.getVisibleChild(i).generateFlatInput(builder, true, this);
				}
				
				builder.append(">");
			}
			
			builder.append('(').append(getArgumentList().generateFlatInput()).append(')');
		}
		
		if (outputChildren)
		{
			generateAccessedNode(builder, safeNavigation);
		}
		
		return builder;
	}
	
	public void addDefaultGenericTypeArguments()
	{
		if (getFlatMethod() != null)
		{
			FlatMethodDeclaration decl = getFlatMethod();
			
			GenericTypeParameterList params = decl.getMethodGenericTypeParameterDeclaration();
			GenericTypeArgumentList args = getMethodGenericTypeArgumentList();
			
			if (parent instanceof Instantiation)
			{
				params = decl.getGenericTypeParameterDeclaration();
			}
			
			addCommonDefaultGenericParameters(decl, params, args);
		}
	}
	
	private void addCommonDefaultGenericParameters(FlatMethodDeclaration decl, GenericTypeParameterList params, GenericTypeArgumentList args)
	{
		for (int i = args.getNumVisibleChildren(); i < params.getNumVisibleChildren(); i++)
		{
			GenericTypeArgument arg = new GenericTypeArgument(args, getLocationIn().asNew());
			
			GenericTypeParameter param = params.getParameter(i);
			
			Value common = null;
			
			for (int n = 0; n < decl.getParameterList().getNumParameters(); n++)
			{
				Parameter p = decl.getParameterList().getParameter(n);
				
				if (p instanceof ClosureDeclaration)
				{
					Value v = (Value)getArgumentList().getVisibleChild(n);
					v = v instanceof ClosureVariable ? ((FunctionType)v.getTypeObject()).closure : v;
					
					common = common == null ? v : SyntaxUtils.getTypeInCommon(common, v);
				}
			}
			
			if (common == null)
			{
				arg.setTypeValue(param.getDefaultType());//.getFlatType(this));
			}
			else
			{
				arg.setType(common);
			}
			
			args.addChild(arg, args);
		}
	}
	
	public boolean isCallingClosureVariable()
	{
		return declaration instanceof FirstClassClosureDeclaration && ((FirstClassClosureDeclaration)declaration).reference instanceof ClosureVariable;
	}
	
	public ClosureVariable getClosureVariable()
	{
		return (ClosureVariable)((FirstClassClosureDeclaration)declaration).reference;
	}
	
	@Override
	public void onAdded(Node parent)
	{
		super.onAdded(parent);
		
		if (isCallingClosureVariable())
		{
			ClosureVariable var = getClosureVariable();
			
			var.declaration.references.add(var);
		}
	}
	
	/**
	 * @see Node#validate(int)
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		Identifier returned  = this;
		
		Accessible reference = returned.getReferenceNode();
		Accessible accessing = returned.getAccessingNode();
		Identifier accessed  = returned.getAccessedNode();
		
		if (isCallingClosureVariable())
		{
			ClosureVariable var = getClosureVariable();
			
			if (var.declaration instanceof FieldDeclaration)
			{
				FieldDeclaration field = (FieldDeclaration)var.declaration;
				
				if (field.getAccessorMethod() != null && !field.getAccessorMethod().isDisabled())
				{
					var.declaration = field.getAccessorMethod();
				}
			}
			
			SyntaxTree.validateNodes(declaration, phase);
		}
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
//			// TODO: Update to never do this
//			if (isPrimitiveGenericType() && !(reference.toValue().getTypeClassLocation().equals("flatlang/datastruct/list/CharArray") || reference.toValue().getTypeClassLocation().equals("flatlang/datastruct/list/IntArray") || reference.toValue().getTypeClassLocation().equals("flatlang/datastruct/list/DoubleArray")))
//			{
//				if (accessed instanceof Variable)
//				{
//					if (accessed.getName().equals("value"))
//					{
//						return result;
//					}
//				}
//				
//				String input = returned.generateFlatInputUntil(returned) + ".value";
//				
//				if (accessed != null)
//				{
//					input += "." + accessed.generateFlatInput();
//				}
//				
//				Identifier value = (Identifier)SyntaxTree.decodeIdentifierAccess(getParent(), input, getLocationIn(), false, false);
//				
//				if (value == null)
//				{
//					return result.errorOccurred();
//				}
//				
//				returned.getParent().replace(returned, value);
//				
//				result.returnedNode = value;
//				
//				return result;
//				
////				setDataType(VALUE);
//			}
			
			if (accessing instanceof Variable || accessing instanceof Literal)
			{
				Value var = accessing.toValue();
				
				if (var.isPrimitiveType() && var.getTypeClass() != null && var.getDataType() != Value.POINTER)
				{
					SearchFilter filter  = new SearchFilter();
					filter.checkAncestor = false;
					filter.checkStatic   = true;
					filter.staticValue   = true;
					
					if (var.getTypeClass().getMethods(getName(), filter).length > 0)
					{
						Value staticCall = SyntaxUtils.replaceWithPrimitiveFacade(this);
						
						if (staticCall == null)
						{
							return result.errorOccurred();
						}
						
						result.returnedNode = staticCall;
					}
					else if (!(var.getParent() instanceof Instantiation))
					{
						result.returnedNode = SyntaxUtils.replaceWithAutoboxPrimitive(var);
					}
					
					return result;
				}
//				else if (var.declaration instanceof ClassInstanceDeclaration && getName().equals("isOfType") && getDeclaringClass().getClassLocation().equals("flatlang/meta/Class"))
//				{
//					int j = 5;
//				}
			}
			
		}
		else if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (isCallingClosureVariable() && (getBaseNode() instanceof Assignment == false || !getBaseNode().containsProperty("userMade")))
			{
				ClosureVariable var = getClosureVariable();
				
				Variable v = getAncestorWithScope().getScope().createLocalVariable(var);
				v.setType(var);
				
				Accessible root = getRootAccessNode();
				
				Assignment a = Assignment.generateDefault(root.toValue().parent, getLocationIn());
				a.setProperty("userMade", false);
				a.getAssigneeNodes().addChild(v);
				a.addChild(Literal.decodeStatement(a, "null", getLocationIn(), true, true));

				String flat = "";
				
				if (isAccessed())
				{
					flat = root.generateFlatInputUntil(getAccessingNode()).toString();
				}
				
				flat += (flat.length() > 0 ? "." : "") + getName();
				
//				root.toValue().replaceWith(a);
				Node n = SyntaxTree.decodeScopeContents(getBaseNode(), flat, getLocationIn(), true);
				
				a.setProperty("methodCall", this);
				
				a.getAssignmentNode().replaceWith(n);//root.toValue());
				root.toValue().replaceWith(a);
				
				root.toValue().parent = a.parent;
				
				((FirstClassClosureDeclaration)getCallableMethodBase()).reference = a.getAssignedNode();
				
				result.returnedNode = a;
				
				return result;
			}
		}
		
		return result;
	}

	@Override
	public boolean onAfterDecoded()
	{
		if (getProgram() != null && getProgram().getPhase() > SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			genericParameter = searchGenericTypeParameter(0);
		}

		return super.onAfterDecoded();
	}
	
	@Override
	public StringBuilder generateFlatType(StringBuilder builder, Value context, boolean checkArray, boolean defaultGeneric)
	{
		if (declaration instanceof FirstClassClosureDeclaration && declaration.parent instanceof GenericTypeArgument)
		{
			FirstClassClosureDeclaration f = (FirstClassClosureDeclaration)declaration;
			
			return f.generateFlatInput(builder);
		}
		
		return super.generateFlatType(builder, context, checkArray, defaultGeneric);
	}
	
	public CallableMethod getRootDeclaration()
	{
		if (getDeclaration() instanceof FlatMethodDeclaration)
		{
			return ((FlatMethodDeclaration)getDeclaration()).getRootDeclaration();
		}
		
		return (CallableMethod)getDeclaration();
	}
	
	@Override
	public String getName()
	{
		if (declaration instanceof FirstClassClosureDeclaration)
		{
			return ((Identifier)declaration.parent).getName();
		}
		
		return super.getName();
	}
	
	/**
	 * Check to see if a local declaration is required to keep a virtual
	 * method from being called too many times.
	 * 
	 * @return Whether or not a local declaration for a virtual method
	 * 		call is required.
	 */
	private boolean localDeclarationRequired()
	{
		return false;//getInferredDeclaration().isVirtual() && !isVirtualTypeKnown() && isAccessed() && getAccessingNode() instanceof MethodCall;// && !getAccessingNode().isVirtualTypeKnown();
	}

	@Override
	public String getType(boolean checkCast) {
		if (this.isChainNavigation()) {
			return getReferenceNode().toValue().getType(checkCast);
		}

		return super.getType(checkCast);
	}

	@Override
	public boolean isGenericType(boolean checkArray, boolean checkCast) {
		if (this.isChainNavigation()) {
			return getReferenceNode().toValue().isGenericType(checkArray, checkCast);
		}

		return super.isGenericType(checkArray, checkCast);
	}

	@Override
	public GenericTypeArgument getGenericTypeArgument(int index, Node node, boolean require) {
		if (this.isChainNavigation()) {
			return getReferenceNode().toValue().getGenericTypeArgument(index, node, require);
		}

		return super.getGenericTypeArgument(index, node, require);
	}

	@Override
	public GenericTypeArgument[] getGenericTypeArguments(String params) {
		if (this.isChainNavigation()) {
			return getReferenceNode().toValue().getGenericTypeArguments(params);
		}

		return super.getGenericTypeArguments(params);
	}

	@Override
	public GenericTypeArgument getGenericTypeArgumentInstance(String parameterName, Node value, boolean require) {
		if (this.isChainNavigation()) {
			return getReferenceNode().toValue().getGenericTypeArgumentInstance(parameterName, value, require);
		}

		return super.getGenericTypeArgumentInstance(parameterName, value, require);
	}

	@Override
	public String getFlatType(Value context, boolean checkArray, boolean defaultGeneric) {
		if (this.isChainNavigation()) {
			return getReferenceNode().toValue().getFlatType(context, checkArray, defaultGeneric);
		}

		return super.getFlatType(context, checkArray, defaultGeneric);
	}

	@Override
	public Value getFlatTypeValue(Value context) {
		if (this.isChainNavigation()) {
			return getReferenceNode().toValue().getFlatTypeValue(context);
		}

		return super.getFlatTypeValue(context);
	}

	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public MethodCall clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		MethodCall node = new MethodCall(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public MethodCall cloneTo(MethodCall node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link MethodCall} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodCall cloneTo(MethodCall node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	@Override
	public byte getDataType()
	{
		return getCallableMethodBase().getDataType(false);
	}
	
	/**
	 * Test the MethodCall class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	/**
	 * Implementation of the ExtraData for this class.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.29 Aug 28, 2014 at 6:56:45 PM
	 * @version	v0.2.29 Aug 28, 2014 at 6:56:45 PM
	 */
	public static class MethodData extends ExtraData
	{
		private String name;
	}
}