package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.Field;
import net.fathomsoft.nova.tree.variables.FieldList;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;
import net.fathomsoft.nova.tree.variables.StaticFieldList;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * Declaration extension that represents the declaration of a class
 * node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:15:51 PM
 * @version	v0.2.14 Jun 18, 2014 at 10:11:40 PM
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
		
		FieldList    fields         = new FieldList(this, null);
		MethodList   constructors   = new MethodList(this, null);
		MethodList   destructors    = new MethodList(this, null);
		MethodList   methods        = new MethodList(this, null);
		ExternalTypeList externalTypes  = new ExternalTypeList(this, null);
		FieldList    externalFields = new FieldList(this, null);
		
		super.addChild(fields);
		super.addChild(constructors);
		super.addChild(destructors);
		super.addChild(methods);
		super.addChild(externalTypes);
		super.addChild(externalFields);
	}
	
	/**
	 * Get the FieldList instance that contains the list of fields
	 * that this class node contains.
	 * 
	 * @return The FieldList for this class node.
	 */
	public FieldList getFieldList()
	{
		return (FieldList)getChild(0);
	}
	
	/**
	 * Get the MethodList instance that contains the list of
	 * constructors that this class node contains.
	 * 
	 * @return The MethodList for constructors of this class node.
	 */
	public MethodList getConstructorListNode()
	{
		return (MethodList)getChild(1);
	}
	
	/**
	 * Get the MethodList instance that contains the list of
	 * destructors that this class node contains.
	 * 
	 * @return The MethodList for destructors of this class node.
	 */
	public MethodList getDestructorListNode()
	{
		return (MethodList)getChild(2);
	}
	
	/**
	 * Get the MethodList instance that contains the list of methods
	 * that this class node contains.
	 * 
	 * @return The MethodList for this class node.
	 */
	public MethodList getMethodList()
	{
		return (MethodList)getChild(3);
	}
	
	/**
	 * Get the ExternalTypeList instance that contains the list of
	 * external types that this class node contains.
	 * 
	 * @return The ExternalTypeList for this class node.
	 */
	public ExternalTypeList getExternalTypeListNode()
	{
		return (ExternalTypeList)getChild(4);
	}
	
	/**
	 * Get the FieldList instance that contains the list of external
	 * fields that this class node contains.
	 * 
	 * @return The external FieldList for this class node.
	 */
	public FieldList getExternalFieldsListNode()
	{
		return (FieldList)getChild(5);
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
		return (VTable)getChild(6);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#getAccessedNode()
	 */
	public Identifier getAccessedNode()
	{
		if (getNumChildren() <= 6)
		{
			return null;
		}
		
		return (Identifier)getChild(6);
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
		ClassDeclaration clazz = this;
		
		while (clazz != null)
		{
			if (clazz == node)
			{
				return true;
			}
			
			clazz = clazz.getExtendedClass();
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
		ClassDeclaration extendedClass = getProgram().getClassDeclaration(getExtendedClassName());
		
		return extendedClass;
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
		int size = implementedClasses.length + 1;
		
		String temp[] = implementedClasses;
		
		implementedClasses = new String[size];
		
		System.arraycopy(temp, 0, implementedClasses, 0, temp.length);
		
		implementedClasses[size - 1] = implementedClass;
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
	public Field getField(String fieldName)
	{
		FieldList fields = getFieldList();
		
		Field     field  = fields.getField(fieldName);
		
		if (field == null)
		{
			fields = getExternalFieldsListNode();
			
			field  = fields.getField(fieldName);
		}
		
		if (field == null && getExtendedClassName() != null)
		{
			return getExtendedClass().getField(fieldName);
		}
		
		return field;
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
		return getMethod(methodName) != null;
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
	public Method getMethod(String methodName)
	{
		MethodList methods = getMethodList();
		Method     method  = methods.getMethod(methodName);
		
		if (method != null)
		{
			return method;
		}
		
		methods = getConstructorListNode();
		method  = methods.getMethod(methodName);
		
		if (method != null)
		{
			return method;
		}
		
		methods = getDestructorListNode();
		method  = methods.getMethod(methodName);
		
		if (method == null && getExtendedClassName() != null)
		{
			return getExtendedClass().getMethod(methodName);
		}
		
		return method;
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
		Field field = getField(name);
		
		if (field != null)
		{
			return field;
		}

		Method method = getMethod(name);
		
		return method;
	}
	
	/**
	 * Get the prefix that is used for the data that is contained
	 * within the specified class.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * package "this/is/my/package";
	 * 
	 * public class Test
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * The method prefix would look like:
	 * "<code>this_is_my_package_Test</code>"
	 * 
	 * @return The prefix that is used for the data contained within
	 * 		the class.
	 */
	public String generateUniquePrefix()
	{
		String str = getName();
		
		return str;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclaration#setAttribute(java.lang.String)
	 */
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclaration#setAttribute(java.lang.String, int)
	 */
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
		if (child instanceof Method)
		{
			if (child instanceof Constructor)
			{
				getConstructorListNode().addChild(child);
			}
			else if (child instanceof Destructor)
			{
				getDestructorListNode().addChild(child);
			}
			else if (child instanceof Method)
			{
				getMethodList().addChild(child);
			}
		}
		else if (child instanceof ExternalStatement)
		{
			super.addChild(child);
		}
		else if (child instanceof ExternalType)
		{
			getExternalTypeListNode().addChild(child);
		}
		else if (child instanceof Field)
		{
			Field field = (Field)child;
			
			if (field.isExternal())
			{
				getExternalFieldsListNode().addChild(field);
			}
			else
			{
				getFieldList().addChild(field);
			}
		}
		else
		{
			SyntaxMessage.error("Unexpected statement within class " + getName(), child);
		}
	}
	
	/**
	 * Get whether or not the class contains any private fields.
	 * 
	 * @return Whether or not the class contains private data.
	 */
	public boolean containsPrivateData()
	{
		return containsStaticPrivateData() || containsNonStaticPrivateData();
	}
	
	/**
	 * Get whether or not the class contains any fields.
	 * 
	 * @return Whether or not the class contains data.
	 */
	public boolean containsData()
	{
		return containsStaticData() || containsNonStaticData() || containsPrivateData();
	}
	
	/**
	 * Get whether or not the class contains any private static fields.
	 * 
	 * @return Whether or not the class contains private static data.
	 */
	public boolean containsStaticPrivateData()
	{
		StaticFieldList staticPrivateFields = getFieldList().getPrivateStaticFieldList();
		
		if (staticPrivateFields.getNumChildren() > 0)
		{
			return true;
		}
		
		ClassDeclaration extended = getExtendedClass();
		
		if (extended != null)
		{
			return extended.containsStaticPrivateData();
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
		InstanceFieldList privateFields = getFieldList().getPrivateFieldList();
		
		if (privateFields.getNumChildren() > 0)
		{
			return true;
		}
		
		ClassDeclaration extended = getExtendedClass();
		
		if (extended != null)
		{
			return extended.containsNonStaticPrivateData();
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
		InstanceFieldList fields = getFieldList().getPublicFieldList();
		
		if (fields.getNumChildren() > 0 || containsNonStaticPrivateData())
		{
			return true;
		}
		
		ClassDeclaration extended = getExtendedClass();
		
		if (extended != null)
		{
			return extended.containsNonStaticData();
		}
		
		return false;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		if (isStatic())
		{
			SyntaxMessage.error("Static classes are not implemented in C yet.", this);
		}
		if (isConstant())
		{
			SyntaxMessage.error("Const classes are not implemented in C yet.", this);
		}
		
		if (isReference())
		{
			SyntaxMessage.error("A class cannot be of a reference type", this);
		}
		else if (isPointer())
		{
			SyntaxMessage.error("A class cannot be of a pointer type", this);
		}
		
		FieldList fields = getFieldList();
		
		if (containsNonStaticData())
		{
			builder.append('\n').append("CCLASS_CLASS").append('\n').append('(').append('\n');
			
			builder.append(getName()).append(", ").append('\n').append('\n');
			
			fields.generateNonStaticCHeader(builder);
			
			if (containsNonStaticPrivateData())
			{
				builder.append("struct Private* prv;").append('\n');
			}
				
			builder.append(')').append('\n');
		}
		
		fields.generateStaticCHeader(builder).append('\n');
		
		MethodList constructors = getConstructorListNode();
		constructors.generateCHeader(builder);
		
		MethodList destructors = getDestructorListNode();
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
		FieldList fields = getFieldList();
		
		InstanceFieldList privateFields = fields.getPrivateFieldList();
		
		if (containsNonStaticPrivateData())
		{
			builder.append('\n');
			
			builder.append("CCLASS_PRIVATE").append('\n').append('(').append('\n').append(privateFields.generateCSource()).append(')').append('\n');
		}
		
		builder.append(generatePrivateMethodPrototypes());
		
		fields.generateStaticCSource(builder);
		
		for (int i = 6; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			builder.append('\n').append(child.generateCSource());
		}

		fields.generateNonStaticCSource(builder);
		
		getConstructorListNode().generateCSource(builder);
		getDestructorListNode().generateCSource(builder);
		getMethodList().generateCSource(builder);
		
		return builder;
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
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ClassDeclaration.
	 */
	public static ClassDeclaration decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		// If contains 'class' in the statement.
		if (Regex.indexOf(statement, Patterns.PRE_CLASS) >= 0)
		{
			ClassDeclaration n = new ClassDeclaration(parent, location);
			
			n.iterateWords(statement, new ClassData());
			
			// TODO: Check for the standard library version of Object.
			if (n.getExtendedClassName() == null && !n.getName().equals("Object"))
			{
				n.setExtendedClass("Object");
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, int, net.fathomsoft.nova.util.Bounds, int, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		ClassData data = (ClassData)extra;
		
		if (data.extending)
		{
			setExtendedClass(word);
			
			data.extending = false;
		}
		else if (data.implementing)
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
				setAttribute(word, wordNumber);
				
				if (data.previousWord.equals("class"))
				{
					setName(word);
					setType(word);
				}
				
				data.previousWord = word;
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.Variable#generateCSourceName()
	 */
	@Override
	public StringBuilder generateCSourceName(StringBuilder builder)
	{
		return builder.append(Nova.LANGUAGE_NAME).append(getName());
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
			Method method = (Method)methods.getChild(i);
			
			if (method.getVisibility() == InstanceDeclaration.PRIVATE)
			{
				method.generateCSourcePrototype(builder).append('\n');
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
		if (phase != 1)
		{
			return this;
		}
		
		ClassDeclaration clazz = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class);
		
		if (clazz == null)
		{
			FileDeclaration file = getFileDeclaration();
			
			if (!file.getName().equals(getName()))
			{
				SyntaxMessage.error("The name of the class '" + getName() + "' must be the same as the file that it is contained within", this, false);
				
				getParent().removeChild(this);
				
				return null;
			}
		}
		
		return this;
	}
	
	/**
	 * Make sure that the Class is a valid declaration.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	public void validateDeclaration(int phase)
	{
		validateExtension(phase);
		validateImplementations(phase);
	}
	
	/**
	 * Make sure that the fields are all valid.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	public void validateFields(int phase)
	{
		
	}
	
	/**
	 * Make sure that the methods are all valid
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	public void validateMethods(int phase)
	{
		if (!containsConstructor())
		{
			Location loc = new Location();
			
			Constructor defaultConstructor = new Constructor(this, null);
			defaultConstructor.setName(getName());
			defaultConstructor.setType(getName());
			defaultConstructor.setVisibility(Field.PUBLIC);
			defaultConstructor.setLocationIn(loc);
			addChild(defaultConstructor);
		}
		
		if (!containsDestructor())
		{
			Location loc = new Location();
			
			Destructor defaultDestructor = new Destructor(this, null);
			defaultDestructor.setName(getName());
			defaultDestructor.setType("void");
			defaultDestructor.setVisibility(Field.PUBLIC);
			defaultDestructor.setLocationIn(loc);
			
			addChild(defaultDestructor);
		}
		
		getMethodList().validate(phase);
		getConstructorListNode().validate(phase);
		getDestructorListNode().validate(phase);
	}
	
	/**
	 * Validate that the extended class has been declared and that it
	 * is valid to extend.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	public void validateExtension(int phase)
	{
		if (extendedClass == null)
		{
			return;
		}
		
		Program program  = getProgram();
		
		ClassDeclaration   clazz    = program.getClassDeclaration(extendedClass);
		
		String      tempName = extendedClass;
		
		if (clazz == null)
		{
			extendedClass = null;
			
			SyntaxMessage.error("Class '" + tempName + "' not declared", this);
		}
		else
		{
			if (clazz.isConstant())
			{
				extendedClass = null;
				
				SyntaxMessage.error("Class '" + tempName + "' not is constant and cannot be extended", this);
			}
		}
		
//		FieldList fields = clazz.getFieldList().clone();
//		
//		for (int i = 0; i < fields.getNumChildren(); i++)
//		{
//			addChild(fields.getChild(i));
//		}
	}
	
	/**
	 * Validate that all of the implemented classes have been declared
	 * and that they are valid interfaces.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	public void validateImplementations(int phase)
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
	 * Get whether or not the Nova class contains static data. i.e
	 * static variables, methods, etc.
	 * 
	 * @return Whether or not the class contains static data.
	 */
	public boolean containsStaticData()
	{
		return containsStaticData(this);
	}
	
	/**
	 * Get whether or not the Nova class contains static data. i.e
	 * static variables, methods, etc.
	 * 
	 * @param root The root Node to search for the static modifier
	 * 		from.
	 * @return Whether or not the class contains static data.
	 */
	public boolean containsStaticData(Node root)
	{
		for (int i = 0; i < root.getNumChildren(); i++)
		{
			Node child = root.getChild(i);
			
			if (child instanceof InstanceDeclaration)
			{
				InstanceDeclaration declaration = (InstanceDeclaration)child;
				
				if (declaration.isStatic() && !declaration.isExternal())
				{
					return true;
				}
			}
			else if (containsStaticData(child))
			{
				return true;
			}
		}
		
		return false;
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
		return containsMethod(getName(), false, getName());
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
		MethodList constructors = getConstructorListNode();
		
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
		return containsMethod("~" + getName(), false, null);
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
		return containsMethod(this, methodName, staticVal, type);
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
			Node child = root.getChild(i);
			
			if (child instanceof Method)
			{
				Method method = (Method)child;
				
				if (method.isStatic() == staticVal && method.getName().equals(methodName) && method.getType().equals(type))
				{
					return true;
				}
			}
			else if (containsMethod(child, methodName, staticVal, type))
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
		ClassDeclaration clazz = new ClassDeclaration(parent, locationIn);
		
		return clazz;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public ClassDeclaration clone(Node temporaryParent, Location locationIn)
	{
		ClassDeclaration node = new ClassDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ClassDeclaration with the data that is in the
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
	 * @version	v0.2.13 Jun 11, 2014 at 8:31:46 PM
	 */
	private static class ClassData extends ExtraData
	{
		private boolean	extending, implementing;
		
		private String	previousWord;
		
		public ClassData()
		{
			previousWord = "";
		}
	}
}