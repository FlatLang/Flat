package nova.c.nodewriters;

import net.fathomsoft.nova.tree.variables.Super;

public abstract class SuperWriter extends PriorityWriter
{
	public abstract Super node();
	
	@Override
	public StringBuilder generateSource(StringBuilder builder)
	{
		return super.generateSourceFragment(builder).append(";\n");
	}
}