package net.fathomsoft.fathom.tree;

import java.util.ArrayList;
import java.util.regex.Pattern;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;

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
		
		FieldListNode  fields       = new FieldListNode();
		MethodListNode constructors = new MethodListNode();
		MethodListNode methods      = new MethodListNode();
		
		super.addChild(fields);
		super.addChild(constructors);
		super.addChild(methods);
	}
	
	public FieldListNode getFieldListNode()
	{
		return (FieldListNode)getChild(0);
	}
	
	public MethodListNode getConstructorListNode()
	{
		return (MethodListNode)getChild(1);
	}
	
	public MethodListNode getMethodListNode()
	{
		return (MethodListNode)getChild(2);
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
			if (child instanceof ConstructorNode)
			{
				getConstructorListNode().addChild(child);
			}
			else
			{
				getMethodListNode().addChild(child);
			}
		}
		else if (child instanceof FieldNode)
		{
			getFieldListNode().addChild(child);
		}
		else
		{
			SyntaxMessage.error("Unexpected statement", child.getLocationIn());
			
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
			SyntaxMessage.error("A class cannot be of a reference type", getLocationIn());
			
			return null;
		}
		else if (isPointer())
		{
			SyntaxMessage.error("A class cannot be of a pointer type", getLocationIn());
			
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
			SyntaxMessage.error("Static classes are not implemented in C yet.", this);
		}
		if (isConst())
		{
			SyntaxMessage.error("Const classes are not implemented in C yet.", this);
		}
		
		if (isReference())
		{
			SyntaxMessage.error("A class cannot be of a reference type", getLocationIn());
			
			return null;
		}
		else if (isPointer())
		{
			SyntaxMessage.error("A class cannot be of a pointer type", getLocationIn());
			
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
			
			if (child != getConstructorListNode())
			{
				if (child != fields)
				{
					builder.append(child.generateCHeaderOutput());
				}
			}
		}
		
		builder.append(')').append('\n').append('\n');
		
		MethodListNode constructors = getConstructorListNode();
		
		if (constructors.getChildren().size() > 0)
		{
			builder.append(constructors.generateCHeaderOutput()).append('\n');
		}
		
		if (containsStaticData())
		{
			builder.append("extern ").append(getName()).append("* ").append("__static__").append(getName()).append(';').append('\n').append('\n');
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder  builder = new StringBuilder();
		
		if (containsStaticData())
		{
			builder.append(getName()).append("* ").append("__static__").append(getName()).append(';').append('\n').append('\n');
		}
		
		MethodListNode methods = getMethodListNode();
		
		if (methods.getChildren().size() > 0)
		{
			builder.append(methods.generateCSourcePrototypes());
		}
		
		MethodListNode constructors = getConstructorListNode();
		
		builder.append(constructors.generateCSourcePrototypes()).append('\n');
		
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
				
				public void interactWord(String word, int wordNumber, Bounds bounds, int numWords)
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
							setAttribute(word, wordNumber);
							
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
	
	/**
	 * Get whether or not the Fathom class contains static data. i.e
	 * static variables, methods, etc.
	 * 
	 * @return Whether or not the class contains static data.
	 */
	public boolean containsStaticData()
	{
		return containsStaticData(this);
	}
	
	/**
	 * Get whether or not the Fathom class contains static data. i.e
	 * static variables, methods, etc.
	 * 
	 * @param root The root TreeNode to search for the static modifier
	 * 		from.
	 * @return Whether or not the class contains static data.
	 */
	public boolean containsStaticData(TreeNode root)
	{
		for (int i = 0; i < root.getChildren().size(); i++)
		{
			TreeNode child = root.getChild(i);
			
			if (child instanceof DeclarationNode && child instanceof ConstructorNode == false)
			{
				DeclarationNode declaration = (DeclarationNode)child;
				
				if (declaration.isStatic())
				{
					return true;
				}
			}
			else if (containsStaticData(child))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean containsConstructor()
	{
		return containsMethod(getName(), true, getName());
	}
	
//	public boolean containsDestructor()
//	{
//		return containsMethod(getName(), true, null);
//	}
	
	/**
	 * Get whether or not the Fathom class contains a method with the
	 * given specifications.
	 * 
	 * @param methodName The name of the method to search for.
	 * @param staticVal Whether or not the method is static.
	 * @param type The return type of the method.
	 * @return Whether or not the class contains the method.
	 */
	public boolean containsMethod(String methodName, boolean staticVal, String type)
	{
		return containsMethod(this, methodName, staticVal, type);
	}
	
	/**
	 * Get whether or not the Fathom class contains a method with the
	 * given specifications.
	 * 
	 * @param root The root TreeNode to search for the method from.
	 * @param methodName The name of the method to search for.
	 * @param staticVal Whether or not the method is static.
	 * @param type The return type of the method.
	 * @return Whether or not the class contains the method.
	 */
	public boolean containsMethod(TreeNode root, String methodName, boolean staticVal, String type)
	{
		for (int i = 0; i < root.getChildren().size(); i++)
		{
			TreeNode child = root.getChild(i);
			
			if (child instanceof MethodNode)
			{
				MethodNode method = (MethodNode)child;
				
				if (method.isStatic() == staticVal && method.getName().equals(methodName) && method.getType().equals(type))
				{
					return true;
				}
			}
			else if (containsMethod(child, methodName, staticVal, type))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ClassNode clone()
	{
		ClassNode clone = new ClassNode();
		clone.setArrayDimensions(getArrayDimensions());
		clone.setConst(isConst());
		clone.setVisibility(getVisibility());
		clone.setType(getType());
		clone.setReference(isReference());
		clone.setPointer(isPointer());
		clone.setName(getName());
		clone.extendedClass = extendedClass;
		
		for (int i = 0; i < implementedClasses.size(); i++)
		{
			String implementedClass = implementedClasses.get(i);
			
			clone.addImplementedClass(implementedClass);
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}