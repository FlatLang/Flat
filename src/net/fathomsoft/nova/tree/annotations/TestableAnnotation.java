package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.util.Location;

import java.util.ArrayList;

public class TestableAnnotation extends Annotation implements ModifierAnnotation
{
	public String aliasUsed;
	public boolean generatedRunTestsMethod, writeMessage;
	
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
	
	public NovaMethodDeclaration getRunTestsMethod()
	{
		ClassDeclaration clazz = (ClassDeclaration)parent;
		
		MethodList.SearchFilter filter = new MethodList.SearchFilter();
		filter.checkAncestor = false;
		
		return (NovaMethodDeclaration)clazz.getMethod(clazz, "runTests", filter, new Value[0]);
	}
	
	public ArrayList<NovaMethodDeclaration> getMethodsWithTypeAnnotation(Class type)
	{
		final ArrayList<NovaMethodDeclaration> methods = new ArrayList<>();
		
		ClassDeclaration clazz = (ClassDeclaration)parent;
		
		clazz.getMethodList().forEachNovaMethod(method -> {
			if (method.containsAnnotationOfType(type))
			{
				methods.add(method);
			}
		});
		
		return methods;
	}
	
	public void callMethodsWithAnnotationOfType(Class type)
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
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			NovaMethodDeclaration method = getRunTestsMethod();
			
			if (method == null)
			{
				method = BodyMethodDeclaration.decodeStatement(parent, "public runTests()", Location.INVALID, true);
				
				parent.addChild(method);
				
				generatedRunTestsMethod = true;
			}
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
	
	public void insertMessage()
	{
		if (parameters.containsKey("message"))
		{
			Literal message = (Literal)parameters.get("message");
			
			if (!message.isNullLiteral() && !message.value.equals("false") && message.value.length() > 2)
			{
				message.value = "\"================== " + message.value.substring(1, message.value.length() - 1) + " ==================\"";
				
				writeMessage(message);
			}
		}
		else
		{
			ClassDeclaration clazz = (ClassDeclaration)parent;
			
			writeMessage((Literal)Literal.decodeStatement(parent, "\"================== Testing " + clazz.getName() + " ==================\"", Location.INVALID, true, true));
		}
	}
	
	public void writeMessage(Literal message)
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
		
		writeMessage = true;
	}
	
	public void addTestCalls()
	{
		ClassDeclaration clazz = (ClassDeclaration)parent;
		
		final NovaMethodDeclaration runMethod = getRunTestsMethod();
		
		callMethodsWithAnnotationOfType(InitTestClassAnnotation.class);
		
		clazz.getMethodList().forEachNovaMethod(method -> {
			if (method.containsAnnotationOfType(TestAnnotation.class))
			{
				TestAnnotation test = (TestAnnotation)method.getAnnotationOfType(TestAnnotation.class);
				
				if (method.getParameterList().getNumParameters() > 0)
				{
					SyntaxMessage.error("Test method '" + method.getName() + "' cannot contain parameters", method);
				}
				
				callMethodsWithAnnotationOfType(InitTestAnnotation.class);
				
				MethodCall call = MethodCall.decodeStatement(runMethod, method.getName() + "()", Location.INVALID, true, false, method);
				
				runMethod.addChild(call);
				
				if (test.writeMessage)
				{
					StaticClassReference write = (StaticClassReference)SyntaxTree.decodeIdentifierAccess(method, "Console.writeLine(\"- Success\")", Location.INVALID, true);
					
					runMethod.addChild(write);
				}
				
				callMethodsWithAnnotationOfType(CleanTestAnnotation.class);
			}
		});
		
		callMethodsWithAnnotationOfType(CleanTestClassAnnotation.class);
		
		StaticClassReference write = (StaticClassReference)SyntaxTree.decodeIdentifierAccess(runMethod, "Console.writeLine()", Location.INVALID, true);
		
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
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "testable" };
	}
}