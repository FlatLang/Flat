package flat.tree;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.tree.variables.VariableDeclaration;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

/**
 * Loop extension that represents the declaration of a "for loop"
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:55:15 PM
 * @version v0.2.36 Oct 13, 2014 at 12:16:42 AM
 */
public class ForLoop extends Loop {
    public static final String IDENTIFIER = "for";

    public LocalDeclaration elementDeclaration;

    /**
     * Instantiate a new ForLoop and initialize its default values.
     *
     * @see Node#Node(Node, Location)
     */
    public ForLoop(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        ArgumentList argumentsNode = new ArgumentList(this, locationIn);

        addChild(argumentsNode, this);
    }

    @Override
    public VariableDeclaration searchVariable(Node parent, Scope scope, String name, boolean checkAncestors) {
        if (elementDeclaration != null && name.equals(elementDeclaration.getName())) {
            return elementDeclaration;
        }

        return super.searchVariable(parent, scope, name, checkAncestors);
    }

    /**
     * @see Node#getNumDefaultChildren()
     */
    @Override
    public int getNumDefaultChildren() {
        return super.getNumDefaultChildren() + 1;
    }

    /**
     * Get the ArgumentList instance that contains the initialization,
     * condition, and update nodes that instruct the for loop.
     *
     * @return The ArgumentList instance containing the arguments of
     * the for loop.
     */
    public ArgumentList getArgumentList() {
        return (ArgumentList) getChild(1);
    }

    /**
     * Get the Node that describes the initialization section of the
     * for loop. For instance: "for (int i = 0; i &lt; 10; i++)" the first
     * section containing "int i = 0" is the initialization section.
     *
     * @return The Node instance that describes the initialization
     * section of the for loop.
     */
    public Assignment getLoopInitialization() {
        return (Assignment) getArgumentList().getChild(0);
    }

    /**
     * Get the Node that describes the condition section of the for
     * loop. For instance: "for (int i = 0; i &lt; 10; i++)" the middle
     * section containing "i &lt; 10" is the condition section.
     *
     * @return The Node instance that describes the condition section
     * of the for loop.
     */
    public Node getCondition() {
        return getArgumentList().getChild(1);
    }

    /**
     * Get the Node that describes the update section of the for loop.
     * For instance: "for (int i = 0; i &lt; 10; i++)" the last section
     * containing "i++" is the update section.
     *
     * @return The Node instance that describes the update section of
     * the for loop.
     */
    public Node getLoopUpdate() {
        return getArgumentList().getChild(2);
    }

    /**
     * Decode the given statement into a ForLoop instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>for (int i = 0; i &lt; array.size(); i++)</li>
     * 	<li>for (index = size - 1; index &gt;= 0; index -= 2) <i>(Where index and size are already declared variables)</i></li>
     * 	<li>for (;;) <i>(This is an infinite loop. Preferably use "while (true)" instead.)</i></li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  ForLoop instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a ForLoop.
     */
    public static ForLoop decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (statement.startsWith(IDENTIFIER) && StringUtils.findNextWord(statement).equals(IDENTIFIER)) {
            ForLoop n = new ForLoop(parent, location);
            Bounds bounds = SyntaxUtils.findParenthesesBounds(n, statement);

            if (!bounds.isValid()) {
                SyntaxMessage.queryError("Incorrect " + IDENTIFIER + " loop definition", n, require);

                return null;
            }

            if (!bounds.extractPreString(statement).trim().equals(IDENTIFIER)) {
                SyntaxMessage.queryError("Incorrect " + IDENTIFIER + " loop definition", n, require);

                return null;
            }

            bounds = StringUtils.removeSurroundingParenthesis(statement, bounds);

            if (bounds.getStart() >= 0) {
                String contents = statement.substring(bounds.getStart(), bounds.getEnd());

                if (n.decodeArguments(contents, bounds, require) && n.decodeScopeFragment(statement, bounds)) {
                    return n;
                }
            } else {
                SyntaxMessage.error("For loop missing arguments", n);
            }
        }

