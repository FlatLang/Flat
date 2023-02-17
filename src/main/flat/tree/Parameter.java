package flat.tree;

import flat.Flat;
import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.annotations.Annotation;
import flat.tree.lambda.LambdaMethodDeclaration;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * LocalDeclaration extension that represents a Parameter of a method.
 * See {@link #decodeStatement(Node, String, Location, boolean)} for more
 * details on what correct inputs look like.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:52:01 PM
 * @version v0.2.43 Jan 16, 2015 at 11:59:17 AM
 */
public class Parameter extends LocalDeclaration {
    public String defaultValueString;
    public Value defaultValue;

    public boolean requireNamed;

    /**
     * @see Node#Node(Node, Location)
     */
    public Parameter(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public int getIndex() {
        ParameterList params = (ParameterList) getParent();

        for (int i = 0; i < params.getNumParameters(); i++) {
            if (this == params.getParameter(i)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean isLocal() {
        return true;
    }

    public boolean isOptional() {
        return defaultValueString != null || defaultValue != null;
    }

    public boolean isRequired() {
        return !isOptional();
    }

    public boolean isObjectReference() {
        return getName().equals(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
    }

    @Override
    public StringBuilder generateFlatAnnotations(StringBuilder builder) {
        return generateFlatAnnotations(builder, false);
    }

    public boolean isUnnamedParameter() {
        return getParentMethod() instanceof LambdaMethodDeclaration && getName().startsWith("_");
    }

    public void updateGenericParameter(Parameter other) {
        if (other.getType() != null) {
            setDataType(other.getDataType());
        }
    }

    @Override
    public boolean isUserMade(boolean checkAncestor) {
        if (!super.isUserMade(checkAncestor)) {
            return false;
        }

        FlatMethodDeclaration method = getParentMethod();

        return method != null && method.isUserMade(checkAncestor) && !isUnnamedParameter();
    }

    @Override
    public boolean isUsed() {
        if (super.isUsed()) {
            return true;
        }

        FlatMethodDeclaration method = getParentMethod();

        return method == null || method.isExternal();
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        return generateFlatInput(builder, outputChildren, true);
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray) {
        return generateFlatInput(builder, outputChildren, generateArray, true);
    }

    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray, boolean outputDefaultValue) {
        generateFlatAnnotations(builder);

        builder.append(generateFlatType());

        if (requireNamed) {
            builder.append(":");
        }

        builder.append(' ').append(getName());

        if (outputDefaultValue && isOptional()) {
            builder.append(" = ").append(defaultValueString != null ? defaultValueString : defaultValue.generateFlatInput());
        }

        return builder;
    }

    /**
     * Get the default value of the parameter, if no value is passed to
     * the method.
     *
     * @return The value that the parameter will be set to, if no value is
     * passed to a method.
     */
    public Value getDefaultValue() {
        return defaultValue;
    }

    /**
     * Set the default value of the parameter, if no value is passed to
     * the method.
     *
     * @param defaultValue The value that the parameter will be set to,
     *                     if no value is passed to a method.
     */
    public void setDefaultValue(Value defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Decode the given statement into a Parameter instance, if
     * possible. If it is not possible, this method returns null.
     * A parameter node is essentially a variable declaration, but in
     * the context of a method declaration.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>String name</li>
     * 	<li>int age</li>
     * 	<li>Node parent</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  Parameter instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a Parameter.
     */
    public static Parameter decodeStatement(Node parent, String statement, Location location, boolean require) {
        return decodeStatement(parent, statement, location, require, true, true);
    }

    /**
     * Decode the given statement into a Parameter instance, if
     * possible. If it is not possible, this method returns null.
     * A parameter node is essentially a variable declaration, but in
     * the context of a method declaration.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>String name</li>
     * 	<li>int age</li>
     * 	<li>Node parent</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  Parameter instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @param checkName Whether or not to check for naming conflicts.
     * @return The generated node, if it was possible to translated it
     * into a Parameter.
     */
    public static Parameter decodeStatement(Node parent, String statement, Location location, boolean require, boolean checkName, boolean checkType) {
        String[][] modifierData = SyntaxTree.getPrecedingModifiers(statement, parent, location, 0, 1);

        if (modifierData != null) {
            statement = modifierData[0][0];
        }

        String defaultValue = null;

        Parameter n = new Parameter(parent, location);
        if (modifierData != null) {
            if (!Arrays.stream(modifierData[1]).allMatch(n::parseModifier)) {
                return null;
            }
        }

        int index = SyntaxUtils.findCharInBaseScope(statement, '=');

        if (index > 0) {
            defaultValue = statement.substring(index + 1).trim();
            statement = statement.substring(0, index).trim();
        }

        index = SyntaxUtils.findCharInBaseScope(statement, ':');

        if (index > 0 && StringUtils.findNextWhitespaceIndex(statement.substring(index + 1).trim(), 0) < 0) {
            n.requireNamed = true;

            statement = statement.substring(0, index) + statement.substring(index + 1);
        }

        VariableDeclaration node = ClosureDeclaration.decodeStatement(parent, statement, location, false);

        if (node == null) {
            node = LocalDeclaration.decodeStatement(parent, statement, location, require, checkName, checkType);

            if (node == null) {
                SyntaxMessage.queryError("Could not decode parameter", parent, location, require);

                return null;
            }
        }

        if (node instanceof Parameter) {
            Parameter old = n;

            n = (Parameter) node;

            if (old.getAnnotations() != null) {
                for (int i = old.getAnnotations().size() - 1; i >= 0; i--) {
                    n.addAnnotation(old.getAnnotations().get(i));
                }
            }
        } else {
            ArrayList<Annotation> annotations = n.getAnnotations();

            if (annotations != null) {
                for (int i = annotations.size() - 1; i >= 0; i--) {
                    node.addAnnotation(annotations.get(i));
                }
            }

            node.onAfterDecoded();
            node.cloneTo(n);
        }

        if (defaultValue != null) {
            n.defaultValueString = defaultValue;
        }

        return n;
    }

    @Override
    public void replace(Node old, Node replacement) {
        if (old == defaultValue) {
            defaultValue = (Value) replacement;
        } else {
            super.replace(old, replacement);
        }
    }

    private void checkFunctionMapParameter() {
        if (defaultValueString != null) {
            if (getParentClass().isPropertyTrue("functionMap")) {
                defaultValueString = null;
            }
        }
    }

    public void addDefaultParameterInitialization() {
        DefaultParameterInitialization init = new DefaultParameterInitialization(getParentMethod(), getLocationIn(), this);

        getParentMethod().addChild(init);
    }

    /**
     * @see VariableDeclaration#validate(int)
     */
    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
            checkFunctionMapParameter();

            if (defaultValueString != null && !getParentClass().isPrimitiveOverload()) {
                defaultValue = SyntaxTree.decodeValue(this, defaultValueString, getLocationIn(), true);
                defaultValue.onAfterDecoded();
                defaultValue.onAdded(this);

                defaultValueString = null;
            }
        }

        if (defaultValue != null) {
            SyntaxTree.validateNodes(defaultValue, phase);

            propagateToPrimitiveOverloads();
        }

        if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
            if (isPrimitiveType() && getParentMethod() != null) {
                int index = ((FlatParameterList) getAncestorOfType(FlatParameterList.class)).getVisibleParameterIndex(getName());

                if (index >= 0) {
                    FlatMethodDeclaration current = getParentMethod().getOverriddenMethod();

                    while (current != null) {
                        if (!current.getParameter(index).isPrimitiveType())//.isGenericType())
                        {
							/*setPrimitiveWrapperType();
							
							LocalDeclaration decl = LocalDeclaration.decodeStatement(getParentMethod(), getType() + " " + getParentMethod().generateTemporaryVariableName("prim"),
									Location.INVALID, true);
							
							getParentMethod().addChild(decl);
							
							Variable assignee = decl.generateUsableVariable(getParentMethod(), getLocationIn());
							
							Assignment assign = Assignment.decodeStatement(getParentMethod(), assignee.getName() + " = " + getName() + ".value", Location.INVALID, true, true,
									new Value[] { assignee }, null);
							
							getParentMethod().addChild(assign);
							
							swapNames(decl);*/

                            current = null;
                        } else {
                            current = current.getOverriddenMethod();
                        }
                    }
                }
				
				/*if (!isObjectReference() && getTypeClass() != null && getTypeClass().equals(getProgram().getClassDeclaration(Flat.getClassLocation("Number"))))
				{
					setDataType(VALUE);
				}*/
            }
        }

        return result;
    }

    public void propagateToPrimitiveOverloads() {
        if (!Flat.PRIMITIVE_OVERLOADS) {
            return;
        }

        FlatMethodDeclaration method = getParentMethod();

        if (method != null && method.correspondingPrimitiveOverloads.size() > 0) {
            for (FlatMethodDeclaration overload : method.correspondingPrimitiveOverloads) {
                propagateToMethod(overload);
            }
        }
    }

    public void propagateToMethod(FlatMethodDeclaration overload) {
        Parameter corresponding = overload.getParameter(getIndex());

        Value clone = (Value) defaultValue.clone(overload, defaultValue.getLocationIn(), true, true);

        if (Flat.PRIMITIVE_OVERLOADS) {
            clone.convertConvertedTypes(getParentMethod());
        }

        corresponding.defaultValue = clone;

        clone.onAfterDecoded();

        if (overload instanceof Constructor) {
            propagateToMethod(((Constructor) overload).initMethod);
        }
    }

    /**
     * @see LocalDeclaration#clone(Node, Location)
     */
    @Override
    public Parameter clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Parameter node = new Parameter(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Parameter cloneTo(Parameter node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Parameter} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Parameter cloneTo(Parameter node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.defaultValueString = defaultValueString;
        node.defaultValue = defaultValue;
        node.requireNamed = requireNamed;

        return node;
    }

    @Override
    public void setPrimitive() {
        super.setPrimitive();

        if (isPrimitive() && "null".equals(defaultValueString)) {
            setDataType(POINTER);
        }
    }

    /**
     * Test the Parameter class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}