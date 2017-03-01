package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * {@link MethodCall} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class ChainedMethodCall extends MethodCall
{
	public ChainedMethodCall chained;
	public Variable variable;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ChainedMethodCall(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link ChainedMethodCall} instance, if
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
	 * @param statement The statement to try to decode into a
	 * 		{@link ChainedMethodCall} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link ChainedMethodCall}.
	 */
	public static ChainedMethodCall decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.endsWith(")"))
		{
			int match = StringUtils.findEndingMatch(statement, statement.length() - 1, '(', ')', -1);
			
			if (match > 0)
			{
				String preChain = statement.substring(0, match).trim();
				
				MethodCall call = MethodCall.decodeStatement(parent, preChain, location, require);
				
				if (call != null)
				{
					ChainedMethodCall ref = new ChainedMethodCall(parent, call.getLocationIn());
					call.cloneTo(ref);
					
					ChainedMethodCall n = new ChainedMethodCall(call, location);
					
					VariableDeclaration declaration = call.getCallableDeclaration().toDeclaration();
					
					if (declaration.getTypeObject() instanceof FunctionType == false)
					{
						SyntaxMessage.error("Function '" + declaration.getName() + "' does not return a function reference and cannot be called as a function chain", n);
					}
					
					FunctionType type = (FunctionType)declaration.getTypeObject();
					
					n.setDeclaration(type.closure);
					ref.chained = n;
					
					return ref;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 *
	 * @param phase The phase that the node is being validated in.
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (chained != null)
			{
				variable = getAncestorWithScope().getScope().createLocalVariable(this);
			}
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ChainedMethodCall clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ChainedMethodCall node = new ChainedMethodCall(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ChainedMethodCall cloneTo(ChainedMethodCall node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ChainedMethodCall} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ChainedMethodCall cloneTo(ChainedMethodCall node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.chained = chained;
		node.variable = variable;
		
		return node;
	}
	
	/**
	 * Test the {@link ChainedMethodCall} class type to make sure everything
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