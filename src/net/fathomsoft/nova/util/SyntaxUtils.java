package net.fathomsoft.nova.util;

import java.util.regex.Matcher;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.error.UnimplementedOperationException;
import net.fathomsoft.nova.tree.Accessible;
import net.fathomsoft.nova.tree.BodyMethodDeclaration;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Closure;
import net.fathomsoft.nova.tree.FileDeclaration;
import net.fathomsoft.nova.tree.GenericCompatible;
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
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.UnaryOperation;
import net.fathomsoft.nova.tree.Value;
import net.fathomsoft.nova.tree.generics.GenericArgument;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;

/**
 * Class used for getting information about the Syntax of Nova.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 15, 2014 at 7:55:00 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
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
		if (required == null || given == null)
		{
			return false;
		}
		
		int rank1 = getPrimitiveRank(required);
		int rank2 = getPrimitiveRank(given);
		
		if (rank1 <= 0 || rank2 <= 0)
		{
			return false;
		}
		
		return rank1 >= rank2;
	}
	
	/**
	 * Get whether the specified type is primitive.<br>
	 * <br>
	 * Primitive types include:
	 * <ul>
	 * 	<li>Int</li>
	 * 	<li>Char</li>
	 * 	<li>Long</li>
	 * 	<li>Bool</li>
	 * 	<li>Short</li>
	 * 	<li>Float</li>
	 * 	<li>Double</li>
	 * </ul>
	 * 
	 * @param type The type to check.
	 * @return Whether or not the type is primitive.
	 */
	public static boolean isPrimitiveType(String type)
	{
		return type == null || type.equals("Int") || type.equals("Char") || type.equals("Long") || type.equals("Bool") || type.equals("Short") || type.equals("Float") || type.equals("Double") || type.equals("Byte");
	}
	
	public static String getPrimitiveExternalType(String type)
	{
		if (type == null)
		{
			return type;
		}
		
		switch (type)
		{
			case "Int":
				return "int";
			case "Char":
				return "char";
			case "Long":
				return "long";
			case "Float":
				return "float";
			case "Double":
				return "double";
			case "Bool":
				return "char";
			case "Short":
				return "short";
			case "Byte":
				return "char";
			default:
				return type;
		}
	}
	
	/**
	 * Get whether the specified type is a C primitive type.<br>
	 * <br>
	 * Primitive types include:
	 * <ul>
	 * 	<li>int</li>
	 * 	<li>char</li>
	 * 	<li>long</li>
	 * 	<li>float</li>
	 * 	<li>double</li>
	 * </ul>
	 * 
	 * @param type The type to check.
	 * @return Whether or not the type is primitive.
	 */
	public static boolean isExternalPrimitiveType(String type)
	{
		return getPrimitiveNovaType(type) != type;
	}
	
	public static String getPrimitiveNovaType(String type)
	{
		if (type == null)
		{
			return type;
		}
		
		switch (type)
		{
			case "int":
				return "Int";
			case "char":
				return "Char";
			case "long":
				return "Long";
			case "float":
				return "Float";
			case "double":
				return "Double";
			default:
				return type;
		}
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
	 * Check to see if the given char exists within the
	 * base scope of the given haystack String. The base scope means
	 * outside of any quotes, parenthesis, and/or brackets.
	 * 
	 * @param haystack The String to find the character within.
	 * @param needle The character to search for in the String.
	 * @return Whether or not the char exists within the base scope.
	 */
	public static boolean containsCharInBaseScope(String haystack, char needle)
	{
		return findCharInBaseScope(haystack, needle) >= 0;
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
		return findStringInBaseScope(haystack, StringUtils.toString(needles), start);
	}
	
	/**
	 * Find the next available instance of the given String in the
	 * base scope of the given haystack String. The base scope means
	 * outside of any quotes, parenthesis, and/or brackets.
	 * 
	 * @param haystack The String to find the character within.
	 * @param needle The String to search for in the String.
	 * @return The index to search for in the String. If the String is
	 * 		not found, then -1 is returned instead.
	 */
	public static int findStringInBaseScope(String haystack, String needle)
	{
		return findStringInBaseScope(haystack, needle, 0);
	}
	
	/**
	 * Find the next available instance of the given String in the
	 * base scope of the given haystack String. The base scope means
	 * outside of any quotes, parenthesis, and/or brackets.
	 * 
	 * @param haystack The String to find the character within.
	 * @param needle The String to search for in the String.
	 * @param start The index to start the search at.
	 * @return The index to search for in the String. If the String is
	 * 		not found, then -1 is returned instead.
	 */
	public static int findStringInBaseScope(String haystack, String needle, int start)
	{
		return findStringInBaseScope(haystack, new String[] { needle }, start);
	}
	
	/**
	 * Find the next available instance of the given String in the
	 * base scope of the given haystack String. The base scope means
	 * outside of any quotes, parenthesis, and/or brackets.
	 * 
	 * @param haystack The String to find the character within.
	 * @param needles The String to search for in the String.
	 * @param start The index to start the search at.
	 * @return The index to search for in the String. If the String is
	 * 		not found, then -1 is returned instead.
	 */
	public static int findStringInBaseScope(String haystack, String needles[], int start)
	{
		while (start < haystack.length())
		{
			char c = haystack.charAt(start);
			
			if (StringUtils.containsString(haystack, needles, start))
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
			Value value    = (Value)node;
			Value returned = value.getReturnedNode();
			
			return returned.getType().equals("String");
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
			return "Bool";
		}
		else if (isCharLiteral(literal))
		{
			return "Char";
		}
		else if (isStringLiteral(literal))
		{
			return "String";
		}
		else if (isNumber(literal))
		{
			if (isInteger(literal))
			{
				return "Int";
			}
			else if (isDouble(literal))
			{
				return "Double";
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
		return value.length() > 0 && (isInteger(value) || isDouble(value));
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
	 * Get whether or not the given String is a valid variable assignment.
	 * Assignments contain an identifier or array access on the left
	 * hand side of the assignment, and anything that returns a value on
	 * the right hand side.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * variable_NAME1 = (calculateSize() + 5) / array[index]</pre></blockquote>
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
		
		int binary = StringUtils.findStrings(statement, Operator.LOGICAL_OPERATORS, index - 1).getStart();
		
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
	 * Get whether or not the given statement is a valid instantiation method call.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * new ListNode&lt;E&gt;()
	 * new Object()</pre></blockquote>
	 * The "ListNode&lt;E&gt;()" is a valid instantiation method call, as
	 * well as "Object()".
	 * 
	 * @param statement The statement to test.
	 * @return Whether or not the given statement is a valid instantiation call.
	 */
	public static boolean isInstantiationCall(String statement)
	{
		Bounds bounds = SyntaxUtils.findParenthesesBounds(null, statement);
		
		statement = bounds.trimString(statement);
		bounds    = StringUtils.findNextWordBounds(statement);
		statement = bounds.trimString(statement);
		bounds    = StringUtils.findContentBoundsWithin(statement, VariableDeclaration.GENERIC_START, VariableDeclaration.GENERIC_END, 0, true);
		statement = bounds.trimString(statement);
		statement = statement.trim();
		
		return SyntaxUtils.isValidIdentifier(statement);
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
		int end   = StringUtils.findEndingMatch(statement, start, '(', ')') + 1;
		
		if (end <= 0)
		{
			if (parent == null)
			{
				return Bounds.EMPTY;
			}
			
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
		if (methodDeclaration.getName() != null && methodDeclaration.getName().equals("main") && methodDeclaration.isStatic() && methodDeclaration.getType() == null && methodDeclaration.getVisibility() == FieldDeclaration.PUBLIC)
		{
			ParameterList<Parameter> params = methodDeclaration.getParameterList();
			
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
		return StringUtils.startsWithWord(statement, Instantiation.IDENTIFIER);
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
	public static void validateMethodAccess(Accessible accessor, MethodDeclaration accessed, Node parent)
	{
		if (!accessed.isExternal() && !accessed.isStatic())
		{
			if (accessor instanceof ClassDeclaration)
			{
				SyntaxMessage.error("Cannot call a non-static method from a static context", parent);
			}
		}
		else if (!isAccessibleFrom(((Value)accessor).getTypeClass(), accessed))
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
	public static boolean isVisible(ClassDeclaration accessingClass, InstanceDeclaration declaration)
	{
		if (declaration.getVisibility() == InstanceDeclaration.PRIVATE)
		{
			ClassDeclaration clazz2 = declaration.getParentClass();
			
			if (accessingClass.isAncestorOf(clazz2, true) || clazz2.isAncestorOf(accessingClass))
			{
				return true;
			}
			
			return false;
		}
		
		return true;
	}
	
	public static Identifier generatePrimitiveFacade(MethodCall call)
	{
		Accessible accessing = call.getAccessingNode();
		
		Accessible id = accessing.getRootAccessNode();
		
		String input = ((Value)accessing).getTypeClassName() + "." + call.getName() + "(" + id.generateNovaInputUntil(accessing);
		
		String params = call.getArgumentList().generateNovaInput().toString();
		
		if (params.length() > 0)
		{
			input += ". " + params;
		}
		
		input += ")";
		
		Identifier output = (Identifier)SyntaxTree.decodeScopeContents(id.getParent(), input, call.getLocationIn(), true);
		
		return output;
	}
	
	public static Identifier replaceWithPrimitiveFacade(MethodCall call)
	{
		Accessible accessing = call.getAccessingNode().getRootAccessNode();
		
		Identifier output = generatePrimitiveFacade(call);
		
		accessing.getParent().replace((Node)accessing, output);
		
		return output;
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
			String className = returned.getType();
			
			String instantiation = "new " + className + '(' + primitive.generateNovaInput() + ')';
			
			node = Instantiation.decodeStatement(primitive.getParent(), instantiation, primitive.getLocationIn(), true, false);
		}
		
		return node;
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
	public static Instantiation autoboxPrimitiveOnly(Value primitive)
	{
		Instantiation node = null;
		
		if (primitive.isPrimitiveType())
		{
			String className = primitive.getType();
			
			String instantiation = "new " + className + '(' + primitive.generateNovaInput(false) + ')';
			
			node = Instantiation.decodeStatement(primitive.getParent(), instantiation, primitive.getLocationIn(), true, false);
		}
		
		return node;
	}
	
	public static Instantiation replaceWithAutoboxPrimitive(Identifier primitive)
	{
		Instantiation autobox = autoboxPrimitiveOnly(primitive);
		
		autobox.setAccessedNode(primitive.getAccessedNode());
		
		primitive.getParent().replace(primitive, autobox);
		
		return autobox;
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
		
		value = getValidValue(value);
		
		return checkGenericType(value, type) || getValidType(value, type) != null;
	}
	
	private static boolean checkGenericType(Value value, String type)
	{
		return value.getParentClass(true).containsGenericParameter(type);
	}
	
	private static Value getValidValue(Value value)
	{
		if (value instanceof Return)
		{
			value = value.getParentMethod();
		}
		else if (value instanceof MethodCall)
		{
			MethodCall call = (MethodCall)value;
			
			value = call.getMethodDeclaration();
		}
		
		return value;
	}
	
	public static String getValidType(Value value, String type)
	{
		Value original = value;
		
		value = getValidValue(value);
		
		if (value.isWithinExternalContext() || value instanceof Literal ||
				value instanceof Operator || value instanceof ClassDeclaration)
		{
			return type;
		}
		else if (value instanceof MethodDeclaration)
		{
			MethodDeclaration methodDeclaration = (MethodDeclaration)value;
			
			if (methodDeclaration.isExternalType())
			{
				return type;
			}
		}
		else if (value instanceof Variable)
		{
			Variable var = (Variable)value;
			
			if (var.getDeclaration().isExternal())
			{
				return type;
			}
		}
		
		// TODO: Check this after generic declarations??
		ClassDeclaration clazz = value.getParentClass();
		
		while (clazz != null)
		{
			if (clazz.getName().equals(type))
			{
				return type;
			}
			
			clazz = clazz.getParentClass();
		}
		
		if (checkGenericType(value, type))
		{
			Variable genericCheck = null;
			
			if (original != value && original instanceof Identifier)
			{
				Identifier id = (Identifier)original;
				
				genericCheck = (Variable)id.getAccessingNode();
				
				if (genericCheck.doesUseGenericTypes())
				{
					String genericName = value.getType();
					
					GenericArgument generic = getCorrespondingGenericType(genericCheck.getTypeClass(), genericCheck.getDeclaration(), genericName);
	
					return generic.getType();
				}
			}
			
			return value.getParentClass().getGenericParameter(type).getDefaultType();
		}
		
		String location = value.getFileDeclaration().getImportList().getAbsoluteClassLocation(type);
		
		clazz = value.getProgram().getClassDeclaration(location);
		
		if (clazz != null)
		{
			if (SyntaxUtils.validateImported(value, location))
			{
				return type;
			}
			
			return null;
		}
		
		if (value.getParentClass().containsExternalType(type))
		{
			return type;
		}
		
		return null;
	}
	
	public static GenericArgument getCorrespondingGenericType(ClassDeclaration genericDeclaration, GenericCompatible genericUse, String genericName)
	{
		int index = genericDeclaration.getGenericArgumentIndex(genericName);
		
		if (index < 0)
		{
			SyntaxMessage.error("Could not find the corresponding generic for generic type '" + genericName + "'", (Node)genericUse);
		}
		
		return genericUse.getGenericArgument(index);
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
	 * Check to see if the given clazz is an instanceof any of the
	 * classes in the given type array.
	 * 
	 * @param types The types to check against.
	 * @param clazz The type to check for.
	 * @return Whether or not the given clazz is an instanceof any of
	 * 		the classes in the given array.
	 */
	public static boolean checkTypes(Class<?> types[], Class<?> clazz)
	{
		for (Class<?> type : types)
		{
			if (type.isAssignableFrom(clazz))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static ClassDeclaration getParameterGenericReturnType(Parameter required, Value given)
	{
		GenericCompatible gen = null;
		
		MethodCall call = (MethodCall)given.getAncestorOfType(MethodCall.class);
		
		if (call != null)
		{
			gen = call.getGenericCompatible();
		}
		else
		{
			gen = given.getParentClass();//given.getParentMethod();
		}
		
		String name = gen.getGenericArgumentType(required.getType(), given);
		
		return SyntaxUtils.getImportedClass(given.getFileDeclaration(), name);
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
		return isTypeCompatible(context.getClassDeclaration(given), context.getClassDeclaration(required));
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
		return isTypeCompatible(required, given, true);
	}
	
	public static boolean areTypesCompatible(Program context, String[] required, String[] given)
	{
		for (int i = 0; i < required.length && i < given.length; i++)
		{
			if (!isTypeCompatible(context, required[i], given[i]))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean areTypesCompatible(Value[] required, Value[] given)
	{
		return areTypesCompatible(required, given, true);
	}
	
	public static boolean areTypesCompatible(Value[] required, Value[] given, boolean searchGenerics)
	{
		for (int i = 0; i < required.length && i < given.length; i++)
		{
			if (!isTypeCompatible(required[i], given[i], searchGenerics))
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Check to see if the 'given' type is compatible with the
	 * required type.
	 * 
	 * @param required The type that the 'given' type is required
	 * 		to be compatible with.
	 * @param given The given type to check against the required type.
	 * @param searchGeneric Whether or not to search for the actual generic
	 * 		return type.
	 * @return Whether or not the two types are compatible.
	 */
	public static boolean isTypeCompatible(Value required, Value given, boolean searchGeneric)
	{
		return isTypeCompatible(required, given, searchGeneric, 0);
	}
	
	/**
	 * Check to see if the 'given' type is compatible with the
	 * required type.
	 * 
	 * @param required The type that the 'given' type is required
	 * 		to be compatible with.
	 * @param given The given type to check against the required type.
	 * @param searchGeneric Whether or not to search for the actual generic
	 * 		return type.
	 * @param arrayDifference The difference that is allowed between the two
	 * 		type's array dimensions. Difference =
	 * 		required.dimensions - given.dimensions.
	 * @return Whether or not the two types are compatible.
	 */
	public static boolean isTypeCompatible(Value required, Value given, boolean searchGeneric, int arrayDifference)
	{
		if (given instanceof Closure)
		{
			return true;
		}
		if (given.isWithinExternalContext() && given.getType() != null && getPrimitiveExternalType(given.getType()).equals(required.getType()))
		{
			return true;
		}
		else if (required.getArrayDimensions() - given.getArrayDimensions() - arrayDifference != 0)
		{
			Nova.debuggingBreakpoint(required.getTypeClassLocation() == null);
			if (required.getTypeClassLocation().equals(Nova.getClassLocation("Char")) && required.getArrayDimensions() == 1 && given.getTypeClassLocation() != null && given.getTypeClassLocation().equals(Nova.getClassLocation("String")))
			{
				return true;
			}
			
			return false;
		}
		
		if (searchGeneric && required.isGenericType())
		{
			if (!(required instanceof Parameter))
			{
				throw new UnimplementedOperationException("The validation of generic type " + required.getClass().getName() + " is not implemented.");
			}
			
			Parameter param = (Parameter)required;
			
			Value value = getParameterGenericReturnType(param, given);
			
			if (!Literal.isNullLiteral(given) && !isTypeCompatible(value, given, false))
			{
				SyntaxMessage.error("Incorrect type '" + given.getType() + "' given for required generic type of '" + value.getType() + "' type", given);
				
				return false;
			}
			
			return true;
		}
		if (given.isExternalType() ^ required.isExternalType())
		{
			return false;
		}
		else if (given.isExternalType() && given.getType().equals(required.getType()))
		{
			return true;
		}
		else if (/*given.isPrimitiveType() ^ required.isPrimitiveType() == false && */given.getTypeClass() != null && given.getTypeClass().isOfType(required.getTypeClass()))
		{
			return true;
		}
		else if (arePrimitiveTypesCompatible(required.getTypeClassName(), given.getTypeClassName()))
		{
			return true;
		}
		
		if (required.getTypeClassLocation() == null || given.getTypeClassLocation() == null)
		{
			return false;
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
			ClassDeclaration c = node.getParentClass(true);
			
			while (c != null)
			{
				if (c.getClassLocation().equals(clazz))
				{
					return true;
				}
				
				c = c.getParentClass();
			}
			
			if (!isImported(node.getFileDeclaration(), clazz))
			{
				throwImportException(node, clazz, location);
			}
			
			return true;
		}
		
		return false;
	}
	
	public static void throwImportException(Node parent, String type, Location location)
	{
		SyntaxMessage.error("Type '" + type + "' is not imported", parent, location);
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
	
	public static boolean areSameTypes(Value types1[], Value types2[])
	{
		if (types1.length != types2.length)
		{
			return false;
		}
		
		for (int i = 0; i < types1.length; i++)
		{
			Value  value1 = types1[i];
			Value  value2 = types2[i];
			
			String type1  = types1[i].getType();
			String type2  = types2[i].getType();
			
			if (!type1.equals(type2) || value1.getArrayDimensions() != value2.getArrayDimensions())
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isAbsoluteClassLocation(String location)
	{
		if (location == null)
		{
			return false;
		}
		
		return location.contains("/");
	}
	
	public static String getClassParentLocation(String classLocation)
	{
		if (classLocation == null)
		{
			return null;
		}
		
		int lastIndex = classLocation.lastIndexOf('/');
		
		return classLocation.substring(0, lastIndex);
	}
	
	public static String getClassName(String classLocation)
	{
		if (classLocation == null)
		{
			return null;
		}
		
		int lastIndex = classLocation.lastIndexOf('/') + 1;
		
		return classLocation.substring(lastIndex);
	}
	
	public static ClassDeclaration getImportedClass(FileDeclaration file, String className)
	{
		String location = file.getImportList().getAbsoluteClassLocation(className);
		
		return file.getProgram().getClassDeclaration(location);
	}
	
	public static Bounds findValueBounds(String source, int start)
	{
		Bounds bounds = findIdentifierBounds(source, start, true, true);
		
		if (bounds != null)
		{
			return bounds;
		}
		
		char c = source.charAt(start);
		
		switch (c)
		{
			case '\'':
			case '"':
				bounds = new Bounds(start, start);
				bounds.setEnd(StringUtils.findEndingChar(source, c, start, 1) + 1);
				return bounds;
			case '-':
				bounds = new Bounds(start, start);
				bounds.setEnd(StringUtils.findNextWhitespaceIndex(source, start + 1));
				return bounds;
			default:
				return Bounds.EMPTY;
		}
	}
	
	public static Bounds findIdentifierBounds(String source, int start)
	{
		return findIdentifierBounds(source, start, false, false);
	}
	
	public static Bounds findIdentifierBounds(String source, int start, boolean includeGroupings, boolean includeUnary)
	{
		start = StringUtils.findNextNonWhitespaceIndex(source, start);
		
		if (!includeGroupings)
		{
			start = StringUtils.findNextLetterIndex(source, start, 1);
		}
		else if (StringUtils.findEndingMatch(source, start, source.charAt(start)) > 0)
		{
			return checkIdentifierUnary(source, start, StringUtils.findEndingMatch(source, start, source.charAt(start)) + 1, includeUnary);
		}
		
		int current = start;
		
		while (true)
		{
			Bounds word = StringUtils.findNextWordBounds(source, current);
			
			if (word.isValid())
			{
				int prev = word.getEnd();
				current  = StringUtils.findNextNonWhitespaceIndex(source, word.getEnd());
				int temp = 0;
				
				if (current >= 0)
				{
					temp = StringUtils.findEndingMatch(source, current, source.charAt(current)) + 1;
					
					if (temp > 0)
					{
						current = temp;
					}
				}
				if (temp <= 0)
				{
					current = prev;
				}
				
				int next = StringUtils.findNextNonWhitespaceIndex(source, current);
				
				if (current < 0 || next < 0)
				{
					return checkIdentifierUnary(source, start, word.getEnd(), includeUnary);
				}
				else if (source.charAt(next) != '.')
				{
					return checkIdentifierUnary(source, start, current, includeUnary);
				}
				
				current = StringUtils.findNextNonWhitespaceIndex(source, next + 1);
			}
			else
			{
				if (current <= start)
				{
					return Bounds.EMPTY;
				}
				
				return checkIdentifierUnary(source, start, current, includeUnary);
			}
		}
	}
	
	private static Bounds checkIdentifierUnary(String source, int start, int end, boolean includeUnary)
	{
		if (includeUnary)
		{
			String symbols = StringUtils.findGroupedSymbols(source, end);
			
			if (symbols.length() > 0 && UnaryOperation.isCompatibleOnRight(symbols))
			{
				end = StringUtils.findNextNonWhitespaceIndex(source, end) + symbols.length();
			}
		}
		
		return new Bounds(start, end);
	}
}