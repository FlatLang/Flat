package flat.tree;

import flat.TestContext;
import flat.util.Location;
import flat.util.SyntaxUtils;

import java.util.Arrays;

/**
 * Node extension that contains children of the type Import.
 * Contains all of a files imports.
 *
 * @author Braden Steffaniak
 * @since v0.1 Apr 2, 2014 at 8:49:52 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class ImportList extends TypeList<Import> {
    /**
     * @see Node#Node(Node, Location)
     */
    public ImportList(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Get whether or not the given location has been imported.
     *
     * @param importLocation The location of the import.
     * @return Whether or not the given location has been imported.
     */
    public boolean containsImport(String importLocation) {
        return getImport(importLocation) != null;
    }

    /**
     * Get the Import node with the given import location, if it exists.
     *
     * @param importLocation The location of the import.
     * @return The Import with the specified import location, if it
     * exists.
     */
    public Import getImport(String importLocation) {
        return getImport(importLocation, true);
    }

    /**
     * Get the Import node with the given import location, if it exists.
     *
     * @param importLocation The location of the import.
     * @return The Import with the specified import location, if it
     * exists.
     */
    public Import getImport(String importLocation, boolean absoluteLocation) {
        return getImport(importLocation, true, true);
    }

    /**
     * Get the Import node with the given import location, if it exists.
     *
     * @param importLocation The location of the import.
     * @return The Import with the specified import location, if it
     * exists.
     */
    public Import getImport(String importLocation, boolean absoluteLocation, boolean aliased) {
        if (importLocation == null) {
            return null;
        }

        for (int i = 0; i < getNumVisibleChildren(); i++) {
            Import child = getVisibleChild(i);

            if (child.isPackageImport()) {
                continue;
            }

            String location = child.getClassLocation(aliased);

            if (!absoluteLocation) {
                location = SyntaxUtils.getClassName(location, false);
            }

            if (importLocation.equals(location)) {
                child.markUsed();

                return child;
            }
        }

        return null;
    }

    public String getAbsoluteClassLocation(String className) {
        return getAbsoluteClassLocation(className, false);
    }

    public String getAbsoluteClassLocation(String className, boolean aliased) {
        if (className == null) {
            return null;
        }
        if (SyntaxUtils.isAbsoluteClassLocation(className)) {
            return className;
        }

        String rootClass = className;
        String childClass = "";

        if (className.contains(".")) {
            rootClass = className.substring(0, className.indexOf('.'));
            childClass = className.substring(className.indexOf('.') + 1);
        }

        ClassDeclaration clazz = getFileDeclaration().getClassDeclaration(rootClass);

        if (clazz != null) {
            ClassDeclaration childClazz = checkForChildClass(clazz, childClass);

            if (childClazz != null) {
                return childClazz.getClassLocation();
            }
        }

        Import i = getImport(className, false, true);

        if (i != null) {
            return i.getClassLocation(aliased);
        }

        i = getImport(rootClass, false, true);

        if (i == null) {
            return null;
        }

        if (!rootClass.equals(className)) {
            clazz = checkForChildClass(i.getClassDeclaration(), childClass);

            if (clazz != null) {
                return clazz.getClassLocation();
            } else {
                return null;
            }
        } else {
            return i.getClassLocation(aliased);
        }
    }

    private ClassDeclaration checkForChildClass(ClassDeclaration clazz, String childClass) {
        while (childClass.length() > 0) {
            String current = childClass.contains(".") ? childClass.substring(0, childClass.indexOf('.')) : childClass;

            clazz = Arrays.stream(clazz.getEncapsulatedClasses())
                .filter(c -> c.getName().equals(current))
                .findFirst()
                .orElse(null);

            if (clazz == null) {
                break;
            }

            if (childClass.contains(".")) {
                childClass = childClass.substring(childClass.indexOf('.') + 1);
            } else {
                childClass = "";
            }
        }

        return clazz;
    }

    /**
     * Get whether or not the given location is an external C import.
     *
     * @return Whether or not the given location is an external C import.
     */
    public boolean isExternal(String importLocation) {
        for (int i = 0; i < getNumChildren(); i++) {
            Import child = (Import) getChild(i);

            if (child.isExternal() && child.getClassLocation().equals(importLocation)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ImportList clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ImportList node = new ImportList(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ImportList cloneTo(ImportList node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ImportList} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ImportList cloneTo(ImportList node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the ImportList class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}