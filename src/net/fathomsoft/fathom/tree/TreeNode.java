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

import net.fathomsoft.fathom.Fathom;
import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.exceptionhandling.CatchNode;
import net.fathomsoft.fathom.tree.exceptionhandling.ExceptionHandlingNode;
import net.fathomsoft.fathom.tree.exceptionhandling.ExceptionNode;
import net.fathomsoft.fathom.tree.exceptionhandling.FinallyNode;
import net.fathomsoft.fathom.tree.exceptionhandling.ThrowNode;
import net.fathomsoft.fathom.tree.exceptionhandling.TryNode;
import net.fathomsoft.fathom.tree.variables.FieldListNode;
import net.fathomsoft.fathom.tree.variables.FieldNode;
import net.fathomsoft.fathom.tree.variables.LocalVariableNode;
import net.fathomsoft.fathom.tree.variables.PrivateFieldListNode;
import net.fathomsoft.fathom.tree.variables.PublicFieldListNode;
import net.fathomsoft.fathom.tree.variables.VariableListNode;
import net.fathomsoft.fathom.tree.variables.VariableNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * Class that is the parent of all Nodes on the Tree. Keeps the basic
 * information of where the statement was in the source, and where it was
 * output in the destination file. A TreeNode can have any number of
 * children, however some of the extensions of TreeNode have default
 * children at the start.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:11 PM
 * @version	v0.2 Apr 22, 2014 at 5:36:14 AM
 */
public abstract class TreeNode
{
	private Location			locationIn, locationOut;
	
	private TreeNode			parent;
	
	private ArrayList<TreeNode>	children;
	
	private static final Class<?>	FILE_CHILD_DECODE[] = new Class<?>[]
	{
		ImportNode.class, ClassNode.class
	};
	
	private static final Class<?>	CLASS_CHILD_DECODE[] = new Class<?>[]
	{
		DestructorNode.class, ConstructorNode.class, MethodNode.class, FieldNode.class
	};
	
	private static final Class<?>	SCOPE_CHILD_DECODE[] = new Class<?>[]
	{
		ExceptionHandlingNode.class, ReturnNode.class, ArrayAccessNode.class, InstantiationNode.class,
		ElseStatementNode.class, IfStatementNode.class, LoopNode.class, UnaryOperatorNode.class,
		MethodCallNode.class, AssignmentNode.class, LocalVariableNode.class
	};
	
	private static final Class<?>	METHOD_CALL_CHILD_DECODE[] = new Class<?>[]
	{
		MethodCallNode.class, BinaryOperatorNode.class
	};
	
	/**
	 * Create a new TreeNode. Initializes the data.
	 */
	public TreeNode()
	{
		children = new ArrayList<TreeNode>();
	}
	
