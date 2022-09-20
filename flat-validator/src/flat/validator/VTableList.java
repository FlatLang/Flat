package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;

public class VTableList extends TypeList<VTable>
{
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public VTableList(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public ExtensionVTable getExtensionVTable()
	{
		return (ExtensionVTable)getChild(super.getNumDefaultChildren() + 0);
	}
	
	public TraitVTable getInterfaceVTable()
	{
		return (TraitVTable)getChild(super.getNumDefaultChildren() + 1);
	}
	
	/**
	 * @see NodeValidator#validate(int)
	 */
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (getNumVisibleChildren() <= 0)
		{
			ExtensionVTable extension = new ExtensionVTable(this, Location.INVALID);
			TraitVTable inter     = new TraitVTable(this, Location.INVALID);
			
			addChild(extension);
			addChild(inter);
		}
		
		return result;
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public VTableList clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		VTableList node = new VTableList(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public VTableList cloneTo(VTableList node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link VTableList} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VTableList cloneTo(VTableList node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link VTableList} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}