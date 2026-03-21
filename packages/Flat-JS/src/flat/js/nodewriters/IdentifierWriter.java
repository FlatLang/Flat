package flat.js.nodewriters;

import flat.tree.*;
import flat.tree.annotations.AwaitAnnotation;

public abstract class IdentifierWriter extends ValueWriter implements AccessibleWriter
{
	public abstract Identifier node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		if (isInstanceClosure())
		{
			return writeInstanceClosure(builder);
		}

		if (!node().isAccessed()) {
			node().getAccessedNodes(true)
				.stream()
				.filter(n -> n.toValue().isAwait())
				.forEach(n -> builder.append("(await "));
		}

		writeUseExpression(builder);

		if (node().isAwait()) {
			builder.append(')');
		}

		writeAccessedExpression(builder);

		return builder;
	}

	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		return writeName(builder).append(writeArrayAccess());
	}

	public StringBuilder writeName()
	{
		return writeName(new StringBuilder());
	}

	public StringBuilder writeName(StringBuilder builder)
	{
		switch (node().getName()) {
			case "arguments": return builder.append("_js_arguments");
			case "public": return builder.append("_js_public");
			case "private": return builder.append("_js_private");
			case "package": return builder.append("_js_package");
			case "class": return builder.append("_js_class");
			case "default": return builder.append("_js_default");
			case "case": return builder.append("_js_case");
			case "function": return builder.append("_js_function");
		}

		return builder.append(node().getName());
	}
}
