/**
 * The Fathom Programming Language. Write Unbelievable Code.
 *  Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fathomsoft.fathom.tree;

import java.util.ArrayList;
import java.util.regex.Pattern;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.variables.FieldListNode;
import net.fathomsoft.fathom.tree.variables.FieldNode;
import net.fathomsoft.fathom.tree.variables.LocalVariableNode;
import net.fathomsoft.fathom.tree.variables.PrivateFieldListNode;
import net.fathomsoft.fathom.tree.variables.PublicFieldListNode;
import net.fathomsoft.fathom.tree.variables.VariableNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * DeclarationNode extension that represents the declaration of a class
 * node type. See {@link net.fathomsoft.fathom.tree.ClassNode#decodeStatement(net.fathomsoft.fathom.tree.TreeNode, java.lang.String, net.fathomsoft.fathom.util.Location) decodeStatement}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:15:51 PM
 * @since	v0.1
 * @version	Apr 2, 2014 at 4:39:13 PM
 * @version	v0.2
 */
public class ClassNode extends DeclarationNode
{
	private String				extendedClass;
	
	private	ArrayList<String>	implementedClasses;
	
	/**
	 * Instantiate and initialize default values for a class node.
	 */
	public ClassNode()
	{
		implementedClasses = new ArrayList<String>();
		
		setType("class");
		
		FieldListNode  fields       = new FieldListNode();
		MethodListNode constructors = new MethodListNode();
		MethodListNode destructors  = new MethodListNode();
		MethodListNode methods      = new MethodListNode();
		
		super.addChild(fields);
		super.addChild(constructors);
		super.addChild(destructors);
		super.addChild(methods);
	}
	
	/**
	 * Get the FieldListNode instance that contains the list of fields
	 * that this class node contains.
	 * 
	 * @return The FieldListNode for this class node.
	 */
	public FieldListNode getFieldListNode()
	{
		return (FieldListNode)getChild(0);
	}
	
	/**
	 * Get the MethodListNode instance that contains the list of
	 * constructors that this class node contains.
	 * 
	 * @return The MethodListNode for constructors of this class node.
	 */
	public MethodListNode getConstructorListNode()
	{
		return (MethodListNode)getChild(1);
	}
	
	/**
	 * Get the MethodListNode instance that contains the list of
	 * destructors that this class node contains.
	 * 
	 * @return The MethodListNode for destructors of this class node.
	 */
	public MethodListNode getDestructorListNode()
	{
		return (MethodListNode)getChild(2);
	}
	
	/**
	 * Get the MethodListNode instance that contains the list of methods
	 * that this class node contains.
	 * 
	 * @return The MethodListNode for this class node.
	 */
	public MethodListNode getMethodListNode()
	{
		return (MethodListNode)getChild(3);
	}
	
	/**
	 * Get the name of the class that this node extends.
	 * 
	 * @return The name of the class that this node extends.
	 */
	public String getExtendedClass()
	{
		return extendedClass;
	}
	
	/**
	 * Set the class that this class node will extend.<br>
	 * <br>
	 * For instance: "public class ClassName extends SuperClass"
	 * The "SuperClass" is a class that is extended by the "ClassName"
	 * class.<br>
	 * <br>
	 * A class node can only extend one class.
	 * 
	 * @param extendedClass The name of the class that is to be
	 * 		extended.
	 */
	public void setExtendedClass(String extendedClass)
	{
		this.extendedClass = extendedClass;
	}
	
	/**
	 * Get an ArrayList instance that contains all of the interfaces
	 * that are implemented by this class node.
	 * 
	 * @return An ArrayList instance that contains the interface names.
	 */
	public ArrayList<String> getImplementedClasses()
	{
		return implementedClasses;
	}
	
	/**
	 * Add a class to the list of implemented classes of this class
	 * instance.<br>
	 * <br>
	 * For instance: "public class ClassName implements InterfaceName"
	 * The "InterfaceName" is an interface that is implemented by
	 * the "ClassName" class.<br>
	 * <br>
	 * A class node can implement as many interfaces as it needs to.
	 * 
	 * @param implementedClass The name of the class that is to be
	 * 		implemented.
	 */
	public void addImplementedClass(String implementedClass)
	{
		this.implementedClasses.add(implementedClass);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.DeclarationNode#setAttribute(java.lang.String)
	 */
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.DeclarationNode#setAttribute(java.lang.String, int)
	 */
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
			else if (child instanceof DestructorNode)
			{
				getDestructorListNode().addChild(child);
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
		
		builder.append('\n').append('\n');

		FieldListNode fields = getFieldListNode();
		
		PublicFieldListNode publicFields = fields.getPublicFieldListNode();
		
		if (publicFields.getChildren().size() > 0)
		{
			builder.append(publicFields.generateCHeaderOutput()).append('\n');
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getConstructorListNode() && child != getDestructorListNode())
			{
				if (child != fields)
				{
					builder.append(child.generateCHeaderOutput());
				}
			}
		}
		
		builder.append(')').append('\n').append('\n');
		
		MethodListNode constructors = getConstructorListNode();
		builder.append(constructors.generateCHeaderOutput());
		
		MethodListNode destructors = getDestructorListNode();
		builder.append(destructors.generateCHeaderOutput());
		
		if (containsStaticData())
		{
			builder.append("extern ").append(getName()).append("* ").append("__static__").append(getName()).append(';').append('\n').append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderFragment()
	 */
	@Override
	public String generateCHeaderFragment()
	{
		return null;
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
		
		MethodListNode constructors = getConstructorListNode();
		builder.append(constructors.generateCSourcePrototypes());

		MethodListNode destructors = getDestructorListNode();
		builder.append(destructors.generateCSourcePrototypes());
		
		MethodListNode methods = getMethodListNode();
		
		if (methods.getChildren().size() > 0)
		{
			builder.append(methods.generateCSourcePrototypes());
		}
		
		builder.append('\n');
		
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
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
	}
	
	/**
	 * Decode the given statement into a ClassNode, if possible. If it is
	 * not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public class ClassName</li>
	 * 	<li>private static class ClassName</li>
	 * 	<li>public abstract class ClassName</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to translate into a ClassNode
	 * 		if possible.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ClassNode.
	 */
	public static ClassNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		// If contains 'class' in the statement.
		if (Regex.indexOf(statement, Patterns.PRE_CLASS) >= 0)
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
	
	/**
	 * Get whether or not the class contains a constructor implementation
	 * or not.
	 * 
	 * @return Whether or not the class contains a constructor
	 * 		implementation or not.
	 */
	public boolean containsConstructor()
	{
		return containsMethod(getName(), true, getName());
	}
	
	/**
	 * Get whether or not the class contains a destructor implementation
	 * or not.
	 * 
	 * @return Whether or not the class contains a destructor
	 * 		implementation or not.
	 */
	public boolean containsDestructor()
	{
		return containsMethod("~" + getName(), true, null);
	}
	
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
		ClassNode node = new ClassNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ClassNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClassNode clone(ClassNode node)
	{
		super.clone(node);
		
		node.extendedClass = extendedClass;
		
		for (int i = 0; i < implementedClasses.size(); i++)
		{
			String implementedClass = implementedClasses.get(i);
			
			node.addImplementedClass(implementedClass);
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}