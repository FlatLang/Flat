package net.fathomsoft.fathom.tree;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:02:42 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:02:42 PM
 * @version	v
 */
public class VariableNode extends ModifierNode
{
	private boolean	constVal;
	private boolean arrayVal;
	
	private String	type;
	
	public VariableNode()
	{
		
	}
	
	public boolean isConst()
	{
		return constVal;
	}
	
	public String getConstText()
	{
		return "const";
	}
	
	public void setConst(boolean constVal)
	{
		this.constVal = constVal;
	}
	
	public boolean isArray()
	{
		return arrayVal;
	}
	
	public String getArrayText()
	{
		return "*";
	}
	
	public void setArray(boolean arrayVal)
	{
		this.arrayVal = arrayVal;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	public void setAttribute(String attribute, int argNum)
	{
		if (attribute.equals("const"))
		{
			setConst(true);
		}
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isConst())
		{
			builder.append(getConstText()).append(' ');
		}
		
		builder.append(getType()).append(' ');
		
		if (isReference())
		{
			builder.append(getReferenceText()).append(' ');
		}
		else if (isPointer())
		{
			builder.append(getPointerText()).append(' ');
		}
		
		builder.append(getName());
		
		return builder.toString();
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
		StringBuilder builder = new StringBuilder();
		
		if (isConst())
		{
			builder.append(getConstText()).append(' ');
		}
		
		builder.append(getType()).append(' ');
		
		if (isReference())
		{
			builder.append(getReferenceText()).append(' ');
		}
		else if (isPointer())
		{
			builder.append(getPointerText()).append(' ');
		}
		else if (isArray())
		{
			builder.append(getArrayText());
		}
		
		builder.append(getName());
		
		return builder.toString();
	}
}