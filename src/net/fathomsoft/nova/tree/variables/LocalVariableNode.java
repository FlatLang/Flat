package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.MethodNode;
import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Location;

/**
 * VariableNode extension that represents the declaration of a local variable
 * node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:12:00 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class LocalVariableNode extends VariableNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 */
	public LocalVariableNode(TreeNode temporaryParent)
	{
		super(temporaryParent);
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#validate()
	 */
	@Override
	public void validate()
	{
		// If possibly accessing a shadowed field. ONLY for shadowed fields.
		if (getName().equals(MethodNode.getObjectReferenceIdentifier()) && getChildren().size() > 0)
		{
			TreeNode child = getChild(0);
			
			if (child instanceof VariableNode)
			{
				VariableNode var  = (VariableNode)child;
				
				VariableNode node = getExistingNode(var.getClassNode(), var.getName());
				
				if (node instanceof FieldNode)
				{
					node = node.clone(null);
					
					node.inheritChildren(var);
					
					TreeNode parent = getParent();
					
					parent.replace(this, node);
				}
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public LocalVariableNode clone(TreeNode temporaryParent)
	{
		LocalVariableNode node = new LocalVariableNode(temporaryParent);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given LocalVariableNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LocalVariableNode cloneTo(LocalVariableNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
