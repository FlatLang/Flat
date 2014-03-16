package net.fathomsoft.fathom.tree;

import java.util.regex.Matcher;

import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.StringUtils;
import net.fathomsoft.fathom.util.SyntaxUtils;

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
		return super.generateJavaSourceOutput();
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
		return super.generateCSourceOutput();
	}
	
	public static LocalVariableNode decodeStatement(TreeNode parentNode, final String statement, Location location)
	{
		if (SyntaxUtils.isLiteral(statement))
		{
			return null;
		}
		
		LocalVariableNode n = new LocalVariableNode()
		{
			private boolean	done;
			
			private String	oldWord;
			
			@Override
			public void interactWord(String word, int wordNumber, Bounds bounds, int numWords)
			{
				if (!done)
				{
					setAttribute(word, wordNumber);
					
					setName(word);
					setType(oldWord);
					
					oldWord = word;
				}
				else
				{
					setArrayDimensions(1);
				}
				
				int firstBracketIndex = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getEnd());
				
				if (firstBracketIndex >= 0)
				{
					char c = statement.charAt(firstBracketIndex);
					
					if (c == '[' && wordNumber == numWords - 2)
					{
						done = true;
					}
				}
				
				if (wordNumber == numWords - 1)
				{
					// If it is an array declaration.
					if (Regex.matches(statement, bounds.getEnd(), Patterns.ARRAY_BRACKETS))
					{
						int dimensions = SyntaxUtils.getArrayDimensions(statement);
						
						setArrayDimensions(dimensions);
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
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public LocalVariableNode clone()
	{
		LocalVariableNode clone = new LocalVariableNode();
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
