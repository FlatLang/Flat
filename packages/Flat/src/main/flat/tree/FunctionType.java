package flat.tree;

public class FunctionType extends Type {
    public Type type;

    public String[] parameterNames;

    public FirstClassClosureDeclaration closure;

    public static FunctionType parse(Node parent, String value) {
        if (value != null && value.indexOf('(') > 0) {
            ClosureDeclaration c =
                ClosureDeclaration.decodeStatement(parent, value, parent.getLocationIn(), false);

            if (c != null) {
                FunctionType type = new FunctionType();

                FirstClassClosureDeclaration firstClass =
                    new FirstClassClosureDeclaration(parent, c.getLocationIn());

                type.value = c.getName();
                type.type = c.getTypeObject();
                type.closure = (FirstClassClosureDeclaration) c.cloneTo(firstClass);

                if (parent instanceof Identifier) {
                    firstClass.reference = (Identifier) parent;
                } else {
                    throw new RuntimeException("Invalid parent type " + parent);
                }

                return type;
            }
        }

        return null;
    }

    @Override
    public String toFlat() {
        return closure.generateFlatInput().toString();
    }

    public FunctionType cloneTo(FunctionType clone) {
        clone.type = type != null ? type.clone() : null;
        clone.closure = closure;
        clone.parameterNames = parameterNames;

        return clone;
    }

    public FunctionType clone() {
        return cloneTo(new FunctionType());
    }
}

