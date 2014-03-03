package net.fathomsoft.fathom.tree;

import java.util.regex.Matcher;

import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:12:00 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:12:00 PM
 * @version	v
 */
public class LocalVariableNode extends VariableNode
{
	public LocalVariableNode()
	{
		
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return super.generateJavaSourceOutput() + ";";
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
		return super.generateCSourceOutput() + ";";
	}
	
	public static LocalVariableNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		LocalVariableNode n = new LocalVariableNode();
		
		// Pattern used to find word boundaries. 
		Matcher matcher = Patterns.WORD_BOUNDARIES.matcher(statement);
		
		/* Keep track of the type. The type is always specified before the identifier,
		   therefore proving that the type is the second to last word before the end
		   of the declaration. */
		String oldWord  = null;
		
		int		argNum  = 0;
		int     index   = 0;
		
		boolean end     = false;
		
		while (matcher.find())
		{
			if (end)
			{
				String str = statement.substring(index, matcher.start());
				
				n.setAttribute(str, argNum);
				
				n.setName(str);
				n.setType(oldWord);
				
				oldWord = str;
				
				argNum++;
				end = false;
			}
			else
			{
				index = matcher.start();
				
				end = true;
			}
		}
		
		if (n.getType() == null)
		{
			return null;
		}
		
		return n;
	}
}