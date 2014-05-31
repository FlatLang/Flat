package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.variables.VariableListNode;
import net.fathomsoft.nova.tree.variables.VariableNode;
import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents a scope of code. In essence, a
 * collection of statements within a pair of curly braces.<br>
 * <br>
 * For example:
 * <blockquote><pre>
 * {
 * 	...
 * 
 * 	// Statements within here...
 * 
 * 	...
 * }</pre></blockquote>
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Apr 5, 2014 at 10:54:20 PM
 * @version	v0.2.11 May 31, 2014 at 1:19:11 PM
 */
public class ScopeNode extends TreeNode
{
	private int			id;
	
	private static int	currentId = 1;
	
	/**
	 * Instantiate and initialize the default values.
	 * 
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ScopeNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		VariableListNode variablesNode = new VariableListNode(this, locationIn);
		
		super.addChild(variablesNode);
		
		id = currentId++;
	}
	
	/**
	 * Get the VariableListNode that contains all of the variables
	 * that have been declared within this ScopeNode.
	 * 
	 * @return The VariableListNode instance.
	 */
	public VariableListNode getVariableListNode()
	{
		return (VariableListNode)getChild(0);
	}
	
	/**
	 * Get the id of the scope used for variable name differentiation.
	 * 
	 * @return The id of the scope instance.
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof VariableNode)
		{
			VariableNode var = (VariableNode)child;
			
			if (var.isDeclaration())
			{
				if (child instanceof LocalDeclarationNode)
				{
					LocalDeclarationNode declaration = (LocalDeclarationNode)child;
					
					if (declaration.getScopeID() == 0)
					{
						declaration.setScopeID(getID());
					}
				}
				
//				MethodNode method = (MethodNode)var.getAncestorOfType(MethodNode.class);
//				
//				method.getScopeNode().getVariableListNode().addChild(var);
				getVariableListNode().addChild(var);
				
				return;
			}
		}
//		else if (child instanceof ExternalTypeNode)
//		{
////			getVariableListNode().addChild(child.getChild(0).clone());
//			
//			return;
//		}
		
		super.addChild(child);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		if (getChildren().size() <= 1)
		{
			if (getParent() instanceof MethodNode == false)
			{
				if (getParent() instanceof LoopNode)
				{
					return ";";
				}
				
				return "";
			}
		}
		
		StringBuilder builder = new StringBuilder();
		
		builder.append('{').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateCSource());
		}
		
//		VariableListNode variables = getVariableListNode();
//		
//		builder.append(variables.generateFreeVariablesOutput());
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ScopeNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ScopeNode node = new ScopeNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ScopeNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ScopeNode cloneTo(ScopeNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}