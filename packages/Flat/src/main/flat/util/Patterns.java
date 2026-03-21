package flat.util;

import java.util.regex.Pattern;

/**
 * Utility class that contains a multitude of pre-compiled patterns used for searching through text
 * for specific events.
 *
 * @author Braden Steffaniak
 * @since v0.1 Apr 5, 2014 at 3:53:04 PM
 * @version v0.2.36 Oct 13, 2014 at 12:16:42 AM
 */
public class Patterns {
    /**
     * Pattern that searches for the boundaries of a "word." A "word" consists of anything that is
     * not whitespace.
     */
    public static final Pattern WORD_BOUNDARIES =
        Pattern.compile("(?<=\\s|^)(?=[\\S])|(?<=\\S)(?=\\s|$)");

    /**
     * Pattern that searches for the boundaries of an identifier.
     */
    public static final Pattern IDENTIFIER_BOUNDARIES = Pattern.compile("\\b");

    /**
     * Pattern that searches for the boundaries of an identifier.
     */
    public static final Pattern EXTENSION_IDENTIFIER =
        Pattern.compile("[A-Za-z0-9_]+\\s*+\\.\\s*[A-Za-z0-9_]+");

    /**
     * Pattern that searches for an identifier declaration. An identifier declaration consists of
     * letters, numbers, underscores, brackets, and spaces.
     */
    public static final Pattern IDENTIFIER_DECLARATION =
        Pattern.compile("[A-Za-z0-9_ \\n\\t\\[\\].\\*\\&]+");

    /**
     * Pattern that searches for an identifier. An identifier consists of letters, numbers, and
     * underscores.
     */
    public static final Pattern IDENTIFIER = Pattern.compile("[A-Za-z0-9_]+");


    /**
     * Pattern that searches for an identifier. An identifier consists of letters, numbers, and
     * underscores.
     */
    public static final Pattern NAMED_ARGUMENT = Pattern.compile("[A-Za-z0-9_]+\\s*:");

    /**
     * Pattern that searches for non-identifier character.
     */
    public static final Pattern NON_IDENTIFIER = Pattern.compile("[^a-zA-Z0-9_]");

    /**
     * Pattern that searches for the start of a statement.
     */
    public static final Pattern STATEMENT_START = Pattern.compile("(?=[^\\}])(?=\\S)");

    /**
     * Pattern that searches for the end of a statement.
     */
    public static final Pattern STATEMENT_END = Pattern.compile("\\s*[\\{\\;]");

    /**
     * Pattern that searches for the next line.
     */
    public static final Pattern NEXT_TEXT_LINE = Pattern.compile("(.)", Pattern.MULTILINE);

    /**
     * Pattern that searches for specific characters that trigger events for the source code
     * formatter.
     */
    public static final Pattern FORMATTER_PATTERN = Pattern.compile("[\\}\\{\\)\\(\\n]");

    /**
     * Pattern that searches for the left hand value of an equation/assignment.<br>
     * <br>
     * For example: In the case of "int number = 25 + 42" the left hand value is the "int number"
     * part of the equation/assignment.
     */
    public static final Pattern PRE_EQUALS_SIGN = Pattern.compile("[\\S\\s]+(?=[=])");

    /**
     * Pattern that searches for the right hand value of an equation/assignment.<br>
     * <br>
     * For example: In the case of "int number = 25 + 42" the right hand value is the "25 + 42" part
     * of the equation/assignment.
     */
    public static final Pattern POST_EQUALS_SIGN = Pattern.compile("(?<=(=\\s{0,9}))(?=\\S+)");

    /**
     * Pattern that searches for unary arithmetic operators. The operators include ++, --.
     */
    public static final Pattern UNARY_ARITH_OPERATORS =
        Pattern.compile("(?<=\\s{0,9})([\\+]{2}|[-]{1,2})(?=\\s*)");

    /**
     * Pattern that searches for binary arithmetic operators. The operators include *, /, +, -, =,
     * %.
     */
    public static final Pattern BINARY_ARITH_OPERATORS = Pattern.compile("([\\*\\+\\/\\-=%])");

