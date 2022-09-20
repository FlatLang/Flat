package flat.validator.match;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.Node;
import flat.tree.Scope;
import flat.tree.SyntaxTree;
import flat.util.Location;

public abstract class MatchCase extends Node implements MatchChild
{
	/**
	 * @see Node#Node(Node, Location)
	 */
	public MatchCase(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		Scope s = new Scope(this, locationIn.asNew());
		
		setScope(s);
	}
	
	/**
	 * @see Node#pendingScopeFragment(Node)
	 */
	@Override
	public boolean pendingScopeFragment(Node node)
	{
		return getScope().isEmpty();
	}
	
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	@Override
	public Scope getScope()
	{
		return (Scope)getChild(super.getNumDefaultChildren() + 0);
	}
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (!(getParent().getAncestorWithScope() instanceof Match))
			{
				SyntaxMessage.error("Case statements must be inside match scopes", this);
			}
		}
		
		return result;
	}
	
	/**
	 * Test the {@link MatchCase} class type to make sure everything
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