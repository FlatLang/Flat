package flat.error;

/**
 * Runtime Exception that is thrown if there is an error that was decoded
 * in the syntax.
 *
 * @author Braden Steffaniak
 * @since v0.2.8 May 26, 2014 at 4:24:19 PM
 * @version v0.2.35 Oct 5, 2014 at 11:22:42 PM
 */
public class SyntaxErrorException extends RuntimeException {
    private int type;

    private String info;

    /**
     * Generate an exception that outputs a given error message.
     *
     * @param info The error message to be output.
     */
    public SyntaxErrorException(String info, int type) {
        super(info);

        this.info = info;
        this.type = type;
    }

    /**
     * Get the type that the SyntaxErrorException is.<br>
     * Possible values include:
     * <ul>
     * 	<li>{@link Message#ERROR}</li>
     * 	<li>{@link Message#WARNING}</li>
     * 	<li>{@link Message#MESSAGE}</li>
     * </ul>
     *
     * @return The type that the SyntaxErrorException is.
     */
    public int getType() {
        return type;
    }

    /**
     * Get the info describing the error that occurred.
     *
     * @return The info describing the error that occurred.
     */
    public String getInfo() {
        return info;
    }
}