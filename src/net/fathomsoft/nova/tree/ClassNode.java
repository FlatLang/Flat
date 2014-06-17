package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.TreeNode.ExtraData;
import net.fathomsoft.nova.tree.variables.FieldListNode;
import net.fathomsoft.nova.tree.variables.FieldNode;
import net.fathomsoft.nova.tree.variables.InstanceFieldListNode;
import net.fathomsoft.nova.tree.variables.StaticFieldListNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * DeclarationNode extension that represents the declaration of a class
 * node type. See {@link #decodeStatement(TreeNode, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:15:51 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ClassNode extends InstanceDeclarationNode
{
	private String	extendedClass;
	
	private	String	implementedClasses[];
	
	/**
	 * Instantiate and initialize default values for a class node.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ClassNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		implementedClasses = new String[0];
		
		setType("class");
		
		FieldListNode        fields         = new FieldListNode(this, null);
		MethodListNode       constructors   = new MethodListNode(this, null);
		MethodListNode       destructors    = new MethodListNode(this, null);
		MethodListNode       methods        = new MethodListNode(this, null);
		ExternalTypeNodeList externalTypes  = new ExternalTypeNodeList(this, null);
		FieldListNode        externalFields = new FieldListNode(this, null);
		
		super.addChild(fields);
		super.addChild(constructors);
		super.addChild(destructors);
		super.addChild(methods);
		super.addChild(externalTypes);
		super.addChild(externalFields);
	}
	
	/**
	 * Get the FieldListNode instance that contains the list of fields
	 * that this class node contains.
	 * 
	 * @return The FieldListNode for this class node.
	 */
	public FieldListNode getFieldListNode()
	{
		return (FieldListNode)getChild(0);
	}
	
	/**
	 * Get the MethodListNode instance that contains the list of
	 * constructors that this class node contains.
	 * 
	 * @return The MethodListNode for constructors of this class node.
	 */
	public MethodListNode getConstructorListNode()
	{
		return (MethodListNode)getChild(1);
	}
	
	/**
	 * Get the MethodListNode instance that contains the list of
	 * destructors that this class node contains.
	 * 
	 * @return The MethodListNode for destructors of this class node.
	 */
	public MethodListNode getDestructorListNode()
	{
		return (MethodListNode)getChild(2);
	}
	
	/**
	 * Get the MethodListNode instance that contains the list of methods
	 * that this class node contains.
	 * 
	 * @return The MethodListNode for this class node.
	 */
	public MethodListNode getMethodListNode()
	{
		return (MethodListNode)getChild(3);
	}
	
	/**
	 * Get the ExternalTypeNodeList instance that contains the list of
	 * external types that this class node contains.
	 * 
	 * @return The ExternalTypeNodeList for this class node.
	 */
	public ExternalTypeNodeList getExternalTypeListNode()
	{
		return (ExternalTypeNodeList)getChild(4);
	}
	
	/**
	 * Get the FieldListNode instance that contains the list of external
	 * fields that this class node contains.
	 * 
	 * @return The external FieldListNode for this class node.
	 */
	public FieldListNode getExternalFieldsListNode()
	{
		return (FieldListNode)getChild(5);
	}
	
	/**
	 * Get the VTableNode instance that contains the list of pointers
	 * to the virtual methods of a class, if any virtual methods, or
	 * any methods of the class are overridden..
	 * 
	 * @return The VTableNode for this class node.
	 */
	public VTableNode getVTableNode()
	{
		return (VTableNode)getChild(6);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ValueNode#getAccessedNode()
	 */
	public IdentifierNode getAccessedNode()
	{
		if (getNumChildren() <= 6)
		{
			return null;
		}
		
		return (IdentifierNode)getChild(6);
	}
	
	/**
	 * Check whether or not the specified ClassNode extends the given
	 * ClassNode.
	 * 
	 * @param node The ClassNode to check if the specified Class
	 * 		extends.
	 * @return Whether or not the specified ClassNode extends the given
	 * 		ClassNode.
	 */
	public boolean isOfType(ClassNode node)
	{
		ClassNode clazz = this;
		
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
	 * @return The ClassNode instance of the class that is extended.
	 */
	public ClassNode getExtendedClass()
	{
		ClassNode extendedClass = getProgramNode().getClassNode(getExtendedClassName());
		
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
	 * @return The ClassNode instance of the class that is extended.
	 */
	public ClassNode[] getImplementedClasses()
	{
		ProgramNode program = getProgramNode();
		
		ClassNode classes[] = new ClassNode[implementedClasses.length];
		
		for (int i = 0; i < classes.length; i++)
		{
			classes[i] = program.getClassNode(implementedClasses[i]);
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
	 * Get whether or not the ClassNode contains an ExternalTypeNode with
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
	 * return the ExternalTypeNode for the "<code>FILE</code>" external
	 * type.
	 * 
	 * @param typeName The name of the external type to search for.
	 * @return Whether or not the ClassNode contains the MethodNode with
	 * 		the specified name.
	 */
	public boolean containsExternalType(String typeName)
	{
		return getExternalTypeListNode().containsType(typeName);
	}
	
	/**
	 * Get the ClassNode's ExternalTypeNode with the specified type.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	external type FILE;
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getType("FILE")</code>" would
	 * return the ExternalTypeNode for the "<code>FILE</code>" external
	 * type.
	 * 
	 * @param typeName The name of the external type to search for.
	 * @return The ExternalTypeNode for the external type, if it exists.
	 */
	public ExternalTypeNode getExternalType(String typeName)
	{
		return getExternalTypeListNode().getType(typeName);
	}
	
	/**
	 * Get whether or not the ClassNode contains the FieldNode with the
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
	 * @return Whether or not the ClassNode contains the FieldNode with
	 * 		the specified name.
	 */
	public boolean containsField(String fieldName)
	{
		return getField(fieldName) != null;
	}
	
	/**
	 * Get the ClassNode's FieldNode with the specified name.<br>
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
	 * FieldNode for the "<code>age</code>" int field.
	 * 
	 * @param fieldName The name of the field to search for.
	 * @return The FieldNode for the field, if it exists.
	 */
	public FieldNode getField(String fieldName)
	{
		FieldListNode fields = getFieldListNode();
		
		FieldNode     field  = fields.getField(fieldName);
		
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
	 * Get whether or not the ClassNode contains the MethodNode with the
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
	 * @return Whether or not the ClassNode contains the MethodNode with
	 * 		the specified name.
	 */
	public boolean containsMethod(String methodName)
	{
		return getMethod(methodName) != null;
	}
	
	/**
	 * Get the ClassNode's MethodNode with the specified name.<br>
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
	 * return the MethodNode for the "<code>doSomething</code>" method.
	 * 
	 * @param methodName The name of the method to search for.
	 * @return The MethodNode for the method, if it exists.
	 */
	public MethodNode getMethod(String methodName)
	{
		MethodListNode methods = getMethodListNode();
		MethodNode     method  = methods.getMethod(methodName);
		
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
	 * Get the DeclarationNode child of the specified ClassNode that has
	 * the given name. The DeclarationNode can either be a field or
	 * method.
	 * 
	 * @param name The name of the declaration node to search for.
	 * @return The DeclarationNode instance that was found, if any.
	 */
	public InstanceDeclarationNode getDeclaration(String name)
	{
		FieldNode field = getField(name);
		
		if (field != null)
		{
			return field;
		}

		MethodNode method = getMethod(name);
		
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
	 * @see net.fathomsoft.nova.tree.InstanceDeclarationNode#setAttribute(java.lang.String)
	 */
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclarationNode#setAttribute(java.lang.String, int)
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
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof MethodNode)
		{
			if (child instanceof ConstructorNode)
			{
				getConstructorListNode().addChild(child);
			}
			else if (child instanceof DestructorNode)
			{
				getDestructorListNode().addChild(child);
			}
			else if (child instanceof MethodNode)
			{
				getMethodListNode().addChild(child);
			}
		}
		else if (child instanceof ExternalStatementNode)
		{
			super.addChild(child);
		}
		else if (child instanceof ExternalTypeNode)
		{
			getExternalTypeListNode().addChild(child);
		}
		else if (child instanceof FieldNode)
		{
			FieldNode field = (FieldNode)child;
			
			if (field.isExternal())
			{
				getExternalFieldsListNode().addChild(field);
			}
			else
			{
				getFieldListNode().addChild(field);
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
		StaticFieldListNode staticPrivateFields = getFieldListNode().getPrivateStaticFieldListNode();
		
		if (staticPrivateFields.getNumChildren() > 0)
		{
			return true;
		}
		
		ClassNode extended = getExtendedClass();
		
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
//		PublicFieldListNode staticFields = getFieldListNode().getPublicStaticFieldListNode();
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
		InstanceFieldListNode privateFields = getFieldListNode().getPrivateFieldListNode();
		
		if (privateFields.getNumChildren() > 0)
		{
			return true;
		}
		
		ClassNode extended = getExtendedClass();
		
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
		InstanceFieldListNode fields = getFieldListNode().getPublicFieldListNode();
		
		if (fields.getNumChildren() > 0 || containsNonStaticPrivateData())
		{
			return true;
		}
		
		ClassNode extended = getExtendedClass();
		
		if (extended != null)
		{
			return extended.containsNonStaticData();
		}
		
		return false;
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
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
		
		FieldListNode fields = getFieldListNode();
		
		if (containsNonStaticData())
		{
			builder.append('\n').append("CCLASS_CLASS").append('\n').append('(').append('\n');
			
			builder.append(getName());
			
//			if (containsNonStaticData())
//			{
				builder.append(", ");
			
				builder.append('\n').append('\n');
				
				builder.append(fields.generateNonStaticCHeader());
				
				if (containsNonStaticPrivateData())
				{
					builder.append("struct Private* prv;").append('\n');
				}
//			}
//			else
//			{
//				builder.append('\n');
//			}
			
			builder.append(')').append('\n');
		}
		
		builder.append(fields.generateStaticCHeader()).append('\n');
		
		MethodListNode constructors = getConstructorListNode();
		builder.append(constructors.generateCHeader());
		
		MethodListNode destructors = getDestructorListNode();
		builder.append(destructors.generateCHeader());
		
		MethodListNode methods = getMethodListNode();
		builder.append(methods.generateCHeader());
		
//		if (containsStaticData())
//		{
//			builder.append("extern ").append(getName()).append("* ").append("__static__").append(getName()).append(';').append('\n').append('\n');
//		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		FieldListNode fields  = getFieldListNode();
		
		InstanceFieldListNode privateFields = fields.getPrivateFieldListNode();
		
//		if (containsStaticData())
//		{
//			builder.append('\n');
//			builder.append(getName()).append("* ").append("__static__").append(getName()).append(';').append('\n');
//		}
		
		if (containsNonStaticPrivateData())
		{
//			if (!containsStaticData())
//			{
				builder.append('\n');
//			}
			
			builder.append("CCLASS_PRIVATE").append('\n').append('(').append('\n');
			builder.append(privateFields.generateCSource());
			builder.append(')').append('\n');
		}
		
		builder.append(generatePrivateMethodPrototypes());
		
		builder.append(fields.generateStaticCSource());
		
		for (int i = 6; i < getNumChildren(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append('\n').append(child.generateCSource());
		}

		builder.append(fields.generateNonStaticCSource());
		
		builder.append(getConstructorListNode().generateCSource());
		builder.append(getDestructorListNode().generateCSource());
		builder.append(getMethodListNode().generateCSource());
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a ClassNode, if possible. If it is
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
	 * @param statement The statement to translate into a ClassNode
	 * 		if possible.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ClassNode.
	 */
	public static ClassNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean scope)
	{
		// If contains 'class' in the statement.
		if (Regex.indexOf(statement, Patterns.PRE_CLASS) >= 0)
		{
			ClassNode n = new ClassNode(parent, location);
			
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
	 * @see net.fathomsoft.nova.tree.TreeNode#interactWord(java.lang.String, int, net.fathomsoft.nova.util.Bounds, int, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.TreeNode.ExtraData)
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
	 * @see net.fathomsoft.nova.tree.IdentifierNode#generateCSourceName()
	 */
	@Override
	public String generateCSourceName()
	{
		return Nova.LANGUAGE_NAME + getName();
	}
	
	/**
	 * Generate the prototypes for specifically the private methods.
	 * 
	 * @return A String containing the prototype definitions.
	 */
	private String generatePrivateMethodPrototypes()
	{
		StringBuilder  builder = new StringBuilder();
		
		MethodListNode methods = getMethodListNode();
		
		for (int i = 0; i < methods.getNumChildren(); i++)
		{
			MethodNode method = (MethodNode)methods.getChild(i);
			
			if (method.getVisibility() == InstanceDeclarationNode.PRIVATE)
			{
				builder.append(method.generateCSourcePrototype()).append('\n');
			}
		}
		
		if (builder.length() > 0)
		{
			builder.insert(0, '\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#validate(int)
	 */
	public TreeNode validate(int phase)
	{
		if (phase != 1)
		{
			return this;
		}
		
		ClassNode clazz = (ClassNode)getAncestorOfType(ClassNode.class);
		
		if (clazz == null)
		{
			FileNode file = getFileNode();
			
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
			
			ConstructorNode defaultConstructor = new ConstructorNode(this, null);
			defaultConstructor.setName(getName());
			defaultConstructor.setType(getName());
			defaultConstructor.setVisibility(FieldNode.PUBLIC);
			defaultConstructor.setLocationIn(loc);
			addChild(defaultConstructor);
		}
		
		if (!containsDestructor())
		{
			Location loc = new Location();
			
			DestructorNode defaultDestructor = new DestructorNode(this, null);
			defaultDestructor.setName(getName());
			defaultDestructor.setType("void");
			defaultDestructor.setVisibility(FieldNode.PUBLIC);
			defaultDestructor.setLocationIn(loc);
			
			addChild(defaultDestructor);
		}
		
		getMethodListNode().validate(phase);
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
		
		ProgramNode program  = getProgramNode();
		
		ClassNode   clazz    = program.getClassNode(extendedClass);
		
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
		
//		FieldListNode fields = clazz.getFieldListNode().clone();
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
		ProgramNode program = getProgramNode();
		
		for (String implementedClass : implementedClasses)
		{
			ClassNode clazz = program.getClassNode(implementedClass);
			
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
	 * @param root The root TreeNode to search for the static modifier
	 * 		from.
	 * @return Whether or not the class contains static data.
	 */
	public boolean containsStaticData(TreeNode root)
	{
		for (int i = 0; i < root.getNumChildren(); i++)
		{
			TreeNode child = root.getChild(i);
			
			if (child instanceof InstanceDeclarationNode)
			{
				InstanceDeclarationNode declaration = (InstanceDeclarationNode)child;
				
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
		MethodListNode constructors = getConstructorListNode();
		
		for (int i = 0; i < constructors.getNumChildren(); i++)
		{
			ConstructorNode method = (ConstructorNode)constructors.getChild(i);
			
			if (method.getParameterListNode().getNumChildren() == 1)
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
	 * @param root The root TreeNode to search for the method from.
	 * @param methodName The name of the method to search for.
	 * @param staticVal Whether or not the method is static.
	 * @param type The return type of the method.
	 * @return Whether or not the class contains the method.
	 */
	public boolean containsMethod(TreeNode root, String methodName, boolean staticVal, String type)
	{
		for (int i = 0; i < root.getNumChildren(); i++)
		{
			TreeNode child = root.getChild(i);
			
			if (child instanceof MethodNode)
			{
				MethodNode method = (MethodNode)child;
				
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
	 * Generate a ClassNode with the given parent and location for
	 * temporary use.
	 * 
	 * @param parent The node to set as the ClassNode parent.
	 * @param locationIn The location to set as the ClassNode location.
	 * @return The generated temporary ClassNode.
	 */
	public static ClassNode generateTemporaryClass(TreeNode parent, Location locationIn)
	{
		ClassNode clazz = new ClassNode(parent, locationIn);
		
		return clazz;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
	 */
	@Override
	public ClassNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ClassNode node = new ClassNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ClassNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClassNode cloneTo(ClassNode node)
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