package flat;

import flat.tree.Node;

/**
 * Class used to hold information on how a validation method has resulted.
 *
 * @author Braden Steffaniak
 * @since v0.2.35 Oct 4, 2014 at 12:37:45 AM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class ValidationResult {
    public Node returnedNode, originalNode;

    public boolean errorOccurred;
    public boolean continueValidation;
    public boolean skipCycle;

    public int nextIncrement;

    /**
     * Create a new ValidationResult that has the expectation to return the given Node.
     *
     * @param returnedNode The Node that the validation method can expect to return.
     */
    public ValidationResult(Node returnedNode) {
        this.returnedNode = returnedNode;
        this.originalNode = returnedNode;

        this.continueValidation = true;
        this.errorOccurred = false;
        this.skipCycle = false;

        this.nextIncrement = 1;
    }

    public boolean skipValidation() {
        return errorOccurred || skipCycle || !continueValidation || returnedNode != originalNode;
    }

    /**
     * Declare that the ValidationResult has had an error occur.
     *
     * @param remove The Node to remove from the tree.
     * @return The ValidationResult that the error occurred on.
     */
    public ValidationResult errorOccurred(Node remove) {
        // remove.detach();

        return errorOccurred();
    }

    /**
     * Declare that the ValidationResult has had an error occur.
     *
     * @return The ValidationResult that the error occurred on.
     */
    public ValidationResult errorOccurred() {
        errorOccurred = true;
        skipCycle = true;
        returnedNode = null;

        return this;
    }

    public ValidationResult skippedCycle() {
        skipCycle = false;

        return this;
    }

    public int increment() {
        int temp = nextIncrement;

        nextIncrement = 1;

        return temp;
    }
}

