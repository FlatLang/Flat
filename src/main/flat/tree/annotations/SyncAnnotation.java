package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.*;
import flat.util.Location;

public class SyncAnnotation extends Annotation implements ModifierAnnotation {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public SyncAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static SyncAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("Sync")) {
            SyncAnnotation n = new SyncAnnotation(parent, location);

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
        } else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
            FlatMethodDeclaration method = getParentMethod();

            getFileDeclaration().addImport("flat/thread/Thread");

            Scope scope = method.getScope();

            StaticClassReference lock = (StaticClassReference) SyntaxTree.decodeIdentifierAccess(method, "Thread.lock()", Location.INVALID, true, false, true);
            StaticClassReference unlock = (StaticClassReference) SyntaxTree.decodeIdentifierAccess(method, "Thread.unlock()", Location.INVALID, true, false, true);

            scope.addChildBefore(scope.getFirstStatement(), lock);
            scope.addChildAfter(scope.getLastChild(), unlock);
        }

        return result;
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof FlatMethodDeclaration) {
                return true;
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public SyncAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        SyncAnnotation node = new SyncAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public SyncAnnotation cloneTo(SyncAnnotation node) {
        return cloneTo(node, true, true);
    }

    public SyncAnnotation cloneTo(SyncAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"sync"};
    }
}