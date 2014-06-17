package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.InstanceDeclarationNode;
import net.fathomsoft.nova.tree.LocalDeclarationNode;
import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.tree.TreeNode.ExtraData;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;

/**
 * DeclarationNode extension that represents the declaration of a field
 * node type. See {@link #decodeStatement(TreeNode, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:12:04 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class FieldNode extends InstanceDeclarationNode
{
	/**
	 * Declares that a variable can be viewed from anywhere, but not
	 * modified.
	 */
	public static final int	VISIBLE	= 4;
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public FieldNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclarationNode#isVisibilityValid()
	 */
	@Override
	public boolean isVisibilityValid()
	{
		int visibility = getVisibility();
		
		return super.isVisibilityValid() || visibility == VISIBLE;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclarationNode#getVisibilityText()
	 */
	@Override
	public String getVisibilityText()
	{
		int visibility = getVisibility();
		
		if (visibility == VISIBLE)
		{
			return "visible";
		}
		
		return super.getVisibilityText();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclarationNode#setAttribute(java.lang.String, int)
	 */
	@Override
	public boolean setAttribute(String attribute, int argNum)
	{
		if (super.setAttribute(attribute, argNum))
		{
			return true;
		}
		
		if (argNum == 0)
		{
			if (attribute.equals("visible"))
			{
				setVisibility(VISIBLE);
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		
		return true;
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		if (isDeclaration() && isStatic() && (getVisibility() == PUBLIC || getVisibility() == VISIBLE))
		{
			return "extern " + generateCSource();
		}
		
		return generateCSource();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isDeclaration())
		{
	//		if (isStatic())
	//		{
	//			builder.append(getStaticText()).append(' ');
	//		}
			if (isConstant())
			{
				builder.append(getConstantText()).append(' ');
			}
			
			builder.append(generateCTypeOutput());
			
			if (isReference())
			{
				builder.append('&');
			}
			else if (isPointer())
			{
				builder.append('*');
			}
			if (isArray())
			{
				builder.append(getArrayText());
			}
			if (!isPrimitiveType() && !isExternalType())
			{
				builder.append('*');
			}
			
			builder.append(' ').append(generateCSourceName());
			
	//		if (!isPrimitiveType())
	//		{
	//			builder.append(" = 0");
	//		}
		}
		else
		{
			builder.append(generateCSourceFragment());
		}
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		if (isSpecialFragment())
		{
			return generateSpecialFragment();
		}
		
		return generateUseOutput() + generateChildrenCSourceFragment();
	}
	
	/**
	 * Decode the given statement into a FieldNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public static constant int uuid</li>
	 * 	<li>private Person p</li>
	 * 	<li>private String name</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		FieldNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a FieldNode.
	 */
	public static FieldNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean scope)
	{
		FieldNode n    = new FieldNode(parent, location);
		
		FieldData data = new FieldData(statement);
		
		// Find the localDeclaration bounds.
		n.iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES, data);
		
		if (data.localDeclaration.getStart() < 0 || data.localDeclaration.getEnd() < 0)
		{
			return null;
		}
		
		String preStatement      = statement.substring(0, data.localDeclaration.getStart());
		String localStatement    = statement.substring(data.localDeclaration.getStart(), data.localDeclaration.getEnd());
		
		LocalDeclarationNode var = LocalDeclarationNode.decodeStatement(n, localStatement, new Location(location), require, scope);
		
		if (var == null)
		{
			return null;
		}
		
		var.cloneTo(n);
		
		n.iterateWords(preStatement);
		
		return n;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#interactWord(java.lang.String, int, net.fathomsoft.nova.util.Bounds, int, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.TreeNode.ExtraData)
	 */
	@Override
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		FieldData data = (FieldData)extra;
		
		if (!setAttribute(word, wordNumber))
		{
			if (data.localDeclaration.getStart() == -1)
			{
				data.localDeclaration.setStart(bounds.getStart());
			}
			else if (wordNumber == numWords - 1)
			{
				data.localDeclaration.setEnd(data.statement.length());
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
	 */
	@Override
	public FieldNode clone(TreeNode temporaryParent, Location locationIn)
	{
		FieldNode node = new FieldNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given FieldNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FieldNode cloneTo(FieldNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Implementation of the ExtraData for this class.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.13 Jun 11, 2014 at 8:31:46 PM
	 * @version	v0.2.13 Jun 11, 2014 at 8:31:46 PM
	 */
	private static class FieldData extends ExtraData
	{
		private Bounds	localDeclaration;
		
		private String	statement;
		
		public FieldData(String statement)
		{
			this.statement = statement;
			
			localDeclaration = Bounds.EMPTY.clone();
		}
	}
}