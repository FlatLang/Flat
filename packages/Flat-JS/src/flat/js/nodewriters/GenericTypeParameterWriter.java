package flat.js.nodewriters;

import flat.tree.CallableMethod;
import flat.tree.Constructor;
import flat.tree.MethodCall;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeParameter;

public abstract class GenericTypeParameterWriter extends ValueWriter
{
	public abstract GenericTypeParameter node();

	public StringBuilder writeReifiedAccess(MethodCall call) {
		return writeReifiedAccess(new StringBuilder(), call);
	}

	public StringBuilder writeReifiedAccess(StringBuilder builder, MethodCall call) {
		GenericTypeArgument arg = node().getCorrespondingArgument(call);
		GenericTypeParameter param = arg.getGenericTypeParameter();

		if (param != null && param.isReified()) {
			builder.append("this.");
			return builder.append(arg.getGenericTypeParameter().getName());
		} else {
			getWriter(arg.getTypeClass()).writeUseExpression(builder);
			return builder.append(".accessor__js_class()");
		}
	}
}
