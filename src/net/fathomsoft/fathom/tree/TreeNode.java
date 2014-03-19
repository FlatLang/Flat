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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * Class that is the parent of all Nodes on the Tree. Keeps the basic
 * information of where the statement was in the source, and where it was
 * output in the destination file. A TreeNode can have any number of
 * children, however some of the extensions of TreeNode have default
 * children at the start.
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:00:11 PM
 * @since	v0.1
 * @version	Feb 24, 2014 at 4:25:30 PM
 * @version	v0.1
 */
public abstract class TreeNode
{
	private Location			locationIn, locationOut;
	
	private TreeNode			parent;
	
	private ArrayList<TreeNode>	children;
	
	/**
	 * Create a new TreeNode. Initializes the data.
	 */
	public TreeNode()
	{
		children = new ArrayList<TreeNode>();
	}
	
	/**
	 * Get the location that the data in the TreeNode is in the source
	 * file/text.
	 * 
	 * @return The Location instance holding the information.
	 */
	public Location getLocationIn()
	{
		return locationIn;
	}
	
	/**
	 * Set the location that the data in the TreeNode is in the source
	 * file/text.
	 * 
	 * @param locationIn The Location instance holding the information.
	 */
	public void setLocationIn(Location locationIn)
	{
		this.locationIn = locationIn;
	}
	
	/**
	 * Get the location that the data in the TreeNode is in the destination
	 * file/text.
	 * 
	 * @return The Location instance holding the information.
	 */
	public Location getLocationOut()
	{
		return locationOut;
	}
	
	/**
	 * Set the location that the data in the TreeNode is in the destination
	 * file/text.
	 * 
	 * @param locationIn The Location instance holding the information.
	 */
	public void setLocationOut(Location locationOut)
	{
		this.locationOut = locationOut;
	}
	
	/**
	 * Get the parent of the specified TreeNode. If the TreeNode
	 * does not have a parent, null is returned.
	 * 
	 * @return The parent TreeNode instance.
	 */
	public TreeNode getParent()
	{
		return parent;
	}
	
	/**
	 * Get the nearest ancestor TreeNode to the specific TreeNode with
	 * the given Class type.
	 * 
	 * @param type The Class type of the Ancestor to search for.
	 * @return The nearest ancestor TreeNode to the specific TreeNode.
	 */
	public TreeNode getAncestorOfType(Class<?> type)
	{
		return getAncestorOfType(type, false);
	}
	
	/**
	 * Get the nearest ancestor TreeNode to the specific TreeNode with
	 * the given Class type.
	 * 
	 * @param type The Class type of the Ancestor to search for.
	 * @param inclusive Whether or not to check the current TreeNode.
	 * @return The nearest ancestor TreeNode to the specific TreeNode.
	 */
	public TreeNode getAncestorOfType(Class<?> type, boolean inclusive)
	{
		boolean  checkSuper = true;//false;
		
		TreeNode node       = null;
		
		if (inclusive)
		{
			node = this;
			
			if (node.getClass().getSimpleName().length() <= 0 || true)
			{
				checkSuper = true;
			}
		}
		else
		{
			node = parent;
		}
		
		while (node != null && ((!checkSuper || !instanceOf(node, type)) && !node.getClass().equals(type)))
		{
			checkSuper = false;
			
			node = node.getParent();
			
			if (node != null && node.getClass().getSimpleName().length() <= 0 || true)
			{
				checkSuper = true;
			}
		}
		
		return node;
	}
	
