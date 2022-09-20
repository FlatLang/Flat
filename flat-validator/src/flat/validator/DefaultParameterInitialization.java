package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;

public class DefaultParameterInitialization extends NodeValidator
{
    public Parameter parameter;
    
    /**
     * @see NodeValidator#NodeValidator(NodeValidator, Location)
     */
    public DefaultParameterInitialization(NodeValidator temporaryParent, Location locationIn, Parameter parameter)
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
     * @see NodeValidator#validate(int)
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
            parameter = ((FlatMethodDeclarationValidator)getParent().getParent()).getParameterList().getParameter(parameter.getIndex());
        }
        
        return result;
    }

    /**
     * @see NodeValidator#clone(NodeValidator, Location, boolean)
     */
    @Override
    public DefaultParameterInitialization clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
    {
        DefaultParameterInitialization node = new DefaultParameterInitialization(temporaryParent, locationIn, parameter);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see NodeValidator#cloneTo(NodeValidator)
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