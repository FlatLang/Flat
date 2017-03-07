package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

interface RunnableTests
{
	default void implementTestRunner()
	{
		ClassDeclaration clazz = (ClassDeclaration)((Node)this).parent;
		
		if (!clazz.implementsInterface(((Node)this).getProgram().getClassDeclaration("novex/nest/TestRunner")))
		{
			((Node)this).getFileDeclaration().addImport("novex/nest/TestRunner");
			
			TraitImplementation implementation = TraitImplementation.decodeStatement(clazz, "TestRunner", Location.INVALID, true);
			
			clazz.getInterfacesImplementationList().addChild(implementation);
		}
	}
	
	default Assignment initializeTestCases(InitializationMethod parent)
	default MethodCall.Pair<FieldDeclaration, FieldDeclaration> generateTestRunnerFields(String testRunnerType, String name, String initializer)
	{
		((Annotation)this).getFileDeclaration().addImport("novex/nest/TestRunnerModel");
		
		FieldDeclaration runner = addFieldInitialization(testRunnerType, name, initializer);
		runner.validate(((Annotation)this).getProgram().getPhase());
		
		FieldDeclaration override = FieldDeclaration.decodeStatement(((Annotation)this).parent, "visible TestRunnerModel model => " + name, Location.INVALID, true);
		
		((Annotation)this).parent.addChild(override);
		override.onAfterDecoded();
		override.validate(SyntaxTree.PHASE_INSTANCE_DECLARATIONS);
		override.validate(SyntaxTree.PHASE_METHOD_CONTENTS);
		
		return new MethodCall.Pair<>(runner, override);
	}
	
	{
		((Node)this).getFileDeclaration().addImport("novex/nest/TestCase");
		
		String initializerValues = "[" + String.join(", ", ((TestableAnnotation)parent.getParentClass().getAnnotationOfType(TestableAnnotation.class))
			.getMethodsWithTypeAnnotation(TestAnnotation.class).stream()
			.map(x -> (TestAnnotation)x.getAnnotationOfType(TestAnnotation.class))
			.map(x -> x.testCase.getName()).collect(Collectors.toList())) + "]";
		
		if (initializerValues.length() == 2)
		{
			initializerValues = "new TestCase[0]";
		}
		
		Assignment a = Assignment.decodeStatement(parent, "this.testCases = " + initializerValues, Location.INVALID, true);
		
		if (parent.getScope().getFirstStatement() == null)
		{
			parent.getScope().addChild(a);
		}
		else
		{
			parent.getScope().addChildBefore(parent.getScope().getFirstStatement(), a);
		}
		
		a.onAfterDecoded();
		
		return a;
	}
	
	default NovaMethodDeclaration getRunTestsMethod()
	{
		ClassDeclaration clazz = ((Node)this).getParentClass(true);
		
		MethodList.SearchFilter filter = new MethodList.SearchFilter();
		filter.checkAncestor = false;
		filter.checkInterfaces = false;
		
		MethodDeclaration[] methods = clazz.getMethods("runTests", filter);
		
		return methods.length == 1 ? (NovaMethodDeclaration)methods[0] : null;
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
	
	default void writeMessage(Literal message, NovaMethodDeclaration method, boolean newLine)
	{
		StaticClassReference write = (StaticClassReference)SyntaxTree.decodeIdentifierAccess(method, "Console.write" + (newLine ? "Line" : "") + "(\"\")", Location.INVALID, true);
		
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
		parent.getFileDeclaration().addImport("nova/time/Timer");
		
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
	
	default void addResultCall(Node parent, boolean success, Variable timer, NovaMethodDeclaration method)
	{
		parent.getFileDeclaration().addImport("novex/nest/TestResult");
		
		TestAnnotation test = (TestAnnotation)method.getAnnotationOfType(TestAnnotation.class);
		
		String description = test.parameters.containsKey("message") && ((Literal)test.parameters.get("message")).isStringInstantiation() ? ", " + ((Node)test.parameters.get("message")).generateNovaInput() : "";
		
		String name = parent.getAncestorWithScope().getScope().getUniqueName("testResult");
		Assignment a = Assignment.decodeStatement(parent, "let " + name + " = new TestResult(" + (success ? "true" : "false") + ", " + timer.getName() + ", " + test.testCase.getName() + ")", Location.INVALID, true);
		
		parent.addChild(a);
		a.onAfterDecoded();
		
		Consumer<NovaMethodDeclaration> callFunction = m -> {
			MethodCall call = MethodCall.decodeStatement(parent, m.getName() + "(" + name + ")", Location.INVALID, true, false, m);
			
			parent.addChild(call);
			call.onAfterDecoded();
		};
		
		if (success)
		{
			getMethodsWithTypeAnnotation(TestSuccessAnnotation.class).forEach(callFunction);
		}
		else
		{
			getMethodsWithTypeAnnotation(TestFailureAnnotation.class).forEach(callFunction);
		}
		
		getMethodsWithTypeAnnotation(TestResultAnnotation.class).forEach(callFunction);
		
		if (parent.getParentMethod(true).getParameter("propagateFunc") != null)
		{
			MethodCall call = MethodCall.decodeStatement(parent, "propagateFunc(" + name + ")", Location.INVALID, true, false);
			
			parent.addChild(call);
			call.onAfterDecoded();
		}
	}
}