	public boolean instanceOf(Object o, Class<?> clazz)
	{
		Class<?> current = o.getClass();
		
		while (current != null)
		{
			if (current.equals(clazz))
			{
				return true;
			}
			
			current = current.getSuperclass();
		}
		
		return false;
	}
	
//	/**
//	 * Get the nearest ancestor TreeNode to the specific TreeNode with
//	 * the given Class type.
//	 * 
//	 * @param type The Class type of the Ancestor to search for.
//	 * @return The nearest ancestor TreeNode to the specific TreeNode.
//	 */
//	public TreeNode getChildOfType(Class<?> type)
//	{
//		return getChildOfType(type, false);
//	}
//	
//	/**
//	 * Get the nearest ancestor TreeNode to the specific TreeNode with
//	 * the given Class type.
//	 * 
//	 * @param type The Class type of the Ancestor to search for.
//	 * @param inclusive Whether or not to check the current TreeNode.
//	 * @return The nearest ancestor TreeNode to the specific TreeNode.
//	 */
//	public TreeNode getChildOfType(Class<?> type, boolean inclusive)
//	{
//		boolean  checkSuper = true;//false;
//		
//		TreeNode parent     = this;
//		
//		if (inclusive)
//		{
//			if (parent.getClass().getSimpleName().length() <= 0 || true)
//			{
//				checkSuper = true;
//			}
//			
//			if (parent.getClass().equals(type))
//			{
//				return parent;
//			}
//		}
//		
//		for (int i = 0; parent != null && i < parent.getChildren().size(); i++)
//		{
//			TreeNode node = parent.getChild(i);
//			
//			if (((!checkSuper || !node.getClass().getSuperclass().equals(type)) && !node.getClass().equals(type)))
//			{
//				return node;
//			}
//			
//			checkSuper = false;
//			
//			if (node != null && node.getClass().getSimpleName().length() <= 0 || true)
//			{
//				checkSuper = true;
//			}
//		}
//		
//		for (int i = 0; parent != null && i < parent.getChildren().size(); i++)
//		{
//			TreeNode node  = parent.getChild(i);
//			
//			TreeNode child = node.getChildOfType(type, inclusive);
//			
//			if (child != null)
//			{
//				return child;
//			}
//		}
//		
//		return null;
//	}
	
	/**
	 * Get an ArrayList with all of the children TreeNodes to the specific
	 * TreeNode.
	 * 
	 * @return An ArrayList instance with all of the Children TreeNodes.
	 */
	public ArrayList<TreeNode> getChildren()
	{
		return children;
	}
	
	/**
	 * Get the child TreeNode at the specific index in the children
	 * ArrayList.
	 * 
	 * @param index The index to access the child node from.
	 * @return The child TreeNode at the specific index.
	 */
	public TreeNode getChild(int index)
	{
		return children.get(index);
	}
	
	/**
	 * Add the specific TreeNode under the current TreeNode as a child.
	 * 
	 * @param node The node to set as the child node.
	 */
	public void addChild(TreeNode node)
	{
		children.add(node);
		
		// If the node already belongs to a parent, remove it from its old parent.
		if (node.parent != null)
		{
			node.parent.children.remove(node);
		}
		
		// Set this instance as the new parent.
		node.parent = this;
	}
	
	/**
	 * Iterate through the words of the statement. A word is just anything
	 * that is surrounded by whitespace. e.g. In the statement:
	 * "public void test() { }" the words consist of:
	 * [ public, void, test(), {, } ]
	 * 
	 * @param statement The statement to iterate the words from.
	 */
	public void iterateWords(String statement)
	{
		iterateWords(statement, Patterns.WORD_BOUNDARIES);
	}
	
	public void iterateWords(String statement, Pattern pattern)
	{
		// Pattern used to find word boundaries.
		Matcher matcher = pattern.matcher(statement);
		
		int     index   = 0;
		
		boolean end     = false;
		
		ArrayList<Bounds> bounds = new ArrayList<Bounds>();
		ArrayList<String> words  = new ArrayList<String>();
		
		while (matcher.find())
		{
			if (end)
			{
				bounds.add(new Bounds(index, matcher.start()));
				
				words.add(statement.substring(index, matcher.start()));
				
				end = false;
			}
			else
			{
				index = matcher.start();
				
				end   = true;
			}
		}
		
		for (int i = 0; i < bounds.size(); i++)
		{
			String word  = words.get(i);
			Bounds bound = bounds.get(i);
			
			interactWord(word, i, bound, bounds.size());
		}
	}
	
