package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;
import flat.util.SyntaxUtils;


public abstract class IdentifierValidator extends ValueValidator implements AccessibleValidator
{
	public boolean safeNavigation;
	public boolean chainNavigation;

	private SyntaxUtils.LiteralNameData literalNameData;

	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public IdentifierValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public boolean isSafeNavigation()
	{
		return safeNavigation;
	}
	
	@Override
	public void setSafeNavigation(boolean safeNavigation)
	{
		this.safeNavigation = safeNavigation;
	}

	@Override
	public boolean isChainNavigation()
	{
		return chainNavigation;
	}

	@Override
	public void setChainNavigation(boolean chainNavigation)
	{
		this.chainNavigation = chainNavigation;
	}
	
	/**
	 * Get whether or not the Identifier is accessed in a non-static way.
	 * 
	 * @return Whether or not the Identifier is accessed in a non-static
	 * 		way.
	 */
	public boolean isInstance()
	{
		return SyntaxUtils.getImportedClass(getFileDeclaration(), getName()) == null;
	}

	@Override
	public ValueValidator getReturnedNode()
	{
		return AccessibleValidator.super.getReturnedNode();
	}
	
//	@Override
//	public boolean isUserMade(boolean checkAncestor)
//	{
//		return !doesForceOriginalName() && super.isUserMade(checkAncestor);
//	}
	
	@Override
	public boolean isExternalType()
	{
		return getType() != null && getDeclaringClass() != null && getDeclaringClass().containsExternalType(getType());
	}
	
	/**
	 * Set the name of the Identifier. Identifier names consist of the
	 * following character types: [A-Z, a-z, 0-9, _]. However, cannot
	 * start with a number.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>correctGrammar</li>
	 * 	<li>INCORrect_grammaR_but_123123STILL_workz</li>
	 * 	<li>identifierName4</li>
	 * </ul>
	 * <br>
	 * Incorrect inputs include:<br>
	 * <ul>
	 * 	<li>24JeffGordon <i>(Cannot start an identifier with a number)</i></li>
	 * 	<li>This.Doesnt_work <i>(Cannot contain a period (or any other punctuation))</i></li>
	 * 	<li>#omgProgramin <i>(This is not Twitter)</i></li>
	 * </ul>
	 * 
	 * @param name The String containing the name to set as the
	 * 		identifier.
	 */
	public void setName(String name)
	{
		setName(name, false);
	}
	
	/**
	 * Get whether or not the Identifier is a declaration.
	 * 
	 * @return Whether or not the Identifier is a declaration.
	 */
	public boolean isDeclaration()
	{
		return false;
	}
	
	/**
	 * @see NodeValidator#generateFlatInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append(getName());
		generateFlatArrayAccess(builder);
		
		if (outputChildren)
		{
			generateAccessedNode(builder, safeNavigation);
		}
		
		return builder;
	}
	
	/**
	 * @see ValueValidator#isVirtualTypeKnown()
	 */
	@Override
	public boolean isVirtualTypeKnown()
	{
		if (isAccessedWithinStaticContext())
		{
			return true;
		}
		
		if (isAccessed())
		{
			if (getAccessingNode() instanceof IdentifierValidator)
			{
				IdentifierValidator id = (IdentifierValidator)getAccessingNode();
				
				return !id.isInstance();
			}
		}
		
		return false;
	}
	
	/**
	 * Get the name of the Identifier. For the rules on what can and
	 * cannot be an Identifier, refer to {@link IdentifierValidator#setName(String) setName}
	 * 
	 * @return The name of the Identifier.
	 */
	public abstract String getName();

	/**
	 * Set the name of the Variable. You specify whether or not you want
	 * the output in the C language to be the original given name,
	 * or if it will differentiate it depending on its scope. 
	 * 
	 * @param name The String to set as the new name.
	 * @param forceOriginal Whether or not the name will be output in the
	 * 		c code verbatim.
	 */
	public abstract void setName(String name, boolean forceOriginal);
	
	/**
	 * Whether or not the output in the C language will be the
	 * original given name, or if it will differentiate it depending on
	 * its scope. 
	 * 
	 * @return Whether or not the name will be output in the C code
	 * 		verbatim.
	 */
	public abstract boolean doesForceOriginalName();
	
	/**
	 * Whether or not you want the output in the C language to be the
	 * original given name, or if it will differentiate it depending on
	 * its scope. 
	 * 
	 * @param forceOriginal Whether or not the name will be output in the
	 * 		c code verbatim.
	 */
	public abstract void setForceOriginalName(boolean forceOriginal);

	public SyntaxUtils.LiteralNameData getLiteralNameData() {
		return literalNameData;
	}

	public void setLiteralNameData(SyntaxUtils.LiteralNameData literalNameData) {
		this.literalNameData = literalNameData;
	}

	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		result.returnedNodeValidator = checkSafeNavigation();
		
		return result;
	}
	
	/**
	 * Fill the given {@link IdentifierValidator} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public IdentifierValidator cloneTo(IdentifierValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link IdentifierValidator} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public IdentifierValidator cloneTo(IdentifierValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
//		node.setName(getName());
//		node.setForceOriginalName(doesForceOriginalName());
//		node.setArrayDimensions(getArrayDimensions());
//		node.setTypeValue(getType());
//		node.setDataType(getDataType());
		node.safeNavigation = safeNavigation;
		node.chainNavigation = chainNavigation;
		node.literalNameData = literalNameData;

		return node;
	}
	
	/**
	 * Test the Identifier class type to make sure everything
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