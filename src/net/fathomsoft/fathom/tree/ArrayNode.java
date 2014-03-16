package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Mar 16, 2014 at 1:13:49 AM
 * @since	v0.1
 * @version	Mar 16, 2014 at 1:13:49 AM
 * @version	v0.1
 */
public class ArrayNode extends TreeNode
{
	public ArrayNode()
	{
		
	}
	
	public IdentifierNode getIdentifierNode()
	{
		return (IdentifierNode)getChild(0);
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
		StringBuilder builder = new StringBuilder();
		
		IdentifierNode identifier = getIdentifierNode();
		
		builder.append('(').append(identifier.getName()).append('*').append(')');
		
		builder.append("malloc(sizeof(").append(identifier.getName()).append(')');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != identifier)
			{
				builder.append(" * ").append(child.generateCSourceOutput());
			}
		}
		
		builder.append(')');
		
		return builder.toString();
	}
	
	public static ArrayNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (SyntaxUtils.isArrayInitialization(statement))
		{
			ArrayNode n = new ArrayNode();
			
			int index = statement.indexOf('[') + 1;
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.IDENTIFIER);
			
			String idValue = statement.substring(bounds.getStart(), bounds.getEnd());
			
			IdentifierNode id = new IdentifierNode();
			id.setName(idValue);
			n.addChild(id);
			
			while (index > 1)
			{
				int endIndex  = statement.indexOf(']', index);
				
				String length = statement.substring(index, endIndex);
				
				if (SyntaxUtils.isNumber(length))
				{
					LiteralNode node = new LiteralNode();
					node.setValue(length);
					
					n.addChild(node);
				}
				else
				{
					IdentifierNode node = TreeNode.getExistingNode(parent, length);
					
					n.addChild(node);
				}
				
				index = statement.indexOf('[', endIndex + 1) + 1;
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ArrayNode clone()
	{
		ArrayNode clone = new ArrayNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
	
}