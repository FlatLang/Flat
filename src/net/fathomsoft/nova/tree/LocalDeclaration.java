package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.generics.GenericArgument;
import net.fathomsoft.nova.tree.generics.GenericDeclaration;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * LocalVariable extension that represents the declaration of local
 * variable.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 Jan 5, 2014 at 9:10:49 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class LocalDeclaration extends VariableDeclaration
{
	private int scopeID;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public LocalDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the ID of the scope that the variable was declared in.
	 * 
	 * @return The ID of the scope that the variable was declared in.
	 */
	public int getScopeID()
	{
		return scopeID;
	}
	
	/**
	 * Set the ID of the scope that the variable was declared in.
	 * 
	 * @param scopeID The ID of the scope that the variable was declared
	 * 		in.
	 */
	public void setScopeID(int scopeID)
	{
		this.scopeID = scopeID;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#onAdded(Node)
	 */
	@Override
	public void onAdded(Node parent)
	{
		LocalDeclaration previous = this;
		
		for (int i = 0; i < extraDeclarations.length; i++)
		{
			String name = extraDeclarations[i];
			String type = "";
			
			if (!StringUtils.containsMultipleWords(name))
			{
				type = previous.generateNovaType() + "";
			}
			
			LocalDeclaration local = LocalDeclaration.decodeStatement(getParent(), type + " " + name, getLocationIn(), true);
			
			local.setVolatile(isVolatile());
			
			parent.addChildAfter(previous, local);
			
			previous = local;
		}
		
		extraDeclarations = new String[0];
		
		super.onAdded(parent);
	}

	/**
	 * Decode the given statement into a LocalDeclaration instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>int index</li>
	 * 	<li>constant char c</li>
	 * 	<li>String name</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		LocalDeclaration instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a LocalDeclaration.
	 */
	public static LocalDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		return decodeStatement(parent, statement, location, require, true);
	}
	
	/**
	 * Decode the given statement into a LocalDeclaration instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>int index</li>
	 * 	<li>constant char c</li>
	 * 	<li>String name</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		LocalDeclaration instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param checkName Whether or not to check for naming conflicts.
	 * @return The generated node, if it was possible to translated it
	 * 		into a LocalDeclaration.
	 */
	public static LocalDeclaration decodeStatement(Node parent, String statement, Location location, boolean require, boolean checkName)
	{
		LocalDeclaration n = new LocalDeclaration(parent, location);
		
		Bounds extraDeclarations = n.findExtraDeclarations(statement);
		
		if (extraDeclarations.isValid())
		{
			statement = extraDeclarations.extractPreString(statement);
		}
		
		if (!SyntaxUtils.isLiteral(statement) && StringUtils.containsMultipleWords(statement) && !StringUtils.containsChar(statement, StringUtils.INVALID_DECLARATION_CHARS))// || !Regex.matches(statement, Patterns.IDENTIFIER_DECLARATION))
		{
			DeclarationData  data = new DeclarationData();
			
			GenericDeclaration.searchGenericTypes(statement, data);
			
			n.iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES, data, require);
		
			if (data.error != null)
			{
				SyntaxMessage.queryError(data.error, n, require);
				
				return null;
			}
			
			n.checkExternal();
			
			if (!checkName || n.validateDeclaration())
			{
				for (int i = 0; i < n.getNumGenericArguments(); i++)
				{
					GenericArgument type = n.getGenericArgument(i);
					
					if (!type.isGenericType() && !SyntaxUtils.validateImported(n, type.getTypeClassLocation()))
					{
						return null;
					}
				}
				
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Make sure that the declaration is valid and that a declaration of
	 * another variable does not have the same name.
	 * 
	 * @return Whether or not the specified declaration is valid.
	 */
	private boolean validateDeclaration()
	{
		if (getType() == null || getName() == null)
		{
			return false;
		}
		
		VariableDeclaration node = SyntaxTree.findDeclaration(getParent(), getName());
		
		// If a local variable with the same name has already been declared.
		if (node instanceof LocalDeclaration)
		{
			SyntaxMessage.error("Local variable '" + getName() + "' has already been declared", this);
		}
		
		return true;
	}
	
	/**
	 * Check whether or not the specified declaration is external.
	 */
	private void checkExternal()
	{
		if (getParent() instanceof ExternalType)
		{
			setExternal(true);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(String, Bounds, String, String, ExtraData)
	 */
	@Override
	public void interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData data)
	{
		DeclarationData extra = (DeclarationData)data;
		
		if (extra.isLastWord())
		{
			interactName(word, leftDelimiter, rightDelimiter, extra);
		}
		else if (!setAttribute(word, extra.getWordNumber()))
		{
			if (getType() != null)
			{
				extra.error = "Invalid syntax '" + leftDelimiter + word + "'";
			}
			else
			{
				setType(leftDelimiter + word, true, false, getProgram().getPhase() == SyntaxTree.PHASE_METHOD_CONTENTS);
				
				if (getProgram().getPhase() == SyntaxTree.PHASE_METHOD_CONTENTS && !setType(getType(), false))
				{
					extra.error = "Type '" + leftDelimiter + word + "' does not exist";
				}
			}
		}
		
		checkArray(extra.statement, bounds.getEnd(), rightDelimiter);
	}
	
	/**
	 * Decode the given word as the name of the declaration.
	 * 
	 * @param word The name of the declared variable.
	 * @param leftDelimiter The left delimiter of the name.
	 * @param rightDelimiter The right delimiter of the name.
	 * @param extra The ExtraData for the word iteration.
	 */
	private void interactName(String word, String leftDelimiter, String rightDelimiter, DeclarationData extra)
	{
		if (getType() == null)// || (leftDelimiter.length() != 0 && !StringUtils.containsOnly(leftDelimiter, new char[] { '*', '&' })))
		{
			return;
		}
		
		setName(word);
		
		if (leftDelimiter.length() > 0)
		{
			if (leftDelimiter.equals("*"))
			{
				setDataType(Value.POINTER);
			}
			else if (leftDelimiter.equals("&"))
			{
				setDataType(Value.REFERENCE);
			}
		}
		
		if (extra.getLeftAdjacentSkipBounds() != null)
		{
			decodeGenericArguments(extra.statement, extra.getLeftAdjacentSkipBounds());
			
			extra.decrementGenericsRemaining();
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableDeclaration#clone(Node, Location)
	 */
	@Override
	public LocalDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		LocalDeclaration node = new LocalDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public LocalDeclaration cloneTo(LocalDeclaration node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link LocalDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LocalDeclaration cloneTo(LocalDeclaration node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the LocalDeclaration class type to make sure everything
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