package flat.error;

/**
 * Runtime Exception that is thrown if an operation has not been
 * implemented yet, or ever.
 *
 * @author Braden Steffaniak
 * @since v0.2.13 Jun 8, 2014 at 3:11:16 PM
 * @version v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class UnimplementedOperationException extends RuntimeException {
    /**
     * Generate an exception that outputs a given error message.
     *
     * @param message The error message to be output.
     */
    public UnimplementedOperationException(String message) {
        super(message);
    }
}