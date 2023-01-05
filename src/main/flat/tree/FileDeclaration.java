package flat.tree;

import flat.Flat;
import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.annotations.Annotation;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.ObjectReference;
import flat.util.FileUtils;
import flat.util.Location;
import flat.util.SyntaxUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

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
	public StringBuilder	header;
	public String 			source;

	public File			file;

	public ArrayList<ClosureDeclaration> closures;
	public ArrayList<ClosureContext> contexts;

	public static int closureId, contextId;

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
			"flat/exception/ExceptionData",
			"flat/exception/Exception",
			"flat/exception/DivideByZeroException",
			"flat/primitive/number/Number",
			"flat/primitive/number/Byte",
			"flat/primitive/number/Short",
			"flat/primitive/number/Int",
			"flat/primitive/number/Long",
			"flat/primitive/number/Float",
			"flat/primitive/number/Double",
			"flat/primitive/Null",
			"flat/primitive/number/Char",
			"flat/primitive/Bool",
			"flat/datastruct/list/List",
			"flat/datastruct/list/Array",
			"flat/datastruct/list/IntRange",
			"flat/Math",
			"flat/Object",
			"flat/String",
			"flat/meta/Class",
			"flat/meta/Field",
			"flat/meta/Function",
		};
	}

	private final ArrayList<Annotation> pendingAnnotations = new ArrayList<>();

	public void addPendingAnnotation(Annotation annotation)
	{
		pendingAnnotations.add(annotation);
	}

	public void removePendingAnnotation(Annotation annotation)
	{
		pendingAnnotations.remove(annotation);
	}

	public boolean containsPendingAnnotationOfType(Class type)
	{
		return getPendingAnnotationOfType(type) != null;
	}

	public Annotation getPendingAnnotationOfType(Class type)
	{
		return getPendingAnnotationsOfType(type).stream().findFirst().orElse(null);
	}

	public ArrayList<Annotation> getPendingAnnotationsOfType(Class type)
	{
		ArrayList<Annotation> list = new ArrayList<>();

		for (Annotation a : pendingAnnotations)
		{
			if (type.isAssignableFrom(a.getClass()))
			{
				list.add(a);
			}

			Annotation b = a.getAnnotationOfType(type);

			if (b != null)
			{
				list.add(b);
			}
		}

		return list;
	}

	/**
	 * Instantiate and initialize the default values.
	 *
	 * @see Node#Node(Node, Location)
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
	 * @see Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 2;
	}

	public boolean isLibraryFile()
	{
		return getLibrary() != null;
	}

	public boolean isExcludedExternalFile(java.util.List<String> targetFileExtensions) {
		if (isExternalFile())
		{
			String extension = getExternalExtension();

			return targetFileExtensions.stream().noneMatch(extension::equals);
		}

		return false;
	}

	public File getLibrary()
	{
		return getController().getLibrary(file);
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
	public Import addImport(String loc) {
		return addImport(loc, false);
	}

	public Import addImport(String loc, String alias) {
		return addImport(loc, false, alias, false);
	}

	/**
	 * Add a static Import node from the given location.
	 *
	 * @param loc The location to add an import from.
	 * @return The generated Import node.
	 */
	public Import addStaticImport(String loc) {
		return addImport(loc, true);
	}

	public Import addImport(String loc, boolean staticImport) {
		return addImport(loc, staticImport, null, false);
	}

	public Import addImport(String loc, boolean staticImport, String alias, boolean force)
	{
		if (!force && (loc == null || containsImport(loc) || !SyntaxUtils.isAbsoluteClassLocation(loc) && getClassDeclaration() != null && getClassDeclaration().getName().equals(loc)))
		{
			return null;
		}

		Import importNode = Import.decodeStatement(this, "import " + (staticImport ? "static " : "") + "\"" + loc + "\"" + (alias != null ? " as " + alias : ""), getLocationIn(), true);

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

		return closureId++;
	}

	public int registerClosureContext(ClosureContext context)
	{
		contexts.add(context);

		return contextId++;
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
	public boolean containsImport(String importLocation, boolean absoluteLocation) {
		return containsImport(importLocation, absoluteLocation, false);
	}

	public boolean containsImport(String importLocation, boolean absoluteLocation, boolean aliased)
	{
		return getImport(importLocation, absoluteLocation, aliased) != null;
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
		if (className == null)
		{
			return null;
		}

		ClassDeclaration clazz = from.getParentClass(true);

		while (clazz != null)
		{
			if (clazz.getName().equals(className))
			{
				return clazz;
			}

			clazz = clazz.encapsulatingClass;
		}

		for (ClassDeclaration c : from.getFileDeclaration(true).getClassDeclarations())
		{
			if (c.getName().equals(className)) // TODO: need to check if at valid depth
			{
				return c;
			}
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
		for (int i = 0; getNumChildren() > super.getNumDefaultChildren() + getClassOffset() + i; i++)
		{
			ClassDeclaration c = (ClassDeclaration)getChild(super.getNumDefaultChildren() + getClassOffset() + i);

			if (c.getClassLocation(false, false).equals(className))
			{
				return c;
			}
		}
		for (int i = 0; getNumChildren() > super.getNumDefaultChildren() + getClassOffset() + i; i++)
		{
			ClassDeclaration c = (ClassDeclaration)getChild(super.getNumDefaultChildren() + getClassOffset() + i);

			if (c.getName().equals(className)) {
				return c;
			}
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
	 * For example: A getName() call for a FileDeclaration of Test.flat would
	 * return "Test"
	 *
	 * @return The name of the file without the extension.
	 */
	public String getName()
	{
		if (isExternalFile()) {
			return FileUtils.removeFileExtension(FileUtils.removeFileExtension(file.getName()));
		} else {
			return FileUtils.removeFileExtension(file.getName());
		}
	}

	@Override
	public FileDeclaration getOriginalFile() {
		return this;
	}

	/**
	 * @see Node#validate(int)
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);

		if (phase >= SyntaxTree.PHASE_METHOD_CONTENTS) {
			getController().processStep(28);
		} else if (phase >= SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
			getController().processStep(10);
		}

		if (result.skipValidation() || isExcludedExternalFile(getController().targetFileExtensions))
		{
			return result;
		}

		String phaseLabel = "???";

		switch (phase) {
			case SyntaxTree.PHASE_CLASS_DECLARATION: phaseLabel = "one"; break;
			case SyntaxTree.PHASE_INSTANCE_DECLARATIONS: phaseLabel = "two"; break;
			case SyntaxTree.PHASE_METHOD_CONTENTS: phaseLabel = "three"; break;
			case SyntaxTree.PHASE_PRE_GENERATION: phaseLabel = "four"; break;
		}

		String name = getPackage().getLocation() + "/" + getName();

		if (isExternalFile()) {
			name += '.' + getExternalExtension();
		}

		name += ".flat";

		getController().log("Phase " + phaseLabel + " validation for '" + name + "'");

		if (isExternalFile())
		{
			mergeFiles(getProgram().getFile(getClassDeclaration().getClassLocation(false)), phase);
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
				detach();

				result.skipCycle = true;
				result.nextIncrement = 0;
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
				Import clone = (Import)im.clone(other.getImportList(), Location.INVALID);
				clone.setOriginalFile(this);
				other.getImportList().addChild(clone);

				SyntaxTree.validateNodes(im, phase);
				SyntaxTree.validateNodes(clone, phase);
			}
		}

		ClassDeclaration thisClass = getClassDeclaration();
		ClassDeclaration otherClass = other.getClassDeclaration();

		thisClass.getInnerClasses(false).getVisibleListChildren().forEach(innerClass -> {
			innerClass.setOriginalFile(this);
			otherClass.addChild(innerClass);
		});

		thisClass.getExternalTypeListNode().getVisibleListChildren().forEach(external -> {
			external.setOriginalFile(this);
			otherClass.getExternalTypeListNode().addChild(external);
		});

		List[] fieldLists = new List[]{
			thisClass.getFieldList().getPublicFieldList(),
			thisClass.getFieldList().getPublicStaticFieldList(),
			thisClass.getFieldList().getPrivateFieldList(),
			thisClass.getFieldList().getPrivateStaticFieldList()
		};

		for (List thisList : fieldLists) {
			for (Node fieldNode : thisList.toArray()) {
				FieldDeclaration field = (FieldDeclaration)fieldNode;

				if (field.isUserMade()) {
					field.setOriginalFile(this);

					FieldDeclaration otherField = otherClass.getField(field.getName());

					if (otherField != null) {
						otherField.replaceWith(field);
					} else {
						otherClass.getFieldList().addChild(field);
					}

					SyntaxTree.validateNodes(field, phase);
				}
			}
		}

		MethodList[] methodLists = new MethodList[]{
			thisClass.getConstructorList(),
			thisClass.getMethodList(),
			thisClass.getPropertyMethodList()
		};

		for (MethodList thisList : methodLists) {
			for (FlatMethodDeclaration method : thisList.getMethods()) {
				if (method.isUserMade()) {
					method.setOriginalFile(this);

					MethodList.SearchFilter filter = new MethodList.SearchFilter();
					filter.checkAncestor = false;
					filter.checkInterfaces = false;
					MethodDeclaration otherMethod = otherClass.getMethod(new GenericCompatible[] { null }, method.getName(), filter, method.getParameterList().getTypes());

					if (otherMethod != null) {
						otherMethod.replaceWith(method);
					} else {
						otherClass.addChild(method);
					}

					method.objectReference = new ObjectReference(method);

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

//			if (!node.isUsed())
//			{
//				getImportList().removeChild(i);
//			}
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
	 * @see Node#addChild(Node)
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
			ClassDeclaration c = (ClassDeclaration) child;
			String location = c.getClassLocation();

			java.util.List<ClassDeclaration> dupes = getProgram().getVisibleListChildren().stream()
					.flatMap(f -> Arrays.stream(f.getClassDeclarations()))
					.filter(c2 -> c2 != c)
					.filter(c2 -> c2.getClassLocation().equals(location))
					.filter(c2 -> c2.genericOverload == c.genericOverload)
					.collect(Collectors.toList());

			if (dupes.size() > 0) {
				getProgram().getVisibleListChildren().stream()
					.flatMap(f -> Arrays.stream(f.getClassDeclarations()))
					.filter(c2 -> c2 != c)
					.filter(c2 -> c2.getClassLocation().equals(location))
					.filter(c2 -> c2.genericOverload == c.genericOverload)
					.collect(Collectors.toList());
				String classes = dupes.stream().map(ClassDeclaration::getClassLocation).collect(Collectors.joining(", "));
				SyntaxMessage.error("Duplicate class declaration for class '" + c.getClassLocation() + "' (" + classes + ")", child);
			}

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
		this.source = source;
	}

	/**
	 * Add all of the default imports that each file must include. The
	 * default imports are included within the {@link #DEFAULT_IMPORTS}
	 * array.
	 */
	private void addDefaultImports()
	{
		if (getProgram().getController().libraries.stream().anyMatch(l -> l.endsWith("/IO"))) {
			addImport("flat/io/Console");
		}
		if (getProgram().getController().libraries.stream().anyMatch(l -> l.endsWith("/System"))) {
			addImport("flat/system/System");
		}
		if (getProgram().getController().libraries.stream().anyMatch(l -> l.endsWith("/Test") || l.endsWith("/Test/src"))) {
			addStaticImport("flat/test/Test");
		}
		if (getProgram().getController().libraries.stream().anyMatch(l -> l.endsWith("/Stream") || l.endsWith("/Stream/src"))) {
			addStaticImport("flat/stream/StreamListExtensions");
		}

		for (String importLoc : DEFAULT_IMPORTS)
		{
			if (importLoc.length() > 0)
			{
				addImport(importLoc).markUsed();
			}
		}

		getProgram().getController().defaultImports.forEach(this::addImport);
		getProgram().getController().defaultStaticImports.forEach(this::addStaticImport);
	}

	public static FileDeclaration generateTemporaryFile(Node parent, Location locationIn)
	{
		FileDeclaration node = new FileDeclaration(parent, locationIn, new File("Temp"));

		return node;
	}

	public static FileDeclaration generateTemporaryHierarchy(Flat controller)
	{
		Program p = Program.generateTemporaryHierarchy(controller);

		FileDeclaration file = generateTemporaryFile(p, Location.INVALID);
		p.addChild(file);

		return file;
	}

	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public FileDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		FileDeclaration node = new FileDeclaration(temporaryParent, locationIn, null);

		return cloneTo(node, cloneChildren, cloneAnnotations);
	}

	/**
	 * @see Node#cloneTo(Node)
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
		node.closures = new ArrayList<>();

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

	public String getSource() {
		return source;
	}
}
