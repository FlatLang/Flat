package flat.js.nodewriters;

import flat.tree.ReifiedParameterAccess;

public abstract class ReifiedParameterAccessWriter extends IIdentifierWriter
{
	public abstract ReifiedParameterAccess node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder) {
		builder.append("this.");

		writeName(builder);

		return builder;
	}

	@Override
	public boolean shouldFallbackToNull() {
		if (node().doesAccess()) {
			Writer writer = getWriter(node().getReturnedNode());

			if (writer instanceof AccessibleWriter) {
				return ((AccessibleWriter) writer).shouldFallbackToNull();
			}
		}

		return false;
	}
}
