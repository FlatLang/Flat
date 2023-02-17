package flat.tree;

import flat.TestContext;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.variables.Variable;
import flat.util.Location;
import flat.util.SyntaxUtils;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * {@link IIdentifier} extension that represents the use
 *
 * @author Braden Steffaniak
 * @since v0.2.9 Aug 23, 2014 at 11:25:19 PM
 * @version v0.2.34 Oct 1, 2014 at 9:51:33 PM
 */
public class ReifiedParameterAccess extends IIdentifier {
    /**
     * @see Node#Node(Node, Location)
     */
    public ReifiedParameterAccess(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  {@link ReifiedParameterAccess} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link ReifiedParameterAccess}.`
     */
    public static ReifiedParameterAccess decodeStatement(Node parent, String statement, Location location, boolean require) {
        ClassDeclaration refClass = parent instanceof Accessible && ((Accessible) parent).canAccess() ? ((Accessible) parent).toValue().getTypeClass() : parent.getParentClass(true);

        if (refClass != null) {
            GenericTypeParameter param = refClass.getGenericTypeParameter(statement);

            if (param != null && param.isReified()) {
                ReifiedParameterAccess n = new ReifiedParameterAccess(parent, location);

                n.setName(statement);
                n.setType(statement);

                return n;
            }
        }

        return null;
    }

    @Override
    public ClassDeclaration getTypeClass(boolean checkCast, boolean defaultGenericType) {
        if (getParent() instanceof ReifiedParameterAccess) {
            ReifiedParameterAccess ref = (ReifiedParameterAccess) getParent();
            ClassDeclaration typeClass = ref.getTypeClass(checkCast, defaultGenericType);
            ClassDeclaration tc = Stream.concat(
                    Arrays.stream(typeClass.getEncapsulatedClasses()),
                    Arrays.stream(typeClass.getSiblingClasses())
                )
                .filter(c -> c.getName().equals(getName()))
                .findFirst()
                .orElse(null);

            return tc;
        }

        return super.getTypeClass(checkCast, defaultGenericType);
    }

    @Override
    public String getTypeClassLocation(boolean checkCast) {
        if (getParent() instanceof ReifiedParameterAccess) {
            ReifiedParameterAccess ref = (ReifiedParameterAccess) getParent();
            ClassDeclaration typeClass = ref.getTypeClass(checkCast, false);
            ClassDeclaration tc = Stream.concat(
                    Arrays.stream(typeClass.getEncapsulatedClasses()),
                    Arrays.stream(typeClass.getSiblingClasses())
                )
                .filter(c -> c.getName().equals(getName()))
                .findFirst()
                .orElse(null);

            if (tc != null) {
                return tc.getClassLocation();
            }
        }

        return super.getTypeClassLocation(checkCast);
    }

    @Override
    public String getType(boolean checkCast) {
        return super.getType(checkCast);
    }

    @Override
    public GenericCompatible getContext() {
        return this;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean onAfterDecoded() {
        if (!doesAccess()) {
            Variable var = (Variable) SyntaxTree.decodeIdentifier(this, "class", getLocationIn(), false);

            if (var != null) {
//				setAccessedNode(var);
            }
        }

        return super.onAfterDecoded();
    }

    @Override
    public ClassDeclaration getDeclaringClass() {
        if (isAccessed()) {
            return getAccessingNode().getDeclaringClass();
        }

        return SyntaxUtils.getImportedClass(getFileDeclaration(), getName());
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ReifiedParameterAccess clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ReifiedParameterAccess node = new ReifiedParameterAccess(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ReifiedParameterAccess cloneTo(ReifiedParameterAccess node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ReifiedParameterAccess} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ReifiedParameterAccess cloneTo(ReifiedParameterAccess node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ReifiedParameterAccess} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
