package flat.error;

import flat.Flat;
import flat.tree.Node;
import flat.util.Location;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Class that outputs an error of a specific type.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:28:12 PM
 * @version v0.2.14 Jul 19, 2014 at 7:33:13 PM
 */
public class SyntaxMessage {
    public static final HashMap<String, Constructor> ERROR_TYPES =
        new HashMap<String, Constructor>() {
            {
                put("type", TypeError.class.getConstructors()[0]);
            }
        };

    /**
     * Output an error message from the compiler.
     *
     * @param message The message describing the error.
     * @param controller The controller of the compiling program.
     */
    public static void error(String message, Flat controller) {
        Message error = new Message(message, controller);

        error.outputMessage(Message.ERROR);
    }

    /**
     * Output a warning message from the compiler.
     *
     * @param message The message describing the warning.
     * @param controller The controller of the compiling program.
     */
    public static void warning(String message, Flat controller) {
        Message error = new Message(message, controller);

        error.outputMessage(Message.WARNING);
    }

    /**
     * Output an error message from the compiler.
     *
     * @param message The message describing the error.
     * @param node The node that the error occurred from.
     */
    public static void error(String message, Node node) {
        error(message, node, null, null);
    }

    /**
     * Output a warning message from the compiler.
     *
     * @param message The message describing the warning.
     * @param node The node that the warning occurred from.
     */
    public static void warning(String message, Node node) {
        warning(message, node, null);
    }

    /**
     * Output an error message from the compiler.
     *
     * @param message The message describing the error.
     * @param node The node that the error occurred from.
     * @param throwException Whether or not to throw a SyntaxErrorException.
     */
    public static void error(String message, Node node, boolean throwException) {
        error(message, node, null, throwException);
    }

    /**
     * Output a warning message from the compiler.
     *
     * @param message The message describing the warning.
     * @param node The node that the warning occurred from.
     * @param throwException Whether or not to throw a SyntaxErrorException.
     */
    public static void warning(String message, Node node, boolean throwException) {
        warning(message, node, null, throwException);
    }

    /**
     * Output an error message from the compiler.
     *
     * @param message The message describing the error.
     * @param node The node that the error occurred from.
     * @param location The location that the error occurred at.
     */
    public static void error(String message, Node node, Location location) {
        error(message, node, location, null);
    }

    public static void error(String message, Node node, Location location, String type) {
        error(message, node, location, true, type);
    }

    /**
     * Output a warning message from the compiler.
     *
     * @param message The message describing the warning.
     * @param node The node that the warning occurred from.
     * @param location The location that the warning occurred at.
     */
    public static void warning(String message, Node node, Location location) {
        Message warning = new Message(message, node, location);

        warning.outputMessage(Message.WARNING);
    }

    /**
     * Output an error message from the compiler.
     *
     * @param message The message describing the error.
     * @param node The node that the error occurred from.
     * @param location The location that the error occurred at.
     * @param throwException Whether or not to throw a SyntaxErrorException.
     */
    public static void error(String message, Node node, Location location, boolean throwException) {
        error(message, node, location, throwException, null);
    }

    public static void error(String message, Node node, Location location, boolean throwException,
        String type) {
        Message error = new Message(message, node, location);

        error.outputMessage(Message.ERROR, throwException, type);
    }

    /**
     * Output a warning message from the compiler.
     *
     * @param message The message describing the warning.
     * @param node The node that the warning occurred from.
     * @param location The location that the warning occurred at.
     * @param throwException Whether or not to throw a SyntaxErrorException.
     */
    public static void warning(String message, Node node, Location location,
        boolean throwException) {
        Message warning = new Message(message, node, location);

        warning.outputMessage(Message.WARNING, throwException);
    }

    /**
     * Check whether or not to output an error message from the compiler. The output is dependent on
     * whether or not require is true. If require is false, this method simply returns false.
     *
     * @param message The message describing the error.
     * @param node The node that the error occurred from.
     * @param require Whether or not to follow through with the error output.
     */
    public static boolean queryError(String message, Node node, boolean require) {
        return queryError(message, node, require, null);
    }

