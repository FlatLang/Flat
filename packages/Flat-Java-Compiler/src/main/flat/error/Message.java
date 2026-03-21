package flat.error;

import flat.Flat;
import flat.tree.FileDeclaration;
import flat.tree.Node;
import flat.util.Location;

import java.lang.reflect.InvocationTargetException;

/**
 * Class that holds the information for a message that will be output from the compiler.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:28:08 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Message {
    private Node node;

    private FileDeclaration file;

    private Location location;

    private String message;

    private Flat controller;

    public static final int MESSAGE = 1, WARNING = 2, ERROR = 3;

    /**
     * Create a new message instance with the given message.
     *
     * @param message The message that describes what happened.
     * @param controller The controller of the compiling program.
     */
    public Message(String message, Flat controller) {
        this.message = message;
        this.controller = controller;
    }

    /**
     * Create a new message instance with the given message that is representing the given node.
     *
     * @param message The message that describes what happened.
     * @param node The node that the message is talking about.
     */
    public Message(String message, Node node, Location location) {
        this.file = node.getOriginalFile();
        this.node = node;
        this.location = location;
        this.message = message;
        this.controller = node.getController();
    }

    /**
     * Output a message from the compiler.
     *
     * @param type The type of message that is being output.
     */
    public void outputMessage(int type) {
        outputMessage(type, type == ERROR);
    }

    /**
     * Output a message from the compiler.
     *
     * @param type The type of message that is being output.
     * @param throwException Whether or not to throw a SyntaxErrorException.
     */
    public void outputMessage(int type, boolean throwException) {
        outputMessage(type, throwException, null);
    }

    public void outputMessage(int type, boolean throwException, String exceptionType) {
        String info = message;

        if (node != null) {
            String prefix = "";

            if (file.getPackage() != null && file.getPackage().getLocation() != null) {
                prefix = file.getPackage().getLocation() + "/";
            }

            info += " - in file \"" + prefix + file.getFile().getName() + '"';
        }

        if (type == MESSAGE) {
            controller.log(info);
        } else if (type == WARNING) {
            controller.warning(info);
        } else if (type == ERROR) {
            controller.error(info);
        }

        if (throwException) {
            SyntaxErrorException e = null;

            if (exceptionType != null) {
                try {
                    e = (SyntaxErrorException) SyntaxMessage.ERROR_TYPES.get(exceptionType)
                        .newInstance(info, type);
                } catch (InstantiationException ex) {
                    e.printStackTrace();
                } catch (IllegalAccessException ex) {
                    e.printStackTrace();
                } catch (InvocationTargetException ex) {
                    e.printStackTrace();
                }
            } else {
                e = new SyntaxErrorException(info, type);
            }

            throw e;
        }
    }
}