	/**
	 * Method that is to be overridden. Whenever the iterateWords(String)
	 * method is called, this method will be called with the specific word
	 * and the number (order) the word came in the statement.
	 * 
	 * @param word The word that was found.
	 * @param wordNumber The index of the word on a word-by-word basis.
	 * @param bounds The bounds of the word that was found.
	 * @param numWords The number of words that were parsed.
	 */
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords)
	{
		
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the TreeNode to the Java programming
	 * language syntax.
	 * 
	 * @return The Java syntax representation of the TreeNode.
	 */
	public abstract String generateJavaSourceOutput();
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the TreeNode to the C programming
	 * language header file syntax.
	 * 
	 * @return The C header file syntax representation of the TreeNode.
	 */
	public abstract String generateCHeaderOutput();
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the TreeNode to the C programming
	 * language source file syntax.
	 * 
	 * @return The C source syntax representation of the TreeNode.
	 */
	public abstract String generateCSourceOutput();
	
	/**
	 * Decode the specific statement into its correct TreeNode value. If
	 * the statement does not translate into a TreeNode, a syntax error
	 * has occurred. 
	 * 
	 * @param parent The Parent TreeNode of the current statement to be
	 * 		decoded.
	 * @param statement The statement to be decoded into a TreeNode.
	 * @param location The Location in the source text where the statement
	 * 		is located at.
	 * @return The TreeNode constructed from the statement.
	 */
	public static TreeNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		TreeNode node = null;
		
		if (parent instanceof FileNode)
		{
			if ((node = ImportNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
			else if ((node = ClassNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
		}
		else if (parent instanceof ClassNode)
		{
			if ((node = DestructorNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
			else if ((node = ConstructorNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
			else if ((node = MethodNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
			else if ((node = FieldNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
		}
		else if (parent instanceof MethodNode)
		{
			if ((node = ReturnNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
			else if ((node = InstantiationNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
			else if ((node = IfStatementNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
			else if ((node = MethodCallNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
			else if ((node = AssignmentNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
			else if ((node = LocalVariableNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
		}
		else if (parent instanceof MethodCallNode)
		{
			if ((node = MethodCallNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
			else if ((node = BinaryOperatorNode.decodeStatement(parent, statement, location)) != null)
			{
				return node;
			}
		}
		
//		SyntaxError.outputNewError("Unknown statement", location);
		
		return null;
	}
	
//	public static MethodNode getMethodNode(TreeNode node, String objectContaining, String methodName)
//	{
//		ClassNode classNode = (ClassNode)node.getAncestorOfType(ClassNode.class, true);
//		
//		String objects[] = objectContaining.split(".");
//		
//		
//		
//		MethodListNode methods = classNode.getMethodListNode();
//		
//		
//	}
	
	public static IdentifierNode getExistingNode(TreeNode node, String statement)
	{
		if (SyntaxUtils.isLiteral(statement))
		{
			return null;
		}
		
		if (SyntaxUtils.isMethodCall(statement))
		{
//			int dot = containsBefore(statement, '.', '(');
//			
//			IdentifierNode identifier = null;
//			
//			if (dot > 0)
//			{
//				String identifierName = statement.substring(0, dot);
//				
//				identifier = getExistingNode(node, identifierName);
//			}
//			
//			Bounds         bounds     = Regex.boundsOf(statement, Patterns.METHOD_NAME);
//			
//			String         methodName = statement.substring(bounds.getStart(), bounds.getEnd());
//			
//			ClassNode      classNode  = (ClassNode)node.getAncestorOfType(ClassNode.class, true);
//			
//			MethodListNode methods    = classNode.getMethodListNode();
//			
//			for (int i = 0; i < methods.getChildren().size(); i++)
//			{
//				MethodNode method = (MethodNode)methods.getChild(i);
//				
//				if (method.getName().equals(methodName))
//				{
//					return method;
//				}
//			}
			
			return null;
		}
		else if (SyntaxUtils.isValidIdentifier(statement))
		{
			MethodNode methodNode = (MethodNode)node.getAncestorOfType(MethodNode.class, true);
			
			if (methodNode != null)
			{
				LocalVariableListNode variables = methodNode.getLocalVariableListNode();
				
				for (int i = 0; i < variables.getChildren().size(); i++)
				{
					VariableNode variable = (VariableNode)variables.getChild(i);
					
					if (variable.getName().equals(statement))
					{
						return variable;
					}
				}
				
				ParameterListNode parameters = methodNode.getParameterListNode();
				
				for (int i = 0; i < parameters.getChildren().size(); i++)
				{
					ParameterNode parameter = (ParameterNode)parameters.getChild(i);
					
					if (parameter.getName().equals(statement))
					{
						return parameter;
					}
				}
			}
			
			ClassNode classNode = (ClassNode)node.getAncestorOfType(ClassNode.class, true);
			
			PrivateFieldListNode variables = classNode.getFieldListNode().getPrivateFieldListNode();
			
			for (int i = 0; i < variables.getChildren().size(); i++)
			{
				VariableNode variable = (VariableNode)variables.getChild(i);
				
				if (variable.getName().equals(statement))
				{
					return variable;
				}
			}
			
			PublicFieldListNode fields = classNode.getFieldListNode().getPublicFieldListNode();
			
			for (int i = 0; i < fields.getChildren().size(); i++)
			{
				VariableNode variable = (VariableNode)fields.getChild(i);
				
				if (variable.getName().equals(statement))
				{
					return variable;
				}
			}
		}
		
		return null;
	}
	
	private static int containsBefore(String text, char before, char after)
	{
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			
			if (c == before)
			{
				return i;
			}
			else if (c == after)
			{
				return -1;
			}
		}
		
		return -1;
	}
	
	public abstract TreeNode clone();
}
