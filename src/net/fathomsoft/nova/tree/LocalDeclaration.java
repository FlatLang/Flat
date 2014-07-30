package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * LocalVariable extension that represents the declaration of local
 * variable.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 Jan 5, 2014 at 9:10:49 PM
 * @version	v0.2.21 Jul 30, 2014 at 1:45:00 PM
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
		if (SyntaxUtils.isLiteral(statement) || !Regex.matches(statement, Patterns.IDENTIFIER_DECLARATION))
		{
			return null;
		}
		
		LocalDeclaration n    = new LocalDeclaration(parent, location);
		DeclarationData  data = new DeclarationData(statement);
		
		n.iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES, data);
	
		if (data.error != null)
		{
			SyntaxMessage.queryError(data.error, n, require);
			
			return null;
		}
		
		n.checkExternal();
		
		if (n.validateDeclaration())
		{
			return n;
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
	 * @see net.fathomsoft.nova.tree.Node#interactWord(String, int, Bounds, int, String, String, ExtraData)
	 */
	@Override
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData data)
	{
		DeclarationData extra = (DeclarationData)data;
		
		if (wordNumber == numWords - 1)
		{
			interactName(word, leftDelimiter, rightDelimiter);
		}
		else if (!setAttribute(word, wordNumber))
		{
			if (getType() != null)
			{
				extra.error = "Unknown syntax '" + leftDelimiter + word + "'";
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
	 */
	private void interactName(String word, String leftDelimiter, String rightDelimiter)
	{
		if (getType() == null || (leftDelimiter.length() != 0 && !StringUtils.containsOnly(leftDelimiter, new char[] { '*', '&' })))
		{
			return;
		}
		
		setName(word);
		
		if (leftDelimiter.length() > 0)
		{
			if (leftDelimiter.equals("*"))
			{
				setDataType(IValue.POINTER);
			}
			else if (leftDelimiter.equals("&"))
			{
				setDataType(IValue.REFERENCE);
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableDeclaration#clone(Node, Location)
	 */
	@Override
	public LocalDeclaration clone(Node temporaryParent, Location locationIn)
	{
		LocalDeclaration node = new LocalDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link LocalDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LocalDeclaration cloneTo(LocalDeclaration node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Implementation of the ExtraData for this class.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.13 Jun 11, 2014 at 8:31:46 PM
	 * @version	v0.2.13 Jun 11, 2014 at 8:31:46 PM
	 */
	private static class DeclarationData extends ExtraData
	{
		private String	statement;
		
		/**
		 * Construct the DeclarationData instance with the given statement
		 * to pass to the interactWord() method.
		 * 
		 * @param statement The statement to pass to the interactWord()
		 * 		method.
		 */
		public DeclarationData(String statement)
		{
			this.statement = statement;
		}
	}
	
	/**
	 * Test the LocalDeclaration class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test()
	{
		
		
		return null;
	}
}