    /**
     * Pattern that searches for binary logical operators. The operators include !=, ==, &gt;=,
     * &lt;=, |, &amp;, &lt;, &gt;
     */
    public static final Pattern BINARY_LOGIC_OPERATORS =
        Pattern.compile("!=|==|>=|<=|[\\|\\&\\<\\>]");

    /**
     * Pattern that searches for the data before an operator.<br>
     * <br>
     * For example: In the case of "45 * 6" the data before the '*' operator is returned as "45 "
     */
    public static final Pattern PRE_OPERATORS =
        Pattern.compile("[\\S\\s]+?(?=([!<>=]=?|&{1,2}|\\|{1,2}|[\\*\\+\\/\\-%])[^=|&])");

    /**
     * Pattern that searches for the data after an operator.<br>
     * <br>
     * For example: In the case of "45 * 6" the data after the '*' operator is returned as " 6"
     */
    public static final Pattern POST_OPERATORS =
        Pattern.compile("(?<=([!<>=]=?|&{1,2}|\\|{1,2}|[\\*\\+\\/\\-%])[^=|&]\\s{0,9})(\\S+\\s*)+");

    /**
     * Pattern that searches for a whitespace character.
     */
    public static final Pattern WHITESPACE = Pattern.compile("\\s");

    /**
     * Pattern that searches for a non-whitespace character.
     */
    public static final Pattern NON_WHITESPACE = Pattern.compile("\\S");

    /**
     * Pattern that searches for the word "return" followed by the possibility of whitespace.<br>
     * <br>
     * For example, both of the following are correct: "return " and "return"
     */
    public static final Pattern PRE_RETURN = Pattern.compile("return\\s*");

    /**
     * Pattern that searches for the data after a return statement.<br>
     * <br>
     * For example: "return 43" would return the output of "43"
     */
    public static final Pattern POST_RETURN = Pattern.compile("(?<=return\\s{0,9})(\\S)");

    /**
     * Pattern that searches for the word "if" followed by an opening parenthesis.<br>
     * <br>
     * For example, both of the following are correct: "if (" and "if("
     */
    public static final Pattern PRE_IF = Pattern.compile("if(?=\\s*\\()");

    /**
     * Pattern that searches for the contents of an if statement.<br>
     * <br>
     * For example:<br>
     * "if (getSize() &gt; 4)" will return "getSize() &gt; 4"
     */
    public static final Pattern IF_CONTENTS =
        Pattern.compile("(?<=if\\s{0,9}\\(\\s{0,9})([\\S\\s]+)(?=\\s*\\))");

    /**
     * Pattern that searches for the word "else" followed by any whitespace.<br>
     * <br>
     * For example, both of the following are correct: "else " and "else"
     */
    public static final Pattern ELSE = Pattern.compile("else\\s*");

    /**
     * Pattern that searches for the word "while" followed by an opening parenthesis.<br>
     * <br>
     * For example, both of the following are correct: "while (" and "while("
     */
    public static final Pattern PRE_WHILE = Pattern.compile("while(?=\\s*\\()");

    /**
     * Pattern that searches for the contents of a while statement.<br>
     * <br>
     * For example:<br>
     * "while (!thing.isEmpty())" will return "!thing.isEmpty()"
     */
    public static final Pattern WHILE_CONTENTS =
        Pattern.compile("(?<=while\\s{0,9}\\(\\s{0,9})([\\S\\s]+)(?=\\s*\\))");

    /**
     * Pattern that searches for the word "for" followed by an opening parenthesis.<br>
     * <br>
     * For example, both of the following are correct: "for (" and "for("
     */
    public static final Pattern PRE_FOR = Pattern.compile("for(?=\\s*\\()");

    public static final Pattern CONTROL_STRUCTURES =
        Pattern.compile("(for|while|if|return)(\\s+|[(]|$)");

    public static final Pattern TABS = Pattern.compile("\n(\t|[ ]{4})+");

