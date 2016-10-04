package net.fathomsoft.nova;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TargetC;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.util.FileUtils;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import static net.fathomsoft.nova.Nova.*;

public class CCodeGeneratorEngine extends CodeGeneratorEngine
{
	private ArrayList<File>		cSourceFiles, cHeaderFiles;
	
	public File					nativeInterfaceSource, nativeInterfaceHeader;
	public File					interfaceVTableHeader;
	
	private static final String NATIVE_INTERFACE_FILE_NAME = "NovaNativeInterface";
	private static final String INTERFACE_VTABLE_FILE_NAME = "InterfaceVTable";
	private static final String ENVIRONMENT_VAR            = "novaEnv";
	
	public CCodeGeneratorEngine(Nova controller)
	{
		super(controller);
		
		cSourceFiles = new ArrayList<>();
		cHeaderFiles = new ArrayList<>();
	}
	
	/**
	 * Generate the C Header output from the data contained within the
	 * syntax tree.
	 */
	public void generateCHeaderOutput()
	{
		tree.getRoot().getTarget().generateHeader(new StringBuilder());
	}
	
	/**
	 * Generate the C Source output from the data contained within the
	 * syntax tree.
	 */
	public void generateCSourceOutput()
	{
		tree.getRoot().getTarget().generateSource(new StringBuilder());
	}
	
	/**
	 * Generate the C Source and Header output from the data contained
	 * within the syntax tree.
	 */
	public void generateOutput()
	{
		generateCHeaderOutput();
		generateCSourceOutput();
	}
	
	public void formatOutput()
	{
		tree.getRoot().getTarget().formatSourceOutput();
		tree.getRoot().getTarget().formatHeaderOutput();
	}
	
