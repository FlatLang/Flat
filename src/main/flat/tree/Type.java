package flat.tree;

public class Type {
    public String value;

    public byte dataType;
    public int arrayDimensions;

    public Type() {
        dataType = Value.POINTER;
    }

    public void setType(String value) {
        this.value = value;
    }

    public static Type parse(Node parent, String value) {
        FunctionType funcType = FunctionType.parse(parent, value);

        if (funcType != null) {
            return funcType;
        }

        Type type = new Type();
        type.value = value;

        return type;
    }

    public String toFlat() {
        return value;
    }

    public Type cloneTo(Type clone) {
        clone.value = value;
        clone.dataType = dataType;
        clone.arrayDimensions = arrayDimensions;

        return clone;
    }

    public Type clone() {
        return cloneTo(new Type());
    }
}