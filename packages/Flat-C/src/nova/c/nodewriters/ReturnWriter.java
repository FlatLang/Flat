package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.util.SyntaxUtils;

public abstract class ReturnWriter extends IValueWriter
{
	public abstract Return node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder).append(";\n");
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		builder.append("return");
		
		Value value = node().getValueNode();
		
		if (value != null)
		{
			builder.append(' ');
			
			Value returned = value.getReturnedNode();
			
			NovaMethodDeclaration method = node().getParentMethod();
			
			if (value.getReturnedNode().isGenericType(true) || !SyntaxUtils.isSameType(node().getParentMethod(), returned, false) ||
				(returned instanceof MethodCall && !SyntaxUtils.isSameType(node().getParentMethod(), (Value)((MethodCall)returned).getCallableMethodBase())))
			{
				getWriter(method).generateTypeCast(builder).append(getWriter(returned).generatePointerToValueConversion(returned));
			}
			
//			if (node().getParentMethod().getTypeObject() instanceof FunctionType && returned instanceof Closure)
//			{
//				getWriter(method).generateTypeCast(builder);
//			}
			
			getWriter(value).generateSourceFragment(builder);
		}
		
		return builder;
	}
}