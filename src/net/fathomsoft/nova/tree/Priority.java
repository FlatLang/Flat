package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * Value extension that represents an operation within parentheses.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.10 May 29, 2014 at 1:50:25 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Priority extends Value implements Accessible
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Priority(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public GenericCompatible getContext()
	{
		return null;
	}
	
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 1;
	}
	
	@Override
	public boolean isConstant()
	{
		return getContents().isConstant();
	}
	
	@Override
	public boolean isConsistent()
	{
		return getContents().isConsistent();
	}
	
	@Override
	public Accessible getAccessingNode()
	{
		return null;
	}

	@Override
	public Identifier getAccessedNode()
	{
		return getNumChildren() > 1 ? (Identifier)super.getChild(1) : null;
	}

	@Override
	public GenericTypeArgumentList getGenericTypeArgumentList()
	{
		return getContents().getReturnedNode().getGenericTypeArgumentList();
	}
	
	@Override
	public GenericTypeParameter getGenericTypeParameter()
	{
		if (!doesAccess())
		{
			return getReturnedContents().getGenericTypeParameter();
		}
		
		return super.getGenericTypeParameter();
	}
	
	@Override
	public String getGenericReturnType()
	{
		if (!doesAccess())
		{
			return getReturnedContents().getGenericReturnType();
		}
		
		return super.getGenericReturnType();
	}
	
	/*public GenericTypeArgument getGenericTypeArgumentFromParameter(String type)
	{
		return ((Accessible)getReturnedContents()).getGenericTypeArgumentFromParameter(type);
	}*/
	
	/**
	 * Get the Value that represents the contents inside the
	 * parentheses.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * int j = (32 + 3 * 3);</pre></blockquote>
	 * In the statement above, The binary operation "<u><code>32 + 3 * 3</code></u>"
	 * is the contents of the Priority node.
	 * 
	 * @return The Value that represents the contents inside
	 * 		the parentheses.
	 */
	public Value getContents()
	{
		return (Value)getChild(0);
	}
	
	public Value getReturnedContents()
	{
		return getContents().getReturnedNode();
	}
	
	@Override
	public String getType()
	{
		return getReturnedContents().getType();
	}
	
	@Override
	public String getTypeStringValue()
	{
		return getReturnedContents().getTypeStringValue();
	}
	
	@Override
	public void setTypeValue(String type)
	{
		getReturnedContents().setTypeValue(type);
	}
	
	@Override
	public int getArrayDimensions()
	{
		return getReturnedContents().getArrayDimensions() - getArrayAccessDimensions();
	}
	
	@Override
	public void setArrayDimensions(int arrayDimensions)
	{
		getReturnedContents().setArrayDimensions(arrayDimensions);
	}
	
	@Override
	public byte getDataType(boolean checkGeneric)
	{
		return getReturnedContents().getDataType(checkGeneric);
	}
	
	@Override
	public void setDataType(byte type)
	{
		getReturnedContents().setDataType(type);
	}

	@Override
	public Value getReturnedNode()
	{
		return Accessible.super.getReturnedNode();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append('(').append(getContents().generateNovaInput(outputChildren)).append(')').append(generateNovaArrayAccess());
		
		if (outputChildren && doesAccess())
		{
			builder.append('.').append(getAccessedNode().generateNovaInput());
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		if (isSpecialFragment())
		{
			return generateSpecialFragment(builder);
		}
		else
		{
			return builder.append('(').append(getContents().generateCSourceFragment()).append(')').append(generateCArrayAccess()).append(generateChildrenCSourceFragment());
		}
	}
	
	@Override
	public StringBuilder generateCUseOutput(StringBuilder builder)
	{
		return builder.append('(').append(getContents().generateCSourceFragment()).append(')').append(generateCArrayAccess());
	}
	
	/**
	 * Decode the given statement into a Priority instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>(5 + 4)</li>
	 * 	<li>(getName() + " is here")</li>
	 * 	<li>( 54 * (2 + 4) )</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Priority instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Priority.
	 */
	public static Priority decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.charAt(0) == '(')
		{
			Priority n = new Priority(parent, location);
			
			if (!n.validatePriority(statement, require))
			{
				return null;
			}
			
			statement = statement.substring(1, statement.length() - 1);
			statement = StringUtils.trimSurroundingWhitespace(statement);
			
			Location contentsLoc = location.asNew();
			contentsLoc.addOffset(1);
			contentsLoc.moveBounds(1, -1);
			
			if (n.decodeContents(statement, contentsLoc, require))
			{
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Validate that the given statement is a Priority. To see what a
	 * Priority looks like see {@link #decodeStatement(Node, String, Location, boolean)}
	 * 
	 * @param statement The statement to validate.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the given statement is a valid Priority.
	 */
	private boolean validatePriority(String statement, boolean require)
	{
		int endingIndex = StringUtils.findEndingMatch(statement, 0, '(', ')');
		
		if (endingIndex < 0)
		{
			return SyntaxMessage.queryError("Missing ending parenthesis", this, require);
		}
		
		return endingIndex == statement.length() - 1;
	}
	
	/**
	 * Decode the contents of the Priority. The contents is the data
	 * within the parentheses.
	 * 
	 * @param contents The contents within the parentheses of the
	 * 		statement.
	 * @param location The location of the contents within the source
	 * 		file.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the contents decoded successfully.
	 */
	private boolean decodeContents(String contents, Location location, boolean require)
	{
		Value node = null;
		
//		Value node = null
//		
//		until (node != null)
//		{
//			node = Literal.decodeStatement(this, contents, location, true, true),
//			node = UnaryOperation.decodeStatement(this, contents, location, require),
//			node = BinaryOperation.decodeStatement(this, contents, location, require),
//			node = SyntaxTree.getUsableExistingNode(this, contents, location),
//			
//			SyntaxMessage.queryError("Could not decode contents '" + contents + "'", this, require)
//		}
//		
//		addChild(node)
//		
//		return true
		
		node = SyntaxTree.decodeValue(getAncestorWithScope(), contents, location, require);
		
//		node = Literal.decodeStatement(this, contents, location, true, true);
//		
//		if (node == null)
//		{
//			node = UnaryOperation.decodeStatement(this, contents, location, require);
//			
//			if (node == null)
//			{
//				node = BinaryOperation.decodeStatement(this, contents, location, require);
//				
//				if (node == null)
//				{
//					node = SyntaxTree.getUsableExistingNode(this, contents, location);
					
					if (node == null)
					{
						return SyntaxMessage.queryError("Could not decode contents '" + contents + "'", this, require);
					}
//				}
//			}
//		}
		
		addChild(node);
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Priority clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Priority node = new Priority(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Priority cloneTo(Priority node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Priority} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Priority cloneTo(Priority node, boolean cloneChildren)
	{
		super.cloneTo((Node)node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the Priority class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}