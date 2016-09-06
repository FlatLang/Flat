package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.lambda.LambdaMethodDeclaration;
import net.fathomsoft.nova.util.Location;

import java.util.ArrayList;

/**
 * Node extension that represents a list of parameters for a nova
 * method.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.38 Nov 11, 2014 at 3:44:42 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class NovaParameterList extends ParameterList<Parameter>
{
	private ReturnParameterList returnParameters;
	
	public NovaParameterList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		returnParameters = new ReturnParameterList(this, locationIn.asNew());
	}

	public int getNumRequiredParameters()
	{
		int required = 0;

		for (Parameter p : this)
		{
			if (p.isOptional())
			{
				return required;
			}

			required++;
		}

		return required;
	}

	public Parameter[] getOptionalParameters()
	{
		ArrayList<Parameter> list = new ArrayList<>();
		
		for (Parameter p : this)
		{
			if (p.isOptional())
			{
				list.add(p);
			}
		}

		return list.toArray(new Parameter[0]);
	}
	
	public int getNumReturnParameters()
	{
		return returnParameters.getNumVisibleChildren();
	}
	
	public void addReturnParameter(String type)
	{
		Parameter p = Parameter.decodeStatement(this, type + " ret" + (returnParameters.getNumVisibleChildren() + 1), getLocationIn().asNew(), true, false, true);
		p.setForceOriginalName(true);
		p.validateType();
		p.setIsValueReference(true);
		
		returnParameters.addChild(p);
	}
	
	public Value[] getReturnTypes()
	{
		Value[] types = new Value[getNumReturnParameters()];
		
		for (int i = 0; i < returnParameters.getNumVisibleChildren(); i++)
		{
			types[i] = returnParameters.getVisibleChild(i);
		}
		
		return types;
	}
	
	@Override
	public StringBuilder generateCSourceParameters(StringBuilder builder)
	{
		super.generateCSourceParameters(builder);
		
		if (returnParameters.getNumVisibleChildren() > 0)
		{
			builder.append(", ");
			
			returnParameters.generateCSourceParameters(builder);
		}
		
		if (getMethodDeclaration() instanceof LambdaMethodDeclaration)
		{
			builder.append(", ").append(((LambdaMethodDeclaration)getMethodDeclaration()).context.getName()).append("* ").append(ClosureVariableDeclaration.CONTEXT_VARIABLE_NAME);
		}
		
		return builder;
	}
	
	@Override
	public StringBuilder generateCHeaderParameters(StringBuilder builder)
	{
		super.generateCHeaderParameters(builder);
		
		if (returnParameters.getNumVisibleChildren() > 0)
		{
			builder.append(", ");
			
			returnParameters.generateCHeaderParameters(builder);
		}
		
		if (getMethodDeclaration() instanceof LambdaMethodDeclaration)
		{
			builder.append(", ").append(((LambdaMethodDeclaration)getMethodDeclaration()).context.getName()).append('*');
		}
		
		return builder;
	}
	
	@Override
	public Parameter getParameter(int parameterIndex)
	{
		Parameter param = super.getParameter(parameterIndex);
		
		if (param == null)
		{
			param = returnParameters.getVisibleChild(parameterIndex - getNumVisibleChildren());
		}
		
		return param;
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public NovaParameterList clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		NovaParameterList node = new NovaParameterList(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public NovaParameterList cloneTo(NovaParameterList node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link NovaParameterList} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public NovaParameterList cloneTo(NovaParameterList node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		node.returnParameters = returnParameters.clone(node, getLocationIn().asNew(), true);
		
		return node;
	}
	
	/**
	 * Test the NovaParameterList class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	/**
	 * ParameterList extension that represents the extraneous return
	 * values that the method returns.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.42 Dec 26, 2014 at 4:14:42 PM
	 * @version	v0.2.42 Dec 26, 2014 at 4:14:42 PM
	 */
	public static class ReturnParameterList extends ParameterList<Parameter>
	{
		public ReturnParameterList(Node temporaryParent, Location locationIn)
		{
			super(temporaryParent, locationIn);
			
			slaughterEveryLastChild();
		}
		
		@Override
		public int getParameterOffset()
		{
			return 0;
		}
		
		/**
		 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
		 */
		@Override
		public ReturnParameterList clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
		{
			ReturnParameterList node = new ReturnParameterList(temporaryParent, locationIn);
			
			return cloneTo(node, cloneChildren);
		}
		
		/**
		 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
		 */
		public ReturnParameterList cloneTo(ReturnParameterList node)
		{
			return cloneTo(node, true);
		}
		
		/**
		 * Fill the given {@link ReturnParameterList} with the data that is in the
		 * specified node.
		 * 
		 * @param node The node to copy the data into.
		 * @return The cloned node.
		 */
		public ReturnParameterList cloneTo(ReturnParameterList node, boolean cloneChildren)
		{
			super.cloneTo(node, cloneChildren);
			
			return node;
		}
	}
}