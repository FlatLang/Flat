package net.fathomsoft.nova.tree.switches;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.ControlStatement;
import net.fathomsoft.nova.tree.Literal;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Scope;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.Value;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.37 Oct 17, 2014 at 7:25:10 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Switch extends ControlStatement
{
	private boolean conventional;
	
	public static final String IDENTIFIER = "switch";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Switch(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		conventional = true;
	}
	
	@Override
	public boolean pendingScopeFragment(Node node)
	{
		if (node == null)
		{
			return true;
		}
		
		Node ancestor = node.getAncestorWithScope();
		
		return ancestor instanceof SwitchCase;
	}
	
	public boolean isConventionalSwitch()
	{
		return conventional;
	}
	
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 1;
	}
	
	public Value getControlValue()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 0);
	}
	
	public Variable getLocalFallthrough()
	{
		if (getNumVisibleChildren() > 0)
		{
			return (Variable)getVisibleChild(0);
		}
		
		return null;
	}
	
	@Override
	public void addChild(Node node)
	{
		if (!isDecoding())
		{
			if (node instanceof Default)
			{
				
			}
			else if (node instanceof Case)
			{
				if (getLastVisibleChild() instanceof Default)
				{
					SyntaxMessage.error("'default' statement must be the last statement in a switch", node);
				}
				
				Case c = (Case)node;
				
				if (!c.getValue().isConstant())
				{
					conventional = false;
				}
			}
			else
			{
				SyntaxMessage.error("Switch statements only allow 'case' and 'default' statements as children", node);
			}
		}
		
		super.addChild(node);
	}
	
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		if (isConventionalSwitch())
		{
			builder.append("switch (" + getControlValue().generateCSourceFragment() + ")\n");
			
			getScope().generateCSource(builder);
		}
		else
		{
			boolean requiresFacade = requiresLoopFacade();
			
			if (requiresFacade)
			{
				builder.append("do\n{\n");
			}
			
			getScope().generateCSource(builder, false);
			
			if (requiresFacade)
			{
				builder.append("}\nwhile (0);\n");
			}
		}
		
		return builder;
	}
	
	public boolean requiresLocalFallthroughVariable()
	{
		if (isConventionalSwitch())
		{
			return false;
		}
		
		boolean waitingForCase = true;
		
		for (int i = getScope().getNumVisibleChildren() - 1; i >= 0; i--)
		{
			Node child = getScope().getVisibleChild(i);
			
			if (child instanceof Case)
			{
				Case c = (Case)child;
				
				if (waitingForCase)
				{
					waitingForCase = false;
					
					continue;
				}
				
				if (c.containsFallthrough())
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean requiresLoopFacade()
	{
		boolean waitingForFall = false;
		
		for (int i = getScope().getNumVisibleChildren() - 1; i >= 0; i--)
		{
			Node child = getScope().getVisibleChild(i);
			
			if (child instanceof Case)
			{
				Case c = (Case)child;
				
				if (!c.containsFallthrough())
				{
					waitingForFall = true;
				}
				else if (waitingForFall)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Decode the given statement into a {@link Switch} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>switch (value)</li>
	 * 	<li>switch (name)</li>
	 * 	<li>switch (person.calculateAge())</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link Switch} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link Switch}.
	 */
	public static Switch decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (StringUtils.findNextWord(statement).equals(IDENTIFIER))
		{
			int index = StringUtils.findNextNonWhitespaceIndex(statement, IDENTIFIER.length());//statement.indexOf('(', IDENTIFIER.length());
			
			Switch n = new Switch(parent, location);
			
			if (index < 0)
			{
				if (SyntaxMessage.queryError("Unable to decode " + IDENTIFIER + " statement", n, require))
				{
					return null;
				}
			}
			
			String contents = statement.substring(index);//SyntaxUtils.findInnerParenthesesBounds(n, statement).extractString(statement);
			
			if (n.decodeControlValue(contents, require))
			{
				return n;
			}
		}
		
		return null;
	}
	
	private boolean decodeControlValue(String contents, boolean require)
	{
		Value value = SyntaxTree.decodeValue(getParent(), contents, getLocationIn().asNew(), require);
		
		if (value == null)
		{
			return SyntaxMessage.queryError("Unable to decode " + IDENTIFIER + " statement control value", this, require);
		}
		
		addChild(value, this);
		
		return true;
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (getLocalFallthrough() == null && requiresLocalFallthroughVariable())
			{
				Literal falseVal = Literal.decodeStatement(getParent().getAncestorWithScope(), Literal.FALSE_IDENTIFIER, Location.INVALID, true);
				
				Variable localFallthrough = getAncestorWithScope().getScope().registerLocalVariable(falseVal, true);
				
				addChild(localFallthrough, this);
			}
			if (!getControlValue().isConsistent() && (!(getControlValue() instanceof Variable) || !((Variable)getControlValue()).willForceOriginalName()))
			{
//				Assignment decl = Assignment.decodeStatement(getParent(), , location, require)
				Variable var = getAncestorWithScope().getScope().registerLocalVariable(getControlValue(), true);
				
				replace(getControlValue(), var);
			}
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Switch clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Switch node = new Switch(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Switch} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Switch cloneTo(Switch node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the {@link Switch} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		context.method.addChild(SyntaxTree.decodeScopeContents(context.method, "Int num = 3", Location.INVALID));
		
		Switch s = decodeStatement(context.method, "switch (num)", Location.INVALID, false);
		
		if (s == null)
		{
			return "Could not decode switch statement with an Int";
		}
		
		Case c = Case.decodeStatement(s, "case 1", Location.INVALID, false);
		
		if (c == null)
		{
			return "Could not decode basic case statement";
		}
		
		s.addChild(c);
		
		c = Case.decodeStatement(s, "case 2 num++", Location.INVALID, false);
		
		if (c == null)
		{
			return "Could not decode case fragment statement";
		}
		
		context.reset();
		
		return null;
	}
}