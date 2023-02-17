package flat.tree.annotations;

import flat.tree.Node;

public interface ModifierAnnotation {
    String[] getAliases();

    String getAliasUsed();

    void setAliasUsed(String aliasUsed);

    default void onAdded(Node to) {
        apply(to, getAliasUsed());
    }

    default boolean apply(Node to, String alias) {
        if (to != ((Annotation) this).getParent() && !to.containsAnnotationOfType(this.getClass(), false, false)) {
            if (onAppliedAsModifier(to, false)) {
                to.addAnnotation((Annotation) this);

                setAliasUsed(alias);

                return true;
            } else {
                return false;
            }
        }

        return true;
    }

    default boolean onAppliedAsModifier(Node toNode, boolean throwError) {
        return ((Annotation) this).onApplied(toNode, throwError);
    }
}