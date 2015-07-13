package net.fathomsoft.nova.tree.switches;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Assignment;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Return;
import net.fathomsoft.nova.tree.Scope;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.Value;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * {@link SwitchCase} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.37 Oct 17, 2014 at 7:25:10 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Case extends SwitchCase
{
	public static final String IDENTIFIER = "case";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Case(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public String getIdentifier()
	{
		return IDENTIFIER;
	}
	
	/**
	 * Get the {@link net.fathomsoft.nova.tree.switches.Fallthrough Fallthrough}
	 * instance that is paired with this switch case (if a fallthrough exists)
	 * 
	 * @return The Fallthrough instance.
	 */
	public Fallthrough getFallthrough()
	{
		if (getScope().getLastVisibleChild() instanceof Fallthrough)
		{
			return (Fallthrough)getScope().getLastVisibleChild();
		}
		
		return null;
	}
	
	/**
	 * Get whether or not the specified switch case contains a fallthrough.
	 * 
	 * @return Whether or not it contains a fallthrough.
	 */
	public boolean containsFallthrough()
	{
		return getFallthrough() != null;
	}
	
	/**
	 * Get whether or not the specified switch case requires a break
	 * statement in order to function properly. The only cases when this
	 * method returns false is when the case contains a fallthrough or 
	 * return statement.
	 * 
	 * @return Whether or not the case requires a break statement to
	 * 		function correctly.
	 */
	public boolean requiresBreak()
	{
		return !containsFallthrough() && !(getScope().getLastChild() instanceof Return);
	}
	
	@Override
	public void addChild(Node node)
	{
		if (containsFallthrough())
		{
			SyntaxMessage.error("Fallthrough statement must be the last statement within a '" + IDENTIFIER + "' statement", getFallthrough());
		}
		
		super.addChild(node);
	}
	
	/**
	 * Get the value that the specified switch case occurs on.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * switch (num)
	 * 	case (value) ...
	 * 	default ...</pre></blockquote>
	 * On the line '<code>case (value)</code>' in the above switch statement, the
	 * '<code><i>value</i></code>' component of the case statement is the
	 * {@link net.fathomsoft.nova.tree.Value Value} Node that is returned from
	 * this method.
	 * 
	 * @return The value that the specified case occurs on.
	 */
	public Value getValue()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 0);
	}
	
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append(IDENTIFIER + " " + getValue().generateNovaInput(true)).append('\n');
		
		if (outputChildren)
		{
			getScope().generateNovaInput(builder, outputChildren);
		}
		
		return builder;
	}
	
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		if (getParentSwitch().isConventionalSwitch())
		{
			builder.append("case " + getValue().generateCSourceFragment() + ":\n");
			
			getScope().generateCSource(builder, false);
			
			if (requiresBreak())
			{
				builder.append("break;\n");
			}
		}
		else
		{
			String control = getParentSwitch().getControlValue().generateCSourceFragment().toString();
			
			Case   before = null;
			String fall   = "";
			
			if (getParent().getChildBefore(this) instanceof Case)
			{
				before = (Case)getParent().getChildBefore(this);
			}
			
			if (before != null)
			{
				if (before.containsFallthrough())
				{
					fall = getParentSwitch().getLocalFallthrough().generateCSourceFragment() + " || ";
				}
				else
				{
					builder.append("else ");
				}
			}
			
			builder.append("if (" + fall + control + " == " + getValue().generateCSourceFragment() + ")").append('\n');
			builder.append("{\n");
			
			getScope().generateCSource(builder, false);
			
			if (getParentSwitch().requiresLoopFacade() && requiresBreak())
			{
				builder.append("break;\n");
			}
			
			builder.append("}\n");
		}
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a {@link Case} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>case 43</li>
	 * 	<li>case "56" num++</li>
	 * 	<li>case person.name</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link Case} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link Case}.
	 */
	public static Case decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (StringUtils.findNextWord(statement).equals(IDENTIFIER))
		{
			Case n = new Case(parent, location);
			
			int valueStartIndex = StringUtils.findNextNonWhitespaceIndex(statement, IDENTIFIER.length() + 1);
			
			Bounds bounds = SyntaxUtils.findValueBounds(statement, valueStartIndex);
			
			if (!bounds.isValid())
			{
				if (!SyntaxMessage.queryError("Unable to decode " + IDENTIFIER + " statement", n, require))
				{
					return null;
				}
			}
			
			String contents = bounds.extractString(statement);
			
			if (n.decodeContents(contents, require) && n.decodeScopeFragment(statement, bounds))
			{
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Decode the Value that the switch case occurs on. For more
	 * information on what the Value is, see {@link #getValue()}
	 * 
	 * @param contents The String containing the value to decode.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return Whether or not the contents were decoded successfully.
	 */
	public boolean decodeContents(String contents, boolean require)
	{
		Value value = SyntaxTree.decodeValue(getParent(), contents, getLocationIn().asNew(), require);
		
		if (value == null)
		{
			return SyntaxMessage.queryError("Unable to decode " + IDENTIFIER + " statement value", this, require);
		}
		
		addChild(value, this);
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Case clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Case node = new Case(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Case cloneTo(Case node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Case} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Case cloneTo(Case node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link Case} class type to make sure everything
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