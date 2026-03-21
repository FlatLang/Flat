package flat.tree.variables;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.tree.InstanceDeclaration;
import flat.tree.List;
import flat.tree.Node;
import flat.util.Location;

/**
 * Node extension that contains all of the Fields for a ClassDeclaration.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 10:00:50 PM
 * @version v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class FieldList extends List {
    /**
     * Instantiate and initialize default data.
     */
    public FieldList(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        InstanceFieldList privateFields = new InstanceFieldList(this, locationIn);
        InstanceFieldList publicFields = new InstanceFieldList(this, locationIn);
        StaticFieldList privateStaticFields = new StaticFieldList(this, locationIn);
        StaticFieldList publicStaticFields = new StaticFieldList(this, locationIn);

        super.addChild(privateFields);
        super.addChild(publicFields);
        super.addChild(publicStaticFields);
        super.addChild(privateStaticFields);
    }

    /**
     * @see Node#getNumDefaultChildren()
     */
    @Override
    public int getNumDefaultChildren() {
        return super.getNumDefaultChildren() + 4;
    }

    /**
     * Get the PrivateFieldList that contains all of the private Fields for its parent
     * ClassDeclaration.
     *
     * @return The PrivateFieldList instance.
     */
    public InstanceFieldList getPrivateFieldList() {
        return (InstanceFieldList) getChild(0);
    }

    /**
     * Get the PublicFieldList that contains all of the public Fields for its parent
     * ClassDeclaration.
     *
     * @return The PublicFieldList instance.
     */
    public InstanceFieldList getPublicFieldList() {
        return (InstanceFieldList) getChild(1);
    }

    /**
     * Get the PublicFieldList that contains all of the public Fields for its parent
     * ClassDeclaration.
     *
     * @return The PublicFieldList instance.
     */
    public StaticFieldList getPublicStaticFieldList() {
        return (StaticFieldList) getChild(2);
    }

    /**
     * Get the PrivateFieldList that contains all of the private Fields for its parent
     * ClassDeclaration.
     *
     * @return The PrivateFieldList instance.
     */
    public StaticFieldList getPrivateStaticFieldList() {
        return (StaticFieldList) getChild(3);
    }

    /**
     * @see Node#addChild(Node node)
     */
    @Override
    public void addChild(Node node) {
        if (node instanceof FieldDeclaration) {
            FieldDeclaration field = (FieldDeclaration) node;

            if (field.getVisibility() == InstanceDeclaration.PRIVATE) {
                if (field.isStatic()) {
                    getPrivateStaticFieldList().addChild(field);
                } else {
                    getPrivateFieldList().addChild(field);
                }
            } else if (field.getVisibility() == InstanceDeclaration.PUBLIC
                || field.getVisibility() == FieldDeclaration.VISIBLE) {
                if (field.isStatic()) {
                    getPublicStaticFieldList().addChild(field);
                } else {
                    getPublicFieldList().addChild(field);
                }
            } else {
                SyntaxMessage.error("Missing visibility declaration", this);
            }
        } else {
            SyntaxMessage.error("Unknown node being added", this);
        }
    }

    /**
     * Get whether or not the FieldList contains the Field with the specified name.<br>
     * <br>
     * For example: <blockquote>
     * 
     * <pre>
     * public class Person
     * {
     * 	private int age;
     * 	private String name;
     *
     * 	...
     *
     * }
     * </pre>
     * 
     * </blockquote> <br>
     * A call like: "<code>containsField("age")</code>" would return true.
     *
     * @param fieldName The name of the field to search for.
     * @return Whether or not the FieldList contains the Field with the specified name.
     */
    public boolean containsField(String fieldName) {
        return getField(fieldName) != null;
    }

    /**
     * Get the FieldList's Field with the specified name.<br>
     * <br>
     * For example: <blockquote>
     * 
     * <pre>
     * public class Person
     * {
     * 	private int age;
     * 	private String name;
     *
     * 	...
     *
     * }
     * </pre>
     * 
     * </blockquote> <br>
     * A call like: "<code>getField("age")</code>" would return the Field for the "<code>age</code>"
     * int field.
     *
     * @param fieldName The name of the field to search for.
     * @return The Field for the field, if it exists.
     */
    public FieldDeclaration getField(String fieldName) {
        Node nodes[] = new Node[] {getPrivateFieldList(), getPrivateStaticFieldList(),
            getPublicFieldList(), getPublicStaticFieldList()};

        for (Node node : nodes) {
            FieldDeclaration field = searchFieldList(node, fieldName);

            if (field != null) {
                return field;
            }
        }

        return null;
    }

    /**
     * Search the given Node (which should only contain Field children) for a Field with the given
     * name.
     *
     * @param fieldList The list of fields to search through.
     * @param fieldName The name of the field to search for.
     * @return The Field instance with the given name, if found.
     */
    private FieldDeclaration searchFieldList(Node fieldList, String fieldName) {
        for (int i = 0; i < fieldList.getNumChildren(); i++) {
            FieldDeclaration variable = (FieldDeclaration) fieldList.getChild(i);

            if (variable.getName().equals(fieldName)) {
                return variable;
            }
        }

        return null;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public FieldList clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        FieldList node = new FieldList(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public FieldList cloneTo(FieldList node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link FieldList} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public FieldList cloneTo(FieldList node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the FieldList class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    public String toString() {
        if (getNumVisibleChildren() == 0) {
            return "(none)";
        }

        String str = "";

        for (int i = 0; i < getNumVisibleChildren(); i++) {
            FieldDeclaration field = (FieldDeclaration) getVisibleChild(i);

            if (str.length() > 0) {
                str += "\n";
            }

            str += field.generateFlatInput();
        }

        return str;
    }
}

