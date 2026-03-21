package flat.js.nodewriters;

import flat.tree.*;

public abstract class BinaryOperationWriter extends IValueWriter
{
	public abstract BinaryOperation node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		boolean requiresFloor = false;

		ClassDeclaration charClass = node().getProgram().getClassDeclaration("flat/primitive/number/Char");

		boolean numerical = node().getOperator().isNumerical();
		boolean shorthand = node().getOperator().isShorthand();
		boolean leftRequiresToCharCode = numerical && node().getLeftOperand().getReturnedNode().getTypeClass() == charClass;
		boolean rightRequiresToCharCode = numerical && node().getRightOperand().getReturnedNode().getTypeClass() == charClass;

		if (node().getOperator().getOperator().equals("/") || node().getOperator().getOperator().equals("%"))
		{
			ClassDeclaration integer = node().getProgram().getClassDeclaration("flat/primitive/number/Integer");

			requiresFloor = node().getLeftOperand().getReturnedNode().getTypeClass().isOfType(integer) &&
					node().getRightOperand().getReturnedNode().getTypeClass().isOfType(integer);

			if (!requiresFloor) {
				builder.append("/*").append(node().getLeftOperand().getReturnedNode().getType()).append("*/");
				builder.append("/*").append(node().getRightOperand().getReturnedNode().getType()).append("*/");
			}
		}

		if (requiresFloor)
		{
			builder.append("~~(");
		}

		if (shorthand && leftRequiresToCharCode)
		{
			builder.append("(");
			getWriter(node().getLeftOperand()).writeExpression(builder).append(" = String.fromCharCode(");
		}

		getWriter(node().getLeftOperand()).writeExpression(builder);

		if (leftRequiresToCharCode)
		{
			builder.append(".charCodeAt(0)");
		}

		if (shorthand && leftRequiresToCharCode) {
			builder.append(' ').append(node().getOperator().getNonShorthand()).append(' ');
		} else if (node().getOperator().getOperator().equals(Operator.EQUALS)) {
			builder.append(' ').append("===").append(' ');
		} else {
			builder.append(' ').append(getWriter(node().getOperator()).write()).append(' ');
		}

		builder.append(getWriter(node().getRightOperand()).writeExpression());

		if (rightRequiresToCharCode)
		{
			builder.append(".charCodeAt(0)");
		}

		if (shorthand && leftRequiresToCharCode)
		{
			builder.append("))");
		}

		if (requiresFloor)
		{
			builder.append(')');
		}

		return builder;
	}
}