package flat.tree.annotations;

import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.Variable;
import flat.util.Location;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestSuiteAnnotation extends Annotation implements RunnableTests {
    public boolean generatedRunTestsMethod, writeMessage;
    public AnonymousCompilerMethod propagationFunction;
    public String suiteInitializer;
    public FieldDeclaration suiteRunner;

    public TestSuiteAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public String[] defaultParameterNames() {
        return new String[]{"classes"};
    }

    @Override
    public String[][] defaultParameterTypes() {
        return new String[][]{{"list"}};
    }

    public static TestSuiteAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("TestSuite")) {
            TestSuiteAnnotation n = new TestSuiteAnnotation(parent, location);

            n.parseParameters(parameters);

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

        if (phase == SyntaxTree.PHASE_CLASS_DECLARATION) {
            implementTestRunner();
        } else if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
            FlatMethodDeclaration method = getRunTestsMethod();

            generatedRunTestsMethod = method == null || method.isPropertyTrue("generated");

            if (method == null) {
                parent.getFileDeclaration().addImport("flat/io/OutputStream");
                parent.getFileDeclaration().addImport("flat/test/TestResult");

                method = BodyMethodDeclaration.decodeStatement(parent, "public async runTests(onResult(TestResult) = {}, OutputStream out = Console.out)", Location.INVALID, true);

                parent.addChild(method);

                method.setProperty("generated", true);
            }

            generateSuiteRunner();
        } else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
            String[] classNames = (String[]) parameters.get("classes");

            boolean containsResultFunction =
                getMethodsWithTypeAnnotation(TestSuccessAnnotation.class).size() > 0 ||
                    getMethodsWithTypeAnnotation(TestFailureAnnotation.class).size() > 0 ||
                    getMethodsWithTypeAnnotation(TestResultAnnotation.class).size() > 0;

            for (String className : classNames) {
                ClassDeclaration c = getFileDeclaration().getImportedClass(this, className);

                SyntaxMessage.queryError("Class '" + className + "' is not imported", this, c == null);
                SyntaxMessage.queryError("Class '" + className + "' is does not contain Testable annotation", this, !c.containsAnnotationOfType(TestableAnnotation.class));
                SyntaxMessage.queryError("Testable class '" + className + "' requires a default constructor", this, !c.containsDefaultConstructor());
            }
            if (containsResultFunction) {
                getFileDeclaration().addImport("flat/test/TestResult");
                propagationFunction = getParentClass(true).generateAnonymousFunction();

                Parameter param = Parameter.decodeStatement(propagationFunction.getParameterList(), "TestResult result", Location.INVALID, true);
                propagationFunction.getParameterList().addChild(param);

                getParentClass(true).addChild(propagationFunction);

                IfStatement ifStatement = IfStatement.decodeStatement(propagationFunction, "if (result.success)", Location.INVALID, true);
                propagationFunction.addChild(ifStatement);

                ElseStatement elseStatement = ElseStatement.decodeStatement(propagationFunction, "else", Location.INVALID, true);
                propagationFunction.addChild(elseStatement);

                Function<FlatMethodDeclaration, MethodCall> generateCall = m -> {
                    return MethodCall.decodeStatement(propagationFunction, m.getName() + "(result)", Location.INVALID, true);
                };

                getMethodsWithTypeAnnotation(TestSuccessAnnotation.class).forEach(m -> {
                    ifStatement.addChild(generateCall.apply(m));
                });
                getMethodsWithTypeAnnotation(TestFailureAnnotation.class).forEach(m -> {
                    elseStatement.addChild(generateCall.apply(m));
                });
                getMethodsWithTypeAnnotation(TestResultAnnotation.class).forEach(m -> {
                    propagationFunction.addChild(generateCall.apply(m));
                });
            }

            insertMessage();
        } else if (phase == SyntaxTree.PHASE_PRE_GENERATION) {
            if (generatedRunTestsMethod) {
                addTestCalls();
            }
        }

        return result;
    }

    public void generateSuiteRunner() {
        if (suiteRunner == null && suiteInitializer == null) {
            suiteInitializer = getTestSuiteInitializer();

            suiteRunner = checkGenerateField();
        }
    }

    public FieldDeclaration checkGenerateField() {
        if (getParentClass().getAnnotationsOfType(TestSuiteAnnotation.class).stream().map(x -> (TestSuiteAnnotation) x).allMatch(x -> x.suiteInitializer != null)) {
            getFileDeclaration().addImport("flat/test/TestSuiteRunnerModel");
            getFileDeclaration().addImport("flat/test/TestSuite");
            FlatMethodDeclaration method = getRunTestsMethod();

            String name = method.getScope().getUniqueName("_" + method.getName() + "TestSuite");

            String initializer = "[" + getParentClass().getAnnotationsOfType(TestSuiteAnnotation.class).stream()
                .map(x -> (TestSuiteAnnotation) x)
                .map(x -> "TestSuite(" + x.suiteInitializer + ")")
                .collect(Collectors.joining(", ")) + "]";

            MethodCall.Pair<FieldDeclaration, FieldDeclaration> fields = generateTestRunnerFields("TestSuiteRunnerModel", name, "TestSuiteRunnerModel(" + initializer + ")");

            return fields.a;
        }

        return null;
    }

    public String getTestSuiteInitializer() {
        String initializerValues = "[" + Arrays.stream((String[]) parameters.get("classes"))
            .map(x -> {
                ClassDeclaration c = getFileDeclaration().getImportedClass(this, x);

                if (c == null) {
                    SyntaxMessage.error("Test file " + x + " not found", getController());
                }

                return c;
            })
            .filter(Objects::nonNull)
            .map(x -> {
                TestableAnnotation annotation = (TestableAnnotation) x.getAnnotationOfType(TestableAnnotation.class);

                if (annotation == null) {
                    SyntaxMessage.error("Class " + x + " does not have a valid 'testable' annotation", getController());
                }

                return annotation;
            })
            .filter(Objects::nonNull)
            .map(x -> x.getParentClass().getName() + "." + x.generateTestRunner().getName())
            .collect(Collectors.joining(", ")) + "]";

        if (initializerValues.length() == 2) {
            initializerValues = "TestRunner[0]";
        }

        return initializerValues;
    }

    public void insertMessage() {
//		if (parameters.containsKey("message"))
//		{
//			Literal message = (Literal)parameters.get("message");
//
//			if (!message.isNullLiteral() && !message.value.equals("false") && message.value.length() > 2)
//			{
//				message.value = "\"================== " + message.value.substring(1, message.value.length() - 1) + " ==================\"";
//
//				writeMessage(message);
//			}
//		}
//		else
//		{
//			ClassDeclaration clazz = (ClassDeclaration)parent;
//
//			writeMessage((Literal)Literal.decodeStatement(parent, "\"================== Testing " + clazz.getName() + " ==================\"", Location.INVALID, true, true));
//		}
    }

    public void writeMessage(Literal message) {
        RunnableTests.super.writeMessage(message, getRunTestsMethod(), true, "out");

        writeMessage = true;
    }

    public void addTestCalls() {
        ClassDeclaration clazz = (ClassDeclaration) parent;

        final FlatMethodDeclaration runMethod = getRunTestsMethod();

        callMethodsWithAnnotationOfType(InitTestClassAnnotation.class);

        String[] classNames = (String[]) parameters.get("classes");

        for (String className : classNames) {
            Assignment call = Assignment.decodeStatement(runMethod, "let test" + className + " = " + className + "()", getLocationIn(), true);

            runMethod.addChild(call);
            call.onAfterDecoded();

            ClassDeclaration c = getFileDeclaration().getImportedClass(this, className);

            TestableAnnotation testable = (TestableAnnotation) c.getAnnotationOfType(TestableAnnotation.class);

            c.getMethodList().forEachFlatMethod(x -> {
                if (x instanceof InitializationMethod) {
//					initializeTestCases((InitializationMethod)x);
                }
            });


        }

        java.util.List<ClassDeclaration> testableClasses = Arrays.stream(classNames)
            .map(name -> getFileDeclaration().getImportedClass(this, name))
            .filter(c -> !c.containsAnnotationOfType(IgnoreAnnotation.class))
            .filter(c -> {
                TestableAnnotation testable = (TestableAnnotation) c.getAnnotationOfType(TestableAnnotation.class);

                if (testable == null) {
                    SyntaxMessage.error("Class " + c + " does not have a valid 'testable' annotation", getController());
                    return false;
                }

                return true;
            })
            .collect(Collectors.toList());

        java.util.List<ClassDeclaration> onlyTestableClasses = testableClasses.stream()
            .filter(c -> {
                if (c.containsAnnotationOfType(OnlyAnnotation.class)) {
                    return true;
                }

                TestableAnnotation testable = (TestableAnnotation) c.getAnnotationOfType(TestableAnnotation.class);

                return !testable.getMethodsWithTypeAnnotation(OnlyAnnotation.class).isEmpty();
            })
            .collect(Collectors.toList());

        if (!onlyTestableClasses.isEmpty()) {
            testableClasses = onlyTestableClasses;
        }

        testableClasses.forEach(c -> {
            TestableAnnotation testable = (TestableAnnotation) c.getAnnotationOfType(TestableAnnotation.class);

            FlatMethodDeclaration method = testable.getRunTestsMethod();

            Variable call = (Variable) SyntaxTree.decodeIdentifierAccess(runMethod, "test" + c.getName() + "." + method.getName() + "(onResult, out)", getLocationIn(), true);

            runMethod.addChild(call);
        });
    }

    @Override
    public boolean onApplied(Node next, boolean throwError) {
        if (!checkDuplicate(next, throwError)) {
            if (next instanceof ClassDeclaration) {
                // valid
            } else {
                return invalidApplication(next, throwError);
            }
        }

        return super.onApplied(next, throwError);
    }

    @Override
    public TestSuiteAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        TestSuiteAnnotation node = new TestSuiteAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public TestSuiteAnnotation cloneTo(TestSuiteAnnotation node) {
        return cloneTo(node, true, true);
    }

    public TestSuiteAnnotation cloneTo(TestSuiteAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }
}