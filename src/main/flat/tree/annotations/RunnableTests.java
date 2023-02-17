package flat.tree.annotations;

import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.Variable;
import flat.util.Location;

import java.util.ArrayList;
import java.util.function.Consumer;

interface RunnableTests {
    default void implementTestRunner() {
        ClassDeclaration clazz = (ClassDeclaration) ((Node) this).parent;

        if (!clazz.implementsInterface(((Node) this).getProgram().getClassDeclaration("flat/test/TestRunner"))) {
            ((Node) this).getFileDeclaration().addImport("flat/test/TestRunner");

            TraitImplementation implementation = TraitImplementation.decodeStatement(clazz, "TestRunner", Location.INVALID, true);

            clazz.getInterfacesImplementationList().addChild(implementation);
        }
    }

    default FieldDeclaration addFieldInitialization(String type, String name, String initialization) {
        FieldDeclaration field = FieldDeclaration.decodeStatement(((Node) this).getParentClass(true), "static visible " + type + " " + name + " = " + initialization, Location.INVALID, true);

        ((Node) this).getParentClass().addChild(field);
        field.onAfterDecoded();

        return field;
    }

    default MethodCall.Pair<FieldDeclaration, FieldDeclaration> generateTestRunnerFields(String testRunnerType, String name, String initializer) {
        ((Annotation) this).getFileDeclaration().addImport("flat/test/TestRunnerModel");

        FieldDeclaration runner = addFieldInitialization(testRunnerType, name, initializer);
        runner.validate(((Annotation) this).getProgram().getPhase());

        FieldDeclaration override = FieldDeclaration.decodeStatement(((Annotation) this).parent, "visible TestRunnerModel model => " + name, Location.INVALID, true);

        ((Annotation) this).parent.addChild(override);
        override.onAfterDecoded();
        override.validate(SyntaxTree.PHASE_INSTANCE_DECLARATIONS);
        override.validate(SyntaxTree.PHASE_METHOD_CONTENTS);

        return new MethodCall.Pair<>(runner, override);
    }

//	default Assignment initializeTestCases(InitializationMethod parent)
//	{
//		((Node)this).getFileDeclaration().addImport("flat/test/TestCase");
//
//		String initializerValues = ((TestableAnnotation)parent.getParentClass(true).getAnnotationOfType(TestableAnnotation.class)).getTestCaseInitializer();
//
//		Assignment a = Assignment.decodeStatement(parent, "this.testCases = " + initializerValues, Location.INVALID, true);
//
//		if (parent.getScope().getFirstStatement() == null)
//		{
//			parent.getScope().addChild(a);
//		}
//		else
//		{
//			parent.getScope().addChildBefore(parent.getScope().getFirstStatement(), a);
//		}
//
//		a.onAfterDecoded();
//
//		return a;
//	}

    default Assignment initializeTestSuiteRunners(InitializationMethod parent, TestSuiteAnnotation suite) {
        ((Node) this).getFileDeclaration().addImport("flat/test/TestCase");

        String initializerValues = "[" /*+ String.join(", ", Arrays.stream((String[])suite.parameters.get("classes"))
			.map(x -> suite.getFileDeclaration().getImportedClass(parent, x))
			.collect(Collectors.toList()))*/ + "]";

        if (initializerValues.length() == 2) {
            initializerValues = "TestRunner[0]";
        }

        Assignment a = Assignment.decodeStatement(parent, "this.testRunners = " + initializerValues, Location.INVALID, true);

        if (parent.getScope().getFirstStatement() == null) {
            parent.getScope().addChild(a);
        } else {
            parent.getScope().addChildBefore(parent.getScope().getFirstStatement(), a);
        }

        a.onAfterDecoded();

        return a;
    }

    default FlatMethodDeclaration getRunTestsMethod() {
        ClassDeclaration clazz = ((Node) this).getParentClass(true);

        MethodList.SearchFilter filter = new MethodList.SearchFilter();
        filter.checkAncestor = false;
        filter.checkInterfaces = false;

        MethodDeclaration[] methods = clazz.getMethods("runTests", filter);

        return methods.length == 1 ? (FlatMethodDeclaration) methods[0] : null;
    }

