package flat.tree.generics;

import flat.TestContext;
import flat.ValidationResult;
import flat.tree.*;
import flat.util.Location;
import flat.util.SyntaxUtils;

/**
 * {@link Node} extension that represents a generic type parameter.
 * Contains the information of a generic type parameter. Information
 * such as the name of the parameter and the default type.
 *
 * @author Braden Steffaniak
 * @since v0.2.41 Dec 7, 2014 at 11:01:21 PM
 * @version v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class GenericTypeParameter extends IValue {
    private String defaultType;
    private boolean reified;

    /**
     * @see Node#Node(Node, Location)
     */
    public GenericTypeParameter(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        defaultType = "Object";
    }

    public String getName() {
        return getType();
    }

    public void setName(String name) {
        setTypeValue(name);
    }

    public boolean isReified() {
        return reified;
    }

    public void setReified(boolean reified) {
        this.reified = reified;
    }

    public String getDefaultType() {
        return defaultType;
    }

    public ClassDeclaration getDefaultTypeClass() {
        return SyntaxUtils.getImportedClass(getFileDeclaration(), defaultType);
    }

    public void setDefaultType(String type) {
        this.defaultType = type;

//		if (SyntaxUtils.isPrimitiveType(type))
//		{
//			setDataType(VALUE);
//		}
//		else
//		{
//			setDataType(POINTER);
//		}
    }

    public boolean isMethodGenericParameter() {
        return getParentMethod() != null;
    }

    public GenericTypeParameterList getGenericDeclaration() {
        return (GenericTypeParameterList) getParent();
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_CLASS_DECLARATION || (getParentMethod() != null && phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)) {
            if (!getDefaultType().equals("Object") && !SyntaxUtils.validateImported(this, getDefaultType(), false)) {
                SyntaxUtils.validateImported(this, getDefaultType(), false);
                SyntaxUtils.throwImportException(this, getDefaultType(), getLocationIn(), false);

                result = result.errorOccurred(this);
            }
        }
		
		/*GenericTypeArgumentList args = getGenericTypeArgumentList();
		GenericTypeParameterList decl = getProgram().getClassDeclaration(SyntaxUtils.getTypeClassLocation(this, getDefaultType())).getGenericTypeParameterDeclaration();
		
		for (int i = args.getNumVisibleChildren(); i < decl.getNumParameters(); i++)
		{
			args.addChild(SyntaxUtils.getGenericTypeArgumentName(this, decl.getParameter(i).getDefaultType()));
		}*/

        return result;
    }

    public Value getTypeValue(GenericCompatible context) {
        return getCorrespondingArgument(context);
    }

    public GenericTypeArgument getCorrespondingArgument(GenericCompatible context) {
        if (context instanceof Accessible) {
            return ((Accessible) context).getGenericTypeArgumentFromParameter(this);
        }

        return null;
    }

    @Override
    public String getTypeClassLocation(boolean checkCast) {
        return SyntaxUtils.getTypeClassLocation(this, getDefaultType(), checkCast);
    }

    @Override
    public String getFlatType(Value context, boolean checkArray, boolean defaultGeneric) {
        if (context instanceof MethodCall) {
            GenericTypeArgumentList args = ((MethodCall) context).getMethodGenericTypeArgumentList();

            if (args.getNumVisibleChildren() > getIndex()) {
                GenericTypeArgument arg = args.getVisibleChild(getIndex());

                return arg.getFlatType();
            }
        }

        return getDefaultType();
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        if (isReified()) {
            builder.append("reified ");
        }

        builder.append(getName());

        if (!getDefaultType().equals("Object")) {
            builder.append(" extends ").append(getDefaultType());
        }

        return builder;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public GenericTypeParameter clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        GenericTypeParameter node = new GenericTypeParameter(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public GenericTypeParameter cloneTo(GenericTypeParameter node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link GenericTypeParameter} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public GenericTypeParameter cloneTo(GenericTypeParameter node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.defaultType = defaultType;
        node.reified = reified;

        return node;
    }

    /**
     * Test the {@link GenericTypeParameter} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    public String toString(boolean carets) {
        String str = "";

        if (isReified()) {
            str += "reified ";
        }

        str += getName();

        if (!getDefaultType().equals("Object")) {
            str += " extends " + getDefaultType();
        }

        if (carets) {
            str = "<" + str + ">";
        }

        return str;
    }

    public String toString() {
        return toString(true);
    }
}
