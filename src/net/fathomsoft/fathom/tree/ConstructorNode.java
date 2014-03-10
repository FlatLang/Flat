package net.fathomsoft.fathom.tree;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fathomsoft.fathom.error.SyntaxError;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:50:47 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:50:47 PM
 * @version	v
 */
public class ConstructorNode extends MethodNode
{
	public ConstructorNode()
	{
		
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
			SyntaxError.outputNewError("Constructor cannot be static", getLocationIn());
			
			return null;
		}
		if (isConst())
		{
			SyntaxError.outputNewError("Constructor cannot be const", getLocationIn());
			
			return null;
		}
		
		if (isReference())
		{
			SyntaxError.outputNewError("Constructor cannot return a reference", getLocationIn());
			
			return null;
		}
		else if (isPointer())
		{
			SyntaxError.outputNewError("Constructor cannot return a pointer", getLocationIn());
			
			return null;
		}
		
		builder.append(getName()).append('(');
		
		builder.append(getParameterListNode().generateJavaSourceOutput());
		
		builder.append(')').append('\n').append('{').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof ParameterListNode == false)
			{
				builder.append(child.generateJavaSourceOutput());
			}
		}
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append('\n');
		
		if (isVisibilityValid())
		{
			if (getVisibility() == DeclarationNode.PRIVATE)
			{
				return "";
			}
		}
		if (isStatic())
		{
			SyntaxError.outputNewError("Constructor cannot be static", getLocationIn());
			
			return null;
		}
		if (isConst())
		{
			SyntaxError.outputNewError("Constructor cannot be const", getLocationIn());
			
			return null;
		}
		
		if (isReference())
		{
			SyntaxError.outputNewError("Constructor cannot return a reference", getLocationIn());
			
			return null;
		}
		else if (isPointer())
		{
			SyntaxError.outputNewError("Constructor cannot return a pointer", getLocationIn());
			
			return null;
		}
		
		ClassNode classNode = (ClassNode)getAncestorOfType(ClassNode.class, true);
		
		builder.append("FUNC(");
		
		builder.append(classNode.getName()).append("*").append(", ");
		
		builder.append(getName()).append(", ");
		
		builder.append(getParameterListNode().generateCHeaderOutput());
		
		builder.append(");").append('\n');
		
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
	
	public static ConstructorNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		int firstParenthIndex = statement.indexOf('(');
		int lastParenthIndex  = statement.lastIndexOf(')');
		
		if (firstParenthIndex >= 0)
		{
			// TODO: make better check for last parenth. Take a count of each of the starting parenthesis and
			// subtract the ending ones from the number.
			if (lastParenthIndex < 0)
			{
				SyntaxError.outputNewError("Expected a ')' ending parenthesis", location);
				
				return null;
			}
			
			String parameterList = statement.substring(firstParenthIndex + 1, lastParenthIndex);
			
			String parameters[]  = Regex.splitCommas(parameterList);
			
			statement = statement.substring(0, firstParenthIndex);
			
			ConstructorNode n = new ConstructorNode()
			{
				public void interactWord(String word, int argNum)
				{
					setAttribute(word, argNum);
					
					setName(word);
				}
			};
			
			for (int i = 0; i < parameters.length; i++)
			{
				if (parameters[i].length() > 0)
				{
					ParameterNode param = ParameterNode.decodeStatement(parentNode, parameters[i], location);
					
					if (param == null)
					{
						SyntaxError.outputNewError("Incorrect parameter definition", location);
						
						return null;
					}
					
					n.getParameterListNode().addChild(param);
				}
			}
			
			n.iterateWords(statement);
			
			ClassNode classNode = (ClassNode)parentNode.getAncestorOfType(ClassNode.class, true);
			
			if (classNode.getName().equals(n.getName()))
			{
				return n;
			}
		}
		
		return null;
	}
}
