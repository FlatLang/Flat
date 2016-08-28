package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.annotations.Annotation;
import net.fathomsoft.nova.tree.variables.Super;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Value extension that represents what one type is being casted
 * to another. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more information on what a cast looks like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.25 Aug 3, 2014 at 1:52:00 PM
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class Cast extends IValue
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Cast(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDecodedChildren()
	 */
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 1;
	}
	
	public Value getValueNode()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 0);
	}
	
	@Override
	public Value getReturnedNode()
	{
		return getValueNode().getReturnedNode();
	}
	
	public boolean isExplicitCast()
	{
		if (getParent() instanceof Super)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#generateCSourceFragment(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		builder.append('(').append(generateCType()).append(')');
		getValueNode().getReturnedNode().generatePointerToValueConversion(builder);
		getValueNode().generateCSourceFragment(builder);
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#generateNovaInput(java.lang.StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append('(').append(generateNovaType()).append(')');
		getValueNode().generateNovaInput(builder);
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a {@link Cast} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>(Int)5.2</li>
	 * 	<li>(Value)getChild(getNumChildren())</li>
	 * 	<li>(String[])array</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link Cast} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link Cast}.
	 */
	public static Cast decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.charAt(0) == '(')
		{
			Cast   n        = new Cast(parent, location);
			Bounds bounds   = SyntaxUtils.findParenthesesBounds(n, statement);
			String contents = StringUtils.removeSurroundingParenthesis(statement, bounds).extractString(statement);
			
			Annotation a = Annotation.decodeStatement(n, contents, location, false);
			
			if (a != null)
			{
				n.addAnnotation(a);
				contents = Annotation.getFragment(contents);
			}
			
			String value    = statement.substring(bounds.getEnd()).trim();
			
			if (contents != null && n.decodeType(contents, require) && n.decodeValue(value, bounds, require))
			{
				return n;
			}
		}
		
		return null;
	}
	
	private boolean decodeType(String contents, boolean require)
	{
		/*if (StringUtils.containsMultipleWords(contents))
		{
			return invalidTypeError(contents, require);
		}*/
		
		String type = StringUtils.findNextWord(contents);
		
		if (type == null)
		{
			return invalidTypeError(type, require);
		}
		
		int index = contents.indexOf(type);
		
		// If symbols are before the type... can't have that.
		if (index > 0)
		{
			return invalidTypeError(contents, require);
		}
		
		if (!checkArray(contents.substring(type.length()), require))
		{
			return false;
		}
		
		return setType(type, require);
	}
	
	private boolean checkArray(String postfix, boolean require)
	{
		if (postfix.length() > 0)
		{
			int dimensions = SyntaxUtils.findArrayDimensions(postfix, false);
			
			if (dimensions <= 0)
			{
				return SyntaxMessage.queryError("Invalid symbols '" + postfix + "'", this, require);
			}
			
			setArrayDimensions(dimensions);
		}
		
		return true;
	}
	
	private boolean decodeValue(String value, Bounds bounds, boolean require)
	{
		Location newLoc = getLocationIn().asNew();
		
		bounds = bounds.clone();
		bounds.setStart(bounds.getEnd());
		bounds.setEnd(bounds.getStart() + value.length());
		
		newLoc.setBounds(bounds);
		
		Value node = SyntaxTree.decodeValue(this, value, newLoc, require);
		
		if (node == null)
		{
			if (require)
			{
				SyntaxMessage.error("Could not decode value '" + value + "'", this);
			}
			
			return false;
		}
		
		if (!getTypeClass().isRelatedTo(node.getReturnedNode().getTypeClass()))
		{
			if (!getProgram().getController().isTesting)
			{
				getTypeClass().isRelatedTo(node.getReturnedNode().getTypeClass());
			}
			
			SyntaxMessage.error("Cannot cast from type '" + node.getReturnedNode().getTypeClassName() + "' to type '" + getTypeClassName() + "'", this);
		}
		
		addChild(node);
		
		return true;
	}
	
	private void invalidTypeError(String type)
	{
		invalidTypeError(type, true);
	}
	
	private boolean invalidTypeError(String type, boolean require)
	{
		return SyntaxMessage.queryError("Cannot cast to invalid type '" + type + "'", this, require);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Cast clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Cast node = new Cast(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Cast cloneTo(Cast node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Cast} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Cast cloneTo(Cast node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link Cast} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		Cast   node      = null;
		String statement = null;
		
		statement = "(Int)5.2";
		node      = decodeStatement(context.method, statement, Location.INVALID, true);
		
		if (node != null)
		{
			statement = "(String)\"test\"";
			node      = decodeStatement(context.method, statement, Location.INVALID, true);
			
			if (node != null)
			{
				try
				{
					statement = "(String)54";
					node      = decodeStatement(context.method, statement, Location.INVALID, true);
					
					return "Cast failed test at '" + statement + "'";
				}
				catch (SyntaxErrorException e)
				{
					
				}
				
				
			}
		}
		
		if (node == null)
		{
			return "Could not decode cast '" + statement + "'";
		}
		
		return null;
	}
}