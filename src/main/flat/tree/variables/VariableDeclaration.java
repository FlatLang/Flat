package flat.tree.variables;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.tree.annotations.*;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameterList;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Identifier extension that represents something that returns a value.
 * For the rules on what can and cannot be an value node, refer to
 * {@link #setType(java.lang.String)}
 *
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 2, 2014 at 11:14:37 PM
 * @version	v0.2.43 Jan 16, 2015 at 11:59:17 AM
 */
public class VariableDeclaration extends IIdentifier
{
	private boolean            volatileVal, external, reference, lazy;
	public ArrayList<ClosureVariableDeclaration> closureVariableDeclarations = new ArrayList<>();

	public  String[]           extraDeclarations;

	public volatile java.util.List<Variable> references = Collections.synchronizedList(new ArrayList<>());

	/**
	 * @see Node#Node(Node, Location)
	 */
	public VariableDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);

		GenericTypeArgumentList implementation = new GenericTypeArgumentList(this, locationIn.asNew());
		addChild(implementation, this);

		extraDeclarations = new String[0];
	}

	public boolean requiresHeapAllocation()
	{
		return closureVariableDeclarations.stream().anyMatch(x -> x.requiresHeapAllocation());
	}

	public boolean isValueReference()
	{
		return reference;
	}

	public void setIsValueReference(boolean reference)
	{
		this.reference = reference;
	}

	public boolean isImmutable()
	{
		if (isPrimitive())
		{
			return isFinal();
		}
		else if (isExternalType())
		{
			return false;
		}

		return getTypeClass() != null && getTypeClass().isImmutable() || getImmutableAnnotation() != null;
	}

	public ImmutableAnnotation getImmutableAnnotation()
	{
		if (getAnnotations() != null)
		{
			for (Annotation a : getAnnotations())
			{
				if (a instanceof ImmutableAnnotation)
				{
					ImmutableAnnotation i = (ImmutableAnnotation)a;

					if (i.parameters.size() == 0)
					{
						return i;
					}
				}
			}
		}

		return null;
	}

	public boolean isFinal()
	{
		return getFinalAnnotation() != null;
	}

	public FinalAnnotation getFinalAnnotation()
	{
		return (FinalAnnotation)getAnnotationOfType(FinalAnnotation.class, false, true);
	}

	public boolean isVar()
	{
		return getVarAnnotation() != null;
	}

	public VarAnnotation getVarAnnotation()
	{
		return (VarAnnotation)getAnnotationOfType(VarAnnotation.class, false, true);
	}

	@Override
	public void onReplaced(Node parent, Node replacement)
	{
		if (replacement instanceof VariableDeclaration)
		{
			references.forEach(x -> {
				x.declaration = (VariableDeclaration)replacement;
			});
		}

		super.onReplaced(parent, replacement);
	}

	public boolean isLocal()
	{
		return true;
	}

	public VariableDeclaration getOriginalDeclaration()
	{
		return this;
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
	 * Get the Bounds that contains the extra field declarations.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * public String firstName, lastName, middleI</pre></blockquote>
	 * In the above Field Declaration, the '<code><i>lastName, middleI</i></code>' part of
	 * the declaration is classified as an 'extra' declaration. The Bounds would start
	 * before the comma after '<code><i>firstName</i></code>' and end after
	 * '<code><i>lastName, middleI</i></code>' section.
	 *
	 * @param statement The statement to search for the extra declarations in.
	 * @return The Bounds containing the extra declarations. If there are no
	 * 		extra declarations, then Bounds.EMPTY is returned.
	 */
	public Bounds findExtraDeclarations(String statement)
	{
		String declarations[] = StringUtils.splitCommas(statement, 1);

		if (declarations.length > 1)
		{
			extraDeclarations = new String[declarations.length - 1];

			System.arraycopy(declarations, 1, extraDeclarations, 0, declarations.length - 1);

			return new Bounds(declarations[0].length(), statement.length());
		}

		return Bounds.EMPTY;
	}

	public boolean isTangible()
	{
		return isAccessible();
	}

	public boolean isAccessible()
	{
		return true;
	}

	public boolean isSettable()
	{
		return true;
	}

	@Override
	public ClassDeclaration getDeclaringClass()
	{
		return getParentClass();
	}

	/**
	 * @see Value#getGenericReturnType()
	 */
	@Override
	public String getGenericReturnType(Value context, boolean checkCast)
	{
		return getGenericTypeParameter().getDefaultType();
	}

	/**
	 * @see Identifier#isDeclaration()
	 */
	@Override
	public boolean isDeclaration()
	{
		return true;
	}

	/**
	 * Get whether or not the variable is external. For more information
	 * on external variables, see {@link #setExternal(boolean)}.
	 *
	 * @return Whether or not the variable is external.
	 */
	public boolean isExternal()
	{
		if (getParent() instanceof FieldDeclaration)
		{
			FieldDeclaration field = (FieldDeclaration)getParent();

			return field.isExternal();
		}

		return external;
	}

	/**
	 * @see Node#isWithinExternalContext()
	 */
	@Override
	public boolean isWithinExternalContext()
	{
		return isExternal() || super.isWithinExternalContext();
	}

	/**
	 * Set whether or not the variable is external. A variable is external
	 * if it is referenced from a language outside of Flat. For example,
	 * a variable from the C language. Furthermore, a variable is external
	 * if it begins with an externally imported C file's name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * import "externalFile.h";
	 *
	 * ...
	 *
	 * public static void main(String args[])
	 * {
	 *	// This is the external variable declaration.
	 * 	externalFile.externalType varName;
	 *
	 * 	// This is the external variable assignment.
	 * 	varName = externalFile.variableInstance;
	 * }</pre></blockquote>
	 * In this example, 'externalFile' is the C header file that is
	 * imported. 'variableInstance' is the name of a variable that
	 * is contained within the imported header file.<br>
	 *
	 * @param external Whether or not the variable will be external.
	 */
	public void setExternal(boolean external)
	{
		this.external = external;

		setForceOriginalName(true);
	}

	/**
	 * Get whether or not the variable's value is volatile. This is used
	 * for exception handling to make sure local variables keep their
	 * values after an exception has been thrown.
	 *
	 * @return Whether or not the variable's value is volatile.
	 */
	public boolean isVolatile()
	{
		return volatileVal;
	}

	/**
	 * Get the C equivalent of the 'constant' keyword.
	 *
	 * @return The C equivalent of the 'constant' keyword.
	 */
	public String getVolatileText()
	{
		return "volatile";
	}

	/**
	 * Set whether or not the variable's value is volatile. This is used
	 * for exception handling to make sure local variables keep their
	 * values after an exception has been thrown.
	 *
	 * @param volatileVal Whether or not the variable's value
	 * 		is volatile.
	 */
	public void setVolatile(boolean volatileVal)
	{
		this.volatileVal = volatileVal;
	}

	/**
	 * Set the name of the Variable.
	 *
	 * @see Identifier#setName(java.lang.String)
	 *
	 * @param name The String to set as the new name.
	 */
	public void setName(String name)
	{
		setName(name, false);
	}

	/**
	 * Set a specified attribute to true.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private static int index;</pre></blockquote>
	 * <u><code>private</code></u> sets the visibility of the declaration
	 * to private. <u><code>static</code></u> sets the variable as static.
	 *
	 * @param attribute The attribute to set true.
	 */
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}

	/**
	 * Set a specified attribute to true.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private static int index;</pre></blockquote>
	 * <u><code>private</code></u> is the first attribute (index: 0) that
	 * sets the visibility of the declaration to private.
	 * "<u><code>static</code></u>" is the second attribute (index: 1) that
	 * sets the variable as static.
	 *
	 * @param attribute The attribute to set true.
	 * @param argNum The index of the attribute in the order that it
	 * 		came in.
	 * @return Whether or not an attribute was successfully set.
	 */
	public boolean setAttribute(String attribute, int argNum)
	{
		return parseModifier(attribute);
	}

	public void swapNames(Variable other)
	{
		swapNames(other.getDeclaration());
	}

	public void swapNames(VariableDeclaration other)
	{
		String  temp  = getName();
		boolean force = doesForceOriginalName();

		setName(other.getName(), other.doesForceOriginalName());
		other.setName(temp, force);
	}

	@Override
	public boolean onAfterDecoded()
	{
		convertArrays();

		return super.onAfterDecoded();
	}

	/**
	 * Compare the specified variable with the given one to see if they
	 * come from the same declaration.
	 *
	 * @param other The variable to compare with.
	 * @return Whether or not the variables come from the same
	 * 		declaration.
	 */
	public boolean isSameVariable(Variable other)
	{
		VariableDeclaration second = other.getDeclaration();

		return this == second;
	}

	/**
	 * @see Node#generateFlatInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		generateFlatAnnotations(builder);

		if (isFunctionType())
		{
			return generateFlatType(builder, this);
		}

		return generateFlatType(builder, this).append(' ').append(getName());
	}

	/**
	 * Check to see if the statement is declaring an array.
	 * e.g. "<u><code>String names[]</code></u>"
	 *
	 * @param statement The statement possibly containing array brackets.
	 * @param index The index to start the search for array brackets at.
	 * @param rightDelimiter The right delimiter possibly containing
	 * 		array brackets.
	 */
	public boolean checkArray(String statement, int index, String rightDelimiter, boolean require)
	{
		// If it is an array declaration.
		if (rightDelimiter.length() > 0 && rightDelimiter.charAt(0) == '[')
		{
			int dimensions = SyntaxUtils.findArrayDimensions(statement, index, false);

			if (dimensions < 0)
			{
				return SyntaxMessage.queryError("Array brackets cannot contain data", this, require);
			}

			setArrayDimensions(getArrayDimensions() + dimensions);
		}

		return true;
	}

	/**
	 * Generate a usable Variable instance that refers to the specified
	 * VariableDeclaration.
	 *
	 * @param parent The parent of the newly generated Variable.
	 * @param location The location of the newly generated Variable.
	 * @return The newly generated Variable.
	 */
	public Variable generateUsableVariable(Node parent, Location location)
	{
		Variable var = new Variable(parent, location);

		return generateUsableVariable(var);
	}

	/**
	 * Generate a usable Variable instance that refers to the specified
	 * VariableDeclaration from the given Variable instance.
	 *
	 * @param toVar The Variable to set up as a reference to the
	 * 		VariableDeclaration.
	 * @return The correctly set up Variable.
	 */
	public Variable generateUsableVariable(Variable toVar)
	{
		toVar.setDeclaration(this);

		return toVar;
	}

	/**
	 * @see Node#validate(int)
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);

		if (result.skipValidation())
		{
			return result;
		}

		if (getVarAnnotation() == null && getFinalAnnotation() == null)
		{
			FinalAnnotation finalAnnotation = new FinalAnnotation(this, getLocationIn());

			finalAnnotation.onApplied(this);
			addAnnotation(finalAnnotation);
		}

		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			if (!validateType())
			{
				return result.errorOccurred();
			}
		}

		addDefaultGenericTypeArguments();

		if (phase >= SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			convertToPrimitiveType();
		}

		return result;
	}

	public boolean isUsed()
	{
		return !isUserMade() || references.size() > 0;
	}

	public void addDefaultGenericTypeArguments()
	{
		addDefaultGenericTypeArguments(false);
	}

	public void addDefaultGenericTypeArguments(boolean clearChildren)
	{
		if (getTypeClass() != null && !isGenericType())
		{
			GenericTypeArgumentList args = getGenericTypeArgumentList();
			GenericTypeParameterList decl = getTypeClass().getGenericTypeParameterDeclaration();

			if (clearChildren && args.getNumVisibleChildren() > 0)
			{
				args.slaughterEveryLastChild();
			}

			for (int i = args.getNumVisibleChildren(); i < decl.getNumParameters(); i++)
			{
				String type = decl.getParameter(i).getDefaultType();

				GenericTypeArgument arg = SyntaxUtils.getGenericTypeArgumentName(this, type);
				arg.autoAdded = true;

				args.addChild(arg);

				Import imp = decl.getFileDeclaration().getImport(type, false);

				if (imp == null) {
					SyntaxMessage.error("Invalid type '" + type + "'", this);
					return;
				}

				getFileDeclaration().addImport(imp.getClassLocation());
			}
		}
	}

	@Override
	public boolean canAccess() {
		return false;
	}

	public boolean validateType()
	{
		if (getType() != null && !setType(getType(), false, !isGenericType()))
		{
			setType(getType(), false, !isGenericType());
			SyntaxMessage.error("Type '" + getType() + "' does not exist", this, false);

			isGenericType();

			return false;
		}

		return true;
	}

	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public VariableDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		VariableDeclaration node = new VariableDeclaration(temporaryParent, locationIn);

		return cloneTo(node, cloneChildren, cloneAnnotations);
	}

	/**
	 * @see Node#cloneTo(Node)
	 */
	public VariableDeclaration cloneTo(VariableDeclaration node)
	{
		return cloneTo(node, true, true);
	}

	/**
	 * Fill the given {@link VariableDeclaration} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VariableDeclaration cloneTo(VariableDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);

		node.external     = external;
		node.volatileVal  = volatileVal;
		node.reference    = reference;
		node.lazy         = lazy;

		node.extraDeclarations = new String[extraDeclarations.length];

		for (int i = 0; i < extraDeclarations.length; i++)
		{
			node.extraDeclarations[i] = extraDeclarations[i];
		}

		return node;
	}

	/**
	 * Test the VariableDeclaration class type to make sure everything
	 * is working properly.
	 *
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{


		return null;
	}

	public boolean getLazy() {
		return containsAnnotationOfType(LazyAnnotation.class);
	}

	public void setLazy(boolean lazy) {
		if (lazy) {
			addAnnotation(new LazyAnnotation(this, Location.INVALID));
		} else if (getLazy()) {
			removeAnnotationOfType(LazyAnnotation.class);
		}
	}

	/**
	 * Implementation of the ExtraData for this class.
	 *
	 * @author	Braden Steffaniak
	 * @since	v0.2.29 Aug 28, 2014 at 6:56:45 PM
	 * @version	v0.2.29 Aug 28, 2014 at 6:56:45 PM
	 */
	public static class DeclarationData extends ExtraData
	{
		private int	genericsRemaining;

		public int getGenericsRemaining()
		{
			return genericsRemaining;
		}

		public void setGenericsRemaining(int remaining)
		{
			this.genericsRemaining = remaining;
		}

		public void decrementGenericsRemaining()
		{
			genericsRemaining--;
		}
	}
}
