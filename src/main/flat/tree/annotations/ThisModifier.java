package flat.tree.annotations;

import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

public class ThisModifier extends Annotation implements ModifierAnnotation {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public ThisModifier(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
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

        if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
            if (parent instanceof Parameter) {
                if (!parent.isPropertyTrue("addedAssignment")) {
                    Parameter param = (Parameter) parent;

                    if (param.getParentMethod() instanceof Constructor) {
                        param.setProperty("addedAssignment", true);

                        Constructor constructor = (Constructor) param.getParentMethod();

                        Node preAssignment = SyntaxTree.decodeScopeContents(param.getParentMethod(), "this." + param.getName() + " = " + param.getName(), param.getLocationIn(), true);
                        Node postAssignment = SyntaxTree.decodeScopeContents(param.getParentMethod(), "this." + param.getName() + " = " + param.getName(), param.getLocationIn(), true);

                        if (preAssignment != null) {
                            Scope s = constructor.initMethod.getScope();

                            s.addChildBefore(s.getFirstStatement(), preAssignment);
                        } else {
                            SyntaxMessage.error("Unable to assign '" + param.getName() + "' field's value from parameter", param);
                        }

                        constructor.initMethod.getScope().addChild(postAssignment);
                    }
                }
            } else {
                invalidApplication(this, true);
            }
        }

        return result;
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof VariableDeclaration) {
                // valid
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray) {
        return builder.append(aliasUsed);
    }

    @Override
    public ThisModifier clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ThisModifier node = new ThisModifier(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public ThisModifier cloneTo(ThisModifier node) {
        return cloneTo(node, true, true);
    }

    public ThisModifier cloneTo(ThisModifier node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"this"};
    }
}