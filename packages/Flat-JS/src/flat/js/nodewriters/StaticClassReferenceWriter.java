package flat.js.nodewriters;

import flat.tree.*;
import flat.util.Location;

import java.util.Optional;

public abstract class StaticClassReferenceWriter extends IIdentifierWriter
{
	public abstract StaticClassReference node();

	@Override
	public StringBuilder writeName(StringBuilder builder)
	{
		return getWriter(node().getTypeClass()).writeName(builder);
	}

	@Override
	public StringBuilder writeExpression(StringBuilder builder) {
		Optional<Accessible> instantiationCall = node().getAccessedNodes().stream().filter(n -> n instanceof Instantiation).findFirst();

		if (instantiationCall.isPresent()) {
			return getWriter(instantiationCall.get().toValue()).writeExpression(builder);
		}

		if (node().isMetaClass()) {
			ClassDeclaration c = node().getFileDeclaration().getImportedClass(node(), node().getName());
			String classLocation = c.getClassLocation();
			boolean isInterface = false;

			Instantiation instantiation = Instantiation.decodeStatement(node().parent, "String(\"" + classLocation + "\")", Location.INVALID, true);

			return builder.append("flatConstructors.newClass(").append(getWriter(instantiation).writeExpression()).append(", ").append(isInterface).append(")");
		} else {
			return super.writeExpression(builder);
		}
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