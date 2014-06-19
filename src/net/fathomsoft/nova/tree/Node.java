package net.fathomsoft.nova.tree;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.UnimplementedOperationException;
import net.fathomsoft.nova.tree.exceptionhandling.Try;
import net.fathomsoft.nova.tree.variables.LocalVariable;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;

/**
 * Class that is the parent of all Nodes on the Tree. Keeps the basic
 * information of where the statement was in the source, and where it was
 * output in the destination file. A Node can have any number of
 * children, however some of the extensions of Node have default
 * children at the start.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:11 PM
 * @version	v0.2.14 Jun 18, 2014 at 10:11:40 PM
 */
public abstract class Node
{
	private Location	locationIn;
	
	private Node		parent;
	
	private ArrayList<Node>	children;
	
	/**
	 * Create a new Node. Initializes the data.
	 * 
	 * @param temporaryParent The Node to act as the parent temporarily.
	 * @param locationIn The location of the Node in the source file.
	 */
	public Node(Node temporaryParent, Location locationIn)
	{
		children = new ArrayList<Node>();
		
		setTemporaryParent(temporaryParent);
		setLocationIn(locationIn);
	}
	
	/**
	 * Get the line number in which the Node was decoded at.
	 * 
	 * @return The line number in which the Node was decoded at.
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
	 * Get the location that the data in the Node is in the source
	 * file/text.
	 * 
	 * @return The Location instance holding the information.
	 */
	public Location getLocationIn()
	{
		return locationIn;
	}
	
	/**
	 * Set the location that the data in the Node is in the source
	 * file/text.
	 * 
	 * @param locationIn The Location instance holding the information.
	 */
	public void setLocationIn(Location locationIn)
	{
		this.locationIn = locationIn;
	}
	
	/**
	 * Get the parent of the specified Node. If the Node
	 * does not have a parent, null is returned.
	 * 
	 * @return The parent Node instance.
	 */
	public Node getParent()
	{
		return parent;
	}
	
	/**
	 * Set a temporary parent for the specified Node. When, if ever,
	 * the Node is formally added to a Node, the temporary parent
	 * will be removed.
	 * 
	 * @param parent The Node to act as the parent temporarily.
	 */
	public void setTemporaryParent(Node parent)
	{
		detach();
		
		this.parent = parent;
	}
	
	/**
	 * Get the nearest ancestor Node to the specific Node with
	 * the given Class type.
	 * 
	 * @param type The Class type of the Ancestor to search for.
	 * @return The nearest ancestor Node to the specific Node.
	 */
	public Node getAncestorOfType(Class<?> type)
	{
		return getAncestorOfType(type, false);
	}
	
	/**
	 * Get the nearest ancestor Node to the specific Node with
	 * the given Class type.
	 * 
	 * @param type The Class type of the Ancestor to search for.
	 * @param inclusive Whether or not to check the current Node.
	 * @return The nearest ancestor Node to the specific Node.
	 */
	public Node getAncestorOfType(Class<?> type, boolean inclusive)
	{
		Node node = null;
		
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
	 * Get the Scope instance of this Node if it even has
	 * a scope. If the Node does not have a Scope then this
	 * method call will return null.
	 * 
	 * @return The Scope instance, if it exists.
	 */
	public Scope getScope()
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
		return getScope() != null;
	}
	
	/**
	 * Set the Scope of the specified Node to the given
	 * Scope instance.
	 * 
	 * @param scope The Scope instance to use.
	 */
	public void setScope(Scope scope)
	{
		addChild(0, scope, this);
	}
	
	/**
	 * Add the given LocalVariable to the nearest scope.
	 * 
	 * @param node The LocalVariable to add.
	 */
	public void addToNearestScope(LocalVariable node)
	{
		getAncestorWithScope().addChild(node);
	}
	
