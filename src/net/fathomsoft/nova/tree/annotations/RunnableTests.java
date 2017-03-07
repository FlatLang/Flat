package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;

import java.util.ArrayList;

interface RunnableTests
{
	default NovaMethodDeclaration getRunTestsMethod()
	{
		ClassDeclaration clazz = (ClassDeclaration)((Node)this).parent;
		
		MethodList.SearchFilter filter = new MethodList.SearchFilter();
		filter.checkAncestor = false;
		
		return (NovaMethodDeclaration)clazz.getMethod(clazz, "runTests", filter, new Value[0]);
	}
	
	default ArrayList<NovaMethodDeclaration> getMethodsWithTypeAnnotation(Class type)
	{
		final ArrayList<NovaMethodDeclaration> methods = new ArrayList<>();
		
		ClassDeclaration clazz = (ClassDeclaration)((Node)this).parent;
		
		clazz.getMethodList().forEachNovaMethod(method -> {
			if (method.containsAnnotationOfType(type))
			{
				methods.add(method);
			}
		});
		
		return methods;
	}
	
	default void callMethodsWithAnnotationOfType(Class type)
	{
		NovaMethodDeclaration runMethod = getRunTestsMethod();
		
		getMethodsWithTypeAnnotation(type).forEach(method -> {
			if (method.getParameterList().getNumParameters() != 0)
			{
				SyntaxMessage.error("Test method '" + method.getName() + "' cannot contain parameters", method);
			}
			
			MethodCall call = MethodCall.decodeStatement(runMethod, method.getName() + "()", Location.INVALID, true, false, method);
			
			runMethod.addChild(call);
		});
	}
	
	default void writeMessage(Literal message)
	{
		NovaMethodDeclaration method = getRunTestsMethod();
		
		StaticClassReference write = (StaticClassReference)SyntaxTree.decodeIdentifierAccess(method, "Console.writeLine(\"\")", Location.INVALID, true);
		
		((MethodCall)write.getReturnedNode()).getArgumentList().getVisibleChild(0).replaceWith(message);
		
		Node first = method.getScope().getFirstStatement();
		
		if (first != null)
		{
			method.getScope().addChildBefore(first, write);
		}
		else
		{
			method.getScope().addChild(write);
		}
	}
	
	default Variable generateTimer(Node parent, String prefix)
	{
		String timerName = parent.getAncestorWithScope().getScope().getUniqueName(prefix + "Timer");
		
		Assignment a = Assignment.decodeStatement(parent, "let " + timerName + " = new Timer().start()", Location.INVALID, true);
		
		parent.addChild(a);
		a.onAfterDecoded();
		
		return a.getAssignedNode();
	}
	
	default Variable stopTimer(Node parent, Variable timer)
	{
		Variable stopped = (Variable)SyntaxTree.decodeIdentifierAccess(parent, timer.getName() + ".stop()", Location.INVALID, true, false, true);
		
		parent.addChild(stopped);
		stopped.onAfterDecoded();
		
		return stopped;
	}
	
	default void addResultCall(Node parent, boolean success)
	{
		
	}
}