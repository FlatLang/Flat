package flat.error;

/**
 * Runtime Exception that is thrown if there is an error that was decoded in the syntax.
 *
 * @author Braden Steffaniak
 * @since v0.2.8 May 26, 2014 at 4:24:19 PM
 * @version v0.2.35 Oct 5, 2014 at 11:22:42 PM
 */
public class TypeError extends SyntaxErrorException {
    /**
     * Generate an exception that outputs a given error message.
     *
     * @param info The error message to be output.
     */
    public TypeError(String info, int type) {
        super(info, type);
    }
}

