package flat.js.nodewriters;

import flat.tree.*;

public abstract class FileDeclarationWriter extends NodeWriter
{
	public abstract FileDeclaration node();

	@Override
	public StringBuilder write(final StringBuilder builder)
	{
		node().forEachChild(child -> {
			if (child != node().getImportList())
			{
				getWriter(child).write(builder);
			}
		});

		return builder;
	}
}