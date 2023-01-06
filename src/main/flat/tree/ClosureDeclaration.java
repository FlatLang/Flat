package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.lambda.LambdaExpression;
import flat.tree.variables.ObjectReference;
import flat.tree.variables.Variable;
import flat.util.*;
import flat.tree.variables.VariableDeclaration;
import flat.util.Bounds;

/**
 * Identifier extension that represents the use of a variable
 * type. Harnesses the needed information of each variable, such as
 * whether or not it is constant, external, or an array, and its type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jul 5, 2014 at 9:02:42 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class ClosureDeclaration extends Parameter implements CallableMethod, ClosureCompatible
{
	public int id = -1;
	
	@Override
	public FlatMethodDeclaration getRootDeclaration()
	{
		return null;
	}
	
	@Override
	public ObjectReference getObjectReference()
	{
		return null;
	}
	
	@Override
	public ClosureDeclaration getClosureDeclaration()
	{
		return this;
	}
	
	/**
	 * @see Node#Node(Node, Location)
	 */
	public ClosureDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ParameterList<Value> parameterList = new ClosureParameterList(this, Location.INVALID);
		
		addChild(parameterList);
		
		parameterList.getObjectReference().setType(null, true, false, false);
		parameterList.getObjectReference().setDataType(POINTER);
	}
	
	public void register()
	{
		id = getFileDeclaration().registerClosure(this);
	}
	
	public void unregister()
	{
		unregister(getFileDeclaration());
	}
	
	public void unregister(FileDeclaration file)
	{
		file.unregisterClosure(this);
	}
	
	public boolean isPublic()
	{
		FlatMethodDeclaration method = (FlatMethodDeclaration)this.getAncestorOfType(FlatMethodDeclaration.class);
		
		return method == null || method.getVisibility() != InstanceDeclaration.PRIVATE;
	}
	
	/**
	 * @see CallableMethod#isVirtual()
	 */
	@Override
	public boolean isVirtual()
	{
		return false;
	}
	
	@Override
	public Value getFlatTypeValue(Value context)
	{
		if (isGenericType())
		{
			if (getParentMethod() != null && getParentMethod().containsGenericTypeParameter(getType()))
			{
				return getParentMethod().getGenericTypeParameter(getType());
			}
			if (getParentClass().containsGenericTypeParameter(getType()))
			{
				return getParentClass().getGenericTypeParameter(getType(), this);
			}
		}
		
		return super.getFlatTypeValue(context);
	}
	
	/**
	 * @see Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	/**
	 * @see CallableMethod#getParameterList()
	 */
	@Override
	public ParameterList<Value> getParameterList()
	{
		return (ParameterList<Value>)getChild(super.getNumDefaultChildren());
	}
	
	/**
	 * @see CallableMethod#isStatic()
	 */
	@Override
	public boolean isStatic()
	{
		return false;
	}
	
	/**
	 * @see CallableMethod#isInstance()
	 */
	@Override
	public boolean isInstance()
	{
		return true;
	}
	
	@Override
	public void updateGenericParameter(Parameter other)
	{
		super.updateGenericParameter(other);
		
		if (other instanceof ClosureDeclaration)
		{
			ClosureDeclaration closure = (ClosureDeclaration)other;
			
			ParameterList<Value> params = closure.getParameterList();
			
			for (int i = 0; i < getParameterList().getNumVisibleChildren(); i++)
			{
				getParameterList().getParameter(i).setDataType(params.getParameter(i).getDataType());
			}
		}
	}
	
	/**
	 * Decode the given statement into a ClosureDeclaration instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>findPerson(String, Int) -> Person</li>
	 * 	<li>calculateArea(Int, Int) -> Int</li>
	 * 	<li>callback()</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ClosureDeclaration instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ClosureDeclaration.
	 */
	public static ClosureDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		ClosureDeclaration n = new ClosureDeclaration(parent, location);
		
		statement = n.parseModifiers(statement);
		
		if (validateMethodDeclaration(statement))
		{
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
	
	@Override
	public Variable generateUsableVariable(Node parent, Location location)
	{
		ClosureVariable var = new ClosureVariable(parent, location);
		
		return generateUsableVariable(var);
	}
	
	@Override
	public Variable generateUsableVariable(Variable toVar)
	{
		super.generateUsableVariable(toVar);

		String type = generateFlatInput().toString();

		int index = -1;
		if ((index = SyntaxUtils.findCharInBaseScope(type, '=')) != -1) {
			type = type.substring(0, index).trim();
		}
		
		toVar.setType(type);
		
		return toVar;
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
		
		return firstParenthIndex >= 0 && !StringUtils.startsWithWord(statement, ExternalMethodDeclaration.PREFIX);
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
		String signature = FlatMethodDeclaration.findMethodSignature(statement);
		
		String returnType = null;
		int returnIndex = signature.indexOf("->");
		
		if (returnIndex > 0) {
			returnType = signature.substring(returnIndex + 2).trim();
			signature = signature.substring(0, returnIndex).trim();
		}
		
		ExtraData data   = iterateWords(signature, Patterns.IDENTIFIER_BOUNDARIES, require);
		
		if (data.error != null)
		{
			return SyntaxMessage.queryError(data.error, this, require);
		}
		
		setReturnType(returnType);
		
		return SyntaxUtils.isValidIdentifier(signature);
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
			setDataType(Value.POINTER);
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
		
		String parameters[] = StringUtils.splitCommas(parameterList, 1);
		
		Location location = new Location(getLocationIn());
		
		for (int i = 0; i < parameters.length; i++)
		{
			if (parameters[i].length() > 0)
			{
				Parameter p;
				if ((p = Parameter.decodeStatement(this, parameters[i], location, false)) != null) {
					getParameterList().addChild(p);
					continue;
				}

				GenericTypeArgument arg = getGenericTypeArgumentName(parameters[i]);
				
				IIdentifier param = arg;//Value.generateFromType(this, location, parameters[i], require);
				
				if (!SyntaxUtils.isPrimitiveType(param.getType()) && !param.isExternalType())
				{
					param.setDataType(POINTER);
				}
				//arg.cloneTo(param, true);
				
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
	 * @see Node#interactWord(java.lang.String, Bounds, java.lang.String, java.lang.String, Node.ExtraData)
	 */
	@Override
	public boolean interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		if (extra.error != null || !setAttribute(word, extra.getWordNumber()))
		{
			if (leftDelimiter.equals("->"))
			{
				setType(word, true, false);
				
				if (!checkArray(extra.statement, bounds.getEnd(), rightDelimiter, extra.require))
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
				extra.error = "Unknown closure definition";
				
				return false;
			}
		}

		return true;
	}
	
	@Override
	public void propagateToMethod(FlatMethodDeclaration overload)
	{
		
	}
	
	/**
	 * @see VariableDeclaration#validate(int)
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS && defaultValueString != null)
		{
			defaultValue = LambdaExpression.decodeStatement(this, defaultValueString, getLocationIn(), true, this, null);
			
			if (defaultValue != null)
			{
				defaultValue.onAfterDecoded();
				defaultValue.onAdded(this);
				
				if (defaultValue instanceof LambdaExpression)
				{
					defaultValue = ((LambdaExpression)defaultValue).generateClosure();
				}
				
				defaultValueString = null;
			}
		}
		
		if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (id == -1)
			{
				register();
			}
		}
		
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		return result;
	}
	
	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public ClosureDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ClosureDeclaration node = new ClosureDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public ClosureDeclaration cloneTo(ClosureDeclaration node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ClosureDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClosureDeclaration cloneTo(ClosureDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.id = id;
		
		return node;
	}
	
	/**
	 * Test the ClosureDeclaration class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		return generateFlatInput(builder, outputChildren, true);
	}
	
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray)
	{
		return generateFlatInput(builder, outputChildren, generateArray, true);
	}
	
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray, boolean outputDefaultValue)
	{
		builder.append(getName()).append("(").append(getParameterList().generateFlatInput()).append(")");
		
		if (getType() != null)
		{
			builder.append(" -> ").append(generateFlatType());
		}
		
		if (outputDefaultValue && defaultValueString != null)
		{
			builder.append(" = ").append(defaultValueString);
		}
		
		return builder;
	}
}