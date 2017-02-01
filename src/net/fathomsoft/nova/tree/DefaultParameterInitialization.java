package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class DefaultParameterInitialization extends Node
{
    public Parameter parameter;
    
    /**
     * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
     */
    public DefaultParameterInitialization(Node temporaryParent, Location locationIn, Parameter parameter)
    {
        super(temporaryParent, locationIn);
        
        this.parameter = parameter;
    }
    
    @Override
    public boolean isUserMade(boolean checkAncestor)
    {
        return false;
    }
    
    /**
     * @see net.fathomsoft.nova.tree.Node#validate(int)
     */
    @Override
    public ValidationResult validate(int phase)
    {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation())
        {
            return result;
        }
        
        // If methods arent same
        if (getParent().getParent() != parameter.getParent().getParent())
        {
            parameter = ((NovaMethodDeclaration)getParent().getParent()).getParameterList().getParameter(parameter.getIndex());
        }
        
        return result;
    }

    /**
     * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
     */
    @Override
    public DefaultParameterInitialization clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
    {
        DefaultParameterInitialization node = new DefaultParameterInitialization(temporaryParent, locationIn, parameter);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
     */
    public DefaultParameterInitialization cloneTo(DefaultParameterInitialization node)
    {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link DefaultParameterInitialization} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public DefaultParameterInitialization cloneTo(DefaultParameterInitialization node, boolean cloneChildren, boolean cloneAnnotations)
    {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link DefaultParameterInitialization} class type to make sure everything
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