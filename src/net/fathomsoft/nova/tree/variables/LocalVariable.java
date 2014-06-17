package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.Method;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * VariableNode extension that represents the declaration of a local variable
 * node type. See {@link net.fathomsoft.nova.tree.LocalDeclaration#decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:12:00 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class LocalVariable extends Variable
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public LocalVariable(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}

	/**
	 * @param phase The phase that the node is being validated in.
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public Node validate(int phase)
	{
		// If possibly accessing a shadowed field. ONLY for shadowed fields.
		if (getName().equals(Method.getObjectReferenceIdentifier()) && getNumChildren() > 0)
		{
			Node child = getChild(0);
			
			if (child instanceof Variable)
			{
				Variable var  = (Variable)child;
				
				Variable node = SyntaxTree.getExistingNode(var.getClassNode(), var.getName());
				
				if (node instanceof Field)
				{
					node = node.clone(null, null);
					
					node.inheritChildren(var);
					
					Node parent = getParent();
					
					parent.replace(this, node);
				}
			}
		}
		
		return this;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public LocalVariable clone(Node temporaryParent, Location locationIn)
	{
		LocalVariable node = new LocalVariable(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given LocalVariableNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LocalVariable cloneTo(LocalVariable node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
