package flat.java.nodewriters;

import flat.tree.MethodCall;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.generics.MethodGenericTypeParameter;

public abstract class MethodGenericTypeParameterWriter extends GenericTypeParameterWriter {
    public abstract MethodGenericTypeParameter node();
}
