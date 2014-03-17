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
	public static final Pattern PRE_EQUALS_SIGN			= Pattern.compile("(\\S+\\s*)+=");
	public static final Pattern POST_EQUALS_SIGN		= Pattern.compile("(?<=(=\\s{0,999}))(?=\\S+)");
	public static final Pattern BINARY_ARITH_OPERATORS	= Pattern.compile("[\\*\\+\\/\\-=]");
	public static final Pattern BINARY_LOGIC_OPERATORS	= Pattern.compile("[\\|\\&]");
	public static final Pattern PRE_OPERATORS			= Pattern.compile("\\s+[\\*\\+\\/\\-]");
	public static final Pattern WHITESPACE				= Pattern.compile("\\s");
	public static final Pattern NON_WHITESPACE			= Pattern.compile("\\S");
	public static final Pattern PRE_RETURN				= Pattern.compile("return\\s*");
	public static final Pattern POST_RETURN     	    = Pattern.compile("(?<=return\\s{0,999})(\\S+)");
	public static final Pattern PRE_IF					= Pattern.compile("if\\s*\\(");
	public static final Pattern POST_IF	     	    	= Pattern.compile("(?<=if\\s{0,9}\\(\\s{0,999})(\\S+\\))");
	public static final Pattern PRE_IMPORT          	= Pattern.compile("import\\s*");
	public static final Pattern POST_IMPORT				= Pattern.compile("(?<=import\\s{0,999})(\\S+)");
	public static final Pattern PRE_INSTANTIATION		= Pattern.compile("new\\s+\\S+");
	public static final Pattern POST_INSTANTIATION		= Pattern.compile("(?<=new\\s{1,999})(\\S+)");
	public static final Pattern PRE_METHOD_CALL			= Pattern.compile("\\S+\\s*\\(");
//	public static final Pattern POST_METHOD_CALL		= Pattern.compile("(?<=\\S+\\s{0,999}\\(\\s{0,999})(\\S+\\))");
	public static final Pattern ARRAY_INIT				= Pattern.compile("\\S+\\s*(\\[\\S+\\])+");
	public static final Pattern DEFINE_PATTERN			= Pattern.compile("(?<=#\\s{0,999}define\\s{1,999})\\S");
	public static final Pattern	ALPHANUMERIC			= Pattern.compile("[a-zA-Z0-9_]");
	public static final Pattern	NON_ALPHANUMERIC		= Pattern.compile("[^a-zA-Z0-9_]");
	public static final Pattern FUNCTION_PROTOTYPE		= Pattern.compile("(?<=([\\n;} \\t\\r]\\s?))(([a-zA-Z0-9_\\*\\&]+\\s*)+(\\(.*?\\);))");
	public static final Pattern ARRAY_BRACKETS     	    = Pattern.compile("\\s*\\[\\s*\\]");
	public static final Pattern METHOD_NAME     	    = Pattern.compile("\\S+(?=\\s*\\()");
	public static final Pattern SINGLE_LINE_COMMENT	    = Pattern.compile("//.*");
}