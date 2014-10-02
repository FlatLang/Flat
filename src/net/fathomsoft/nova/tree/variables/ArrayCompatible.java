package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.Dimensions;
import net.fathomsoft.nova.tree.Node;

public interface ArrayCompatible
{
	/**
	 * Get the number of dimensions that this node contains.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * int i[][][] = new int[5][4][3];</pre></blockquote>
	 * The above array has three dimensions. In general, the number of
	 * dimensions relates to the number of sets of brackets there are
	 * contained within the array declaration.
	 * 
	 * @return The number of dimensions that this node contains.
	 */
	public default int getNumDimensions()
	{
		return getDimensions().getNumChildren();
	}
	
	/**
	 * Get the node that represents the dimensions of the array. The
	 * Dimensions class contains information of the index that is
	 * being accessed.
	 * 
	 * @return The node that represents the dimensions being accessed.
	 */
	public Dimensions getDimensions();
	
	/**
	 * Add a dimension, that contains the index that is being attained,
	 * to the Dimensions instance of the ArrayAccess.
	 * 
	 * @param child The node that describes the index that is being
	 * 		accessed by the array at the specified dimension that is
	 * 		about to be added.
	 */
	public default void addDimension(Node child)
	{
		getDimensions().addChild(child);
	}
}