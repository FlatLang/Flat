package flat.java.nodewriters;

import flat.tree.Trait;
import flat.tree.Value;

public abstract class TraitWriter extends ClassDeclarationWriter
{
	public abstract Trait node();

	@Override
	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive, Value context, boolean writeGenerics, boolean writeArray)
	{
		return builder.append("interface").append(space ? ' ' : "");
	}

	@Override
	public StringBuilder writeSignature(StringBuilder builder, Value context, String name) {
		writeVisibility(builder).append(writeStatic());

		writeType(builder);
		writeName(builder, name);

		writeGenericTypeParametersDeclaration(builder);

		return writeInterfaceExtensions(builder, "extends");
	}

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		writeSignature(builder).append(" {\n");

		getWriter(node().getPropertyMethodList()).write(builder);
		getWriter(node().getMethodList()).write(builder);

		return builder.append("}\n");
	}
}