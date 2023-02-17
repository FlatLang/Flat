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
public class MutatorMethod extends PropertyMethod {
    public static final String DISABLED_IDENTIFIER = "no";
    public static final String IDENTIFIER = "set";

    /**
     * @see Node#Node(Node, Location)
     */
    public MutatorMethod(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public String getMethodPrefix() {
        return "Mutator";
    }

    /**
     * Decode the given statement into a {@link MutatorMethod} instance, if
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
     *                  {@link MutatorMethod} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link MutatorMethod}.
     */
    public static MutatorMethod decodeStatement(Node parent, String statement, Location location, boolean require) {
        return decodeStatement(parent, statement, location, require, null);
    }

    public static MutatorMethod decodeStatement(Node parent, String statement, Location location, boolean require, Value type) {
        type = type == null ? (Value) parent : type;

        if (StringUtils.findNextWord(statement).equals(DISABLED_IDENTIFIER)) {
            String remainder = statement.substring(DISABLED_IDENTIFIER.length() + 1).trim();

            if (remainder.equals(IDENTIFIER)) {
                MutatorMethod n = new MutatorMethod(parent, location);
                n.setName(n.getParentField().getName());
                n.setType(type.getType());
                n.setDisabled(true);

                return n;
            }
        } else if (StringUtils.findNextWord(statement).equals(IDENTIFIER)) {
            MutatorMethod n = new MutatorMethod(parent, location);

            n.setName(n.getParentField().getName());
            n.setType(type);
            n.setVisibility(n.getParentField().getVisibility());

            if (StringUtils.findNextNonWhitespaceChar(statement, IDENTIFIER.length()) == '(') {
                String parameterList = SyntaxUtils.findInnerParenthesesBounds(n, statement).extractString(statement);

                if (!n.decodeParameters(parameterList, require)) {
                    return null;
                }
            } else {
                n.addDefaultParameter(type);
            }

            return n;
        }

        return null;
    }

    private void addDefaultParameter(Value type) {
        Parameter p = Parameter.decodeStatement(this, type.generateFlatType() + " " + PARAMETER_NAME, getLocationIn().asNew(), true);
        getParentField().cloneTo(p, true, false);
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
                Return returnValue = (Return) SyntaxTree.decodeScopeContents(this, "return " + getParameter(0).getName(), getLocationIn());

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
    public MutatorMethod clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        MutatorMethod node = new MutatorMethod(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public MutatorMethod cloneTo(MutatorMethod node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link MutatorMethod} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public MutatorMethod cloneTo(MutatorMethod node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link MutatorMethod} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}