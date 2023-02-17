package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.Node;
import flat.util.Location;

public class ApplicableAnnotationBase extends Annotation {
    public ApplicableAnnotationBase(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
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
        if (next instanceof Annotation == false) {
            return invalidApplication(next, throwError);
        }

        return super.onApplied(next);
    }

    @Override
    public ApplicableAnnotationBase clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ApplicableAnnotationBase node = new ApplicableAnnotationBase(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public ApplicableAnnotationBase cloneTo(ApplicableAnnotationBase node) {
        return cloneTo(node, true, true);
    }

    public ApplicableAnnotationBase cloneTo(ApplicableAnnotationBase node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }
}