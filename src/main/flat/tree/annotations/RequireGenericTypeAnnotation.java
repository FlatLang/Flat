package flat.tree.annotations;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.ClassDeclaration;
import flat.tree.Node;
import flat.tree.Value;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.generics.GenericTypeParameterList;
import flat.tree.variables.Variable;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;
import flat.util.SyntaxUtils;

/**
 * {@link Annotation} extension that represents
 *
 * @author Braden Steffaniak
 */
public class RequireGenericTypeAnnotation extends Annotation {
    /**
     * @see Node#Node(Node, Location)
     */
    public RequireGenericTypeAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        addChild(new GenericTypeParameterList(this, locationIn));
    }

    public String getRequiredType(String name) {
        GenericTypeParameter param = getGenericTypeParameterDeclaration().getParameter(name);

        if (param != null) {
            return param.getDefaultType();
        }

        return null;
    }

    @Override
    public int getNumDefaultChildren() {
        return super.getNumDefaultChildren() + 1;
    }

    public GenericTypeParameterList getGenericTypeParameterDeclaration() {
        return (GenericTypeParameterList) getChild(super.getNumDefaultChildren() + 0);
    }

    /**
     * Decode the given statement into a {@link RequireGenericTypeAnnotation} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li></li>
     * 	<li></li>
     * 	<li></li>
     * </ul>
     *
     * @param parent   The parent node of the statement.
     * @param name     The statement to try to decode into a
     *                 {@link RequireGenericTypeAnnotation} instance.
     * @param location The location of the statement in the source code.
     * @param require  Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link RequireGenericTypeAnnotation}.
     */
    public static RequireGenericTypeAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("RequireGenericType")) {
            RequireGenericTypeAnnotation n = new RequireGenericTypeAnnotation(parent, location);

            if (parameters.length() == 0) {
                n.requiresArguments(require);

                return null;
            }

            n.getGenericTypeParameterDeclaration().decodeGenericTypeParameters(parameters);

            return n;
        }

        return null;
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        Node node = getParent();

        if (node instanceof VariableDeclaration == false) {
            invalidApplication(node, true);
        }

        VariableDeclaration decl = (VariableDeclaration) node;

        for (Variable reference : decl.references) {
            if (reference.getParentMethod() == null || reference.getParentMethod().isUserMade()) {
                Variable v = (Variable) reference.getReferenceNode();

                for (GenericTypeParameter required : getGenericTypeParameterDeclaration()) {
                    int index = decl.getParentClass().getRootOverloadedClass().getGenericTypeParameterDeclaration().getParameterIndex(required.getName());

                    ClassDeclaration type = v.getTypeClass();

                    Value given = null;

                    if (type.isPrimitiveOverload()) {
                        if (type.getGenericTypeParameter(required.getName()) != null) {
                            index = type.getGenericTypeParameter(required.getName()).getIndex();
                        } else {
                            given = type.getValueFromPrimitiveOverloadParameter(index);

                            index = -1;
                        }
                    }

                    if (index >= 0) {
                        // FIXME:
                        given = v.getGenericTypeArgument(index);
                    }

                    if (!SyntaxUtils.isTypeCompatible(v.getContext(), getFileDeclaration().getImportedClass(this, required.getDefaultType()), given.getTypeClass())) {
                        SyntaxUtils.isTypeCompatible(v.getContext(), getFileDeclaration().getImportedClass(this, required.getDefaultType()), given.getTypeClass());
                        SyntaxMessage.error("Method call on method '" + v.getName() + "' requires a generic type of '" + required.getDefaultType() + "'", v, false);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public boolean onNextStatementDecoded(Node next) {
        if (next instanceof VariableDeclaration) {
            next.addAnnotation(this);
        }

        return super.onNextStatementDecoded(next);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public RequireGenericTypeAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        RequireGenericTypeAnnotation node = new RequireGenericTypeAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public RequireGenericTypeAnnotation cloneTo(RequireGenericTypeAnnotation node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link RequireGenericTypeAnnotation} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public RequireGenericTypeAnnotation cloneTo(RequireGenericTypeAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link RequireGenericTypeAnnotation} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}