package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.Node;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

public class ExtensionAnnotation extends Annotation {
    public ExtensionAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static ExtensionAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("Extension")) {
            ExtensionAnnotation n = new ExtensionAnnotation(parent, location);

            n.parseParameters(parameters);

            return n;
        }

        return null;
    }

    @Override
    public String[] defaultParameterNames() {
        return new String[]{"class"};
    }

    @Override
    public String[][] defaultParameterTypes() {
        return new String[][]{{"String"}};
    }

    @Override
    public void onAdded(Node parent) {
        super.onAdded(parent);
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray) {
        return builder.append("let");
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
            if (next instanceof VariableDeclaration) {
//			((VariableDeclaration)next).isFinal = true;
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public ExtensionAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ExtensionAnnotation node = new ExtensionAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public ExtensionAnnotation cloneTo(ExtensionAnnotation node) {
        return cloneTo(node, true, true);
    }

    public ExtensionAnnotation cloneTo(ExtensionAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }
}