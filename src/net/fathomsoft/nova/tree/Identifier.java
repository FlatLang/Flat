package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.tree.generics.GenericTypeParameterDeclaration;
import net.fathomsoft.nova.tree.variables.Array;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;


/**
 * Value extension that represents an Identifier. For the rules on
 * what can and cannot be an Identifier, refer to
 * {@link net.fathomsoft.nova.tree.Identifier#setName(java.lang.String) setName}
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:19 PM
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public abstract class Identifier extends Value implements Accessible
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Identifier(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get whether or not the Identifier is accessed in a non-static way.
	 * 
	 * @return Whether or not the Identifier is accessed in a non-static
	 * 		way.
	 */
	public boolean isInstance()
	{
		return SyntaxUtils.getImportedClass(getFileDeclaration(), getName()) == null;
	}

	@Override
	public Value getReturnedNode()
	{
		return Accessible.super.getReturnedNode();
	}
	
	@Override
	public boolean isUserMade()
	{
		return !doesForceOriginalName() && super.isUserMade();
	}
	
	@Override
	public boolean isExternalType()
	{
		return getType() != null && getDeclaringClass().containsExternalType(getType());
	}
	
	/**
	 * Set the name of the Identifier. Identifier names consist of the
	 * following character types: [A-Z, a-z, 0-9, _]. However, cannot
	 * start with a number.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>correctGrammar</li>
	 * 	<li>INCORrect_grammaR_but_123123STILL_workz</li>
	 * 	<li>identifierName4</li>
	 * </ul>
	 * <br>
	 * Incorrect inputs include:<br>
	 * <ul>
	 * 	<li>24JeffGordon <i>(Cannot start an identifier with a number)</i></li>
	 * 	<li>This.Doesnt_work <i>(Cannot contain a period (or any other punctuation))</i></li>
	 * 	<li>#omgProgramin <i>(This is not Twitter)</i></li>
	 * </ul>
	 * 
	 * @param name The String containing the name to set as the
	 * 		identifier.
	 */
	public void setName(String name)
	{
		setName(name, false);
	}
	
	/**
	 * Get whether or not the Identifier is a declaration.
	 * 
	 * @return Whether or not the Identifier is a declaration.
	 */
	public boolean isDeclaration()
	{
		return false;
	}
	
	/**
	 * Get whether or not the specified Node is both used within a
	 * static context and not accessed by an instance.
	 * 
	 * @return Whether or not the specified Node is both used within a
	 * 		static context and not accessed by an instance.
	 */
	public boolean isAccessedWithinStaticContext()
	{
		return isWithinStaticContext() && !isAccessed() && (!isInstance() || getParentClass().containsMethod(getName()));
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append(getName());
		generateNovaArrayAccess(builder);
		
		if (outputChildren && doesAccess())
		{
			builder.append('.').append(getAccessedNode().generateNovaInput());
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder).append(";\n");
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		if (isGenericType() && doesAccess())
		{
			getReturnedNode().generateCTypeCast(builder);

			builder.append('(');
		}

		if (isSpecialFragment())
		{
			generateSpecialFragment(builder);
		}
		else
		{
			generateCUseOutput(builder).append(generateChildrenCSourceFragment());
		}

		if (isGenericType() && doesAccess())
		{
			builder.append(')');
		}
		
		return builder;
	}
	
	/**
	 * Generate the representation of when the variable is being used, in
	 * action, rather than being declared.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * Person p;
	 * p.getName();</pre></blockquote>
	 * The first line shows the declaration of the Variable. The second
	 * line demonstrates a "variable use" for the "p" variable.
	 * Essentially, the "variable use" output is exactly what it says,
	 * what the variable looks like when it is being used to do something.
	 * 
	 * @return What the variable looks like when it is being used to do
	 * 		something.
	 */
	public StringBuilder generateCUseOutput(StringBuilder builder)
	{
		return generateCUseOutput(builder, false);
	}
	
	/**
	 * Generate the representation of when the variable is being used, in
	 * action, rather than being declared.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * Person p;
	 * p.getName();</pre></blockquote>
	 * The first line shows the declaration of the Variable. The second
	 * line demonstrates a "variable use" for the "p" variable.
	 * Essentially, the "variable use" output is exactly what it says,
	 * what the variable looks like when it is being used to do something.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @param pointer Whether or not the variable is to be accessed by a
	 * 		pointer.
	 * @return What the variable looks like when it is being used to do
	 * 		something.
	 */
	public StringBuilder generateCUseOutput(StringBuilder builder, boolean pointer)
	{
		return generateCUseOutput(builder, pointer, true);
	}
	
	public StringBuilder generateCUseOutput(StringBuilder builder, boolean pointer, boolean checkAccesses)
	{
//		if (!isSpecialFragment())
//		{
//			builder.append(generateDataTypeOutput());
//		}
		
		FieldDeclaration field = null;
		
		Node parent = getParent();
		
		if (parent instanceof Array)
		{
			VariableDeclaration node = SyntaxTree.findDeclaration(parent.getParent(), getName());
			
			if (node instanceof FieldDeclaration)
			{
				field = (FieldDeclaration)node;
			}
		}
		else if (this instanceof Variable)
		{
			VariableDeclaration decl = ((Variable)this).getDeclaration();
			
			if (decl instanceof FieldDeclaration)
			{
				field = (FieldDeclaration)decl;
			}
		}
		else if (this instanceof FieldDeclaration)
		{
			field = (FieldDeclaration)this;
		}
		
		if (field != null && !field.isExternal())
		{
			if (!field.isStatic())
			{
				Value ref = (Value)getReferenceNode();
				
				if (ref.getTypeClass().isContainingClass(this))
				{
					if (!isAccessed())
					{
						if (pointer)
						{
							builder.append('(').append('*');
						}
						
						builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
						
						if (pointer)
						{
							builder.append(')');
						}
					}
					
					if (!isAccessed())//ref.isContainingClass(this))
					{
						builder.append("->");
					}
					if (field.getVisibility() == FieldDeclaration.PRIVATE)
					{
						builder.append("prv").append("->");
					}
//					else
//					{
//						builder.append("->");
//					}
				}
			}
		}
		
		if (isValueReference())
		{
			builder.append("(*");
			
			generateCSourcePrefix(builder);
		}
		
		generateCSourceName(builder);
		
		if (isValueReference())
		{
			builder.append(')');
		}
		
		generateCArrayAccess(builder);
		
		return builder;
	}
	
	/*public GenericTypeArgument getGenericTypeArgumentFromParameter(GenericTypeParameter param)
	{
		return getGenericTypeArgumentFromParameter(param.getType());
	}*/
	
	public String getCName()
	{
		return getName();
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 * 
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public final StringBuilder generateCSourceName()
	{
		return generateCSourceName(new StringBuilder());
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public final StringBuilder generateCSourceName(StringBuilder builder)
	{
		return generateCSourceName(builder, null);
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 * 
	 * @param uniquePrefix The unique identifying prefix to prepend to the
	 * 		name output.
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public final StringBuilder generateCSourceName(String uniquePrefix)
	{
		return generateCSourceName(new StringBuilder(), uniquePrefix);
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @param uniquePrefix The unique identifying prefix to prepend to the
	 * 		name output.
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public StringBuilder generateCSourceName(StringBuilder builder, String uniquePrefix)
	{
		String name = getCName();
		
		if (doesForceOriginalName())
		{
			return builder.append(name);
		}
		
		VariableDeclaration existing = null;
		
		if (isDeclaration())
		{
			existing = (VariableDeclaration)this;
		}
		else if (this instanceof Variable)
		{
			existing = ((Variable)this).getDeclaration();
		}
		else
		{
			existing = SyntaxTree.findDeclaration(getParent(), name, false);
			
			if (existing == null)
			{
				SyntaxMessage.error("Unable to find declaration for variable '" + name + "'", this);
			}
		}
		
		if (!(existing instanceof LocalDeclaration && existing instanceof Parameter == false))
		{
			existing.getParentClass(true).generateCSourceName(builder).append('_');
		}
		
//		if (existing instanceof InstanceDeclaration)
//		{
//			InstanceDeclaration node = (InstanceDeclaration)existing;
//			
//			if (node.isStatic())
//			{
//				if (!(node instanceof MethodDeclaration && ((MethodDeclaration)node).isInstance()))
//				{
//					builder.append("static_");
//				}
//			}
//		}
		
		if (existing instanceof LocalDeclaration && existing instanceof Parameter == false)
		{
			LocalDeclaration declaration = (LocalDeclaration)existing;
			
			builder.append('l').append(declaration.getScopeID()).append('_');
		}

		if (uniquePrefix != null)
		{
			builder.append(uniquePrefix).append('_');
		}
		
		builder.append(Nova.LANGUAGE_NAME).append("_");
		
		return builder.append(name);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#isVirtualTypeKnown()
	 */
	@Override
	public boolean isVirtualTypeKnown()
	{
		if (isAccessedWithinStaticContext())
		{
			return true;
		}
		
		if (isAccessed())
		{
			if (getAccessingNode() instanceof Identifier)
			{
				Identifier id = (Identifier)getAccessingNode();
				
				return !id.isInstance();
			}
		}
		
		return false;
	}
	
	/**
	 * Get the name of the Identifier. For the rules on what can and
	 * cannot be an Identifier, refer to {@link net.fathomsoft.nova.tree.Identifier#setName(java.lang.String) setName}
	 * 
	 * @return The name of the Identifier.
	 */
	public abstract String getName();

	/**
	 * Set the name of the Variable. You specify whether or not you want
	 * the output in the C language to be the original given name,
	 * or if it will differentiate it depending on its scope. 
	 * 
	 * @param name The String to set as the new name.
	 * @param forceOriginal Whether or not the name will be output in the
	 * 		c code verbatim.
	 */
	public abstract void setName(String name, boolean forceOriginal);
	
	/**
	 * Whether or not the output in the C language will be the
	 * original given name, or if it will differentiate it depending on
	 * its scope. 
	 * 
	 * @return Whether or not the name will be output in the C code
	 * 		verbatim.
	 */
	public abstract boolean doesForceOriginalName();
	
	/**
	 * Whether or not you want the output in the C language to be the
	 * original given name, or if it will differentiate it depending on
	 * its scope. 
	 * 
	 * @param forceOriginal Whether or not the name will be output in the
	 * 		c code verbatim.
	 */
	public abstract void setForceOriginalName(boolean forceOriginal);
	
	/**
	 * Fill the given {@link Identifier} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Identifier cloneTo(Identifier node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Identifier} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Identifier cloneTo(Identifier node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		node.setName(getName());
		node.setForceOriginalName(doesForceOriginalName());
		node.setArrayDimensions(getArrayDimensions());
		node.setTypeValue(getType());
		node.setDataType(getDataType());
		
		return node;
	}
	
	/**
	 * Test the Identifier class type to make sure everything
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