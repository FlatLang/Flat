package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * Node extension that represents the declaration of a Loop
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:55:18 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Loop extends Node {
    /**
     * Instantiate a new Loop and initialize the default values.
     *
     * @see Node#Node(Node, Location)
     */
    public Loop(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        Scope scope = new Scope(this, locationIn.asNew());

        setScope(scope);
    }

    /**
     * @see Node#pendingScopeFragment(Node)
     */
    @Override
    public boolean pendingScopeFragment(Node node) {
        return getScope().isEmpty();
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
     * Decode the given statement into a Loop instance, if
     * possible. If it is not possible, this method returns null.<br>
     * The statement can be either a while loop or a for loop.
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>for (int i = 0; i &lt; 100; i++)</li>
     * 	<li>for (int i = 0; array != null &amp;&amp; i &lt; array.getSize(); i = num * 3 * i)</li>
     * 	<li>while (currentNode != null)</li>
     * 	<li>while (true)</li>
     * 	<li>while (number.isEven())</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  Loop instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a Loop.
     */
    public static Loop decodeStatement(Node parent, String statement, Location location, boolean require) {
        Loop node = null;

        if ((node = Repeat.decodeStatement(parent, statement, location, require)) != null) {
            return node;
        } else if ((node = ForEachLoop.decodeStatement(parent, statement, location, require)) != null) {
            return node;
        } else if ((node = ForLoop.decodeStatement(parent, statement, location, require)) != null) {
            return node;
        } else if ((node = WhileLoop.decodeStatement(parent, statement, location, require)) != null) {
            return node;
        }

        return null;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Loop clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Loop node = new Loop(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Loop cloneTo(Loop node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Loop} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Loop cloneTo(Loop node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the Loop class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}