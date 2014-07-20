package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * MethodDeclaration extension that represents the declaration of a Destructor
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:50:43 PM
 * @version	v0.2.14 Jul 19, 2014 at 7:33:13 PM
 */
public class Destructor extends MethodDeclaration
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Destructor(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
//		setStatic(true);
		
//		getParameterList().getObjectReference().setDataType(POINTER);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#isInstance()
	 */
	@Override
	public boolean isInstance()
	{
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		if (isVisibilityValid())
		{
			if (getVisibility() == InstanceDeclaration.PRIVATE)
			{
				SyntaxMessage.error("Destructor must be public", this);
			}
		}
		
		if (isConstant())
		{
			SyntaxMessage.error("Destructor cannot be const", this);
		}
		
		return generateCSourcePrototype(builder).append('\n');
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		generateCSourceSignature(builder).append('\n').append('{').append('\n');

		nullChecker(builder).append('\n');
		
		deleteData(builder).append('\n');
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child != getParameterList())
			{
				child.generateCSource(builder);
			}
		}
		
		builder.append("NOVA_FREE(").append('*').append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(");").append('\n');
		
		builder.append('}').append('\n');
		
		return builder;
	}
	
	/**
	 * Generate the code needed to check if a variable is null before
	 * trying to free its members.
	 * 
	 * @return The code needed to check whether a variable is null or not.
	 */
	private StringBuilder nullChecker(StringBuilder builder)
	{
		builder.append("if (!*").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(')').append('\n');
		builder.append('{').append('\n');
		builder.append("return;").append('\n');
		builder.append('}').append('\n');
		
		return builder;
	}
	
	/**
	 * Generate the code needed to delete each member of the class.
	 * 
	 * @return The code needed to delete each member of the class.
	 */
	private StringBuilder deleteData(StringBuilder builder)
	{
		ClassDeclaration  classDeclaration = getParentClass();
		
		InstanceFieldList privateFields    = classDeclaration.getFieldList().getPrivateFieldList();
		
		for (int i = 0; i < privateFields.getNumChildren(); i++)
		{
			FieldDeclaration field = (FieldDeclaration)privateFields.getChild(i);

			generateFreeFieldSource(builder, field).append('\n');
		}
		
		if (classDeclaration.containsNonStaticPrivateData())
		{
			generateFreeMemberSource(builder, "prv").append('\n');
		}
		
		InstanceFieldList publicFields = classDeclaration.getFieldList().getPublicFieldList();
		
		for (int i = 0; i < publicFields.getNumChildren(); i++)
		{
			FieldDeclaration field = (FieldDeclaration)publicFields.getChild(i);
			
			field.generateFreeOutput(builder);
		}
		
		return builder;
	}
	
	/**
	 * Generate a String for the code used to free memory of an allocated
	 * field variable located within the current class.
	 * 
	 * @param field The node that contains the information of the field.
	 * @return The generated String for the code.
	 */
	private StringBuilder generateFreeFieldSource(StringBuilder builder, FieldDeclaration field)
	{
		if (field.isPrimitiveType() || field.isExternalType())
		{
			if (!field.isPrimitive())
			{
				//builder.append("NOVA_FREE(").append(field.generateVariableUseOutput(true)).append(");");builder.append("printf(\"Aft2.\");");
			}
		}
		else
		{
			builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append("_del_").append(field.getType()).append('(').append('&');
			
			field.generateCUseOutput(builder, true).append(", ").append(Exception.EXCEPTION_DATA_IDENTIFIER).append(");");
		}
		
		return builder;
	}
	
	/**
	 * Generate a String for the code used to free memory of an allocated
	 * member variable with the given name.
	 * 
	 * @param name The name of the variable to delete.
	 * @return The generated String for the code.
	 */
	private StringBuilder generateFreeMemberSource(StringBuilder builder, String name)
	{
		return builder.append("NOVA_FREE((*").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(")->").append(name).append(");");
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#generateCSourcePrototype(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourcePrototype(StringBuilder builder)
	{
		return generateCSourceSignature(builder).append(";");
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#generateCSourceSignature(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceSignature(StringBuilder builder)
	{
		ClassDeclaration classDeclaration = getParentClass();
		
		builder.append(getType()).append(' ');
		builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append("_del_");
		builder.append(classDeclaration.getName()).append('(').append(getParameterList().generateCSource()).append(')');
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a Destructor instance, if
	 * possible. If it is not possible, this method returns null. A
	 * destructor must have the same name as the class that it is
	 * within preceded by a tilde (A tilde is a '~' located above the tab
	 * key). Destructors also do not have a return value and are always
	 * private. They never accept parameters, because they are never
	 * called programmatically.<br>
	 * <br>
	 * The only acceptable syntax input includes: "private ~ClassName()"
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Destructor instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Destructor.
	 */
	public static Destructor decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		int firstParenthIndex = statement.indexOf('(');
		
		if (firstParenthIndex >= 0)
		{
			Destructor n = new Destructor(parent, location);

			int lastParenthIndex = StringUtils.findEndingMatch(statement, firstParenthIndex, '(', ')');
			
			if (lastParenthIndex < 0)
			{
				SyntaxMessage.error("Expected a ')' ending parenthesis", n);
			}
			
			String signature = statement.substring(0, firstParenthIndex);
			
			n.iterateWords(signature);
			
			if (n.getName() == null)
			{
				return null;
			}
			
			String parameterList = statement.substring(firstParenthIndex + 1, lastParenthIndex);
			
			n.validateParameters(parameterList);
			
			ClassDeclaration classDeclaration = n.getParentClass();
			
			if (classDeclaration.getName().equals(n.getName()))
			{
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Validate that there are no parameters because Destructors cannot
	 * have any parameters.
	 * 
	 * @param parameterList The String containing the parameters.
	 */
	private void validateParameters(String parameterList)
	{
		String parameters[]  = StringUtils.splitCommas(parameterList);
		
		if (parameters.length > 0)
		{
			SyntaxMessage.error("Destructors cannot have any parameters", this);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, int, net.fathomsoft.nova.util.Bounds, int, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		setAttribute(word, wordNumber);
		
		if (wordNumber == numWords - 1)
		{
			if (bounds.getStart() > 0)
			{
				if (leftDelimiter.equals("~"))
				{
					setName(word);
				}
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Destructor clone(Node temporaryParent, Location locationIn)
	{
		Destructor node = new Destructor(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Destructor with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Destructor cloneTo(Destructor node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the Destructor class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test()
	{
		
		
		return null;
	}
}