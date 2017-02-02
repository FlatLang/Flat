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
	
	default BodyMethodDeclaration decodeAccessor()
	{
		return AccessorMethod.decodeStatement((Node)this, "get", getLocationIn(), true)
			.cloneTo(new ShorthandAccessor((Node)this, getLocationIn()));
	}
	
	default BodyMethodDeclaration decodeMutator()
	{
		return decodeMutator(null);
	}
	
	default BodyMethodDeclaration decodeMutator(Value context)
	{
		return MutatorMethod.decodeStatement((Node)this, "set", getLocationIn(), true, context)
			.cloneTo(new ShorthandMutator((Node)this, getLocationIn()));
	}
	
	void setType(Value value);
	
	default void decodeShorthandAccessor()
	{
		decodeShorthandAccessor(null);
	}
	
	default Value decodeAccessorValue()
	{
		BodyMethodDeclaration a = decodeAccessor();
		
		Return returnValue = Return.decodeStatement(a, "return " + getShorthandAccessor(), getLocationIn(), true, false);
		
		a.addChild(returnValue);
		
		addChild(a);
		
		return returnValue.getValueNode();
	}
	
	default void decodeShorthandAccessor(Value context)
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

//			Value type = returnValue.getReturnedNode().getNovaTypeValue(returnValue.getReturnedNode());
//			
//			if (returnValue.getValueNode() instanceof Cast)
//			{
//				setType(returnValue.getValueNode());
//			}
//			else
//			{
//				setType(type);
//			}
			//a.setType(type);
			
			if (twoWayBinding)
			{
				if (containsMutatorMethod())
				{
					SyntaxMessage.error("Cannot have both a mutator method and two-way shorthand value assignment to a field declaration", (Node)this);
				}
				
				BodyMethodDeclaration m = decodeMutator(context);
				
				addChild(m);
				
				Value value = returnValue.getValueNode();
				
				if (value instanceof Cast)
				{
					accessorValue = accessorValue.substring(StringUtils.findEndingMatch(accessorValue, 0, '(', ')') + 1);
				}
				
				Assignment assignment = Assignment.decodeStatement(m, accessorValue + " = value", getLocationIn(), true);
				
				m.addChild(assignment);
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