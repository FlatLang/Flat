package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.Node;
import flat.tree.Parameter;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

public class NamedArgumentModifier extends Annotation implements ModifierAnnotation {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public NamedArgumentModifier(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
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

        if (parent instanceof Parameter) {
            Parameter param = (Parameter) parent;

            param.requireNamed = true;
        } else {
            invalidApplication(this, true);
        }

        return result;
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof VariableDeclaration) {
                // valid
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray) {
        return builder.append(aliasUsed);
    }

    @Override
    public NamedArgumentModifier clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        NamedArgumentModifier node = new NamedArgumentModifier(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public NamedArgumentModifier cloneTo(NamedArgumentModifier node) {
        return cloneTo(node, true, true);
    }

    public NamedArgumentModifier cloneTo(NamedArgumentModifier node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"named"};
    }
}