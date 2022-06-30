package org.flatlang.tree.match;

import org.flatlang.TestContext;
import org.flatlang.ValidationResult;
import org.flatlang.error.SyntaxMessage;
import org.flatlang.tree.*;
import org.flatlang.tree.variables.Variable;
import org.flatlang.util.Location;
import org.flatlang.util.StringUtils;
import org.flatlang.util.SyntaxUtils;
import org.flatlang.tree.Node;
import org.flatlang.tree.Value;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.37 Oct 17, 2014 at 7:25:10 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Match extends ControlStatement
{
	private boolean conventional, strict;
	
	/**
	 * @see Node#Node(Node, Location)
	 */
	public Match(Node temporaryParent, Location locationIn)
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
		
		return ancestor instanceof MatchCase;
	}
	
	/**
	 * Whether or not the specified switch case can be translated into
	 * a conventional switch statement in C. Essentially if the values
	 * in the case statements are constants, then it is a traditional
	 * switch. Otherwise the switch is converted into an if-else statement
	 * at compile time.
	 * 
	 * @return Whether or not the specified switch case is a valid
	 * switch statement in the C language.
	 */
	public boolean isConventionalSwitch()
	{
		return conventional;
	}
	
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 1;
	}
	
	/**
	 * Get the value that is being switched over.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * switch (num)
	 * 	case (value) ...
	 * 	default ...</pre></blockquote> 
	 * On the line '<code>switch (num)</code>' in the above switch statement, the
	 * '<code><i>num</i></code>' component of the case statement is the
	 * {@link Value Value} Node that is returned from
	 * this method.
	 * 
	 * @return The value that is being switched over.
	 */
	public Value getControlValue()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 0);
	}
	
	/**
	 * Get the {@link Variable Variable} that
	 * allows the if-else structure to work like a traditional switch statement
	 * in that it allows fallthroughs.
	 * 
	 * @return Get the {@link Variable Variable}
	 * 		that is used to generate fallthrough scenarios.
	 */
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
				
				if (c.getValue() instanceof Literal == false || ((Literal)c.getValue()).isStringInstantiation())
				{
					if (strict)
					{
						SyntaxMessage.error("Switch statements cannot have variable cases", c);
					}
					
					conventional = false;
				}
			}
			else
			{
				SyntaxMessage.error("Match statements only allow 'case' and 'default' statements as children", node);
			}
		}
		
		super.addChild(node);
	}
	
	/**
	 * Get whether or not the specified switch statement requires a fallthrough
	 * facade to be generated. This occurs when the switch statement has to be
	 * generated as an if-else statement and there are fallthroughs that are
	 * needed to make the switch statement function correctly.
	 * 
	 * @return Whether or not the specified switch statement requires
	 * 		a fallthrough variable to make the if-else statement
	 * 		emulate a traditional switch statement.
	 */
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
	
	/**
	 * Get whether or not the switch statement requires a loop facade
	 * to allow the switch to break out of the switch and skip the remaining
	 * case statements. This is required when the switch statement is
	 * emulated by an if-else statement.
	 * 
	 * @return Whether or not the switch statement requires a loop facade.
	 */
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
	 * Decode the given statement into a {@link Match} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>match (value)</li>
	 * 	<li>match (name)</li>
	 * 	<li>match (person.calculateAge())</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link Match} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link Match}.
	 */
	public static Match decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		String type = StringUtils.findNextWord(statement);
		
		if ((type.equals("match") || type.equals("switch")) && SyntaxUtils.findStringInBaseScope(statement, type) >= 0)
		{
			int index = StringUtils.findNextNonWhitespaceIndex(statement, type.length());//statement.indexOf('(', IDENTIFIER.length());
			
			Match n = new Match(parent, location);
			n.strict = type.equals("switch");
			
			if (index < 0)
			{
				SyntaxMessage.queryError("Unable to decode " + type + " statement", n, require);
				
				return null;
			}
			
			String contents = statement.substring(index);//SyntaxUtils.findInnerParenthesesBounds(n, statement).extractString(statement);
			
			if (n.decodeControlValue(contents, require))
			{
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Decode the value that the switch statement will be switching over.
	 * 
	 * @param contents The String containing the value to switch over.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return Whether or not the control value was decoded successfully.
	 */
	private boolean decodeControlValue(String contents, boolean require)
	{
		Value value = SyntaxTree.decodeValue(getParent(), contents, getLocationIn().asNew(), require);
		
		if (value == null)
		{
			return SyntaxMessage.queryError("Unable to decode " + (strict ? "switch" : "match") + " statement control value", this, require);
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
				Literal falseVal = (Literal)Literal.decodeStatement(getParent().getAncestorWithScope(), Literal.FALSE_IDENTIFIER, Location.INVALID, true);
				
				Variable localFallthrough = getAncestorWithScope().getScope().registerLocalVariable(falseVal, true);
				
				addChild(localFallthrough, this);
			}
			if (!getControlValue().isConsistent() && (!(getControlValue() instanceof Variable) || !((Variable)getControlValue()).doesForceOriginalName()))
			{
//				Assignment decl = Assignment.decodeStatement(getParent(), , location, require)
				Variable var = getAncestorWithScope().getScope().registerLocalVariable(getControlValue(), true);
				
				replace(getControlValue(), var);
			}
		}
		
		return result;
	}
	
	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public Match clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Match node = new Match(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public Match cloneTo(Match node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Match} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Match cloneTo(Match node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link Match} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		context.method.addChild(SyntaxTree.decodeScopeContents(context.method, "Int num = 3", Location.INVALID));
		
		Match s = decodeStatement(context.method, "match (num)", Location.INVALID, false);
		
		if (s == null)
		{
			return "Could not decode match statement with an Int";
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