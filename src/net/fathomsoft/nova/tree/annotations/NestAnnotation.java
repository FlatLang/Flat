package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.tree.NovaMethodDeclaration;
import net.fathomsoft.nova.tree.Parameter;
import net.fathomsoft.nova.util.Location;

interface NestAnnotation
{
	default void addOutputStreamParameter(NovaMethodDeclaration method)
	{
		method.getFileDeclaration().addImport("nova/io/OutputStream");
		
		Parameter param = Parameter.decodeStatement(method.getParameterList(), "OutputStream out = Console.out", Location.INVALID, true);
		
		method.getParameterList().addChild(param);
		param.onAfterDecoded();
		param.validate(method.getProgram().getPhase());
		param.setProperty("userMade", false);
		method.addDefaultParameterInitializations();
	}
}