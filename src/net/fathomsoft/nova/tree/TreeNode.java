package net.fathomsoft.nova.tree;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.UnimplementedOperationException;
import net.fathomsoft.nova.tree.exceptionhandling.TryNode;
import net.fathomsoft.nova.tree.variables.LocalVariableNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;

/**
 * Class that is the parent of all Nodes on the Tree. Keeps the basic
 * information of where the statement was in the source, and where it was
 * output in the destination file. A TreeNode can have any number of
 * children, however some of the extensions of TreeNode have default
 * children at the start.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:11 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public abstract class TreeNode
{
	private Location			locationIn;
	
	private TreeNode			parent;
	
	private ArrayList<TreeNode>	children;
	
	/**
	 * Create a new TreeNode. Initializes the data.
	 * 
	 * @param temporaryParent The TreeNode to act as the parent temporarily.
	 * @param locationIn The location of the Node in the source file.
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
		if (parent == null)
		{
			return 0;
		}
		else
		{
			int lineNumber = parent.getLineNumber();
			
			Location loc = getLocationIn();
			
			if (loc != null && loc.isValid())
			{
				lineNumber += loc.getLineNumber();
			}
			
			return lineNumber;
		}
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
		TreeNode node = null;
		
		if (inclusive)
		{
			node = this;
		}
		else
		{
			node = parent;
		}
		
		while (node != null && !type.isAssignableFrom(node.getClass()) && !node.getClass().equals(type))
		{
			node = node.getParent();
		}
		
		return node;
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
		return instanceOf(new Class<?>[] { clazz });
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
			if (c.isAssignableFrom(getClass()))
			{
				return true;
			}
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
	 * Set the ScopeNode of the specified TreeNode to the given
	 * ScopeNode instance.
	 * 
	 * @param scope The ScopeNode instance to use.
	 */
	public void setScopeNode(ScopeNode scope)
	{
		addChild(0, scope, this);
	}
	
	/**
	 * Add the given LocalVariableNode to the nearest scope.
	 * 
	 * @param node The LocalVariableNode to add.
	 */
	public void addToNearestScope(LocalVariableNode node)
	{
		getAncestorWithScope().addChild(node);
	}
	
	/**
	 * Get the nearest ancestor that contains a scope. (inclusive)
	 * 
	 * @return The nearest ancestor to the specified node that has a
	 * 		scope.
	 */
	public TreeNode getAncestorWithScope()
	{
		TreeNode node = this;
		
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
	 * Get the number of children that the specified TreeNode has.
	 * 
	 * @return The number of children that the specified TreeNode has.
	 */
	public int getNumChildren()
	{
		return children.size();
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
	 * Get whether or not the given TreeNode is a child of the specified
	 * TreeNode.
	 * 
	 * @param child The TreeNode to check whether is a child or not.
	 * @return Whether or not the given TreeNode is a child.
	 */
	public boolean containsChild(TreeNode child)
	{
		return children.contains(child);
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
	 * Add the specific TreeNode under the given 'toNode' TreeNode as a
	 * child.
	 * 
	 * @param node The node to set as the child node.
	 */
	public void addChild(TreeNode node, TreeNode toNode)
	{
		addChild(toNode.children.size(), node, toNode);
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
		ScopeNode scope = getScopeNode();
		
		if (scope != null)
		{
			addChild(node, scope);
		}
		else
		{
			addChild(index, node, this);
		}
	}
	
	/**
	 * Add the specific TreeNode under the given 'toNode' TreeNode as a
	 * child.
	 * 
	 * @param index The index to add the node at.
	 * @param node The node to set as the child node.
	 * @param toNode The node to add the child to.
	 */
	public void addChild(int index, TreeNode node, TreeNode toNode)
	{
		// If the node already belongs to a parent, remove it from its old parent.
		node.detach();
		
		toNode.children.add(index, node);
		
		// Set this instance as the new parent.
		node.parent = this;
	}
	
	/**
	 * Add the given 'toAdd' node before the given 'node', if the node
	 * exists.
	 * 
	 * @return Whether or not the child was successfully added.
	 */
	public boolean addChildBefore(TreeNode node, TreeNode toAdd)
	{
		return addChildAtOffset(node, toAdd, 0);
	}
	
	/**
	 * Add the given 'toAdd' node after the given 'node', if the node
	 * exists.
	 * 
	 * @return Whether or not the child was successfully added.
	 */
	public boolean addChildAfter(TreeNode node, TreeNode toAdd)
	{
		return addChildAtOffset(node, toAdd, 1);
	}
	
	/**
	 * Add the given 'toAdd' node relative to the given 'node' at the
	 * given offset index.
	 * 
	 * @param node The node to add the given 'toAdd' node relatively from.
	 * @param toAdd The TreeNode to add as a child.
	 * @param offset The offset in which to add the child at relative to
	 * 		the given 'node' child.
	 * @return Whether or not the child was successfully added.
	 */
	private boolean addChildAtOffset(TreeNode node, TreeNode toAdd, int offset)
	{
		int index = children.indexOf(node);
		
		if (index < 0)
		{
			return false;
		}
		
		addChild(index + offset, node);
		
		return true;
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
		
		TreeNode scope = null;
		
		if (parent.getNumChildren() > 0)
		{
			scope = parent.getScopeNode();
		}
		if (scope == null)
		{
			parent.children.remove(this);
		}
		else
		{
			scope.children.remove(this);
		}
		
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
		iterateWords(statement, Patterns.WORD_BOUNDARIES, null);
	}
	
	/**
	 * Iterate through the words of the statement. A word is just anything
	 * that is surrounded by whitespace. e.g. In the statement:
	 * "public void test() { }" the words consist of:
	 * [ public, void, test(), {, } ]
	 * 
	 * @param statement The statement to iterate the words from.
	 * @param extra The extra data that may or may not be needed for the
	 * 		interactWord() methods.
	 */
	public void iterateWords(String statement, ExtraData extra)
	{
		iterateWords(statement, Patterns.WORD_BOUNDARIES, extra);
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
		iterateWords(statement, pattern, null);
	}
	
	/**
	 * Iterate through each of the groupings of the given Pattern on the
	 * statement. In the default case, it will search for the boundaries
	 * of words and iterate through all of them.
	 * 
	 * @param statement The statement to search through.
	 * @param pattern The Pattern to search with.
	 * @param extra The extra data that may or may not be needed for the
	 * 		interactWord() methods.
	 */
	public void iterateWords(String statement, Pattern pattern, ExtraData extra)
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
			
			interactWord(word, i, bound, bounds.size(), delims.get(i), delims.get(i + 1), extra);
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
	 * @param leftDelimiter The text that is between the previous word and
	 * 		the current word.
	 * @param rightDelimiter The text that is between the current word and
	 * 		the next word.
	 * @param extra The extra data that may or may not be needed for the
	 * 		interactWord() methods.
	 */
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData extra)
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
		throw new UnimplementedOperationException("The Java implementation for this feature has not been implemented yet.");
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
		throw new UnimplementedOperationException("The C Header implementation for this feature has not been implemented yet.");
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
		throw new UnimplementedOperationException("The C Header fragment implementation for this feature has not been implemented yet.");
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
		throw new UnimplementedOperationException("The C Source implementation for this feature has not been implemented yet.");
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
		throw new UnimplementedOperationException("The C Source fragment implementation for this feature has not been implemented yet.");
	}
	
	/**
	 * Generate the Nova syntax String that represents the TreeNode.
	 * Essentially, this is the String that is decoded into the node.
	 * It is the input value from the .fat source file.
	 * 
	 * @return A String that represents the input String in Nova syntax.
	 */
	public final String generateNovaInput()
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
		throw new UnimplementedOperationException("The Nova input implementation for this feature has not been implemented yet.");
	}
	
	/**
	 * Validate the node to make last minute changes or error checking.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	public TreeNode validate(int phase)
	{
		return this;
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
	 * Get the ClassNode parent instance of the TreeNode, if one exists.
	 * 
	 * @return The nearest ClassNode instance that contains this node.
	 */
	public ClassNode getClassNode()
	{
		return (ClassNode)getAncestorOfType(ClassNode.class, true);
	}
	
	/**
	 * Get the MethodNode parent instance of the TreeNode, if one exists.
	 * 
	 * @return The nearest MethodNode instance that contains this node.
	 */
	public final MethodNode getMethodNode()
	{
		return (MethodNode)getAncestorOfType(MethodNode.class, true);
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
			locIn.setLineNumber(locationIn.getLineNumber());
			node.setLocationIn(locIn);
		}
		
		for (int i = 0; i < children.size(); i++)
		{
			TreeNode child = children.get(i);
			
			node.children.add(child.clone(node, child.getLocationIn()));
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
		try
		{
			return generateNovaInput();
		}
		catch (UnimplementedOperationException e)
		{
			return "[TreeNode: " + super.toString() + ']';
		}
	}
	
	/**
	 * Class used to pass data to the interactWord() methods.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.13 Jun 11, 2014 at 8:29:57 PM
	 * @version	v0.2.13 Jun 11, 2014 at 8:29:57 PM
	 */
	public static class ExtraData
	{
		
	}
}