        return null;
    }

    /**
     * Decode all of the arguments given within the for loop.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * for (int i = 0; i < 1000; i++)</pre></blockquote>
     * In the above for loop declaration, the arguments are the data
     * that is contained within the parentheses. In this case that data
     * is: "<code>int i = 0; i < 1000; i++</code>"
     *
     * @param contents The contents within the parentheses of the for
     *                 loop declaration.
     * @param bounds   The bounds that the contents are located within.
     * @param require  Whether or not this should throw an exception if
     *                 anything bad happens.
     * @return Whether or not all of the arguments decoded correctly.
     */
    private boolean decodeArguments(String contents, Bounds bounds, boolean require) {
        Location newLoc = new Location(getLocationIn());
        newLoc.addBounds(bounds.getStart(), bounds.getEnd());

        String arguments[] = contents.split("\\s*;\\s*");

        return arguments.length >= 3 &&
            decodeInitialization(arguments[0], newLoc, require) &&
            decodeCondition(arguments[1], newLoc, require) &&
            decodeUpdate(arguments[2], newLoc, require);
    }

    /**
     * Decode the first argument of the for loop declaration. The first
     * argument is the initialization argument.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * for (int i = 0; i < 1000; i++)</pre></blockquote>
     * In the above for loop declaration, the initialization argument is
     * the first argument within the parentheses. In this case that data
     * is: "<code>int i = 0</code>"
     *
     * @param argument The variable initialization that will be used
     *                 within the for loop.
     * @param location The location that the argument is being decoded at.
     * @param require  Whether or not this should throw an exception if
     *                 anything bad happens.
     * @return Whether or not the initialization argument decoded
     * correctly.
     */
    private boolean decodeInitialization(String argument, Location location, boolean require) {
        Assignment initialization = Assignment.decodeStatement(this, argument, getLocationIn(), require);

        if (initialization == null) {
            return false;
        }

        initialization.wasDeclaration = true;
        initialization.onAfterDecoded();
        getArgumentList().addChild(initialization);

        elementDeclaration = (LocalDeclaration) initialization.getAssignedNode().declaration;

        elementDeclaration.setProperty("forLoopVariable", true);
        elementDeclaration.setProperty("userMade", false);

        elementDeclaration.detach();
        elementDeclaration.parent = this;

        return true;
    }

    /**
     * Decode the second argument of the for loop declaration. The second
     * argument is the condition argument.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * for (int i = 0; i < 1000; i++)</pre></blockquote>
     * In the above for loop declaration, the condition argument is
     * the second argument within the parentheses. In this case that data
     * is: "<code>i < 1000</code>"
     *
     * @param argument The boolean condition that is used to determine
     *                 when the loop stops looping.
     * @param location The location that the argument is being decoded at.
     * @param require  Whether or not this should throw an exception if
     *                 anything bad happens.
     * @return Whether or not the condition argument decoded correctly.
     */
    private boolean decodeCondition(String argument, Location location, boolean require) {
        /*
         * 	until (condition != null)
         * 	{
         * 		condition = BinaryOperation.decodeStatement(getParent(), argument, location, require),
         * 		condition = SyntaxTree.getUsableExistingNode(getArgumentList(), argument, location),
         *
         * 		return false
         * 	}
         *
         * 	getArgumentList().addChild(condition)
         *
         * 	return true
         */

        Value condition = SyntaxTree.decodeValue(getArgumentList(), argument, location, require);

        if (condition == null) {
            return false;
        }

        getArgumentList().addChild(condition);

        if (!"Bool".equals(condition.getReturnedNode().getType())) {
            condition.replaceWithNullCheck();
        }

        return true;
    }

    /**
     * Decode the third argument of the for loop declaration. The third
     * argument is the update argument.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * for (int i = 0; i < 1000; i++)</pre></blockquote>
     * In the above for loop declaration, the update argument is
     * the third argument within the parentheses. In this case that data
     * is: "<code>i++</code>"
     *
     * @param argument The variable update.
     * @param location The location that the argument is being decoded at.
     * @param require  Whether or not this should throw an exception if
     *                 anything bad happens.
     * @return Whether or not the update argument decoded correctly.
     */
    private boolean decodeUpdate(String argument, Location location, boolean require) {
        UnaryOperation unaryUpdate = UnaryOperation.decodeStatement(getArgumentList(), argument, location, require);

        if (unaryUpdate != null) {
            getArgumentList().addChild(unaryUpdate);
        } else {
            Value update = SyntaxTree.decodeValue(getArgumentList(), argument, location, require);

            if (update == null || (update instanceof Assignment == false && (update instanceof BinaryOperation == false || !((BinaryOperation) update).getOperator().isShorthand()))) {
                return false;
            }

            getArgumentList().addChild(update);
        }

        return true;
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray) {
        builder.append("for (var ").append(getLoopInitialization().generateFlatInput()).append("; ").append(getCondition().generateFlatInput()).append("; ").append(getLoopUpdate().generateFlatInput()).append(")");

        if (outputChildren) {
            getScope().generateFlatInput(builder);
        }

        return builder;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ForLoop clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ForLoop node = new ForLoop(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ForLoop cloneTo(ForLoop node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ForLoop} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ForLoop cloneTo(ForLoop node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the ForLoop class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}