package flat.tree.annotations;

import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.error.UnimplementedOperationException;
import flat.tree.*;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.Variable;
import flat.util.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class PureFunctionAnnotation extends Annotation implements ModifierAnnotation {
    public boolean validated;

    public String aliasUsed;

    @Override
    public String getAliasUsed() {
        return aliasUsed;
    }

    @Override
    public void setAliasUsed(String aliasUsed) {
        this.aliasUsed = aliasUsed;
    }

    public PureFunctionAnnotation(Node temporaryParent, Location locationIn) {
        this(temporaryParent, locationIn, false);
    }

    public PureFunctionAnnotation(Node temporaryParent, Location locationIn, boolean validated) {
        super(temporaryParent, locationIn);

        this.validated = validated;
    }

    public static PureFunctionAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("Pure")) {
            PureFunctionAnnotation n = new PureFunctionAnnotation(parent, location);

            return n;
        }

        return null;
    }

    @Override
    public void onAdded(Node parent) {
        ModifierAnnotation.super.onAdded(parent);
        super.onAdded(parent);
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }
        if (true) return result;
        if (phase == SyntaxTree.PHASE_METHOD_CONTENTS && !validated) {
            if (getParent() instanceof FieldDeclaration) {
                FieldDeclaration field = (FieldDeclaration) getParent();

                if (field.getAccessorMethod() == null || field.getMutatorMethod() == null) {
                    SyntaxMessage.error("Field '" + field.getDeclaringClass().getName() + "." + field.getName() + "' which does not contain an accessor or mutator function cannot contain pure annotation", field, false);

                    result.errorOccurred = true;

                    return result;
                }
            }

            CallableMethod declaration;

            if (getParent() instanceof FieldDeclaration) {
                declaration = ((FieldDeclaration) getParent()).getAccessorMethod();
            } else if (getParent() instanceof ArrayBracketOverload) {
                declaration = ((ArrayBracketOverload) getParent()).getArrayAccessorMethod();
            } else {
                declaration = (CallableMethod) getParent();
            }

            if (declaration instanceof BodyMethodDeclaration) {
                final BodyMethodDeclaration method = (BodyMethodDeclaration) declaration;

                validatePure(var -> {
                    if (var instanceof MethodCall) {
                        MethodCall call = (MethodCall) var;

                        SyntaxMessage.error("Impure function '" + call.getDeclaringClass().getName() + "." + call.getName() + "' cannot be called in pure function '" + method.getDeclaringClass().getName() + "." + method.getName() + "'", call, false);
                    } else {
                        if (var.getDeclaration() instanceof FieldDeclaration) {
                            String reason = "";

                            if (!var.isFinal()) {
                                reason += "non-final ";
                            }
                            if (!var.isImmutable()) {
                                reason += "mutable ";
                            }

                            reason = Character.toUpperCase(reason.charAt(0)) + reason.substring(1);

                            SyntaxMessage.error(reason + "field '" + var.getName() + "' cannot be referenced from pure function '" + method.getDeclaringClass().getName() + "." + method.getName() + "'", var, false);
                        } else {
                            SyntaxMessage.error("Property '" + var.getName() + "' cannot be modified in pure function '" + method.getDeclaringClass().getName() + "." + method.getName() + "'", var, false);
                        }
                    }

                    result.errorOccurred = true;
                }, method, false);

                if (result.errorOccurred) {
                    detach();
                }

                validated = true;
            } else if (declaration instanceof ClosureDeclaration) {
                final ClosureDeclaration closure = (ClosureDeclaration) declaration;

                validatePure(call -> {
                    SyntaxMessage.error("Impure function '" + call.getDeclaringClass().getName() + "." + call.getName() + "' cannot be passed as required pure closure '" + closure.getDeclaringClass().getName() + "." + closure.getName() + "'", call, false);

                    result.errorOccurred = true;
                }, closure, true);

                if (result.errorOccurred) {
                    detach();
                }
            } else if (declaration instanceof ExternalMethodDeclaration) {

            } else {
                SyntaxMessage.error("Invalid pure declaration '" + declaration.getName() + "'", (Node) declaration, false);

                result.errorOccurred = true;
            }

            // SyntaxMessage.error("Closure purity not implemented yet for '" + call.getName() + "' in '" + declaration.getName() + "'", call, false);
        }

        return result;
    }

    public boolean validatePure(MethodDeclaration method, boolean allowInstanceModification) {
        return validatePure(x -> {
        }, method, allowInstanceModification);
    }

    public boolean validatePure(Consumer<Variable> onImpure, MethodDeclaration method, boolean allowInstanceModification) {
        if (method.isPure() && (method.getPureAnnotation() == null || method.getPureAnnotation().validated)) {
            return true;
        } else if (method.isImpure()) {
            return false;
        } else if (method instanceof ExternalMethodDeclaration) {
            return false;
        }

        final boolean[] pure = new boolean[]{true};

        if (method instanceof BodyMethodDeclaration) {
            ArrayList<MethodCall> calls = new ArrayList<>();

            Node[] nodes = method.getChildrenOfType(Variable.class);

            for (Node n : nodes) {
                if (n instanceof MethodCall) {
                    calls.add((MethodCall) n);
                } else {
                    Variable var = (Variable) n;

                    if (var.getDeclaration().getParentMethod() != method) {
                        if (var.getDeclaration() instanceof FieldDeclaration) {
                            if (!var.isFinal() || !var.isImmutable()) {
                                detach();
                                pure[0] = false;

                                onImpure.accept(var);

                                continue;
                            }
                        }

                        Variable ref = var.getAccessingNode() instanceof Variable ? (Variable) var.getAccessingNode() : null;

                        // Should this reference check be recursive?
                        if ((ref == null || (ref.getDeclaration().getParentMethod() != method)) &&
                            !var.isFinal() && var.isBeingModified()) {
                            if (!allowInstanceModification || var.getDeclaration().getParentClass() != method.getDeclaringClass()) {
                                detach();
                                pure[0] = false;

                                onImpure.accept(var);
                            }
                        }
                    }
                }
            }

            calls.forEach(x -> {
                Variable ref = x.getReferenceNode() instanceof Variable ? (Variable) x.getReferenceNode() : null;
                boolean allowInstance = ref != null && ref.getDeclaration().getParentMethod() == method || x.getReferenceNode() instanceof Instantiation;

                if (!validatePure(x.getInferredDeclaration(), allowInstance)) {
                    validatePure(x.getInferredDeclaration(), allowInstance);
                    detach();
                    pure[0] = false;

                    onImpure.accept(x);
                }
            });
        }

        return pure[0];
    }

    public boolean validatePure(CallableMethod m, boolean allowInstanceModification) {
        if (m instanceof FlatMethodDeclaration) {
            return validatePure((FlatMethodDeclaration) m, allowInstanceModification);
        } else if (m instanceof ClosureDeclaration) {
            return validatePure((ClosureDeclaration) m, allowInstanceModification);
        }

        return m.isPure();
    }

    public boolean validatePure(ClosureDeclaration closure, boolean allowInstanceModification) {
        return validatePure(x -> {
        }, closure, allowInstanceModification);
    }

    public boolean validatePure(Consumer<Variable> onImpure, ClosureDeclaration closure, boolean allowInstanceModification) {
        final int index = closure.getParentMethod().getParameterList().getVisibleIndex(this);
        final FlatMethodDeclaration parentMethod = closure.getParentMethod();

        final PureFunctionAnnotation self = this;

        final boolean[] pure = new boolean[]{true};

        Arrays.stream(parentMethod.getReferencesIncludingOverrides()).forEach(x -> {
            Parameter param = (Parameter) x.getCallableDeclaration().getParameterList().getParameter(index);

            Value argument = x.getArgument(param.getName());

            if (argument == null) {
                return;
            }

            argument = argument.getReturnedNode();

            if (argument instanceof Closure) {
                Closure c = (Closure) argument;

                if (!validatePure(c.getMethodDeclaration(), false)) {
                    self.detach();
                    pure[0] = false;
                }
            } else if (argument instanceof Variable && ((Variable) argument).getDeclaration() instanceof ClosureDeclaration) {
                ClosureDeclaration refClosure = (ClosureDeclaration) ((Variable) argument).getDeclaration();

                if (refClosure.getParentMethod() != argument.getParentMethod()) {
                    if (!validatePure(refClosure, false)) {
                        self.detach();
                        pure[0] = false;
                    }
                }
            } else if (argument == null) {

            } else {
//				SyntaxMessage.error("Invalid argument '" + param.getName() + "' given for pure closure '" + closure.getDeclaringClass().getName() + "." + closure.getName() + "'", param, false);
//				
//				result.errorOccurred = true;
                throw new UnimplementedOperationException("Unknown type " + argument.getClass().getTypeName());
            }
        });

        return pure[0];
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof MethodDeclaration ||
                next instanceof ArrayBracketOverload ||
                next instanceof ClosureDeclaration ||
                next instanceof FieldDeclaration) {
                // valid
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public PureFunctionAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        PureFunctionAnnotation node = new PureFunctionAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public PureFunctionAnnotation cloneTo(PureFunctionAnnotation node) {
        return cloneTo(node, true, true);
    }

    public PureFunctionAnnotation cloneTo(PureFunctionAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.aliasUsed = aliasUsed;

        return node;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"pure"};
    }
}