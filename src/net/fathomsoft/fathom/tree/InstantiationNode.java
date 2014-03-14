package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

public class InstantiationNode extends TreeNode
{
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
	
	public static ReturnNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		ReturnNode n = null;
		
		if (Regex.indexOf(statement, Patterns.PRE_INSTANTIATION) == 0)
		{
			n = new ReturnNode();
			
			int returnStartIndex = Regex.indexOf(statement, Patterns.POST_INSTANTIATION);
			
			if (returnStartIndex >= 0)
			{
				statement = statement.substring(returnStartIndex);
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setOffset(location.getOffset() + returnStartIndex);
				
				TreeNode child = MethodCallNode.decodeStatement(parent, statement, newLoc);
				
				n.addChild(child);
			}
		}
		
		return n;
	}
}