package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.util.Location;
import flat.util.StringUtils;

import java.io.File;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 * @since v0.2.31 Sep 24, 2014 at 11:13:15 AM
 * @version v0.2.32 Sep 26, 2014 at 12:17:33 PM
 */
public class Package extends Node {
    public String location;

    public static final String PACKAGE_KEYWORD = "package";

    /**
     * @see Node#Node(Node, Location)
     */
    public Package(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public String[] getFolders() {
        return isDefaultPackage() ? new String[0] : location.split("/");
    }

    public String getRootFolder() {
        return isDefaultPackage() ? null : getFolders()[0];
    }

    public String getLocation() {
        return location;
    }

    public boolean isDefaultPackage() {
        return location == null;
    }

    public File getParentFile() {
        String directories[] = getFolders();

        File current = getFileDeclaration().getFile().getParentFile();

        for (int i = directories.length - 1; i >= 0; i--) {
            String directory = directories[i];

            if (!current.isDirectory()) {
                return null;
            } else if (!directory.equals(current.getName())) {
                return null;
            }

            current = current.getParentFile();
        }

        return current;
    }

    /**
     * Decode the given statement into a {@link Package} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>package "some/package/name"</li>
     * 	<li>package "flat/standard"</li>
     * 	<li>package "com/example"</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  {@link Package} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link Package}.
     */
    public static Package decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (statement.startsWith(PACKAGE_KEYWORD)) {
            Package n = generateDefaultPackage(parent, location);

            statement = StringUtils.trimFirstWord(statement, true);

            if (StringUtils.isSurroundedByQuotes(statement)) {
                statement = StringUtils.removeSurroundingQuotes(statement);
            }

            n.location = statement;

            if (n.validatePackageExists(require)) {
                return n;
            }
        }

        return null;
    }

    /**
     * @see Node#validate(int)
     */
    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_CLASS_DECLARATION) {
            if (!validLocation() && !isDefaultPackage()) {
                result.errorOccurred();
                result.continueValidation = false;

                SyntaxMessage.error("Undefined package location", this, false);
            }
        }

        return result;
    }

    public boolean validLocation() {
        return location != null;
    }

    private boolean validatePackageExists(boolean require) {
        String directories[] = location.split("/");

        if (directories.length == 0 || directories.length == 1 && directories[0].length() <= 0) {
            throwIncorrectPackageException("Package location cannot be empty. Perhaps try using the default package");
        }

        if (getParentFile() == null) {
            getParentFile();
            throwIncorrectPackageException();
        }

        return true;
    }

    private void throwIncorrectPackageException() {
        throwIncorrectPackageException("The package location of \"" + location + "\" is not valid");
    }

    private void throwIncorrectPackageException(String message) {
        SyntaxMessage.error(message, this);
    }

    public static Package generateDefaultPackage(Node parent, Location location) {
        Package n = new Package(parent, location);

        return n;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Package clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Package node = new Package(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Package cloneTo(Package node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Package} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Package cloneTo(Package node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link Package} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}