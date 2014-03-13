package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxError;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:52:01 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:52:01 PM
 * @version	v
 */
public class ParameterNode extends LocalVariableNode
{
	private String	type;
	private String	defaultValue;
	
	public ParameterNode()
	{
		
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getDefaultValue()
	{
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return type + " " + getName();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return type + " " + getName();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		return type + " " + getName();
	}
	
	public static ParameterNode decodeStatement(TreeNode parentNode, String statement, final Location location)
	{
		ParameterNode n = new ParameterNode()
		{
			public void interactWord(String word, int argNum, Bounds bounds, int numWords)
			{
				if (argNum == 0)
				{
					setType(word);
				}
				else if (argNum == 1)
				{
					setName(word);
				}
				else
				{
					SyntaxError.outputNewError("Incorrect parameter definition", location);
				}
			}
		};
		
		n.iterateWords(statement);
		
		return n;
	}
}