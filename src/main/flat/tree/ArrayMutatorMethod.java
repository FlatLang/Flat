package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 * @since v0.2.37 Oct 18, 2014 at 9:00:50 PM
 * @version v0.2.39 Dec 7, 2014 at 3:17:17 AM
 */
public class ArrayMutatorMethod extends ArrayOverloadMethod {
    public static final String DISABLED_IDENTIFIER = "no";
    public static final String IDENTIFIER = "set";

    /**
     * @see Node#Node(Node, Location)
     */
    public ArrayMutatorMethod(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link ArrayMutatorMethod} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>no set</li>
     * 	<li>set</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  {@link ArrayMutatorMethod} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link ArrayMutatorMethod}.
     */
    public static ArrayMutatorMethod decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (StringUtils.findNextWord(statement).equals(DISABLED_IDENTIFIER)) {
            String remainder = statement.substring(DISABLED_IDENTIFIER.length() + 1).trim();

            if (remainder.equals(IDENTIFIER)) {
                ArrayMutatorMethod n = new ArrayMutatorMethod(parent, location);

                n.setName(IDENTIFIER);
                n.getArrayBracketOverload().cloneTo(n, false, true);
                n.setLocationIn(location);
                n.setType(n.getArrayBracketOverload());
                n.setDisabled(true);

                n.addIndexParameter();
                n.addDefaultParameter();

                return n;
            }
        } else if (StringUtils.findNextWord(statement).equals(IDENTIFIER)) {
            ArrayMutatorMethod n = new ArrayMutatorMethod(parent, location);

            n.setName(IDENTIFIER);
            n.getArrayBracketOverload().cloneTo(n, false, true);
            n.setLocationIn(location);
            n.setType(n.getArrayBracketOverload());

            n.addIndexParameter();

            if (StringUtils.findNextNonWhitespaceChar(statement, IDENTIFIER.length()) == '(') {
                String parameterList = SyntaxUtils.findInnerParenthesesBounds(n, statement).extractString(statement);

                if (!n.decodeParameters(parameterList, require)) {
                    return null;
                }
            } else {
                n.addDefaultParameter();
            }

            return n;
        }

        return null;
    }

    private void addDefaultParameter() {
        Parameter p = Parameter.decodeStatement(this, getArrayBracketOverload().generateFlatType() + " " + PARAMETER_NAME, getLocationIn().asNew(), true);
        getArrayBracketOverload().cloneTo(p, false, false);
        p.setName(PARAMETER_NAME);

        getParameterList().addChild(p);
    }

    /**
     * @see Node#validate(int)
     */
    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
            if (!isDisabled()) {
                Return returnValue = (Return) SyntaxTree.decodeScopeContents(this, "return " + getParameter(1).getName(), getLocationIn());

                if (returnValue == null) {
                    SyntaxMessage.error("Could not decode implicit return statement for mutator method", this);
                }

                addChild(returnValue);
            }
        }

        return result;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ArrayMutatorMethod clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ArrayMutatorMethod node = new ArrayMutatorMethod(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ArrayMutatorMethod cloneTo(ArrayMutatorMethod node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ArrayMutatorMethod} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ArrayMutatorMethod cloneTo(ArrayMutatorMethod node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ArrayMutatorMethod} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}