    /**
     * Pattern that searches for the contents of a for statement.<br>
     * <br>
     * For example:<br>
     * "for (int i = 0; i &lt; 10; i++)" will return "int i = 0; i &lt; 10; i++"
     */
    public static final Pattern FOR_CONTENTS =
        Pattern.compile("(?<=for\\s{0,9}\\(\\s{0,9})([\\S\\s]+)(?=\\s*\\))");

    /**
     * Pattern that searches for the word "import" followed by the possibility of whitespace.<br>
     * <br>
     * For example, both of the following are correct: "import " and "import"
     */
    public static final Pattern PRE_IMPORT = Pattern.compile("import\\s*");

    /**
     * Pattern that searches for the data after an import statement.<br>
     * <br>
     * For example:<br>
     * <u><i>import "String"</i></u> would return the output of <u><i>"String"</i></u>
     */
    public static final Pattern POST_IMPORT = Pattern.compile("(?<=import\\s{0,9})(\\S+)");

    /**
     * Pattern that searches for the data before a method call.<br>
     * <br>
     * For example:<br>
     * "person.getAge()" would return the output of "person.getAge"
     */
    // public static final Pattern PRE_METHOD_CALL = Pattern.compile("\\w+?(<[^>]+?>)?\\s*\\(");

    // /**
    // * Pattern that searches for
    // */
    // public static final Pattern POST_METHOD_CALL =
    // Pattern.compile("(?<=\\S+\\s{0,999}\\(\\s{0,999})(\\S+\\))");

    /**
     * Pattern that searches for an array initialization.<br>
     * <br>
     * For example:<br>
     * "array = new int[423]" would return "int[423]"
     */
    public static final Pattern ARRAY_INIT =
        Pattern.compile("[A-Za-z0-9_]+(\\s*\\[\\s*([^\\]]+?\\s*\\]|\\]))+");

    /**
     * Pattern that searches for an array access.<br>
     * <br>
     * For example:<br>
     * "array[423]"
     */
    public static final Pattern ARRAY_ACCESS = Pattern.compile("(\\s*\\[\\s*[^\\]]+?\\s*\\])+");// Pattern.compile("\\(?[A-Za-z0-9_]+\\)?(\\s*\\[\\s*[^\\]]+?\\s*\\])+");

    /**
     * Pattern that searches for a #define declaration.
     */
    public static final Pattern DEFINE_PATTERN = Pattern.compile("(?<=#\\s{0,9}define\\s{1,9})\\S");

    /**
     * Pattern that searches for any alphanumeric character.
     */
    public static final Pattern ALPHANUMERIC = Pattern.compile("[a-zA-Z0-9]");

