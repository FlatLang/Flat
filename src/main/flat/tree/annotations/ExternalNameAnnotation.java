package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.Identifier;
import flat.tree.Node;
import flat.util.Location;

public class ExternalNameAnnotation extends Annotation implements ModifierAnnotation {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public ExternalNameAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static ExternalNameAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("ExternalName")) {
            ExternalNameAnnotation n = new ExternalNameAnnotation(parent, location);

            n.parseParameters(parameters);

            return n;
        }

        return null;
    }

    @Override
    public String[] defaultParameterNames() {
        return new String[]{"name"};
    }

    @Override
    public String[][] defaultParameterTypes() {
        return new String[][]{{"Identifier"}};
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

        String name = ((Identifier) parent).getName();

        if (parameters.get("name") != null) {
            name = (String) parameters.get("name");
        }

        parent.setProperty("externalName", name);

        return result;
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof Identifier) {
                return true;
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public ExternalNameAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ExternalNameAnnotation node = new ExternalNameAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public ExternalNameAnnotation cloneTo(ExternalNameAnnotation node) {
        return cloneTo(node, true, true);
    }

    public ExternalNameAnnotation cloneTo(ExternalNameAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"external_name"};
    }
}