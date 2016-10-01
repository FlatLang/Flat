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
        public StringBuilder generateHeaderFragment(StringBuilder builder, AbstractMethodDeclaration node)
        {
            return super.generateSourcePrototype(builder, node);
        }
        
        public StringBuilder generateSource(StringBuilder builder, AbstractMethodDeclaration node)
        {
            return builder;
        }
        
        public StringBuilder generateInterfaceVTableSource(StringBuilder builder, AbstractMethodDeclaration node)
        {
            return builder.append(0);
        }
    }  
    
    public static class TargetNovaMethodDeclaration extends TargetMethodDeclaration
    {
        public StringBuilder generateInterfaceVTableSource(StringBuilder builder, NovaMethodDeclaration node)
        {
            NovaMethodDeclaration root = node.getVirtualMethod();//.getRootDeclaration();
    
            builder.append("(").append(generateType(root)).append("(*)(").append(root.getParameterList().generateHeader()).append("))");
            return generateSourceName(builder, node);
        }
    
        public StringBuilder generatelosureContext(StringBuilder builder, NovaMethodDeclaration node)
        {
            return builder.append(NovaMethodDeclaration.NULL_IDENTIFIER);
        }
    
        public StringBuilder generateSourceNativeName(StringBuilder builder, NovaMethodDeclaration node, boolean declaration)
        {
            super.generateSourceNativeName(builder, declaration);
    
            if (!declaration && isOverloaded())
            {
                for (Parameter param : getParameterList())
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

        public StringBuilder generateInterfaceVTableHeader(StringBuilder builder, NovaMethodDeclaration node)
        {
            return generateType(builder).append(" (*").append(getVirtualMethod().generateVirtualMethodName()).append(")(").append(getParameterList().generateHeader()).append(");\n");
        }

        /**
         * Generate the identifier that will be used to call the method.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The updated StringBuilder.
         */
        public StringBuilder generateMethodCall(StringBuilder builder, NovaMethodDeclaration node)
        {
            if (isVirtual())
            {
                return getVirtualMethod().generateVirtualMethodName(builder);
            }

            return super.generateMethodCall(builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Identifier#generateSourceName(java.lang.StringBuilder, String)
         */
        @Override
        public StringBuilder generateSourceName(StringBuilder builder, NovaMethodDeclaration node, String uniquePrefix)
        {
            return generateSourceName(builder, uniquePrefix, true);
        }

        /**
         * @see net.fathomsoft.nova.tree.Identifier#generateSourceName(java.lang.StringBuilder, String)
         */
        public StringBuilder generateSourceName(StringBuilder builder, NovaMethodDeclaration node, String uniquePrefix, boolean outputOverload)
        {
            if (overloadID == -1)
            {
                return super.generateSourceName(builder, uniquePrefix);
            }

            if (uniquePrefix == null)
            {
                uniquePrefix = "";
            }
            if (outputOverload)
            {
                uniquePrefix += overloadID;
            }

            return super.generateSourceName(builder, uniquePrefix);
        }
    }
    
    public static class TargetMethodDeclaration extends TargetInstanceDeclaration
    {
        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeader(StringBuilder)
         */
        @Override
        public StringBuilder generateHeader(StringBuilder builder, MethodDeclaration node)
        {
            return generateHeaderFragment(builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeaderFragment(StringBuilder)
         */
        @Override
        public StringBuilder generateHeaderFragment(StringBuilder builder, MethodDeclaration node)
        {
            return builder;
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateSource(StringBuilder)
         */
        @Override
        public StringBuilder generateSource(StringBuilder builder, MethodDeclaration node)
        {
            return generateSourceFragment(builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateSourceFragment(StringBuilder)
         */
        @Override
        public StringBuilder generateSourceFragment(StringBuilder builder, MethodDeclaration node)
        {
            return builder;
        }

        public StringBuilder generateSourceNativeName(StringBuilder builder, MethodDeclaration node, boolean declaration)
        {
            if (declaration)
            {
                return generateSourceName(builder, "native");
            }

            return builder.append(getName());
////		String location = getFileDeclaration().getPackage().getLocation().replace('/', '_');
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
        public StringBuilder generateSourcePrototype(StringBuilder builder, MethodDeclaration node)
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
        public StringBuilder generateSourceSignature(StringBuilder builder, MethodDeclaration node)
        {
            generateModifiersSource(builder).append(' ');
            generateSourceName(builder);
            generateParameterOutput(builder);

            return builder;
        }

        public StringBuilder generateParameterOutput(StringBuilder builder, MethodDeclaration node)
        {
            builder.append('(');

            getParameterList().generateSource(builder);

            return builder.append(')');
        }

        /**
         * Generate the identifier that will be used to call the method.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The updated StringBuilder.
         */
        public StringBuilder generateMethodCall(StringBuilder builder, MethodDeclaration node)
        {
            return generateSourceName(builder);
        }
    }
    
    public static class TargetInstanceDeclaration extends TargetVariableDeclaration
    {
        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeader(StringBuilder)
         */
        @Override
        public StringBuilder generateHeader(StringBuilder builder, VariableDeclaration node)
        {
            return generateHeaderFragment(builder).append(";\n");
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeaderFragment(StringBuilder)
         */
        @Override
        public StringBuilder generateHeaderFragment(StringBuilder builder, VariableDeclaration node)
        {
            return generateModifiersSource(builder).append(' ').append(getName());
        }
    }
    
    public static class TargetVariableDeclaration extends TargetIIdentifier
    {
        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeader(StringBuilder)
         */
        @Override
        public StringBuilder generateHeader(StringBuilder builder, IIdentifier node)
        {
            return generateSource(builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateSource(StringBuilder)
         */
        @Override
        public StringBuilder generateSource(StringBuilder builder, IIdentifier node)
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
        public StringBuilder generateDeclarationFragment(StringBuilder builder, IIdentifier node)
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
         * This also checks if the type requires a pointer.
         *
         * @param builder The StringBuilder to append to.
         * @return The appended StringBuilder.
         */
        public StringBuilder generateModifiersSource(StringBuilder builder, IIdentifier node)
        {
            if (isVolatile())//!(this instanceof Parameter || this instanceof FieldDeclaration))
            {
                builder.append(getVolatileText()).append(' ');
            }

            generateType(builder);

            return builder;
        }

        public StringBuilder generateDefaultValue(StringBuilder builder, IIdentifier node)
        {
            if (isPrimitive())
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
        public StringBuilder generateFreeOutput(StringBuilder builder, IIdentifier node)
        {
            if (isConstant())
            {
                return builder;
            }

            if (isPrimitiveType() || isExternalType())
            {
                if (!isPrimitive())
                {
                    builder.append("NOVA_FREE(");

                    generateUseOutput(builder, true).append(");\n");
                }
            }
            else
            {
                getTypeClass().getDestructor().generateSourceName(builder).append('(').append('&');

                generateUseOutput(builder, true).append(", ").append(Exception.EXCEPTION_DATA_IDENTIFIER).append(");\n");
            }

            return builder;
        }
    }
    
    public static class TargetIIdentifier extends TargetIdentifier
    {
        /**
         * @see net.fathomsoft.nova.tree.Node#generateSource(StringBuilder)
         */
        @Override
        public StringBuilder generateSource(StringBuilder builder, Identifier node)
        {
            return generateSourceFragment(builder).append(";\n");
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateSourceFragment(StringBuilder)
         */
        @Override
        public StringBuilder generateSourceFragment(StringBuilder builder, Identifier node)
        {
            if (isGenericType() && doesAccess())
            {
                getReturnedNode().generateTypeCast(builder);

                builder.append('(');
            }

            if (isSpecialFragment())
            {
                generateSpecialFragment(builder);
            }
            else
            {
                generateUseOutput(builder).append(generatehildrenCSourceFragment());
            }

            if (isGenericType() && doesAccess())
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
        public StringBuilder generateUseOutput(StringBuilder builder, Identifier node)
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
        public StringBuilder generateUseOutput(StringBuilder builder, Identifier node, boolean pointer)
        {
            return generateUseOutput(builder, pointer, true);
        }

        public StringBuilder generateUseOutput(StringBuilder builder, Identifier node, boolean pointer, boolean checkAccesses)
        {
//		if (!isSpecialFragment())
//		{
//			builder.append(generateDataTypeOutput());
//		}

            FieldDeclaration field = null;

            Node parent = getParent();

            if (parent instanceof Array)
            {
                VariableDeclaration node = SyntaxTree.findDeclaration(parent.getParent(), getName());

                if (node instanceof FieldDeclaration)
                {
                    field = (FieldDeclaration)node;
                }
            }
            else if (this instanceof Variable)
            {
                VariableDeclaration decl = ((Variable)this).getDeclaration();

                if (decl instanceof FieldDeclaration)
                {
                    field = (FieldDeclaration)decl;
                }
            }
            else if (this instanceof FieldDeclaration)
            {
                field = (FieldDeclaration)this;
            }

            if (field != null && !field.isExternal())
            {
                if (!field.isStatic())
                {
                    Value ref = (Value)getReferenceNode();

                    if (ref.getTypeClass().isContainingClass(this))
                    {
                        if (!isAccessed())
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

                        if (!isAccessed())//ref.isContainingClass(this))
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

            if (isValueReference())
            {
                builder.append("(*");

                generateSourcePrefix(builder);
            }

            generateSourceName(builder);

            if (isValueReference())
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

        public String getCName(Identifier node)
        {
            return getName();
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
        public final StringBuilder generateSourceName(StringBuilder builder, Identifier node)
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
        public final StringBuilder generateSourceName(Identifier node, String uniquePrefix)
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
        public StringBuilder generateSourceName(StringBuilder builder, Identifier node, String uniquePrefix)
        {
            String name = getCName();

            if (doesForceOriginalName())
            {
                return builder.append(name);
            }

            VariableDeclaration existing = null;

            if (isDeclaration())
            {
                existing = (VariableDeclaration)this;
            }
            else if (this instanceof Variable)
            {
                existing = ((Variable)this).getDeclaration();
            }
            else
            {
                existing = SyntaxTree.findDeclaration(getParent(), name, false);

                if (existing == null)
                {
                    SyntaxMessage.error("Unable to find declaration for variable '" + name + "'", this);
                }
            }

            if (!(existing instanceof LocalDeclaration && existing instanceof Parameter == false))
            {
                existing.getParentClass(true).generateSourceName(builder).append('_');
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
        @Override
        public StringBuilder generateHeader(StringBuilder builder, Value node)
        {
            return generateHeaderFragment(builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateHeaderFragment(StringBuilder)
         */
        @Override
        public StringBuilder generateHeaderFragment(StringBuilder builder, Value node)
        {
            return generateSourceFragment(builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateSource(StringBuilder)
         */
        @Override
        public StringBuilder generateSource(StringBuilder builder, Value node)
        {
            return generateSourceFragment(builder);
        }

        /**
         * @see net.fathomsoft.nova.tree.Node#generateSource(StringBuilder)
         */
        @Override
        public StringBuilder generateSourceFragment(StringBuilder builder, Value node)
        {
            return generateType(builder);
        }

        public StringBuilder generateSourcePrefix(StringBuilder builder, Value node)
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
            return generateNullOutput(new StringBuilder());
        }

        /**
         * Generate the C null representation for the given value type.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The generated null output.
         */
        public StringBuilder generateNullOutput(StringBuilder builder, Value node)
        {
            return generateTypeCast(builder).append(NULL_IDENTIFIER);
        }

        public StringBuilder generateArgumentOutput(StringBuilder builder, Value node)
        {
            return generateSourceFragment(builder);
        }

        public final StringBuilder generateTypeClassName(Value node)
        {
            return generateTypeClassName(new StringBuilder());
        }

        public StringBuilder generateTypeClassName(StringBuilder builder, Value node)
        {
            String type = getType();

            if (isGenericType())
            {
                type = getGenericReturnType();
            }

            if (isExternalType() || SyntaxUtils.isExternalPrimitiveType(type))
            {
                builder.append(type);
            }
            else
            {
                FileDeclaration file = getReferenceFile();//getFileDeclaration();
			
			/*if (this instanceof Identifier && !isGenericType())
			{
				file = ((Identifier)this).getDeclaringClass().getFileDeclaration();
			}*/

                ClassDeclaration clazz = SyntaxUtils.getImportedClass(file, type);

                if (clazz != null)
                {
                    clazz.generateSourceName(builder);
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
            return generateType(new StringBuilder());
        }

        /**
         * Generate the C syntax for the type of the specified Value.
         *
         * @param builder The StringBuider to append the data to.
         * @return The C syntax for the type of the Value.
         */
        public final StringBuilder generateType(StringBuilder builder, Value node)
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
        public final StringBuilder generateType(StringBuilder builder, Value node, boolean checkArray)
        {
            return generateType(builder, checkArray, true);
        }

        public StringBuilder generateType(StringBuilder builder, Value node, boolean checkArray, boolean checkValueReference)
        {
            generateTypeName(builder);

            if (isReference())
            {
                builder.append('&');
            }
            else if (isPointer())
            {
                builder.append('*');
            }
            else if (isDoublePointer())
            {
                builder.append("**");
            }
            if (checkValueReference && isValueReference())
            {
                builder.append('*');
            }
            if (checkArray && isPrimitiveArray())
            {
                builder.append(generateArrayText());
            }

            return builder;
        }

        public StringBuilder generateTypeName(Value node)
        {
            return generateTypeName(new StringBuilder());
        }

        public StringBuilder generateTypeName(StringBuilder builder, Value node)
        {
            String type = getType();

            if (isGenericType())
            {
                type = getGenericReturnType();
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
            else if (SyntaxUtils.isPrimitiveType(type) && (getDataType() == VALUE || (isReturnParameter() && getDataType() == POINTER)))
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
        public final StringBuilder generateTypeCast(Value node)
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
        public final StringBuilder generateTypeCast(StringBuilder builder, Value node)
        {
            return generateTypeCast(builder, true, true);
        }

        public final StringBuilder generateTypeCast(StringBuilder builder, Value node, boolean checkArray, boolean checkValueReference)
        {
            return builder.append('(').append(generateType(new StringBuilder(), checkArray, checkValueReference)).append(')').append(generatePointerToValueConversion(this));
        }

        public StringBuilder generatePointerToValueConversion(Value node)
        {
            return generatePointerToValueConversion(new StringBuilder());
        }

        public StringBuilder generatePointerToValueConversion(StringBuilder builder, Value node)
        {
            return generatePointerToValueConversion(builder, this);
        }

        public StringBuilder generatePointerToValueConversion(Value node, Value required)
        {
            return generatePointerToValueConversion(new StringBuilder(), required);
        }

        public StringBuilder generatePointerToValueConversion(StringBuilder builder, Value node, Value required)
        {
            boolean ptr = false;

            if (/*isGenericType() && */this instanceof Accessible)
            {
                Accessible ref = ((Accessible)this).getReferenceNode();

                ptr = ref != null && getArrayDimensions() == 0 && (required.isOriginallyGenericType() || isOriginallyGenericType()) && ref.toValue().isPrimitiveGenericTypeWrapper();
            }
            else
            {
                Node base = getBaseNode();

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
        public StringBuilder generateUseOutput(StringBuilder builder, Value node)
        {
            return generateType(builder);
        }

        public StringBuilder generateArrayAccess(Value node)
        {
            return generateArrayAccess(new StringBuilder());
        }

        public StringBuilder generateArrayAccess(StringBuilder builder, Value node)
        {
            if (arrayAccess != null)
            {
                return arrayAccess.generateSourceFragment(builder);
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
            return generateHeader(new StringBuilder());
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
            return generateHeaderFragment(new StringBuilder());
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
            return generateSource(new StringBuilder());
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
            return generateSourceFragment(new StringBuilder());
        }

        /**
         * Method that each Node overrides. Returns a String that translates
         * the data that is stored in the Node to the C programming
         * language header file syntax.
         *
         * @param builder The StringBuilder to append the data to.
         * @return The C header file syntax representation of the Node.
         */
        public StringBuilder generateHeader(StringBuilder builder, Node node)
        {
            return generateHeaderFragment(builder).append('\n');
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
        public StringBuilder generateHeaderFragment(StringBuilder builder, Node node)
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
        public StringBuilder generateSource(StringBuilder builder, Node node)
        {
            return generateSourceFragment(builder).append('\n');
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
        public StringBuilder generateSourceFragment(StringBuilder builder, Node node)
        {
            throw new UnimplementedOperationException("The C Source fragment implementation for " + this.getClass().getName() + " has not been implemented yet.");
        }
    }
}