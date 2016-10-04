package net.fathomsoft.nova;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.error.UnimplementedOperationException;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.Package;
import net.fathomsoft.nova.tree.annotations.Annotation;
import net.fathomsoft.nova.tree.exceptionhandling.*;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
import net.fathomsoft.nova.tree.lambda.LambdaMethodDeclaration;
import net.fathomsoft.nova.tree.match.*;
import net.fathomsoft.nova.tree.variables.*;
import net.fathomsoft.nova.util.FileUtils;
import net.fathomsoft.nova.util.Stack;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;

import static net.fathomsoft.nova.Nova.CSOURCE;
import static net.fathomsoft.nova.Nova.NO_C_OUTPUT;

public class TargetC
{
    public static abstract class TargetSkeleton extends TargetNode
    {
        
    }
    
    public static abstract class TargetStaticFieldList extends TargetList
    {
		public abstract StaticFieldList node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
                
                child.getTarget().generateHeader(builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
                
                child.getTarget().generateSource(builder);
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetInstanceFieldList extends TargetList
    {
		public abstract InstanceFieldList node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            ClassDeclaration extended = node().getParentClass().getExtendedClassDeclaration();
        
            if (extended != null)
            {
                boolean publicList = node() == node().getParentClass().getFieldList().getPublicFieldList();
            
                InstanceFieldList fields = null;
            
                if (publicList)
                {
                    fields = extended.getFieldList().getPublicFieldList();
                }
                else
                {
                    fields = extended.getFieldList().getPrivateFieldList();
                }
            
                fields.getTarget().generateHeader(builder);
            }
        
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
                
                child.getTarget().generateHeader(builder);
            }
        
            return builder;
        }
        
        public StringBuilder generateSource(StringBuilder builder)
        {
            boolean hasMethods = false;
        
            if (node().getNumChildren() > 0)
            {
                ClassDeclaration parent = node().getParentClass();
            
                if (parent.getMethodList().getNumChildren() > 0)
                {
                    hasMethods = true;
                }
            }
        
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
                
                child.getTarget().generateSource(builder);
            }
        
            if (hasMethods)
            {
                builder.append('\n');
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetFieldList extends TargetList
    {
		public abstract FieldList node();

        /**
         * Generate the C Header output for the all of the non-static
         * variables.
         *
         * @return The C Header file output.
         */
        public StringBuilder generateNonStaticHeader(StringBuilder builder)
        {
            return node().getPublicFieldList().getTarget().generateHeader(builder);
        }
    
        /**
         * Generate the C Header output for the all of the public static
         * variables.
         *
         * @return The C Header file output.
         */
        public StringBuilder generateStaticHeader(StringBuilder builder)
        {
            return node().getPublicStaticFieldList().getTarget().generateHeader(builder);
        }
    
        /**
         * Generate the C Source output for the all of the public static
         * variables.
         *
         * @return The C Source file output.
         */
        public StringBuilder generateStaticSource(StringBuilder builder)
        {
            return node().getPublicStaticFieldList().getTarget().generateSource(builder);
        }
    
        /**
         * Generate the C Source output for the all of the non-static
         * variables.
         *
         * @return The C Source file output.
         */
        public StringBuilder generateNonStaticSource(StringBuilder builder)
        {
            return node().getPrivateStaticFieldList().getTarget().generateHeader(builder);
        }
    }
    
    public static abstract class TargetWhileLoop extends TargetLoop
    {
		public abstract WhileLoop node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            Node condition = node().getCondition();
        
            builder.append("while (").append(condition.getTarget().generateSourceFragment()).append(')').append('\n');
        
            Scope scope = node().getScope();
            
            scope.getTarget().generateSource(builder);
        
            return builder;
        }
    }
    
    public static abstract class TargetUntil extends TargetIfStatement
    {
		public abstract Until node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            super.generateSourceFragment(builder).append('\n');
        
            Scope scope = node().getScope();
            
            return scope.getTarget().generateSource(builder);
        }
    }
    
    public static abstract class TargetUnaryOperation extends TargetIValue
    {
		public abstract UnaryOperation node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder).append(";\n");
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
                
