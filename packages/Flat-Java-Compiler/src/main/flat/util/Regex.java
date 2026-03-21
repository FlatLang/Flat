package flat.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class will several Regular Expression helper methods.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 10, 2014 at 3:12:48 AM
 * @version v0.2.14 Jul 19, 2014 at 7:33:13 PM
 */
public class Regex {
    /**
     * Check whether or not the given src String matches the given Pattern at the start of the src.
     *
     * @param src The source to check with the Pattern.
     * @param pattern The Pattern to test the source against.
     * @return Whether or not the given src String matches the given Pattern at the start.
     */
    public static boolean startsWith(String src, Pattern pattern) {
        return matches(src, 0, pattern);
    }

    /**
     * Check whether or not the given src String matches the given Pattern at the specified index.
     *
     * @param src The source to check with the Pattern.
     * @param index The index to see if it matches at.
     * @param pattern The Pattern to test the source against.
     * @return Whether or not the given src String matches the given Pattern at the specified index.
     */
    public static boolean matches(String src, int index, Pattern pattern) {
        Matcher matcher = pattern.matcher(src);

        if (!matcher.find(index)) {
            return false;
        }

        return matcher.start() == index;
    }

    /**
     * Check whether or not the given src String matches the given Pattern exactly.
     *
     * @param src The source to check with the Pattern.
     * @param pattern The Pattern to test the source against.
     * @return Whether or not the given src String matches the given Pattern exactly..
     */
    public static boolean matches(String src, Pattern pattern) {
        Matcher matcher = pattern.matcher(src);

        if (!matcher.find(0)) {
            return false;
        }

        return matcher.start() == 0 && matcher.end() == src.length();
    }

