package nova.c.nodewriters;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.util.SyntaxUtils;

public abstract class LiteralWriter extends IValueWriter implements AccessibleWriter
{
	public abstract Literal node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		return generateSource(builder);
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder).append(";\n");
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		if (node().isSpecialFragment())
		{
			return generateSpecialFragment(builder);
		}
		
		return generateUseOutput(builder).append(generateChildrenSourceFragment());
	}
	
	public StringBuilder generateUseOutput(StringBuilder builder)
	{
		if (!node().isWithinExternalContext() && node().isStringInstantiation())
		{
			Instantiation str = node().getStringInstantiation();
			
			return getWriter(str).generateSourceFragment(builder);
		}
		else if (node().isNullLiteral())
		{
			return generateNullOutput(builder);
		}
		else if (node().value.equals(Literal.TRUE_IDENTIFIER))
		{
			return builder.append(1);
		}
		else if (node().value.equals(Literal.FALSE_IDENTIFIER))
		{
			return builder.append(0);
		}
		else if (SyntaxUtils.isInteger(node().value))
		{
			long l = Long.parseLong(node().value);
			
			if (l == Long.MIN_VALUE)
			{
				return builder.append("(").append(l + 1).append("LL").append(" - 1)");
			}
			else if (l > Integer.MAX_VALUE || l <= Integer.MIN_VALUE)
			{
				return builder.append(node().value).append("LL");
			}
		}
		
		return builder.append(node().value);
	}
}