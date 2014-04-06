package net.fathomsoft.fathom.tree.exceptionhandling;

import java.util.ArrayList;

import net.fathomsoft.fathom.Fathom;
import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.BinaryOperatorNode;
import net.fathomsoft.fathom.tree.IfStatementNode;
import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Mar 22, 2014 at 4:01:38 PM
 * @since	v
 * @version	Mar 22, 2014 at 4:01:38 PM
 * @version	v
 */
public class TryNode extends ExceptionHandlingNode
{
	private ArrayList<Integer>	codes;
	
	public TryNode()
	{
		codes = new ArrayList<Integer>();
	}
	
	public void addExceptionCode(int code)
	{
		codes.add(code);
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
		
		builder.append("TRY").append('\n');
		builder.append('{').append('\n');
		builder.append(generateExceptionCodes()).append('\n');
		
		builder.append(getScopeNode().generateCSourceOutput());
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
	}
	
	public static TryNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.matches(statement, 0, Patterns.TRY))
		{
			TryNode n = new TryNode();
			
			return n;
		}
		
		return null;
	}
	
	private String generateExceptionCodes()
	{
		StringBuilder builder = new StringBuilder();
			
		String variableName = "__" + Fathom.LANGUAGE_NAME.toUpperCase() + "__exception_data";
		
		for (int i = 0; i < codes.size(); i++)
		{
			int code = codes.get(i);
			
			builder.append(variableName).append("->addCode(").append(variableName).append(", ").append(variableName).append(", ").append(code).append(");").append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public TryNode clone()
	{
		TryNode node = new TryNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given TryNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public TryNode clone(TryNode node)
	{
		super.clone(node);
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}