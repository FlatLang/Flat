package flat.tree;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

/**
 * Loop extension that represents the declaration of a "while loop"
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:55:59 PM
 * @version v0.2.37 Oct 16, 2014 at 11:38:42 PM
 */
public class WhileLoop extends Loop {
    public static final String IDENTIFIER = "while";

    /**
     * @see Node#Node(Node, Location)
     */
    public WhileLoop(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#getNumDefaultChildren()
     */
    @Override
    public int getNumDecodedChildren() {
        return super.getNumDecodedChildren() + 1;
    }

    /**
     * Get the Node that describes the condition section of the while
     * loop. For instance: "while (i &lt; 10)" the contents between the
     * parenthesis is the condition.
     *
     * @return The Node instance that describes the condition section
     * of the while loop.
     */
    public Node getCondition() {
        return getChild(1);
    }

    /**
     * Decode the given statement into a WhileLoop instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>while (currentNode != null)</li>
     * 	<li>while (true)</li>
     * 	<li>while (number.isEven())</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  WhileLoop instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a WhileLoop.
     */
    public static WhileLoop decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (statement.startsWith(IDENTIFIER) && StringUtils.findNextWord(statement).equals(IDENTIFIER)) {
            WhileLoop n = new WhileLoop(parent, location);
            Bounds bounds = SyntaxUtils.findParenthesesBounds(n, statement);

            if (!bounds.extractPreString(statement).trim().equals(IDENTIFIER)) {
                SyntaxMessage.queryError("Incorrect " + IDENTIFIER + " loop definition", n, require);

                return null;
            }

            bounds = StringUtils.removeSurroundingParenthesis(statement, bounds);

            if (bounds.isValid()) {
                Location newLoc = location.asNew();
                newLoc.addBounds(bounds.getStart(), bounds.getEnd());

                String contents = statement.substring(bounds.getStart(), bounds.getEnd());

                if (n.decodeCondition(contents, newLoc) && n.decodeScopeFragment(statement, bounds)) {
                    return n;
                }

                SyntaxMessage.error("Could not decode while loop condition '" + contents + "'", n);
            } else {
                SyntaxMessage.error("While loop missing condition", n);
            }
        }

        return null;
    }

    /**
     * Decode the given condition of the if statement.
     *
     * @param contents The condition expression to decode.
     * @param location The location of the condition in the source code.
     * @return Whether or not the condition decoded successfully.
     */
    private boolean decodeCondition(String contents, Location location) {
        Value condition = SyntaxTree.decodeValue(this, contents, location, true);

        addChild(condition, this);

        if (!"Bool".equals(condition.getReturnedNode().getType())) {
            condition.replaceWithNullCheck();
        }

        return true;
    }

    /**
     * Check to see if the while loop's condition is a method call.
     * Also validate that the return type of the method is a bool.
     *
     * @param contents The contents of the while loop's condition.
     * @param location The location of the while loop's condition.
     * @return The decoded while loop's condition.
     */
    private MethodCall checkMethodCallCondition(String contents, Location location) {
        MethodCall call = MethodCall.decodeStatement(this, contents, location, false);

        if (call != null && call.getTypeClassLocation().equals("flat/primitive/Bool")) {
            return call;
        }

        return null;
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        builder.append(IDENTIFIER).append(" (").append(getCondition().generateFlatInput()).append(") ");

        if (outputChildren) {
            getScope().generateFlatInput(builder);
        }

        return builder;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public WhileLoop clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        WhileLoop node = new WhileLoop(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public WhileLoop cloneTo(WhileLoop node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link WhileLoop} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public WhileLoop cloneTo(WhileLoop node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the WhileLoop class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}