package flat.util;

/**
 * Class used to store data about a start and end location.
 *
 * @author Braden Steffaniak
 * @since v0.1 Mar 12, 2014 at 4:08:41 PM
 * @version v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class Bounds {
    public static final Bounds EMPTY = new EmptyBounds();

    private int start, end;

    /**
     * Create a Bounds instance with the specified start and
     * end location.
     *
     * @param start The start location of the Bound.
     * @param end   The end location of the Bound.
     */
    public Bounds(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * The start position of the Bounds.
     *
     * @return The start position.
     */
    public int getStart() {
        return start;
    }

    /**
     * Set the start position of the Bounds.
     *
     * @param start The new start position of the Bounds.
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * The end position of the Bounds.
     *
     * @return The end position.
     */
    public int getEnd() {
        return end;
    }

    /**
     * Set the end position of the Bounds.
     *
     * @param end The new end position of the Bounds.
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * Extract a substring from the given source at the specified bound's
     * start and end. This is equivalent to the following:
     * <blockquote><pre>source.substring(Bounds.getStart(), Bounds.getEnd())</pre></blockquote>
     *
     * @param source The String to get the substring from.
     * @return The extracted substring.
     */
    public String extractString(String source) {
        if (!isValid()) {
            return null;
        }

        return source.substring(getStart(), getEnd());
    }

    public String extractPreString(String source) {
        if (!isValid()) {
            return null;
        }

        return source.substring(0, getStart());
    }

    public String extractPostString(String source) {
        if (!isValid()) {
            return source;
        }

        return source.substring(getEnd());
    }

    public String trimString(String source) {
        if (!isValid()) {
            return source;
        }

        return extractPreString(source) + extractPostString(source);
    }

    /**
     * Get whether or not the bounds are endless.
     *
     * @return Whether or not the bounds will search endlessly.
     */
    public boolean isEndless() {
        return end < 0;
    }

    /**
     * Get whether or not the bounds are optional.
     *
     * @return Whether or not the bounds will search optionally.
     */
    public boolean isOptional() {
        return start == 0;
    }

    /**
     * Get the length of the Bounds. end - start.
     *
     * @return The length of the Bounds.
     */
    public int length() {
        return end - start;
    }

    /**
     * Get whether or not the bounds are positive and that they have
     * a length.
     *
     * @return Whether or not the Bounds is valid.
     */
    public boolean isValid() {
        return start >= 0 && end > 0 && start <= end;
    }

    public void setInvalid() {
        start = -1;
        end = -1;
    }

    /**
     * @param bounds The Bounds to check against.
     * @return Whether or not the two Bounds have equal values.
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Bounds bounds) {
        return bounds != null && bounds.getStart() == start && bounds.getEnd() == end;
    }

    /**
     * Generate a String representation of the Bounds Object
     * containing the start and end position of the Bounds.
     *
     * @return A String representation of the Bounds Object.
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[" + start + ", " + end + "]";
    }

    /**
     * Clone the values from the specified Bounds instance into the given
     * Bounds instance. This overwrites the existing values in the given
     * Bounds instance in favor for the specified Bounds's values.
     *
     * @param bounds The Bounds to set the values of.
     */
    public void cloneTo(Bounds bounds) {
        bounds.start = start;
        bounds.end = end;
    }

    /**
     * Clone the specified Bounds's values into a brand spankin' new
     * Bounds instance.
     *
     * @return The new Bounds instance.
     * @see java.lang.Object#clone()
     */
    public Bounds clone() {
        return new Bounds(start, end);
    }

    /**
     * Class specifically for the EMPTY Bounds constant. Prevents the
     * modification of the values.
     *
     * @author Braden Steffaniak
     * @since v0.2.13 Jun 17, 2014 at 3:03:39 PM
     * @version v0.2.13 Jun 17, 2014 at 3:03:39 PM
     */
    private static class EmptyBounds extends Bounds {
        public EmptyBounds() {
            super(-1, -1);
        }

        /**
         * @see Bounds#setStart(int)
         */
        public void setStart(int start) {
            throw new EmptyBoundsModificationException("You cannot modify the start value of the EMPTY Bounds instance.");
        }

        /**
         * @see Bounds#setEnd(int)
         */
        public void setEnd(int end) {
            throw new EmptyBoundsModificationException("You cannot modify the end value of the EMPTY Bounds instance.");
        }
    }

    /**
     * Exception thrown when the final EMPTY bounds is attempted to be
     * modified.
     *
     * @author Braden Steffaniak
     * @since v0.2.13 Jun 12, 2014 at 12:29:46 PM
     * @version v0.2.13 Jun 12, 2014 at 12:29:46 PM
     */
    public static class EmptyBoundsModificationException extends RuntimeException {
        /**
         * Create an exception for when the final EMPTY Bounds type is
         * attempted to be modified.
         *
         * @param message The message describing what was trying to be
         *                modified.
         */
        public EmptyBoundsModificationException(String message) {
            super(message);
        }
    }
}
