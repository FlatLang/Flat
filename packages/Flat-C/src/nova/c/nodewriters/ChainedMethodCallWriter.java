package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ChainedMethodCallWriter extends MethodCallWriter
{
	public abstract ChainedMethodCall node();
	
	@Override
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		builder.append("(");
		
		boolean call = true;
		
		if (node().chained != null)
		{
			ClosureDeclaration closure = ((FunctionType)node().getNovaTypeValue(node()).getTypeObject()).closure;
			
			if (((FirstClassClosureDeclaration)closure).reference instanceof ClosureDeclaration)
			{
				closure = (ClosureDeclaration)((FirstClassClosureDeclaration)closure).reference;
			}
			
			getWriter(closure).generateTypeCast(builder);
			
			builder.append("(");
			getWriter(node().variable).generateSourceName(builder).append(" = ");
			getWriter(node().variable).generateTypeCast(builder);
			
			getWriter(node().chained).generateSourceFragment(builder).append(")");
		}
		else if (node().variable != null)
		{
			getWriter(node().variable).generateSourceName(builder).append(" = (nova_funcStruct*)");
			
			getWriter(node().getChainBase()).generateUseOutput(builder);
			
			builder.append(")");
			
			call = false;
		}
		else
		{
			Identifier ref = node().getChainReference();
			
			getWriter(((FunctionType)ref.getTypeObject()).closure).generateTypeCast(builder);
			
			getWriter(ref).generateSourceName(builder);
		}
		
		if (call)
		{
			builder.append("->func)");
			
			MethodCallArgumentList args;
			
			if (node().chained == null || node().chained.chained != null || node().chained.variable == null)
			{
				args = node().getArgumentList();
			}
			else
			{
				args = node().chained.getArgumentList();
			}
			
			getWriter(args).generateSource(builder);
		}
		
		return builder;
	}
}