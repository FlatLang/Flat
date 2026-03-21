package nova.c.engines;

import net.fathomsoft.nova.CodeGeneratorEngine;
import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.annotations.NativeAnnotation;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.util.FileUtils;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;
import nova.c.nodewriters.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static net.fathomsoft.nova.Nova.*;
import static nova.c.nodewriters.NodeWriter.getWriter;

public class CCodeGeneratorEngine extends CodeGeneratorEngine
{
	private ArrayList<File>		cSourceFiles, cHeaderFiles;
	
	public boolean forceRecompile;
	
	public File					nativeInterfaceSource, nativeInterfaceHeader;
	public File					interfaceVTableHeader, vtableDeclarationsHeader, vtableDeclarationsSource;
	
//	private static final String NATIVE_INTERFACE_FILE_NAME = "NovaNativeInterface";
	private static final String INTERFACE_VTABLE_FILE_NAME = "InterfaceVTable";
	private static final String SINGLE_FILE_BUILD_FILE_NAME = "NovaOutput";
	private static final String MAIN_FUNCTION_FILE_NAME = "MainFunction";
	private static final String VTABLE_DECLARATIONS_FILE_NAME = "VTableDeclarations";
//	private static final String ENVIRONMENT_VAR            = "novaEnv";
	
	private CCompileEngine compileEngine;
	
	public static final HashSet<String> KEYWORDS = new HashSet<String>() {{
		add("auto");
		add("break");
		add("case");
		add("char");
		add("const");
		add("continue");
		add("default");
		add("do");
		add("double");
		add("else");
		add("enum");
		add("extern");
		add("float");
		add("for");
		add("goto");
		add("if");
		add("int");
		add("long");
		add("register");
		add("return");
		add("short");
		add("signed");
		add("sizeof");
		add("static");
		add("struct");
		add("switch");
		add("typedef");
		add("union");
		add("unsigned");
		add("void");
		add("volatile");
		add("while");
	}};
	
	public CCodeGeneratorEngine(Nova controller)
	{
		super(controller);
		
		cSourceFiles = new ArrayList<>();
		cHeaderFiles = new ArrayList<>();
	}
	
	public void init()
	{
		compileEngine = (CCompileEngine)controller.compileEngine;
	}
	
	/**
	 * Generate the C Header output from the data contained within the
	 * syntax tree.
	 */
	public void generateCHeaderOutput()
	{
		getWriter(tree.getRoot()).generateHeader(new StringBuilder());
	}
	
	/**
	 * Generate the C Source output from the data contained within the
	 * syntax tree.
	 */
	public void generateCSourceOutput()
	{
		getWriter(tree.getRoot()).generateSource(new StringBuilder());
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
		getWriter(tree.getRoot()).formatSourceOutput();
		getWriter(tree.getRoot()).formatHeaderOutput();
	}
	
	/**
	 * Get the C Header output text (destination text) from the Syntax
	 * tree.
	 *
	 * @return The C Header output text after compilation.
	 */
	public String[] getCHeaderOutput()
	{
		Program root = tree.getRoot();
		
		String headers[] = new String[root.getNumChildren()];
		
		for (int i = 0; i < headers.length; i++)
		{
			Node child = root.getChild(i);
			
			headers[i] = getWriter(child).generateHeader().toString();
		}
		
		return headers;
	}
	
	/**
	 * Get the C Source output text (destination text) from the Syntax
	 * tree.
	 *
	 * @return The C Source output text after compilation.
	 */
	public String[] getCSourceOutput()
	{
		Program root = tree.getRoot();
		
		String sources[] = new String[root.getNumChildren()];
		
		for (int i = 0; i < sources.length; i++)
		{
			Node child = root.getChild(i);
			
			sources[i] = getWriter(child).generateSource().toString();
		}
		
		return sources;
	}
	
	public static File getLibraryOutputDirectory(File library)
	{
		return new File(library, ".novalib/c");
	}
	
