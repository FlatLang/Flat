package flat.js.nodewriters;

import flat.tree.AssignmentMethod;
import flat.tree.ClassDeclaration;
import flat.tree.Constructor;
import flat.tree.generics.GenericTypeParameter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ConstructorWriter extends BodyMethodDeclarationWriter
{
	public abstract Constructor node();

	@Override
	public StringBuilder writeBody(StringBuilder builder)
	{
		builder.append("{\n");

		ClassDeclaration extended = node().getDeclaringClass().getExtendedClassDeclaration();

		builder.append("var __value = new ");
		getWriter(node().getDeclaringClass()).writeName(builder).append("();\n");

		java.util.List<GenericTypeParameter> params = getReifiedParameters();

		params.forEach(p -> builder.append("__value.").append(p.getName()).append(" = ").append(p.getName()).append("_value;\n"));

		writeExtensionAssignmentMethodCalls(builder, extended);

//		builder.append("this.__proto__ = ").append(getWriter(node().getDeclaringClass()).writeName()).append(".prototype;\n\n");

		AssignmentMethod assignmentMethod = node().getParentClass().getAssignmentMethodNode();

		getWriter(assignmentMethod).writeAssignedVariable(builder).append(".apply(__value, [].slice.call(arguments));\n");

		getWriter(node().getScope()).write(builder, false, true);

		builder.append("return __value;\n");

		return builder.append('}');
	}

	private static void writeExtensionAssignmentMethodCalls(StringBuilder builder, ClassDeclaration extended) {
		if (extended != null) {
			ClassDeclaration superExtended = extended.getExtendedClassDeclaration();

			if (superExtended != null) {
				writeExtensionAssignmentMethodCalls(builder, superExtended);
			}

			getWriter(extended.getAssignmentMethodNode())
				.writeAssignedVariable(builder)
				.append(".apply(__value, [].slice.call(arguments));\n");
		}
	}

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		writeAssignedVariable(builder).append(" = function ");

		builder.append("(");
		getWriter(node().getParameterList()).write(builder, false);

		java.util.List<GenericTypeParameter> params = getReifiedParameters();

		if (params.size() > 0) {
			if (node().getParameterList().getNumVisibleChildren() > 0) {
				builder.append(", ");
			}

			builder.append(params.stream()
				.map(p -> p.getName()  + "_value")
				.collect(Collectors.joining(", ")));
		}

		builder.append(") ");


		writeBody(builder);

		return builder.append(";\n\n");
	}

	@Override
	public StringBuilder writePrototypeAccess(StringBuilder builder) {
		return builder;
	}

	@Override
	public StringBuilder writeAssignedVariable(StringBuilder builder)
	{
		return writeName(builder);//builder.append("flatConstructors.new").append(writeName());
	}

	public StringBuilder writeConstructorListName()
	{
		return writeConstructorListName(new StringBuilder());
	}

	public StringBuilder writeConstructorListName(StringBuilder builder)
	{
		java.util.List<ClassDeclaration> dupes = getWriter(node().getParentClass()).getClassesWithSameName();

		builder.append("new");

		if (dupes.size() > 0) {
			return builder.append(node().getParentClass().getClassLocation().replaceAll("[^\\w\\d_]", "_"));
		} else {
			return super.writeName(builder);
		}
	}

	@Override
	public StringBuilder writeName(StringBuilder builder)
	{
		return builder.append("flatConstructors.").append(writeConstructorListName());
	}

	private List<GenericTypeParameter> getReifiedParameters() {
		return node().getDeclaringClass().getReifiedParameters();
	}
}
