package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.FlatMethodDeclaration;
import flat.tree.Node;
import flat.tree.SyntaxTree;
import flat.util.Location;

public class InitTestAnnotation extends Annotation implements ModifierAnnotation, NestAnnotation {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public InitTestAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static InitTestAnnotation decodeStatement(Node parent, String name, String parameters,
        Location location, boolean require) {
        if (name.equals("InitTest")) {
            InitTestAnnotation n = new InitTestAnnotation(parent, location);

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
            addOutputStreamParameter((FlatMethodDeclaration) parent);
        }

        return result;
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof FlatMethodDeclaration) {
                // valid
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public InitTestAnnotation clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        InitTestAnnotation node = new InitTestAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public InitTestAnnotation cloneTo(InitTestAnnotation node) {
        return cloneTo(node, true, true);
    }

    public InitTestAnnotation cloneTo(InitTestAnnotation node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[] {"init_test"};
    }
}

