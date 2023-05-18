package flat.tree;

import java.util.ArrayList;

/**
 * @author Braden Steffaniak
 * @since v0.2.37 Oct 18, 2014 at 9:00:50 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public interface Listenable {
    public default ArrayList<Listener> getListeners() {
        return null;
    }

    public default void onAdded(Node parent) {
        if (getListeners() == null) {
            return;
        }

        for (Listener l : getListeners()) {
            l.onAdded(parent, (Node) this);
        }
    }
}

