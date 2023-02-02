package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.annotations.FinalAnnotation;
import flat.tree.annotations.VarAnnotation;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeParameterList;
import flat.tree.lambda.LambdaExpression;
import flat.tree.variables.VariableDeclaration;
import flat.util.*;

/**
 * LocalVariable extension that represents the declaration of local
 * variable.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 Jan 5, 2014 at 9:10:49 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class LocalDeclaration extends VariableDeclaration
{
	private int scopeID;

	private boolean isImplicit;
	public boolean allocatedOnHeap;
	
	public Value implicitType;
	public Value correspondingImplicit;
	
	/**
	 * @see Node#Node(Node, Location)
	 */
	public LocalDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public boolean isLocal()
	{
		return true;
	}
	
	/**
	 * Get the ID of the scope that the variable was declared in.
	 * 
	 * @return The ID of the scope that the variable was declared in.
	 */
	public int getScopeID()
	{
		return scopeID;
	}
	
	@Override
	public boolean isAllocatedOnHeap()
	{
		return allocatedOnHeap;
	}
	
	/**
	 * Set the ID of the scope that the variable was declared in.
	 * 
	 * @param scopeID The ID of the scope that the variable was declared
	 * 		in.
	 */
	public void setScopeID(int scopeID)
	{
		this.scopeID = scopeID;
	}
	
	public boolean isImplicit()
	{
		return isImplicit;
	}
	
	/**
	 * @see Node#onAdded(Node)
	 */
	@Override
	public void onAdded(Node parent)
	{
		LocalDeclaration previous = this;
		
		for (int i = 0; i < extraDeclarations.length; i++)
		{
			String name = extraDeclarations[i];
			String type = "";
			
			if (!StringUtils.containsMultipleWords(name))
			{
				type = previous.generateFlatType() + "";
			}
			
			LocalDeclaration local = LocalDeclaration.decodeStatement(getParent(), type + " " + name, getLocationIn(), true);
			cloneAnnotationsTo(local);
			
			local.setVolatile(isVolatile());
			
			parent.addChildAfter(previous, local);
			
			previous = local;
		}
		
		extraDeclarations = new String[0];
		
		super.onAdded(parent);
	}
	
	private static boolean containsParenthesis(String statement)
	{
		return SyntaxUtils.findCharInBaseScope(statement, '(', 0, 1) >= 0;
	}
	
	/**
	 * Decode the given statement into a LocalDeclaration instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>int index</li>
	 * 	<li>constant char c</li>
	 * 	<li>String name</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		LocalDeclaration instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a LocalDeclaration.
	 */
	public static LocalDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		return decodeStatement(parent, statement, location, require, true, true);
	}
	
	/**
	 * Decode the given statement into a LocalDeclaration instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>int index</li>
	 * 	<li>constant char c</li>
	 * 	<li>String name</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		LocalDeclaration instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param checkName Whether or not to check for naming conflicts.
	 * @return The generated node, if it was possible to translated it
	 * 		into a LocalDeclaration.
	 */
	public static LocalDeclaration decodeStatement(Node parent, String statement, Location location, boolean require, boolean checkName, boolean checkType)
	{
		LocalDeclaration n = new LocalDeclaration(parent, location);
		
		Bounds extraDeclarations = n.findExtraDeclarations(statement);
		
		if (extraDeclarations.isValid())
		{
			statement = extraDeclarations.extractPreString(statement);
		}
		
		if (!SyntaxUtils.isLiteral(n, statement) && StringUtils.containsMultipleWords(statement) && !containsParenthesis(statement))// || !Regex.matches(statement, Patterns.IDENTIFIER_DECLARATION))
		{
			DeclarationData  data = new DeclarationData();
			
			data.checkType = checkType;
			
			GenericTypeParameterList.searchGenerics(statement, data);
			
			n.iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES, data, require);
			
			if (data.error != null)
			{
				SyntaxMessage.queryError(data.error, n, require);
				
				return null;
			}
			
			n.checkExternal();
			
			if (!checkName || n.validateDeclaration(require))
			{
				for (int i = 0; i < n.getNumGenericTypeArguments(); i++)
				{
					GenericTypeArgument type = n.getGenericTypeArgument(i);

					if (!type.isFunctionType() && !type.isGenericType() && !SyntaxUtils.validateImported(n, type.getTypeClassLocation()))
					{
						type.isGenericType();
						SyntaxUtils.validateImported(n, type.getTypeClassLocation());
						SyntaxMessage.queryError("Invalid type '" + type.getType() + "'", n, require);
						
						return null;
					}
				}
				
				if (parent.getProgram().getPhase() >= SyntaxTree.PHASE_METHOD_CONTENTS)
				{
					n.convertToPrimitiveType();
				}
				
				return n;
			}
		}
		
		return null;
	}
	
	public boolean containsImplicitCompatibleAnnotation()
	{
		return containsAnnotationOfType(VarAnnotation.class, false, true) || containsAnnotationOfType(FinalAnnotation.class, false, true);
	}
	
	/**
	 * Make sure that the declaration is valid and that a declaration of
	 * another variable does not have the same name.
	 * 
	 * @return Whether or not the specified declaration is valid.
	 */
	public boolean validateDeclaration(boolean require)
	{
		if ((!containsImplicitCompatibleAnnotation() && getType() == null) || getName() == null)
		{
			return false;
		}
		
		VariableDeclaration node = SyntaxTree.findDeclaration(getParent(), getName());
		
		// If a local variable with the same name has already been declared.
		if (node instanceof LocalDeclaration)
		{
			SyntaxTree.findDeclaration(getParent(), getName());
			SyntaxMessage.queryError("Local variable '" + getName() + "' has already been declared", this, require);
			return false;
		}
		
		return true;
	}
	
	@Override
	public Node followedByScope(boolean scope)
	{
		if (scope && isFunctionType())
		{
			isImplicit = true;
			getAncestorWithScope().addChild(this);
			
			Assignment replacement = Assignment.generateDefault(parent, getLocationIn());
			replacement.wasDeclaration = true;
			
			FunctionType type = (FunctionType)getTypeObject();
			
			String params = "()";
			
			if (type.parameterNames != null)
			{
				params = "(";
				
				for (int i = 0; i < type.parameterNames.length; i++)
				{
					params += type.closure.getParameterList().getParameter(i).generateFlatType() + " " + type.parameterNames[i];
				}
				
				params += ")";
			}
			
			replacement.getAssigneeNodes().addChild(generateUsableVariable(parent, getLocationIn()));
			replacement.addChild(LambdaExpression.decodeStatement(parent, params + " => {", getLocationIn(), true));
			
			return replacement;
		}
		
		return super.followedByScope(scope);
	}
	
	/**
	 * Check whether or not the specified declaration is external.
	 */
	private void checkExternal()
	{
		if (getParent() instanceof ExternalType)
		{
			setExternal(true);
		}
	}
	
	/**
	 * @see Node#interactWord(String, Bounds, String, String, ExtraData)
	 */
	@Override
	public boolean interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData data)
	{
		DeclarationData extra = (DeclarationData)data;

		boolean nullable = rightDelimiter.startsWith("?");
		
		if (extra.isLastWord()) {
			if (getType() == null && containsImplicitCompatibleAnnotation()) {
				isImplicit = true;
			}

			interactName(word, leftDelimiter, rightDelimiter, extra);
		} else if (extra.isFirstWord() && leftDelimiter.length() > 0) {
			extra.error = "Invalid symbol '" + leftDelimiter + "'";
		} else if (setAttribute(word, extra.getWordNumber())) {

		} else if (getType() != null || isImplicit()) {
			extra.error = "Invalid syntax '" + leftDelimiter + word + "'";
		} else if (rightDelimiter.equals(".")) {

		} else if (rightDelimiter.length() == 0 || nullable ||  rightDelimiter.equals("*") || (rightDelimiter.startsWith("[") && rightDelimiter.length() > 1)) {
			String type = word;

			for (
				int i = extra.getWordNumber();
				i > 0 && extra.delims.get(i).equals(".");
				i--
			) {
				type = extra.words.get(i - 1) + extra.delims.get(i) + type;

				String left = extra.delims.get(i - 1);

				if (left.length() > 0 && !left.equals(".")) {
					extra.error = "Invalid type '" + type + "'";
					return false;
				}
			}

			setType(type, true, false, true || extra.checkType || getProgram().getPhase() == SyntaxTree.PHASE_METHOD_CONTENTS);

			if (getProgram().getPhase() == SyntaxTree.PHASE_METHOD_CONTENTS && (!setType(getType(), false, extra.checkType) || getType() == null))
			{
				extra.error = "Type '" + type + "' does not exist";
			}

			if (rightDelimiter.equals("*"))
			{
				if (getDataType() == POINTER)
				{
					setDataType(DOUBLE_POINTER);
				}
				else
				{
					setDataType(POINTER);
				}
			} else if (nullable) {
				setDataType(POINTER);
				explicitlyNullable = true;
			}
		}

		if (nullable) {
			rightDelimiter = rightDelimiter.substring(1);
		}
		
		if (!checkArray(extra.statement, bounds.getEnd() + (nullable ? 1 : 0), rightDelimiter, extra.require))
		{
			extra.error = "Could not parse array brackets";
		}

		return true;
	}
	
	/**
	 * Decode the given word as the name of the declaration.
	 * 
	 * @param word The name of the declared variable.
	 * @param leftDelimiter The left delimiter of the name.
	 * @param rightDelimiter The right delimiter of the name.
	 * @param extra The ExtraData for the word iteration.
	 */
	private void interactName(String word, String leftDelimiter, String rightDelimiter, DeclarationData extra)
	{
		if (!containsImplicitCompatibleAnnotation() && getType() == null)// || (leftDelimiter.length() != 0 && !StringUtils.containsOnly(leftDelimiter, new char[] { '*', '&' })))
		{
			return;
		}
		
		setName(word);
		
		if (extra.getLeftAdjacentSkipBounds() != null)
		{
			decodeGenericTypeArguments(extra.statement, extra.getLeftAdjacentSkipBounds());
			
			extra.decrementGenericsRemaining();
		}
	}

	public Value getImplicitType()
	{
		return implicitType;
	}

	public void setImplicitType(Value implicitType)
	{
		this.implicitType = implicitType;
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);

		if (result.skipValidation())
		{
			return result;
		}

		if (isImplicit())
		{
			if (implicitType != null && (getType() == null || !getType().equals(implicitType.getType())))
			{
				setType(implicitType);
			}
		}
		
		if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (!isUsed())
			{
				isUsed();
				//SyntaxMessage.warning("Variable '" + getName() + "' is never used", this, false);
			}
		}

		return result;
	}

	/**
	 * @see VariableDeclaration#clone(Node, Location)
	 */
	@Override
	public LocalDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		LocalDeclaration node = new LocalDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public LocalDeclaration cloneTo(LocalDeclaration node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link LocalDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LocalDeclaration cloneTo(LocalDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.allocatedOnHeap = allocatedOnHeap;
		
		return node;
	}
	
	/**
	 * Test the LocalDeclaration class type to make sure everything
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
		return generateFlatInput().toString();
	}
}