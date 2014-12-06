package net.fathomsoft.nova.tree.switches;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Scope;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * {@link SwitchCase} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.37 Oct 17, 2014 at 7:25:10 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Default extends SwitchCase
{
	public static final String IDENTIFIER = "default";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Default(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public String getIdentifier()
	{
		return IDENTIFIER;
	}
	
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		if (getParentSwitch().isConventionalSwitch())
		{
			builder.append("default:").append('\n');
			
			getScope().generateCSource(builder, false);
		}
		else
		{
			builder.append("else").append('\n');
			
			getScope().generateCSource(builder);
		}
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a {@link Default} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>default</li>
	 * 	<li>default Console.writeLine("Not found")</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link Default} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link Default}.
	 */
	public static Default decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (StringUtils.findNextWord(statement).equals(IDENTIFIER))
		{
			Default n = new Default(parent, location);
			
			if (statement.length() > IDENTIFIER.length())
			{
				int index = StringUtils.findNextNonWhitespaceIndex(statement, IDENTIFIER.length() + 1);
				
				Bounds bounds = new Bounds(IDENTIFIER.length(), index - 1);
				
				if (!n.decodeScopeFragment(statement, bounds))
				{
					SyntaxMessage.queryError("Unable to decode '" + IDENTIFIER + "' statement contents", n, require);
				}
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Default clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Default node = new Default(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Default} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Default cloneTo(Default node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the {@link Default} class type to make sure everything
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