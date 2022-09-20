package flat.validator.annotations;

import flat.ValidationResult;
import flat.util.Location;

public class SyncAnnotationValidator extends AnnotationValidator
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
	
	public SyncAnnotationValidator(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static SyncAnnotationValidator decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Sync"))
		{
			SyncAnnotationValidator n = new SyncAnnotationValidator(parent, location);
			
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
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			FlatMethodDeclaration method = getParentMethod();
			
			getFileDeclaration().addImport("flat/thread/Thread");
			
			Scope scope = method.getScope();
			
			StaticClassReference lock = (StaticClassReference)SyntaxTree.decodeIdentifierAccess(method, "Thread.lock()", Location.INVALID, true, false, true);
			StaticClassReference unlock = (StaticClassReference)SyntaxTree.decodeIdentifierAccess(method, "Thread.unlock()", Location.INVALID, true, false, true);
			
			scope.addChildBefore(scope.getFirstStatement(), lock);
			scope.addChildAfter(scope.getLastChild(), unlock);
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
	public SyncAnnotationValidator clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		SyncAnnotationValidator node = new SyncAnnotationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public SyncAnnotationValidator cloneTo(SyncAnnotationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	public SyncAnnotationValidator cloneTo(SyncAnnotationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "sync" };
	}
}