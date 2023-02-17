package flat.tree;

import flat.Flat;
import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.generics.GenericTypeParameterList;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.Stack;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * MethodDeclaration extension that represents the declaration of a Constructor
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:50:47 PM
 * @version v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class Constructor extends BodyMethodDeclaration {
    public static final String IDENTIFIER = "construct";

    private boolean initializedInitMethod;

    public InitializationMethod initMethod;

    /**
     * Create a Constructor and initialize default values.
     *
     * @see Node#Node(Node, Location)
     */
    public Constructor(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public void searchVirtualMethodDeclaration() {

    }

    @Override
    public boolean doesConvertToPrimitive() {
        return false;
    }

    @Override
    public boolean isPure() {
        return true;
    }

    @Override
    public Value inferShorthandActionType(String action, Value contents) {
        return contents;
    }

    /**
     * @see MethodDeclaration#isInstance()
     */
    @Override
    public boolean isInstance() {
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public GenericTypeParameter getGenericTypeParameter(String name) {
        GenericTypeParameterList decl = getParentClass().getGenericTypeParameterDeclaration();

        if (decl != null) {
            return decl.getParameter(name);
        }

        return super.getGenericTypeParameter(name);
    }

    /**
     * @see FlatMethodDeclaration#generateFlatInput(java.lang.StringBuilder, boolean)
     */
    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        return super.generateFlatInput(builder, outputChildren, false);
    }

    /**
     * Decode the given statement into a Constructor instance, if
     * possible. If it is not possible, this method returns null. A
     * constructor must have the same name as the class that it is
     * within. Constructors also do not have a return value.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>public ClassName()</li>
     * 	<li>private ClassName(int numChildren, String name)</li>
     * 	<li>public ClassName(Node parent, Location location)</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  Constructor instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a Constructor.
     */
    public static Constructor decodeStatement(Node parent, String statement, Location location, boolean require) {
        BodyMethodDeclaration method = BodyMethodDeclaration.decodeStatement(parent, statement, location, false);

        if (method != null && method.getName().equals(IDENTIFIER)) {
            Constructor n = new Constructor(parent, location);

            method.cloneTo(n);

            ClassDeclaration parentClass = n.getParentClass();
            StringBuilder type = new StringBuilder(parentClass.getName());
            GenericTypeParameterList params = parentClass.getGenericTypeParameterDeclaration();

            if (params.getNumVisibleChildren() > 0) {
                type.append("<");

                int[] i = new int[]{0};

                params.forEachVisibleListChild(param -> {
                    if (i[0]++ != 0) {
                        type.append(", ");
                    }

                    type.append(param.getName());
                });

                type.append(">");
            }

            n.setName(IDENTIFIER);
            n.setType(type.toString(), true, false);
            n.setStatic(true);
            n.setDataType(POINTER);

//			GenericTypeParameterList params = n.getMethodGenericTypeParameterDeclaration();
//			GenericTypeParameterList classParams = parentClass.getGenericTypeParameterDeclaration();
//			
//			for (int i = 0; i < classParams.getNumParameters(); i++)
//			{
//				GenericTypeParameter param = new GenericTypeParameter(params, Location.INVALID);
//				param.setType(classParams.getParameter(i));
//				
//				params.addChild(param);
//			}

            return n;
        }

        return null;
    }

    public FlatMethodDeclaration getExistingConvertedPrimitiveMethod(ClassDeclaration type, GenericTypeArgumentList args) {
        return getExistingConvertedPrimitiveMethod(type.getExistingConvertedPrimitiveClass(args.getTypes()));
    }

    public FlatMethodDeclaration getExistingConvertedPrimitiveMethod(ClassDeclaration c) {
        if (c != null) {
            MethodList.SearchFilter filter = new MethodList.SearchFilter();
            filter.checkAncestor = false;
            filter.checkInterfaces = false;
            filter.checkConstructors = true;
            filter.checkProperties = false;

            Value[] original = getParameterList().getTypes();
            Value[] types = getParameterList().clone(getParameterList().getParent(), getParameterList().getLocationIn(), true, true).getTypes();

            if (c.genericOverload != null) {
                for (int i = 0; i < types.length; i++) {
                    c.genericOverload.replacePrimitiveGenerics(c.primitiveOverloadTypes, original[i], types[i]);
                }
            }

            return (FlatMethodDeclaration) c.getMethod((GenericCompatible) null, c.getName(), filter, types);
        }

        return null;
    }

    public FlatMethodDeclaration convertPrimitiveMethod(ClassDeclaration type, Value[] types) {
        return getExistingConvertedPrimitiveMethod(type.convertToPrimitive(types));
    }

    @Override
    public FlatMethodDeclaration getConvertedPrimitiveMethod(MethodCall call) {
        ClassDeclaration type = getDeclaringClass();
        GenericTypeArgumentList args = call.getMethodGenericTypeArgumentList();

        ClassDeclaration existing = type.getConvertedPrimitiveClass(args.getTypes());

        if (existing != null) {
            call.getFileDeclaration().addImport(existing.getClassLocation());

            return getExistingConvertedPrimitiveMethod(existing);
        }

        return null;
    }

    /**
     * @see Node#onAdded(Node)
     */
    @Override
    public void onAdded(Node parent) {
        initMethod = new InitializationMethod(getParent(), Location.INVALID);

        initMethod.createFrom(this);
        initMethod.constructor = this;

        setName(getParentClass().getName());

        getParentClass().addChild(initMethod);
    }

    @Override
    public Node detach(Node fromNode, boolean detachChildren, boolean soft) {
        if (initMethod != null) {
            initMethod.detach();
        }

        return super.detach(fromNode, detachChildren, soft);
    }

    /**
     * @see FlatMethodDeclaration#validate(int)
     */
    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase >= SyntaxTree.PHASE_METHOD_CONTENTS) {
            extractToInitializationMethod(phase, result);

            if (phase == SyntaxTree.PHASE_PRE_GENERATION) {
                getScope().getVariableList().closureContextDeclarations = new ArrayList<>();
            }
        }

        return result;
    }

    private void extractToInitializationMethod(int phase, ValidationResult result) {
        if (!initializedInitMethod) {
            java.util.List<MethodCall> superCalls = Flat.keepSuperCallInConstructor
                ? getScope().getChildStream()
                .filter(c -> c instanceof MethodCall)
                .map(c -> (MethodCall) c)
                .filter(MethodCall::isSuperCall)
                .collect(Collectors.toList())
                : new ArrayList<>();

            initMethod.getScope().slaughterEveryLastVisibleChild();
            initMethod.getScope().getVariableList().closureContextDeclarations = getScope().getVariableList().closureContextDeclarations;
            initMethod.getScope().inheritChildren(getScope());
            initMethod.setLocationIn(getLocationIn());
            initMethod.getScope().localVariableID = getScope().localVariableID;

            superCalls.forEach(this::addChild);

            String args = generateParameterOutput(this);

            MethodCall init = MethodCall.decodeStatement(this, "this(" + args + ")", Location.INVALID, true, false, initMethod);
            addChild(init);

            SyntaxTree.validateNodes(getParameterList(), phase);
            result.returnedNode = initMethod;

            initializedInitMethod = true;
        }
    }

    @Override
    public void checkDataType(String type) {

    }

    public void addSuperCallFor(Stack<MethodCall> constructorCalls, Constructor current) {
        ClassDeclaration clazz = current.getParentClass().getExtendedClassDeclaration();

        if (clazz == null) {
            return;
        }
    }

    private String generateParameterOutput(FlatMethodDeclaration method) {
        return generateParameterOutput(method, null);
    }

    private String generateParameterOutput(FlatMethodDeclaration method, Constructor inherited) {
        StringBuilder args = new StringBuilder();

        if (inherited == null || method.areCompatibleParameterTypes(getContext(), inherited.getParameterList().getTypes())) {
            for (int i = 0; i < method.getParameterList().getNumVisibleChildren(); i++) {
                if (i > 0) {
                    args.append(", ");
                }

                args.append(method.getParameter(i).getName()).append(": ").append(method.getParameter(i).getName());
            }
        }

        return args.toString();
    }

    /**
     * Validate that the specified statement really is a Constructor
     * declaration.
     *
     * @param data    The data that will specify if there was an error.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return Whether or not the statement is a Constructor declaration.
     */
    private boolean validateDeclaration(ExtraData data, boolean require) {
        if (getType() != null) {
            return false;
        } else if (data.error != null) {
            return SyntaxMessage.queryError(data.error, this, require);
        }

        setName(getParentClass().getName());

        return setType(getName(), false);
    }

    /**
     * @see Node#interactWord(java.lang.String, Bounds, java.lang.String, java.lang.String, Node.ExtraData)
     */
    @Override
    public boolean interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra) {
        if (super.interactWord(word, bounds, leftDelimiter, rightDelimiter, extra)) {

            if (extra.isLastWord()) {
                if (!word.equals(IDENTIFIER)) {
                    extra.error = "Constructor must be named \"" + IDENTIFIER + '"';
                }
            } else if (word.equals("static")) {
                extra.error = "Constructor cannot be declared as static";
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Constructor clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Constructor node = new Constructor(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Constructor cloneTo(Constructor node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Constructor} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Constructor cloneTo(Constructor node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the Constructor class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