                child.getTarget().generateSourceFragment(builder);
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetArrayAccess extends TargetNode
    {
		public abstract ArrayAccess node();

        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            Dimensions dimensions = node().getDimensions();
        
            dimensions.getTarget().generateSourceFragment(builder);
        
            return builder;
        }
    }
    
    public static abstract class TargetArray extends TargetVariableDeclaration
    {
		public abstract Array node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder);
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            generateTypeCast(builder);
    //		builder.insert(builder.length() - 1, '*');
        
            if (node().getNumDimensions() > 1)
            {
                builder.append("nova_gen_array(");
            }
        
            builder.append("NOVA_MALLOC(sizeof(").append(generateTypeClassName()).append(")");
        
            Dimensions dim = node().getDimensions();

    //		dim.getVisibleChild(0).generateCSourceFragment(builder);
    //		for (int i = 0; i < dim.getNumVisibleChildren(); i++)
    //		{
    //			if (i > 0)
    //			{
    //				builder.append(" * ");
    //			}
    //			
    //			dim.getVisibleChild(i).generateCSourceFragment(builder);
    //		}
            dim.getTarget().generateSourceFragment(builder, " * ", "");

    //		builder.append(')');
        
            if (node().getNumDimensions() > 1)
            {
                builder.append("), (int[]) { ");
            
                for (int i = 0; i < dim.getNumVisibleChildren(); i++)
                {
                    if (i > 0)
                    {
                        builder.append(", ");
                    }
                
                    Node child = dim.getVisibleChild(i);
                    
                    child.getTarget().generateSourceFragment(builder);
                }
            
                builder.append(" }, 0, ").append(dim.getNumVisibleChildren() - 1).append(", ");
            
                builder.append("sizeof(").append(generateTypeClassName()).append(')');
            }
        
            return builder.append(')');
        }
    }
    
    public static abstract class TargetTernaryOperation extends TargetIValue implements TargetAccessible
    {
		public abstract TernaryOperation node();

        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            Value t = node().getTrueValue();
            Value f = node().getFalseValue();
            
            String trueValue = t.getTarget().generateSourceFragment().toString();
            String falseValue = f.getTarget().generateSourceFragment().toString();
        
            ClassDeclaration trueType = t.getReturnedNode().getTypeClass();
            ClassDeclaration falseType = f.getReturnedNode().getTypeClass();
        
            if (trueType != falseType)
            {
                ClassDeclaration commonClass = trueType.getCommonAncestor(f.getReturnedNode().getTypeClass());
            
                if (trueType != commonClass)
                {
                    Value r = f.getReturnedNode();
                    
                    trueValue = r.getTarget().generateTypeCast() + trueValue;
                }
                else
                {
                    Value r = t.getReturnedNode();
                    
                    falseValue = r.getTarget().generateTypeCast() + falseValue;
                }
            }
    
            Value condition = node().getCondition();
            
            return condition.getTarget().generateSourceFragment(builder).append(" ? ").append(trueValue).append(" : ").append(falseValue);
        }
    }
    
    public static abstract class TargetStaticClassReference extends TargetIIdentifier
    {
		public abstract StaticClassReference node();

        public StringBuilder generateUseOutput(StringBuilder builder, boolean pointer, boolean checkAccesses)
        {
            return builder.append(0);
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            if (!node().doesAccess())
            {
                return generateUseOutput(builder);
            }
        
            if (node().isSpecialFragment())
            {
                return generateSpecialFragment(builder);
            }
            
            Identifier accessed = node().getAccessedNode();
            
            return accessed.getTarget().generateSourceFragment(builder);
        }
    
        public StringBuilder generateArgumentReference(StringBuilder builder, Identifier callingMethod)
        {
            return builder.append(0);//getAccessedNode().generateArgumentReference(builder, callingMethod);
        }
    }
    
    public static abstract class TargetStaticBlock extends TargetNode
    {
		public abstract StaticBlock node();

        public StringBuilder generateHeader(StringBuilder builder, ClassDeclaration clazz)
        {
            return generateMethodHeader(builder, clazz).append(';').append('\n');
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            Scope scope = node().getScope();
            
            return scope.getTarget().generateSource(builder);
        }
    
        public StringBuilder generateMethodHeader(StringBuilder builder, ClassDeclaration clazz)
        {
            builder.append("void ");
        
            generateMethodName(builder, clazz);
            
            ParameterList params = node().getParameterList();
            
            builder.append('(').append(params.getTarget().generateSource()).append(')');
        
            return builder;
        }
    
        public static StringBuilder generateMethodName(StringBuilder builder, ClassDeclaration clazz)
        {
            return builder.append(clazz.getTarget().generateSourceName()).append(StaticBlock.C_PREFIX).append(StaticBlock.IDENTIFIER);
        }
    
        public static StringBuilder generateMethodCall(StringBuilder builder, ClassDeclaration clazz)
        {
            return generateMethodName(builder, clazz).append("(" + Exception.EXCEPTION_DATA_IDENTIFIER + ")");
        }
    }
    
    public static abstract class TargetScope extends TargetNode
    {
		public abstract Scope node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSource(builder, true);
        }
        
        public StringBuilder generateSource(StringBuilder builder, boolean braces)
        {
            if (node().getNumChildren() <= 1)
            {
                if (node().getParent() instanceof Loop)
                {
                    // Insert the semicolon before the new line.
                    return builder.insert(builder.length() - 1, ";");
                }
            }
        
            if (braces)
            {
                builder.append('{').append('\n');
            }
        
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
                
                child.getTarget().generateSource(builder);
            }
        
            if (braces)
            {
                builder.append('}').append('\n');
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetReturn extends TargetIValue
    {
		public abstract Return node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder).append(";\n");
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            builder.append("return");
            
            Value value = node().getValueNode();
            
            if (value != null)
            {
                builder.append(' ');
            
                if (value.getReturnedNode().isGenericType(true) || !SyntaxUtils.isSameType(node().getParentMethod(), value.getReturnedNode(), false))
                {
                    NovaMethodDeclaration method = node().getParentMethod();
                    Value r = value.getReturnedNode();
                    
                    method.getTarget().generateTypeCast(builder).append(r.getTarget().generatePointerToValueConversion(r));
                }
            
                value.getTarget().generateSourceFragment(builder);
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetRepeat extends TargetLoop
    {
		public abstract Repeat node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            Value value = node().getValueNode();
            
            if (value != null)
            {
                builder.append("for (").append(node().getName()).append(" = 0; ").append(node().getName()).append(" < ").append(value.getTarget().generateSourceFragment()).append("; ").append(node().getName()).append("++)\n");
            }
            else
            {
                builder.append("for (;;)\n");
            }
            
            Scope scope = node().getScope();
            
            scope.getTarget().generateSource(builder);
        
            return builder;
        }
    }
    
    public static abstract class TargetPropertyMethod extends TargetBodyMethodDeclaration
    {
		public abstract PropertyMethod node();

        public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
        {
            return super.generateSourceName(builder, node().getMethodPrefix());
        }
    
        public StringBuilder generateHeader(StringBuilder builder)
        {
            if (node().isDisabled())
            {
                return builder;
            }
        
            return super.generateHeader(builder);
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            if (node().isDisabled())
            {
                return builder;
            }
        
            return super.generateSource(builder);
        }
    
        public StringBuilder generateSourcePrototype(StringBuilder builder)
        {
            if (node().isDisabled())
            {
                return builder;
            }
        
            return super.generateSourcePrototype(builder);
        }
    }
    
    public static abstract class TargetProgram extends TargetNode
    {
		public abstract Program node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
                
                child.getTarget().generateHeader();
            }
        
            return builder;
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
                
                child.getTarget().generateSource();
            }
        
            return builder;
        }
    
        /**
         * Format the C Header output to follow syntactical rules.
         */
        public void formatHeaderOutput()
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                FileDeclaration fileDeclaration = (FileDeclaration)node().getChild(i);
            
                fileDeclaration.getTarget().formatHeaderOutput();
            }
        }
    
        /**
         * Format the C Source output to follow syntactical rules.
         */
        public void formatSourceOutput()
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                FileDeclaration fileDeclaration = (FileDeclaration)node().getChild(i);
            
                fileDeclaration.getTarget().formatSourceOutput();
            }
        }
    
        /**
         * Format the C Header and Source output to follow syntactical rules.
         */
        public void formatOutput()
        {
            formatHeaderOutput();
            formatSourceOutput();
        }
    }
    
    public static abstract class TargetPriority extends TargetValue implements TargetAccessible
    {
		public abstract Priority node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder);
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            if (node().isSpecialFragment())
            {
                return generateSpecialFragment(builder);
            }
            else
            {
                Value contents = node().getContents();
                
                return builder.append('(').append(contents.getTarget().generateSourceFragment()).append(')').append(generateArrayAccess()).append(generateChildrenSourceFragment());
            }
        }
    
        public StringBuilder generateUseOutput(StringBuilder builder)
        {
            Value contents = node().getContents();
            
            return builder.append('(').append(contents.getTarget().generateSourceFragment()).append(')').append(generateArrayAccess());
        }
    }
    
    public static abstract class TargetPackage extends TargetNode
    {
		public abstract Package node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            return builder;
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            return builder;
        }
    
        public StringBuilder generateHeaderLocation()
        {
            return generateHeaderLocation(new StringBuilder());
        }
    
        public StringBuilder generateHeaderLocation(StringBuilder builder)
        {
            return builder.append(node().getLocation());
        }
    
        public StringBuilder generateLocation()
        {
            return generateLocation(new StringBuilder());
        }
    
        public StringBuilder generateLocation(StringBuilder builder)
        {
            if (!node().validLocation())
            {
                return builder;
            }
        
            String output = node().location.replace('/', '_');
        
            return builder.append(output);
        }
    }
    
    public static abstract class TargetOperator extends TargetIValue
    {
		public abstract Operator node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder);
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            if (node().operator.equals(Operator.AND))
            {
                return builder.append(Operator.AND_C);
            }
            else if (node().operator.equals(Operator.OR))
            {
                return builder.append(Operator.OR_C);
            }
        
            return builder.append(node().operator);
        }
    }
    
    public static abstract class TargetNovaParameterList extends TargetParameterList
    {
		public abstract NovaParameterList node();

        public StringBuilder generateSourceParameters(StringBuilder builder)
        {
            super.generateSourceParameters(builder);
        
            if (node().returnParameters.getNumVisibleChildren() > 0)
            {
                builder.append(", ");
            
                node().returnParameters.getTarget().generateSourceParameters(builder);
            }
        
            if (node().getMethodDeclaration() instanceof LambdaMethodDeclaration)
            {
                builder.append(", ").append(((LambdaMethodDeclaration)node().getMethodDeclaration()).context.getName()).append("* ").append(ClosureVariableDeclaration.CONTEXT_VARIABLE_NAME);
            }
        
            return builder;
        }
    
        public StringBuilder generateHeaderParameters(StringBuilder builder)
        {
            super.generateHeaderParameters(builder);
        
            if (node().returnParameters.getNumVisibleChildren() > 0)
            {
                builder.append(", ");
    
                node().returnParameters.getTarget().generateHeaderParameters(builder);
            }
        
            if (node().getMethodDeclaration() instanceof LambdaMethodDeclaration)
            {
                builder.append(", ").append(((LambdaMethodDeclaration)node().getMethodDeclaration()).context.getName()).append('*');
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetMethodList extends TargetTypeList
    {
		public abstract MethodList node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                MethodDeclaration methodDeclaration = node().getChild(i);
            
                if (!methodDeclaration.isExternal())
                {
                    methodDeclaration.getTarget().generateHeader(builder);
                }
            }
        
            return builder;
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            if (node().getNumChildren() > 0)
            {
                builder.append('\n');
            }
        
            boolean printed = false;
        
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                MethodDeclaration methodDeclaration = node().getChild(i);
            
                if (!methodDeclaration.isExternal())
                {
                    if (printed)
                    {
                        builder.append('\n');
                    }
                
                    methodDeclaration.getTarget().generateSource(builder);
                
                    printed = true;
                }
            }
        
            return builder;
        }
    
        /**
         * Generate a String containing all of the prototypes for each method
         * contained within node() node(). A method prototype follows the
         * following syntax: "static returnType methodName(arguments);"
         *
         * @return A String containing all of the prototypes for the methods
         * 		contained within node() node().
         */
        public StringBuilder generateSourcePrototypes(StringBuilder builder)
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                MethodDeclaration child = node().getChild(i);
            
                child.getTarget().generateSourcePrototype(builder).append('\n');
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetMethodCallArgumentList extends TargetArgumentList
    {
		public abstract MethodCallArgumentList node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder);
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            MethodCall call = node().getMethodCall();
        
            CallableMethod method = call.getInferredDeclaration();
        
            builder.append('(');
        
            generateDefaultArguments(builder);
        
            int i = 0;
        
            Value[] values = method instanceof NovaMethodDeclaration ? node().getArgumentsInOrder((NovaMethodDeclaration)method) : node().getArgumentsInOrder();
        
            while (i < values.length)
            {
                if (i > 0)
                {
                    builder.append(", ");
                }
            
                Value arg = values[i];
                Value param = method.getParameterList().getParameter(i);
            
                if (arg instanceof DefaultArgument)
                {
                    DefaultArgument.generateDefaultArgumentOutput(builder, param);
                }
                else
                {
                    if (method.isVirtual() && !call.isVirtualTypeKnown())
                    {
                        VirtualMethodDeclaration virtual = ((NovaMethodDeclaration)method).getVirtualMethod();
                    
                        if (virtual != null)
                        {
                            param = virtual.getParameter(i);
                        }
                    }
                
                    boolean sameType = SyntaxUtils.isSameType(arg.getReturnedNode(), param, false) || param.isPrimitiveType() && arg.isPrimitiveType();
                
                    if (!sameType)
                    {
                        Value ret = arg.getReturnedNode();
                        
                        param.getTarget().generateTypeCast(builder).append(ret.getTarget().generatePointerToValueConversion(param));
                    }
                
                    generateArgumentPrefix(builder, arg, i);
                
                    if (!sameType)
                    {
                        builder.append('(');
                    }
                
                    if (param.isValueReference())
                    {
                        builder.append('&');
                    }
                
                    arg.getTarget().generateArgumentOutput(builder);
                
                    if (!sameType)
                    {
                        builder.append(')');
                    }
                }
            
                i++;
            }
        
            ParameterList params = node().getMethodDeclaration().getParameterList();
        
            while (i < params.getNumVisibleChildren())
            {
                builder.append(", ");
            
                DefaultArgument.generateDefaultArgumentOutput(builder, params.getVisibleChild(i));
            
                i++;
            }
        
            if (node().getMethodCall().getCallableDeclaration() instanceof ClosureDeclaration)
            {
                builder.append(", ").append(((ClosureDeclaration)node().getMethodCall().getCallableDeclaration()).getContextName());
            }
        
            return builder.append(')');
        }
    
        /**
         * Generate the output of the default arguments. The default arguments
         * may include the ExceptionData instance as well as the class
         * instance, if it is non-static.
         *
         * @param builder The StringBuilder to append to.
         * @return The appended StringBuilder instance.
         */
        private StringBuilder generateDefaultArguments(StringBuilder builder)
        {
            if (!node().getMethodCall().isExternal())
            {
                checkReference(builder).append(Exception.EXCEPTION_DATA_IDENTIFIER);
            
                if (node().getNumChildren() > 0)
                {
                    builder.append(", ");
                }
            }
        
            return builder;
        }
    
        /**
         * Generate any data that needs to be output before the argument
         * is generated, such as a type cast for a volatile local variable
         * or a data type change.
         *
         * @param builder The StringBuilder to append the data to.
         * @param child The Value that is being output as an argument.
         * @param argNum The number of argument that the list is outputting.
         * @return The StringBuilder with the appended data.
         */
        private StringBuilder generateArgumentPrefix(StringBuilder builder, Value child, int argNum)
        {
            Value parameter = node().getMethodCall().getInferredDeclaration().getParameterList().getParameter(argNum);
        
            if (child instanceof Variable)
            {
                Variable var = (Variable)child;
            
                if (var.isVolatile())
                {
                    parameter.getTarget().generateTypeCast(builder);
                }
            }
        
            if (parameter.getDataType() != child.getReturnedNode().getDataType())
            {
                if (!node().getMethodCall().getReferenceNode().toValue().isPrimitiveGenericTypeWrapper())//parameter.getArrayDimensions() == 0 || parameter.isWithinExternalContext() || parameter.getArrayDimensions() != child.getReturnedNode().getArrayDimensions())
                {
                    builder.append(parameter.generateDataTypeOutput(child.getReturnedNode().getDataType()));
                }
            }
        
            return builder;
        }
    
        /**
         * If the method call needs to pass a reference of the class instance,
         * then generate the required argument.
         *
         * @param builder The StringBuilder to append to.
         * @return The appended StringBuilder instance.
         */
        private StringBuilder checkReference(StringBuilder builder)
        {
            CallableMethod method = node().getMethodCall().getInferredDeclaration();
        
            if (method instanceof Constructor || !node().getMethodCall().getDeclaration().isInstance())
            {
                builder.append(0);
            }
            else if (method instanceof ClosureDeclaration)
            {
                ClosureDeclaration closure = (ClosureDeclaration)method;
            
                closure.getTarget().generateObjectReferenceIdentifier(builder);
            }
            else
            {
                if (method instanceof Destructor)
                {
                    builder.append('&');
                }
            
                Accessible context  = node().getMethodCallContext();
                MethodCall call     = node().getMethodCall();
                ClassDeclaration castClass = null;
            
                boolean sameType = SyntaxUtils.isSameType((Value)call.getReferenceNode(), method.getParentClass(), false);
            
                if (method.isVirtual() && !call.isVirtualTypeKnown())
                {
                    castClass = ((NovaMethodDeclaration)method).getVirtualMethod().getParentClass();
                }
                else if (!sameType)
                {
                    castClass = method.getParentClass();
                }
            
                if (castClass != null)
                {
                    castClass.getTarget().generateTypeCast(builder, true, false).append('(');
                }
            
                // Chop off the method call so it does not get cloned over.
                Accessible accessible = context;
            
                if (accessible.doesAccess())
                {
                    Accessible accessed = context.getAccessedNode();
                
                    while (accessed != null && accessed != call)
                    {
                        accessible = accessible.getAccessedNode();
                        accessed   = accessible.getAccessedNode();
                    }
                
                    accessible.setAccessedNode(call);
                }
    
                Accessible ref = context.getCArgumentReferenceContext();
                
                ref.getTarget().generateArgumentReference(builder, call);
            
                if (castClass != null)
                {
                    builder.append(')');
                }
            }
        
            builder.append(", ");
        
            return builder;
        }
    }
    
    public static abstract class TargetMethodCall extends TargetVariable
    {
		public abstract MethodCall node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder).append(';').append('\n');
        }
        
        public StringBuilder generatedCSourceFragment(StringBuilder builder, boolean checkSpecial)
        {
            if (checkSpecial && node().isSpecialFragment())
            {
                return generateSpecialFragment(builder);
            }
        
            return generateUseOutput(builder);
        }
    
        /**
         * Generate a String representing the output of the children of the
         * MethodCall.
         *
         * @return A String representing the output of the children of the
         * 		MethodCall.
         */
        public StringBuilder generatehildrenCSourceFragment(StringBuilder builder)
        {
            for (int i = 1; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
            
                builder.append("->");
            
                child.getTarget().generateSourceFragment(builder);
            }
        
            return builder;
        }
    
        /**
         * Generate the representation of when the method call is being used
         * in action.
         *
         * @return What the method call looks like when it is being used in
         * 		action.
         */
        public StringBuilder generateUseOutput(StringBuilder builder, boolean pointer, boolean checkAccesses)
        {
            VariableDeclaration method   = node().getMethodDeclaration();
            CallableMethod      callable = (CallableMethod)method;
        
            boolean requiresCast = checkAccesses && node().doesAccess() && node().getAccessedNode() instanceof MethodCall == false && node().isGenericType();
        
            if (requiresCast)
            {
                builder.append('(');
                generateTypeCast(builder);
            }
        
            if (callable.isVirtual() && ((NovaMethodDeclaration)method).getVirtualMethod() != null && !node().isVirtualTypeKnown())
            {
                NovaMethodDeclaration novaMethod = (NovaMethodDeclaration)method;
			
                /*if (!isAccessed())
                {
                    builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append("->");
                }
                
                if (getParent() instanceof Variable)
                {
                    //((Variable)getParent()).generateUseOutput(builder).append("->");
                }
                
                builder.append(VTable.IDENTIFIER).append("->");
                
                if (method.getParentClass() instanceof Interface)
                {
                    builder.append(InterfaceVTable.IDENTIFIER).append(".");
                }*/
                
			    VirtualMethodDeclaration virtual = novaMethod.getVirtualMethod();
			
                virtual.getTarget().generateSourceName(builder);
            }
            else
            {
                method.getTarget().generateSourceName(builder);
            }
            
            MethodCallArgumentList args = node().getArgumentList();
        
            args.getTarget().generateSource(builder);
        
            if (requiresCast)
            {
                builder.append(')');
            }
        
            return builder;
        }
    
        public StringBuilder generateExtraArguments(StringBuilder builder)
        {
            return builder;
        }
    
        public StringBuilder generateObjectReferenceIdentifier(StringBuilder builder)
        {
            return builder;
        }
    }
    
    public static abstract class TargetLiteral extends TargetIValue implements TargetAccessible
    {
		public abstract Literal node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            return generateSource(builder);
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder).append(";\n");
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            if (node().isSpecialFragment())
            {
                return generateSpecialFragment(builder);
            }
        
            return generateUseOutput(builder);
        }
    
        public StringBuilder generateUseOutput(StringBuilder builder)
        {
            if (!node().isWithinExternalContext() && node().isStringInstantiation())
            {
                Instantiation str = Instantiation.decodeStatement(node().getParent(), "new String(" + node().value + ")", node().getLocationIn(), true);
            
                return str.getTarget().generateSourceFragment(builder);
            }
            else if (node().isNullLiteral(node()))
            {
                return generateNullOutput(builder);
            }
            else if (node().value.equals(Literal.TRUE_IDENTIFIER))
            {
                return builder.append(1);
            }
            else if (node().value.equals(Literal.FALSE_IDENTIFIER))
            {
                return builder.append(0);
            }
            else if (SyntaxUtils.isInteger(node().value))
            {
                long l = Long.parseLong(node().value);
            
                if (l == Long.MIN_VALUE)
                {
                    return builder.append("(").append(l + 1).append("LL").append(" - 1)");
                }
                else if (l > Integer.MAX_VALUE || l <= Integer.MIN_VALUE)
                {
                    return builder.append(node().value).append("LL");
                }
            }
        
            return builder.append(node().value);
        }
    }
    
    public static abstract class TargetInterface extends TargetClassDeclaration
    {
		public abstract Interface node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return super.generateSource(builder);
        }
    }
    
    public static abstract class TargetInstantiation extends TargetIIdentifier
    {
		public abstract Instantiation node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder).append(";\n");
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            Identifier id = node().getIdentifier();
            
            return id.getTarget().generateSourceFragment(builder);
        }
    
        public StringBuilder generateUseOutput(StringBuilder builder, boolean pointer, boolean checkAccesses)
        {
            Identifier id = node().getIdentifier();
    
            return id.getTarget().generateUseOutput(builder, pointer, checkAccesses);
        }
    }
    
    public static abstract class TargetImportList extends TargetList
    {
		public abstract ImportList node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
                
                child.getTarget().generateSource(builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            FileDeclaration file = node().getFileDeclaration();
        
            Import importNode = Import.decodeStatement(node(), "import \"" + file.getClassDeclaration().getClassLocation() + "\"", node().getLocationIn(), true);
        
            return importNode.getTarget().generateSource(builder);
        }
    }
    
    public static abstract class TargetImport extends TargetNode
    {
		public abstract Import node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            return generateSource(builder);
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            builder.append("#include ");
        
            if (node().isExternal() || !node().getFileDeclaration().getName().equals(node().location))
            {
                return builder.append('<').append(node().getLocation()).append('>').append('\n');
            }
            else
            {
                return builder.append('"').append(node().getLocation()).append('"').append('\n');
            }
        }
    }
    
    public static abstract class TargetForLoop extends TargetLoop
    {
		public abstract ForLoop node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            Assignment initialization = node().getLoopInitialization();
            Node       condition      = node().getCondition();
            Node       update         = node().getLoopUpdate();
        
            if (initialization != null)
            {
                initialization.getTarget().generateSource(builder);//.append('\n');
            }
        
            builder.append("for (; ");
        
            if (condition != null)
            {
                condition.getTarget().generateSourceFragment(builder);
            }
        
            builder.append("; ");
        
            if (update != null)
            {
                update.getTarget().generateSourceFragment(builder);
            }
        
            builder.append(')').append('\n');
        
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
            
                if (child != node().getArgumentList())
                {
                    child.getTarget().generateSource(builder);
                }
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetForEachLoop extends TargetLoop
    {
		public abstract ForEachLoop node();

        public StringBuilder generateSource(StringBuilder builde)
        {
            StringBuilder builder = new StringBuilder();
    
            Value hasNextCheck = node().getHasNextCheck();
            
            builder.append("while (").append(hasNextCheck.getTarget().generateSourceFragment()).append(')').append('\n');
        
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
            
                if (child != node().getArgumentList())
                {
                    child.getTarget().generateSource(builder);
                }
            }
        
            return builde.append(builder);
        }
    }
    
    public static abstract class TargetLoop extends TargetNode
    {
        
    }
    
    public static abstract class TargetFileDeclaration extends TargetNode
    {
		public abstract FileDeclaration node();

        /**
         * Get the name of the file that will be output as a C header file.<br>
         * <br>
         * For example: A generateHeaderName() call for a FileDeclaration of Test.nova
         * would return "nova_Test.h"
         *
         * @return The name of the file output as a C header file.
         */
        public String generateHeaderName()
        {
            Package pkg = node().getPackage();
            ClassDeclaration clazz = node().getClassDeclaration();
            
            return pkg.getTarget().generateHeaderLocation() + "/" + clazz.getTarget().generateSourceName() + ".h";
        }
    
        /**
         * Get the name of the file that will be output as a C source file.<br>
         * <br>
         * For example: A generateSourceName() call for a FileDeclaration of Test.nova
         * would return "nova_Test.c"
         *
         * @return The name of the file output as a C source file.
         */
        public String generateSourceName()
        {
            Package pkg = node().getPackage();
            ClassDeclaration clazz = node().getClassDeclaration();
    
            return pkg.getTarget().generateHeaderLocation() + "/" + clazz.getTarget().generateSourceName() + ".c";
        }
    
        public StringBuilder generateHeader(StringBuilder builder)
        {
            if (node().header == null)
            {
                ClassDeclaration clazz = node().getClassDeclaration();
                
                String definitionName = "FILE_" + clazz.getTarget().generateSourceName() + "_" + Nova.LANGUAGE_NAME.toUpperCase();
            
                builder.append("#pragma once").append('\n');
                builder.append("#ifndef ").append(definitionName).append('\n');
                builder.append("#define ").append(definitionName).append("\n\n");
            
                generateDummyTypes(builder).append('\n');
    
                generateClosureDefinitions(builder, true).append('\n');
            
                ImportList imports = node().getImportList();
            
                imports.getTarget().generateHeader(builder);
            
                builder.append('\n');
            
                for (int i = 0; i < node().getNumChildren(); i++)
                {
                    Node child = node().getChild(i);
                
                    if (child != imports)
                    {
                        child.getTarget().generateHeader(builder);
                    }
                }
            
                builder.append('\n').append("#endif").append('\n');
    
                node().header = builder;
            }
        
            return node().header;
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            if (node().source == null)
            {
                builder.append("#include <precompiled.h>\n");
                
                ImportList imports = node().getImportList();
                
                imports.getTarget().generateSource(builder).append('\n');
            
                generateSourceClosureContextDefinitions(builder).append('\n');
                generateClosureDefinitions(builder, false).append('\n');
            
                for (int i = 0; i < node().getNumChildren(); i++)
                {
                    Node child = node().getChild(i);
                
                    if (child != node().getImportList())
                    {
                        child.getTarget().generateSource(builder);
                    }
                }
    
                node().source = builder.append('\n');
            }
        
            return node().source;
        }
    
        public StringBuilder generateHeaderNativeInterface(StringBuilder builder)
        {
            for (ClassDeclaration clazz : node().getClassDeclarations())
            {
                clazz.getTarget().generateHeaderNativeInterface(builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateSourceNativeInterface(StringBuilder builder)
        {
            for (ClassDeclaration clazz : node().getClassDeclarations())
            {
                clazz.getTarget().generateSourceNativeInterface(builder);
            }
        
            return builder;
        }
    
        /**
         * Generate dummy class declarations for each of the imported files.
         * node() is needed in a situation when a class imports a class that
         * in returns needs to import the respective one. In other words,
         * the chicken vs egg scenario.
         *
         * @return The generated code used for generating the dummy class
         * 		types.
         */
        private StringBuilder generateDummyTypes(StringBuilder builder)
        {
    //		builder.append("typedef struct ExceptionData ExceptionData;\n");
        
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
            
                if (child instanceof ClassDeclaration)
                {
                    ClassDeclaration clazz = (ClassDeclaration)child;
                
                    builder.append("typedef struct ").append(clazz.getTarget().generateSourceName()).append(' ').append(clazz.getTarget().generateSourceName()).append(';').append('\n');
                }
            }

    //		ImportList imports = getImportList();
    //		
    //		for (int i = 0; i < imports.getNumChildren(); i++)
    //		{
    //			Import node = (Import)imports.getChild(i);
    //			
    //			if (!node().isExternal())
    //			{
    //				String name = node().getLocationNode().getName();
    //				
    //				builder.append("typedef struct ").append(name).append(' ').append(name).append(';').append('\n');
    //			}
    //		}
        
            return builder;
        }
    
        private StringBuilder generateSourceClosureContextDefinitions(StringBuilder builder)
        {
            for (ClosureContext context : node().contexts)
            {
                context.getTarget().generateSource(builder);
            }
        
            return builder;
        }
    
        /**
         * Generate the type definitions for the closures used within the
         * file.
         *
         * @param builder The StringBuilder to append the data to.
         * @param publicClosures Whether to generate the definitions for the
         * 		public closures, or the private ones.
         * @return The StringBuilder with the appended data.
         */
        private StringBuilder generateClosureDefinitions(StringBuilder builder, boolean publicClosures)
        {
            ArrayList<String> types = new ArrayList<>();
        
            for (ClosureDeclaration closure : node().closures)
            {
                if (closure.isPublic() == publicClosures)
                {
                    SyntaxUtils.addTypesToTypeList(builder, closure, types);
                }
            }
        
            if (types.size() > 0)
            {
                builder.append('\n');
            }
        
            for (ClosureDeclaration closure : node().closures)
            {
                if (closure.isPublic() == publicClosures)
                {
                    closure.getTarget().generateClosureDefinition(builder);
                }
            }
        
            return builder;
        }
        
        /**
         * Format the C Header output, if the output has been generated.
         */
        public void formatHeaderOutput()
        {
            if (node().header == null)
            {
                return;
            }
    
            node().setHeader(SyntaxUtils.formatText(node().header.toString()));
        }
    
        /**
         * Format the C Source output, if the output has been generated.
         */
        public void formatSourceOutput()
        {
            if (node().source == null)
            {
                return;
            }
    
            node().setSource(SyntaxUtils.formatText(node().source.toString()));
        }
    }
    
    public static abstract class TargetExternalMethodDeclaration extends TargetMethodDeclaration
    {
		public abstract ExternalMethodDeclaration node();

        public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
        {
            return builder.append(node().alias);
        }
    }
    
    public static abstract class TargetInterfaceVTable extends TargetVTable
    {
		public abstract InterfaceVTable node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            return builder;
        }
    
        public StringBuilder generateHeaderFragment(StringBuilder builder)
        {
            return generateType(builder).append(" ").append(InterfaceVTable.IDENTIFIER);
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            return builder;
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            NovaMethodDeclaration[] methods = node().getVirtualMethods();
        
            builder.append("{\n");
        
            for (NovaMethodDeclaration method : methods)
            {
                if (method != null)
                {
                    method.getTarget().generateInterfaceVTableSource(builder);
                }
                else
                {
                    builder.append(0);
                }
            
                builder.append(",\n");
            }
        
            builder.append("}");
        
            return builder;
            //return super.generateSourceFragment(builder);
        }
    }
    
    public static abstract class TargetExtensionVTable extends TargetVTable
    {
		public abstract ExtensionVTable node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            InterfaceVTable table = node().getInterfaceVTable();
            
            table.getTarget().generateHeader(builder).append('\n');
        
            super.generateHeader(builder);
        
            builder.append("extern ").append(generateType()).append(' ').append(generateSourceName()).append(";\n");
        
            return builder;
        }
    }
    
    public static abstract class TargetVTable extends TargetIIdentifier
    {
		public abstract VTable node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            NovaMethodDeclaration methods[] = node().getVirtualMethods();
        
            builder.append("typedef struct ").append(generateTypeName()).append(' ').append(generateTypeName()).append(";\n");
        
            if (methods.length <= 0)
            {
                return builder;
            }
        
            builder.append("struct ").append(generateTypeName()).append("\n{\n");
        
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
            
                child.getTarget().generateHeaderFragment(builder).append(";\n");
            }
        
            generateVirtualMethodDeclarations(builder, methods);
            
            builder.append("}").append(";\n\n");
        
            return builder;
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            NovaMethodDeclaration methods[] = node().getVirtualMethods();
        
            if (methods.length <= 0)
            {
                return builder;
            }
        
            generateType(builder).append(' ').append(generateSourceName()).append(" =\n");
        
            builder.append("{\n");
        
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
            
                child.getTarget().generateSourceFragment(builder).append(",\n");
            }
        
            generateVirtualMethodValues(builder, methods);
            
            builder.append("};\n");
        
            return builder;
        }
    
        /**
         * Generate the virtual method declarations that declares the names
         * of the methods that are used in the class and its ancestors.
         *
         * @param builder The StringBuilder to append the data to.
         * @param methods The methods to add the identifiers from.
         * @return The StringBuilder with the appended data.
         */
        public StringBuilder generateVirtualMethodDeclarations(StringBuilder builder, NovaMethodDeclaration methods[])
        {
            for (NovaMethodDeclaration method : methods)
            {
                generateVirtualMethodDeclaration(builder, method);
            }
        
            return builder;
        }
    
        /**
         * Generate the virtual method declaration that declares the name
         * of the given method.
         *
         * @param builder The StringBuilder to append the data to.
         * @param method The method to add the identifier from.
         * @return The StringBuilder with the appended data.
         */
        public StringBuilder generateVirtualMethodDeclaration(StringBuilder builder, NovaMethodDeclaration method)
        {
            VirtualMethodDeclaration virtual = method.getVirtualMethod();
            ParameterList params = method.getParameterList();
            
            return method.getTarget().generateType(builder).append(" (*").append(virtual.getTarget().generateVirtualMethodName()).append(")(").append(params.getTarget().generateHeader()).append(");\n");
        }
    
        /**
         * Add the vtable values that point to the correct virtual method
         * implementation for the specified class.
         *
         * @param builder The StringBuilder to append the data to.
         * @param methods The methods to add the references to.
         * @return The StringBuilder with the appended data.
         */
        public StringBuilder generateVirtualMethodValues(StringBuilder builder, NovaMethodDeclaration methods[])
        {
            for (NovaMethodDeclaration method : methods)
            {
                if (method != null)
                {
    //				method.generateVirtualMethodName(builder);
                    if (method instanceof AbstractMethodDeclaration)
                    {
                        method = method.getVirtualMethod();
                    }
                
                    method.getTarget().generateSourceName(builder);
                }
                else
                {
                    builder.append(0);
                }
            
                builder.append(",\n");
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetIfStatement extends TargetControlStatement
    {
		public abstract IfStatement node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            generateSourceFragment(builder).append('\n');
            
            Scope scope = node().getScope();
            
            scope.getTarget().generateSource(builder);
        
            return builder;
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            Value condition = node().getCondition();
            
            return builder.append("if (").append(condition.getTarget().generateSourceFragment()).append(')');
        }
    }
    
    public static abstract class TargetElseStatement extends TargetControlStatement
    {
		public abstract ElseStatement node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            builder.append("else");
        
            if (node().getNumChildren() == 2)
            {
                Node child = node().getChild(1);
            
                if (child instanceof IfStatement)
                {
                    builder.append(' ');
                
                    child.getTarget().generateSourceFragment(builder);
                }
            }
        
            builder.append('\n');
            
            Scope scope = node().getScope();
            
            return scope.getTarget().generateSource(builder);
        }
    }
    
    public static abstract class TargetDimensions extends TargetNode
    {
		public abstract Dimensions node();

        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            return generateSourceFragment(builder, "[", "]");
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder, String prefix, String postfix)
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
                
                builder.append(prefix).append(child.getTarget().generateSourceFragment()).append(postfix);
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetDestructor extends TargetBodyMethodDeclaration
    {
		public abstract Destructor node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            generateSourceSignature(builder).append('\n').append('{').append('\n');
    
            nullChecker(builder).append('\n');
        
            deleteData(builder).append('\n');
        
            for (int i = 0; i < node().getNumVisibleChildren(); i++)
            {
                Node child = node().getVisibleChild(i);
            
                if (child != node().getParameterList())
                {
                    child.getTarget().generateSource(builder);
                }
            }
        
            builder.append("NOVA_FREE(").append('*').append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(");").append('\n');
        
            builder.append('}').append('\n');
        
            return builder;
        }
    
        /**
         * Generate the code needed to check if a variable is null before
         * trying to free its members.
         *
         * @return The code needed to check whether a variable is null or not.
         */
        private StringBuilder nullChecker(StringBuilder builder)
        {
            builder.append("if (!*").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(')').append('\n');
            builder.append('{').append('\n');
            builder.append("return;").append('\n');
            builder.append('}').append('\n');
        
            return builder;
        }
    
        /**
         * Generate the code needed to delete each member of the class.
         *
         * @return The code needed to delete each member of the class.
         */
        private StringBuilder deleteData(StringBuilder builder)
        {
            ClassDeclaration  classDeclaration = node().getParentClass();
            InstanceFieldList privateFields    = classDeclaration.getFieldList().getPrivateFieldList();
        
            for (int i = 0; i < privateFields.getNumChildren(); i++)
            {
                FieldDeclaration field = (FieldDeclaration)privateFields.getChild(i);
            
                generateFreeFieldSource(builder, field).append('\n');
            }
        
            if (classDeclaration.containsNonStaticPrivateData())
            {
                generateFreeMemberSource(builder, "prv").append('\n');
            }
        
            InstanceFieldList publicFields = classDeclaration.getFieldList().getPublicFieldList();
        
            for (int i = 0; i < publicFields.getNumChildren(); i++)
            {
                FieldDeclaration field = (FieldDeclaration)publicFields.getChild(i);

//			field.generateFreeOutput(builder);
                generateFreeFieldSource(builder, field).append('\n');
            }
        
            return builder;
        }
    
        /**
         * Generate a String for the code used to free memory of an allocated
         * field variable located within the current class.
         *
         * @param field The node that contains the information of the field.
         * @return The generated String for the code.
         */
        private StringBuilder generateFreeFieldSource(StringBuilder builder, FieldDeclaration field)
        {
            if (field.isPrimitiveType() || field.isExternalType() || field.isGenericType())
            {
                if (!field.isPrimitive())
                {
                    //builder.append("NOVA_FREE(").append(field.generateVariableUseOutput(true)).append(");");builder.append("printf(\"Aft2.\");");
                }
            }
            else
            {
                if (field.isPrimitiveArray())
                {
    //				void nova_free_array(void** array, int* dimensionSizes, int dimension, int dimensions, del_function function);
    //				builder.append("nova_free_array(" + field.generateUseOutput(new StringBuilder(), true) + ", );");
                    builder.append("NOVA_FREE(" + field.getTarget().generateUseOutput(new StringBuilder(), true) + ");");
                }
                else if (field.getTypeClass().getDestructor() != null)
                {
                    Destructor dest = field.getTypeClass().getDestructor();
                    
                    dest.getTarget().generateSourceName(builder).append('(').append('&');
                
                    field.getTarget().generateUseOutput(builder, true).append(", ").append(Exception.EXCEPTION_DATA_IDENTIFIER).append(");");
                }
            }
        
            return builder;
        }
    
        /**
         * Generate a String for the code used to free memory of an allocated
         * member variable with the given name.
         *
         * @param name The name of the variable to delete.
         * @return The generated String for the code.
         */
        private StringBuilder generateFreeMemberSource(StringBuilder builder, String name)
        {
            return builder.append("NOVA_FREE((*").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(")->").append(name).append(");");
        }
        
    //	/**
    //	 * @see net.fathomsoft.nova.tree.MethodDeclaration#generateSourcePrototype( node, StringBuilder)
    //	 */
    //	@Override
    //	public StringBuilder generateSourcePrototype( node, StringBuilder builder)
    //	{
    //		return generateSourceSignature(builder).append(";");
    //	}
    //	
    //	/**
    //	 * @see net.fathomsoft.nova.tree.MethodDeclaration#generateSourceSignature( node, StringBuilder)
    //	 */
    //	@Override
    //	public StringBuilder generateSourceSignature( node, StringBuilder builder)
    //	{
    //		ClassDeclaration classDeclaration = getParentClass();
    //		
    //		generateType(builder).append(' ');
    //		generateSourceName(builder).append('(').append(getParameterList().generateSource()).append(')');
    //		
    //		return builder;
    //	}
    }
    
    public static abstract class TargetDefaultParameterInitialization extends TargetNode
    {
		public abstract DefaultParameterInitialization node();

        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            String use = node().parameter.getTarget().generateUseOutput().toString();
        
            builder.append(use).append(" = ");
            node().parameter.getTarget().generateTypeCast(builder).append('(');
        
            builder.append(use).append(" == ");
        
            if (node().parameter.isPrimitive())
            {
                builder.append("(intptr_t)nova_null");
            }
            else
            {
                builder.append(0);
            }
            
            ClassDeclaration obj = node().getProgram().getClassDeclaration("nova/Object");
            
            String cast = !node().parameter.getDefaultValue().isPrimitive() ? obj.getTarget().generateTypeCast().toString() : "";
            
            Value defaultValue = node().parameter.getDefaultValue();
            
            builder.append(" ? ").append(cast).append(defaultValue.getTarget().generateSourceFragment()).append(" : ").append(cast).append(use);
        
            return builder.append(");");
        }
    }
    
    public static abstract class TargetContinue extends TargetNode
    {
		public abstract Continue node();

        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            return builder.append("continue;");
        }
    }
    
    public static abstract class TargetConstructor extends TargetBodyMethodDeclaration
    {
		public abstract Constructor node();

        public StringBuilder generateTypeName(StringBuilder builder)
        {
            return generateTypeClassName(builder);
        }
    
        public StringBuilder generateHeader(StringBuilder builder)
        {
            if (node().isVisibilityValid())
            {
                if (node().getVisibility() == InstanceDeclaration.PRIVATE)
                {
                    return builder;
                }
            }
        
            if (node().isReference())
            {
                SyntaxMessage.error("Constructor cannot return a reference", node());
            }
        
            return generateSourcePrototype(builder).append('\n');
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            generateSourceSignature(builder).append('\n');
        
            builder.append('{').append('\n');
        
            ClassDeclaration classDeclaration = node().getParentClass();
        
            if (classDeclaration.containsNonStaticData() || classDeclaration.containsVirtualMethods())
            {
                ClassDeclaration clazz = node().getTypeClass();
                
                builder.append("CCLASS_NEW(").append(clazz.getTarget().generateSourceName()).append(", ").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
            
                if (!classDeclaration.containsNonStaticPrivateData())
                {
                    builder.append(",");
                }
            
                builder.append(");");
            }
            else
            {
                builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(" = ").append(generateTypeCast()).append("1").append(';');
            }
        
            builder.append('\n');
        
            VTable extension = node().getParentClass().getVTableNodes().getExtensionVTable();
        
            builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append("->").append(VTable.IDENTIFIER).append(" = &").append(extension.getTarget().generateSourceName()).append(";\n");
        
            {
                Stack<AssignmentMethod> calls = new Stack<>();
            
                ClassDeclaration extended = node().getParentClass().getExtendedClassDeclaration();
            
                while (extended != null)
                {
                    calls.push(extended.getAssignmentMethodNode());
                
                    extended = extended.getExtendedClassDeclaration();
                }
            
                while (!calls.isEmpty())
                {
                    AssignmentMethod method = calls.pop();
                
                    if (method != null)
                    {
                        method.getTarget().generateMethodCall(builder, true);
                    }
                }
            }
        
            // Generate super calls.
            {
                Stack<MethodCall> calls = new Stack<>();
    
                node().addSuperCallFor(calls, node());
            
                while (!calls.isEmpty())
                {
                    MethodCall call = calls.pop();
                    
                    call.getTarget().generateSource(builder);
                }
            }
    
            AssignmentMethod assignmentMethod = node().getParentClass().getAssignmentMethodNode();
            
            assignmentMethod.getTarget().generateMethodCall(builder);
        
            builder.append('\n');
    
            Scope scope = node().getScope();
            
            scope.getTarget().generateSource(builder);
        
            builder.append('\n');
        
            builder.append("return ").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(';').append('\n');
        
            builder.append('}').append('\n');
        
            return builder;
        }
    
        public String getCName()
        {
            return Constructor.IDENTIFIER;
        }
    }
    
    public static abstract class TargetClosureParameterList extends TargetParameterList
    {
		public abstract ClosureParameterList node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            generateHeaderDefaultParameters(builder);
        
            for (Value param : node())
            {
                builder.append(", ");
            
                param.getTarget().generateHeader(builder);
            }
        
            builder.append(", void*");
        
            return builder;
        }
    }
    
    public static abstract class TargetParameterList extends TargetTypeList
    {
		public abstract ParameterList node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            return generateHeaderParameters(builder);
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceParameters(builder);
        }
    
        public StringBuilder generateHeaderParameters(StringBuilder builder)
        {
            generateHeaderDefaultParameters(builder);
        
            for (int i = 0; i < node().getNumVisibleChildren(); i++)
            {
                if (i > 0 || node().getParameterOffset() > 0)
                {
                    builder.append(", ");
                }
    
                Node child = node().getVisibleChild(i);
                
                child.getTarget().generateHeader(builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateSourceParameters(StringBuilder builder)
        {
            generateSourceDefaultParameters(builder);
        
            for (int i = 0; i < node().getNumVisibleChildren(); i++)
            {
                if (i > 0 || node().getParameterOffset() > 0)
                {
                    builder.append(", ");
                }
    
                Node child = node().getVisibleChild(i);
                
                child.getTarget().generateSource(builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateHeaderDefaultParameters(StringBuilder builder)
        {
            for (int i = 0; i < node().getParameterOffset(); i++)
            {
                if (i > 0)
                {
                    builder.append(", ");
                }
    
                Node child = node().getChild(i);
                
                child.getTarget().generateHeader(builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateSourceDefaultParameters(StringBuilder builder)
        {
            for (int i = 0; i < node().getParameterOffset(); i++)
            {
                if (i > 0)
                {
                    builder.append(", ");
                }
    
                Node child = node().getChild(i);
                
                child.getTarget().generateSource(builder);
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetClosureDeclaration extends TargetParameter
    {
		public abstract ClosureDeclaration node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            return generateSource(builder);
        }
        
        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder);
        }
        
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            builder.append(generateType()).append(' ').append(generateSourceName()).append(", ");
            
            Parameter ref = node().getParameterList().getObjectReference();
            
            ref.getTarget().generateType(builder).append(' ');
            
            generateObjectReferenceIdentifier(builder).append(", ");
            generateContextParameter(builder);
        
            return builder;
        }
        
        public StringBuilder generateArguments(StringBuilder builder, Variable context, NovaMethodDeclaration method)
        {
            if (context.getRootReferenceNode() instanceof ClassDeclaration == false)
            {
                Accessible root = context.getRootReferenceNode();
                
                root.getTarget().generateArgumentReference(builder, context);
            }
            else
            {
                builder.append(ClosureDeclaration.NULL_IDENTIFIER);//method.getParameterList().getObjectReference().generateNullOutput(builder);
            }
        
            builder.append(", ");
            method.getTarget().generateClosureContext(builder);
        
            return builder;
        }
        
        public StringBuilder generateObjectReferenceIdentifier(StringBuilder builder)
        {
            return builder.append(generateSourceName("ref"));
        }
        
        public StringBuilder generateContextParameter()
        {
            return generateContextParameter(new StringBuilder());
        }
        
        public StringBuilder generateContextParameter(StringBuilder builder)
        {
            return builder.append("void* ").append(node().getContextName());
        }
        
        public StringBuilder generateType(StringBuilder builder, boolean checkArray, boolean checkValueReference)
        {
            return builder.append(generateSourceName("closure" + node().id));
        }
        
        /**
         * Generate the C type definition for the closure of the specified
         * method declaration.<br>
         * <br>
         * For example:
         * <blockquote><pre>
         * public void test()
         * {
         * 	...
         * }</pre></blockquote>
         * will output will have the effect of
         * "<code>typedef void (*closure_test)();</code>"
         *
         * @return The C closure type definition for the method.
         */
        public StringBuilder generateClosureDefinition(StringBuilder builder)
        {
            builder.append("typedef ");
        
            super.generateType(builder, true, true).append(" (*").append(generateSourceName("closure" + node().id)).append(')');
            
            ParameterList params = node().getParameterList();
            
            builder.append('(').append(params.getTarget().generateHeader()).append(')').append(";\n");
        
            return builder;
        }
    }
    
    public static abstract class TargetParameter extends TargetLocalDeclaration
    {
		public abstract Parameter node();

        public StringBuilder generateTypeName(StringBuilder builder)
        {
            if (node().isObjectReference() && node().getType() != null)
            {
                return generateTypeClassName(builder);
            }
            /*else if (getTypeClass() != null && getTypeClass().equals(getProgram().getClassDeclaration(Nova.getClassLocation("Number"))))
            {
                return builder.append("long_long");
            }*/
        
            return super.generateTypeName(builder);
        }
    
        public StringBuilder generateHeader(StringBuilder builder)
        {
            return generateModifiersSource(builder);
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateHeader(builder).append(' ').append(generateSourceName());
        }
    }
    
    public static abstract class TargetVariableDeclarationList extends TargetList
    {
		public abstract VariableDeclarationList node();

        /**
         * Generate the output needed to free the variables after they are
         * finished with.
         *
         * @return The String output of the variables being freed.
         */
        public StringBuilder generateFreeVariablesOutput(StringBuilder builder)
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                VariableDeclaration variable = (VariableDeclaration)node().getChild(i);
            
                variable.getTarget().generateFreeOutput(builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateHeader(StringBuilder builder)
        {
            return builder;
        }
    
        public StringBuilder generateSource(StringBuilder builder)
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                LocalDeclaration child = (LocalDeclaration)node().getChild(i);
            
                child.getTarget().generateDeclarationFragment(builder).append(" = ");
                child.getTarget().generateDefaultValue(builder);
            
                builder.append(";\n");
            }
        
            if (node().getNumChildren() > 0)
            {
                builder.append('\n');
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetClosureContextDeclaration extends TargetLocalDeclaration
    {
		public abstract ClosureContextDeclaration node();

        public StringBuilder generateDeclarationFragment(StringBuilder builder)
        {
            return builder.append(node().context.getName()).append(' ').append(node().getName());
        }
        
        public StringBuilder generateDefaultValue(StringBuilder builder)
        {
            builder.append("\n{\n");
        
            for (ClosureVariableDeclaration var : node().context)
            {
                generateDeclarationValue(builder, var);
            }
        
            return builder.append("}");
        }
        
        public StringBuilder generateDeclarationValue(StringBuilder builder, ClosureVariableDeclaration var)
        {
            //Variable v = var.generateUsableVariable(node(), Location.INVALID);
        
            if (var.originalDeclaration instanceof ClosureVariableDeclaration)
            {
                builder.append("context->");
            }
            else
            {
                builder.append('&');
            }
        
            var.originalDeclaration.getTarget().generateSourceName(builder);
        
            return builder.append(",\n");
        }
    }
    
    public static abstract class TargetFieldDeclaration extends TargetInstanceDeclaration
    {
		public abstract FieldDeclaration node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            if (node().isStatic() && (node().getVisibility() == FieldDeclaration.PUBLIC || node().getVisibility() == FieldDeclaration.VISIBLE))
            {
                builder.append("extern ");
            }
        
            return generateSource(builder);
        }
    }
    
    public static abstract class TargetLocalDeclaration extends TargetVariableDeclaration
    {
		public abstract LocalDeclaration node();

        public StringBuilder generateType(StringBuilder builder, boolean checkArray, boolean checkValueReference)
        {
            if (node().isImplicit())
            {
                /*builder.append("void*");
                
                if (checkValueReference && isValueReference())
                {
                    builder.append('*');
                }
                
                return builder;*/
                return node().implicitType.getTarget().generateType(builder, checkArray, checkValueReference);
            }
        
            return super.generateType(builder, checkArray, checkValueReference);
        }
    }
    
    public static abstract class TargetClosureContext extends TargetTypeList
    {
		public abstract ClosureContext node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder).append(";\n");
        }
        
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            builder.append("typedef struct\n");
            builder.append("{\n");
        
            for (ClosureVariableDeclaration var : node())
            {
                builder.append("/* ").append(var.originalDeclaration).append(" */ ");
                var.getTarget().generateSource(builder);
			
                /*boolean original = var.originalDeclaration.isValueReference();
                var.originalDeclaration.setIsValueReference(true);
                var.originalDeclaration.generateSource(builder);
                var.originalDeclaration.setIsValueReference(original);*/
            }
        
            builder.append("} ").append(node().getName());
        
            return builder;
        }
    }
    
    public static abstract class TargetTypeList extends TargetList
    {
		public abstract TypeList node();

        public StringBuilder generateHeaderFragment(StringBuilder builder)
        {
            for (Object child : node())
            {
                ((Node)child).getTarget().generateHeader(builder);
            }
        
            return builder;
        }
        
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            for (Object child : node())
            {
                ((Node)child).getTarget().generateSource(builder);
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetClosure extends TargetVariable
    {
		public abstract Closure node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder);
        }
        
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            ClosureDeclaration decl = node().getClosureDeclaration();
            
            decl.getTarget().generateTypeCast(builder);
        
            if (node().getMethodDeclaration().isVirtual() && !node().isVirtualTypeKnown())
            {
                Accessible root = node().getRootReferenceNode();
                
                root.getTarget().generateArgumentReference(builder, node()).append("->").append(VTable.IDENTIFIER).append("->");
                
                VirtualMethodDeclaration virtual = node().getMethodDeclaration().getVirtualMethod();
                
                builder.append(virtual.getTarget().generateVirtualMethodName());
            }
            else
            {
                VariableDeclaration d = node().getDeclaration();
                
                builder.append('&').append(d.getTarget().generateSourceName());
            }
        
            builder.append(", ");
    
            decl.getTarget().generateArguments(builder, node(), node().getMethodDeclaration());
        
            return builder;
        }
    }
    
    public static abstract class TargetVariable extends TargetIdentifier
    {
		public abstract Variable node();

        public StringBuilder generateSourcePrefix(StringBuilder builder)
        {
            super.generateSourcePrefix(builder);
        
            if (node().declaration instanceof ClosureVariableDeclaration)
            {
                builder.append(ClosureVariableDeclaration.CONTEXT_VARIABLE_NAME).append("->");
            }
        
            return builder;
        }
        
        public StringBuilder generateArgumentOutput(StringBuilder builder)
        {
            super.generateArgumentOutput(builder);
        
            generateExtraArguments(builder);
        
            return builder;
        }
    
        public StringBuilder generateExtraArguments(StringBuilder builder)
        {
            if (node().getDeclaration() instanceof ClosureDeclaration)
            {
                builder.append(", ");
            
                ClosureDeclaration declaration = (ClosureDeclaration)node().getDeclaration();
            
                if (declaration.getParent() instanceof NovaParameterList)
                {
                    builder.append(declaration.getContextName());
                }
                else
                {
                    declaration.getTarget().generateArguments(builder, node(), node().getParentMethod());
                }
            }
        
            return builder;
        }
        
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            super.generateSourceFragment(builder);
        
            generateObjectReferenceIdentifier(builder);
        
            return builder;
        }
    
        public StringBuilder generateObjectReferenceIdentifier(StringBuilder builder)
        {
            if (node().getDeclaration() instanceof ClosureDeclaration && node().getParent() instanceof ArgumentList)
            {
                ClosureDeclaration declaration = (ClosureDeclaration)node().getDeclaration();
            
                builder.append(", ");
                declaration.getTarget().generateObjectReferenceIdentifier(builder);
            }
        
            return builder;
        }
    
        public String generateGenericType()
        {
            GenericTypeArgumentList args = node().getGenericTypeArgumentList();
        
            if (args != null && args.getNumVisibleChildren() > 0)
            {
                String s = GenericCompatible.GENERIC_START;
            
                for (int i = 0; i < args.getNumVisibleChildren(); i++)
                {
                    if (i > 0)
                    {
                        s += ", ";
                    }
                
                    //GenericTypeArgument arg = getGenericTypeArgumentFromParameter(args.getVisibleChild(i).getType());
                    GenericTypeArgument arg = args.getVisibleChild(i);
                
                    s += arg.generateNovaInput(new StringBuilder(), true, node());
                }
            
                s += GenericCompatible.GENERIC_END;
            
                return s;
            }
        
            return "";
        }
    }
    
    public static abstract class TargetClassDeclaration extends TargetInstanceDeclaration
    {
		public abstract ClassDeclaration node();

        /**
         * Generate the C output for when node() value node is being used
         * as an argument for a method call.
         *
         * @param builder The StringBuilder to append the data to.
         * @param callingMethod The method that is being called by the
         * 		specified Identifier.
         * @return The C output for when node() value node is being used
         * 		as an argument for a method call.
         */
        public StringBuilder generateArgumentReference(StringBuilder builder, Identifier callingMethod)
        {
            if (callingMethod instanceof MethodCall)
            {
                CallableMethod declaration = ((MethodCall)callingMethod).getInferredDeclaration();
                
                if (declaration.isStatic() || declaration instanceof Constructor)
                {
                    Parameter ref = declaration.getParameterList().getObjectReference();
                    
                    return ref.getTarget().generateNullOutput(builder);
                }
                else if (declaration instanceof ClosureDeclaration)
                {
                    ClosureDeclaration closure = (ClosureDeclaration)declaration;
                    
                    return closure.getTarget().generateSourceName(builder, "ref");
                }
            }
            
            return super.generateArgumentReference(builder, callingMethod);
        }
    
        public StringBuilder generateHeaderNativeInterface(StringBuilder builder)
        {
            MethodDeclaration[] methods = node().getVisibleNativeMethods();
		
            /*if (methods.length <= 0)
            {
                return builder;
            }*/
        
            String name = generateSourceName("native").toString();
        
            for (MethodDeclaration method : methods)
            {
                builder.append("typedef " + method.getTarget().generateType() + " (*");
            
                method.getTarget().generateSourceNativeName(builder, true).append(")(");
            
                ParameterList params = method.getParameterList();
                
                params.getTarget().generateHeader(builder).append(");\n");
            }
        
            builder.append("\ntypedef struct " + name + "\n");
            builder.append("{\n");
        
            for (MethodDeclaration method : methods)
            {
                method.getTarget().generateSourceNativeName(builder, true).append(" ");
                method.getTarget().generateSourceNativeName(builder, false).append(";\n");
            }
        
            builder.append("} " + name + ";\n");
        
            return builder;
        }
    
        public StringBuilder generateSourceNativeInterface(StringBuilder builder)
        {
    //		String name = generateSourceName("native").toString();
        
            MethodDeclaration[] methods = node().getVisibleNativeMethods();

    //		builder.append('\n');

    //		builder.append("struct " + name + "\n");
            builder.append("{\n");
        
            for (MethodDeclaration method : methods)
            {
                String value = "&" + method.getTarget().generateSourceName();
            
                if (method instanceof NovaMethodDeclaration)
                {
                    NovaMethodDeclaration n = (NovaMethodDeclaration)method;
                
                    if (n.isOverridden() && !(n instanceof Constructor))
                    {
                        value = "0";//getVTableNode().getName() + "." + n.generateVirtualMethodName();
                    }
                }
            
                builder.append(value + ",\n");
            }
        
            builder.append("},\n");
        
            return builder;
        }
        
        public StringBuilder generateHeader(StringBuilder builder)
        {
            VTableList vtables = node().getVTableNodes();
            
            vtables.getTarget().generateHeader(builder).append('\n');
        
            if (node().containsNonStaticData() || node().containsVirtualMethods())
            {
                builder.append("CCLASS_CLASS").append('\n').append('(').append('\n');
            
                generateSourceName(builder).append(", ").append('\n').append('\n');
            
                VTable extension = node().getVTableNodes().getExtensionVTable();
            
                builder.append(extension.getTarget().generateType()).append("* ").append(VTable.IDENTIFIER).append(";\n");
    
                FieldList fields = node().getFieldList();
                
                fields.getTarget().generateNonStaticHeader(builder);
            
                if (node().containsNonStaticPrivateData())
                {
                    builder.append("struct Private* prv;").append('\n');
                }
            
                builder.append(')').append('\n');
            }
    
            FieldList fields = node().getFieldList();
            
            fields.getTarget().generateStaticHeader(builder).append('\n');
        
            if (node().getStaticBlockList().getNumVisibleChildren() > 0)
            {
                StaticBlock child = node().getStaticBlockList().getChild(0);
                
                child.getTarget().generateHeader(builder, node());
            }
        
            MethodList constructors = node().getConstructorList();
            constructors.getTarget().generateHeader(builder);
    
            node().getDestructorList().getTarget().generateHeader(builder);
            node().getMethodList().getTarget().generateHeader(builder);
            node().getPropertyMethodList().getTarget().generateHeader(builder);
            node().getHiddenMethodList().getTarget().generateHeader(builder);
            node().getVirtualMethodList().getTarget().generateHeader(builder);
        
            return builder;
        }
        
        public StringBuilder generateSource(StringBuilder builder)
        {
            VTableList vtables = node().getVTableNodes();
            
            vtables.getTarget().generateSource(builder).append('\n');
        
            if (node().containsNonStaticPrivateData())
            {
                builder.append("CCLASS_PRIVATE").append('\n').append('(').append('\n').append(generatePrivateFieldsSource()).append(')').append('\n');
            }
        
            builder.append(generatePrivateMethodPrototypes());
    
            FieldList fields = node().getFieldList();
            
            fields.getTarget().generateStaticSource(builder);
        
            for (int i = node().getNumDefaultChildren(); i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
            
                builder.append('\n').append(child.getTarget().generateSource());
            }
    
            fields = node().getFieldList();
            
            fields.getTarget().generateNonStaticSource(builder);
        
            generateStaticBlocksSource(builder);
    
            node().getConstructorList().getTarget().generateSource(builder);
            node().getDestructorList().getTarget().generateSource(builder);
            node().getMethodList().getTarget().generateSource(builder);
            node().getPropertyMethodList().getTarget().generateSource(builder);
            node().getHiddenMethodList().getTarget().generateSource(builder);
            node().getVirtualMethodList().getTarget().generateSource(builder);
        
            return builder;
        }
    
        private StringBuilder generateStaticBlocksSource(StringBuilder builder)
        {
            if (node().getStaticBlockList().getNumVisibleChildren() > 0)
            {
                StaticBlock block = node().getStaticBlockList().getChild(0);
                
                block.getTarget().generateMethodHeader(builder, node()).append('\n');
            
                builder.append('{').append('\n');
            
                for (int i = 0; i < node().getStaticBlockList().getNumVisibleChildren(); i++)
                {
                    block = node().getStaticBlockList().getChild(i);
                
                    block.getTarget().generateSource(builder);
                }
            
                builder.append('}').append('\n');
            }
        
            return builder;
        }
    
        /**
         * Generate the C source representation of the private field
         * declarations.
         *
         * @return The StringBuilder with the appended data.
         */
        private StringBuilder generatePrivateFieldsSource()
        {
            return generatePrivateFieldsSource(new StringBuilder());
        }
    
        /**
         * Generate the C source representation of the private field
         * declarations.
         *
         * @param builder The StringBuilder to append that data to.
         * @return The StringBuilder with the appended data.
         */
        private StringBuilder generatePrivateFieldsSource(StringBuilder builder)
        {
            if (node().getExtendedClassDeclaration() != null)
            {
                ClassDeclaration clazz = node().getExtendedClassDeclaration();
                
                clazz.getTarget().generatePrivateFieldsSource(builder);
            }
        
            InstanceFieldList fields = node().getFieldList().getPrivateFieldList();
            
            return fields.getTarget().generateSource(builder);
        }
        
        public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
        {
            if (uniquePrefix == null)
            {
                uniquePrefix = Nova.LANGUAGE_NAME;
            }
        
            return generateUniquePrefix(builder).append(uniquePrefix).append("_").append(node().getName());
        }
    
        /**
         * Generate the prototypes for specifically the private methods.
         *
         * @return A String containing the prototype definitions.
         */
        private String generatePrivateMethodPrototypes()
        {
            StringBuilder  builder = new StringBuilder();
        
            generatePrototypes(builder, node().getMethodList());
            generatePrototypes(builder, node().getPropertyMethodList());
        
            if (builder.length() > 0)
            {
                builder.insert(0, '\n');
            }
        
            return builder.toString();
        }
    
        private void generatePrototypes(StringBuilder builder, MethodList methods)
        {
            for (int i = 0; i < methods.getNumChildren(); i++)
            {
                MethodDeclaration methodDeclaration = methods.getChild(i);
            
                if (methodDeclaration.getVisibility() == InstanceDeclaration.PRIVATE)
                {
                    methodDeclaration.getTarget().generateSourcePrototype(builder).append('\n');
                }
            }
        }
        
        public StringBuilder generateUniquePrefix()
        {
            return generateUniquePrefix(new StringBuilder());
        }
    
        /**
         * Get the prefix that is used for the data that is contained
         * within the specified class.<br>
         * <br>
         * For example:
         * <blockquote><pre>
         * package "node()/is/my/package"
         *
         * public class Test
         * {
         * 	...
         * }</pre></blockquote>
         * The method prefix would look like:
         * "<code>node()_is_my_package_NovaTest</code>"
         *
         * @return The prefix that is used for the data contained within
         * 		the class.
         */
        public StringBuilder generateUniquePrefix(StringBuilder builder)
        {
            Package p = node().getFileDeclaration().getPackage();
            
            return p.getTarget().generateLocation(builder).append('_');
        }
    }
    
    public static abstract class TargetCast extends TargetIValue
    {
		public abstract Cast node();

        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            builder.append('(').append(generateType()).append(')');
            
            Value value = node().getValueNode();
            Value ret = value.getReturnedNode();
            
            ret.getTarget().generatePointerToValueConversion(builder);
            value.getTarget().generateSourceFragment(builder);
        
            return builder;
        }
    }
    
    public static abstract class TargetBreak extends TargetNode
    {
		public abstract Break node();

        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            return builder.append("break;");
        }
    }
    
    public static abstract class TargetBinaryOperation extends TargetIValue
    {
		public abstract BinaryOperation node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            generateSourceFragment(builder);
        
            if (node().getOperator().isShorthand())
            {
                builder.append(";\n");
            }
        
            return builder;
        }
        
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            Operator operator = node().getOperator();
            
            if (node().getNumChildren() == 1)
            {
                Value operand = node().getLeftOperand();
                
                return operand.getTarget().generateSourceFragment(builder);
            }
        
            String leftCast = "";
            String rightCast = "";
            
            Value left = node().getLeftOperand();
            Value right = node().getRightOperand();
            Value leftReturned = left.getReturnedNode();
            Value rightReturned = right.getReturnedNode();
        
            if (leftReturned.isOriginallyGenericType())
            {
                leftCast = leftReturned.getTarget().generateTypeCast(new StringBuilder(), true, false).toString();
            }
            if (rightReturned.isOriginallyGenericType())
            {
                rightCast = rightReturned.getTarget().generateTypeCast(new StringBuilder(), true, false).toString();
            }
        
            return builder.append(leftCast).append(left.getTarget().generateSourceFragment()).append(' ')
                .append(operator.getTarget().generateSourceFragment()).append(' ')
                .append(rightCast).append(right.getTarget().generateSourceFragment());
        }
    }
    
    public static abstract class TargetAssignmentMethod extends TargetBodyMethodDeclaration
    {
		public abstract AssignmentMethod node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            return generateSourcePrototype(builder).append('\n');
        }
        
        public StringBuilder generateSource(StringBuilder builder)
        {
            generateSourceSignature(builder).append('\n');
        
            builder.append('{').append('\n');
        
            generateFieldDefaultAssignments(builder);
        
            for (int i = 0; i < node().getNumVisibleChildren(); i++)
            {
                Node child = node().getVisibleChild(i);
                
                child.getTarget().generateSource(builder);
            }
    
            Scope scope = node().getScope();
            
            scope.getTarget().generateSource(builder, false);
        
            builder.append('}').append('\n');
        
            return builder;
        }
        
        public StringBuilder generateMethodCall(StringBuilder builder)
        {
            return generateMethodCall(builder, false);
        }
    
        /**
         * @param cast Whether or not to add an explicit type cast for the
         * 		object reference identifier.
         */
        public StringBuilder generateMethodCall(StringBuilder builder, boolean cast)
        {
            super.generateMethodCall(builder).append("(");
        
            if (cast)
            {
                ClassDeclaration clazz = node().getParentClass();
                
                builder.append('(').append(clazz.getTarget().generateType()).append(')');
            }
        
            builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
        
            return builder.append(", ").append(Exception.EXCEPTION_DATA_IDENTIFIER).append(");\n");
        }
    
        /**
         * node() method returns a String that contains the code needed to
         * assign the default null value to each uninitialized/uninstantiated
         * field variables.
         *
         * @param builder The StringBuilder to append the assignments to.
         * @return The appended buffer.
         */
        private StringBuilder generateFieldDefaultAssignments(StringBuilder builder)
        {
            FieldList fields = node().getParentClass().getFieldList();
            
            generateFieldDefaultAssignments(builder, fields.getPublicFieldList());
            generateFieldDefaultAssignments(builder, fields.getPrivateFieldList());
            
            return builder;
        }
    
        /**
         * node() method returns a String that contains the code needed to
         * assign the default null value to each uninitialized/uninstantiated
         * field variables.
         *
         * @param builder The StringBuilder to append the assignments to.
         * @param fields The list of fields to assign default values to.
         * @return The appended buffer.
         */
        private StringBuilder generateFieldDefaultAssignments(StringBuilder builder, InstanceFieldList fields)
        {
            for (int i = 0; i < fields.getNumChildren(); i++)
            {
                FieldDeclaration field = (FieldDeclaration)fields.getChild(i);
            
                if (!field.isExternal())
                {
                    field.getTarget().generateUseOutput(builder).append(" = ");
                
                    if (!field.isPrimitiveType() && !field.isExternalType())
                    {
                        field.getTarget().generateNullOutput(builder);
                    }
                    else
                    {
                        builder.append(0);
                    }
                
                    builder.append(';').append('\n');
                }
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetIValue extends TargetValue
    {
		public abstract IValue node();

    }
    
    public static abstract class TargetAssignment extends TargetValue
    {
		public abstract Assignment node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder).append(";\n");
        }
        
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            if (node().getAssignedNodeValue().getDataType() == Value.POINTER &&
                node().getAssignmentNode().getReturnedNode().getDataType() == Value.VALUE ||
                node().getAssignedNodeValue().getDataType() == Value.DOUBLE_POINTER &&
                    node().getAssignmentNode().getReturnedNode().getDataType() == Value.POINTER)
            {
                builder.append('*');
            }
        
            Value assignee = node().getAssigneeNode();
            
            return assignee.getTarget().generateSourceFragment(builder).append(" = ").append(generateAssignmentSource());
        }
    
        /**
         * Generate the assignment's right hand value C output.
         *
         * @return The assignment's right hand value C output.
         */
        private StringBuilder generateAssignmentSource()
        {
            return generateAssignmentSource(new StringBuilder());
        }
    
        /**
         * Generate the assignment's right hand value C output.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The assignment's right hand value C output.
         */
        private StringBuilder generateAssignmentSource(StringBuilder builder)
        {
            Value assignment = node().getAssignmentNode();
        
            String assignmentType = assignment.getReturnedNode().getType();
            String assignedType = node().getAssignedNodeValue().getType();
        
            boolean sameType = assignmentType.equals(assignedType);
        
            if (sameType && assignment instanceof Accessible)
            {
                MethodCall call = (MethodCall)((Accessible)assignment).getLastAccessedOfType(MethodCall.class, false);
            
                if (call != null)
                {
                    sameType = !call.isVirtual();
                }
            }
            
            Value asignment = node().getAssignmentNode();
            
            if (!sameType)
            {
                Value assigned = node().getAssignedNodeValue();
                Value returned = assignment.getReturnedNode();
                
                assigned.getTarget().generateTypeCast(builder, true, false).append(returned.getTarget().generatePointerToValueConversion(returned)).append('(');
            }
        
            builder.append(assignment.generateDataTypeOutput(node().getAssignedNodeValue().getDataType())).append(assignment.getTarget().generateSourceFragment());
        
            if (!sameType)
            {
                builder.append(')');
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetArgumentList extends TargetList
    {
		public abstract ArgumentList node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder);
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            for (int i = 0; i < node().getNumChildren(); i++)
            {
                Node child = node().getChild(i);
            
                child.getTarget().generateSourceFragment(builder);
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetList extends TargetNode
    {
        
    }
    
    public static abstract class TargetMatch extends TargetControlStatement
    {
		public abstract Match node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            Scope scope = node().getScope();
            
            if (node().isConventionalSwitch())
            {
                Value control = node().getControlValue();
                
                builder.append("switch (" + control.getTarget().generateSourceFragment() + ")\n");
    
                scope.getTarget().generateSource(builder);
            }
            else
            {
                boolean requiresFacade = node().requiresLoopFacade();
            
                if (requiresFacade)
                {
                    builder.append("do\n{\n");
                }
    
                scope.getTarget().generateSource(builder, false);
            
                if (requiresFacade)
                {
                    builder.append("}\nwhile (0);\n");
                }
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetControlStatement extends TargetNode
    {
        
    }
    
    public static abstract class TargetFallthrough extends TargetMatchChild
    {
		public abstract Fallthrough node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            Variable fall = node().getParentSwitch().getLocalFallthrough();
        
            if (fall != null)
            {
                fall.getTarget().generateSourceFragment(builder).append(" = 1;\n");
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetMatchChild extends TargetNode
    {
        
    }
    
    public static abstract class TargetDefault extends TargetMatchCase
    {
		public abstract Default node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            Scope scope = node().getScope();
    
            if (node().getParentSwitch().isConventionalSwitch())
            {
                builder.append("default:").append('\n');
            
                scope.getTarget().generateSource(builder, false);
            }
            else
            {
                builder.append("else").append('\n');
            
                scope.getTarget().generateSource(builder);
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetCase extends TargetMatchCase
    {
		public abstract Case node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            Scope scope = node().getScope();
            
            if (node().getParentSwitch().isConventionalSwitch())
            {
                Value value = node().getValue();
                
                builder.append("case " + value.getTarget().generateSourceFragment() + ":\n");
            
                scope.getTarget().generateSource(builder, false);
            
                if (node().requiresBreak())
                {
                    builder.append("break;\n");
                }
            }
            else
            {
                Value controlValue = node().getParentSwitch().getControlValue();
                
                String control = controlValue.getTarget().generateSourceFragment().toString();
            
                Case before = null;
                String fall   = "";
            
                if (node().getParent().getChildBefore(node()) instanceof Case)
                {
                    before = (Case)node().getParent().getChildBefore(node());
                }
            
                if (before != null)
                {
                    if (before.containsFallthrough())
                    {
                        Variable fallthrough = node().getParentSwitch().getLocalFallthrough();
                        
                        fall = fallthrough.getTarget().generateSourceFragment() + " || ";
                    }
                    else
                    {
                        builder.append("else ");
                    }
                }
                
                Value value = node().getValue();
                
                builder.append("if (" + fall + control + " == " + value.getTarget().generateSourceFragment() + ")").append('\n');
                builder.append("{\n");
            
                scope.getTarget().generateSource(builder, false);
            
                if (node().getParentSwitch().requiresLoopFacade() && node().requiresBreak())
                {
                    builder.append("break;\n");
                }
            
                builder.append("}\n");
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetMatchCase extends TargetNode
    {
        
    }
    
    public static abstract class TargetLambdaMethodDeclaration extends TargetBodyMethodDeclaration
    {
		public abstract LambdaMethodDeclaration node();

        public StringBuilder generateClosureContext(StringBuilder builder)
        {
            return builder.append('&').append(node().contextDeclaration.getName());
        }
    }
    
    public static abstract class TargetTry extends TargetExceptionHandler
    {
		public abstract Try node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            builder.append("TRY").append('\n');
            builder.append('{').append('\n');
            generateExceptionCodes(builder).append('\n');
    
            Scope scope = node().getScope();
            
            scope.getTarget().generateSource(builder);
        
            builder.append('}').append('\n');
        
            return builder;
        }
    
        /**
         * Generate a String that adds all of the exception codes that node()
         * try node catches to the exception data instance.
         *
         * @return The generated C language String.
         */
        private StringBuilder generateExceptionCodes(StringBuilder builder)
        {
            String variableName = Exception.EXCEPTION_DATA_IDENTIFIER;
        
            for (int i = 0; i < node().codes.size(); i++)
            {
                int code = node().codes.get(i);
            
                builder.append("novaEnv.nova_exception_ExceptionData.addCode(").append(variableName).append(", ").append(variableName).append(", ").append(code).append(");").append('\n');
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetThrow extends TargetExceptionHandler
    {
		public abstract Throw node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            builder.append("THROW").append('(').append(node().getException().getID()).append(", ");
            Identifier exception = node().getExceptionInstance();
            
            exception.getTarget().generateSourceFragment(builder).append(')').append(';').append('\n');
        
            return builder;
        }
    }
    
    public static abstract class TargetFinally extends TargetExceptionHandler
    {
		public abstract Finally node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            builder.append("FINALLY").append('\n');
    
            Scope scope = node().getScope();
            
            scope.getTarget().generateSource(builder);
        
            builder.append("END_TRY;").append('\n');
        
            return builder;
        }
    }
    
    public static abstract class TargetCatch extends TargetExceptionHandler
    {
		public abstract Catch node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            builder.append("CATCH ").append('(').append(node().getException().getID()).append(')').append('\n');
        
            Scope scope = node().getScope();
            
            scope.getTarget().generateSource(builder);
        
            return builder;
        }
    }
    
    public static abstract class TargetExceptionHandler extends TargetNode
    {
        
    }
    
    public static abstract class TargetAnnotation extends TargetNode
    {
		public abstract Annotation node();

        public StringBuilder generateHeaderFragment(StringBuilder builder)
        {
            return builder;
        }
        
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            return builder;
        }
    }
    
    public static abstract class TargetAbstractMethodDeclaration extends TargetNovaMethodDeclaration
    {
		public abstract AbstractMethodDeclaration node();

        public StringBuilder generateHeaderFragment(StringBuilder builder)
        {
            return super.generateSourcePrototype(builder);
        }
        
        public StringBuilder generateSource(StringBuilder builder)
        {
            return builder;
        }
        
        public StringBuilder generateInterfaceVTableSource(StringBuilder builder)
        {
            return builder.append(0);
        }
    }
    
    public static abstract class TargetVirtualMethodDeclaration extends TargetBodyMethodDeclaration
    {
		public abstract VirtualMethodDeclaration node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            generateSourceSignature(builder);
		
            /*
            if (getType() == null)
            {
                builder.append("{}");
            }
            else
            {
                builder.append("{return 0;}");
            }
            */
        
            builder.append("\n{\n");
        
            if (node().getType() != null)
            {
                builder.append("return ");
            }
    
            Parameter ref = node().getOriginalParameterList().getObjectReference();
            
            ref.getTarget().generateSourceFragment(builder).append("->");
        
            builder.append(VTable.IDENTIFIER).append("->");
        
            if (node().getParentClass() instanceof Interface)
            {
                builder.append(InterfaceVTable.IDENTIFIER).append(".");
            }
        
            String call = node().getName() + "(";
        
            for (int i = 0; i < node().getParameterList().getNumVisibleChildren(); i++)
            {
                if (i > 0)
                {
                    call += ", ";
                }
            
                call += node().getParameterList().getVisibleChild(i).getName();
            }
        
            call += ")";
        
            MethodCall output = MethodCall.decodeStatement(node().getScope(), call, node().getLocationIn().asNew(), true, true, node());
        
            generateVirtualMethodName(builder);
            output.getArgumentList().getTarget().generateSourceFragment(builder);
        
            return builder.append(";\n}\n");
        }
        
        public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
        {
            return generateVirtualMethodName(builder);
        }
    
        /**
         * Get the identifier for the virtual abstract method in the vtable.
         *
         * @return The identifier for the virtual method in the vtable.
         */
        public StringBuilder generateVirtualMethodName()
        {
            return generateVirtualMethodName(new StringBuilder());
        }
    
        /**
         * Get the identifier for the virtual abstract method in the vtable.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The identifier for the virtual method in the vtable.
         */
        public StringBuilder generateVirtualMethodName(StringBuilder builder)
        {
            String prefix = "virtual";
        
            if (node().base instanceof PropertyMethod)
            {
                prefix += "_" + ((PropertyMethod)node().base).getMethodPrefix();
            }
        
            return generateSourceName(builder, prefix, true);
        }
    }
    
    public static abstract class TargetBodyMethodDeclaration extends TargetNovaMethodDeclaration
    {
		public abstract BodyMethodDeclaration node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            if (node().isVisibilityValid())
            {
                if (node().getVisibility() == InstanceDeclaration.PRIVATE)
                {
                    return builder;
                }
            }
        
            generateSourcePrototype(builder).append('\n');
        
            return builder;
        }
        
        public StringBuilder generateSource(StringBuilder builder)
        {
            generateSourceSignature(builder).append('\n');
            
            Scope scope = node().getScope();
            
            return scope.getTarget().generateSource(builder);
        }
    }
    
    public static abstract class TargetNovaMethodDeclaration extends TargetMethodDeclaration
    {
		public abstract NovaMethodDeclaration node();

        public StringBuilder generateInterfaceVTableSource(StringBuilder builder)
        {
            NovaMethodDeclaration root = node().getVirtualMethod();//.getRootDeclaration();
            NovaParameterList params = root.getParameterList();
            
            builder.append("(").append(root.getTarget().generateType()).append("(*)(").append(params.getTarget().generateHeader()).append("))");
            
            return generateSourceName(builder);
        }
    
        public StringBuilder generateClosureContext(StringBuilder builder)
        {
            return builder.append(NovaMethodDeclaration.NULL_IDENTIFIER);
        }
    
        public StringBuilder generateSourceNativeName(StringBuilder builder, boolean declaration)
        {
            super.generateSourceNativeName(builder, declaration);
    
            if (!declaration && node().isOverloaded())
            {
                for (Parameter param : node().getParameterList())
                {
                    builder.append('_');
    
                    String location = null;
    
                    if (param.isExternalType())
                    {
                        location = param.getType();
                    }
                    else
                    {
                        ClassDeclaration clazz = param.getTypeClass();
    
                        if (clazz != null)
                        {
                            location = clazz.getFileDeclaration().getPackage().getLocation().replace('/', '_');
    
                            if (location.length() > 0)
                            {
                                location += '_';
                            }
    
                            location += clazz.getName();
                        }
                        else
                        {
                            location = "void";
                        }
                    }
    
                    builder.append('_');
    
                    if (param.isPrimitiveArray())
                    {
                        builder.append("Array" + param.getArrayDimensions() + "d_");
                    }
    
                    builder.append(location);
                }
            }
    
            return builder;
        }

        public StringBuilder generateInterfaceVTableHeader(StringBuilder builder)
        {
            VirtualMethodDeclaration virtual = node().getVirtualMethod();
            NovaParameterList params = node().getParameterList();
            
            return generateType(builder).append(" (*").append(virtual.getTarget().generateVirtualMethodName()).append(")(").append(params.getTarget().generateHeader()).append(");\n");
        }

        /**
         * Generate the identifier that will be used to call the method.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The updated StringBuilder.
         */
        public StringBuilder generateMethodCall(StringBuilder builder)
        {
            if (node().isVirtual())
            {
                VirtualMethodDeclaration virtual = node().getVirtualMethod();
                
                return virtual.getTarget().generateVirtualMethodName(builder);
            }

            return super.generateMethodCall(builder);
        }
        
        public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
        {
            return generateSourceName(builder, uniquePrefix, true);
        }
        
        public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix, boolean outputOverload)
        {
            if (node().overloadID == -1)
            {
                return super.generateSourceName(builder, uniquePrefix);
            }

            if (uniquePrefix == null)
            {
                uniquePrefix = "";
            }
            if (outputOverload)
            {
                uniquePrefix += node().overloadID;
            }

            return super.generateSourceName(builder, uniquePrefix);
        }
    }
    
    public static abstract class TargetMethodDeclaration extends TargetInstanceDeclaration
    {
		public abstract MethodDeclaration node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            return generateHeaderFragment(builder);
        }
        
        public StringBuilder generateHeaderFragment(StringBuilder builder)
        {
            return builder;
        }
        
        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder);
        }
        
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            return builder;
        }

        public StringBuilder generateSourceNativeName(StringBuilder builder, boolean declaration)
        {
            if (declaration)
            {
                return generateSourceName(builder, "native");
            }

            return builder.append(node().getName());
    //		String location = getFileDeclaration().getPackage().getLocation().replace('/', '_');
    //		String prefix   = "";
    //		
    //		if (declaration)
    //		{
    //			prefix = "native";
    //			
    ////			if (location.length() > 0)
    ////			{
    ////				prefix += '_';
    ////			}
    //		}
    //		
    ////		if (location.length() > 0)
    ////		{
    ////			location = location + '_';
    ////		}
    //		
    //		builder.append(prefix).append(getName());
    //		
    //		return builder;
        }

        /**
         * Generate the C prototype for the method header.<br>
         * <br>
         * For example:
         * <blockquote><pre>
         * public void test()
         * {
         * 	...
         * }</pre></blockquote>
         * will output as "<code>static void test();</code>"<br>
         * <br>
         * In essence, node() method is just {@link #generateSourceSignature(StringBuilder)}
         * with a semi-colon attached to the end.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The C prototype for the method header.
         */
        public StringBuilder generateSourcePrototype(StringBuilder builder)
        {
            return generateSourceSignature(builder).append(";");
        }

        /**
         * Generate the method signature that will appear in the c source
         * output.<br>
         * <br>
         * For example:
         * <blockquote><pre>
         * public void test()
         * {
         * 	...
         * }</pre></blockquote>
         * will output as "<code>static void test()</code>"
         *
         * @param builder The StringBuilder to append the data to.
         * @return The method signature in the C language.
         */
        public StringBuilder generateSourceSignature(StringBuilder builder)
        {
            generateModifiersSource(builder).append(' ');
            generateSourceName(builder);
            generateParameterOutput(builder);

            return builder;
        }

        public StringBuilder generateParameterOutput(StringBuilder builder)
        {
            builder.append('(');
    
            ParameterList params = node().getParameterList();
            
            params.getTarget().generateSource(builder);

            return builder.append(')');
        }

        /**
         * Generate the identifier that will be used to call the method.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The updated StringBuilder.
         */
        public StringBuilder generateMethodCall(StringBuilder builder)
        {
            return generateSourceName(builder);
        }
    }
    
    public static abstract class TargetInstanceDeclaration extends TargetVariableDeclaration
    {
		public abstract InstanceDeclaration node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            return generateHeaderFragment(builder).append(";\n");
        }
        
        public StringBuilder generateHeaderFragment(StringBuilder builder)
        {
            return generateModifiersSource(builder).append(' ').append(node().getName());
        }
    }
    
    public static abstract class TargetVariableDeclaration extends TargetIIdentifier
    {
		public abstract VariableDeclaration node();

        public StringBuilder generateHeader(StringBuilder builder)
        {
            return generateSource(builder);
        }
        
        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateDeclarationFragment(builder).append(";\n");
        }

        /**
         * Generate a String with the declaration modifiers and the name of
         * the variable declared.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The appended StringBuilder.
         */
        public StringBuilder generateDeclarationFragment(StringBuilder builder)
        {
            return generateModifiersSource(builder).append(' ').append(generateSourceName());
        }

        /**
         * Generate the modifiers for the specified Variable.<br>
         * <br>
         * For example:
         * <blockquote><pre>
         * Person people[] = new Person[42];</pre></blockquote>
         * In the above Variable declaration, the modifiers are the type of
         * the variable ("<u><code>Person</code></u>") and the type of
         * declaration is an array.<br>
         * node() also checks if the type requires a pointer.
         *
         * @param builder The StringBuilder to append to.
         * @return The appended StringBuilder.
         */
        public StringBuilder generateModifiersSource(StringBuilder builder)
        {
            if (node().isVolatile())//!(node() instanceof Parameter || node() instanceof FieldDeclaration))
            {
                builder.append(node().getVolatileText()).append(' ');
            }

            generateType(builder);

            return builder;
        }

        public StringBuilder generateDefaultValue(StringBuilder builder)
        {
            if (node().isPrimitive())
            {
                builder.append(0);
            }
            else
            {
                builder.append(generateTypeCast()).append(Value.NULL_IDENTIFIER);
            }

            return builder;
        }

        /**
         * Generate a String for the code used to free memory of the
         * specified variable.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The generated String for the code.
         */
        public StringBuilder generateFreeOutput(StringBuilder builder)
        {
            if (node().isConstant())
            {
                return builder;
            }

            if (node().isPrimitiveType() || node().isExternalType())
            {
                if (!node().isPrimitive())
                {
                    builder.append("NOVA_FREE(");

                    generateUseOutput(builder, true).append(");\n");
                }
            }
            else
            {
                Destructor destructor = node().getTypeClass().getDestructor();
                
                destructor.getTarget().generateSourceName(builder).append('(').append('&');

                generateUseOutput(builder, true).append(", ").append(Exception.EXCEPTION_DATA_IDENTIFIER).append(");\n");
            }

            return builder;
        }
    }
    
    public static abstract class TargetIIdentifier extends TargetIdentifier
    {
        
    }
    
    public static abstract class TargetIdentifier extends TargetValue implements TargetAccessible
    {
		public abstract Identifier node();

        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder).append(";\n");
        }
    
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            if (node().isGenericType() && node().doesAccess())
            {
                Value value = node().getReturnedNode();
                
                value.getTarget().generateTypeCast(builder);
            
                builder.append('(');
            }
        
            if (node().isSpecialFragment())
            {
                node().getTarget().generateSpecialFragment(builder);
            }
            else
            {
                generateUseOutput(builder).append(node().getTarget().generateChildrenSourceFragment());
            }
        
            if (node().isGenericType() && node().doesAccess())
            {
                builder.append(')');
            }
        
            return builder;
        }
    
        /**
         * Generate the representation of when the variable is being used, in
         * action, rather than being declared.<br>
         * <br>
         * For example:<br>
         * <blockquote><pre>
         * Person p;
         * p.getName();</pre></blockquote>
         * The first line shows the declaration of the Variable. The second
         * line demonstrates a "variable use" for the "p" variable.
         * Essentially, the "variable use" output is exactly what it says,
         * what the variable looks like when it is being used to do something.
         *
         * @return What the variable looks like when it is being used to do
         * 		something.
         */
        public StringBuilder generateUseOutput(StringBuilder builder)
        {
            return generateUseOutput(builder, false);
        }
    
        /**
         * Generate the representation of when the variable is being used, in
         * action, rather than being declared.<br>
         * <br>
         * For example:<br>
         * <blockquote><pre>
         * Person p;
         * p.getName();</pre></blockquote>
         * The first line shows the declaration of the Variable. The second
         * line demonstrates a "variable use" for the "p" variable.
         * Essentially, the "variable use" output is exactly what it says,
         * what the variable looks like when it is being used to do something.
         *
         * @param builder The StringBuilder to append the data to.
         * @param pointer Whether or not the variable is to be accessed by a
         * 		pointer.
         * @return What the variable looks like when it is being used to do
         * 		something.
         */
        public StringBuilder generateUseOutput(StringBuilder builder, boolean pointer)
        {
            return generateUseOutput(builder, pointer, true);
        }
    
        public StringBuilder generateUseOutput(StringBuilder builder, boolean pointer, boolean checkAccesses)
        {
            //		if (!isSpecialFragment())
            //		{
            //			builder.append(generateDataTypeOutput());
            //		}
        
            FieldDeclaration field = null;
        
            Node parent = node().getParent();
        
            if (parent instanceof Array)
            {
                VariableDeclaration n = SyntaxTree.findDeclaration(parent.getParent(), node().getName());
            
                if (n instanceof FieldDeclaration)
                {
                    field = (FieldDeclaration)n;
                }
            }
            else if (node() instanceof Variable)
            {
                VariableDeclaration decl = ((Variable)node()).getDeclaration();
            
                if (decl instanceof FieldDeclaration)
                {
                    field = (FieldDeclaration)decl;
                }
            }
            else if (node() instanceof FieldDeclaration)
            {
                field = (FieldDeclaration)node();
            }
        
            if (field != null && !field.isExternal())
            {
                if (!field.isStatic())
                {
                    Value ref = (Value)node().getReferenceNode();
                
                    if (ref.getTypeClass().isContainingClass(node()))
                    {
                        if (!node().isAccessed())
                        {
                            if (pointer)
                            {
                                builder.append('(').append('*');
                            }
                        
                            builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
                        
                            if (pointer)
                            {
                                builder.append(')');
                            }
                        }
                    
                        if (!node().isAccessed())//ref.isContainingClass(node()))
                        {
                            builder.append("->");
                        }
                        if (field.getVisibility() == FieldDeclaration.PRIVATE)
                        {
                            builder.append("prv").append("->");
                        }
                        //					else
                        //					{
                        //						builder.append("->");
                        //					}
                    }
                }
            }
        
            if (node().isValueReference())
            {
                builder.append("(*");
            
                generateSourcePrefix(builder);
            }
        
            generateSourceName(builder);
        
            if (node().isValueReference())
            {
                builder.append(')');
            }
        
            generateArrayAccess(builder);
        
            return builder;
        }
	
        /*public GenericTypeArgument getGenericTypeArgumentFromParameter(GenericTypeParameter param)
        {
            return getGenericTypeArgumentFromParameter(param.getType());
        }*/
    
        public String getCName()
        {
            return node().getName();
        }
    
        /**
         * Generate a variable name that will be used to keep the variables
         * in their own "namespace" per-say.
         *
         * @return The name of the variable that will be output to the C
         * 		source output.
         */
        public final StringBuilder generateSourceName()
        {
            return generateSourceName(new StringBuilder());
        }
    
        /**
         * Generate a variable name that will be used to keep the variables
         * in their own "namespace" per-say.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The name of the variable that will be output to the C
         * 		source output.
         */
        public final StringBuilder generateSourceName(StringBuilder builder)
        {
            return generateSourceName(builder, null);
        }
    
        /**
         * Generate a variable name that will be used to keep the variables
         * in their own "namespace" per-say.
         *
         * @param uniquePrefix The unique identifying prefix to prepend to the
         * 		name output.
         * @return The name of the variable that will be output to the C
         * 		source output.
         */
        public final StringBuilder generateSourceName(String uniquePrefix)
        {
            return generateSourceName(new StringBuilder(), uniquePrefix);
        }
    
        /**
         * Generate a variable name that will be used to keep the variables
         * in their own "namespace" per-say.
         *
         * @param builder The StringBuilder to append the data to.
         * @param uniquePrefix The unique identifying prefix to prepend to the
         * 		name output.
         * @return The name of the variable that will be output to the C
         * 		source output.
         */
        public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
        {
            String name = getCName();
        
            if (node().doesForceOriginalName())
            {
                return builder.append(name);
            }
        
            VariableDeclaration existing = null;
        
            if (node().isDeclaration())
            {
                existing = (VariableDeclaration)node();
            }
            else if (node() instanceof Variable)
            {
                existing = ((Variable)node()).getDeclaration();
            }
            else
            {
                existing = SyntaxTree.findDeclaration(node().getParent(), name, false);
            
                if (existing == null)
                {
                    SyntaxMessage.error("Unable to find declaration for variable '" + name + "'", node());
                }
            }
        
            if (!(existing instanceof LocalDeclaration && existing instanceof Parameter == false))
            {
                ClassDeclaration clazz = existing.getParentClass(true);
            
                clazz.getTarget().generateSourceName(builder).append('_');
            }
        
            //		if (existing instanceof InstanceDeclaration)
            //		{
            //			InstanceDeclaration node = (InstanceDeclaration)existing;
            //			
            //			if (node().isStatic())
            //			{
            //				if (!(node instanceof MethodDeclaration && ((MethodDeclaration)node).isInstance()))
            //				{
            //					builder.append("static_");
            //				}
            //			}
            //		}
        
            if (existing instanceof LocalDeclaration && existing instanceof Parameter == false)
            {
                LocalDeclaration declaration = (LocalDeclaration)existing;
            
                builder.append('l').append(declaration.getScopeID()).append('_');
            }
        
            if (uniquePrefix != null)
            {
                builder.append(uniquePrefix).append('_');
            }
        
            builder.append(Nova.LANGUAGE_NAME).append("_");
        
            return builder.append(name);
        }
    }
    
    public interface TargetAccessible
    {
		public abstract Accessible node();

        /**
         * If the Value accesses a method call, generate a specialized
         * output.
         *
         * @param builder The StringBuilder to append the data to.
         * @return A specialized String generation.
         */
        default StringBuilder generateSpecialFragment(StringBuilder builder)
        {
            Accessible current = node().getLastAccessedNode();
            
            while (!((Value)current).isSpecial())
            {
                current = current.getAccessingNode();
            }
            
            return ((Value)current).getTarget().generateSourceFragment(builder);
        }
    
        /**
         * Generate the C output for when node() value node is being used
         * as an argument for a method call.
         *
         * @param builder The StringBuilder to append the data to.
         * @param callingMethod The method that is being called by the
         * 		specified Identifier.
         * @return The C output for when node() value node is being used
         * 		as an argument for a method call.
         */
        default StringBuilder generateArgumentReference(StringBuilder builder, Identifier callingMethod)
        {
            Value n = (Value)node();
        
            if (n instanceof Identifier)
            {
                ((Identifier)n).getTarget().generateUseOutput(builder, false, true);
            }
            else
            {
                n.getTarget().generateUseOutput(builder);
            }
        
            generateChildrenSourceFragment(builder, true, callingMethod, false);
        
            return builder;
        }
    
        /**
         * Generate a String representing the accessed nodes.
         *
         * @return The generated String.
         */
        default StringBuilder generateChildrenSourceFragment()
        {
            return generateChildrenSourceFragment(new StringBuilder(), true);
        }
    
        /**
         * Generate a String representing the accessed nodes.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The StringBuilder with the appended generation output.
         */
        default StringBuilder generateChildrenSourceFragment(StringBuilder builder)
        {
            return generateChildrenSourceFragment(builder, true);
        }
    
        /**
         * Generate a String representing the accessed nodes.
         *
         * @param reference Whether or not to start the string off with
         * 		a "-&gt;" reference operator.
         * @return The generated String.
         */
        default StringBuilder generateChildrenSourceFragment(boolean reference)
        {
            return generateChildrenSourceFragment(new StringBuilder(), reference, null);
        }
    
        /**
         * Generate a String representing the accessed nodes.
         *
         * @param builder The StringBuilder to append the data to.
         * @param reference Whether or not to start the string off with
         * 		a "-&gt;" reference operator.
         * @return The StringBuilder with the appended generation output.
         */
        default StringBuilder generateChildrenSourceFragment(StringBuilder builder, boolean reference)
        {
            return generateChildrenSourceFragment(builder, reference, null);
        }
    
        /**
         * Generate a String representing the accessed nodes.
         *
         * @param reference Whether or not to start the string off with
         * 		a "-&gt;" reference operator.
         * @param stopBefore The Identifier to stop the generation before.
         * @return The generated String.
         */
        default StringBuilder generateChildrenSourceFragment(boolean reference, Identifier stopBefore)
        {
            return generateChildrenSourceFragment(new StringBuilder(), reference, stopBefore);
        }
    
        // TODO: use stopAt instead of stopBefore.
    
        /**
         * Generate a String representing the accessed nodes.
         *
         * @param builder The StringBuilder to append the data to.
         * @param reference Whether or not to start the string off with
         * 		a "-&gt;" reference operator.
         * @param stopBefore The Identifier to stop the generation before.
         * @return The StringBuilder with the appended generation output.
         */
        default StringBuilder generateChildrenSourceFragment(StringBuilder builder, boolean reference, Identifier stopBefore)
        {
            return generateChildrenSourceFragment(builder, reference, stopBefore, true);
        }
    
        default StringBuilder generateChildrenSourceFragment(StringBuilder builder, boolean reference, Identifier stopBefore, boolean checkAccesses)
        {
            Identifier child = node().getAccessedNode();
        
            if (child == null)
            {
                return builder;
            }
        
            StringBuilder output = child.getTarget().generateChildSourceFragment(reference, stopBefore, checkAccesses);
        
            if (output.length() > 0 && reference)
            {
                builder.append("->");
            }
        
            return builder.append(output);
        }
    
        /**
         * Generate the source fragment for the specified node().
         *
         * @param reference Whether or not to prepend the "->" operator at
         * 		the beginning of the generated output.
         * @param stopBefore The Identifier to stop the generation before.
         * @return The StringBuilder with the appended generation output.
         */
        default StringBuilder generateChildSourceFragment(boolean reference, Identifier stopBefore)
        {
            return generateChildSourceFragment(reference, stopBefore, true);
        }
    
        default StringBuilder generateChildSourceFragment(boolean reference, Identifier stopBefore, boolean checkAccesses)
        {
            Value n = (Value)node();
        
            StringBuilder builder = new StringBuilder();
        
            // If generating the output for the use of an argument.
            if (stopBefore != null)
            {
                if (node() == stopBefore)//instanceof MethodCall || node() instanceof Instantiation)
                {
                    return builder;
                }
            
                StringBuilder use = null;
            
                if (n instanceof Identifier)
                {
                    use = ((Identifier)n).getTarget().generateUseOutput(builder, false, checkAccesses);
                }
                else
                {
                    use = n.getTarget().generateUseOutput(builder);
                }
            
                return use.append(generateChildrenSourceFragment(true, stopBefore));
            }
        
            if (n instanceof Identifier)
            {
                Identifier id = (Identifier)n;
            
                if (id.isSpecialFragment())
                {
                    id.getTarget().generateSpecialFragment(builder);
                }
            }
        
            return n.getTarget().generateSourceFragment(builder);
        }
    
        /**
         * Generate the C Source for the Identifier and the Identifiers that
         * it accesses until the given 'stopAt' Identifier is reached.
         *
         * @param delimiter The String to append in between each Identifier
         * 		that is accessed.
         * @param stopAt The Identifier to stop the generation before.
         * @return The StrignBuilder with the appended data.
         */
        default StringBuilder generateSourceUntil(String delimiter, Identifier stopAt)
        {
            return generateSourceUntil(new StringBuilder(), delimiter, stopAt);
        }
    
        /**
         * Generate the C Source for the Identifier and the Identifiers that
         * it accesses until the given 'stopAt' Identifier is reached.
         *
         * @param builder The StringBuilder to append the data to.
         * @param delimiter The String to append in between each Identifier
         * 		that is accessed.
         * @param stopAt The Identifier to stop the generation before.
         * @return The StrignBuilder with the appended data.
         */
        default StringBuilder generateSourceUntil(StringBuilder builder, String delimiter, Identifier stopAt)
        {
            Accessible current = node();
        
            while (current != null && current != stopAt)
            {
                ((Value)current).getTarget().generateUseOutput(builder).append(delimiter);
            
                current = current.getAccessedNode();
            }
        
            return builder;
        }
    }
    
    public static abstract class TargetValue extends TargetNode
    {
        public abstract Value node();
        
        public StringBuilder generateHeader(StringBuilder builder)
        {
            return generateHeaderFragment(builder);
        }
        
        public StringBuilder generateHeaderFragment(StringBuilder builder)
        {
            return generateSourceFragment(builder);
        }
        
        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder);
        }
        
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            return generateType(builder);
        }

        public StringBuilder generateSourcePrefix(StringBuilder builder)
        {
            return builder;
        }

        /**
         * Generate the C null representation for the given value type.
         *
         * @return The generated null output.
         */
        public final StringBuilder generateNullOutput()
        {
            return generateNullOutput(new StringBuilder());
        }

        /**
         * Generate the C null representation for the given value type.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The generated null output.
         */
        public StringBuilder generateNullOutput(StringBuilder builder)
        {
            return generateTypeCast(builder).append(Value.NULL_IDENTIFIER);
        }

        public StringBuilder generateArgumentOutput(StringBuilder builder)
        {
            return generateSourceFragment(builder);
        }

        public final StringBuilder generateTypeClassName()
        {
            return generateTypeClassName(new StringBuilder());
        }

        public StringBuilder generateTypeClassName(StringBuilder builder)
        {
            String type = node().getType();

            if (node().isGenericType())
            {
                type = node().getGenericReturnType();
            }

            if (node().isExternalType() || SyntaxUtils.isExternalPrimitiveType(type))
            {
                builder.append(type);
            }
            else
            {
                FileDeclaration file = node().getReferenceFile();//getFileDeclaration();
			
                /*if (node() instanceof Identifier && !isGenericType())
                {
                    file = ((Identifier)node()).getDeclaringClass().getFileDeclaration();
                }*/

                ClassDeclaration clazz = SyntaxUtils.getImportedClass(file, type);

                if (clazz != null)
                {
                    clazz.getTarget().generateSourceName(builder);
                }
                else
                {
                    builder.append(type);
                }
            }

            return builder;
        }

        /**
         * Generate the C syntax for the type of the specified Value.
         *
         * @return The C syntax for the type of the Value.
         */
        public final StringBuilder generateType()
        {
            return generateType(new StringBuilder());
        }

        /**
         * Generate the C syntax for the type of the specified Value.
         *
         * @param builder The StringBuider to append the data to.
         * @return The C syntax for the type of the Value.
         */
        public final StringBuilder generateType(StringBuilder builder)
        {
            return generateType(builder, true);
        }

        /**
         * Generate the C syntax for the type of the specified Value.
         *
         * @param builder The StringBuider to append the data to.
         * @param checkArray Whether or not to check if the type is an array.
         * @return The C syntax for the type of the Value.
         */
        public final StringBuilder generateType(StringBuilder builder, boolean checkArray)
        {
            return generateType(builder, checkArray, true);
        }

        public StringBuilder generateType(StringBuilder builder, boolean checkArray, boolean checkValueReference)
        {
            generateTypeName(builder);

            if (node().isReference())
            {
                builder.append('&');
            }
            else if (node().isPointer())
            {
                builder.append('*');
            }
            else if (node().isDoublePointer())
            {
                builder.append("**");
            }
            if (checkValueReference && node().isValueReference())
            {
                builder.append('*');
            }
            if (checkArray && node().isPrimitiveArray())
            {
                builder.append(node().generateArrayText());
            }

            return builder;
        }

        public StringBuilder generateTypeName()
        {
            return generateTypeName(new StringBuilder());
        }

        public StringBuilder generateTypeName(StringBuilder builder)
        {
            String type = node().getType();

            if (node().isGenericType())
            {
                type = node().getGenericReturnType();
            }

            if (type == null)
            {
                builder.append("void");
            }
            else if (type.equals("long"))
            {
                builder.append("long_long");
            }
            else if (type.equals("bool"))
            {
                builder.append("char");
            }
            else if (type.equals("byte"))
            {
                builder.append("char");
            }
            else if (SyntaxUtils.isPrimitiveType(type) && (node().getDataType() == Value.VALUE || (node().isReturnParameter() && node().getDataType() == Value.POINTER)))
            {
                builder.append(SyntaxUtils.getPrimitiveExternalType(type));
            }
            else
            {
                generateTypeClassName(builder);
            }

            return builder;
        }

        /**
         * Generate a String representing a type cast for the specified Value
         * in C syntax.
         *
         * @return The StringBuilder with the appended data.
         */
        public final StringBuilder generateTypeCast()
        {
            return generateTypeCast(new StringBuilder());
        }

        /**
         * Generate a String representing a type cast for the specified Value
         * in C syntax.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The StringBuilder with the appended data.
         */
        public final StringBuilder generateTypeCast(StringBuilder builder)
        {
            return generateTypeCast(builder, true, true);
        }

        public final StringBuilder generateTypeCast(StringBuilder builder, boolean checkArray, boolean checkValueReference)
        {
            return builder.append('(').append(generateType(new StringBuilder(), checkArray, checkValueReference)).append(')').append(generatePointerToValueConversion());
        }

        public StringBuilder generatePointerToValueConversion()
        {
            return generatePointerToValueConversion(new StringBuilder());
        }

        public StringBuilder generatePointerToValueConversion(StringBuilder builder)
        {
            return generatePointerToValueConversion(builder, node());
        }

        public StringBuilder generatePointerToValueConversion(Value required)
        {
            return generatePointerToValueConversion(new StringBuilder(), required);
        }

        public StringBuilder generatePointerToValueConversion(StringBuilder builder, Value required)
        {
            boolean ptr = false;

            if (/*isGenericType() && */node() instanceof Accessible)
            {
                Accessible ref = ((Accessible)node()).getReferenceNode();

                ptr = ref != null && node().getArrayDimensions() == 0 && (required.isOriginallyGenericType() || node().isOriginallyGenericType()) && ref.toValue().isPrimitiveGenericTypeWrapper();
            }
            else
            {
                Node base = node().getBaseNode();

                if (base instanceof Value)
                {
                    ptr = ((Value)base).isPrimitiveGenericTypeWrapper();
                }
            }

            if (ptr)
            {
                builder.append("(intptr_t)");
            }

            return builder;
        }

        /**
         * Generate the representation of when the value node is being used
         * in action.
         *
         * @return What the method call looks like when it is being used in
         * 		action
         */
        public final StringBuilder generateUseOutput()
        {
            return generateUseOutput(new StringBuilder());
        }

        /**
         * Generate the representation of when the value node is being used
         * in action.
         *
         * @param builder The StringBuilder to append the data to.
         * @return What the method call looks like when it is being used in
         * 		action
         */
        public StringBuilder generateUseOutput(StringBuilder builder)
        {
            return generateType(builder);
        }

        public StringBuilder generateArrayAccess()
        {
            return generateArrayAccess(new StringBuilder());
        }

        public StringBuilder generateArrayAccess(StringBuilder builder)
        {
            if (node().arrayAccess != null)
            {
                return node().arrayAccess.getTarget().generateSourceFragment(builder);
            }

            return builder;
        }
    }
    
    public static abstract class TargetNode
    {
        public abstract Node node();
    
        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language header file syntax.
         *
         * @return The C header file syntax representation of the node().
         */
        public final StringBuilder generateHeader()
        {
            return generateHeader(new StringBuilder());
        }

        /**
         * Method that each Node can override. Returns a String that
         * translates the data that is stored in the Node to the C
         * programming language header file 'fragment' syntax.
         *
         * @return The C header syntax representation of the node().
         */
        public final StringBuilder generateHeaderFragment()
        {
            return generateHeaderFragment(new StringBuilder());
        }

        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language source file syntax.
         *
         * @return The C source syntax representation of the node().
         */
        public final StringBuilder generateSource()
        {
            return generateSource(new StringBuilder());
        }

        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language source file 'fragment' syntax.
         *
         * @return The C source syntax representation of the node().
         */
        public final StringBuilder generateSourceFragment()
        {
            return generateSourceFragment(new StringBuilder());
        }

        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language header file syntax.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The C header file syntax representation of the node().
         */
        public StringBuilder generateHeader(StringBuilder builder)
        {
            return generateHeaderFragment(builder).append('\n');
            //throw new UnimplementedOperationException("The C Header implementation for " + node().getClass().getName() + " has not been implemented yet.");
        }

        /**
         * Method that each Node can override. Returns a String that
         * translates the data that is stored in the Node to the C
         * programming language header file 'fragment' syntax.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The C header syntax representation of the node().
         */
        public StringBuilder generateHeaderFragment(StringBuilder builder)
        {
            throw new UnimplementedOperationException("The C Header fragment implementation for " + node().getClass().getName() + " has not been implemented yet.");
        }

        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language source file syntax.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The C source syntax representation of the node().
         */
        public StringBuilder generateSource(StringBuilder builder)
        {
            return generateSourceFragment(builder).append('\n');
            //throw new UnimplementedOperationException("The C Source implementation for " + node().getClass().getName() + " has not been implemented yet.");
        }

        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language source file 'fragment' syntax.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The C source syntax representation of the node().
         */
        public StringBuilder generateSourceFragment(StringBuilder builder)
        {
            throw new UnimplementedOperationException("The C Source fragment implementation for " + node().getClass().getName() + " has not been implemented yet.");
        }
    }
}