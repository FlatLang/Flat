package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

/**
 * IfStatement extension that represents the declaration of a "until" statement type. See
 * {@link #decodeStatement(Node, String, Location, boolean)} for more details on what correct inputs
 * look like.
 *
 * @author Braden Steffaniak
 * @since v0.2.14 Jul 5, 2014 at 12:29:23 AM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Until extends IfStatement {
    /**
     * @see Node#Node(Node, Location)
     */
    public Until(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see IfStatement#pendingScopeFragment(Node)
     */
    @Override
    public boolean pendingScopeFragment(Node node) {
        return false;
    }

    /**
     * Decode the given statement into a Until instance, if possible. If it is not possible, this
     * method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * <li>until (currentNode != null)</li>
     * <li>until (ready)</li>
     * <li>until (number.isEven())</li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a Until instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a Until.
     */
    public static Until decodeStatement(Node parent, String statement, Location location,
        boolean require) {
        if (StringUtils.findNextWord(statement, 0).equals("until")) {
            Until n = new Until(parent, location);

            Bounds bounds = SyntaxUtils.findInnerParenthesesBounds(n, statement);

            if (bounds.isValid()) {
                Location newLoc = location.asNew();
                newLoc.addBounds(bounds.getStart(), bounds.getEnd());

                String contents = statement.substring(bounds.getStart(), bounds.getEnd());

                if (n.decodeCondition(contents, newLoc)) {
                    return n;
                }
            } else {
                SyntaxMessage.error("Until statement missing condition", n);
            }
        }

        return null;
    }

    /**
     * Decode the given condition of the until statement.
     *
     * @param contents The condition expression to decode.
     * @param location The location of the condition in the source code.
     * @return Whether or not the condition decoded successfully.
     */
    private boolean decodeCondition(String contents, Location location) {
        contents = "!(" + contents + ")";

        Value condition = UnaryOperation.decodeStatement(this, contents, location, true);// BinaryOperation.decodeStatement(getParent(),
                                                                                         // contents,
                                                                                         // location,
                                                                                         // true);

        if (condition == null) {
            condition = SyntaxTree.getUsableExistingNode(this, contents, location);

            if (condition == null) {
                condition = Literal.decodeStatement(this, contents, location, true, true);

                if (condition == null) {
                    return false;
                }
            }
        }

        addChild(condition, this);

        if (!"Bool".equals(condition.getReturnedNode().getType())) {
            condition.replaceWithNullCheck();
        }

        return true;
    }

    /**
     * Actually set up the until statement structure.
     *
     * @see Node#validate(int)
     */
    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
            String statement = "if (" + getCondition().generateFlatInput().toString() + ")";

            IfStatement clonable =
                IfStatement.decodeStatement(getScope(), statement, getLocationIn(), true);
            IfStatement clone = null;

            Scope scope = getScope();

            for (int i = scope.getNumVisibleChildren() - 1; i >= 0; i--) {
                IfStatement newClone = (IfStatement) clonable.clone(getScope(), getLocationIn());

                Node node = scope.getVisibleChild(i);

                newClone.addChild(node);

                if (clone != null) {
                    newClone.addChild(clone);
                }

                clone = newClone;
            }

            if (clone != null) {
                scope.addChild(clone);

                getParent().replace(this, clone);

                result.returnedNode = clone;
            }
        }

        return result;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Until clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        Until node = new Until(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Until cloneTo(Until node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Until} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Until cloneTo(Until node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the Until class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}

