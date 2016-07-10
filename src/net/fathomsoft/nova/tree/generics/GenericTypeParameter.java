package net.fathomsoft.nova.tree.generics;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * {@link Node} extension that represents a generic type parameter.
 * Contains the information of a generic type parameter. Information
 * such as the name of the parameter and the default type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.41 Dec 7, 2014 at 11:01:21 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class GenericTypeParameter extends IValue
{
	private String defaultType;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericTypeParameter(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		defaultType = "Object";
	}
	
	public String getName()
	{
		return getType();
	}
	
	public void setName(String name)
	{
		setTypeValue(name);
	}
	
	public String getDefaultType()
	{
		return defaultType;
	}
	
	public void setDefaultType(String type)
	{
		this.defaultType = type;
	}
	
	public GenericTypeParameterDeclaration getGenericDeclaration()
	{
		return (GenericTypeParameterDeclaration)getParent();
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_CLASS_DECLARATION || (getParentMethod() != null && phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS))
		{
			if (!getDefaultType().equals("Object") && !SyntaxUtils.validateImported(this, getDefaultType(), false))
			{
				SyntaxUtils.validateImported(this, getDefaultType(), false);
				SyntaxUtils.throwImportException(this, getDefaultType(), getLocationIn(), false);
				
				result = result.errorOccurred(this);
			}
		}
		
		/*GenericTypeArgumentList args = getGenericTypeArgumentList();
		GenericTypeParameterDeclaration decl = getProgram().getClassDeclaration(SyntaxUtils.getTypeClassLocation(this, getDefaultType())).getGenericTypeParameterDeclaration();
		
		for (int i = args.getNumVisibleChildren(); i < decl.getNumParameters(); i++)
		{
			args.addChild(SyntaxUtils.getGenericTypeArgumentName(this, decl.getParameter(i).getDefaultType()));
		}*/
		
		return result;
	}
	
	public GenericTypeArgument getCorrespondingArgument(GenericCompatible context)
	{
		if (context instanceof Accessible)
		{
			//Value reference = ((Accessible)context).getReferenceNode().toValue();
			
			/*ClassDeclaration clazz = null;
			
			if (reference.isGenericType())
			{
				clazz = reference.getGenericTypeParameter().getCorrespondingArgument(reference).getTypeClass();
			}
			else
			{
				clazz = reference.getTypeClass();
			}*/
			
			if (context instanceof Identifier)
			{
				//Identifier var = (Identifier)reference;
				
				//return var.getGenericTypeArgumentFromParameter(this);
				
				/*if (reference.isGenericType() && reference.getGenericTypeParameter() == this)
				{
					return ((Identifier)reference).getGenericTypeArgumentFromParameter(this);
				}*/
				
				return ((Identifier)context).getGenericTypeArgumentFromParameter(this);
			}
			/*else if (isMethodGenericTypeParameter())
			{
				return null;
			}*/
			
			return null;//reference.getGenericTypeArgument(getType());
			
			//clazz.getgenerictypepara
		}
		
		return null;
	}
	
	@Override
	public String getNovaType(Value context)
	{
		if (isGenericType())
		{
			return getDefaultType();
		}
		
		return super.getNovaType(context);
	}
	
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append(getName());
		
		if (!getDefaultType().equals("Object"))
		{
			builder.append(" extends ").append(getDefaultType());
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericTypeParameter clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		GenericTypeParameter node = new GenericTypeParameter(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public GenericTypeParameter cloneTo(GenericTypeParameter node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link GenericTypeParameter} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericTypeParameter cloneTo(GenericTypeParameter node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		node.defaultType = this.defaultType;
		
		return node;
	}
	
	/**
	 * Test the {@link GenericTypeParameter} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	public String toString(boolean carets)
	{
		String str = getName();
		
		if (!getDefaultType().equals("Object"))
		{
			str += " extends " + getDefaultType();
		}
		
		if (carets)
		{
			str = "<" + str + ">";
		}
		
		return str;
	}
	
	public String toString()
	{
		return toString(true);
	}
}