package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.FieldList;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;

/**
 * MethodDeclaration extension that represents the declaration of a Constructor
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:50:47 PM
 * @version	v0.2.27 Aug 7, 2014 at 1:32:02 AM
 */
public class Constructor extends BodyMethodDeclaration
{
	public static final String	IDENTIFIER = "construct";
	
	/**
	 * Create a Constructor and initialize default values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Constructor(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#isInstance()
	 */
	@Override
	public boolean isInstance()
	{
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#onAdded(net.fathomsoft.nova.tree.Node)
	 */
	@Override
	public void onAdded(Node parent)
	{
		
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		if (isVisibilityValid())
		{
			if (getVisibility() == InstanceDeclaration.PRIVATE)
			{
				return builder;
			}
		}
		if (isConstant())
		{
			SyntaxMessage.error("Constructor cannot be const", this);
		}
		
		if (isReference())
		{
			SyntaxMessage.error("Constructor cannot return a reference", this);
		}
		
		return generateCSourcePrototype(builder).append('\n');
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		generateCSourceSignature(builder).append('\n');
		
		builder.append('{').append('\n');
		
		ClassDeclaration classDeclaration = getParentClass();
		
		if (classDeclaration.containsNonStaticData() || classDeclaration.containsVirtualMethods())
		{
			builder.append("CCLASS_NEW(").append(getTypeClassName()).append(", ").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
			
			if (!classDeclaration.containsNonStaticPrivateData())
			{
				builder.append(",");
			}
			
			builder.append(");");
		}
		else
		{
			builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(" = ").append(generateCTypeCast()).append("1").append(';');
		}
		
		builder.append('\n').append('\n');
		
		generateFieldDefaultAssignments(builder);
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child != getParameterList())
			{
				child.generateCSource(builder);
			}
		}
		
		builder.append('\n');
		
		builder.append("return ").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(';').append('\n');
		
		builder.append('}').append('\n');
		
		return builder;
	}
	
	/**
	 * This method returns a String that contains the code needed to
	 * assign the default null value to each uninitialized/uninstantiated
	 * field variables.
	 * 
	 * @param builder The StringBuilder to append the assignments to.
	 * @return The appended buffer.
	 */
	private StringBuilder generateFieldDefaultAssignments(StringBuilder builder)
	{
		FieldList fields = getParentClass().getFieldList();
		
		generateFieldDefaultAssignments(builder, fields.getPublicFieldList());
		generateFieldDefaultAssignments(builder, fields.getPrivateFieldList());
		
		if (getParentClass().containsVirtualMethods())
		{
			VTable table = getParentClass().getVTableNode();
			
			builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append("->").append(VTable.IDENTIFIER).append(" = &").append(table.generateCSourceName()).append(";\n");
		}
		
		return builder;
	}
	
	/**
	 * This method returns a String that contains the code needed to
	 * assign the default null value to each uninitialized/uninstantiated
	 * field variables.
	 * 
	 * @param builder The StringBuilder to append the assignments to.
	 * @param fields The list of fields to assign default values to.
	 * @return The appended buffer.
	 */
	private StringBuilder generateFieldDefaultAssignments(StringBuilder builder, InstanceFieldList fields)
	{
		for (int i = 0; i < fields.getNumChildren(); i++)
		{
			FieldDeclaration field = (FieldDeclaration)fields.getChild(i);
			
			if (!field.isExternal())
			{
				field.generateCUseOutput(builder).append(" = ");
				
				if (!field.isPrimitiveType())
				{
					field.generateCNullOutput(builder);
				}
				else
				{
					builder.append(0);
				}
				
				builder.append(';').append('\n');
			}
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#generateCSourcePrototype(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourcePrototype(StringBuilder builder)
	{
		return generateCSourceSignature(builder).append(";");
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#generateCSourceSignature(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceSignature(StringBuilder builder)
	{
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		builder.append(getType()).append('*');
		
		builder.append(' ');
		
		generateCSourceName(builder).append('(');
		
		getParameterList().generateCSource(builder);
		
		builder.append(')');
		
		return builder;
	}
	
	@Override
	public StringBuilder generateCSourceName(StringBuilder builder)
	{
		builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append('_');
		
		if (getOverloadID() >= 0)
		{
			builder.append(getOverloadID()).append('_');
		}
		
		builder.append(getParentClass().getName()).append('_');
		
		return builder.append(IDENTIFIER);
	}
	
	/**
	 * Decode the given statement into a Constructor instance, if
	 * possible. If it is not possible, this method returns null. A
	 * constructor must have the same name as the class that it is
	 * within. Constructors also do not have a return value.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public ClassName()</li>
	 * 	<li>private ClassName(int numChildren, String name)</li>
	 * 	<li>public ClassName(Node parent, Location location)</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Constructor instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Constructor.
	 */
	public static Constructor decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		MethodDeclaration method = BodyMethodDeclaration.decodeStatement(parent, statement, location, false);
		
		if (method != null && method.getName().equals(IDENTIFIER))
		{
			Constructor n = new Constructor(parent, location);
			
			method.cloneTo(n);
			
//			n.setName(n.getParentClass().getName());
			n.setName(IDENTIFIER);
			n.setType(n.getParentClass().getName(), true, false);
			n.setStatic(true);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * Validate that the specified statement really is a Constructor
	 * declaration.
	 * 
	 * @param The data that will specify if there was an error.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return Whether or not the statement is a Constructor declaration.
	 */
	private boolean validateDeclaration(ExtraData data, boolean require)
	{
		if (getType() != null)
		{
			return false;
		}
		else if (data.error != null)
		{
			return SyntaxMessage.queryError(data.error, this, require);
		}
		
		setName(getParentClass().getName());
		
		return setType(getName(), false);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, int, net.fathomsoft.nova.util.Bounds, int, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		super.interactWord(word, wordNumber, bounds, numWords, leftDelimiter, rightDelimiter, extra);
		
		if (wordNumber == numWords - 1)
		{
			if (!word.equals(IDENTIFIER))
			{
				extra.error = "Constructor must be named \"" + IDENTIFIER + '"';
			}
		}
		else if (word.equals("static"))
		{
			extra.error = "Constructor cannot be declared as static";
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Constructor clone(Node temporaryParent, Location locationIn)
	{
		Constructor node = new Constructor(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Constructor} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Constructor cloneTo(Constructor node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the Constructor class type to make sure everything
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