package flat.tree.annotations;

import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.tree.variables.FieldDeclaration;
import flat.util.Location;

public interface VisibilityModifier {
    default boolean addAssignment() {
        if (!((Annotation) this).getParentClass().isPropertyTrue("functionMap")) {
            Node parent = ((Annotation) this).getParent();

            if (parent instanceof Parameter && !parent.isPropertyTrue("addedAssignment")) {
                Parameter param = (Parameter) parent;

                if (param.getParentMethod() instanceof Constructor == false
                    && param.getParentMethod() instanceof InitializationMethod == false) {
                    SyntaxMessage.error(
                        "Parameter field declarations can only be used in constructors", param);

                    return false;
                }

                if (param.getParentMethod() instanceof Constructor) {
                    param.setProperty("addedAssignment", true);

                    Node assignment = SyntaxTree.decodeScopeContents(param.getParentMethod(),
                        "this." + param.getName() + " = " + param.getName(), param.getLocationIn(),
                        true);

                    if (assignment != null) {
                        param.getParentMethod().getScope().addChild(assignment);
                    }

                    return assignment != null;
                }
            }
        }

        return true;
    }

    default boolean createFieldFromParameter(Parameter param) {
        if (!((Annotation) this).getParentClass().isPropertyTrue("functionMap")) {
            FieldDeclaration current = param.getParentClass().getField(param.getName(), false);

            if (current == null) {
                FieldDeclaration field =
                    new FieldDeclaration(param.getParentClass(), Location.INVALID);

                param.cloneTo(field, true, true);
                field.setLocationIn(Location.INVALID);

                param.getParentClass().addChild(field);
            } else {
                // if (current.getVisibility() != param.getvis)
            }
        }

        return true;
    }

    default boolean checkParameter(Node next) {
        if (next instanceof Parameter) {
            return true;
        }

        return false;
    }
}

