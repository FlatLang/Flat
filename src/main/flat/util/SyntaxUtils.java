package flat.util;

import flat.Flat;
import flat.error.SyntaxMessage;
import flat.error.UnimplementedOperationException;
import flat.tree.*;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.lambda.LambdaMethodDeclaration;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.ObjectReference;
import flat.tree.variables.Variable;
import flat.tree.variables.VariableDeclaration;

import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static flat.tree.Value.POINTER;

/**
 * Class used for getting information about the Syntax of Flat.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 15, 2014 at 7:55:00 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class SyntaxUtils
{
	private static final int CHAR = 1, BYTE = 1, SHORT = 2, INT = 3, LONG = 4, FLOAT = 5, DOUBLE = 6, NUMBER = 6;

	public static final char[] WHITESPACE = new char[] {' ', '\t', '\n', '\r'};

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
		else if (primitiveType.equals("Number"))
		{
			return NUMBER;
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
	 * primitive type.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>Int abc = 'a'</pre></blockquote>
	 * In the previous code sample, Int is the required type and Char is
	 * the given type.
	 * 
	 * @param required The required primitive type.
	 * @param given The given primitive type to check against the required
	 * 		primitive type.
	 * @return Whether or not the two primitive types are compatible.
	 */
	public static boolean arePrimitiveTypesCompatible(String required, String given)
	{
		return getPrimitiveDistance(required, given) >= 0;
	}
	
	public static boolean arePrimitiveTypesCompatibleGeneral(String a, String b)
	{
		return Math.abs(getPrimitiveDistance(a, b)) >= 0;
	}
	
	public static int getPrimitiveDistance(String required, String given)
	{
		if (required == null || given == null)
		{
			return -1;
		}
		
		int rank1 = getPrimitiveRank(required);
		int rank2 = getPrimitiveRank(given);
		
		if (rank1 <= 0 || rank2 <= 0)
		{
			return -1;
		}
		
		return rank1 - rank2;
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
				return "long_long";
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
	
	public static String getPrimitiveDefaultValue(String type)
	{
		if (type == null)
		{
			return null;
		}
		
		switch (type)
		{
			case "Int":
				return "0";
			case "Char":
				return "'\0'";
			case "Long":
				return "0";
			case "Float":
				return "0";
			case "Double":
				return "0";
			case "Bool":
				return "false";
			case "Short":
				return "0";
			case "Byte":
				return "0";
			default:
				return null;
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
		return getPrimitiveFlatType(type) != type;
	}
	
	public static String getPrimitiveFlatType(String type)
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
	
	public static int findStatementEnd(String str, int index)
	{
		int start = index;
		int lastIndex = start;
		boolean ending = false;
		
		while (index < str.length() && index >= start)
		{
			lastIndex = index;
			
			char c = str.charAt(index);
			
			if (c == '(')
			{
				index = StringUtils.findEndingMatch(str, index, '(', ')') + 1;
				
				ending = true;
			}
			else if (c == '[')
			{
				index = StringUtils.findEndingMatch(str, index, '[', ']') + 1;
				
				ending = true;
			}
			else if (c == '"')
			{
				index = StringUtils.findEndingQuote(str, index) + 1;
				
				ending = true;
			}
			else
			{
				if (StringUtils.isSymbol(c) || StringUtils.isWhitespace(c))
				{
					ending = true;
				}
				
				if (ending)
				{
					if (StringUtils.isWhitespace(c))
					{
						int i = StringUtils.findNextWhitespaceIndex(str, index);
						
						c = str.charAt(i);
						
						if (c == '\0' || c != '.')
						{
							return index;
						}
						else
						{
							index = i + 1;
						}
					}
					else if (c != '.')
					{
						return index;
					}
					
					ending = false;
				}
				
				index++;
			}
		}
		
		return index >= start ? index : lastIndex;
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
	 * Find the next available chain operator index within the given String.
	 *
	 * @param str The String to find the chain operator within.
	 * @return The index of the chain operator. If a chain operator is not
	 * 		found, then -1 is returned instead.
	 */
	public static int findChainOperator(String str)
	{
		return findCharInBaseScope(str, ':', 0);
	}

	/**
	 * Find the next available chain operator index within the given String.
	 *
	 * @param str The String to find the chain operator within.
	 * @param start The index to start the search at.
	 * @return The index of the chain operator. If a chain operator is not
	 * 		found, then -1 is returned instead.
	 */
	public static int findChainOperator(String str, int start)
	{
		return findCharInBaseScope(str, ':', start);
	}

	/**
	 * Find the next available dot or chain operator index within the given String.
	 *
	 * @param str The String to find the dot or chain operator within.
	 * @return The index of the dot or chain operator. If a dot or chain operator is not
	 * 		found, then -1 is returned instead.
	 */
	public static int findDotOrChainOperator(String str)
	{
		return findDotOrChainOperator(str, 0);
	}

	/**
	 * Find the next available dot or chain operator index within the given String.
	 *
	 * @param str The String to find the dot or chain operator within.
	 * @param start The index to start the search at.
	 * @return The index of the dot or chain operator. If a dot or chain operator is not
	 * 		found, then -1 is returned instead.
	 */
	public static int findDotOrChainOperator(String str, int start)
	{
		return findCharInBaseScope(str, new char[] {'.', ':'}, start);
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
		return findCharInBaseScope(haystack, needle, start, 0);
	}
	
	public static int findCharInBaseScope(String haystack, char needle, int start, int searchGenerics)
	{
		return findCharInBaseScope(haystack, new char[] { needle }, start, searchGenerics);
	}
	
	/**
	 * Find the next available instance of the given character in the
	 * base scope of the given haystack String. The base scope means
	 * outside of any quotes, parenthesis, and/or brackets.
	 *
	 * @param haystack The String to find the character within.
	 * @param needles The characters to search for in the String.
	 * @return The index of the character. If the character is not
	 * 		found, then -1 is returned instead.
	 */
	public static int findCharInBaseScope(String haystack, char[] needles)
	{
		return findCharInBaseScope(haystack, needles, 0);
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
		return findCharInBaseScope(haystack, needles, start, 0);
	}
	
	public static int findCharInBaseScope(String haystack, char needles[], int start, int searchGenerics)
	{
		return findStringInBaseScope(haystack, StringUtils.toString(needles), start, searchGenerics);
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
		return findStringInBaseScope(haystack, needles, start, 0);
	}
	
	public static int findStringInBaseScope(String haystack, String needles[], int start, int searchGenerics)
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
				
				if (start <= 0)
				{
					return -1;
				}
			}
			else if (c == '\'')
			{
				start = StringUtils.findEndingChar(haystack, c, start, 1) + 1;
				
				if (start <= 0)
				{
					return -1;
				}
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
//			else if (c == '{')
//			{
//				start = StringUtils.findEndingMatch(haystack, start, '{', '}') + 1;
//				
//				if (start <= 0)
//				{
//					return -1;
//				}
//			}
			else if (searchGenerics != 0 && c == '<')
			{
				if (searchGenerics == 1)
				{
					start = StringUtils.findEndingMatch(haystack, start, '<', '>') + 1;
					
					if (start <= 0)
					{
						return -1;
					}
				}
				else if (searchGenerics == 2)
				{
					int i = StringUtils.findEndingMatch(haystack, start, '<', '>') + 1;
					
					if (i > 0)
					{
						char e = i < haystack.length() ? StringUtils.findNextNonWhitespaceChar(haystack, i) : 0;
						
						if (i >= haystack.length() || e == '(' || e == ',')
						{
							start = i;
						}
						else
						{
							start++;
						}
					}
					else
					{
						start++;
					}
				} else {
					throw new RuntimeException("Invalid searchGenerics value of " + searchGenerics);
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
	
	public static int findStringOutsideOfQuotes(String source, String search, int index)
	{
		while (index < source.length())
		{
			char c = source.charAt(index);
			
			for (int i = 0; i < search.length(); i++)
			{
				if (source.charAt(i + index) != search.charAt(i))
				{
					break;
				}
				if (i == search.length() - 1)
				{
					return index;
				}
			}
			
			if (c == '"' || c == '\'')
			{
				index = StringUtils.findEndingChar(source, c, index, 1);
			}
			
			index++;
		}
		
		return -1;
	}
	
	public static int findEndingBrace(String source, int index)
	{
		int scope = 1;
		
		while (index < source.length())
		{
			char c = source.charAt(index);
			
			if (c == '{')
			{
				scope++;
			}
			else if (c == '}')
			{
				scope--;
			}
			else if (c == '"' || c == '\'')
			{
				index = StringUtils.findEndingChar(source, c, index, 1);
			}
			
			if (scope == 0)
			{
				return index;
			}
			
			index++;
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
		if (node instanceof Literal && !((Literal)node).doesAccess())
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
	public static String getLiteralTypeName(Node node, String literal)
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
			literal = removeUnderscores(literal);
			
			if (isInteger(literal))
			{
				long l = Long.parseLong(literal);
				
				if (l <= Byte.MAX_VALUE && l >= Byte.MIN_VALUE)
				{
					return "Byte";
				}
				else if (l <= Short.MAX_VALUE && l >= Short.MIN_VALUE)
				{
					return "Short";
				}
				else if (l <= Integer.MAX_VALUE && l >= Integer.MIN_VALUE)
				{
					return "Int";
				}
				else if (l <= Long.MAX_VALUE && l >= Long.MIN_VALUE)
				{
					return "Long";
				}
				
				SyntaxMessage.error("Number literal out of primitive number range of [" + Long.MIN_VALUE + ", " + Long.MAX_VALUE + "]", node);
				
				return null;
			}
			else if (isDouble(literal))
			{
				double l = Double.parseDouble(literal);
				
				if (l <= Float.MAX_VALUE && l >= -Float.MAX_VALUE)
				{
					return "Float";
				}
				else if (l <= Double.MAX_VALUE && l >= -Double.MAX_VALUE)
				{
					return "Double";
				}
				
				SyntaxMessage.error("Decimal literal out of primitive decimal range of [" + Double.MIN_VALUE + ", " + Double.MAX_VALUE + "]", node);
				
				return null;
			}
			else if (isHexadecimal(literal))
			{
				return "Long";
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
	public static boolean isLiteral(Node node, String value)
	{
		return getLiteralTypeName(node, value) != null;//isCharLiteral(value) || isStringLiteral(value) || isNumber(value);
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
		return value.length() > 0 && (isInteger(value) || isDouble(value) || isHexadecimal(value));
	}
	
	public static String removeUnderscores(String value)
	{
		if (value.length() < 3)
		{
			return value;
		}
		
		boolean negative = value.charAt(0) == '-';
		
		if (negative && value.charAt(1) == '_' || !negative && value.charAt(0) == '_')
		{
			return value;
		}
		
		int decimalIndex = value.indexOf('.');
		String decimal = decimalIndex >= 0 ? value.substring(decimalIndex) : "";
		
		StringBuilder output = new StringBuilder(value.substring(negative ? 1 : 0, decimalIndex >= 0 ? decimalIndex : value.length()));
		
		for (int i = 4; i < output.length(); i += 3)
		{
			if (output.charAt(output.length() - i) != '_')
			{
				return value;
			}
			
			output.deleteCharAt(output.length() - i);
		}
		
		return (negative ? "-" : "") + output.toString() + decimal;
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
		value = removeUnderscores(value);
		
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
	
	public static boolean isHexadecimal(String value)
	{
		if (value.startsWith("-"))
		{
			value = value.substring(1);
		}
		
		value = value.toLowerCase();
		
		if (value.length() <= 2 || !value.startsWith("0x"))
		{
			return false;
		}
		
		for (int i = 2; i < value.length(); i++)
		{
			char c = value.charAt(i);
			
			if (!(c >= '0' && c <= '9' || c >= 'a' && c <= 'f'))
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
		value = removeUnderscores(value);
		
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
		
		if (binary - 1 != index && binary != index && binary != index - 1)
		{
			binary = StringUtils.findStrings(statement, new String[] { "=>" }, index - 1).getStart();
			
			return binary - 1 != index && binary != index && binary != index - 1;
		}
		
		return false;
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
//		Bounds bounds = Regex.boundsOf(statement, Patterns.PRE_METHOD_CALL);
//		
//		if (bounds.getStart() == 0)
//		{
//			return StringUtils.findEndingMatch(statement, bounds.getEnd() - 1, '(', ')') == statement.length() - 1;
//		}
		
		boolean checkedIdentifier = false;
		int index = statement.indexOf('<');
		int paren = statement.indexOf('(');
		
		index = paren >= 0 && paren < index ? -1 : index;
		
		if (index == 0)
		{
			return false;
		}
		else if (index > 0)
		{
			if (!SyntaxUtils.isValidIdentifier(statement.substring(0, index).trim()))
			{
				return false;
			}
			
			checkedIdentifier = true;
			
			index = StringUtils.findEndingMatch(statement, index, '<', '>');
			index = index > 0 ? index + 1 : index;
		}
		else
		{
			index = 0;
		}
		
		if (index < 0)
		{
			return false;
		}
		else
		{
			index = statement.indexOf('(', index);
			
			if (index < 0 || !checkedIdentifier && !SyntaxUtils.isValidIdentifier(statement.substring(0, index).trim()))
			{
				return false;
			}
			
			index = StringUtils.findEndingMatch(statement, index, '(', ')');
			
			if (index != statement.length() - 1)
			{
				return false;
			}
		}
		
		return true;
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
		String withoutGenerics = statement;
		
		int start = statement.indexOf('<');
		
		if (start > 0)
		{
			int end = StringUtils.findEndingMatch(statement, start, '<', '>');
			
			if (end > start)
			{
				withoutGenerics = statement.substring(0, start) + statement.substring(end + 1);
			}
		}
		
		return Regex.matches(withoutGenerics, Patterns.ARRAY_INIT);
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
		int start = findCharInBaseScope(statement, '(');

		if (start < 0) {
			return Bounds.EMPTY;
		}

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
				
				if (param.getFlatType().equals("Array<String>"))
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
		if (declaration.getParentClass(true).isAncestorOf(accessedFrom, true))
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
		
		ClassDeclaration refClass = ((Value)accessor).getTypeClass();
		
		if (accessor instanceof ObjectReference)
		{
			if (accessor.toValue().getParentMethod() instanceof ExtensionMethodDeclaration)
			{
				refClass = accessor.toValue().getParentClass();
			}
		}
		
		if (!isAccessibleFrom(((Value)accessor).getParentClass(), accessed))
		{
			isAccessibleFrom(((Value)accessor).getParentClass(), accessed);
			SyntaxMessage.error("Method '" + accessed.getName() + "' of class '" + accessed.getParentClass().classInstanceDeclaration + "' is not visible from class '" + ((Value)accessor).getParentClass().getClassLocation() + "'", parent);
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
			int index = formatMatcher.start();
			char c = text.charAt(index);
			
			if ((c == '{' || c == '(') && (index == 0 || text.charAt(index - 1) != '\''))
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

				if (substring.trim().length() == 0) {
					while (builder.length() > 0 && builder.charAt(builder.length() - 1) != '\n') {
						builder.deleteCharAt(builder.length() - 1);
					}

					builder.append('\n').append(tabs);
				} else {
					builder.append(substring).append('\n').append(tabs);
				}

				lastIndex = formatMatcher.start() + 1;
				
				sameLine = false;
			}
		}
		
		builder.append(tabs).append(text.substring(lastIndex));
		
		return builder.toString();
	}
	
	/**
	 * Get whether or not the declaration is accessible from the
	 * given accessor context.
	 * 
	 * @param accessingClass The Identifier that is accessing the type.
	 * @param declaration The declaration of the type that is being
	 * 		accessed.
	 * @return Whether or not the declaration is accessible from the
	 * 		given accessor context.
	 */
	public static boolean isVisible(ClassDeclaration accessingClass, InstanceDeclaration declaration)
	{
		if (declaration.getVisibility() == InstanceDeclaration.PRIVATE)
		{
			ClassDeclaration clazz2 = declaration.getParentClass(true).getRootOverloadedClass();
			accessingClass = accessingClass.getRootOverloadedClass();

			while (accessingClass != null)
			{
				if (checkClassCommonality(accessingClass, clazz2))
				{
					return true;
				}
				
				accessingClass = accessingClass.encapsulatingClass;
			}
			
			return false;
		}
		
		return true;
	}

	private static boolean checkClassCommonality(ClassDeclaration class1, ClassDeclaration class2) {
		return class1.isAncestorOf(class2, true) || class2.isAncestorOf(class1, true) || class1.encapsulates(class2, true);
	}
	
	public static Identifier generatePrimitiveFacade(MethodCall call)
	{
		Accessible accessing = call.getAccessingNode();
		Accessible accessed = call.getAccessedNode();

		Accessible id = accessing.getRootAccessNode();
		
		String input = ((Value)accessing).getTypeClassName();

		if (accessed != null && accessed.isChainNavigation()) {
			input += ':';
		} else {
			input += '.';
		}

		input += call.getName() + "(" + id.generateFlatInputUntil(accessing);
		
		String params = call.getArgumentList().generateFlatInput().toString();
		
		if (params.length() > 0)
		{
			input += ". " + params;
		}
		
		input += ")";
		
		if (call.doesAccess())
		{
			if (call.getAccessedNode().isChainNavigation()) {
				input += ':';
			} else {
				input += '.';
			}

			input += call.getAccessedNode().generateFlatInput();
		}
		
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
	public static Value autoboxPrimitive(Value primitive)
	{
		if (!Flat.PRIMITIVE_OVERLOADS) {
			return primitive;
		}
		return autoboxPrimitive(primitive, primitive.getReturnedNode().getType());
	}
	
	public static Value autoboxPrimitive(Value primitive, String type)
	{
		Instantiation node = null;
		
		Value returned = primitive.getReturnedNode();
		
		if (returned.isPrimitiveType())
		{
			String className     = type;
			String defaultValue  = SyntaxUtils.getPrimitiveDefaultValue(className);
			String instantiation = className + '(' + defaultValue + ')';

			primitive.getFileDeclaration().addImport(returned.getTypeClassLocation());
			
			node = Instantiation.decodeStatement(primitive.getParent(), instantiation, primitive.getLocationIn(), true, false);
			
			MethodCall   call = (MethodCall)node.getIdentifier();
			ArgumentList list = call.getArgumentList();
			
			list.removeVisibleChild(0);
			list.addChild(primitive.clone(node, primitive.getLocationIn()));
		}
		
		return node;
	}
	
	public static Value unboxPrimitive(Value primitive)
	{
		return unboxPrimitive(primitive, primitive.getReturnedNode().getFlatType(primitive, false));
	}
	
	public static Value unboxPrimitive(Value primitive, String type)
	{
		return unboxPrimitive(primitive, type, true);
	}

	public static Value unboxPrimitive(Value primitive, String type, boolean replace)
	{
		if (primitive instanceof Cast) {
			if (replace) {
				primitive = Priority.generateFrom(primitive);
			} else {
				throw new UnimplementedOperationException("Not implemented yet");
			}
		}
		if (primitive instanceof Accessible)
		{
			Priority p = new Priority(primitive.getParent(), primitive.getLocationIn());

			Accessible value = SyntaxTree.decodeAccessible(primitive.getReturnedNode(), "value", Location.INVALID, true, false);

			Cast c = new Cast(p, primitive.getLocationIn());
			c.setTypeValue(type);
			c.setDataType(POINTER);

			c.addChild(primitive.clone(primitive.getParent(), primitive.getLocationIn()));
			p.addChild(c);
			
			if (value instanceof Identifier)
			{
				p.setAccessedNode((Identifier)value);
			}

			if (replace && primitive.getParent().containsChild(primitive, false))
			{
				primitive.replaceWith(p);
			}
			
			return p;//(Value)SyntaxTree.decodeIdentifierAccess(primitive.parent, primitive.generateFlatInput().toString() + ".value", Location.INVALID, true);
		}
		
		return primitive;
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
		
		if (primitive.isPrimitiveType() && primitive instanceof Accessible)
		{
			String className = primitive.getType();
			
			String instantiation = className + '(' + ((Accessible)primitive).getRootAccessNode().generateFlatInputUntil((Accessible)primitive) + ')';
			
			node = Instantiation.decodeStatement(primitive.getParent(), instantiation, primitive.getLocationIn(), true, false);
		}
		
		return node;
	}
	
	public static Value replaceWithAutoboxPrimitive(Value primitive) {
		if (!Flat.PRIMITIVE_OVERLOADS) {
			return primitive;
		}
		return replaceWithAutoboxPrimitive2(primitive);
	}

	public static Value replaceWithAutoboxPrimitive2(Value primitive) {
		Instantiation autobox = autoboxPrimitiveOnly(primitive);
		
		if (primitive instanceof Accessible && ((Accessible)primitive).getAccessedNode() != null)
		{
			autobox.setAccessedNode(((Accessible)primitive).getAccessedNode());
		}

		primitive.replaceWith(autobox);
		
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
		if (value.getParentMethod(true) != null && value.getParentMethod(true).containsGenericTypeParameter(type))
		{
			return true;
		}
		
		return value.getParentClass(true) == null ? true : value.getParentClass(true).containsGenericTypeParameter(type);
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
		
		if (type.indexOf('(') > 0)
		{
			return type;
		}
		
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
			if (clazz.getName() != null && clazz.getName().equals(type))
			{
				return type;
			}
			
			clazz = clazz.encapsulatingClass;
		}
		
		clazz = getImportedClass(value.getFileDeclaration(), type);
		
		if (clazz != null)
		{
			return type;
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
					
					GenericTypeArgument generic = genericCheck.getTypeClass().getGenericTypeParameter(genericName, value).getCorrespondingArgument(value);//getCorrespondingGenericType(genericCheck.getTypeClass(), genericCheck.getDeclaration(), genericName);
	
					return generic.getType();
				}
			}
			
			return type;//value.getParentClass().getGenericTypeParameter(type).getDefaultType();
		}
		
		String location = null;
		
		if (value.getParentClass() instanceof ExtensionDeclaration)
		{
			location = value.getFileDeclaration().getImportList().getAbsoluteClassLocation(type);
		}
		
		if (location == null)
		{
			location = value.getReferenceFile().getImportList().getAbsoluteClassLocation(type);
		}
		
		clazz = value.getProgram().getClassDeclaration(location);
		
		if (clazz != null)
		{
			if (true)//SyntaxUtils.validateImported(value, location))
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
		if (value1.isExternalType() && value2.isExternalType())
		{
			return value1.getType().equals(value2.getType());
		}
		else if (value1.isGenericType() && value2.isGenericType() && value1.getType().equals(value2.getType()))
		{
			return true;
		}
		
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
		if (value1 == null || value2 == null) {
			return null;
		}
		if (value1.isPrimitive() && value2.isPrimitive() && value1.getType() != null && value2.getType() != null)
		{
			String type = getHighestPrimitiveType(value1.getType(), value2.getType());
			
			return value1.getType().equals(type) ? value1.getTypeClass() : value2.getTypeClass();
		}
		
		final ClassDeclaration originalType1 = value1.getTypeClass();
		final ClassDeclaration originalType2 = value2.getTypeClass();

		ClassDeclaration type1 = originalType1;
		ClassDeclaration type2 = originalType2;
		
		if (type1 == null || type2 == null)
		{
			return null;
		}
		
		ClassDeclaration originalType = type2;
		
		while (type2 != null)
		{
			if (type1.isOfType(type2))
			{
				return type2;
			}
			
			type2 = type2.getExtendedClassDeclaration();
		}
		
		type2 = originalType;
		originalType = type1;

		while (type1 != null)
		{
			if (type2.isOfType(type1))
			{
				return type1;
			}
			
			type1 = type1.getExtendedClassDeclaration();
		}

		type1 = originalType;
		originalType = type2;

		Optional<Trait> t = Arrays.stream(type2.getImplementedInterfaces(true))
			.filter(originalType1::isOfType)
			.findFirst();

		if (t.isPresent()) {
			return t.get();
		}

		t = Arrays.stream(type1.getImplementedInterfaces(true))
			.filter(originalType2::isOfType)
			.findFirst();

		if (t.isPresent()) {
			return t.get();
		}
		
		return null;
	}
	
	public static Value getValueInCommon(Value value1, Value value2)
	{
		if (value1.isPrimitive() && value2.isPrimitive() && value1.getType() != null && value2.getType() != null)
		{
			String type = getHighestPrimitiveType(value1.getType(), value2.getType());
			
			return value1.getType().equals(type) ? value1 : value2;
		}
		
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
				IIdentifier value = new IIdentifier(value2.getParent(), value2.getLocationIn());
				value.setType(value1);
				value.setTypeValue(type2.getType());
				
				return value;
			}
			
			type2 = type2.getExtendedClassDeclaration();
		}
		
		type2 = type3;
		
		while (type1 != null)
		{
			if (type2.isOfType(type1))
			{
				IValue value = new IValue(value2.getParent(), value2.getLocationIn());
				value.setType(value1);
				value.setTypeValue(type1.getType());
				
				return value;
			}
			
			type1 = type1.getExtendedClassDeclaration();
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
			
			clazz2 = clazz2.getExtendedClassDeclaration();
		}
		
		clazz2 = clazz3;
		
		while (clazz1 != null)
		{
			if (clazz2.isOfType(clazz1))
			{
				return type1;
			}
			
			clazz1 = clazz1.getExtendedClassDeclaration();
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

	public static ClassDeclaration searchInnerClasses(Node node, ClassDeclaration c, String name) {
		ArrayList<ClassDeclaration> classes = c.getInnerClasses(false).filterVisibleListChildren(inner -> inner.getName().equals(name));

		if (classes.size() > 1) {
			SyntaxMessage.error("Ambiguous class name '" + name + "', cannot determine correct class to reference for value type.", node);
			return null;
		} else if (classes.size() == 0) {
			return c.getInnerClasses(false)
				.getChildStream()
				.map(inner -> (ClassDeclaration)inner)
				.map(inner -> searchInnerClasses(node, inner, name))
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);
		}

		return classes.get(0);
	}

	public static ClassDeclaration getClosestClass(FileDeclaration file, Node node, String name) {
		ClassDeclaration c = node.getParentClass(true);

		if (c.getName().equals(name)) {
			return c;
		}

		ClassDeclaration inner = searchInnerClasses(node, c, name);

		if (inner != null) {
			return inner;
		}

		ClassDeclaration current = c;

		while (current != null) {
			if (current.getName().equals(name)) {
				return current;
			}

			current = current.getParentClass(false);
		}

		java.util.List<ClassDeclaration> classes = Arrays.stream(file.getClassDeclarations())
			.filter(x -> x.getName().equals(name))
			.collect(Collectors.toList());

		if (classes.size() > 1) {
			SyntaxMessage.error("Ambiguous class name '" + name + "', cannot determine correct class to reference for value type.", node);
			return null;
		} else if (classes.size() == 0) {
			return null;
		}

		return classes.get(0);
	}
	
	public static String getTypeClassLocation(Node node, String type)
	{
		return getTypeClassLocation(node, type, true);
	}
	
	public static String getTypeClassLocation(Node node, String type, boolean checkCast)
	{
		ClassDeclaration c = getTypeClass(node, type, checkCast);

		if (c != null) {
			return c.getClassLocation();
		}

		return null;
	}

	public static ClassDeclaration getTypeClass(Node node, String type)
	{
		return getTypeClass(node, type, true);
	}

	public static ClassDeclaration getTypeClass(Node node, String type, boolean checkCast)
	{
		FileDeclaration file = node.getReferenceFile(checkCast);

		if (file != null)
		{
			ClassDeclaration closest = getClosestClass(file, node, type);

			if (closest != null) {
				return closest;
			}

			return Flat.instance.getTree().getRoot().getClassDeclaration(file.getImportList().getAbsoluteClassLocation(type));
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
		return isTypeCompatible(context.getClassDeclaration(given), context.getClassDeclaration(given), context.getClassDeclaration(required));
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
	public static boolean isTypeCompatible(GenericCompatible context, Value required, Value given)
	{
		return isTypeCompatible(context, required, given, true);
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
	
	public static boolean areTypesCompatible(GenericCompatible[] contexts, Value[] required, Value[] given)
	{
		return areTypesCompatible(contexts, required, given, true);
	}
	
	public static boolean areTypesCompatible(GenericCompatible[] contexts, Value[] required, Value[] given, boolean searchGenerics)
	{
		for (int i = 0; i < required.length && i < given.length; i++)
		{
			if (!isTypeCompatible(contexts == null ? null : (contexts.length > i ? contexts[i] : contexts[contexts.length - 1]), required[i], given[i], searchGenerics))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static String stripGenerics(String type)
	{
		if (type != null && type.indexOf('<') > 0)
		{
			return type.substring(0, type.indexOf('<')).trim();
		}
		
		return type;
	}
	
	public static String strictStripGenerics(String type)
	{
		if (type != null)
		{
			int start = findCharInBaseScope(type, '<');
			
			if (start > 0)
			{
				int end = start + 1;
				int stack = 0;
				
				while (end < type.length())
				{
					char c = type.charAt(end);
					
					if (StringUtils.isSymbol(c))
					{
						if (c == '<')
						{
							stack++;
						}
						else if (c == '>')
						{
							if (stack == 0)
							{
								break;
							}
							
							stack--;
						}
						else if (c != ',')
						{
							break;
						}
					}
					
					end++;
				}
				
				if (end < type.length() && type.charAt(end) == '>')
				{
					return strictStripGenerics(type.substring(0, start) + type.substring(end + 1));
				}
			}
		}
		
		return type;
	}

	public static class LiteralNameData {
		public String literalName;
		public String validName;

		public LiteralNameData(String literalName, String validName) {
			this.literalName = literalName;
			this.validName = validName;
		}
	}

	public static class StatementLiteralNameData {
		public String statement;
		public int index;
		public LiteralNameData literalNameData;

		public StatementLiteralNameData(String statement, int index, LiteralNameData literalNameData) {
			this.statement = statement;
			this.index = index;
			this.literalNameData = literalNameData;
		}
	}

	public static LiteralNameData getFirstLiteralNameData(String statement) {
		return getFirstLiteralNameData(statement, 0);
	}

	public static LiteralNameData getFirstLiteralNameData(String statement, int startIndex) {
		int startTick = SyntaxUtils.findCharInBaseScope(statement, '`', startIndex);
		String literalName;
		String validName;

		if (startTick >= 0) {
			int endTick = SyntaxUtils.findCharInBaseScope(statement, '`', startTick + 1);

			if (endTick > startTick) {
				literalName = statement.substring(startTick + 1, endTick);
				validName = SyntaxUtils.convertLiteralNameToValidName(literalName);

				return new LiteralNameData(literalName, validName);
			}
		}

		return null;
	}

	public static StatementLiteralNameData[] getStatementLiteralNameData(String statement) {
		ArrayList<StatementLiteralNameData> data = new ArrayList<>();

		int index = 0;

		while (index >= 0) {
			LiteralNameData literalData = getFirstLiteralNameData(statement, index);

			if (literalData == null) {
				break;
			}

			data.add(
				new StatementLiteralNameData(
					statement,
					index,
					literalData
				)
			);

			index = statement.indexOf('`' + literalData.literalName + '`', index) + literalData.literalName.length() + 2;
		}

		return data.toArray(new StatementLiteralNameData[0]);
	}

	public static String convertLiteralNameToValidName(String literalName) {
		String converted = literalName
			.replace(' ', '_')
			.replaceAll("[^\\w\\d]", "");

		if (isNumber(converted.substring(0, 1))) {
			converted = "func" + converted;
		}

		return converted;
	}

	public static class ValueDistance
	{
		public int genericDistance;
		public int a;
		public int b;
		public int exactTypeDistance;
		
		public ValueDistance(int a, int b)
		{
			this.genericDistance = 0;
			this.a = a;
			this.b = b;
		}
	}
	
	public static ValueDistance getParametersDistance(Value[] required, Value[] given)
	{
		return getParametersDistance(null, required, given);
	}
	
	public static ValueDistance getParametersDistance(Value context, Value[] required, Value[] given)
	{
		return getParametersDistance(context, required, given, false);
	}
	
	public static ValueDistance getParametersDistance(Value context, Value[] required, Value[] given, boolean defaultGeneric)
	{
		ValueDistance pair = new ValueDistance(0, 0);
		
		for (int i = 0; i < Math.min(required.length, given.length); i++)
		{
//			if (!required[i].isGenericType())
			{
				ClassDeclaration g = given[i].getFlatTypeClass(context, false, defaultGeneric);
				ClassDeclaration r = required[i].getFlatTypeClass(context, false, defaultGeneric);
				
				if (g != null && r != null)
				{
					getDistance(context, g, r, defaultGeneric, pair);
				}
				else if (given[i].isGenericType() != required[i].isGenericType())
				{
					if (given[i].isGenericType() && !given[i].getGenericReturnType().equals(required[i].getReturnedNode().getType()) ||
						required[i].isGenericType() && !required[i].getGenericReturnType().equals(given[i].getReturnedNode().getType())) {
						pair.genericDistance++;
					}
				}
			}
		}
		
		return pair;
	}
	
	public static ValueDistance getDistance(Value g, Value r)
	{
		return getDistance(null, g, r, false);
	}
	
	public static ValueDistance getDistance(Value context, Value g, Value r, boolean defaultGeneric)
	{
		return getDistance(context, g, r, defaultGeneric, new ValueDistance(0, 0));
	}
	
	public static ValueDistance getDistance(Value context, Value g, Value r, boolean defaultGeneric, ValueDistance pair)
	{
		if (g.isPrimitiveType() || r.isPrimitiveType())
		{
			if (g.isPrimitiveType() && r.isPrimitiveType())
			{
				int dist = getPrimitiveDistance(r.getType(), g.getType());
				
				pair.b += dist > 0 ? 1 : (dist < 0 ? -1 : 0);
				
				if (dist == 0 && !g.getType().equals(r.getType()))
				{
					pair.exactTypeDistance++;
				}
			}
			else
			{
				pair.a += 1000;
			}
		}
		else
		{
			ClassDeclaration gc = g.getFlatTypeClass(context, false, defaultGeneric);
			ClassDeclaration rc = r.getFlatTypeClass(context, false, defaultGeneric);
			
			if (gc != null && rc != null)
			{
				pair.a += gc.getDistanceFrom(rc);
				
				if (r.getGenericTypeArgumentList() != null)
				{
					GenericTypeArgumentList gargs = g.getGenericTypeArgumentList();
					GenericTypeArgumentList rargs = r.getGenericTypeArgumentList();
					
					for (int n = 0; n < Math.min(gargs.getNumVisibleChildren(), rargs.getNumVisibleChildren()); n++)
					{
						getDistance(context, gargs.getVisibleChild(n), rargs.getVisibleChild(n), defaultGeneric, pair);
					}
				}
			}
		}
		
		return pair;
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
	public static boolean isTypeCompatible(GenericCompatible context, Value required, Value given, boolean searchGeneric)
	{
		return isTypeCompatible(context, required, given, searchGeneric, 0);
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
	public static boolean isTypeCompatible(GenericCompatible context, Value required, Value given, boolean searchGeneric, int arrayDifference)
	{
		if (required == null ^ given == null)
		{
			return false;
		}
		else if (given != null && given.isFunctionType() && required.getTypeClass() != null && required.getTypeClass().getClassLocation().equals("flat/Object"))
		{
			return true;
		}
		if (required != null && (required instanceof ClosureDeclaration && !(given.isFunctionType() || given instanceof Closure || given instanceof ClosureDeclaration || given instanceof Variable && ((Variable)given).getDeclaration() instanceof ClosureVariableDeclaration))) {
			return false;
		}
		
		if (given instanceof FlatMethodDeclaration && ((FlatMethodDeclaration)given).shorthandAction != null || required instanceof FlatMethodDeclaration && ((FlatMethodDeclaration)required).shorthandAction != null)
		{
			return true;
		}
		
		if (given instanceof DefaultArgument)
		{
			return true;
		}
		
		if (context != null && given instanceof ClosureDeclaration == false && given.isGenericType() && context instanceof Constructor == false)
		{
			GenericTypeParameter param = given.getGenericTypeParameter();//((Value)context).getTypeClass().getGenericTypeParameter(given.getType(), given);
			
			Value value = param.getTypeValue(context);
			
			if (value != null)
			{
				Value newContext = (Value)((Accessible)context).getReferenceNode();
				
				if (newContext instanceof Variable == false)
				{
					newContext = null;
				}
				
				//SyntaxUtils.getImportedClass(given.getFileDeclaration(), arg.getType());
				
				if (isTypeCompatible(newContext, required, value, false))
				{
					return true;
				}
			}
		}

//		if (given instanceof Instantiation == false)
//		{
//			GenericTypeArgumentList requiredArgs = required.getGenericTypeArgumentList();
//			GenericTypeArgumentList givenArgs = given.getGenericTypeArgumentList();
//			
//			if (requiredArgs != null && givenArgs != null)
//			{
//				if (requiredArgs.getNumVisibleChildren() != givenArgs.getNumVisibleChildren())
//				{
//					return false;
//				}
//				else if (requiredArgs.getNumVisibleChildren() > 0)
//				{
//					for (int i = 0; i < requiredArgs.getNumVisibleChildren(); i++)
//					{
//						GenericTypeArgument requiredArg = requiredArgs.getVisibleChild(i);
//						GenericTypeArgument givenArg = givenArgs.getVisibleChild(i);
//						
//						/*if (requiredArg != required && givenArg != given && !isTypeCompatible(givenArg.getContext(), requiredArg, givenArg))
//						{
//							return false;
//						}*/
//					}
//				}
//			}
//		}
		
		if (given instanceof Closure || given instanceof ClosureVariable)
		{
			return true;
		}
		if (given.isWithinExternalContext() && given.getType() != null && getPrimitiveExternalType(given.getType()).equals(required.getType()))
		{
			return true;
		}
		else if (required.getArrayDimensions() - given.getArrayDimensions() - arrayDifference != 0)
		{
			if (required.isExternalType() || (required.getTypeClassLocation().equals("flat/primitive/number/Char") && required.getArrayDimensions() == 1 && given.getTypeClassLocation() != null && given.getTypeClassLocation().equals("flat/String")))
			{
				return true;
			}
			
			return false;
		}
		
		String genType1 = given.isGenericType() ? given.getGenericReturnType() : given.getType();
		String genType2 = required.isGenericType() ? required.getGenericReturnType() : required.getType();
		
		if (genType1 != null && genType1.equals(genType2) || genType1 == genType2)
		{
			return true;
		}
		
		VariableDeclaration givenDeclaration = given instanceof VariableDeclaration ? (VariableDeclaration)given : (given instanceof  Variable ? ((Variable)given).getDeclaration() : null);
		VariableDeclaration requiredDeclaration = required instanceof VariableDeclaration ? (VariableDeclaration)required : (required instanceof  Variable ? ((Variable)required).getDeclaration() : null);

		if (requiredDeclaration instanceof ClosureDeclaration && givenDeclaration instanceof ClosureDeclaration == false &&
			given.isFunctionType())
		{
			givenDeclaration = ((FunctionType)given.getTypeObject()).closure;
		}
		
		if (givenDeclaration instanceof ClosureDeclaration && requiredDeclaration instanceof ClosureDeclaration)
		{
			ClassDeclaration requiredClass = requiredDeclaration.getTypeClass();
			ClassDeclaration givenClass = givenDeclaration.getTypeClass();
			
			if (!((givenClass == null && requiredClass == null) || (requiredClass != null && givenClass != null && requiredClass.isAncestorOf(givenClass, true))))
			{
				return false;
			}
			
			ClosureDeclaration r = (ClosureDeclaration)requiredDeclaration;
			ClosureDeclaration g = (ClosureDeclaration)givenDeclaration;
			
			if (r.getParameterList().getNumParameters() != g.getParameterList().getNumParameters())
			{
				return false;
			}
			
			for (int i = 0; i < r.getParameterList().getNumParameters(); i++)
			{
				Value requiredParam = r.getParameterList().getParameter(i);
				Value givenParam = g.getParameterList().getParameter(i);
				
				if (givenParam.getTypeClass() == null || !givenParam.getTypeClass().isOfType(requiredParam.getTypeClass()))//!isTypeCompatible(/*givenParam.getContext()*/null, requiredParam, givenParam))
				{
					return false;
				}
			}
			
			return true;
		}
		
		if (searchGeneric && required.isGenericType())
		{
			if (Literal.isNullLiteral(given))
			{
				return true;
			}
			
			if (!(required instanceof Value))
			{
				throw new UnimplementedOperationException("The validation of generic type " + required.getClass().getName() + " is not implemented.");
			}
			
			GenericTypeParameter param = required.getGenericTypeParameter();
			
			if (param != null)
			{
				GenericTypeArgument arg = param.getCorrespondingArgument(context);
				
				if (arg != null)
				{
					Value value = arg.cloneTo(new GenericTypeArgument(arg.getParent(), arg.getLocationIn()), false, true);
					value.setArrayDimensions(required.getArrayDimensions());
					
					if (!isTypeCompatible(context, value, given, false))
					{
						if (!value.getProgram().getController().isTesting)
						{
							required.getGenericTypeParameter();
							param.getCorrespondingArgument(context);
							value.getTypeClass();
							isTypeCompatible(context, value, given, false);
							param.getCorrespondingArgument(context);
						}

						SyntaxMessage.error("Incorrect type '" + given.getType() + "' given for required generic type of '" + value.getType() + "' type", given);

						return false;
					}
					else
					{
						return true;
					}
				}
			}
		}
		
		if (given.isExternalType() ^ required.isExternalType())
		{
			return false;
		}
		// TODO: Does this check array dimensions?
		else if (given.isExternalType() && given.getType().equals(required.getType()))
		{
			return true;
		}
		
		ClassDeclaration givenType = given.getTypeClass();
		ClassDeclaration requiredType = required.getTypeClass();
		
		if (givenType != null && requiredType != null)
		{
			if (givenType.isOfType(requiredType))
			{
				return true;
			}
			else if (givenType.isPrimitiveOverload() && requiredType.isPrimitiveOverload())
			{
				if (givenType.genericOverload.isOfType(requiredType.genericOverload))
				{
					return true;
				}
			}
		}
		
		if (arePrimitiveTypesCompatible(required.getTypeClassName(), given.getTypeClassName()))
		{
			return true;
		}
		else if (required.isGenericType() && given.isGenericType() && required.getGenericTypeParameter() == given.getGenericTypeParameter())
		{
			return true;
		}
		else if (required.getTypeClassLocation() == null || given.getTypeClassLocation() == null)
		{
			return false;
		}
		else if (Literal.isNullLiteral(given) && !required.isPrimitive())
		{
			return true;
		}
		if (required instanceof Parameter && required.isPrimitive() && Literal.isNullLiteral(given))
		{
			int index = ((ParameterList)required.parent).getParameterIndex(((Parameter)required).getName()) - ((ParameterList)required.parent).getParameterOffset();
			
			if (!required.getParentMethod().genericOverload.getParameter(index).isPrimitive())
			{
				given.replaceWithDefaultLiteralValue(required);
				
				return true;
			}
		}
		
		return false;
	}
	
	public static void replaceArrayAccessWithSetter(Variable assigned, Value assignment)
	{
		assigned.replaceWith(generateArraySetterCallFromAccess(assigned, assignment));
	}
	
	public static MethodCall generateArraySetterCallFromAccess(Variable assigned, Value assignment)
	{
		String call = "set(" + ((MethodCall)assigned).getArgumentList().getVisibleChild(0).generateFlatInput() + ", " + assignment.generateFlatInput() + ")";
		
		return MethodCall.decodeStatement(assigned.getParent(), call, assigned.getLocationIn(), true, false, ((Variable)assigned.getParent()).getTypeClass().getArrayMutatorMethod());
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
		return validateImported(node, clazz, true);
	}
	
	public static boolean validateImported(Node node, String clazz, boolean fullPath)
	{
		return validateImported(node, clazz, fullPath, node.getLocationIn());
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
		return validateImported(node, clazz, true, location);
	}
	
	public static boolean validateImported(Node node, String clazz, boolean fullPath, Location location)
	{
		if (!fullPath)
		{
			ClassDeclaration c = node.getFileDeclaration().getImportedClass(node, clazz);
			
			if (c != null)
			{
				return true;
			}
		}
		else
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
		}
		
		return false;
	}
	
	public static void throwImportException(Node parent, String type, Location location)
	{
		throwImportException(parent, type, location, true);
	}

	public static void throwImportException(Node parent, String type, Location location, boolean throwException)
	{
		SyntaxMessage.error("Type '" + type + "' is not imported", parent, location, throwException);
	}
	
	public static boolean invalidType(Node parent, String type, boolean require)
	{
		return SyntaxMessage.queryError("Type '" + type + "' does not exist", parent, require, "type");
	}
	
	public static boolean invalidType(Node parent, String type, boolean require, boolean throwException)
	{
		return SyntaxMessage.queryError("Type '" + type + "' does not exist", parent, throwException, require);
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
			Value value1 = types1[i];
			Value value2 = types2[i];
			
			if (value1 instanceof ClosureDeclaration || value2 instanceof ClosureDeclaration)
			{
				if (value1 instanceof ClosureDeclaration ^ value2 instanceof ClosureDeclaration)
				{
					return false;
				}
				if (value1.getType() == null ^ value2.getType() == null || value1.getType() != null && !value1.getType().equals(value2.getType()))
				{
					return false;
				}
				
				return areSameTypes(((ClosureDeclaration)value1).getParameterList().getTypes(), ((ClosureDeclaration)value2).getParameterList().getTypes());
			}
			else
			{
				String type1 = types1[i].getType();
				String type2 = types2[i].getType();
				
				if ((type1 == null ^ type2 == null) || !type1.equals(type2) || value1.getArrayDimensions() != value2.getArrayDimensions())
				{
					return false;
				}
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
	
	public static String getClassName(String classLocation) {
		return getClassName(classLocation, true);
	}

	public static String getClassName(String classLocation, boolean includeEncapsulatingClasses)
	{
		if (classLocation == null)
		{
			return null;
		}
		
		int lastIndex = classLocation.lastIndexOf('/') + 1;
		int endIndex = includeEncapsulatingClasses ? -1 : classLocation.lastIndexOf('.');
		
		return classLocation.substring(Math.max(endIndex + 1, lastIndex));
	}
	
	public static ClassDeclaration getImportedClass(FileDeclaration file, String className)
	{
		String location = file.getImportList().getAbsoluteClassLocation(className);
		
		return file.getProgram().getClassDeclaration(location);
	}
	
	public static Bounds findValueBounds(String source, int start)
	{
		char c = source.charAt(start);
		
		Bounds bounds;
		
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
		}
		
		bounds = findIdentifierBounds(source, start, true, true);
		
		if (bounds != null)
		{
			return bounds;
		}
		
		return Bounds.EMPTY;
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
	
	public static GenericTypeArgument[] getGenericTypeArguments(Node parent, String params)
	{
		String paramsList[] = StringUtils.splitCommas(params, 1);
		
		ArrayList<GenericTypeArgument> args = new ArrayList<>();

		int i = 0;
		for (String param : paramsList)
		{
			GenericTypeArgument arg = getGenericTypeArgumentName(parent, param);
			arg.genericParameter = arg.searchGenericTypeParameter(i++);
			args.add(arg);
		}
		
		return args.toArray(new GenericTypeArgument[0]);
	}
	
	public static GenericTypeArgument getGenericTypeArgumentName(Node parent, GenericTypeParameter parameter)
	{
		GenericTypeArgument type = new GenericTypeArgument(parent, Location.INVALID, parameter);
		type.setType(parameter.getType(), true, false, true);
		
		return type;
	}

	public static GenericTypeArgument getGenericTypeArgumentName(Node parent, String parameterName)
	{
		GenericTypeArgument type = new GenericTypeArgument(parent, Location.INVALID);
		type.setType(parameterName, true, false, true);

		/*DeclarationData data = new DeclarationData();

		GenericTypeParameterList.searchGenerics(parameterName, data);
		type.iterateWords(parameterName, data, true);

		if (data.containsSkipBounds())
		{
			//type.setType(data.getSkipBounds(0).trimString(parameterName), true, false);
			int j = 4;
		}*/

		return type;
	}

	public static boolean isLambdaPositionalParameterName(String name) {
		return name.startsWith("_") && tryParse(name.substring(1)) != null;
	}

	private static Integer tryParse(String text)
	{
		try
		{
			return Integer.parseInt(text);
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
	
	/**
	 * Check to see if the two types are the same type.
	 *
	 * @param value1 The first Value to compare.
	 * @param value2 The second Value to compare.
	 * @return Whether or not the two types are the same.
	 */
	public static boolean isSameType(Value value1, Value value2)
	{
		return isSameType(value1, value2, true);
	}
	
	public static boolean isSameType(Value value1, Value value2, boolean checkGeneric)
	{
		String type1 = value1.getInstanceType(checkGeneric);
		String type2 = value2.getInstanceType(checkGeneric);

		type1 = type1 != null ? type1.replace("*", "").replace("&", "") : null;
		type2 = type2 != null ? type2.replace("*", "").replace("&", "") : null;

		if (type1 != null && type1.equals(type2))
		{
			if (value1.isGenericType())
			{
				type1 = value1.getGenericReturnType();
			}
			if (value2.isGenericType())
			{
				type2 = value2.getGenericReturnType();
			}
		}
		
		if (value1 instanceof Closure || value2 instanceof Closure)
		{
			return true;
		}
		else if (type1 == null || type2 == null)
		{
			return type1 == null && type2 == null;
		}
		else if (type1.equals("String") && value2.getArrayDimensions() == 1 && type2.equals("Char"))
		{
			return value1 instanceof Literal;
		}
		else if (type2.equals("String") && value1.getArrayDimensions() == 1 && type1.equals("Char"))
		{
			return value2 instanceof Literal;
		}
		
		return type1.equals(type2);
	}
	
	public static Scope cloneToScope(Scope scope, Node parent)
	{
		Scope temp = new Scope(parent, Location.INVALID);
		
		scope.cloneChildrenTo(temp);
		
		return temp;
	}
	
	public static String getConvertedFlatContents(Scope scope, Node parent, FlatMethodDeclaration conversionTarget)
	{
		Scope temp = SyntaxUtils.cloneToScope(scope, parent);
		
		temp.extractLambdas();
		temp.extractArrayInitializers();

		return temp.getFlatContents();
	}
	
	public static void parseConvertedContentsTo(Scope scope, Node parent, FlatMethodDeclaration conversionTarget, Node to)
	{
		String code = SyntaxUtils.getConvertedFlatContents(scope, parent, conversionTarget);
		
		TreeGenerator generator = new TreeGenerator(null, code, parent.getProgram().getTree());
		
		generator.traverseCode(to, 0, null, false);
	}
	
	public static GenericTypeArgument performWalk(Value context, Value subContext, ClassDeclaration current, ClassDeclaration required, GenericTypeArgument argument)
	{
		return performWalk(context, subContext, current, required, argument.getGenericTypeParameter());
	}
	
	public static GenericTypeArgument performWalk(Value context, Value subContext, ClassDeclaration current, ClassDeclaration required, GenericTypeParameter parameter)
	{
		return performWalk(context, subContext, current, required, parameter, false);
	}
	
	public static GenericTypeArgument performWalk(Value context, Value subContext, ClassDeclaration current, ClassDeclaration required, GenericTypeParameter parameter, boolean allowGeneric)
	{
		return performWalk(context, subContext, current, required, parameter.getVisibleIndex(), allowGeneric);
	}
	
	public static GenericTypeArgument performWalk(Value context, Value subContext, ClassDeclaration current, ClassDeclaration required, int parameterIndex)
	{
		return performWalk(context, subContext, current, required, parameterIndex, false);
	}
	
	public static GenericTypeArgument performWalk(Value context, Value subContext, ClassDeclaration current, ClassDeclaration required, int parameterIndex, boolean allowGeneric)
	{
		Stack<IValue> path = new Stack<>();
		
		path = performWalk(current, required, path);
		
		if (path != null)
		{
			while (!path.isEmpty())
			{
				IValue value = path.pop();
				ClassDeclaration type = value.getTypeClass();
				
				GenericTypeArgumentList args = value.getGenericTypeArgumentList();
				GenericTypeArgument arg = null;
				
				if (type.isPrimitiveOverload())
				{
					GenericTypeParameter param = type.genericOverload.getGenericTypeParameter(parameterIndex);
					
					GenericTypeParameter corresponding = type.getGenericTypeParameter(param.getName());
					
					if (corresponding != null)
					{
						arg = value.getGenericTypeArgument(corresponding.getVisibleIndex());
					}
					else
					{
						arg = (GenericTypeArgument)type.primitiveOverloadTypes[parameterIndex];
					}
				}
				else if (args.getNumVisibleChildren() <= parameterIndex)
				{
					return null;
				}
				else
				{
					arg = args.getVisibleChild(parameterIndex);
				}
				
				if (!arg.isGenericType() || allowGeneric && arg.getParentClass() == current)
				{
					return arg;
				}
				else
				{
					GenericTypeParameter param = arg.getGenericTypeParameter();
					
					parameterIndex = param.getIndex();
				}
			}
		}
		
		if (current.isPrimitiveOverload() && current.genericOverload == required)
		{
			GenericTypeParameter param = current.genericOverload.getGenericTypeParameter(parameterIndex);
			
			GenericTypeParameter corresponding = current.getGenericTypeParameter(param.getName());
			
			if (corresponding != null)
			{
//				arg = value.getGenericTypeArgument(corresponding.getVisibleIndex());
			}
			else
			{
				return (GenericTypeArgument)current.primitiveOverloadTypes[parameterIndex];
			}
		}
		else if (subContext.getGenericTypeArgumentList() != null && subContext.getGenericTypeArgumentList().getNumVisibleChildren() > parameterIndex)
		{
			return subContext.getGenericTypeArgument(parameterIndex);
		}
		
		return null;
	}
	
	public static Stack<IValue> performWalk(ClassDeclaration current, ClassDeclaration required, Stack<IValue> path)
	{
		if (required instanceof Trait)
		{
			TraitImplementation implementation = checkInterface(current, (Trait)required);
			
			if (implementation != null)
			{
				path.push(implementation);
				
				return path;
			}
		}
		
		ExtendedClass extended = current.getExtendedClass();
		
		if (extended != null)
		{
			path.push(extended);
			
			ClassDeclaration type = extended.getTypeClass();
			
			if (type != required && type.genericOverload != required)
			{
				return performWalk(type, required, path);
			}
			else
			{
				return path;
			}
		}
		
		return null;
	}
	
	public static TraitImplementation checkInterface(ClassDeclaration current, Trait required)
	{
		return current.getInterfacesImplementationList().firstWhere(x -> {
			ClassDeclaration type = x.getTypeClass();
			
			return type == required || type.genericOverload == required;
		});
	}
}
