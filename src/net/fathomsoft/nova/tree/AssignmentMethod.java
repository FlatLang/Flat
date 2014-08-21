package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.FieldList;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;
import net.fathomsoft.nova.util.Location;

/**
 * {@link NovaMethodDeclaration} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.28 Aug 12, 2014 at 1:54:08 AM
 * @version	v0.2.28 Aug 20, 2014 at 12:10:45 AM
 */
public class AssignmentMethod extends NovaMethodDeclaration
{
	public static final String	IDENTIFIER = InitializationMethod.SUPER_IDENTIFIER;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public AssignmentMethod(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		NovaMethodDeclaration method = decodeStatement(temporaryParent, "public " + IDENTIFIER + "()", Location.INVALID, true);
		
		method.cloneTo(this);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#generateCHeader(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		return generateCSourcePrototype(builder).append('\n');
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.NovaMethodDeclaration#generateCSource(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		generateCSourceSignature(builder).append('\n');
		
		builder.append('{').append('\n');
		
		Nova.debuggingBreakpoint(getFileDeclaration().getName().equals("Math"));
		
		generateFieldDefaultAssignments(builder);
		
		builder.append('}').append('\n');
		
		return builder;
	}
	
	/**
	 * @see #generateCMethodCall(java.lang.StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateCMethodCall(StringBuilder builder)
	{
		return generateCMethodCall(builder, false);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.NovaMethodDeclaration#generateCMethodCall(java.lang.StringBuilder)
	 * 
	 * @param cast Whether or not to add an explicit type cast for the
	 * 		object reference identifier.
	 */
	public StringBuilder generateCMethodCall(StringBuilder builder, boolean cast)
	{
		super.generateCMethodCall(builder).append("(");
		
		if (cast)
		{
			builder.append('(').append(getParentClass().generateCType()).append(')');
		}
		
		builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
		
		return builder.append(", 0);\n");
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
				
				if (field.containsInitializationValue())
				{
					field.getInitializationValue().generateCSourceFragment(builder);
				}
				else if (!field.isPrimitiveType())
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
	 * Get whether or not the specified Method has overridden a method
	 * from a super class
	 * 
	 * @return Whether or not the specified Method has overridden a
	 * 		method from a super class.
	 */
	public boolean doesOverride()
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.NovaMethodDeclaration#isVirtual()
	 */
	@Override
	public boolean isVirtual()
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public AssignmentMethod clone(Node temporaryParent, Location locationIn)
	{
		AssignmentMethod node = new AssignmentMethod(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link AssignmentMethod} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public AssignmentMethod cloneTo(AssignmentMethod node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the {@link AssignmentMethod} class type to make sure everything
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