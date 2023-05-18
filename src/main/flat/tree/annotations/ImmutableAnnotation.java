package flat.tree.annotations;

import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;
import flat.util.SyntaxUtils;

import java.util.Arrays;
import java.util.Optional;

public class ImmutableAnnotation extends Annotation implements ModifierAnnotation {
    private boolean searched = false;

    public ClassDeclaration mutableClass;

    public FlatMethodDeclaration toImmutable;

    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public ImmutableAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static ImmutableAnnotation decodeStatement(Node parent, String name, String parameters,
        Location location, boolean require) {
        if (name.equals("Immutable")) {
            ImmutableAnnotation n = new ImmutableAnnotation(parent, location);

            n.parseParameters(parameters);

            Object className = n.parameters.get("className");

            if (className != null) {
                if (className instanceof Literal) {
                    String value = ((Literal) className).value;

                    n.parameters.put("className", value.substring(1, value.length() - 1).trim());
                } else {
                    n.invalidArgument("className", true);
                }
            }

            return n;
        }

        return null;
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren,
        boolean generateArray) {
        return builder.append("immutable");
    }

    @Override
    public void onAdded(Node parent) {
        ModifierAnnotation.super.onAdded(parent);
        super.onAdded(parent);
    }

    public boolean appliedClassIsImmutable() {
        return toImmutable == null;
    }

    @Override
    public String[] defaultParameterNames() {
        return new String[] {"className", "deep"};
    }

    @Override
    public String[][] defaultParameterTypes() {
        return new String[][] {{"String", "Bool"}};
    }

    @Override
    public boolean onNextStatementDecoded(Node next) {
        if (next instanceof ClassDeclaration) {

        } else if (getProgram().getPhase() > SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
            searchProperties(next);
        }

        return super.onNextStatementDecoded(next);
    }

    private void searchProperties(Node next) {
        if (searched)
            return;

        searched = true;

        if (next instanceof VariableDeclaration) {
            VariableDeclaration declaration = (VariableDeclaration) next;

            ClassDeclaration mutableClass = declaration.getTypeClass();
            ClassDeclaration immutableClass = null;

            if (mutableClass != null && !mutableClass.isImmutable()) {
                immutableClass = getImmutableClass();
                this.mutableClass = mutableClass;

                MethodDeclaration[] methods = mutableClass.getMethods("toImmutable");

                Optional<MethodDeclaration> method = Arrays.stream(methods).filter(x -> {
                    for (Parameter p : x.getParameterList()) {
                        if (!p.isOptional()) {
                            return false;
                        }
                    }

                    return true;
                }).findFirst();

                if (method.isPresent()) {
                    toImmutable = (FlatMethodDeclaration) method.get();
                } else if (parameters.containsKey("className")) {
                    SyntaxMessage.error(
                        "Immutable class '" + immutableClass.getClassLocation()
                            + "' must contain a toImmutable function with 0 required arguments",
                        this);
                }

                if (!parameters.containsKey("className")) {
                    if (immutableClass != null) {
                        declaration.setType(immutableClass.getName());
                    } else {
                        SyntaxMessage.error("No immutable type is specified for type '"
                            + declaration.getType() + "'", this);
                    }
                }
            }
        }
    }

    public ClassDeclaration getImmutableClass() {
        if (getParent() instanceof VariableDeclaration) {
            VariableDeclaration declaration = ((VariableDeclaration) getParent());

            ClassDeclaration mutableClass = declaration.getTypeClass();

            ImmutableAnnotation reference = (ImmutableAnnotation) mutableClass
                .getAnnotationOfType(ImmutableAnnotation.class, false);

            if (reference != null) {
                return mutableClass.getFileDeclaration().getImportedClass(mutableClass,
                    (String) reference.parameters.get("className"));
            }
        }

        return null;
    }

    public void convertAssignment(Value assignment) {
        if (toImmutable != null) {
            if (!SyntaxUtils.isTypeCompatible(assignment, toImmutable,
                assignment.getReturnedNode())) {
                MethodCall call = MethodCall.decodeStatement(assignment.getReturnedNode(),
                    "toImmutable()", assignment.getLocationIn(), true, true, toImmutable);

                if (call != null) {
                    ((Accessible) assignment.getReturnedNode()).setAccessedNode(call);
                }
            }
        }
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof ClassDeclaration) {

            } else {


                if (next instanceof VariableDeclaration) {

                } else {
                    return invalidApplication(next, throwError);
                }
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
            searchProperties(getParent());
        } else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
            Node node = getParent();

            if (!parameters.containsKey("className")) {
                if (node instanceof ClassDeclaration) {
                    ClassDeclaration clazz = (ClassDeclaration) node;

                    clazz.getFieldList().getPublicFieldList().forEachVisibleChild(f -> {
                        FieldDeclaration field = (FieldDeclaration) f;

                        if (field.isTangible()
                            && field.getVisibility() == InstanceDeclaration.PUBLIC) {
                            SyntaxMessage.error("Immutable class '" + clazz.getClassLocation()
                                + "' cannot have public fields", field, false);

                            result.errorOccurred = true;
                        } else if (!field.isStatic()) {
                            field.references.forEach(variable -> {
                                if (variable.getParentMethod() instanceof Constructor == false &&
                                    variable.getParentMethod() instanceof AssignmentMethod == false
                                    &&
                                    variable.getParentMethod() instanceof MutatorMethod == false) {
                                    if (variable.isInTree() && variable.isBeingModified()) {
                                        SyntaxMessage.error(
                                            "Field '" + field.getName() + "' of immutable class '"
                                                + clazz.getClassLocation()
                                                + "' cannot be modified outside of a constructor",
                                            variable, false);

                                        result.errorOccurred = true;
                                    }
                                }
                            });
                        }
                    });
                }
            }
        }

        return result;
    }

    @Override
    public ImmutableAnnotation clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        ImmutableAnnotation node = new ImmutableAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public ImmutableAnnotation cloneTo(ImmutableAnnotation node) {
        return cloneTo(node, true, true);
    }

    public ImmutableAnnotation cloneTo(ImmutableAnnotation node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.searched = searched;
        node.mutableClass = mutableClass;
        node.toImmutable = toImmutable;
        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[] {"immutable"};
    }
}

