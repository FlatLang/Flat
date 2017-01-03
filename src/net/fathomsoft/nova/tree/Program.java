package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.annotations.Annotation;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Node extension that represents a whole Nova program. The
 * purpose of this Node is to keep track of each FileDeclaration within
 * a compiled program and contain methods to manipulate the
 * FileDeclarations.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2 Apr 14, 2014 at 11:52:33 PM
 * @version	v0.2.35 Oct 5, 2014 at 11:22:42 PM
 */
public class Program extends TypeList<FileDeclaration>
{
	public boolean decodeShorthandActions;
	
	private SyntaxTree tree;
	
	private Nova       controller;
	
	private NovaMethodDeclaration[] interfaceMethods;
	private ClosureDeclaration[]	closures;
	
	private final HashMap<String, Integer>	files = new HashMap<>();
	
	private int uniqueId = 1;
	
	private ArrayList<Annotation> pendingAnnotations = new ArrayList<>();
	
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
		for (Annotation a : pendingAnnotations)
		{
			if (type.isAssignableFrom(a.getClass()))
			{
				return a;
			}
			
			Annotation b = a.getAnnotationOfType(type);
			
			if (b != null)
			{
				return b;
			}
		}
		
		return null;
	}
	
	public int getUniqueId()
	{
		return uniqueId++;
	}
	
	/**
	 * Instantiate and initialize a Program that contains a reference
	 * to the compiler's controller.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 * 
	 * @param controller The controller of the compiler.
	 */
	public Program(Nova controller, SyntaxTree tree)
	{
		super(null, null);
		
		this.controller = controller;
		this.tree       = tree;
	}

	public SyntaxTree getTree()
	{
		return tree;
	}

	/**
	 * Override addChild(Node) method to make it synchronized. Needs
	 * to be synchronized so that the threads dont try to write their
	 * file nodes to the Program at the same time and end up creating
	 * empty spaces in the tree.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#addChild(Node)
	 */
	public synchronized void addChild(Node child)
	{
		FileDeclaration file = (FileDeclaration)child;
		
		if (file.isExternalFile())
		{
			String extension = file.getExternalExtension();
			
			if (!extension.equals(controller.targetFileExtension))
			{
				return;
			}
		}
		
		super.addChild(child);
	}
	
	public NovaMethodDeclaration[] getInterfaceMethods()
	{
		if (interfaceMethods != null)
		{
			return interfaceMethods;
		}
		
		ArrayList<NovaMethodDeclaration> methods = new ArrayList<>();
		
		for (FileDeclaration file : tree.getFiles())
		{
			for (ClassDeclaration clazz : file.getClassDeclarations())
			{
				if (clazz instanceof Trait)
				{
					NovaMethodDeclaration[][] methodLists = new NovaMethodDeclaration[][] { clazz.getMethods(), clazz.getPropertyMethodList().getMethods() }; 
					
					for (NovaMethodDeclaration[] list : methodLists)
					{
						for (NovaMethodDeclaration method : list)
						{
							VirtualMethodDeclaration virtual = method.getVirtualMethod();
							
							if (virtual != null && !methods.contains(virtual))
							{
								methods.add(virtual);
							}
						}
					}
				}
			}
		}
		
		interfaceMethods = methods.toArray(new NovaMethodDeclaration[0]);
		
		return interfaceMethods;
	}
	
	public ClosureDeclaration[] getPublicClosures()
	{
		if (closures != null)
		{
			return closures;
		}
		
		ArrayList<ClosureDeclaration> list = new ArrayList<>();
		
		for (FileDeclaration file : tree.getFiles())
		{
			ClosureDeclaration array[] = file.getPublicClosures();
			
			for (ClosureDeclaration c : array)
			{
				list.add(c);
			}
		}
		
		closures = list.toArray(new ClosureDeclaration[0]);
		
		return closures;
	}
	
	/**
	 * Add Imports for all of the classes within the same package as the
	 * given FileDeclaration.
	 */
	public void addAutoImports()
	{
		forEachVisibleListChild(file -> file.addAutoImports());
	}
	
	/**
	 * Add Imports for all of the classes within the same package as the
	 * given FileDeclaration.
	 * 
	 * @param file The FileDeclaration to add the Imports to.
	 */
	public void addAutoImports(FileDeclaration file)
	{
		String dir = file.getFile().getParent();
		
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			FileDeclaration child = getVisibleChild(i);
			
			if (!child.isExternalFile() && file != child && dir.equals(child.getFile().getParent()))
			{
				ClassDeclaration clazz = child.getClassDeclaration();
				
				if (clazz != null && clazz instanceof ExtensionDeclaration == false)
				{
					file.addImport(clazz.getClassLocation());
				}
			}
		}
	}
	
	/**
	 * Get the compiler's controller. The controller is used for
	 * logging, error output, and other compiler options.
	 * 
	 * @return The compiler's controller instance.
	 */
	public Nova getController()
	{
		return controller;
	}
	
	public String[] getClassLocationComponents(String classLocation)
	{
		if (classLocation != null)
		{
			int index = classLocation.indexOf('.');
			
			if (index > 0)
			{
				return new String[] { classLocation.substring(0, index), classLocation.substring(index + 1) };
			}
		}
		
		return new String[] { classLocation };
	}
	
	/**
	 * Get the Program's ClassDeclaration with the specified name.<br>
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
	 * ClassDeclaration for the "<code>Person</code>" class.
	 * 
	 * @param classLocation The name of the class to search for.
	 * @return The ClassDeclaration for the class, if it exists.
	 */
	public ClassDeclaration getClassDeclaration(String classLocation)
	{
		String[] components = getClassLocationComponents(classLocation);
		
		if (!files.containsKey(components[0]))
		{
			return null;
		}
		
		int index = files.get(components[0]);
		
		FileDeclaration node = getVisibleChild(index);
		
		String className = SyntaxUtils.getClassName(components.length > 1 ? components[1] : components[0]);
		
		return node.getClassDeclaration(className);
	}
	
	/**
	 * Get the Program's FileDeclaration with the specified name.
	 * 
	 * @param filename The name of the file to search for.
	 * @return The FileDeclaration for the file, if it exists.
	 */
	public FileDeclaration getFile(String filename)
	{
		int index = files.get(filename);
		
		return getChild(index);
	}
	
	@Override
	public void onChildRemoved(Node child)
	{
		if (child instanceof FileDeclaration)
		{
			FileDeclaration file = (FileDeclaration)child;
			
			if (file.getClassDeclaration() != null)
			{
				int index = 0;
				
				if (!file.isExternalFile())
				{
					index = files.remove(file.getClassDeclaration().getClassLocation());
				}
				else
				{
					index = files.get(file.getClassDeclaration().getClassLocation());
				}
				
				for (Map.Entry<String, Integer> p : files.entrySet())
				{
					if (p.getValue() >= index)
					{
						files.put(p.getKey(), p.getValue() - 1);
					}
				}
			}
		}
	}
	
	/**
	 * Get the phase that the SyntaxTree is currently decoding in.
	 * 
	 * @return The phase that the SyntaxTree is currently decoding in.
	 */
	public int getPhase()
	{
		return tree.getPhase();
	}
	
	public static Program generateTemporaryProgram(Nova controller)
	{
		return controller.getTree().getRoot();
	}
	
	public static Program generateTemporaryHierarchy(Nova controller)
	{
		return generateTemporaryProgram(controller);
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		if (phase == SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			for (int i = 0; i < getNumVisibleChildren(); i++)
			{
				FileDeclaration file = getVisibleChild(i);
				
				ClassDeclaration clazz = file.getClassDeclaration();
				
				if (clazz != null)
				{
					String location = clazz.getClassLocation();
					
					files.put(location, i);
				}
			}
		}
		
		return super.validate(phase);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Program clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Program node = new Program(controller, tree);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Program cloneTo(Program node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Program} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Program cloneTo(Program node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.controller = controller;
		
		return node;
	}
	
	/**
	 * Test the Program class type to make sure everything
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