	/**
	 * Get the line number in which the TreeNode was decoded at.
	 * 
	 * @return The line number in which the TreeNode was decoded at.
	 */
	public int getLineNumber()
	{
		int lineNumber = 0;
		
		if (parent instanceof FileNode == false)
		{
			lineNumber += parent.getLineNumber();
		}
		
		Location loc = getLocationIn();
		
		if (loc != null)
		{
			lineNumber += loc.getLineNumber();
		}
		
		return lineNumber - 1;
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
	 * @param locationOut The Location instance holding the information.
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
		
		while (node != null && ((!checkSuper || !node.instanceOf(type)) && !node.getClass().equals(type)))
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
	
	/**
	 * Get whether or not the given Object is an instance of any of the
	 * given Classes.
	 * 
	 * @param classes The Classes to check the Object against.
	 * @return Whether or not the given Object is an instance of the given
	 * 		Class.
	 */
	public boolean instanceOf(Class<?> classes[])
	{
		for (Class<?> c : classes)
		{
			if (instanceOf(c))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Get whether or not the given Object is an instance of the given
	 * Class.
	 * 
	 * @param clazz The Class to check the Object against.
	 * @return Whether or not the given Object is an instance of the given
	 * 		Class.
	 */
	public boolean instanceOf(Class<?> clazz)
	{
		Class<?> current = getClass();
		
		while (current != null)
		{
			if (current.equals(clazz))
			{
				return true;
			}
			
			Class<?>[] interfaces = current.getInterfaces();
			
			for (Class<?> iface : interfaces)
			{
				if (iface.equals(clazz))
				{
					return true;
				}
			}
			
			current = current.getSuperclass();
		}
		
		return false;
	}
	
	/**
	 * Get the ScopeNode instance of this TreeNode if it even has
	 * a scope. If the TreeNode does not have a ScopeNode then this
	 * method call will return null.
	 * 
	 * @return The ScopeNode instance, if it exists.
	 */
	public ScopeNode getScopeNode()
	{
		return null;
	}
	
	/**
	 * Get whether or not the current node type has a scope.
	 * 
	 * @return Whether or not the current node type has a scope.
	 */
	public boolean containsScope()
	{
		return getScopeNode() != null;
	}
	
	/**
	 * Add the given LocalVariableNode to the nearest scope.
	 * 
	 * @param node The LocalVariableNode to add.
	 */
	public void addToNearestScope(LocalVariableNode node)
	{
		getAncestorWithScope(this).addChild(node);
	}
	
	/**
	 * Get the nearest ancestor that contains a scope.
	 * 
	 * @param node The node to start the search at.
	 * @return The nearest ancestor to the given node that has a scope.
	 */
	public static TreeNode getAncestorWithScope(TreeNode node)
	{
		while (node != null)
		{
			if (node.containsScope())
			{
				return node;
			}
			
			node = node.getParent();
		}
		
		return null;
	}
	
	/**
	 * Get whether or not the specified node is an ancestor of the given
	 * node.
	 * 
	 * @param node The node to search for the ancestor of.
	 * @return Whether or not the specified node is an ancestor of the
	 * 		given node.
	 */
	public boolean isAncestorOf(TreeNode node)
	{
		return isAncestorOf(node, false);
	}
	
	/**
	 * Get whether or not the specified node is an ancestor of the given
	 * node.
	 * 
	 * @param node The node to search for the ancestor of.
	 * @param inclusive Whether or not to check if the node itself is a
	 * 		match.
	 * @return Whether or not the specified node is an ancestor of the
	 * 		given node.
	 */
	public boolean isAncestorOf(TreeNode node, boolean inclusive)
	{
		TreeNode current = node;
		
		if (!inclusive)
		{
			current = node.parent;
		}
		
		while (current != null)
		{
			if (current == this)
			{
				return true;
			}
		}
		
		return false;
	}
	
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
		addChild(children.size(), node);
	}
	
	/**
	 * Add the specific TreeNode under the current TreeNode as a child.
	 * 
	 * @param index The index to add the node at.
	 * @param node The node to set as the child node.
	 */
	public void addChild(int index, TreeNode node)
	{
		children.add(index, node);
		
		// If the node already belongs to a parent, remove it from its old parent.
		if (node.parent != null)
		{
//			System.err.println(this + " " + node);
			node.parent.children.remove(node);
		}
		
		// Set this instance as the new parent.
		node.parent = this;
	}
	
	/**
	 * Detach the specified node from its parent.
	 */
	public void detach()
	{
		if (parent == null)
		{
			return;
		}

		parent.getChildren().remove(this);
		
		parent = null;
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
	
	/**
	 * Iterate through each of the groupings of the given Pattern on the
	 * statement. In the default case, it will search for the boundaries
	 * of words and iterate through all of them.
	 * 
	 * @param statement The statement to search through.
	 * @param pattern The Pattern to search with.
	 */
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
	 * Get whether or not the specified TreeNode is used within an
	 * external context.
	 * 
	 * @return Whether or not the specified TreeNode is used within an
	 * 		external context.
	 */
	public boolean isWithinExternalContext()
	{
		if (this instanceof MethodCallNode)
		{
			MethodCallNode thisNode = (MethodCallNode)this;
			
			if (thisNode.isExternal())
			{
				return true;
			}
		}
		
		MethodCallNode current = (MethodCallNode)getAncestorOfType(MethodCallNode.class);
		
		while (current != null)
		{
			if (current.isWithinExternalContext())
			{
				return true;
			}
			
			current = (MethodCallNode)current.getAncestorOfType(MethodCallNode.class);
		}
		
		return false;
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the TreeNode to the Java programming
	 * language syntax.
	 * 
	 * @return The Java syntax representation of the TreeNode.
	 */
	public String generateJavaSource()
	{
		return null;
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the TreeNode to the C programming
	 * language header file syntax.
	 * 
	 * @return The C header file syntax representation of the TreeNode.
	 */
	public String generateCHeader()
	{
		return null;
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the TreeNode to the C programming
	 * language source file syntax.
	 * 
	 * @return The C source syntax representation of the TreeNode.
	 */
	public String generateCSource()
	{
		return null;
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the TreeNode to the C programming
	 * language source file 'fragment' syntax.
	 * 
	 * @return The C source syntax representation of the TreeNode.
	 */
	public String generateCSourceFragment()
	{
		return null;
	}
	
	/**
	 * Method that each Node can override. Returns a String that
	 * translates the data that is stored in the TreeNode to the C
	 * programming language header file 'fragment' syntax.
	 * 
	 * @return The C header syntax representation of the TreeNode.
	 */
	public String generateCHeaderFragment()
	{
		return null;
	}

	
	/**
	 * If the specified node is within an try block, return the node for
	 * the try block.
	 * 
	 * @return The parent TryNode, if there is one.
	 */
	public TryNode getParentTry()
	{
		TryNode node = (TryNode)getAncestorOfType(TryNode.class, true);
		
		return node;
	}
	
	/**
	 * Get whether or not the specified node is within an try block.
	 * 
	 * @return Whether or not the specified node is within a try block.
	 */
	public boolean isWithinTry()
	{
		return getParentTry() != null;
	}
	
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
	 * @param types The types of TreeNodes to try to decode, in the given
	 * 		order.
	 * @return The TreeNode constructed from the statement, if any.
	 */
	public static TreeNode decodeStatement(TreeNode parent, String statement, Location location, Class<?> types[])
	{
		TreeNode node = null;
		
		for (Class<?> type : types)
		{
			if      (type == LocalVariableNode.class) node = LocalVariableNode.decodeStatement(parent, statement, location);
			else if (type == IfStatementNode.class) node = IfStatementNode.decodeStatement(parent, statement, location);
			else if (type == ElseStatementNode.class) node = ElseStatementNode.decodeStatement(parent, statement, location);
			else if (type == ArgumentListNode.class) node = ArgumentListNode.decodeStatement(parent, statement, location);
			else if (type == ArrayAccessNode.class) node = ArrayAccessNode.decodeStatement(parent, statement, location);
			else if (type == AssignmentNode.class) node = AssignmentNode.decodeStatement(parent, statement, location);
			else if (type == ArrayNode.class) node = ArrayNode.decodeStatement(parent, statement, location);
			else if (type == BinaryOperatorNode.class) node = BinaryOperatorNode.decodeStatement(parent, statement, location);
			else if (type == ClassNode.class) node = ClassNode.decodeStatement(parent, statement, location);
			else if (type == ConditionNode.class) node = ConditionNode.decodeStatement(parent, statement, location);
			else if (type == ConstructorNode.class) node = ConstructorNode.decodeStatement(parent, statement, location);
			else if (type == DeclarationNode.class) node = DeclarationNode.decodeStatement(parent, statement, location);
			else if (type == DestructorNode.class) node = DestructorNode.decodeStatement(parent, statement, location);
			else if (type == FileNode.class) node = FileNode.decodeStatement(parent, statement, location);
			else if (type == ForLoopNode.class) node = ForLoopNode.decodeStatement(parent, statement, location);
			else if (type == IdentifierNode.class) node = IdentifierNode.decodeStatement(parent, statement, location);
			else if (type == IfStatementNode.class) node = IfStatementNode.decodeStatement(parent, statement, location);
			else if (type == ImportListNode.class) node = ImportListNode.decodeStatement(parent, statement, location);
			else if (type == ImportNode.class) node = ImportNode.decodeStatement(parent, statement, location);
			else if (type == InstantiationNode.class) node = InstantiationNode.decodeStatement(parent, statement, location);
			else if (type == LiteralNode.class) node = LiteralNode.decodeStatement(parent, statement, location);
			else if (type == LoopInitializationNode.class) node = LoopInitializationNode.decodeStatement(parent, statement, location);
			else if (type == LoopNode.class) node = LoopNode.decodeStatement(parent, statement, location);
			else if (type == LoopUpdateNode.class) node = LoopUpdateNode.decodeStatement(parent, statement, location);
			else if (type == MethodCallNode.class) node = MethodCallNode.decodeStatement(parent, statement, location);
			else if (type == MethodListNode.class) node = MethodListNode.decodeStatement(parent, statement, location);
			else if (type == MethodNode.class) node = MethodNode.decodeStatement(parent, statement, location);
			else if (type == ModifierNode.class) node = ModifierNode.decodeStatement(parent, statement, location);
			else if (type == OperatorNode.class) node = OperatorNode.decodeStatement(parent, statement, location);
			else if (type == ParameterListNode.class) node = ParameterListNode.decodeStatement(parent, statement, location);
			else if (type == ProgramNode.class) node = ProgramNode.decodeStatement(parent, statement, location);
			else if (type == ReturnNode.class) node = ReturnNode.decodeStatement(parent, statement, location);
			else if (type == ScopeNode.class) node = ScopeNode.decodeStatement(parent, statement, location);
			else if (type == UnaryOperatorNode.class) node = UnaryOperatorNode.decodeStatement(parent, statement, location);
			else if (type == WhileLoopNode.class) node = WhileLoopNode.decodeStatement(parent, statement, location);
			else if (type == FieldListNode.class) node = FieldListNode.decodeStatement(parent, statement, location);
			else if (type == FieldNode.class) node = FieldNode.decodeStatement(parent, statement, location);
			else if (type == PrivateFieldListNode.class) node = PrivateFieldListNode.decodeStatement(parent, statement, location);
			else if (type == PublicFieldListNode.class) node = PublicFieldListNode.decodeStatement(parent, statement, location);
			else if (type == VariableListNode.class) node = VariableListNode.decodeStatement(parent, statement, location);
			else if (type == VariableNode.class) node = VariableNode.decodeStatement(parent, statement, location);
			else if (type == CatchNode.class) node = CatchNode.decodeStatement(parent, statement, location);
			else if (type == ExceptionHandlingNode.class) node = ExceptionHandlingNode.decodeStatement(parent, statement, location);
			else if (type == ExceptionNode.class) node = ExceptionNode.decodeStatement(parent, statement, location);
			else if (type == FinallyNode.class) node = FinallyNode.decodeStatement(parent, statement, location);
			else if (type == ThrowNode.class) node = ThrowNode.decodeStatement(parent, statement, location);
			else if (type == TryNode.class) node = TryNode.decodeStatement(parent, statement, location);
			
			if (node != null)
			{
				node.setLocationIn(location);
				
				return node;
			}
		}
		
//		SyntaxMessage.error("Unknown statement", location, parent.getController());
		
		return null;
	}
	
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
	 * @return The TreeNode constructed from the statement, if any.
	 */
	public static TreeNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (parent instanceof FileNode)
		{
			return decodeStatement(parent, statement, location, FILE_CHILD_DECODE);
		}
		else if (parent instanceof ClassNode)
		{
			return decodeStatement(parent, statement, location, CLASS_CHILD_DECODE);
		}
		else if (parent instanceof MethodNode || parent instanceof IfStatementNode || parent instanceof ElseStatementNode || parent instanceof LoopNode || parent instanceof ExceptionHandlingNode || parent instanceof ScopeNode)
		{
			return decodeStatement(parent, statement, location, SCOPE_CHILD_DECODE);
		}
		else if (parent instanceof MethodCallNode)
		{
			return decodeStatement(parent, statement, location, METHOD_CALL_CHILD_DECODE);
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
	
	/**
	 * Try to find an existing node from the given statement. This method
	 * searches through fields and local variables.
	 * 
	 * @param node The parent TreeNode to use as our context.
	 * @param statement The statement to check for an existing node from.
	 * @return The IdentifierNode that is found, if any.
	 */
	public static VariableNode getExistingNode(TreeNode node, String statement)
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
			if (statement.equals("this"))
			{
				ClassNode clazz = (ClassNode)node.getAncestorOfType(ClassNode.class, true);
				
				VariableNode var = new VariableNode();
				var.setName(MethodNode.getObjectReferenceIdentifier(), true);
				var.setType(clazz.getName());
				
				return var;
			}
			
			TreeNode scopeNode = getAncestorWithScope(node);
			
			while (scopeNode != null)
			{
				if (scopeNode != null)
				{
					VariableListNode variables = scopeNode.getScopeNode().getVariableListNode();
					
					VariableNode     variable  = variables.getVariable(statement);
					
					if (variable != null)
					{
						return variable;
					}
					
					if (scopeNode instanceof MethodNode)
					{
						MethodNode        methodNode = (MethodNode)scopeNode;
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
				}
				
				scopeNode = getAncestorWithScope(scopeNode.getParent());
			}
			
			ClassNode classNode = (ClassNode)node.getAncestorOfType(ClassNode.class, true);
			
			return classNode.getField(statement);
		}
		else if (SyntaxUtils.isValidIdentifierAccess(statement))
		{
			if (statement.indexOf('.') >= 0)
			{
				ClassNode    reference = (ClassNode)node.getAncestorOfType(ClassNode.class);
				
				ClassNode    type      = SyntaxUtils.getClassType(reference, statement);
				
				String       var       = statement.substring(statement.lastIndexOf('.') + 1);
				
				VariableNode n         = type.getDeclaration(SyntaxUtils.getIdentifierName(var));
				
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Get whether or not the String contains the given 'before' char
	 * before the 'after' char is reached. The search is started at index
	 * 0 of the 'text' String input.
	 * 
	 * @param text The text to search.
	 * @param before The char that should appear before the 'after'
	 * 		variable.
	 * @param after The char that should appear after the 'before'
	 * 		variable.
	 * @return The index in which the 'before' char appeared at. If it did
	 * 		not appear before the 'after' char then -1 is returned.
	 */
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
	
	/**
	 * Get the ProgramNode (The oldest parent) of this TreeNode.
	 * 
	 * @return The ProgramNode of this TreeNode.
	 */
	public ProgramNode getProgramNode()
	{
		if (parent != null)
		{
			return parent.getProgramNode();
		}
		
		return (ProgramNode)this;
	}
	
	/**
	 * Get the compiler's controller. The controller is used for
	 * logging, error output, and other compiler options.
	 * 
	 * @return The compiler's controller instance.
	 */
	public Fathom getController()
	{
		return getProgramNode().getController();
	}
	
	/**
	 * Return a new TreeNode containing a copy of the values of the
	 * specified node, including clones of the children.
	 */
	public abstract TreeNode clone();
	
	/**
	 * Fill the given TreeNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public TreeNode clone(TreeNode node)
	{
		if (locationIn != null)
		{
			Location locIn  = new Location(locationIn);
			node.setLocationIn(locIn);
		}
		
		if (locationOut != null)
		{
			Location locOut = new Location(locationOut);
			node.setLocationOut(locOut);
		}
		
		for (int i = 0; i < children.size(); i++)
		{
			TreeNode child = children.get(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}
