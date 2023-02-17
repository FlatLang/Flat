package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.InstanceDeclaration;
import flat.tree.Node;
import flat.tree.SyntaxTree;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.FieldList;
import flat.util.Location;

public class CompilerVisibleAnnotation extends Annotation implements ModifierAnnotation, VisibilityModifier {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public CompilerVisibleAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static CompilerVisibleAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("CompilerVisible")) {
            CompilerVisibleAnnotation n = new CompilerVisibleAnnotation(parent, location);

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
        } else if (phase == SyntaxTree.PHASE_PRE_GENERATION) {
            InstanceDeclaration instance = (InstanceDeclaration) parent;

            if (parent instanceof FieldDeclaration) {
                FieldList fields = (FieldList) parent.parent.parent;

                if (instance.isStatic()) {
                    fields.getPublicStaticFieldList().addChild(parent);
                } else {
                    fields.getPublicFieldList().addChild(parent);
                }
            }

            instance.setVisibility(InstanceDeclaration.PUBLIC);

            PublicAnnotation a = new PublicAnnotation(parent, getLocationIn());

            replaceWith(a);
        }

        return result;
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof InstanceDeclaration) {
                return true;
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public CompilerVisibleAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        CompilerVisibleAnnotation node = new CompilerVisibleAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public CompilerVisibleAnnotation cloneTo(CompilerVisibleAnnotation node) {
        return cloneTo(node, true, true);
    }

    public CompilerVisibleAnnotation cloneTo(CompilerVisibleAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"compiler_visible"};
    }
}