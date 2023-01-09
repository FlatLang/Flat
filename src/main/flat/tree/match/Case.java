package flat.tree.match;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.util.Location;
import flat.util.SyntaxUtils;
import flat.tree.Node;
import flat.tree.Value;

/**
 * {@link MatchCase} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.37 Oct 17, 2014 at 7:25:10 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Case extends MatchCase
{
	public Case returningCase;
	
	/**
	 * @see Node#Node(Node, Location)
	 */
	public Case(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 2;
	}
	
	/**
	 * Get the {@link Fallthrough Fallthrough}
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
	 * Get the {@link Fallthrough Fallthrough}
	 * instance that is paired with this switch case (if a fallthrough exists)
	 *
	 * @return The Fallthrough instance.
	 */
	public Continue getContinue()
	{
		if (getScope().getLastVisibleChild() instanceof Continue)
		{
			return (Continue)getScope().getLastVisibleChild();
		}

		return null;
	}
	
	@Override
	public boolean pendingScopeFragment(Node node)
	{
		if (returningCase != null)
		{
			return returningCase.pendingScopeFragment(node);
		}
		
		return super.pendingScopeFragment(node);
	}
	
	public Case getReturningCase()
	{
		return returningCase != null ? returningCase.getReturningCase() : this;
	}
	
	@Override
	public Node getDecodedParent()
	{
		return getReturningCase();
	}
	
	@Override
	public void onAdded(Node parent)
	{
		if (returningCase != null)
		{
			getAncestorOfType(Match.class).addChild(returningCase);
		}
		
		super.onAdded(parent);
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

	public boolean containsContinue()
	{
		return getContinue() != null;
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
		return !containsFallthrough() && !containsContinue() && !(getScope().getLastChild() instanceof Return);
	}
	
	@Override
	public void addChild(Node node)
	{
		if (node instanceof Case)
		{
//			getAncestorOfType(Match.class).addChild(node);
		}
		else if (containsFallthrough())
		{
			SyntaxMessage.error("Fallthrough statement must be the last statement within a case statement", getFallthrough());
		}
		else
		{
			super.addChild(node);
		}
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
	 * {@link Value Value} Node that is returned from
	 * this method.
	 * 
	 * @return The value that the specified case occurs on.
	 */
	public Value getValue()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 0);
	}
	
	public Value getCondition()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 1);
	}
	
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append(getValue().generateFlatInput(true)).append(" => ");
		
		if (outputChildren)
		{
			getScope().generateFlatInput(builder, outputChildren);
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
		int index = SyntaxUtils.findStringInBaseScope(statement, "=>");
		
		if (index > 0)
		{
			Case n = new Case(parent, location);
			
			if (parent instanceof Case && parent.containsScope() && parent.getScope().getNumVisibleChildren() == 0)
			{
				parent.addChild(new Fallthrough(parent, parent.getLocationIn()));
				((Case)parent).returningCase = n;
				
				parent = parent.getAncestorOfType(Match.class);
			}
			if (parent instanceof Match)
			{
				String contents = statement.substring(0, index).trim();
				
				if (n.decodeContents(contents, require) && n.decodeScopeFragment(statement, index + 2))
				{
					return n;
				}
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
			return SyntaxMessage.queryError("Unable to decode match case statement value", this, require);
		}
		
		addChild(value, this);
		
		return true;
	}
	
	public boolean decodeCondition(boolean require)
	{
		Value control = getParentMatch().getControlValue();
		
		Value value = (Value)getParentMatch().getControlValue().clone(control.getParent(), control.getLocationIn(), true);
		Value clone = (Value)getValue().clone(getValue().getParent(), getValue().getLocationIn(), true);
		
		if (value.getReturnedNode().getTypeClass().isOfType("flat/String") && !Literal.isNullLiteral(clone))
		{
			CallableMethod stringEquals = getProgram().getClassDeclaration("flat/String").getMethodList().getMethods("equals", MethodList.SearchFilter.getDefault())[0];
			
			MethodCall call = MethodCall.decodeStatement(value.getReturnedNode(), "equals(null)", value.getLocationIn(), require, false, stringEquals);
			
			call.getArgumentList().getVisibleChild(0).replaceWith(clone);
			
			((Accessible)value.getReturnedNode()).setAccessedNode(call);
			
			addChild(value, this);
		}
		else
		{
			BinaryOperation operation = BinaryOperation.generateDefault(getValue().getParent(), getValue().getLocationIn());
			
			operation.getLeftOperand().replaceWith(value);
			operation.getOperator().setOperator("==");
			operation.getRightOperand().replaceWith(clone);
			
			addChild(operation, this);
		}
		
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
			if (!decodeCondition(true))
			{
				SyntaxMessage.error("Unable to decode case condition", this);
			}
		}
		
		return result;
	}
	
	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public Case clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Case node = new Case(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public Case cloneTo(Case node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Case} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Case cloneTo(Case node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
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