	/**
	 * Get the nearest ancestor that contains a scope. (inclusive)
	 * 
	 * @return The nearest ancestor to the specified node that has a
	 * 		scope.
	 */
	public Node getAncestorWithScope()
	{
		Node node = this;
		
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
	public boolean isAncestorOf(Node node)
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
	public boolean isAncestorOf(Node node, boolean inclusive)
	{
		Node current = node;
		
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
	 * Get the number of children that the specified Node has.
	 * 
	 * @return The number of children that the specified Node has.
	 */
	public int getNumChildren()
	{
		return children.size();
	}
	
	/**
	 * Get the child Node at the specific index in the children
	 * ArrayList.
	 * 
	 * @param index The index to access the child node from.
	 * @return The child Node at the specific index.
	 */
	public Node getChild(int index)
	{
		return children.get(index);
	}
	
	/**
	 * Get whether or not the given Node is a child of the specified
	 * Node.
	 * 
	 * @param child The Node to check whether is a child or not.
	 * @return Whether or not the given Node is a child.
	 */
	public boolean containsChild(Node child)
	{
		return children.contains(child);
	}
	
	/**
	 * Add the specific Node under the current Node as a child.
	 * 
	 * @param node The node to set as the child node.
	 */
	public void addChild(Node node)
	{
		addChild(getNumChildren(), node);
	}
	
	/**
	 * Add the specific Node under the given 'toNode' Node as a
	 * child.
	 * 
	 * @param node The node to set as the child node.
	 */
	public void addChild(Node node, Node toNode)
	{
		addChild(toNode.getNumChildren(), node, toNode);
	}
	
	/**
	 * Add the specific Node under the current Node as a child.
	 * 
	 * @param index The index to add the node at.
	 * @param node The node to set as the child node.
	 */
	public void addChild(int index, Node node)
	{
		Scope scope = getScope();
		
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
	 * Add the specific Node under the given 'toNode' Node as a
	 * child.
	 * 
	 * @param index The index to add the node at.
	 * @param node The node to set as the child node.
	 * @param toNode The node to add the child to.
	 */
	public void addChild(int index, Node node, Node toNode)
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
	public boolean addChildBefore(Node node, Node toAdd)
	{
		return addChildAtOffset(node, toAdd, 0);
	}
	
	/**
	 * Add the given 'toAdd' node after the given 'node', if the node
	 * exists.
	 * 
	 * @return Whether or not the child was successfully added.
	 */
	public boolean addChildAfter(Node node, Node toAdd)
	{
		return addChildAtOffset(node, toAdd, 1);
	}
	
	/**
	 * Add the given 'toAdd' node relative to the given 'node' at the
	 * given offset index.
	 * 
	 * @param node The node to add the given 'toAdd' node relatively from.
	 * @param toAdd The Node to add as a child.
	 * @param offset The offset in which to add the child at relative to
	 * 		the given 'node' child.
	 * @return Whether or not the child was successfully added.
	 */
	private boolean addChildAtOffset(Node node, Node toAdd, int offset)
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
	 * Remove the specific Node from the current Node as a child.
	 * 
	 * @param index The index to remove the node from.
	 */
	public void removeChild(int index)
	{
		Node node = children.get(index);
		
		node.detach();
	}
	
	/**
	 * Remove the specific Node from the current Node as a child.
	 * 
	 * @param node The node to remove as the child node.
	 */
	public void removeChild(Node node)
	{
		children.remove(node);
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
		
		Node scope = null;
		
		if (parent.getNumChildren() > 0)
		{
			scope = parent.getScope();
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
	public void replace(Node old, Node replacement)
	{
		int index = children.indexOf(old);
		
		old.detach();
		
		addChild(index, replacement);
	}
	
	/**
	 * Give the specified node the given nodes children. This removes the
	 * children from the given oldParent node.
	 */
	public void inheritChildren(Node oldParent)
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
	public void inheritChildren(Node oldParent, boolean clone)
	{
		int index = children.size();
		
		for (int i = oldParent.getNumChildren() - 1; i >= 0; i--)
		{
			Node child = oldParent.getChild(i);
			
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
	 * Get whether or not the specified Node is used within an
	 * external context.
	 * 
	 * @return Whether or not the specified Node is used within an
	 * 		external context.
	 */
	public boolean isWithinExternalContext()
	{
		if (this instanceof ExternalType || this instanceof ExternalStatement)
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
	 * the data that is stored in the Node to the Java programming
	 * language syntax.
	 * 
	 * @return The Java syntax representation of the Node.
	 */
	public String generateJavaSource()
	{
		throw new UnimplementedOperationException("The Java implementation for this feature has not been implemented yet.");
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the Node to the C programming
	 * language header file syntax.
	 * 
	 * @return The C header file syntax representation of the Node.
	 */
	public final StringBuilder generateCHeader()
	{
		return generateCHeader(new StringBuilder());
	}
	
	/**
	 * Method that each Node can override. Returns a String that
	 * translates the data that is stored in the Node to the C
	 * programming language header file 'fragment' syntax.
	 * 
	 * @return The C header syntax representation of the Node.
	 */
	public final StringBuilder generateCHeaderFragment()
	{
		return generateCHeaderFragment(new StringBuilder());
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the Node to the C programming
	 * language source file syntax.
	 * 
	 * @return The C source syntax representation of the Node.
	 */
	public final StringBuilder generateCSource()
	{
		return generateCSource(new StringBuilder());
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the Node to the C programming
	 * language source file 'fragment' syntax.
	 * 
	 * @return The C source syntax representation of the Node.
	 */
	public final StringBuilder generateCSourceFragment()
	{
		return generateCSourceFragment(new StringBuilder());
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the Node to the C programming
	 * language header file syntax.
	 * 
	 * @return The C header file syntax representation of the Node.
	 */
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		throw new UnimplementedOperationException("The C Header implementation for this feature has not been implemented yet.");
	}
	
	/**
	 * Method that each Node can override. Returns a String that
	 * translates the data that is stored in the Node to the C
	 * programming language header file 'fragment' syntax.
	 * 
	 * @return The C header syntax representation of the Node.
	 */
	public StringBuilder generateCHeaderFragment(StringBuilder builder)
	{
		throw new UnimplementedOperationException("The C Header fragment implementation for this feature has not been implemented yet.");
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the Node to the C programming
	 * language source file syntax.
	 * 
	 * @return The C source syntax representation of the Node.
	 */
	public StringBuilder generateCSource(StringBuilder builder)
	{
		throw new UnimplementedOperationException("The C Source implementation for this feature has not been implemented yet.");
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the Node to the C programming
	 * language source file 'fragment' syntax.
	 * 
	 * @return The C source syntax representation of the Node.
	 */
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		throw new UnimplementedOperationException("The C Source fragment implementation for this feature has not been implemented yet.");
	}
	
	/**
	 * Generate the Nova syntax String that represents the Node.
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
	 * Generate the Nova syntax String that represents the Node.
	 * Essentially, this is the String that is decoded into the node.
	 * It is the input value from the .fat source file.
	 * 
	 * @param outputChildren Whether or not to output the children of the
	 * 		children of the Node as well.
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
	public Node validate(int phase)
	{
		return this;
	}
	
	/**
	 * Generate a String containing information of where the Node is
	 * located in reference to the source input files.
	 * 
	 * @return A String containing information of where the Node is
	 * 		located in reference to the source input files.
	 */
	public String getLocationInfo()
	{
		FileDeclaration file = getFileDeclaration();
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
	 * @return The parent Try, if there is one.
	 */
	public Try getParentTry()
	{
		Try node = (Try)getAncestorOfType(Try.class, true);
		
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
	 * Get the Program (The oldest parent) of this Node.
	 * 
	 * @return The Program of this Node.
	 */
	public Program getProgram()
	{
		if (parent != null)
		{
			return parent.getProgram();
		}
		
		return (Program)this;
	}
	
	/**
	 * Get the FileDeclaration of this Node, if it exists.
	 * 
	 * @return The FileDeclaration of this Node.
	 */
	public FileDeclaration getFileDeclaration()
	{
		Node current = this;
		
		while (current != null)
		{
			if (current instanceof FileDeclaration)
			{
				return (FileDeclaration)current;
			}
			
			current = current.parent;
		}
		
		return null;
	}
	
	/**
	 * Get the ClassDeclaration parent instance of the Node, if one exists.
	 * 
	 * @return The nearest ClassDeclaration instance that contains this node.
	 */
	public ClassDeclaration getClassDeclaration()
	{
		return (ClassDeclaration)getAncestorOfType(ClassDeclaration.class, true);
	}
	
	/**
	 * Get the Method parent instance of the Node, if one exists.
	 * 
	 * @return The nearest Method instance that contains this node.
	 */
	public final Method getMethod()
	{
		return (Method)getAncestorOfType(Method.class, true);
	}
	
	/**
	 * Get the compiler's controller. The controller is used for
	 * logging, error output, and other compiler options.
	 * 
	 * @return The compiler's controller instance.
	 */
	public Nova getController()
	{
		return getProgram().getController();
	}
	
	/**
	 * Return a new Node containing a copy of the values of the
	 * specified node, including clones of the children.
	 * 
	 * @param temporaryParent The Node to act as the parent
	 * 		temporarily.
	 */
	public abstract Node clone(Node temporaryParent, Location locationIn);
	
	/**
	 * Fill the given Node with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Node cloneTo(Node node)
	{
		if (locationIn != null)
		{
			Location locIn  = new Location(locationIn);
			locIn.setLineNumber(locationIn.getLineNumber());
			node.setLocationIn(locIn);
		}
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = children.get(i);
			
			node.children.add(child.clone(node, child.getLocationIn()));
		}
		
		return node;
	}
	
	/**
	 * Generate a String that represents the Node as how it
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
			return "[Node: " + super.toString() + ']';
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