    default ArrayList<FlatMethodDeclaration> getMethodsWithTypeAnnotation(Class type) {
        final ArrayList<FlatMethodDeclaration> methods = new ArrayList<>();

        ClassDeclaration clazz = (ClassDeclaration) ((Node) this).parent;

        clazz.getMethodList().forEachFlatMethod(method -> {
            if (method.containsAnnotationOfType(type)) {
                methods.add(method);
            }
        });

        return methods;
    }

    default void callMethodsWithAnnotationOfType(Class type) {
        FlatMethodDeclaration runMethod = getRunTestsMethod();

        getMethodsWithTypeAnnotation(type).forEach(method -> {
            if (method.getParameterList().any(x -> x.isUserMade())) {
                SyntaxMessage.error("Test method '" + method.getName() + "' cannot contain parameters", method);
            }

            MethodCall call = MethodCall.decodeStatement(runMethod, method.getName() + "(out)", Location.INVALID, true, false, method);

            runMethod.addChild(call);
        });
    }

    default void writeMessage(Literal message, FlatMethodDeclaration method, boolean newLine, String outputStream) {
        writeMessage(message, method, newLine, outputStream, "write");
    }

    default void writeMessage(Literal message, FlatMethodDeclaration method, boolean newLine, String outputStream, String functionName) {
        Node write = (Node) SyntaxTree.decodeIdentifierAccess(method, outputStream + "." + functionName + "(" + message.generateFlatInput() + (newLine ? " + \"\\n\"" : "") + ")", Location.INVALID, true);

        Node first = method.getScope().getFirstStatement();

        if (first != null) {
            method.getScope().addChildBefore(first, write);
        } else {
            method.getScope().addChild(write);
        }
    }

    default Variable generateTimer(Node parent, String prefix) {
        parent.getFileDeclaration().addImport("flat/time/Timer");

        String timerName = parent.getAncestorWithScope().getScope().getUniqueName(prefix + "Timer", true);

        Assignment a = Assignment.decodeStatement(parent, "let " + timerName + " = Timer().start()", Location.INVALID, true);

        parent.addChild(a);
        a.onAfterDecoded();

        return a.getAssignedNode();
    }

    default Variable stopTimer(Node parent, Variable timer) {
        Variable stopped = (Variable) SyntaxTree.decodeIdentifierAccess(parent, timer.getName() + ".stop()", Location.INVALID, true, false, true);

        parent.addChild(stopped);
        stopped.onAfterDecoded();

        return stopped;
    }

    default void addResultCall(Node parent, boolean success, Variable timer, FlatMethodDeclaration method) {
        parent.getFileDeclaration().addImport("flat/test/TestResult");

        TestAnnotation test = (TestAnnotation) method.getAnnotationOfType(TestAnnotation.class);

        String description = test.parameters.containsKey("message") && ((Literal) test.parameters.get("message")).isStringInstantiation() ? ", " + ((Node) test.parameters.get("message")).generateFlatInput() : "";

        String name = parent.getAncestorWithScope().getScope().getUniqueName("testResult", true);
        String failureMessage = success ? "" : ", e.message";
        Assignment a = Assignment.decodeStatement(parent, "let " + name + " = TestResult(" + (success ? "true" : "false") + ", " + timer.getName() + ", " + test.generateTestCase().getName() + failureMessage + ")", Location.INVALID, true);

        parent.addChild(a);
        a.onAfterDecoded();

        Consumer<FlatMethodDeclaration> callFunction = m -> {
            MethodCall call = MethodCall.decodeStatement(parent, m.getName() + "(" + name + ")", Location.INVALID, true, false, m);

            parent.addChild(call);
            call.onAfterDecoded();
        };

        if (success) {
            getMethodsWithTypeAnnotation(TestSuccessAnnotation.class).forEach(callFunction);
        } else {
            getMethodsWithTypeAnnotation(TestFailureAnnotation.class).forEach(callFunction);
        }

        getMethodsWithTypeAnnotation(TestResultAnnotation.class).forEach(callFunction);

        if (parent.getParentMethod(true).getParameter("onResult") != null) {
            MethodCall call = MethodCall.decodeStatement(parent, "onResult(" + name + ")", Location.INVALID, true, false);

            parent.addChild(call);
            call.onAfterDecoded();
        }
    }
}