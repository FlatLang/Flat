package flat.tree.annotations;

import flat.ValidationResult;
import flat.tree.Node;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

public class VarAnnotation extends Annotation implements ModifierAnnotation {
    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public VarAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        return result;
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray) {
        return builder.append("var");
    }

    @Override
    public void onAdded(Node parent) {
        ModifierAnnotation.super.onAdded(parent);
        super.onAdded(parent);
    }

    @Override
    public boolean onNextStatementDecoded(Node next) {
//		if (next instanceof LocalDeclaration && next instanceof Parameter == false)
//		{
//			((LocalDeclaration)next).setImplicit(true);
//		}

        return super.onNextStatementDecoded(next);
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof VariableDeclaration) {

            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public VarAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        VarAnnotation node = new VarAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public VarAnnotation cloneTo(VarAnnotation node) {
        return cloneTo(node, true, true);
    }

    public VarAnnotation cloneTo(VarAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"var"};
    }
}