package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.exceptionhandling.Catch;
import net.fathomsoft.nova.tree.exceptionhandling.Try;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TestableAnnotation extends Annotation implements ModifierAnnotation, RunnableTests
{
	public String aliasUsed;
	public boolean generatedRunTestsMethod, writeMessage;
	public FieldDeclaration runner;
	
	@Override
	public String getAliasUsed()
	{
		return aliasUsed;
	}
	
	@Override
	public void setAliasUsed(String aliasUsed)
	{
		this.aliasUsed = aliasUsed;
	}
	
	public TestableAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public String[] defaultParameterNames()
	{
		return new String[] { "message" };
	}
	
	@Override
	public String[][] defaultParameterTypes()
	{
		return new String[][] { { "Literal" } };
	}
	
	public static TestableAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Testable"))
		{
			TestableAnnotation n = new TestableAnnotation(parent, location);
			
			n.parseParameters(parameters);
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public void onAdded(Node parent)
	{
		ModifierAnnotation.super.onAdded(parent);
		super.onAdded(parent);
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
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			searchRunTestsMethod();
		}
		else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
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
	
	public NovaMethodDeclaration searchRunTestsMethod()
	{
		NovaMethodDeclaration method = getRunTestsMethod();
		
		if (method == null)
		{
			getFileDeclaration().addImport("novex/nest/TestResult");
			getFileDeclaration().addImport("nova/io/OutputStream");
			
			method = BodyMethodDeclaration.decodeStatement(parent, "public async runTests(onResult(TestResult) = {}, OutputStream out = Console.out)", Location.INVALID, true);
			
			parent.addChild(method);
			
			generatedRunTestsMethod = true;
		}
		
		return method;
	}
	
	public FieldDeclaration generateTestRunner()
	{
		if (runner == null)
		{
			getFileDeclaration().addImport("novex/nest/TestCase");
			getFileDeclaration().addImport("novex/nest/TestRunnerModel");
			NovaMethodDeclaration method = searchRunTestsMethod();
			
			String name = method.getScope().getUniqueName("_" + method.getName() + "TestRunner");
			String description = parameters.containsKey("message") && ((Literal)parameters.get("message")).isStringInstantiation() ? ", " + ((Literal)parameters.get("message")).generateNovaInput() : "";
			
			MethodCall.Pair<FieldDeclaration, FieldDeclaration> fields = generateTestRunnerFields("TestRunnerModel", name, "new TestRunnerModel(" + getTestCaseInitializer() + description + ")");
			
			runner = fields.a;
		}
		
		return runner;
	}
	
	public String getTestCaseInitializer()
	{
		String initializerValues = "[" + String.join(", ", getMethodsWithTypeAnnotation(TestAnnotation.class).stream()
			.map(x -> (TestAnnotation)x.getAnnotationOfType(TestAnnotation.class))
			.map(x -> x.generateTestCase().getName())
			.collect(Collectors.toList())) + "]";
		
		if (initializerValues.length() == 2)
		{
			initializerValues = "new TestCase[0]";
		}
		
		return initializerValues;
	}
	
	public void insertMessage()
	{
		if (parameters.containsKey("message"))
		{
			Literal message = (Literal)parameters.get("message");
			
			if (!message.isNullLiteral() && !message.value.equals("false") && message.value.length() > 2)
			{
				message.value = "\"" + message.value.substring(1, message.value.length() - 1) + "\"";
				
				writeMessage(message, getRunTestsMethod(), false, "Nest.out", "writeHeader");
			}
		}
		else
		{
			ClassDeclaration clazz = (ClassDeclaration)parent;
			getFileDeclaration().addImport("novex/nest/Nest");
			
			writeMessage((Literal)Literal.decodeStatement(parent, "\"Testing " + clazz.getName() + "\"", Location.INVALID, true, true), getRunTestsMethod(), false, "Nest.out", "writeHeader");
		}
	}
	
	public void writeMessage(Literal message)
	{
		RunnableTests.super.writeMessage(message, getRunTestsMethod(), true, "out");
		
		writeMessage = true;
	}
	
	public void addTestCalls()
	{
		ClassDeclaration clazz = (ClassDeclaration)parent;
		
		final NovaMethodDeclaration runMethod = getRunTestsMethod();
		
		callMethodsWithAnnotationOfType(InitTestClassAnnotation.class);
		
		getMethodsWithTypeAnnotation(TestAnnotation.class).forEach(method -> {
			getFileDeclaration().addImport("novex/nest/NestException");
			
			TestAnnotation test = (TestAnnotation)method.getAnnotationOfType(TestAnnotation.class);
			
			if (method.getParameterList().any(x -> x.isUserMade()))
			{
				SyntaxMessage.error("Test method '" + method.getName() + "' cannot contain parameters", method);
			}
			
			callMethodsWithAnnotationOfType(InitTestAnnotation.class);
			
			Variable timer = generateTimer(runMethod, method.getName());
			
			Try tryBlock = Try.decodeStatement(runMethod, "try", Location.INVALID, true);
			
			MethodCall call = MethodCall.decodeStatement(tryBlock, method.getName() + "(out)", Location.INVALID, true, false, method);
			call.parseModifier("await");
			
			tryBlock.addChild(call);
			
			runMethod.addChild(tryBlock);
			stopTimer(tryBlock, timer);
			
			Catch catchBlock = Catch.decodeStatement(runMethod, "catch (NestException e)", Location.INVALID, true);
			
			runMethod.addChild(catchBlock);
			stopTimer(catchBlock, timer);
			
			if (test.writeMessage)
			{
				Variable success = (Variable)SyntaxTree.decodeIdentifierAccess(tryBlock, "out.write(\"- Success\\n\")", Location.INVALID, true);
				tryBlock.addChild(success);
				
				Variable failure = (Variable)SyntaxTree.decodeIdentifierAccess(catchBlock, "out.write(\"- Failure: #e.message\\n\")", Location.INVALID, true);
				catchBlock.addChild(failure);
			}
			
			callMethodsWithAnnotationOfType(CleanTestAnnotation.class);
			
			addResultCall(tryBlock, true, timer, method);
			addResultCall(catchBlock, false, timer, method);
		});
		
		callMethodsWithAnnotationOfType(CleanTestClassAnnotation.class);
		
		Variable write = (Variable)SyntaxTree.decodeIdentifierAccess(runMethod, "out.write(\"\\n\")", Location.INVALID, true);
		
		runMethod.addChild(write);
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
	public TestableAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		TestableAnnotation node = new TestableAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public TestableAnnotation cloneTo(TestableAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public TestableAnnotation cloneTo(TestableAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "testable" };
	}
}