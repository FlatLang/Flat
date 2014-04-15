/**
 * The Fathom Programming Language. Write Unbelievable Code.
 *  Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fathomsoft.fathom.util;

import java.util.regex.Matcher;

import net.fathomsoft.fathom.Fathom;
import net.fathomsoft.fathom.tree.MethodNode;
import net.fathomsoft.fathom.tree.ParameterListNode;
import net.fathomsoft.fathom.tree.ParameterNode;
import net.fathomsoft.fathom.tree.variables.FieldNode;

/**
 * Class used for getting information about the Syntax of Fathom.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 15, 2014 at 7:55:00 PM
 * @version	v0.2 Apr 8, 2014 at 6:08:56 PM
 */
public class SyntaxUtils
{
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
		if (value.length() != 3)
		{
			return false;
		}
		
		return value.charAt(0) == '\'' && value.charAt(value.length() - 1) == '\'';
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
		
		return value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"';
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
	    boolean seenDot     = false;
	    boolean seenExp     = false;
	    boolean justSeenExp = false;
	    boolean seenDigit   = false;
	    
	    for (int i = 0; i < value.length(); i++)
	    {
	        char c = value.charAt(i);
	        
	        if (c >= '0' && c <= '9')
	        {
	            seenDigit = true;
	            
	            continue;
	        }
	        
	        if ((c == '-' || c == '+') && (i == 0 || justSeenExp))
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
	    
	    try
	    {
	        Double.parseDouble(value);
	        
	        return true;
	    }
	    catch (NumberFormatException e)
	    {
	        return false;
	    }
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
		return type.equals("int") || type.equals("char") || type.equals("long_long") || type.equals("boolean") || type.equals("short") || type.equals("float") || type.equals("double") || type.equals("void");
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
		return Regex.indexOf(statement, Patterns.PRE_EQUALS_SIGN) == 0;
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
		Bounds bounds = Regex.boundsOf(value, Patterns.IDENTIFIER);
		
		return bounds.getStart() == 0 && bounds.getEnd() == value.length();
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
		
		return bounds.getStart() == 0;
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
		Bounds bounds = Regex.boundsOf(statement, Patterns.ARRAY_INIT);
		
		return bounds.getStart() == 0 && bounds.getEnd() == statement.length();
	}
	
	/**
	 * Get the number of dimensions that the given array has, if any.<br>
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
	 * @return The number of dimensions that the given array has, if any.
	 */
	public static int getArrayDimensions(String statement)
	{
		int num   = 0;
		int index = statement.indexOf('[') + 1;
		
		while (index > 1)
		{
			int endIndex = statement.indexOf(']', index);
			
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
	public static boolean isMainMethod(MethodNode method)
	{
		if ((method.getName().equals("main") || method.getName().equals("__" + Fathom.LANGUAGE_NAME.toUpperCase() + "__main")) && method.isStatic() && method.getType().equals("void") && method.getVisibility() == FieldNode.PUBLIC)
		{
			ParameterListNode params = (ParameterListNode)method.getParameterListNode();
			
			if (params.getChildren().size() == 1)
			{
				ParameterNode param = (ParameterNode)params.getChild(0);
				
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
	 * {@link net.fathomsoft.fathom.tree.InstantiationNode#decodeStatement(TreeNode, String, Location)}.
	 * 
	 * @param statement The statement to test.
	 * @return Whether or not the given statement is an instantiation.
	 */
	public static boolean isInstantiation(String statement)
	{
		return Regex.indexOf(statement, Patterns.PRE_INSTANTIATION) == 0;
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
}