package flat.tree.match;

import flat.tree.Node;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 * @since v0.2.37 Oct 17, 2014 at 11:46:55 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public interface MatchChild {
    /**
     * Get the {@link Match Match} that contains the specified MatchChild.
     *
     * @return The Match statement that contains the specified MatchChild.
     */
    public default Match getParentMatch() {
        return (Match) ((Node) this).getAncestorOfType(Match.class);
    }
}

