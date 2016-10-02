package net.fathomsoft.nova;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.error.UnimplementedOperationException;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.variables.Array;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.SyntaxUtils;

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
    public static final TargetNode TARGET_NODE = new TargetNode();
    
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
        
            if (node.getType() != null)
            {
                builder.append("return ");
            }
        
            super.getParameterList().getObjectReference().generateSourceFragment(builder).append("->");
        
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
            output.getArgumentList().generateSourceFragment(builder);
        
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
        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeader(StringBuilder)
         */
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
    
        /**
         * @see net.fathomsoft.nova.tree.Node#generateSource(StringBuilder)
         */
        public StringBuilder generateSource(BodyMethodDeclaration node, StringBuilder builder)
        {
            generateSourceSignature(node, builder).append('\n');
        
            return node.getScope().generateSource(node, builder);
        }
    }
    
    public static class TargetNovaMethodDeclaration extends TargetMethodDeclaration
    {
        public StringBuilder generateInterfaceVTableSource(NovaMethodDeclaration node, StringBuilder builder)
        {
            NovaMethodDeclaration root = node.getVirtualMethod();//.getRootDeclaration();
    
            builder.append("(").append(generateType(root)).append("(*)(").append(root.getParameterList().generateHeader()).append("))");
            return generateSourceName(node, builder, node);
        }
    
        public StringBuilder generatelosureContext(NovaMethodDeclaration node, StringBuilder builder)
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
            return generateType(node, builder).append(" (*").append(node.getVirtualMethod().generateVirtualMethodName()).append(")(").append(node.getParameterList().generateHeader()).append(");\n");
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
                return node.getVirtualMethod().generateVirtualMethodName(node, builder);
            }

            return super.generateMethodCall(node, builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Identifier#generateSourceName(java.lang.StringBuilder, String)
         */
        public StringBuilder generateSourceName(NovaMethodDeclaration node, StringBuilder builder, String uniquePrefix)
        {
            return generateSourceName(node, builder, uniquePrefix, true);
        }

        /**
         * @see net.fathomsoft.nova.tree.Identifier#generateSourceName(java.lang.StringBuilder, String)
         */
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
        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeader(StringBuilder)
         */
        public StringBuilder generateHeader(MethodDeclaration node, StringBuilder builder)
        {
            return generateHeaderFragment(node, builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeaderFragment(StringBuilder)
         */
        public StringBuilder generateHeaderFragment(MethodDeclaration node, StringBuilder builder)
        {
            return builder;
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateSource(StringBuilder)
         */
        public StringBuilder generateSource(MethodDeclaration node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateSourceFragment(StringBuilder)
         */
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
         * In essence, this method is just {@link #generateSourceSignature(StringBuilder)}
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
    
            node.getParameterList().generateSource(node, builder);

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
        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeader(StringBuilder)
         */
        public StringBuilder generateHeader(InstanceDeclaration node, StringBuilder builder)
        {
            return generateHeaderFragment(node, builder).append(";\n");
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeaderFragment(StringBuilder)
         */
        public StringBuilder generateHeaderFragment(InstanceDeclaration node, StringBuilder builder)
        {
            return generateModifiersSource(node, builder).append(' ').append(node.getName());
        }
    }
    
    public static class TargetVariableDeclaration extends TargetIIdentifier
    {
        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeader(StringBuilder)
         */
        public StringBuilder generateHeader(VariableDeclaration node, StringBuilder builder)
        {
            return generateSource(node, builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateSource(StringBuilder)
         */
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
                node.getTypeClass().getDestructor().generateSourceName(node, builder).append('(').append('&');

                generateUseOutput(node, builder, true).append(", ").append(Exception.EXCEPTION_DATA_IDENTIFIER).append(");\n");
            }

            return builder;
        }
    }
    
    public static class TargetIIdentifier extends TargetIdentifier
    {
        /**
         * @see net.fathomsoft.nova.tree.Node#generateSource(StringBuilder)
         */
        public StringBuilder generateSource(IIdentifier node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder).append(";\n");
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateSourceFragment(StringBuilder)
         */
        public StringBuilder generateSourceFragment(IIdentifier node, StringBuilder builder)
        {
            if (node.isGenericType() && node.doesAccess())
            {
                node.getReturnedNode().generateTypeCast(node, builder);

                builder.append('(');
            }

            if (node.isSpecialFragment())
            {
                node.generateSpecialFragment(node, builder);
            }
            else
            {
                generateUseOutput(node, builder).append(node.generatehildrenCSourceFragment());
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
        public StringBuilder generateUseOutput(IIdentifier node, StringBuilder builder)
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
        public StringBuilder generateUseOutput(IIdentifier node, StringBuilder builder, boolean pointer)
        {
            return generateUseOutput(node, builder, pointer, true);
        }

        public StringBuilder generateUseOutput(IIdentifier node, StringBuilder builder, boolean pointer, boolean checkAccesses)
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

        public String getCName(IIdentifier node)
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
        public final StringBuilder generateSourceName(IIdentifier node)
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
        public final StringBuilder generateSourceName(IIdentifier node, StringBuilder builder)
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
        public final StringBuilder generateSourceName(IIdentifier node, String uniquePrefix)
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
        public StringBuilder generateSourceName(IIdentifier node, StringBuilder builder, String uniquePrefix)
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
                existing.getParentClass(true).generateSourceName(node, builder).append('_');
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
    
    public static class TargetIdentifier extends TargetValue
    {
        
    }
    
    public static class TargetValue extends TargetNode
    {
        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeader(StringBuilder)
         */
        public StringBuilder generateHeader(Value node, StringBuilder builder)
        {
            return generateHeaderFragment(node, builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeaderFragment(StringBuilder)
         */
        public StringBuilder generateHeaderFragment(Value node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateSource(StringBuilder)
         */
        public StringBuilder generateSource(Value node, StringBuilder builder)
        {
            return generateSourceFragment(node, builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateSource(StringBuilder)
         */
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
                    clazz.generateSourceName(node, builder);
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
            return builder.append('(').append(generateType(node, new StringBuilder(), checkArray, checkValueReference)).append(')').append(generatePointerToValueConversion(this));
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
                return node.arrayAccess.generateSourceFragment(node, builder);
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