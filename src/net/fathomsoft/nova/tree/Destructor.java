package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TargetC;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;
import net.fathomsoft.nova.util.Location;

/**
 * MethodDeclaration extension that represents the declaration of a Destructor
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:50:43 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class Destructor extends BodyMethodDeclaration
{
	public static final String IDENTIFIER = "destroy";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Destructor(Node temporaryParent, Location locationIn)
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
	
	@Override
	public boolean isUsed()
	{
		return true;
	}
	
	/**
	 * Decode the given statement into a Destructor instance, if
	 * possible. If it is not possible, this method returns null. A
	 * destructor must have the same name as the class that it is
	 * within preceded by a tilde (A tilde is a '~' located above the tab
	 * key). Destructors also do not have a return value and are always
	 * private. They never accept parameters, because they are never
	 * called programmatically.<br>
	 * <br>
	 * The only acceptable syntax input includes: "private ~ClassName()"
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Destructor instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Destructor.
	 */
	public static Destructor decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		NovaMethodDeclaration m = NovaMethodDeclaration.decodeStatement(parent, statement, location, require);
		
		if (m != null)
		{
			Destructor n = new Destructor(parent, location);
			
			m.cloneTo(n);
			
			if (n.checkName() && n.validateParameters(require))
			{
				n.getParameterList().getObjectReference().setDataType(DOUBLE_POINTER);
				
				return n;
			}
		}
		
		return null;
	}
	
	private boolean checkName()
	{
		return getName().equals(IDENTIFIER);
	}
	
	/**
	 * Validate that there are no parameters because Destructors cannot
	 * have any parameters.
	 * 
	 * @param parameterList The String containing the parameters.
	 */
	private boolean validateParameters(boolean require)
	{
		if (getParameterList().getNumVisibleChildren() > 0)
		{
			return SyntaxMessage.queryError("Destructors cannot have any parameters", this, require);
		}
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Destructor clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Destructor node = new Destructor(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Destructor cloneTo(Destructor node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Destructor} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Destructor cloneTo(Destructor node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the Destructor class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	@Override
	public TargetC.TargetDestructor getTarget()
	{
		final Destructor self = this;
		
		return new TargetC.TargetDestructor()
		{
			public Destructor node()
			{
				return self;
			}
		};
	}
}