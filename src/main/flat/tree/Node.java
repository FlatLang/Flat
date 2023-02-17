package flat.tree;

import flat.Flat;
import flat.TestContext;
import flat.ValidationResult;
import flat.error.UnimplementedOperationException;
import flat.tree.annotations.*;
import flat.tree.exceptionhandling.Try;
import flat.tree.variables.*;
import flat.util.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

/**
 * Class that is the parent of all Nodes on the Tree. Keeps the basic
 * information of where the statement was in the source, and where it was
 * output in the destination file. A Node can have any number of
 * children, however some of the extensions of Node have default
 * children at the start.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:00:11 PM
 * @version v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public abstract class Node implements Listenable, Annotatable {
    public long id = ++idcount;
    public static long idcount = 0;

    private Location locationIn;

    public Node parent;

    private ArrayList<Node> children;
    private ArrayList<Annotation> annotations;

    private HashMap<String, Object> properties;

    private FileDeclaration originalFile;

    /**
     * Create a new Node. Initializes the data.
     *
     * @param temporaryParent The Node to act as the parent temporarily.
     * @param locationIn      The location of the Node in the source file.
     */
    public Node(Node temporaryParent, Location locationIn) {
        children = new ArrayList<Node>(4);

        setTemporaryParent(temporaryParent);
        setLocationIn(locationIn);
    }

    public Object getProperty(String name) {
        return properties != null ? properties.get(name) : null;
    }

    public void setProperty(String name, Object value) {
        if (properties == null) {
            properties = new HashMap<>();
        }

        properties.put(name, value);
    }

    public boolean containsProperty(String name) {
        return getProperty(name) != null;
    }

    public boolean isPropertyTrue(String name) {
        return containsProperty(name) && (Boolean) getProperty(name);
    }

    /**
     * Get the number of default children that the specified Node has
     * right after it is decoded.
     *
     * @return The number of default children that the specified Node has
     * right after it is decoded.
     */
    public int getNumDecodedChildren() {
        return getNumDefaultChildren();
    }

    public void prePreGenerationValidation() {
        for (Node child : children) {
            child.prePreGenerationValidation();
        }
    }

    /**
     * Get the number of default children that the specified Node has
     * right after it is created.
     *
     * @return The number of default children that the specified Node has
     * right after it is created.
     */
    public int getNumDefaultChildren() {
        return 0;
    }

    public int getNumAnnotations() {
        return annotations != null ? annotations.size() : 0;
    }

    @Override
    public ArrayList<Annotation> getAnnotations() {
        return annotations;
    }

    @Override
    public void addAnnotation(Annotation annotation) {
        if (annotations == null) {
            annotations = new ArrayList<>();
        }

        if (annotation.getAnnotations() != null) {
            for (Annotation a : annotation.getAnnotations().toArray(new Annotation[0])) {
                addAnnotation(a);

                //a.validate(getProgram().getPhase());
            }
        }
        if (annotation instanceof TargetAnnotation || annotation instanceof TargetRuntimeAnnotation) {
            return;
        }

        if (annotation.getParent() != null && annotation.getParent().annotations != null) {
            annotation.getParent().annotations.remove(annotation);
        }

        getFileDeclaration().removePendingAnnotation(annotation);

        annotations.add(annotation);

        annotation.setTemporaryParent(this);
        annotation.onAdded(this);
    }

    public void removeAnnotation(Annotation annotation) {
        if (annotations != null) {
            annotations.remove(annotation);
        }
    }

    /**
     * Get whether or not the specified Node has had no custom Nodes
     * added to it. I.e. It is in its default state. I.e. It is in its
     * post-initialization state.
     *
     * @return Whether or not the specified Node has had no custom Nodes
     * added to it.
     */
    public boolean isEmpty() {
        if (getNumVisibleChildren() > 0) {
            return false;
        }

        for (int i = 0; i < getNumChildren(); i++) {
            if (!getChild(i).isEmpty()) {
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
     * single statement to add as a child.
     */
    public boolean pendingScopeFragment(Node next) {
        return false;
    }

    public Node getDecodedParent() {
        return this;
    }

    /**
     * Get the line number in which the Node was decoded at.
     *
     * @return The line number in which the Node was decoded at.
     */
    public int getLineNumber() {
        if (parent == null) {
            return 0;
        }

        int lineNumber = parent.getLineNumber();

        Location loc = getLocationIn();

        if (loc != null && loc.isValid()) {
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
    public Location getLocationIn() {
        return locationIn;
    }

    /**
     * Set the location that the data in the Node is in the source
     * file/text.
     *
     * @param locationIn The Location instance holding the information.
     */
    public void setLocationIn(Location locationIn) {
        this.locationIn = locationIn;
    }

    /**
     * Get the parent of the specified Node. If the Node
     * does not have a parent, null is returned.
     *
     * @return The parent Node instance.
     */
    public Node getParent() {
        return parent;
    }

    public final VariableDeclaration searchVariable(Node parent, Scope scope, String name) {
        return searchVariable(parent, scope, name, true);
    }

    public VariableDeclaration searchVariable(Node parent, Scope scope, String name, boolean checkAncestors) {
        Scope childScope = getScope();

        if (childScope != null) {
            VariableDeclarationList variables = childScope.getVariableList();
            VariableDeclaration variable = variables.getVariable(name, scope);

            if (variable != null) {
                return variable;
            }
        }

        if (checkAncestors) {
            Node scopeNode = getParent().getAncestorWithScope();

            if (scopeNode != null) {
                return scopeNode.searchVariable(parent, scope, name, checkAncestors);
            }
        }

        return null;
    }

    public ScopeAncestor getParentScopeAncestor() {
        return (ScopeAncestor) getAncestorOfType(ScopeAncestor.class);
    }

    /**
     * Set a temporary parent for the specified Node. When, if ever,
     * the Node is formally added to a Node, the temporary parent
     * will be removed.
     *
     * @param parent The Node to act as the parent temporarily.
     */
    public void setTemporaryParent(Node parent) {
        if (this.parent != parent) {
            detach();

            this.parent = parent;
        }
    }

    /**
     * Get the nearest ancestor Node to the specific Node with
     * the given Class type.
     *
     * @param type The Class type of the Ancestor to search for.
     * @return The nearest ancestor Node to the specific Node.
     */
    public Node getAncestorOfType(Class<?> type) {
        return getAncestorOfType(type, false);
    }

    /**
     * Get the nearest ancestor Node to the specific Node with
     * the given Class type.
     *
     * @param type      The Class type of the Ancestor to search for.
     * @param inclusive Whether or not to check the current Node.
     * @return The nearest ancestor Node to the specific Node.
     */
    public Node getAncestorOfType(Class<?> type, boolean inclusive) {
        Node node = getAncestor(inclusive);

        while (node != null && !type.isAssignableFrom(node.getClass()) && !node.getClass().equals(type)) {
            node = node.getParent();
        }

        return node;
    }

    public Node getAncestorOfType(Class<?>[] types) {
        return getAncestorOfType(types, false);
    }

    public Node getAncestorOfType(Class<?>[] types, boolean inclusive) {
        final Node[] node = new Node[]{getAncestor(inclusive)};

        while (node != null && !stream(types).anyMatch(x -> x.isAssignableFrom(node.getClass()) && !node.getClass().equals(x))) {
            node[0] = node[0].getParent();
        }

        return node[0];
    }

    /**
     * Get the next available ancestor that is of the {@link ScopeAncestor}
     * Class type.
     *
     * @param inclusive Whether or not to check the current Node.
     * @return The next available ancestor that is a {@link ScopeAncestor}
     */
    public ScopeAncestor getNextScopeAncestor(boolean inclusive) {
        Node node = getAncestor(inclusive);

        while (node != null && !(node instanceof ScopeAncestor)) {
            node = node.getParent();
        }

        return (ScopeAncestor) node;
    }

    /**
     * Get the ancestor of the Node. The ancestor will be the specified
     * node if the call is inclusive. Otherwise it will return the parent
     * of the specified Node.
     *
     * @param inclusive Whether or not to return the specified Node.
     * @return The specified Node if inclusive, otherwise the parent of
     * the specified Node.
     */
    private Node getAncestor(boolean inclusive) {
        if (inclusive) {
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
     * Class.
     */
    public boolean instanceOf(Class<?> clazz) {
        return instanceOf(new Class<?>[]{clazz});
    }

    /**
     * Get whether or not the given Object is an instance of any of the
     * given Classes.
     *
     * @param classes The Classes to check the Object against.
     * @return Whether or not the given Object is an instance of the given
     * Class.
     */
    public boolean instanceOf(Class<?> classes[]) {
        for (Class<?> c : classes) {
            if (c.isAssignableFrom(getClass())) {
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
    public Scope getScope() {
        return null;
    }

    /**
     * Get whether or not the current node type has a scope.
     *
     * @return Whether or not the current node type has a scope.
     */
    public boolean containsScope() {
        return getScope() != null;
    }

    /**
     * Set the Scope of the specified Node to the given
     * Scope instance.
     *
     * @param scope The Scope instance to use.
     */
    public void setScope(Scope scope) {
        addChild(scope, this);
    }

    /**
     * Add the given LocalVariable to the nearest scope.
     *
     * @param node The LocalVariable to add.
     */
    public void addToNearestScope(LocalDeclaration node) {
        getAncestorWithScope().addChild(node);
    }

    /**
     * Get the nearest ancestor that contains a scope. (inclusive)
     *
     * @return The nearest ancestor to the specified node that has a
     * scope.
     */
    public Node getAncestorWithScope() {
        Node node = this;

        while (node != null && !node.containsScope()) {
            node = node.getParent();
        }

        return node;
    }

    public Node getAncestorWithScopeOrClass() {
        Node node = getAncestorWithScope();

        if (node == null) {
            node = getParentClass();
        }

        return node;
    }

    public Node getNearestScopeAncestor() {
        Node ancestor = getAncestorWithScope();

        if (ancestor.containsChild(this, true) && !ancestor.getScope().containsChild(this, true)) {
            ancestor = ancestor.getParent().getAncestorWithScope();
        }

        while (ancestor.isDecoding()) {
            ancestor = ancestor.getParent().getAncestorWithScope();
        }

        return ancestor;
    }

    /**
     * Get whether or not the specified node is an ancestor of the given
     * node.
     *
     * @param node The node to search for the ancestor of.
     * @return Whether or not the specified node is an ancestor of the
     * given node.
     */
    public boolean isAncestorOf(Node node) {
        return isAncestorOf(node, false);
    }

    /**
     * Get whether or not the specified node is an ancestor of the given
     * node.
     *
     * @param node      The node to search for the ancestor of.
     * @param inclusive Whether or not to check if the node itself is a
     *                  match.
     * @return Whether or not the specified node is an ancestor of the
     * given node.
     */
    public boolean isAncestorOf(Node node, boolean inclusive) {
        Node current = node.getAncestor(inclusive);

        while (current != null && current != this) {
            current = current.parent;
        }

        return current == this;
    }

    public FieldDeclaration getExceptionData() {
        return getProgram().getClassDeclaration("flat/system/System").getField("exceptionData");
    }

    public Variable getExceptionDataVariable(Node parent, Location location) {
        return getExceptionData().generateUsableVariable(parent, location);
    }

    /**
     * Get the number of children that the specified Node has.
     *
     * @return The number of children that the specified Node has.
     */
    public int getNumChildren() {
        return children.size();
    }

    public Stream<Node> getChildStream() {
        return children.stream();
    }

    public ArrayList<Node> getVisibleChildren() {
        ArrayList<Node> nodes = new ArrayList<>();

        int start = getNumDecodedChildren();

        for (int i = 0; i < getNumVisibleChildren(); i++) {
            nodes.add(children.get(start + i));
        }

        return nodes;
    }

    /**
     * Get the child Node at the specific index in the children
     * ArrayList.
     *
     * @param index The index to access the child node from.
     * @return The child Node at the specific index.
     */
    public Node getChild(int index) {
        return children.get(index);
    }

    public Node getFirstChild() {
        if (getNumChildren() <= 0) {
            return null;
        }

        return getChild(0);
    }

    public Node getLastChild() {
        if (getNumChildren() <= 0) {
            return null;
        }

        return getChild(getNumChildren() - 1);
    }

    public Node getFirstVisibleChild() {
        if (getNumVisibleChildren() <= 0) {
            return null;
        }

        return getVisibleChild(0);
    }

    public Node getLastVisibleChild() {
        if (getNumVisibleChildren() <= 0) {
            return null;
        }

        return getVisibleChild(getNumVisibleChildren() - 1);
    }

    public int getIndex() {
        return getParent().children.indexOf(this);
    }

    public int getVisibleIndex() {
        return getParent().children.indexOf(this) - getNumVisibleChildren();
    }

    /**
     * Get the number of children that have been added to the specified
     * Node after the Node has been decoded.
     *
     * @return The number of children added to the Node after decoding.
     */
    public int getNumVisibleChildren() {
        return getNumChildren() - getNumDecodedChildren();
    }

    /**
     * Get the child that has added at the specified index after the Node
     * has been decoded.
     *
     * @param index The index (starting at 0) of the child to get.
     * @return The child at the given index.
     */
    public Node getVisibleChild(int index) {
        return getChild(index + getNumDecodedChildren());
    }

    /**
     * Remove the child that was added at the specified index after the
     * Node has been decoded.
     *
     * @param index index The index (starting at 0) of the child to
     *              remove.
     */
    public void removeVisibleChild(int index) {
        removeChild(index + getNumDecodedChildren());
    }

    /**
     * Get the child that was added before the given Node child. On a tree
     * representation, the node to the left of this Node.
     *
     * @param node The Node to get the child before.
     * @return The Node that was added before the given node.
     */
    public Node getChildBefore(Node node) {
        return children.get(children.indexOf(node) - 1);
    }

    /**
     * Get the child that was added after the given Node child. On a tree
     * representation, the node to the right of this Node.
     *
     * @param node The Node to get the child after.
     * @return The Node that was added after the given node.
     */
    public Node getChildAfter(Node node) {
        return children.get(children.indexOf(node) + 1);
    }

    /**
     * Get whether or not the given Node is a child of the specified
     * Node.
     *
     * @param child The Node to check whether is a child or not.
     * @return Whether or not the given Node is a child.
     */
    public boolean containsChild(Node child) {
        return containsChild(child, false);
    }

    public boolean containsChild(Node child, boolean recursive) {
        if (children.contains(child)) {
            return true;
        }

        if (recursive) {
            for (Node n : children) {
                if (n.containsChild(child, recursive)) {
                    return true;
                }
            }
        }

        return false;
    }

    public Node getChildOfType(Class<?> type) {
        return getChildOfType(type, true);
    }

    public Node getChildOfType(Class<?> type, boolean recursive) {
        for (Node n : children) {
            if (type.isAssignableFrom(n.getClass()) || recursive && n.containsChildOfType(type)) {
                return n;
            }
        }

        return null;
    }

    public Node[] getChildrenOfType(Class<?> type) {
        return getChildrenOfType(type, false);
    }

    public Node[] getChildrenOfType(Class<?> type, boolean inclusive) {
        ArrayList<Node> nodes = new ArrayList<>();

        if (inclusive && type.isAssignableFrom(this.getClass())) {
            nodes.add(this);
        }

        return addChildrenOfType(type, nodes).toArray(new Node[0]);
    }

    private ArrayList<Node> addChildrenOfType(Class<?> type, ArrayList<Node> nodes) {
        for (Node n : children) {
            if (type.isAssignableFrom(n.getClass())) {
                nodes.add(n);
            }

            n.addChildrenOfType(type, nodes);
        }

        return nodes;
    }

    public boolean containsChildOfType(Class<?> type) {
        return containsChildOfType(type, true);
    }

    public boolean containsChildOfType(Class<?> type, boolean recursive) {
        return getChildOfType(type, recursive) != null;
    }

    public boolean whereChildOfType(Class<?> type, Function<Node, Boolean> test) {
        for (Node n : children) {
            if (type.isAssignableFrom(n.getClass()) && test.apply(n) || n.whereChildOfType(type, test)) {
                return true;
            }
        }

        return false;
    }

    public void addReference(Node node) {
        addChild(getNumChildren(), node, this, false);
    }

    public void convertConvertedTypes(FlatMethodDeclaration context) {
        Node[] nodes = getChildrenOfType(Value.class);

        ClassDeclaration targetContext = getParentClass();

        Value[] types = context.getParentClass().primitiveOverloadTypes;

        for (Node n : nodes) {
            if (n instanceof LocalDeclaration || n instanceof Instantiation || n instanceof Array || n instanceof Cast) {
                if (types != null) {
                    targetContext.replacePrimitiveGenerics(types, (Value) n);
                }

                targetContext.replacePrimitiveGenerics(getParentMethod().getMethodGenericTypeParameterDeclaration(), context.getMethodGenericTypeParameterDeclaration().getTypes(), (Value) n);
            }
        }
    }

    /**
     * Add the specific Node under the current Node as a child.
     *
     * @param node The node to set as the child node.
     */
    public void addChild(Node node) {
        addChild(getNumChildren(), node);
    }

    /**
     * Add the specific Node under the given 'toNode' Node as a
     * child.
     *
     * @param node   The node to set as the child node.
     * @param toNode The node to add the child to.
     */
    public void addChild(Node node, Node toNode) {
        addChild(toNode.getNumChildren(), node, toNode);
    }

    /**
     * Add the specific Node under the current Node as a child.
     *
     * @param index The index to add the node at.
     * @param node  The node to set as the child node.
     */
    public void addChild(int index, Node node) {
        Scope scope = getScope();

        if (scope != null) {
            scope.addChild(node);
        } else {
            addChild(index, node, this);
        }
    }

    public void addVisibleChild(int index, Node node) {
        addChild(getNumChildren() - getNumVisibleChildren() + index, node, this);
    }

    /**
     * Add the specific Node under the given 'toNode' Node as a
     * child.
     *
     * @param index  The index to add the node at.
     * @param node   The node to set as the child node.
     * @param toNode The node to add the child to.
     */
    public void addChild(int index, Node node, Node toNode) {
        addChild(index, node, toNode, true);
    }

    public void addChild(int index, Node node, Node toNode, boolean detach) {
        if (node == null || toNode.children.contains(node)) {
            return;
        }

        if (detach) {
            // If the node already belongs to a parent, remove it from its old parent.
            node.detach();

            // Set this instance as the new parent.
            node.parent = this;

            if (node instanceof Annotation) {
                toNode.addAnnotation((Annotation) node);
            } else {
                toNode.children.add(index, node);
            }
        } else {
            if (index >= getNumChildren()) {
                toNode.children.add(index, node);
            } else {
                toNode.children.set(index, node);
            }
        }

        node.onAdded(toNode);

        checkCircular();
    }

    private void checkCircular() {
        if (parent != null) {
            Node current = parent.getParent();

            while (current != null) {
                if (current == parent) {
                    parent = null;

                    throw new RuntimeException("Circular reference created.........!");
                }

                current = current.getParent();
            }
        }
    }

    /**
     * Add the given 'toAdd' node before the given 'node', if the node
     * exists.
     *
     * @param node  The node to add the child before.
     * @param toAdd The child to add before the given node.
     * @return Whether or not the child was successfully added.
     */
    public boolean addChildBefore(Node node, Node toAdd) {
        return addChildAtOffset(node, toAdd, 0);
    }

    /**
     * Add the given 'toAdd' node after the given 'node', if the node
     * exists.
     *
     * @param node  The Node to add the child after.
     * @param toAdd The Node to add after the given node.
     * @return Whether or not the child was successfully added.
     */
    public boolean addChildAfter(Node node, Node toAdd) {
        return addChildAtOffset(node, toAdd, 1);
    }

    /**
     * Add the given 'toAdd' node relative to the given 'node' at the
     * given offset index.
     *
     * @param node   The node to add the given 'toAdd' node relatively from.
     * @param toAdd  The Node to add as a child.
     * @param offset The offset in which to add the child at relative to
     *               the given 'node' child.
     * @return Whether or not the child was successfully added.
     */
    private boolean addChildAtOffset(Node node, Node toAdd, int offset) {
        int index = children.indexOf(node);

        if (index < 0) {
            if (containsScope()) {
                return ((Node) getScope()).addChildAtOffset(node, toAdd, offset);
            }

            return false;
        }

        addChild(index + offset, toAdd);

        return true;
    }

    public void onAdded(Node parent) {
        Listenable.super.onAdded(parent);
    }

    public void onRemoved(Node parent) {
//		if (id == 1348265)
//		{
//			int j = 5;
//		}
    }

    /**
     * Remove the specific Node from the current Node as a child.
     *
     * @param index The index to remove the node from.
     */
    public void removeChild(int index) {
        Node node = children.get(index);

        node.detach();
    }

    /**
     * Remove the specific Node from the current Node as a child.
     *
     * @param node The node to remove as the child node.
     */
    public void removeChild(Node node) {
        node.detach();
    }

    /**
     * Get whether or not the specified Node is being decoded at
     * the current moment.
     *
     * @return Whether or not the specified Node is being decoded at
     * the current moment.
     */
    public boolean isDecoding() {
        return getParent() == null || !getParent().containsChild(this);
    }

    /**
     * Detach the specified node from its parent.
     */
    public Node detach() {
        if (parent == null)// || isDecoding())
        {
            return this;
        }

        Node from = parent;

        if (!parent.isDecoding() && parent.getNumChildren() > 0 && !parent.containsChild(this) && parent.containsScope()) {
            from = parent.getScope();
        }

        return detach(from);
    }

    /**
     * Detach the specified Node from the given Node.
     *
     * @param fromNode The Node to detach the specified Node from.
     */
    public Node detach(Node fromNode) {
        return detach(fromNode, false);
    }

    public Node detach(Node fromNode, boolean detachChildren) {
        return detach(fromNode, detachChildren, false);
    }

    public Node detach(Node fromNode, boolean detachChildren, boolean soft) {
        fromNode.children.remove(this);

        if (!soft) {
            parent = null;
        }

        onRemoved(fromNode);
        fromNode.onChildRemoved(this);

        if (detachChildren) {
            for (int i = children.size() - 1; i >= 0; i--) {
                children.get(i).detach(this);
            }
        }

        return this;
    }

    public void onChildRemoved(Node child) {

    }

    /**
     * Replace the given old Node with the specified replacement.
     *
     * @param old         The Node to replace.
     * @param replacement The replacement Node.
     */
    public void replace(Node old, Node replacement) {
        replace(old, replacement, true);
    }

    public void replace(Node old, Node replacement, boolean detach) {
        int index = children.indexOf(old);

        if (detach) {
            old.detach();//this, true);
        }

        if (replacement != null) {
            addChild(index, replacement, this, detach);
        }

        onChildReplaced(old, replacement);
        old.onReplaced(this, replacement);
    }

    public void onChildReplaced(Node old, Node replacement) {

    }

    public void onReplaced(Node parent, Node replacement) {
        if (replacement != null) {
            for (int i = 0; i < Math.min(getNumChildren(), replacement.getNumChildren()); i++) {
                getChild(i).onReplaced(this, replacement.getChild(i));
            }
        }
    }

    /**
     * Replace the specified Node with the given Node.
     *
     * @param replacement The Node to replace the specified one with.
     */
    public void replaceWith(Node replacement) {
        getParent().replace(this, replacement);
    }

    public void slaughterEveryLastVisibleChild() {
        while (getNumVisibleChildren() > 0) {
            getVisibleChild(0).detach(this);
        }
    }

    /**
     * @see Node#slaughterEveryLastChild(int)
     */
    public void slaughterEveryLastChild() {
        slaughterEveryLastChild(getNumChildren());
    }

    /**
     * Kill off all of the specified Node's children and send them to
     * the void. MAKE SURE THEY PAY
     *
     * @param amount The amount of children to slaughter, starting at the
     *               first-born.
     */
    public void slaughterEveryLastChild(int amount) {
        if (amount > getNumChildren()) {
            amount = getNumChildren();
        }

        for (int i = 0; i < amount; i++) {
            getChild(0).detach(this);
        }
    }

    public void forEachChild(Consumer<Node> action) {
        for (int i = 0; i < getNumChildren(); i++) {
            action.accept(getChild(i));
        }
    }

    public void forEachChildRecursive(Consumer<Node> action) {
        forEachChildRecursive(action, false);
    }

    public void forEachChildRecursive(Consumer<Node> action, boolean inclusive) {
        if (inclusive) {
            action.accept(this);
        }

        for (int i = 0; i < getNumChildren(); i++) {
            getChild(i).forEachChildRecursive(action, true);
        }
    }

    public void forEachVisibleChild(Consumer<Node> action) {
        for (int i = 0; i < getNumVisibleChildren(); i++) {
            action.accept(getVisibleChild(i));
        }
    }

    public ArrayList<Node> filterChildren(Function<Node, Boolean> filter) {
        ArrayList<Node> result = new ArrayList<>();

        for (int i = 0; i < getNumChildren(); i++) {
            if (filter.apply(getChild(i))) {
                result.add(getChild(i));
            }
        }

        return result;
    }

    public ArrayList<Node> filterVisibleChildren(Function<Node, Boolean> filter) {
        ArrayList<Node> result = new ArrayList<>();

        for (int i = 0; i < getNumVisibleChildren(); i++) {
            if (filter.apply(getVisibleChild(i))) {
                result.add(getVisibleChild(i));
            }
        }

        return result;
    }

    public Node getNextNode() {
        return getAdjacentNode(1);
    }

    public Node getPreviousNode() {
        return getAdjacentNode(-1);
    }

    public Node getAdjacentNode(int offset) {
        if (getParent() != null) {
            int index = getParent().children.indexOf(this) + offset;

            if (index >= 0 && index < getParent().children.size()) {
                return getParent().children.get(index);
            }
        }

        return null;
    }

    /**
     * Give the specified node the given nodes children. This removes the
     * children from the given oldParent node.
     *
     * @param oldParent The parent to inherit the children from.
     */
    public void inheritChildren(Node oldParent) {
        inheritChildren(oldParent, false);
    }

    /**
     * Give the specified node the given nodes children. This removes the
     * children from the given oldParent node.
     *
     * @param oldParent The parent to inherit the children from.
     * @param clone     Whether or not to clone the children and not remove
     *                  them from the previous owner.
     */
    public void inheritChildren(Node oldParent, boolean clone) {
        int index = children.size();
        int end = oldParent.getNumDefaultChildren();

        for (int i = oldParent.getNumChildren() - 1; i >= 0; i--) {
            Node child = oldParent.getChild(i);

            if (i >= end) {
                if (clone) {
                    child = child.clone(this, child.getLocationIn());
                } else {
                    child.detach();
                }

                addChild(index, child);
            } else {
                getChild(i).inheritChildren(child, clone);
            }
        }
    }

    public String parseModifiers(String input) {
        String word = StringUtils.findNextWord(input);

        while (parseModifier(word)) {
            input = input.substring(word.length()).trim();

            word = StringUtils.findNextWord(input);
        }

        return input;
    }

    public boolean isModifier(String modifier) {
        return Annotation.MODIFIERS.containsKey(modifier);
    }

    public boolean parseModifier(String modifier) {
        java.lang.reflect.Constructor c = Annotation.MODIFIERS.get(modifier);

        if (c != null) {
            try {
                ModifierAnnotation annotation = (ModifierAnnotation) c.newInstance(null, null);

                return annotation.apply(this, modifier);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Get whether or not the Node requires a special form of output.
     * Examples are non-virtual method calls.
     *
     * @return Whether or not the Node requires a special form of output.
     */
    public boolean isSpecial() {
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
    public final ExtraData iterateWords(String statement, boolean require) {
        return iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES, null, require);
    }

    /**
     * Iterate through the words of the statement. A word is just anything
     * that is surrounded by whitespace. e.g. In the statement:
     * "public void test() { }" the words consist of:
     * [ public, void, test(), {, } ]
     *
     * @param statement The statement to iterate the words from.
     * @param extra     The extra data that may or may not be needed for the
     *                  interactWord() methods.
     * @return The given ExtraData instance.
     */
    public final ExtraData iterateWords(String statement, ExtraData extra, boolean require) {
        return iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES, extra, require);
    }

    /**
     * Iterate through each of the groupings of the given Pattern on the
     * statement. In the default case, it will search for the boundaries
     * of words and iterate through all of them.
     *
     * @param statement The statement to search through.
     * @param pattern   The Pattern to search with.
     * @return The given ExtraData instance.
     */
    public final ExtraData iterateWords(String statement, Pattern pattern, boolean require) {
        return iterateWords(statement, pattern, null, require);
    }

    /**
     * Iterate through each of the groupings of the given Pattern on the
     * statement. In the default case, it will search for the boundaries
     * of words and iterate through all of them.
     *
     * @param statement The statement to search through.
     * @param pattern   The Pattern to search with.
     * @param extra     The extra data that may or may not be needed for the
     *                  interactWord() methods.
     * @return The given ExtraData instance.
     */
    public ExtraData iterateWords(String statement, Pattern pattern, ExtraData extra, boolean require) {
        // Pattern used to find word boundaries.
        Matcher matcher = pattern.matcher(statement);

        if (extra == null) {
            extra = new ExtraData();
        }

        extra.statement = statement;
        extra.require = require;

        findWords(statement, matcher, extra);

        extra.wordNumber = 0;

        while (extra.wordNumber < extra.bounds.size()) {
            String word = extra.words.get(extra.wordNumber);
            Bounds bound = extra.bounds.get(extra.wordNumber);

            String leftDelim = extra.delims.get(extra.wordNumber);
            String rightDelim = extra.delims.get(extra.wordNumber + 1);

            if (!interactWord(word, bound, leftDelim, rightDelim, extra)) {
                extra.earlyReturn = true;
                break;
            }

            extra.wordNumber++;
        }

        return extra;
    }

    /**
     * Find the words, bounds, and delimiters in the given statement.
     *
     * @param statement The statement to find the information from.
     * @param matcher   The matcher searching through the statement.
     * @param extra     The ExtraData containing the lists that will acquire the
     *                  words, delimiters, and bounds.
     */
    public static void findWords(String statement, Matcher matcher, ExtraData extra) {
        int index = 0;
        int oldIndex = 0;
        int lastValidIndex = 0;

        for (boolean end = false; matcher.find(); end = !end) {
            if (end) {
                Bounds bounds = new Bounds(oldIndex, index);
                String delim = "";

                trimBounds(bounds, extra);

                if (bounds.isValid()) {
                    delim = bounds.extractString(statement);

                    lastValidIndex = matcher.start();
                }

                oldIndex = matcher.start();

                bounds = new Bounds(index, oldIndex);

                trimBounds(bounds, extra);

                if (bounds.isValid()) {
                    extra.fullDelims.add(delim);
                    extra.delims.add(StringUtils.trimSurroundingWhitespace(delim));
                    extra.bounds.add(bounds);
                    extra.words.add(statement.substring(index, oldIndex));
                } else {
                    lastValidIndex = matcher.start();
                }
            } else {
                index = matcher.start();
            }
        }

        if (extra.containsSkipBounds()) {
            Bounds last = extra.getSkipBounds(extra.getNumSkipBounds() - 1);

            if (lastValidIndex < last.getEnd()) {
                lastValidIndex = last.getEnd();
            }
        }

        // Don't forget the last delimiter.
        extra.delims.add(statement.substring(lastValidIndex));
    }

    public static void trimBounds(Bounds bounds, ExtraData extra) {
        for (int i = 0; i < extra.skipBounds.length; i++) {
            Bounds skip = extra.skipBounds[i];

            boolean end = bounds.getEnd() > skip.getStart() && bounds.getEnd() <= skip.getEnd();

            if (bounds.getStart() < skip.getEnd() && bounds.getStart() >= skip.getStart()) {
                if (end) {
                    bounds.setInvalid();
                    return;
                }

                bounds.setStart(skip.getEnd());
            } else if (end) {
                bounds.setEnd(skip.getStart());
            }
        }
    }

    /**
     * Method that is to be overridden. Whenever the iterateWords(String)
     * method is called, this method will be called with the specific word
     * and the number (order) the word came in the statement.
     *
     * @param word           The word that was found.
     * @param bounds         The bounds of the word that was found.
     * @param leftDelimiter  The text that is between the previous word and
     *                       the current word.
     * @param rightDelimiter The text that is between the current word and
     *                       the next word.
     * @param extra          The extra data that may or may not be needed for the
     *                       interactWord() methods.
     */
    public boolean interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra) {
        return true;
    }

    public final boolean isUserMade() {
        return isUserMade(true);
    }

    public boolean isUserMade(boolean checkAncestor) {
        return (!containsProperty("userMade") || isPropertyTrue("userMade")) && (!checkAncestor || (getParent() == null || getParent().isUserMade()));
    }

    /**
     * Get whether or not the specified Node is used within a
     * static context.
     *
     * @return Whether or not the specified Node is used within a
     * static context.
     */
    public boolean isWithinStaticContext() {
        return getParentMethod(true) == null || !getParentMethod(true).isInstance();
    }

    /**
     * Get whether or not the specified Node is used within an
     * external context.
     *
     * @return Whether or not the specified Node is used within an
     * external context.
     */
    public boolean isWithinExternalContext() {
        if (this instanceof ExternalType) {
            return true;
        } else if (parent != null) {
            return parent.isWithinExternalContext();
        }

        return false;
    }

    public Node getLastAncestorOfType(Class<?>[] classes, boolean opposite) {
        Node prev = null;
        Node current = this;

        while (SyntaxUtils.checkTypes(classes, current.getClass()) != opposite) {
            prev = current;
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
    public String generateJavaSource() {
        throw new UnimplementedOperationException("The Java implementation for this feature has not been implemented yet.");
    }

    /**
     * Generate the Flat syntax String that represents the Node.
     * Essentially, this is the String that is decoded into the node.
     * It is the input value from the .fat source file.
     *
     * @return The appended StringBuilder that represents the input String
     * in Flat syntax.
     */
    public final StringBuilder generateFlatInput() {
        return generateFlatInput(true);
    }

    /**
     * Generate the Flat syntax String that represents the Node.
     * Essentially, this is the String that is decoded into the node.
     * It is the input value from the .flat source file.
     *
     * @param outputChildren Whether or not to output the children of the
     *                       children of the Node as well.
     * @return The appended StringBuilder that represents the input String
     * in Flat syntax.
     */
    public final StringBuilder generateFlatInput(boolean outputChildren) {
        return generateFlatInput(new StringBuilder(), outputChildren);
    }

    /**
     * Generate the Flat syntax String that represents the Node.
     * Essentially, this is the String that is decoded into the node.
     * It is the input value from the .flat source file.
     *
     * @param builder The StringBuilder to append the data to.
     * @return The appended StringBuilder that represents the input String
     * in Flat syntax.
     */
    public final StringBuilder generateFlatInput(StringBuilder builder) {
        return generateFlatInput(builder, true);
    }

    /**
     * Generate the Flat syntax String that represents the Node.
     * Essentially, this is the String that is decoded into the node.
     * It is the input value from the .flat source file.
     *
     * @param builder        The StringBuilder to append the data to.
     * @param outputChildren Whether or not to output the children of the
     *                       children of the Node as well.
     * @return The appended StringBuilder that represents the input String
     * in Flat syntax.
     */
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        return generateFlatInput(builder, outputChildren, true);
    }

    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray) {
        throw new UnimplementedOperationException("The Flat input implementation for " + getClass().getName() + " has not been implemented yet.");
    }

    public Node followedByScope(boolean scope) {
        return this;
    }

    public boolean onAfterDecoded() {
        for (int i = children.size() - 1; i >= 0; i--) {
            Node n = children.get(i);

            if (!n.onAfterDecoded()) {
                return false;
            }
        }
        if (annotations != null) {
            for (int i = annotations.size() - 1; i >= 0; i--) {
                Annotation annotation = annotations.get(i);

                annotation.onNextStatementDecoded(this);
            }
        }

        return true;
    }

    public void onStackPopped(Node popped) {
        for (Node n : children) {
            n.onStackPopped(popped);
        }
    }

    public boolean onNextStatementDecoded(Node next) {
        for (Node n : children) {
            if (!n.onNextStatementDecoded(next)) {
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
    public ValidationResult validate(int phase) {
        if (getNumAnnotations() > 0) {
            for (int i = annotations.size() - 1; i >= 0; i--) {
                ValidationResult result = annotations.get(i).validate(phase);

                if (result.skipValidation()) {
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
    public void rollback() {
        for (int i = 0; i < getNumChildren(); i++) {
            getChild(i).rollback();
        }
    }

    public boolean isValid() {
        return parent != null;
    }

    /**
     * Generate a String containing information of where the Node is
     * located in reference to the source input files.
     *
     * @return A String containing information of where the Node is
     * located in reference to the source input files.
     */
    public String getLocationInfo() {
        FileDeclaration file = getFileDeclaration();

        String info = "";

        if (file != null) {
            info += " in file " + file.getName();
        }

        return info;
    }

    /**
     * If the specified node is within an try block, return the node for
     * the try block.
     *
     * @return The parent Try, if there is one.
     */
    public Try getParentTry() {
        Try node = (Try) getAncestorOfType(Try.class, true);

        return node;
    }

    /**
     * Get whether or not the specified node is within an try block.
     *
     * @return Whether or not the specified node is within a try block.
     */
    public boolean isWithinTry() {
        return getParentTry() != null;
    }

    /**
     * Get the Program (The oldest parent) of this Node.
     *
     * @return The Program of this Node.
     */
    public Program getProgram() {
        if (parent != null && parent != this) {
            return parent.getProgram();
        }

        return null;
    }

    public boolean isInTree() {
        Node current = parent;

        while (current != null && current instanceof Program == false) {
            if (!current.isDecoding()) {
                current = current.getParent();
            } else {
                return false;
            }
        }

        return current instanceof Program;
    }

    public final FileDeclaration getReferenceFile() {
        return getReferenceFile(true);
    }

    public FileDeclaration getReferenceFile(boolean checkCast) {
        return getFileDeclaration();
    }

    /**
     * Get the FileDeclaration of this Node, if it exists.
     *
     * @return The FileDeclaration of this Node.
     */
    public FileDeclaration getFileDeclaration() {
        return getFileDeclaration(false);
    }

    /**
     * Get the FileDeclaration of this Node, if it exists.
     *
     * @param inclusive Whether or not to check the specified Node.
     * @return The FileDeclaration of this Node.
     */
    public FileDeclaration getFileDeclaration(boolean inclusive) {
        Node current = getAncestor(inclusive);

        while (current != null && current instanceof FileDeclaration == false) {
            current = current.parent;
        }

        return (FileDeclaration) current;
    }

    public ClassDeclaration[] getExtensionClasses() {
        ArrayList<ClassDeclaration> list = new ArrayList<>();

        if (getParentClass() instanceof ExtensionDeclaration) {
            ExtensionDeclaration extension = (ExtensionDeclaration) getParentClass();

            list.add(extension);
        }

        getFileDeclaration().getImportList().forEachVisibleListChild(i -> {
            ClassDeclaration c = i.getClassDeclaration();

            if (c instanceof ExtensionDeclaration) {
                ExtensionDeclaration extension = (ExtensionDeclaration) c;

                list.add(extension);
            }
        });

        return list.toArray(new ClassDeclaration[0]);
    }

    public ClassDeclaration[] getStaticImports() {
        ArrayList<ClassDeclaration> list = new ArrayList<>();

        getFileDeclaration().getImportList().forEachVisibleListChild(i -> {
            if (i.isStatic) {
                ClassDeclaration c = i.getClassDeclaration();

                if (c != null) {
                    list.add(c);
                }
            }
        });

        return list.toArray(new ClassDeclaration[0]);
    }

    /**
     * Get the ClassDeclaration parent instance of the Node, if one exists.
     *
     * @return The nearest ClassDeclaration instance that contains this node.
     */
    public ClassDeclaration getParentClass() {
        return getParentClass(false);
    }

    /**
     * Get the ClassDeclaration parent instance of the Node, if one exists.
     *
     * @param inclusive Whether or not to inclusively check the specified
     *                  Node to see if it is a ClassDeclaration.
     * @return The nearest ClassDeclaration instance that contains this node.
     */
    public ClassDeclaration getParentClass(boolean inclusive) {
        return (ClassDeclaration) getAncestorOfType(ClassDeclaration.class, inclusive);
    }

    /**
     * Get the Method parent instance of the Node, if one exists.
     *
     * @return The nearest Method instance that contains this node.
     */
    public final FlatMethodDeclaration getParentMethod() {
        return getParentMethod(false);
    }

    /**
     * Get the Method parent instance of the Node, if one exists.
     *
     * @param inclusive Whether or not to inclusively check the specified
     *                  Node to see if it is a MethodDeclaration.
     * @return The nearest Method instance that contains this node.
     */
    public final FlatMethodDeclaration getParentMethod(boolean inclusive) {
        return (FlatMethodDeclaration) getAncestorOfType(FlatMethodDeclaration.class, inclusive);
    }

    /**
     * Get the compiler's controller. The controller is used for
     * logging, error output, and other compiler options.
     *
     * @return The compiler's controller instance.
     */
    public Flat getController() {
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
     * @param bounds    The bounds of the Node's arguments.
     * @return Whether or not the scope fragment decoded correctly.
     */
    public boolean decodeScopeFragment(String statement, Bounds bounds) {
        return decodeScopeFragment(statement, bounds.getEnd() + 1);
    }

    public boolean decodeScopeFragment(String statement, int start) {
        int nextChar = StringUtils.findNextNonWhitespaceIndex(statement, start);

        if (nextChar <= 0) {
            return true;
        }

        String fragment = statement.substring(nextChar);

        Location location = new Location(getLocationIn());

        Node node = SyntaxTree.decodeScopeContents(this, fragment, location);

        if (node == null) {
            return false;
        }

        addChild(node);

        return true;
    }

    public Node getStatementRootNode() {
        Node current = this;

        while (current != null && (!current.containsScope()))// || current.isDecoding()))
        {
            current = current.getParent();
        }

        return current;
    }

    /**
     * Get the Node that is highest on the tree, up until a scope is hit.
     * (The Node that is returned will have a scope as a parent)
     *
     * @return The Node that is the highest on the tree up until a scope
     * is found.
     */
    public Node getBaseNode() {
        return getBaseNode(false);
    }

    public Node getBaseNode(boolean inclusive) {
        Node prev = this;
        Node current = getParent();

        while (current != null && !current.containsScope() && !(current instanceof Scope)) {
            prev = current;
            current = current.getParent();
        }

        if (current != null && current.containsChild(this) && !(current instanceof Scope)) {
            return current;
        }

        if (inclusive) {
            return current;
        }

        return prev;
    }

    /**
     * Return a new Node containing a copy of the values of the
     * specified node, including clones of the children.
     *
     * @param temporaryParent The Node to act as the parent
     *                        temporarily.
     * @param locationIn      The Location instance holding the information.
     * @return A clone of the specified Node.
     */
    public final Node clone(Node temporaryParent, Location locationIn) {
        return clone(temporaryParent, locationIn, true);
    }

    public final Node clone(Node temporaryParent, Location locationIn, boolean cloneChildren) {
        return clone(temporaryParent, locationIn, cloneChildren, true);
    }

    /**
     * Return a new Node containing a copy of the values of the
     * specified node, including clones of the children.
     *
     * @param temporaryParent The Node to act as the parent
     *                        temporarily.
     * @param locationIn      The Location instance holding the information.
     * @param cloneChildren   Whether or not to clone the children of the
     *                        Node as well.
     * @return A clone of the specified Node.
     */
    public abstract Node clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations);

    /**
     * Fill the given {@link Node} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Node cloneTo(Node node) {
        throw new UnsupportedOperationException("Class " + this.getClass().getName() + " must implement cloneTo(Node, boolean)");

//		return cloneTo(node, true);
    }

    /**
     * Fill the given {@link Node} with the data that is in the
     * specified node.
     *
     * @param node          The node to copy the data into.
     * @param cloneChildren Whether or not to clone the children of the
     *                      Node as well.
     * @return The cloned node.
     */
    public final Node cloneTo(Node node, boolean cloneChildren) {
        return cloneTo(node, cloneChildren, true);
    }

    public void cloneAnnotationsTo(Node node) {
        if (annotations != null) {
            ArrayList<Annotation> cloned = new ArrayList<>(annotations.size());

            for (Annotation a : annotations) {
                cloned.add(a.clone(node, node.getLocationIn(), true, true));
            }

            cloned.forEach(a -> a.onAdded(node));

            if (node.annotations == null) {
                node.annotations = cloned;
            } else {
                for (Annotation a : cloned) {
                    node.annotations.add(a);
                }
            }
        }
    }

    public Node cloneTo(Node node, boolean cloneChildren, boolean cloneAnnotations) {
        if (locationIn != null) {
            Location locIn = new Location(locationIn);
            locIn.setLineNumber(locationIn.getLineNumber());
            node.setLocationIn(locIn);
        }

        if (cloneChildren) {
            cloneChildrenTo(node);
        }

        if (cloneAnnotations) {
            cloneAnnotationsTo(node);
        }

        if (properties != null) {
            for (Map.Entry<String, Object> entry : properties.entrySet()) {
                node.setProperty(entry.getKey(), entry.getValue());
            }
        }

        node.originalFile = originalFile;

        return node;
    }

    public void cloneChildrenTo(final Node node) {
        if (getNumDefaultChildren() > 0) {
            node.slaughterEveryLastChild(getNumDefaultChildren());
        }

        for (int i = getNumChildren() - 1; i >= 0; i--) {
            Node child = children.get(i);

            node.children.add(0, child.clone(node, child.getLocationIn()));
        }

        //forEachChild(x -> node.addChild(x.clone(node, x.getLocationIn())));
    }

    public void inheritAnnotations(Node from) {
        if (from.annotations != null) {
            for (int i = from.annotations.size() - 1; i >= 0; i--) {
                Annotation a = from.annotations.get(i);

                addAnnotation(a);
                a.onAdded(this);
            }
        }
    }

    /**
     * Generate a String that represents the Node as how it
     * was decoded.
     *
     * @return The Flat input equivalent to the node.
     */
    public String toString() {
        try {
            return generateFlatInput().toString();
        } catch (UnimplementedOperationException e) {
            return "[Node: " + super.toString() + ']';
        }
    }

    public static Node newEmptyNode() {
        return new Node(null, null) {
            @Override
            public Node clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
                return null;
            }
        };
    }

    public FileDeclaration getOriginalFile() {
        if (originalFile != null) {
            return originalFile;
        }

        if (parent != null) {
            return parent.getOriginalFile();
        }

        return null;
    }

    protected void setOriginalFile(FileDeclaration originalFile) {
        this.originalFile = originalFile;
    }

    /**
     * Class used to pass data to the interactWord() methods.
     *
     * @author Braden Steffaniak
     * @since v0.2.13 Jun 11, 2014 at 8:29:57 PM
     * @version v0.2.13 Jun 11, 2014 at 8:29:57 PM
     */
    public static class ExtraData {
        public boolean require, checkType;
        public boolean earlyReturn;

        private int wordNumber;

        private Bounds[] skipBounds;

        protected ArrayList<Bounds> bounds;
        protected ArrayList<String> words;
        protected ArrayList<String> delims;
        protected ArrayList<String> fullDelims;

        public String error;
        public String statement;

        public ExtraData() {
            skipBounds = new Bounds[0];

            bounds = new ArrayList<>();
            words = new ArrayList<>();
            delims = new ArrayList<>();
            fullDelims = new ArrayList<>();
        }

        public int getWordNumber() {
            return wordNumber;
        }

        public boolean isLastWord() {
            return wordNumber == words.size() - 1;
        }

        public boolean isFirstWord() {
            return wordNumber == 0;
        }

        public Bounds getCurrentWordBounds() {
            return bounds.get(wordNumber);
        }

        public Bounds getNextWordBounds() {
            if (isLastWord()) {
                return null;
            }

            return bounds.get(wordNumber + 1);
        }

        public Bounds getPreviousWordBounds() {
            if (isFirstWord()) {
                return null;
            }

            return bounds.get(wordNumber - 1);
        }

        public String getNextWord() {
            if (isLastWord()) {
                return null;
            }

            return words.get(wordNumber + 1);
        }

        public String getPreviousWord() {
            if (isFirstWord()) {
                return null;
            }

            return words.get(wordNumber - 1);
        }

        public String getNextDelimiter() {
            if (isLastWord()) {
                return null;
            }

            return delims.get(wordNumber + 1);
        }

        public String getPreviousDelimiter() {
            if (isFirstWord()) {
                return null;
            }

            return delims.get(wordNumber - 1);
        }

        public boolean isSkipBoundsNext() {
            return getRightAdjacentSkipBounds() != null;
        }

        public Bounds getRightAdjacentSkipBounds() {
            for (Bounds skip : skipBounds) {
                if (skip.getStart() >= getCurrentWordBounds().getEnd() &&
                    (isLastWord() || skip.getEnd() <= getNextWordBounds().getStart())) {
                    return skip;
                }
            }

            return null;
        }

        public Bounds getLeftAdjacentSkipBounds() {
            for (Bounds skip : skipBounds) {
                if (skip.getEnd() <= getCurrentWordBounds().getStart() &&
                    (isFirstWord() || skip.getStart() >= getPreviousWordBounds().getEnd())) {
                    return skip;
                }
            }

            return null;
        }

        public int getNumSkipBounds() {
            return skipBounds.length;
        }

        public Bounds getSkipBounds(int index) {
            return skipBounds[index];
        }

        public boolean containsSkipBounds() {
            return getNumSkipBounds() > 0;
        }

        public void addSkipBounds(Bounds bounds) {
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
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}