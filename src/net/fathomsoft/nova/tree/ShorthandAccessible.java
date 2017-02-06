package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

public interface ShorthandAccessible
{
	boolean isTwoWayBinding();
	void setTwoWayBinding(boolean twoWay);
	
	boolean alreadyDecoded();
	
	String getShorthandAccessor();
	void setShorthandAccessor(String shorthand);
	
	default int findAccessorAssignment(String statement)
	{
		return SyntaxUtils.findStringInBaseScope(statement, "=>");
	}
	
	default int getShorthandAccessorIndex(String statement)
	{
		int accessorIndex = findAccessorAssignment(statement);
		
		if (accessorIndex > 0)
		{
			setShorthandAccessor(statement.substring(accessorIndex + 2).trim());
			setTwoWayBinding(statement.substring(accessorIndex - 1, accessorIndex + 2).trim().equals("<=>"));
		}
		
		return accessorIndex;
	}
	
	boolean containsAccessorMethod();
	boolean containsMutatorMethod();
	void addDisabledAccessor();
	void addDisabledMutator();
	NovaMethodDeclaration addDefaultAccessor();
	NovaMethodDeclaration addDefaultMutator();
	void addChild(Node n);
	Location getLocationIn();
	
	default Node getParseContext()
	{
		return (Node)this;
	}
	
	default BodyMethodDeclaration decodeAccessor()
	{
		return AccessorMethod.decodeStatement(getParseContext(), "get", getLocationIn(), true)
			.cloneTo(new ShorthandAccessor(getParseContext(), getLocationIn()));
	}
	
	default BodyMethodDeclaration decodeShorthandAccessor()
	{
		return decodeMutator(null);
	}
	
	default BodyMethodDeclaration decodeMutator(Value context)
	{
		return MutatorMethod.decodeStatement(getParseContext(), "set", getLocationIn(), true, context)
			.cloneTo(new ShorthandMutator(getParseContext(), getLocationIn()));
	}
	
	void setType(Value value);
	
	default void decodeArrowBinding()
	{
		decodeArrowBinding(null);
	}
	
	default Value decodeAccessorValue()
	{
		BodyMethodDeclaration a = decodeShorthandAccessor();
		
		addChild(a);
		
		Return returnValue = Return.decodeStatement(a, "return " + getShorthandAccessor(), getLocationIn(), true, false);
		
		a.addChild(returnValue);
		
		return returnValue.getValueNode();
	}
	
	default void decodeMutatorValue(Value value, Value context)
	{
		BodyMethodDeclaration m = decodeShorthandMutator(context);
		
		addChild(m);
		
		String accessorValue = getShorthandAccessor();
		
		if (value instanceof Cast)
		{
			accessorValue = accessorValue.substring(StringUtils.findEndingMatch(accessorValue, 0, '(', ')') + 1);
		}
		
		Assignment assignment = Assignment.decodeStatement(m, accessorValue + " = value", getLocationIn(), true);
		
		m.addChild(assignment);
	}
	
	default void decodeArrowBinding(Value context)
	{
		String accessorValue = getShorthandAccessor();
		boolean twoWayBinding = isTwoWayBinding();
		
		if (accessorValue != null && !alreadyDecoded())
		{
			if (containsAccessorMethod())
			{
				containsAccessorMethod();
				SyntaxMessage.error("Cannot have both an accessor method and shorthand value assignment to a field declaration", (Node)this);
			}
			
			Value value = decodeAccessorValue();
			
			if (twoWayBinding)
			{
				if (containsMutatorMethod())
				{
					SyntaxMessage.error("Cannot have both a mutator method and two-way shorthand value assignment to a field declaration", (Node)this);
				}
				
				decodeMutatorValue(value, context);
			}
			else
			{
				if (!containsMutatorMethod())
				{
					addDisabledMutator();
				}
			}
		}
	}
}