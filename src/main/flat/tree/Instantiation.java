package flat.tree;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.variables.Array;
import flat.tree.variables.VariableDeclaration;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import java.util.Arrays;

/**
 * Value extension that represents the declaration of an
 * instantiation node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 *
 * @author Braden Steffaniak
 * @since v0.1 Apr 3, 2014 at 7:53:35 PM
 * @version v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class Instantiation extends IIdentifier implements GenericCompatible {
    /**
     * @see Node#Node(Node, Location)
     */
    public Instantiation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        GenericTypeArgumentList implementation = new GenericTypeArgumentList(temporaryParent, locationIn.asNew());
        addChild(implementation);
    }

    /**
     * @see Node#getNumDefaultChildren()
     */
    @Override
    public int getNumDefaultChildren() {
        return super.getNumDefaultChildren() + 1;
    }

    /**
     * @see IIdentifier#getArrayDimensions()
     */
    @Override
    public int getArrayDimensions() {
        if (getIdentifier() == null) {
            return 0;
        }
        return getIdentifier().getArrayDimensions() - getArrayAccessDimensions();
    }

    /**
     * @see GenericCompatible#getGenericTypeArgumentList()
     */
    @Override
    public GenericTypeArgumentList getGenericTypeArgumentList() {
        if (getNumChildren() > super.getNumDefaultChildren() + 1) {
            if (getIdentifier() instanceof MethodCall) {
                return ((MethodCall) getIdentifier()).getMethodGenericTypeArgumentList();
            } else {
                return getIdentifier().getGenericTypeArgumentList();
            }
        }

        return (GenericTypeArgumentList) getChild(super.getNumDefaultChildren() + 0);
    }

    @Override
    public ClassDeclaration getDeclaringClass() {
        if (isAccessed()) {
            return getAccessingNode().getDeclaringClass();
        }

        return getFileDeclaration().getImportedClass(this, getType());
    }

    /**
     * @see Node#getNumDecodedChildren()
     */
    @Override
    public int getNumDecodedChildren() {
        return super.getNumDecodedChildren() + 1;
    }

    /**
     * Get the node represents the type of instance that is being
     * instantiated. For example, a method call or an array
     * initialization node.
     *
     * @return The node that represents the type of instance that is
     * being instantiated.
     */
    public Identifier getIdentifier() {
        return getNumChildren() > super.getNumDefaultChildren() + 1 ? (Identifier) getChild(super.getNumDefaultChildren() + 1) : null;
    }

    /**
     * @see Node#addChild(Node)
     */
    @Override
    public void addChild(Node child) {
        if (getNumChildren() < getNumDecodedChildren()) {
            super.addChild(child);
        } else {
            getIdentifier().addChild(child);
        }
    }

    /**
     * @see Identifier#getAccessedNode()
     */
    public Identifier getAccessedNode() {
        return getIdentifier().getAccessedNode();
    }

    public String getType() {
        if (getNumVisibleChildren() < 2) {
            return super.getType();
        }

        return getIdentifier().getType();
    }

    @Override
    public void setTypeValue(String type) {
        if (getIdentifier() instanceof MethodCall) {
            MethodCall call = (MethodCall) getIdentifier();
            ClassDeclaration impor = getFileDeclaration().getImportedClass(getParentClass(), type);

            MethodDeclaration method = null;

            FlatMethodDeclaration decl = call.getFlatMethod();

            if (decl != null) {
                for (FlatMethodDeclaration m : decl.correspondingPrimitiveOverloads) {
                    if (m.getParentClass() == impor) {
                        method = m;

                        break;
                    }
                }
            }

            if (method == null) {
                method = impor.getMethod(this, type, call.getArgumentList());
            }

            call.declaration = method;
        } else if (getIdentifier() != null) {
            getIdentifier().setType(type);
            getIdentifier().setName(type);
        }

        setName(type);

        super.setTypeValue(type);
    }

    /**
     * Decode the given statement into an Instantiation instance, if
     * possible. If it is not possible, this method returns null.<br>
     * Instantiations always begin with the 'new' keyword.
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>new Person("Joe")</li>
     * 	<li>new Armadillo()</li>
     * 	<li>new String("asdf", 32)</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  Instantiation instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a Instantiation.
     */
    public static Instantiation decodeStatement(Node parent, String statement, Location location, boolean require) {
        return decodeStatement(parent, statement, location, require, true);
    }

    /**
     * Decode the given statement into an Instantiation instance, if
     * possible. If it is not possible, this method returns null.<br>
     * Instantiations always begin with the 'new' keyword.
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>new Person("Joe")</li>
     * 	<li>new Armadillo()</li>
     * 	<li>new String("asdf", 32)</li>
     * </ul>
     *
     * @param parent         The parent node of the statement.
     * @param statement      The statement to try to decode into a
     *                       Instantiation instance.
     * @param location       The location of the statement in the source code.
     * @param require        Whether or not to throw an error if anything goes wrong.
     * @param validateAccess Whether or not to check if method call can be
     *                       accessed legally.
     * @return The generated node, if it was possible to translated it
     * into a Instantiation.
     */
    public static Instantiation decodeStatement(Node parent, String statement, Location location, boolean require, boolean validateAccess) {
        return decodeStatement(parent, statement, location, require, validateAccess, null);
    }

    public static Instantiation decodeStatement(Node parent, String statement, Location location, boolean require, boolean validateAccess, CallableMethod reference) {
        Instantiation n = new Instantiation(parent, location);

        String[][] modifierData = SyntaxTree.getPrecedingModifiers(statement, parent, location, 0, 1);

        if (modifierData != null) {
            statement = modifierData[0][0];
        }

        if (modifierData != null) {
            if (!Arrays.stream(modifierData[1]).allMatch(n::parseModifier)) {
                return null;
            }
        }

        return n.decodeInstantiation(statement, location, require, validateAccess, reference);
    }

    /**
     * Decode the given instantiation<br>
     * <br>
     * For example: "<code>String()</code>"
     *
     * @param instantiation  The instantiation as exemplified above.
     * @param location       The location that the instantiation occurred.
     * @param require        Whether or not the given statement is the beginning of
     *                       a scope.
     * @param validateAccess Whether or not to check if method call can be
     *                       accessed legally.
     * @return The generated Instantiation.
     */
    private Instantiation decodeInstantiation(String instantiation, Location location, boolean require, boolean validateAccess) {
        return decodeInstantiation(instantiation, location, require, validateAccess, null);
    }

    private Instantiation decodeInstantiation(String instantiation, Location location, boolean require, boolean validateAccess, CallableMethod reference) {
        Identifier child = null;
        String params = null;
        Bounds bounds = StringUtils.findContentBoundsWithin(instantiation, VariableDeclaration.GENERIC_START, VariableDeclaration.GENERIC_END, 0, false);

        if (bounds.isValid()) {
            params = bounds.extractString(instantiation);
            bounds = StringUtils.findContentBoundsWithin(instantiation, VariableDeclaration.GENERIC_START, VariableDeclaration.GENERIC_END, 0);

            //instantiation = bounds.trimString(instantiation);
        }

        if (bounds.isValid()) {
            //decodeGenericTypeArguments(params);
        }

        String className = null;

        if (SyntaxUtils.isMethodCall(instantiation)) {
            className = StringUtils.findNextWord(instantiation);

//			setTypeValue(className);
//			
//			ClassDeclaration clazz = getFileDeclaration().getImportedClass(this, className);
//			
//			instantiation = clazz.getName() + instantiation.substring(className.length());
//			
            MethodCall methodCall = MethodCall.decodeStatement(this, instantiation, location, require, validateAccess, reference, true);

            if (methodCall == null) {
                return null;
            } else {
                ClassDeclaration callType = methodCall.getTypeClass();
                ClassDeclaration type = getTypeClass();

                if (callType != null) {
                    setTypeValue(className);

                    if (!callType.isOfType(getTypeClass()) && type != null && type.getConstructorList().getNumVisibleChildren() > 0) {
                        MethodCall.decodeStatement(this, instantiation, location, require, validateAccess, reference);
                        SyntaxMessage.queryError("Incompatible arguments given to " + getName() + " constructor", this, require);

                        return null;
                    }
                }
            }

//			methodCall.declaration.getGenericTypeArgumentList().cloneChildrenTo(getGenericTypeArgumentList());

            child = methodCall;
        } else if (SyntaxUtils.isArrayInitialization(instantiation)) {
            Value value = Array.decodeStatement(getParent(), instantiation, location, require);

            if (value != null) {
                setTypeValue(value.getType());

                if (value instanceof Instantiation) {
                    return (Instantiation) value;
                } else if (value instanceof Array) {
                    child = (Array) value;
                }
            } else {
                return null;
            }
        }

        if (child == null) {
            SyntaxMessage.queryError("Unable to parse instantiation of '" + instantiation + "'", this, require);

            return null;
        }

        addChild(child);
        setName(className);

        setDataType(child.getDataType());

        return this;
    }

    /**
     * @see Node#generateFlatInput(StringBuilder, boolean)
     */
    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        return builder.append(getIdentifier().generateFlatInput(outputChildren));
    }

//	/**
//	 * @see flat.tree.Identifier#generateCSourceName(java.lang.StringBuilder, java.lang.String)
//	 */
//	@Override
//	public StringBuilder generateCSourceName(StringBuilder builder, String uniquePrefix)
//	{
//		return builder.append(getName());
//	}

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Instantiation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Instantiation node = new Instantiation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Instantiation cloneTo(Instantiation node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Instantiation} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Instantiation cloneTo(Instantiation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the Instantiation class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}