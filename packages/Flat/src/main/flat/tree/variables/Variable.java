package flat.tree.variables;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.tree.annotations.FinalAnnotation;
import flat.tree.annotations.ImmutableAnnotation;
import flat.tree.annotations.VarAnnotation;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.lambda.LambdaMethodDeclaration;
import flat.util.Location;
import flat.util.SyntaxUtils;

import java.util.AbstractMap;

/**
 * Identifier extension that represents the use of a variable type. Harnesses the needed information
 * of each variable, such as whether or not it is constant, external, or an array, and its type.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:02:42 PM
 * @version v0.2.43 Jan 16, 2015 at 11:59:17 AM
 */
public class Variable extends Identifier {
    public VariableDeclaration declaration;

    public GenericTypeArgumentList genericTypeArgumentList;

    /**
     * @see Node#Node(Node, Location)
     */
    public Variable(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public Type getTypeObject() {
        return declaration != null ? declaration.getTypeObject() : null;
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override
    public boolean isConsistent() {
        return true;
    }

    public boolean isAllocatedOnHeap() {
        return declaration.isAllocatedOnHeap();
    }

    public boolean requiresHeapAllocation() {
        return declaration.requiresHeapAllocation();
    }

    @Override
    public boolean isValid() {
        return super.isValid() && getDeclaration() != null && getDeclaration().isValid();
    }

    @Override
    public AbstractMap.SimpleEntry<Value, Boolean> getCastedType() {
        AbstractMap.SimpleEntry<Value, Boolean> cast = getIfStatementCast();

        if (cast == null) {
            return super.getCastedType();
        }

        return cast;
    }

    public AbstractMap.SimpleEntry<Value, Boolean> getBinaryStatementCast() {
        BinaryOperation o = (BinaryOperation) getAncestorOfType(BinaryOperation.class);


        return null;
    }

    public AbstractMap.SimpleEntry<Value, Boolean> getIfStatementCast() {
        IfStatement current = (IfStatement) getParent().getAncestorOfType(IfStatement.class);

        if (current != null) {
            if (current.getCondition() == null) {
                current = (IfStatement) current.getAncestorOfType(IfStatement.class);
            }

            if (current != null) {
                if (current.getCondition().containsChild(this, true)) {
                    return null;
                }

                while (current != null) {
                    Node[] children = current.getCondition().getChildrenOfType(MethodCall.class);

                    for (Node n : children) {
                        MethodCall m = (MethodCall) n;

                        if (m.getName().equals("isOfType") && m.getDeclaringClass()
                            .getClassLocation().endsWith("flat/meta/Class")) {
                            Accessible r = m.getReferenceNode().getReferenceNode();

                            if (r instanceof Variable
                                && ((Variable) r).declaration == declaration) {
                                Accessible root = m.getRootAccessNode();
                                Node parent = root.getParent();

                                boolean negated =
                                    parent instanceof UnaryOperation && ((UnaryOperation) parent)
                                        .getOperator().getOperator().equals(Operator.BANG);

                                Value cloneType = ((Variable) r)
                                    .cloneTo(new IValue(parent, getLocationIn()), true, true, true);
                                cloneType.setTypeValue(m.getArgument("other").getType());

                                return new AbstractMap.SimpleEntry<>(cloneType, negated);
                            }
                        }
                    }

                    current = (IfStatement) current.getAncestorOfType(IfStatement.class);
                }
            }
        }

        return null;
    }

    @Override
    public GenericTypeArgumentList getGenericTypeArgumentList() {
        if (genericTypeArgumentList != null) {
            return genericTypeArgumentList;
        }

        return getDeclaration() != null ? getDeclaration().getGenericTypeArgumentList() : null;
    }

    public Accessible getCArgumentReferenceContext() {
        if (isAccessed() && getAccessingNode() instanceof MethodCall
            || getAccessedNode() instanceof Closure) {
            return getAccessingNode();
        }

        return super.getCArgumentReferenceContext();
    }

    public String getGenericReturnType(Value context, boolean checkCast) {
        GenericTypeParameter param = getGenericTypeParameter();
        GenericTypeArgument arg = searchGenericTypeArgument(context);

        param = arg != null && arg.isGenericType() ? arg.getGenericTypeParameter() : param;

        return arg != null && !arg.isGenericType() ? arg.getType() : param.getDefaultType();
    }

    @Override
    public GenericTypeParameter getExtendedGenericParameter(GenericTypeParameter type) {
        // if (genericParameter == null)
        {
            ClassDeclaration refClass = getReferenceNode().toValue().getTypeClass();

            if (refClass != null && refClass != type.getParentClass()
                && refClass.isOfType(type.getParentClass())) {
                GenericTypeArgument arg = SyntaxUtils.performWalk(this, this, refClass,
                    type.getParentClass(), type, true);

                if (arg != null) {
                    // genericParameter = arg.getGenericTypeParameter();
                    //
                    // if (genericParameter != null)
                    // {
                    // return genericParameter;
                    // }
                    GenericTypeParameter extracted = arg.getGenericTypeParameter();

                    if (extracted != null) {
                        return extracted;
                    }
                }
            }
        }

        return type;
    }

    @Override
    public void onReplaced(Node parent, Node replacement) {
        if (parent.getAncestorOfType(Assignment.class, true) != null) {
            Assignment a = (Assignment) parent.getAncestorOfType(Assignment.class, true);

            VariableDeclaration decl = a.getAssignedNode().getDeclaration();

            if (decl instanceof LocalDeclaration) {
                LocalDeclaration local = (LocalDeclaration) decl;

                if (local.isImplicit() && local.getImplicitType() == this) {
                    local.setImplicitType((Value) replacement);
                }
            }
        }

        super.onReplaced(parent, replacement);
    }

    /**
     * Compare the specified variable with the given one to see if they come from the same
     * declaration.
     *
     * @param other The variable to compare with.
     * @return Whether or not the variables come from the same declaration.
     */
    public boolean isSameVariable(Variable other) {
        VariableDeclaration first = getDeclaration();
        VariableDeclaration second = other.getDeclaration();

        return first == second;
    }

    /**
     * Get whether or not the specified Variable is a local variable. It is a local variable if it
     * was declared inside a method. TODO: Can optimize to realize that if a parent is a method only
     * node, stop there and return true.
     *
     * @return Whether or not the specified Variable is local.
     */
    public boolean isLocal() {
        return isValid() && getDeclaration().getParentMethod() != null;
    }

    /**
     * Get the ClassDeclaration instance that declared this variable.
     *
     * @return The ClassDeclaration instance that declared this variable.
     */
    // public ClassDeclaration getDeclaringClass()
    // {
    // VariableDeclaration var = getDeclaration();
    //
    // Accessible ref = getReferenceNode();
    // FileDeclaration file = null;
    //
    // if (getParent() ==
    // ref.getParent())//ref.getName().equals(ParameterList.OBJECT_REFERENCE_IDENTIFIER))
    // {
    // file = getFileDeclaration();
    // }
    // else
    // {
    // file = ref.getDeclaringClass().getFileDeclaration();
    // }
    //
    // if (var.isGenericType())
    // {
    // return SyntaxUtils.getImportedClass(file, var.getGenericReturnType());
    // }
    //
    // ClassDeclaration clazz = SyntaxUtils.getImportedClass(file, var.getType());
    //
    // if (clazz == null)
    // {
    // return super.getDeclaringClass();
    // }
    //
    // return clazz;
    // }
    public GenericCompatible getContext() {
        return this;
    }

    /**
     * Get the Instance/LocalDeclaration that declares the specified variable.
     *
     * @return The Instance/LocalDeclaration that declares the specified variable.
     */
    public VariableDeclaration getDeclaration() {
        return declaration;
    }

    @Override
    public ClassDeclaration getDeclaringClass() {
        return declaration != null ? declaration.getParentClass() : null;
    }

    /**
     * Set the Instance/LocalDeclaration that declares the specified variable.
     *
     * @param declaration The Instance/LocalDeclaration that declares the specified variable.
     */
    public void setDeclaration(VariableDeclaration declaration) {
        this.declaration = declaration;
    }

    /**
     * @see Identifier#getName()
     */
    @Override
    public String getName() {
        return declaration == null ? null : declaration.getName();
    }

    /**
     * @see Identifier#setName(java.lang.String, boolean)
     */
    @Override
    public void setName(String name, boolean forceOriginal) {
        if (declaration != null) {
            declaration.setName(name, forceOriginal);
        } else {
            // TODO: need to throw an error here or something
        }
    }

    @Override
    public Accessible getReferenceTypeNode(boolean requireAccessingNode, boolean skipPriority) {
        Accessible node = super.getReferenceTypeNode(requireAccessingNode, skipPriority);

        if (node instanceof ObjectReference == false)
            return node;
        if (isLocal())
            return null;

        return node;
    }

    /**
     * @see Identifier#doesForceOriginalName()
     */
    @Override
    public boolean doesForceOriginalName() {
        return declaration != null && declaration.doesForceOriginalName() || isExternal();
    }

    /**
     * Get whether or not the variable is external. For more information on external variables.
     *
     * @return Whether or not the variable is external.
     */
    public boolean isExternal() {
        return declaration != null && declaration.isExternal();
    }

    /**
     * @see Identifier#setForceOriginalName(boolean)
     */
    @Override
    public void setForceOriginalName(boolean forceOriginal) {
        declaration.setForceOriginalName(forceOriginal);
    }

    /**
     * @see Value#getArrayDimensions()
     */
    @Override
    public int getArrayDimensions() {
        if (declaration == null) {
            return 0;
        }

        return declaration.getArrayDimensions() - getArrayAccessDimensions();
    }

    /**
     * @see Value#setArrayDimensions(int)
     */
    @Override
    public void setArrayDimensions(int arrayDimensions) {
        // declaration.setArrayDimensions(arrayDimensions);
    }

    public void swapNames(Variable other) {
        swapNames(other.getDeclaration());
    }

    public void swapNames(VariableDeclaration other) {
        getDeclaration().swapNames(other);
    }

    /**
     * @see Value#getType()
     */
    @Override
    public String getType() {
        return getType(true);
    }

    public String getType(boolean checkCast) {
        if (declaration == null) {
            return null;
        }

        Cast cast = getCast();

        if (checkCast && cast != null) {
            return cast.getType();
        }

        return declaration.getType();
    }

    @Override
    public String getTypeStringValue() {
        return declaration.getTypeStringValue();
    }

    /**
     * @see AbstractValue#setTypeValue(java.lang.String)
     */
    @Override
    public void setTypeValue(String type) {
        declaration.setTypeValue(type);
    }

    /**
     * @see Value#setType(java.lang.String, boolean, boolean, boolean)
     */
    @Override
    public boolean setType(String type, boolean require, boolean checkType, boolean checkExternal) {
        if (this instanceof ClosureVariable) {
            return super.setType(type, require, checkType, checkExternal);
        } else {
            return declaration.setType(type, require, checkType, checkExternal);
        }
    }

    public byte getDataType(boolean checkGeneric) {
        return getDataType(checkGeneric, true);
    }

    public byte getDataType(boolean checkGeneric, boolean checkCast) {
        if (declaration == null) {
            return 0;
        }

        if (checkCast) {
            Cast cast = getExplicitCast();

            if (cast != null) {
                return cast.getDataType();
            }
        }

        if (checkGeneric && isGenericType()) {
            Value value = getFlatTypeValue(this);

            if (!value.isGenericType()) {
                return value.getDataType();
            }
        }

        return declaration.getDataType();
    }

    /**
     * @see Value#setDataType(byte)
     */
    @Override
    public void setDataType(byte type) {
        declaration.setDataType(type);
    }

    /**
     * @see VariableDeclaration#isVolatile()
     */
    public boolean isVolatile() {
        return declaration.isVolatile();
    }

    /**
     * @see VariableDeclaration#setVolatile(boolean)
     */
    public void setVolatile(boolean volatileVal) {
        declaration.setVolatile(volatileVal);
    }

    @Override
    public boolean onAfterDecoded() {
        return super.onAfterDecoded();
    }

    @Override
    public GenericTypeArgument getGenericTypeArgument(int index, Node value, boolean require) {
        GenericTypeArgument arg = super.getGenericTypeArgument(index, value, require);

        if (arg != null && arg.isGenericType()) {
            GenericTypeArgument extracted =
                getGenericTypeArgumentFromParameter(arg.getGenericTypeParameter());

            if (extracted != null) {
                return extracted;
            }
        }

        return arg;
    }

    public GenericTypeArgument getIntelligentGenericTypeArgument(int index) {
        return getIntelligentGenericTypeArgument(
            getGenericTypeArgumentList().getVisibleChild(index));
    }

    public GenericTypeArgument getIntelligentGenericTypeArgument(GenericTypeArgument arg) {
        GenericTypeArgument extractedType =
            getGenericTypeArgumentFromParameter(arg.getGenericTypeParameter());

        if (extractedType != null) {
            return extractedType;
        }

        return arg;
    }

    @Override
    public Value getFlatTypeValue(Value context) {
        if (isGenericType()) {
            GenericTypeParameter param = getGenericTypeParameter();

            if (param != null) {
                GenericTypeArgument extractedType = getGenericTypeArgumentFromParameter(param);

                if (extractedType != null) {
                    return extractedType;
                }
            }
        }

        return super.getFlatTypeValue(context);
    }

    public ImmutableAnnotation getImmutableAnnotation() {
        return getDeclaration() != null ? getDeclaration().getImmutableAnnotation() : null;
    }

    public boolean isImmutable() {
        return getDeclaration() != null && getDeclaration().isImmutable();
    }

    public FinalAnnotation getFinalAnnotation() {
        return getDeclaration() != null ? getDeclaration().getFinalAnnotation() : null;
    }

    public boolean isFinal() {
        return getDeclaration() != null && getDeclaration().isFinal();
    }

    public VarAnnotation getVarAnnotation() {
        return getDeclaration() != null ? getDeclaration().getVarAnnotation() : null;
    }

    public boolean isVar() {
        return getDeclaration() != null && getDeclaration().isVar();
    }

    public boolean isBeingModified() {
        if (getReturnedNode() == this) {
            if (getParent() instanceof UnaryOperation
                && ((UnaryOperation) getParent()).getOperator().doesModify()) {
                return true;
            }

            Assignment a = (Assignment) getAncestorOfType(Assignment.class);

            if (a != null && !a.wasDeclaration && arrayAccess == null) {
                if (a.getAssignedNodeValue() == this) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public synchronized void onAdded(Node parent) {
        if (declaration != null) {
            if (getDeclaration() instanceof Parameter) {
                Parameter p = (Parameter) getDeclaration();

                if (p.isUnnamedParameter()) {
                    ((LambdaMethodDeclaration) p.getParentMethod())
                        .updateUnnamedParameterPosition();
                }
            }

            declaration.references.add(this);
        }

        super.onAdded(parent);
    }

    public GenericTypeParameter getGenericTypeParameter(boolean checkCast) {
        if (genericParameter != null) {
            return genericParameter;
        }
        if (declaration == null) {
            return null;
        }

        // Accessible ref = getReferenceNode();
        //
        // if (ref != null)
        // {
        // ClassDeclaration type = ref.toValue().getTypeClass();
        //
        // if (type != null && type.isOfType(getReferenceDeclaration().getParentClass()))
        // {
        // GenericTypeParameter param = type.getGenericTypeParameter(getType(checkCast), this);
        //
        // if (param != null)
        // {
        // return param;
        // }
        // }
        // }

        return declaration.getGenericTypeParameter();
    }

    public VariableDeclaration getReferenceDeclaration() {
        Accessible c = getCorrespondingImplicitValue();

        if (c != null && c instanceof Variable) {
            return ((Variable) c).getDeclaration();
        }

        return declaration;
    }

    public Accessible getImplicitReference() {
        Accessible c = getCorrespondingImplicitValue();

        if (c != null) {
            return c.getAccessingNode();
        }

        return null;
    }

    public Accessible getCorrespondingImplicitValue() {
        /*
         * if (declaration instanceof LocalDeclaration &&
         * ((LocalDeclaration)declaration).isImplicit()) { Accessible root = getRootAccessNode();
         * 
         * if (root.getParent().getParent() instanceof Assignment) { Assignment a =
         * (Assignment)root.getParent().getParent();
         * 
         * if (a.getAssigneeNode() == root) { return
         * (Accessible)a.getAssignmentNode().getReturnedNode(); } } }
         */

        if (declaration instanceof LocalDeclaration) {
            Value v = ((LocalDeclaration) declaration).correspondingImplicit;

            if (v instanceof Accessible) {
                if (!doesAccess((Accessible) v)) {
                    return (Accessible) v;
                } else {
                    int j = 4;
                }
            }
        }

        return null;
    }

    @Override
    public Accessible getReferenceNode() {
        Accessible a = getImplicitReference();

        if (a != null) {
            return a;
        }

        return super.getReferenceNode();
    }

    public boolean doesUseGenericTypes() {
        return getDeclaration().getGenericTypeParameterDeclaration().getNumParameters() > 0;
    }

    @Override
    public boolean isValueReference() {
        return getDeclaration().isValueReference();
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase >= SyntaxTree.PHASE_METHOD_CONTENTS) {
            if (getDeclaration() != null) {
                if (!getDeclaration().isAccessible()) {
                    SyntaxMessage.error("The field '" + getName() + "' is not accessible", this,
                        false);

                    getParent().removeChild(this);

                    return result.errorOccurred();
                }

                if (isPrimitiveType() && doesAccess()) {
                    Identifier accessed = getAccessedNode();

                    if (accessed.getName().equals("class")) {
                        Identifier nextAccessed = accessed.getAccessedNode();

                        if (nextAccessed instanceof MethodCall
                            && nextAccessed.getName().equals("isOfType")) {
                            MethodCall call = (MethodCall) nextAccessed;
                            MethodCallArgumentList argList = call.getArgumentList();

                            Value[] args = argList.getArgumentsInOrder();

                            if (args.length == 1 && args[0] instanceof StaticClassReference) {
                                StaticClassReference ref = (StaticClassReference) args[0];

                                String bool =
                                    getTypeClass().isOfType(ref.getTypeClass()) ? "true" : "false";

                                Literal literal = (Literal) Literal.decodeStatement(parent, bool,
                                    getLocationIn(), true, true);

                                if (call.doesAccess()) {
                                    literal.setAccessedNode(call.getAccessedNode());
                                }

                                replaceWith(literal);

                                result.returnedNode = literal;

                                return result;
                            }
                        }

                        StaticClassReference replacement = StaticClassReference.decodeStatement(
                            getParent(), getTypeClassName(), getLocationIn(), true);

                        if (replacement != null) {
                            replaceWith(replacement);
                            replacement.setAccessedNode(accessed);

                            result.returnedNode = replacement;

                            return result;
                        }
                    } else if (getDataType() == VALUE && accessed instanceof MethodCall) {
                        result.returnedNode = replaceWithPrimitiveStaticCall();

                        return result;
                    }
                }
            }

            if (getDeclaration() instanceof FieldDeclaration) {
                FieldDeclaration field = (FieldDeclaration) getDeclaration();
                AccessorMethod accessor = field.getAccessorMethod();

                if (accessor != null && !accessor.isDisabled()
                    && (isAccessed() || getParentMethod() != accessor)) {
                    if (field.allowsPropertyMethods()
                        && (!field.isTangible() || getParentMethod() != field.getMutatorMethod())) {
                        String noAwait = isPropertyTrue("noAwait") ? "$" : "";

                        MethodCall access = MethodCall.decodeStatement(getParent(),
                            getName() + "()" + noAwait, getLocationIn(), true, false, accessor);

                        getParent().replace(this, access);

                        access.inheritChildren(this);

                        result.returnedNode = access;

                        return result;
                    }
                }
            }
        }

        return result;
    }

    private Value replaceWithPrimitiveStaticCall() {
        MethodCall call = (MethodCall) getAccessedNode();

        Accessible root = getRootAccessNode();

        String extraArgs = "";

        MethodCallArgumentList args = call.getArgumentList();

        for (int i = 0; i < args.getNumVisibleChildren(); i++) {
            String name = args.getArgumentName(i);
            name = name != null ? name + ": " : "";
            extraArgs += ", " + name + args.getVisibleChild(i).generateFlatInput();
        }

        String syntax = getType() + "." + call.getName() + "("
            + root.generateFlatInputUntil(this).toString() + extraArgs + ")";

        Value staticCall = SyntaxTree
            .decodeIdentifierAccess(root.getParent(), syntax, call.getLocationIn(), true, false)
            .toValue();

        Identifier accessed = call.getAccessedNode();

        if (accessed != null) {
            ((Accessible) staticCall.getReturnedNode()).setAccessedNode(accessed);
        }

        root.toValue().replaceWith(staticCall);

        return staticCall;
    }

    @Override
    public String getFlatType(Value context, boolean checkArray, boolean defaultGeneric) {
        if (declaration instanceof ClassInstanceDeclaration) {
            return declaration.getType();
        }

        return super.getFlatType(context, checkArray, defaultGeneric);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Variable clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        Variable node = new Variable(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Variable cloneTo(Variable node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Variable} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Variable cloneTo(Variable node, boolean cloneChildren, boolean cloneAnnotations) {
        node.declaration = declaration;
        node.arrayAccess = arrayAccess;
        node.safeNavigation = safeNavigation;
        node.chainNavigation = chainNavigation;
        node.genericTypeArgumentList = genericTypeArgumentList;

        if (cloneAnnotations) {
            cloneAnnotationsTo(node);
        }

        // super.cloneTo(node, cloneChildren, cloneAnnotations);

        if (cloneChildren) {
            cloneChildrenTo(node);
        }

        return node;
    }

    /**
     * Test the Variable class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    public String toString() {
        return generateFlatInput() + " of type " + generateFlatType(this);// +
                                                                          // generateGenericType();
    }
}
