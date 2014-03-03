package net.fathomsoft.fathom.tree;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:00:23 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:00:23 PM
 * @version	v
 */
public class ModifierNode extends IdentifierNode
{
	private boolean	referenceVal;
	private boolean	pointerVal;
	
	public ModifierNode()
	{
		
	}
	
	public boolean isReference()
	{
		return referenceVal;
	}
	
	public String getReferenceText()
	{
		return "&";
	}
	
	public void setReference(boolean referenceVal)
	{
		this.referenceVal = referenceVal;
	}
	
	public boolean isPointer()
	{
		return pointerVal;
	}
	
	public String getPointerText()
	{
		return "*";
	}
	
	public void setPointer(boolean pointerVal)
	{
		this.pointerVal = pointerVal;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		return null;
	}
}