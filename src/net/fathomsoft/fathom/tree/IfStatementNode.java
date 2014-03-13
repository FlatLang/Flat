package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:57:13 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:57:13 PM
 * @version	v
 */
public class IfStatementNode extends TreeNode
{
	public IfStatementNode()
	{
		
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		return null;
	}
	
	public static IfStatementNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_IF))
		{
			IfStatementNode n = new IfStatementNode();
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.POST_IF);
			
			String contents = statement.substring(bounds.getStart(), bounds.getEnd());
			
			if (returnStartIndex >= 0)
			{
				statement = statement.substring(returnStartIndex);
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setOffset(location.getOffset() + returnStartIndex);
				
				TreeNode child = BinaryOperatorNode.decodeStatement(parent, statement, newLoc);
				
				n.addChild(child);
			}
		}
		
		return null;
	}
}