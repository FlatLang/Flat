package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.*;
import flat.tree.variables.FieldDeclaration;
import flat.util.Location;

public class TestAnnotation extends Annotation implements ModifierAnnotation, RunnableTests, NestAnnotation
{
	public String aliasUsed;
	public boolean writeMessage;
	public FieldDeclaration testCase;

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

		FlatMethodDeclaration method = (FlatMethodDeclaration)parent;

		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			addOutputStreamParameter(method);
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
				String name = method.getLiteralNameData() != null ? method.getLiteralNameData().literalName : method.getName();

				writeMessage((Literal)Literal.decodeStatement(parent, "\"Testing " + name + " \"", Location.INVALID, true, true));
			}
		}

		return result;
	}

	public FieldDeclaration generateTestCase()
	{
		if (testCase == null)
		{
			FlatMethodDeclaration method = (FlatMethodDeclaration)parent;

			getFileDeclaration().addImport("flat/test/TestCase");

			String className = method.getParentClass().getName();
			String testName = method.getLiteralNameData() != null ? method.getLiteralNameData().literalName : method.getName();

			String name = method.getScope().getUniqueName("_" + method.getName() + "TestCase");
			String description = parameters.containsKey("message") && ((Literal)parameters.get("message")).isStringInstantiation() ? ", " + ((Literal)parameters.get("message")).generateFlatInput() : "";

			testCase = addFieldInitialization("TestCase", name, "TestCase(\"" + className + "\", \"" + testName + "\"" + description + ")");
			testCase.validate(getProgram().getPhase());
		}

		return testCase;
	}

	public void writeMessage(Literal message)
	{
		RunnableTests.super.writeMessage(message, (FlatMethodDeclaration)parent, false, "out");

		writeMessage = true;
	}

	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (!checkDuplicate(next, throwError))
		{
			if (next instanceof FlatMethodDeclaration)
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

		node.aliasUsed = aliasUsed;

		return node;
	}

	@Override
	public String[] getAliases()
	{
		return new String[] { "test" };
	}
}