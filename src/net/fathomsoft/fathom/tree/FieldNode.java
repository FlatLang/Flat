package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxError;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:12:04 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:12:04 PM
 * @version	v
 */
public class FieldNode extends DeclarationNode
{
	public static final int	VISIBLE	= 4;
	
	public FieldNode()
	{
		
	}
	
	public boolean isVisibilityValid()
	{
		int visibility = getVisibility();
		
		return super.isVisibilityValid() || visibility == VISIBLE;
	}
	
	public String getVisibilityText()
	{
		int visibility = getVisibility();
		
		if (visibility == VISIBLE)
		{
			return "visible";
		}
		
		return super.getVisibilityText();
	}
	
	public void setAttribute(String attribute, int argNum)
	{
		super.setAttribute(attribute, argNum);
		
		if (argNum == 0)
		{
			if (attribute.equals("visibility"))
			{
				setVisibility(VISIBLE);
			}
		}
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
		return super.generateCHeaderOutput();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isStatic())
		{
			builder.append(getStaticText()).append(' ');
		}
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
		
		builder.append(' ').append(getName());
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	public static FieldNode decodeStatement(TreeNode parentNode, final String statement, Location location)
	{
		FieldNode n = new FieldNode()
		{
			private boolean	methods = false;
			
			/* Keep track of the type. The type is always specified before the identifier,
			   therefore proving that the type is the second to last word before the end
			   of the declaration. */
			private String	oldWord = null;
			
			public void interactWord(String word, int argNum, Bounds bounds, int numWords)
			{
				if (word.equals("{"))
				{
					methods = true;
					
					return;
				}
				else if (methods && word.equals("}"))
				{
					return;
				}
				
				setAttribute(word, argNum);
				
				setName(word);
				setType(oldWord);
				
				oldWord = word;
				
				if (argNum == numWords - 1)
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
			SyntaxError.outputNewError("A type for the field must be declared", n);
		}
		
		return n;
	}
}