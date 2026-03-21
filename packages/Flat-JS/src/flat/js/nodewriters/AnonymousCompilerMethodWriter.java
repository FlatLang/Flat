package flat.js.nodewriters;

import flat.tree.*;
import flat.tree.annotations.AsyncAnnotation;
import flat.tree.lambda.LambdaMethodDeclaration;

import java.util.Arrays;

public abstract class AnonymousCompilerMethodWriter extends BodyMethodDeclarationWriter
{
	public abstract AnonymousCompilerMethod node();

	@Override
	public StringBuilder writePrototypeAccess(StringBuilder builder) {
		if (node().isExtension()) {
			return builder;
		}

		return super.writePrototypeAccess(builder);
	}
}