	public void writeFiles()
	{
		generateNativeInterface();
		generateInterfaceVTable();
		
		String headers[] = tree.getCHeaderOutput();
		String sources[] = tree.getCSourceOutput();
		FileDeclaration files[] = tree.getFiles();
		
		if (controller.isFlagEnabled(CSOURCE))
		{
			for (int i = 0; i < headers.length; i++)
			{
				controller.log(headers[i]);
				controller.log(sources[i]);
			}
		}
		
		if (!controller.isFlagEnabled(NO_C_OUTPUT))
		{
			controller.log("Writing files...");
		}
		
		StringBuilder allHeaders = new StringBuilder();
		StringBuilder includes   = new StringBuilder();
		StringBuilder types      = new StringBuilder();
		
		allHeaders.append("#pragma once\n");
		allHeaders.append("#ifndef NOVA_ALL_HEADERS\n");
		allHeaders.append("#define NOVA_ALL_HEADERS\n\n");
		allHeaders.append("#include <Nova.h>\n");
		allHeaders.append("#include <math.h>\n");
		allHeaders.append("#include <ExceptionHandler.h>\n");
		allHeaders.append("#include <setjmp.h>\n").append('\n');
		
		if (controller.outputDirectory == null)
		{
			try
			{
				controller.outputDirectory = Files.createTempDirectory("tempNovaOutput").toFile();
				
				controller.deleteOutputDirectory = true;
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		
		for (int i = 0; i < files.length; i++)
		{
			File outputDir = controller.outputDirectory;
			
			FileDeclaration file   = files[i];
			String          header = headers[i];
			String          source = sources[i];
			
			String basePackage = file.getPackage().getRootFolder();
			
			if (controller.outputDirectories.containsKey(basePackage))
			{
				outputDir = new File(controller.outputDirectories.get(basePackage));
			}
			
			new File(outputDir, file.getPackage().getLocation()).mkdirs();
			
			types.append("typedef struct ").append(file.getName()).append(' ').append(file.getName()).append(';').append('\n');
			includes.append("#include <").append(file.getTarget().generateHeaderName()).append('>').append('\n');
			
			try
			{
				if (!controller.isFlagEnabled(NO_C_OUTPUT))
				{
					File headerFile = FileUtils.writeFile(file.getTarget().generateHeaderName(), outputDir, header);
					File sourceFile = FileUtils.writeFile(file.getTarget().generateSourceName(), outputDir, source);
					
					cHeaderFiles.add(headerFile);
					cSourceFiles.add(sourceFile);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
				
				controller.completed(false);
			}
		}
		
		allHeaders.append(types).append('\n');
		allHeaders.append(includes).append('\n');
		
		allHeaders.append("#endif");
		
		if (!controller.isFlagEnabled(NO_C_OUTPUT))
		{
			controller.log("Done writing files.");
		}
	}
	
	/**
	 * Get the directory that holds the Nova library.
	 *
	 * @return The location of the directory that holds the library.
	 */
	private String getLibraryDir()
	{
		return FileUtils.formatPath(controller.workingDir + "/lib");
	}
	
	/**
	 * Get the directory that holds the Nova include files.
	 *
	 * @return The location of the directory that holds the include files.
	 */
	private String getIncludeDir()
	{
		return FileUtils.formatPath(controller.workingDir + "/include");
	}
	
	private void generateNativeInterface()
	{
		generateNativeInterfaceHeader();
		generateNativeInterfaceSource();
	}
	
	private void generateNativeInterfaceHeader()
	{
		StringBuilder nativeInterface = new StringBuilder();
		
		nativeInterface.append("#ifndef NOVA_NATIVE_INTERFACE\n");
		nativeInterface.append("#define NOVA_NATIVE_INTERFACE\n\n");
		
		for (FileDeclaration file : tree.getFiles())
		{
			nativeInterface.append("#include <" + file.getTarget().generateHeaderName() + ">\n");
		}
		
		nativeInterface.append('\n');
		
		for (FileDeclaration file : tree.getFiles())
		{
			file.getTarget().generateHeaderNativeInterface(nativeInterface).append("\n");
		}
		
		nativeInterface.append("\ntypedef struct nova_env\n");
		nativeInterface.append("{\n");
		
		for (FileDeclaration file : tree.getFiles())
		{
			for (ClassDeclaration clazz : file.getClassDeclarations())
			{
				clazz.getTarget().generateSourceName(nativeInterface, "native").append(" ").append(clazz.getNativeLocation()).append(";\n");
			}
		}
		
		nativeInterface.append("} nova_env;\n\n");
		nativeInterface.append("extern nova_env " + ENVIRONMENT_VAR + ";\n\n");
		nativeInterface.append("\n#endif\n");
		
		try
		{
			File include = new File(StringUtils.removeSurroundingQuotes(getIncludeDir()));
			
			nativeInterfaceHeader = FileUtils.writeFile(NATIVE_INTERFACE_FILE_NAME + ".h", include, nativeInterface.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void generateNativeInterfaceSource()
	{
		StringBuilder nativeInterface = new StringBuilder();
		
		nativeInterface.append("#include <precompiled.h>\n");
		nativeInterface.append("#include \"" + NATIVE_INTERFACE_FILE_NAME + ".h\"\n\n");
		
		nativeInterface.append("nova_env " + ENVIRONMENT_VAR + " = {\n");
		
		for (FileDeclaration file : tree.getFiles())
		{
			file.getTarget().generateSourceNativeInterface(nativeInterface).append('\n');
		}
		
		nativeInterface.append("};\n");
		
		try
		{
			File include = new File(StringUtils.removeSurroundingQuotes(getIncludeDir()));
			
			nativeInterfaceSource = FileUtils.writeFile(NATIVE_INTERFACE_FILE_NAME + ".c", include, nativeInterface.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void generateInterfaceVTable()
	{
		String header = generateInterfaceVTableHeader();
		
		try
		{
			File include = new File(StringUtils.removeSurroundingQuotes(getIncludeDir()));
			
			interfaceVTableHeader = FileUtils.writeFile(INTERFACE_VTABLE_FILE_NAME + ".h", include, header);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private String generateInterfaceVTableHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		NovaMethodDeclaration[] methods = tree.getRoot().getProgram().getInterfaceMethods();
		ClosureDeclaration[] closures = tree.getRoot().getProgram().getPublicClosures();
		
		builder.append("#ifndef NOVA_INTERFACE_VTABLE\n");
		builder.append("#define NOVA_INTERFACE_VTABLE\n\n");
		
		ArrayList<String> types = new ArrayList<String>();
		
		for (NovaMethodDeclaration method : methods)
		{
			SyntaxUtils.generateTypeDefinition(builder, method.getParentClass(), types);
			SyntaxUtils.generateTypeDefinition(builder, method, types);
			
			SyntaxUtils.addTypesToTypeList(builder, method, types);
		}
		
		for (ClosureDeclaration c : closures)
		{
			SyntaxUtils.addTypesToTypeList(builder, c, types);
			
			c.getTarget().generateClosureDefinition(builder);
		}
		
		builder.append("\n");
		builder.append("typedef struct ").append(InterfaceVTable.TYPE).append("\n");
		builder.append("{\n");
		
		for (NovaMethodDeclaration method : methods)
		{
			method.getVirtualMethod().getTarget().generateInterfaceVTableHeader(builder);
		}
		
		builder.append("} ").append(InterfaceVTable.TYPE).append(";\n");
		
		builder.append("\n");
		builder.append("#endif\n");
		
		return builder.toString();
	}
	
	private StringBuilder generateNativeVirtualMethodAssignments()
	{
		StringBuilder builder = new StringBuilder();
		
		Program root = tree.getRoot();
		
		for (int i = 0; i < root.getNumVisibleChildren(); i++)
		{
			FileDeclaration  file  = (FileDeclaration)root.getVisibleChild(i);
			
			for (ClassDeclaration clazz : file.getClassDeclarations())
			{
				MethodDeclaration[] methods = clazz.getVisibleNativeMethods();
				
				for (MethodDeclaration method : methods)
				{
					if (method instanceof NovaMethodDeclaration)
					{
						NovaMethodDeclaration n = (NovaMethodDeclaration)method;
						
						if (n.isOverridden() && !(n instanceof Constructor))
						{
							//n = n.getVirtualMethod();
							
							String itable = "";
							
							if (n.getParentClass() instanceof Interface)
							{
								itable = InterfaceVTable.IDENTIFIER + ".";
							}
							
							VirtualMethodDeclaration virtual = n.getVirtualMethod();
							
							builder.append(ENVIRONMENT_VAR + "." + clazz.getNativeLocation() + "." + n.getTarget().generateSourceNativeName(new StringBuilder(), false) + " = " + clazz.getVTableNodes().getExtensionVTable().getName() + "." + itable + virtual.getTarget().generateVirtualMethodName() + ";\n");
						}
					}
				}
			}
		}
		
		return builder;
	}
	
	/**
	 * Insert the main method into the correct file. Also set up the
	 * initialization for the program within the main method.
	 */
	public void insertMainMethod()
	{
		MethodDeclaration mainMethod = tree.getMainMethod(mainClass);
		
		if (mainMethod == null)
		{
			if (!controller.isFlagEnabled(LIBRARY))
			{
				if (mainClass != null)
				{
					SyntaxMessage.error("No main method found in class '" + mainClass + "'", controller);
				}
				else
				{
					SyntaxMessage.error("No main method found in program", controller);
				}
				
				controller.completed(true);
			}
			
			return;
		}
		
		StringBuilder staticBlockCalls  = generateStaticBlockCalls();
		StringBuilder nativeAssignments = generateNativeVirtualMethodAssignments();
		
		FileDeclaration fileDeclaration = mainMethod.getFileDeclaration();
		
		if (mainMethod != null)
		{
//			FileDeclaration file = mainMethod.getFileDeclaration();
//			file.addChild(Import.decodeStatement(file, "import \"GC\"", file.getLocationIn(), true, false));
			Value gcInit = (Value)SyntaxTree.decodeIdentifierAccess(mainMethod, "GC.init()", mainMethod.getLocationIn(), true);
			Value gcColl = (Value)SyntaxTree.decodeIdentifierAccess(mainMethod, "GC.collect()", mainMethod.getLocationIn(), true);
			Value enter  = (Value)SyntaxTree.decodeIdentifierAccess(mainMethod, "Console.waitForEnter()", mainMethod.getLocationIn(), true);
			
			Instantiation nullConstructor = Instantiation.decodeStatement(mainMethod, "new Null()", mainMethod.getLocationIn(), true);
			Constructor   strConstructor  = (Constructor)((MethodCall)Instantiation.decodeStatement(mainMethod, "new String(new Char[0])", mainMethod.getLocationIn(), true).getIdentifier()).getDeclaration();
			
			StringBuilder mainMethodText = new StringBuilder();
			
			mainMethodText.append('\n').append('\n');
			mainMethodText.append("nova_primitive_Nova_Null* nova_null;").append('\n');
			mainMethodText.append("void* ").append(Literal.GARBAGE_IDENTIFIER).append(';').append('\n');
			mainMethodText.append('\n');
			mainMethodText.append("int main(int argc, char** argvs)").append('\n');
			mainMethodText.append("{").append('\n');
			mainMethodText.append	("nova_Nova_String** args;").append('\n');
			mainMethodText.append	("int      i;").append('\n').append('\n');
			mainMethodText.append	("nova_exception_Nova_ExceptionData* ").append(Exception.EXCEPTION_DATA_IDENTIFIER).append(" = 0;").append('\n');
//			mainMethodText.append	("ShowWindow(FindWindowA(\"ConsoleWindowClass\", NULL), 0);").append('\n');
//			mainMethodText.append	("FreeConsole();").append('\n');
//			mainMethodText.append	("AllocConsole();").append('\n');
			mainMethodText.append	("srand(currentTimeMillis());").append('\n');
			mainMethodText.append	(Literal.GARBAGE_IDENTIFIER).append(" = malloc(sizeof(void*));").append('\n');
			mainMethodText.append	(gcInit.getTarget().generateSource()).append('\n');
			mainMethodText.append	("nova_null = ").append(nullConstructor.getTarget().generateSourceFragment()).append(';').append('\n');
			mainMethodText.append	(nativeAssignments).append('\n');
			mainMethodText.append	(staticBlockCalls).append('\n');
			mainMethodText.append	("args = (nova_Nova_String**)NOVA_MALLOC(argc * sizeof(nova_Nova_String));").append('\n');
			mainMethodText.append	('\n');
			mainMethodText.append	("for (i = 0; i < argc; i++)").append('\n');
			mainMethodText.append	("{").append('\n');
			mainMethodText.append		("char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);").append('\n');
			mainMethodText.append		("copy_string(str, argvs[i]);").append('\n');
			mainMethodText.append		("args[i] = ").append(strConstructor.getTarget().generateSourceName()).append("(0, 0, str);").append('\n');
			mainMethodText.append	("}").append('\n');
			mainMethodText.append	("nova_datastruct_list_Nova_Array* argsArray = nova_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData, (nova_Nova_Object**)args, argc);");
			mainMethodText.append	('\n');
			mainMethodText.append	("TRY").append('\n');
			mainMethodText.append	('{').append('\n');
			mainMethodText.append		(mainMethod.getTarget().generateSourceName()).append("(0, ").append(Exception.EXCEPTION_DATA_IDENTIFIER).append(", argsArray);").append('\n');
			mainMethodText.append	('}').append('\n');
			mainMethodText.append	("CATCH (1)").append('\n');
			mainMethodText.append	('{').append('\n');
			mainMethodText.append		("nova_exception_Nova_Exception* base = (nova_exception_Nova_Exception*)").append(Exception.EXCEPTION_DATA_IDENTIFIER).append("->nova_exception_Nova_ExceptionData_Nova_thrownException;").append('\n');
			mainMethodText.append		("printf(\"Exception in Thread 'main': %s\", base->nova_exception_Nova_Exception_Nova_message->nova_Nova_String_Nova_chars);").append('\n');
			mainMethodText.append		(enter.getTarget().generateSource()).append('\n');
			mainMethodText.append	('}').append('\n');
			mainMethodText.append	("FINALLY").append('\n');
			mainMethodText.append	('{').append('\n');
			mainMethodText.append		('\n');
			mainMethodText.append	('}').append('\n');
			mainMethodText.append	("END_TRY;").append('\n');
			
			if (OS == WINDOWS)
			{
				mainMethodText.append("FreeConsole();").append('\n');
			}
			
			mainMethodText.append   ("NOVA_FREE(args);").append('\n');
			mainMethodText.append	(gcColl.getTarget().generateSource()).append('\n');
			mainMethodText.append	('\n');
			mainMethodText.append	("return 0;").append('\n');
			mainMethodText.append("}\n");
			
			String newSource = fileDeclaration.getTarget().generateSource() + mainMethodText.toString();
			
			newSource = SyntaxUtils.formatText(newSource);
			
			fileDeclaration.setSource(newSource);
		}
	}
	
	private StringBuilder generateStaticBlockCalls()
	{
		StringBuilder builder = new StringBuilder();
		
		Program root = tree.getRoot();
		
		for (int i = 0; i < root.getNumVisibleChildren(); i++)
		{
			FileDeclaration  file  = (FileDeclaration)root.getVisibleChild(i);
			
			for (ClassDeclaration clazz : file.getClassDeclarations())
			{
				TypeList<StaticBlock> blocks = clazz.getStaticBlockList();
				
				for (int j = 0; j < blocks.getNumVisibleChildren(); j++)
				{
					TargetC.TargetStaticBlock.generateMethodCall(builder, clazz).append(';').append('\n');
				}
			}
		}
		
		return builder;
	}
}