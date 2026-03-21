package flat.js.nodewriters;

import flat.tree.*;

public abstract class ScopeWriter extends NodeWriter
{
	public abstract Scope node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return write(builder, true);
	}

	public StringBuilder write(StringBuilder builder, boolean braces)
	{
		return write(builder, braces, true);
	}

	public StringBuilder write(boolean braces)
	{
		return write(new StringBuilder(), braces);
	}

	public StringBuilder write(boolean braces, boolean newLine)
	{
		return write(new StringBuilder(), braces, newLine);
	}

	public StringBuilder write(StringBuilder builder, boolean braces, boolean newLine)
	{
		if (node().getNumChildren() <= 1)
		{
			if (node().getParent() instanceof Loop)
			{
				// Insert the semicolon before the new line.
				return builder.insert(builder.length() - 1, ";");
			}
		}

		if (braces)
		{
			builder.append('{').append('\n');
		}
		if (node().getParent() instanceof ForEachLoop) {
			getWriter(((ForEachLoop)node().getParent()).getVariable().getDeclaration()).write(builder);
		}

		node().forEachChild(child -> {
			getWriter(child).write(builder);
		});

		if (node().parent instanceof InitializationMethod) {
			builder.append("return self;\n");
		}

		if (braces)
		{
			builder.append('}');
		}
		if (newLine)
		{
			builder.append('\n');
		}

		return builder;
	}
}