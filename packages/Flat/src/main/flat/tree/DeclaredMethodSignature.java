package flat.tree;

import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameter;
import java.util.Objects;

/** Declaration identity, never assignment/call compatibility. Kept local to the
 * legacy compiler's overlay replacement and duplicate-method checks.
 * Return types, parameter names and defaults do not select an overload.
 */
final class DeclaredMethodSignature {
    private DeclaredMethodSignature() {}

    static boolean matches(FlatMethodDeclaration left, FlatMethodDeclaration right) {
        if (!Objects.equals(left.getName(), right.getName()) || left.isStatic() != right.isStatic()) return false;
        // Extension decoding removes the explicit 'this' from visible parameters
        // and stores its full type on the hidden reference parameter. Unlike an
        // ordinary member's implicit owning-class receiver, it selects overloads.
        boolean extensionA = left instanceof ExtensionMethodDeclaration;
        boolean extensionB = right instanceof ExtensionMethodDeclaration;
        if (extensionA != extensionB) return false;
        if (extensionA && !sameType(left.getParameterList().getReferenceParameter(),
            right.getParameterList().getReferenceParameter())) return false;
        int genericsA = left.getMethodGenericTypeParameterDeclaration() == null ? 0
            : left.getMethodGenericTypeParameterDeclaration().getNumVisibleChildren();
        int genericsB = right.getMethodGenericTypeParameterDeclaration() == null ? 0
            : right.getMethodGenericTypeParameterDeclaration().getNumVisibleChildren();
        return genericsA == genericsB
            && sameTypes(left.getParameterList().getTypes(), right.getParameterList().getTypes());
    }

    private static boolean sameTypes(Value[] left, Value[] right) {
        if (left.length != right.length) return false;
        for (int i = 0; i < left.length; i++) {
            if (!sameType(left[i], right[i])) return false;
        }
        return true;
    }

    private static boolean sameType(Value left, Value right) {
        if (left.explicitlyNullable != right.explicitlyNullable
            || left.getArrayDimensions() != right.getArrayDimensions()
            || (left.getTypeObject() == null ? 0 : left.getDataType(false, false))
                != (right.getTypeObject() == null ? 0 : right.getDataType(false, false))
            || left.isNative() != right.isNative()
            || left.isNativeArray() != right.isNativeArray()
            || left.isExternalType() != right.isExternalType()) return false;

        if (left instanceof ClosureDeclaration || right instanceof ClosureDeclaration) {
            if (!(left instanceof ClosureDeclaration && right instanceof ClosureDeclaration)) return false;
            if (!sameTypes(((ClosureDeclaration)left).getParameterList().getTypes(),
                ((ClosureDeclaration)right).getParameterList().getTypes())) return false;
        }
        if (left.getTypeObject() instanceof FunctionType || right.getTypeObject() instanceof FunctionType) {
            if (!(left.getTypeObject() instanceof FunctionType && right.getTypeObject() instanceof FunctionType)) return false;
            return sameType(((FunctionType)left.getTypeObject()).closure,
                ((FunctionType)right.getTypeObject()).closure);
        }

        GenericTypeParameter a = left.getGenericTypeParameter();
        GenericTypeParameter b = right.getGenericTypeParameter();
        if (a != null || b != null) {
            // Generic names may differ between the shared and target declaration;
            // their declaring scope and ordinal, not their upper bounds, identify them.
            if (a == null || b == null || a.isMethodGenericParameter() != b.isMethodGenericParameter()
                || a.getIndex() != b.getIndex()) return false;
            if (!a.isMethodGenericParameter()
                && !Objects.equals(a.getParentClass().getClassLocation(false),
                    b.getParentClass().getClassLocation(false))) return false;
        } else if (!sameUnderlyingType(left, right)) {
            return false;
        }

        GenericTypeArgumentList argsA = left.getGenericTypeArgumentList();
        GenericTypeArgumentList argsB = right.getGenericTypeArgumentList();
        int countA = argsA == null ? 0 : argsA.getNumVisibleChildren();
        int countB = argsB == null ? 0 : argsB.getNumVisibleChildren();
        if (countA != countB) return false;
        for (int i = 0; i < countA; i++) {
            if (!sameType(argsA.getVisibleChild(i), argsB.getVisibleChild(i))) return false;
        }
        return true;
    }

    private static boolean sameUnderlyingType(Value left, Value right) {
        if (left.getType() == null || right.getType() == null) {
            return left.getType() == null && right.getType() == null;
        }
        if (left.isExternalType()) return Objects.equals(left.getType(), right.getType());
        // getTypeClass uses the parsed underlying declaration, not rendered Flat
        // syntax (which can contain '?' and is not a class name).
        ClassDeclaration a = left.getTypeClass(false, false);
        ClassDeclaration b = right.getTypeClass(false, false);
        if (a != null || b != null) {
            return a != null && b != null
                && Objects.equals(a.getClassLocation(false), b.getClassLocation(false));
        }
        // Unresolved declarations still need a stable identity before later type
        // validation, but unrelated imports must not collapse by simple name.
        String aLocation = left.getReferenceFile().getImportList().getAbsoluteClassLocation(left.getType());
        String bLocation = right.getReferenceFile().getImportList().getAbsoluteClassLocation(right.getType());
        return aLocation != null && bLocation != null ? aLocation.equals(bLocation)
            : aLocation == null && bLocation == null && Objects.equals(left.getType(), right.getType())
                && left.getReferenceFile() == right.getReferenceFile();
    }
}
