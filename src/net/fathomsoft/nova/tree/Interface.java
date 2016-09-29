package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * {@link ClassDeclaration} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.38 Nov 26, 2014 at 7:17:45 PM
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class Interface extends ClassDeclaration
{
	public static final String IDENTIFIER = "interface";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Interface(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ClassDeclaration#isAbstract()
	 */
	@Override
	public boolean isAbstract()
	{
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ClassDeclaration#getExtendedClassLocation()
	 */
	@Override
	public String getExtendedClassLocation()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ClassDeclaration#generateCSource(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return super.generateCSource(builder);
	}
	
	/**
	 * Decode the given statement into a {@link Interface} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public interface TestInterface</li>
	 * 	<li>public interface Stream</li>
	 * 	<li>public interface InterfaceName</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link Interface} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link Interface}.
	 */
	public static Interface decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (StringUtils.containsWord(statement, IDENTIFIER))
		{
			statement = statement.replaceFirst(IDENTIFIER, ClassDeclaration.IDENTIFIER);
			
			ClassData data = new ClassData(false, false, true);
			
			ClassDeclaration clazz = ClassDeclaration.decodeStatement(parent, statement, location, require, data);
			
			if (clazz != null)
			{
				Interface n = new Interface(parent, location);
				
				clazz.cloneTo(n);
				n.setExtendedClass(null);
				
				return n;
			}
		}
		
		return null;
	}
	
	@Override
	public void addDefaultConstructor()
	{
		
	}
	
	@Override
	public void addDefaultDestructor()
	{
		
	}
	
	@Override
	public void addAssignmentMethods()
	{
		
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Interface clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Interface node = new Interface(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Interface cloneTo(Interface node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Interface} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Interface cloneTo(Interface node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link Interface} class type to make sure everything
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