package net.fathomsoft.fathom.tree;

import java.util.regex.Matcher;

import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

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
	
	public static LocalVariableNode decodeStatement(TreeNode parentNode, final String statement, Location location)
	{
		LocalVariableNode n = new LocalVariableNode()
		{
			private String oldWord;
			
			@Override
			public void interactWord(String word, int wordNumber, Bounds bounds, int numWords)
			{
				setAttribute(word, wordNumber);
				
				setName(word);
				setType(oldWord);
				
				oldWord = word;
				
				if (wordNumber == numWords - 1)
				{
					// If it is an array declaration.
					if (Regex.matches(statement, bounds.getEnd(), Patterns.ARRAY_BRACKETS))
					{
						setArray(true);
					}
				}
			}
		};
		
		n.iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES);
		
		if (n.getType() == null)
		{
			return null;
		}
		
		return n;
	}
}
