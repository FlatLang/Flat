package net.fathomsoft.fathom.tree;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fathomsoft.fathom.error.SyntaxError;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:15:51 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:15:51 PM
 * @version	v
 */
public class ClassNode extends DeclarationNode
{
	private String				extendedClass;
	
	private	ArrayList<String>	implementedClasses;
	
	public ClassNode()
	{
		implementedClasses = new ArrayList<String>();
		
		setType("class");
		
		FieldListNode  fields  = new FieldListNode();
		MethodListNode methods = new MethodListNode();
		
		super.addChild(fields);
		super.addChild(methods);
	}
	
	public FieldListNode getFieldListNode()
	{
		return (FieldListNode)getChild(0);
	}
	
	public MethodListNode getMethodListNode()
	{
		return (MethodListNode)getChild(1);
	}
	
	public String getExtendedClass()
	{
		return extendedClass;
	}
	
	public void setExtendedClass(String extendedClass)
	{
		this.extendedClass = extendedClass;
	}
	
	public ArrayList<String> getImplementedClasses()
	{
		return implementedClasses;
	}
	
	public void addImplementedClass(String implementedClass)
	{
		this.implementedClasses.add(implementedClass);
	}
	
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	public void setAttribute(String attribute, int argNum)
	{
		super.setAttribute(attribute, argNum);
		
		if (attribute.equals("class"))
		{
			
		}
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof MethodNode)
		{
			getMethodListNode().addChild(child);
		}
		else if (child instanceof FieldNode)
		{
			getFieldListNode().addChild(child);
		}
		else
		{
			SyntaxError.outputNewError("Unexpected statement", child.getLocationIn());
			
			//super.addChild(child);
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
			SyntaxError.outputNewError("A class cannot be of a reference type", getLocationIn());
			
			return null;
		}
		else if (isPointer())
		{
			SyntaxError.outputNewError("A class cannot be of a pointer type", getLocationIn());
			
			return null;
		}
		
		builder.append(getName());
		
		if (getExtendedClass() != null)
		{
			builder.append(" extends ").append(getExtendedClass());
		}
		
		if (getImplementedClasses().size() > 0)
		{
			builder.append(" implements ");
			
			for (int i = 0; i < getImplementedClasses().size(); i++)
			{
				builder.append(getImplementedClasses().get(i));
				
				if (i < getImplementedClasses().size() - 1)
				{
					builder.append(", ");
				}
			}
		}
		
		builder.append('\n').append('{').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSourceOutput());
		}
		
		builder.append('}').append('\n').append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		// TODO: make use of the modifiers for the c implementation.
		
		builder.append("CLASS");
		
		if (isStatic())
		{
			SyntaxError.outputNewError("Static classes are not implemented in C yet.", this);
		}
		if (isConst())
		{
			SyntaxError.outputNewError("Const classes are not implemented in C yet.", this);
		}
		
		if (isReference())
		{
			SyntaxError.outputNewError("A class cannot be of a reference type", getLocationIn());
			
			return null;
		}
		else if (isPointer())
		{
			SyntaxError.outputNewError("A class cannot be of a pointer type", getLocationIn());
			
			return null;
		}
		
		if (getExtendedClass() != null)
		{
			builder.append("_EXT");
		}
		
		builder.append('\n').append('(').append('\n');
		
		builder.append(getName()).append(", ");

		if (getExtendedClass() != null)
		{
			builder.append(getExtendedClass()).append(", ");
		}
		
//		if (getImplementedClasses().size() > 0)
//		{
//			builder.append(" implements ");
//			
//			for (int i = 0; i < getImplementedClasses().size(); i++)
//			{
//				builder.append(getImplementedClasses().get(i));
//				
//				if (i < getImplementedClasses().size() - 1)
//				{
//					builder.append(", ");
//				}
//			}
//		}
		
		builder.append('\n');

		FieldListNode fields = getFieldListNode();
		
		PublicFieldListNode publicFields = fields.getPublicFieldListNode();
		
		if (publicFields.getChildren().size() > 0)
		{
			builder.append('\n');
			
			builder.append(publicFields.generateCSourceOutput());
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != fields)
			{
				builder.append(child.generateCHeaderOutput());
			}
		}
		
		builder.append(')').append('\n').append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		MethodListNode methods = getMethodListNode();
		
		if (methods.getChildren().size() > 0)
		{
			builder.append(methods.generateCSourcePrototypes());
			
			builder.append('\n');
		}
		
		FieldListNode fields = getFieldListNode();
		
		PrivateFieldListNode privateFields = fields.getPrivateFieldListNode();
		
		if (privateFields.getChildren().size() > 0)
		{
			builder.append("PRIVATE").append('\n').append('(').append('\n');
			
			builder.append(privateFields.generateCSourceOutput());
			
			builder.append(')');
		}
		else
		{
			builder.append("NO_PRIVATE");
		}
		
		builder.append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != fields)
			{
				builder.append(child.generateCSourceOutput());
			}
		}
		
		return builder.toString();
	}
	
	public static ClassNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		// If contains 'class' in the statement.
		if (Pattern.compile("class\\s+").matcher(statement).find())
		{
			ClassNode n = new ClassNode()
			{
				private boolean	extending		= false;
				private boolean	implementing	= false;
				
				private String	prevWord		= "";
				
				public void interactWord(String word, int argNum)
				{
					if (extending)
					{
						setExtendedClass(word);
						
						extending = false;
					}
					else if (implementing)
					{
						if (word.startsWith(","))
						{
							word = word.substring(1);
						}
						else if (word.endsWith(","))
						{
							word = word.substring(0, word.length() - 1);
						}
						
						if (word.length() > 0)
						{
							addImplementedClass(word);
						}
					}
					else
					{
						if (word.equals("extends"))
						{
							extending = true;
						}
						else if (word.equals("implements"))
						{
							implementing = true;
						}
						else
						{
							setAttribute(word, argNum);
							
							if (prevWord.equals("class"))
							{
								setName(word);
							}
							
							prevWord = word;
						}
					}
				}
			};
			
			n.iterateWords(statement);
			
			return n;
		}
		
		return null;
	}
}