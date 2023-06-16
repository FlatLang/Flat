package flat.tree;

import flat.Flat;
import flat.TestContext;
import flat.util.Location;
import java.util.Arrays;
import java.util.HashMap;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 * @since v0.2.28 Aug 16, 2014 at 8:57:51 PM
 * @version v0.2.34 Oct 1, 2014 at 9:51:33 PM
 */
public class StaticBlock extends Node implements ScopeAncestor {
    private int uniqueID;

    private static HashMap<Integer, Scope> scopes = new HashMap<>();

    public static final String C_PREFIX = "_" + Flat.LANGUAGE_NAME + "_init_";

    public static final String IDENTIFIER = "static";

    /**
     * @see Node#Node(Node, Location)
     */
    public StaticBlock(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        Scope scope = new Scope(this, Location.INVALID);

        setScope(scope);

        ParameterList<Parameter> list = new ParameterList<Parameter>(this, Location.INVALID);

        addChild(list, this);
    }

    /**
     * @see Node#getScope()
     */
    @Override
    public Scope getScope() {
        return (Scope) getChild(super.getNumDefaultChildren() + 0);
    }

    @Override
    public int getUniqueID() {
        return ++uniqueID;
    }

    @Override
    public HashMap<Integer, Scope> getScopes() {
        return scopes;
    }

    public ParameterList getParameterList() {
        return (ParameterList) getChild(super.getNumDefaultChildren() + 1);
    }

    /**
     * Decode the given statement into a {@link StaticBlock} instance, if possible. If it is not
     * possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * <li>static</li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a {@link StaticBlock} instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a {@link StaticBlock}.
     */
    public static StaticBlock decodeStatement(Node parent, String statement, Location location,
        boolean require) {
        String[][] modifierData =
            SyntaxTree.getPrecedingModifiers(statement, parent, location, 0, 1);

        if (modifierData != null) {
            statement = modifierData[0][0];
        }

        if (statement.equals(IDENTIFIER)) {
            StaticBlock b = generateEmptyBlock(parent, location);

            if (modifierData != null) {
                if (!Arrays.stream(modifierData[1]).allMatch(b::parseModifier)) {
                    return null;
                }
            }

            return b;
        }

        return null;
    }

    public static StaticBlock generateEmptyBlock(Node parent, Location location) {
        return new StaticBlock(parent, location);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public StaticBlock clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        StaticBlock node = new StaticBlock(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public StaticBlock cloneTo(StaticBlock node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link StaticBlock} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public StaticBlock cloneTo(StaticBlock node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link StaticBlock} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}

