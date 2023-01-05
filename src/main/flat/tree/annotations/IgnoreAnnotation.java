package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.ClassDeclaration;
import flat.tree.FlatMethodDeclaration;
import flat.tree.Node;
import flat.util.Location;

public class IgnoreAnnotation extends Annotation implements ModifierAnnotation, RunnableTests, NestAnnotation
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

	public IgnoreAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
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

		return result;
	}

	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (!checkDuplicate(next, throwError))
		{
			if (next instanceof FlatMethodDeclaration || next instanceof ClassDeclaration)
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
	public IgnoreAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		IgnoreAnnotation node = new IgnoreAnnotation(temporaryParent, locationIn);

		return cloneTo(node, cloneChildren, cloneAnnotations);
	}

	public IgnoreAnnotation cloneTo(IgnoreAnnotation node)
	{
		return cloneTo(node, true, true);
	}

	public IgnoreAnnotation cloneTo(IgnoreAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);

		node.aliasUsed = aliasUsed;

		return node;
	}

	@Override
	public String[] getAliases()
	{
		return new String[] { "ignore" };
	}
}