package flat.java.nodewriters;

import flat.Flat;
import flat.tree.*;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.generics.GenericTypeParameterList;

public abstract class ValueWriter extends NodeWriter
{
	public abstract Value node();
	
	public final StringBuilder writeUseExpression()
	{
		return writeUseExpression(new StringBuilder());
	}
	
	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		return builder.append(writeArrayAccess());
	}
	
	public final StringBuilder writeArrayAccess()
	{
		return writeArrayAccess(new StringBuilder());
	}
	
	public StringBuilder writeArrayAccess(StringBuilder builder)
	{
		if (node().arrayAccess != null)
		{
			return getWriter(node().arrayAccess).writeExpression(builder);
		}
		
		return builder;
	}
	
	public final StringBuilder writeType()
	{
		return writeType(new StringBuilder());
	}
	
	public final StringBuilder writeType(boolean space)
	{
		return writeType(new StringBuilder(), space);
	}
	
	public final StringBuilder writeType(StringBuilder builder)
	{
		return writeType(builder, true);
	}
	
	public final StringBuilder writeType(StringBuilder builder, boolean space)
	{
		return writeType(builder, space, true);
	}
	
	public final StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive) {
		return writeType(builder, space, convertPrimitive, node().isPrimitiveType() && node().isPointer());
	}

	public final StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive) {
		return writeType(builder, space, convertPrimitive, boxPrimitive, null);
	}

	public final StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive, Value context) {
		return writeType(builder, space, convertPrimitive, boxPrimitive, context, true);
	}

	public final StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive, Value context, boolean writeGenerics) {
		return writeType(builder, space, convertPrimitive, boxPrimitive, context, writeGenerics, true);
	}

	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive, Value context, boolean  writeGenerics, boolean  writeArray)
	{
		if (node().isFunctionType()) {
			return getWriter(((FunctionType)node().getTypeObject()).closure).writeType(builder, space, true, true, context);
		}

		writeTypeName(builder, convertPrimitive, boxPrimitive, context);
		if (writeGenerics) writeGenericArguments(builder, context);
		if (writeArray) writeArrayDimensions(builder);
		
		if (space)
		{
			builder.append(' ');
		}
		
		return builder;
	}

	public final StringBuilder writeTypeName() {
		return writeTypeName(new StringBuilder());
	}

	public final StringBuilder writeTypeName(StringBuilder builder) {
		return writeTypeName(builder, true);
	}

	public final StringBuilder writeTypeName(StringBuilder builder, boolean convertPrimitive) {
		return writeTypeName(builder, convertPrimitive, node().isPrimitiveType() && node().isPrimitive(), null);
	}

	public StringBuilder writeTypeName(StringBuilder builder, boolean convertPrimitive, boolean boxPrimitive, Value context) {
		if (node().isNative()) {
			if (node().isPrimitiveType()) {
				writePrimitiveType(builder, convertPrimitive, boxPrimitive);
			} else {
				writeNativeType(builder);
			}
		} else if (node().isGenericType()) {
			writeGenericType(builder, context);
		} else if (node().getType() == null) {
			builder.append("void");
		} else if (convertPrimitive && !node().isPointer() && node().isPrimitiveType()) {
			writePrimitiveType(builder, true, boxPrimitive);
		} else if (node().isExternalType()) {
			builder.append(node().getType());
		} else {
			writeTypeClassName(builder);
		}

		return builder;
	}

	public StringBuilder writeGenericType(StringBuilder builder, Value context) {
		GenericTypeParameter param = node().getGenericTypeParameter();
		String name = param.getName();

		if (context != null) {
			GenericTypeArgument arg = param.getCorrespondingArgument(context);

			if (arg != null) {
				name = getWriter(arg).writeType(new StringBuilder(), false, false, true, null).toString();
			}
		}

		return builder.append(name);
	}

	private StringBuilder writeTypeClassName(StringBuilder builder) {
		ClassDeclaration c = node().getTypeClass();

		if (c == null) {
			if (node() instanceof FlatMethodDeclaration) {
				GenericTypeParameter param = ((FlatMethodDeclaration)node()).getMethodGenericTypeParameterDeclaration().getParameter(node().getType());

				if (param != null) {
					builder.append(param.getDefaultType());
				} else {
					Flat.debuggingBreakpoint(true);
					builder.append("BLOOP1");
					builder.append("/*");
					builder.append(node().getClass().getName());
					builder.append("*/");
				}
			} else if (node() instanceof ClosureDeclaration) {
				if (node().isGenericType()) {
					builder.append(node().getType());
				} else {
					ClassDeclaration typeClass = node().getTypeClass();

					if (typeClass != null) {
						getWriter(node().getTypeClass()).writeName(builder);
					} else {
						Flat.debuggingBreakpoint(true);
						builder.append("BLOOP1");
						builder.append("/*");
						builder.append(node().getType());
						builder.append(':');
						builder.append(node().getClass().getName());
						builder.append("*/");
					}
				}
			} else {
				Flat.debuggingBreakpoint(true);
				builder.append("BLOOP2");
				builder.append("/*");
				builder.append(node().getClass().getName());
				builder.append("*/");
			}
		} else {
			getWriter(node().getTypeClass()).writeName(builder);
		}

		return builder;
	}

	private StringBuilder writeNativeType(StringBuilder builder) {
		if (!node().isPrimitiveArray()) {
			return builder.append(node().getType());
		}
		if (node().isGenericType()) {
			return builder.append(node().getGenericTypeParameter().getName());
		}

		return writeTypeClassName(builder);
	}

	public final StringBuilder writeGenericArguments(StringBuilder builder) {
		return writeGenericArguments(builder, null);
	}

	public GenericTypeArgumentList getGenericTypeArgumentList() {
		return node().getGenericTypeArgumentList();
	}

	public StringBuilder writeGenericArguments(StringBuilder builder, Value context) {
		if (node().isGenericType()) return builder;

		GenericTypeArgumentList args = getGenericTypeArgumentList();

		boolean printCaret = true;
		int i = 0;

		if (args != null && args.getNumVisibleChildren() > 0) {
			printCaret = false;
			builder.append("<");

			for (; i < args.getNumVisibleChildren(); i++) {
				if (i > 0) builder.append(", ");

				writeGenericTypeArgument(builder, context, args.getVisibleChild(i));
			}

			builder.append(">");
		}

		ClassDeclaration typeClass = node().getTypeClass();

		if (typeClass != null) {
			GenericTypeParameterList params = typeClass.getGenericTypeParameterDeclaration();

			if (params != null && params.getNumVisibleChildren() > 0) {
				if (printCaret) builder.append("<");

				for (; i < params.getNumVisibleChildren(); i++) {
					if (i > 0) builder.append(", ");

					getWriter(params.getVisibleChild(i)).writeDefaultType(builder);
				}

				if (printCaret) builder.append(">");
			}
		}

		return builder;
	}

	public StringBuilder writeGenericTypeArgument(StringBuilder builder, Value context, GenericTypeArgument arg) {
		return getWriter(arg).writeExpression(builder, null, context);
	}

	private StringBuilder writePrimitiveType(StringBuilder builder, boolean convertPrimitive, boolean boxPrimitive) {
		if (boxPrimitive) {
			if (!convertPrimitive) return builder.append(node().getType());

			switch (node().getType())
			{
				case "Byte":
					builder.append("java.lang.Byte");
					break;
				case "Char":
					builder.append("java.lang.Character");
					break;
				case "Short":
					builder.append("java.lang.Short");
					break;
				case "Int":
					builder.append("java.lang.Integer");
					break;
				case "Long":
					builder.append("java.lang.Long");
					break;
				case "Float":
					builder.append("java.lang.Float");
					break;
				case "Double":
					builder.append("java.lang.Double");
					break;
				case "Bool":
					builder.append("java.lang.Boolean");
					break;
			}
		} else {
			switch (node().getType()) {
				case "Byte":
					builder.append("byte");
					break;
				case "Char":
					builder.append("char");
					break;
				case "Short":
					builder.append("short");
					break;
				case "Int":
					builder.append("int");
					break;
				case "Long":
					builder.append("long");
					break;
				case "Float":
					builder.append("float");
					break;
				case "Double":
					builder.append("double");
					break;
				case "Bool":
					builder.append("boolean");
					break;
			}
		}

		return builder;
	}

	public StringBuilder writeArrayDimensions()
	{
		return writeArrayDimensions(new StringBuilder());
	}
	
	public StringBuilder writeArrayDimensions(StringBuilder builder)
	{
		for (int i = 0; i < node().getArrayDimensions(); i++)
		{
			builder.append("[]");
		}
		
		return builder;
	}
}
