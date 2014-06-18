package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.InstanceDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that contains all of the Fields for a ClassDeclaration.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:00:50 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class FieldList extends Node
{
	/**
	 * Instantiate and initialize default data.
	 */
	public FieldList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		InstanceFieldList privateFields       = new InstanceFieldList(this, locationIn);
		InstanceFieldList publicFields        = new InstanceFieldList(this, locationIn);
		StaticFieldList   privateStaticFields = new StaticFieldList(this, locationIn);
		StaticFieldList   publicStaticFields  = new StaticFieldList(this, locationIn);
		
		super.addChild(privateFields);
		super.addChild(publicFields);
		super.addChild(privateStaticFields);
		super.addChild(publicStaticFields);
	}
	
	/**
	 * Get the PrivateFieldList that contains all of the private
	 * Fields for its parent ClassDeclaration.
	 * 
	 * @return The PrivateFieldList instance.
	 */
	public InstanceFieldList getPrivateFieldList()
	{
		return (InstanceFieldList)getChild(0);
	}
	
	/**
	 * Get the PublicFieldList that contains all of the public
	 * Fields for its parent ClassDeclaration.
	 * 
	 * @return The PublicFieldList instance.
	 */
	public InstanceFieldList getPublicFieldList()
	{
		return (InstanceFieldList)getChild(1);
	}
	
	/**
	 * Get the PrivateFieldList that contains all of the private
	 * Fields for its parent ClassDeclaration.
	 * 
	 * @return The PrivateFieldList instance.
	 */
	public StaticFieldList getPrivateStaticFieldList()
	{
		return (StaticFieldList)getChild(2);
	}
	
	/**
	 * Get the PublicFieldList that contains all of the public
	 * Fields for its parent ClassDeclaration.
	 * 
	 * @return The PublicFieldList instance.
	 */
	public StaticFieldList getPublicStaticFieldList()
	{
		return (StaticFieldList)getChild(3);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#addChild(Node node)
	 */
	@Override
	public void addChild(Node node)
	{
		if (node instanceof Field)
		{
			Field field = (Field)node;
			
			if (field.getVisibility() == InstanceDeclaration.PRIVATE)
			{
				if (field.isStatic())
				{
					getPrivateStaticFieldList().addChild(field);
				}
				else
				{
					getPrivateFieldList().addChild(field);
				}
			}
			else if (field.getVisibility() == InstanceDeclaration.PUBLIC || field.getVisibility() == Field.VISIBLE)
			{
				if (field.isStatic())
				{
					getPublicStaticFieldList().addChild(field);
				}
				else
				{
					getPublicFieldList().addChild(field);
				}
			}
			else
			{
				SyntaxMessage.error("Missing visibility declaration", this);
			}
		}
		else
		{
			SyntaxMessage.error("Unknown node being added", this);
		}
	}
	
	/**
	 * Get whether or not the FieldList contains the Field with
	 * the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	private int age;
	 * 	private String name;
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>containsField("age")</code>" would return true.
	 * 
	 * @param fieldName The name of the field to search for.
	 * @return Whether or not the FieldList contains the Field
	 * 		with the specified name.
	 */
	public boolean containsField(String fieldName)
	{
		return getField(fieldName) != null;
	}
	
	/**
	 * Get the FieldList's Field with the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	private int age;
	 * 	private String name;
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getField("age")</code>" would return the
	 * Field for the "<code>age</code>" int field.
	 * 
	 * @param fieldName The name of the field to search for.
	 * @return The Field for the field, if it exists.
	 */
	public Field getField(String fieldName)
	{
		Node nodes[] = new Node[] { getPrivateFieldList(), getPrivateStaticFieldList(), getPublicFieldList(), getPublicStaticFieldList() };
		
		for (Node node : nodes)
		{
			Field field = searchFieldList(node, fieldName);
			
			if (field != null)
			{
				return field;
			}
		}
		
		return null;
	}
	
	/**
	 * Search the given Node (which should only contain Field
	 * children) for a Field with the given name.
	 * 
	 * @param fieldList The list of fields to search through.
	 * @param fieldName The name of the field to search for.
	 * @return The Field instance with the given name, if found.
	 */
	private Field searchFieldList(Node fieldList, String fieldName)
	{
		for (int i = 0; i < fieldList.getNumChildren(); i++)
		{
			Field variable = (Field)fieldList.getChild(i);
			
			if (variable.getName().equals(fieldName))
			{
				return variable;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
		}
		
		if (getNumChildren() > 0)
		{
			ClassDeclaration parent = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class, true);
			
			if (parent.getMethodList().getNumChildren() > 0)
			{
				builder.append('\n');
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Generate the C Header output for the all of the non-static
	 * variables.
	 * 
	 * @return The C Header file output.
	 */
	public String generateNonStaticCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getPublicFieldList().generateCHeader());
		
		return builder.toString();
	}
	
	/**
	 * Generate the C Header output for the all of the public static
	 * variables.
	 * 
	 * @return The C Header file output.
	 */
	public String generateStaticCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getPublicStaticFieldList().generateCHeader());
		
		return builder.toString();
	}
	
	/**
	 * Generate the C Source output for the all of the public static
	 * variables.
	 * 
	 * @return The C Source file output.
	 */
	public String generateStaticCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getPublicStaticFieldList().generateCSource());
		
		return builder.toString();
	}

	/**
	 * Generate the C Source output for the all of the non-static
	 * variables.
	 * 
	 * @return The C Source file output.
	 */
	public String generateNonStaticCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getPrivateStaticFieldList().generateCHeader());
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public FieldList clone(Node temporaryParent, Location locationIn)
	{
		FieldList node = new FieldList(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given FieldList with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FieldList cloneTo(FieldList node)
	{
		super.cloneTo(node);
		
		return node;
	}
}