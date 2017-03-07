package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.util.Location;

public class TestAnnotation extends Annotation implements ModifierAnnotation, RunnableTests
{
	public String aliasUsed;
	public boolean writeMessage;
	
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
	
	public TestAnnotation(Node temporaryParent, Location locationIn)
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
	
	public static TestAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Test"))
		{
			TestAnnotation n = new TestAnnotation(parent, location);
			
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
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (parameters.containsKey("message"))
			{
				Literal message = (Literal)parameters.get("message");
				
				if (!message.isNullLiteral() && !message.value.equals("false") && message.value.length() > 2)
				{
					char c = message.value.charAt(message.value.length() - 2);
					
					if (c != ' ' && c != '\t' && c != '\n' && c != '\r')
					{
						message.value = message.value.substring(0, message.value.length() - 1) + " \"";
					}
					
					writeMessage(message);
				}
			}
			else
			{
				NovaMethodDeclaration method = (NovaMethodDeclaration)parent;
				
				writeMessage((Literal)Literal.decodeStatement(parent, "\"Testing " + method.getName() + " \"", Location.INVALID, true, true));
			}
		}
		
		return result;
	}
	
	public void writeMessage(Literal message)
	{
		RunnableTests.super.writeMessage(message);
		
		writeMessage = true;
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (!checkDuplicate(next, throwError))
		{
			if (next instanceof NovaMethodDeclaration)
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
	public TestAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		TestAnnotation node = new TestAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public TestAnnotation cloneTo(TestAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public TestAnnotation cloneTo(TestAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "test" };
	}
}