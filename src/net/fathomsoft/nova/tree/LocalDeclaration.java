package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.LocalVariable;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * LocalVariableNode extension that represents the declaration of local
 * variable.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 Jan 5, 2014 at 9:10:49 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class LocalDeclaration extends LocalVariable
{
	private int scopeID;
	
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
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public LocalDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}

	/**
	 * Decode the given statement into a LocalDeclarationNode instance, if
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
	 * 		LocalDeclarationNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a LocalDeclarationNode.
	 */
	public static LocalDeclaration decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (SyntaxUtils.isLiteral(statement))
		{
			return null;
		}
		if (!Regex.matches(statement, Patterns.IDENTIFIER_DECLARATION))
		{
			return null;
		}
		
		LocalDeclaration n = new LocalDeclaration(parent, location);
		
		DeclarationData data = new DeclarationData(statement);
		
		n.iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES, data);
		
		if (n.getType() == null || n.getName() == null || data.error)
		{
			return null;
		}
		
		Variable node = SyntaxTree.getExistingNode(parent, n.getName());
		
		// If a local variable with the same name has already been declared.
		if (node instanceof LocalDeclaration)
		{
			SyntaxMessage.error("Local variable '" + n.getName() + "' has already been declared", n);
//			SyntaxMessage.error("Local variable '" + n.getName() + "' has already been declared", parent.getFileNode(), location, parent.getController());
		}
		
		if (parent instanceof ExternalType)
		{
			n.setExternal(true);
		}
		
		return n;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, int, net.fathomsoft.nova.util.Bounds, int, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData data)
	{
		DeclarationData extra = (DeclarationData)data;
		
		if (extra.error)
		{
			return;
		}
		
		if (!extra.decodingArray)
		{
			if (wordNumber == numWords - 1)
			{
				if (getType() == null || (leftDelimiter.length() != 0 && !StringUtils.containsOnly(leftDelimiter, new char[] { '*', '&' })))
				{
					extra.error = true;
					
					return;
				}
				
				setName(word);
				
				if (leftDelimiter.length() > 0)
				{
					if (leftDelimiter.equals("*"))
					{
						setDataType(POINTER);
					}
					else if (leftDelimiter.equals("&"))
					{
						setDataType(REFERENCE);
					}
				}
			}
			else if (!setAttribute(word, wordNumber))
			{
				if (getType() != null)
				{
					SyntaxMessage.error("Unknown syntax '" + leftDelimiter + word + "'", this);
					
					extra.error = true;
				}
				
				setType(leftDelimiter + word);
			}
			
			extra.oldWord = word;
		}
		
		int firstBracketIndex = StringUtils.findNextNonWhitespaceIndex(extra.statement, bounds.getEnd());
		
		if (firstBracketIndex > 0)
		{
			char c = extra.statement.charAt(firstBracketIndex);
			
			if (c == '[')
			{
				extra.decodingArray = true;
				
				String brackets  = extra.statement.substring(bounds.getEnd());
				
				int dimensions   = SyntaxUtils.calculateArrayDimensions(brackets, false);
				
				setArrayDimensions(dimensions);
			}
		}
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
		private boolean	error;
		private boolean	decodingArray;
		
		private String	oldWord;
		private String	statement;
		
		public DeclarationData(String statement)
		{
			this.statement = statement;
		}
	}
}