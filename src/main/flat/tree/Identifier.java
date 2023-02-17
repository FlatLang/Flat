package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;
import flat.util.SyntaxUtils;


/**
 * Value extension that represents an Identifier. For the rules on
 * what can and cannot be an Identifier, refer to
 * {@link Identifier#setName(java.lang.String) setName}
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:00:19 PM
 * @version v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public abstract class Identifier extends Value implements Accessible {
    public boolean safeNavigation;
    public boolean chainNavigation;

    private SyntaxUtils.LiteralNameData literalNameData;

    /**
     * @see Node#Node(Node, Location)
     */
    public Identifier(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public boolean isSafeNavigation() {
        return safeNavigation;
    }

    @Override
    public void setSafeNavigation(boolean safeNavigation) {
        this.safeNavigation = safeNavigation;
    }

    @Override
    public boolean isChainNavigation() {
        return chainNavigation;
    }

    @Override
    public void setChainNavigation(boolean chainNavigation) {
        this.chainNavigation = chainNavigation;
    }

    /**
     * Get whether or not the Identifier is accessed in a non-static way.
     *
     * @return Whether or not the Identifier is accessed in a non-static
     * way.
     */
    public boolean isInstance() {
        return SyntaxUtils.getImportedClass(getFileDeclaration(), getName()) == null;
    }

    @Override
    public Value getReturnedNode() {
        return Accessible.super.getReturnedNode();
    }

//	@Override
//	public boolean isUserMade(boolean checkAncestor)
//	{
//		return !doesForceOriginalName() && super.isUserMade(checkAncestor);
//	}

    @Override
    public boolean isExternalType() {
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
     *             identifier.
     */
    public void setName(String name) {
        setName(name, false);
    }

    /**
     * Get whether or not the Identifier is a declaration.
     *
     * @return Whether or not the Identifier is a declaration.
     */
    public boolean isDeclaration() {
        return false;
    }

    /**
     * @see Node#generateFlatInput(StringBuilder, boolean)
     */
    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        builder.append(getName());
        generateFlatArrayAccess(builder);

        if (outputChildren) {
            generateAccessedNode(builder, safeNavigation);
        }

        return builder;
    }

    /**
     * @see Value#isVirtualTypeKnown()
     */
    @Override
    public boolean isVirtualTypeKnown() {
        if (isAccessedWithinStaticContext()) {
            return true;
        }

        if (isAccessed()) {
            if (getAccessingNode() instanceof Identifier) {
                Identifier id = (Identifier) getAccessingNode();

                return !id.isInstance();
            }
        }

        return false;
    }

    /**
     * Get the name of the Identifier. For the rules on what can and
     * cannot be an Identifier, refer to {@link Identifier#setName(java.lang.String) setName}
     *
     * @return The name of the Identifier.
     */
    public abstract String getName();

    /**
     * Set the name of the Variable. You specify whether or not you want
     * the output in the C language to be the original given name,
     * or if it will differentiate it depending on its scope.
     *
     * @param name          The String to set as the new name.
     * @param forceOriginal Whether or not the name will be output in the
     *                      c code verbatim.
     */
    public abstract void setName(String name, boolean forceOriginal);

    /**
     * Whether or not the output in the C language will be the
     * original given name, or if it will differentiate it depending on
     * its scope.
     *
     * @return Whether or not the name will be output in the C code
     * verbatim.
     */
    public abstract boolean doesForceOriginalName();

    /**
     * Whether or not you want the output in the C language to be the
     * original given name, or if it will differentiate it depending on
     * its scope.
     *
     * @param forceOriginal Whether or not the name will be output in the
     *                      c code verbatim.
     */
    public abstract void setForceOriginalName(boolean forceOriginal);

    public SyntaxUtils.LiteralNameData getLiteralNameData() {
        return literalNameData;
    }

    public void setLiteralNameData(SyntaxUtils.LiteralNameData literalNameData) {
        this.literalNameData = literalNameData;
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        result.returnedNode = checkSafeNavigation();

        return result;
    }

    /**
     * Fill the given {@link Identifier} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Identifier cloneTo(Identifier node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Identifier} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Identifier cloneTo(Identifier node, boolean cloneChildren, boolean cloneAnnotations) {
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
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}