    /**
     * Search for the next occurrence of the Pattern within the src String and return the start
     * index of the finding.
     *
     * @param src The source String to search through.
     * @param regex The regex pattern to search for in the src String.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, String regex) {
        return indexOf(src, 0, regex);
    }

    /**
     * Search for the next occurrence of the Pattern within the src String and return the start
     * index of the finding.
     *
     * @param src The source String to search through.
     * @param pattern The Pattern to search for in the src String.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, Pattern pattern) {
        return indexOf(src, 0, pattern);
    }

    /**
     * Search for the next occurrence of the Pattern within the src String and return the start
     * index of the finding.
     *
     * @param src The source String to search through.
     * @param matcher The Matcher instance that searches for the Pattern in the src String.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, Matcher matcher) {
        return indexOf(src, 0, matcher);
    }

    /**
     * Search for the next occurrence of the Pattern within the src String and return the start
     * index of the finding.
     *
     * @param src The source String to search through.
     * @param start The index to start the search at.
     * @param regex The regex pattern to search for in the src String.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, int start, String regex) {
        Pattern pattern = Pattern.compile(regex);

        return indexOf(src, start, pattern);
    }

    /**
     * Search for the next occurrence of the Pattern within the src String and return the start
     * index of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param pattern The Pattern to search for in the src String.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, int start, Pattern pattern) {
        Matcher matcher = pattern.matcher(src);

        return indexOf(src, start, matcher);
    }

    /**
     * Search for the next occurrence of the Pattern within the src String and return the start
     * index of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param matcher The Matcher instance that searches for the Pattern in the src String.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, int start, Matcher matcher) {
        if (matcher.find(start)) {
            return matcher.start();
        }

        return -1;
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, char c) {
        return indexOf(src, 0, c);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, int start, char c) {
        return indexOf(src, start, c, null, null, 1);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, char c, char excludePrefixes[], char excludePostfixes[]) {
        return indexOf(src, 0, c, excludePrefixes, excludePostfixes, 1);
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @return The start index of the last possible occurrence, if any.
     */
    public static int lastIndexOf(String src, char c) {
        return lastIndexOf(src, src.length() - 1, c);
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @return The start index of the last possible occurrence, if any.
     */
    public static int lastIndexOf(String src, int start, char c) {
        return indexOf(src, start, c, null, null, -1);
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @return The start index of the last possible occurrence, if any.
     */
    public static int lastIndexOf(String src, char c, char excludePrefixes[],
        char excludePostfixes[]) {
        return indexOf(src, src.length() - 1, c, excludePrefixes, excludePostfixes, -1);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @param direction The direction to search for the match in.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, int start, char c, int direction) {
        return indexOf(src, start, c, null, null, direction);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param direction The direction to search for the match in.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, char c, char excludePrefixes[], char excludePostfixes[],
        int direction) {
        return indexOf(src, 0, c, excludePrefixes, excludePostfixes, direction);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param direction The direction to search for the match in.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, int start, char c, char excludePrefixes[],
        char excludePostfixes[], int direction) {
        return indexOf(src, start, c, excludePrefixes, excludePostfixes, null, null, null, null,
            direction);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int lastIndexOf(String src, char c, char excludePrefixes[],
        char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[],
        boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[]) {
        return indexOf(src, src.length() - 1, c, excludePrefixes, excludePostfixes, excludeBinary,
            excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, -1);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, char c, char excludePrefixes[], char excludePostfixes[],
        char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[],
        boolean excludeBinaryBackslash[]) {
        return indexOf(src, 0, c, excludePrefixes, excludePostfixes, excludeBinary,
            excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, 1);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @param direction The direction to search for the match in.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, char c, char excludePrefixes[], char excludePostfixes[],
        char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[],
        boolean excludeBinaryBackslash[], int direction) {
        return indexOf(src, 0, c, excludePrefixes, excludePostfixes, excludeBinary,
            excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, direction);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, int start, char c, char excludePrefixes[],
        char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[],
        boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[]) {
        return indexOf(src, start, c, excludePrefixes, excludePostfixes, excludeBinary,
            excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, 1);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOfExcludeText(String src, int start, char c) {
        return indexOf(src, start, c, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOfExcludeTextAndParentheses(String src, int start, char c) {
        return indexOf(src, start, c, new char[] {'('}, new char[] {')'}, new char[] {'"'},
            new boolean[] {false}, new boolean[] {false}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOfExcludeText(String src, char c) {
        return indexOf(src, 0, c, new char[] {}, new char[] {}, new char[] {'"'}, new boolean[] {},
            new boolean[] {}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOfExcludeTextAndParentheses(String src, char c) {
        return indexOf(src, 0, c, new char[] {'('}, new char[] {')'}, new char[] {'"'},
            new boolean[] {false}, new boolean[] {false}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given characters 'cs' in the src String and return the
     * start index of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param cs The chars to search for.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOfExcludeText(String src, int start, char cs[]) {
        return indexOf(src, start, cs, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given characters 'cs' in the src String and return the
     * start index of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param cs The chars to search for.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOfExcludeTextAndParentheses(String src, int start, char cs[]) {
        return indexOf(src, start, cs, new char[] {'('}, new char[] {')'}, new char[] {'"'},
            new boolean[] {false}, new boolean[] {false}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given characters 'cs' in the src String and return the
     * start index of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param cs The chars to search for.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOfExcludeText(String src, char cs[]) {
        return indexOf(src, 0, cs, new char[] {}, new char[] {}, new char[] {'"'}, new boolean[] {},
            new boolean[] {}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given characters 'cs' in the src String and return the
     * start index of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param cs The chars to search for.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOfExcludeTextAndParentheses(String src, char cs[]) {
        return indexOf(src, 0, cs, new char[] {'('}, new char[] {')'}, new char[] {'"'},
            new boolean[] {false}, new boolean[] {false}, new boolean[] {true});
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * start index of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @return The start index of the last possible occurrence, if any.
     */
    public static int lastIndexOfExcludeText(String src, int start, char c) {
        return indexOf(src, start, c, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true}, -1);
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * start index of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @return The start index of the last possible occurrence, if any.
     */
    public static int lastIndexOfExcludeTextAndParentheses(String src, int start, char c) {
        return indexOf(src, start, c, new char[] {'('}, new char[] {')'}, new char[] {'"'},
            new boolean[] {false}, new boolean[] {false}, new boolean[] {true}, -1);
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * start index of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @return The start index of the last possible occurrence, if any.
     */
    public static int lastIndexOfExcludeText(String src, char c) {
        return indexOf(src, src.length() - 1, c, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true});
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * start index of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @return The start index of the last possible occurrence, if any.
     */
    public static int lastIndexOfExcludeTextAndParentheses(String src, char c) {
        return indexOf(src, src.length() - 1, c, new char[] {'('}, new char[] {')'},
            new char[] {'"'}, new boolean[] {false}, new boolean[] {false}, new boolean[] {true},
            -1);
    }

    /**
     * Search for the last occurrence of the given characters 'cs' in the src String and return the
     * start index of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param cs The chars to search for.
     * @return The start index of the last possible occurrence, if any.
     */
    public static int lastIndexOfExcludeText(String src, int start, char cs[]) {
        return indexOf(src, start, cs, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true}, -1);
    }

    /**
     * Search for the last occurrence of the given characters 'cs' in the src String and return the
     * start index of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param cs The chars to search for.
     * @return The start index of the last possible occurrence, if any.
     */
    public static int lastIndexOfExcludeTextAndParentheses(String src, int start, char cs[]) {
        return indexOf(src, start, cs, new char[] {'('}, new char[] {')'}, new char[] {'"'},
            new boolean[] {false}, new boolean[] {false}, new boolean[] {true}, -1);
    }

    /**
     * Search for the last occurrence of the given characters 'cs' in the src String and return the
     * start index of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param cs The chars to search for.
     * @return The start index of the last possible occurrence, if any.
     */
    public static int lastIndexOfExcludeText(String src, char cs[]) {
        return indexOf(src, src.length() - 1, cs, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true}, -1);
    }

    /**
     * Search for the last occurrence of the given characters 'cs' in the src String and return the
     * start index of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param cs The chars to search for.
     * @return The start index of the last possible occurrence, if any.
     */
    public static int lastIndexOfExcludeTextAndParentheses(String src, char cs[]) {
        return indexOf(src, src.length() - 1, cs, new char[] {'('}, new char[] {')'},
            new char[] {'"'}, new boolean[] {false}, new boolean[] {false}, new boolean[] {true},
            -1);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @param direction The direction to search for the match in.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, int start, char c, char excludePrefixes[],
        char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[],
        boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[], int direction) {
        return indexOf(src, start, new char[] {c}, excludePrefixes, excludePostfixes, excludeBinary,
            excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, direction);
    }

    /**
     * Search for the next occurrence of the given characters 'cs' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param cs The chars to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, int start, char cs[], char excludePrefixes[],
        char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[],
        boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[]) {
        return indexOf(src, start, cs, excludePrefixes, excludePostfixes, excludeBinary,
            excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, 1);
    }

    /**
     * Search for the next occurrence of the given characters 'cs' in the src String and return the
     * start index of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param cs The chars to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @param direction The direction to search for the match in.
     * @return The start index of the next possible occurrence, if any.
     */
    public static int indexOf(String src, int start, char cs[], char excludePrefixes[],
        char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[],
        boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[], int direction) {
        return boundsOf(src, start, cs, excludePrefixes, excludePostfixes, excludeBinary,
            excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, direction)
                .getStart();
    }

    /**
     * Search for the next occurrence of the Pattern within the src String and return the Bounds of
     * the finding.
     *
     * @param src The source String to search through.
     * @param regex The regex pattern to search for in the src String.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, String regex) {
        return boundsOf(src, 0, regex);
    }

    /**
     * Search for the next occurrence of the Pattern within the src String and return the Bounds of
     * the finding.
     *
     * @param src The source String to search through.
     * @param pattern The Pattern to search for in the src String.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, Pattern pattern) {
        return boundsOf(src, 0, pattern);
    }

    /**
     * Search for the next occurrence of the Pattern within the src String and return the Bounds of
     * the finding.
     *
     * @param src The source String to search through.
     * @param matcher The Matcher instance that searches for the Pattern in the src String.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, Matcher matcher) {
        return boundsOf(src, 0, matcher);
    }

    /**
     * Search for the next occurrence of the Pattern within the src String and return the Bounds of
     * the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param regex The regex pattern to search for in the src String.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, int start, String regex) {
        Pattern pattern = Pattern.compile(regex);

        return boundsOf(src, start, pattern);
    }

    /**
     * Search for the next occurrence of the Pattern within the src String and return the Bounds of
     * the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param pattern The Pattern to search for in the src String.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, int start, Pattern pattern) {
        Matcher matcher = pattern.matcher(src);

        return boundsOf(src, start, matcher);
    }

    /**
     * Search for the next occurrence of the Pattern within the src String and return the Bounds of
     * the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param matcher The Matcher instance that searches for the Pattern in the src String.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, int start, Matcher matcher) {
        if (matcher.find(start)) {
            return new Bounds(matcher.start(), matcher.end());
        }

        return Bounds.EMPTY;
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, char c) {
        return boundsOf(src, 0, c);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, int start, char c) {
        return boundsOf(src, start, c, null, null, 1);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, char c, char excludePrefixes[],
        char excludePostfixes[]) {
        return boundsOf(src, 0, c, excludePrefixes, excludePostfixes, 1);
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @return The Bounds of the last possible occurrence, if any.
     */
    public static Bounds lastBoundsOf(String src, char c) {
        return lastBoundsOf(src, src.length() - 1, c);
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @return The Bounds of the last possible occurrence, if any.
     */
    public static Bounds lastBoundsOf(String src, int start, char c) {
        return boundsOf(src, start, c, null, null, -1);
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @return The Bounds of the last possible occurrence, if any.
     */
    public static Bounds lastBoundsOf(String src, char c, char excludePrefixes[],
        char excludePostfixes[]) {
        return boundsOf(src, src.length() - 1, c, excludePrefixes, excludePostfixes, -1);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @param direction The direction to search for the match in.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, int start, char c, int direction) {
        return boundsOf(src, start, c, null, null, direction);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param direction The direction to search for the match in.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, char c, char excludePrefixes[],
        char excludePostfixes[], int direction) {
        return boundsOf(src, 0, c, excludePrefixes, excludePostfixes, direction);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param direction The direction to search for the match in.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, int start, char c, char excludePrefixes[],
        char excludePostfixes[], int direction) {
        return boundsOf(src, start, c, excludePrefixes, excludePostfixes, null, null, null, null,
            direction);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds lastBoundsOf(String src, char c, char excludePrefixes[],
        char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[],
        boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[]) {
        return boundsOf(src, src.length() - 1, c, excludePrefixes, excludePostfixes, excludeBinary,
            excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, -1);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, char c, char excludePrefixes[],
        char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[],
        boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[]) {
        return boundsOf(src, 0, c, excludePrefixes, excludePostfixes, excludeBinary,
            excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, 1);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @param direction The direction to search for the match in.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, char c, char excludePrefixes[],
        char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[],
        boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[], int direction) {
        return boundsOf(src, 0, c, excludePrefixes, excludePostfixes, excludeBinary,
            excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, direction);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, int start, char c, char excludePrefixes[],
        char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[],
        boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[]) {
        return boundsOf(src, start, c, excludePrefixes, excludePostfixes, excludeBinary,
            excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, 1);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOfExcludeText(String src, int start, char c) {
        return boundsOf(src, start, c, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOfExcludeTextAndParentheses(String src, int start, char c) {
        return boundsOf(src, start, c, new char[] {'('}, new char[] {')'}, new char[] {'"'},
            new boolean[] {false}, new boolean[] {false}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOfExcludeText(String src, char c) {
        return boundsOf(src, 0, c, new char[] {}, new char[] {}, new char[] {'"'}, new boolean[] {},
            new boolean[] {}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOfExcludeTextAndParentheses(String src, char c) {
        return boundsOf(src, 0, c, new char[] {'('}, new char[] {')'}, new char[] {'"'},
            new boolean[] {false}, new boolean[] {false}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given characters 'cs' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param cs The chars to search for.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOfExcludeText(String src, int start, char cs[]) {
        return boundsOf(src, start, cs, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given characters 'cs' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param cs The chars to search for.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOfExcludeTextAndParentheses(String src, int start, char cs[]) {
        return boundsOf(src, start, cs, new char[] {'('}, new char[] {')'}, new char[] {'"'},
            new boolean[] {false}, new boolean[] {false}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given characters 'cs' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param cs The chars to search for.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOfExcludeText(String src, char cs[]) {
        return boundsOf(src, 0, cs, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true});
    }

    /**
     * Search for the next occurrence of the given characters 'cs' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param cs The chars to search for.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOfExcludeTextAndParentheses(String src, char cs[]) {
        return boundsOf(src, 0, cs, new char[] {'('}, new char[] {')'}, new char[] {'"'},
            new boolean[] {false}, new boolean[] {false}, new boolean[] {true});
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @return The Bounds of the last possible occurrence, if any.
     */
    public static Bounds lastBoundsOfExcludeText(String src, int start, char c) {
        return boundsOf(src, start, c, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true}, -1);
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @return The Bounds of the last possible occurrence, if any.
     */
    public static Bounds lastBoundsOfExcludeTextAnd(String src, int start, char c) {
        return boundsOf(src, start, c, new char[] {'('}, new char[] {')'}, new char[] {'"'},
            new boolean[] {false}, new boolean[] {false}, new boolean[] {true}, -1);
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @return The Bounds of the last possible occurrence, if any.
     */
    public static Bounds lastBoundsOfExcludeText(String src, char c) {
        return boundsOf(src, src.length() - 1, c, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true});
    }

    /**
     * Search for the last occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param c The char to search for.
     * @return The Bounds of the last possible occurrence, if any.
     */
    public static Bounds lastBoundsOfExcludeTextAndParentheses(String src, char c) {
        return boundsOf(src, src.length() - 1, c, new char[] {'('}, new char[] {')'},
            new char[] {'"'}, new boolean[] {false}, new boolean[] {false}, new boolean[] {true},
            -1);
    }

    /**
     * Search for the last occurrence of the given characters 'cs' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param cs The chars to search for.
     * @return The Bounds of the last possible occurrence, if any.
     */
    public static Bounds lastBoundsOfExcludeText(String src, int start, char cs[]) {
        return boundsOf(src, start, cs, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true}, -1);
    }

    /**
     * Search for the last occurrence of the given characters 'cs' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param cs The chars to search for.
     * @return The Bounds of the last possible occurrence, if any.
     */
    public static Bounds lastBoundsOfExcludeTextAndParentheses(String src, int start, char cs[]) {
        return boundsOf(src, start, cs, new char[] {'('}, new char[] {')'}, new char[] {'"'},
            new boolean[] {false}, new boolean[] {false}, new boolean[] {true}, -1);
    }

    /**
     * Search for the last occurrence of the given characters 'cs' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within double quotes.
     *
     * @param src The source String to search through.
     * @param cs The chars to search for.
     * @return The Bounds of the last possible occurrence, if any.
     */
    public static Bounds lastBoundsOfExcludeText(String src, char cs[]) {
        return boundsOf(src, src.length() - 1, cs, new char[] {}, new char[] {}, new char[] {'"'},
            new boolean[] {}, new boolean[] {}, new boolean[] {true}, -1);
    }

    /**
     * Search for the last occurrence of the given characters 'cs' in the src String and return the
     * Bounds of the finding. The occurrence cannot be within parentheses or double quotes.
     *
     * @param src The source String to search through.
     * @param cs The chars to search for.
     * @return The Bounds of the last possible occurrence, if any.
     */
    public static Bounds lastBoundsOfExcludeTextAndParentheses(String src, char cs[]) {
        return boundsOf(src, src.length() - 1, cs, new char[] {'('}, new char[] {')'},
            new char[] {'"'}, new boolean[] {false}, new boolean[] {false}, new boolean[] {true},
            -1);
    }

    /**
     * Search for the next occurrence of the given character 'c' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param c The char to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @param direction The direction to search for the match in.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, int start, char c, char excludePrefixes[],
        char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[],
        boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[], int direction) {
        return boundsOf(src, start, new char[] {c}, excludePrefixes, excludePostfixes,
            excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash,
            direction);
    }

    /**
     * Search for the next occurrence of the given characters 'cs' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param cs The chars to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, int start, char cs[], char excludePrefixes[],
        char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[],
        boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[]) {
        return boundsOf(src, start, cs, excludePrefixes, excludePostfixes, excludeBinary,
            excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, 1);
    }

    /**
     * Search for the next occurrence of the given characters 'cs' in the src String and return the
     * Bounds of the finding.
     *
     * @param src The source String to search through.
     * @param start The start index of the search.
     * @param cs The chars to search for.
     * @param excludePrefixes Prefix chars to exclude from the search. e.g. '('
     * @param excludePostfixes Postfix chars to exclude from the search. e.g. ')'
     * @param excludeBinary Binary chars to exclude from the search. e.g. '"'
     * @param excludePrefixBackslash Prefix chars to escape check before excluding. e.g. '(' and
     *        "\(" would be skipped over.
     * @param excludePostfixBackslash Postfix chars to escape check before excluding. e.g. ')' and
     *        "\)" would be skipped over.
     * @param excludeBinaryBackslash Binary chars to escape check before excluding. e.g. '"' and
     *        "\"" would be skipped over.
     * @param direction The direction to search for the match in.
     * @return The Bounds of the next possible occurrence, if any.
     */
    public static Bounds boundsOf(String src, int start, char cs[], char excludePrefixes[],
        char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[],
        boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[], int direction) {
        if (excludePrefixes == null) {
            excludePrefixes = new char[0];
        }
        if (excludePostfixes == null) {
            excludePostfixes = new char[0];
        }
        if (excludeBinary == null) {
            excludeBinary = new char[0];
        }
        if (excludePrefixBackslash == null) {
            excludePrefixBackslash = new boolean[excludePrefixes.length];
        }
        if (excludePostfixBackslash == null) {
            excludePostfixBackslash = new boolean[excludePostfixes.length];
        }

        int first = start;

        int excluding = 0;

        int i = first;

        while (i < src.length() && i >= 0) {
            char c = src.charAt(i);

            int p = 0;

            if ((p = StringUtils.searchChar(excludePrefixes, c)) >= 0) {
                if (i == 0 || !excludePrefixBackslash[p] || src.charAt(i - 1) != '\\') {
                    i = StringUtils.findEndingMatch(src, i, excludePrefixes[p],
                        excludePostfixes[p]);

                    if (i < 0) {
                        break;
                    }
                }
            } else if (StringUtils.searchChar(excludeBinary, c) >= 0) {
                if (i == 0 || !StringUtils.containsChar(excludeBinary, src.charAt(i - 1))) {
                    i = StringUtils.findEndingChar(src, c, i, direction);

                    if (i < 0) {
                        break;
                    }
                }
            } else if (excluding == 0 && i >= start && StringUtils.containsChar(cs, c)) {
                return new Bounds(i, i);
            }

            i += direction;
        }

        return Bounds.EMPTY;
    }

    /**
     * Get the list of all of the occurrences of the Regex pattern from the given String and
     * Pattern. Return all the occurrences in a String array.
     *
     * @param input The String to search for the occurrences within.
     * @param pattern The Pattern to search the input String with.
     * @return A String array of the occurrences from the Pattern.
     */
    public static String[] findAll(String input, Pattern pattern) {
        Matcher matcher = pattern.matcher(input);

        return findAll(matcher);
    }

    /**
     * Get the list of all of the occurrences of the Regex pattern from the given matcher. Return
     * all the occurrences in a String array.
     *
     * @param matcher The matcher to get the occurrences from.
     * @return A String array of the occurrences from the matcher.
     */
    public static String[] findAll(Matcher matcher) {
        ArrayList<String> list = new ArrayList<String>();

        while (matcher.find()) {
            list.add(matcher.group());
        }

        String stringArray[] = list.toArray(new String[0]);

        return stringArray;
    }

    /**
     * Get the sign of the integer. Possible return values:
     * <ul>
     * <li>num > 0 returns 1</li>
     * <li>num == 0 returns 0</li>
     * <li>num < 0 returns -1</li>
     * </ul>
     *
     * @param num The number to get the sign of.
     * @return The sign of the given integer.
     */
    private static int sign(int num) {
        if (num > 0) {
            return 1;
        } else if (num < 0) {
            return -1;
        }

        return num;
    }
}

