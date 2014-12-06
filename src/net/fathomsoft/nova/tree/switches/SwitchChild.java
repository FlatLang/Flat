package net.fathomsoft.nova.tree.switches;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.37 Oct 17, 2014 at 11:46:55 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public interface SwitchChild
{
	public default Switch getParentSwitch()
	{
		return (Switch)((Node)this).getAncestorOfType(Switch.class);
	}
}