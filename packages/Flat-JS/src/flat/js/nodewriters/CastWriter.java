package flat.js.nodewriters;

import flat.tree.*;

public abstract class CastWriter extends IValueWriter
{
	public abstract Cast node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		String before = "";
		String after = "";

		ClassDeclaration charClass = node().getProgram().getClassDeclaration("flat/primitive/number/Char");
		ClassDeclaration integerClass = node().getProgram().getClassDeclaration("flat/primitive/number/Integer");

		ClassDeclaration cast = node().getTypeClass();
		ClassDeclaration value = node().getReturnedNode().getTypeClass(false);

		if (!node().getReturnedNode().isPrimitiveArray())
		{
			if (value == charClass && cast != charClass && cast.implementsInterface(integerClass))
			{
				after = ".charCodeAt(0)";
			}
			else if (value != null && value != charClass && value.implementsInterface(integerClass) && cast == charClass)
			{
				before = "String.fromCharCode(";
				after = ")";
			}
		}

		return builder.append(before).append(getWriter(node().getValueNode()).writeExpression()).append(after);
	}
}