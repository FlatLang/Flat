package nova.c.nodewriters;

import net.fathomsoft.nova.tree.ClosureDeclaration;
import net.fathomsoft.nova.tree.DefaultArgument;
import net.fathomsoft.nova.tree.Value;

public abstract class DefaultArgumentWriter extends IValueWriter
{
	public abstract DefaultArgument node();
	
	public static StringBuilder generateDefaultArgumentOutput(StringBuilder builder, Value parameter)
	{
		if (parameter instanceof ClosureDeclaration)
		{
			builder.append("0, 0, 0");
		}
		else
		{
			if (parameter.isPrimitive())
			{
				builder.append("(int)(intptr_t)").append(ValueWriter.NULL_IDENTIFIER);
			}
			else
			{
				builder.append(0);
			}
		}
		
		return builder;
	}
}