package org.flat.tree.annotations;

import org.flat.tree.FlatMethodDeclaration;
import org.flat.tree.Parameter;
import org.flat.util.Location;

interface NestAnnotation
{
	default void addOutputStreamParameter(FlatMethodDeclaration method)
	{
		method.getFileDeclaration().addImport("flat/io/OutputStream");
		
		Parameter param = Parameter.decodeStatement(method.getParameterList(), "OutputStream out = Console.out", Location.INVALID, true);
		
		method.getParameterList().addChild(param);
		param.onAfterDecoded();
		param.validate(method.getProgram().getPhase());
		param.setProperty("userMade", false);
		method.addDefaultParameterInitializations();
	}
}