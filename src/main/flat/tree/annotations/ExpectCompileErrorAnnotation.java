package flat.tree.annotations;

import flat.ValidationResult;
import flat.error.SyntaxErrorException;
import flat.error.SyntaxMessage;
import flat.tree.FlatMethodDeclaration;
import flat.tree.Node;
import flat.tree.SyntaxTree;
import flat.util.Location;

import java.util.*;
import java.util.stream.Collectors;

public class ExpectCompileErrorAnnotation extends Annotation {
    public List<Class> types;

    public static final List<String> ERROR_PACKAGES;

    static {
        ERROR_PACKAGES = Arrays.stream(Package.getPackages()).filter(p -> {
            return p.getName().startsWith(SyntaxErrorException.class.getPackage().getName());
        }).map(p -> {
            return p.getName();
        }).collect(Collectors.toList());
    }

    public ExpectCompileErrorAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static ExpectCompileErrorAnnotation decodeStatement(Node parent, String name,
        String parameters, Location location, boolean require) {
        if (name.equals("ExpectCompileError")) {
            ExpectCompileErrorAnnotation n = new ExpectCompileErrorAnnotation(parent, location);

            n.parseParameters(parameters);

            SyntaxMessage.queryError("Must supply error type argument", n,
                n.parameters.get("type") == null);

            n.types = new ArrayList<>();
            n.types.add(n.getErrorClass((String) n.parameters.get("type")));

            return n;
        }

        return null;
    }

    public Class getErrorClass(String name) {
        Optional<Class> c = ERROR_PACKAGES.stream().map(p -> {
            try {
                return (Class) Class.forName(p + "." + name);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }).filter(Objects::nonNull).findFirst();

        SyntaxMessage.queryError("Could not find error type of '" + name + "'", this,
            !c.isPresent());

        return c.get();
    }

    @Override
    public String[] defaultParameterNames() {
        return new String[] {"type"};
    }

    @Override
    public String[][] defaultParameterTypes() {
        return new String[][] {{"Identifier"}};
    }

    @Override
    public void onAdded(Node parent) {
        super.onAdded(parent);
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {

        }

        return result;
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof FlatMethodDeclaration) {
                // valid
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public ExpectCompileErrorAnnotation clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        ExpectCompileErrorAnnotation node =
            new ExpectCompileErrorAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public ExpectCompileErrorAnnotation cloneTo(ExpectCompileErrorAnnotation node) {
        return cloneTo(node, true, true);
    }

    public ExpectCompileErrorAnnotation cloneTo(ExpectCompileErrorAnnotation node,
        boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }
}

