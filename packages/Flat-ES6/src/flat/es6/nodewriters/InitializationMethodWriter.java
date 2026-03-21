package flat.es6.nodewriters;

import flat.tree.ClassDeclaration;
import flat.tree.InitializationMethod;

public abstract class InitializationMethodWriter extends BodyMethodDeclarationWriter
{
	public abstract InitializationMethod node();
	
//	@Override
//	public StringBuilder writePrototypeAccess(StringBuilder builder)
//	{
//		return builder;
//	}
//
//	@Override
//	public StringBuilder writePrototypeAssignment(StringBuilder builder, ClassDeclaration clazz, boolean superCall)
//	{
//		return builder;
//	}


	@Override
	public StringBuilder writeSignature(StringBuilder builder) {
		builder.append("static ");

		return super.writeSignature(builder);
	}

	@Override
	public StringBuilder writeName(StringBuilder builder)
	{
		return builder.append("__init").append(writeOverload());
	}
}
