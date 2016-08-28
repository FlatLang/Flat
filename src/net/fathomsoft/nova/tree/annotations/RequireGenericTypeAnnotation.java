package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Identifier;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.tree.generics.GenericTypeParameterDeclaration;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * {@link Annotation} extension that represents
 * 
 * @author	Braden Steffaniak
 */
public class RequireGenericTypeAnnotation extends Annotation
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public RequireGenericTypeAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		addChild(new GenericTypeParameterDeclaration(this, locationIn));
	}
	
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	public GenericTypeParameterDeclaration getGenericTypeParameterDeclaration()
	{
		return (GenericTypeParameterDeclaration)getChild(super.getNumDefaultChildren() + 0);
	}
	
	/**
	 * Decode the given statement into a {@link RequireGenericTypeAnnotation} instance, if
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
	 * @param name The statement to try to decode into a
	 * 		{@link RequireGenericTypeAnnotation} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link RequireGenericTypeAnnotation}.
	 */
	public static RequireGenericTypeAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("RequireGenericType"))
		{
			RequireGenericTypeAnnotation n = new RequireGenericTypeAnnotation(parent, location);
			
			if (parameters.length() == 0)
			{
				requiresArguments(n, require);
				
				return null;
			}
			
			n.getGenericTypeParameterDeclaration().decodeGenericTypeParameters(parameters);
			
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
		
		Node node = getParent();
		
		if (node instanceof VariableDeclaration == false)
		{
			invalidExpression(this, true);
		}
		
		VariableDeclaration decl = (VariableDeclaration)node;
		
		for (Variable reference : decl.references)
		{
			if (reference.getParentMethod() == null || reference.getParentMethod().isUserMade())
			{
				Variable v = (Variable) reference.getReferenceNode();
				
				for (GenericTypeParameter required : getGenericTypeParameterDeclaration())
				{
					int index = v.getDeclaration().getTypeClass().getGenericTypeParameterDeclaration().getParameterIndex(required.getName());
					
					GenericTypeArgument given = v.getIntelligentGenericTypeArgument(index);
					
					if (!SyntaxUtils.isTypeCompatible(v.getContext(), getFileDeclaration().getImportedClass(this, required.getDefaultType()), given.getTypeClass()))
					{
						SyntaxMessage.error("Method call on method '" + v.getName() + "' requires a generic type of '" + required.getDefaultType() + "'", v, false);
					}
				}
			}
		}
		
		return result;
	}
	
	@Override
	public boolean onNextStatementDecoded(Node next)
	{
		if (next instanceof VariableDeclaration)
		{
			next.addAnnotation(this);
		}
		
		return super.onNextStatementDecoded(next);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public RequireGenericTypeAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		RequireGenericTypeAnnotation node = new RequireGenericTypeAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public RequireGenericTypeAnnotation cloneTo(RequireGenericTypeAnnotation node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link RequireGenericTypeAnnotation} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public RequireGenericTypeAnnotation cloneTo(RequireGenericTypeAnnotation node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link RequireGenericTypeAnnotation} class type to make sure everything
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