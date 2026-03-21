package flat.tree;

import flat.TestContext;
import flat.util.Location;

import java.util.ArrayList;
import java.util.function.Consumer;


/**
 * {@link Node} extension that represents all of the Methods within a class.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 10:29:22 PM
 * @version v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class MethodList extends TypeList<MethodDeclaration> {
    /**
     * @see Node#Node(Node, Location)
     */
    public MethodList(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Get whether or not the ClassDeclaration contains the Method with the specified name.<br>
     * <br>
     * For example: <blockquote>
     * 
     * <pre>
     * public class ClassName
     * {
     * 	public void doSomething()
     *    {
     * 		...
     *    }
     * }
     * </pre>
     * 
     * </blockquote> <br>
     * A call like: "<code>containsMethod("doSomething")</code>" would return true.
     *
     * @param methodName The name of the method to search for.
     * @return Whether or not the ClassDeclaration contains the Method with the specified name.
     */
    public boolean containsMethod(String methodName) {
        return getMethods(methodName, SearchFilter.DEFAULT).length > 0;
    }

    public void forEachFlatMethod(Consumer<FlatMethodDeclaration> action) {
        for (int i = 0; i < getNumVisibleChildren(); i++) {
            MethodDeclaration method = getVisibleChild(i);

            if (method instanceof FlatMethodDeclaration) {
                action.accept((FlatMethodDeclaration) method);
            }
        }
    }

    /**
     * Get the ClassDeclaration's Method with the specified name.<br>
     * <br>
     * For example: <blockquote>
     * 
     * <pre>
     * public class ClassName
     * {
     * 	public void doSomething()
     *    {
     * 		...
     *    }
     * }
     * </pre>
     * 
     * </blockquote> <br>
     * A call like: "<code>getMethod("doSomething")</code>" would return the Method for the
     * "<code>doSomething</code>" method.
     *
     * @param methodName The name of the method to search for.
     * @return The Method for the method, if it exists.
     */
    public MethodDeclaration[] getMethods(String methodName, SearchFilter filter) {
        ArrayList<MethodDeclaration> methods = new ArrayList<>();

        for (int i = 0; i < getNumChildren(); i++) {
            MethodDeclaration method = getChild(i);

            if (method.getName() != null && method.getName().equals(methodName)
                && (!filter.checkStatic || filter.staticValue == method.isStatic())) {
                methods.add(method);
            }
        }

        return methods.toArray(new MethodDeclaration[0]);
    }

    public FlatMethodDeclaration[] getMethods() {
        ArrayList<FlatMethodDeclaration> methods = new ArrayList<>();

        for (int i = 0; i < getNumVisibleChildren(); i++) {
            MethodDeclaration method = getVisibleChild(i);

            if (method instanceof FlatMethodDeclaration
                && (method instanceof PropertyMethod == false
                    || !((PropertyMethod) method).isDisabled())) {
                methods.add((FlatMethodDeclaration) method);
            }
        }

        return methods.toArray(new FlatMethodDeclaration[0]);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public MethodList clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        MethodList node = new MethodList(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public MethodList cloneTo(MethodList node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link MethodList} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public MethodList cloneTo(MethodList node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the MethodList class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    /**
     * @author Braden Steffaniak
     * @since v0.2.28 Aug 8, 2014 at 12:35:41 PM
     * @version v0.2.28 Aug 8, 2014 at 12:35:41 PM
     */
    public static class SearchFilter {
        public boolean checkAncestor, requireExactMatch, checkStatic, defaultGeneric, staticValue,
            checkInterfaces, checkConstructors, checkProperties, allowMoreParameters,
            requireEqualParameterCount;

        public String className;

        private static final SearchFilter DEFAULT = new SearchFilter();

        public SearchFilter() {
            checkAncestor = true;
            requireExactMatch = false;
            checkStatic = false;
            defaultGeneric = false;
            checkInterfaces = true;
            checkConstructors = true;
            checkProperties = false;
            allowMoreParameters = false;
            requireEqualParameterCount = false;
        }

        public static final SearchFilter getDefault() {
            DEFAULT.className = null;

            return DEFAULT;
        }

        public void checkStatic(boolean staticValue) {
            this.staticValue = staticValue;

            checkStatic = true;
        }
    }

    public String toString() {
        if (getNumVisibleChildren() == 0) {
            return "(none)";
        }

        String str = "";

        for (MethodDeclaration method : this) {
            if (str.length() > 0) {
                str += "\n";
            }

            str += method.toString();
        }

        return str;
    }
}

