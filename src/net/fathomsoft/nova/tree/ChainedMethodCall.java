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
	
	public Identifier getChainReference()
	{
		if (variable != null)
		{
			return variable;
		}
		
		ChainedMethodCall base = getChainBase();
		
		if (base.declaration instanceof FirstClassClosureDeclaration)
		{
			return ((FirstClassClosureDeclaration)base.declaration).reference;
		}
		else
		{
			FunctionType type = (FunctionType)base.getNovaTypeValue(base).getTypeObject();
			
			return type.closure.reference;
		}
	}
	
	@Override
	public StringBuilder generateNovaInputUntil(StringBuilder builder, Accessible stopAt)
	{
		if (stopAt instanceof ChainedMethodCall)
		{
			throw new UnimplementedOperationException("Unimplemented");
		}
		
		return super.generateNovaInputUntil(builder, stopAt);
	}
	
	@Override
	public Value getReturnedNode()
	{
		return chained != null ? chained.getReturnedNode() : this;
	}
	
	public ChainedMethodCall getChainBase()
	{
		ChainedMethodCall current = this;
		
		while (current.parent instanceof ChainedMethodCall)
		{
			current = (ChainedMethodCall)current.parent;
		}
		
		return current;
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
	public static Identifier decodeStatementValue(Node parent, String statement, Location location, boolean require)
	{
		if (statement.endsWith(")"))
		{
			int match = StringUtils.findEndingMatch(statement, statement.length() - 1, '(', ')', -1);
			
			if (match > 0)
			{
				String preChain = statement.substring(0, match).trim();
				
				Value value = SyntaxTree.decodeValue(parent, preChain, location, require);
				
				if (value != null && value.getReturnedNode() instanceof MethodCall)
				{
					MethodCall call = (MethodCall)value.getReturnedNode();
					ChainedMethodCall ref = null;
					
					if (call instanceof ChainedMethodCall)
					{
						ref = (ChainedMethodCall)call.getReturnedNode();
					}
					else
					{
						ref = new ChainedMethodCall(parent, call.getLocationIn());
						call.cloneTo(ref);
					}
					
					ChainedMethodCall n = new ChainedMethodCall(call, location);
					
//					VariableDeclaration declaration = call.getCallableDeclaration().toDeclaration();
					
					Value funcType = call.getNovaTypeValue(call);
					
					if (!funcType.isFunctionType())
					{
						SyntaxMessage.error("Function '" + call.getCallableDeclaration().toDeclaration().getName() + "' does not return a function reference and cannot be called as a function chain", n);
					}
					
					FunctionType type = (FunctionType)funcType.getTypeObject();
					
					if (!n.decodeArguments(statement, SyntaxUtils.findInnerParenthesesBounds(n, statement), true))//statement.substring(match + 1, statement.length() - 1).trim())
					{
						return null;
					}
					
					n.setDeclaration(type.closure);
					ref.chained = n;
					n.parent = ref;
					
					ChainedMethodCall base = ref.getChainBase();
					
					if (value != call)
					{
						call.getAccessingNode().setAccessedNode(ref);
						
						return (Identifier)value;
					}
					
					return base;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public Identifier getAccessedNode()
	{
		return chained == null ? super.getAccessedNode() : null;
	}
	
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		super.generateNovaInput(builder, false);
		
		generateChain(builder);
		
		return generateAccessedNode(builder, safeNavigation);
	}
	
	public StringBuilder generateChain(StringBuilder builder)
	{
		if (chained != null)
		{
			builder.append("(");
			chained.getArgumentList().generateNovaInput(builder);
			builder.append(")");
			
			chained.generateChain(builder);
		}
		
		return builder;
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
		
		if (chained != null)
		{
			result = SyntaxTree.validateNodes(chained, phase);
		}
		
		if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (chained != null)
			{
				variable = getAncestorWithScope().getScope().createLocalVariable(this);
				variable.declaration.setType(this);
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