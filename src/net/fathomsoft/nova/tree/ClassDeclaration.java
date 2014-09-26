package net.fathomsoft.nova.tree;

import java.util.ArrayList;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.MethodList.SearchFilter;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.FieldList;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;
import net.fathomsoft.nova.tree.variables.StaticFieldList;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Declaration extension that represents the declaration of a class
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:15:51 PM
 * @version	v0.2.30 Sep 2, 2014 at 7:58:20 PM
 */
public class ClassDeclaration extends InstanceDeclaration
{
	private String	extendedClass;
	
	private	String	implementedClasses[];
	
	/**
	 * Instantiate and initialize default values for a class node.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ClassDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		implementedClasses = new String[0];
		
		setType("class");
		
		FieldList             fields         = new FieldList(this, Location.INVALID);
		MethodList            constructors   = new MethodList(this, Location.INVALID);
		MethodList            destructors    = new MethodList(this, Location.INVALID);
		MethodList            methods        = new MethodList(this, Location.INVALID);
		ExternalTypeList      externalTypes  = new ExternalTypeList(this, Location.INVALID);
		FieldList             externalFields = new FieldList(this, Location.INVALID);
		TypeList<StaticBlock> staticBlocks   = new TypeList<StaticBlock>(this, Location.INVALID);
		VTable                vtable         = new VTable(this, Location.INVALID);
		
		addChild(fields, this);
		addChild(constructors, this);
		addChild(destructors, this);
		addChild(methods, this);
		addChild(externalTypes, this);
		addChild(externalFields, this);
		addChild(staticBlocks, this);
		addChild(vtable, this);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 8;
	}
	
	/**
	 * Get the FieldList instance that contains the list of fields
	 * that this class node contains.
	 * 
	 * @return The FieldList for this class node.
	 */
	public FieldList getFieldList()
	{
		return (FieldList)getChild(super.getNumDefaultChildren() + 0);
	}
	
	/**
	 * Get the MethodList instance that contains the list of
	 * constructors that this class node contains.
	 * 
	 * @return The MethodList for constructors of this class node.
	 */
	public MethodList getConstructorList()
	{
		return (MethodList)getChild(super.getNumDefaultChildren() + 1);
	}
	
	/**
	 * Get the MethodList instance that contains the list of
	 * destructors that this class node contains.
	 * 
	 * @return The MethodList for destructors of this class node.
	 */
	public MethodList getDestructorList()
	{
		return (MethodList)getChild(super.getNumDefaultChildren() + 2);
	}
	
	/**
	 * Get the MethodList instance that contains the list of methods
	 * that this class node contains.
	 * 
	 * @return The MethodList for this class node.
	 */
	public MethodList getMethodList()
	{
		return (MethodList)getChild(super.getNumDefaultChildren() + 3);
	}
	
	/**
	 * Get the ExternalTypeList instance that contains the list of
	 * external types that this class node contains.
	 * 
	 * @return The ExternalTypeList for this class node.
	 */
	public ExternalTypeList getExternalTypeListNode()
	{
		return (ExternalTypeList)getChild(super.getNumDefaultChildren() + 4);
	}
	
	/**
	 * Get the FieldList instance that contains the list of external
	 * fields that this class node contains.
	 * 
	 * @return The external FieldList for this class node.
	 */
	public FieldList getExternalFieldsListNode()
	{
		return (FieldList)getChild(super.getNumDefaultChildren() + 5);
	}
	
	/**
	 * Get the AssignmentMethod instance that contains the list of
	 * default field assignments and vtable assignment.
	 * 
	 * @return The AssignmentMethod for this class node.
	 */
	public AssignmentMethod getAssignmentMethodNode()
	{
		return (AssignmentMethod)getMethodList().getChild(getMethodList().getNumChildren() - 1);
	}
	
	/**
	 * Get the List that contains the StaticBlocks for the specified
	 * Class.
	 * 
	 * @return The StaticBlock List.
	 */
	public TypeList<StaticBlock> getStaticBlockList()
	{
		return (TypeList<StaticBlock>)getChild(super.getNumDefaultChildren() + 6);
	}
	
	/**
	 * Get the VTableNode instance that contains the list of pointers
	 * to the virtual methods of a class, if any virtual methods, or
	 * any methods of the class are overridden..
	 * 
	 * @return The VTableNode for this class node.
	 */
	public VTable getVTableNode()
	{
		return (VTable)getChild(super.getNumDefaultChildren() + 7);
	}
	
	/**
	 * Get whether or not the class contains any virtual methods. For
	 * more information on what virtual methods are, see {@link #getVirtualMethods()}
	 * 
	 * @return Whether or not the class contains any virtual methods.
	 */
	public boolean containsVirtualMethods()
	{
		return getVirtualMethods().length > 0;
	}
	
