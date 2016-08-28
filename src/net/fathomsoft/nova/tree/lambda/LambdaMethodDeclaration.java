package net.fathomsoft.nova.tree.lambda;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

/**
 * {@link BodyMethodDeclaration} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class LambdaMethodDeclaration extends BodyMethodDeclaration
{
	private int unnamedParameterPosition = 0;
	
	public ClosureContext context;
	
	public ClosureContextDeclaration contextDeclaration;
	
	private Scope scope;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public LambdaMethodDeclaration(Node temporaryParent, Location locationIn, Scope scope)
	{
		super(temporaryParent, locationIn);
		
		this.scope = scope;
		context = new ClosureContext(this, locationIn);
		
		context.id = getFileDeclaration().registerClosureContext(context);
	}
	
	@Override
	public StringBuilder generateCClosureContext(StringBuilder builder)
	{
		return builder.append('&').append(contextDeclaration.getName());
	}
	
	public Parameter getNextUnnamedParameter()
	{
		if (getParameterList().getNumParameters() <= unnamedParameterPosition)
		{
			SyntaxMessage.error("Too many unnamed lambda expression parameters used", this);
			
			return null;
		}
		
		return getParameterList().getParameter(unnamedParameterPosition++);
	}
	
	@Override
	public VariableDeclaration searchVariable(Node parent, Scope scope, String name, boolean checkAncestors)
	{
		VariableDeclaration var = super.searchVariable(parent, scope, name, checkAncestors);
		
		if (var != null)
		{
			return var;
		}
		
		var = this.scope.searchVariable(parent, scope, name, checkAncestors);
		
		if (var != null)
		{
			return context.addDeclaration(var);
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public LambdaMethodDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		LambdaMethodDeclaration node = new LambdaMethodDeclaration(temporaryParent, locationIn, scope);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public LambdaMethodDeclaration cloneTo(LambdaMethodDeclaration node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link LambdaMethodDeclaration} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LambdaMethodDeclaration cloneTo(LambdaMethodDeclaration node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link LambdaMethodDeclaration} class type to make sure everything
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