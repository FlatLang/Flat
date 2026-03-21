package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.InstanceDeclaration;
import flat.tree.Node;
import flat.tree.Parameter;
import flat.tree.SyntaxTree;
import flat.util.Location;

public class PrivateAnnotation extends ApplicableAnnotationBase
    implements ModifierAnnotation, VisibilityModifier {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public PrivateAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static PrivateAnnotation decodeStatement(Node parent, String name, String parameters,
        Location location, boolean require) {
        if (name.equals("Private") || name.equals("-")) {
            PrivateAnnotation n = new PrivateAnnotation(parent, location);

            return n;
        }

        return null;
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren,
        boolean generateArray) {
        return builder.append("private");
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
            if (!addAssignment()) {
                result.errorOccurred = true;
            }
        }

        return result;
    }

    @Override
    public boolean onNextStatementDecoded(Node next) {
        if (next instanceof Parameter && createFieldFromParameter((Parameter) next)) {
            return true;
        }

        return super.onNextStatementDecoded(next);
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (checkParameter(next)) {
                return true;
            } else if (next instanceof InstanceDeclaration) {
                ((InstanceDeclaration) next).setVisibility(InstanceDeclaration.PRIVATE);

                return true;
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public PrivateAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        PrivateAnnotation node = new PrivateAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public PrivateAnnotation cloneTo(PrivateAnnotation node) {
        return cloneTo(node, true, true);
    }

    public PrivateAnnotation cloneTo(PrivateAnnotation node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[] {"private"};
    }
}

