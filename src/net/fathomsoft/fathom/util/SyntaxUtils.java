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

import net.fathomsoft.fathom.Fathom;
import net.fathomsoft.fathom.tree.IdentifierNode;
import net.fathomsoft.fathom.tree.LiteralNode;
import net.fathomsoft.fathom.tree.MethodNode;
import net.fathomsoft.fathom.tree.ParameterListNode;
import net.fathomsoft.fathom.tree.ParameterNode;
import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.tree.variables.FieldNode;

/**
 * Class used for getting information about the Syntax of Fathom.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 15, 2014 at 7:55:00 PM
 * @since	v0.1
 * @version	Mar 15, 2014 at 7:55:00 PM
 * @version	v0.1
 */
public class SyntaxUtils
{
	public static boolean isLiteral(String value)
	{
		return isCharLiteral(value) || isStringLiteral(value) || isNumber(value);
	}
	
	public static boolean isCharLiteral(String value)
	{
		if (value.length() != 3)
		{
			return false;
		}
		
		return value.charAt(0) == '\'' && value.charAt(value.length() - 1) == '\'';
	}
	
	public static boolean isStringLiteral(String value)
	{
		if (value.length() < 2)
		{
			return false;
		}
		
		return value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"';
	}
	
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
	
	public static boolean isPrimitiveType(String type)
	{
		return type.equals("int") || type.equals("char") || type.equals("long_long") || type.equals("bool") || type.equals("short") || type.equals("float") || type.equals("double") || type.equals("void");
	}
	
	public static boolean isVariableAssignment(String statement)
	{
		return Regex.indexOf(statement, Patterns.PRE_EQUALS_SIGN) == 0;
	}
	
	public static boolean isValidIdentifier(String value)
	{
		Bounds bounds = Regex.boundsOf(value, Patterns.IDENTIFIER);
		
		return bounds.getStart() == 0 && bounds.getEnd() == value.length();
	}
	
	public static boolean isValidArrayAccess(String value)
	{
		Bounds bounds = Regex.boundsOf(value, Patterns.ARRAY_ACCESS);
		
		return bounds.getStart() == 0 && bounds.getEnd() == value.length();
	}
	
	public static boolean isMethodCall(String statement)
	{
		Bounds bounds = Regex.boundsOf(statement, Patterns.PRE_METHOD_CALL);
		
		return bounds.getStart() == 0;
	}
	
	public static boolean isArrayInitialization(String statement)
	{
		Bounds bounds = Regex.boundsOf(statement, Patterns.ARRAY_INIT);
		
		return bounds.getStart() == 0 && bounds.getEnd() == statement.length();
	}
	
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
	
	public static boolean isInstantiation(String statement)
	{
		return Regex.indexOf(statement, Patterns.PRE_INSTANTIATION) == 0;
	}
}