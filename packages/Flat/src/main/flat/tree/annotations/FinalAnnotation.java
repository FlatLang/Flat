package flat.tree.annotations;

import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

public class FinalAnnotation extends Annotation implements ModifierAnnotation {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public FinalAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static FinalAnnotation decodeStatement(Node parent, String name, String parameters,
        Location location, boolean require) {
        if (name.equals("Final")) {
            FinalAnnotation n = new FinalAnnotation(parent, location);

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
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren,
        boolean generateArray) {
        return builder.append("let");
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
            VariableDeclaration declaration = (VariableDeclaration) getParent();

            declaration.references.forEach(variable -> {
                if (variable == null) {
                    throw new RuntimeException(
                        "Parallelization error with variable declaration references: "
                            + declaration.getDeclaringClass().getName() + "."
                            + declaration.getName());
                }
                if (variable.getParentMethod() instanceof Constructor == false &&
                    variable.getParentMethod() instanceof AssignmentMethod == false &&
                    variable.getParentMethod() instanceof MutatorMethod == false &&
                    variable.getAncestorOfType(StaticBlock.class) == null) {
                    if (variable.isUserMade() && variable.isInTree() && variable.isBeingModified()
                        && !variable.declaration.isPropertyTrue("elvis")) {
                        variable.isUserMade();
                        variable.isBeingModified();
                        variable.declaration.isPropertyTrue("elvis");
                        SyntaxMessage.error("Final variable '" + declaration.getName()
                            + "' cannot be modified in this context", variable, false);

                        result.errorOccurred = true;
                    }
                }
            });
        }

        return result;
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof VariableDeclaration) {
                // ((VariableDeclaration)next).isFinal = true;
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public FinalAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        FinalAnnotation node = new FinalAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public FinalAnnotation cloneTo(FinalAnnotation node) {
        return cloneTo(node, true, true);
    }

    public FinalAnnotation cloneTo(FinalAnnotation node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[] {"let", "final"};
    }
}

