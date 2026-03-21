package flat.es6.nodewriters;

import flat.tree.AccessorMethod;
import flat.tree.Node;
import flat.tree.Return;

public abstract class AccessorMethodWriter extends PropertyMethodWriter
{
	public abstract AccessorMethod node();

//	@Override
//	public StringBuilder writeSignature(StringBuilder builder) {
//		builder.append("get ");
//
//		return super.writeSignature(builder);
//	}

	@Override
	public StringBuilder write(StringBuilder builder) {
		if (node().getLazy() && !node().isStatic()) {
			writeLazyName(builder).append(" = undefined;\n");
		}

		return super.write(builder);
	}

	@Override
	public StringBuilder writeBody(StringBuilder builder) {
		if (shouldUseShorthand()) {
			Node child = node().getScope().getVisibleChild(0);
			if (child instanceof Return) {
				child = ((Return)child).getValueNode();
			}
			return getWriter(child).writeExpression(builder);
		}
		builder.append("{\n");

		if (node().getLazy()) {
			builder.append("return typeof ");
			writeLazyAccess(builder).append(" !== 'undefined' ? ");
			writeLazyAccess(builder).append(" : (");
			writeLazyAccess(builder);
			builder.append(" = (() => {\n");
		}

		if (shouldUseSelfVariable())
		{
			builder.append("let self = this;\n\n");
		}

		getWriter(node().getScope()).write(builder, false, false);

		if (node().getLazy()) {
			builder.append("})());\n");
		}

		return builder.append('}');
	}

	public StringBuilder writeLazyName(StringBuilder builder) {
		builder.append("__lazy_");
		return writeName(builder);
	}

	public StringBuilder writeLazyAccess(StringBuilder builder) {
		if (node().isStatic()) {
			getWriter(node().getParentClass()).writeName(builder).append(".");
		} else {
			builder.append("this.");
		}

		return writeLazyName(builder);
	}

	@Override
	public StringBuilder writeName(StringBuilder builder)
	{
		builder.append("accessor_");
		
		return super.writeName(builder);
	}
}
