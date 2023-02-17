package flat.tree.exceptionhandling;

import flat.TestContext;
import flat.tree.Node;
import flat.tree.Scope;
import flat.util.Location;

/**
 * Node extension that represents the declaration of an exception
 * handling node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 *
 * @author Braden Steffaniak
 * @since v0.1 Mar 21, 2014 at 10:50:26 PM
 * @version v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class ExceptionHandler extends Node {
    /**
     * Instantiate and initialize default values.
     */
    public ExceptionHandler(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        Scope scope = new Scope(this, locationIn);

        setScope(scope);
    }

    /**
     * @see Node#getNumDefaultChildren()
     */
    @Override
    public int getNumDefaultChildren() {
        return super.getNumDefaultChildren() + 1;
    }

    /**
     * @see Node#getScope()
     */
    @Override
    public Scope getScope() {
        return (Scope) getChild(super.getNumDefaultChildren());
    }

    /**
     * Decode the given statement into a ExceptionHandler instance,
     * if possible. If it is not possible, this method returns null.
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>try</li>
     * 	<li>catch (Exception e)</li>
     * 	<li>finally</li>
     * 	<li>throw new IOException()</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  ExceptionHandler instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a ExceptionHandler.
     */
    public static ExceptionHandler decodeStatement(Node parent, String statement, Location location, boolean require) {
        ExceptionHandler node = Try.decodeStatement(parent, statement, location, require);

        if (node == null) {
            node = Catch.decodeStatement(parent, statement, location, require);

            if (node == null) {
                node = Finally.decodeStatement(parent, statement, location, require);
            }
        }

        return node;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ExceptionHandler clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ExceptionHandler node = new ExceptionHandler(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ExceptionHandler cloneTo(ExceptionHandler node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ExceptionHandler} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ExceptionHandler cloneTo(ExceptionHandler node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the ExceptionHandler class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}