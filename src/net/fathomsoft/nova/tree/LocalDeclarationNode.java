package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.LocalVariableNode;
import net.fathomsoft.nova.tree.variables.VariableNode;
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
 * @version	v0.2.11 May 31, 2014 at 1:19:11 PM
 */
public class LocalDeclarationNode extends LocalVariableNode
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
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public LocalDeclarationNode(TreeNode temporaryParent, Location locationIn)
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
	 * @return The generated node, if it was possible to translated it
	 * 		into a LocalDeclarationNode.
	 */
	public static LocalDeclarationNode decodeStatement(TreeNode parent, String statement, Location location, boolean require)
	{
		if (SyntaxUtils.isLiteral(statement))
		{
			return null;
		}
		if (!Regex.matches(statement, Patterns.IDENTIFIER_DECLARATION))
		{
			return null;
		}
		
		final String   finalStatement = statement;
		
		final boolean decodingArray[] = new boolean[1];
		final boolean error[]         = new boolean[1];
		final String  oldWord[]       = new String[1];
			
		LocalDeclarationNode n = new LocalDeclarationNode(parent, location)
		{
			@Override
			public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter)
			{
				if (error[0])
				{
					return;
				}
				
				if (!decodingArray[0])
				{
					if (wordNumber == numWords - 1)
					{
						if (getType() == null || (leftDelimiter.length() != 0 && !StringUtils.containsOnly(leftDelimiter, new char[] { '*', '&' })))
						{
							error[0] = true;
							
							return;
						}
						
						setName(word);
						
						if (leftDelimiter.length() > 0)
						{
							setType(getType() + leftDelimiter);
						}
					}
					else if (!setAttribute(word, wordNumber))
					{
						if (getType() != null)
						{
							SyntaxMessage.error("Unknown syntax '" + leftDelimiter + word + "'", this);
							
							error[0] = true;
						}
						
						setType(leftDelimiter + word);
					}
					
					oldWord[0] = word;
				}
				
				int firstBracketIndex = StringUtils.findNextNonWhitespaceIndex(finalStatement, bounds.getEnd());
				
				if (firstBracketIndex > 0)
				{
					char c = finalStatement.charAt(firstBracketIndex);
					
					if (c == '[')
					{
						decodingArray[0] = true;
						
						String brackets  = finalStatement.substring(bounds.getEnd());
						
						int dimensions   = SyntaxUtils.calculateArrayDimensions(brackets, false);
						
						setArrayDimensions(dimensions);
					}
				}
			}
		};
		
		n.iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES);
		
		if (n.getType() == null || n.getName() == null || error[0])
		{
			return null;
		}
		
		VariableNode node = TreeNode.getExistingNode(parent, n.getName());
		
		if (node instanceof LocalDeclarationNode)
		{
			SyntaxMessage.error("Local variable '" + n.getName() + "' has already been declared", n);
//			SyntaxMessage.error("Local variable '" + n.getName() + "' has already been declared", parent.getFileNode(), location, parent.getController());
		}
		
		if (parent instanceof ExternalTypeNode)
		{
			n.setExternal(true);
		}
		
		return n;
	}
}