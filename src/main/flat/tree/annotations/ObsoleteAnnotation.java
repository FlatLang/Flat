package flat.tree.annotations;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.Variable;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;
import flat.util.StringUtils;

/**
 * {@link Annotation} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ObsoleteAnnotation extends Annotation implements ModifierAnnotation {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public boolean fail;

    public String message;

    /**
     * @see Node#Node(Node, Location)
     */
    public ObsoleteAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link ObsoleteAnnotation} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li></li>
     * 	<li></li>
     * 	<li></li>
     * </ul>
     *
     * @param parent   The parent node of the statement.
     * @param name     The statement to try to decode into a
     *                 {@link ObsoleteAnnotation} instance.
     * @param location The location of the statement in the source code.
     * @param require  Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link ObsoleteAnnotation}.
     */
    public static ObsoleteAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("Obsolete")) {
            ObsoleteAnnotation n = new ObsoleteAnnotation(parent, location);

            if (parameters.length() > 0) {
                String[] args = StringUtils.splitCommas(parameters);

                if (args.length >= 1 && args.length < 3) {
                    Value messageNode = Literal.decodeStatement(n, args[0], location, require, true);

                    if (messageNode instanceof Literal) {
                        Literal message = (Literal) messageNode;

                        if (message.getType().equals("String")) {
                            n.message = message.getValue();
                        } else if (args.length > 1) {
                            SyntaxMessage.queryError("Obsolete requires that if a message is given, for it to be the first argument", n, require);

                            return null;
                        }
                    }

                    if (args.length >= 2) {
                        Literal fail = (Literal) Literal.decodeStatement(n, args[1], location, require, true);

                        if (fail.getType().equals("Bool")) {
                            n.fail = Boolean.parseBoolean(fail.getValue());
                        }
                    }
                } else {
                    n.tooManyArguments(require);

                    return null;
                }
            }

            return n;
        }

        return null;
    }

    @Override
    public void onAdded(Node parent) {
        ModifierAnnotation.super.onAdded(parent);
        super.onAdded(parent);
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
            Node node = getParent();

            if (node instanceof VariableDeclaration == false) {
                invalidApplication(node, true);
            }

            VariableDeclaration decl = (VariableDeclaration) node;

            for (Variable reference : decl.references) {
                Variable v = reference;//(Variable)reference.getReferenceNode();

                if (fail) {
                    SyntaxMessage.error(message, v);
                } else {
                    SyntaxMessage.warning(message, v);
                }
            }
        }

        return result;
    }

    @Override
    public boolean onNextStatementDecoded(Node next) {
        if (next instanceof FieldDeclaration || next instanceof FlatMethodDeclaration) {
            next.addAnnotation(this);

            VariableDeclaration decl = (VariableDeclaration) next;

            String output = "";
            String type = "";

            if (decl instanceof FieldDeclaration) {
                output = decl.generateFlatInput().toString();

                type = "Field";
            } else {
                output = ((FlatMethodDeclaration) decl).generateFlatSignature();

                type = "Method";
            }

            message = type + " '" + output + "' is obsolete" + (message.length() > 0 ? ": " : "") + message;
        }

        return super.onNextStatementDecoded(next);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ObsoleteAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ObsoleteAnnotation node = new ObsoleteAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ObsoleteAnnotation cloneTo(ObsoleteAnnotation node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ObsoleteAnnotation} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ObsoleteAnnotation cloneTo(ObsoleteAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    /**
     * Test the {@link ObsoleteAnnotation} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"obsolete"};
    }
}