package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.SyntaxUtils;

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
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCSourceOutput());
		}
		
		return builder.toString();
	}
	
	public static InstantiationNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		InstantiationNode n = null;
		
		if (SyntaxUtils.isInstantiation(statement))
		{
			n = new InstantiationNode();
			
			int startIndex = Regex.indexOf(statement, Patterns.POST_INSTANTIATION);
			
			statement = statement.substring(startIndex);
			
			Location newLoc = new Location();
			newLoc.setLineNumber(location.getLineNumber());
			newLoc.setOffset(location.getOffset() + startIndex);
			
			TreeNode child = null;
			
			if (SyntaxUtils.isMethodCall(statement))
			{
				child = MethodCallNode.decodeStatement(parent, statement, newLoc);
			}
			else if (SyntaxUtils.isArrayInitialization(statement))
			{
				child = ArrayNode.decodeStatement(parent, statement, newLoc);
			}
			else
			{
				SyntaxMessage.error("Unable to parse instantiation", newLoc);
				
				return null;
			}
			
			n.addChild(child);
		}
		
		return n;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public InstantiationNode clone()
	{
		InstantiationNode clone = new InstantiationNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}