	/**
	 * Get an array containing all of the virtual methods that this class
	 * and its ancestors contain.<br><br>
	 * <b>What are virtual methods?</b><br>
	 * Virtual methods are methods that have been overridden, or override
	 * a method.
	 * 
	 * @return An array containing all of the virtual methods that the
	 * 		class and its ancestors contain.
	 */
	public NovaMethodDeclaration[] getVirtualMethods()
	{
		ArrayList<NovaMethodDeclaration> methods = new ArrayList<NovaMethodDeclaration>();
		
		addVirtualMethods(methods, this);
		
		return methods.toArray(new NovaMethodDeclaration[0]);
	}
	
	/**
	 * Add all of the virtual methods that the specified class contains
	 * to the given method list.
	 * 
	 * @param methods The list of methods to add the found virtual method
	 * 		data to.
	 */
	private void addVirtualMethods(ArrayList<NovaMethodDeclaration> methods, ClassDeclaration context)
	{
		if (getExtendedClassName() != null)
		{
			getExtendedClass().addVirtualMethods(methods, this);
		}
		
		MethodList list = getMethodList();
		
		for (int i = 0; i < list.getNumVisibleChildren(); i++)
		{
			MethodDeclaration m = (MethodDeclaration)list.getChild(i);
			
			if (!m.isExternal())
			{
				NovaMethodDeclaration method = (NovaMethodDeclaration)m;
				
				if (method.getParentClass() == this && method.isVirtual())
				{
					NovaMethodDeclaration existing = getMethod(method, methods);
					
					if (existing != null)
					{
						int index = methods.indexOf(existing);
						
						methods.set(index, method);
					}
					else
					{
						methods.add(method);
					}
				}
			}
		}
	}
	
	/**
	 * Check to see whether or not the given methods list already contains
	 * a compatible method with the given 'method' method.
	 * 
	 * @param method The method to see if the methods list contains.
	 * @param methods The list of methods to search through.
	 * @return Whether or not the given method is contained within the
	 * 		methods list.
	 */
	private boolean containsMethod(NovaMethodDeclaration method, ArrayList<NovaMethodDeclaration> methods)
	{
		return getMethod(method, methods) != null;
	}
	
