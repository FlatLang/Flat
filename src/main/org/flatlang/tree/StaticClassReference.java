package org.flatlang.tree;

import org.flatlang.TestContext;
import org.flatlang.tree.generics.GenericTypeArgumentList;
import org.flatlang.tree.variables.Variable;
import org.flatlang.util.Location;
import org.flatlang.util.SyntaxUtils;

/**
 * {@link IIdentifier} extension that represents the use of a Static
 * Class name to reference a static instance within the specified
 * Class.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.9 Aug 23, 2014 at 11:25:19 PM
 * @version	v0.2.34 Oct 1, 2014 at 9:51:33 PM
 */
public class StaticClassReference extends IIdentifier
{
	/**
	 * @see Node#Node(Node, Location)
	 */
	public StaticClassReference(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link StaticClassReference} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>Math</li>
	 * 	<li>Object</li>
	 * 	<li>ClassName</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link StaticClassReference} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link StaticClassReference}.`
	 */
	public static StaticClassReference decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		Node ref = parent instanceof Accessible && ((Accessible) parent).canAccess() ? ((Accessible) parent).toValue().getTypeClass() : parent;
		ref = ref == null ? parent : ref;

		if (ref.getFileDeclaration().getClassDeclaration(statement) != null || ref.getFileDeclaration().containsImport(statement, false))
		{
			StaticClassReference n = new StaticClassReference(parent, location);
			
			n.setName(statement);
			n.setType(statement);
			
			return n;
		}
		
		return null;
	}

	public boolean isMetaClass() {
		return !isDecoding() && !doesAccess() && (parent instanceof MethodCallArgumentList == false || parent.parent.isDecoding() || !((MethodCallArgumentList)parent).getMethodDeclaration().getParentClass().getClassLocation().equals("flatlang/meta/Class")) && parent.getAncestorOfType(GenericTypeArgumentList.class, true) != null;
	}
	
	@Override
	public ClassDeclaration getTypeClass(boolean checkCast, boolean defaultGenericType)
	{
		if (isMetaClass())
		{
			return getProgram().getClassDeclaration("flatlang/meta/Class");
		}
		
		return super.getTypeClass(checkCast, defaultGenericType);
	}
	
	@Override
	public String getType(boolean checkCast)
	{
		if (isMetaClass())
		{
			return "Class";
		}
		
		return super.getType(checkCast);
	}
	
	@Override
	public boolean onAfterDecoded()
	{
		if (!doesAccess())
		{
			Variable var = (Variable)SyntaxTree.decodeIdentifier(this, "class", getLocationIn(), false);
			
			if (var != null)
			{
//				setAccessedNode(var);
			}
		}
		
		return super.onAfterDecoded();
	}
	
	@Override
	public ClassDeclaration getDeclaringClass()
	{
		if (isAccessed()) {
			return getAccessingNode().getDeclaringClass();
		}

		return SyntaxUtils.getImportedClass(getFileDeclaration(), getName());
	}
	
	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public StaticClassReference clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		StaticClassReference node = new StaticClassReference(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public StaticClassReference cloneTo(StaticClassReference node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link StaticClassReference} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public StaticClassReference cloneTo(StaticClassReference node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link StaticClassReference} class type to make sure everything
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