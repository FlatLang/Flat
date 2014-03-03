package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxError;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Regex;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:10:53 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:10:53 PM
 * @version	v
 */
public class MethodNode extends DeclarationNode
{
	public MethodNode()
	{
		ParameterListNode parameterList     = new ParameterListNode();
		LocalVariableListNode variablesNode = new LocalVariableListNode();
		
		super.addChild(parameterList);
		super.addChild(variablesNode);
	}
	
	public ParameterListNode getParameterListNode()
	{
		return (ParameterListNode)getChild(0);
	}
	
	public LocalVariableListNode getLocalVariableListNode()
	{
		return (LocalVariableListNode)getChild(1);
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof LocalVariableNode)
		{
			getLocalVariableListNode().addChild(child);
		}
		else
		{
			super.addChild(child);
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
		
		if (isReference())
		{
			builder.append(getReferenceText()).append(' ');
		}
		else if (isPointer())
		{
			builder.append(getPointerText()).append(' ');
		}
		
		builder.append(getType()).append(' ');
		
		builder.append(getName()).append('(');

		ParameterListNode parameterList = getParameterListNode();
		
		builder.append(parameterList.generateJavaSourceOutput());
		
		builder.append(')').append('\n').append('{').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != parameterList)
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
		
		if (isVisibilityValid())
		{
			if (getVisibility() == DeclarationNode.PRIVATE)
			{
				return "";
			}
		}
		if (isStatic())
		{
			SyntaxError.outputNewError("Static methods are not supported in the C implementation yet", getLocationIn());
			
			return null;
		}
		if (isConst())
		{
			SyntaxError.outputNewError("Const methods are not supported in the C implementation yet", getLocationIn());
			
			return null;
		}
		
		builder.append("FUNC(");
		
		builder.append(getType());
		
		if (isReference())
		{
			builder.append(getReferenceText()).append(' ');
		}
		else if (isPointer())
		{
			builder.append(getPointerText()).append(' ');
		}
		
		builder.append(", ");
		
		builder.append(getName()).append(", ");

		ParameterListNode parameterList = getParameterListNode();
		
		builder.append(parameterList.generateCHeaderOutput());
		
		builder.append(");").append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCSourceSignature()).append('\n').append('{').append('\n');
		
		ParameterListNode parameterList = getParameterListNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != parameterList)
			{
				builder.append(child.generateCSourceOutput());
			}
		}
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	public String generateCSourcePrototype()
	{
		return generateCSourceSignature().concat(";");
	}
	
	public String generateCSourceSignature()
	{
		StringBuilder builder = new StringBuilder();
		
		if (!isStatic())
		{
			builder.append("static ");
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
		
		builder.append(getName()).append('(');
		
		builder.append(getParameterListNode().generateCSourceOutput());
		
		builder.append(')');
		
		return builder.toString();
	}
	
	public static MethodNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		int firstParenthIndex = Regex.indexOf(statement, '(', new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
		int lastParenthIndex  = Regex.lastIndexOf(statement, ')', new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
		
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
			
			MethodNode n = new MethodNode()
			{
				private String returnType = null;
				
				public void interactWord(String word, int argNum)
				{
					setAttribute(word, argNum);
					
					setType(returnType);
					setName(word);
					
					returnType = word;
				}
			};
			
			n.setLocationIn(location);
			
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
			
			return n;
		}
		
		return null;
	}
}