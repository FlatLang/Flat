package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.ArrayOverloadMethod;
import flat.tree.InstanceDeclaration;
import flat.tree.Node;
import flat.tree.PropertyMethod;
import flat.util.Location;

public class StaticAnnotation extends Annotation implements ModifierAnnotation {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public StaticAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static StaticAnnotation decodeStatement(Node parent, String name, String parameters,
        Location location, boolean require) {
        if (name.equals("Static")) {
            StaticAnnotation n = new StaticAnnotation(parent, location);

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

        return result;
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof PropertyMethod || next instanceof ArrayOverloadMethod) {
                return true;
            } else if (next instanceof InstanceDeclaration) {
                ((InstanceDeclaration) next).setStatic(true);
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public StaticAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        StaticAnnotation node = new StaticAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public StaticAnnotation cloneTo(StaticAnnotation node) {
        return cloneTo(node, true, true);
    }

    public StaticAnnotation cloneTo(StaticAnnotation node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[] {"static"};
    }
}

