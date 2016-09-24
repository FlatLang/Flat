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
    public StringBuilder generateCSourceFragment(StringBuilder builder)
    {
        String use = parameter.generateCUseOutput().toString();
        
        builder.append(use).append(" = ");
        parameter.generateCTypeCast(builder).append('(');
        
        builder.append(use).append(" == ");
        
        if (parameter.isPrimitive())
        {
            builder.append("(intptr_t)nova_null");
        }
        else
        {
            builder.append(0);
        }
        
        String cast = !parameter.getDefaultValue().isPrimitive() ? getProgram().getClassDeclaration("nova/Object").generateCTypeCast().toString() : "";
        
        builder.append(" ? ").append(cast).append(parameter.getDefaultValue().generateCSourceFragment()).append(" : ").append(cast).append(use);
        
        return builder.append(");");
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
    public DefaultParameterInitialization clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
    {
        DefaultParameterInitialization node = new DefaultParameterInitialization(temporaryParent, locationIn, parameter);

        return cloneTo(node, cloneChildren);
    }

    /**
     * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
     */
    public DefaultParameterInitialization cloneTo(DefaultParameterInitialization node)
    {
        return cloneTo(node, true);
    }

    /**
     * Fill the given {@link DefaultParameterInitialization} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public DefaultParameterInitialization cloneTo(DefaultParameterInitialization node, boolean cloneChildren)
    {
        super.cloneTo(node, cloneChildren);

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