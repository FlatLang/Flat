package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ForLoopWriter extends LoopWriter
{
	public abstract ForLoop node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		Assignment initialization = node().getLoopInitialization();
		Node       condition      = node().getCondition();
		Node       update         = node().getLoopUpdate();
		
		getWriter(node().elementDeclaration).generateSource(builder);
		
		if (initialization != null)
		{
			getWriter(initialization).generateSource(builder);//.append('\n');
		}
		
		builder.append("for (; ");
		
		if (condition != null)
		{
			getWriter(condition).generateSourceFragment(builder);
		}
		
		builder.append("; ");
		
		if (update != null)
		{
			getWriter(update).generateSourceFragment(builder);
		}
		
		builder.append(')').append('\n');
		
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			if (child != node().getArgumentList())
			{
				getWriter(child).generateSource(builder);
			}
		}
		
		return builder;
	}
}