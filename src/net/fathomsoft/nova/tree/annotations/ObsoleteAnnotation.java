package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Identifier;
import net.fathomsoft.nova.tree.Literal;
import net.fathomsoft.nova.tree.MethodDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.NovaMethodDeclaration;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.tree.generics.GenericTypeParameterDeclaration;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * {@link Annotation} extension that represents
 * 
 * @author	Braden Steffaniak
 */
public class ObsoleteAnnotation extends Annotation
{
	public boolean fail;
	
	public String message;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ObsoleteAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link ObsoleteAnnotation} instance, if
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
	 * 		{@link ObsoleteAnnotation} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link ObsoleteAnnotation}.
	 */
	public static ObsoleteAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Obsolete"))
		{
			ObsoleteAnnotation n = new ObsoleteAnnotation(parent, location);
			
			if (parameters.length() > 0)
			{
				String[] args = StringUtils.splitCommas(parameters);
				
				if (args.length >= 1 && args.length < 3)
				{
					Literal message = Literal.decodeStatement(n, args[0], location, require, true);
					
					if (message.getType().equals("String"))
					{
						n.message = message.getValue();
					}
					else if (args.length > 1)
					{
						SyntaxMessage.queryError("Obsolete requires that if a message is given, for it to be the first argument", n, require);
						
						return null;
					}
					
					if (args.length >= 2)
					{
						Literal fail = Literal.decodeStatement(n, args[1], location, require, true);
						
						if (fail.getType().equals("Bool"))
						{
							n.fail = Boolean.parseBoolean(fail.getValue());
						}
					}
				}
				else
				{
					tooManyArguments(n, require);
					
					return null;
				}
			}
			
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
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			Node node = getParent();
			
			if (node instanceof VariableDeclaration == false)
			{
				invalidExpression(this, true);
			}
			
			VariableDeclaration decl = (VariableDeclaration)node;
			
			for (Variable reference : decl.references)
			{
				Variable v = (Variable)reference.getReferenceNode();
				
				if (fail)
				{
					SyntaxMessage.error(message, v);
				}
				else
				{
					SyntaxMessage.warning(message, v);
				}
			}
		}
		
		return result;
	}
	
	@Override
	public boolean onNextStatementDecoded(Node next)
	{
		if (next instanceof FieldDeclaration || next instanceof NovaMethodDeclaration)
		{
			next.addAnnotation(this);
			
			VariableDeclaration decl = (VariableDeclaration)next;
			
			String output = "";
			String type = "";
			
			if (decl instanceof FieldDeclaration)
			{
				output = decl.generateNovaInput().toString();
				
				type = "Field";
			}
			else
			{
				output = ((NovaMethodDeclaration)decl).generateNovaSignature();
				
				type = "Method";
			}
			
			message = type + " '" + output + "' is obsolete" + (message.length() > 0 ? ": " : "") + message;
		}
		
		return super.onNextStatementDecoded(next);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ObsoleteAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ObsoleteAnnotation node = new ObsoleteAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ObsoleteAnnotation cloneTo(ObsoleteAnnotation node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link ObsoleteAnnotation} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ObsoleteAnnotation cloneTo(ObsoleteAnnotation node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link ObsoleteAnnotation} class type to make sure everything
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