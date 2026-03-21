package flat.util;

/**
 * Class that contains information for a line number and character offset. Used in the compilation
 * process, in the event that there is an error or warning, for knowing where exactly it occurred in
 * the source code.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 7, 2014 at 10:11:40 AM
 * @version v0.2.39 Dec 7, 2014 at 3:17:17 AM
 */
public class Location {
    private int lineNumber, offset;

    private Bounds bounds;

    public static final Location INVALID = new Location(0, 0, 0, 0);

    /**
     * Default constructor.
     */
    public Location() {
        bounds = new Bounds(0, 0);
    }

    /**
     * Copy the location data from the given Location variable into a newly instantiated Location
     * instance.
     *
     * @param loc The location to copy the data from.
     */
    public Location(Location loc) {
        this(0, loc.offset, loc.bounds.getStart(), loc.bounds.getEnd());
    }

    /**
     * Constructor that initializes the lineNumber and offset to the given values.
     *
     * @param lineNumber The lineNumber that the Location represents.
     * @param offset The character offset horizontally on the current line.
     * @param start The character offset that the Location represents as the start throughout the
     *        whole source text.
     * @param end The character offset that the Location represents as the end throughout the whole
     *        source text.
     */
    public Location(int lineNumber, int offset, int start, int end) {
        // Initialize default data.
        this();

        this.offset = offset;
        setLineNumber(lineNumber);
        setBounds(start, end);
    }

    /**
     * Get the line number that the Location represents.
     *
     * @return The line number that the Location represents.
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Set the line number that the Location represents.
     *
     * @param lineNumber The line number that the Location represents.
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Get the character offset that the Location represents as the start throughout the whole
     * source text.
     *
     * @return The character offset that the Location represents as the start throughout the whole
     *         source text.
     */
    public int getStart() {
        return bounds.getStart();
    }

    /**
     * Get the character offset that the Location represents as the end throughout the whole source
     * text.
     *
     * @return The character offset that the Location represents as the end throughout the whole
     *         source text.
     */
    public int getEnd() {
        return bounds.getEnd();
    }

    /**
     * Get character offset that the Location represents on the specified line number.
     *
     * @return The character offset that the Location represents on the specified line number.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Set character offset that the Location represents on the specified line number.
     *
     * @param offset The character offset that the Location represents on the specified line number.
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Add the given amount to each of the Location's offset value.<br>
     * <br>
     * This is the same as calling the following: <blockquote>
     * 
     * <pre>
     * bounds.setOffset(bounds.getOffset() + amount);
     * </pre>
     * 
     * </blockquote>
     *
     * @param amount The amount to add to the Location's offset.
     */
    public void addOffset(int amount) {
        offset += amount;
    }

    /**
     * Subtract the given amount from each of the Location's offset value.<br>
     * <br>
     * This is the same as calling the following: <blockquote>
     * 
     * <pre>
     * bounds.setOffset(bounds.getOffset() - amount);
     * </pre>
     * 
     * </blockquote>
     *
     * @param amount The amount to subtract from the Location's offset.
     */
    public void subtractOffset(int amount) {
        offset -= amount;
    }

    /**
     * Get the Bounds of the Location's offset. The starting and ending offsets.
     *
     * @return The Bounds instance containing the starting and ending offsets.
     */
    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    /**
     * Set the Bounds that the location encapsulates.
     *
     * @param start The character offset that the Location represents as the start on the specified
     *        line.
     * @param end The character offset that the Location represents as the end on the specified
     *        line.
     */
    public void setBounds(int start, int end) {
        bounds.setStart(start);
        bounds.setEnd(end);
    }

    /**
     * Add the given amount to each of the Bounds's start and end values.<br>
     * <br>
     * This is the same as calling the following: <blockquote>
     * 
     * <pre>
     * bounds.setStart(bounds.getStart() + amount);
     * bounds.setEnd(bounds.getEnd() + amount);
     * </pre>
     * 
     * </blockquote>
     *
     * @param amount The amount to add to the Bounds's values.
     */
    public void addBounds(int amount) {
        bounds.setStart(bounds.getStart() + amount);
        bounds.setEnd(bounds.getEnd() + amount);
    }

    /**
     * Add the given amounts to each of the Bounds's start and end values.<br>
     * <br>
     * This is the same as calling the following: <blockquote>
     * 
     * <pre>
     * bounds.setStart(bounds.getStart() + startAmount);
     * bounds.setEnd(bounds.getEnd() + endAmount);
     * </pre>
     * 
     * </blockquote>
     *
     * @param startAmount The amount to add to the Bounds's start value.
     * @param endAmount The amount to add to the Bounds's end value.
     */
    public void moveBounds(int startAmount, int endAmount) {
        bounds.setStart(bounds.getStart() + startAmount);
        bounds.setEnd(bounds.getEnd() + endAmount);
    }

    /**
     * Subtract the given amounts to each of the Bounds's start and end values.<br>
     * <br>
     * This is the same as calling the following: <blockquote>
     * 
     * <pre>
     * bounds.setStart(bounds.getStart() - startAmount);
     * bounds.setEnd(bounds.getEnd() - endAmount);
     * </pre>
     * 
     * </blockquote>
     *
     * @param startAmount The amount to subtract to the Bounds's start value.
     * @param endAmount The amount to subtract to the Bounds's end value.
     */
    public void subtractBounds(int startAmount, int endAmount) {
        bounds.setStart(bounds.getStart() - startAmount);
        bounds.setEnd(bounds.getEnd() - endAmount);
    }

    /**
     * Subtract the given amount from each of the Bounds's start and end values.<br>
     * <br>
     * This is the same as calling the following: <blockquote>
     * 
     * <pre>
     * bounds.setStart(bounds.getStart() - amount);
     * bounds.setEnd(bounds.getEnd() - amount);
     * </pre>
     * 
     * </blockquote>
     *
     * @param amount The amount to subtract from the Bounds's values.
     */
    public void subtractBounds(int amount) {
        bounds.setStart(bounds.getStart() - amount);
        bounds.setEnd(bounds.getEnd() - amount);
    }

    /**
     * Add the given amounts to each of the Bounds's start and end values.<br>
     * <br>
     * This is the same as calling the following: <blockquote>
     * 
     * <pre>
     * bounds.setStart(bounds.getStart() + startAmount);
     * bounds.setEnd(bounds.getStart() + endAmount);
     * </pre>
     * 
     * </blockquote>
     *
     * @param startAmount The amount to add as the Bounds's start value.
     * @param endAmount The amount to add as the Bounds's end value.
     */
    public void addBounds(int startAmount, int endAmount) {
        bounds.setStart(bounds.getStart() + startAmount);
        bounds.setEnd(bounds.getStart() + endAmount);
    }

    /**
     * Get whether the line number is a valid location in a source file, or just generated.
     *
     * @return Whether or not the Location is valid in the source file.
     */
    public boolean isValid() {
        return lineNumber > 0;
    }

    /**
     * Create a new Location that reflects the specified parent Location.
     *
     * @return A new Location reflecting the specified parent Location.
     */
    public Location asNew() {
        return new Location(this);
    }

    public String toString() {
        return "Line " + lineNumber + " " + bounds;
    }
}

