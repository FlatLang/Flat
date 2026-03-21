package flat.es6.nodewriters;

import flat.es6.engines.ES6CompileEngine;
import flat.tree.*;
import flat.tree.annotations.AsyncAnnotation;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.variables.Array;
import flat.util.SyntaxUtils;

import java.util.List;
import java.util.stream.Collectors;

public abstract class MethodCallWriter extends VariableWriter
{
	public abstract MethodCall node();
	
	@Override
	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		CallableMethod callable = node().getCallableDeclaration();

		if (ES6CompileEngine.INLINE_ARRAY_INITIALIZERS && callable.toDeclaration().getProperty("array") != null) {
			builder.append("FlatArray.construct1([");

			Array array = (Array) callable.toDeclaration().getProperty("array");
			TypeList<Value> initValues = array.getInitializerValues();

			for (int i = 0; i < initValues.getNumChildren(); i++) {
				if (i > 0) {
					builder.append(", ");
				}

				Value child = initValues.getChild(i);
				child.parent = node().getArgumentList();

				if (child.getReturnedNode().isPrimitive()) {
					child = SyntaxUtils.autoboxPrimitive(child, child.getReturnedNode().getType());
				}

				getWriter(child).writeExpression(builder);
			}

			return builder.append("], ").append(initValues.getNumVisibleChildren()).append(")");
		}

		if (callable instanceof InitializationMethod)
		{
			if (node().getParentMethod() instanceof InitializationMethod) {
				builder.append("self = ");
			} else {
				builder.append("__value = ");
			}
		}

		writeUsePrefix(builder);

		if (callable instanceof Constructor) {
			getWriter(callable.getParentClass()).writeName(builder).append(".");
		}

		if (callable instanceof ExtensionMethodDeclaration) {
			ExtensionMethodDeclaration method = (ExtensionMethodDeclaration)callable;
			builder.append("__callExtension");

			if (node().isChainNavigation()) {
				builder.append("Chain");
			}
			if (((Node)callable).containsAnnotationOfType(AsyncAnnotation.class)) {
				builder.append("Async");
			}

			builder.append("(").append(getWriter(method.getDeclaringClass()).writeName()).append('.').append(getWriter(method).writeName()).append(", [");
			getWriter(node().getArgumentList()).write(builder, false).append("])");
		} else if (node().isChainNavigation()) {
			builder.append("__chain");

			if (((Node)callable).containsAnnotationOfType(AsyncAnnotation.class)) {
				builder.append("Async");
			}

			builder.append("('").append(writeName()).append("', [");
			getWriter(node().getArgumentList()).write(builder, false).append("])");
		} else {
			writeName(builder);

			if (callable instanceof InitializationMethod) {
				builder.append(".call(");

				if (node().getParentMethod() instanceof Constructor) {
					builder.append("__value");
				} else {
					builder.append("this");
				}

				if (node().getArgumentList().getNumVisibleChildren() > 0) {
					builder.append(", ");
				}

				getWriter(node().getArgumentList()).write(builder, false);

				return builder.append(')');
			} else {
				builder.append("(");
				getWriter(node().getArgumentList()).write(builder, false);

				if (node().getFlatMethod() != null) {
					Value[] values = node().getArgumentList().getArgumentsInOrder(node().getFlatMethod());

					for (int i = values.length; i < node().getFlatMethod().getParameterList().getNumVisibleChildren(); i++) {
						if (i > 0) {
							builder.append(", ");
						}

						builder.append("undefined");
					}
				}

				if (callable instanceof Constructor) {
					List<GenericTypeParameter> reifiedParams = callable.getTypeClass().getReifiedParameters();

					if (reifiedParams.size() > 0) {
						if (node().getArgumentList().getNumVisibleChildren() > 0) {
							builder.append(", ");
						}

						builder.append(reifiedParams.stream()
							.map(p -> getWriter(p).writeReifiedAccess(node()))
							.collect(Collectors.joining(", ")));
					}
				}

				return builder.append(")");
			}
		}

		writeArrayAccess(builder);

//		writeNullFallbackPostfix(builder);

		return builder;
	}

	@Override
	public StringBuilder writeUsePrefix(StringBuilder builder) {
		if (node().getFlatMethod() != null && node().getFlatMethod().isExtension() && node().getFlatMethod() instanceof AnonymousCompilerMethod) {
			return getWriter(node().getFlatMethod().getParentClass()).writeName(builder).append('.');
		}

		return super.writeUsePrefix(builder);
	}

	@Override
	public StringBuilder writeName(StringBuilder builder) {
		CallableMethod callable = node().getCallableDeclaration();

		if (callable instanceof MethodDeclaration) {
			return getWriter((MethodDeclaration) callable).writeName(builder);
		} else {
			return builder.append(node().getName());
		}
	}
}
