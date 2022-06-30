package org.flatlang.tree.annotations;

import org.flatlang.tree.FlatMethodDeclaration;
import org.flatlang.tree.Parameter;
import org.flatlang.util.Location;

interface NestAnnotation
{
	default void addOutputStreamParameter(FlatMethodDeclaration method)
	{
		method.getFileDeclaration().addImport("flatlang/io/OutputStream");
		
		Parameter param = Parameter.decodeStatement(method.getParameterList(), "OutputStream out = Console.out", Location.INVALID, true);
		
		method.getParameterList().addChild(param);
		param.onAfterDecoded();
		param.validate(method.getProgram().getPhase());
		param.setProperty("userMade", false);
		method.addDefaultParameterInitializations();
	}
}