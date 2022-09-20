package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.generics.GenericTypeArgumentList;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.Stack;

import java.util.ArrayList;

public class Constructor extends BodyMethodDeclarationValidator
{
	public static final String	IDENTIFIER = "construct";
	
	private boolean initializedInitMethod;
	
	public InitializationMethodValidator initMethod;
	
	/**
	 * Create a Constructor and initialize default values.
	 * 
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public Constructor(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public void searchVirtualMethodDeclaration()
	{
		
	}
	
	@Override
	public boolean doesConvertToPrimitive()
	{
		return false;
	}
	
	@Override
	public boolean isPure()
	{
		return true;
	}
	
	@Override
	public ValueValidator inferShorthandActionType(String action, ValueValidator contents)
	{
		return contents;
	}
	
	/**
	 * @see MethodDeclarationValidator#isInstance()
	 */
	@Override
	public boolean isInstance()
	{
		return true;
	}
	
	@Override
	public boolean isSpecial()
	{
		return true;
	}
	
	/**
	 * @see FlatMethodDeclarationValidator#generateFlatInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		return super.generateFlatInput(builder, outputChildren, false);
	}
	
	/**
	 * Decode the given statement into a Constructor instance, if
	 * possible. If it is not possible, this method returns null. A
	 * constructor must have the same name as the class that it is
	 * within. Constructors also do not have a return value.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public ClassName()</li>
	 * 	<li>private ClassName(int numChildren, String name)</li>
	 * 	<li>public ClassName(Node parent, Location location)</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Constructor instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Constructor.
	 */
	public static Constructor decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		BodyMethodDeclarationValidator method = BodyMethodDeclarationValidator.decodeStatement(parent, statement, location, false);
		
		if (method != null && method.getName().equals(IDENTIFIER))
		{
			Constructor n = new Constructor(parent, location);
			
			method.cloneTo(n);
			
			n.setName(IDENTIFIER);
			n.setType(n.getParentClass().getName(), true, false);
			n.setStatic(true);
			n.setDataType(POINTER);
			
//			GenericTypeParameterList params = n.getMethodGenericTypeParameterDeclaration();
//			GenericTypeParameterList classParams = n.getParentClass().getGenericTypeParameterDeclaration();
//			
//			for (int i = 0; i < classParams.getNumParameters(); i++)
//			{
//				GenericTypeParameter param = new GenericTypeParameter(params, Location.INVALID);
//				param.setType(classParams.getParameter(i));
//				
//				params.addChild(param);
//			}
			
			return n;
		}
		
