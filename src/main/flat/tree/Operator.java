package flat.tree;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import static java.util.Arrays.stream;

/**
 * Node extension that represents an operator in an operation.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:19:40 PM
 * @version v0.2.36 Oct 13, 2014 at 12:16:42 AM
 */
public class Operator extends IValue {
    public String operator;

    // Logical operators
    public static final String AND = "&&", AND_C = "&&";
    public static final String OR = "||", OR_C = "||";

    // Math operators
    public static final String DIVIDE = "/", DIVIDE_C = "/";
    public static final String MULTIPLY = "*", MULTIPLY_C = "*";
    public static final String MODULO = "%", MODULO_C = "%";
    public static final String ADD = "+", ADD_C = "+";
    public static final String SUBTRACT = "-", SUBTRACT_C = "-";
    public static final String ASSIGN = "=", ASSIGN_C = "=";
    public static final String BANG = "!", BANG_C = "!";
    public static final String EQUALS = "==", EQUALS_C = "==";
    public static final String NOT_EQUAL = "!=", NOT_EQUAL_C = "!=";
    public static final String INCREMENT = "++", INCREMENT_C = "++";
    public static final String DECREMENT = "--", DECREMENT_C = "--";
    public static final String BITWISE_AND = "&", BITWISE_AND_C = "&";
    public static final String BITWISE_OR = "|", BITWISE_OR_C = "|";
    public static final String BITWISE_XOR = "^", BITWISE_XOR_C = "^";
    public static final String BITWISE_COMPLEMENT = "~", BITWISE_COMPLEMENT_C = "~";
    public static final String GREATER = ">", GREATER_C = ">";
    public static final String LESS = "<", LESS_C = "<";
    public static final String GREATER_EQ = ">=", GREATER_EQ_C = ">=";
    public static final String LESS_EQ = "<=", LESS_EQ_C = "<=";
    public static final String L_SHIFT = "<<", L_SHIFT_C = "<<";
    public static final String R_SHIFT = ">>", R_SHIFT_C = ">>";
    public static final String UR_SHIFT = ">>>", UR_SHIFT_C = ">>";

    public static final String SHORTHAND_DIVIDE = "/=", SHORTHAND_DIVIDE_C = "/=";
    public static final String SHORTHAND_MULTIPLY = "*=", SHORTHAND_MULTIPLY_C = "*=";
    public static final String SHORTHAND_MODULO = "%=", SHORTHAND_MODULO_C = "%=";
    public static final String SHORTHAND_ADD = "+=", SHORTHAND_ADD_C = "+=";
    public static final String SHORTHAND_SUBTRACT = "-=", SHORTHAND_SUBTRACT_C = "-=";
    public static final String SHORTHAND_BITWISE_AND = "&=", SHORTHAND_BITWISE_AND_C = "&=";
    public static final String SHORTHAND_BITWISE_OR = "|=", SHORTHAND_BITWISE_OR_C = "|=";
    public static final String SHORTHAND_BITWISE_XOR = "^=", SHORTHAND_BITWISE_XOR_C = "^=";
    public static final String SHORTHAND_L_SHIFT = "<<=", SHORTHAND_L_SHIFT_C = "<<=";
    public static final String SHORTHAND_R_SHIFT = ">>=", SHORTHAND_R_SHIFT_C = ">>=";
    public static final String SHORTHAND_UR_SHIFT = ">>>=", SHORTHAND_UR_SHIFT_C = ">>=";

    public static final String OPERATORS[] =
        new String[] {SHORTHAND_ADD, SHORTHAND_SUBTRACT, SHORTHAND_MULTIPLY,
            SHORTHAND_DIVIDE, SHORTHAND_MODULO, SHORTHAND_L_SHIFT,
            SHORTHAND_R_SHIFT, SHORTHAND_BITWISE_AND, SHORTHAND_BITWISE_OR,
            AND, OR, DIVIDE, MULTIPLY, MODULO, INCREMENT, DECREMENT,
            EQUALS, BANG, ASSIGN, NOT_EQUAL, ADD, SUBTRACT,
            L_SHIFT, UR_SHIFT, R_SHIFT, GREATER_EQ, LESS_EQ, GREATER, LESS,
            BITWISE_AND, BITWISE_OR, BITWISE_XOR, BITWISE_COMPLEMENT};

    public static final String UNARY_OPERATORS[] =
        new String[] {BANG, INCREMENT, DECREMENT, SUBTRACT, BITWISE_COMPLEMENT};
    public static final String UNARY_OPERATORS_NO_MINUS[] =
        new String[] {BANG, INCREMENT, DECREMENT, BITWISE_COMPLEMENT};
    public static final String MINUS[] = new String[] {SUBTRACT};

