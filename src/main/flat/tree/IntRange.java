package flat.tree;

import flat.TestContext;
import flat.util.Location;
import flat.util.SyntaxUtils;

/**
 * {@link Instantiation} extension that represents
 *
 * @author Braden Steffaniak
 */
public class IntRange extends Instantiation {
    public static final String OPERATOR = "..";

    /**
     * @see Node#Node(Node, Location)
     */
    public IntRange(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link IntRange} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li></li>
     * 	<li></li>
     * 	<li></li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  {@link IntRange} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link IntRange}.
     */
    public static IntRange decodeStatement(Node parent, String statement, Location location, boolean require) {
        int operatorIndex = SyntaxUtils.findStringInBaseScope(statement, OPERATOR);

        if (operatorIndex > 0) {
            String startValueString = statement.substring(0, operatorIndex);
            String endValueString = statement.substring(operatorIndex + OPERATOR.length());

            IntRange n = new IntRange(parent, location);
			
			/*Value startValue = SyntaxTree.decodeValue(n, startValueString, location, require);
			
			if (startValue == null)
			{
				SyntaxMessage.queryError("Invalid start value '" + startValueString + "' for integer range", n, require);
				
				return null;
			}
			
			Value endValue = SyntaxTree.decodeValue(n, endValueString, location, require);
			
			if (endValue == null)
			{
				SyntaxMessage.queryError("Invalid end value '" + endValueString + "' for integer range", n, require);
				
				return null;
			}*/

            if (!startValueString.startsWith("(Int)")) {
                startValueString = "(Int)" + startValueString;
            }
            if (!endValueString.startsWith("(Int)")) {
                endValueString = "(Int)" + endValueString;
            }

            Instantiation inst = Instantiation.decodeStatement(parent, "IntRange(" + startValueString + ", " + endValueString + ")", location, false);

            if (inst == null) {
                //SyntaxMessage.queryError("Unable to decode integer range '" + statement + "'", n, require);

                return null;
            }

            inst.cloneTo(n);

            return n;
        }

        return null;
    }

    public Value getStartValue() {
        return (Value) ((MethodCall) getIdentifier()).getArgumentList().getVisibleChild(0);
    }

    public Value getEndValue() {
        return (Value) ((MethodCall) getIdentifier()).getArgumentList().getVisibleChild(1);
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        return getStartValue().generateFlatInput(builder).append(OPERATOR).append(getEndValue());
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public IntRange clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        IntRange node = new IntRange(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public IntRange cloneTo(IntRange node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link IntRange} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public IntRange cloneTo(IntRange node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link IntRange} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}