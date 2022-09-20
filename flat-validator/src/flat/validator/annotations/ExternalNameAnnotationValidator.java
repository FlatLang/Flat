package flat.validator.annotations;

import flat.ValidationResult;
import flat.tree.Identifier;
import flat.tree.Node;
import flat.util.Location;

public class ExternalNameAnnotationValidator extends AnnotationValidator
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
	
	public ExternalNameAnnotationValidator(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static ExternalNameAnnotationValidator decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("ExternalName"))
		{
			ExternalNameAnnotationValidator n = new ExternalNameAnnotationValidator(parent, location);
			
			n.parseParameters(parameters);
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public String[] defaultParameterNames()
	{
		return new String[] { "name" };
	}
	
	@Override
	public String[][] defaultParameterTypes()
	{
		return new String[][] { { "Identifier" } };
	}
	
	@Override
	public void onAdded(Node parent)
	{
		ModifierAnnotation.super.onAdded(parent);
		super.onAdded(parent);
		
		
	}
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		String name = ((Identifier)parent).getName();
		
		if (parameters.get("name") != null)
		{
			name = (String)parameters.get("name");
		}
		
		parent.setProperty("externalName", name);
		
		return result;
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (!checkDuplicate(next, throwError))
		{
			if (next instanceof Identifier)
			{
				return true;
			}
			else
			{
				return invalidApplication(next, throwError);
			}
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public ExternalNameAnnotationValidator clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ExternalNameAnnotationValidator node = new ExternalNameAnnotationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public ExternalNameAnnotationValidator cloneTo(ExternalNameAnnotationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	public ExternalNameAnnotationValidator cloneTo(ExternalNameAnnotationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "external_name" };
	}
}