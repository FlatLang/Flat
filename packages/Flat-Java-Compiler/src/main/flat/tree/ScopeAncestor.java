package flat.tree;

import java.util.HashMap;

/**
 * @author Braden Steffaniak
 * @since v0.2.29 Aug 23, 2014 at 9:35:01 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public interface ScopeAncestor {
    /**
     * Get a unique integer used for differentiating local variables within the scope.
     *
     * @return A unique identifier for local variables.
     */
    default int generateUniqueID(Scope scope) {
        int id = getUniqueID();

        getScopes().put(id, scope);

        return id;
    }

    public int getUniqueID();

    public HashMap<Integer, Scope> getScopes();

    default Scope getScope(int id) {
        return getScopes().get(id);
    }

    public Scope getScope();

    public default String generateTemporaryVariableName() {
        return generateTemporaryVariableName("temp");
    }

    public default String generateTemporaryVariableName(String prefix) {
        String name = prefix;
        int i = 0;

        while (SyntaxTree.findDeclaration(getScope(), name, false) != null) {
            name = prefix + i++;
        }

        return name;
    }
}

