package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.util.SyntaxUtils;

public abstract class BinaryOperationWriter extends IValueWriter
{
	public abstract BinaryOperation node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		generateSourceFragment(builder);
		
		if (node().getOperator().isShorthand())
		{
			builder.append(";\n");
		}
		
		return builder;
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		Operator operator = node().getOperator();
		
		if (node().getNumChildren() == 1)
		{
			Value operand = node().getLeftOperand();
			
			return getWriter(operand).generateSourceFragment(builder);
		}
		
		String leftCast = "";
		String rightCast = "";
		
		Value left = node().getLeftOperand();
		Value right = node().getRightOperand();
		Value leftReturned = left.getReturnedNode();
		Value rightReturned = right.getReturnedNode();
		
		boolean zero = leftReturned instanceof Literal && ((Literal)leftReturned).value.equals("0") || rightReturned instanceof Literal && ((Literal)rightReturned).value.equals("0");
		
		if (!zero)
		{
			boolean checkType = false;
			
			switch (node().getOperator().getOperator())
			{
				case (Operator.EQUALS):
				case (Operator.NOT_EQUAL):
					checkType = true;
					break;
				default:
					checkType = false;
			}
			
			if (checkType && !leftReturned.isPrimitive())
			{
				if (!leftReturned.getType().equals(rightReturned.getType()))
				{
					Value common = SyntaxUtils.getTypeInCommon(leftReturned, rightReturned);
					
					if (common != null)
					{
						leftCast = getWriter(common).generateTypeCast(new StringBuilder(), true, false).toString();
						rightCast = leftCast;
					}
				}
			}
			
			if (checkType && leftReturned.isPrimitive() != rightReturned.isPrimitive() && !Literal.isNullLiteral(leftReturned) && !Literal.isNullLiteral(rightReturned))
			{
				if (leftReturned.isPrimitive())
				{
					leftCast = getWriter(rightReturned).generateTypeCast() + "(intptr_t)" + leftCast;
				}
				else
				{
					rightCast = getWriter(leftReturned).generateTypeCast() + "(intptr_t)" + rightCast;
				}
			}
		}
		
		if (leftCast == "" && rightCast == "")
		{
			if (leftReturned.isOriginallyGenericType())
			{
				leftCast = getWriter(leftReturned).generateTypeCast(new StringBuilder(), true, false).toString();
			}
			if (rightReturned.isOriginallyGenericType())
			{
				rightCast = getWriter(rightReturned).generateTypeCast(new StringBuilder(), true, false).toString();
			}
		}
		
		if (operator.operator.equals(Operator.UR_SHIFT))
		{
			leftCast = "(unsigned int)" + leftCast;
		}
		else if (operator.operator.equals(Operator.MODULO) && (leftReturned.getTypeClass().isOfType("nova/primitive/number/RealNumber") || rightReturned.getTypeClass().isOfType("nova/primitive/number/RealNumber")))
		{
			return builder.append("fmod(").append(leftCast).append(getWriter(left).generateSourceFragment()).append(", ").append(rightCast).append(getWriter(right).generateSourceFragment()).append(")");
		}
		
		return builder.append(leftCast).append(getWriter(left).generateSourceFragment()).append(' ')
			.append(getWriter(operator).generateSourceFragment()).append(' ')
			.append(rightCast).append(getWriter(right).generateSourceFragment());
	}
}