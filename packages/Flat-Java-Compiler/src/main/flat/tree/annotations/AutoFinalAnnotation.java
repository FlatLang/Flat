package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.ClassDeclaration;
import flat.tree.FlatMethodDeclaration;
import flat.tree.Node;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

public class AutoFinalAnnotation extends Annotation {
    public AutoFinalAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static AutoFinalAnnotation decodeStatement(Node parent, String name, String parameters,
        Location location, boolean require) {
        if (name.equals("AutoFinal")) {
            AutoFinalAnnotation n = new AutoFinalAnnotation(parent, location);

            return n;
        }

        return null;
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        Node[] nodes = getParent().getChildrenOfType(VariableDeclaration.class);

        for (Node n : nodes) {
            VariableDeclaration declaration = (VariableDeclaration) n;

            if (declaration instanceof FlatMethodDeclaration == false && !declaration.isVar()
                && declaration.isUserMade() && !declaration.isFinal()) {
                if (!declaration.isPropertyTrue("forLoopVariable")) {
                    declaration.isUserMade();
                    declaration.isFinal();
                    declaration.addAnnotation(
                        new FinalAnnotation(declaration, declaration.getLocationIn()));
                }
            }
        }

        return result;
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof ClassDeclaration || next.containsScope()) {
                // ((InstanceDeclaration)next).setStatic(true);
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public AutoFinalAnnotation clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        AutoFinalAnnotation node = new AutoFinalAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public AutoFinalAnnotation cloneTo(AutoFinalAnnotation node) {
        return cloneTo(node, true, true);
    }

    public AutoFinalAnnotation cloneTo(AutoFinalAnnotation node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }
}

