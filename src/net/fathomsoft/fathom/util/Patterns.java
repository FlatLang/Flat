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

import java.util.regex.Pattern;

public class Patterns
{
	public static final Pattern WORD_BOUNDARIES			= Pattern.compile("(?<=\\s|^)(?=[\\S])|(?<=\\S)(?=\\s|$)");
	public static final Pattern IDENTIFIER_BOUNDARIES	= Pattern.compile("\\b");
	public static final Pattern IDENTIFIER				= Pattern.compile("[A-Za-z0-9_]+");
	public static final Pattern	STATEMENT_START			= Pattern.compile("(?=[^\\}])(?=\\S)");
	public static final Pattern	STATEMENT_END			= Pattern.compile("\\s*[\\{\\;]");
	public static final Pattern	NEXT_TEXT_LINE			= Pattern.compile("(.)", Pattern.MULTILINE);
	public static final Pattern FORMATTER_PATTERN		= Pattern.compile("[\\}\\{\\)\\(\\n]");
	public static final Pattern PRE_EQUALS_SIGN			= Pattern.compile("([A-Za-z0-9_\\[\\]]+\\s*)+(?=[=])");
	public static final Pattern POST_EQUALS_SIGN		= Pattern.compile("(?<=(=\\s{0,9}))(?=\\S+)");
	public static final Pattern BINARY_ARITH_OPERATORS	= Pattern.compile("([\\*\\+\\/\\-=])");
	public static final Pattern BINARY_LOGIC_OPERATORS	= Pattern.compile("==|>=|<=|[\\|\\&\\<\\>]");
	public static final Pattern PRE_OPERATORS			= Pattern.compile("([A-Za-z0-9_]+\\s*)+(?=(==|>=|<=|[\\*\\+\\/\\-=\\<\\>]))");
	public static final Pattern POST_OPERATORS			= Pattern.compile("(?<=(([\\<\\>=]{1,2})|[\\*\\+\\/\\-])\\s{0,9})[A-Za-z0-9_]\\s*(\\S+\\s*)*");
	public static final Pattern WHITESPACE				= Pattern.compile("\\s");
	public static final Pattern NON_WHITESPACE			= Pattern.compile("\\S");
	public static final Pattern PRE_RETURN				= Pattern.compile("return\\s*");
	public static final Pattern POST_RETURN     	    = Pattern.compile("(?<=return\\s{0,9})(\\S+)");
	public static final Pattern PRE_IF					= Pattern.compile("if(?=\\s*\\()");
	public static final Pattern POST_IF					= Pattern.compile("(?<=if\\s{0,9}\\(\\s{0,9})([\\S\\s]+)(?=\\s*\\))");
	public static final Pattern PRE_WHILE				= Pattern.compile("while(?=\\s*\\()");
	public static final Pattern POST_WHILE     	    	= Pattern.compile("(?<=while\\s{0,9}\\(\\s{0,9})([\\S\\s]+)(?=\\s*\\))");
	public static final Pattern PRE_FOR					= Pattern.compile("for(?=\\s*\\()");
	public static final Pattern POST_FOR     	    	= Pattern.compile("(?<=for\\s{0,9}\\(\\s{0,9})([\\S\\s]+)(?=\\s*\\))");
	public static final Pattern PRE_IMPORT          	= Pattern.compile("import\\s*");
	public static final Pattern POST_IMPORT				= Pattern.compile("(?<=import\\s{0,9})(\\S+)");
	public static final Pattern PRE_INSTANTIATION		= Pattern.compile("new\\s+\\S+");
	public static final Pattern POST_INSTANTIATION		= Pattern.compile("(?<=new\\s{1,9})(\\S+)");
	public static final Pattern PRE_METHOD_CALL			= Pattern.compile("\\S+(?=\\s*\\()");
//	public static final Pattern POST_METHOD_CALL		= Pattern.compile("(?<=\\S+\\s{0,999}\\(\\s{0,999})(\\S+\\))");
	public static final Pattern ARRAY_INIT				= Pattern.compile("\\S+\\s*(\\[\\S+\\])+");
	public static final Pattern DEFINE_PATTERN			= Pattern.compile("(?<=#\\s{0,9}define\\s{1,9})\\S");
	public static final Pattern	ALPHANUMERIC			= Pattern.compile("[a-zA-Z0-9_]");
	public static final Pattern	NON_ALPHANUMERIC		= Pattern.compile("[^a-zA-Z0-9_]");
	public static final Pattern FUNCTION_PROTOTYPE		= Pattern.compile("(?<=([\\n;} \\t\\r]\\s?))(([a-zA-Z0-9_\\*\\&]+\\s*)+(\\([\\s\\S]*?\\);))");
	public static final Pattern ARRAY_BRACKETS     	    = Pattern.compile("\\s*\\[\\s*\\]");
	public static final Pattern METHOD_NAME     	    = Pattern.compile("\\S+(?=\\s*\\()");
	public static final Pattern SINGLE_LINE_COMMENT	    = Pattern.compile("//.*");
	public static final Pattern MULTI_LINE_COMMENT	    = Pattern.compile("/\\*(.|[\r\n])*?\\*/");
	public static final Pattern COMMENT					= Pattern.compile("(//.*)|(/\\*(.|[\r\n])*?\\*/)");
	public static final Pattern TRY						= Pattern.compile("try");
	public static final Pattern PRE_CATCH				= Pattern.compile("catch(?=\\s*\\()");
	public static final Pattern POST_CATCH				= Pattern.compile("(?<=catch\\s{0,9}\\(\\s{0,9})([\\S\\s]+)(?=\\s*\\))");
	public static final Pattern FINALLY					= Pattern.compile("finally");
	public static final Pattern PRE_THROW				= Pattern.compile("throw(?=\\s)");
	public static final Pattern POST_THROW				= Pattern.compile("(?<=throw\\s{1,9})((\\S+\\s*)+)");
}