    public static final String BINARY_OPERATORS[] =
        new String[] {SHORTHAND_ADD, SHORTHAND_SUBTRACT, SHORTHAND_MULTIPLY,
            SHORTHAND_DIVIDE, SHORTHAND_MODULO, SHORTHAND_L_SHIFT,
            SHORTHAND_R_SHIFT, SHORTHAND_BITWISE_AND, SHORTHAND_BITWISE_OR,
            AND, OR, DIVIDE, MULTIPLY, MODULO, ADD, SUBTRACT,
            EQUALS, NOT_EQUAL, ASSIGN, GREATER_EQ, LESS_EQ, L_SHIFT,
            UR_SHIFT, R_SHIFT, GREATER, LESS, BITWISE_AND, BITWISE_OR,
            BITWISE_XOR};

    public static final String LOGICAL_OPERATORS[] =
        new String[] {SHORTHAND_ADD, SHORTHAND_SUBTRACT, SHORTHAND_MULTIPLY,
            SHORTHAND_DIVIDE, SHORTHAND_MODULO, SHORTHAND_L_SHIFT,
            SHORTHAND_R_SHIFT, SHORTHAND_BITWISE_AND, SHORTHAND_BITWISE_OR,
            AND, OR, DIVIDE, MULTIPLY, MODULO, ADD, SUBTRACT,
            EQUALS, NOT_EQUAL, GREATER_EQ, LESS_EQ, L_SHIFT,
            UR_SHIFT, R_SHIFT, GREATER, LESS, BITWISE_AND, BITWISE_OR,
            BITWISE_XOR, BITWISE_COMPLEMENT};

    public static final String SHORTHAND_OPERATORS[] =
        new String[] {SHORTHAND_ADD, SHORTHAND_SUBTRACT, SHORTHAND_MULTIPLY,
            SHORTHAND_DIVIDE, SHORTHAND_MODULO, SHORTHAND_L_SHIFT,
            SHORTHAND_R_SHIFT, SHORTHAND_BITWISE_AND, SHORTHAND_BITWISE_OR,
            SHORTHAND_UR_SHIFT, SHORTHAND_BITWISE_XOR};

    /**
     * @see Node#Node(Node, Location)
     */
    public Operator(Node temporaryParent, Location locationIn) {
        this(temporaryParent, locationIn, null);
    }

    public Operator(Node temporaryParent, Location locationIn, String operator) {
        super(temporaryParent, locationIn);

        this.operator = operator;
    }

    /**
     * Get the value of the operator. For example: '+', '*', 'and', etc.
     *
     * @return The value of the operator.
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Set the operator value. For example: '+', '*', 'and', etc.
     *
     * @param operator The new value of the operator.
     */
    public void setOperator(String operator) {
        this.operator = operator;

        updateType();
    }

    public ClassDeclaration getOperatorOverload() {
        switch (operator) {
            case "==":
                return getProgram().getClassDeclaration("flat/Object");
            case "!=":
                return getProgram().getClassDeclaration("flat/operators/NotEqualToOperator");
            case "*":
                return getProgram().getClassDeclaration("flat/operators/MultiplyOperator");
            case "*=":
                return getProgram().getClassDeclaration("flat/operators/MultiplyEqualsOperator");
            case "+":
                return getProgram().getClassDeclaration("flat/operators/PlusOperator");
            case "+=":
                return getProgram().getClassDeclaration("flat/operators/PlusEqualsOperator");
            case "-":
                return getProgram().getClassDeclaration("flat/operators/MinusOperator");
            case "-=":
                return getProgram().getClassDeclaration("flat/operators/MinusEqualsOperator");
            case "<":
                return getProgram().getClassDeclaration("flat/operators/LessThanOperator");
            case "<=":
                return getProgram().getClassDeclaration("flat/operators/LessThanOrEqualToOperator");
            case ">":
                return getProgram().getClassDeclaration("flat/operators/GreaterThanOperator");
            case ">=":
                return getProgram()
                    .getClassDeclaration("flat/operators/GreaterThanOrEqualToOperator");
            default:
                return null;
        }
    }

    public boolean isShorthand() {
        return stream(SHORTHAND_OPERATORS).anyMatch(x -> x.equals(operator));
    }

    public String getNonShorthand() {
        switch (operator) {
            case SHORTHAND_ADD:
                return ADD;
            case SHORTHAND_BITWISE_AND:
                return BITWISE_AND;
            case SHORTHAND_BITWISE_OR:
                return BITWISE_OR;
            case SHORTHAND_BITWISE_XOR:
                return BITWISE_XOR;
            case SHORTHAND_DIVIDE:
                return DIVIDE;
            case SHORTHAND_L_SHIFT:
                return L_SHIFT;
            case SHORTHAND_MODULO:
                return MODULO;
            case SHORTHAND_MULTIPLY:
                return MULTIPLY;
            case SHORTHAND_UR_SHIFT:
                return UR_SHIFT;
            case SHORTHAND_R_SHIFT:
                return R_SHIFT;
            case SHORTHAND_SUBTRACT:
                return SUBTRACT;
        }

        return null;
    }

    public boolean doesModify() {
        if (isShorthand()) {
            return true;
        }

        switch (operator) {
            case INCREMENT:
            case DECREMENT:
                return true;
        }

        return false;
    }

