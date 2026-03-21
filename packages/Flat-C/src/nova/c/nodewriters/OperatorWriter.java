package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class OperatorWriter extends IValueWriter
{
	public abstract Operator node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder);
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		if (node().operator.equals(Operator.AND))
		{
			return builder.append(Operator.AND_C);
		}
		else if (node().operator.equals(Operator.OR))
		{
			return builder.append(Operator.OR_C);
		}
		else if (node().operator.equals(Operator.UR_SHIFT))
		{
			return builder.append(Operator.UR_SHIFT_C);
		}
		
		return builder.append(node().operator);
	}
}