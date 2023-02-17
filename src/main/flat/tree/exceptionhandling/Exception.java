package flat.tree.exceptionhandling;

import flat.TestContext;
import flat.tree.ClassDeclaration;
import flat.tree.Node;
import flat.util.Location;

/**
 * Node extension that represents an Exception instance that is to
 * be thrown within a Throw statement.
 * <blockquote><pre>
 * // Instantiate a new Exception type.
 * ExceptionName varName = new ExceptionName(... optional arguments ...);
 *
 * // Throw the generated Exception type.
 * throw varName;</pre></blockquote>
 * For more information on what
 * it looks like to throw an Exception, see
 * {@link Throw#decodeStatement(Node, String, Location, boolean)}.
 *
 * @author Braden Steffaniak
 * @since v0.1 Apr 6, 2014 at 8:44:35 PM
 * @version v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class Exception extends Node {
    public boolean soft = false;

    public ClassDeclaration type;

    /**
     * Identifier for the exception data passed to a method.
     */
    public static final String EXCEPTION_DATA_IDENTIFIER = "exceptionData";

    /**
     * @see Node#Node(Node, Location)
     */
    public Exception(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Set the type of Exception that is being generated. If the type
     * is unique to anything created prior to this, the id of the
     * generated Exception will also be unique. If the Exception
     * type has already been used before, it will use the already
     * generated id that belongs to the Exception.
     *
     * @param type The name (type) of the Exception that is being
     *             generated.
     */
    public void setType(ClassDeclaration type) {
        this.type = type;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Exception clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Exception node = new Exception(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Exception cloneTo(Exception node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Exception} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Exception cloneTo(Exception node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.type = type;

        return node;
    }

    /**
     * Test the Exception class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}