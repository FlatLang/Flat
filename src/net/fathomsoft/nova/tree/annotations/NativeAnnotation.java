package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.Assignment;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

public class NativeAnnotation extends Annotation implements ModifierAnnotation
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public NativeAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return builder.append("[Native]");
	}
	
	@Override
	public boolean onApplied(Node appliedTo, boolean throwError)
	{
		return appliedTo instanceof VariableDeclaration || appliedTo instanceof Assignment || super.onApplied(appliedTo, throwError);
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "native" };
	}
	
	/**
	 * Decode the given statement into a {@link NativeAnnotation} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li></li>
	 * 	<li></li>
	 * 	<li></li>
	 * </ul>
	 *
	 * @param parent The parent node of the statement.
	 * @param parameters The statement to try to decode into a
	 * 		{@link NativeAnnotation} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link NativeAnnotation}.
	 */
	public static NativeAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Native"))
		{
			NativeAnnotation n = new NativeAnnotation(parent, location);
			
			return n;
		}
		
		return null;
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
	
	@Override
	public void onAdded(Node parent)
	{
		if (parent instanceof Assignment)
		{
			((Assignment)parent).getAssignedNode().getDeclaration().addAnnotation(this);
		}
		
		super.onAdded(parent);
	}
	
	@Override
	public boolean onNextStatementDecoded(Node next)
	{
		onAdded(next);
		
		if (next instanceof Assignment == false)
		{
			next.addAnnotation(this);
		}
		
		return super.onNextStatementDecoded(next);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public NativeAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		NativeAnnotation node = new NativeAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public NativeAnnotation cloneTo(NativeAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link NativeAnnotation} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public NativeAnnotation cloneTo(NativeAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link NativeAnnotation} class type to make sure everything
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