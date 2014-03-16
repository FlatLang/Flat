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
	
	private int		arrayDimensions;
	
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
		return arrayDimensions > 0;
	}
	
	public int getArrayDimensions()
	{
		return arrayDimensions;
	}
	
	public String getArrayText()
	{
		return "*";
	}
	
	public void setArrayDimensions(int arrayDimensions)
	{
		this.arrayDimensions = arrayDimensions;
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
		
		builder.append(getName()).append(';');
		
		return builder.toString();
	}
	
	public String generateVariableUseOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		String visibility = "";
		
		if (this instanceof FieldNode)
		{
			FieldNode field = (FieldNode)this;
			
			visibility = MethodNode.getObjectReferenceIdentifier() + "->";
			
			if (field.getVisibility() == FieldNode.PRIVATE)
			{
				visibility += "prv->";
			}
		}
		
		builder.append(visibility).append(getName());
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return generateCSourceOutput();
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
		
		builder.append(getType());
		
		if (isReference())
		{
			builder.append(getReferenceText());
		}
		else if (isPointer())
		{
			builder.append(getPointerText());
		}
		else if (isArray())
		{
			builder.append(getArrayText());
		}
		
		builder.append(' ').append(getName()).append(';');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public VariableNode clone()
	{
		VariableNode clone = new VariableNode();
		clone.setName(getName());
		clone.setConst(isConst());
		clone.setArrayDimensions(getArrayDimensions());
		clone.setType(getType());
		clone.setReference(isReference());
		clone.setPointer(isPointer());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}