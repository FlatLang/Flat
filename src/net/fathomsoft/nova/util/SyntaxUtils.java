package net.fathomsoft.nova.util;

import java.util.regex.Matcher;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.BodyMethodDeclaration;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Closure;
import net.fathomsoft.nova.tree.FileDeclaration;
import net.fathomsoft.nova.tree.Identifier;
import net.fathomsoft.nova.tree.InstanceDeclaration;
import net.fathomsoft.nova.tree.Instantiation;
import net.fathomsoft.nova.tree.Literal;
import net.fathomsoft.nova.tree.MethodCall;
import net.fathomsoft.nova.tree.MethodDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Operator;
import net.fathomsoft.nova.tree.Parameter;
import net.fathomsoft.nova.tree.ParameterList;
import net.fathomsoft.nova.tree.Program;
import net.fathomsoft.nova.tree.Return;
import net.fathomsoft.nova.tree.Value;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.Variable;

/**
 * Class used for getting information about the Syntax of Nova.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 15, 2014 at 7:55:00 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class SyntaxUtils
{
	private static final int CHAR = 1, BYTE = 1, SHORT = 2, INT = 3, LONG = 4, FLOAT = 5, DOUBLE = 6;
	
	/**
	 * Get the rank of the given primitive type in terms of assignment
	 * hierarchy. For example: integers can be assigned to long types and
	 * not vice-versa.
	 * 
	 * @param primitiveType The primitive type to get the rank of.
	 * @return The rank of the given primitive type.
	 */
	private static int getPrimitiveRank(String primitiveType)
	{
		primitiveType = getPrimitiveWrapperClassName(primitiveType);
		
		if (primitiveType.equals("Char"))
		{
			return CHAR;
		}
		else if (primitiveType.equals("Byte"))
		{
			return BYTE;
		}
		else if (primitiveType.equals("Short"))
		{
			return SHORT;
		}
		else if (primitiveType.equals("Int"))
		{
			return INT;
		}
		else if (primitiveType.equals("Long"))
		{
			return LONG;
		}
		else if (primitiveType.equals("Float"))
		{
			return FLOAT;
		}
		else if (primitiveType.equals("Double"))
		{
			return DOUBLE;
		}
		
		return 0;
	}
	
	/**
	 * Compare the ranks of the two given primitive types and return the
	 * higher one.
	 * 
	 * @param type1 The first primitive type to check.
	 * @param type2 The second primitive type to check.
	 * @return The higher ranked primitive type of the two given.
	 */
	public static String getHighestPrimitiveType(String type1, String type2)
	{
		int rank1 = getPrimitiveRank(type1);
		int rank2 = getPrimitiveRank(type2);
		
		if (rank1 > rank2)
		{
			return type1;
		}
		
		return type2;
	}
	
	/**
	 * Check to see if the given primitive is compatible with the required
	 * primitive type.
	 * 
	 * @param required The required primitive type.
	 * @param given The given primitive type to check against the required
	 * 		primitive type.
	 * @return Whether or not the two primitive types are compatible.
	 */
	public static boolean arePrimitiveTypesCompatible(String required, String given)
	{
		int rank1 = getPrimitiveRank(required);
		int rank2 = getPrimitiveRank(given);
		
		if (rank1 <= 0 || rank2 <= 0)
		{
			return false;
		}
		
		return rank2 <= rank1;
	}
	
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
		return findCharInBaseScope(haystack, new char[] { needle }, start);
	}
	
	/**
	 * Find the next available instance of the given character in the
	 * base scope of the given haystack String. The base scope means
	 * outside of any quotes, parenthesis, and/or brackets.
	 * 
	 * @param haystack The String to find the character within.
	 * @param needles The characters to search for in the String.
	 * @param start The index to start the search at.
	 * @return The index of the character. If the character is not
	 * 		found, then -1 is returned instead.
	 */
	public static int findCharInBaseScope(String haystack, char needles[], int start)
	{
		while (start < haystack.length())
		{
			char c = haystack.charAt(start);
			
			if (StringUtils.containsChar(needles, c))
			{
				return start;
			}
			else if (c == '"')
			{
				start = StringUtils.findEndingQuote(haystack, start) + 1;
			}
			else if (c == '\'')
			{
				start = StringUtils.findEndingChar(haystack, c, start, 1) + 1;
			}
			else if (c == '(')
			{
				start = StringUtils.findEndingMatch(haystack, start, '(', ')') + 1;
				
				if (start <= 0)
				{
					return -1;
				}
			}
			else if (c == '[')
			{
				start = StringUtils.findEndingMatch(haystack, start, '[', ']') + 1;
				
				if (start <= 0)
				{
					return -1;
				}
			}
//			else if (c == '=')
//			{
//				return -1;
//			}
			else
			{
				start++;
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
			Value abstractValue = (Value)node;
			
			abstractValue = abstractValue.getReturnedNode();
			
//			if (value.getNumChildren() > 0)
//			{
//				return isString(value.getChild(0));
//			}
			
			return abstractValue.getType().equals("String");
		}
		
		return false;
	}
	
	/**
	 * Get the class name of the type that the literal value implies.<br>
	 * <br>
	 * For example: "this is a String literal" would return "String"
	 * because String is the class name of the String literal type.
	 * 
	 * @param literal The literal to find the type name of.
	 * @return The class name of the type that the literal value implies.
	 */
	public static String getLiteralTypeName(String literal)
	{
		if (literal.equals(Literal.NULL_IDENTIFIER))
		{
			return "Object";
		}
		else if (literal.equals(Literal.TRUE_IDENTIFIER) || literal.equals(Literal.FALSE_IDENTIFIER))
		{
			return "bool";
		}
		else if (isCharLiteral(literal))
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
		return getLiteralTypeName(value) != null;//isCharLiteral(value) || isStringLiteral(value) || isNumber(value);
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
		return type == null || type.equals("int") || type.equals("char") || type.equals("long") || type.equals("bool") || type.equals("short") || type.equals("float") || type.equals("double") || type.equals("byte");
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
		if (primitiveType == null)
		{
			return "Object";
		}
		else if (primitiveType.equals("int"))
		{
			return "Int";
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
		else if (primitiveType.equals("byte"))
		{
			return "Byte";
		}
		
		return primitiveType;
	}
	
	/**
	 * Get the name of the primitive type given the wrapper class name.
	 * 
	 * @param wrapperName The name of the wrapper class.
	 * @return The name of the primitive type given the wrapper class
	 * 		name.
	 */
	public static String getWrapperClassPrimitiveName(String wrapperName)
	{
		if (wrapperName.equals("Int"))
		{
			return "int";
		}
		else if (wrapperName.equals("Byte"))
		{
			return "byte";
		}
		else if (wrapperName.equals("Char"))
		{
			return "char";
		}
		else if (wrapperName.equals("Long"))
		{
			return "long";
		}
		else if (wrapperName.equals("Bool"))
		{
			return "bool";
		}
		else if (wrapperName.equals("Short"))
		{
			return "short";
		}
		else if (wrapperName.equals("Float"))
		{
			return "float";
		}
		else if (wrapperName.equals("Double"))
		{
			return "double";
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
	 * <blockquote><pre>
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
		
		int binary = StringUtils.findStrings(statement, StringUtils.BINARY_OPERATORS, index - 1).getStart();
		
		return binary - 1 != index && binary != index && binary != index - 1;
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
	 * Calculate the Bounds of the data that is within the parentheses
	 * in the given statement.
	 * 
	 * @param parent The node to throw an error with if anything goes
	 * 		wrong.
	 * @param statement The statement containing parentheses.
	 * @return The bounds of the data within the parentheses.
	 */
	public static Bounds findParenthesesBounds(Node parent, String statement)
	{
		int start = statement.indexOf('(');
		int end   = StringUtils.findEndingMatch(statement, start, '(', ')');
		
		if (end < 0)
		{
			SyntaxMessage.error("Expected a ')' ending parenthesis", parent);
		}
		
		return new Bounds(start, end);
	}
	
	/**
	 * Calculate the Bounds of the data that is within the parentheses
	 * in the given statement.
	 * 
	 * @param parent The node to throw an error with if anything goes
	 * 		wrong.
	 * @param statement The statement containing parentheses.
	 * @return The bounds of the data within the parentheses.
	 */
	public static Bounds findInnerParenthesesBounds(Node parent, String statement)
	{
		return StringUtils.removeSurroundingParenthesis(statement, findParenthesesBounds(parent, statement));
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
	public static int findArrayDimensions(String statement, boolean contentPossible)
	{
		return findArrayDimensions(statement, 0, contentPossible);
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
	 * @param start The index to start the search at.
	 * @param contentPossible Whether or not content is allowed to exist
	 * 		within the set of brackets.
	 * @return The number of dimensions that the given array has, if any.
	 */
	public static int findArrayDimensions(String statement, int start, boolean contentPossible)
	{
		if (start < 0)
		{
			return 0;
		}
		
		int index = statement.indexOf('[', start);
		
		if (index < 0)
		{
			return 0;
		}
		
		int end = StringUtils.findEndingMatch(statement, index, '[', ']');
		
		if (end < 0)
		{
			return 0;
		}
		
		int next = StringUtils.findNextNonWhitespaceIndex(statement, end + 1);
		
		if (!contentPossible)
		{
			if (StringUtils.findNextNonWhitespaceIndex(statement, index + 1) != end)
			{
				return -1;
			}
		}
		
		int nextAmount = findArrayDimensions(statement, next, contentPossible);
		
		if (nextAmount < 0)
		{
			return nextAmount;
		}
		
		return 1 + nextAmount;
	}
	
	/**
	 * Get whether or not the given Method is a valid main method.
	 * The main method has the method header as the following:
	 * <blockquote><pre>
	 * public static void main(String args[])
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * 
	 * @param methodDeclaration The Method instance to validate.
	 * @return Whether or not the given Method is a valid main method.
	 */
	public static boolean isMainMethod(BodyMethodDeclaration methodDeclaration)
	{
		if (methodDeclaration.getName().equals("main") && methodDeclaration.isStatic() && methodDeclaration.getType() == null && methodDeclaration.getVisibility() == FieldDeclaration.PUBLIC)
		{
			ParameterList<Value> params = methodDeclaration.getParameterList();
			
			if (params.getNumVisibleChildren() == 1)
			{
				Parameter param = (Parameter)params.getVisibleChild(0);
				
				if (param.getType().equals("String") && param.isArray())
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Check to see if the given Variable needs to be set as
	 * volatile to retain its value after a throw statement
	 * has been thrown.
	 * 
	 * @param variable The Variable to check.
	 */
	public static void checkVolatile(Variable variable)
	{
		if (variable.isLocal())
		{
			if (variable.getParentTry() != null && variable.getDeclaration().getParentTry() != variable.getParentTry())
			{
				variable.setVolatile(true);
			}
		}
	}
	
	/**
	 * Get whether or not the given statement is an instantiation. For
	 * more details on what an instantiation consists of see
	 * {@link net.fathomsoft.nova.tree.Instantiation#decodeStatement(Node, String, Location, boolean)}.
	 * 
	 * @param statement The statement to test.
	 * @return Whether or not the given statement is an instantiation.
	 */
	public static boolean isInstantiation(String statement)
	{
		return Regex.indexOf(statement, Patterns.PRE_INSTANTIATION) == 0;
	}
	
	/**
	 * Get whether or not the given Declaration is able to be accessed
	 * from the given ClassDeclaration context.
	 * 
	 * @param accessedFrom The ClassDeclaration context that the Declaration
	 * 		was accessed from.
	 * @param declaration The Declaration that was accessed from the
	 * 		given ClassDeclaration context.
	 * @return Whether or not the given Declaration is able to be
	 * 		accessed from the given ClassDeclaration context.
	 */
	private static boolean isAccessibleFrom(ClassDeclaration accessedFrom, InstanceDeclaration declaration)
	{
		if (accessedFrom.isAncestorOf(declaration))
		{
			return true;
		}
		
		int visibility = declaration.getVisibility();
		
		return visibility == InstanceDeclaration.PUBLIC || visibility == FieldDeclaration.VISIBLE;
	}
	
	/**
	 * Validate that the given MethodDeclaration can be accessed through
	 * the given Identifier accessor.
	 * 
	 * @param accessor The Identifier that is accessing the method.
	 * @param accessed The MethodDeclaration that was accessed.
	 * @param parent The parent to use as the context for the error,
	 * 		if one happens.
	 */
	public static void validateMethodAccess(Identifier accessor, MethodDeclaration accessed, Node parent)
	{
		if (!accessed.isExternal() && !accessed.isStatic())
		{
			if (accessor instanceof ClassDeclaration)
			{
				SyntaxMessage.error("Cannot call a non-static method from a static context", parent);
			}
		}
		else if (!isAccessibleFrom(accessor.getTypeClass(), accessed))
		{
			SyntaxMessage.error("Method '" + accessed.getName() + "' is not visible", parent);
		}
	}
	
	/**
	 * Get the ClassDeclaration that contains the accessed identifier. For more
	 * information on what an identifierAccess looks like, see
	 * {@link #isValidIdentifierAccess(String)}.
	 * 
	 * @param reference The ClassDeclaration context that the identifier was
	 * 		accessed from.
	 * @param identifierAccess The trace of the identifier that was
	 * 		accessed.
	 * @return The ClassDeclaration that contains the accessed identifier.
	 */
	public static ClassDeclaration getClassType(ClassDeclaration reference, String identifierAccess)
	{
		if (!isValidIdentifierAccess(identifierAccess))
		{
			return null;
		}
		
		String values[] = identifierAccess.split("\\s*\\.\\s*");
		String output[] = new String[values.length - 1];
		
		FileDeclaration file = reference.getFileDeclaration();
		
		if (file.isExternalImport(values[0]))
		{
			return null;
		}
		
		System.arraycopy(values, 1, output, 0, output.length);
		
		return getClassType(reference, output);
	}
	
	/**
	 * Get the ClassDeclaration that contains the accessed identifier.
	 * 
	 * @param reference The ClassDeclaration context that the identifier was
	 * 		accessed from.
	 * @param identifiers A list of identifiers leading up to the
	 * 		identifier that is being accessed.
	 * @return The ClassDeclaration that contains the accessed identifier.
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
			
			if (f.getImportList().containsImport(identifier))
			{
				return f.getProgram().getClassDeclaration(identifier);
			}
			
			InstanceDeclaration dec = reference.getDeclaration(identifier);
			
			if (!isAccessibleFrom(reference, dec))
			{
				SyntaxMessage.error("Variable '" + dec.getName() + "' is not visible", reference.getController());
			}
			
			current = dec.getProgram().getClassDeclaration(dec.getType());
			
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
	 * @param accessor The Identifier that is accessing the type.
	 * @param declaration The declaration of the type that is being
	 * 		accessed.
	 * @return Whether or not the declaration is accessible from the
	 * 		given accessor context.
	 */
	public static boolean isVisible(Identifier accessor, InstanceDeclaration declaration)
	{
		if (declaration.getVisibility() == InstanceDeclaration.PRIVATE)
		{
			ClassDeclaration clazz1 = accessor.getParentClass(true);
			ClassDeclaration clazz2 = declaration.getParentClass();
			
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
	 * primitive value. If the given Value does not have a primitive
	 * type, then null is returned.
	 * 
	 * @param primitive The Value to try to autobox.
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
			
			node = Instantiation.decodeStatement(primitive.getParent(), instantiation, primitive.getLocationIn(), true);
		}
		
		return node;
	}
	
	/**
	 * Check whether or not the given type is valid within the
	 * context of the given Value.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * NonExistingType varName;</pre></blockquote>
	 * In the example above, "<code>NonExistingType</code>" is not an
	 * existing Class and is therefore not a valid type.
	 * 
	 * @param value The Value to use as the context in which the type
	 * 		is being declared.
	 * @param type The type to be tested.
	 * @return Whether or not the given type is valid.
	 */
	public static boolean isValidType(Value value, String type)
	{
		if (type == null)
		{
			return true;
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
			value = (MethodDeclaration)value.getAncestorOfType(MethodDeclaration.class);
		}
		else if (value instanceof MethodCall)
		{
			MethodCall call = (MethodCall)value;
			
			value = call.getParentMethod();
		}
		if (value instanceof MethodDeclaration)
		{
			MethodDeclaration methodDeclaration = (MethodDeclaration)value;
			
			if (methodDeclaration.isExternalType())
			{
				return true;
			}
		}
		else if (value instanceof Variable)
		{
			Variable var = (Variable)value;
			
			if (var.getDeclaration().isExternal())
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
			ClassDeclaration clazz = value.getProgram().getClassDeclaration(type);
			
			if (clazz != null)
			{
				return SyntaxUtils.validateImported(value, clazz.getType());
			}
		}
		
		return value.getParentClass().containsExternalType(type);
	}
	
	/**
	 * Check whether or not the two given values are compatible
	 * through comparison or arithmetic.
	 * 
	 * @param value1 The first Value to check.
	 * @param value2 The second Value to check.
	 * @return Whether or not the two given values are compatible
	 * 		through comparison or arithmetic.
	 */
	public static boolean validateCompatibleTypes(Value value1, Value value2)
	{
		if (value1.isExternalType() || value2.isExternalType())
		{
			if (value1 instanceof Literal || value2 instanceof Literal)
			{
				Literal value = value1 instanceof Literal ? (Literal)value1 : (Literal)value2;
				
				if (value.getValue().equals("0"))
				{
					return true;
				}
			}
			
			if (value1.isExternalType() && value2.isExternalType())
			{
				return value1.getType().equals(value2.getType());
			}
		}
//		else if (value1 instanceof Literal || value2 instanceof Literal)
//		{
//			Literal value = value1 instanceof Literal ? (Literal)value1 : (Literal)value2;
//			
//			if (value.getValue().equals("0"))
//			{
//				return true;
//			}
//		}
		
		return getTypeInCommon(value1, value2) != null;
	}
	
	/**
	 * Get the base class type that the two Values have in common. If
	 * the two nodes do not have anything in common, null is returned.
	 * 
	 * @param value1 The first Value to check.
	 * @param value2 The second Value to check.
	 * @return The ClassDeclaration instance that the two Values have in
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
	
//	/**
//	 * Get the base class type that the two Values have in common. If
//	 * the two nodes do not have anything in common, null is returned.
//	 * 
//	 * @param value1 The first Value to check.
//	 * @param value2 The second Value to check.
//	 * @return The ClassDeclaration instance that the two Values have in
//	 * 		common. If they have nothing in common, null is returned.
//	 */
//	public static ClassDeclaration getTypeInCommon(Value value1, Value value2)
//	{
//		Program program = value1.getProgram();
//		
//		return program.getClassDeclaration(getTypeInCommon(program, value1.getType(), value2.getType()));
//	}
	
	/**
	 * Get the type in common between the two given types.
	 * 
	 * @param context The program that the two types are located in.
	 * @param type1 The first type to compare.
	 * @param type2 The second type to compare.
	 * @return The type in common between the two types. If there is
	 * 		no type in common, null is returned.
	 */
	public static String getTypeInCommon(Program context, String type1, String type2)
	{
		ClassDeclaration clazz1 = context.getClassDeclaration(type1);
		ClassDeclaration clazz2 = context.getClassDeclaration(type2);
		
		if (clazz1 == null || clazz2 == null)
		{
			return null;
		}
		
		ClassDeclaration clazz3 = clazz1;
		
		while (clazz2 != null)
		{
			if (clazz1.isOfType(clazz2))
			{
				return type2;
			}
			
			clazz2 = clazz2.getExtendedClass();
		}
		
		clazz2 = clazz3;
		
		while (clazz1 != null)
		{
			if (clazz2.isOfType(clazz1))
			{
				return type1;
			}
			
			clazz1 = clazz1.getExtendedClass();
		}
		
		return null;
	}
	
	/**
	 * Check to see if the 'given' type is compatible with the
	 * required type.
	 * 
	 * @param context The program that the two types are located in.
	 * @param required The type that the 'given' type is required
	 * 		to be compatible with.
	 * @param given The given type to check against the required type.
	 * @return Whether or not the two types are compatible.
	 */
	public static boolean isTypeCompatible(Program context, String required, String given)
	{
		return isTypeCompatible(context.getClassDeclaration(getPrimitiveWrapperClassName(given)), context.getClassDeclaration(getPrimitiveWrapperClassName(required)));
	}
	
	/**
	 * Check to see if the 'given' type is compatible with the
	 * required type.
	 * 
	 * @param required The type that the 'given' type is required
	 * 		to be compatible with.
	 * @param given The given type to check against the required type.
	 * @return Whether or not the two types are compatible.
	 */
	public static boolean isTypeCompatible(Value required, Value given)
	{
		if (given instanceof Closure)
		{
			return true;
		}
		else if (given.isExternalType() ^ required.isExternalType())
		{
			return false;
		}
		else if (given.isExternalType() && given.getType().equals(required.getType()))
		{
			return true;
		}
		else if (given.isPrimitiveType() ^ required.isPrimitiveType() == false && given.getTypeClass().isOfType(required.getTypeClass()))
		{
			return true;
		}
		else if (arePrimitiveTypesCompatible(required.getTypeClassName(), given.getTypeClassName()))
		{
			return true;
		}
		
		if (required.getTypeClassName().equals("Char") && required.getArrayDimensions() == 1 && given.getTypeClassName().equals("String"))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check to see if the FileDeclaration already has the given class
	 * imported or not.
	 * 
	 * @param file The File to check to see whether or not has the class
	 * 		imported.
	 * @param clazz The class to see if the File has imported.
	 * @return Whether or not the File already has the given class
	 * 		imported.
	 */
	public static boolean isImported(FileDeclaration file, String clazz)
	{
		return file.containsImport(clazz) || file.containsClass(clazz);
	}
	
	/**
	 * Validate that given class has been imported by the given node's
	 * FileDeclaration.
	 * 
	 * @param node The node that needs to validate that the class
	 * 		has been imported.
	 * @param clazz The class to check to see if has been imported.
	 * @return Whether or not the class has been imported.
	 */
	public static boolean validateImported(Node node, String clazz)
	{
		return validateImported(node, clazz, node.getLocationIn());
	}
	
	/**
	 * Validate that given class has been imported by the given node's
	 * FileDeclaration.
	 * 
	 * @param node The node that needs to validate that the class
	 * 		has been imported.
	 * @param clazz The class to check to see if has been imported.
	 * @param location The location to point the error at if the class
	 * 		has not been imported.
	 * @return Whether or not the class has been imported.
	 */
	public static boolean validateImported(Node node, String clazz, Location location)
	{
		if (node.getProgram().getClassDeclaration(clazz) != null)
		{
			if (!isImported(node.getFileDeclaration(), clazz))
			{
				SyntaxMessage.error("Type '" + clazz + "' is not imported", node, location);
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check to see if the type is a basic type and does not
	 * have a class to represent it.
	 * 
	 * @param type The type to check.
	 * @return Whether or not it is a basic type.
	 */
	public static boolean isBasicType(String type)
	{
		return type.equals("class");
	}
}