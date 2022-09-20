package flat.validator.annotations;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.Variable;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;
import flat.util.StringUtils;
import flat.validator.NodeValidator;

public class ObsoleteAnnotationValidator extends AnnotationValidator
{
	public String aliasUsed;
	
	@Override
	public String getAliasUsed()
	{
		return aliasUsed;
	}
	
	@Override
	public void setAliasUsed(String aliasUsed)
	{
		this.aliasUsed = aliasUsed;
	}
	
	public boolean fail;
	
	public String message;
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public ObsoleteAnnotationValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link ObsoleteAnnotationValidator} instance, if
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
	 * 		{@link ObsoleteAnnotationValidator} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link ObsoleteAnnotationValidator}.
	 */
	public static ObsoleteAnnotationValidator decodeStatement(NodeValidator parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Obsolete"))
		{
			ObsoleteAnnotationValidator n = new ObsoleteAnnotationValidator(parent, location);
			
			if (parameters.length() > 0)
			{
				String[] args = StringUtils.splitCommas(parameters);
				
				if (args.length >= 1 && args.length < 3)
				{
					Value messageNode = Literal.decodeStatement(n, args[0], location, require, true);
					
					if (messageNode instanceof Literal)
					{
						Literal message = (Literal) messageNode;
						
						if (message.getType().equals("String"))
						{
							n.message = message.getValue();
						}
						else if (args.length > 1)
						{
							SyntaxMessage.queryError("Obsolete requires that if a message is given, for it to be the first argument", n, require);
							
							return null;
						}
					}
					
					if (args.length >= 2)
					{
						Literal fail = (Literal)Literal.decodeStatement(n, args[1], location, require, true);
						
						if (fail.getType().equals("Bool"))
						{
							n.fail = Boolean.parseBoolean(fail.getValue());
						}
					}
				}
				else
				{
					n.tooManyArguments(require);
					
					return null;
				}
			}
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public void onAdded(NodeValidator parent)
	{
		ModifierAnnotation.super.onAdded(parent);
		super.onAdded(parent);
	}
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			NodeValidator nodeValidator = getParent();
			
			if (nodeValidator instanceof VariableDeclaration == false)
			{
				invalidApplication(nodeValidator, true);
			}
			
			VariableDeclaration decl = (VariableDeclaration) nodeValidator;
			
			for (Variable reference : decl.references)
			{
				Variable v = reference;//(Variable)reference.getReferenceNode();
				
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
	public boolean onNextStatementDecoded(NodeValidator next)
	{
		if (next instanceof FieldDeclaration || next instanceof FlatMethodDeclaration)
		{
			next.addAnnotation(this);
			
			VariableDeclaration decl = (VariableDeclaration)next;
			
			String output = "";
			String type = "";
			
			if (decl instanceof FieldDeclaration)
			{
				output = decl.generateFlatInput().toString();
				
				type = "Field";
			}
			else
			{
				output = ((FlatMethodDeclaration)decl).generateFlatSignature();
				
				type = "Method";
			}
			
			message = type + " '" + output + "' is obsolete" + (message.length() > 0 ? ": " : "") + message;
		}
		
		return super.onNextStatementDecoded(next);
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public ObsoleteAnnotationValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ObsoleteAnnotationValidator node = new ObsoleteAnnotationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public ObsoleteAnnotationValidator cloneTo(ObsoleteAnnotationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ObsoleteAnnotationValidator} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ObsoleteAnnotationValidator cloneTo(ObsoleteAnnotationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	/**
	 * Test the {@link ObsoleteAnnotationValidator} class type to make sure everything
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
	public String[] getAliases()
	{
		return new String[] { "obsolete" };
	}
}