    /**
     * Get the left Value that the operator is being used with.
     *
     * @return The left Value that the operator is being used with.
     */
    public Value getLeftOperand() {
        return (Value) getParent().getChildBefore(this);
    }

    /**
     * Get the right Value that the operator is being used with.
     *
     * @return The right Value that the operator is being used with.
     */
    public Value getRightOperand() {
        return (Value) getParent().getChildAfter(this);
    }

    public boolean isComparison() {
        switch (operator) {
            case AND:
            case OR:
                return false;
            default:
                return true;
        }
    }

    public boolean isConjunction() {
        switch (operator) {
            case AND:
            case OR:
                return true;
            default:
                return false;
        }
    }

    public boolean isArithmetic() {
        switch (operator) {
            case MULTIPLY:
            case SHORTHAND_MULTIPLY:
            case DIVIDE:
            case SHORTHAND_DIVIDE:
            case ADD:
            case SHORTHAND_ADD:
            case SUBTRACT:
            case SHORTHAND_SUBTRACT:
            case MODULO:
            case SHORTHAND_MODULO:
                return true;
            default:
                return false;
        }
    }

    public boolean isNumerical() {
        if (isArithmetic()) {
            return true;
        }

        switch (operator) {
            case BITWISE_AND:
            case BITWISE_OR:
            case BITWISE_XOR:
            case BITWISE_COMPLEMENT:
            case GREATER:
            case LESS:
            case GREATER_EQ:
            case LESS_EQ:
            case L_SHIFT:
            case UR_SHIFT:
            case R_SHIFT:
                return true;
            default:
                return false;
        }
    }

    /**
     * Update the type that the operation returns.
     */
    public void updateType() {
        if (!StringUtils.containsString(operator, OPERATORS)) {
            SyntaxMessage.error("Unknown operator '" + operator + "'", this);
        }

        if (isBooleanOperator()) {
            setType("Bool");
        } else if (!isDecoding()) {
            Value l = getLeftOperand().getReturnedNode();
            l = l.getFlatTypeValue(l);

            Value r = getRightOperand().getReturnedNode();
            r = r.getFlatTypeValue(r);

            if (r instanceof BinaryOperation) {
                if (((BinaryOperation) r).getOperator().isBooleanOperator()) {
                    setType("Bool");

                    return;
                }

                r = ((BinaryOperation) r).getLeftOperand().getReturnedNode();
            }

            if (l.isPrimitive() && r.isPrimitive()) {
                String left = SyntaxUtils.getPrimitiveFlatType(l.getType());
                String right = SyntaxUtils.getPrimitiveFlatType(r.getType());

                if (left == null || right == null
                    || !SyntaxUtils.arePrimitiveTypesCompatibleGeneral(left, right)) {
                    left = SyntaxUtils.getPrimitiveFlatType(l.getType());
                    right = SyntaxUtils.getPrimitiveFlatType(r.getType());

                    SyntaxUtils.arePrimitiveTypesCompatibleGeneral(left, right);

                    SyntaxMessage.error(
                        "Type '" + left + "' is not compatible with type '" + right + "'", this);
                }

                setType(SyntaxUtils.getHighestPrimitiveType(left, right));
            } else {
                ClassDeclaration common = SyntaxUtils.getTypeInCommon(l, r);

                if (common == null) {
                    String left = l.getType();
                    String right = r.getType();

                    Value lv = getLeftOperand().getReturnedNode();
                    lv.getFlatTypeValue(lv);

                    Value rv = getRightOperand().getReturnedNode();
                    rv.getFlatTypeValue(rv);

                    SyntaxUtils.getTypeInCommon(l, r);

                    SyntaxMessage.error(
                        "Type '" + left + "' is not compatible with type '" + right + "'", this);
                    return;
                }

                String type = common.getType();

                if (getLeftOperand().getReturnedNode().isPrimitive()
                    && getRightOperand().getReturnedNode().isPrimitive()) {
                    type = SyntaxUtils.getHighestPrimitiveType(
                        getLeftOperand().getReturnedNode().getType(),
                        getRightOperand().getReturnedNode().getType());
                }

                setType(type);
            }
        }
    }

    public boolean isBooleanOperator() {
        return operator.equals(EQUALS) || operator.equals(NOT_EQUAL) || operator.equals(BANG)
            || operator.equals(AND) ||
            operator.equals(OR) || operator.equals(LESS) || operator.equals(GREATER)
            || operator.equals(LESS_EQ) || operator.equals(GREATER_EQ);
    }

    public boolean isEquivalenceOperator() {
        return operator.equals(EQUALS) || operator.equals(NOT_EQUAL);
    }

    /**
     * @see Node#generateFlatInput(StringBuilder, boolean)
     */
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        return builder.append(operator);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Operator clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        Operator node = new Operator(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Operator cloneTo(Operator node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Operator} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Operator cloneTo(Operator node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.operator = operator;

        return node;
    }

    /**
     * Test the Operator class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}

