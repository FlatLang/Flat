package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;

import java.util.ArrayList;
import java.util.function.Function;

public class TestSuiteAnnotation extends Annotation implements RunnableTests
{
	public boolean generatedRunTestsMethod, writeMessage;
	public AnonymousCompilerMethod propagationFunction;
	
	public TestSuiteAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public String[] defaultParameterNames()
	{
		return new String[] { "classes" };
	}
	
	@Override
	public String[][] defaultParameterTypes()
	{
		return new String[][] { { "list" } };
	}
	
	public static TestSuiteAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("TestSuite"))
		{
			TestSuiteAnnotation n = new TestSuiteAnnotation(parent, location);
			
			n.parseParameters(parameters);
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			implementTestRunner();
		}
		else if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			NovaMethodDeclaration method = getRunTestsMethod();
			
			generatedRunTestsMethod = method == null || method.isPropertyTrue("generated");
			
			if (method == null)
			{
				method = BodyMethodDeclaration.decodeStatement(parent, "public runTests()", Location.INVALID, true);
				
				parent.addChild(method);
				
				method.setProperty("generated", true);
			}
		}
		else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			String[] classNames = (String[])parameters.get("classes");
			
			boolean containsResultFunction =
				getMethodsWithTypeAnnotation(TestSuccessAnnotation.class).size() > 0 ||
				getMethodsWithTypeAnnotation(TestFailureAnnotation.class).size() > 0 ||
				getMethodsWithTypeAnnotation(TestResultAnnotation.class).size() > 0;
			
			for (String className : classNames)
			{
				ClassDeclaration c = getFileDeclaration().getImportedClass(this, className);
				
				SyntaxMessage.queryError("Class '" + className + "' is not imported", this, c == null);
				SyntaxMessage.queryError("Class '" + className + "' is does not contain Testable annotation", this, !c.containsAnnotationOfType(TestableAnnotation.class));
				SyntaxMessage.queryError("Testable class '" + className + "' requires a default constructor", this, !c.containsDefaultConstructor());
				
				TestableAnnotation testable = (TestableAnnotation)c.getAnnotationOfType(TestableAnnotation.class);
				
				if (testable.generatedRunTestsMethod)
				{
					NovaMethodDeclaration method = testable.getRunTestsMethod();
					
					method.getFileDeclaration().addImport("novex/nest/TestResult");
					
					ClosureDeclaration param = (ClosureDeclaration)Parameter.decodeStatement(method.getParameterList(), "propagateFunc(TestResult) = {}", Location.INVALID, true);
					
					method.getParameterList().addChild(param);
					param.onAfterDecoded();
					param.validate(phase);
					method.addDefaultParameterInitializations();
				}
			}
			
			if (containsResultFunction)
			{
				getFileDeclaration().addImport("novex/nest/TestResult");
				propagationFunction = getParentClass(true).generateAnonymousFunction();
				
				Parameter param = Parameter.decodeStatement(propagationFunction.getParameterList(), "TestResult result", Location.INVALID, true);
				propagationFunction.getParameterList().addChild(param);
				
				getParentClass(true).addChild(propagationFunction);
				
				IfStatement ifStatement = IfStatement.decodeStatement(propagationFunction, "if (result.success)", Location.INVALID, true);
				propagationFunction.addChild(ifStatement);
				
				ElseStatement elseStatement = ElseStatement.decodeStatement(propagationFunction, "else", Location.INVALID, true);
				propagationFunction.addChild(elseStatement);
				
				Function<NovaMethodDeclaration, MethodCall> generateCall = m -> {
					return MethodCall.decodeStatement(propagationFunction, m.getName() + "(result)", Location.INVALID, true);
				};
				
				getMethodsWithTypeAnnotation(TestSuccessAnnotation.class).forEach(m -> {
					ifStatement.addChild(generateCall.apply(m));
				});
				getMethodsWithTypeAnnotation(TestFailureAnnotation.class).forEach(m -> {
					elseStatement.addChild(generateCall.apply(m));
				});
				getMethodsWithTypeAnnotation(TestResultAnnotation.class).forEach(m -> {
					propagationFunction.addChild(generateCall.apply(m));
				});
			}
			
			insertMessage();
		}
		else if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (generatedRunTestsMethod)
			{
				addTestCalls();
			}
		}
		
		return result;
	}
	
	public void insertMessage()
	{
//		if (parameters.containsKey("message"))
//		{
//			Literal message = (Literal)parameters.get("message");
//			
//			if (!message.isNullLiteral() && !message.value.equals("false") && message.value.length() > 2)
//			{
//				message.value = "\"================== " + message.value.substring(1, message.value.length() - 1) + " ==================\"";
//				
//				writeMessage(message);
//			}
//		}
//		else
//		{
//			ClassDeclaration clazz = (ClassDeclaration)parent;
//			
//			writeMessage((Literal)Literal.decodeStatement(parent, "\"================== Testing " + clazz.getName() + " ==================\"", Location.INVALID, true, true));
//		}
	}
	
	public void writeMessage(Literal message)
	{
		RunnableTests.super.writeMessage(message, getRunTestsMethod(), true);
		
		writeMessage = true;
	}
	
	public void addTestCalls()
	{
		ClassDeclaration clazz = (ClassDeclaration)parent;
		
		final NovaMethodDeclaration runMethod = getRunTestsMethod();
		
		callMethodsWithAnnotationOfType(InitTestClassAnnotation.class);
		
		String[] classNames = (String[])parameters.get("classes");
		
		for (String className : classNames)
		{
			Assignment call = Assignment.decodeStatement(runMethod, "let test" + className + " = new " + className + "()", getLocationIn(), true);
			
			runMethod.addChild(call);
			call.onAfterDecoded();
			
			ClassDeclaration c = getFileDeclaration().getImportedClass(this, className);
			
			TestableAnnotation testable = (TestableAnnotation)c.getAnnotationOfType(TestableAnnotation.class);
			
			clazz.getMethodList().forEachNovaMethod(x -> {
				if (x instanceof InitializationMethod)
				{
					initializeTestCount((InitializationMethod)x, testable.getMethodsWithTypeAnnotation(TestAnnotation.class).size());
				}
			});
		}
		
		for (String className : classNames)
		{
			ClassDeclaration c = getFileDeclaration().getImportedClass(this, className);
			
			TestableAnnotation testable = (TestableAnnotation)c.getAnnotationOfType(TestableAnnotation.class);
			
			NovaMethodDeclaration method = testable.getRunTestsMethod();
			
			String propFunc = propagationFunction != null ? propagationFunction.getName() : "";
			
			Variable call = (Variable)SyntaxTree.decodeIdentifierAccess(runMethod,  "test" + className + "." + method.getName() + "(" + propFunc + ")", getLocationIn(), true);
			runMethod.addChild(call);
		}
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (!checkDuplicate(next, throwError))
		{
			if (next instanceof ClassDeclaration)
			{
				// valid
			}
			else
			{
				return invalidApplication(next, throwError);
			}
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public TestSuiteAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		TestSuiteAnnotation node = new TestSuiteAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public TestSuiteAnnotation cloneTo(TestSuiteAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public TestSuiteAnnotation cloneTo(TestSuiteAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
}