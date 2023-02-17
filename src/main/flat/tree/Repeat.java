package flat.tree;

import flat.TestContext;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

/**
 * {@link Loop} extension that represents
 *
 * @author Braden Steffaniak
 */
public class Repeat extends Loop {
    public static final String IDENTIFIER = "repeat";
    public static final String VARIABLE_NAME = "repeatCounter";

    private int id;

    private static int ids = 1;

    /**
     * @see Node#Node(Node, Location)
     */
    public Repeat(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        id = ids++;
    }

    public String getName() {
        return VARIABLE_NAME + id;
    }

    public Value getValueNode() {
        if (getNumVisibleChildren() > 0) {
            return (Value) getVisibleChild(0);
        }

        return null;
    }

    /**
     * Decode the given statement into a {@link Repeat} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>repeat</li>
     * 	<li>repeat (10)</li>
     * 	<li>repeat (string.count)</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  {@link Repeat} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link Repeat}.
     */
    public static Repeat decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (true) {
            return null;
        }
        String word = StringUtils.findNextWord(statement);

        Bounds end = new Bounds(0, word.length());

        if (word.equals(IDENTIFIER)) {
            Repeat n = new Repeat(parent, location);

            String rest = statement.substring(word.length());

            if (rest.length() > 0) {
                Bounds parenthesis = SyntaxUtils.findParenthesesBounds(n, rest);

                if (parenthesis != Bounds.EMPTY) {
                    Value value = SyntaxTree.decodeValue(parent, StringUtils.removeSurroundingParenthesis(parenthesis.extractString(rest)), location, require);

                    n.addChild(value, n);

                    end.setEnd(end.getEnd() + parenthesis.getEnd() + 1);

                    n.addIteratorDeclaration();
                }
            }

            if (n.decodeScopeFragment(statement, end.getEnd())) {
                return n;
            }
        }

        return null;
    }

    private void addIteratorDeclaration() {
        Scope scope = getParent().getAncestorWithScope().getScope();

        LocalDeclaration iterator = new LocalDeclaration(scope, getLocationIn());
        iterator.setName(getName(), true);
        iterator.setType("Int");

        scope.addChild(iterator);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Repeat clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Repeat node = new Repeat(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Repeat cloneTo(Repeat node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Repeat} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Repeat cloneTo(Repeat node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link Repeat} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}