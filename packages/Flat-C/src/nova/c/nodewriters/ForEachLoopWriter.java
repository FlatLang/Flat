package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ForEachLoopWriter extends LoopWriter
{
	public abstract ForEachLoop node();
	
	public StringBuilder generateSource(StringBuilder builde)
	{
		StringBuilder builder = new StringBuilder();
		
		Value hasNextCheck = node().getHasNextCheck();
		
		getWriter(node().elementDeclaration).generateSource(builder);
		
		builder.append("while (").append(getWriter(hasNextCheck).generateSourceFragment()).append(')').append('\n');
		
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			if (child != node().getArgumentList())
			{
				getWriter(child).generateSource(builder);
			}
		}
		
		return builde.append(builder);
	}
}