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
import net.fathomsoft.nova.tree.match.Case;
import net.fathomsoft.nova.tree.match.Default;
import net.fathomsoft.nova.tree.match.Fallthrough;
import net.fathomsoft.nova.tree.match.Match;
import net.fathomsoft.nova.tree.variables.*;
import net.fathomsoft.nova.util.Stack;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.time.Instant;
import java.util.ArrayList;

public class TargetC
{
    public static final TargetAbstractMethodDeclaration TARGET_ABSTRACT_METHOD_DECLARATION = new TargetAbstractMethodDeclaration();
    public static final TargetVirtualMethodDeclaration TARGET_VIRTUAL_METHOD_DECLARATION = new TargetVirtualMethodDeclaration();
    public static final TargetBodyMethodDeclaration TARGET_BODY_METHOD_DECLARATION = new TargetBodyMethodDeclaration();
    public static final TargetNovaMethodDeclaration TARGET_NOVA_METHOD_DECLARATION = new TargetNovaMethodDeclaration();
    public static final TargetMethodDeclaration TARGET_METHOD_DECLARATION = new TargetMethodDeclaration();
    public static final TargetInstanceDeclaration TARGET_INSTANCE_DECLARATION = new TargetInstanceDeclaration();
    public static final TargetVariableDeclaration TARGET_VARIABLE_DECLARATION = new TargetVariableDeclaration();
    public static final TargetIIdentifier TARGET_IIDENTIFIER = new TargetIIdentifier();
    public static final TargetIdentifier TARGET_IDENTIFIER = new TargetIdentifier();
    public static final TargetValue TARGET_VALUE = new TargetValue();
    public static final TargetAnnotation TARGET_ANNOTATION = new TargetAnnotation();
    public static final TargetCatch TARGET_CATCH = new TargetCatch();
    public static final TargetExceptionHandler TARGET_EXCEPTION_HANDLER = new TargetExceptionHandler();
    public static final TargetFinally TARGET_FINALLY = new TargetFinally();
    public static final TargetThrow TARGET_THROW = new TargetThrow();
    public static final TargetTry TARGET_TRY = new TargetTry();
    public static final TargetLambdaMethodDeclaration TARGET_LAMBDA_METHOD_DECLARATION = new TargetLambdaMethodDeclaration();
    public static final TargetCase TARGET_CASE = new TargetCase();
    public static final TargetDefault TARGET_DEFAULT = new TargetDefault();
    public static final TargetFallthrough TARGET_FALLTHROUGH = new TargetFallthrough();
    public static final TargetMatch TARGET_MATCH = new TargetMatch();
    public static final TargetArgumentList TARGET_ARGUMENT_LIST = new TargetArgumentList();
    public static final TargetAssignment TARGET_ASSIGNMENT = new TargetAssignment();
    public static final TargetAssignmentMethod TARGET_ASSIGNMENT_METHOD = new TargetAssignmentMethod();
    public static final TargetBinaryOperation TARGET_BINARY_OPERATION = new TargetBinaryOperation();
    public static final TargetBreak TARGET_BREAK = new TargetBreak();
    public static final TargetCast TARGET_CAST = new TargetCast();
    public static final TargetClassDeclaration TARGET_CLASS_DECLARATION = new TargetClassDeclaration();
    public static final TargetClosure TARGET_CLOSURE = new TargetClosure();
    public static final TargetClosureContext TARGET_CLOSURE_CONTEXT = new TargetClosureContext();
    public static final TargetClosureContextDeclaration TARGET_CLOSURE_CONTEXT_DECLARATION = new TargetClosureContextDeclaration();
    public static final TargetClosureDeclaration TARGET_CLOSURE_DECLARATION = new TargetClosureDeclaration();
    public static final TargetClosureParameterList TARGET_CLOSURE_PARAMETER_LIST = new TargetClosureParameterList();
    public static final TargetConstructor TARGET_CONSTRUCTOR = new TargetConstructor();
    public static final TargetContinue TARGET_CONTINUE = new TargetContinue();
    public static final TargetDefaultParameterInitialization TARGET_DEFAULT_PARAMETER_INITIALIZATION = new TargetDefaultParameterInitialization();
    public static final TargetDestructor TARGET_DESTRUCTOR = new TargetDestructor();
    public static final TargetDimensions TARGET_DIMENSIONS = new TargetDimensions();
    public static final TargetElseStatement TARGET_ELSE_STATEMENT = new TargetElseStatement();
    public static final TargetExtensionVTable TARGET_EXTENSION_VTABLE = new TargetExtensionVTable();
    public static final TargetExternalMethodDeclaration TARGET_EXTERNAL_METHOD_DECLARATION = new TargetExternalMethodDeclaration();
    public static final TargetFileDeclaration TARGET_FILE_DECLARATION = new TargetFileDeclaration();
    public static final TargetForEachLoop TARGET_FOR_EACH_LOOP = new TargetForEachLoop();
    public static final TargetForLoop TARGET_FOR_LOOP = new TargetForLoop();
    public static final TargetIfStatement TARGET_IF_STATEMENT = new TargetIfStatement();
    public static final TargetImport TARGET_IMPORT = new TargetImport();
    public static final TargetImportList TARGET_IMPORT_LIST = new TargetImportList();
    public static final TargetInstantiation TARGET_INSTANTIATION = new TargetInstantiation();
    public static final TargetInterface TARGET_INTERFACE = new TargetInterface();
    public static final TargetInterfaceVTable TARGET_INTERFACE_VTABLE = new TargetInterfaceVTable();
    public static final TargetIValue TARGET_IVALUE = new TargetIValue();
    public static final TargetLiteral TARGET_LITERAL = new TargetLiteral();
    public static final TargetLocalDeclaration TARGET_LOCAL_DECLARATION = new TargetLocalDeclaration();
    public static final TargetMethodCall TARGET_METHOD_CALL = new TargetMethodCall();
    public static final TargetMethodCallArgumentList TARGET_METHOD_CALL_ARGUMENT_LIST = new TargetMethodCallArgumentList();
    public static final TargetMethodList TARGET_METHOD_LIST = new TargetMethodList();
    public static final TargetNovaParameterList TARGET_NOVA_PARAMETER_LIST = new TargetNovaParameterList();
    public static final TargetOperator TARGET_OPERATOR = new TargetOperator();
    public static final TargetPackage TARGET_PACKAGE = new TargetPackage();
    public static final TargetParameter TARGET_PARAMETER = new TargetParameter();
    public static final TargetParameterList TARGET_PARAMETER_LIST = new TargetParameterList();
    public static final TargetPriority TARGET_PRIORITY = new TargetPriority();
    public static final TargetProgram TARGET_PROGRAM = new TargetProgram();
    public static final TargetPropertyMethod TARGET_PROPERTY_METHOD = new TargetPropertyMethod();
    public static final TargetRepeat TARGET_REPEAT = new TargetRepeat();
    public static final TargetReturn TARGET_RETURN = new TargetReturn();
    public static final TargetScope TARGET_SCOPE = new TargetScope();
    public static final TargetSkeleton TARGET_SKELETON = new TargetSkeleton();
    public static final TargetStaticBlock TARGET_STATIC_BLOCK = new TargetStaticBlock();
    public static final TargetStaticClassReference TARGET_STATIC_CLASS_REFERENCE = new TargetStaticClassReference();
    public static final TargetTernaryOperation TARGET_TERNARY_OPERATION = new TargetTernaryOperation();
    public static final TargetTypeList TARGET_TYPE_LIST = new TargetTypeList();
    public static final TargetUnaryOperation TARGET_UNARY_OPERATION = new TargetUnaryOperation();
    public static final TargetUntil TARGET_UNTIL = new TargetUntil();
    public static final TargetVTable TARGET_VTABLE = new TargetVTable();
    public static final TargetWhileLoop TARGET_WHILE_LOOP = new TargetWhileLoop();
    public static final TargetFieldList TARGET_FIELD_LIST = new TargetFieldList();
    public static final TargetInstanceFieldList TARGET_INSTANCE_FIELD_LIST = new TargetInstanceFieldList();
    public static final TargetStaticFieldList TARGET_STATIC_FIELD_LIST = new TargetStaticFieldList();
    public static final TargetVariable TARGET_VARIABLE = new TargetVariable();
    public static final TargetFieldDeclaration TARGET_FIELD_DECLARATION = new TargetFieldDeclaration();
    public static final TargetVariableDeclarationList TARGET_VARIABLE_DECLARATION_LIST = new TargetVariableDeclarationList();
    public static final TargetArray TARGET_ARRAY = new TargetArray();
    public static final TargetArrayAccess TARGET_ARRAY_ACCESS = new TargetArrayAccess();
    public static final TargetNode TARGET_NODE = new TargetNode();
    
    public static class TargetSkeleton extends TargetNode
    {
        
    }
    
    public static class TargetStaticFieldList extends TargetList
    {
        public StringBuilder generateHeader(StaticFieldList node, StringBuilder builder)
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
                
                child.getTarget().generateHeader(child, builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateSource(StaticFieldList node, StringBuilder builder)
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
                
                child.getTarget().generateSource(child, builder);
            }
        
