package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;

public abstract class GenericTypeArgumentWriter extends IIdentifierWriter
{
	public abstract GenericTypeArgument node();
	
	@Override
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
	{
		if (node().isFunctionType())
		{
			return builder.append(node().getName()).append(((FunctionType)node().getTypeObject()).closure.id);
		}
		
		return generateType(builder);
	}
}