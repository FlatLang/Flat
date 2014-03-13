package net.fathomsoft.fathom.tree;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 10:34:30 PM
 * @since	v
 * @version	Jan 5, 2014 at 10:34:30 PM
 * @version	v
 */
public class LiteralNode extends TreeNode
{
	private String	value;
	
	public LiteralNode()
	{
		
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return value;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return value;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		return value;
	}
	
	public static boolean isString(String value)
	{
		if (value.length() < 2)
		{
			return false;
		}
		
		return value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"';
	}
	
	public static boolean isNumber(String value)
	{        
	    boolean seenDot     = false;
	    boolean seenExp     = false;
	    boolean justSeenExp = false;
	    boolean seenDigit   = false;
	    
	    for (int i = 0; i < value.length(); i++)
	    {
	        char c = value.charAt(i);
	        
	        if (c >= '0' && c <= '9')
	        {
	            seenDigit = true;
	            continue;
	        }
	        
	        if ((c == '-' || c == '+') && (i == 0 || justSeenExp))
	        {
	            continue;
	        }
	        
	        if (c == '.' && !seenDot)
	        {
	            seenDot = true;
	            
	            continue;
	        }
	        
	        justSeenExp = false;
	        
	        if ((c == 'e' || c == 'E') && !seenExp)
	        {
	            seenExp     = true;
	            justSeenExp = true;
	            
	            continue;
	        }
	        
	        return false;
	    }
	    
	    if (!seenDigit)
	    {
	        return false;
	    }
	    
	    try
	    {
	        Double.parseDouble(value);
	        
	        return true;
	    }
	    catch (NumberFormatException e)
	    {
	        return false;
	    }
	}
}