    /**
     * Pattern that searches for non-alphanumeric character.
     */
    public static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^a-zA-Z0-9]");

    /**
     * Pattern that searches for the definition of a function prototype.
     */
    public static final Pattern FUNCTION_PROTOTYPE =
        Pattern.compile("(?<=([\\n;} \\t\\r]\\s?))(([a-zA-Z0-9_\\*\\&]+\\s*)+(\\([\\s\\S]*?\\);))");

    /**
     * Pattern that searches for a pair of brackets that contain something other than
     * whitespace.<br>
     * <br>
     * For example:<br>
     * "array = new int[423]" would return "[423]"
     */
    public static final Pattern ARRAY_BRACKETS =
        Pattern.compile("(?<=\\s{0,9})\\[\\s*[^\\]]+?\\s*\\]");

    /**
     * Pattern that searches for the data within a pair of brackets.<br>
     * <br>
     * For example:<br>
     * "array = new int[423]" would return "423"
     */
    public static final Pattern ARRAY_BRACKETS_DATA =
        Pattern.compile("(?<=\\s{0,9}\\[\\s{0,9})[^\\]]+?(?=\\s*\\])");

    /**
     * Pattern that searches for an empty pair of brackets. An empty pair of brackets can only
     * contain whitespace between the brackets.
     */
    public static final Pattern EMPTY_ARRAY_BRACKETS = Pattern.compile("\\s*\\[\\s*\\]");

    /**
     * Pattern that searches for the name of a method.<br>
     * <br>
     * For example:<br>
     * "public int calculateArea()" would return "calculateArea"
     */
    public static final Pattern METHOD_NAME = Pattern.compile("\\S+(?=\\s*\\()");

    /**
     * Pattern that searches for a single line comment.<br>
     * <br>
     * For example:<br>
     * <blockquote>
     * 
     * <pre>
     * // This is a single line comment, and this whole line would be returned.
     * </pre>
     * 
     * </blockquote>
     */
    public static final Pattern SINGLE_LINE_COMMENT = Pattern.compile("//.*");

    /**
     * Pattern that searches for a multi-line comment.<br>
     * <br>
     * For example:<br>
     * <blockquote>
     * 
     * <pre>
     * /* This is a multi-line comment...
     *  * This is a continuation on the next line...
     *  * until the end of the comment... <code>*</code>/
     * </pre>
     * 
     * </blockquote>
     */
    public static final Pattern MULTI_LINE_COMMENT = Pattern.compile("/\\*(.|[\r\n])*?\\*/");

    /**
     * Pattern that searches for any of the following:
     * <ul>
     * <li>{@link #SINGLE_LINE_COMMENT Single-line Comment}</li>
     * <li>{@link #MULTI_LINE_COMMENT Multi-line Comment}</li>
     * </ul>
     */
    public static final Pattern COMMENT = Pattern.compile("(//.*)|(/\\*(.|[\r\n])*?\\*/)");

    /**
     * Pattern that searches for the word "try"
     */
    public static final Pattern TRY = Pattern.compile("try");

    /**
     * Pattern that searches for a catch declaration and returns the first part of it.<br>
     * <br>
     * For example:<br>
     * <blockquote>
     * 
     * <pre>
     * catch (ExceptionName varName)
     * </pre>
     * 
     * </blockquote> returns "catch"
     */
    public static final Pattern PRE_CATCH = Pattern.compile("catch(?=\\s*\\()");

    /**
     * Pattern that searches for a catch declaration and returns the last part of it.<br>
     * <br>
     * For example:<br>
     * <blockquote>
     * 
     * <pre>
     * catch (ExceptionName varName)
     * </pre>
     * 
     * </blockquote> returns "Exception varName"
     */
    public static final Pattern POST_CATCH =
        Pattern.compile("(?<=catch\\s{0,9}\\(\\s{0,9})([\\S\\s]+)(?=\\s*\\))");

    /**
     * Pattern that searches for the word "finally"
     */
    public static final Pattern FINALLY = Pattern.compile("finally");

    /**
     * Pattern that searches for the word "throw" followed by a whitespace character.
     */
    public static final Pattern PRE_THROW = Pattern.compile("throw(?=\\s+\\S)");

    /**
     * Pattern that searches for a throw declaration and returns the last part of it.<br>
     * <br>
     * For example:<br>
     * <blockquote>
     * 
     * <pre>
     * throw new ExceptionName()
     * </pre>
     * 
     * </blockquote> returns "ExceptionName()"
     */
    public static final Pattern POST_THROW = Pattern.compile("(?<=throw\\s{1,9})((\\S+\\s*)+)");

    /**
     * Pattern that searches for the word "class" followed by a whitespace character.
     */
    public static final Pattern PRE_CLASS = Pattern.compile("class\\s");

    /**
     * Pattern that searches for the word "external" followed by a whitespace character.
     */
    public static final Pattern EXTERNAL = Pattern.compile("external\\s");

    /**
     * Pattern that searches for the word "abstract" followed by a whitespace character.
     */
    public static final Pattern ABSTRACT = Pattern.compile("abstract\\s");

    /**
     * Pattern that searches for the words "external type"
     */
    public static final Pattern EXTERNAL_TYPE = Pattern.compile("external\\s+type\\s+");
}
