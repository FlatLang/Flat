package net.fathomsoft.nova.tree;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.UnimplementedOperationException;
import net.fathomsoft.nova.tree.annotations.Annotatable;
import net.fathomsoft.nova.tree.annotations.Annotation;
import net.fathomsoft.nova.tree.exceptionhandling.Try;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Class that is the parent of all Nodes on the Tree. Keeps the basic
 * information of where the statement was in the source, and where it was
 * output in the destination file. A Node can have any number of
 * children, however some of the extensions of Node have default
 * children at the start.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:11 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public abstract class Node implements Listenable, Annotatable
{
	private Location		locationIn;
	
	private Node			parent;

	private ArrayList<Node>	children;
	private ArrayList<Annotation>	annotations;
	
	/**
	 * Create a new Node. Initializes the data.
	 * 
	 * @param temporaryParent The Node to act as the parent temporarily.
	 * @param locationIn The location of the Node in the source file.
	 */
	public Node(Node temporaryParent, Location locationIn)
	{
		children = new ArrayList<Node>(4);
		
		setTemporaryParent(temporaryParent);
		setLocationIn(locationIn);
	}
	
	/**
	 * Get the number of default children that the specified Node has
	 * right after it is decoded.
	 * 
	 * @return The number of default children that the specified Node has
	 * 		right after it is decoded.
	 */
	public int getNumDecodedChildren()
	{
		return getNumDefaultChildren();
	}
	
	/**
	 * Get the number of default children that the specified Node has
	 * right after it is created.
	 * 
	 * @return The number of default children that the specified Node has
	 * 		right after it is created.
	 */
	public int getNumDefaultChildren()
	{
		return 0;
	}
	
	public int getNumAnnotations()
	{
		return annotations != null ? annotations.size() : 0;
	}
	
	@Override
	public ArrayList<Annotation> getAnnotations()
	{
		return annotations;
	}

	@Override
	public void addAnnotation(Annotation annotation)
	{
		if (annotations == null)
		{
			annotations = new ArrayList<Annotation>();
		}
		
		annotations.add(annotation);
		
		annotation.setTemporaryParent(this);
	}
	
	public void removeAnnotation(Annotation annotation)
	{
		if (annotations != null)
		{
			annotations.remove(annotation);
		}
	}
	
	/**
	 * Get whether or not the specified Node has had no custom Nodes
	 * added to it. I.e. It is in its default state. I.e. It is in its
	 * post-initialization state.
	 * 
	 * @return Whether or not the specified Node has had no custom Nodes
	 * 		added to it.
	 */
	public boolean isEmpty()
	{
		if (getNumVisibleChildren() > 0)
		{
			return false;
		}
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			if (!getChild(i).isEmpty())
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Get whether or not the specified Node is waiting for a single
	 * statement to add as a child.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * if (true)
	 * {
	 * 	execute();
	 * }
	 * 
	 * // Scenario 2
	 * if (true) execute();</pre></blockquote>
	 * In scenario 2, before the execute() method call was decoded,
	 * the if statement node was pending a scope fragment.
	 * 
	 * @return Whether or not the specified Node is waiting for a
	 * 		single statement to add as a child.
	 */
	public boolean pendingScopeFragment(Node next)
	{
		return false;
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
		
		int lineNumber = parent.getLineNumber();
		
		Location loc   = getLocationIn();
		
		if (loc != null && loc.isValid())
		{
			lineNumber += loc.getLineNumber();
		}
		
		return lineNumber;
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
	
	public ScopeAncestor getParentScopeAncestor()
	{
		return (ScopeAncestor)getAncestorOfType(ScopeAncestor.class);
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
		Node node = getAncestor(inclusive);
		
		while (node != null && !type.isAssignableFrom(node.getClass()) && !node.getClass().equals(type))
		{
			node = node.getParent();
		}
		
		return node;
	}
	
	/**
	 * Get the next available ancestor that is of the {@link ScopeAncestor}
	 * Class type.
	 * 
	 * @param inclusive Whether or not to check the current Node.
	 * @return The next available ancestor that is a {@link ScopeAncestor}
	 */
	public ScopeAncestor getNextScopeAncestor(boolean inclusive)
	{
		Node node = getAncestor(inclusive);
		
		while (node != null && !(node instanceof ScopeAncestor))
		{
			node = node.getParent();
		}
		
		return (ScopeAncestor)node;
	}
	
	/**
	 * Get the ancestor of the Node. The ancestor will be the specified
	 * node if the call is inclusive. Otherwise it will return the parent
	 * of the specified Node.
	 * 
	 * @param inclusive Whether or not to return the specified Node.
	 * @return The specified Node if inclusive, otherwise the parent of
	 * 		the specified Node.
	 */
	private Node getAncestor(boolean inclusive)
	{
		if (inclusive)
		{
			return this;
		}
		
		return parent;
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
		addChild(scope, this);
	}
	
	/**
	 * Add the given LocalVariable to the nearest scope.
	 * 
	 * @param node The LocalVariable to add.
	 */
	public void addToNearestScope(LocalDeclaration node)
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
		
		while (node != null && !node.containsScope())
		{
			node = node.getParent();
		}
		
		return node;
	}
	
	public Node getAncestorWithScopeOrClass()
	{
		Node node = getAncestorWithScope();
		
		if (node == null)
		{
			node = getParentClass();
		}
		
		return node;
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
		Node current = node.getAncestor(inclusive);
		
		while (current != null && current != this)
		{
			current = current.parent;
		}
		
		return current == this;
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
	
	public Node getFirstChild()
	{
		if (getNumChildren() <= 0)
		{
			return null;
		}
		
		return getChild(0);
	}
	
	public Node getLastChild()
	{
		if (getNumChildren() <= 0)
		{
			return null;
		}
		
		return getChild(getNumChildren() - 1);
	}
	
	public Node getFirstVisibleChild()
	{
		if (getNumVisibleChildren() <= 0)
		{
			return null;
		}
		
		return getVisibleChild(0);
	}
	
	public Node getLastVisibleChild()
	{
		if (getNumVisibleChildren() <= 0)
		{
			return null;
		}
		
		return getVisibleChild(getNumVisibleChildren() - 1);
	}
	
	/**
	 * Get the number of children that have been added to the specified
	 * Node after the Node has been decoded.
	 * 
	 * @return The number of children added to the Node after decoding.
	 */
	public int getNumVisibleChildren()
	{
		return getNumChildren() - getNumDecodedChildren();
	}
	
	/**
	 * Get the child that has added at the specified index after the Node
	 * has been decoded.
	 * 
	 * @param index The index (starting at 0) of the child to get.
	 * @return The child at the given index.
	 */
	public Node getVisibleChild(int index)
	{
		return getChild(index + getNumDecodedChildren());
	}
	
	/**
	 * Remove the child that was added at the specified index after the
	 * Node has been decoded.
	 * 
	 * @param index index The index (starting at 0) of the child to
	 * 		remove.
	 */
	public void removeVisibleChild(int index)
	{
		removeChild(index + getNumDecodedChildren());
	}
	
	/**
	 * Get the child that was added before the given Node child. On a tree
	 * representation, the node to the left of this Node.
	 * 
	 * @param node The Node to get the child before.
	 * @return The Node that was added before the given node.
	 */
	public Node getChildBefore(Node node)
	{
		return children.get(children.indexOf(node) - 1);
	}
	
	/**
	 * Get the child that was added after the given Node child. On a tree
	 * representation, the node to the right of this Node.
	 * 
	 * @param node The Node to get the child after.
	 * @return The Node that was added after the given node.
	 */
	public Node getChildAfter(Node node)
	{
		return children.get(children.indexOf(node) + 1);
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
		return containsChild(child, false);
	}
	
	public boolean containsChild(Node child, boolean recursive)
	{
		if (children.contains(child))
		{
			return true;
		}
		
		if (recursive)
		{
			for (Node n : children)
			{
				if (n.containsChild(child, recursive))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void addReference(Node node)
	{
		addChild(getNumChildren(), node, this, false);
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
	 * @param toNode The node to add the child to.
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
			scope.addChild(node);
		}
		else
		{
			addChild(index, node, this);
		}
	}
	
	public void addVisibleChild(int index, Node node)
	{
		addChild(getNumChildren() - getNumVisibleChildren() + index, node, this);
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
		addChild(index, node, toNode, true);
	}
	
	public void addChild(int index, Node node, Node toNode, boolean detach)
	{
		if (detach)
		{
			// If the node already belongs to a parent, remove it from its old parent.
			node.detach();
			
			// Set this instance as the new parent.
			node.parent = this;

			toNode.children.add(index, node);
		}
		else
		{
			toNode.children.set(index, node);
		}
		
		node.onAdded(toNode);
	}
	
	/**
	 * Add the given 'toAdd' node before the given 'node', if the node
	 * exists.
	 * 
	 * @param node The node to add the child before.
	 * @param toAdd The child to add before the given node.
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
	 * @param node The Node to add the child after.
	 * @param toAdd The Node to add after the given node.
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
			if (containsScope())
			{
				return ((Node)getScope()).addChildAtOffset(node, toAdd, offset);
			}
			
			return false;
		}
		
		addChild(index + offset, toAdd);
		
		return true;
	}
	
	public void onAdded(Node parent)
	{
		Listenable.super.onAdded(parent);
	}
	
	public void onRemoved(Node parent)
	{
		
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
		node.detach();
	}
	
	/**
	 * Get whether or not the specified Node is being decoded at
	 * the current moment.
	 * 
	 * @return Whether or not the specified Node is being decoded at
	 * 		the current moment.
	 */
	public boolean isDecoding()
	{
		return !getParent().containsChild(this);
	}
	
	/**
	 * Detach the specified node from its parent.
	 */
	public void detach()
	{
		if (parent == null || isDecoding())
		{
			return;
		}
		
		Node from = parent;
		
		if (parent.getNumChildren() > 0 && !parent.containsChild(this) && parent.containsScope())
		{
			from = parent.getScope();
		}
		
		detach(from);
	}
	
	/**
	 * Detach the specified Node from the given Node.
	 * 
	 * @param fromNode The Node to detach the specified Node from.
	 */
	private void detach(Node fromNode)
	{
		fromNode.children.remove(this);
		
		parent = null;
		
		onRemoved(fromNode);
	}
	
	/**
	 * Replace the given old Node with the specified replacement.
	 * 
	 * @param old The Node to replace.
	 * @param replacement The replacement Node.
	 */
	public void replace(Node old, Node replacement)
	{
		replace(old, replacement, true);
	}
	
	public void replace(Node old, Node replacement, boolean detach)
	{
		int index = children.indexOf(old);
		
		if (detach)
		{
			old.detach();
		}
		
		if (replacement != null)
		{
			addChild(index, replacement, this, detach);
		}
	}
	
	/**
	 * Replace the specified Node with the given Node.
	 * 
	 * @param replacement The Node to replace the specified one with.
	 */
	public void replaceWith(Node replacement)
	{
		getParent().replace(this, replacement);
	}
	
	/**
	 * @see Node#slaughterEveryLastChild(int)
	 */
	public void slaughterEveryLastChild()
	{
		slaughterEveryLastChild(getNumChildren());
	}
	
	/**
	 * Kill off all of the specified Node's children and send them to
	 * the void. MAKE SURE THEY PAY
	 * 
	 * @param amount The amount of children to slaughter, starting at the
	 * 		first-born.
	 */
	public void slaughterEveryLastChild(int amount)
	{
		if (amount > getNumChildren())
		{
			amount = getNumChildren();
		}
		
		for (int i = 0; i < amount; i++)
		{
			getChild(0).detach(this);
		}
	}
	
	/**
	 * Give the specified node the given nodes children. This removes the
	 * children from the given oldParent node.
	 * 
	 * @param oldParent The parent to inherit the children from.
	 */
	public void inheritChildren(Node oldParent)
	{
		inheritChildren(oldParent, false);
	}
	
	/**
	 * Give the specified node the given nodes children. This removes the
	 * children from the given oldParent node.
	 * 
	 * @param oldParent The parent to inherit the children from.
	 * @param clone Whether or not to clone the children and not remove
	 * 		them from the previous owner.
	 */
	public void inheritChildren(Node oldParent, boolean clone)
	{
		int index = children.size();
		int end   = oldParent.getNumDefaultChildren();
		
		for (int i = oldParent.getNumChildren() - 1; i >= 0; i--)
		{
			Node child = oldParent.getChild(i);
			
			if (i >= end)
			{
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
			else
			{
				getChild(i).inheritChildren(child, clone);
			}
		}
	}
	
	/**
	 * Get whether or not the Node requires a special form of output.
	 * Examples are non-virtual method calls.
	 * 
	 * @return Whether or not the Node requires a special form of output.
	 */
	public boolean isSpecial()
	{
		return false;
	}
	
	/**
	 * Iterate through the words of the statement. A word is just anything
	 * that is surrounded by whitespace. e.g. In the statement:
	 * "public void test() { }" the words consist of:
	 * [ public, void, test(), {, } ]
	 * 
	 * @param statement The statement to iterate the words from.
	 * @return The given ExtraData instance.
	 */
	public final ExtraData iterateWords(String statement, boolean require)
	{
		return iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES, null, require);
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
	 * @return The given ExtraData instance.
	 */
	public final ExtraData iterateWords(String statement, ExtraData extra, boolean require)
	{
		return iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES, extra, require);
	}
	
	/**
	 * Iterate through each of the groupings of the given Pattern on the
	 * statement. In the default case, it will search for the boundaries
	 * of words and iterate through all of them.
	 * 
	 * @param statement The statement to search through.
	 * @param pattern The Pattern to search with.
	 * @return The given ExtraData instance.
	 */
	public final ExtraData iterateWords(String statement, Pattern pattern, boolean require)
	{
		return iterateWords(statement, pattern, null, require);
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
	 * @return The given ExtraData instance.
	 */
	public ExtraData iterateWords(String statement, Pattern pattern, ExtraData extra, boolean require)
	{
		// Pattern used to find word boundaries.
		Matcher matcher = pattern.matcher(statement);
		
		if (extra == null)
		{
			extra = new ExtraData();
		}
		
		extra.statement = statement;
		extra.require   = require;
		
		findWords(statement, matcher, extra);
		
		extra.wordNumber = 0;
		
		while (extra.wordNumber < extra.bounds.size())
		{
			String word  = extra.words.get(extra.wordNumber);
			Bounds bound = extra.bounds.get(extra.wordNumber);
			
			String leftDelim  = extra.delims.get(extra.wordNumber);
			String rightDelim = extra.delims.get(extra.wordNumber + 1);
			
			interactWord(word, bound, leftDelim, rightDelim, extra);
			
			extra.wordNumber++;
		}
		
		return extra;
	}
	
	/**
	 * Find the words, bounds, and delimiters in the given statement.
	 * 
	 * @param statement The statement to find the information from.
	 * @param matcher The matcher searching through the statement.
	 * @param extra The ExtraData containing the lists that will acquire the
	 * 		words, delimiters, and bounds.
	 */
	private void findWords(String statement, Matcher matcher, ExtraData extra)
	{
		int index    = 0;
		int oldIndex = 0;
		int lastValidIndex = 0;
		
		for (boolean end = false; matcher.find(); end = !end)
		{
			if (end)
			{
				Bounds bounds = new Bounds(oldIndex, index);
				String delim = "";
				
				trimBounds(bounds, extra);
				
				if (bounds.isValid())
				{
					delim = bounds.extractString(statement);
					delim = StringUtils.trimSurroundingWhitespace(delim);
					
					lastValidIndex = matcher.start();
				}
				
				oldIndex = matcher.start();
				
				bounds = new Bounds(index, oldIndex);
				
				trimBounds(bounds, extra);
				
				if (bounds.isValid())
				{
					extra.delims.add(delim);
					extra.bounds.add(bounds);
					extra.words.add(statement.substring(index, oldIndex));
				}
				else
				{
					lastValidIndex = matcher.start();
				}
			}
			else
			{
				index = matcher.start();
			}
		}
		
		// Don't forget the last delimiter.
		extra.delims.add(statement.substring(lastValidIndex));
	}
	
	private void trimBounds(Bounds bounds, ExtraData extra)
	{
		for (int i = 0; i < extra.skipBounds.length; i++)
		{
			Bounds skip = extra.skipBounds[i];
			
			boolean end = bounds.getEnd() > skip.getStart() && bounds.getEnd() <= skip.getEnd();
			
			if (bounds.getStart() < skip.getEnd() && bounds.getStart() >= skip.getStart())
			{
				if (end)
				{
					bounds.setInvalid();
					return;
				}
				
				bounds.setStart(skip.getEnd());
			}
			else if (end)
			{
				bounds.setEnd(skip.getStart());
			}
		}
	}
	
	/**
	 * Method that is to be overridden. Whenever the iterateWords(String)
	 * method is called, this method will be called with the specific word
	 * and the number (order) the word came in the statement.
	 * 
	 * @param word The word that was found.
	 * @param bounds The bounds of the word that was found.
	 * @param leftDelimiter The text that is between the previous word and
	 * 		the current word.
	 * @param rightDelimiter The text that is between the current word and
	 * 		the next word.
	 * @param extra The extra data that may or may not be needed for the
	 * 		interactWord() methods.
	 */
	public void interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		
	}
	
	/**
	 * Get whether or not the specified Node is used within a
	 * static context.
	 * 
	 * @return Whether or not the specified Node is used within a
	 * 		static context.
	 */
	public boolean isWithinStaticContext()
	{
		return getParentMethod() == null || !getParentMethod().isInstance();
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
		if (this instanceof ExternalType)
		{
			return true;
		}
		else if (parent != null)
		{
			return parent.isWithinExternalContext();
		}
		
		return false;
	}
	
	public Node getLastAncestorOfType(Class<?>[] classes, boolean opposite)
	{
		Node prev    = null;
		Node current = this;
		
		while (SyntaxUtils.checkTypes(classes, current.getClass()) != opposite)
		{
			prev    = current;
			current = current.parent;
		}
		
		return prev;
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
	 * @param builder The StringBuilder to append the data to.
	 * @return The C header file syntax representation of the Node.
	 */
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		return generateCHeaderFragment(builder).append('\n');
		//throw new UnimplementedOperationException("The C Header implementation for " + this.getClass().getName() + " has not been implemented yet.");
	}
	
	/**
	 * Method that each Node can override. Returns a String that
	 * translates the data that is stored in the Node to the C
	 * programming language header file 'fragment' syntax.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The C header syntax representation of the Node.
	 */
	public StringBuilder generateCHeaderFragment(StringBuilder builder)
	{
		throw new UnimplementedOperationException("The C Header fragment implementation for " + this.getClass().getName() + " has not been implemented yet.");
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the Node to the C programming
	 * language source file syntax.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The C source syntax representation of the Node.
	 */
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder).append('\n');
		//throw new UnimplementedOperationException("The C Source implementation for " + this.getClass().getName() + " has not been implemented yet.");
	}
	
	/**
	 * Method that each Node overrides. Returns a String that translates
	 * the data that is stored in the Node to the C programming
	 * language source file 'fragment' syntax.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The C source syntax representation of the Node.
	 */
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		throw new UnimplementedOperationException("The C Source fragment implementation for " + this.getClass().getName() + " has not been implemented yet.");
	}
	
	/**
	 * Generate the Nova syntax String that represents the Node.
	 * Essentially, this is the String that is decoded into the node.
	 * It is the input value from the .fat source file.
	 * 
	 * @return The appended StringBuilder that represents the input String
	 * 		in Nova syntax.
	 */
	public final StringBuilder generateNovaInput()
	{
		return generateNovaInput(true);
	}
	
	/**
	 * Generate the Nova syntax String that represents the Node.
	 * Essentially, this is the String that is decoded into the node.
	 * It is the input value from the .nova source file.
	 * 
	 * @param outputChildren Whether or not to output the children of the
	 * 		children of the Node as well.
	 * @return The appended StringBuilder that represents the input String
	 * 		in Nova syntax.
	 */
	public final StringBuilder generateNovaInput(boolean outputChildren)
	{
		return generateNovaInput(new StringBuilder(), outputChildren);
	}
	
	/**
	 * Generate the Nova syntax String that represents the Node.
	 * Essentially, this is the String that is decoded into the node.
	 * It is the input value from the .nova source file.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The appended StringBuilder that represents the input String
	 * 		in Nova syntax.
	 */
	public final StringBuilder generateNovaInput(StringBuilder builder)
	{
		return generateNovaInput(builder, true);
	}
	
	/**
	 * Generate the Nova syntax String that represents the Node.
	 * Essentially, this is the String that is decoded into the node.
	 * It is the input value from the .nova source file.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @param outputChildren Whether or not to output the children of the
	 * 		children of the Node as well.
	 * @return The appended StringBuilder that represents the input String
	 * 		in Nova syntax.
	 */
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		throw new UnimplementedOperationException("The Nova input implementation for this feature has not been implemented yet.");
	}
	
	public boolean onAfterDecoded()
	{
		for (Node n : children)
		{
			if (!n.onAfterDecoded())
			{
				return false;
			}
		}
		
		return true;
	}
	
	public boolean onNextStatementDecoded(Node next)
	{
		for (Node n : children)
		{
			if (!n.onNextStatementDecoded(next))
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Validate the node to make last minute changes or error checking.
	 * 
	 * @param phase The phase that the node is being validated in.
	 * @return The Node to continue the validation off of.
	 */
	public ValidationResult validate(int phase)
	{
		if (getNumAnnotations() > 0)
		{
			for (int i = annotations.size() - 1; i >= 0; i--)
			{
				ValidationResult result = annotations.get(i).validate(phase);
				
				if (result.skipValidation())
				{
					return result;
				}
			}
		}
		
		return new ValidationResult(this);
	}
	
	/**
	 * Rollback any changes to external resources that the specified Node
	 * has made, if the Node is not going to be used.
	 */
	public void rollback()
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			getChild(i).rollback();
		}
	}
	
	public boolean isValid()
	{
		return parent != null;
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
		Location        loc  = getLocationIn();
		
		String info = "";
		
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
		return getFileDeclaration(false);
	}
	
	/**
	 * Get the FileDeclaration of this Node, if it exists.
	 * 
	 * @param inclusive Whether or not to check the specified Node.
	 * @return The FileDeclaration of this Node.
	 */
	public FileDeclaration getFileDeclaration(boolean inclusive)
	{
		Node current = getAncestor(inclusive);
		
		while (current != null && current instanceof FileDeclaration == false)
		{
			current = current.parent;
		}
		
		return (FileDeclaration)current;
	}
	
	/**
	 * Get the ClassDeclaration parent instance of the Node, if one exists.
	 * 
	 * @return The nearest ClassDeclaration instance that contains this node.
	 */
	public ClassDeclaration getParentClass()
	{
		return getParentClass(false);
	}
	
	/**
	 * Get the ClassDeclaration parent instance of the Node, if one exists.
	 * 
	 * @param inclusive Whether or not to inclusively check the specified
	 * 		Node to see if it is a ClassDeclaration.
	 * @return The nearest ClassDeclaration instance that contains this node.
	 */
	public ClassDeclaration getParentClass(boolean inclusive)
	{
		return (ClassDeclaration)getAncestorOfType(ClassDeclaration.class, inclusive);
	}
	
	/**
	 * Get the Method parent instance of the Node, if one exists.
	 * 
	 * @return The nearest Method instance that contains this node.
	 */
	public final BodyMethodDeclaration getParentMethod()
	{
		return getParentMethod(false);
	}
	
	/**
	 * Get the Method parent instance of the Node, if one exists.
	 * 
	 * @param inclusive Whether or not to inclusively check the specified
	 * 		Node to see if it is a MethodDeclaration.
	 * @return The nearest Method instance that contains this node.
	 */
	public final BodyMethodDeclaration getParentMethod(boolean inclusive)
	{
		return (BodyMethodDeclaration)getAncestorOfType(BodyMethodDeclaration.class, inclusive);
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
	 * Decode a scope fragment for the Node, if needed.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * if (true)
	 * {
	 * 	execute();
	 * }
	 * 
	 * // Scenario 2
	 * if (true) execute();</pre></blockquote>
	 * In scenario 2, before the execute() method call was the scope
	 * fragment.
	 * 
	 * @param statement The statement containing the scope fragment.
	 * @param bounds The bounds of the Node's arguments.
	 * @return Whether or not the scope fragment decoded correctly.
	 */
	public boolean decodeScopeFragment(String statement, Bounds bounds)
	{
		int nextChar = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getEnd() + 1);
		
		if (nextChar <= 0)
		{
			return true;
		}
		
		String fragment = statement.substring(nextChar);
		
		Location location = new Location(getLocationIn());
		
		Node node = SyntaxTree.decodeScopeContents(this, fragment, location);
		
		if (node == null)
		{
			return false;
		}
		
		addChild(node);
		
		return true;
	}
	
	/**
	 * Get the Node that is highest on the tree, up until a scope is hit.
	 * (The Node that is returned will have a scope as a parent)
	 * 
	 * @return The Node that is the highest on the tree up until a scope
	 * 		is found.
	 */
	public Node getBaseNode()
	{
		Node prev    = this;
		Node current = getParent();
		
		while (!current.containsScope() && !(current instanceof Scope))
		{
			prev    = current;
			current = current.getParent();
		}
		
		if (current.containsChild(this) && !(current instanceof Scope))
		{
			return current;
		}
		
		return prev;
	}
	
	/**
	 * Return a new Node containing a copy of the values of the
	 * specified node, including clones of the children.
	 * 
	 * @param temporaryParent The Node to act as the parent
	 * 		temporarily.
	 * @param locationIn The Location instance holding the information.
	 * @return A clone of the specified Node.
	 */
	public final Node clone(Node temporaryParent, Location locationIn)
	{
		return clone(temporaryParent, locationIn, true); 
	}
	
	/**
	 * Return a new Node containing a copy of the values of the
	 * specified node, including clones of the children.
	 * 
	 * @param temporaryParent The Node to act as the parent
	 * 		temporarily.
	 * @param locationIn The Location instance holding the information.
	 * @param cloneChildren Whether or not to clone the children of the
	 * 		Node as well.
	 * @return A clone of the specified Node.
	 */
	public abstract Node clone(Node temporaryParent, Location locationIn, boolean cloneChildren);
	
	/**
	 * Fill the given {@link Node} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Node cloneTo(Node node)
	{
		throw new UnsupportedOperationException("Class " + this.getClass().getName() + " must implement cloneTo(Node, boolean)");
		
//		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Node} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @param cloneChildren Whether or not to clone the children of the
	 * 		Node as well.
	 * @return The cloned node.
	 */
	public Node cloneTo(Node node, boolean cloneChildren)
	{
		if (getNumDefaultChildren() > 0)
		{
			node.slaughterEveryLastChild(getNumDefaultChildren());
		}
		
		if (locationIn != null)
		{
			Location locIn = new Location(locationIn);
			locIn.setLineNumber(locationIn.getLineNumber());
			node.setLocationIn(locIn);
		}
		
		if (cloneChildren)
		{
			for (int i = getNumChildren() - 1; i >= 0; i--)
			{
				Node child = children.get(i);
				
				node.children.add(0, child.clone(node, child.getLocationIn()));
			}
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
			return generateNovaInput().toString();
		}
		catch (UnimplementedOperationException e)
		{
			return "[Node: " + super.toString() + ']';
		}
	}
	
	public static Node newEmptyNode()
	{
		return new Node(null, null)
		{
			@Override
			public Node clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
			{
				return null;
			}
		};
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
		public  boolean require, checkType;
		
		private int		wordNumber;
		
		private Bounds	skipBounds[];
		
		private ArrayList<Bounds> bounds;
		private ArrayList<String> words;
		private ArrayList<String> delims;
		
		public String	error;
		public String	statement; 
		
		public ExtraData()
		{
			skipBounds = new Bounds[0];
			
			bounds = new ArrayList<Bounds>();
			words  = new ArrayList<String>();
			delims = new ArrayList<String>();
		}
		
		public int getWordNumber()
		{
			return wordNumber;
		}
		
		public boolean isLastWord()
		{
			return wordNumber == words.size() - 1;
		}
		
		public boolean isFirstWord()
		{
			return wordNumber == 0;
		}
		
		public Bounds getCurrentWordBounds()
		{
			return bounds.get(wordNumber);
		}
		
		public Bounds getNextWordBounds()
		{
			if (isLastWord())
			{
				return null;
			}
			
			return bounds.get(wordNumber + 1);
		}
		
		public Bounds getPreviousWordBounds()
		{
			if (isFirstWord())
			{
				return null;
			}
			
			return bounds.get(wordNumber - 1);
		}
		
		public String getNextWord()
		{
			if (isLastWord())
			{
				return null;
			}
			
			return words.get(wordNumber + 1);
		}
		
		public String getPreviousWord()
		{
			if (isFirstWord())
			{
				return null;
			}
			
			return words.get(wordNumber - 1);
		}
		
		public boolean isSkipBoundsNext()
		{
			return getRightAdjacentSkipBounds() != null;
		}
		
		public Bounds getRightAdjacentSkipBounds()
		{
			for (Bounds skip : skipBounds)
			{
				if (skip.getStart() >= getCurrentWordBounds().getEnd() &&
						(isLastWord() || skip.getEnd() <= getNextWordBounds().getStart()))
				{
					return skip;
				}
			}
			
			return null;
		}
		
		public Bounds getLeftAdjacentSkipBounds()
		{
			for (Bounds skip : skipBounds)
			{
				if (skip.getEnd() <= getCurrentWordBounds().getStart() &&
						(isFirstWord() || skip.getStart() >= getPreviousWordBounds().getEnd()))
				{
					return skip;
				}
			}
			
			return null;
		}
		
		public int getNumSkipBounds()
		{
			return skipBounds.length;
		}
		
		public Bounds getSkipBounds(int index)
		{
			return skipBounds[index];
		}
		
		public boolean containsSkipBounds()
		{
			return getNumSkipBounds() > 0;
		}
		
		public void addSkipBounds(Bounds bounds)
		{
			Bounds temp[] = new Bounds[skipBounds.length + 1];
			
			System.arraycopy(skipBounds, 0, temp, 0, skipBounds.length);
			
			temp[skipBounds.length] = bounds;
			
			skipBounds = temp;
		}
	}
	
	/**
	 * Test the Node class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}