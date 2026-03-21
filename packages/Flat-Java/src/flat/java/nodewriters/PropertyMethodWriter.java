package flat.java.nodewriters;

import flat.Flat;
import flat.tree.*;

public abstract class PropertyMethodWriter extends BodyMethodDeclarationWriter
{
	public abstract PropertyMethod node();

	public boolean isExtension() {
		if (node().getParentClass() instanceof ExtensionDeclaration == false) return false;
		if (node().originalField == null) return false;
		if (node().originalField instanceof ExtensionFieldDeclaration == false) return false;

		return true;
	}

	@Override
	public StringBuilder writeStatic(StringBuilder builder) {
		if (isExtension()) {
			return builder.append("static ");
		}

		return super.writeStatic(builder);
	}

	@Override
	public StringBuilder writeParameters(StringBuilder builder, Value context, String[] paramNames, boolean parenthesis, boolean useGivenNames, boolean box) {
		if (parenthesis) {
			builder.append('(');
		}

		if (isExtension()) {
			writeExtensionReferenceParameter(builder);
		}

		super.writeParameters(builder, context, paramNames, false, useGivenNames, box);

		if (parenthesis) {
			builder.append(')');
		}

		return builder;
	}

	@Override
	public StringBuilder writeGenericArguments(StringBuilder builder, Value context) {
		if (node().originalField instanceof ClassInstanceDeclaration) {
			return builder;
		}

		return super.writeGenericArguments(builder, context);
	}
}