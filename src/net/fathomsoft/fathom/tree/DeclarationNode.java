package net.fathomsoft.fathom.tree;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:10:49 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:10:49 PM
 * @version	v
 */
public class DeclarationNode extends VariableNode
{
	private boolean 		staticVal;
	
	private int				visibility;
	
	public static final int	PRIVATE		= 1;
	
	public static final int	PROTECTED	= 2;
	
	public static final int	PUBLIC		= 3;
	
	public DeclarationNode()
	{
		setVisibility(PRIVATE);
	}
	
	public boolean isStatic()
	{
		return staticVal;
	}
	
	public String getStaticText()
	{
		return "static";
	}
	
	public void setStatic(boolean staticVal)
	{
		this.staticVal = staticVal;
	}
	
	public int getVisibility()
	{
		return visibility;
	}
	
	public boolean isVisibilityValid()
	{
		return visibility >= PRIVATE && visibility <= PUBLIC;
	}
	
	public String getVisibilityText()
	{
		if (visibility == PRIVATE)
		{
			return "private";
		}
		else if (visibility == PROTECTED)
		{
			return "protected";
		}
		else if (visibility == PUBLIC)
		{
			return "public";
		}
		
		return null;
	}
	
	public void setVisibility(int visibility)
	{
		this.visibility = visibility;
	}
	
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	public void setAttribute(String attribute, int argNum)
	{
		super.setAttribute(attribute, argNum);
		
		if (attribute.equals("static"))
		{
			setStatic(true);
		}
		// Put new stuff between here...
		else if (argNum == 0)
		{
			if (attribute.equals("private"))
			{
				setVisibility(PRIVATE);
			}
			else if (attribute.equals("protected"))
			{
				setVisibility(PROTECTED);
			}
			else if (attribute.equals("public"))
			{
				setVisibility(PUBLIC);
			}
		}
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isVisibilityValid())
		{
			builder.append(getVisibilityText()).append(' ');
		}
		if (isStatic())
		{
			builder.append(getStaticText()).append(' ');
		}
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
		
		builder.append(getName()).append(';').append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isVisibilityValid())
		{
			if (getVisibility() == PRIVATE)
			{
				return "";
			}
			
			builder.append(getVisibilityText()).append(' ');
		}
		if (isStatic())
		{
			builder.append(getStaticText()).append(' ');
		}
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
		
		builder.append(getName()).append(';').append('\n');
		
		return builder.toString();
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