            return builder;
        }
    }
    
    public static class TargetInstanceFieldList extends TargetList
    {
        public StringBuilder generateHeader(InstanceFieldList node, StringBuilder builder)
        {
            ClassDeclaration extended = node.getParentClass().getExtendedClassDeclaration();
        
            if (extended != null)
            {
                boolean publicList = node == node.getParentClass().getFieldList().getPublicFieldList();
            
                InstanceFieldList fields = null;
            
                if (publicList)
                {
                    fields = extended.getFieldList().getPublicFieldList();
                }
                else
                {
                    fields = extended.getFieldList().getPrivateFieldList();
                }
            
                fields.getTarget().generateHeader(fields, builder);
            }
        
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
                
                child.getTarget().generateHeader(child, builder);
            }
        
            return builder;
        }
        
        public StringBuilder generateSource(InstanceFieldList node, StringBuilder builder)
        {
            boolean hasMethods = false;
        
            if (node.getNumChildren() > 0)
            {
                ClassDeclaration parent = node.getParentClass();
            
                if (parent.getMethodList().getNumChildren() > 0)
                {
                    hasMethods = true;
                }
            }
        
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
                
                child.getTarget().generateSource(child, builder);
            }
        
            if (hasMethods)
            {
                builder.append('\n');
            }
        
            return builder;
        }
    }
    
    public static class TargetFieldList extends TargetList
    {
        /**
         * Generate the C Header output for the all of the non-static
         * variables.
         *
         * @return The C Header file output.
         */
        public StringBuilder generateNonStaticHeader(FieldList node, StringBuilder builder)
        {
            return node.getPublicFieldList().getTarget().generateHeader(node.getPublicFieldList(), builder);
        }
    
        /**
         * Generate the C Header output for the all of the public static
         * variables.
         *
         * @return The C Header file output.
         */
        public StringBuilder generateStaticHeader(FieldList node, StringBuilder builder)
        {
            return node.getPublicStaticFieldList().getTarget().generateHeader(node.getPublicStaticFieldList(), builder);
        }
    
        /**
         * Generate the C Source output for the all of the public static
         * variables.
         *
         * @return The C Source file output.
         */
        public StringBuilder generateStaticSource(FieldList node, StringBuilder builder)
        {
            return node.getPublicStaticFieldList().getTarget().generateSource(node.getPublicStaticFieldList(), builder);
        }
    
        /**
         * Generate the C Source output for the all of the non-static
         * variables.
         *
         * @return The C Source file output.
         */
        public StringBuilder generateNonStaticSource(FieldList node, StringBuilder builder)
        {
            return node.getPrivateStaticFieldList().getTarget().generateHeader(node.getPrivateStaticFieldList(), builder);
        }
    }
    
    public static class TargetWhileLoop extends TargetLoop
    {
        public StringBuilder generateSource(WhileLoop node, StringBuilder builder)
        {
            Node condition = node.getCondition();
        
            builder.append("while (").append(condition.getTarget().generateSourceFragment(condition)).append(')').append('\n');
        
            Scope scope = node.getScope();
            
            scope.getTarget().generateSource(scope, builder);
        
            return builder;
        }
    }
    
    public static class TargetUntil extends TargetIfStatement
    {
        public StringBuilder generateSource(Until node, StringBuilder builder)
        {
            super.generateSourceFragment(node, builder).append('\n');
        
            Scope scope = node.getScope();
            
            return scope.getTarget().generateSource(scope, builder);
        }
    }
    
    public static class TargetUnaryOperation extends TargetIValue
    {
        public StringBuilder generateSource(UnaryOperation node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder).append(";\n");
        }
    
        public StringBuilder generateSourceFragment(UnaryOperation node, StringBuilder builder)
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
                
                child.getTarget().generateSourceFragment(child, builder);
            }
        
            return builder;
        }
    }
    
    public static class TargetArrayAccess extends TargetNode
    {
        public StringBuilder generateSourceFragment(ArrayAccess node, StringBuilder builder)
        {
            Dimensions dimensions = node.getDimensions();
        
            dimensions.getTarget().generateSourceFragment(dimensions, builder);
        
            return builder;
        }
    }
    
    public static class TargetArray extends TargetVariableDeclaration
    {
        public StringBuilder generateSource(Array node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }
    
        public StringBuilder generateSourceFragment(Array node, StringBuilder builder)
        {
            generateTypeCast(node, builder);
    //		builder.insert(builder.length() - 1, '*');
        
            if (node.getNumDimensions() > 1)
            {
                builder.append("nova_gen_array(");
            }
        
            builder.append("NOVA_MALLOC(sizeof(").append(generateTypeClassName(node)).append(")");
        
            Dimensions dim = node.getDimensions();

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
            dim.getTarget().generateSourceFragment(dim, builder, " * ", "");

    //		builder.append(')');
        
            if (node.getNumDimensions() > 1)
            {
                builder.append("), (int[]) { ");
            
                for (int i = 0; i < dim.getNumVisibleChildren(); i++)
                {
                    if (i > 0)
                    {
                        builder.append(", ");
                    }
                
                    Node child = dim.getVisibleChild(i);
                    
                    child.getTarget().generateSourceFragment(child, builder);
                }
            
                builder.append(" }, 0, ").append(dim.getNumVisibleChildren() - 1).append(", ");
            
                builder.append("sizeof(").append(generateTypeClassName(node)).append(')');
            }
        
            return builder.append(')');
        }
    }
    
    public static class TargetTernaryOperation extends TargetIValue implements TargetAccessible
    {
        public StringBuilder generateSourceFragment(TernaryOperation node, StringBuilder builder)
        {
            Value t = node.getTrueValue();
            Value f = node.getFalseValue();
            
            String trueValue = t.getTarget().generateSourceFragment(t).toString();
            String falseValue = f.getTarget().generateSourceFragment(f).toString();
        
            ClassDeclaration trueType = t.getReturnedNode().getTypeClass();
            ClassDeclaration falseType = f.getReturnedNode().getTypeClass();
        
            if (trueType != falseType)
            {
                ClassDeclaration commonClass = trueType.getCommonAncestor(f.getReturnedNode().getTypeClass());
            
                if (trueType != commonClass)
                {
                    Value r = f.getReturnedNode();
                    
                    trueValue = r.getTarget().generateTypeCast(r) + trueValue;
                }
                else
                {
                    Value r = t.getReturnedNode();
                    
                    falseValue = r.getTarget().generateTypeCast(r) + falseValue;
                }
            }
    
            Value condition = node.getCondition();
            
            return condition.getTarget().generateSourceFragment(condition, builder).append(" ? ").append(trueValue).append(" : ").append(falseValue);
        }
    }
    
    public static class TargetStaticClassReference extends TargetIIdentifier
    {
        public StringBuilder generateUseOutput(StaticClassReference node, StringBuilder builder, boolean pointer, boolean checkAccesses)
        {
            return builder.append(0);
        }
    
        public StringBuilder generateSourceFragment(StaticClassReference node, StringBuilder builder)
        {
            if (!node.doesAccess())
            {
                return generateUseOutput(node, builder);
            }
        
            if (node.isSpecialFragment())
            {
                return generateSpecialFragment(node, builder);
            }
            
            Identifier accessed = node.getAccessedNode();
            
            return accessed.getTarget().generateSourceFragment(accessed, builder);
        }
    
        public StringBuilder generateArgumentReference(StaticClassReference node, StringBuilder builder, Identifier callingMethod)
        {
            return builder.append(0);//getAccessedNode().generateArgumentReference(builder, callingMethod);
        }
    }
    
    public static class TargetStaticBlock extends TargetNode
    {
        public StringBuilder generateHeader(StaticBlock node, StringBuilder builder, ClassDeclaration clazz)
        {
            return generateMethodHeader(node, builder, clazz).append(';').append('\n');
        }
    
        public StringBuilder generateSource(StaticBlock node, StringBuilder builder)
        {
            Scope scope = node.getScope();
            
            return scope.getTarget().generateSource(scope, builder);
        }
    
        public StringBuilder generateMethodHeader(StaticBlock node, StringBuilder builder, ClassDeclaration clazz)
        {
            builder.append("void ");
        
            generateMethodName(builder, clazz);
            
            ParameterList params = node.getParameterList();
            
            builder.append('(').append(params.getTarget().generateSource(params)).append(')');
        
            return builder;
        }
    
        public static StringBuilder generateMethodName(StringBuilder builder, ClassDeclaration clazz)
        {
            return builder.append(clazz.getTarget().generateSourceName(clazz)).append(StaticBlock.C_PREFIX).append(StaticBlock.IDENTIFIER);
        }
    
        public static StringBuilder generateMethodCall(StringBuilder builder, ClassDeclaration clazz)
        {
            return generateMethodName(builder, clazz).append("(" + Exception.EXCEPTION_DATA_IDENTIFIER + ")");
        }
    }
    
    public static class TargetScope extends TargetNode
    {
        public StringBuilder generateSource(Scope node, StringBuilder builder)
        {
            return generateSource(node, builder, true);
        }
        
        public StringBuilder generateSource(Scope node, StringBuilder builder, boolean braces)
        {
            if (node.getNumChildren() <= 1)
            {
                if (node.getParent() instanceof Loop)
                {
                    // Insert the semicolon before the new line.
                    return builder.insert(builder.length() - 1, ";");
                }
            }
        
            if (braces)
            {
                builder.append('{').append('\n');
            }
        
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
                
                child.getTarget().generateSource(child, builder);
            }
        
            if (braces)
            {
                builder.append('}').append('\n');
            }
        
            return builder;
        }
    }
    
    public static class TargetReturn extends TargetIValue
    {
        public StringBuilder generateSource(Return node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder).append(";\n");
        }
    
        public StringBuilder generateSourceFragment(Return node, StringBuilder builder)
        {
            builder.append("return");
            
            Value value = node.getValueNode();
            
            if (value != null)
            {
                builder.append(' ');
            
                if (value.getReturnedNode().isGenericType(true) || !SyntaxUtils.isSameType(node.getParentMethod(), value.getReturnedNode(), false))
                {
                    NovaMethodDeclaration method = node.getParentMethod();
                    Value r = value.getReturnedNode();
                    
                    method.getTarget().generateTypeCast(method, builder).append(r.getTarget().generatePointerToValueConversion(r));
                }
            
                value.getTarget().generateSourceFragment(value, builder);
            }
        
            return builder;
        }
    }
    
    public static class TargetRepeat extends TargetLoop
    {
        public StringBuilder generateSource(Repeat node, StringBuilder builder)
        {
            Value value = node.getValueNode();
            
            if (value != null)
            {
                builder.append("for (").append(node.getName()).append(" = 0; ").append(node.getName()).append(" < ").append(value.getTarget().generateSourceFragment(value)).append("; ").append(node.getName()).append("++)\n");
            }
            else
            {
                builder.append("for (;;)\n");
            }
            
            Scope scope = node.getScope();
            
            scope.getTarget().generateSource(scope, builder);
        
            return builder;
        }
    }
    
    public static class TargetPropertyMethod extends TargetBodyMethodDeclaration
    {
        public StringBuilder generateSourceName(PropertyMethod node, StringBuilder builder, String uniquePrefix)
        {
            return super.generateSourceName(node, builder, node.getMethodPrefix());
        }
    
        public StringBuilder generateHeader(PropertyMethod node, StringBuilder builder)
        {
            if (node.isDisabled())
            {
                return builder;
            }
        
            return super.generateHeader(node, builder);
        }
    
        public StringBuilder generateSource(PropertyMethod node, StringBuilder builder)
        {
            if (node.isDisabled())
            {
                return builder;
            }
        
            return super.generateSource(node, builder);
        }
    
        public StringBuilder generateSourcePrototype(PropertyMethod node, StringBuilder builder)
        {
            if (node.isDisabled())
            {
                return builder;
            }
        
            return super.generateSourcePrototype(node, builder);
        }
    }
    
    public static class TargetProgram extends TargetNode
    {
        public StringBuilder generateHeader(Program node, StringBuilder builder)
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
                
                child.getTarget().generateHeader(child);
            }
        
            return builder;
        }
    
        public StringBuilder generateSource(Program node, StringBuilder builder)
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
                
                child.getTarget().generateSource(child);
            }
        
            return builder;
        }
    }
    
    public static class TargetPriority extends TargetValue implements TargetAccessible
    {
        public StringBuilder generateSource(Priority node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }
    
        public StringBuilder generateSourceFragment(Priority node, StringBuilder builder)
        {
            if (node.isSpecialFragment())
            {
                return generateSpecialFragment(node, builder);
            }
            else
            {
                Value contents = node.getContents();
                
                return builder.append('(').append(contents.getTarget().generateSourceFragment(contents)).append(')').append(generateArrayAccess(node)).append(generateChildrenSourceFragment(node));
            }
        }
    
        public StringBuilder generateUseOutput(Priority node, StringBuilder builder)
        {
            Value contents = node.getContents();
            
            return builder.append('(').append(contents.getTarget().generateSourceFragment(contents)).append(')').append(generateArrayAccess(node));
        }
    }
    
    public static class TargetPackage extends TargetNode
    {
        public StringBuilder generateHeader(Package node, StringBuilder builder)
        {
            return builder;
        }
    
        public StringBuilder generateSource(Package node, StringBuilder builder)
        {
            return builder;
        }
    
        public StringBuilder generateHeaderLocation(Package node)
        {
            return generateHeaderLocation(node, new StringBuilder());
        }
    
        public StringBuilder generateHeaderLocation(Package node, StringBuilder builder)
        {
            return builder.append(node.getLocation());
        }
    
        public StringBuilder generateLocation(Package node)
        {
            return generateLocation(node, new StringBuilder());
        }
    
        public StringBuilder generateLocation(Package node, StringBuilder builder)
        {
            if (!node.validLocation())
            {
                return builder;
            }
        
            String output = node.location.replace('/', '_');
        
            return builder.append(output);
        }
    }
    
    public static class TargetOperator extends TargetIValue
    {
        public StringBuilder generateSource(Operator node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }
    
        public StringBuilder generateSourceFragment(Operator node, StringBuilder builder)
        {
            if (node.operator.equals(Operator.AND))
            {
                return builder.append(Operator.AND_C);
            }
            else if (node.operator.equals(Operator.OR))
            {
                return builder.append(Operator.OR_C);
            }
        
            return builder.append(node.operator);
        }
    }
    
    public static class TargetNovaParameterList extends TargetParameterList
    {
        public StringBuilder generateSourceParameters(NovaParameterList node, StringBuilder builder)
        {
            super.generateSourceParameters(node, builder);
        
            if (node.returnParameters.getNumVisibleChildren() > 0)
            {
                builder.append(", ");
            
                node.returnParameters.getTarget().generateSourceParameters(node.returnParameters, builder);
            }
        
            if (node.getMethodDeclaration() instanceof LambdaMethodDeclaration)
            {
                builder.append(", ").append(((LambdaMethodDeclaration)node.getMethodDeclaration()).context.getName()).append("* ").append(ClosureVariableDeclaration.CONTEXT_VARIABLE_NAME);
            }
        
            return builder;
        }
    
        public StringBuilder generateHeaderParameters(NovaParameterList node, StringBuilder builder)
        {
            super.generateHeaderParameters(node, builder);
        
            if (node.returnParameters.getNumVisibleChildren() > 0)
            {
                builder.append(", ");
    
                node.returnParameters.getTarget().generateHeaderParameters(node.returnParameters, builder);
            }
        
            if (node.getMethodDeclaration() instanceof LambdaMethodDeclaration)
            {
                builder.append(", ").append(((LambdaMethodDeclaration)node.getMethodDeclaration()).context.getName()).append('*');
            }
        
            return builder;
        }
    }
    
    public static class TargetMethodList extends TargetTypeList
    {
        public StringBuilder generateHeader(MethodList node, StringBuilder builder)
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                MethodDeclaration methodDeclaration = (MethodDeclaration)node.getChild(i);
            
                if (!methodDeclaration.isExternal())
                {
                    methodDeclaration.getTarget().generateHeader(methodDeclaration, builder);
                }
            }
        
            return builder;
        }
    
        public StringBuilder generateSource(MethodList node, StringBuilder builder)
        {
            if (node.getNumChildren() > 0)
            {
                builder.append('\n');
            }
        
            boolean printed = false;
        
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                MethodDeclaration methodDeclaration = (MethodDeclaration)node.getChild(i);
            
                if (!methodDeclaration.isExternal())
                {
                    if (printed)
                    {
                        builder.append('\n');
                    }
                
                    methodDeclaration.getTarget().generateSource(methodDeclaration, builder);
                
                    printed = true;
                }
            }
        
            return builder;
        }
    
        /**
         * Generate a String containing all of the prototypes for each method
         * contained within this node. A method prototype follows the
         * following syntax: "static returnType methodName(arguments);"
         *
         * @return A String containing all of the prototypes for the methods
         * 		contained within this node.
         */
        public StringBuilder generateSourcePrototypes(MethodList node, StringBuilder builder)
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                MethodDeclaration child = node.getChild(i);
            
                child.getTarget().generateSourcePrototype(child, builder).append('\n');
            }
        
            return builder;
        }
    }
    
    public static class TargetMethodCallArgumentList extends TargetArgumentList
    {
        public StringBuilder generateSource(MethodCallArgumentList node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }
    
        public StringBuilder generateSourceFragment(MethodCallArgumentList node, StringBuilder builder)
        {
            MethodCall call = node.getMethodCall();
        
            CallableMethod method = call.getInferredDeclaration();
        
            builder.append('(');
        
            generateDefaultArguments(node, builder);
        
            int i = 0;
        
            Value[] values = method instanceof NovaMethodDeclaration ? node.getArgumentsInOrder((NovaMethodDeclaration)method) : node.getArgumentsInOrder();
        
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
                        
                        param.getTarget().generateTypeCast(param, builder).append(ret.getTarget().generatePointerToValueConversion(ret, param));
                    }
                
                    generateArgumentPrefix(node, builder, arg, i);
                
                    if (!sameType)
                    {
                        builder.append('(');
                    }
                
                    if (param.isValueReference())
                    {
                        builder.append('&');
                    }
                
                    arg.getTarget().generateArgumentOutput(arg, builder);
                
                    if (!sameType)
                    {
                        builder.append(')');
                    }
                }
            
                i++;
            }
        
            ParameterList params = node.getMethodDeclaration().getParameterList();
        
            while (i < params.getNumVisibleChildren())
            {
                builder.append(", ");
            
                DefaultArgument.generateDefaultArgumentOutput(builder, params.getVisibleChild(i));
            
                i++;
            }
        
            if (node.getMethodCall().getCallableDeclaration() instanceof ClosureDeclaration)
            {
                builder.append(", ").append(((ClosureDeclaration)node.getMethodCall().getCallableDeclaration()).getContextName());
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
        private StringBuilder generateDefaultArguments(MethodCallArgumentList node, StringBuilder builder)
        {
            if (!node.getMethodCall().isExternal())
            {
                checkReference(node, builder).append(Exception.EXCEPTION_DATA_IDENTIFIER);
            
                if (node.getNumChildren() > 0)
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
        private StringBuilder generateArgumentPrefix(MethodCallArgumentList node, StringBuilder builder, Value child, int argNum)
        {
            Value parameter = node.getMethodCall().getInferredDeclaration().getParameterList().getParameter(argNum);
        
            if (child instanceof Variable)
            {
                Variable var = (Variable)child;
            
                if (var.isVolatile())
                {
                    parameter.getTarget().generateTypeCast(parameter, builder);
                }
            }
        
            if (parameter.getDataType() != child.getReturnedNode().getDataType())
            {
                if (!node.getMethodCall().getReferenceNode().toValue().isPrimitiveGenericTypeWrapper())//parameter.getArrayDimensions() == 0 || parameter.isWithinExternalContext() || parameter.getArrayDimensions() != child.getReturnedNode().getArrayDimensions())
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
        private StringBuilder checkReference(MethodCallArgumentList node, StringBuilder builder)
        {
            CallableMethod method = node.getMethodCall().getInferredDeclaration();
        
            if (method instanceof Constructor || !node.getMethodCall().getDeclaration().isInstance())
            {
                builder.append(0);
            }
            else if (method instanceof ClosureDeclaration)
            {
                ClosureDeclaration closure = (ClosureDeclaration)method;
            
                closure.getTarget().generateObjectReferenceIdentifier(closure, builder);
            }
            else
            {
                if (method instanceof Destructor)
                {
                    builder.append('&');
                }
            
                Accessible context  = node.getMethodCallContext();
                MethodCall call     = node.getMethodCall();
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
                    castClass.getTarget().generateTypeCast(castClass, builder, true, false).append('(');
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
                
                ref.getTarget().generateArgumentReference(ref, builder, call);
            
                if (castClass != null)
                {
                    builder.append(')');
                }
            }
        
            builder.append(", ");
        
            return builder;
        }
    }
    
    public static class TargetMethodCall extends TargetVariable
    {
        public StringBuilder generateSource(MethodCall node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder).append(';').append('\n');
        }
        
        public StringBuilder generatedCSourceFragment(MethodCall node, StringBuilder builder, boolean checkSpecial)
        {
            if (checkSpecial && node.isSpecialFragment())
            {
                return generateSpecialFragment(node, builder);
            }
        
            return generateUseOutput(node, builder);
        }
    
        /**
         * Generate a String representing the output of the children of the
         * MethodCall.
         *
         * @return A String representing the output of the children of the
         * 		MethodCall.
         */
        public StringBuilder generatehildrenCSourceFragment(MethodCall node, StringBuilder builder)
        {
            for (int i = 1; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
            
                builder.append("->");
            
                child.getTarget().generateSourceFragment(child, builder);
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
        public StringBuilder generateUseOutput(MethodCall node, StringBuilder builder, boolean pointer, boolean checkAccesses)
        {
            VariableDeclaration method   = node.getMethodDeclaration();
            CallableMethod      callable = (CallableMethod)method;
        
            boolean requiresCast = checkAccesses && node.doesAccess() && node.getAccessedNode() instanceof MethodCall == false && node.isGenericType();
        
            if (requiresCast)
            {
                builder.append('(');
                generateTypeCast(node, builder);
            }
        
            if (callable.isVirtual() && ((NovaMethodDeclaration)method).getVirtualMethod() != null && !node.isVirtualTypeKnown())
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
			
                virtual.getTarget().generateSourceName(virtual, builder);
            }
            else
            {
                method.getTarget().generateSourceName(method, builder);
            }
            
            MethodCallArgumentList args = node.getArgumentList();
        
            args.getTarget().generateSource(args, builder);
        
            if (requiresCast)
            {
                builder.append(')');
            }
        
            return builder;
        }
    
        public StringBuilder generateExtraArguments(MethodCall node, StringBuilder builder)
        {
            return builder;
        }
    
        public StringBuilder generateObjectReferenceIdentifier(MethodCall node, StringBuilder builder)
        {
            return builder;
        }
    }
    
    public static class TargetLiteral extends TargetIValue implements TargetAccessible
    {
        public StringBuilder generateHeader(Literal node, StringBuilder builder)
        {
            return generateSource(node, builder);
        }
    
        public StringBuilder generateSource(Literal node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder).append(";\n");
        }
    
        public StringBuilder generateSourceFragment(Literal node, StringBuilder builder)
        {
            if (node.isSpecialFragment())
            {
                return generateSpecialFragment(node, builder);
            }
        
            return generateUseOutput(node, builder);
        }
    
        public StringBuilder generateUseOutput(Literal node, StringBuilder builder)
        {
            if (!node.isWithinExternalContext() && node.isStringInstantiation())
            {
                Instantiation str = Instantiation.decodeStatement(node.getParent(), "new String(" + node.value + ")", node.getLocationIn(), true);
            
                return str.getTarget().generateSourceFragment(str, builder);
            }
            else if (node.isNullLiteral(node))
            {
                return generateNullOutput(node, builder);
            }
            else if (node.value.equals(Literal.TRUE_IDENTIFIER))
            {
                return builder.append(1);
            }
            else if (node.value.equals(Literal.FALSE_IDENTIFIER))
            {
                return builder.append(0);
            }
            else if (SyntaxUtils.isInteger(node.value))
            {
                long l = Long.parseLong(node.value);
            
                if (l == Long.MIN_VALUE)
                {
                    return builder.append("(").append(l + 1).append("LL").append(" - 1)");
                }
                else if (l > Integer.MAX_VALUE || l <= Integer.MIN_VALUE)
                {
                    return builder.append(node.value).append("LL");
                }
            }
        
            return builder.append(node.value);
        }
    }
    
    public static class TargetInterface extends TargetClassDeclaration
    {
        public StringBuilder generateSource(Interface node, StringBuilder builder)
        {
            return super.generateSource(node, builder);
        }
    }
    
    public static class TargetInstantiation extends TargetIIdentifier
    {
        public StringBuilder generateSource(Instantiation node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder).append(";\n");
        }
    
        public StringBuilder generateSourceFragment(Instantiation node, StringBuilder builder)
        {
            Identifier id = node.getIdentifier();
            
            return id.getTarget().generateSourceFragment(id, builder);
        }
    
        public StringBuilder generateUseOutput(Instantiation node, StringBuilder builder, boolean pointer, boolean checkAccesses)
        {
            Identifier id = node.getIdentifier();
    
            return id.getTarget().generateUseOutput(id, builder, pointer, checkAccesses);
        }
    }
    
    public static class TargetImportList extends TargetList
    {
        public StringBuilder generateHeader(ImportList node, StringBuilder builder)
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
                
                child.getTarget().generateSource(child, builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateSource(ImportList node, StringBuilder builder)
        {
            FileDeclaration file = node.getFileDeclaration();
        
            Import importNode = Import.decodeStatement(node, "import \"" + file.getClassDeclaration().getClassLocation() + "\"", node.getLocationIn(), true);
        
            return importNode.getTarget().generateSource(importNode, builder);
        }
    }
    
    public static class TargetImport extends TargetNode
    {
        public StringBuilder generateHeader(Import node, StringBuilder builder)
        {
            return generateSource(node, builder);
        }
    
        public StringBuilder generateSource(Import node, StringBuilder builder)
        {
            builder.append("#include ");
        
            if (node.isExternal() || !node.getFileDeclaration().getName().equals(node.location))
            {
                return builder.append('<').append(node.getLocation()).append('>').append('\n');
            }
            else
            {
                return builder.append('"').append(node.getLocation()).append('"').append('\n');
            }
        }
    }
    
    public static class TargetForLoop extends TargetLoop
    {
        public StringBuilder generateSource(ForLoop node, StringBuilder builder)
        {
            Assignment initialization = node.getLoopInitialization();
            Node       condition      = node.getCondition();
            Node       update         = node.getLoopUpdate();
        
            if (initialization != null)
            {
                initialization.getTarget().generateSource(initialization, builder);//.append('\n');
            }
        
            builder.append("for (; ");
        
            if (condition != null)
            {
                condition.getTarget().generateSourceFragment(condition, builder);
            }
        
            builder.append("; ");
        
            if (update != null)
            {
                update.getTarget().generateSourceFragment(update, builder);
            }
        
            builder.append(')').append('\n');
        
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
            
                if (child != node.getArgumentList())
                {
                    child.getTarget().generateSource(child, builder);
                }
            }
        
            return builder;
        }
    }
    
    public static class TargetForEachLoop extends TargetLoop
    {
        public StringBuilder generateSource(ForEachLoop node, StringBuilder builde)
        {
            StringBuilder builder = new StringBuilder();
    
            Value hasNextCheck = node.getHasNextCheck();
            
            builder.append("while (").append(hasNextCheck.getTarget().generateSourceFragment(hasNextCheck)).append(')').append('\n');
        
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
            
                if (child != node.getArgumentList())
                {
                    child.getTarget().generateSource(child, builder);
                }
            }
        
            return builde.append(builder);
        }
    }
    
    public static class TargetLoop extends TargetNode
    {
        
    }
    
    public static class TargetFileDeclaration extends TargetNode
    {
        /**
         * Get the name of the file that will be output as a C header file.<br>
         * <br>
         * For example: A generateHeaderName() call for a FileDeclaration of Test.nova
         * would return "nova_Test.h"
         *
         * @return The name of the file output as a C header file.
         */
        public String generateHeaderName(FileDeclaration node)
        {
            Package pkg = node.getPackage();
            ClassDeclaration clazz = node.getClassDeclaration();
            
            return pkg.getTarget().generateHeaderLocation(pkg) + "/" + clazz.getTarget().generateSourceName(clazz) + ".h";
        }
    
        /**
         * Get the name of the file that will be output as a C source file.<br>
         * <br>
         * For example: A generateSourceName() call for a FileDeclaration of Test.nova
         * would return "nova_Test.c"
         *
         * @return The name of the file output as a C source file.
         */
        public String generateSourceName(FileDeclaration node)
        {
            Package pkg = node.getPackage();
            ClassDeclaration clazz = node.getClassDeclaration();
    
            return pkg.getTarget().generateHeaderLocation(pkg) + "/" + clazz.getTarget().generateSourceName(clazz) + ".c";
        }
    
        public StringBuilder generateHeader(FileDeclaration node, StringBuilder builder)
        {
            if (node.header == null)
            {
                ClassDeclaration clazz = node.getClassDeclaration();
                
                String definitionName = "FILE_" + clazz.getTarget().generateSourceName(clazz) + "_" + Nova.LANGUAGE_NAME.toUpperCase();
            
                builder.append("#pragma once").append('\n');
                builder.append("#ifndef ").append(definitionName).append('\n');
                builder.append("#define ").append(definitionName).append("\n\n");
            
                generateDummyTypes(node, builder).append('\n');
    
                generateClosureDefinitions(node, builder, true).append('\n');
            
                ImportList imports = node.getImportList();
            
                imports.getTarget().generateHeader(imports, builder);
            
                builder.append('\n');
            
                for (int i = 0; i < node.getNumChildren(); i++)
                {
                    Node child = node.getChild(i);
                
                    if (child != imports)
                    {
                        child.getTarget().generateHeader(child, builder);
                    }
                }
            
                builder.append('\n').append("#endif").append('\n');
    
                node.header = builder;
            }
        
            return node.header;
        }
    
        public StringBuilder generateSource(FileDeclaration node, StringBuilder builder)
        {
            if (node.source == null)
            {
                builder.append("#include <precompiled.h>\n");
                
                ImportList imports = node.getImportList();
                
                imports.getTarget().generateSource(imports, builder).append('\n');
            
                generateSourceClosureContextDefinitions(node, builder).append('\n');
                generateClosureDefinitions(node, builder, false).append('\n');
            
                for (int i = 0; i < node.getNumChildren(); i++)
                {
                    Node child = node.getChild(i);
                
                    if (child != node.getImportList())
                    {
                        child.getTarget().generateSource(child, builder);
                    }
                }
    
                node.source = builder.append('\n');
            }
        
            return node.source;
        }
    
        public StringBuilder generateHeaderNativeInterface(FileDeclaration node, StringBuilder builder)
        {
            for (ClassDeclaration clazz : node.getClassDeclarations())
            {
                clazz.getTarget().generateHeaderNativeInterface(clazz, builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateSourceNativeInterface(FileDeclaration node, StringBuilder builder)
        {
            for (ClassDeclaration clazz : node.getClassDeclarations())
            {
                clazz.getTarget().generateSourceNativeInterface(clazz, builder);
            }
        
            return builder;
        }
    
        /**
         * Generate dummy class declarations for each of the imported files.
         * This is needed in a situation when a class imports a class that
         * in returns needs to import the respective one. In other words,
         * the chicken vs egg scenario.
         *
         * @return The generated code used for generating the dummy class
         * 		types.
         */
        private StringBuilder generateDummyTypes(FileDeclaration node, StringBuilder builder)
        {
    //		builder.append("typedef struct ExceptionData ExceptionData;\n");
        
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
            
                if (child instanceof ClassDeclaration)
                {
                    ClassDeclaration clazz = (ClassDeclaration)child;
                
                    builder.append("typedef struct ").append(clazz.getTarget().generateSourceName(clazz)).append(' ').append(clazz.getTarget().generateSourceName(clazz)).append(';').append('\n');
                }
            }

    //		ImportList imports = getImportList();
    //		
    //		for (int i = 0; i < imports.getNumChildren(); i++)
    //		{
    //			Import node = (Import)imports.getChild(i);
    //			
    //			if (!node.isExternal())
    //			{
    //				String name = node.getLocationNode().getName();
    //				
    //				builder.append("typedef struct ").append(name).append(' ').append(name).append(';').append('\n');
    //			}
    //		}
        
            return builder;
        }
    
        private StringBuilder generateSourceClosureContextDefinitions(FileDeclaration node, StringBuilder builder)
        {
            for (ClosureContext context : node.contexts)
            {
                context.getTarget().generateSource(context, builder);
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
        private StringBuilder generateClosureDefinitions(FileDeclaration node, StringBuilder builder, boolean publicClosures)
        {
            ArrayList<String> types = new ArrayList<>();
        
            for (ClosureDeclaration closure : node.closures)
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
        
            for (ClosureDeclaration closure : node.closures)
            {
                if (closure.isPublic() == publicClosures)
                {
                    closure.getTarget().generateClosureDefinition(closure, builder);
                }
            }
        
            return builder;
        }
    }
    
    public static class TargetExternalMethodDeclaration extends TargetMethodDeclaration
    {
        public StringBuilder generateSourceName(ExternalMethodDeclaration node, StringBuilder builder, String uniquePrefix)
        {
            return builder.append(node.alias);
        }
    }
    
    public static class TargetInterfaceVTable extends TargetVTable
    {
        public StringBuilder generateHeader(InterfaceVTable node, StringBuilder builder)
        {
            return builder;
        }
    
        public StringBuilder generateHeaderFragment(InterfaceVTable node, StringBuilder builder)
        {
            return generateType(node, builder).append(" ").append(InterfaceVTable.IDENTIFIER);
        }
    
        public StringBuilder generateSource(InterfaceVTable node, StringBuilder builder)
        {
            return builder;
        }
    
        public StringBuilder generateSourceFragment(InterfaceVTable node, StringBuilder builder)
        {
            NovaMethodDeclaration[] methods = node.getVirtualMethods();
        
            builder.append("{\n");
        
            for (NovaMethodDeclaration method : methods)
            {
                if (method != null)
                {
                    method.getTarget().generateInterfaceVTableSource(method, builder);
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
    
    public static class TargetExtensionVTable extends TargetVTable
    {
        public StringBuilder generateHeader(ExtensionVTable node, StringBuilder builder)
        {
            InterfaceVTable table = node.getInterfaceVTable();
            
            table.getTarget().generateHeader(table, builder).append('\n');
        
            super.generateHeader(node, builder);
        
            builder.append("extern ").append(generateType(node)).append(' ').append(generateSourceName(node)).append(";\n");
        
            return builder;
        }
    }
    
    public static class TargetVTable extends TargetIIdentifier
    {
        public StringBuilder generateHeader(VTable node, StringBuilder builder)
        {
            NovaMethodDeclaration methods[] = node.getVirtualMethods();
        
            builder.append("typedef struct ").append(generateTypeName(node)).append(' ').append(generateTypeName(node)).append(";\n");
        
            if (methods.length <= 0)
            {
                return builder;
            }
        
            builder.append("struct ").append(generateTypeName(node)).append("\n{\n");
        
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
            
                child.getTarget().generateHeaderFragment(child, builder).append(";\n");
            }
        
            generateVirtualMethodDeclarations(node, builder, methods);
            
            builder.append("}").append(";\n\n");
        
            return builder;
        }
    
        public StringBuilder generateSource(VTable node, StringBuilder builder)
        {
            NovaMethodDeclaration methods[] = node.getVirtualMethods();
        
            if (methods.length <= 0)
            {
                return builder;
            }
        
            generateType(node, builder).append(' ').append(generateSourceName(node)).append(" =\n");
        
            builder.append("{\n");
        
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
            
                child.getTarget().generateSourceFragment(child, builder).append(",\n");
            }
        
            generateVirtualMethodValues(node, builder, methods);
            
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
        public StringBuilder generateVirtualMethodDeclarations(VTable node, StringBuilder builder, NovaMethodDeclaration methods[])
        {
            for (NovaMethodDeclaration method : methods)
            {
                generateVirtualMethodDeclaration(node, builder, method);
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
        public StringBuilder generateVirtualMethodDeclaration(VTable node, StringBuilder builder, NovaMethodDeclaration method)
        {
            VirtualMethodDeclaration virtual = method.getVirtualMethod();
            ParameterList params = method.getParameterList();
            
            return method.getTarget().generateType(method, builder).append(" (*").append(virtual.getTarget().generateVirtualMethodName(virtual)).append(")(").append(params.getTarget().generateHeader(params)).append(");\n");
        }
    
        /**
         * Add the vtable values that point to the correct virtual method
         * implementation for the specified class.
         *
         * @param builder The StringBuilder to append the data to.
         * @param methods The methods to add the references to.
         * @return The StringBuilder with the appended data.
         */
        public StringBuilder generateVirtualMethodValues(VTable node, StringBuilder builder, NovaMethodDeclaration methods[])
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
                
                    method.getTarget().generateSourceName(method, builder);
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
    
    public static class TargetIfStatement extends TargetControlStatement
    {
        public StringBuilder generateSource(IfStatement node, StringBuilder builder)
        {
            generateSourceFragment(node, builder).append('\n');
            
            Scope scope = node.getScope();
            
            scope.getTarget().generateSource(scope, builder);
        
            return builder;
        }
    
        public StringBuilder generateSourceFragment(IfStatement node, StringBuilder builder)
        {
            Value condition = node.getCondition();
            
            return builder.append("if (").append(condition.getTarget().generateSourceFragment(condition)).append(')');
        }
    }
    
    public static class TargetElseStatement extends TargetControlStatement
    {
        public StringBuilder generateSource(ElseStatement node, StringBuilder builder)
        {
            builder.append("else");
        
            if (node.getNumChildren() == 2)
            {
                Node child = node.getChild(1);
            
                if (child instanceof IfStatement)
                {
                    builder.append(' ');
                
                    child.getTarget().generateSourceFragment(child, builder);
                }
            }
        
            builder.append('\n');
            
            Scope scope = node.getScope();
            
            return scope.getTarget().generateSource(scope, builder);
        }
    }
    
    public static class TargetDimensions extends TargetNode
    {
        public StringBuilder generateSourceFragment(Dimensions node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder, "[", "]");
        }
    
        public StringBuilder generateSourceFragment(Dimensions node, StringBuilder builder, String prefix, String postfix)
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
                
                builder.append(prefix).append(child.getTarget().generateSourceFragment(child)).append(postfix);
            }
        
            return builder;
        }
    }
    
    public static class TargetDestructor extends TargetBodyMethodDeclaration
    {
        public StringBuilder generateSource(Destructor node, StringBuilder builder)
        {
            generateSourceSignature(node, builder).append('\n').append('{').append('\n');
    
            nullChecker(node, builder).append('\n');
        
            deleteData(node, builder).append('\n');
        
            for (int i = 0; i < node.getNumVisibleChildren(); i++)
            {
                Node child = node.getVisibleChild(i);
            
                if (child != node.getParameterList())
                {
                    child.getTarget().generateSource(child, builder);
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
        private StringBuilder nullChecker(Destructor node, StringBuilder builder)
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
        private StringBuilder deleteData(Destructor node, StringBuilder builder)
        {
            ClassDeclaration  classDeclaration = node.getParentClass();
            InstanceFieldList privateFields    = classDeclaration.getFieldList().getPrivateFieldList();
        
            for (int i = 0; i < privateFields.getNumChildren(); i++)
            {
                FieldDeclaration field = (FieldDeclaration)privateFields.getChild(i);
            
                generateFreeFieldSource(node, builder, field).append('\n');
            }
        
            if (classDeclaration.containsNonStaticPrivateData())
            {
                generateFreeMemberSource(node, builder, "prv").append('\n');
            }
        
            InstanceFieldList publicFields = classDeclaration.getFieldList().getPublicFieldList();
        
            for (int i = 0; i < publicFields.getNumChildren(); i++)
            {
                FieldDeclaration field = (FieldDeclaration)publicFields.getChild(i);

//			field.generateFreeOutput(builder);
                generateFreeFieldSource(node, builder, field).append('\n');
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
        private StringBuilder generateFreeFieldSource(Destructor node, StringBuilder builder, FieldDeclaration field)
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
                    builder.append("NOVA_FREE(" + field.getTarget().generateUseOutput(field, new StringBuilder(), true) + ");");
                }
                else if (field.getTypeClass().getDestructor() != null)
                {
                    Destructor dest = field.getTypeClass().getDestructor();
                    
                    dest.getTarget().generateSourceName(dest, builder).append('(').append('&');
                
                    field.getTarget().generateUseOutput(field, builder, true).append(", ").append(Exception.EXCEPTION_DATA_IDENTIFIER).append(");");
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
        private StringBuilder generateFreeMemberSource(Destructor node, StringBuilder builder, String name)
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
    
    public static class TargetDefaultParameterInitialization extends TargetNode
    {
        public StringBuilder generateSourceFragment(DefaultParameterInitialization node, StringBuilder builder)
        {
            String use = node.parameter.getTarget().generateUseOutput(node.parameter).toString();
        
            builder.append(use).append(" = ");
            node.parameter.getTarget().generateTypeCast(node.parameter, builder).append('(');
        
            builder.append(use).append(" == ");
        
            if (node.parameter.isPrimitive())
            {
                builder.append("(intptr_t)nova_null");
            }
            else
            {
                builder.append(0);
            }
            
            ClassDeclaration obj = node.getProgram().getClassDeclaration("nova/Object");
            
            String cast = !node.parameter.getDefaultValue().isPrimitive() ? obj.getTarget().generateTypeCast(obj).toString() : "";
            
            Value defaultValue = node.parameter.getDefaultValue();
            
            builder.append(" ? ").append(cast).append(defaultValue.getTarget().generateSourceFragment(defaultValue)).append(" : ").append(cast).append(use);
        
            return builder.append(");");
        }
    }
    
    public static class TargetContinue extends TargetNode
    {
        public StringBuilder generateSourceFragment(Continue node, StringBuilder builder)
        {
            return builder.append("continue;");
        }
    }
    
    public static class TargetConstructor extends TargetBodyMethodDeclaration
    {
        public StringBuilder generateTypeName(Constructor node, StringBuilder builder)
        {
            return generateTypeClassName(node, builder);
        }
    
        public StringBuilder generateHeader(Constructor node, StringBuilder builder)
        {
            if (node.isVisibilityValid())
            {
                if (node.getVisibility() == InstanceDeclaration.PRIVATE)
                {
                    return builder;
                }
            }
        
            if (node.isReference())
            {
                SyntaxMessage.error("Constructor cannot return a reference", node);
            }
        
            return generateSourcePrototype(node, builder).append('\n');
        }
    
        public StringBuilder generateSource(Constructor node, StringBuilder builder)
        {
            generateSourceSignature(node, builder).append('\n');
        
            builder.append('{').append('\n');
        
            ClassDeclaration classDeclaration = node.getParentClass();
        
            if (classDeclaration.containsNonStaticData() || classDeclaration.containsVirtualMethods())
            {
                ClassDeclaration clazz = node.getTypeClass();
                
                builder.append("CCLASS_NEW(").append(clazz.getTarget().generateSourceName(clazz)).append(", ").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
            
                if (!classDeclaration.containsNonStaticPrivateData())
                {
                    builder.append(",");
                }
            
                builder.append(");");
            }
            else
            {
                builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(" = ").append(generateTypeCast(node)).append("1").append(';');
            }
        
            builder.append('\n');
        
            VTable extension = node.getParentClass().getVTableNodes().getExtensionVTable();
        
            builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append("->").append(VTable.IDENTIFIER).append(" = &").append(extension.getTarget().generateSourceName(extension)).append(";\n");
        
            {
                Stack<AssignmentMethod> calls = new Stack<>();
            
                ClassDeclaration extended = node.getParentClass().getExtendedClassDeclaration();
            
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
                        method.getTarget().generateMethodCall(method, builder, true);
                    }
                }
            }
        
            // Generate super calls.
            {
                Stack<MethodCall> calls = new Stack<>();
    
                node.addSuperCallFor(calls, node);
            
                while (!calls.isEmpty())
                {
                    MethodCall call = calls.pop();
                    
                    call.getTarget().generateSource(call, builder);
                }
            }
    
            AssignmentMethod assignmentMethod = node.getParentClass().getAssignmentMethodNode();
            
            assignmentMethod.getTarget().generateMethodCall(assignmentMethod, builder);
        
            builder.append('\n');
    
            Scope scope = node.getScope();
            
            scope.getTarget().generateSource(scope, builder);
        
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
    
    public static class TargetClosureParameterList extends TargetParameterList
    {
        public StringBuilder generateHeader(ClosureParameterList node, StringBuilder builder)
        {
            generateHeaderDefaultParameters(node, builder);
        
            for (Value param : node)
            {
                builder.append(", ");
            
                param.getTarget().generateHeader(param, builder);
            }
        
            builder.append(", void*");
        
            return builder;
        }
    }
    
    public static class TargetParameterList extends TargetTypeList
    {
        public StringBuilder generateHeader(ParameterList node, StringBuilder builder)
        {
            return generateHeaderParameters(node, builder);
        }
    
        public StringBuilder generateSource(ParameterList node, StringBuilder builder)
        {
            return generateSourceParameters(node, builder);
        }
    
        public StringBuilder generateHeaderParameters(ParameterList node, StringBuilder builder)
        {
            generateHeaderDefaultParameters(node, builder);
        
            for (int i = 0; i < node.getNumVisibleChildren(); i++)
            {
                if (i > 0 || node.getParameterOffset() > 0)
                {
                    builder.append(", ");
                }
    
                Node child = node.getVisibleChild(i);
                
                child.getTarget().generateHeader(child, builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateSourceParameters(ParameterList node, StringBuilder builder)
        {
            generateSourceDefaultParameters(node, builder);
        
            for (int i = 0; i < node.getNumVisibleChildren(); i++)
            {
                if (i > 0 || node.getParameterOffset() > 0)
                {
                    builder.append(", ");
                }
    
                Node child = node.getVisibleChild(i);
                
                child.getTarget().generateSource(child, builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateHeaderDefaultParameters(ParameterList node, StringBuilder builder)
        {
            for (int i = 0; i < node.getParameterOffset(); i++)
            {
                if (i > 0)
                {
                    builder.append(", ");
                }
    
                Node child = node.getChild(i);
                
                child.getTarget().generateHeader(child, builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateSourceDefaultParameters(ParameterList node, StringBuilder builder)
        {
            for (int i = 0; i < node.getParameterOffset(); i++)
            {
                if (i > 0)
                {
                    builder.append(", ");
                }
    
                Node child = node.getChild(i);
                
                child.getTarget().generateSource(child, builder);
            }
        
            return builder;
        }
    }
    
    public static class TargetClosureDeclaration extends TargetParameter
    {
        public StringBuilder generateHeader(ClosureDeclaration node, StringBuilder builder)
        {
            return generateSource(node, builder);
        }
        
        public StringBuilder generateSource(ClosureDeclaration node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }
        
        public StringBuilder generateSourceFragment(ClosureDeclaration node, StringBuilder builder)
        {
            builder.append(generateType(node)).append(' ').append(generateSourceName(node)).append(", ");
            
            Parameter ref = node.getParameterList().getObjectReference();
            
            ref.getTarget().generateType(ref, builder).append(' ');
            
            generateObjectReferenceIdentifier(node, builder).append(", ");
            generateContextParameter(node, builder);
        
            return builder;
        }
        
        public StringBuilder generateArguments(ClosureDeclaration node, StringBuilder builder, Variable context, NovaMethodDeclaration method)
        {
            if (context.getRootReferenceNode() instanceof ClassDeclaration == false)
            {
                Accessible root = context.getRootReferenceNode();
                
                root.getTarget().generateArgumentReference(root, builder, context);
            }
            else
            {
                builder.append(ClosureDeclaration.NULL_IDENTIFIER);//method.getParameterList().getObjectReference().generateNullOutput(builder);
            }
        
            builder.append(", ");
            method.getTarget().generateClosureContext(method, builder);
        
            return builder;
        }
        
        public StringBuilder generateObjectReferenceIdentifier(ClosureDeclaration node, StringBuilder builder)
        {
            return builder.append(generateSourceName(node, "ref"));
        }
        
        public StringBuilder generateContextParameter(ClosureDeclaration node)
        {
            return generateContextParameter(node, new StringBuilder());
        }
        
        public StringBuilder generateContextParameter(ClosureDeclaration node, StringBuilder builder)
        {
            return builder.append("void* ").append(node.getContextName());
        }
        
        public StringBuilder generateType(ClosureDeclaration node, StringBuilder builder, boolean checkArray, boolean checkValueReference)
        {
            return builder.append(generateSourceName(node, "closure" + node.id));
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
        public StringBuilder generateClosureDefinition(ClosureDeclaration node, StringBuilder builder)
        {
            builder.append("typedef ");
        
            super.generateType(node, builder, true, true).append(" (*").append(generateSourceName(node, "closure" + node.id)).append(')');
            
            ParameterList params = node.getParameterList();
            
            builder.append('(').append(params.getTarget().generateHeader(params)).append(')').append(";\n");
        
            return builder;
        }
    }
    
    public static class TargetParameter extends TargetLocalDeclaration
    {
        public StringBuilder generateTypeName(Parameter node, StringBuilder builder)
        {
            if (node.isObjectReference() && node.getType() != null)
            {
                return generateTypeClassName(node, builder);
            }
            /*else if (getTypeClass() != null && getTypeClass().equals(getProgram().getClassDeclaration(Nova.getClassLocation("Number"))))
            {
                return builder.append("long_long");
            }*/
        
            return super.generateTypeName(node, builder);
        }
    
        public StringBuilder generateHeader(Parameter node, StringBuilder builder)
        {
            return generateModifiersSource(node, builder);
        }
    
        public StringBuilder generateSource(Parameter node, StringBuilder builder)
        {
            return generateHeader(node, builder).append(' ').append(generateSourceName(node));
        }
    }
    
    public static class TargetVariableDeclarationList extends TargetList
    {
        /**
         * Generate the output needed to free the variables after they are
         * finished with.
         *
         * @return The String output of the variables being freed.
         */
        public StringBuilder generateFreeVariablesOutput(VariableDeclarationList node, StringBuilder builder)
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                VariableDeclaration variable = (VariableDeclaration)node.getChild(i);
            
                variable.getTarget().generateFreeOutput(variable, builder);
            }
        
            return builder;
        }
    
        public StringBuilder generateHeader(VariableDeclarationList node, StringBuilder builder)
        {
            return builder;
        }
    
        public StringBuilder generateSource(VariableDeclarationList node, StringBuilder builder)
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                LocalDeclaration child = (LocalDeclaration)node.getChild(i);
            
                child.getTarget().generateDeclarationFragment(child, builder).append(" = ");
                child.getTarget().generateDefaultValue(child, builder);
            
                builder.append(";\n");
            }
        
            if (node.getNumChildren() > 0)
            {
                builder.append('\n');
            }
        
            return builder;
        }
    }
    
    public static class TargetClosureContextDeclaration extends TargetLocalDeclaration
    {
        public StringBuilder generateDeclarationFragment(ClosureContextDeclaration node, StringBuilder builder)
        {
            return builder.append(node.context.getName()).append(' ').append(node.getName());
        }
        
        public StringBuilder generateDefaultValue(ClosureContextDeclaration node, StringBuilder builder)
        {
            builder.append("\n{\n");
        
            for (ClosureVariableDeclaration var : node.context)
            {
                generateDeclarationValue(node, builder, var);
            }
        
            return builder.append("}");
        }
        
        public StringBuilder generateDeclarationValue(ClosureContextDeclaration node, StringBuilder builder, ClosureVariableDeclaration var)
        {
            //Variable v = var.generateUsableVariable(this, Location.INVALID);
        
            if (var.originalDeclaration instanceof ClosureVariableDeclaration)
            {
                builder.append("context->");
            }
            else
            {
                builder.append('&');
            }
        
            var.originalDeclaration.getTarget().generateSourceName(var.originalDeclaration, builder);
        
            return builder.append(",\n");
        }
    }
    
    public static class TargetFieldDeclaration extends TargetInstanceDeclaration
    {
        public StringBuilder generateHeader(FieldDeclaration node, StringBuilder builder)
        {
            if (node.isStatic() && (node.getVisibility() == FieldDeclaration.PUBLIC || node.getVisibility() == FieldDeclaration.VISIBLE))
            {
                builder.append("extern ");
            }
        
            return generateSource(node, builder);
        }
    }
    
    public static class TargetLocalDeclaration extends TargetVariableDeclaration
    {
        public StringBuilder generateType(LocalDeclaration node, StringBuilder builder, boolean checkArray, boolean checkValueReference)
        {
            if (node.isImplicit())
            {
                /*builder.append("void*");
                
                if (checkValueReference && isValueReference())
                {
                    builder.append('*');
                }
                
                return builder;*/
                return node.implicitType.getTarget().generateType(node.implicitType, builder, checkArray, checkValueReference);
            }
        
            return super.generateType(node, builder, checkArray, checkValueReference);
        }
    }
    
    public static class TargetClosureContext extends TargetTypeList
    {
        public StringBuilder generateSource(ClosureContext node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder).append(";\n");
        }
        
        public StringBuilder generateSourceFragment(ClosureContext node, StringBuilder builder)
        {
            builder.append("typedef struct\n");
            builder.append("{\n");
        
            for (ClosureVariableDeclaration var : node)
            {
                builder.append("/* ").append(var.originalDeclaration).append(" */ ");
                var.getTarget().generateSource(var, builder);
			
                /*boolean original = var.originalDeclaration.isValueReference();
                var.originalDeclaration.setIsValueReference(true);
                var.originalDeclaration.generateSource(builder);
                var.originalDeclaration.setIsValueReference(original);*/
            }
        
            builder.append("} ").append(node.getName());
        
            return builder;
        }
    }
    
    public static class TargetTypeList extends TargetList
    {
        public StringBuilder generateHeaderFragment(TypeList<Node> node, StringBuilder builder)
        {
            for (Node child : node)
            {
                child.getTarget().generateHeader(child, builder);
            }
        
            return builder;
        }
        
        public StringBuilder generateSourceFragment(TypeList<Node> node, StringBuilder builder)
        {
            for (Node child : node)
            {
                child.getTarget().generateSource(child, builder);
            }
        
            return builder;
        }
    }
    
    public static class TargetClosure extends TargetVariable
    {
        public StringBuilder generateSource(Closure node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }
        
        public StringBuilder generateSourceFragment(Closure node, StringBuilder builder)
        {
            ClosureDeclaration decl = node.getClosureDeclaration();
            
            decl.getTarget().generateTypeCast(decl, builder);
        
            if (node.getMethodDeclaration().isVirtual() && !node.isVirtualTypeKnown())
            {
                Accessible root = node.getRootReferenceNode();
                
                root.getTarget().generateArgumentReference(root, builder, node).append("->").append(VTable.IDENTIFIER).append("->");
                
                VirtualMethodDeclaration virtual = node.getMethodDeclaration().getVirtualMethod();
                
                builder.append(virtual.getTarget().generateVirtualMethodName(virtual));
            }
            else
            {
                VariableDeclaration d = node.getDeclaration();
                
                builder.append('&').append(d.getTarget().generateSourceName(d));
            }
        
            builder.append(", ");
    
            decl.getTarget().generateArguments(decl, builder, node, node.getMethodDeclaration());
        
            return builder;
        }
    }
    
    public static class TargetVariable extends TargetIdentifier
    {
        public StringBuilder generateSourcePrefix(Variable node, StringBuilder builder)
        {
            super.generateSourcePrefix(node, builder);
        
            if (node.declaration instanceof ClosureVariableDeclaration)
            {
                builder.append(ClosureVariableDeclaration.CONTEXT_VARIABLE_NAME).append("->");
            }
        
            return builder;
        }
        
        public StringBuilder generateArgumentOutput(Variable node, StringBuilder builder)
        {
            super.generateArgumentOutput(node, builder);
        
            generateExtraArguments(node, builder);
        
            return builder;
        }
    
        public StringBuilder generateExtraArguments(Variable node, StringBuilder builder)
        {
            if (node.getDeclaration() instanceof ClosureDeclaration)
            {
                builder.append(", ");
            
                ClosureDeclaration declaration = (ClosureDeclaration)node.getDeclaration();
            
                if (declaration.getParent() instanceof NovaParameterList)
                {
                    builder.append(declaration.getContextName());
                }
                else
                {
                    declaration.getTarget().generateArguments(declaration, builder, node, node.getParentMethod());
                }
            }
        
            return builder;
        }
        
        public StringBuilder generateSourceFragment(Variable node, StringBuilder builder)
        {
            super.generateSourceFragment(node, builder);
        
            generateObjectReferenceIdentifier(node, builder);
        
            return builder;
        }
    
        public StringBuilder generateObjectReferenceIdentifier(Variable node, StringBuilder builder)
        {
            if (node.getDeclaration() instanceof ClosureDeclaration && node.getParent() instanceof ArgumentList)
            {
                ClosureDeclaration declaration = (ClosureDeclaration)node.getDeclaration();
            
                builder.append(", ");
                declaration.getTarget().generateObjectReferenceIdentifier(declaration, builder);
            }
        
            return builder;
        }
    
        public String generateGenericType(Variable node)
        {
            GenericTypeArgumentList args = node.getGenericTypeArgumentList();
        
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
                
                    s += arg.generateNovaInput(new StringBuilder(), true, node);
                }
            
                s += GenericCompatible.GENERIC_END;
            
                return s;
            }
        
            return "";
        }
    }
    
    public static class TargetClassDeclaration extends TargetInstanceDeclaration
    {
        /**
         * Generate the C output for when this value node is being used
         * as an argument for a method call.
         *
         * @param builder The StringBuilder to append the data to.
         * @param callingMethod The method that is being called by the
         * 		specified Identifier.
         * @return The C output for when this value node is being used
         * 		as an argument for a method call.
         */
        public StringBuilder generateArgumentReference(ClassDeclaration node, StringBuilder builder, Identifier callingMethod)
        {
            if (callingMethod instanceof MethodCall)
            {
                CallableMethod declaration = ((MethodCall)callingMethod).getInferredDeclaration();
                
                if (declaration.isStatic() || declaration instanceof Constructor)
                {
                    Parameter ref = declaration.getParameterList().getObjectReference();
                    
                    return ref.getTarget().generateNullOutput(ref, builder);
                }
                else if (declaration instanceof ClosureDeclaration)
                {
                    ClosureDeclaration closure = (ClosureDeclaration)declaration;
                    
                    return closure.getTarget().generateSourceName(closure, builder, "ref");
                }
            }
            
            return super.generateArgumentReference(node, builder, callingMethod);
        }
    
        public StringBuilder generateHeaderNativeInterface(ClassDeclaration node, StringBuilder builder)
        {
            MethodDeclaration[] methods = node.getVisibleNativeMethods();
		
            /*if (methods.length <= 0)
            {
                return builder;
            }*/
        
            String name = generateSourceName(node, "native").toString();
        
            for (MethodDeclaration method : methods)
            {
                builder.append("typedef " + method.getTarget().generateType(method) + " (*");
            
                method.getTarget().generateSourceNativeName(method, builder, true).append(")(");
            
                ParameterList params = method.getParameterList();
                
                params.getTarget().generateHeader(params, builder).append(");\n");
            }
        
            builder.append("\ntypedef struct " + name + "\n");
            builder.append("{\n");
        
            for (MethodDeclaration method : methods)
            {
                method.getTarget().generateSourceNativeName(method, builder, true).append(" ");
                method.getTarget().generateSourceNativeName(method, builder, false).append(";\n");
            }
        
            builder.append("} " + name + ";\n");
        
            return builder;
        }
    
        public StringBuilder generateSourceNativeInterface(ClassDeclaration node, StringBuilder builder)
        {
    //		String name = generateSourceName("native").toString();
        
            MethodDeclaration[] methods = node.getVisibleNativeMethods();

    //		builder.append('\n');

    //		builder.append("struct " + name + "\n");
            builder.append("{\n");
        
            for (MethodDeclaration method : methods)
            {
                String value = "&" + method.getTarget().generateSourceName(method);
            
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
        
        public StringBuilder generateHeader(ClassDeclaration node, StringBuilder builder)
        {
            VTableList vtables = node.getVTableNodes();
            
            vtables.getTarget().generateHeader(vtables, builder).append('\n');
        
            if (node.containsNonStaticData() || node.containsVirtualMethods())
            {
                builder.append("CCLASS_CLASS").append('\n').append('(').append('\n');
            
                generateSourceName(node, builder).append(", ").append('\n').append('\n');
            
                VTable extension = node.getVTableNodes().getExtensionVTable();
            
                builder.append(extension.getTarget().generateType(extension)).append("* ").append(VTable.IDENTIFIER).append(";\n");
    
                FieldList fields = node.getFieldList();
                
                fields.getTarget().generateNonStaticHeader(fields, builder);
            
                if (node.containsNonStaticPrivateData())
                {
                    builder.append("struct Private* prv;").append('\n');
                }
            
                builder.append(')').append('\n');
            }
    
            FieldList fields = node.getFieldList();
            
            fields.getTarget().generateStaticHeader(fields, builder).append('\n');
        
            if (node.getStaticBlockList().getNumVisibleChildren() > 0)
            {
                StaticBlock child = node.getStaticBlockList().getChild(0);
                
                child.getTarget().generateHeader(child, builder, node);
            }
        
            MethodList constructors = node.getConstructorList();
            constructors.getTarget().generateHeader(constructors, builder);
    
            node.getDestructorList().getTarget().generateHeader(node.getDestructorList(), builder);
            node.getMethodList().getTarget().generateHeader(node.getMethodList(), builder);
            node.getPropertyMethodList().getTarget().generateHeader(node.getPropertyMethodList(), builder);
            node.getHiddenMethodList().getTarget().generateHeader(node.getHiddenMethodList(), builder);
            node.getVirtualMethodList().getTarget().generateHeader(node.getVirtualMethodList(), builder);
        
            return builder;
        }
        
        public StringBuilder generateSource(ClassDeclaration node, StringBuilder builder)
        {
            VTableList vtables = node.getVTableNodes();
            
            vtables.getTarget().generateSource(vtables, builder).append('\n');
        
            if (node.containsNonStaticPrivateData())
            {
                builder.append("CCLASS_PRIVATE").append('\n').append('(').append('\n').append(generatePrivateFieldsSource(node)).append(')').append('\n');
            }
        
            builder.append(generatePrivateMethodPrototypes(node));
    
            FieldList fields = node.getFieldList();
            
            fields.getTarget().generateStaticSource(fields, builder);
        
            for (int i = node.getNumDefaultChildren(); i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
            
                builder.append('\n').append(child.getTarget().generateSource(child));
            }
    
            fields = node.getFieldList();
            
            fields.getTarget().generateNonStaticSource(fields, builder);
        
            generateStaticBlocksSource(node, builder);
    
            node.getConstructorList().getTarget().generateSource(node.getConstructorList(), builder);
            node.getDestructorList().getTarget().generateSource(node.getDestructorList(), builder);
            node.getMethodList().getTarget().generateSource( node.getMethodList(), builder);
            node.getPropertyMethodList().getTarget().generateSource(node.getPropertyMethodList(), builder);
            node.getHiddenMethodList().getTarget().generateSource(node.getHiddenMethodList(), builder);
            node.getVirtualMethodList().getTarget().generateSource(node.getVirtualMethodList(), builder);
        
            return builder;
        }
    
        private StringBuilder generateStaticBlocksSource(ClassDeclaration node, StringBuilder builder)
        {
            if (node.getStaticBlockList().getNumVisibleChildren() > 0)
            {
                StaticBlock block = node.getStaticBlockList().getChild(0);
                
                block.getTarget().generateMethodHeader(block, builder, node).append('\n');
            
                builder.append('{').append('\n');
            
                for (int i = 0; i < node.getStaticBlockList().getNumVisibleChildren(); i++)
                {
                    block = node.getStaticBlockList().getChild(i);
                
                    block.getTarget().generateSource(block, builder);
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
        private StringBuilder generatePrivateFieldsSource(ClassDeclaration node)
        {
            return generatePrivateFieldsSource(node, new StringBuilder());
        }
    
        /**
         * Generate the C source representation of the private field
         * declarations.
         *
         * @param builder The StringBuilder to append that data to.
         * @return The StringBuilder with the appended data.
         */
        private StringBuilder generatePrivateFieldsSource(ClassDeclaration node, StringBuilder builder)
        {
            if (node.getExtendedClassDeclaration() != null)
            {
                ClassDeclaration clazz = node.getExtendedClassDeclaration();
                
                clazz.getTarget().generatePrivateFieldsSource(clazz, builder);
            }
        
            InstanceFieldList fields = node.getFieldList().getPrivateFieldList();
            
            return fields.getTarget().generateSource(fields, builder);
        }
        
        public StringBuilder generateSourceName(ClassDeclaration node, StringBuilder builder, String uniquePrefix)
        {
            if (uniquePrefix == null)
            {
                uniquePrefix = Nova.LANGUAGE_NAME;
            }
        
            return generateUniquePrefix(node, builder).append(uniquePrefix).append("_").append(node.getName());
        }
    
        /**
         * Generate the prototypes for specifically the private methods.
         *
         * @return A String containing the prototype definitions.
         */
        private String generatePrivateMethodPrototypes(ClassDeclaration node)
        {
            StringBuilder  builder = new StringBuilder();
        
            generatePrototypes(node, builder, node.getMethodList());
            generatePrototypes(node, builder, node.getPropertyMethodList());
        
            if (builder.length() > 0)
            {
                builder.insert(0, '\n');
            }
        
            return builder.toString();
        }
    
        private void generatePrototypes(ClassDeclaration node, StringBuilder builder, MethodList methods)
        {
            for (int i = 0; i < methods.getNumChildren(); i++)
            {
                MethodDeclaration methodDeclaration = methods.getChild(i);
            
                if (methodDeclaration.getVisibility() == InstanceDeclaration.PRIVATE)
                {
                    methodDeclaration.getTarget().generateSourcePrototype(methodDeclaration, builder).append('\n');
                }
            }
        }
    
        /**
         * @see #generateUniquePrefix(ClassDeclaration, StringBuilder)
         */
        public StringBuilder generateUniquePrefix(ClassDeclaration node)
        {
            return generateUniquePrefix(node, new StringBuilder());
        }
    
        /**
         * Get the prefix that is used for the data that is contained
         * within the specified class.<br>
         * <br>
         * For example:
         * <blockquote><pre>
         * package "this/is/my/package"
         *
         * public class Test
         * {
         * 	...
         * }</pre></blockquote>
         * The method prefix would look like:
         * "<code>this_is_my_package_NovaTest</code>"
         *
         * @return The prefix that is used for the data contained within
         * 		the class.
         */
        public StringBuilder generateUniquePrefix(ClassDeclaration node, StringBuilder builder)
        {
            Package p = node.getFileDeclaration().getPackage();
            
            return p.getTarget().generateLocation(p, builder).append('_');
        }
    }
    
    public static class TargetCast extends TargetIValue
    {
        public StringBuilder generateSourceFragment(Cast node, StringBuilder builder)
        {
            builder.append('(').append(generateType(node)).append(')');
            
            Value value = node.getValueNode();
            Value ret = value.getReturnedNode();
            
            ret.getTarget().generatePointerToValueConversion(ret, builder);
            value.getTarget().generateSourceFragment(value, builder);
        
            return builder;
        }
    }
    
    public static class TargetBreak extends TargetNode
    {
        public StringBuilder generateSourceFragment(Break node, StringBuilder builder)
        {
            return builder.append("break;");
        }
    }
    
    public static class TargetBinaryOperation extends TargetIValue
    {
        public StringBuilder generateSource(BinaryOperation node, StringBuilder builder)
        {
            generateSourceFragment(node, builder);
        
            if (node.getOperator().isShorthand())
            {
                builder.append(";\n");
            }
        
            return builder;
        }
        
        public StringBuilder generateSourceFragment(BinaryOperation node, StringBuilder builder)
        {
            Operator operator = node.getOperator();
            
            if (node.getNumChildren() == 1)
            {
                Value operand = node.getLeftOperand();
                
                return operand.getTarget().generateSourceFragment(operand, builder);
            }
        
            String leftCast = "";
            String rightCast = "";
            
            Value left = node.getLeftOperand();
            Value right = node.getRightOperand();
            Value leftReturned = left.getReturnedNode();
            Value rightReturned = right.getReturnedNode();
        
            if (leftReturned.isOriginallyGenericType())
            {
                leftCast = leftReturned.getTarget().generateTypeCast(leftReturned, new StringBuilder(), true, false).toString();
            }
            if (rightReturned.isOriginallyGenericType())
            {
                rightCast = rightReturned.getTarget().generateTypeCast(rightReturned, new StringBuilder(), true, false).toString();
            }
        
            return builder.append(leftCast).append(left.getTarget().generateSourceFragment(left)).append(' ')
                .append(operator.getTarget().generateSourceFragment(operator)).append(' ')
                .append(rightCast).append(right.getTarget().generateSourceFragment(right));
        }
    }
    
    public static class TargetAssignmentMethod extends TargetBodyMethodDeclaration
    {
        public StringBuilder generateHeader(AssignmentMethod node, StringBuilder builder)
        {
            return generateSourcePrototype(node, builder).append('\n');
        }
        
        public StringBuilder generateSource(AssignmentMethod node, StringBuilder builder)
        {
            generateSourceSignature(node, builder).append('\n');
        
            builder.append('{').append('\n');
        
            generateFieldDefaultAssignments(node, builder);
        
            for (int i = 0; i < node.getNumVisibleChildren(); i++)
            {
                Node child = node.getVisibleChild(i);
                
                child.getTarget().generateSource(child, builder);
            }
    
            Scope scope = node.getScope();
            
            scope.getTarget().generateSource(scope, builder, false);
        
            builder.append('}').append('\n');
        
            return builder;
        }
    
        /**
         * @see #generateMethodCall(AssignmentMethod, java.lang.StringBuilder, boolean)
         */
        public StringBuilder generateMethodCall(AssignmentMethod node, StringBuilder builder)
        {
            return generateMethodCall(node, builder, false);
        }
    
        /**
         * @param cast Whether or not to add an explicit type cast for the
         * 		object reference identifier.
         */
        public StringBuilder generateMethodCall(AssignmentMethod node, StringBuilder builder, boolean cast)
        {
            super.generateMethodCall(node, builder).append("(");
        
            if (cast)
            {
                ClassDeclaration clazz = node.getParentClass();
                
                builder.append('(').append(clazz.getTarget().generateType(clazz)).append(')');
            }
        
            builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
        
            return builder.append(", ").append(Exception.EXCEPTION_DATA_IDENTIFIER).append(");\n");
        }
    
        /**
         * This method returns a String that contains the code needed to
         * assign the default null value to each uninitialized/uninstantiated
         * field variables.
         *
         * @param builder The StringBuilder to append the assignments to.
         * @return The appended buffer.
         */
        private StringBuilder generateFieldDefaultAssignments(AssignmentMethod node, StringBuilder builder)
        {
            FieldList fields = node.getParentClass().getFieldList();
            
            generateFieldDefaultAssignments(node, builder, fields.getPublicFieldList());
            generateFieldDefaultAssignments(node, builder, fields.getPrivateFieldList());
            
            return builder;
        }
    
        /**
         * This method returns a String that contains the code needed to
         * assign the default null value to each uninitialized/uninstantiated
         * field variables.
         *
         * @param builder The StringBuilder to append the assignments to.
         * @param fields The list of fields to assign default values to.
         * @return The appended buffer.
         */
        private StringBuilder generateFieldDefaultAssignments(AssignmentMethod node, StringBuilder builder, InstanceFieldList fields)
        {
            for (int i = 0; i < fields.getNumChildren(); i++)
            {
                FieldDeclaration field = (FieldDeclaration)fields.getChild(i);
            
                if (!field.isExternal())
                {
                    field.getTarget().generateUseOutput(field, builder).append(" = ");
                
                    if (!field.isPrimitiveType() && !field.isExternalType())
                    {
                        field.getTarget().generateNullOutput(field, builder);
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
    
    public static class TargetIValue extends TargetValue
    {
        
    }
    
    public static class TargetAssignment extends TargetValue
    {
        public StringBuilder generateSource(Assignment node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder).append(";\n");
        }
        
        public StringBuilder generateSourceFragment(Assignment node, StringBuilder builder)
        {
            if (node.getAssignedNodeValue().getDataType() == Value.POINTER &&
                node.getAssignmentNode().getReturnedNode().getDataType() == Value.VALUE ||
                node.getAssignedNodeValue().getDataType() == Value.DOUBLE_POINTER &&
                    node.getAssignmentNode().getReturnedNode().getDataType() == Value.POINTER)
            {
                builder.append('*');
            }
        
            Value assignee = node.getAssigneeNode();
            
            return assignee.getTarget().generateSourceFragment(assignee, builder).append(" = ").append(generateAssignmentSource(node));
        }
    
        /**
         * Generate the assignment's right hand value C output.
         *
         * @return The assignment's right hand value C output.
         */
        private StringBuilder generateAssignmentSource(Assignment node)
        {
            return generateAssignmentSource(node, new StringBuilder());
        }
    
        /**
         * Generate the assignment's right hand value C output.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The assignment's right hand value C output.
         */
        private StringBuilder generateAssignmentSource(Assignment node, StringBuilder builder)
        {
            Value assignment = node.getAssignmentNode();
        
            String assignmentType = assignment.getReturnedNode().getType();
            String assignedType = node.getAssignedNodeValue().getType();
        
            boolean sameType = assignmentType.equals(assignedType);
        
            if (sameType && assignment instanceof Accessible)
            {
                MethodCall call = (MethodCall)((Accessible)assignment).getLastAccessedOfType(MethodCall.class, false);
            
                if (call != null)
                {
                    sameType = !call.isVirtual();
                }
            }
            
            Value asignment = node.getAssignmentNode();
            
            if (!sameType)
            {
                Value assigned = node.getAssignedNodeValue();
                Value returned = assignment.getReturnedNode();
                
                assigned.getTarget().generateTypeCast(assigned, builder, true, false).append(returned.getTarget().generatePointerToValueConversion(returned)).append('(');
            }
        
            builder.append(assignment.generateDataTypeOutput(node.getAssignedNodeValue().getDataType())).append(assignment.getTarget().generateSourceFragment(assignment));
        
            if (!sameType)
            {
                builder.append(')');
            }
        
            return builder;
        }
    }
    
    public static class TargetArgumentList extends TargetList
    {
        public StringBuilder generateSource(ArgumentList node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }
    
        public StringBuilder generateSourceFragment(ArgumentList node, StringBuilder builder)
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node child = node.getChild(i);
            
                child.getTarget().generateSourceFragment(child, builder);
            }
        
            return builder;
        }
    }
    
    public static class TargetList extends TargetNode
    {
        
    }
    
    public static class TargetMatch extends TargetControlStatement
    {
        public StringBuilder generateSource(Match node, StringBuilder builder)
        {
            Scope scope = node.getScope();
            
            if (node.isConventionalSwitch())
            {
                Value control = node.getControlValue();
                
                builder.append("switch (" + control.getTarget().generateSourceFragment(control) + ")\n");
    
                scope.getTarget().generateSource(scope, builder);
            }
            else
            {
                boolean requiresFacade = node.requiresLoopFacade();
            
                if (requiresFacade)
                {
                    builder.append("do\n{\n");
                }
    
                scope.getTarget().generateSource(scope, builder, false);
            
                if (requiresFacade)
                {
                    builder.append("}\nwhile (0);\n");
                }
            }
        
            return builder;
        }
    }
    
    public static class TargetControlStatement extends TargetNode
    {
        
    }
    
    public static class TargetFallthrough extends TargetMatchChild
    {
        public StringBuilder generateSource(Fallthrough node, StringBuilder builder)
        {
            Variable fall = node.getParentSwitch().getLocalFallthrough();
        
            if (fall != null)
            {
                fall.getTarget().generateSourceFragment(fall, builder).append(" = 1;\n");
            }
        
            return builder;
        }
    }
    
    public static class TargetMatchChild extends TargetNode
    {
        
    }
    
    public static class TargetDefault extends TargetMatchCase
    {
        public StringBuilder generateSource(Default node, StringBuilder builder)
        {
            Scope scope = node.getScope();
    
            if (node.getParentSwitch().isConventionalSwitch())
            {
                builder.append("default:").append('\n');
            
                scope.getTarget().generateSource(scope, builder, false);
            }
            else
            {
                builder.append("else").append('\n');
            
                scope.getTarget().generateSource(scope, builder);
            }
        
            return builder;
        }
    }
    
    public static class TargetCase extends TargetMatchCase
    {
        public StringBuilder generateSource(Case node, StringBuilder builder)
        {
            Scope scope = node.getScope();
            
            if (node.getParentSwitch().isConventionalSwitch())
            {
                Value value = node.getValue();
                
                builder.append("case " + value.getTarget().generateSourceFragment(value) + ":\n");
            
                scope.getTarget().generateSource(scope, builder, false);
            
                if (node.requiresBreak())
                {
                    builder.append("break;\n");
                }
            }
            else
            {
                Value controlValue = node.getParentSwitch().getControlValue();
                
                String control = controlValue.getTarget().generateSourceFragment(controlValue).toString();
            
                Case before = null;
                String fall   = "";
            
                if (node.getParent().getChildBefore(node) instanceof Case)
                {
                    before = (Case)node.getParent().getChildBefore(node);
                }
            
                if (before != null)
                {
                    if (before.containsFallthrough())
                    {
                        Variable fallthrough = node.getParentSwitch().getLocalFallthrough();
                        
                        fall = fallthrough.getTarget().generateSourceFragment(fallthrough) + " || ";
                    }
                    else
                    {
                        builder.append("else ");
                    }
                }
                
                Value value = node.getValue();
                
                builder.append("if (" + fall + control + " == " + value.getTarget().generateSourceFragment(value) + ")").append('\n');
                builder.append("{\n");
            
                scope.getTarget().generateSource(scope, builder, false);
            
                if (node.getParentSwitch().requiresLoopFacade() && node.requiresBreak())
                {
                    builder.append("break;\n");
                }
            
                builder.append("}\n");
            }
        
            return builder;
        }
    }
    
    public static class TargetMatchCase extends TargetNode
    {
        
    }
    
    public static class TargetLambdaMethodDeclaration extends TargetBodyMethodDeclaration
    {
        public StringBuilder generateClosureContext(LambdaMethodDeclaration node, StringBuilder builder)
        {
            return builder.append('&').append(node.contextDeclaration.getName());
        }
    }
    
    public static class TargetTry extends TargetExceptionHandler
    {
        public StringBuilder generateSource(Try node, StringBuilder builder)
        {
            builder.append("TRY").append('\n');
            builder.append('{').append('\n');
            generateExceptionCodes(node, builder).append('\n');
    
            Scope scope = node.getScope();
            
            scope.getTarget().generateSource(scope, builder);
        
            builder.append('}').append('\n');
        
            return builder;
        }
    
        /**
         * Generate a String that adds all of the exception codes that this
         * try node catches to the exception data instance.
         *
         * @return The generated C language String.
         */
        private StringBuilder generateExceptionCodes(Try node, StringBuilder builder)
        {
            String variableName = Exception.EXCEPTION_DATA_IDENTIFIER;
        
            for (int i = 0; i < node.codes.size(); i++)
            {
                int code = node.codes.get(i);
            
                builder.append("novaEnv.nova_exception_ExceptionData.addCode(").append(variableName).append(", ").append(variableName).append(", ").append(code).append(");").append('\n');
            }
        
            return builder;
        }
    }
    
    public static class TargetThrow extends TargetExceptionHandler
    {
        public StringBuilder generateSource(Throw node, StringBuilder builder)
        {
            builder.append("THROW").append('(').append(node.getException().getID()).append(", ");
            Identifier exception = node.getExceptionInstance();
            
            exception.getTarget().generateSourceFragment(exception, builder).append(')').append(';').append('\n');
        
            return builder;
        }
    }
    
    public static class TargetFinally extends TargetExceptionHandler
    {
        public StringBuilder generateSource(Finally node, StringBuilder builder)
        {
            builder.append("FINALLY").append('\n');
    
            Scope scope = node.getScope();
            
            scope.getTarget().generateSource(scope, builder);
        
            builder.append("END_TRY;").append('\n');
        
            return builder;
        }
    }
    
    public static class TargetCatch extends TargetExceptionHandler
    {
        public StringBuilder generateSource(Catch node, StringBuilder builder)
        {
            builder.append("CATCH ").append('(').append(node.getException().getID()).append(')').append('\n');
        
            Scope scope = node.getScope();
            
            scope.getTarget().generateSource(scope, builder);
        
            return builder;
        }
    }
    
    public static class TargetExceptionHandler extends TargetNode
    {
        
    }
    
    public static class TargetAnnotation extends TargetNode
    {
        public StringBuilder generateHeaderFragment(Annotation node, StringBuilder builder)
        {
            return builder;
        }
        
        public StringBuilder generateSourceFragment(Annotation node, StringBuilder builder)
        {
            return builder;
        }
    }
    
    public static class TargetAbstractMethodDeclaration extends TargetNovaMethodDeclaration
    {
        public StringBuilder generateHeaderFragment(AbstractMethodDeclaration node, StringBuilder builder)
        {
            return super.generateSourcePrototype(node, builder);
        }
        
        public StringBuilder generateSource(AbstractMethodDeclaration node, StringBuilder builder)
        {
            return builder;
        }
        
        public StringBuilder generateInterfaceVTableSource(AbstractMethodDeclaration node, StringBuilder builder)
        {
            return builder.append(0);
        }
    }
    
    public static class TargetVirtualMethodDeclaration extends TargetBodyMethodDeclaration
    {
        public StringBuilder generateSource(VirtualMethodDeclaration node, StringBuilder builder)
        {
            generateSourceSignature(node, builder);
		
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
        
            if (node.getType() != null)
            {
                builder.append("return ");
            }
    
            Parameter ref = node.getOriginalParameterList().getObjectReference();
            
            ref.getTarget().generateSourceFragment(ref, builder).append("->");
        
            builder.append(VTable.IDENTIFIER).append("->");
        
            if (node.getParentClass() instanceof Interface)
            {
                builder.append(InterfaceVTable.IDENTIFIER).append(".");
            }
        
            String call = node.getName() + "(";
        
            for (int i = 0; i < node.getParameterList().getNumVisibleChildren(); i++)
            {
                if (i > 0)
                {
                    call += ", ";
                }
            
                call += node.getParameterList().getVisibleChild(i).getName();
            }
        
            call += ")";
        
            MethodCall output = MethodCall.decodeStatement(node.getScope(), call, node.getLocationIn().asNew(), true, true, node);
        
            generateVirtualMethodName(node, builder);
            output.getArgumentList().getTarget().generateSourceFragment(output.getArgumentList(), builder);
        
            return builder.append(";\n}\n");
        }
        
        public StringBuilder generateSourceName(VirtualMethodDeclaration node, StringBuilder builder, String uniquePrefix)
        {
            return generateVirtualMethodName(node, builder);
        }
    
        /**
         * Get the identifier for the virtual abstract method in the vtable.
         *
         * @return The identifier for the virtual method in the vtable.
         */
        public StringBuilder generateVirtualMethodName(VirtualMethodDeclaration node)
        {
            return generateVirtualMethodName(node, new StringBuilder());
        }
    
        /**
         * Get the identifier for the virtual abstract method in the vtable.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The identifier for the virtual method in the vtable.
         */
        public StringBuilder generateVirtualMethodName(VirtualMethodDeclaration node, StringBuilder builder)
        {
            String prefix = "virtual";
        
            if (node.base instanceof PropertyMethod)
            {
                prefix += "_" + ((PropertyMethod)node.base).getMethodPrefix();
            }
        
            return generateSourceName(node, builder, prefix, true);
        }
    }
    
    public static class TargetBodyMethodDeclaration extends TargetNovaMethodDeclaration
    {
        public StringBuilder generateHeader(BodyMethodDeclaration node, StringBuilder builder)
        {
            if (node.isVisibilityValid())
            {
                if (node.getVisibility() == InstanceDeclaration.PRIVATE)
                {
                    return builder;
                }
            }
        
            generateSourcePrototype(node, builder).append('\n');
        
            return builder;
        }
        
        public StringBuilder generateSource(BodyMethodDeclaration node, StringBuilder builder)
        {
            generateSourceSignature(node, builder).append('\n');
            
            Scope scope = node.getScope();
            
            return scope.getTarget().generateSource(scope, builder);
        }
    }
    
    public static class TargetNovaMethodDeclaration extends TargetMethodDeclaration
    {
        public StringBuilder generateInterfaceVTableSource(NovaMethodDeclaration node, StringBuilder builder)
        {
            NovaMethodDeclaration root = node.getVirtualMethod();//.getRootDeclaration();
            
            NovaParameterList params = root.getParameterList();
            
            builder.append("(").append(generateType(root)).append("(*)(").append(params.getTarget().generateHeader(params)).append("))");
            
            return generateSourceName(node, builder);
        }
    
        public StringBuilder generateClosureContext(NovaMethodDeclaration node, StringBuilder builder)
        {
            return builder.append(NovaMethodDeclaration.NULL_IDENTIFIER);
        }
    
        public StringBuilder generateSourceNativeName(NovaMethodDeclaration node, StringBuilder builder, boolean declaration)
        {
            super.generateSourceNativeName(node, builder, declaration);
    
            if (!declaration && node.isOverloaded())
            {
                for (Parameter param : node.getParameterList())
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

        public StringBuilder generateInterfaceVTableHeader(NovaMethodDeclaration node, StringBuilder builder)
        {
            VirtualMethodDeclaration virtual = node.getVirtualMethod();
            NovaParameterList params = node.getParameterList();
            
            return generateType(node, builder).append(" (*").append(virtual.getTarget().generateVirtualMethodName(virtual)).append(")(").append(params.getTarget().generateHeader(params)).append(");\n");
        }

        /**
         * Generate the identifier that will be used to call the method.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The updated StringBuilder.
         */
        public StringBuilder generateMethodCall(NovaMethodDeclaration node, StringBuilder builder)
        {
            if (node.isVirtual())
            {
                VirtualMethodDeclaration virtual = node.getVirtualMethod();
                
                return virtual.getTarget().generateVirtualMethodName(virtual, builder);
            }

            return super.generateMethodCall(node, builder);
        }
        
        public StringBuilder generateSourceName(NovaMethodDeclaration node, StringBuilder builder, String uniquePrefix)
        {
            return generateSourceName(node, builder, uniquePrefix, true);
        }
        
        public StringBuilder generateSourceName(NovaMethodDeclaration node, StringBuilder builder, String uniquePrefix, boolean outputOverload)
        {
            if (node.overloadID == -1)
            {
                return super.generateSourceName(node, builder, uniquePrefix);
            }

            if (uniquePrefix == null)
            {
                uniquePrefix = "";
            }
            if (outputOverload)
            {
                uniquePrefix += node.overloadID;
            }

            return super.generateSourceName(node, builder, uniquePrefix);
        }
    }
    
    public static class TargetMethodDeclaration extends TargetInstanceDeclaration
    {
        public StringBuilder generateHeader(MethodDeclaration node, StringBuilder builder)
        {
            return generateHeaderFragment(node, builder);
        }
        
        public StringBuilder generateHeaderFragment(MethodDeclaration node, StringBuilder builder)
        {
            return builder;
        }
        
        public StringBuilder generateSource(MethodDeclaration node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }
        
        public StringBuilder generateSourceFragment(MethodDeclaration node, StringBuilder builder)
        {
            return builder;
        }

        public StringBuilder generateSourceNativeName(MethodDeclaration node, StringBuilder builder, boolean declaration)
        {
            if (declaration)
            {
                return generateSourceName(node, builder, "native");
            }

            return builder.append(node.getName());
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
         * In essence, this method is just {@link #generateSourceSignature(MethodDeclaration, StringBuilder)}
         * with a semi-colon attached to the end.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The C prototype for the method header.
         */
        public StringBuilder generateSourcePrototype(MethodDeclaration node, StringBuilder builder)
        {
            return generateSourceSignature(node, builder).append(";");
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
        public StringBuilder generateSourceSignature(MethodDeclaration node, StringBuilder builder)
        {
            generateModifiersSource(node, builder).append(' ');
            generateSourceName(node, builder);
            generateParameterOutput(node, builder);

            return builder;
        }

        public StringBuilder generateParameterOutput(MethodDeclaration node, StringBuilder builder)
        {
            builder.append('(');
    
            ParameterList params = node.getParameterList();
            
            params.getTarget().generateSource(params, builder);

            return builder.append(')');
        }

        /**
         * Generate the identifier that will be used to call the method.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The updated StringBuilder.
         */
        public StringBuilder generateMethodCall(MethodDeclaration node, StringBuilder builder)
        {
            return generateSourceName(node, builder);
        }
    }
    
    public static class TargetInstanceDeclaration extends TargetVariableDeclaration
    {
        public StringBuilder generateHeader(InstanceDeclaration node, StringBuilder builder)
        {
            return generateHeaderFragment(node, builder).append(";\n");
        }
        
        public StringBuilder generateHeaderFragment(InstanceDeclaration node, StringBuilder builder)
        {
            return generateModifiersSource(node, builder).append(' ').append(node.getName());
        }
    }
    
    public static class TargetVariableDeclaration extends TargetIIdentifier
    {
        public StringBuilder generateHeader(VariableDeclaration node, StringBuilder builder)
        {
            return generateSource(node, builder);
        }
        
        public StringBuilder generateSource(VariableDeclaration node, StringBuilder builder)
        {
            return generateDeclarationFragment(node, builder).append(";\n");
        }

        /**
         * Generate a String with the declaration modifiers and the name of
         * the variable declared.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The appended StringBuilder.
         */
        public StringBuilder generateDeclarationFragment(VariableDeclaration node, StringBuilder builder)
        {
            return generateModifiersSource(node, builder).append(' ').append(generateSourceName(node));
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
         * This also checks if the type requires a pointer.
         *
         * @param builder The StringBuilder to append to.
         * @return The appended StringBuilder.
         */
        public StringBuilder generateModifiersSource(VariableDeclaration node, StringBuilder builder)
        {
            if (node.isVolatile())//!(this instanceof Parameter || this instanceof FieldDeclaration))
            {
                builder.append(node.getVolatileText()).append(' ');
            }

            generateType(node, builder);

            return builder;
        }

        public StringBuilder generateDefaultValue(VariableDeclaration node, StringBuilder builder)
        {
            if (node.isPrimitive())
            {
                builder.append(0);
            }
            else
            {
                builder.append(generateTypeCast(node)).append(Value.NULL_IDENTIFIER);
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
        public StringBuilder generateFreeOutput(VariableDeclaration node, StringBuilder builder)
        {
            if (node.isConstant())
            {
                return builder;
            }

            if (node.isPrimitiveType() || node.isExternalType())
            {
                if (!node.isPrimitive())
                {
                    builder.append("NOVA_FREE(");

                    generateUseOutput(node, builder, true).append(");\n");
                }
            }
            else
            {
                Destructor destructor = node.getTypeClass().getDestructor();
                
                destructor.getTarget().generateSourceName(destructor, builder).append('(').append('&');

                generateUseOutput(node, builder, true).append(", ").append(Exception.EXCEPTION_DATA_IDENTIFIER).append(");\n");
            }

            return builder;
        }
    }
    
    public static class TargetIIdentifier extends TargetIdentifier
    {
        
    }
    
    public static class TargetIdentifier extends TargetValue implements TargetAccessible
    {
        public StringBuilder generateSource(Identifier node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder).append(";\n");
        }
    
        public StringBuilder generateSourceFragment(Identifier node, StringBuilder builder)
        {
            if (node.isGenericType() && node.doesAccess())
            {
                Value value = node.getReturnedNode();
                
                value.getTarget().generateTypeCast(value, builder);
            
                builder.append('(');
            }
        
            if (node.isSpecialFragment())
            {
                node.getTarget().generateSpecialFragment(node, builder);
            }
            else
            {
                generateUseOutput(node, builder).append(node.getTarget().generateChildrenSourceFragment(node));
            }
        
            if (node.isGenericType() && node.doesAccess())
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
        public StringBuilder generateUseOutput(Identifier node, StringBuilder builder)
        {
            return generateUseOutput(node, builder, false);
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
        public StringBuilder generateUseOutput(Identifier node, StringBuilder builder, boolean pointer)
        {
            return generateUseOutput(node, builder, pointer, true);
        }
    
        public StringBuilder generateUseOutput(Identifier node, StringBuilder builder, boolean pointer, boolean checkAccesses)
        {
            //		if (!isSpecialFragment())
            //		{
            //			builder.append(generateDataTypeOutput());
            //		}
        
            FieldDeclaration field = null;
        
            Node parent = node.getParent();
        
            if (parent instanceof Array)
            {
                VariableDeclaration n = SyntaxTree.findDeclaration(parent.getParent(), node.getName());
            
                if (n instanceof FieldDeclaration)
                {
                    field = (FieldDeclaration)n;
                }
            }
            else if (node instanceof Variable)
            {
                VariableDeclaration decl = ((Variable)node).getDeclaration();
            
                if (decl instanceof FieldDeclaration)
                {
                    field = (FieldDeclaration)decl;
                }
            }
            else if (node instanceof FieldDeclaration)
            {
                field = (FieldDeclaration)node;
            }
        
            if (field != null && !field.isExternal())
            {
                if (!field.isStatic())
                {
                    Value ref = (Value)node.getReferenceNode();
                
                    if (ref.getTypeClass().isContainingClass(node))
                    {
                        if (!node.isAccessed())
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
                    
                        if (!node.isAccessed())//ref.isContainingClass(this))
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
        
            if (node.isValueReference())
            {
                builder.append("(*");
            
                generateSourcePrefix(node, builder);
            }
        
            generateSourceName(node, builder);
        
            if (node.isValueReference())
            {
                builder.append(')');
            }
        
            generateArrayAccess(node, builder);
        
            return builder;
        }
	
        /*public GenericTypeArgument getGenericTypeArgumentFromParameter(GenericTypeParameter param)
        {
            return getGenericTypeArgumentFromParameter(param.getType());
        }*/
    
        public String getCName(Identifier node)
        {
            return node.getName();
        }
    
        /**
         * Generate a variable name that will be used to keep the variables
         * in their own "namespace" per-say.
         *
         * @return The name of the variable that will be output to the C
         * 		source output.
         */
        public final StringBuilder generateSourceName(Identifier node)
        {
            return generateSourceName(node, new StringBuilder());
        }
    
        /**
         * Generate a variable name that will be used to keep the variables
         * in their own "namespace" per-say.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The name of the variable that will be output to the C
         * 		source output.
         */
        public final StringBuilder generateSourceName(Identifier node, StringBuilder builder)
        {
            return generateSourceName(node, builder, null);
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
        public final StringBuilder generateSourceName(Identifier node, String uniquePrefix)
        {
            return generateSourceName(node, new StringBuilder(), uniquePrefix);
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
        public StringBuilder generateSourceName(Identifier node, StringBuilder builder, String uniquePrefix)
        {
            String name = getCName(node);
        
            if (node.doesForceOriginalName())
            {
                return builder.append(name);
            }
        
            VariableDeclaration existing = null;
        
            if (node.isDeclaration())
            {
                existing = (VariableDeclaration)node;
            }
            else if (node instanceof Variable)
            {
                existing = ((Variable)node).getDeclaration();
            }
            else
            {
                existing = SyntaxTree.findDeclaration(node.getParent(), name, false);
            
                if (existing == null)
                {
                    SyntaxMessage.error("Unable to find declaration for variable '" + name + "'", node);
                }
            }
        
            if (!(existing instanceof LocalDeclaration && existing instanceof Parameter == false))
            {
                ClassDeclaration clazz = existing.getParentClass(true);
            
                clazz.getTarget().generateSourceName(clazz, builder).append('_');
            }
        
            //		if (existing instanceof InstanceDeclaration)
            //		{
            //			InstanceDeclaration node = (InstanceDeclaration)existing;
            //			
            //			if (node.isStatic())
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
        /**
         * If the Value accesses a method call, generate a specialized
         * output.
         *
         * @param builder The StringBuilder to append the data to.
         * @return A specialized String generation.
         */
        default StringBuilder generateSpecialFragment(Accessible node, StringBuilder builder)
        {
            Accessible current = node.getLastAccessedNode();
            
            Value value = (Value)current;
            
            while (!value.isSpecial())
            {
                current = current.getAccessingNode();
            }
            
            value = (Value)current;
            
            return value.getTarget().generateSourceFragment(value, builder);
        }
    
        /**
         * Generate the C output for when this value node is being used
         * as an argument for a method call.
         *
         * @param builder The StringBuilder to append the data to.
         * @param callingMethod The method that is being called by the
         * 		specified Identifier.
         * @return The C output for when this value node is being used
         * 		as an argument for a method call.
         */
        default StringBuilder generateArgumentReference(Accessible node, StringBuilder builder, Identifier callingMethod)
        {
            Value n = (Value)this;
        
            if (n instanceof Identifier)
            {
                ((Identifier)n).getTarget().generateUseOutput((Identifier)n, builder, false, true);
            }
            else
            {
                n.getTarget().generateUseOutput(n, builder);
            }
        
            generateChildrenSourceFragment(node, builder, true, callingMethod, false);
        
            return builder;
        }
    
        /**
         * Generate a String representing the accessed nodes.
         *
         * @return The generated String.
         */
        default StringBuilder generateChildrenSourceFragment(Accessible node)
        {
            return generateChildrenSourceFragment(node, new StringBuilder(), true);
        }
    
        /**
         * Generate a String representing the accessed nodes.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The StringBuilder with the appended generation output.
         */
        default StringBuilder generateChildrenSourceFragment(Accessible node, StringBuilder builder)
        {
            return generateChildrenSourceFragment(node, builder, true);
        }
    
        /**
         * Generate a String representing the accessed nodes.
         *
         * @param reference Whether or not to start the string off with
         * 		a "-&gt;" reference operator.
         * @return The generated String.
         */
        default StringBuilder generateChildrenSourceFragment(Accessible node, boolean reference)
        {
            return generateChildrenSourceFragment(node, new StringBuilder(), reference, null);
        }
    
        /**
         * Generate a String representing the accessed nodes.
         *
         * @param builder The StringBuilder to append the data to.
         * @param reference Whether or not to start the string off with
         * 		a "-&gt;" reference operator.
         * @return The StringBuilder with the appended generation output.
         */
        default StringBuilder generateChildrenSourceFragment(Accessible node, StringBuilder builder, boolean reference)
        {
            return generateChildrenSourceFragment(node, builder, reference, null);
        }
    
        /**
         * Generate a String representing the accessed nodes.
         *
         * @param reference Whether or not to start the string off with
         * 		a "-&gt;" reference operator.
         * @param stopBefore The Identifier to stop the generation before.
         * @return The generated String.
         */
        default StringBuilder generateChildrenSourceFragment(Accessible node, boolean reference, Identifier stopBefore)
        {
            return generateChildrenSourceFragment(node, new StringBuilder(), reference, stopBefore);
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
        default StringBuilder generateChildrenSourceFragment(Accessible node, StringBuilder builder, boolean reference, Identifier stopBefore)
        {
            return generateChildrenSourceFragment(node, builder, reference, stopBefore, true);
        }
    
        default StringBuilder generateChildrenSourceFragment(Accessible node, StringBuilder builder, boolean reference, Identifier stopBefore, boolean checkAccesses)
        {
            Identifier child = node.getAccessedNode();
        
            if (child == null)
            {
                return builder;
            }
        
            StringBuilder output = child.getTarget().generateChildSourceFragment(child, reference, stopBefore, checkAccesses);
        
            if (output.length() > 0 && reference)
            {
                builder.append("->");
            }
        
            return builder.append(output);
        }
    
        /**
         * Generate the source fragment for the specified node.
         *
         * @param reference Whether or not to prepend the "->" operator at
         * 		the beginning of the generated output.
         * @param stopBefore The Identifier to stop the generation before.
         * @return The StringBuilder with the appended generation output.
         */
        default StringBuilder generateChildSourceFragment(Accessible node, boolean reference, Identifier stopBefore)
        {
            return generateChildSourceFragment(node, reference, stopBefore, true);
        }
    
        default StringBuilder generateChildSourceFragment(Accessible node, boolean reference, Identifier stopBefore, boolean checkAccesses)
        {
            Value n = (Value)this;
        
            StringBuilder builder = new StringBuilder();
        
            // If generating the output for the use of an argument.
            if (stopBefore != null)
            {
                if (this == stopBefore)//instanceof MethodCall || this instanceof Instantiation)
                {
                    return builder;
                }
            
                StringBuilder use = null;
            
                if (n instanceof Identifier)
                {
                    use = ((Identifier)n).getTarget().generateUseOutput((Identifier)n, builder, false, checkAccesses);
                }
                else
                {
                    use = n.getTarget().generateUseOutput(n, builder);
                }
            
                return use.append(generateChildrenSourceFragment(node, true, stopBefore));
            }
        
            if (n instanceof Identifier)
            {
                Identifier id = (Identifier)n;
            
                if (id.isSpecialFragment())
                {
                    id.getTarget().generateSpecialFragment(id, builder);
                }
            }
        
            return n.getTarget().generateSourceFragment(n, builder);
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
        default StringBuilder generateSourceUntil(Accessible node, String delimiter, Identifier stopAt)
        {
            return generateSourceUntil(node, new StringBuilder(), delimiter, stopAt);
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
        default StringBuilder generateSourceUntil(Accessible node, StringBuilder builder, String delimiter, Identifier stopAt)
        {
            Accessible current = node;
        
            while (current != null && current != stopAt)
            {
                ((Value)current).getTarget().generateUseOutput((Value)current, builder).append(delimiter);
            
                current = current.getAccessedNode();
            }
        
            return builder;
        }
    }
    
    public static class TargetValue extends TargetNode
    {
        public StringBuilder generateHeader(Value node, StringBuilder builder)
        {
            return generateHeaderFragment(node, builder);
        }
        
        public StringBuilder generateHeaderFragment(Value node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }
        
        public StringBuilder generateSource(Value node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }
        
        public StringBuilder generateSourceFragment(Value node, StringBuilder builder)
        {
            return generateType(node, builder);
        }

        public StringBuilder generateSourcePrefix(Value node, StringBuilder builder)
        {
            return builder;
        }

        /**
         * Generate the C null representation for the given value type.
         *
         * @return The generated null output.
         */
        public final StringBuilder generateNullOutput(Value node)
        {
            return generateNullOutput(node, new StringBuilder());
        }

        /**
         * Generate the C null representation for the given value type.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The generated null output.
         */
        public StringBuilder generateNullOutput(Value node, StringBuilder builder)
        {
            return generateTypeCast(node, builder).append(Value.NULL_IDENTIFIER);
        }

        public StringBuilder generateArgumentOutput(Value node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }

        public final StringBuilder generateTypeClassName(Value node)
        {
            return generateTypeClassName(node, new StringBuilder());
        }

        public StringBuilder generateTypeClassName(Value node, StringBuilder builder)
        {
            String type = node.getType();

            if (node.isGenericType())
            {
                type = node.getGenericReturnType();
            }

            if (node.isExternalType() || SyntaxUtils.isExternalPrimitiveType(type))
            {
                builder.append(type);
            }
            else
            {
                FileDeclaration file = node.getReferenceFile();//getFileDeclaration();
			
                /*if (this instanceof Identifier && !isGenericType())
                {
                    file = ((Identifier)this).getDeclaringClass().getFileDeclaration();
                }*/

                ClassDeclaration clazz = SyntaxUtils.getImportedClass(file, type);

                if (clazz != null)
                {
                    clazz.getTarget().generateSourceName(clazz, builder);
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
        public final StringBuilder generateType(Value node)
        {
            return generateType(node, new StringBuilder());
        }

        /**
         * Generate the C syntax for the type of the specified Value.
         *
         * @param builder The StringBuider to append the data to.
         * @return The C syntax for the type of the Value.
         */
        public final StringBuilder generateType(Value node, StringBuilder builder)
        {
            return generateType(node, builder, true);
        }

        /**
         * Generate the C syntax for the type of the specified Value.
         *
         * @param builder The StringBuider to append the data to.
         * @param checkArray Whether or not to check if the type is an array.
         * @return The C syntax for the type of the Value.
         */
        public final StringBuilder generateType(Value node, StringBuilder builder, boolean checkArray)
        {
            return generateType(node, builder, checkArray, true);
        }

        public StringBuilder generateType(Value node, StringBuilder builder, boolean checkArray, boolean checkValueReference)
        {
            generateTypeName(node, builder);

            if (node.isReference())
            {
                builder.append('&');
            }
            else if (node.isPointer())
            {
                builder.append('*');
            }
            else if (node.isDoublePointer())
            {
                builder.append("**");
            }
            if (checkValueReference && node.isValueReference())
            {
                builder.append('*');
            }
            if (checkArray && node.isPrimitiveArray())
            {
                builder.append(node.generateArrayText());
            }

            return builder;
        }

        public StringBuilder generateTypeName(Value node)
        {
            return generateTypeName(node, new StringBuilder());
        }

        public StringBuilder generateTypeName(Value node, StringBuilder builder)
        {
            String type = node.getType();

            if (node.isGenericType())
            {
                type = node.getGenericReturnType();
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
            else if (SyntaxUtils.isPrimitiveType(type) && (node.getDataType() == Value.VALUE || (node.isReturnParameter() && node.getDataType() == Value.POINTER)))
            {
                builder.append(SyntaxUtils.getPrimitiveExternalType(type));
            }
            else
            {
                generateTypeClassName(node, builder);
            }

            return builder;
        }

        /**
         * Generate a String representing a type cast for the specified Value
         * in C syntax.
         *
         * @return The StringBuilder with the appended data.
         */
        public final StringBuilder generateTypeCast(Value node)
        {
            return generateTypeCast(node, new StringBuilder());
        }

        /**
         * Generate a String representing a type cast for the specified Value
         * in C syntax.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The StringBuilder with the appended data.
         */
        public final StringBuilder generateTypeCast(Value node, StringBuilder builder)
        {
            return generateTypeCast(node, builder, true, true);
        }

        public final StringBuilder generateTypeCast(Value node, StringBuilder builder, boolean checkArray, boolean checkValueReference)
        {
            return builder.append('(').append(generateType(node, new StringBuilder(), checkArray, checkValueReference)).append(')').append(generatePointerToValueConversion(node));
        }

        public StringBuilder generatePointerToValueConversion(Value node)
        {
            return generatePointerToValueConversion(node, new StringBuilder());
        }

        public StringBuilder generatePointerToValueConversion(Value node, StringBuilder builder)
        {
            return generatePointerToValueConversion(node, builder, node);
        }

        public StringBuilder generatePointerToValueConversion(Value node, Value required)
        {
            return generatePointerToValueConversion(node, new StringBuilder(), required);
        }

        public StringBuilder generatePointerToValueConversion(Value node, StringBuilder builder, Value required)
        {
            boolean ptr = false;

            if (/*isGenericType() && */this instanceof Accessible)
            {
                Accessible ref = ((Accessible)this).getReferenceNode();

                ptr = ref != null && node.getArrayDimensions() == 0 && (required.isOriginallyGenericType() || node.isOriginallyGenericType()) && ref.toValue().isPrimitiveGenericTypeWrapper();
            }
            else
            {
                Node base = node.getBaseNode();

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
        public final StringBuilder generateUseOutput(Value node)
        {
            return generateUseOutput(node, new StringBuilder());
        }

        /**
         * Generate the representation of when the value node is being used
         * in action.
         *
         * @param builder The StringBuilder to append the data to.
         * @return What the method call looks like when it is being used in
         * 		action
         */
        public StringBuilder generateUseOutput(Value node, StringBuilder builder)
        {
            return generateType(node, builder);
        }

        public StringBuilder generateArrayAccess(Value node)
        {
            return generateArrayAccess(node, new StringBuilder());
        }

        public StringBuilder generateArrayAccess(Value node, StringBuilder builder)
        {
            if (node.arrayAccess != null)
            {
                return node.arrayAccess.getTarget().generateSourceFragment(node.arrayAccess, builder);
            }

            return builder;
        }
    }
    
    public static class TargetNode
    {
        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language header file syntax.
         *
         * @return The C header file syntax representation of the Node.
         */
        public final StringBuilder generateHeader(Node node)
        {
            return generateHeader(node, new StringBuilder());
        }

        /**
         * Method that each Node can override. Returns a String that
         * translates the data that is stored in the Node to the C
         * programming language header file 'fragment' syntax.
         *
         * @return The C header syntax representation of the Node.
         */
        public final StringBuilder generateHeaderFragment(Node node)
        {
            return generateHeaderFragment(node, new StringBuilder());
        }

        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language source file syntax.
         *
         * @return The C source syntax representation of the Node.
         */
        public final StringBuilder generateSource(Node node)
        {
            return generateSource(node, new StringBuilder());
        }

        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language source file 'fragment' syntax.
         *
         * @return The C source syntax representation of the Node.
         */
        public final StringBuilder generateSourceFragment(Node node)
        {
            return generateSourceFragment(node, new StringBuilder());
        }

        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language header file syntax.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The C header file syntax representation of the Node.
         */
        public StringBuilder generateHeader(Node node, StringBuilder builder)
        {
            return generateHeaderFragment(node, builder).append('\n');
            //throw new UnimplementedOperationException("The C Header implementation for " + this.getClass().getName() + " has not been implemented yet.");
        }

        /**
         * Method that each Node can override. Returns a String that
         * translates the data that is stored in the Node to the C
         * programming language header file 'fragment' syntax.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The C header syntax representation of the Node.
         */
        public StringBuilder generateHeaderFragment(Node node, StringBuilder builder)
        {
            throw new UnimplementedOperationException("The C Header fragment implementation for " + this.getClass().getName() + " has not been implemented yet.");
        }

        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language source file syntax.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The C source syntax representation of the Node.
         */
        public StringBuilder generateSource(Node node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder).append('\n');
            //throw new UnimplementedOperationException("The C Source implementation for " + this.getClass().getName() + " has not been implemented yet.");
        }

        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language source file 'fragment' syntax.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The C source syntax representation of the Node.
         */
        public StringBuilder generateSourceFragment(Node node, StringBuilder builder)
        {
            throw new UnimplementedOperationException("The C Source fragment implementation for " + this.getClass().getName() + " has not been implemented yet.");
        }
    }
}