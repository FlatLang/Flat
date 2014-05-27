package net.fathomsoft.nova.tree;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.exceptionhandling.CatchNode;
import net.fathomsoft.nova.tree.exceptionhandling.ExceptionHandlingNode;
import net.fathomsoft.nova.tree.exceptionhandling.ExceptionNode;
import net.fathomsoft.nova.tree.exceptionhandling.FinallyNode;
import net.fathomsoft.nova.tree.exceptionhandling.ThrowNode;
import net.fathomsoft.nova.tree.exceptionhandling.TryNode;
import net.fathomsoft.nova.tree.variables.ArrayAccessNode;
import net.fathomsoft.nova.tree.variables.ArrayNode;
import net.fathomsoft.nova.tree.variables.FieldListNode;
import net.fathomsoft.nova.tree.variables.FieldNode;
import net.fathomsoft.nova.tree.variables.InstanceFieldListNode;
import net.fathomsoft.nova.tree.variables.LocalVariableNode;
import net.fathomsoft.nova.tree.variables.StaticFieldListNode;
import net.fathomsoft.nova.tree.variables.VariableListNode;
import net.fathomsoft.nova.tree.variables.VariableNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Class that is the parent of all Nodes on the Tree. Keeps the basic
 * information of where the statement was in the source, and where it was
 * output in the destination file. A TreeNode can have any number of
 * children, however some of the extensions of TreeNode have default
 * children at the start.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:11 PM
 * @version	v0.2.8 May 26, 2014 at 11:26:58 PM
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
	
	private static final Class<?>	PRE_VALUE_DECODE[] = new Class<?>[]
	{
		ReturnNode.class, AssignmentNode.class,
	};
	
	private static final Class<?>	SCOPE_CHILD_DECODE[] = new Class<?>[]
	{
		ExceptionHandlingNode.class, AssignmentNode.class, InstantiationNode.class,
		ElseStatementNode.class, IfStatementNode.class, LoopNode.class, ExternalTypeNode.class,
		ArrayAccessNode.class, UnaryOperatorNode.class, MethodCallNode.class, LocalDeclarationNode.class
	};
	
	private static final Class<?>	METHOD_CALL_CHILD_DECODE[] = new Class<?>[]
	{
		MethodCallNode.class, BinaryOperatorNode.class
	};
	
	/**
	 * Create a new TreeNode. Initializes the data.
	 */
	public TreeNode(TreeNode temporaryParent, Location locationIn)
	{
		children = new ArrayList<TreeNode>();
		
		setTemporaryParent(temporaryParent);
		setLocationIn(locationIn);
	}
	
	/**
	 * Get the line number in which the TreeNode was decoded at.
	 * 
	 * @return The line number in which the TreeNode was decoded at.
	 */
	public int getLineNumber()
	{
		int lineNumber = 0;
		
		if (parent == null || parent.getLocationIn() == null || !parent.getLocationIn().isValid())
		{
			lineNumber = 1;
		}
		else
		{
			lineNumber += parent.getLineNumber();
		}
		
		Location loc = getLocationIn();
		
		if (loc != null && loc.isValid())
		{
			lineNumber += loc.getLineNumber();
		}
		
		return lineNumber + 1;
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
			
			current = current.parent;
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
	 * Remove the specific TreeNode from the current TreeNode as a child.
	 * 
	 * @param index The index to remove the node from.
	 */
	public void removeChild(int index)
	{
		TreeNode node = children.get(index);
		
		node.detach();
	}
	
	/**
	 * Remove the specific TreeNode from the current TreeNode as a child.
	 * 
	 * @param node The node to remove as the child node.
	 */
	public void removeChild(TreeNode node)
	{
		removeChild(children.indexOf(node));
	}
	
	/**
	 * Add the specific TreeNode under the current TreeNode as a child.
	 * 
	 * @param index The index to add the node at.
	 * @param node The node to set as the child node.
	 */
	public void addChild(int index, TreeNode node)
	{
		// If the node already belongs to a parent, remove it from its old parent.
		if (node.parent != null)
		{
//			System.err.println(this + " " + node);
//			node.parent.children.remove(node);
			node.detach();
		}
		
		children.add(index, node);
		
		// Set this instance as the new parent.
		node.parent = this;
	}
	
	/**
	 * Set a temporary parent for the specified TreeNode. When, if ever,
	 * the TreeNode is formally added to a TreeNode, the temporary parent
	 * will be removed.
	 * 
	 * @param parent The TreeNode to act as the parent temporarily.
	 */
	public void setTemporaryParent(TreeNode parent)
	{
		detach();
		
		this.parent = parent;
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
	 * Replace the given old node with the specified replacement.
	 * 
	 * @param old The node to replace.
	 * @param replacement The replacement node.
	 */
	public void replace(TreeNode old, TreeNode replacement)
	{
		int index = children.indexOf(old);
		
		old.detach();
		replacement.detach();
		
		addChild(index, replacement);
	}
	
	/**
	 * Give the specified node the given nodes children. This removes the
	 * children from the given oldParent node.
	 */
	public void inheritChildren(TreeNode oldParent)
	{
		inheritChildren(oldParent, false);
	}
	
	/**
	 * Give the specified node the given nodes children. This removes the
	 * children from the given oldParent node.
	 * 
	 * @param clone Whether or not to clone the children and not remove
	 * 		them from the previous owner.
	 */
	public void inheritChildren(TreeNode oldParent, boolean clone)
	{
		int index = children.size();
		
		for (int i = oldParent.children.size() - 1; i >= 0; i--)
		{
			TreeNode child = oldParent.getChild(i);
			
			if (clone)
			{
				child = child.clone(this, child.getLocationIn());
			}
			else
			{
				child.detach();
			}
			
			addChild(index, child);
		}
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
		Matcher matcher  = pattern.matcher(statement);
		
		int     index    = 0;
		int     oldIndex = 0;
		
		boolean end      = false;
		
		ArrayList<Bounds> bounds = new ArrayList<Bounds>();
		ArrayList<String> words  = new ArrayList<String>();
		ArrayList<String> delims = new ArrayList<String>();
		
		while (matcher.find())
		{
			if (end)
			{
				bounds.add(new Bounds(index, matcher.start()));
				
				String delim = statement.substring(oldIndex, index);
				
				delim = StringUtils.trimSurroundingWhitespace(delim);
				
				delims.add(delim);
				
				oldIndex = matcher.start();
				
				words.add(statement.substring(index, matcher.start()));
				
				end = false;
			}
			else
			{
				index = matcher.start();
				
				end   = true;
			}
		}

		delims.add(statement.substring(oldIndex, statement.length()));
		
		for (int i = 0; i < bounds.size(); i++)
		{
			String word  = words.get(i);
			Bounds bound = bounds.get(i);
			
			interactWord(word, i, bound, bounds.size(), delims.get(i), delims.get(i + 1));
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
	 * Method that is to be overridden. Whenever the iterateWords(String)
	 * method is called, this method will be called with the specific word
	 * and the number (order) the word came in the statement.
	 * 
	 * @param word The word that was found.
	 * @param wordNumber The index of the word on a word-by-word basis.
	 * @param bounds The bounds of the word that was found.
	 * @param numWords The number of words that were parsed.
	 * @param leftDelimiter The text that is between the previous word and
	 * 		the current word.
	 * @param rightDelimiter The text that is between the current word and
	 * 		the next word.
	 */
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter)
	{
		interactWord(word, wordNumber, bounds, numWords);
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
		if (this instanceof ExternalTypeNode || this instanceof ExternalStatementNode)
		{
			return true;
		}
		
		if (parent != null)
		{
			return parent.isWithinExternalContext();
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
	 * Generate the Nova syntax String that represents the TreeNode.
	 * Essentially, this is the String that is decoded into the node.
	 * It is the input value from the .fat source file.
	 * 
	 * @return A String that represents the input String in Nova syntax.
	 */
	public String generateNovaInput()
	{
		return generateNovaInput(true);
	}
	
	/**
	 * Generate the Nova syntax String that represents the TreeNode.
	 * Essentially, this is the String that is decoded into the node.
	 * It is the input value from the .fat source file.
	 * 
	 * @param outputChildren Whether or not to output the children of the
	 * 		children of the TreeNode as well.
	 * @return A String that represents the input String in Nova syntax.
	 */
	public String generateNovaInput(boolean outputChildren)
	{
		return null;
	}
	
	/**
	 * Validate the node to make last minute changes or error checking.
	 */
	public void validate()
	{
		
	}
	
	/**
	 * Generate a String containing information of where the TreeNode is
	 * located in reference to the source input files.
	 * 
	 * @return A String containing information of where the TreeNode is
	 * 		located in reference to the source input files.
	 */
	public String getLocationInfo()
	{
		FileNode file = getFileNode();
		Location loc  = getLocationIn();
		
		String   info = "";
		
		if (file != null)
		{
			info += " in file " + file.getName();
		}
		if (loc != null)
		{
			info += " on line number " + loc.getLineNumber() + " at offset " + loc.getOffset();
		}
		
		return info;
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
			if      (type == LocalDeclarationNode.class) node = LocalDeclarationNode.decodeStatement(parent, statement, location);
			else if (type == LocalVariableNode.class) node = LocalVariableNode.decodeStatement(parent, statement, location);
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
			else if (type == InstanceDeclarationNode.class) node = InstanceDeclarationNode.decodeStatement(parent, statement, location);
			else if (type == DestructorNode.class) node = DestructorNode.decodeStatement(parent, statement, location);
			else if (type == ExternalTypeNode.class) node = ExternalTypeNode.decodeStatement(parent, statement, location);
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
			else if (type == OperatorNode.class) node = OperatorNode.decodeStatement(parent, statement, location);
			else if (type == ParameterListNode.class) node = ParameterListNode.decodeStatement(parent, statement, location);
			else if (type == ProgramNode.class) node = ProgramNode.decodeStatement(parent, statement, location);
			else if (type == ReturnNode.class) node = ReturnNode.decodeStatement(parent, statement, location);
			else if (type == ScopeNode.class) node = ScopeNode.decodeStatement(parent, statement, location);
			else if (type == UnaryOperatorNode.class) node = UnaryOperatorNode.decodeStatement(parent, statement, location);
			else if (type == WhileLoopNode.class) node = WhileLoopNode.decodeStatement(parent, statement, location);
			else if (type == FieldListNode.class) node = FieldListNode.decodeStatement(parent, statement, location);
			else if (type == FieldNode.class) node = FieldNode.decodeStatement(parent, statement, location);
			else if (type == StaticFieldListNode.class) node = StaticFieldListNode.decodeStatement(parent, statement, location);
			else if (type == InstanceFieldListNode.class) node = InstanceFieldListNode.decodeStatement(parent, statement, location);
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
//				node.setLocationIn(location);
				
				return node;
			}
		}
		
//		SyntaxMessage.error("Unknown statement", location, parent.getController());
		
		return ExternalStatementNode.decodeStatement(parent, statement, location);
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
			return decodeScopeContents(parent, statement, location);
		}
		else if (parent instanceof MethodCallNode)
		{
			return decodeStatement(parent, statement, location, METHOD_CALL_CHILD_DECODE);
		}
		
//		SyntaxError.outputNewError("Unknown statement", location);
		
		return null;
	}
	
	/**
	 * Decode a String that was found within a scope. That is, a method
	 * or scopes within a method: for loops, while loops, if statements,
	 * etc.
	 * 
	 * @param parent The parent node of the current statement to decode.
	 * @param statement The statement to decode.
	 * @param location The location of the statement.
	 * @return The TreeNode representation of the given statement.
	 */
	public static TreeNode decodeScopeContents(TreeNode parent, String statement, Location location)
	{
		if (SyntaxUtils.isLiteral(statement))
		{
			LiteralNode literal = new LiteralNode(parent, location);
			literal.setValue(statement, parent.isWithinExternalContext());
			
			return literal;
		}
		
		TreeNode root = null;
		TreeNode node = null;
		
		try
		{
			node = decodeStatement(parent, statement, location, PRE_VALUE_DECODE);
		
			if (node != null)
			{
				return node;
			}
		}
		catch (SyntaxErrorException e)
		{
			return null;
		}
		
		int    offset   = 0;
		int    index    = SyntaxUtils.findDotOperator(statement);
		
		String current  = statement;
		
		while (index >= 0)
		{
			current = statement.substring(offset, index);
			node    = decodeValue(parent, current, location);
			
			if (node == null)
			{
				Location currentLoc = new Location(location);
				currentLoc.setBounds(offset, index);
				
				SyntaxMessage.error("Could not decode syntax '" + current + "'", parent.getFileNode(), currentLoc, parent.getController());
				
				return null;
			}
			
			parent.addChild(node);
			
			offset   = index + 1;
			
			index    = SyntaxUtils.findDotOperator(statement, offset);
			
			parent   = node;
			
			if (root == null)
			{
				root = node;
			}
		}
		
		current = statement.substring(offset, statement.length());
		
		node    = decodeStatement(parent, current, location, SCOPE_CHILD_DECODE);
		
		if (node == null)
		{
			node = decodeValue(parent, current, location);
		}
		
		if (root != null && node != null)
		{
			root.setTemporaryParent(root.parent);
			
			parent.addChild(node);
			
			return root;
		}
		else if (node == null)
		{
			if (parent instanceof VariableNode)
			{
//				throw new RuntimeException(statement + parent.getLocationInfo());
//				System.err.println(current + " : " + statement + " " + ((VariableNode)parent).getName());
			}
			else
			{
//				throw new RuntimeException("sumthin gone rong wit " + statement + parent.getLocationInfo());
//				System.err.println("sumthin gone rong wit " + statement + parent.getLocationInfo());
			}
		}
		
		return node;
	}
	
	/**
	 * Decode the value of the given statement. Can be nodes such as
	 * VariableNodes, ExternalTypesNodes, FieldNodes, etc. May also
	 * be just a plain old ValueNode describing the return type of the
	 * statement. For example: a static class's method call.
	 * <code>Math.sin(number)</code> in this instance, Math will be the
	 * ValueNode returned. In most other instances a VariableNode
	 * variation will be returned.
	 * 
	 * @param parent The parent node of the current statement to decode.
	 * @param statement The statement to decode.
	 * @param location The location of the statement.
	 * @return The TreeNode representation of the given statement.
	 */
	private static ValueNode decodeValue(TreeNode parent, String statement, Location location)
	{
		ValueNode node = (ValueNode)decodeStatement(parent, statement, location, SCOPE_CHILD_DECODE);
		
		if (node == null)
		{
			node = getExistingNode(parent, statement);
			
			if (node != null)
			{
				node = node.clone(parent, location);
			}
			else if (parent instanceof ExternalTypeNode)
			{
				ExternalTypeNode type = (ExternalTypeNode)parent;
				
				type.setType(statement);
			}
			else if (parent.getFileNode().containsImport(statement) || parent.getFileNode().containsClass(statement))
			{
				ClassNode clazz = parent.getProgramNode().getClass(statement);
				
				node = new ValueNode(parent, location);
				node.setType(clazz.getName());
			}
		}
		
		return node;
	}
	
	/**
	 * Try to find an existing node from the given statement. This method
	 * searches through fields and local variables.
	 * 
	 * @param parent The parent TreeNode to use as our context.
	 * @param statement The statement to check for an existing node from.
	 * @return The IdentifierNode that is found, if any.
	 */
	public static VariableNode getExistingNode(TreeNode parent, String statement)
	{
		if (SyntaxUtils.isLiteral(statement))
		{
			return null;
		}
		else if (SyntaxUtils.isMethodCall(statement))
		{
			return null;
		}
		else if (SyntaxUtils.isValidIdentifier(statement))
		{
			ClassNode clazz = null;
			
			if (parent instanceof LocalVariableNode || parent instanceof FieldNode)
			{
				VariableNode var = (VariableNode)parent;
				
				clazz  = var.getProgramNode().getClass(var.getType());
			}
			
			if (clazz != null)
			{
				VariableNode var   = (VariableNode)parent;
				
				FieldNode    field = clazz.getField(statement);
				
				if (field != null)
				{
					if (SyntaxUtils.isVisible(var, field))
					{
						return field;
					}
					else
					{
						SyntaxMessage.error("Field '" + field.getName() + "' is not accessible", parent.getFileNode(), parent.getLocationIn(), parent.getController());
						
						return null;
					}
				}
			}
			
			TreeNode scopeNode = getAncestorWithScope(parent);
			
			while (scopeNode != null)
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
					ParameterNode     parameter  = parameters.getParameter(statement);
					
					if (parameter != null)
					{
						return parameter;
					}
				}
				
				scopeNode = getAncestorWithScope(scopeNode.getParent());
			}
			
			clazz = (ClassNode)parent.getAncestorOfType(ClassNode.class, true);
			
			if (clazz != null)
			{
				FieldNode field = clazz.getField(statement);
				
				if (field != null)
				{
					return field;
				}
			}
		}
//		else if (SyntaxUtils.isValidIdentifierAccess(statement))
//		{
//			if (statement.indexOf('.') >= 0)
//			{
//				ClassNode    reference = (ClassNode)parent.getAncestorOfType(ClassNode.class);
//				
//				ClassNode    type      = SyntaxUtils.getClassType(reference, statement);
//				
//				String       var       = statement.substring(statement.lastIndexOf('.') + 1);
//				
//				if (type == null)
//				{
//					FieldNode field = reference.getField(var);
//					
//					if (field != null && field.isExternal())
//					{
//						return field;
//					}
//					
//					return null;
//				}
//				
//				VariableNode n = type.getDeclaration(SyntaxUtils.getIdentifierName(var));
//				
//				return n;
//			}
//		}
		
		return null;
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
	 * Get the FileNode of this TreeNode, if it exists.
	 * 
	 * @return The FileNode of this TreeNode.
	 */
	public FileNode getFileNode()
	{
		TreeNode current = this;
		
		while (current != null)
		{
			if (current instanceof FileNode)
			{
				return (FileNode)current;
			}
			
			current = current.parent;
		}
		
		return null;
	}
	
	/**
	 * Get the compiler's controller. The controller is used for
	 * logging, error output, and other compiler options.
	 * 
	 * @return The compiler's controller instance.
	 */
	public Nova getController()
	{
		return getProgramNode().getController();
	}
	
	/**
	 * Return a new TreeNode containing a copy of the values of the
	 * specified node, including clones of the children.
	 * 
	 * @param temporaryParent The TreeNode to act as the parent
	 * 		temporarily.
	 */
	public abstract TreeNode clone(TreeNode temporaryParent, Location locationIn);
	
	/**
	 * Fill the given TreeNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public TreeNode cloneTo(TreeNode node)
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
			
			node.addChild(child.clone(null, child.getLocationIn()));
		}
		
		return node;
	}
	
	/**
	 * Generate a String that represents the TreeNode as how it
	 * was decoded.
	 * 
	 * @return The Nova input equivalent to the node.
	 */
	public String toString()
	{
		String str = generateNovaInput();
		
		if (str == null)
		{
			return "[TreeNode: " + super.toString() + ']';
		}
		
		return str;
	}
}
