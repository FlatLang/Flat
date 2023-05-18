package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.lambda.LambdaMethodDeclaration;
import flat.util.Location;

import java.util.ArrayList;

/**
 * Node extension that keeps track of all of the arguments that are passed during a method call. The
 * children of this node are all Argument instances. They are stored in the order that they will be
 * passed to the method call.
 *
 * @author Braden Steffaniak
 * @since v0.2.14 Jun 19, 2014 at 12:14:53 PM
 * @version v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class MethodCallArgumentList extends ArgumentList {
    private ArrayList<String> argumentNames;

    /**
     * @see Node#Node(Node, Location)
     */
    public MethodCallArgumentList(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public boolean containsNamedArguments() {
        return argumentNames != null;
    }

    public boolean containsNamedArgument(String name) {
        return getNamedArgumentIndex(name) >= 0;
    }

    public int getNamedArgumentIndex(String name) {
        if (argumentNames != null) {
            for (int i = 0; i < argumentNames.size(); i++) {
                String arg = argumentNames.get(i);

                if (name.equals(arg)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public int getFirstArgumentNameIndex() {
        for (int i = 0; i < getNumVisibleChildren(); i++) {
            if (getArgumentName(i) != null) {
                return i;
            }
        }

        return -1;
    }

    public int getLastArgumentNameIndex() {
        for (int i = getNumVisibleChildren() - 1; i >= 0; i--) {
            if (getArgumentName(i) != null) {
                return i;
            }
        }

        return -1;
    }

    public String getArgumentName(int index) {
        return argumentNames == null || argumentNames.size() <= index ? null
            : argumentNames.get(index);
    }

    public void setArgumentName(int index, String name) {
        argumentNames =
            argumentNames == null ? new ArrayList<>(getNumVisibleChildren()) : argumentNames;

        while (argumentNames.size() <= index) {
            argumentNames.add(null);
        }

        argumentNames.set(index, name);
    }

    /**
     * Get the MethodCall instance that contains the specified arguments.
     *
     * @return The parent MethodCall instance.
     */
    public MethodCall getMethodCall() {
        return (MethodCall) getParent();
    }

    /**
     * Get the types that the Argument list is providing for the parameters.
     *
     * @return An array of Values that represent that types in the argument list.
     */
    public Value[] getTypes() {
        ArrayList<Value> types = new ArrayList<>();

        for (int i = 0; i < getNumVisibleChildren(); i++) {
            types.add(getType(i));
        }

        return types.toArray(new Value[0]);
    }

    public Value[] getTypes(FlatMethodDeclaration method) {
        Value[] values = getArgumentsInOrder(method);

        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].getReturnedNode();
        }

        return values;
    }

    public Value[] getArgumentsInOrder() {
        if (getFirstArgumentNameIndex() >= 0) {
            CallableMethod callable = getMethodCall().getInferredDeclaration();

            if (callable instanceof FlatMethodDeclaration) {
                return getArgumentsInOrder((FlatMethodDeclaration) callable);
            }
        }

        ArrayList<Value> types = new ArrayList<>();

        for (int i = 0; i < getNumVisibleChildren(); i++) {
            types.add((Value) getVisibleChild(i));
        }

        return types.toArray(new Value[0]);
    }

    public Value[] getArgumentsInOrder(FlatMethodDeclaration method) {
        if (getFirstArgumentNameIndex() < 0) {
            return getArgumentsInOrder();
        }

        ArrayList<Value> types = new ArrayList<>();

        for (int i = 0; i < getFirstArgumentNameIndex(); i++) {
            Value child = ((Value) getVisibleChild(i));

            types.add(child);
        }

        int offset = 0;

        for (int i = getFirstArgumentNameIndex(); i < getNumVisibleChildren() + offset; i++) {
            Parameter param = method.getParameterList().getParameter(i);

            if (param != null) {
                if (containsNamedArgument(param.getName())) {
                    Value value = ((Value) getVisibleChild(getNamedArgumentIndex(param.getName())));

                    types.add(value);
                } else if (getLastArgumentNameIndex() == getNumVisibleChildren() - 1
                    && param.isOptional()) {
                    types.add(new DefaultArgument(this, Location.INVALID));

                    offset++;
                } else {
                    types.add(getVisibleChild(i));
                }
            }
        }

        return types.toArray(new Value[0]);
    }

    public Value getType(int index) {
        return ((Value) getVisibleChild(index)).getRealValue().getReturnedNode();
    }

    /**
     * Get the reference variable/value that is being used to call the method.
     *
     * @return The Identifier that is calling the method.
     */
    public Accessible getMethodCallContext() {
        return getMethodCall().getRootReferenceNode();
    }

    /**
     * @see Node#validate(int)
     */
    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase >= SyntaxTree.PHASE_METHOD_CONTENTS) {
            MethodCall call = getMethodCall();
            CallableMethod declaration = call.getCallableMethodBase(true);
            CallableMethod inferred = call.getInferredDeclaration();
            Value[] args = getArgumentsInOrder();

            for (int i = 0; i < getNumVisibleChildren(); i++) {
                Value param;
                Value arg = args[i];

                if (getArgumentName(i) != null) {
                    param = declaration.getParameterList().getParameter(getArgumentName(i));
                } else {
                    param = declaration.getParameterList().getParameter(i);
                }

                if (param == null) {
                    SyntaxMessage.error("Invalid argument", arg);
                    result.errorOccurred = true;
                    break;
                }

                Value inferredType = inferred.getParameterList().getParameter(i);
                Value context = (Value) getVisibleChild(i);
                ClassDeclaration typeClass = context.getReturnedNode().getTypeClass();

                if (typeClass != null) {
                    if (context instanceof Closure) {
                        Closure c = (Closure) context;

                        if (c.declaration instanceof LambdaMethodDeclaration && c.getType() != null
                            && param.getType() != null) {
                            LambdaMethodDeclaration lambda =
                                (LambdaMethodDeclaration) c.declaration;

                            if (c.isPrimitive()) {
                                if (!param.isPrimitive()) {
                                    lambda.setDataType(param.getDataType());
                                }
                            } else if (param.isPrimitive()) {
                                lambda.setDataType(param.getDataType());
                            }
                        }
                    } else if (context.getType() != null) {
                        if (declaration instanceof InitializationMethod == false) {
                            context.replaceWithBoxedValue(param,
                                context.getReturnedNode().getType());
                        }
                    }
                }
            }
        }

        if (phase == SyntaxTree.PHASE_PRE_GENERATION) {
            int numRet = 0;

            if (getMethodDeclaration() instanceof FlatMethodDeclaration) {
                FlatMethodDeclaration flatMethod = (FlatMethodDeclaration) getMethodDeclaration();

                numRet = flatMethod.getParameterList().getNumReturnParameters();

                Node base = getMethodCall().getParent();

                if (base instanceof Assignment) {
                    Assignment assignment = (Assignment) base;

                    numRet -= assignment.getNumAssignees() - 1;

                    for (int i = 1; i < assignment.getNumAssignees(); i++) {
                        addChild(assignment.getAssigneeNodes().getVisibleChild(i));
                    }
                }

                for (int i = numRet; i > 0; i--) {
                    Literal l = new Literal(this, getLocationIn().asNew());
                    l.setValue(Literal.GARBAGE_IDENTIFIER);
                    l.setType("void");
                    l.setDataType(Value.VALUE);

                    addChild(l);
                }

                if (base instanceof Assignment) {
                    Assignment assignment = (Assignment) base;

                    numRet = assignment.getNumAssignees();
                } else {
                    numRet = 0;
                }
            }

            Value[] values = getArgumentsInOrder();

            MethodCall call = getMethodCall();
            CallableMethod method = call.getInferredDeclaration();

            for (int i = 0; i < values.length - numRet; i++) {
                Value value = values[i];
                Value param = null;

                if (method.isVirtual() && !call.isVirtualTypeKnown()) {
                    param = method.getRootDeclaration().getParameterList().getParameter(i);
                } else {
                    param = method.getParameterList().getParameter(i);
                }
            }
        }

        return result;
    }

    public CallableMethod getMethodDeclaration() {
        return (CallableMethod) getMethodCall().getMethodDeclaration();
    }

    /**
     * @see Node#generateFlatInput(StringBuilder, boolean)
     */
    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        for (int i = 0; i < getNumChildren(); i++) {
            if (i > 0) {
                builder.append(", ");
            }

            if (getArgumentName(i) != null) {
                builder.append(getArgumentName(i)).append(": ");
            }

            getChild(i).generateFlatInput(builder);
        }

        return builder;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public MethodCallArgumentList clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        MethodCallArgumentList node = new MethodCallArgumentList(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public MethodCallArgumentList cloneTo(MethodCallArgumentList node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ArgumentList} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public MethodCallArgumentList cloneTo(MethodCallArgumentList node, boolean cloneChildren,
        boolean cloneAnnotations) {
        node.argumentNames = argumentNames;

        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the MethodCallArgumentList class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}

