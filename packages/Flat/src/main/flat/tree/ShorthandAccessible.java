package flat.tree;

import flat.error.SyntaxMessage;
import flat.tree.exceptionhandling.Throw;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

public interface ShorthandAccessible {
    boolean isTwoWayBinding();

    void setTwoWayBinding(boolean twoWay);

    boolean alreadyDecoded();

    String getShorthandAccessor();

    void setShorthandAccessor(String shorthand);

    default int findAccessorAssignment(String statement) {
        return SyntaxUtils.findStringInBaseScope(statement, "=>");
    }

    default int getShorthandAccessorIndex(String statement) {
        int accessorIndex = findAccessorAssignment(statement);

        if (accessorIndex > 0) {
            setShorthandAccessor(statement.substring(accessorIndex + 2).trim());
            setTwoWayBinding(
                statement.substring(accessorIndex - 1, accessorIndex + 2).trim().equals("<=>"));
        }

        return accessorIndex;
    }

    boolean containsAccessorMethod();

    boolean containsMutatorMethod();

    void addDisabledAccessor();

    void addDisabledMutator();

    FlatMethodDeclaration addDefaultAccessor();

    FlatMethodDeclaration addDefaultMutator();

    void addChild(Node n);

    Location getLocationIn();

    default Node getParseContext() {
        return (Node) this;
    }

    default AccessorMethod decodeAccessor() {
        return AccessorMethod.decodeStatement(getParseContext(), "get", getLocationIn(), true);
    }

    default BodyMethodDeclaration decodeShorthandAccessor() {
        return decodeAccessor().cloneTo(new ShorthandAccessor(getParseContext(), getLocationIn()));
    }

    default MutatorMethod decodeMutator(Value context) {
        return MutatorMethod.decodeStatement(getParseContext(), "set", getLocationIn(), true,
            context);
    }

    default BodyMethodDeclaration decodeShorthandMutator() {
        return decodeShorthandMutator(null);
    }

    default BodyMethodDeclaration decodeShorthandMutator(Value context) {
        return decodeMutator(context)
            .cloneTo(new ShorthandMutator(getParseContext(), getLocationIn()));
    }

    void setType(Value value);

    default void decodeArrowBinding() {
        decodeArrowBinding(null);
    }

    default Value decodeAccessorValue() {
        BodyMethodDeclaration a = decodeShorthandAccessor();
        a.onAfterDecoded();

        addChild(a);

        Return returnValue = Return.decodeStatement(a, "return " + getShorthandAccessor(),
            getLocationIn(), true, false);

        if (returnValue == null) {
            SyntaxMessage.error("Failed to parse accessor value '" + getShorthandAccessor() + "'",
                (Node) this);
            return null;
        }

        returnValue.onAfterDecoded();

        Value value = returnValue.getValueNode();

        if (value instanceof Throw) {
            a.addChild(value);
        } else {
            a.addChild(returnValue);
        }

        if (a.getType() == null) {
            a.setType(value.getReturnedNode());
        }

        return value;
    }

    default void decodeMutatorValue(Value value, Value context) {
        BodyMethodDeclaration m = decodeShorthandMutator(context);

        addChild(m);
        m.onAfterDecoded();

        String accessorValue = getShorthandAccessor();
        String cast = "";

        if (value instanceof Cast) {
            accessorValue = accessorValue
                .substring(StringUtils.findEndingMatch(accessorValue, 0, '(', ')') + 1);
            cast = "(" + value.generateFlatType().toString() + ")";
        }

        accessorValue = StringUtils.removeSurroundingParenthesis(accessorValue);

        Assignment assignment = Assignment.decodeStatement(m,
            accessorValue + " = " + cast + "value", getLocationIn(), true);

        m.addChild(assignment);
    }

    default void decodeArrowBinding(Value context) {
        String accessorValue = getShorthandAccessor();
        boolean twoWayBinding = isTwoWayBinding();

        if (accessorValue != null && !alreadyDecoded()) {
            if (containsAccessorMethod()) {
                containsAccessorMethod();
                SyntaxMessage.error(
                    "Cannot have both an accessor method and shorthand value assignment to a field declaration",
                    (Node) this);
            }

            Value value = decodeAccessorValue();

            if (((Value) this).getType() == null) {
                setType(value.getReturnedNode());
            }

            if (twoWayBinding) {
                if (containsMutatorMethod()) {
                    SyntaxMessage.error(
                        "Cannot have both a mutator method and two-way shorthand value assignment to a field declaration",
                        (Node) this);
                }

                decodeMutatorValue(value, context);
            } else {
                if (!containsMutatorMethod()) {
                    addDisabledMutator();
                }
            }
        }
    }
}

