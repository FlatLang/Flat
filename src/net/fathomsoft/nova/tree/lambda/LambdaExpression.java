package net.fathomsoft.nova.tree.lambda;

import java.util.Arrays;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.BodyMethodDeclaration;
import net.fathomsoft.nova.tree.Closure;
import net.fathomsoft.nova.tree.ClosureDeclaration;
import net.fathomsoft.nova.tree.Identifier;
import net.fathomsoft.nova.tree.MethodCall;
import net.fathomsoft.nova.tree.MethodDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.Value;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * {@link Node} extension that represents a lambda expression.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.45 Jun 5, 2014 at 9:00:04 PM
 * @version	v0.2.45 Jun 5, 2016 at 11:43:17 PM
 */
public class LambdaExpression extends Value
{
	public String[] variables; 
	
	public static final String OPERATOR = "->";
	
	private static int id = 1;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public LambdaExpression(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link LambdaExpression} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>x -> x + 1</li>
	 * 	<li>(x, i) -> Console.writeLine(x * i)</li>
	 * 	<li>asdf -> asdf.doSomething()</li>
	 * 	<li>{ Console.writeLine("In parameterless lambda") }</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link LambdaExpression} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link LambdaExpression}.
	 */
	public static Value decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		String[] variables = null;
		int endingIndex = 0;
		
		if (statement.startsWith("("))
		{
			endingIndex = StringUtils.findEndingMatch(statement, 0, '(', ')') + 1;
			
			variables = StringUtils.splitCommas(statement.substring(1, endingIndex - 1));
		}
		else if (statement.startsWith("{"))
		{
			variables = new String[0];
			
			endingIndex = StringUtils.findEndingMatch(statement, 0, '{', '}'); 
		}
		else
		{
			variables = new String[] { StringUtils.findNextWord(statement) };
			
			endingIndex = variables[0].length();
		}
		
		if (endingIndex > 0)
		{
			String operation = null;
			
			if (variables.length > 0)
			{
				operation = statement.substring(endingIndex).trim();
			}
			else
			{
				operation = statement.substring(1, endingIndex).trim();
			}
			
			if (operation.startsWith(OPERATOR) || variables.length == 0)
			{
				if (operation.startsWith(OPERATOR))
				{
					operation = operation.substring(OPERATOR.length()).trim();
				}
				
				MethodCall call = (MethodCall)parent.getAncestorOfType(MethodCall.class);
				
				MethodDeclaration[] methods = ((Value)call.getReferenceNode()).getTypeClass().getMethods(call.getName());
				
				final String[] finalVars = variables;
				final int index = call.getArgumentList().getNumVisibleChildren();
				Nova.debuggingBreakpoint(statement.equals("(x, i) -> x + i + \"?\""));
				
				BodyMethodDeclaration[] validMethods = Arrays.stream(methods)
						.filter(x -> {
							
							if (x instanceof BodyMethodDeclaration &&
									x.getParameterList().getNumVisibleChildren() > index &&
									x.getParameter(index) instanceof ClosureDeclaration)
							{
								ClosureDeclaration closure = (ClosureDeclaration)x.getParameter(index);
								
								if (closure.getParameterList().getNumVisibleChildren() >= finalVars.length)
								{
									return true;
								}
							}
							
							return false;
						})
						.toArray(BodyMethodDeclaration[]::new);
				
				for (BodyMethodDeclaration method : validMethods)
				{
					call.setDeclaration(method);
					
					
				}
				
				if (validMethods.length > 0)
				{
					final int[] i = new int[] { 0 };
					
					ClosureDeclaration closure = (ClosureDeclaration)((BodyMethodDeclaration)call.getDeclaration()).getParameter(index);
					
					String parameters = String.join(", ", Arrays.stream(variables).map(x -> {
						return closure.getParameterList().getParameter(i[0]++).generateNovaType(call) + " " + x;
					}).toArray(String[]::new));
					
					String methodDeclaration = "static testLambda" + id++ + "(" + parameters + ")";
					
					if (closure.getType() != null)
					{
						methodDeclaration += " -> " + closure.generateNovaType(call);
					}
					
					BodyMethodDeclaration method = BodyMethodDeclaration.decodeStatement(parent.getParentClass(true), methodDeclaration, location.asNew(), require); 
					
					if (method != null)
					{
						method.getParentClass().addChild(method);
						
						if (method.getType() != null)
						{
							operation = "return " + operation;
						}
						
						Node node = SyntaxTree.decodeScopeContents(method, operation, location.asNew());
						method.addChild(node);
						
						Closure methodReference = Closure.decodeStatement(parent, method.generateNovaClosureReference(method.getParentClass()), location.asNew(), require);
						methodReference.onAfterDecoded();
						
						return methodReference;
					}
				}
			}
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
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public LambdaExpression clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		LambdaExpression node = new LambdaExpression(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public LambdaExpression cloneTo(LambdaExpression node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link LambdaExpression} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LambdaExpression cloneTo(LambdaExpression node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		node.id = id;
		
		return node;
	}
	
	/**
	 * Test the {@link LambdaExpression} class type to make sure everything
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
	public Value getReturnedNode()
	{
		return null;
	}
	
	@Override
	public String getType()
	{
		Value returned = getReturnedNode();
		
		if (returned == null)
		{
			return null;
		}
		
		return getReturnedNode().getType();
	}

	@Override
	public void setTypeValue(String type)
	{
		Value returned = getReturnedNode();
		
		if (returned == null)
		{
			return;
		}
		
		getReturnedNode().setTypeValue(type);
	}

	@Override
	public int getArrayDimensions()
	{
		Value returned = getReturnedNode();
		
		if (returned == null)
		{
			return 0;
		}
		
		return getReturnedNode().getArrayDimensions();
	}

	@Override
	public void setArrayDimensions(int arrayDimensions)
	{
		Value returned = getReturnedNode();
		
		if (returned == null)
		{
			return;
		}
		
		returned.setArrayDimensions(returned.getArrayDimensions());
	}

	@Override
	public byte getDataType()
	{
		Value returned = getReturnedNode();
		
		if (returned == null)
		{
			return 0;
		}
		
		return getReturnedNode().getDataType();
	}

	@Override
	public void setDataType(byte type)
	{
		Value returned = getReturnedNode();
		
		if (returned == null)
		{
			return;
		}
		
		getReturnedNode().setDataType(type);
	}
}