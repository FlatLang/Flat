package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.*;
import flat.tree.variables.FieldDeclaration;
import flat.util.Location;

public class VisibleAnnotation extends ApplicableAnnotationBase implements ModifierAnnotation, VisibilityModifier {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public VisibleAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static VisibleAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("Visible") || name.equals("*")) {
            VisibleAnnotation n = new VisibleAnnotation(parent, location);

            return n;
        }

        return null;
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray) {
        return builder.append("visible");
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
            } else if (next instanceof PropertyMethod || next instanceof ArrayOverloadMethod) {
                return true;
            } else if (next instanceof FieldDeclaration) {
                ((FieldDeclaration) next).setVisibility(FieldDeclaration.VISIBLE);

                return true;
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public VisibleAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        VisibleAnnotation node = new VisibleAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public VisibleAnnotation cloneTo(VisibleAnnotation node) {
        return cloneTo(node, true, true);
    }

    public VisibleAnnotation cloneTo(VisibleAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"visible", "*"};
    }
}