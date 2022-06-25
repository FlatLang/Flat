package net.fathomsoft.nova.tree.lambda;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * {@link Node} extension that represents a lambda expression.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.45 Jun 5, 2014 at 9:00:04 PM
 * @version	v0.2.45 Jun 5, 2016 at 11:43:17 PM
 */
public class LambdaExpression extends IIdentifier
{
	public String[] variables; 
	public Parameter[] parameters;
	
	public static final String OPERATOR = "=>";
	public static final String UNNAMED_PARAMETER = "_";
	
	private static int id = 1;
	
	public ClosureDeclaration closure;
	
	public Value context;
	
	public boolean block, pending;
	
	public String operation;
	
	public FileDeclaration refFile;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public LambdaExpression(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);;
		
		Scope scope = new Scope(this, locationIn.asNew());
		setScope(scope);
	}
	
	@Override
	public Scope getScope()
	{
		return (Scope)getChild(super.getNumDefaultChildren() + 0);
	}
	
	@Override
	public boolean canAccess()
	{
		return false;
	}
	
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	/**
	 * Decode the given statement into a {@link LambdaExpression} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>x -> x + 1</li>
	 * 	<li>(x, i) -> Console.writeLine(x * i)</li>
	 * 	<li>asdf -> asdf.doSomething()</li>
	 * 	<li>{ Console.writeLine("In parameterless lambda") }</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link LambdaExpression} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link LambdaExpression}.
	 */
	public static LambdaExpression decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		return decodeStatement(parent, statement, location, require, null, null);
	}
	
	public static LambdaExpression decodeStatement(Node parent, String statement, Location location, boolean require, ClosureDeclaration closure, Value context)
	{
		String[] variables = null;
		int endingIndex = 0;
		boolean block = false;
		
		if (statement.startsWith("("))
		{
			endingIndex = StringUtils.findEndingMatch(statement, 0, '(', ')') + 1;
			
			variables = StringUtils.splitCommas(statement.substring(1, endingIndex - 1));
			
			if (variables.length == 1 && variables[0].length() == 0)
			{
				variables = new String[0];
			}
		}
		else if (statement.startsWith("{"))
		{
			variables = new String[0];
			
			endingIndex = StringUtils.findEndingMatch(statement, 0, '{', '}');

			block = true;
		}
		else
		{
			variables = new String[] { StringUtils.findNextWord(statement) };
			
			endingIndex = variables[0].length();
		}
		
		LambdaExpression n = new LambdaExpression(parent, location);
		n.variables = variables;
		
		if (endingIndex > 0)
		{
			String operation = null;
			
			if (variables.length > 0 || statement.startsWith("("))
			{
				operation = statement.substring(endingIndex).trim();
			}
			else
			{
				operation = statement.substring(1, endingIndex).trim();
			}
			
			if (operation.startsWith(OPERATOR) || variables.length == 0)
			{
				if (operation.startsWith(OPERATOR))
				{
					operation = operation.substring(OPERATOR.length()).trim();
				}
				
				if (operation.startsWith("{"))
				{
					block = true;
					
					endingIndex = StringUtils.findEndingMatch(operation, 0, '{', '}');
					
					if (endingIndex > 0)
					{
						operation = operation.substring(1, operation.length() - 1).trim();
					}
				}
				
				if (endingIndex > 0)
				{
					if (closure != null || (context = findContext(parent)) != null && (closure = n.findDeclaration(context, variables)) != null)
					{
						Accessible refNode = context != null ? ((Accessible)context).getReferenceNode() : null;
						ClassDeclaration refClass = refNode != null ? refNode.toValue().getTypeClass() : null;
						final FileDeclaration refFile = refClass != null ? refClass.getFileDeclaration() : null;
						
						n.closure = closure;
						n.context = context;
						n.block = block;
						n.operation = operation;
						n.refFile = refFile;
						
						return n;
					}
					
					return null;
				}
			}
		}
		
		if (block)
		{
			n.pending = true;
			
			n.setToInferredType();
			
			return n;
		}
		
		return null;
	}
	
	private void setToInferredType()
	{
		setName("function");
		setType("function(" + parseParameters() + ")");
		
		pending = true;
	}
	
	private String parseParameters()
	{
		String params = "";
		
		if (variables != null)
		{
			parameters = new Parameter[variables.length];
			
			Return r = (Return)parent.getAncestorOfType(Return.class, true);
			
			for (int i = 0; i < variables.length; i++)
			{
				if (SyntaxUtils.isValidIdentifier(variables[i]))
				{
					if (r != null)
					{
						Type type = r.getParentMethod().getTypeObject();
						
						if (type instanceof FunctionType)
						{
							FunctionType func = (FunctionType)type;
							
							ParameterList ps = func.closure.getParameterList();
							
							for (int j = 0; j < ps.getNumParameters(); j++)
							{
								variables[j] = ps.getParameter(j).generateNovaType() + " " + variables[j];
							}
						}
						else
						{
							SyntaxMessage.error("Function '" + r.getParentMethod().getName() + "' does not return a function type", r);
						}
					}
					else
					{
						SyntaxMessage.error("Types must be specified for local lambda expression parameters", this);
					}
				}
				
				parameters[i] = Parameter.decodeStatement(this, variables[i], getLocationIn(), true);
				
				if (i > 0)
				{
					params += ", ";
				}
				
				params += parameters[i].generateNovaType();
			}
		}
		
		return params;
	}
	
	@Override
	public VariableDeclaration searchVariable(Node parent, Scope scope, String name, boolean checkAncestors)
	{
		if (parameters != null)
		{
			Optional<Parameter> param = Arrays.stream(parameters).filter(x -> x != null && x.getName().equals(name)).findFirst();
			
			if (param.isPresent())
			{
				return param.get();
			}
		}
		
		return super.searchVariable(parent, scope, name, checkAncestors);
	}
	
	private void replaceAccessedLocals(Node scopeAncestor, LambdaMethodDeclaration method)
	{
		Node[] vars = method.getScope().getChildrenOfType(Variable.class);
		
		for (Node n : vars)
		{
			Variable v = (Variable)n;
			
			if (v.declaration.getParentMethod() == scopeAncestor || v.declaration.getAncestorOfType(StaticBlock.class) == scopeAncestor)
			{
				v.declaration = method.context.addDeclaration(v.declaration);
			}
		}
	}
	
	public void constructPendingLambda()
	{
		LambdaMethodDeclaration method = generateLambdaMethod(parent);
		
		method.setName("localLambda" + id++);
		
		method.getParentClass().addChild(method);
		
		method.scope = getScope();
		method.getScope().replaceWith(getScope());
		
		if (parameters != null)
		{
			NovaParameterList params = method.getParameterList();
			
			for (int i = 0; i < parameters.length; i++)
			{
				params.addChild(parameters[i]);
			}
		}
		
		if (method.getScope().getNumVisibleChildren() == 1)
		{
			Node child = method.getScope().getVisibleChild(0);
			
			if (child instanceof Value && child instanceof Return == false)
			{
				Return r = new Return(method, child.getLocationIn());
				
				child.replaceWith(r);
				
				r.getReturnValues().addChild(child);
				
				if (method.getType() == null)
				{
					method.setType(r.getReturnedNode());
				}
				else if (!SyntaxUtils.isTypeCompatible(this, method, r))
				{
					SyntaxMessage.error("Lambda expression must return type '" + method.generateNovaType() + "'", r);
				}
			}
		}
		
		addClosureContext(method);
		
		Node scopeAncestor = getParentMethod();
		scopeAncestor = scopeAncestor == null ? getAncestorOfType(StaticBlock.class) : scopeAncestor;
		
		replaceAccessedLocals(scopeAncestor, method);
		
		replaceWith(generateMethodReference(method));
	}
	
	@Override
	public void onStackPopped(Node parent)
	{
		if (parent == this && pending)
		{
			constructPendingLambda();
		}
		
		super.onStackPopped(parent);
	}
	
	public LambdaMethodDeclaration generateLambdaMethod(Node parent)
	{
		return generateLambdaMethod(parent, null);
	}
	
	public LambdaMethodDeclaration generateLambdaMethod(Node p, BodyMethodDeclaration bodyMethod)
	{
		LambdaMethodDeclaration method = new LambdaMethodDeclaration(p.getParent(), p.getLocationIn(), parent.getAncestorWithScopeOrClass().getScope());
		
		NovaMethodDeclaration parentMethod = parent.getParentMethod();
		
		Node ancestor = parent.getAncestorWithScope();
		
		int scopeId = ancestor != null ? ancestor.getScope().getID() : 0;
		
		if (bodyMethod != null)
		{
			bodyMethod.cloneTo(method);
		}
		
		method.getScope().slaughterEveryLastVisibleChild();
		method.getScope().id = scopeId;
		method.uniqueID = scopeId;
		method.isInstance = parentMethod != null && parentMethod.isInstance();//parentMethod.isStatic();
		method.objectReference = parentMethod != null ? parentMethod.objectReference : null;
		method.index = getIndex();//methodCall.getArgumentList().getNumVisibleChildren()
		
		if (context instanceof MethodCall)
		{
			method.methodCall = (MethodCall)context;
		}
		
		NovaParameterList params = method.getParameterList();
		
		if (parentMethod != null)
		{
			params.getReferenceParameter().setType(parent.getParentMethod(true).getParameterList().getReferenceParameter());
		}
		
		method.contextDeclaration = new ClosureContextDeclaration(parent, getLocationIn(), method.context);
		
		return method;
	}
	
	public Closure generateClosure()
	{
		final StringBuilder builder = new StringBuilder();
		
		if (parameters == null)
		{
			final int[] i = new int[] { 0 };
			
			closure.getParameterList().forEach(x ->
			{
				int id = i[0]++;
				
				Value value = closure.getParameterList().getParameter(id).getNovaTypeValue(context);
				
				String type = value.getNovaType(context);
				String name = "";
				
				if (variables.length > id)
				{
					name = variables[id];
				}
				else
				{
					name = "_" + (id + 1);
				}
				
				if (type.endsWith(")"))
				{
					type = name + type.substring(type.indexOf('('));
				}
				
				builder.append(builder.length() > 0 ? ", " : "").append(type);
				
				if (!type.endsWith(")"))
				{
					builder.append(" ").append(name);
				}
				
				getFileDeclaration().addImport(value.getTypeClassLocation());
				
				value.importGenericArgumentTypesTo(getFileDeclaration());
				
				if (value.isGenericType())
				{
					GenericTypeArgument arg = value.getGenericTypeParameter().getCorrespondingArgument(context);
					
					if (arg != null)
					{
						getFileDeclaration().addImport(arg.getTypeClassLocation());
						arg.importGenericArgumentTypesTo(getFileDeclaration());
					}
				}
				
				//			if (refFile != null)
				//			{
				//				Import imp = refFile.getImport(SyntaxUtils.stripGenerics(type), false);
				//				
				//				if (imp != null)
				//				{
				//					parent.getFileDeclaration().addImport(imp.getClassLocation());
				//				}
				//			}
			});
		}
		
		String methodDeclaration = "static lambda" + id++ + "(" + builder.toString() + ")";
		
		if (closure.getType() != null)
		{
			methodDeclaration += " -> " + closure.getNovaTypeValue(context).getNovaType(context);
		}
		
		BodyMethodDeclaration bodyMethod = BodyMethodDeclaration.decodeStatement(parent.getParentClass(true), methodDeclaration, getLocationIn().asNew(), true);
		
		if (bodyMethod != null)
		{
			if (parameters != null)
			{
				for (int i = 0; i < parameters.length; i++)
				{
					bodyMethod.getParameterList().addChild(parameters[i]);
				}
			}
			
			LambdaMethodDeclaration method = generateLambdaMethod(bodyMethod, bodyMethod);
			
//			for (int n = 0; n < params.getNumVisibleChildren(); n++)
//			{
//				params.getParameter(n).setDataType(closure.getParameterList().getParameter(n).getDataType());
//			}
			
			method.getParentClass().addChild(method);
			
			if (block)
			{
				TreeGenerator generator = new TreeGenerator(null, operation, parent.getProgram().getTree());
				
				generator.traverseCode(method, 0, null, false);
			}
			else
			{
				Node node = SyntaxTree.decodeScopeContents(method, operation, getLocationIn().asNew());
				method.addChild(node);
			}
			
			if ((pending || method.getType() != null) && method.getScope().getNumVisibleChildren() == 1)
			{
				Node returned = method.getScope().getLastChild();
				
				if (returned instanceof Return == false)
				{
					Return r = new Return(method, returned.getLocationIn());
					
					r.getReturnValues().addChild(returned);
					
					method.getScope().addChild(r);
					
					if (pending)
					{
						FunctionType type = (FunctionType)getTypeObject();
						
						type.type = Type.parse(this, r.getReturnedNode().generateNovaType().toString());
						type.closure.setType(r.getReturnedNode());
					}
				}
			}
			
			if (method.getScope().getLastChild() instanceof Return && ((Return)method.getScope().getLastChild()).getValueNode() != null)
			{
				//if (call.isGenericType())
				{
					method.setType(((Return)method.getScope().getLastChild()).getReturnedNode());
				}
			}
			
			Closure methodReference = generateMethodReference(method);
			
			if (methodReference != null)
			{
				Node root = parent.getStatementRootNode();
				
				if (root != null)
				{
					addClosureContext(method, root);
					
					if (pending)
					{
						((FunctionType)getTypeObject()).closure.reference = method;
					}
					else
					{
						replaceWith(methodReference);
					}
					
					return methodReference;
				}
			}
		}
		
		return null;
	}
	
	public Closure generateMethodReference(LambdaMethodDeclaration method)
	{
		Closure methodReference = Closure.decodeStatement(parent, method.generateNovaClosureReference(method.getParentClass()), getLocationIn().asNew(), true, method.getParentClass());
		
		if (methodReference != null)
		{
			if (closure != null)
			{
				methodReference.closureDeclaration = closure;
				methodReference.findDeclaration(method.getParentClass());
			}
			else
			{
				if (getAncestorOfType(Return.class) != null)
				{
					if (!getParentMethod().isFunctionType())
					{
						SyntaxMessage.error("Function '" + getParentMethod().getName() + "' must declare a first-class function return type to return a lambda expression", this);
					}
					
					FunctionType func = (FunctionType)getParentMethod().getTypeObject();
					
					methodReference.closureDeclaration = func.closure;
				}
				
				methodReference.declaration = method;
			}
			
			method.closure = methodReference;
			
			updatePassedFunctionReferences(method);
		}
		
		return methodReference;
	}
	
	public void addClosureContext(LambdaMethodDeclaration method)
	{
		addClosureContext(method, parent.getStatementRootNode());
	}
	
	public void addClosureContext(LambdaMethodDeclaration method, Node root)
	{
		if (root != null)
		{
			Node node = root.getParentMethod(true);
			
			node = node == null ? root.getNearestScopeAncestor() : node;
			
			node.addChild(method.contextDeclaration);
		}
	}
	
	private void updatePassedFunctionReferences(LambdaMethodDeclaration method)
	{
		method.getParameterList().forEachVisibleListChild(param -> {
			if (param instanceof ClosureDeclaration && method.methodCall != null)
			{
				Value value = method.methodCall.getCallableMethodBase().getParameterList().getParameter(method.index);
				
				if (value instanceof ClosureDeclaration)
				{
					ClosureDeclaration closure = (ClosureDeclaration)value;
					
					Value corresponding = closure.getParameterList().getParameter(param.getIndex());
					
					if (corresponding.isGenericType())
					{
						Parameter replacement = new Parameter(param.parent, param.getLocationIn());
						param.cloneTo(replacement, true, true);
						
						replacement.setType(param.generateNovaInput().toString());
						
						param.replaceWith(replacement);
						
						final FunctionType funcType = (FunctionType)replacement.getTypeObject();
						
						param.references.forEach(var -> {
							if (var instanceof MethodCall)
							{
								MethodCall call = (MethodCall)var;
								
								call.declaration = funcType.closure;
							}
						});
					}
				}
			}
		});
	}
	
	private static Value findContext(Node parent)
	{
		MethodCall call = (MethodCall)parent.getAncestorOfType(MethodCall.class);
		
		if (call != null)
		{
			return call;
		}
		else
		{
			NovaMethodDeclaration method = parent.getParentMethod(true);
			
			if (method != null)
			{
				return method;
			}
		}
		
		return null;
	}
	
	private ClosureDeclaration findDeclaration(Value context, final String[] variables)
	{
		if (context instanceof MethodCall)
		{
			return findDeclarationFromFunctionCall((MethodCall)context, variables);
		}
		else if (context instanceof NovaMethodDeclaration)
		{
			return findDeclarationFromFunctionDeclaration((NovaMethodDeclaration)context, variables);
		}
		
		return null;
	}
	
	private ClosureDeclaration findDeclarationFromFunctionDeclaration(NovaMethodDeclaration context, final String[] variables)
	{
		setToInferredType();
		
		if (context.getType() != null)
		{
			if (context.isFunctionType())
			{
				return ((FunctionType)context.getTypeObject()).closure;
			}
			else
			{
				SyntaxMessage.error("Function with return type '" + context.generateNovaType() + "' cannot return a lambda expression", this);
			}
		}
		
		FunctionType type = ((FunctionType)getTypeObject());
		
//		context.setType(type.closure.generateNovaInput().toString());
		
		return type.closure;//((FunctionType)context.getTypeObject()).closure;
	}
	
	private ClosureDeclaration findDeclarationFromFunctionCall(MethodCall context, final String[] variables)
	{
		MethodCall.Pair<ClassDeclaration, MethodList.SearchFilter>[] classes = context.getDeclaringClasses();//.getReferenceNode().toValue().getTypeClass(false);
		
		if (classes == null || classes.length == 0)
		{
			return null;
		}
		
		ClassDeclaration type = context.getReferenceNode().toValue().getTypeClass();
		
		ArrayList<MethodDeclaration> temp = new ArrayList<>();
		
		for (MethodCall.Pair<ClassDeclaration, MethodList.SearchFilter> c : classes)
		{
			if (c != null)
			{
				MethodDeclaration[] methods = c.a.getMethods(context.getName(), c.b);
				
				for (MethodDeclaration m : methods)
				{
					if (type.isPrimitiveOverload() && m instanceof NovaMethodDeclaration)
					{
						NovaMethodDeclaration converted = ((NovaMethodDeclaration)m).checkConvertToClass(type);
						
						if (converted != null)
						{
							m = converted;
						}
					}
					
					temp.add(m);
				}
			}
		}
		
		MethodDeclaration[] methods = temp.toArray(new MethodDeclaration[0]);
		
		if (methods.length > 0)
		{
			final int index = context.getArgumentList().getNumVisibleChildren();
			
			ArrayList<NovaMethodDeclaration> tempMethods = new ArrayList<>();
			
			Arrays.stream(methods)
				.filter(x ->
				{
					if (x instanceof NovaMethodDeclaration &&
						x.getParameterList().getNumVisibleChildren() > index &&
						x.getParameter(index) instanceof ClosureDeclaration)
					{
						ClosureDeclaration closure = (ClosureDeclaration)x.getParameter(index);
						
						if (closure.getParameterList().getNumVisibleChildren() >= variables.length)
						{
							return true;
						}
					}
					
					return false;
				})
				.forEach(x -> tempMethods.add((NovaMethodDeclaration)x));
			
			ArrayList<NovaMethodDeclaration> clone = new ArrayList<>();
			
			for (NovaMethodDeclaration m : tempMethods)
			{
				clone.add(m);
			}
			for (NovaMethodDeclaration x : clone)
			{
				if (x.doesOverride() && tempMethods.contains(x.getOverriddenMethod()))
				{
					tempMethods.remove(x.getOverriddenMethod());
				}
			}
			
			NovaMethodDeclaration[] validMethods = tempMethods.toArray(new NovaMethodDeclaration[0]);
			
			int maxD = 0;
			int maxI = 0;
			
			for (int i = 0; i < validMethods.length; i++)
			{
				for (int j = 0; j < validMethods.length; j++)
				{
					if (i != j)
					{
						int[] distances = validMethods[i].getDistancesFrom(validMethods[j].getParameterList());
						
						int distance = Arrays.stream(distances).sum();
						
						if (distance > maxD)
						{
							maxI = i;
							maxD = distance;
						}
					}
				}
			}
			
			if (validMethods.length > maxI)
			{
				context.setDeclaration(validMethods[maxI]);
				
				return (ClosureDeclaration)((NovaMethodDeclaration)context.getDeclaration()).getParameter(index);
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public LambdaExpression clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		LambdaExpression node = new LambdaExpression(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public LambdaExpression cloneTo(LambdaExpression node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link LambdaExpression} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LambdaExpression cloneTo(LambdaExpression node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.id = id;
		
		return node;
	}
	
	/**
	 * Test the {@link LambdaExpression} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
//	@Override
//	public String getType(boolean checkCast)
//	{
//		Value returned = getReturnedNode();
//		
//		if (returned == null)
//		{
//			return null;
//		}
//		
//		return getReturnedNode().getType(checkCast);
//	}
//	
//	@Override
//	public Type getTypeObject()
//	{
//		Value returned = getReturnedNode();
//		
//		if (returned == null)
//		{
//			return null;
//		}
//		
//		return returned.getTypeObject();
//	}
//	
//	@Override
//	public String getTypeStringValue()
//	{
//		Value returned = getReturnedNode();
//		
//		if (returned == null)
//		{
//			return null;
//		}
//		
//		return getReturnedNode().getTypeStringValue();
//	}
//	
//	@Override
//	public void setTypeValue(String type)
//	{
//		Value returned = getReturnedNode();
//		
//		if (returned == null)
//		{
//			return;
//		}
//		
//		getReturnedNode().setTypeValue(type);
//	}
//
//	@Override
//	public int getArrayDimensions()
//	{
//		Value returned = getReturnedNode();
//		
//		if (returned == null)
//		{
//			return 0;
//		}
//		
//		return getReturnedNode().getArrayDimensions() - getReturnedNode().getArrayAccessDimensions();
//	}
//
//	@Override
//	public void setArrayDimensions(int arrayDimensions)
//	{
//		Value returned = getReturnedNode();
//		
//		if (returned == null)
//		{
//			return;
//		}
//		
//		returned.setArrayDimensions(returned.getArrayDimensions());
//	}
//
//	@Override
//	public byte getDataType(boolean checkGeneric)
//	{
//		Value returned = getReturnedNode();
//		
//		if (returned == null)
//		{
//			return 0;
//		}
//		
//		return getReturnedNode().getDataType();
//	}
//
//	@Override
//	public void setDataType(byte type)
//	{
//		Value returned = getReturnedNode();
//		
//		if (returned == null)
//		{
//			return;
//		}
//		
//		getReturnedNode().setDataType(type);
//	}
}