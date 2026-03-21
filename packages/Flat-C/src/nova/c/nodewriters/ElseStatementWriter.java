package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ElseStatementWriter extends ControlStatementWriter
{
	public abstract ElseStatement node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		builder.append("else");
		
		if (node().getNumChildren() == 2)
		{
			Node child = node().getChild(1);
			
			if (child instanceof IfStatement)
			{
				builder.append(' ');
				
				getWriter(child).generateSourceFragment(builder);
			}
		}
		
		builder.append('\n');
		
		Scope scope = node().getDecodedParent().getScope();
		
		return getWriter(scope).generateSource(builder);
	}
}