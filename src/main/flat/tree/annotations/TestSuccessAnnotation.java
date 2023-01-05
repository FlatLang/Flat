package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.Node;
import flat.tree.FlatMethodDeclaration;
import flat.tree.SyntaxTree;
import flat.util.Location;

public class TestSuccessAnnotation extends Annotation implements ModifierAnnotation, NestAnnotation
{
	public String aliasUsed;

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

	public TestSuccessAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}

	public static TestSuccessAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("TestSuccess"))
		{
			TestSuccessAnnotation n = new TestSuccessAnnotation(parent, location);

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

		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			addOutputStreamParameter((FlatMethodDeclaration)parent);
		}

		return result;
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
	public TestSuccessAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		TestSuccessAnnotation node = new TestSuccessAnnotation(temporaryParent, locationIn);

		return cloneTo(node, cloneChildren, cloneAnnotations);
	}

	public TestSuccessAnnotation cloneTo(TestSuccessAnnotation node)
	{
		return cloneTo(node, true, true);
	}

	public TestSuccessAnnotation cloneTo(TestSuccessAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);

		node.aliasUsed = aliasUsed;

		return node;
	}

	@Override
	public String[] getAliases()
	{
		return new String[] { "test_success" };
	}
}