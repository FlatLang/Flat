package flat.util;

/**
 * Class that is used to listen to Commands.
 *
 * @version Mar 20, 2013 at 7:14:06 PM
 * @author Braden Steffaniak
 * @since Mar 20, 2013 at 7:14:06 PM
 * @since v0.1
 * @version v0.1
 */
public interface CommandListener {
    public void resultReceived(int result);

    public void commandExecuted();

    public void messageReceived(String message);

    public void errorReceived(String message);
}

