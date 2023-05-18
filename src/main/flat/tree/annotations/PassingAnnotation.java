package flat.tree.annotations;

import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

public class PassingAnnotation extends Annotation implements ModifierAnnotation {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public PassingAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static PassingAnnotation decodeStatement(Node parent, String name, String parameters,
        Location location, boolean require) {
        if (name.equals("Passing")) {
            PassingAnnotation n = new PassingAnnotation(parent, location);

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
        return builder.append("passing");
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
                if (variable.getParentMethod() instanceof Constructor == false &&
                    variable.getParentMethod() instanceof AssignmentMethod == false &&
                    variable.getParentMethod() instanceof MutatorMethod == false &&
                    variable.getAncestorOfType(StaticBlock.class) == null) {
                    if (variable.isAccessed()) {
                        SyntaxMessage.error("Passing variable '" + declaration.getName()
                            + "' cannot be accessed from", variable, false);

                        result.errorOccurred = true;
                    } else if (variable.getParent() instanceof MethodCallArgumentList == false) {
                        SyntaxMessage.error("Passing variable '" + declaration.getName()
                            + "' cannot be used in this context", variable, false);

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
    public PassingAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        PassingAnnotation node = new PassingAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public PassingAnnotation cloneTo(PassingAnnotation node) {
        return cloneTo(node, true, true);
    }

    public PassingAnnotation cloneTo(PassingAnnotation node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[] {"passing"};
    }
}