	public void writeFiles()
	{
		generateVTableDeclarations(); // must do first because then sets forceRecompile
		
//		generateNativeInterface();
		generateInterfaceVTable();

		writeMakefile();

		String headers[] = getCHeaderOutput();
		String sources[] = getCSourceOutput();
		FileDeclaration files[] = tree.getFiles();
		
		if (controller.isFlagEnabled(CSOURCE))
		{
			for (int i = 0; i < headers.length; i++)
			{
				controller.log(headers[i]);
				controller.log(sources[i]);
			}
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
		
		HashMap<File, File> headerFiles = new HashMap<>();
		HashMap<File, File> sourceFiles = new HashMap<>();
		
		headerFiles.put(null, new File(controller.outputDirectory, SINGLE_FILE_BUILD_FILE_NAME + ".h"));
		sourceFiles.put(null, new File(controller.outputDirectory, SINGLE_FILE_BUILD_FILE_NAME + ".c"));
		
		HashMap<File, Long> headerTimes = new HashMap<>();
		HashMap<File, Long> sourceTimes = new HashMap<>();
		
		HashMap<File, PrintWriter> headerWriters = new HashMap<>();
		HashMap<File, PrintWriter> sourceWriters = new HashMap<>();
		
		HashMap<File, String> previousHeaders = new HashMap<>();
		HashMap<File, String> previousSources = new HashMap<>();
		
		try
		{
			if (compileEngine.singleFile)
			{
				headerFiles.forEach((l, f) -> {
					if (f.isFile())
					{
						try
						{
							headerTimes.put(l, f.lastModified());
							previousHeaders.put(l, FileUtils.readFile(f));
						}
						catch (IOException e)
						{
							throw new RuntimeException(e);
						}
					}
				});
				
				sourceFiles.forEach((l, f) -> {
					if (f.isFile())
					{
						try
						{
							sourceTimes.put(l, f.lastModified());
							previousSources.put(l, FileUtils.readFile(f));
						}
						catch (IOException e)
						{
							throw new RuntimeException(e);
						}
					}
				});
				
				headerWriters.put(null, FileUtils.getFileWriter(headerFiles.get(null), true));
				sourceWriters.put(null, FileUtils.getFileWriter(sourceFiles.get(null), true));
				
				// TODO: #1
//				for (Map.Entry<File, ArrayList<File>> entry : controller.libraryFiles.entrySet())
//				{
//					File parent = getLibraryOutputDirectory(entry.getKey());
//					parent.mkdirs();
//					
//					File headerFile = new File(parent, entry.getKey().getName() + ".h");
//					File sourceFile = new File(parent, entry.getKey().getName() + ".c");
//					
//					headerWriters.put(entry.getKey(), FileUtils.getFileWriter(headerFile, true));
//					sourceWriters.put(entry.getKey(), FileUtils.getFileWriter(sourceFile, true));
//					
//					FileUtils.clearFile(headerFile);
//					FileUtils.clearFile(sourceFile);
//				}
				
				FileUtils.clearFile(headerFiles.get(null));
				FileUtils.clearFile(sourceFiles.get(null));
				
				sourceWriters.forEach((f, w) -> w.print("#include \"" + (f == null ? SINGLE_FILE_BUILD_FILE_NAME : f.getName()) + ".h\"\n\n"));
				
				headerWriters.forEach((f, w) -> w.print("#ifndef NOVA_" + (f != null ? f.getName() + "_" : "") + "OUTPUT\n"));
				headerWriters.forEach((f, w) -> w.print("#define NOVA_" + (f != null ? f.getName() + "_" : "") + "OUTPUT\n\n"));
				
				for (int i = 0; i < files.length; i++)
				{
					FileDeclaration file = files[i];
					
					// TODO: #1
					headerWriters.get(null/*controller.getLibrary(file.file)*/).print(getWriter(file).generateDummyTypes(new StringBuilder()).toString());
				}
				
				headerWriters.forEach((f, w) -> w.print("#include <MacroLib.h>\n\n"));
				
				for (int i = 0; i < files.length; i++)
				{
					FileDeclaration file = files[i];
					
					// TODO: #1
					headerWriters.get(null/*controller.getLibrary(file.file)*/).print(getWriter(file).generateClosureDefinitions(new StringBuilder()).toString());
				}
				
				headerWriters.forEach((f, w) -> w.print("\n#include <Nova.h>\n"));
				headerWriters.forEach((f, w) -> {
					if (f != null)
					{
						headerWriters.forEach((f2, w2) -> {
							if (f2 != f)
							{
								w2.print("#include <" + f.getName() + ".h>\n");
							}
						});
					}
				});
				headerWriters.forEach((f, w) -> w.print("#include <pcre/pcre2.h>\n"));
				headerWriters.forEach((f, w) -> w.print("#include <ExceptionHandler.h>\n"));
				
				headerWriters.forEach((f, w) -> w.print(getAllExternalIncludes() + "\n"));
				// TODO: #1
//				headerWriters.forEach((f, w) -> {
//					for (Map.Entry<File, ArrayList<File>> entry : controller.libraryFiles.entrySet())
//					{
//						w.print("#include <" + entry.getKey().getName() + "_" + VTABLE_DECLARATIONS_FILE_NAME + ".h>\n");
//					}
//				});
				headerWriters.forEach((f, w) -> w.print("#include \"VTableDeclarations.h\"\n\n"));
			}
			
			for (int i = 0; i < files.length; i++)
			{
				FileDeclaration file = files[i];
				
				// TODO: #1
//				File lib = controller.getLibrary(file.file);
				
				String header = headers[i];
				String source = sources[i];
				
				File outputDir = null;
				
				// TODO: #1
//				if (file.isLibraryFile())
//				{
//					outputDir = getLibraryOutputDirectory(lib);
//				}
//				else
				{
					outputDir = getOutputDirectory(file);
				}
				
				if (!file.getPackage().isDefaultPackage() && !compileEngine.singleFile)
				{
					new File(outputDir, file.getPackage().getLocation()).mkdirs();
				}
				
				types.append("typedef struct ").append(file.getName()).append(' ').append(file.getName()).append(';').append('\n');
				includes.append("#include <").append(getWriter(file).generateHeaderName()).append('>').append('\n');
				
				if (!controller.isFlagEnabled(NO_C_OUTPUT))
				{
					if (compileEngine.singleFile)
					{
						// TODO: #1
						headerWriters.get(null/*controller.getLibrary(file.file)*/).print(header);
						sourceWriters.get(null/*controller.getLibrary(file.file)*/).print(source);
					}
					else
					{
						// TODO: #1
//						headerFiles.put(lib, new File(outputDir, getWriter(file).generateHeaderName()));
//						sourceFiles.put(lib, new File(outputDir, getWriter(file).generateSourceName()));
//						
//						if (compileEngine.forceCheck || forceRecompile || file.getFile().lastModified() > headerFiles.get(lib).lastModified())
//						{
//							if (FileUtils.writeIfDifferent(headerFiles.get(lib), header))
//							{
//								controller.log("Wrote " + headerFiles.get(lib).getCanonicalPath());
//							}
//							else if (compileEngine.forceRecompile)
//							{
//								controller.log("No differences to file " + headerFiles.get(lib).getCanonicalPath());
//							}
//						}
//						if (compileEngine.forceCheck || forceRecompile || file.getFile().lastModified() > sourceFiles.get(lib).lastModified())
//						{
//							if (FileUtils.writeIfDifferent(sourceFiles.get(lib), source))
//							{
//								controller.log("Wrote " + sourceFiles.get(lib).getCanonicalPath());
//							}
//							else if (compileEngine.forceRecompile)
//							{
//								controller.log("No differences to file " + sourceFiles.get(lib).getCanonicalPath());
//							}
//						}
					}
					
					// TODO: #1
					cHeaderFiles.add(headerFiles.get(null/*lib*/));
					cSourceFiles.add(sourceFiles.get(null/*lib*/));
				}
			}
			
			if (compileEngine.singleFile)
			{
				headerWriters.forEach((f, w) -> w.print("\n#endif"));
				
				headerWriters.forEach((f, w) -> w.close());
				sourceWriters.forEach((f, w) -> w.close());
				
				headerFiles.forEach((l, f) -> {
					try
					{
						if (headerTimes.get(l) == null || FileUtils.checkModified(f, headerTimes.get(l), previousHeaders.get(l), FileUtils.readFile(f)))
						{
							controller.log("Wrote " + f.getCanonicalPath());
						}
					}
					catch (IOException e)
					{
						throw new RuntimeException(e);
					}
				});
				
				sourceFiles.forEach((l, f) -> {
					try
					{
						if (sourceTimes.get(l) == null || FileUtils.checkModified(f, sourceTimes.get(l), previousSources.get(l), FileUtils.readFile(f)))
						{
							controller.log("Wrote " + f.getCanonicalPath());
						}
					}
					catch (IOException e)
					{
						throw new RuntimeException(e);
					}
				});
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			
			controller.completed(false);
		}
		
		allHeaders.append(types).append('\n');
		allHeaders.append(includes).append('\n');
		
		allHeaders.append("#endif");
		
//		writeClassData();
	}
	
	public Trait[] getAllInterfaces()
	{
		ArrayList<Trait> list = new ArrayList<>();
		
		for (FileDeclaration file : tree.getFiles())
		{
			if (file.getClassDeclaration() instanceof Trait)
			{
				list.add((Trait)file.getClassDeclaration());
			}
		}
		
		return list.toArray(new Trait[0]);
	}
	
	public ClassDeclaration[] getAllClasses()
	{
		return getAllClasses(true);
	}
	
	public ClassDeclaration[] getAllClasses(boolean includeInterfaces)
	{
		ArrayList<ClassDeclaration> list = new ArrayList<>();
		
		for (FileDeclaration file : tree.getFiles())
		{
			if (includeInterfaces || file.getClassDeclaration() instanceof Trait == false)
			{
				for (ClassDeclaration c : file.getClassDeclarations())
				{
					list.add(c);
				}
			}
		}
		
		return list.toArray(new ClassDeclaration[0]);
	}
	
	public VirtualMethodDeclaration[] getAllVirtualMethods()
	{
		ArrayList<VirtualMethodDeclaration> list = new ArrayList<>();
		
		for (ClassDeclaration c : getAllClasses())
		{
			for (NovaMethodDeclaration method : c.getExtensionVirtualMethods(false))
			{
				VirtualMethodDeclaration virtual = method.getVirtualMethod();
				
				if (virtual != null && !list.contains(virtual))
				{
					list.add(virtual);
				}
			}
		}
		
		return list.toArray(new VirtualMethodDeclaration[0]);
	}
	
	public VirtualMethodDeclaration[] getAllInterfaceVirtualMethods()
	{
		ArrayList<VirtualMethodDeclaration> list = new ArrayList<>();
		
		for (ClassDeclaration c : getAllClasses())
		{
			for (NovaMethodDeclaration method : c.getInterfaceVirtualMethods(false))
			{
				VirtualMethodDeclaration virtual = method.getVirtualMethod();
				
				if (virtual != null && !list.contains(virtual))
				{
					list.add(virtual);
				}
			}
		}
		
		return list.toArray(new VirtualMethodDeclaration[0]);
	}
	
	public VirtualMethodDeclaration[] getAllAndIMeanAllVirtualMethods()
	{
		ArrayList<VirtualMethodDeclaration> list = new ArrayList<>();
		
		for (ClassDeclaration c : getAllClasses())
		{
			for (NovaMethodDeclaration method : c.getExtensionVirtualMethods(false))
			{
				VirtualMethodDeclaration virtual = method.getVirtualMethod();
				
				if (virtual != null && !list.contains(virtual))
				{
					list.add(virtual);
				}
			}
			for (NovaMethodDeclaration method : c.getInterfaceVirtualMethods(false))
			{
				VirtualMethodDeclaration virtual = method.getVirtualMethod();
				
				if (virtual != null && !list.contains(virtual))
				{
					list.add(virtual);
				}
			}
		}
		
		return list.toArray(new VirtualMethodDeclaration[0]);
	}
	
	public String getExternalLocation(String external)
	{
		return new File(FileUtils.formAbsolutePath(external)).getAbsolutePath().replace("\\", "/");
	}
	
	public boolean writeMakefile()
	{
		try
		{
			File makefile = new File(controller.outputDirectory, "makefile");

//			long lastModified = makefile.exists() ? makefile.lastModified() : 0;
			
			FileUtils.writeIfDifferent(makefile, writer ->
			{
				writer.print("NOVA_DEPS =");
				
				if (compileEngine.singleFile)
				{
					writer.print(" " + SINGLE_FILE_BUILD_FILE_NAME + ".h");
				}
				else
				{
					for (FileDeclaration file : tree.getFiles())
					{
						writer.print(" " + getWriter(file).generateFullLocation().replace('\\', '/').replace(" ", "\\ ") + ".h");
					}
				}
				
				for (String external : controller.externalImports)
				{
					String location = getExternalLocation(external);
					
					writer.print(" " + location.substring(0, location.length() - 2).replace('\\', '/').replace(" ", "\\ ") + ".h");
				}
				
				try
				{
					for (Map.Entry<File, ArrayList<File>> entry : controller.libraryFiles.entrySet())
					{
						File f = new File(getLibraryOutputDirectory(entry.getKey()), entry.getKey().getName() + ".h");
						
						writer.print(" " + f.getCanonicalPath().replace('\\', '/').replace(" ", "\\ "));
					}
				}
				catch (IOException e)
				{
					
				}
				
				writer.print("\n");
				
				writer.print("NOVA_OBJ =");
				
				if (compileEngine.singleFile)
				{
					writer.print(" " + SINGLE_FILE_BUILD_FILE_NAME + ".o");
				}
				else
				{
					for (FileDeclaration file : tree.getFiles())
					{
						writer.print(" " + getWriter(file).generateFullLocation().replace('\\', '/').replace(" ", "\\ ") + ".o");
					}
				}
				
				for (String external : controller.externalImports)
				{
					String location = getExternalLocation(external);
					
					if (new File(location.substring(0, location.length() - 2) + ".c").isFile())
					{
						writer.print(" " + location.substring(0, location.length() - 2).replace('\\', '/').replace(" ", "\\ ") + ".o");
					}
				}
				
				writer.print("\n\n");
				
				writer.print("NOVA_COMPILE_HOME = ");
				writer.print(controller.targetEngineWorkingDir.getAbsolutePath().replace('\\', '/').replace(" ", "\\ "));
				writer.print("\n");
				
				writer.print("EXEC_PATH = ");
				writer.print(formatPath(controller.outputFile.getAbsolutePath()));
				writer.print("\n");
				
				writer.print("LDIRS=-L");
				writer.print(formatPath(controller.installDirectory.getAbsolutePath() + "/bin"));
				writer.print("\n");
				
				writer.print("NOVA_STDLIB_LOCATION = ");
				writer.print(controller.targetEngineWorkingDir.getParentFile().getAbsolutePath().replace('\\', '/').replace(" ", "\\ "));
				writer.print("/StandardLibrary\n\n");
				
				writer.print("MAKEFILE_LOCATION = ");
				writer.print("$(NOVA_COMPILE_HOME)/makefile.nova");
				writer.print("\n\n");
				
				writer.print("include $(MAKEFILE_LOCATION)");
			}, forceRecompile);
			
//			if (lastModified > 0)
//			{
//				makefile.setLastModified(lastModified);
//			}
		}
		catch (IOException e)
		{
			return false;
		}
		
		return true;
	}
	
	public String formatPath(String path)
	{
		if (controller.isFlagEnabled(Nova.QUOTE_PATHS))
		{
			return '"' + path + '"';
		}
		else
		{
			return path.replace('\\', '/').replace(" ", "\\ ");
		}
	}
	
	public boolean writeClassData()
	{
		try
		{
			final Trait[] interfaces = getAllInterfaces();
			
			File header = new File(controller.outputDirectory, "NovaClassData.h");
			
			FileUtils.writeIfDifferent(header, writer ->
			{
				writer.print("#ifndef NOVA_CLASS_DATA\n#define NOVA_CLASS_DATA\n\n");
				
				ClassDeclaration clazz = controller.getTree().getRoot().getClassDeclaration("nova/meta/Class");
				
				writer.print("typedef struct NovaClassData NovaClassData;\n\n");
				
				try
				{
					for (Trait i : interfaces)
					{
						getWriter(i).writeVTableTypedef(writer);
					}
					
					for (Trait i : interfaces)
					{
						getWriter(i).writeDefaultVTableDeclaration(writer);
					}
					
					if (!compileEngine.singleFile)
					{
						writer.print("\n");
						writer.write(getAllIncludes());
						writer.print("\n");
					}
					
					for (Trait i : interfaces)
					{
						getWriter(i).writeVTableAssignment(writer);
						writer.print("\n");
					}
					
					writer.print("\n");
					
					writer.print("\nstruct NovaClassData {\n");
					
					//writer.print(clazzWriter.generateType().toString() + " instance_class;\n\n");
					
					for (Trait i : interfaces)
					{
						getWriter(i).writeVTableDeclaration(writer);
					}
					
					writer.print("\n");
					
					for (VirtualMethodDeclaration virtual : getAllVirtualMethods())
					{
						getWriter(virtual).writeVTableDeclaration(writer);
					}
				}
				catch (IOException e)
				{
					throw new RuntimeException(e);
				}
				
				writer.print("};\n");
				
				writer.print("\n#endif");
			}, forceRecompile);
			
			File source = new File(controller.outputDirectory, "NovaClassData.c");
			
			FileUtils.writeIfDifferent(source, writer ->
			{
//				writer.write("#include <NovaClassData.h>\n\n");
				
				try
				{
					for (Trait i : interfaces)
					{
						getWriter(i).writeDefaultVTable(writer);
					}
				}
				catch (IOException e)
				{
					throw new RuntimeException(e);
				}
			}, forceRecompile);
		}
		catch (IOException e)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get the directory that holds the Nova library.
	 *
	 * @return The location of the directory that holds the library.
	 */
	private String getLibraryDir()
	{
		return FileUtils.formatPath(controller.targetEngineWorkingDir.getAbsolutePath() + "/lib");
	}
	
	/**
	 * Get the directory that holds the Nova include files.
	 *
	 * @return The location of the directory that holds the include files.
	 */
	private String getIncludeDir()
	{
		return FileUtils.formatPath(controller.targetEngineWorkingDir.getAbsolutePath() + "/include");
	}
	
//	private void generateNativeInterface()
//	{
//		generateNativeInterfaceHeader();
//		generateNativeInterfaceSource();
//	}
	
	public String getAllExternalIncludes()
	{
		String output = "";
		
		for (String s : controller.externalIncludes)
		{
			output += "#include <" + s + ">\n";
		}
		
		return output;
	}
	
	public String getAllIncludes()
	{
		StringBuilder builder = new StringBuilder();
		
		for (FileDeclaration file : tree.getFiles())
		{
			builder.append(getWriter(file).getIncludeStatement()).append("\n");
		}
		
		return builder.toString();
	}
	
//	private void generateNativeInterfaceHeader()
//	{
//		File header = new File(controller.outputDirectory, NATIVE_INTERFACE_FILE_NAME + ".h");
//		
//		controller.outputDirectory.mkdirs();
//		
//		try
//		{
//			FileUtils.writeIfDifferent(header, writer ->
//			{
//				writer.append("#ifndef NOVA_NATIVE_INTERFACE\n");
//				writer.append("#define NOVA_NATIVE_INTERFACE\n\n");
//				
//				if (compileEngine.singleFile)
//				{
//					writer.append("#include <" + SINGLE_FILE_BUILD_FILE_NAME + ".h>\n");
//					writer.append("#include <ExceptionHandler.h>\n");
//					writer.append(getAllExternalIncludes());
//				}
//				else
//				{
//					writer.append(getAllIncludes());
//				}
//				
//				writer.append('\n');
//				
//				for (FileDeclaration file : tree.getFiles())
//				{
//					writer.print(getWriter(file).generateHeaderNativeInterface(new StringBuilder()).append("\n"));
//				}
//				
//				writer.append("\ntypedef struct nova_env\n");
//				writer.append("{\n");
//				
//				for (FileDeclaration file : tree.getFiles())
//				{
//					for (ClassDeclaration clazz : file.getClassDeclarations())
//					{
//						if (!clazz.isPrimitiveOverload())
//						{
//							writer.print(getWriter(clazz).generateSourceName(new StringBuilder(), "native").append(" ").append(getWriter(clazz).getNativeLocation()).append(";\n"));
//						}
//					}
//				}
//				
//				writer.append("} nova_env;\n\n");
//				writer.append("extern nova_env " + ENVIRONMENT_VAR + ";\n\n");
//				writer.append("\n#endif\n");
//			}, forceRecompile);
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	private void generateNativeInterfaceSource()
//	{
//		File source = new File(controller.outputDirectory, NATIVE_INTERFACE_FILE_NAME + ".c");
//		
//		try
//		{
//			FileUtils.writeIfDifferent(source, writer ->
//			{
//				writer.append("#include \"" + NATIVE_INTERFACE_FILE_NAME + ".h\"\n\n");
//				
//				writer.append("nova_env " + ENVIRONMENT_VAR + " = {\n");
//				
//				for (FileDeclaration file : tree.getFiles())
//				{
//					writer.print(getWriter(file).generateSourceNativeInterface(new StringBuilder()).append('\n'));
//				}
//				
//				writer.append("};\n");
//			}, forceRecompile);
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
	
	private void generateInterfaceVTable()
	{
		File header = new File(controller.outputDirectory, INTERFACE_VTABLE_FILE_NAME + ".h");
		
		try
		{
			FileUtils.writeIfDifferent(header, writer ->
			{
				writer.append(generateInterfaceVTableHeader());
			}, forceRecompile);
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
			FileDeclarationWriter.generateTypeDefinition(builder, method.getParentClass(), types);
			FileDeclarationWriter.generateTypeDefinition(builder, method, types);
			
			FileDeclarationWriter.addTypesToTypeList(builder, method, types);
		}
		
		for (ClosureDeclaration c : closures)
		{
			FileDeclarationWriter.addTypesToTypeList(builder, c, types);
			
			getWriter(c).generateClosureDefinition(builder);
		}
		
		builder.append("\n");
		builder.append("typedef struct ").append(TraitVTable.TYPE).append("\n");
		builder.append("{\n");
		
		for (NovaMethodDeclaration method : methods)
		{
			getWriter(method.getVirtualMethod()).generateInterfaceVTableHeader(builder);
		}
		
		builder.append("} ").append(TraitVTable.TYPE).append(";\n");
		
		builder.append("\n");
		builder.append("#endif\n");
		
		return builder.toString();
	}
	
	private StringBuilder generateVTableClassInstanceAssignments(NovaMethodDeclaration method)
	{
		StringBuilder builder = new StringBuilder();
		
		for (ClassDeclaration c : getAllClasses())
		{
			getWriter(c).generateVTableClassInstanceAssignment(builder, method);
		}
		
		return builder.append("\n");
	}
	
	private StringBuilder generateVTableClassInstancePropertyAssignments()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("nova_Nova_Object** nova_class_interfaces;\n\n");
		
		for (ClassDeclaration c : getAllClasses())
		{
			getWriter(c).generateVTableClassPropertyAssignments(builder);
		}
		
		return builder.append("\n");
	}
	
	private StringBuilder generateVTableClassArray()
	{
		StringBuilder builder = new StringBuilder();
		
		ClassDeclaration[] classes = getAllClasses();
		
		ClassDeclaration classClass = tree.getRoot().getClassDeclaration("nova/meta/Class");
		ClassDeclaration arrayClass = tree.getRoot().getClassDeclaration("nova/datastruct/list/ImmutableArray");
		
		ClassDeclarationWriter clazz = getWriter(classClass);
		
		String name = "nova_all_classes";
		
		builder.append(clazz.generateSourceName()).append("** ").append(name).append(" = NOVA_MALLOC(sizeof(").append(clazz.generateSourceName()).append("*) * ").append(classes.length).append(");\n");
		
		int i = 0;
		
		for (ClassDeclaration c : classes)
		{
			builder.append(name).append("[").append(i++).append("] = ").append(getWriter(c).getVTableClassInstance()).append(";\n");
		}
		
		FieldDeclaration allArray = classClass.getField("ALL", false);
		
		NovaMethodDeclaration[] constructors = arrayClass.getConstructorList().getMethods();
		
		NovaMethodDeclaration method = null;
		
		for (NovaMethodDeclaration c : constructors)
		{
			if (c.getParameterList().getNumParameters() == 2 && c.getParameter(0).isPrimitiveArray())
			{
				method = c;
			}
		}
		
		builder.append(getWriter(allArray).generateSourceName()).append(" = ")
			.append(getWriter(method).generateSourceName()).append("(0, (nova_Nova_Object**)").append(name).append(", ").append(classes.length).append(");\n");
		
		return builder.append("\n");
	}
	
//	private StringBuilder generateNativeVirtualMethodAssignments()
//	{
//		StringBuilder builder = new StringBuilder();
//		
//		Program root = tree.getRoot();
//		
//		for (int i = 0; i < root.getNumVisibleChildren(); i++)
//		{
//			FileDeclaration file = root.getVisibleChild(i);
//			
//			for (ClassDeclaration clazz : file.getClassDeclarations())
//			{
//				if (!clazz.isPrimitiveOverload())
//				{
//					MethodDeclaration[] methods = clazz.getVisibleNativeMethods();
//					
//					for (MethodDeclaration method : methods)
//					{
//						if (method instanceof NovaMethodDeclaration)
//						{
//							if (method.isInstance())
//							{
//								NovaMethodDeclaration n = (NovaMethodDeclaration)method;
//								
//								if (n.isOverridden() && !(n instanceof Constructor))
//								{
//									//n = n.getVirtualMethod();
//									
//									String itable = "";
//									
//									if (n.getRootDeclaration().getParentClass() instanceof Trait)
//									{
//										itable = TraitVTable.IDENTIFIER + ".";
//									}
//									
//									VirtualMethodDeclaration virtual = n.getVirtualMethod();
//									
//									builder.append(ENVIRONMENT_VAR + "." + getWriter(clazz).getNativeLocation() + "." + getWriter(n).generateSourceNativeName(new StringBuilder(), false) + " = " + getWriter(clazz.getVTableNodes().getExtensionVTable()).generateSourceName() + "." + itable + getWriter(virtual).generateVirtualMethodName() + ";\n");
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		
//		return builder;
//	}
	
	private ArrayList<VirtualMethodDeclaration> getAllVirtualMethods(File library)
	{
		ArrayList<VirtualMethodDeclaration> list = new ArrayList<>();
		
		for (ClassDeclaration c : getAllClasses())
		{
			// TODO: #1
//			if (c.getFileDeclaration().getLibrary() == library)
			{
				c.getVirtualMethodList().forEachNovaMethod(x -> {
					if (!list.contains(x))
					{
						list.add((VirtualMethodDeclaration)x);
					}
				});
			}
		}
		
		return list;
	}

	private void generateVTableDeclarations()
	{
		HashMap<File, File> headers = new HashMap<>();
		HashMap<File, File> sources = new HashMap<>();
		
		headers.put(null, new File(controller.outputDirectory, VTABLE_DECLARATIONS_FILE_NAME + ".h"));
		sources.put(null, new File(controller.outputDirectory, VTABLE_DECLARATIONS_FILE_NAME + ".c"));
		
		// TODO: #1
//		for (Map.Entry<File, ArrayList<File>> entry : controller.libraryFiles.entrySet())
//		{
//			File dir = getLibraryOutputDirectory(entry.getKey());
//			
//			headers.put(entry.getKey(), new File(dir, entry.getKey().getName() + "_" + VTABLE_DECLARATIONS_FILE_NAME + ".h"));
//			sources.put(entry.getKey(), new File(dir, entry.getKey().getName() + "_" + VTABLE_DECLARATIONS_FILE_NAME + ".c"));
//		}
		
		final boolean[] forceRecompile = new boolean[] { compileEngine.forceRecompile };
		
		// TODO: #1
//		sources.forEach((l, source) -> {
			try
			{
				// TODO: #1
				forceRecompile[0] = forceRecompile[0] | FileUtils.writeIfDifferent(sources.get(null)/*source*/, writer ->
				{
					// TODO: #1
					writer.append("#include \"").append(/*l != null ? l.getName() + "_" : */"").append(VTABLE_DECLARATIONS_FILE_NAME + ".h\"\n\n");
					
					Arrays.stream(getAllAndIMeanAllVirtualMethods())
						// TODO: #1
//						.filter(v -> v.getFileDeclaration().getLibrary() == l)
						.forEach(v -> writer.append(getWriter(v).generateSource().append("\n")));
					
					for (ClassDeclaration c : getAllClasses())
					{
						VTableList vtables = c.getVTableNodes();
						// TODO: #1
//						File lib = c.getFileDeclaration().getLibrary();
						
						// TODO: #1
//						if (l == null)
//						{
							vtables.forEach(vtable -> {
								writer.append(getWriter(vtable).generateSource(new StringBuilder(), true).append('\n'));
							});
//						}
//						else if (lib == l)
//						{
//							writer.append(getWriter(vtables).generateSource(new StringBuilder()).append('\n'));
//						}
					}
				}, forceRecompile[0]);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
//		});
		
		// TODO: #1
//		headers.forEach((l, header) -> {
			try
			{
				// TODO: #1
				forceRecompile[0] = forceRecompile[0] | FileUtils.writeIfDifferent(headers.get(null)/*header*/, writer ->
				{
					// TODO: #1
					writer.append("#ifndef NOVA_").append(/*l != null ? l.getName() + "_" : */"").append("VTABLE_DECLARATIONS\n");
					writer.append("#define NOVA_").append(/*l != null ? l.getName() + "_" : */"").append("VTABLE_DECLARATIONS\n\n");
					
					int i = 0;
					
					for (NovaMethodDeclaration v : tree.getRoot().getInterfaceMethods())
					{
						// TODO: #1
//						if (v.getFileDeclaration().getLibrary() == l)
						{
							writer.append(getWriter(v.getVirtualMethod()).generateIndexDefinition(new StringBuilder(), i));
						}
//						else
//						{
//							writer.append("/*");
//							writer.append(getWriter(v).generateSourceName()).append(" " + i);
//							writer.append("*/\n");
//						}
						
						i++;
					}
					
					writer.append("\n");
					
					for (ClassDeclaration c : getAllClasses())
					{
						VTableList vtables = c.getVTableNodes();
						
						// TODO: #1
//						File lib = c.getFileDeclaration().getLibrary();
						
						// TODO: #1
//						if (lib == l || l == null)
						{
							writer.append("// ").append(c.getName()).append(" /////////////////////////////////////////////////////\n");
							
							// TODO: #1
							writer.append(getWriter(vtables.getExtensionVTable()).generateTypedef(new StringBuilder(), true/*l == null*/).append('\n'));
							writer.append(getWriter(vtables.getExtensionVTable()).generateExternDeclaration(new StringBuilder(), true/*l == null*/));
							
							writer.append("//////////////////////////////////////////////////////////////////////\n\n");
						}
					}
					
					// TODO: #1
					writer.append("#include <" + (/*l != null ? l.getName() : */SINGLE_FILE_BUILD_FILE_NAME) + ".h" + ">\n");
					
					Arrays.stream(getAllAndIMeanAllVirtualMethods())
						// TODO: #1
//						.filter(v -> v.getFileDeclaration().getLibrary() == l)
						.forEach(v -> writer.append(getWriter(v).generateHeader()));
					
					writer.append("\n");
					
					for (ClassDeclaration c : getAllClasses())
					{
						VTableList vtables = c.getVTableNodes();
						
						// TODO: #1
//						if (c.getFileDeclaration().getLibrary() == l)
						{
							writer.append("// ").append(c.getName()).append(" /////////////////////////////////////////////////////\n");
							
							for (NovaMethodDeclaration method : vtables.getExtensionVTable().getVirtualMethods())
							{
								writer.append(getWriter(method.getVirtualMethod()).generateHeader(new StringBuilder()));
							}
							
							writer.append("//////////////////////////////////////////////////////////////////////\n\n");
						}
					}
					
					writer.append("typedef struct nova_Interface_VTable nova_Interface_VTable;\n\n");
					
					writer.append("// ").append("Interface methods").append(" /////////////////////////////////////////////////////\n");
					
					NovaMethodDeclaration[] interfaceMethods = tree.getRoot().getProgram().getInterfaceMethods();
					
					for (NovaMethodDeclaration method : interfaceMethods)
					{
						// TODO: #1
//						if (method.getFileDeclaration().getLibrary() == l)
						{
							writer.append(getWriter(method.getVirtualMethod()).generateHeader(new StringBuilder()));
						}
					}
					
					writer.append("//////////////////////////////////////////////////////////////////////\n\n");
					
					// TODO: #1
//					if (l == null)
					{
						if (compileEngine.singleFile)
						{
							writer.append("\n#include <" + SINGLE_FILE_BUILD_FILE_NAME + ".h>\n");
						}
						
						// TODO: #1
//						for (Map.Entry<File, ArrayList<File>> entry : controller.libraryFiles.entrySet())
//						{
//							writer.write("#include <" + entry.getKey().getName() + ".h>\n");
//							writer.append("#include <").append(entry.getKey().getName()).append("_").append(VTABLE_DECLARATIONS_FILE_NAME).append(".h>\n");
//						}
						
						writer.append("\n#include <Nova.h>\n");
						
						if (compileEngine.singleFile)
						{
							writer.append("#include <InterfaceVTable.h>\n");
						}
						else
						{
							writer.append(getAllIncludes()).append('\n');
						}
					}
					
					for (ClassDeclaration c : getAllClasses())
					{
						VTableList vtables = c.getVTableNodes();
						// TODO: #1
//						File lib = c.getFileDeclaration().getLibrary();
						
						/*if (l == null)
						{
							vtables.forEach(vtable -> {
								writer.append(getWriter(vtable).generateHeader(new StringBuilder(), true).append('\n'));
							});
						}
						// TODO: #1
						else *///if (lib == l)
						{
							writer.append(getWriter(vtables).generateHeader(new StringBuilder()).append('\n'));
							//					getWriter(vtables).generateSource(builder).append('\n');
						}
					}
					
					writer.append("#endif");
				}, forceRecompile[0]);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
//		});
		
		this.forceRecompile = forceRecompile[0];
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
//		StringBuilder nativeAssignments = generateNativeVirtualMethodAssignments();
		StringBuilder vtableClassInstanceAssignments = generateVTableClassInstanceAssignments((NovaMethodDeclaration)mainMethod);
		StringBuilder vtableClassArray = generateVTableClassArray();
		StringBuilder vtableClassInstancePropertyAssignments = generateVTableClassInstancePropertyAssignments();
		
		FileDeclaration fileDeclaration = mainMethod.getFileDeclaration();
		
		if (mainMethod != null)
		{
//			FileDeclaration file = mainMethod.getFileDeclaration();
//			file.addChild(Import.decodeStatement(file, "import \"GC\"", file.getLocationIn(), true, false));
			Value gcInit = (Value)SyntaxTree.decodeIdentifierAccess(mainMethod, "GC.init()", mainMethod.getLocationIn(), true);
			Value gcColl = (Value)SyntaxTree.decodeIdentifierAccess(mainMethod, "GC.collect()", mainMethod.getLocationIn(), true);
			Value enter  = (Value)SyntaxTree.decodeIdentifierAccess(mainMethod, "Console.waitForEnter()", mainMethod.getLocationIn(), true);
			
			Instantiation nullConstructor = Instantiation.decodeStatement(mainMethod, "new Null()", mainMethod.getLocationIn(), true);
			
			NativeAnnotation annotation = NativeAnnotation.decodeStatement(mainMethod, "Native", "", mainMethod.getLocationIn(), true);
			annotation.onAfterDecoded();
			Constructor   strConstructor  = (Constructor)((MethodCall)Instantiation.decodeStatement(mainMethod, "new String(new Char[0])", mainMethod.getLocationIn(), true).getIdentifier()).getDeclaration();
			strConstructor.addAnnotation(annotation);
			
			BodyMethodDeclarationWriter runMain = (BodyMethodDeclarationWriter)getWriter(mainMethod.getProgram().getClassDeclaration("nova/System").getMethods("runMain")[0]);
			
			File header = new File(controller.outputDirectory, MAIN_FUNCTION_FILE_NAME + ".h");
			File source = new File(controller.outputDirectory, MAIN_FUNCTION_FILE_NAME + ".c");
			
			try
			{
				FileUtils.writeIfDifferent(header, writer ->
				{
					writer.write("#ifndef NOVA_MAIN_FUNCTION_HEADER\n");
					writer.write("#define NOVA_MAIN_FUNCTION_HEADER\n\n");
					
					if (!compileEngine.singleFile)
					{
						writer.write("#include <Nova.h>\n");
					}
					else
					{
						writer.write("#include <" + SINGLE_FILE_BUILD_FILE_NAME + ".h>\n");
						writer.write("#include <NovaExceptionHandling.h>\n");
					}
					
					// TODO: #1
//					for (Map.Entry<File, ArrayList<File>> entry : controller.libraryFiles.entrySet())
//					{
//						writer.write("#include <" + entry.getKey().getName() + ".h>\n");
//						writer.write("#include <" + entry.getKey().getName() + "_" + VTABLE_DECLARATIONS_FILE_NAME + ".h>\n");
//					}
					
					writer.write("#include <InterfaceVTable.h>\n");
					writer.write("#include <ExceptionHandler.h>\n");
					
					if (!compileEngine.singleFile)
					{
						writer.write("#include <");
						writer.write(getWriter(fileDeclaration).generateHeaderName());
						writer.write(">\n");
					}
					
					writer.write("\n#endif");
				});
				
				FileUtils.writeIfDifferent(source, writer ->
				{
					writer.append("#include \"").append(MAIN_FUNCTION_FILE_NAME).append(".h\"").append('\n').append('\n');
					
					writer.append("void novaInitProgramData(void* this)\n");
					writer.append("{\n");
//					writer.append(nativeAssignments).append('\n');
					writer.append(vtableClassInstanceAssignments).append('\n');
					writer.append(vtableClassInstancePropertyAssignments).append('\n');
					writer.append(vtableClassArray).append('\n');
					writer.append('}').append('\n').append('\n');
					
					writer.append("void novaCallStaticBlocks(void* this)\n");
					writer.append("{\n");
					writer.append(staticBlockCalls).append('\n');
					writer.append('}').append('\n').append('\n');
					
					writer.append("int main(int argc, char** argvs)").append('\n');
					writer.append("{").append('\n');
					writer.append(getWriter(gcInit).generateSource()).append('\n');
					writer.append("return nova_Nova_System_static_Nova_runMain(0, argc, argvs, ")
						.append(getWriter((ClosureDeclaration)runMain.node().getParameter("mainFunc")).generateTypeCast())
						.append('&').append(getWriter(mainMethod).generateSourceName())
						.append(", 0, 0, ")
						.append(getWriter((ClosureDeclaration)runMain.node().getParameter("initialize")).generateTypeCast())
						.append("&novaInitProgramData, ")
						.append("0, 0, ")
						.append(getWriter((ClosureDeclaration)runMain.node().getParameter("callStaticBlocks")).generateTypeCast())
						.append("&novaCallStaticBlocks, ")
						.append("0, 0);").append('\n');
					writer.append("}\n");
				});
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
	
	private StringBuilder generateStaticBlockCalls()
	{
		StringBuilder builder = new StringBuilder();
		
		Program root = tree.getRoot();
		
		for (int i = 0; i < root.getNumVisibleChildren(); i++)
		{
			FileDeclaration  file  = root.getVisibleChild(i);
			
			for (ClassDeclaration clazz : file.getClassDeclarations())
			{
//				TypeList<StaticBlock> blocks = clazz.getStaticBlockList();
//				
//				for (int j = 0; j < blocks.getNumVisibleChildren(); j++)
//				{
					StaticBlockWriter.generateMethodCall(builder, clazz).append(';').append('\n');
//				}
			}
		}
		
		return builder;
	}
}