package net.fathomsoft.nova.util;

import java.util.regex.Matcher;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.FileDeclaration;
import net.fathomsoft.nova.tree.InstanceDeclaration;
import net.fathomsoft.nova.tree.Instantiation;
import net.fathomsoft.nova.tree.Literal;
import net.fathomsoft.nova.tree.MethodCall;
import net.fathomsoft.nova.tree.Method;
import net.fathomsoft.nova.tree.Operator;
import net.fathomsoft.nova.tree.ParameterList;
import net.fathomsoft.nova.tree.Parameter;
import net.fathomsoft.nova.tree.Return;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Value;
import net.fathomsoft.nova.tree.variables.Field;
import net.fathomsoft.nova.tree.variables.Variable;

/**
 * Class used for getting information about the Syntax of Nova.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 15, 2014 at 7:55:00 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class SyntaxUtils
{
	/**
	 * Find the next available dot operator index within the given String.
	 * 
	 * @param str The String to find the dot operator within.
	 * @return The index of the dot operator. If a dot operator is not
	 * 		found, then -1 is returned instead.
	 */
	public static int findDotOperator(String str)
	{
		return findCharInBaseScope(str, '.', 0);
	}
	
	/**
	 * Find the next available dot operator index within the given String.
	 * 
	 * @param str The String to find the dot operator within.
	 * @param start The index to start the search at.
	 * @return The index of the dot operator. If a dot operator is not
	 * 		found, then -1 is returned instead.
	 */
	public static int findDotOperator(String str, int start)
	{
		return findCharInBaseScope(str, '.', start);
	}

	/**
	 * Find the next available instance of the given character in the
	 * base scope of the given haystack String. The base scope means
	 * outside of any quotes, parenthesis, and/or brackets.
	 * 
	 * @param haystack The String to find the character within.
	 * @param needle The character to search for in the String.
	 * @return The index of the character. If the character is not
	 * 		found, then -1 is returned instead.
	 */
	public static int findCharInBaseScope(String haystack, char needle)
	{
		return findCharInBaseScope(haystack, needle, 0);
	}
	
	/**
	 * Find the next available instance of the given character in the
	 * base scope of the given haystack String. The base scope means
	 * outside of any quotes, parenthesis, and/or brackets.
	 * 
	 * @param haystack The String to find the character within.
	 * @param needle The character to search for in the String.
	 * @param start The index to start the search at.
	 * @return The index of the character. If the character is not
	 * 		found, then -1 is returned instead.
	 */
	public static int findCharInBaseScope(String haystack, char needle, int start)
	{
		int i = start;
		
		while (i < haystack.length())
		{
			char c = haystack.charAt(i);
			
			if (c == needle)
			{
				return i;
			}
			else if (c == '"')
			{
				i = StringUtils.findEndingQuote(haystack, i) + 1;
			}
			else if (c == '(')
			{
				i = StringUtils.findEndingMatch(haystack, i, '(', ')') + 1;
				
				if (i <= 0)
				{
					return -1;
				}
			}
			else if (c == '[')
			{
				i = StringUtils.findEndingMatch(haystack, i, '[', ']') + 1;
				
				if (i <= 0)
				{
					return -1;
				}
			}
			else if (c == '=')
			{
				return -1;
			}
			else
			{
				i++;
			}
		}
		
		return -1;
	}
	
	/**
	 * Get whether or not the given node is a String literal,
	 * or variable.
	 * 
	 * @param node The node to verify whether or not it is a String.
	 * @return Whether or not the given node is a String literal,
	 * 		or variable.
	 */
	public static boolean isString(Node node)
	{
		if (node instanceof Literal)
		{
			Literal literal = (Literal)node;
			
			return isStringLiteral(literal.getValue());
		}
		else if (node instanceof Value)
		{
			Value value = (Value)node;
			
			value = value.getReturnedNode();
			
//			if (value.getNumChildren() > 0)
//			{
//				return isString(value.getChild(0));
//			}
			
			return value.getType().equals("String");
		}
		
		return false;
	}
	
	/**
	 * Get the class name of the type that the literal value implies.<br>
	 * <br>
	 * For example: "this is a String literal" would return "String"
	 * because String is the class name of the String literal type.
	 * 
	 * @return The class name of the type that the literal value implies.
	 */
	public static String getLiteralTypeName(String literal)
	{
		if (isCharLiteral(literal))
		{
			return "char";
		}
		else if (isStringLiteral(literal))
		{
			return "String";
		}
		else if (isNumber(literal))
		{
			if (isInteger(literal))
			{
				return "int";
			}
			else if (isDouble(literal))
			{
				return "double";
			}
		}
		
		return null;
	}
	
	/**
	 * Get whether or not the given String value represents one of the
	 * following:
	 * <ul>
	 * 	<li>{@link #isStringLiteral(String) String literal}</li>
	 * 	<li>{@link #isCharLiteral(String) Character literal}</li>
	 * 	<li>{@link #isNumber(String) Number literal}</li>
	 * </ul>
	 * 
	 * @param value The String of text to validate.
	 * @return Whether or not the given String value represents a literal.
	 */
	public static boolean isLiteral(String value)
	{
		return isCharLiteral(value) || isStringLiteral(value) || isNumber(value);
	}
	
	/**
	 * Get whether or not the given String value represents a character
	 * literal. A character literal consists of a a single character
	 * surrounded by single quotes.<br>
	 * <br>
	 * Possible inputs:
	 * <ul>
	 * 	<li>'a'</li>
	 * 	<li>'2'</li>
	 * 	<li>'%'</li>
	 * 	<li>'/'</li>
	 * </ul>
	 * 
	 * @param value The String of text to validate.
	 * @return Whether or not the String represents a character literal.
	 */
	public static boolean isCharLiteral(String value)
	{
		if (value.length() == 3)
		{
			return value.charAt(0) == '\'' && value.charAt(value.length() - 1) == '\'';
		}
		if (value.length() == 4)
		{
			return value.charAt(0) == '\'' && value.charAt(1) == '\\' && value.charAt(value.length() - 1) == '\'';
		}
		
		return false;
	}
	
	/**
	 * Get whether or not the given String value represents a String
	 * literal. A String literal consists of a collection of characters
	 * surrounded by double quotes.<br>
	 * <br>
	 * Possible inputs:
	 * <ul>
	 * 	<li>"This is + asdf-523$#$%#4 a String input"</li>
	 * 	<li>"123123123"</li>
	 * 	<li>"More string inputs"</li>
	 * 	<li>"\"Fake quotes around it.\""</li>
	 * </ul>
	 * 
	 * @param value The String of text to validate.
	 * @return Whether or not the String represents a String literal.
	 */
	public static boolean isStringLiteral(String value)
	{
		if (value.length() < 2)
		{
			return false;
		}
		
		if (value.charAt(0) == '"')
		{
			int matching = StringUtils.findEndingQuote(value, 0);
			
			return matching == value.length() - 1;
		}
		
		return false;
	}
	
	/**
	 * Get whether or not the given String value represents zero.
	 * 
	 * @param value The value to check.
	 * @return Whether or not the given String value represents zero.
	 */
	public static boolean isZero(String value)
	{
		boolean seenDot     = false;
		boolean seenExp     = false;
		boolean justSeenExp = false;
		boolean seenDigit   = false;

		int start = value.charAt(0) == '-' ? 1 : 0;
		
		for (int i = start; i < value.length(); i++)
		{
			char c = value.charAt(i);
			
			if (c == '0' && !seenExp)
			{
				seenDigit = true;
				
				continue;
			}
			
			if ((c == '-' || c == '+') && justSeenExp)
			{
				continue;
			}
			
			if (c == '.' && !seenDot)
			{
				seenDot = true;
				
				continue;
			}
			
			justSeenExp = false;
			
			if ((c == 'e' || c == 'E') && !seenExp)
			{
				seenExp     = true;
				justSeenExp = true;
				
				continue;
			}
			
			return false;
		}
		
		if (!seenDigit)
		{
			return false;
		}
		else if (!seenDot)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get whether or not the given String value represents a number.<br>
	 * <br>
	 * Possible inputs:
	 * <ul>
	 * 	<li>-1231412</li>
	 * 	<li>1231412</li>
	 * 	<li>4141.12312</li>
	 * 	<li>-4141.12312</li>
	 * </ul>
	 * 
	 * @param value The String of text to validate.
	 * @return Whether or not the String represents a number.
	 */
	public static boolean isNumber(String value)
	{
		return isInteger(value) || isDouble(value);
	}
	
	/**
	 * Get whether or not the given String value represents a number.<br>
	 * <br>
	 * Possible inputs:
	 * <ul>
	 * 	<li>-1231412</li>
	 * 	<li>1231412</li>
	 * 	<li>4141.12312</li>
	 * 	<li>-4141.12312</li>
	 * </ul>
	 * 
	 * @param value The String of text to validate.
	 * @return Whether or not the String represents a number.
	 */
	public static boolean isInteger(String value)
	{
		if (value.length() <= 0)
		{
			return false;
		}
		
		int start = value.charAt(0) == '-' ? 1 : 0;
		
	    for (int i = start; i < value.length(); i++)
	    {
	        char c = value.charAt(i);
	        
	        if (c < '0' || c > '9')
	        {
	        	return false;
	        }
	    }
	    
	    return true;
	}
	
	/**
	 * Get whether or not the given String value represents a number.<br>
	 * <br>
	 * Possible inputs:
	 * <ul>
	 * 	<li>-1231412</li>
	 * 	<li>1231412</li>
	 * 	<li>4141.12312</li>
	 * 	<li>-4141.12312</li>
	 * </ul>
	 * 
	 * @param value The String of text to validate.
	 * @return Whether or not the String represents a number.
	 */
	public static boolean isDouble(String value)
	{
		boolean seenDot     = false;
		boolean seenExp     = false;
		boolean justSeenExp = false;
		boolean seenDigit   = false;

		int start = value.charAt(0) == '-' ? 1 : 0;
		
		for (int i = start; i < value.length(); i++)
		{
			char c = value.charAt(i);
			
			if (c >= '0' && c <= '9')
			{
				seenDigit = true;
				
				continue;
			}
			
			if ((c == '-' || c == '+') && justSeenExp)
			{
				continue;
			}
			
			if (c == '.' && !seenDot)
			{
				seenDot = true;
				
				continue;
			}
			
			justSeenExp = false;
			
			if ((c == 'e' || c == 'E') && !seenExp)
			{
				seenExp     = true;
				justSeenExp = true;
				
				continue;
			}
			
			return false;
		}
		
		if (!seenDigit)
		{
			return false;
		}
		else if (!seenDot)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get whether the specified type is primitive.<br>
	 * <br>
	 * Primitive types include:
	 * <ul>
	 * 	<li>int</li>
	 * 	<li>char</li>
	 * 	<li>long_long (aka long)</li>
	 * 	<li>boolean</li>
	 * 	<li>short</li>
	 * 	<li>float</li>
	 * 	<li>double</li>
	 * 	<li>void (lightly)</li>
	 * </ul>
	 * 
	 * @param type The type to check.
	 * @return Whether or not the type is primitive.
	 */
	public static boolean isPrimitiveType(String type)
	{
		return type.equals("int") || type.equals("char") || type.equals("long") || type.equals("bool") || type.equals("short") || type.equals("float") || type.equals("double") || type.equals("void");
	}
	
	/**
	 * Get the name of the wrapper class for the given primitive type.
	 * 
	 * @param primitiveType The primitive type to get the wrapper class
	 * 		of.
	 * @return The name of the wrapper class for the given primitive type.
	 */
	public static String getPrimitiveWrapperClassName(String primitiveType)
	{
		if (primitiveType.equals("int"))
		{
			return "Integer";
		}
		else if (primitiveType.equals("char"))
		{
			return "Char";
		}
		else if (primitiveType.equals("long"))
		{
			return "Long";
		}
		else if (primitiveType.equals("bool"))
		{
			return "Bool";
		}
		else if (primitiveType.equals("short"))
		{
			return "Short";
		}
		else if (primitiveType.equals("float"))
		{
			return "Float";
		}
		else if (primitiveType.equals("double"))
		{
			return "Double";
		}
		
		return null;
	}
	
	/**
	 * Get whether or not the given String is a valid variable assignment.
	 * Assignments contain an identifier or array access on the left
	 * hand side of the assignment, and anything that returns a value on
	 * the right hand side.<br>
	 * <br>
	 * For example:
	 * <blockqoute><pre>
	 * variable_NAME1 = (getSize() + 5) / array[index]</pre></blockquote>
	 * 
	 * @param statement The String of text to validate.
	 * @return Whether or not the given String is a valid variable
	 * 		assignment.
	 */
	public static boolean isVariableAssignment(String statement)
	{
		int index = findCharInBaseScope(statement, '=');
		
		if (index <= 0)
		{
			return false;
		}
		
		int binary = StringUtils.findStrings(statement, index - 1, StringUtils.BINARY_OPERATORS).getStart();
		
		if (binary - 1 == index || binary == index)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get whether or not the given String is a valid identifier.
	 * Identifiers can contain characters of the following types:
	 * <ul>
	 * 	<li>A-Z</li>
	 * 	<li>a-z</li>
	 * 	<li>0-9</li>
	 * 	<li>The '_' character (underscore)</li>
	 * </ul>
	 * It should also be noted that numbers cannot start an identifier.
	 * 
	 * @param value The String of text to validate.
	 * @return Whether or not the given String is a valid identifier.
	 */
	public static boolean isValidIdentifier(String value)
	{
		if (Regex.matches(value, Patterns.IDENTIFIER))
		{
			return !SyntaxUtils.isNumber(value.charAt(0) + "");
		}
		
		return false;
	}
	
	/**
	 * Get whether or not the given String is a valid identifier access.<br>
	 * <br>
	 * For example: "<code>getName().publicVarName.methodName().var[72]</code>"
	 * 
	 * @param value The String of text to validate.
	 * @return Whether or not the given String is a valid identifier
	 * 		access.
	 */
	public static boolean isValidIdentifierAccess(String value)
	{
		if (value.length() <= 0)
		{
			return false;
		}
		
		String values[] = value.split("\\s*\\.\\s*");
		
		if (values.length <= 0)
		{
			return false;
		}
		
		for (int i = 0; i < values.length; i++)
		{
			String v = values[i];
			
			if (!isValidIdentifier(v) && !isMethodCall(v) && !isValidArrayAccess(v))
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Get whether or not the given statement is an array access.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * variableName[index]</pre></blockquote>
	 * 
	 * @param value The statement to test.
	 * @return Whether or not the given statement is an array access.
	 */
	public static boolean isValidArrayAccess(String value)
	{
		Bounds bounds = Regex.boundsOf(value, Patterns.ARRAY_ACCESS);
		
		return bounds.getStart() == 0 && bounds.getEnd() == value.length();
	}
	
	/**
	 * Get whether or not the given statement is a method call.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * containingInstance.methodName(arguments, arguments, ..., arguments)</pre></blockquote>
	 * 
	 * @param statement The statement to test.
	 * @return Whether or not the given statement is a method call.
	 */
	public static boolean isMethodCall(String statement)
	{
		Bounds bounds = Regex.boundsOf(statement, Patterns.PRE_METHOD_CALL);
		
		if (bounds.getStart() == 0)
		{
			return StringUtils.findEndingMatch(statement, bounds.getEnd() - 1, '(', ')') == statement.length() - 1;
		}
		
		return false;
	}
	
	/**
	 * Get whether or not the given statement is an array
	 * initialization.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * new dataType[size]...[size]</pre></blockquote>
	 * 
	 * @param statement The statement to test.
	 * @return Whether or not the given statement is an array
	 * 		initialization.
	 */
	public static boolean isArrayInitialization(String statement)
	{
		return Regex.matches(statement, Patterns.ARRAY_INIT);
	}
	
	/**
	 * Calculate the number of dimensions that the given array has, if
	 * any.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * int array[][][] = new int[5][6][7];</pre></blockquote>
	 * The above array declaration has three dimensions. In essence, the
	 * number of dimensions is the amount of brackets that the array
	 * variable declaration contains.<br>
	 * <br>
	 * However, you need to pass only one part of the above code example.
	 * Either pass the declaration, or the instantiation, or else the
	 * number of dimensions will be counted twice.
	 * 
	 * @param statement The statement to find the number of dimensions
	 * 		from.
	 * @param contentPossible Whether or not content is allowed to exist
	 * 		within the set of brackets.
	 * @return The number of dimensions that the given array has, if any.
	 */
	public static int calculateArrayDimensions(String statement, boolean contentPossible)
	{
		int num   = 0;
		int index = statement.indexOf('[') + 1;
		
		while (index > 0)
		{
			int endIndex = statement.indexOf(']', index);
			
			if (endIndex < 0)
			{
				return -1;
			}
			
			String brackets = statement.substring(index - 1, endIndex + 1);
			
			if (!Regex.matches(brackets, Patterns.EMPTY_ARRAY_BRACKETS) && !(contentPossible && Regex.matches(brackets, Patterns.ARRAY_BRACKETS)))
			{
				return -1;
			}
			
			num++;
			
			index = statement.indexOf('[', endIndex + 1) + 1;
		}
		
		return num;
	}
	
	/**
	 * Get whether or not the given MethodNode is a valid main method.
	 * The main method has the method header as the following:
	 * <blockquote><pre>
	 * public static void main(String args[])
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * 
	 * @param method The MethodNode instance to validate.
	 * @return Whether or not the given MethodNode is a valid main method.
	 */
	public static boolean isMainMethod(Method method)
	{
		if (method.getName().equals("main") && method.isStatic() && method.getType().equals("void") && method.getVisibility() == Field.PUBLIC)
		{
			ParameterList params = (ParameterList)method.getParameterListNode();
			
			if (params.getNumChildren() == 2)
			{
				Parameter param = (Parameter)params.getChild(1);
				
				if (param.getType().equals("String") && param.isArray())
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Get whether or not the given statement is an instantiation. For
	 * more details on what an instantiation consists of see
	 * {@link net.fathomsoft.nova.tree.Instantiation#decodeStatement(Node, String, Location, boolean, boolean)}.
	 * 
	 * @param statement The statement to test.
	 * @return Whether or not the given statement is an instantiation.
	 */
	public static boolean isInstantiation(String statement)
	{
		return Regex.indexOf(statement, Patterns.PRE_INSTANTIATION) == 0;
	}
	
	/**
	 * Get whether or not the given DeclarationNode is able to be accessed
	 * from the given ClassNode context.
	 * 
	 * @param accessedFrom The ClassNode context that the DeclarationNode
	 * 		was accessed from.
	 * @param declaration The DeclarationNode that was accessed from the
	 * 		given ClassNode context.
	 * @return Whether or not the given DeclarationNode is able to be
	 * 		accessed from the given ClassNode context.
	 */
	private static boolean isAccessibleFrom(ClassDeclaration accessedFrom, InstanceDeclaration declaration)
	{
		if (accessedFrom.isAncestorOf(declaration))
		{
			return true;
		}
		
		int visibility = declaration.getVisibility();
		
		return visibility == InstanceDeclaration.PUBLIC || visibility == Field.VISIBLE;
	}
	
	/**
	 * Get the ClassNode that contains the accessed identifier. For more
	 * information on what an identifierAccess looks like, see
	 * {@link #isValidIdentifierAccess(String)}.
	 * 
	 * @param reference The ClassNode context that the identifier was
	 * 		accessed from.
	 * @param identifierAccess The trace of the identifier that was
	 * 		accessed.
	 * @return The ClassNode that contains the accessed identifier.
	 */
	public static ClassDeclaration getClassType(ClassDeclaration reference, String identifierAccess)
	{
		if (!isValidIdentifierAccess(identifierAccess))
		{
			return null;
		}
		
		String values[] = identifierAccess.split("\\s*\\.\\s*");
		String output[] = new String[values.length - 1];
		
		FileDeclaration file = reference.getFileNode();
		
		if (file.isExternalImport(values[0]))
		{
			return null;
		}
		
		System.arraycopy(values, 1, output, 0, output.length);
		
		return getClassType(reference, output);
	}
	
	/**
	 * Get the ClassNode that contains the accessed identifier.
	 * 
	 * @param reference The ClassNode context that the identifier was
	 * 		accessed from.
	 * @param identifiers A list of identifiers leading up to the
	 * 		identifier that is being accessed.
	 * @return The ClassNode that contains the accessed identifier.
	 */
	private static ClassDeclaration getClassType(ClassDeclaration reference, String identifiers[])
	{
		if (identifiers.length < 2)
		{
			return reference;
		}
		
		String identifier = identifiers[0];
		
		ClassDeclaration current = null;
		
		if (!identifier.equals("this"))
		{
			identifier = getIdentifierName(identifier);
			
			FileDeclaration f = (FileDeclaration)reference.getAncestorOfType(FileDeclaration.class);
			
			if (f.getImportListNode().containsImport(identifier))
			{
				return f.getProgramNode().getClassNode(identifier);
			}
			
			InstanceDeclaration dec = reference.getDeclaration(identifier);
			
			if (!isAccessibleFrom(reference, dec))
			{
				SyntaxMessage.error("Variable '" + dec.getName() + "' is not visible", reference.getController());
			}
			
			current = dec.getProgramNode().getClassNode(dec.getType());
			
			if (identifiers.length <= 2)
			{
				return current;
			}
		}
		
		String output[] = new String[identifiers.length - 1];
		
		System.arraycopy(identifiers, 1, output, 0, output.length);
		
		return getClassType(current, output);
	}
	
	/**
	 * Get the identifier name that is represented by the given access
	 * String variable.<br>
	 * <br>
	 * For example:
	 * <ul>
	 * 	<li><code>testName()</code> <i>returns</i> <code>testName</code></li>
	 * 	<li><code>arrayAccess[54]</code> <i>returns</i> <code>arrayAccess</code></li>
	 * 	<li><code>idName32</code> <i>returns</i> <code>idName32</code></li>
	 * 	<li><code>methodCall (String name, int args)</code> <i>returns</i> <code>methodCall</code></li>
	 * </ul>
	 * 
	 * @param access The identifier access to get the identifier name
	 * 		from.
	 * @return The name of the identifier from given access String.
	 */
	public static String getIdentifierName(String access)
	{
		if (isMethodCall(access))
		{
			int endIndex = StringUtils.findNextNonWhitespaceIndex(access, access.indexOf('(') - 1, -1) + 1;
			
			access       = access.substring(0, endIndex);
		}
		else if (isValidArrayAccess(access))
		{
			int endIndex = StringUtils.findNextNonWhitespaceIndex(access, access.indexOf('[') - 1, -1) + 1;
			
			access       = access.substring(0, endIndex);
		}
		
		return access;
	}
	
	/**
	 * Format the text to follow syntactical rules.
	 * 
	 * @param text The String to format.
	 * @return The formatted version of the String.
	 */
	public static String formatText(String text)
	{
		StringBuilder builder       = new StringBuilder();
		
		StringBuilder tabs          = new StringBuilder();
		
		Matcher       formatMatcher = Patterns.FORMATTER_PATTERN.matcher(text);
		
		int           lastIndex     = 0;
		boolean       sameLine      = false;
		
		while (formatMatcher.find())
		{
			char c = text.charAt(formatMatcher.start());
			
			if (c == '{' || c == '(')
			{
				tabs.append('\t');
				
				sameLine = true;
			}
			else if (c == '}' || c == ')')
			{
				if (tabs.length() > 0)
				{
					tabs.deleteCharAt(0);
				}
				
				if (builder.length() > 0 && !sameLine)
				{
					builder.deleteCharAt(builder.length() - 1);
				}
			}
			else if (c == '\n')
			{
				String substring = text.substring(lastIndex, formatMatcher.start());
				
				builder.append(substring).append('\n').append(tabs);
				
				lastIndex = formatMatcher.start() + 1;
				
				sameLine = false;
			}
		}
		
		builder.append(tabs).append(text.substring(lastIndex, text.length()));
		
		return builder.toString();
	}
	
	/**
	 * Get whether or not the declaration is accessible from the
	 * given accessor context.
	 * 
	 * @return Whether or not the declaration is accessible from the
	 * 		given accessor context.
	 */
	public static boolean isVisible(Variable accessor, InstanceDeclaration declaration)
	{
		if (declaration.getVisibility() == InstanceDeclaration.PRIVATE)
		{
			ClassDeclaration clazz1 = accessor.getClassNode();
			ClassDeclaration clazz2 = declaration.getDeclaringClassNode();
			
			if (clazz1.isAncestorOf(clazz2, true) || clazz2.isAncestorOf(clazz1))
			{
				return true;
			}
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * Try to autobox the given primitive Node, if it truly has a
	 * primitive value. If the given ValueNode does not have a primitive
	 * type, then null is returned.
	 * 
	 * @param primitive The ValueNode to try to autobox.
	 * @return An instantiation from the corresponding primitive wrapper
	 * 		class. If the given value is not primitive, then null is
	 * 		returned.
	 */
	public static Instantiation autoboxPrimitive(Value primitive)
	{
		Instantiation node = null;
		
		Value returned = primitive.getReturnedNode();
		
		if (returned.isPrimitiveType())
		{
			String className = getPrimitiveWrapperClassName(returned.getType());
			
			String instantiation = "new " + className + '(' + primitive.generateNovaInput() + ')';
			
			node = Instantiation.decodeStatement(primitive.getParent(), instantiation, primitive.getLocationIn(), true, false);
		}
		
		return node;
	}
	
	/**
	 * Check whether or not the given type is valid within the
	 * context of the given ValueNode.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * NonExistingType varName;</pre><blockquote>
	 * In the example above, "<code>NonExistingType</code>" is not an
	 * existing Class and is therefore not a valid type.
	 * 
	 * @param value The ValueNode to use as the context in which the type
	 * 		is being declared.
	 * @param type The type to be tested.
	 * @return Whether or not the given type is valid.
	 */
	public static boolean isValidType(Value value, String type)
	{
		if (type == null)
		{
			return false;
		}
		
		if (value.isWithinExternalContext())
		{
			return true;
		}
		if (value instanceof Literal)
		{
			return true;
		}
		if (value instanceof Operator)
		{
			return true;
		}
		if (value instanceof ClassDeclaration)
		{
			return true;
		}
		else if (value instanceof Return)
		{
			value = (Method)value.getAncestorOfType(Method.class);
		}
		else if (value instanceof MethodCall)
		{
			MethodCall call = (MethodCall)value;
			
			value = call.getMethodDeclarationNode();
		}
		if (value instanceof Method)
		{
			Method method = (Method)value;
			
			if (method.isExternalType() || method.isExternal())
			{
				return true;
			}
		}
		else if (value instanceof Variable)
		{
			Variable var = (Variable)value;
			
			if (var.isExternal())
			{
				return true;
			}
		}
		
		if (SyntaxUtils.isPrimitiveType(type))
		{
			return true;
		}
		else
		{
			ClassDeclaration clazz = value.getProgramNode().getClassNode(type);
			
			if (clazz != null)
			{
				return true;
			}
		}
		
		return value.getClassNode().containsExternalType(type);
	}
	
	/**
	 * Get the base class type that the two ValueNodes have in common. If
	 * the two nodes do not have anything in common, null is returned.
	 * 
	 * @param value1 The first ValueNode to check.
	 * @param value2 The second ValueNode to check.
	 * @return The ClassNode instance that the two ValueNodes have in
	 * 		common. If they have nothing in common, null is returned.
	 */
	public static ClassDeclaration getTypeInCommon(Value value1, Value value2)
	{
		ClassDeclaration type1 = value1.getTypeClass();
		ClassDeclaration type2 = value2.getTypeClass();
		
		if (type1 == null || type2 == null)
		{
			return null;
		}
		
		ClassDeclaration type3 = type2;
		
		while (type2 != null)
		{
			if (type1.isOfType(type2))
			{
				return type2;
			}
			
			type2 = type2.getExtendedClass();
		}
		
		type2 = type3;
		
		while (type1 != null)
		{
			if (type2.isOfType(type1))
			{
				return type1;
			}
			
			type1 = type1.getExtendedClass();
		}
		
		return null;
	}
}