    public static boolean queryError(String message, Node node, boolean require, String type) {
        if (!require) {
            return false;
        }

        error(message, node, null, require, type);

        return true;
    }

    /**
     * Check whether or not to output a warning message from the compiler. The output is dependent
     * on whether or not require is true. If require is false, this method simply returns false.
     *
     * @param message The message describing the warning.
     * @param node The node that the warning occurred from.
     * @param require Whether or not to follow through with the error output.
     */
    public static boolean queryWarning(String message, Node node, boolean require) {
        if (!require) {
            return false;
        }

        warning(message, node);

        return true;
    }

    /**
     * Check whether or not to output an error message from the compiler. The output is dependent on
     * whether or not require is true. If require is false, this method simply returns false.
     *
     * @param message The message describing the error.
     * @param node The node that the error occurred from.
     * @param throwException Whether or not to throw a SyntaxErrorException.
     * @param require Whether or not to follow through with the error output.
     */
    public static boolean queryError(String message, Node node, boolean throwException,
        boolean require) {
        if (!require) {
            return false;
        }

        error(message, node, throwException);

        return true;
    }

    /**
     * Check whether or not to output a warning message from the compiler. The output is dependent
     * on whether or not require is true. If require is false, this method simply returns false.
     *
     * @param message The message describing the warning.
     * @param node The node that the warning occurred from.
     * @param throwException Whether or not to throw a SyntaxErrorException.
     * @param require Whether or not to follow through with the error output.
     */
    public static boolean queryWarning(String message, Node node, boolean throwException,
        boolean require) {
        if (!require) {
            return false;
        }

        warning(message, node, throwException);

        return true;
    }

    /**
     * Check whether or not to output an error message from the compiler. The output is dependent on
     * whether or not require is true. If require is false, this method simply returns false.
     *
     * @param message The message describing the error.
     * @param node The node that the error occurred from.
     * @param location The location that the error occurred at.
     * @param require Whether or not to follow through with the error output.
     */
    public static boolean queryError(String message, Node node, Location location,
        boolean require) {
        if (!require) {
            return false;
        }

        error(message, node, location);

        return true;
    }

    /**
     * Check whether or not to output a warning message from the compiler. The output is dependent
     * on whether or not require is true. If require is false, this method simply returns false.
     *
     * @param message The message describing the warning.
     * @param node The node that the warning occurred from.
     * @param location The location that the warning occurred at.
     * @param require Whether or not to follow through with the error output.
     */
    public static boolean queryWarning(String message, Node node, Location location,
        boolean require) {
        if (!require) {
            return false;
        }

        warning(message, node, location);

        return true;
    }

    /**
     * Check whether or not to output an error message from the compiler. The output is dependent on
     * whether or not require is true. If require is false, this method simply returns false.
     *
     * @param message The message describing the error.
     * @param node The node that the error occurred from.
     * @param location The location that the error occurred at.
     * @param throwException Whether or not to throw a SyntaxErrorException.
     * @param require Whether or not to follow through with the error output.
     */
    public static boolean queryError(String message, Node node, Location location,
        boolean throwException, boolean require) {
        if (!require) {
            return false;
        }

        error(message, node, location, throwException);

        return true;
    }

    /**
     * Check whether or not to output a warning message from the compiler. The output is dependent
     * on whether or not require is true. If require is false, this method simply returns false.
     *
     * @param message The message describing the warning.
     * @param node The node that the warning occurred from.
     * @param location The location that the warning occurred at.
     * @param throwException Whether or not to throw a SyntaxErrorException.
     * @param require Whether or not to follow through with the error output.
     */
    public static boolean queryWarning(String message, Node node, Location location,
        boolean throwException, boolean require) {
        if (!require) {
            return false;
        }

        warning(message, node, location, throwException);

        return true;
    }
}

