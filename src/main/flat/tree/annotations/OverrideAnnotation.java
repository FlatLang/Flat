package flat.tree.annotations;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.FlatMethodDeclaration;
import flat.tree.InstanceDeclaration;
import flat.tree.Node;
import flat.tree.SyntaxTree;
import flat.tree.variables.FieldDeclaration;
import flat.util.Location;

import java.util.Arrays;

public class OverrideAnnotation extends Annotation implements ModifierAnnotation {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    /**
     * @see Node#Node(Node, Location)
     */
    public OverrideAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link OverrideAnnotation} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li></li>
     * 	<li></li>
     * 	<li></li>
     * </ul>
     *
     * @param parent     The parent node of the statement.
     * @param parameters The statement to try to decode into a
     *                   {@link OverrideAnnotation} instance.
     * @param location   The location of the statement in the source code.
     * @param require    Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link OverrideAnnotation}.
     */
    public static OverrideAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("Override")) {
            OverrideAnnotation n = new OverrideAnnotation(parent, location);

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

        if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
            Node node = getParent();

            FlatMethodDeclaration[] methods = new FlatMethodDeclaration[0];

            if (node instanceof FieldDeclaration) {
                methods = new FlatMethodDeclaration[]{((FieldDeclaration) node).getAccessorMethod(), ((FieldDeclaration) node).getMutatorMethod()};
            } else if (node instanceof FlatMethodDeclaration) {
                methods = new FlatMethodDeclaration[]{(FlatMethodDeclaration) node};
            } else {
                invalidApplication(node, true);
            }

            if (Arrays.stream(methods).anyMatch(x -> x != null && !x.checkOverrides())) {
                Arrays.stream(methods).anyMatch(x -> x != null && !x.checkOverrides());

                if (node instanceof FieldDeclaration) {
                    SyntaxMessage.error("Field '" + node + "' does not override any fields", node);
                } else {
                    SyntaxMessage.error("Method '" + methods[0] + "' does not override any methods", methods[0]);
                }
            }
        }

        return result;
    }

    @Override
    public boolean onNextStatementDecoded(Node next) {
        if (next instanceof FlatMethodDeclaration) {
            next.addAnnotation(this);
        }

        return super.onNextStatementDecoded(next);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public OverrideAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        OverrideAnnotation node = new OverrideAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public OverrideAnnotation cloneTo(OverrideAnnotation node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link OverrideAnnotation} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public OverrideAnnotation cloneTo(OverrideAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    /**
     * Test the {@link OverrideAnnotation} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    @Override
    public boolean onApplied(Node appliedTo, boolean throwError) {
        return appliedTo instanceof InstanceDeclaration || super.onApplied(appliedTo, throwError);
    }

    @Override
    public String[] getAliases() {
        return new String[]{"override"};
    }
}