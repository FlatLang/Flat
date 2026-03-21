package nova.c.nodewriters;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.Package;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.tree.generics.GenericTypeParameterList;
import net.fathomsoft.nova.tree.variables.FieldList;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;
import net.fathomsoft.nova.util.Location;

import java.io.PrintWriter;
import java.util.function.Consumer;

public abstract class ClassDeclarationWriter extends InstanceDeclarationWriter
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
				
				return getWriter(ref).generateNullOutput(builder);
			}
			else if (declaration instanceof ClosureDeclaration)
			{
				ClosureDeclaration closure = (ClosureDeclaration)declaration;
				
				return getWriter(closure).generateSourceName(builder, "ref");
			}
		}
		
		return super.generateArgumentReference(builder, callingMethod);
	}
	
	public StringBuilder generateHeaderNativeInterface(StringBuilder builder)
	{
		if (node().isPrimitiveOverload())
		{
			return builder;
		}
		
		MethodDeclaration[] methods = node().getVisibleNativeMethods();
		
            /*if (methods.length <= 0)
            {
                return builder;
            }*/
		
		String name = generateSourceName("native").toString();
		
		for (MethodDeclaration method : methods)
		{
//			if (method instanceof NovaMethodDeclaration == false || !((NovaMethodDeclaration)method).isPrimitiveOverload())
			{
				builder.append("typedef " + getWriter(method).generateType() + " (*");
				
				getWriter(method).generateSourceNativeName(builder, true).append(")(");
				
				ParameterList params = method.getParameterList();
				
				getWriter(params).generateHeader(builder).append(");\n");
			}
		}
		
		builder.append("\ntypedef struct " + name + "\n");
		builder.append("{\n");
		
		for (MethodDeclaration m : methods)
		{
			NovaMethodDeclaration method = (NovaMethodDeclaration)m;
			
//			if (method instanceof NovaMethodDeclaration == false || !((NovaMethodDeclaration)method).isPrimitiveOverload())
			{
				if (method.isInstance() && !method.isPrimitiveOverload() && (method instanceof AbstractMethodDeclaration == false || method.isOverridden()))
				{
					getWriter(method).generateSourceNativeName(builder, true).append(" ");
					getWriter(method).generateSourceNativeName(builder, false).append(";\n");
				}
			}
		}
		
		builder.append("} " + name + ";\n");
		
		return builder;
	}
	
	public StringBuilder generateSourceNativeInterface(StringBuilder builder)
	{
		if (node().isPrimitiveOverload())
		{
			return builder;
		}
		
		//		String name = generateSourceName("native").toString();
		
		MethodDeclaration[] methods = node().getVisibleNativeMethods();
		
		//		builder.append('\n');
		
		//		builder.append("struct " + name + "\n");
		builder.append("{\n");
		
		for (MethodDeclaration m : methods)
		{
//			if (m instanceof NovaMethodDeclaration == false || !((NovaMethodDeclaration)m).isPrimitiveOverload())
			{
				NovaMethodDeclaration method = (NovaMethodDeclaration)m;
				
				if (method.isInstance() && !method.isPrimitiveOverload() && (method instanceof AbstractMethodDeclaration == false || method.isOverridden()))
				{
					String value = "&" + getWriter(method).generateSourceName();
					
					if (method instanceof NovaMethodDeclaration)
					{
						NovaMethodDeclaration n = method;
						
						if (n.isOverridden() && !(n instanceof Constructor))
						{
							value = "0";//getVTableNode().getName() + "." + n.generateVirtualMethodName();
						}
					}
					
					builder.append(value + ",\n");
				}
			}
		}
		
		builder.append("},\n");
		
		return builder;
	}
	
	public StringBuilder writeClassDataDeclaration(StringBuilder builder)
	{
		return builder.append("NovaClassData classData;\n");
	}
	
	public StringBuilder writeClassData(StringBuilder builder)
	{
		
		
		return builder;
	}
	
	public PrintWriter writeClassInstanceDeclaration(PrintWriter writer)
	{
		ClassDeclarationWriter clazz = getWriter(node().getProgram().getClassDeclaration("nova/meta/Class"));
		
		writer.print("extern " + clazz.generateType() + " " + getClassInstanceName() + ";\n");
		
		return writer;
	}
	
	public String getClassInstanceName()
	{
		return generateSourceName(getClassInstanceVTableName()).toString();
	}
	
	public String getVTableClassInstance()
	{
		return getWriter(node().getVTableNodes().getExtensionVTable()).generateSourceName(!node().getFileDeclaration().isLibraryFile()) + "." + getClassInstanceVTableName();
	}
	
	public StringBuilder generateVTableClassInstanceAssignment(StringBuilder builder, NovaMethodDeclaration method)
	{
		builder.append("// ").append(node().getClassLocation()).append('\n');
		
		ExtensionVTableWriter vtable = getWriter(node().getVTableNodes().getExtensionVTable());
		
		ClassDeclaration classClass = node().getProgram().getClassDeclaration("nova/meta/Class");
		
		MethodDeclaration constructor = classClass.getConstructorList().getChild(0);
		
		MethodCall constructorCall = MethodCall.decodeStatement(method, "Class(\"" + node().getClassLocation() + "\", " + (node() instanceof Trait ? "true" : "false") + ")", Location.INVALID, true, false, constructor);
		
		MethodCallWriter callWriter = getWriter(constructorCall);
		
		builder.append(getVTableClassInstance() + " = " + callWriter.generateSourceFragment() + ";\n");
		
		/////////////////////////////
		
		if (!node().isPropertyTrue("functionMap"))
		{
			generateMap(builder, "FunctionMap", method, classClass);
			generateMap(builder, "PropertyMap", method, classClass);
		}
		
		return builder.append('\n');
	}
	
	public StringBuilder generateMap(StringBuilder builder, String type, NovaMethodDeclaration method, ClassDeclaration classClass)
	{
		String lower = Character.toLowerCase(type.charAt(0)) + type.substring(1);
		String funMapName = node().getName() + type;
		
		ClassDeclaration clazz = node().getFileDeclaration().getClassDeclaration(funMapName);
		
		if (clazz != null)
		{
			MethodDeclaration constructor = clazz.getConstructorList().getChild(0);
			
			MethodCall constructorCall = MethodCall.decodeStatement(method, funMapName + "()", Location.INVALID, true, false, constructor);
			
			MethodCallWriter callWriter = getWriter(constructorCall);
			
			FieldDeclarationWriter functionMap = getWriter(classClass.getField(lower));
			
			builder.append(getVTableClassInstance() + "->" + functionMap.generateSourceName() + " = " + functionMap.generateTypeCast() + callWriter.generateSourceFragment() + ";\n");
		}
		
		return builder;
	}
	
	public String getNativeLocation()
	{
		String location = node().getPackage().isDefaultPackage() ? "" : node().getPackage().getLocation().replace('/', '_');
		
		if (location.length() > 0)
		{
			location += "_";
		}
		
		if (node().isPrimitiveOverload())
		{
			location += getPrimitiveOverloadPrefix();
		}
		
		location += getName();
		
		return location;
	}
	
	public StringBuilder generateVTableClassPropertyAssignments(StringBuilder builder)
	{
		generateVTableExtensionAssignment(builder);
		generateVTableInterfaceAssignments(builder);
		
		return builder;
	}
	
	public StringBuilder generateVTableExtensionAssignment(StringBuilder builder)
	{
		ClassDeclaration clazz = node().getProgram().getClassDeclaration("nova/meta/Class");
		
		String value;
		
		if (node().doesExtendClass())
		{
			value = getWriter(node().getExtendedClassDeclaration()).getVTableClassInstance();
		}
		else
		{
			value = "(" + getWriter(clazz).generateSourceName() + "*)nova_null";
		}
		
		builder.append(getVTableClassInstance()).append("->").append(getWriter(clazz.getField("extension")).generateSourceName()).append(" = ").append(value).append(";\n");
		
		return builder;
	}
	
	public StringBuilder generateVTableInterfaceAssignments(StringBuilder builder)
	{
		ClassDeclaration clazz = node().getProgram().getClassDeclaration("nova/meta/Class");
		ClassDeclaration array = node().getProgram().getClassDeclaration("nova/datastruct/list/ImmutableArray");
		
		NovaMethodDeclaration[] constructors = array.getConstructorList().getMethods();
		
		NovaMethodDeclaration method = null;
		
		for (NovaMethodDeclaration c : constructors)
		{
			if (c.getParameterList().getNumParameters() == 2 && c.getParameter(0).isPrimitiveArray())
			{
				method = c;
			}
		}
		
		Trait[] interfaceList = node().getImplementedInterfaces(false);
		
		String name = "nova_class_interfaces";
		
		builder.append(name).append(" = NOVA_MALLOC(sizeof(nova_Nova_Object**) * ").append(interfaceList.length).append(");\n");
		
		int i = 0;
		
		for (Trait inter : interfaceList)
		{
			builder.append(name).append("[").append(i++).append("] = (nova_Nova_Object*)").append(getWriter(inter).getVTableClassInstance()).append(";\n");
		}
		
		String interfaces = getVTableClassInstance() + "->" + getWriter(clazz.getField("interfaces")).generateSourceName();
		
		builder.append(interfaces).append(" = ")
			.append(getWriter(method).generateSourceName()).append("(0, (nova_Nova_Object**)").append(name).append(", ").append(interfaceList.length).append(");\n");
		
		return builder;
	}
	
	public static String getClassInstanceVTableName()
	{
		return "classInstance";
	}
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		if (node().getFileDeclaration().getClassDeclaration() != node())
		{
			builder.append('\n');
		}
		
		builder.append("CCLASS_CLASS").append('\n').append('(').append('\n');
		
		generateSourceName(builder).append(", ").append('\n').append('\n');

		ExtensionVTable extension = node().getVTableNodes().getExtensionVTable();

		builder.append(getWriter(extension).generateTypeName(!node().getFileDeclaration().isLibraryFile())).append("* ").append(VTable.IDENTIFIER).append(";\n");
		
		if (node().containsNonStaticPrivateData())
		{
			builder.append("struct Private_").append(generateSourceName()).append("* prv;").append('\n');
		}
		else
		{
			builder.append("void* prv;").append('\n');
		}

		//writeClassDataDeclaration(builder);
		
		if (node() instanceof Trait == false)
		{
			FieldList list = node().getFieldList();
			
			getWriter(list).generateNonStaticHeader(builder);
		}
		
		ClosureVariable[] variables = node().getPublicClosureVariables();
		
		for (ClosureVariable c : variables)
		{
			getWriter(c).generateDeclaration(builder);
		}
		
		builder.append(')').append('\n');
		
		FieldList fields = node().getFieldList();
		
		getWriter(fields).generateStaticHeader(builder).append('\n');
		
		if (node().getStaticBlockList().getNumVisibleChildren() > 0)
		{
			StaticBlock child = node().getStaticBlockList().getChild(0);
			
			getWriter(child).generateHeader(builder, node());
		}
		
		MethodList constructors = node().getConstructorList();
		getWriter(constructors).generateHeader(builder);
		
		getWriter(node().getDestructorList()).generateHeader(builder);
		getWriter(node().getMethodList()).generateHeader(builder);
		getWriter(node().getPropertyMethodList()).generateHeader(builder);
		getWriter(node().getHiddenMethodList()).generateHeader(builder);
		
		return builder;
	}
	
	public StringBuilder generatePrivateDataDeclaration(StringBuilder builder)
	{
		if (node().containsNonStaticPrivateData())
		{
			builder.append("CCLASS_PRIVATE").append('\n').append('(').append('\n')
				.append(generateSourceName()).append(",\n")
				.append(generatePrivateFieldsSource()).append(')').append('\n');
		}
		
		Consumer<NovaMethodDeclaration> writePrivatePrototype = x -> {
			if (x.getVisibility() == InstanceDeclaration.PRIVATE)
			{
				getWriter(x).generateSourcePrototype(builder).append('\n');
			}
		};
		
		node().getConstructorList().forEachNovaMethod(writePrivatePrototype);
		node().getMethodList().forEachNovaMethod(writePrivatePrototype);
		node().getPropertyMethodList().forEachNovaMethod(writePrivatePrototype);
		
		return builder;
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		if (node().getFileDeclaration().getClassDeclaration() != node())
		{
			builder.append('\n');
		}
		
		builder.append(generatePrivateMethodPrototypes());
		
		FieldList fields = node().getFieldList();
		
		getWriter(fields).generateStaticSource(builder);
		
		for (int i = node().getNumDefaultChildren(); i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			builder.append('\n').append(getWriter(child).generateSource());
		}
		
		fields = node().getFieldList();
		
		getWriter(fields).generateNonStaticSource(builder);
		
		generateStaticBlocksSource(builder);
		
		getWriter(node().getConstructorList()).generateSource(builder);
		getWriter(node().getDestructorList()).generateSource(builder);
		getWriter(node().getMethodList()).generateSource(builder);
		getWriter(node().getPropertyMethodList()).generateSource(builder);
		getWriter(node().getHiddenMethodList()).generateSource(builder);
		
		return builder;
	}
	
	private StringBuilder generateStaticBlocksSource(StringBuilder builder)
	{
		if (node().getStaticBlockList().getNumVisibleChildren() > 0)
		{
			StaticBlock block = node().getStaticBlockList().getChild(0);
			
			getWriter(block).generateMethodHeader(builder, node(), true).append('\n');
			
			builder.append('{').append('\n');
			
			builder.append("if (!");
			getWriter(block).generateInitedVariable(builder, node()).append(") {\n");
			getWriter(block).generateInitedVariable(builder, node()).append(" = 1;\n");
			
			for (int i = 0; i < node().getStaticBlockList().getNumVisibleChildren(); i++)
			{
				block = node().getStaticBlockList().getChild(i);
				
				getWriter(block).generateSource(builder);
			}
			
			builder.append("}\n");
			
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
			
			getWriter(clazz).generatePrivateFieldsSource(builder);
		}
		
		ClosureVariable[] variables = node().getPrivateClosureVariables();
		
		for (ClosureVariable c : variables)
		{
			getWriter(c).generateDeclaration(builder);
		}
		
		InstanceFieldList fields = node().getFieldList().getPrivateFieldList();
		
		return getWriter(fields).generateSource(builder);
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
				getWriter(methodDeclaration).generateSourcePrototype(builder).append('\n');
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
		
		getWriter(p).generateLocation(builder).append('_');
		
		if (node().isPrimitiveOverload())
		{
			builder.append(getPrimitiveOverloadPrefix());
		}
		
		return builder;
	}
	
	public String getPrimitiveOverloadPrefix()
	{
		String prefix = "";
		
		Value[] types = node().primitiveOverloadTypes;
		
		for (int i = 0; i < types.length; i++)
		{
			Value type = types[i];
			
//			if (type.isPrimitiveType())
//			{
//				prefix += getWriter(type).generateTypeName() + "_";
//			}
//			else
			{
				prefix += type.getType() + "_";
			}
		}
		
		return prefix;
	}
}