package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class MethodCallArgument extends Value
{
    public String name;
    
    public Value value;
    
    /**
     * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
     */
    public MethodCallArgument(Node temporaryParent, Location locationIn, Value value)
    {
        super(temporaryParent, locationIn);
        
        this.value = value;
        
        value.setTemporaryParent(this);
    }

    @Override
    public void replace(Node old, Node replacement)
    {
        if (old == value)
        {
            value.detach();
            
            value = (Value)replacement;
            
            replacement.setTemporaryParent(this);
        }
        else
        {
            super.replace(old, replacement);
        }
    }

    @Override
    public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
    {
        return value.generateNovaInput(builder, outputChildren);
    }

    @Override
    public StringBuilder generateCHeader(StringBuilder builder)
    {
        return value.generateCHeader(builder);
    }

    @Override
    public StringBuilder generateCHeaderFragment(StringBuilder builder)
    {
        return value.generateCHeaderFragment(builder);
    }

    @Override
    public StringBuilder generateCSource(StringBuilder builder)
    {
        return value.generateCSource(builder);
    }

    @Override
    public StringBuilder generateCSourceFragment(StringBuilder builder)
    {
        return value.generateCSourceFragment(builder);
    }

    @Override
    public StringBuilder generateCSourcePrefix(StringBuilder builder)
    {
        return value.generateCSourcePrefix(builder);
    }

    @Override
    public void onAdded(Node parent)
    {
        value.onAdded(parent);
    }

    /**
     * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
     */
    @Override
    public MethodCallArgument clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
    {
        MethodCallArgument node = new MethodCallArgument(temporaryParent, locationIn, value);

        return cloneTo(node, cloneChildren);
    }

    /**
     * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
     */
    public MethodCallArgument cloneTo(MethodCallArgument node)
    {
        return cloneTo(node, true);
    }

    /**
     * Fill the given {@link MethodCallArgument} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public MethodCallArgument cloneTo(MethodCallArgument node, boolean cloneChildren)
    {
        super.cloneTo(node, cloneChildren);

        return node;
    }

    /**
     * Test the {@link MethodCallArgument} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * 		successful, null is returned.
     */
    public static String test(TestContext context)
    {


        return null;
    }

    @Override
    public String getType()
    {
        return value.getType();
    }

    @Override
    public String getTypeStringValue()
    {
        return value.getTypeStringValue();
    }

    @Override
    public void setTypeValue(String type)
    {
        value.setTypeValue(type);
    }

    @Override
    public int getArrayDimensions()
    {
        return value.getArrayDimensions();
    }

    @Override
    public void setArrayDimensions(int arrayDimensions)
    {
        value.setArrayDimensions(arrayDimensions);
    }

    @Override
    public byte getDataType(boolean checkGeneric)
    {
        return value.getDataType(checkGeneric);
    }

    @Override
    public void setDataType(byte type)
    {
        value.setDataType(type);
    }
}