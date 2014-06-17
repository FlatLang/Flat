package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.MethodNode;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Location;

/**
 * VariableNode extension that represents the declaration of a local variable
 * node type. See {@link #decodeStatement(TreeNode, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:12:00 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class LocalVariableNode extends VariableNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public LocalVariableNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}

	/**
	 * @param phase The phase that the node is being validated in.
	 * @see net.fathomsoft.nova.tree.TreeNode#validate(int)
	 */
	@Override
	public TreeNode validate(int phase)
	{
		// If possibly accessing a shadowed field. ONLY for shadowed fields.
		if (getName().equals(MethodNode.getObjectReferenceIdentifier()) && getNumChildren() > 0)
		{
			TreeNode child = getChild(0);
			
			if (child instanceof VariableNode)
			{
				VariableNode var  = (VariableNode)child;
				
				VariableNode node = SyntaxTree.getExistingNode(var.getClassNode(), var.getName());
				
				if (node instanceof FieldNode)
				{
					node = node.clone(null, null);
					
					node.inheritChildren(var);
					
					TreeNode parent = getParent();
					
					parent.replace(this, node);
				}
			}
		}
		
		return this;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
	 */
	@Override
	public LocalVariableNode clone(TreeNode temporaryParent, Location locationIn)
	{
		LocalVariableNode node = new LocalVariableNode(temporaryParent, locationIn);
		
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
