package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Identifier extension that represents the use of a variable
 * type. Harnesses the needed information of each variable, such as
 * whether or not it is constant, external, or an array, and its type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jul 5, 2014 at 9:02:42 PM
 * @version	v0.2.14 Jul 19, 2014 at 7:33:13 PM
 */
public class ClosureDeclaration extends Parameter implements CallableMethod
{
	private int id;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ClosureDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ParameterList<Value> parameterList = new ParameterList<Value>(this, null);
		
		addChild(parameterList);
		
		parameterList.getObjectReference().setType("void");
		parameterList.getObjectReference().setDataType(POINTER);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.CallableMethod#isVirtual()
	 */
	@Override
	public boolean isVirtual()
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.CallableMethod#getParameterList()
	 */
	@Override
	public ParameterList<Value> getParameterList()
	{
		return (ParameterList<Value>)getChild(super.getNumDefaultChildren());
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.CallableMethod#isStatic()
	 */
	@Override
	public boolean isStatic()
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.CallableMethod#isInstance()
	 */
	@Override
	public boolean isInstance()
	{
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableDeclaration#setAttribute(java.lang.String, int)
	 */
	@Override
	public boolean setAttribute(String attribute, int argNum)
	{
		if (super.setAttribute(attribute, argNum))
		{
			return !attribute.equals(getConstantText());
		}
		
		return false;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		return generateCSource(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		builder.append(generateCTypeOutput()).append(' ').append(generateCSourceName()).append(", ");
		getParameterList().getObjectReference().generateCTypeOutput(builder).append(' ').append(generateCSourceName("ref"));
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#generateCTypeOutput(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCTypeOutput(StringBuilder builder)
	{
		return builder.append(generateCSourceName(id + ""));
	}
	
	/**
	 * Generate the C type definition for the closure of the specified
	 * method declaration.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void test()
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * will output will have the effect of
	 * "<code>typedef void (*closure_test)();</code>"
	 * 
	 * @return The C closure type definition for the method.
	 */
	public StringBuilder generateCClosureDefinition(StringBuilder builder)
	{
		builder.append("typedef ");
		
		super.generateCTypeOutput(builder).append(" (*").append(generateCSourceName(id + "")).append(')');
		builder.append('(').append(getParameterList().generateCHeader()).append(')').append(";\n");
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a Closure instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>Person findPerson(String, int)</li>
	 * 	<li>int calculateArea(int, int)</li>
	 * 	<li>void callback()</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Closure instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Closure.
	 */
	public static ClosureDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (validateMethodDeclaration(statement))
		{
			ClosureDeclaration n = new ClosureDeclaration(parent, location);
			
			// Bounds of the data within the parentheses.
			Bounds bounds = SyntaxUtils.findInnerParenthesesBounds(n, statement);
			
			if (n.decodeSignature(statement, require) && n.validateDeclaration(statement, bounds, require))
			{
				n.checkExternalType();
				n.id = n.getFileDeclaration().registerClosure(n);
				
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Validate that the given statement is a method declaration.
	 * 
	 * @param statement The statement to validate.
	 * @return Whether or not the given statement is a valid method
	 * 		declaration.
	 */
	private static boolean validateMethodDeclaration(String statement)
	{
		int firstParenthIndex = statement.indexOf('(');
		
		return firstParenthIndex >= 0 && !Regex.startsWith(statement, Patterns.EXTERNAL);
	}
	
	/**
	 * Decode the method signature.<br>
	 * <br>
	 * For example: "<u><code>public static void main</code></u>"
	 * 
	 * @param statement The statement to decode the signature from.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the signature was successfully decoded.
	 */
	private boolean decodeSignature(String statement, boolean require)
	{
		int parenthesisIndex = statement.indexOf('(');
		int end              = StringUtils.findNextNonWhitespaceIndex(statement, parenthesisIndex - 1, -1) + 1;
		
		String signature = statement.substring(0, end);
		ExtraData data   = iterateWords(signature, new ExtraData());
		
		if (data.error != null)
		{
			return SyntaxMessage.queryError(data.error, this, require);
		}
		
		return true;
	}
	
	/**
	 * Validate that the method declaration is correct. Make sure that a
	 * return type is specified, its parameters are declared correctly,
	 * and other issues.
	 * 
	 * @param statement The statement containing the method declaration.
	 * @param bounds The Bounds of the parameters.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the declaration is valid.
	 */
	private boolean validateDeclaration(String statement, Bounds bounds, boolean require)
	{
		if (getType() == null)
		{
			return false;
		}
		
		String parameterList = statement.substring(bounds.getStart(), bounds.getEnd());
		
		return decodeParameters(parameterList, require);
	}
	
	/**
	 * Check to see if the return type of the method is an external type.
	 */
	private void checkExternalType()
	{
		if (getParentClass().containsExternalType(getType()))
		{
			setDataType(Value.POINTER);
		}
	}
	
	/**
	 * Decode the given parameters that were declared for the Method.
	 * 
	 * @param parameterList The statement that contains the parameters
	 * 		separated by commas.
	 * @param require Whether or not a successful decode is required.
	 * @return Whether or not the parameters were decoded correctly.
	 */
	public boolean decodeParameters(String parameterList, boolean require)
	{
		if (parameterList.length() <= 0)
		{
			return true;
		}
		
		String parameters[] = StringUtils.splitCommas(parameterList);
		
		Location location = new Location(getLocationIn());
		
		for (int i = 0; i < parameters.length; i++)
		{
			if (parameters[i].length() > 0)
			{
				IValue param = IValue.generateFromType(this, location, parameters[i], require);
				
				if (param == null)
				{
					return SyntaxMessage.queryError("Incorrect parameter definition", this, require);
				}
				
				getParameterList().addChild(param);
			}
			else
			{
				SyntaxMessage.error("Expected a parameter definition", this);
			}
		}
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, int, net.fathomsoft.nova.util.Bounds, int, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData data)
	{
		String full = word;
		
		word = StringUtils.findNextWord(full, 0);
		
		if (wordNumber == numWords - 1)
		{
			setName(word);
		}
		else if (wordNumber == numWords - 2)
		{
			if (!setType(word, false))
			{
				data.error = "Type '" + word + "' does not exist";
				
				return;
			}
			
			int index = full.indexOf(word) > 0 ? 0 : word.length();
			
			checkArray(full, index, rightDelimiter);
		}
		else
		{
			data.error = "Unknown closure definition";
			
			return;
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public ClosureDeclaration clone(Node temporaryParent, Location locationIn)
	{
		ClosureDeclaration node = new ClosureDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Variable with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClosureDeclaration cloneTo(ClosureDeclaration node)
	{
		super.cloneTo(node);
		
		node.id = id;
		
		return node;
	}

	/**
	 * @see net.fathomsoft.nova.tree.CallableMethod#generateCVirtualMethodName()
	 */
	@Override
	public StringBuilder generateCVirtualMethodName()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.nova.tree.CallableMethod#generateCVirtualMethodName(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCVirtualMethodName(StringBuilder builder)
	{
		return null;
	}
	
	/**
	 * Test the ClosureDeclaration class type to make sure everything
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