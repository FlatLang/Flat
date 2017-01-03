package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.FileUtils;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class used to organize the Files that are fed to the compiler.
 * Each file has a file name that is stored in the name field variable
 * of this class.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Feb 18, 2014 at 8:57:00 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class FileDeclaration extends Node
{
	public StringBuilder	header, source;
	
	public File			file;
	
	public ArrayList<ClosureDeclaration> closures;
	public ArrayList<ClosureContext> contexts;
	
	/**
	 * The default imports that each file uses.
	 */
	public static final String DEFAULT_IMPORTS[];
	
	/**
	 * Initialize the defaultImports constant.
	 */
	static
	{
		DEFAULT_IMPORTS = new String[]
		{
			"nova/exception/ExceptionData",
			"nova/exception/Exception",
			"nova/exception/DivideByZeroException",
			"nova/io/Console",
			"nova/primitive/number/Number",
			"nova/primitive/number/Byte",
			"nova/primitive/number/Short",
			"nova/primitive/number/Int",
			"nova/primitive/number/Long",
			"nova/primitive/number/Float",
			"nova/primitive/number/Double",
			"nova/primitive/Null",
			"nova/primitive/number/Char",
			"nova/primitive/Bool",
			"nova/datastruct/list/Array",
			"nova/datastruct/list/ImmutableArray",
			"nova/datastruct/list/IntArray",
			"nova/datastruct/list/CharArray",
			"nova/datastruct/list/DoubleArray",
			"nova/datastruct/list/IntRange",
			"nova/thread/Thread",
			"nova/thread/async/Async",
			"nova/thread/async/Task",
			"nova/gc/GC",
			"nova/math/Math",
			"nova/Object",
			"nova/String",
			"nova/System",
			"nova/meta/Class",
			"nova/regex/Pattern",
		};
	}
	
	/**
	 * Instantiate and initialize the default values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public FileDeclaration(Node temporaryParent, Location locationIn, File file)
	{
		super(temporaryParent, locationIn);
		
		closures  = new ArrayList<>();
		contexts  = new ArrayList<>();
		
		this.file = file;
		
		Package p = Package.generateDefaultPackage(this, Location.INVALID);
		addChild(p, this);
		
		ImportList imports = new ImportList(this, locationIn);
		addChild(imports, this);
		
		addDefaultImports();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 2;
	}
	
	/**
	 * Add all of the imports of the classes that are within the same
	 * directory of the specified file.
	 */
	public void addAutoImports()
	{
		getProgram().addAutoImports(this);
	}
	
	/**
	 * Add an Import node from the given location.
	 * 
	 * @param loc The location to add an import from.
	 * @return The generated Import node.
	 */
	public Import addImport(String loc)
	{
		if (containsImport(loc) || !SyntaxUtils.isAbsoluteClassLocation(loc) && getClassDeclaration() != null && getClassDeclaration().getName().equals(loc))
		{
			return null;
		}
		
		Import importNode = Import.decodeStatement(this, "import \"" + loc + "\"", getLocationIn(), true);
		
		addChild(importNode);
		
		return importNode;
	}
	
	public ClosureDeclaration[] getPublicClosures()
	{
		ArrayList<ClosureDeclaration> closures = new ArrayList<>();
		
		for (int i =  0; i < this.closures.size(); i++)
		{
			ClosureDeclaration c = this.closures.get(i);
			
			if (c.isPublic())
			{
				closures.add(c);
			}
		}
		
		return closures.toArray(new ClosureDeclaration[0]);
	}
	
	/**
	 * Register the ClosureDeclaration so that the FileDeclaration
	 * can define the closure type during generation.
	 * 
	 * @param closure The ClosureDeclaration to register.
	 * @return The id of the registered ClosureDeclaration.
	 */
	public int registerClosure(ClosureDeclaration closure)
	{
		closures.add(closure);
		
		return closures.size();
	}
	
	public int registerClosureContext(ClosureContext context)
	{
		contexts.add(context);
		
		return contexts.size();
	}
	
	public void unregisterClosure(ClosureDeclaration closure)
	{
		int index = closures.indexOf(closure);
		
		if (index >= 0)
		{
			closures.set(index, null);
		}
	}
	
	/**
	 * Get whether or not the given location has been imported.
	 * 
	 * @param importLocation The location of the import.
	 * @return Whether or not the given location has been imported.
	 */
	public boolean containsImport(String importLocation)
	{
		return containsImport(importLocation, true);
	}
	
	/**
	 * Get whether or not the given location has been imported.
	 * 
	 * @param importLocation The location of the import.
	 * @param absoluteLocation Whether or not the importLocation is an
	 * 		package relative path (true), or just a class name (false).
	 * @return Whether or not the given location has been imported.
	 */
	public boolean containsImport(String importLocation, boolean absoluteLocation)
	{
		return getImport(importLocation, absoluteLocation, false) != null;
	}
	
	/**
	 * Get the Import node with the given import location, if it exists.
	 * 
	 * @param importLocation The location of the import.
	 * @return The Import with the specified import location, if it
	 * 		exists.
	 */
	public Import getImport(String importLocation)
	{
		return getImport(importLocation, true);
	}
	
	/**
	 * Get the Import node with the given import location, if it exists.
	 * 
	 * @param importLocation The location of the import.
	 * @return The Import with the specified import location, if it
	 * 		exists.
	 */
	public Import getImport(String importLocation, boolean absoluteLocation)
	{
		return getImport(importLocation, absoluteLocation, true);
	}
	
	/**
	 * Get the Import node with the given import location, if it exists.
	 * 
	 * @param importLocation The location of the import.
	 * @param absoluteLocation Whether or not the importLocation is an
	 * 		package relative path (true), or just a class name (false).
	 * @return The Import with the specified import location, if it
	 * 		exists.
	 */
	public Import getImport(String importLocation, boolean absoluteLocation, boolean aliased)
	{
		Import node = getImportList().getImport(importLocation, absoluteLocation, aliased);
		
		if (node != null)
		{
			node.markUsed();
		}
		
		return node;
	}
	
	public ClassDeclaration getImportedClass(Node from, String className)
	{
		ClassDeclaration clazz = from.getParentClass(true);
		
		while (clazz != null)
		{
			if (clazz.getName().equals(className))
			{
				return clazz;
			}
			
			clazz = clazz.getParentClass();
		}
		
		Import i = getImport(className, false, true);
		
		return i != null ? i.getClassDeclaration() : null;
	}
	
	/**
	 * Get whether or not the given location is an external C import.
	 * 
	 * @return Whether or not the given location is an external C import.
	 */
	public boolean isExternalImport(String importLocation)
	{
		return getImportList().isExternal(importLocation);
	}
	
	/**
	 * Get whether or not the FileDeclaration contains the ClassDeclaration with
	 * the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>containsClass("Person")</code>" would return
	 * true.
	 * 
	 * @param className The name of the class to search for.
	 * @return Whether or not the FileDeclaration contains the ClassDeclaration
	 * 		with the specified name.
	 */
	public boolean containsClass(String className)
	{
		return getClassDeclaration(className) != null;
	}
	
	/**
	 * Get the FileDeclaration's ClassDeclaration with the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getClass("Person")</code>" would return the
	 * ClassDeclaration for the "<code>age</code>" int field.
	 * 
	 * @param className The name of the class to search for.
	 * @return The ClassDeclaration for the class, if it exists.
	 */
	public ClassDeclaration getClassDeclaration(String className)
	{
		ClassDeclaration clazz = getClassDeclaration();
		
		if (clazz != null && clazz.getName().equals(className))
		{
			return clazz;
		}
		
		return null;
	}
	
	public ClassDeclaration[] getClassDeclarations()
	{
		ArrayList<ClassDeclaration> classes = new ArrayList<>();
		
		for (int i = 0; getNumChildren() > super.getNumDefaultChildren() + getClassOffset() + i; i++)
		{
			classes.add((ClassDeclaration)getChild(super.getNumDefaultChildren() + getClassOffset() + i));
		}
		
		return classes.toArray(new ClassDeclaration[0]);
	}
	
	public Package getPackage()
	{
		return (Package)getChild(super.getNumDefaultChildren() + 0);
	}
	
	private void setPackage(Package n)
	{
		getPackage().replaceWith(n);
	}
	
	private int getClassOffset()
	{
		int offset = 1;
		
		if (getPackage() != null)
		{
			if (getNumChildren() < getNumDecodedChildren() + 1)
			{
//				SyntaxMessage.error("Missing class declaration", this);
			}
			
			offset++;
		}
		
		return offset;
	}
	
	/**
	 * Get the ClassDeclaration of the class that is contained within the file.
	 * 
	 * @return The ClassDeclaration instance from the file.
	 */
	public ClassDeclaration getClassDeclaration()
	{
		if (getNumChildren() > super.getNumDefaultChildren() + getClassOffset())
		{
			return (ClassDeclaration)getChild(super.getNumDefaultChildren() + getClassOffset());
		}
		
		return null;
	}
	
	/**
	 * Get the File that this FileDeclaration represents.
	 * 
	 * @return The File that this FileDeclaration represents.
	 */
	public File getFile()
	{
		return file.getAbsoluteFile();
	}
	
	/**
	 * Get the name of the file without the extension.<br>
	 * <br>
	 * For example: A getName() call for a FileDeclaration of Test.nova would
	 * return "Test"
	 * 
	 * @return The name of the file without the extension.
	 */
	public String getName()
	{
		return FileUtils.removeFileExtension(file.getName());
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			if (Arrays.stream(getClassDeclarations()).noneMatch(clazz ->
			{
				if (!getName().substring(0, getName().indexOf('.') > 0 ? getName().indexOf('.') : getName().length()).equals(clazz.getName()))
				{
					SyntaxMessage.error("The name of the class '" + clazz.getName() + "' must be the same as the file '" + getName() + "' that it is contained within", this, false);
					
					return false;
				}
				
				return true;
			}))
			{
				return result.errorOccurred();
			}
		}
		else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			validateImports();
		}
		else if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (isExternalFile())
			{
				mergeFiles(getProgram().getFile(getClassDeclaration().getClassLocation(false)), phase);
				
				detach();
				
				result.skipCycle = true;
			}
		}
		
		return result;
	}
	
	public void mergeFiles(FileDeclaration other, int phase)
	{
		for (int i = getImportList().getNumVisibleChildren() - 1; i >= 0; i--)
		{
			Import im = getImportList().getVisibleChild(i);
			
			if (!other.getImportList().containsImport(im.location))
			{
				other.getImportList().addChild(im);
				
				SyntaxTree.validateNodes(im, phase);
			}
		}
		
		ClassDeclaration thisClass = getClassDeclaration();
		ClassDeclaration otherClass = other.getClassDeclaration();
		
		MethodList[] methodLists = new MethodList[] { thisClass.getMethodList() };//, thisClass.getPropertyMethodList() };
		MethodList[] otherMethodLists = new MethodList[] { otherClass.getMethodList(), otherClass.getPropertyMethodList() };
		
		for (int i = 0; i < methodLists.length; i++)
		{
			MethodList thisList = methodLists[i];
			MethodList otherList = otherMethodLists[i];
			
			for (NovaMethodDeclaration method : thisList.getMethods())
			{
				if (method.isUserMade())
				{
					MethodDeclaration otherMethod = otherClass.getMethod((GenericCompatible)null, method.getName(), method.getParameterList().getTypes());
					
					if (otherMethod != null)
					{
						otherMethod.replaceWith(method);
					}
					else
					{
						otherClass.addChild(method);
					}
					
					SyntaxTree.validateNodes(method, phase);
				}
			}
		}
	}
	
	public boolean isExternalFile()
	{
		return getExternalExtension() != null;
	}
	
	public String getExternalExtension()
	{
		int first = getFile().getName().indexOf('.');
		int last = getFile().getName().lastIndexOf('.');
		
		if (first != last)
		{
			return getFile().getName().substring(first + 1, last).toLowerCase();
		}
		
		return null;
	}
	
	private void validateImports()
	{
		for (int i = getImportList().getNumChildren() - 1; i >= 0; i--)
		{
			Import node = getImportList().getChild(i);
			
			if (!node.isUsed())
			{
				getImportList().removeChild(i);
			}
		}
	}
	
	/**
	 * Get the ImportList that contains all of the imports used within
	 * the file.
	 * 
	 * @return The ImportList instance.
	 */
	public ImportList getImportList()
	{
		return (ImportList)getChild(super.getNumDefaultChildren() + 1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#addChild(Node)
	 */
	@Override
	public void addChild(Node child)
	{
		if (child instanceof Package)
		{
			if (!getPackage().isDefaultPackage())
			{
				SyntaxMessage.error("Package statement must be the first statement in the file", child);
			}
			
			setPackage((Package)child);
		}
		else if (child instanceof Import)
		{
			getImportList().addChild(child);
		}
		else if (child instanceof ClassDeclaration)
		{
			super.addChild(child);
		}
		else
		{
			SyntaxMessage.error("Unexpected statement", child);
		}
	}
	
	/**
	 * Set the header output String for the File.
	 * 
	 * @param header The header output String.
	 */
	public void setHeader(String header)
	{
		this.header = new StringBuilder(header);
	}
	
	/**
	 * Set the source output String for the File.
	 * 
	 * @param source The source output String.
	 */
	public void setSource(String source)
	{
		this.source = new StringBuilder(source);
	}
	
	/**
	 * Add all of the default imports that each file must include. The
	 * default imports are included within the {@link #DEFAULT_IMPORTS}
	 * array.
	 */
	private void addDefaultImports()
	{
		for (String importLoc : DEFAULT_IMPORTS)
		{
			if (importLoc.length() > 0)
			{
				addImport(importLoc).markUsed();
			}
		}
	}
	
	public static FileDeclaration generateTemporaryFile(Node parent, Location locationIn)
	{
		FileDeclaration node = new FileDeclaration(parent, locationIn, new File("Temp"));
		
		return node;
	}
	
	public static FileDeclaration generateTemporaryHierarchy(Nova controller)
	{
		Program p = Program.generateTemporaryHierarchy(controller);
		
		FileDeclaration file = generateTemporaryFile(p, Location.INVALID);
		p.addChild(file);
		
		return file;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public FileDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		FileDeclaration node = new FileDeclaration(temporaryParent, locationIn, null);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public FileDeclaration cloneTo(FileDeclaration node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link FileDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FileDeclaration cloneTo(FileDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.file     = new File(file.getAbsolutePath());
		node.closures = new ArrayList<ClosureDeclaration>();
		
		for (ClosureDeclaration c : closures)
		{
			node.closures.add(c);
		}
		
		return node;
	}
	
	/**
	 * Get the name of the file as the toString().
	 * 
	 * @return The name of the file.
	 */
	public String toString()
	{
		return file.getName();
	}
	
	/**
	 * Test the FileDeclaration class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}