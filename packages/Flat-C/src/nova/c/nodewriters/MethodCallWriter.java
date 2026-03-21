package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;

public abstract class MethodCallWriter extends VariableWriter
{
	public abstract MethodCall node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder).append(';').append('\n');
	}
	
	/**
	 * Generate the representation of when the method call is being used
	 * in action.
	 *
	 * @return What the method call looks like when it is being used in
	 * 		action.
	 */
	public StringBuilder generateUseOutput(StringBuilder builder, boolean pointer, boolean checkAccesses)
	{
		VariableDeclaration method   = node().getMethodDeclaration();
		CallableMethod      callable = (CallableMethod)method;
		
		boolean requiresCast = checkAccesses && node().doesAccess() && node().getAccessedNode() instanceof MethodCall == false && node().isGenericType();
		
		if (requiresCast)
		{
			builder.append('(');
			generateTypeCast(builder);
		}
		
		CallableMethod base = node().getCallableMethodBase();
		
		/*if (callable instanceof ClosureVariable)
		{
			ClosureVariable var = (ClosureVariable)callable;
			
			if (!node().isAccessed())
			{
				getWriter(var).writeInstanceAccess(builder);
			}
			
			getWriter(var).generateSourceFragment(builder);
		}
		else */if (callable instanceof FirstClassClosureDeclaration)
		{
			FirstClassClosureDeclaration closure = (FirstClassClosureDeclaration)callable;
			
			builder.append("(");
			getWriter(closure).generateTypeCast(builder);
			getWriter(closure.reference).generateUseOutput(builder).append("->func)");
		}
		else
		{
			getWriter((Identifier)base).generateSourceName(builder);
		}
		
		MethodCallArgumentList args = node().getArgumentList();
		
		getWriter(args).generateSource(builder);
		
		if (requiresCast)
		{
			builder.append(')');
		}
		
		return builder;
	}
	
	public StringBuilder generateExtraArguments(StringBuilder builder)
	{
		return builder;
	}
	
	public StringBuilder generateObjectReferenceIdentifier(StringBuilder builder)
	{
		return builder;
	}
}