		return null;
	}
	
	public FlatMethodDeclarationValidator getExistingConvertedPrimitiveMethod(ClassDeclarationValidator type, GenericTypeArgumentList args)
	{
		return getExistingConvertedPrimitiveMethod(type.getExistingConvertedPrimitiveClass(args.getTypes()));
	}
	
	public FlatMethodDeclarationValidator getExistingConvertedPrimitiveMethod(ClassDeclarationValidator c)
	{
		if (c != null)
		{
			MethodList.SearchFilter filter = new MethodList.SearchFilter();
			filter.checkAncestor = false;
			filter.checkInterfaces = false;
			filter.checkConstructors = true;
			filter.checkProperties = false;
			
			ValueValidator[] original = getParameterList().getTypes();
			ValueValidator[] types = getParameterList().clone(getParameterList().getParent(), getParameterList().getLocationIn(), true, true).getTypes();
			
			if (c.genericOverload != null)
			{
				for (int i = 0; i < types.length; i++)
				{
					c.genericOverload.replacePrimitiveGenerics(c.primitiveOverloadTypes, original[i], types[i]);
				}
			}
			
			return (FlatMethodDeclarationValidator)c.getMethod((GenericCompatible)null, c.getName(), filter, types);
		}
		
		return null;
	}
	
	public FlatMethodDeclarationValidator convertPrimitiveMethod(ClassDeclarationValidator type, ValueValidator[] types)
	{
		return getExistingConvertedPrimitiveMethod(type.convertToPrimitive(types));
	}
	
	@Override
	public FlatMethodDeclarationValidator getConvertedPrimitiveMethod(MethodCall call)
	{
		ClassDeclarationValidator type = getDeclaringClass();
		GenericTypeArgumentList args = call.getMethodGenericTypeArgumentList();
		
		ClassDeclarationValidator existing = type.getConvertedPrimitiveClass(args.getTypes());
		
		if (existing != null)
		{
			call.getFileDeclaration().addImport(existing.getClassLocation());
			
			return getExistingConvertedPrimitiveMethod(existing);
		}
		
		return null;
	}
	
	/**
	 * @see NodeValidator#onAdded(NodeValidator)
	 */
	@Override
	public void onAdded(NodeValidator parent)
	{
		initMethod = new InitializationMethodValidator(getParent(), Location.INVALID);

		initMethod.createFrom(this);
		initMethod.constructor = this;
		
		setName(getParentClass().getName());
		
		getParentClass().addChild(initMethod);
	}

	@Override
	public NodeValidator detach(NodeValidator fromNodeValidator, boolean detachChildren, boolean soft) {
		if (initMethod != null) {
			initMethod.detach();
		}

		return super.detach(fromNodeValidator, detachChildren, soft);
	}

	/**
	 * @see FlatMethodDeclarationValidator#validate(int)
	 */
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase >= SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (!initializedInitMethod)
			{
				initMethod.getScope().slaughterEveryLastVisibleChild();
				initMethod.getScope().getVariableList().closureContextDeclarations = getScope().getVariableList().closureContextDeclarations;
				initMethod.getScope().inheritChildren(getScope());
				initMethod.setLocationIn(getLocationIn());
				initMethod.getScope().localVariableID = getScope().localVariableID;
				
				String args = generateParameterOutput(this);
				
				MethodCall init = MethodCall.decodeStatement(this, "this(" + args + ")", Location.INVALID, true, false, initMethod);
				addChild(init);
				
				SyntaxTree.validateNodes(getParameterList(), phase);
				result.returnedNodeValidator = initMethod;
				
				initializedInitMethod = true;
			}
			
			if (phase == SyntaxTree.PHASE_PRE_GENERATION)
			{
				getScope().getVariableList().closureContextDeclarations = new ArrayList<>();
			}
		}
		
		return result;
	}
	
	@Override
	public void checkDataType(String type)
	{
		
	}
	
	public void addSuperCallFor(Stack<MethodCall> constructorCalls, Constructor current)
	{
		ClassDeclarationValidator clazz = current.getParentClass().getExtendedClassDeclaration();
		
		if (clazz == null)
		{
			return;
		}
	}
	
	private String generateParameterOutput(FlatMethodDeclarationValidator method)
	{
		return generateParameterOutput(method, null);
	}
	
	private String generateParameterOutput(FlatMethodDeclarationValidator method, Constructor inherited)
	{
		StringBuilder args = new StringBuilder();
		
		if (inherited == null || method.areCompatibleParameterTypes(getContext(), inherited.getParameterList().getTypes()))
		{
			for (int i = 0; i < method.getParameterList().getNumVisibleChildren(); i++)
			{
				if (i > 0)
				{
					args.append(", ");
				}
				
				args.append(method.getParameter(i).getName()).append(": ").append(method.getParameter(i).getName());
			}
		}
		
		return args.toString();
	}
	
	/**
	 * Validate that the specified statement really is a Constructor
	 * declaration.
	 * 
	 * @param data The data that will specify if there was an error.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return Whether or not the statement is a Constructor declaration.
	 */
	private boolean validateDeclaration(ExtraData data, boolean require)
	{
		if (getType() != null)
		{
			return false;
		}
		else if (data.error != null)
		{
			return SyntaxMessage.queryError(data.error, this, require);
		}
		
		setName(getParentClass().getName());
		
		return setType(getName(), false);
	}
	
	/**
	 * @see NodeValidator#interactWord(String, Bounds, String, String, ExtraData)
	 */
	@Override
	public boolean interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		if (super.interactWord(word, bounds, leftDelimiter, rightDelimiter, extra)) {

			if (extra.isLastWord()) {
				if (!word.equals(IDENTIFIER)) {
					extra.error = "Constructor must be named \"" + IDENTIFIER + '"';
				}
			} else if (word.equals("static")) {
				extra.error = "Constructor cannot be declared as static";
			}

			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public Constructor clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Constructor node = new Constructor(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public Constructor cloneTo(Constructor node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Constructor} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Constructor cloneTo(Constructor node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the Constructor class type to make sure everything
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