package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.InstanceDeclaration;
import flat.tree.Node;
import flat.tree.Parameter;
import flat.tree.SyntaxTree;
import flat.util.Location;

public class DataIgnoreAnnotation extends ApplicableAnnotationBase implements ModifierAnnotation, VisibilityModifier {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public DataIgnoreAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static DataIgnoreAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("DataIgnore")) {
            DataIgnoreAnnotation n = new DataIgnoreAnnotation(parent, location);

            return n;
        }

        return null;
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray) {
        return builder.append("data_ignore");
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
    public void onAdded(Node parent) {
        ModifierAnnotation.super.onAdded(parent);
        super.onAdded(parent);
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
                ((InstanceDeclaration) next).setVisibility(InstanceDeclaration.PUBLIC);

                return true;
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public DataIgnoreAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        DataIgnoreAnnotation node = new DataIgnoreAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public DataIgnoreAnnotation cloneTo(DataIgnoreAnnotation node) {
        return cloneTo(node, true, true);
    }

    public DataIgnoreAnnotation cloneTo(DataIgnoreAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"data_ignore"};
    }
}