package flat.tree.exceptionhandling;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.tree.generics.GenericTypeArgumentList;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;

/**
 * ExceptionHandler extension that represents the declaration of a throw node type. See
 * {@link #decodeStatement(Node, String, Location, boolean)} for more details on what correct inputs
 * look like.
 *
 * @author Braden Steffaniak
 * @since v0.1 Mar 22, 2014 at 11:02:52 PM
 * @version v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class Throw extends Value {
    public boolean soft = false;

    public static final String IDENTIFIER = "throw";
    public static final String SOFT_IDENTIFIER = "toss";

    /**
     * @see Node#Node(Node, Location)
     */
    public Throw(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#getNumDecodedChildren()
     */
    @Override
    public int getNumDecodedChildren() {
        return super.getNumDecodedChildren() + 2;
    }

    /**
     * Get the Exception that contains the information about the type of exception that was thrown.
     *
     * @return The Exception instance that contains the information about the exception type.
     */
    public Exception getException() {
        return (Exception) getChild(super.getNumDefaultChildren() + 0);
    }

    /**
     * Get the Exception Object Instance that is being thrown by the specified Throw instance.
     *
     * @return The Exception Object Instance that is being thrown.
     */
    public Identifier getExceptionInstance() {
        return (Identifier) getChild(super.getNumDefaultChildren() + 1);
    }

    /**
     * @see Node#generateFlatInput(java.lang.StringBuilder, boolean)
     */
    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        return builder.append(IDENTIFIER).append(" ")
            .append(getExceptionInstance().generateFlatInput());
    }

    /**
     * Decode the given statement into a Throw instance, if possible. If it is not possible, this
     * method returns null. <br>
     * Example inputs include:<br>
     * <ul>
     * <li>throw new IOException()</li>
     * <li>throw exceptionInstance;</li>
     * <li>throw new IllegalArgumentException()</li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a Throw instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a Throw.
     */
    public static Throw decodeStatement(Node parent, String statement, Location location,
        boolean require) {
        boolean soft = StringUtils.startsWithWord(statement, SOFT_IDENTIFIER);
        boolean hard = !soft && StringUtils.startsWithWord(statement, IDENTIFIER);

        if (soft || hard) {
            Throw n = new Throw(parent, location);
            n.soft = soft;

            Location newLoc = location.asNew();

            Bounds bounds = n.calculateThrowContents(statement);
            String thrown = statement.substring(bounds.getStart(), bounds.getEnd());

            newLoc.addBounds(bounds.getStart(), bounds.getEnd());

            if (n.decodeThrownNode(thrown, newLoc, require)) {
                return n;
            }
        }

        return null;
    }

    /**
     * Calculate the Bounds that contain the contents that the Throw is throwing.
     *
     * @param statement The statement containing the data.
     * @return The Bounds of the contents of the Throw.
     */
    private Bounds calculateThrowContents(String statement) {
        Bounds bounds = new Bounds(StringUtils.findNextNonWhitespaceIndex(statement,
            (soft ? SOFT_IDENTIFIER.length() : IDENTIFIER.length()) + 1), statement.length());

        if (!bounds.isValid()) {
            SyntaxMessage.error("Throw statement missing exception type", this);
        }

        return bounds;
    }

    /**
     * Decode the identifier that is being thrown by the Throw statement.
     *
     * @param thrown The data representing what is being thrown.
     * @param location The location that the data is within the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return Whether or not the data was decoded successfully.
     */
    private boolean decodeThrownNode(String thrown, Location location, boolean require) {
        Node thrownNode = SyntaxTree.decodeScopeContents(this, thrown, location, require);

        if (!(thrownNode instanceof Identifier)) {
            SyntaxTree.decodeScopeContents(this, thrown, location, require);
            SyntaxMessage.error("Incorrect form of exception thrown", this);
        }

        Identifier node = (Identifier) thrownNode;
        Exception exception = new Exception(this, location);
        exception.setType(node.getTypeClass());

        addChild(exception, this);
        addChild(node, this);

        return true;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Throw clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        Throw node = new Throw(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Throw cloneTo(Throw node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Throw} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Throw cloneTo(Throw node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    @Override
    public GenericTypeArgumentList getGenericTypeArgumentList() {
        return null;
    }

    /**
     * Test the Throw class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    @Override
    public String getType(boolean checkCast) {
        return "Object";
    }

    @Override
    public Value getArrayTypeValue() {
        return null;
    }

    @Override
    public String getTypeStringValue() {
        return "Object";
    }

    @Override
    public Type getTypeObject() {
        return null;
    }

    @Override
    public void setTypeValue(String type) {

    }

    @Override
    public int getArrayDimensions() {
        return 0;
    }

    @Override
    public void setArrayDimensions(int arrayDimensions) {

    }

    @Override
    public byte getDataType(boolean checkGeneric) {
        return getDataType(checkGeneric, true);
    }

    public byte getDataType(boolean checkGeneric, boolean checkCast) {
        return 0;
    }

    @Override
    public void setDataType(byte type) {

    }
}

