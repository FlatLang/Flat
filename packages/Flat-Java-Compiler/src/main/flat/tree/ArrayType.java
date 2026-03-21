package flat.tree;

public class ArrayType extends Type {
    public Type type;

    public int arrayDimensions;

    @Override
    public void setType(String value) {
        if (type == null) {
            type = new Type();
        }

        type.setType(value);
    }

    public ArrayType cloneTo(ArrayType clone) {
        super.cloneTo(clone);

        clone.type = type != null ? type.clone() : null;
        clone.arrayDimensions = arrayDimensions;

        return clone;
    }

    public ArrayType clone() {
        return cloneTo(new ArrayType());
    }
}

