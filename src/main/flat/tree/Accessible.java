package flat.tree;

import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.generics.GenericTypeParameterList;
import flat.tree.variables.ObjectReference;
import flat.tree.variables.Variable;
import flat.tree.variables.VariableDeclaration;
import flat.util.SyntaxUtils;

import java.util.ArrayList;

/**
 * @author Braden Steffaniak
 * @since v0.2.36 Oct 6, 2014 at 9:58:16 PM
 * @version v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public interface Accessible {
    public Node getParent();

    public GenericCompatible getContext();

    public boolean isSafeNavigation();

    public void setSafeNavigation(boolean safeNavigation);

    public boolean isChainNavigation();

    public void setChainNavigation(boolean chainNavigation);

    default GenericCompatible getReferenceContext() {
        return (GenericCompatible) getReferenceNode();
    }

    default Value toValue() {
        return (Value) this;
    }

    default boolean isAccessedBy(Accessible other) {
        Accessible current = getAccessingNode();

        while (current != null) {
            if (current == other) {
                return true;
            }

            current = current.getAccessingNode();
        }

        return false;
    }

    default boolean doesAccess(Accessible other) {
        Accessible current = getAccessedNode();

        while (current != null) {
            if (current == other) {
                return true;
            }

            current = current.getAccessedNode();
        }

        return false;
    }

    boolean isInstance();

    /**
     * Get whether or not the specified Node is both used within a
     * static context and not accessed by an instance.
     *
     * @return Whether or not the specified Node is both used within a
     * static context and not accessed by an instance.
     */
    default boolean isAccessedWithinStaticContext() {
        return !isAccessed() && !isInstance() || isAccessed() && getAccessingNode() instanceof StaticClassReference;
    }

    /**
     * Get the ClassDeclaration instance that declares the method that
     * this MethodCall is calling.
     *
     * @return The Class that this MethodCall's declaration is declared
     * in.
     */
    default ClassDeclaration getDeclaringClass() {
        return ((Value) getReferenceNode()).getTypeClass(false);
    }

    default MethodCall.Pair<ClassDeclaration, MethodList.SearchFilter>[] getDeclaringClasses() {
        ArrayList<MethodCall.Pair<ClassDeclaration, MethodList.SearchFilter>> list = new ArrayList<>();

        MethodList.SearchFilter filter = new MethodList.SearchFilter();
        filter.staticValue = isAccessedWithinStaticContext();
        filter.checkStatic = filter.staticValue;

        list.add(new MethodCall.Pair<>(getDeclaringClass(), filter));

        filter = new MethodList.SearchFilter();
        filter.checkStatic(true);

        for (ClassDeclaration c : toValue().getStaticImports()) {
            list.add(new MethodCall.Pair<>(c, filter));
        }

        for (ClassDeclaration c : toValue().getExtensionClasses()) {
            list.add(new MethodCall.Pair<>(c, MethodList.SearchFilter.getDefault()));
        }

        return list.toArray(new MethodCall.Pair[0]);
    }

    default GenericTypeParameter getExtendedGenericParameter(GenericTypeParameter type) {
        return type;
    }

    default GenericTypeArgument getGenericTypeArgumentFromParameter(GenericTypeParameter type) {
        type = getExtendedGenericParameter(type);

        ClassDeclaration typeClass = null;

        Value refNode = (Value) getReferenceContext();
        Value value = refNode;

        if (value == this) {
            value = (Value) getReferenceNode();
        }

        Value flatType = value.getFlatTypeValue((Value) this);

        if (flatType.isGenericType()) {
            return ((Accessible) value).getGenericTypeArgumentFromParameter(type);
        } else {
            typeClass = value.getTypeClass();
        }

        ClassDeclaration paramClass = type.getParentClass();

        GenericTypeParameterList params = paramClass.getGenericTypeParameterDeclaration();

        if (params == null) {
            return null;
        }

        int index = /*getDeclaration()*//*getReferenceNode().toValue().*/params.getParameterIndex(type.getType());

        if (typeClass == null) {
            return null;
        }

        if (index >= 0) {
            if (value.getTypeClass() == type.getParentClass() && value.getGenericTypeArgumentList() != null && value.getGenericTypeArgumentList().getNumVisibleChildren() > index) {
                GenericTypeArgument arg = value.getGenericTypeArgumentList().getVisibleChild(index);

                if (arg.isGenericType() && ((Accessible) value).canAccess()) {
                    GenericTypeArgument extracted = ((Accessible) value).getGenericTypeArgumentFromParameter(arg.getGenericTypeParameter());

                    if (extracted != null) {
                        return extracted;
                    }
                }

                return arg;
            }

            if (typeClass != type.getParentClass() && !(refNode instanceof Variable && ((Variable) refNode).declaration instanceof ReferenceParameter)) {
                if (refNode != value) {
                    return SyntaxUtils.performWalk(this.toValue(), this.toValue(), typeClass, type.getParentClass(), index);
                } else {
                    return SyntaxUtils.performWalk(toValue(), value, typeClass, type.getParentClass(), index);
                }
            }

            if (value.getBaseNode() instanceof Assignment && !value.getBaseNode().isDecoding()) {
                Assignment assignment = (Assignment) value.getBaseNode();
                VariableDeclaration decl = assignment.getAssignedNode().getDeclaration();

                if (decl.getGenericTypeArgumentList().getNumVisibleChildren() > index) {
                    return decl.getGenericTypeArgument(index);
                }
            }
        }

        return null;
    }

    /**
     * Get the next accessed node that is of the given class type.
     *
     * @param type The class type to search for.
     * @return The next accessed node of the given type. If there are
     * no matches, null is returned.
     */
    default Accessible getNextAccessingOfType(Class<?> type) {
        return getNextAccessingOfType(new Class<?>[]{type});
    }

    /**
     * Get the next accessed node that is of the given class types.
     *
     * @param types The class types to search for.
     * @return The next accessed node of the given types. If there are
     * no matches, null is returned.
     */
    default Accessible getNextAccessingOfType(Class<?> types[]) {
        return getNextAccessingOfType(types, false);
    }

    /**
     * Get the next accessed node that is of the given class types.
     *
     * @param types    The class types to search for.
     * @param opposite Whether or not to search for or against the given
     *                 data.
     * @return The next accessed node of the given types. If there are
     * no matches, null is returned.
     */
    default Accessible getNextAccessingOfType(Class<?> types[], boolean opposite) {
        Accessible current = getAccessingNode();

        while (current != null && SyntaxUtils.checkTypes(types, current.getClass()) == opposite) {
            current = current.getAccessingNode();
        }

        return current;
    }

    /**
     * Get the last accessed node of the given type that was in a series.
     * In other words, if we are looking for the last method call and
     * there are three consecutive method calls in a row, the third method
     * call node would be returned.
     *
     * @param type     The class type to search for.
     * @param opposite Whether or not to search for or against the given
     *                 type.
     * @return The last accessed node of the given type. If there is not a
     * match, null is returned.
     */
    default Accessible getLastAccessingOfType(Class<?> type, boolean opposite) {
        return getLastAccessingOfType(type, opposite);
    }

    default Accessible getLastAccessingOfType(Class<?> type, boolean opposite, boolean inclusive) {
        return getLastAccessingOfType(new Class<?>[]{type}, opposite, inclusive);
    }

    /**
     * Get the last accessed node of the given types that was in a series.
     * In other words, if we are looking for the last method call and
     * there are three consecutive method calls in a row, the third method
     * call node would be returned.
     *
     * @param types    An arrayAccess of accepted types.
     * @param opposite Whether or not to search for or against the given
     *                 data.
     * @return The last accessed node of the given types. If there is not
     * a match, null is returned.
     */
    default Accessible getLastAccessingOfType(Class<?> types[], boolean opposite) {
        return getLastAccessingOfType(types, opposite, false);
    }

    default Accessible getLastAccessingOfType(Class<?> types[], boolean opposite, boolean inclusive) {
        Accessible previous = null;
        Accessible current = this;

        if (inclusive && SyntaxUtils.checkTypes(types, current.getClass()) != opposite) {
            return current;
        }

        while (current != null && SyntaxUtils.checkTypes(types, current.getClass()) != opposite) {
            previous = current;
            current = current.getAccessingNode();
        }

        return previous;
    }

    default Accessible getLastAccessedOfType(Class<?> type, boolean opposite) {
        return getLastAccessedOfType(new Class<?>[]{type}, opposite);
    }

    default Accessible getLastAccessedOfType(Class<?> types[], boolean opposite) {
        Accessible previous = null;
        Accessible current = this;

        while (current != null) {
            if (SyntaxUtils.checkTypes(types, current.getClass()) != opposite) {
                previous = current;
            }

            current = current.getAccessedNode();
        }

        return previous;
    }

    default Accessible getLastAccessed() {
        Accessible previous = this;
        Accessible current = this;

        while (current != null) {
            previous = current;
            current = current.getAccessedNode();
        }

        return previous;
    }

    /**
     * Get the next node that this node accesses that is of the given
     * type.
     *
     * @param type The type to search for.
     * @return The next accessed of the given type. If there is not a
     * match, null is returned.
     */
    default Identifier getNextAccessedOfType(Class<?> type) {
        return getNextAccessedOfType(new Class<?>[]{type});
    }

    /**
     * Get the next node that this node accesses that is of the given
     * types.
     *
     * @param types The types to search for.
     * @return The next accessed of the given types. If there is not a
     * match, null is returned.
     */
    default Identifier getNextAccessedOfType(Class<?> types[]) {
        Identifier current = getAccessedNode();

        while (current != null && !SyntaxUtils.checkTypes(types, current.getClass())) {
            current = current.getAccessedNode();
        }

        return current;
    }

    /**
     * Get the root variable that is accessing the specified Identifier.
     *
     * @return The root variable that is accessing the specified
     * Identifier.
     */
    default Accessible getRootReferenceNode() {
        return getRootReferenceNode(false);
    }

    /**
     * Get the root variable that is accessing the specified Identifier.
     *
     * @param inclusive Whether or not to return the specified Identifier
     *                  if the Identifier is not accessed.
     * @return The root variable that is accessing the specified
     * Identifier.
     */
    default Accessible getRootReferenceNode(boolean inclusive) {
        if (!isAccessed()) {
            if (inclusive) {
                return this;
            }

            return getReferenceNode(false, true);
        }

        Accessible reference = getReferenceNode(false, true);

        Accessible node = reference.getLastAccessingOfType(new Class<?>[]{Closure.class, MethodCall.class}, true);

        if (node == null) {
            return reference;
        }

        return node;
    }

    default Priority getParentPriority() {
        Accessible current = this;

        while (current != null) {
            if (current.getParent() instanceof Cast && current.getParent().getParent() instanceof Priority) {
                return (Priority) current.getParent().getParent();
            }
            if (current.getParent() instanceof Priority) {
                return (Priority) current.getParent();
            }

            current = current.getAccessingNode();
        }

        return null;
    }

    /**
     * Get the Node that represents the value that contains the value.<br>
     * For example:<br>
     * <blockquote><pre>
     * ClassName obj = new ClassName();
     *
     * obj.data.methodName();</pre></blockquote>
     * In the previous statements, the method "<u><code>methodName()</code></u>"
     * is being referenced through the "<u><code>data</code></u>"
     * identifier.<br>
     * <br>
     * If the identifier is not explicitly called through an object, it
     * will return the instance type that the node was referenced
     * within.<br>
     * For example:
     * <blockquote><pre>
     * obj.calculateArea().width;</pre></blockquote>
     * The Identifier for the method call "<u><code>calculateArea()</code></u>"
     * is returned.<br>
     * <br>
     * If the node is referenced within a static context, the containing
     * ClassDeclaration is returned.<br>
     * For example:
     * <blockquote><pre>
     * Time.currentTimeMillis();</pre></blockquote>
     * The Identifier for the ClassDeclaration "<u><code>Time</code></u>" is
     * returned.
     *
     * @return The Node that represents the calling Identifier.
     */
    default Accessible getReferenceNode() {
        return getReferenceNode(false);
    }

    default Accessible getReferenceNode(boolean requireAccessingNode) {
        return getReferenceNode(requireAccessingNode, false);
    }

    default Accessible getReferenceNode(boolean requireAccessingNode, boolean skipPriority) {
        Value n = (Value) this;

        Accessible accessing = getAccessingNode(skipPriority);

        if (accessing != null || requireAccessingNode) {
            return accessing;
        }

        MethodDeclaration methodDeclaration = n.getParentMethod();

        if (methodDeclaration != null) {
            ObjectReference id = n.getObjectReferenceNode(methodDeclaration);

            if (id != null) {
                return id;
            }
        }

        return n.getParentClass(true);
    }

    default Accessible getReferenceTypeNode() {
        return getReferenceTypeNode(false);
    }

    default Accessible getReferenceTypeNode(boolean requireAccessingNode) {
        return getReferenceTypeNode(requireAccessingNode, false);
    }

    default Accessible getReferenceTypeNode(boolean requireAccessingNode, boolean skipPriority) {
        return getReferenceNode(requireAccessingNode, skipPriority);
    }

    /**
     * Get the farthest node that is accessing the specified identifier.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * tree.next.calculateSize()</pre></blockquote>
     * In the previous statement, "<code>tree</code>" is the context. If
     * the identifier is not accessed by any other identifiers, then
     * null is returned.<br>
     * For example:
     * <blockquote><pre>
     * calculateSize()</pre></blockquote>
     * The above would return null.
     *
     * @return The furthest node that accesses the specified identifier.
     */
    default Accessible getContextNode() {
        return getContextNode(getReferenceNode());
    }

    /**
     * Get the furthest node that is accessing the specified identifier.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * tree.next.calculateSize()</pre></blockquote>
     * In the previous statement, "<code>tree</code>" is the context. If
     * the identifier is not accessed by any other identifiers, then
     * null is returned.<br>
     * For example:
     * <blockquote><pre>
     * calculateSize()</pre></blockquote>
     * The above would return null.
     *
     * @param escape The Identifier if it the specified Identifier
     *               is not accessed through any Identifiers.
     * @return The furthest node that accesses the specified identifier.
     */
    default Accessible getContextNode(Accessible escape) {
        Accessible node = this;

        if (!node.isAccessed()) {
            return escape;
        }

        while (node.isAccessed()) {
            node = node.getAccessingNode();
        }

        return node;
    }

    default Node checkSafeNavigation() {
        if (isSafeNavigation()) {
            setSafeNavigation(false);

            Node parent = getParent();
            Node newParent = null;

            Accessible root = getRootNode();
            Node base = toValue().getBaseNode();
            Node old = base.getParent();

            int rootIndex = root.toValue().getIndex();
            int baseIndex = base.getIndex();
            int index = 0;

            boolean assignment = root != base && base instanceof Assignment && ((Assignment) base).getAssigneeNode() == root;
            boolean ternary = !assignment && root != base;

            String original = null;

            if (ternary) {
                if (isAccessed()) {
                    newParent = root.getParent();
                } else {
                    newParent = parent;
                }

                original = generateFlatInputUntil(this) + "?." + getAccessedNode().generateFlatInputUntil(null);

                index = rootIndex;
            } else {
                newParent = old;
                index = baseIndex;
            }

            Identifier accessed = getAccessedNode();

            setAccessedNode(null);

            BinaryOperation nullCheck = BinaryOperation.generateNullCheck(root.getParent(), toValue(), root.toValue());

            Node n = null;

            Variable local = TernaryOperation.getLocalVariableFromNullCheck(nullCheck).getDeclaration().generateUsableVariable(nullCheck, toValue().getLocationIn());
            local.setAccessedNode(accessed);
            local.declaration.setProperty("userMade", false);

            if (ternary) {
                TernaryOperation t = TernaryOperation.generateDefault(old, toValue().getLocationIn());

                t.setProperty("safeNavigation", original);

                Priority p = Priority.generateFrom(local);

                t.getCondition().replaceWith(nullCheck);
                t.getTrueValue().replaceWith(p);
                t.getFalseValue().replaceWith(Literal.generateDefaultValue(t, t.getLocationIn(), local.getReturnedNode()));

                t.setType(local.getReturnedNode().getFlatTypeValue(local.getReturnedNode()));

                n = Priority.generateFrom(t);
            } else {
                IfStatement s = IfStatement.generateDefault(old, toValue().getLocationIn());

                s.getCondition().replaceWith(nullCheck);

                if (assignment) {
                    ((Assignment) base).getAssigneeNodes().addChild(local);

                    s.addChild(base);
                } else {
                    s.addChild(local);
                }

                n = s;
            }

            newParent.addChild(index, n, newParent);

            return n;
        }

        return (Node) this;
    }

    public default StringBuilder generateAccessedNode(StringBuilder builder, boolean safeNavigation) {
        if (doesAccess()) {
            if (safeNavigation) {
                builder.append('?');
            }

            if (getAccessedNode().isChainNavigation()) {
                builder.append(':');
            } else {
                builder.append('.');
            }

            builder.append(getAccessedNode().generateFlatInput());
        }

        return builder;
    }

    /**
     * Get the furthest node that is accessing the specified identifier.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * tree.next.calculateSize()</pre></blockquote>
     * In the previous statement, "<code>tree</code>" is the context. This
     * differs from the getContextNode() call by returning the specified
     * node if the identifier is not accessed by any other identifiers.<br>
     * For example:
     * <blockquote><pre>
     * calculateSize()</pre></blockquote>
     * The above would return the method call identifier for
     * "<code>calculateSize()</code>".
     *
     * @return The furthest node that accesses the specified identifier.
     */
    default Accessible getRootAccessNode() {
        return getContextNode(this);
    }

    /**
     * Get the root Node that accesses the specified Identifier. This
     * only differs from getRootAccessNode() in one aspect: If the
     * root access Node's parent is an Instantiation, return the
     * Instantiation and not the MethodCall instance.
     *
     * @return The root Node that accesses the specified Identifier.
     */
    default Accessible getRootNode() {
        Accessible node = getRootAccessNode();
        Node n = (Node) node;

        if (n.getParent() instanceof Instantiation) {
            return (Instantiation) n.getParent();
        }

        return node;
    }

    /**
     * Get the last node that is accessed by the specified node.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * tree.next.calculateSize()</pre></blockquote>
     * In the previous statement, "<code>calculateSize()</code>" is the
     * last accessed node.
     *
     * @return The last node that is accessed by the specified node.
     */
    default Identifier getLastAccessedNode() {
        Identifier prev = null;
        Identifier node = getAccessedNode();

        while (node != null) {
            prev = node;
            node = node.getAccessedNode();
        }

        return prev;
    }

    /**
     * Get whether or not the Value accesses a method call.
     *
     * @return Whether or not the Value accesses a method call.
     */
    default boolean isSpecialFragment() {
//		Identifier lastAccessed = getLastAccessedNode();
//		
//		if (lastAccessed == null)
//		{
//			return false;
//		}
//		
//		Identifier next = (Identifier)lastAccessed.getNextAccessingOfType(new Class<?>[] { MethodCall.class, Closure.class });
//		
//		return next != null && next.isSpecial();

        Accessible current = getLastAccessedNode();

        while (current != this && current != null) {
            if (current.toValue().isSpecial()) {
                return true;
            }

            current = current.getAccessingNode(true);
        }

        return false;
    }

    /**
     * Get the Value that returns a value if it is used in an
     * expression.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * // Scenario 1
     * tree.value.next;
     *
     * //Scenario 2
     * tree;</pre></blockquote>
     * In scenario 1, "<u><code>next</code></u>" is the returned node
     * because it is the last accessed node. In scenario 2
     * "<u><code>tree</code></u>" is the returned node because it does not
     * access any nodes and is therefore the value that is returned.
     *
     * @return The last accessed node, or if the node does not access any
     * nodes, it returns itself.
     */
    default Value getReturnedNode() {
        Identifier lastAccessed = getLastAccessedNode();

        if (lastAccessed != null) {
            return lastAccessed.getReturnedNode();
        }

        return (Value) this;
    }

    /**
     * Get whether or not the specified Identifier accesses another Node.
     * For more information see {@link #getAccessedNode()}
     *
     * @return The next node that is accessed by the specified node.
     */
    default boolean doesAccess() {
        return getAccessedNode() != null;
    }

    /**
     * Get the next node that is accessed by the specified node.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * tree.next.calculateSize()</pre></blockquote>
     * In the previous statement, "<code>calculateSize()</code>" is the
     * accessed node of the "<code>next</code>" node, and
     * "<code>next</code>" is the accessed node of the "<code>tree</code>"
     * node.
     *
     * @return The next node that is accessed by the specified node.
     */
    default Identifier getAccessedNode() {
        Node n = (Node) this;

        if (n.getNumVisibleChildren() <= 0 || n.getVisibleChild(0) instanceof Identifier == false) {
            return null;
        }

        return (Identifier) n.getVisibleChild(0);
    }

    /**
     * Set the Identifier that this Identifier accesses.
     *
     * @param node The Identifier for this Identifier to access.
     */
    default void setAccessedNode(Identifier node) {
        Node n = (Node) this;

        if (doesAccess()) {
            n.replace(getAccessedNode(), node);
        } else if (node != null) {
            n.addChild(node);
        }
    }

    /**
     * Get whether this specified identifier node was accessed through
     * the dot operator of another Identifier.
     *
     * @return Whether or not the identifier was accessed.
     */
    default boolean isAccessed() {
        return getAccessingNode() != null;
    }

    default boolean canAccess() {
        Value n = (Value) this;

        return n.getParent() instanceof Parameter || n.getAncestorWithScope() != null;
    }

    /**
     * Get whether or not the specified identifier is still in the
     * process of being decoded.
     *
     * @param node The node that is thought to be accessed.
     * @return Whether or not the specified identifier is still in the
     * process of being decoded.
     */
    default boolean isDecodingAccessedNode(Node node) {
        return node.getParent() == this && !((Node) this).containsChild(node);
    }

    default Cast getCast() {
        if (!toValue().isDecoding() && getReturnedNode() == this) {
            Accessible root = getRootAccessNode();

            if (root.getParent() instanceof Cast) {
                Cast cast = (Cast) root.getParent();

                if (cast.getParent() instanceof Priority) {
                    Priority p = ((Priority) cast.getParent());

                    if (!p.getContents().isAncestorOf((Node) this)) {
                        return p.getCast();
                    }
                }

                return cast;
            }
        }

        return null;
    }

    default Cast getExplicitCast() {
        Cast c = getCast();

        while (c != null && !c.isExplicitCast()) {
            c = ((Accessible) c.getParent()).getCast();
        }

        return c;
    }

    /**
     * Get the Identifier Node that accesses the specified Identifier
     * Node. If the Identifier is not accessed through a Node, then
     * null is returned.
     *
     * @return The Identifier Node that accesses the specified Identifier
     * Node.
     */
    default Accessible getAccessingNode() {
        return getAccessingNode(false);
    }

    default ArrayList<Accessible> getAccessedNodes() {
        return getAccessedNodes(false);
    }

    default ArrayList<Accessible> getAccessedNodes(boolean inclusive) {
        ArrayList<Accessible> nodes = new ArrayList<>();
        Accessible current = inclusive ? this : getAccessedNode();

        while (current != null) {
            nodes.add(current);

            current = current.getAccessedNode();
        }

        return nodes;
    }

    default Accessible getAccessingNode(boolean skipPriority) {
        Node n = (Node) this;

        if (canAccess() && n.getParent() instanceof Accessible && n.getParent() instanceof VariableDeclaration == false && !n.getParent().containsScope()) {
            Accessible id = (Accessible) n.getParent();

            if (!skipPriority && id instanceof Priority) {
                Priority priority = (Priority) id;
                Node last = priority.getLastAncestorOfType(new Class[]{Priority.class}, false);

                last = last == null ? priority : last;

                if (this != priority.getContents() && priority.getReturnedContents() instanceof Accessible) {
                    return (Accessible) priority.getReturnedContents();
                } else if (last.getParent() instanceof Accessible && ((Accessible) last.getParent()).doesAccess()) {
                    return (Accessible) id.getParent();
                }
            }

            if (id instanceof TernaryOperation && ((TernaryOperation) id).isDecoding()) {

            } else if (id.isDecodingAccessedNode(n) || id.getAccessedNode() == this) {
                return id;
            }
        }

        return null;
    }

    default Accessible getCArgumentReferenceContext() {
        return this;
    }

    /**
     * Generate the Flat input String until the given stopAt Identifier
     * is found.
     *
     * @param stopAt The Node to stop at.
     * @return The generated Flat input.
     */
    default StringBuilder generateFlatInputUntil(Accessible stopAt) {
        return generateFlatInputUntil(new StringBuilder(), stopAt);
    }

    /**
     * Generate the Flat input String until the given stopAt Identifier
     * is found.
     *
     * @param builder The builder to append the data to.
     * @param stopAt  The Node to stop at. (This Node will output)
     * @return The generated Flat input.
     */
    default StringBuilder generateFlatInputUntil(StringBuilder builder, Accessible stopAt) {
        Node n = (Node) this;

        if (stopAt == null) {
            return n.generateFlatInput(builder, true);
        }

        stopAt = stopAt.getAccessedNode();

        Accessible current = this;

        while (current != stopAt) {
            ((Node) current).generateFlatInput(builder, false);

            if (current.doesAccess() && current.getAccessedNode().isChainNavigation()) {
                builder.append(':');
            } else {
                builder.append('.');
            }

            current = current.getAccessedNode();
        }

        return builder.deleteCharAt(builder.length() - 1);
    }
}