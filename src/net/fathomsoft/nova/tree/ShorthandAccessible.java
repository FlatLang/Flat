package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

public interface ShorthandAccessible
{
	boolean isTwoWayBinding();
	void setTwoWayBinding(boolean twoWay);
	
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
	void addChild(Node n);
	Location getLocationIn();
	
	default BodyMethodDeclaration decodeAccessor()
	{
		return AccessorMethod.decodeStatement((Node)this, "get", getLocationIn(), true)
			.cloneTo(new ShorthandAccessor((Node)this, getLocationIn()));
	}
	
	default BodyMethodDeclaration decodeMutator()
	{
		return MutatorMethod.decodeStatement((Node)this, "set", getLocationIn(), true)
			.cloneTo(new ShorthandMutator((Node)this, getLocationIn()));
	}
	
	default void decodeShorthandAccessor()
	{
		String accessorValue = getShorthandAccessor();
		boolean twoWayBinding = isTwoWayBinding();
		
		if (accessorValue != null)
		{
			if (containsAccessorMethod())
			{
				SyntaxMessage.error("Cannot have both an accessor method and shorthand value assignment to a field declaration", (Node)this);
			}
			
			BodyMethodDeclaration a = decodeAccessor();
			
			addChild(a);
			
			a.addChild(Return.decodeStatement(a, "return " + accessorValue, getLocationIn(), true));
			
			if (twoWayBinding)
			{
				if (containsMutatorMethod())
				{
					SyntaxMessage.error("Cannot have both a mutator method and two-way shorthand value assignment to a field declaration", (Node)this);
				}
				
				BodyMethodDeclaration m = decodeMutator();
				
				addChild(m);
				
				m.addChild(Assignment.decodeStatement(m, accessorValue + " = value", getLocationIn(), true));
			}
			else
			{
				if (!containsMutatorMethod())
				{
					addChild(MutatorMethod.decodeStatement((Node)this, "no set", getLocationIn(), true));
				}
			}
		}
	}
}