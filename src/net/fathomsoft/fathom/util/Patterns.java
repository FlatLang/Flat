package net.fathomsoft.fathom.util;

import java.util.regex.Pattern;

public class Patterns
{
	public static final Pattern WORD_BOUNDARIES		= Pattern.compile("(?<=\\s|^)(?=[\\S])|(?<=\\S)(?=\\s|$)");
	public static final Pattern	STATEMENT_START		= Pattern.compile("(?=[^\\}])(?=\\S)");
	public static final Pattern	STATEMENT_END		= Pattern.compile("\\s*[\\{\\;]");
	public static final Pattern	NEXT_TEXT_LINE		= Pattern.compile("(.)", Pattern.MULTILINE);
	public static final Pattern FORMATTER_PATTERN	= Pattern.compile("[\\}\\{\\n]");
	public static final Pattern PRE_EQUALS_SIGN		= Pattern.compile("\\s+=");
	public static final Pattern POST_EQUALS_SIGN	= Pattern.compile("(?<=(=\\s{0,99999999999999999999}))(?=\\S+)");
	public static final Pattern OPERATORS			= Pattern.compile("[\\*\\+\\/\\-]");
	public static final Pattern PRE_OPERATORS		= Pattern.compile("\\s+[\\*\\+\\/\\-]");
	public static final Pattern WHITESPACE			= Pattern.compile("\\s");
	public static final Pattern NON_WHITESPACE		= Pattern.compile("\\S");
	public static final Pattern PRE_RETURN          = Pattern.compile("return\\s*");
	public static final Pattern POST_RETURN         = Pattern.compile("(?<=return\\s{0,99999999999999999999})(\\S+)");
	public static final Pattern PRE_IMPORT          = Pattern.compile("import\\s*");
	public static final Pattern POST_IMPORT         = Pattern.compile("(?<=import\\s{0,99999999999999999999})(\\S+)");
	public static final Pattern DEFINE_PATTERN		= Pattern.compile("(?<=#\\s{0,99999999999999999999}define\\s{1,99999999})\\S");
	public static final Pattern	ALPHANUMERIC		= Pattern.compile("[a-zA-Z0-9_]");
	public static final Pattern	NON_ALPHANUMERIC	= Pattern.compile("[^a-zA-Z0-9_]");
	public static final Pattern FUNCTION_PROTOTYPE	= Pattern.compile("(?<=([\\n;} \\t\\r]\\s?))(([a-zA-Z0-9_\\*\\&]+\\s*)+(\\(.*?\\);))");
}