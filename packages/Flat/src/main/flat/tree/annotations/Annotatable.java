package flat.tree.annotations;

import flat.tree.FileDeclaration;
import flat.tree.Node;

import java.util.ArrayList;

/**
 * @author Braden Steffaniak
 */
public interface Annotatable {
    ArrayList<Annotation> getAnnotations();

    void addAnnotation(Annotation annotation);

    default boolean isAsync() {
        return containsAnnotationOfType(AsyncAnnotation.class);
    }

    default boolean isAwait() {
        return containsAnnotationOfType(AwaitAnnotation.class);
    }

    default boolean containsAnnotationOfType(Class c) {
        return containsAnnotationOfType(c, false);
    }

    default boolean containsAnnotationOfType(Class c, boolean checkAncestors) {
        return containsAnnotationOfType(c, checkAncestors, false);
    }

    default boolean containsAnnotationOfType(Class c, boolean checkAncestors,
        boolean checkPending) {
        return getAnnotationOfType(c, checkAncestors, checkPending) != null;
    }

    default Annotation getAnnotationOfType(Class c) {
        return getAnnotationOfType(c, false);
    }

    default Annotation getAnnotationOfType(Class c, boolean checkAncestors) {
        return getAnnotationOfType(c, checkAncestors, false);
    }

    default Annotation getAnnotationOfType(Class c, boolean checkAncestors, boolean checkPending) {
        return getAnnotationsOfType(c, checkAncestors, checkPending).stream().findFirst()
            .orElse(null);
    }

    default ArrayList<Annotation> getAnnotationsOfType(Class c) {
        return getAnnotationsOfType(c, false);
    }

    default ArrayList<Annotation> getAnnotationsOfType(Class c, boolean checkAncestors) {
        return getAnnotationsOfType(c, checkAncestors, false);
    }

    default ArrayList<Annotation> getAnnotationsOfType(Class c, boolean checkAncestors,
        boolean checkPending) {
        ArrayList<Annotation> list = new ArrayList<>();
        ArrayList<Annotation> annotations = getAnnotations();

        if (annotations != null) {
            for (Annotation a : annotations) {
                if (c.isAssignableFrom(a.getClass())) {
                    list.add(a);
                }
            }
        }

        if (checkAncestors) {
            if (((Node) this).getParent() != null
                && ((Node) this).getParent() instanceof FileDeclaration == false) {
                for (Annotation a : ((Node) this).getParent().getAnnotationsOfType(c, true,
                    checkPending)) {
                    list.add(a);
                }
            }

            if (checkPending && ((Node) this).getFileDeclaration() != null) {
                for (Annotation a : ((Node) this).getFileDeclaration()
                    .getPendingAnnotationsOfType(c)) {
                    list.add(a);
                }
            }
        }

        return list;
    }

    default Annotation removeAnnotationOfType(Class c) {
        return removeAnnotationOfType(c, false);
    }

    default Annotation removeAnnotationOfType(Class c, boolean checkAncestors) {
        return removeAnnotationOfType(c, checkAncestors, false);
    }

    default Annotation removeAnnotationOfType(Class c, boolean checkAncestors,
        boolean checkPending) {
        ArrayList<Annotation> annotations = getAnnotations();

        if (annotations != null) {
            for (int i = annotations.size() - 1; i >= 0; i--) {
                Annotation a = annotations.get(i);

                if (c.isAssignableFrom(a.getClass())) {
                    annotations.remove(i);
                    a.detach();
                }
            }
        }

        if (checkAncestors) {
            if (((Node) this).getParent() != null
                && ((Node) this).getParent() instanceof FileDeclaration == false) {
                return ((Node) this).getParent().getAnnotationOfType(c, true, checkPending);
            }

            if (checkPending && ((Node) this).getFileDeclaration() != null) {
                return ((Node) this).getFileDeclaration().getPendingAnnotationOfType(c);
            }
        }

        return null;
    }

    default StringBuilder generateFlatAnnotations(StringBuilder builder) {
        return generateFlatAnnotations(builder, true);
    }

    default StringBuilder generateFlatAnnotations(StringBuilder builder, boolean newLine) {
        ArrayList<Annotation> annotations = getAnnotations();

        if (annotations != null) {
            for (Annotation a : annotations) {
                a.generateFlatInput(builder).append(newLine ? '\n' : " ");
            }
        }

        return builder;
    }
}

