package flat.tree;

import flat.TestContext;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

/**
 * Variable extension that represents the declaration of a field or method. Contains the modifiers
 * for visibility and whether or not the field/method is static.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:10:49 PM
 * @version v0.2.36 Oct 13, 2014 at 12:16:42 AM
 */
public class InstanceDeclaration extends VariableDeclaration {
    private boolean staticVal;

    private int visibility;

    /**
     * This visibility allows manipulation and view from only the class that it is declared in.
     */
    public static final int PRIVATE = 1;

    /**
     * This visibility allows manipulation and viewing from anywhere within its package.
     */
    public static final int PROTECTED = 2;

    /**
     * This visibility allows open manipulation and viewing from anywhere in a program.
     */
    public static final int PUBLIC = 3;

    /**
     * Instantiate a new InstanceDeclaration and initialize the default values.
     *
     * @see Node#Node(Node, Location)
     */
    public InstanceDeclaration(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        setVisibility(PRIVATE);
    }

    @Override
    public boolean isLocal() {
        return false;
    }

    /**
     * Get whether or not the specified InstanceDeclaration is static. Static variables/methods are
     * shared among each of its containing class's instances.
     *
     * @return Whether or not the specified InstanceDeclaration is static.
     */
    public boolean isStatic() {
        return staticVal;
    }

    /**
     * (WARNING: CURRENTLY ONLY RETURNS "static")<br>
     * Get the text that is associated with the static value of the specified
     * InstanceDeclaration.<br>
     * <br>
     * For example: If static is true, the method will return "static" if it is not, it will return
     * an empty String.
     *
     * @return The value representing the static value.
     */
    public String getStaticText() {
        return "static";
    }

    /**
     * Set whether or not the specified InstanceDeclaration is static. Static variables/methods are
     * shared among each of its containing class's instances.
     *
     * @param staticVal Whether or not to set it as static.
     */
    public void setStatic(boolean staticVal) {
        this.staticVal = staticVal;
    }

    /**
     * Get the value of the current visibility of the node.<br>
     * <br>
     * Possible values include:<br>
     * <ul>
     * <li>InstanceDeclaration.PRIVATE</li>
     * <li>InstanceDeclaration.PROTECTED</li>
     * <li>InstanceDeclaration.PUBLIC</li>
     * </ul>
     *
     * @return The current visibility value of the node.
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     * Determine whether or not the visibility valid. Essentially checking if the visibility value
     * is one of the available three values.<br>
     * <br>
     * Possible values include:<br>
     * <ul>
     * <li>InstanceDeclaration.PRIVATE</li>
     * <li>InstanceDeclaration.PROTECTED</li>
     * <li>InstanceDeclaration.PUBLIC</li>
     * </ul>
     *
     * @return Returns whether or not the visibility's value is valid.
     */
    public boolean isVisibilityValid() {
        return visibility >= PRIVATE && visibility <= PUBLIC;
    }

    /**
     * Get the text that is associated with the visibility of the specified InstanceDeclaration.<br>
     * <br>
     * For example: InstanceDeclaration.PRIVATE would return a String with the value of "private"
     *
     * @return The text that is associated with the visibility of the current node.
     */
    public String getVisibilityText() {
        if (visibility == PRIVATE) {
            return "private";
        } else if (visibility == PROTECTED) {
            return "protected";
        } else if (visibility == PUBLIC) {
            return "public";
        }

        return null;
    }

    /**
     * Set the visibility of the field or method that was declared.<br>
     * <br>
     * Possible options include:<br>
     * <ul>
     * <li>InstanceDeclaration.PRIVATE</li>
     * <li>InstanceDeclaration.PROTECTED</li>
     * <li>InstanceDeclaration.PUBLIC</li>
     * </ul>
     *
     * @param visibility The visibility of the field/method that was declared.
     */
    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    /**
     * @see VariableDeclaration#setAttribute(java.lang.String)
     */
    @Override
    public void setAttribute(String attribute) {
        setAttribute(attribute, -1);
    }

    /**
     * @see VariableDeclaration#setAttribute(java.lang.String, int)
     */
    @Override
    public boolean setAttribute(String attribute, int argNum) {
        if (super.setAttribute(attribute, argNum)) {
            return true;
        }

        if (argNum == 0) {
            if (attribute.equals("protected")) {
                setVisibility(PROTECTED);
            } else if (attribute.equals("external")) {
                setExternal(true);
            } else {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public InstanceDeclaration clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        InstanceDeclaration node = new InstanceDeclaration(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public InstanceDeclaration cloneTo(InstanceDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link InstanceDeclaration} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public InstanceDeclaration cloneTo(InstanceDeclaration node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.staticVal = staticVal;
        node.visibility = visibility;

        return node;
    }

    /**
     * Test the InstanceDeclaration class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}