	private NovaMethodDeclaration getMethod(NovaMethodDeclaration method, ArrayList<NovaMethodDeclaration> methods)
	{
		for (NovaMethodDeclaration m : methods)
		{
			// TODO: need to make this more strict.
			if (m.getName().equals(method.getName()) && m.areCompatibleParameterTypes(false, method.getParameterList().getTypes()))// method.areCompatibleParameterTypes(m.getParameterList().getTypes()))
			{
				return m;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#isExternalType()
	 */
	@Override
	public boolean isExternalType()
	{
		return false;
	}
	
	public boolean isRelatedTo(ClassDeclaration node)
	{
		return isOfType(node) || node.isOfType(this);
	}
	
	/**
	 * Check whether or not the specified ClassDeclaration extends the given
	 * ClassDeclaration.
	 * 
	 * @param node The ClassDeclaration to check if the specified Class
	 * 		extends.
	 * @return Whether or not the specified ClassDeclaration extends the given
	 * 		ClassDeclaration.
	 */
	public boolean isOfType(ClassDeclaration node)
	{
		if (node == null)
		{
			return false;
		}
		
		ClassDeclaration clazz = this;
		
		while (clazz != null)
		{
			if (clazz == node)
			{
				return true;
			}
			
			clazz = clazz.getExtendedClass();
		}
		
		if (SyntaxUtils.arePrimitiveTypesCompatible(SyntaxUtils.getWrapperClassPrimitiveName(node.getType()), SyntaxUtils.getWrapperClassPrimitiveName(getType())))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Get the class that this class node extends.<br>
	 * <br>
	 * For instance: "public class ClassName extends SuperClass"
	 * The "SuperClass" is a name of the class that the "ClassName" class
	 * extends.<br>
	 * <br>
	 * A class node can only extend one class.
	 * 
	 * @return The ClassDeclaration instance of the class that is extended.
	 */
	public ClassDeclaration getExtendedClass()
	{
		if (getExtendedClassName() == null)
		{
			return null;
		}
		
		return SyntaxUtils.getImportedClass(getFileDeclaration(), getExtendedClassName());
	}
	
	/**
	 * Get the name of the class that this node extends.
	 * 
	 * @return The name of the class that this node extends.
	 */
	public String getExtendedClassName()
	{
		return extendedClass;
	}
	
	/**
	 * Set the class that this class node will extend.<br>
	 * <br>
	 * For instance: "public class ClassName extends SuperClass"
	 * The "SuperClass" is a class that is extended by the "ClassName"
	 * class.<br>
	 * <br>
	 * A class node can only extend one class.
	 * 
	 * @param extendedClass The name of the class that is to be
	 * 		extended.
	 */
	public void setExtendedClass(String extendedClass)
	{
		this.extendedClass = extendedClass;
	}
	
	/**
	 * Get the class that this class node extends.<br>
	 * <br>
	 * For instance: "public class ClassName extends SuperClass"
	 * The "SuperClass" is a name of the class that the "ClassName" class
	 * extends.<br>
	 * <br>
	 * A class node can only extend one class.
	 * 
	 * @return The ClassDeclaration instance of the class that is extended.
	 */
	public ClassDeclaration[] getImplementedClasses()
	{
		Program program = getProgram();
		
		ClassDeclaration classes[] = new ClassDeclaration[implementedClasses.length];
		
		for (int i = 0; i < classes.length; i++)
		{
			classes[i] = program.getClassDeclaration(implementedClasses[i]);
		}
		
		return classes;
	}
	
	/**
	 * Get an ArrayList instance that contains all of the interfaces
	 * that are implemented by this class node.
	 * 
	 * @return An ArrayList instance that contains the interface names.
	 */
	public String[] getImplementedClassNames()
	{
		return implementedClasses;
	}
	
	/**
	 * Add a class to the list of implemented classes of this class
	 * instance.<br>
	 * <br>
	 * For instance: "public class ClassName implements InterfaceName"
	 * The "InterfaceName" is an interface that is implemented by
	 * the "ClassName" class.<br>
	 * <br>
	 * A class node can implement as many interfaces as it needs to.
	 * 
	 * @param implementedClass The name of the class that is to be
	 * 		implemented.
	 */
	public void addImplementedClass(String implementedClass)
	{
		implementedClasses = StringUtils.appendString(implementedClasses, implementedClass);
	}
	
	/**
	 * Get whether or not the ClassDeclaration contains an ExternalType with
	 * the specified type name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	external type FILE;
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getType("FILE")</code>" would
	 * return the ExternalType for the "<code>FILE</code>" external
	 * type.
	 * 
	 * @param typeName The name of the external type to search for.
	 * @return Whether or not the ClassDeclaration contains the Method with
	 * 		the specified name.
	 */
	public boolean containsExternalType(String typeName)
	{
		return getExternalTypeListNode().containsType(typeName);
	}
	
	/**
	 * Get the ClassDeclaration's ExternalType with the specified type.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	external type FILE;
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getType("FILE")</code>" would
	 * return the ExternalType for the "<code>FILE</code>" external
	 * type.
	 * 
	 * @param typeName The name of the external type to search for.
	 * @return The ExternalType for the external type, if it exists.
	 */
	public ExternalType getExternalType(String typeName)
	{
		return getExternalTypeListNode().getType(typeName);
	}
	
	/**
	 * Get whether or not the ClassDeclaration contains the Field with the
	 * specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	private int age;
	 * 	private String name;
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>containsField("age")</code>" would return true.
	 * 
	 * @param fieldName The name of the field to search for.
	 * @return Whether or not the ClassDeclaration contains the Field with
	 * 		the specified name.
	 */
	public boolean containsField(String fieldName)
	{
		return getField(fieldName) != null;
	}
	
	/**
	 * Get the ClassDeclaration's Field with the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	private int age;
	 * 	private String name;
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getField("age")</code>" would return the
	 * Field for the "<code>age</code>" int field.
	 * 
	 * @param fieldName The name of the field to search for.
	 * @return The Field for the field, if it exists.
	 */
	public FieldDeclaration getField(String fieldName)
	{
		return getField(fieldName, true);
	}
	
	/**
	 * Get the ClassDeclaration's Field with the specified name.
	 * 
	 * @param fieldName The name of the field to search for.
	 * @param checkAncestor Whether or not to check the ancestor class
	 * 		if the field is not found.
	 * @return
	 */
	private FieldDeclaration getField(String fieldName, boolean checkAncestor)
	{
		FieldDeclaration field = getFieldList().getField(fieldName);
		
		if (field == null)
		{
			field = getExternalFieldsListNode().getField(fieldName);
		
			if (field == null && checkAncestor && getExtendedClassName() != null)
			{
				field = getExtendedClass().getField(fieldName);
			}
		}
		
		return field;
	}
	
	/**
	 * Get the method with the given methodName and parameterTypes. If a
	 * method with the specified name and parameter types does not exist,
	 * null is returned.
	 * 
	 * @param methodName The name of the method to search for.
	 * @param parameterTypes An array of types that the parameters of the
	 * 		searching method must be compatible with.
	 * @return The compatible method with the given method name.
	 */
	public MethodDeclaration getMethod(String methodName, Value ... parameterTypes)
	{
		return getMethod(methodName, SearchFilter.DEFAULT, parameterTypes);
	}
	
	/**
	 * Get the method with the given methodName and parameterTypes. If a
	 * method with the specified name and parameter types does not exist,
	 * null is returned.
	 * 
	 * @param methodName The name of the method to search for.
	 * @param parameterTypes An array of types that the parameters of the
	 * 		searching method must be compatible with.
	 * @return The compatible method with the given method name.
	 */
	public MethodDeclaration getMethod(String methodName, SearchFilter filter, Value ... parameterTypes)
	{
		MethodDeclaration methods[] = getMethods(methodName, parameterTypes.length, filter);
		
		for (MethodDeclaration method : methods)
		{
			if (method.areCompatibleParameterTypes(false, parameterTypes))// && SyntaxUtils.isTypeCompatible(getProgram(), method.getType(), returnType))
			{
				return method;
			}
		}
		
		return null;
	}
	
	/**
	 * Get all of the methods that have the given methodName and the given
	 * number of parameters.
	 * 
	 * @param methodName The name of the methods to search for.
	 * @param numParams The number of parameters that the methods can
	 * 		have.
	 * @return An array of methods that are compatible with the given
	 * 		data.
	 */
	public MethodDeclaration[] getMethods(String methodName, int numParams)
	{
		return getMethods(methodName, numParams, SearchFilter.DEFAULT);
	}
	
	/**
	 * Get all of the methods that have the given methodName and the given
	 * number of parameters.
	 * 
	 * @param methodName The name of the methods to search for.
	 * @param numParams The number of parameters that the methods can
	 * 		have.
	 * @return An array of methods that are compatible with the given
	 * 		data.
	 */
	public MethodDeclaration[] getMethods(String methodName, int numParams, SearchFilter filter)
	{
		ArrayList<MethodDeclaration> output = new ArrayList<MethodDeclaration>();
		
		MethodDeclaration methods[] = getMethods(methodName, filter);
		
		for (MethodDeclaration method : methods)
		{
			if (method.getParameterList().getNumVisibleChildren() == numParams)
			{
				output.add(method);
			}
		}
		
		return output.toArray(new MethodDeclaration[0]);
	}
	
	/**
	 * Get whether or not the ClassDeclaration contains the Method with the
	 * specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	public void doSomething()
	 * 	{
	 * 		...
	 * 	}
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>containsMethod("doSomething")</code>" would
	 * return true.
	 * 
	 * @param methodName The name of the method to search for.
	 * @return Whether or not the ClassDeclaration contains the Method with
	 * 		the specified name.
	 */
	public boolean containsMethod(String methodName)
	{
		return getMethods(methodName).length > 0;
	}
	
	/**
	 * Get the ClassDeclaration's Method with the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	public void doSomething()
	 * 	{
	 * 		...
	 * 	}
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getMethod("doSomething")</code>" would
	 * return the Method for the "<code>doSomething</code>" method.
	 * 
	 * @param methodName The name of the method to search for.
	 * @return The Method for the method, if it exists.
	 */
	public MethodDeclaration[] getMethods(String methodName)
	{
		return getMethods(methodName, SearchFilter.DEFAULT);
	}
	
	/**
	 * Get the ClassDeclaration's Method with the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	public void doSomething()
	 * 	{
	 * 		...
	 * 	}
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getMethod("doSomething")</code>" would
	 * return the Method for the "<code>doSomething</code>" method.
	 * 
	 * @param methodName The name of the method to search for.
	 * @return The Method for the method, if it exists.
	 */
	public MethodDeclaration[] getMethods(String methodName, SearchFilter filter)
	{
		ArrayList<MethodDeclaration> output = new ArrayList<MethodDeclaration>();
		
		if (methodName.equals(InitializationMethod.SUPER_IDENTIFIER))
		{
			if (filter.checkAncestor && getExtendedClassName() != null)
			{
				addMethods(output, getExtendedClass().getMethods(InitializationMethod.IDENTIFIER));
			}
			
			return output.toArray(new MethodDeclaration[0]);
		}
		
		addMethods(output, getMethodList().getMethods(methodName, filter));
		
		if (filter.checkConstructors && methodName.equals(getName()))
		{
			addMethods(output, getConstructorList().getMethods(Constructor.IDENTIFIER, filter));
		}
		else
		{
			addMethods(output, getConstructorList().getMethods(methodName, filter));
		}
		
		if (filter.checkAncestor && getExtendedClassName() != null)
		{
			addMethods(output, getExtendedClass().getMethods(methodName));
		}
		
		return output.toArray(new MethodDeclaration[0]);
	}
	
	/**
	 * Add the methods in the src array to the given dst array list.
	 * 
	 * @param dst The array list to add the given src methods to.
	 * @param src The array containing the methods to add to the list.
	 */
	private void addMethods(ArrayList<MethodDeclaration> dst, MethodDeclaration src[])
	{
		for (MethodDeclaration method : src)
		{
			dst.add(method);
		}
	}
	
	/**
	 * Get the Declaration child of the specified ClassDeclaration that has
	 * the given name. The Declaration can either be a field or
	 * method.
	 * 
	 * @param name The name of the declaration node to search for.
	 * @return The Declaration instance that was found, if any.
	 */
	public InstanceDeclaration getDeclaration(String name)
	{
		FieldDeclaration field = getField(name);
		
		if (field != null)
		{
			return field;
		}
		
		MethodDeclaration methods[] = getMethods(name);
		
		if (methods.length > 0)
		{
			return methods[0];
		}
		
		return null;
	}
	
	public String getClassLocation()
	{
		Package p = getFileDeclaration().getPackage();
		
		return p.getLocation() + "/" + getName();
	}
	
	/**
	 * @see #generateUniquePrefix(StringBuilder)
	 */
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
	public StringBuilder generateUniquePrefix(StringBuilder builder)
	{
		return getFileDeclaration().getPackage().generateCLocation(builder).append('_');
	}
	
	public Package getPackage()
	{
		return getFileDeclaration().getPackage();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclaration#setAttribute(java.lang.String)
	 */
	@Override
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclaration#setAttribute(java.lang.String, int)
	 */
	@Override
	public boolean setAttribute(String attribute, int argNum)
	{
		if (super.setAttribute(attribute, argNum))
		{
			return true;
		}
		
		if (attribute.equals("class"))
		{
			return false;
		}
		else
		{
			return false;
		}
		
//		return true;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#addChild(Node)
	 */
	@Override
	public void addChild(Node child)
	{
		if (child instanceof MethodDeclaration)
		{
			if (child instanceof Constructor)
			{
				getConstructorList().addChild(child);
			}
			else if (child instanceof Destructor)
			{
				getDestructorList().addChild(child);
			}
			else
			{
				getMethodList().addChild(child);
			}
		}
		else if (child instanceof ExternalType)
		{
			getExternalTypeListNode().addChild(child);
		}
		else if (child instanceof FieldDeclaration)
		{
			FieldDeclaration field = (FieldDeclaration)child;
			
			if (field.isExternal())
			{
				getExternalFieldsListNode().addChild(field);
			}
			else
			{
				getFieldList().addChild(field);
			}
		}
		else if (child instanceof StaticBlock)
		{
			getStaticBlockList().addChild(child);
		}
		else
		{
			SyntaxMessage.error("Unexpected node type of '" + child.getClass().getSimpleName() + "' within class " + getName(), child);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#setType(java.lang.String)
	 */
	@Override
	public boolean setType(String type)
	{
		return setType(type, true, false);
	}
	
	/**
	 * Get whether or not the class contains any private fields.
	 * 
	 * @return Whether or not the class contains private data.
	 */
	public boolean containsPrivateData(boolean checkAncestor)
	{
		return containsStaticPrivateData(checkAncestor) || containsNonStaticPrivateData(checkAncestor);
	}
	
	/**
	 * Get whether or not the class contains any fields.
	 * 
	 * @return Whether or not the class contains data.
	 */
	public boolean containsData(boolean checkAncestor)
	{
		return containsNonStaticData(checkAncestor) || containsPrivateData(checkAncestor);
	}
	
	/**
	 * Get whether or not the class contains any private static fields.
	 * 
	 * @return Whether or not the class contains private static data.
	 */
	public boolean containsStaticPrivateData()
	{
		return containsStaticPrivateData(true);
	}
	
	/**
	 * Get whether or not the class contains any private static fields.
	 * 
	 * @param checkAncestor Whether or not to check the ancestor class
	 * 		if the data is not found.
	 * @return Whether or not the class contains private static data.
	 */
	public boolean containsStaticPrivateData(boolean checkAncestor)
	{
		StaticFieldList staticPrivateFields = getFieldList().getPrivateStaticFieldList();
		
		if (staticPrivateFields.getNumChildren() > 0)
		{
			return true;
		}
		
		if (checkAncestor && getExtendedClassName() != null)
		{
			return getExtendedClass().containsStaticPrivateData();
		}
		
		return false;
	}
	
//	/**
//	 * Get whether or not the class contains any fields.
//	 * 
//	 * @return Whether or not the class contains data.
//	 */
//	public boolean containsStaticData()
//	{
//		PublicFieldList staticFields = getFieldList().getPublicStaticFieldList();
//		
//		return staticFields.getNumChildren() > 0 || containsStaticPrivateData();
//	}
	
	/**
	 * Get whether or not the class contains any private non-static
	 * fields.
	 * 
	 * @return Whether or not the class contains private non-static data.
	 */
	public boolean containsNonStaticPrivateData()
	{
		return containsNonStaticPrivateData(true);
	}

	/**
	 * Get whether or not the class contains any private non-static
	 * fields.
	 * 
	 * @param checkAncestor Whether or not to check the ancestor class
	 * 		if the data is not found.
	 * @return Whether or not the class contains private non-static data.
	 */
	public boolean containsNonStaticPrivateData(boolean checkAncestor)
	{
		InstanceFieldList privateFields = getFieldList().getPrivateFieldList();
		
		if (privateFields.getNumVisibleChildren() > 0)
		{
			return true;
		}
		
		if (checkAncestor && getExtendedClassName() != null)
		{
			return getExtendedClass().containsNonStaticPrivateData();
		}
		
		return false;
	}

	/**
	 * Get whether or not the class contains any non-static fields.
	 * 
	 * @return Whether or not the class contains non-static data.
	 */
	public boolean containsNonStaticData()
	{
		return containsNonStaticData(true);
	}
	
	/**
	 * Get whether or not the class contains any non-static fields.
	 * 
	 * @param checkAncestor Whether or not to check the ancestor class
	 * 		if the data is not found.
	 * @return Whether or not the class contains non-static data.
	 */
	public boolean containsNonStaticData(boolean checkAncestor)
	{
		InstanceFieldList fields = getFieldList().getPublicFieldList();
		
		if (fields.getNumChildren() > 0 || containsNonStaticPrivateData(checkAncestor))
		{
			return true;
		}
		
		if (checkAncestor && getExtendedClassName() != null)
		{
			return getExtendedClass().containsNonStaticData();
		}
		
		return false;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		getVTableNode().generateCHeader(builder).append('\n');
		
		if (containsNonStaticData() || containsVirtualMethods())
		{
			builder.append("CCLASS_CLASS").append('\n').append('(').append('\n');
			
			generateCSourceName(builder).append(", ").append('\n').append('\n');
			
			if (containsVirtualMethods())
			{
				VTable table = getVTableNode();
				
				builder.append(table.generateCType()).append("* ").append(VTable.IDENTIFIER).append(";\n");
			}
			
			getFieldList().generateNonStaticCHeader(builder);
			
			if (containsNonStaticPrivateData())
			{
				builder.append("struct Private* prv;").append('\n');
			}
			
			builder.append(')').append('\n');
		}
		
		getFieldList().generateStaticCHeader(builder).append('\n');
		
		if (getStaticBlockList().getNumVisibleChildren() > 0)
		{
			StaticBlock.generateCHeader(builder, this);
		}
		
		MethodList constructors = getConstructorList();
		constructors.generateCHeader(builder);
		
		MethodList destructors = getDestructorList();
		destructors.generateCHeader(builder);
		
		MethodList methods = getMethodList();
		methods.generateCHeader(builder);
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		getVTableNode().generateCSource(builder).append('\n');
		
		if (containsNonStaticPrivateData())
		{
			builder.append("CCLASS_PRIVATE").append('\n').append('(').append('\n').append(generateCPrivateFieldsSource()).append(')').append('\n');
		}
		
		builder.append(generatePrivateMethodPrototypes());
		
		getFieldList().generateStaticCSource(builder);
		
		for (int i = getNumDefaultChildren(); i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			builder.append('\n').append(child.generateCSource());
		}

		getFieldList().generateNonStaticCSource(builder);
		
		generateStaticBlocksSource(builder);
		
		getConstructorList().generateCSource(builder);
		getDestructorList().generateCSource(builder);
		getMethodList().generateCSource(builder);
		
		return builder;
	}
	
	private StringBuilder generateStaticBlocksSource(StringBuilder builder)
	{
		if (getStaticBlockList().getNumVisibleChildren() > 0)
		{
			StaticBlock.generateCMethodHeader(builder, this).append('\n');
			builder.append('{').append('\n');
			
			for (int i = 0; i < getStaticBlockList().getNumVisibleChildren(); i++)
			{
				StaticBlock block = getStaticBlockList().getChild(i);
				
				block.generateCSource(builder);
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
	private StringBuilder generateCPrivateFieldsSource()
	{
		return generateCPrivateFieldsSource(new StringBuilder());
	}
	
	/**
	 * Generate the C source representation of the private field
	 * declarations.
	 * 
	 * @param builder The StringBuilder to append that data to.
	 * @return The StringBuilder with the appended data.
	 */
	private StringBuilder generateCPrivateFieldsSource(StringBuilder builder)
	{
		if (getExtendedClassName() != null)
		{
			getExtendedClass().generateCPrivateFieldsSource(builder);
		}
		
		return getFieldList().getPrivateFieldList().generateCSource(builder);
	}
	
	/**
	 * Decode the given statement into a ClassDeclaration, if possible. If it is
	 * not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public class ClassName</li>
	 * 	<li>private static class ClassName</li>
	 * 	<li>public abstract class ClassName</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to translate into a ClassDeclaration
	 * 		if possible.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ClassDeclaration.
	 */
	public static ClassDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		// If contains 'class' in the statement.
		if (Regex.indexOf(statement, Patterns.PRE_CLASS) >= 0)
		{
			ClassDeclaration n = new ClassDeclaration(parent, location);
			
			ClassData data = new ClassData();
			
			n.searchGenericParameters(statement, data);
			
			n.iterateWords(statement, data, require);
			
			if (data.getGenericsRemaining() > 0)
			{
				Location newLoc = n.getLocationIn().asNew();
				Bounds   b      = data.getSkipBounds(data.getNumSkipBounds() - data.getGenericsRemaining());
				
				newLoc.addBounds(b.getStart(), b.getEnd());
				
				SyntaxMessage.error("Invalid generic type declaration", n, newLoc);
			}
			
			// TODO: Check for the standard library version of Object.
			if (n.getExtendedClassName() == null && !n.getClassLocation().equals("nova/standard/Object"))
			{
				n.setExtendedClass("nova/standard/Object");
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, net.fathomsoft.nova.util.Bounds, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		ClassData data = (ClassData)extra;
		
		if (data.extending || data.implementing)
		{
			if (data.extending)
			{
				setExtendedClass(word);
				
				data.extending = false;
			}
			else
			{
				if (word.startsWith(","))
				{
					word = word.substring(1);
				}
				if (word.endsWith(","))
				{
					word = word.substring(0, word.length() - 1);
				}
				
				if (word.length() > 0)
				{
					addImplementedClass(word);
				}
			}
			
			if (data.getRightAdjacentSkipBounds() != null)
			{
				decodeGenericParameters(data.statement, data.getRightAdjacentSkipBounds());
				
				data.decrementGenericsRemaining();
			}
		}
		else
		{
			if (word.equals("extends"))
			{
				data.extending = true;
			}
			else if (word.equals("implements"))
			{
				data.implementing = true;
			}
			else
			{
				setAttribute(word, extra.getWordNumber());
				
				if (!data.isFirstWord() && data.getPreviousWord().equals("class"))
				{
					setName(word);
					setType(word);
					
					if (data.getRightAdjacentSkipBounds() != null)
					{
						decodeGenericParameters(data.statement, data.getRightAdjacentSkipBounds());
						
						data.decrementGenericsRemaining();
					}
				}
			}
		}
	}
	
	public String generateTemporaryMethodName()
	{
		return generateTemporaryMethodName("temp");
	}
	
	public String generateTemporaryMethodName(String prefix)
	{
		String name = prefix;
		int    i    = 0;
		
		while (containsMethod(name))
		{
			name = prefix + i++;
		}
		
		return name;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.Variable#generateCSourceName(StringBuilder, String)
	 */
	@Override
	public StringBuilder generateCSourceName(StringBuilder builder, String uniquePrefix)
	{
		return generateUniquePrefix(builder).append(Nova.LANGUAGE_NAME).append(getName());
	}
	
	/**
	 * Generate the prototypes for specifically the private methods.
	 * 
	 * @return A String containing the prototype definitions.
	 */
	private String generatePrivateMethodPrototypes()
	{
		StringBuilder  builder = new StringBuilder();
		
		MethodList methods = getMethodList();
		
		for (int i = 0; i < methods.getNumChildren(); i++)
		{
			MethodDeclaration methodDeclaration = (MethodDeclaration)methods.getChild(i);
			
			if (methodDeclaration.getVisibility() == InstanceDeclaration.PRIVATE)
			{
				methodDeclaration.generateCSourcePrototype(builder).append('\n');
			}
		}
		
		if (builder.length() > 0)
		{
			builder.insert(0, '\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	public Node validate(int phase)
	{
		if (phase == SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			validateDeclaration(phase);
			
			ClassDeclaration clazz = getParentClass();
			
			if (clazz == null)
			{
				FileDeclaration file = getFileDeclaration();
				
				if (!file.getName().equals(getName()))
				{
					SyntaxMessage.error("The name of the class '" + getName() + "' must be the same as the file that it is contained within", this);//, false);
					
//					getParent().getParent().removeChild(getParent());
					
					return null;
				}
			}
			
			if (getExtendedClassName() != null && getFileDeclaration().containsImport(getExtendedClassName()))
			{
				getFileDeclaration().getImport(getExtendedClassName()).markUsed();
			}
		}
		else if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			validateFields(phase);
			validateMethods(phase);
		}
		
		return this;
	}
	
	/**
	 * Make sure that the Class is a valid declaration.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	private void validateDeclaration(int phase)
	{
		validateExtension(phase);
		validateImplementations(phase);
	}
	
	/**
	 * Make sure that the fields are all valid.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	private void validateFields(int phase)
	{
		
	}
	
	/**
	 * Make sure that the methods are all valid
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	private void validateMethods(int phase)
	{
		if (!containsConstructor())
		{
			addChild(Constructor.decodeStatement(this, "public construct()", Location.INVALID, true));
		}
		
		if (!containsDestructor())
		{
			Destructor defaultDestructor = new Destructor(this, Location.INVALID);
			defaultDestructor.setName(getName());
			defaultDestructor.setVisibility(FieldDeclaration.PUBLIC);
			
			addChild(defaultDestructor);
		}
		
		getMethodList().validate(phase);
		getConstructorList().validate(phase);
		getDestructorList().validate(phase);
		
		AssignmentMethod assignments = new AssignmentMethod(this, Location.INVALID);
		addChild(assignments);
	}
	
	/**
	 * Validate that the extended class has been declared and that it
	 * is valid to extend.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	private void validateExtension(int phase)
	{
		if (extendedClass == null)
		{
			return;
		}
		
		ClassDeclaration clazz = SyntaxUtils.getImportedClass(getFileDeclaration(), extendedClass);
		
		String tempName = extendedClass;
		
		if (clazz == null)
		{
			extendedClass = null;
			
			SyntaxMessage.error("Class '" + tempName + "' not declared", this);
		}
		else if (clazz.isConstant())
		{
			extendedClass = null;
			
			SyntaxMessage.error("Class '" + tempName + "' not is constant and cannot be extended", this);
		}
	}
	
	/**
	 * Validate that all of the implemented classes have been declared
	 * and that they are valid interfaces.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	private void validateImplementations(int phase)
	{
		Program program = getProgram();
		
		for (String implementedClass : implementedClasses)
		{
			ClassDeclaration clazz = program.getClassDeclaration(implementedClass);
			
			if (clazz == null)
			{
				SyntaxMessage.error("Class '" + implementedClass + "' not declared", getController());
			}
		}
	}
	
	/**
	 * Get whether or not the class contains a constructor implementation
	 * or not.
	 * 
	 * @return Whether or not the class contains a constructor
	 * 		implementation or not.
	 */
	public boolean containsConstructor()
	{
		return containsMethod(Constructor.IDENTIFIER, true, getName());
	}
	
	/**
	 * Get whether or not the class contains a default constructor
	 * implementation or not.
	 * 
	 * @return Whether or not the class contains a default constructor
	 * 		implementation or not.
	 */
	public boolean containsDefaultConstructor()
	{
		MethodList constructors = getConstructorList();
		
		for (int i = 0; i < constructors.getNumChildren(); i++)
		{
			Constructor method = (Constructor)constructors.getChild(i);
			
			if (method.getParameterList().getNumChildren() == 1)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Get whether or not the class contains a destructor implementation
	 * or not.
	 * 
	 * @return Whether or not the class contains a destructor
	 * 		implementation or not.
	 */
	public boolean containsDestructor()
	{
		return containsMethod("~" + getName(), true, null);
	}
	
	/**
	 * Get whether or not the Nova class contains a method with the
	 * given specifications.
	 * 
	 * @param methodName The name of the method to search for.
	 * @param staticVal Whether or not the method is static.
	 * @param type The return type of the method.
	 * @return Whether or not the class contains the method.
	 */
	public boolean containsMethod(String methodName, boolean staticVal, String type)
	{
		return containsMethod(getConstructorList(), methodName, staticVal, type) ||
				containsMethod(getDestructorList(), methodName, staticVal, type) ||
				containsMethod(getMethodList(), methodName, staticVal, type);
	}
	
	/**
	 * Get whether or not the Nova class contains a method with the
	 * given specifications.
	 * 
	 * @param root The root Node to search for the method from.
	 * @param methodName The name of the method to search for.
	 * @param staticVal Whether or not the method is static.
	 * @param type The return type of the method.
	 * @return Whether or not the class contains the method.
	 */
	public boolean containsMethod(Node root, String methodName, boolean staticVal, String type)
	{
		for (int i = 0; i < root.getNumChildren(); i++)
		{
			MethodDeclaration methodDeclaration = (MethodDeclaration)root.getChild(i);
			
			if (methodDeclaration.isStatic() == staticVal && methodDeclaration.getName().equals(methodName) && methodDeclaration.getType().equals(type))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Generate a ClassDeclaration with the given parent and location for
	 * temporary use.
	 * 
	 * @param parent The node to set as the ClassDeclaration parent.
	 * @param locationIn The location to set as the ClassDeclaration location.
	 * @return The generated temporary ClassDeclaration.
	 */
	public static ClassDeclaration generateTemporaryClass(Node parent, Location locationIn)
	{
		ClassDeclaration methodDeclaration = decodeStatement(parent, "public class Temp", locationIn, true);
		
		return methodDeclaration;
	}
	
	public static ClassDeclaration generateTemporaryHierarchy(Nova controller)
	{
		FileDeclaration f = FileDeclaration.generateTemporaryHierarchy(controller);
		
		ClassDeclaration clazz = generateTemporaryClass(f, Location.INVALID);
		f.addChild(clazz);
		
		return clazz;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ClassDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ClassDeclaration node = new ClassDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link ClassDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClassDeclaration cloneTo(ClassDeclaration node)
	{
		super.cloneTo(node);
		
		node.extendedClass = extendedClass;
		
		for (int i = 0; i < implementedClasses.length; i++)
		{
			String implementedClass = implementedClasses[i];
			
			node.addImplementedClass(implementedClass);
		}
		
		return node;
	}
	
	/**
	 * Implementation of the ExtraData for this class.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.13 Jun 11, 2014 at 8:31:46 PM
	 * @version	v0.2.29 Jun 11, 2014 at 8:31:46 PM
	 */
	public static class ClassData extends DeclarationData
	{
		private boolean	extending, implementing;
	}
	
	/**
	 * Test the ClassDeclaration class type to make sure everything
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