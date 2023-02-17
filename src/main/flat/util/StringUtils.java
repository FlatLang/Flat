package flat.util;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * Class that contains methods used for finding data about
 * a String.
 *
 * @author Braden Steffaniak
 * @since v0.1 Mar 13, 2014 at 9:38:42 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class StringUtils {
    public static final char WHITESPACE[] = new char[]{' ', '\n', '\t', '\r'};
    public static final char SYMBOLS_CHARS[] = new char[]{'-', '+', '~', '!', '=', '%', '^', '&', '|', '*', '/', '\\', '>', '<', ',', '"', '\'', '[', ']', '{', '}', ';', ':', '?', '(', ')'};
    public static final char STMT_PRE_CONT_CHARS[] = new char[]{'-', '+', '~', '!', '=', '%', '^', '&', '|', '*', '/', '\\', '>', '<', ',', '.', '[', '?', ':'};
    public static final char STMT_POST_CONT_CHARS[] = new char[]{'-', '+', '~', '!', '=', '%', '^', '&', '|', '*', '/', '\\', '>', '<', ',', '.', ']', '?', ':'};
    public static final char INVALID_DECLARATION_CHARS[] = new char[]{'-', '+', '~', '!', '=', '%', '^', '|', '/', '\\', '"', '\'', '{', '}', ';', '(', ')'};

    public static final char[] SCOPE_CHECKS_ALL = new char[]{'"', '\'', '(', '['};

    public static final String SYMBOLS[];

    static {
        SYMBOLS = new String[SYMBOLS_CHARS.length];

        for (int i = 0; i < SYMBOLS.length; i++) {
            SYMBOLS[i] = SYMBOLS_CHARS[i] + "";
        }
    }

    /**
     * Get the next sequence of symbols, if they are symbols.<br>
     * For example:
     * <blockquote><pre>
     * // Scenario 1
     * findGroupedSymbols("number = ++num2", 9);
     *
     * // Scenario 2
     * findGroupedSymbols("number = ++num2", 8);</pre></blockquote>
     * Scenario 1 returns the "++" String and scenario 2 returns ""
     * because the index that it starts at is whitespace, not a
     * symbol.
     *
     * @param str   The String to search through for the symbols.
     * @param start The index to start the search at.
     * @return A String containing the grouped chars. If there
     * were no grouped chars, then an empty String is returned.
     */
    public static String findGroupedSymbols(CharSequence str, int start) {
        return findGroupedSymbols(str, start, 1);
    }

    /**
     * Get the next sequence of symbols, if they are symbols.<br>
     * For example:
     * <blockquote><pre>
     * // Scenario 1
     * findGroupedSymbols("number = ++num2", 9, 1);
     *
     * // Scenario 2
     * findGroupedSymbols("number = ++num2", 10, -1);</pre></blockquote>
     * Scenario 1 and 2 both return the "++" String because they are
     * searching the same symbols at opposite ends and directions.
     *
     * @param str       The String to search through for the symbols.
     * @param start     The index to start the search at.
     * @param direction The direction to search for the symbols at.
     * @return A String containing the grouped chars. If there
     * were no grouped chars, then an empty String is returned.
     */
    public static String findGroupedSymbols(CharSequence str, int start, int direction) {
        return findGroupedChars(str, SYMBOLS_CHARS, start, direction);
    }

    /**
     * Get the next sequence of characters, if they are contained within
     * the given chars array.<br>
     * For example:
     * <blockquote><pre>
     * // Scenario 1
     * findGroupedChars("number = ++num2", StringUtils.SYMBOLS_CHARS, 9, 1);
     *
     * // Scenario 2
     * findGroupedChars("number = ++num2", StringUtils.SYMBOLS_CHARS, 10, -1);</pre></blockquote>
     * Scenario 1 and 2 both return the "++" String because they are
     * searching the same symbols at opposite ends and directions.
     *
     * @param str       The String to search through for the characters.
     * @param chars     The chars to search for.
     * @param start     The index to start the search at.
     * @param direction The direction to search for the characters at.
     * @return A String containing the grouped chars. If there
     * were no grouped chars, then an empty String is returned.
     */
    public static String findGroupedChars(CharSequence str, char chars[], int start, int direction) {
        int index = start;

        while (index < str.length() && index >= 0 && StringUtils.containsChar(chars, str.charAt(index))) {
            index += direction;
        }

        if (direction < 0) {
            return str.subSequence(index + 1, start + 1).toString();
        }

        return str.subSequence(start, index).toString();
    }

    /**
     * Get the Bounds of the next sequence of characters, if they are
     * contained within the given chars array.<br>
     * For example:
     * <blockquote><pre>
     * // Scenario 1
     * findGroupedCharsBounds("number = ++num2", StringUtils.SYMBOLS_CHARS, 9);
     *
     * // Scenario 2
     * findGroupedCharsBounds("number = ++num2", StringUtils.SYMBOLS_CHARS, 2);</pre></blockquote>
     * Scenario 1 returns a Bounds containing [9, 11].<br>
     * Scenario 2 returns a Bounds containing [-1, -1], In other words
     * Bounds.EMPTY.
     *
     * @param source The String to search through for the characters to
     *               find the Bounds from.
     * @param start  The index to start the search at.
     * @param types  Character arrays containing valid characters to search
     *               for.
     * @return The Bounds of the location that the characters were found
     * at in the given source String.
     */
    public static Bounds findGroupedCharsBounds(String source, int start, char[]... types) {
        return findGroupedCharsBounds(source, start, 1, false, types);
    }

    /**
     * Get the Bounds of the next sequence of characters, if they are
     * contained within the given chars array.<br>
     * For example:
     * <blockquote><pre>
     * // Scenario 1
     * findGroupedCharsBounds("number = ++num2", 9, 1, false, StringUtils.SYMBOLS_CHARS);
     *
     * // Scenario 2
     * findGroupedCharsBounds("number = ++num2", 0, 1, true, StringUtils.SYMBOLS_CHARS);</pre></blockquote>
     * Scenario 1 returns a Bounds containing [9, 11].<br>
     * Scenario 2 returns a Bounds containing [0, 7].
     *
     * @param source    The String to search through for the characters to
     *                  find the Bounds from.
     * @param start     The index to start the search at.
     * @param direction The direction to search for the characters in.
     * @param opposite  Whether to search for or against the data in the
     *                  types array.
     * @param types     Character arrays containing valid characters to search
     *                  for.
     * @return The Bounds of the location that the characters were found
     * at in the given source String.
     */
    public static Bounds findGroupedCharsBounds(String source, int start, int direction, boolean opposite, char[]... types) {
        int index = start;

        while (index >= 0 && index < source.length()) {
            char c = source.charAt(index);

            if (containsChar(types, c) == opposite) {
                index -= direction;

                break;
            }

            index += direction;
        }

        if (index != start && index > start == direction > 0) {
            if (index < 0) {
                index = 0;
            }

            if (direction > 0) {
                return new Bounds(start, index);
            } else {
                return new Bounds(index, start + 1);
            }
        }

        return Bounds.EMPTY;
    }

    public static boolean containsWords(String source) {
        return findNumWords(source) > 0;
    }

    public static boolean containsMultipleWords(String source) {
        return findNumWords(source) > 1;
    }

    public static int findNumWords(String source) {
        return findNumWords(source, 0);
    }

    public static int findNumWords(String source, int start) {
        int num = 0;

        while (start < source.length()) {
            String word = findNextWord(source, start);

            if (word != null) {
                num++;
                start += word.length() + 1;
            }
        }

        return num;
    }

    public static String trimFirstWord(String source) {
        return trimFirstWord(source, false);
    }

    public static String trimFirstWord(String source, boolean trimTrailingWhitespace) {
        int index = StringUtils.findNextWhitespaceIndex(source, 0);

        if (trimTrailingWhitespace) {
            index = StringUtils.findNextNonWhitespaceIndex(source, index + 1);

            if (index < 0) {
                index = source.length();
            }
        }

        return source.substring(index, source.length());
    }

    /**
     * Find the index of the next word in the given source String starting
     * at the start of the source String.
     *
     * @param source The source String to search for the next word in.
     * @param word   The word to search for.
     * @return The index of the word. If the word was not found, -1 is
     * returned.
     */
    public static int findWordIndex(String source, String word) {
        return findWordIndex(source, word, 0);
    }

    /**
     * Find the index of the next word in the given source String starting
     * at the given start index.
     *
     * @param source The source String to search for the next word in.
     * @param word   The word to search for.
     * @param start  The index to start the search at.
     * @return The index of the word. If the word was not found, -1 is
     * returned.
     */
    public static int findWordIndex(String source, String word, int start) {
        return findWordIndex(source, word, start, 1);
    }

    /**
     * Find the index of the last instance of the given word in the given
     * source String.
     *
     * @param source The source String to search for the next word in.
     * @param word   The word to search for.
     * @return The index of the word. If the word was not found, -1 is
     * returned.
     */
    public static int findLastWordIndex(String source, String word) {
        return findWordIndex(source, word, source.length() - 1, -1);
    }

    /**
     * Find the index of the next word in the given source String starting
     * at the given start index.
     *
     * @param source    The source String to search for the next word in.
     * @param word      The word to search for.
     * @param start     The index to start the search at.
     * @param direction The direction to search for the word in.
     * @return The index of the word. If the word was not found, -1 is
     * returned.
     */
    public static int findWordIndex(String source, String word, int start, int direction) {
        String current = findNextWord(source, start, direction);

        while (current != null) {
            int i = findIndex(source, current, start, direction);

            if (current.equals(word)) {
                return i;
            }

            start = i;

            if (direction > 0) {
                start += current.length() + 1;
            } else {
                start -= 1;
            }

            current = findNextWord(source, start, direction);
        }

        return -1;
    }

    /**
     * Find the index of the given word in the given source String.
     *
     * @param source    The source String to search for the word in.
     * @param word      The word to search for.
     * @param start     The index to start the search at.
     * @param direction The direction to search for the word in.
     * @return The index of the found word. If the word is not found,
     * -1 is returned.
     */
    private static int findIndex(String source, String word, int start, int direction) {
        if (direction > 0) {
            return source.indexOf(word, start);
        } else {
            return source.lastIndexOf(word, start);
        }
    }

    public static boolean startsWithWord(CharSequence source, String word) {
        Bounds bounds = findNextWordBounds(source);

        return bounds.isValid() && bounds.getStart() == 0 && bounds.extractString(source.toString()).equals(word);
    }

    public static Bounds findWordBounds(CharSequence source, String word) {
        String s = source.toString();

        Bounds bounds = findNextWordBounds(s);

        while (bounds.isValid()) {
            if (bounds.extractString(s).equals(word)) {
                return bounds;
            }

            bounds = findNextWordBounds(s, bounds.getEnd() + 1);
        }

        return Bounds.EMPTY;
    }

    public static boolean containsWord(CharSequence source, String word) {
        return findWordBounds(source, word).isValid();
    }

    /**
     * Find the next word in the given source starting at the beginning
     * of the String. If there are no words available, an empty String is
     * returned.<br>
     * For example:
     * <blockquote><pre>
     * // Scenario 1
     * findNextWord("number = ++num2");
     *
     * // Scenario 2
     * findNextWord("asdf = ++num2");</pre></blockquote>
     * Scenario 1 returns "number"<br>
     * Scenario 2 returns "asdf"
     *
     * @param source The source to search within.
     * @return The next available word if available, else it returns
     * an empty String.
     */
    public static String findNextWord(CharSequence source) {
        return findNextWord(source, 0);
    }

    /**
     * Find the next word in the given source starting at the given
     * index. If there are no words available, an empty String is
     * returned.<br>
     * For example:
     * <blockquote><pre>
     * // Scenario 1
     * findNextWord("number = ++num2", 0);
     *
     * // Scenario 2
     * findNextWord("number = ++num2", 8);</pre></blockquote>
     * Scenario 1 returns "number"<br>
     * Scenario 2 returns "num2"
     *
     * @param source The source to search within.
     * @param start  The index to start the search.
     * @return The next available word if available, else it returns
     * an empty String.
     */
    public static String findNextWord(CharSequence source, int start) {
        return findNextWord(source, start, 1);
    }

    /**
     * Find the next word in the given source starting at the given
     * index. If there are no words available, an empty String is
     * returned.<br>
     * For example:
     * <blockquote><pre>
     * // Scenario 1
     * findNextWord("number = ++num2", 0, 1);
     *
     * // Scenario 2
     * findNextWord("number = ++num2", 8, -1);</pre></blockquote>
     * Scenario 1 and 2 both return "number"
     *
     * @param source    The source to search within.
     * @param start     The index to start the search.
     * @param direction The direction to search for the word in.
     * @return The next available word if available, else it returns
     * an empty String.
     */
    public static String findNextWord(CharSequence source, int start, int direction) {
        Bounds bounds = findNextWordBounds(source, start, direction);

        if (bounds == null) {
            return null;
        }

        return bounds.extractString(source.toString());
    }

    public static Bounds findNextWordBounds(CharSequence source) {
        return findNextWordBounds(source, 0);
    }

    public static Bounds findNextWordBounds(CharSequence source, int start) {
        return findNextWordBounds(source, start, 1);
    }

    public static Bounds findNextWordBounds(CharSequence source, int start, int direction) {
        if (start < 0 || start >= source.length()) {
            return Bounds.EMPTY;
        }

        start = StringUtils.findNextLetterIndex(source, start, direction, false, true);

        int index = StringUtils.findNextLetterIndex(source, start + direction, direction, true, true);

        if (direction < 0) {
            if (index == 0) {
                index--;
            }

            return new Bounds(index + 1, start + 1);
        }

        return new Bounds(start, index);
    }

    /**
     * Find the next index in which a letter resides, starting at the
     * given index. If there are no letters available, -1 is returned.<br>
     * <br>
     * A letter is anything besides whitespace and symbols.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * // Scenario 1
     * findNextWord("number = ++num2", 0, 1);
     *
     * // Scenario 2
     * findNextWord("number = ++num2", 8, -1);</pre></blockquote>
     * Scenario 1 returns 0<br>
     * Scenario 2 returns 5
     *
     * @param source    The source to search within.
     * @param start     The index to start the search.
     * @param direction The direction to search for the word in.
     * @return The next index in which a letter resides. If no letters
     * are found, -1 is returned.
     */
    public static int findNextLetterIndex(CharSequence source, int start, int direction) {
        return findNextLetterIndex(source, start, direction, false);
    }

    /**
     * Find the next index in which a letter resides, starting at the
     * given index. If there are no letters available, -1 is returned.
     * However, if the <u>opposite</u> param is true, then the opposite
     * of what was stated above is done.<br>
     * <br>
     * A letter is anything besides whitespace and symbols.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * // Scenario 1
     * findNextWord("number = ++num2", 0, 1, true);
     *
     * // Scenario 2
     * findNextWord("number = ++num2", 8, -1, true);</pre></blockquote>
     * Scenario 1 returns 6<br>
     * Scenario 2 returns 8
     *
     * @param source    The source to search within.
     * @param start     The index to start the search.
     * @param direction The direction to search for the word in.
     * @param opposite  Whether or not to search for anything that is NOT
     *                  a letter (instead of searching for a letter).
     * @return The next index in which a letter resides. If no letters
     * are found, -1 is returned.
     */
    public static int findNextLetterIndex(CharSequence source, int start, int direction, boolean opposite) {
        return findNextLetterIndex(source, start, direction, opposite, false);
    }

    /**
     * Find the next index in which a letter resides, starting at the
     * given index. If there are no letters available, -1 is returned.
     * However, if the <u>opposite</u> param is true, then the opposite
     * of what was stated above is done. Passing true for the <u>bound</u>
     * parameter makes sure that -1 is never returned (It will return the
     * end-point of whichever direction that the method was searching
     * in).<br>
     * <br>
     * A letter is anything besides whitespace and symbols.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * // Scenario 1
     * findNextWord("number = ++num2", 11, 1, true);
     *
     * // Scenario 2
     * findNextWord("number = ++num2", 4, -1, true, true);</pre></blockquote>
     * Scenario 1 returns 15<br>
     * Scenario 2 returns 0
     *
     * @param source    The source to search within.
     * @param start     The index to start the search.
     * @param direction The direction to search for the word in.
     * @param opposite  Whether or not to search for anything that is NOT
     *                  a letter (instead of searching for a letter).
     * @param bound     Whether or not to bound the result returned if the a
     *                  result was not found.
     * @return The next index in which a letter resides.
     */
    public static int findNextLetterIndex(CharSequence source, int start, int direction, boolean opposite, boolean bound) {
        while (start >= 0 && start < source.length()) {
            char c = source.charAt(start);

            if ((containsChar(WHITESPACE, c) || containsChar(SYMBOLS_CHARS, c)) == opposite) {
                return start;
            }

            start += direction;
        }

        if (bound) {
            if (direction > 0) {
                return source.length();
            }

            return 0;
        }

        return -1;
    }

    public static int findEndingMatch(CharSequence str, int index, char startChar) {
        char endChar = 0;

        if (startChar == '(') {
            endChar = ')';
        } else if (startChar == '[') {
            endChar = ']';
        } else if (startChar == '\'') {
            endChar = '\'';
        } else if (startChar == '"') {
            endChar = '"';
        } else {
            return -1;
        }

        return findEndingMatch(str, index, startChar, endChar);
    }

    /**
     * Find the index of the ending char for the match. For instance, to
     * search for an ending parenthesis, starting from the opening
     * parenthesis, you would pass findEndingMatch(str, 0, '(', ')');
     * The method call would return the index of the ending parenthesis
     * that is paired with the index of 0.
     *
     * @param str       The String to search for the pair to the start char.
     * @param index     The index of the start char in the pair.
     * @param startChar The char that starts off the pair. eg. '('
     * @param endChar   The char that ends the pair. eg: ')'
     * @return The index of the pair to the starting char, if no pair is
     * found then -1 is returned.
     */
    public static int findEndingMatch(CharSequence str, int index, char startChar, char endChar) {
        return findEndingMatch(str, index, startChar, endChar, '\0');
    }

    /**
     * Find the index of the ending char for the match. For instance, to
     * search for an ending parenthesis, starting from the opening
     * parenthesis, you would pass findEndingMatch(str, 0, '(', ')');
     * The method call would return the index of the ending parenthesis
     * that is paired with the index of 0.
     *
     * @param str       The String to search for the pair to the start char.
     * @param index     The index of the start char in the pair.
     * @param startChar The char that starts off the pair. eg. '('
     * @param endChar   The char that ends the pair. eg: ')'
     * @param direction The direction in which to search for the match in.
     * @return The index of the pair to the starting char, if no pair is
     * found then -1 is returned.
     */
    public static int findEndingMatch(CharSequence str, int index, char startChar, char endChar, int direction) {
        return findEndingMatch(str, index, startChar, endChar, '\0', direction);
    }

    /**
     * Find the index of the ending char for the match. For instance, to
     * search for an ending parenthesis, starting from the opening
     * parenthesis, you would pass findEndingMatch(str, 0, '(', ')', '\\');
     * <i>(The backslash would act to escape any parentheses. eg: \\(
     * would not be counted.)</i> The method call would return the index
     * of the ending parenthesis that is paired with the index of 0.
     *
     * @param str        The String to search for the pair to the start char.
     * @param index      The index of the start char in the pair.
     * @param startChar  The char that starts off the pair. eg. '('
     * @param endChar    The char that ends the pair. eg: ')'
     * @param escapeChar The char that escapes a start or end char, of
     *                   there is no escape char, pass '(char)0'.
     * @return The index of the pair to the starting char, if no pair is
     * found then -1 is returned.
     */
    public static int findEndingMatch(CharSequence str, int index, char startChar, char endChar, char escapeChar) {
        return findEndingMatch(str, index, startChar, endChar, escapeChar, 1);
    }

    /**
     * Find the index of the ending char for the match. For instance, to
     * search for an ending parenthesis, starting from the opening
     * parenthesis, you would pass findEndingMatch(str, 0, '(', ')', '\\');
     * <i>(The backslash would act to escape any parentheses. eg: \\(
     * would not be counted.)</i> The method call would return the index
     * of the ending parenthesis that is paired with the index of 0.
     *
     * @param str        The String to search for the pair to the start char.
     * @param index      The index of the start char in the pair.
     * @param startChar  The char that starts off the pair. eg. '('
     * @param endChar    The char that ends the pair. eg: ')'
     * @param escapeChar The char that escapes a start or end char, of
     *                   there is no escape char, pass '(char)0'.
     * @param direction  The direction in which to search for the match in.
     * @return The index of the pair to the starting char, if no pair is
     * found then -1 is returned.
     */
    public static int findEndingMatch(CharSequence str, int index, char startChar, char endChar, char escapeChar, int direction) {
        return findEndingMatch(str, index, Character.toString(startChar), Character.toString(endChar), escapeChar, direction);
    }

    /**
     * Find the index of the ending String for the match. For instance, to
     * search for an ending parenthesis, starting from the opening
     * parenthesis, you would pass findEndingMatch(str, 0, "(", ")", '\\');
     * <i>(The backslash would act to escape any parentheses. eg: \\(
     * would not be counted.)</i> The method call would return the index
     * of the ending parenthesis that is paired with the index of 0.
     *
     * @param str   The String to search for the pair to the start String.
     * @param index The index of the start String in the pair.
     * @param start The String that starts off the pair. eg. "("
     * @param end   The String that ends the pair. eg: ")"
     * @return The index of the pair to the starting String, if no pair is
     * found then -1 is returned.
     */
    public static int findEndingMatch(CharSequence str, int index, String start, String end) {
        return findEndingMatch(str, index, start, end, '\0');
    }

    /**
     * Find the index of the ending String for the match. For instance, to
     * search for an ending parenthesis, starting from the opening
     * parenthesis, you would pass findEndingMatch(str, 0, "(", ")", '\\');
     * <i>(The backslash would act to escape any parentheses. eg: \\(
     * would not be counted.)</i> The method call would return the index
     * of the ending parenthesis that is paired with the index of 0.
     *
     * @param str       The String to search for the pair to the start String.
     * @param index     The index of the start String in the pair.
     * @param start     The String that starts off the pair. eg. "("
     * @param end       The String that ends the pair. eg: ")"
     * @param direction The direction in which to search for the match in.
     * @return The index of the pair to the starting String, if no pair is
     * found then -1 is returned.
     */
    public static int findEndingMatch(CharSequence str, int index, String start, String end, int direction) {
        return findEndingMatch(str, index, start, end, '\0', direction);
    }

    /**
     * Find the index of the ending String for the match. For instance, to
     * search for an ending parenthesis, starting from the opening
     * parenthesis, you would pass findEndingMatch(str, 0, "(", ")", '\\');
     * <i>(The backslash would act to escape any parentheses. eg: \\(
     * would not be counted.)</i> The method call would return the index
     * of the ending parenthesis that is paired with the index of 0.
     *
     * @param str        The String to search for the pair to the start String.
     * @param index      The index of the start String in the pair.
     * @param start      The String that starts off the pair. eg. "("
     * @param end        The String that ends the pair. eg: ")"
     * @param escapeChar The char that escapes a start or end char, of
     *                   there is no escape char, pass '(char)0'.
     * @return The index of the pair to the starting String, if no pair is
     * found then -1 is returned.
     */
    public static int findEndingMatch(CharSequence str, int index, String start, String end, char escapeChar) {
        return findEndingMatch(str, index, start, end, escapeChar, 1);
    }

    /**
     * Find the index of the ending String for the match. For instance, to
     * search for an ending parenthesis, starting from the opening
     * parenthesis, you would pass findEndingMatch(str, 0, "(", ")", '\\');
     * <i>(The backslash would act to escape any parentheses. eg: \\(
     * would not be counted.)</i> The method call would return the index
     * of the ending parenthesis that is paired with the index of 0.
     *
     * @param str        The String to search for the pair to the start String.
     * @param index      The index of the start String in the pair.
     * @param start      The String that starts off the pair. eg. "("
     * @param end        The String that ends the pair. eg: ")"
     * @param escapeChar The char that escapes a start or end char, of
     *                   there is no escape char, pass '(char)0'.
     * @param direction  The direction in which to search for the match in.
     * @return The index of the pair to the starting String, if no pair is
     * found then -1 is returned.
     */
    public static int findEndingMatch(CharSequence str, int index, String start, String end, char escapeChar, int direction) {
        if (direction < 0) {
            String temp = start;
            start = end;
            end = temp;
        }

        int scope = 0;

        while (index >= 0 && index < str.length()) {
            char c = str.charAt(index);

            if (c == escapeChar && direction > 0) {
                if (index < str.length() - 1) {
                    if (StringUtils.containsString(str, end, index + 1) || StringUtils.containsString(str, end, index + 1)) {
                        index += end.length();
                    }
                }
            } else if (StringUtils.containsString(str, start, index) && (direction > 0 || index > 0 && str.charAt(index - 1) != escapeChar) && (!start.equals(end) || scope == 0)) {
                scope++;
            } else if (StringUtils.containsString(str, end, index) && (direction > 0 || index > 0 && str.charAt(index - 1) != escapeChar) && (!end.equals(">") || str.charAt(index - 1) != '-')) {
                scope--;

                if (scope == 0) {
                    return index;
                }
            } else if (c == '"') {
                index = findEndingQuote(str, index, direction);

                if (index < 0) {
                    break;
                }
            } else if (c == '\'') {
                index = findEndingChar(str, c, index, direction);

                if (index < 0) {
                    break;
                }
            }

            index += direction;
        }

        return -1;
    }

    /**
     * Find the index of the ending quote, given the index of the start
     * quote.
     *
     * @param value The String to search within.
     * @param start The index of the starting quote.
     * @return The index of the ending quote. If an end is not found, -1
     * is returned instead.
     */
    public static int findEndingQuote(CharSequence value, int start) {
        return findEndingQuote(value, start, 1);
    }

    /**
     * Find the index of the ending quote, given the index of the start
     * quote.
     *
     * @param value     The String to search within.
     * @param start     The index of the starting quote.
     * @param direction The direction in which to search the given String.
     * @return The index of the ending quote. If an end is not found, -1
     * is returned instead.
     */
    public static int findEndingQuote(CharSequence value, int start, int direction) {
        QuadFunction<CharSequence, Character, Integer, Integer, Integer> condition = (str, c, i, dir) -> {
            i += dir;

            if (dir > 0 && i < str.length() - 3 && str.charAt(i) == '#' && !isCharacterEscaped(str, i) && str.charAt(i + 1) == '{') {
                return (i = findEndingMatch(str, i + dir, '{', '}', dir)) >= 0 ? i + dir : i;
            }

            return defaultCharacterCheck(str, c, i - dir, dir);
        };

        return findEndingChar(value, '"', start, direction, condition);
    }

    public static boolean isCharacterEscaped(CharSequence value, int start) {
        if (start <= 0) {
            return false;
        }
        if (value.charAt(start - 1) != '\\') {
            return false;
        }

        return !isCharacterEscaped(value, start - 1);
    }

    public static int defaultCharacterCheck(CharSequence value, char c, int start, int direction) {
        start += direction;

        while (isCharacterEscaped(value, start)) {
            start = start + direction;
        }

        return start;
    }

    public static int findEndingChar(CharSequence value, char c, int start, int direction) {
        return findEndingChar(value, c, start, direction, StringUtils::defaultCharacterCheck);
    }

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {

        R apply(A a, B b, C c);

        default <V> TriFunction<A, B, C, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (A a, B b, C c) -> after.apply(apply(a, b, c));
        }
    }

    @FunctionalInterface
    interface QuadFunction<A, B, C, D, R> {

        R apply(A a, B b, C c, D d);

        default <V> QuadFunction<A, B, C, D, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (A a, B b, C c, D d) -> after.apply(apply(a, b, c, d));
        }
    }

    /**
     * Find the index of the ending char that matches the given 'c' param,
     * given the index of the start char.
     *
     * @param value     The String to search within.
     * @param c         The char to search for an end for.
     * @param start     The index of the starting quote.
     * @param direction The direction in which to search the given String.
     * @return The index of the matching char. If an end is not found, -1
     * is returned instead.
     */
    public static int findEndingChar(CharSequence value, char c, int start, int direction, QuadFunction<CharSequence, Character, Integer, Integer, Integer> advance) {
        if (start < 0 || start >= value.length()) {
            return -1;
        }

        start = advance.apply(value, value.charAt(start), start, direction);

        while (start >= 0 && start < value.length()) {
            if (value.charAt(start) == c) {
                return start;
            }

            start = advance.apply(value, value.charAt(start), start, direction);
        }

        return -1;
    }

    public static String[] toString(char array[]) {
        String dest[] = new String[array.length];

        for (int i = 0; i < dest.length; i++) {
            dest[i] = Character.toString(array[i]);
        }

        return dest;
    }

    public static <E> String[] toString(E array[]) {
        String dest[] = new String[array.length];

        for (int i = 0; i < dest.length; i++) {
            dest[i] = array[i].toString();
        }

        return dest;
    }

    public static boolean containsString(CharSequence haystack, String needles[], int index) {
        for (String needle : needles) {
            if (containsString(haystack, needle, index)) {
                return true;
            }
        }

        return false;
    }

    public static boolean containsString(CharSequence haystack, String needle, int index) {
        for (int i = 0; i < needle.length(); i++) {
            if (i + index >= haystack.length()) {
                return false;
            }
            if (haystack.charAt(i + index) != needle.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Remove on the BASE SCOPE all of the instances in which the
     *
     * @param source
     * @param start
     * @param end
     * @return
     */
    //
    public static String removeContentsWithin(String source, String start, String end) {
        return removeContentsWithin(source, start, end, 0);
    }

    public static String removeContentsWithin(String source, String start, String end, int index) {
        Bounds bounds = findContentBoundsWithin(source, start, end, index);

        return source.substring(0, bounds.getStart() - start.length()) + source.substring(bounds.getEnd() + end.length());
    }

    public static Bounds findContentBoundsWithin(String source, String start, String end, int index) {
        return findContentBoundsWithin(source, start, end, index, true);
    }

    public static Bounds findContentBoundsWithin(String source, String start, String end, int index, boolean includeEndings) {
        return findContentBoundsWithin(source, start, end, index, includeEndings, (char) 0);
    }

    public static Bounds findContentBoundsWithin(String source, String start, String end, int index, boolean includeEndings, char escapeChar) {
        // Start and end bounds.
        int s = SyntaxUtils.findStringInBaseScope(source, start, index);
        int e = StringUtils.findEndingMatch(source, s, start, end, escapeChar);

        if (includeEndings) {
            e += end.length();
        } else {
            s += start.length();
        }

        Bounds bounds = new Bounds(s, e);

        if (!bounds.isValid()) {
            return Bounds.EMPTY;
        }

        return bounds;
    }

    /**
     * Search through the given value for any match within the strings
     * array.
     *
     * @param value   The String to search through.
     * @param strings The array to search through.
     * @return A Bounds instance with the end points of the found String.
     * If a String is not found, [-1, -1] is returned.
     */
    public static Bounds findStrings(CharSequence value, String strings[]) {
        return findStrings(value, strings, 0);
    }

    /**
     * Search through the given value for any match within the strings
     * array.
     *
     * @param value   The String to search through.
     * @param strings The array to search through.
     * @param start   The index to start the search at.
     * @return A Bounds instance with the end points of the found String.
     * If a String is not found, [-1, -1] is returned.
     */
    public static Bounds findStrings(CharSequence value, String strings[], int start) {
        return findStrings(value, strings, start, 1);
    }

    /**
     * Search through the given value for any match within the strings
     * array.
     *
     * @param value     The String to search through.
     * @param strings   The array to search through.
     * @param start     The index to start the search at.
     * @param direction The direction to search in.
     * @return A Bounds instance with the end points of the found String.
     * If a String is not found, [-1, -1] is returned.
     */
    public static Bounds findStrings(CharSequence value, String strings[], int start, int direction) {
        return findStrings(value, strings, start, direction, true);
    }

    /**
     * Search through the given value for any match within the strings
     * array.
     *
     * @param value     The String to search through.
     * @param strings   The array to search through.
     * @param start     The index to start the search at.
     * @param direction The direction to search in.
     * @return A Bounds instance with the end points of the found String.
     * If a String is not found, [-1, -1] is returned.
     */
    public static Bounds findStrings(CharSequence value, String strings[], int start, int direction, boolean checkBaseScope) {
        return findStrings(value, strings, start, direction, checkBaseScope ? SCOPE_CHECKS_ALL : null);
    }

    public static Bounds findStrings(CharSequence value, String strings[], int start, int direction, char[] scopeChecks) {
        while (start >= 0 && start < value.length()) {
            char c = value.charAt(start);

            if (scopeChecks != null) {
                if (containsChar(scopeChecks, '"') && c == '"') {
                    start = findEndingQuote(value, start, direction);// findEndingQuote(value, start, direction) + direction;

                    if (start < 0) return Bounds.EMPTY;

                    start += direction;

                    continue;
                } else if (containsChar(scopeChecks, '\'') && c == '\'') {
                    start = findEndingChar(value, c, start, direction);// findEndingQuote(value, start, direction) + direction;

                    if (start < 0) return Bounds.EMPTY;

                    start += direction;

                    continue;
                } else if (containsChar(scopeChecks, '(') && (c == '(' && direction > 0 || c == ')' && direction < 0)) {
                    start = findEndingMatch(value, start, '(', ')', direction);

                    if (start < 0) return Bounds.EMPTY;

                    start += direction;

                    if (start <= 0 || start >= value.length()) {
                        return Bounds.EMPTY;
                    }

                    continue;
                } else if (containsChar(scopeChecks, '[') && (c == '[' && direction > 0 || c == ']' && direction < 0)) {
                    start = findEndingMatch(value, start, '[', ']', direction);

                    if (start < 0) return Bounds.EMPTY;

                    start += direction;

                    if (start == 0) {
                        return Bounds.EMPTY;
                    }

                    continue;
                }
            }

            for (String str : strings) {
                for (int i = 0; i < str.length() && start + i < value.length(); i++) {
                    if (value.charAt(start + i) != str.charAt(i)) {
                        break;
                    } else if (i == str.length() - 1) {
                        Bounds bounds = new Bounds(start, start + str.length());

                        return bounds;
                    }
                }
            }

            start += direction;
        }

        return Bounds.EMPTY;
    }

    /**
     * Search the given haystack String for any match within the strings
     * array starting at the given char start index. If a match is
     * encountered, the String that makes the match is returned. If not
     * match is made, null is returned.
     *
     * @param haystack The String try to match.
     * @param strings  The array to search through to try and match.
     * @param start    The index to start to search the value at.
     * @return The String in the strings array that made the match. If no
     * match was found, null is returned.
     */
    public static String findMatch(String haystack, String strings[], int start) {
        Bounds bounds = findStrings(haystack, strings, start);

        if (bounds.getStart() < 0) {
            return null;
        }

        return haystack.substring(bounds.getStart(), bounds.getEnd());
    }

    /**
     * Search the given haystack String for any match within the strings
     * array at the given char offset index. If a match is encountered, the
     * String that makes the match is returned. If not match is made, null
     * is returned.
     *
     * @param haystack The String try to match at the given index.
     * @param strings  The array to search through to try and match.
     * @param index    The index to search the value at.
     * @return The String in the strings array that made the match. If no
     * match was found, null is returned.
     */
    public static String getMatch(String haystack, String strings[], int index) {
        for (String str : strings) {
            for (int i = 0; i < str.length() && index + i < haystack.length(); i++) {
                if (haystack.charAt(index + i) != str.charAt(i)) {
                    break;
                }

                if (i == str.length() - 1) {
                    return str;
                }
            }
        }

        return null;
    }

    /**
     * Search the given haystack String for any match within the strings
     * array at the given char offset index.
     *
     * @param haystack The String try to match at the given index.
     * @param strings  The array to search through to try and match.
     * @param index    The index to search the value at.
     * @return Whether or not there is a match within the strings array at
     * the given index in the haystack String.
     */
    public static boolean matches(String haystack, String strings[], int index) {
        return getMatch(haystack, strings, index) != null;
    }

    /**
     * Check to see if the character is a new-line character. If so,
     * increment the line-number variable.
     *
     * @param start          The index to start the search at.
     * @param statementStart The index to end the search at.
     * @param source         The source String to search in.
     * @return The number of lines that the iterator encountered
     * within the given bounds.
     */
    public static int numNewLines(int start, int statementStart, CharSequence source) {
        int lines = 0;

        for (int i = start; i < statementStart; i++) {
            if (source.charAt(i) == '\n') {
                lines++;
            }
        }

        return lines;
    }

    public static char findNextNonWhitespaceChar(CharSequence str, int index) {
        index = findNextNonWhitespaceIndex(str, index);

        if (index < 0) {
            return '\0';
        }

        return str.charAt(index);
    }

    public static boolean isWhitespace(char c) {
        return containsChar(WHITESPACE, c);
    }

    public static boolean isSymbol(char c) {
        return containsChar(SYMBOLS_CHARS, c);
    }

    public static char findNextWhitespaceChar(CharSequence str, int index) {
        index = findNextWhitespaceIndex(str, index);

        if (index < 0) {
            return '\0';
        }

        return str.charAt(index);
    }

    /**
     * Get the next possible index in the String that is not a whitespace
     * character.
     *
     * @param str   The String to search through.
     * @param index The index to start the search at.
     * @return The next possible index in the String that is not
     * whitespace.
     */
    public static int findNextNonWhitespaceIndex(CharSequence str, int index) {
        return findNextNonWhitespaceIndex(str, index, 1);
    }

    /**
     * Get the next possible index in the String that is not a whitespace
     * character, while moving in the specified direction.
     *
     * @param str       The String to search through.
     * @param index     The index to start the search at.
     * @param direction The direction in which to increment the index.
     * @return The next possible index in the String that is not
     * whitespace.
     */
    public static int findNextNonWhitespaceIndex(CharSequence str, int index, int direction) {
        return findNextCharacter(str, WHITESPACE, index, direction, true);
    }

    /**
     * Get the next possible index in the String that is a whitespace
     * character.
     *
     * @param str   The String to search through.
     * @param index The index to start the search at.
     * @return The next possible index in the String that is
     * whitespace.
     */
    public static int findNextWhitespaceIndex(CharSequence str, int index) {
        return findNextWhitespaceIndex(str, index, 1);
    }

    /**
     * Get the next possible index in the String that is a whitespace
     * character.
     *
     * @param str       The String to search through.
     * @param index     The index to start the search at.
     * @param direction The direction in which to increment the index.
     * @return The next possible index in the String that is
     * whitespace.
     */
    public static int findNextWhitespaceIndex(CharSequence str, int index, int direction) {
        return findNextCharacter(str, WHITESPACE, index, direction);
    }

    /**
     * Get the next possible index in the String that contains a char
     * in the given character array, while moving in the specified
     * direction. If none exists, -1 is returned.
     *
     * @param str       The String to search through.
     * @param chars     The array of valid characters to search for.
     * @param index     The index to start the search at.
     * @param direction The direction in which to increment the index.
     * @return The next possible index in the String that is
     * a character in the given array.
     */
    public static int findNextCharacter(CharSequence str, char chars[], int index, int direction) {
        return findNextCharacter(str, chars, index, direction, false);
    }

    /**
     * Get the next possible index in the String that contains a char
     * in the given character array, while moving in the specified
     * direction. If none exists, -1 is returned.
     *
     * @param str       The String to search through.
     * @param chars     The array of valid characters to search for.
     * @param index     The index to start the search at.
     * @param direction The direction in which to increment the index.
     * @param opposite  Whether or not to search for or against what is
     *                  within the character array.
     * @return The next possible index in the String that is
     * a character in the given array.
     */
    public static int findNextCharacter(CharSequence str, char chars[], int index, int direction, boolean opposite) {
        while (index >= 0 && index < str.length()) {
            if (containsChar(chars, str.charAt(index)) != opposite) {
                return index;
            }

            index += direction;
        }

        return -1;
    }

    /**
     * Escape the spaces in an input String.<br>
     * <br>
     * For example: An input of "this is a test" would return
     * "this\ is\ a\ test"
     *
     * @param input The String to escape the spaces from.
     * @return The input String with escaped spaces.
     */
    public static String escapeSpaces(String input) {
        StringBuilder output = new StringBuilder(input);

        int index = output.indexOf(" ");

        while (index >= 0) {
            output.insert(index, "\\");

            index = output.indexOf(" ", index + 2);
        }

        return output.toString();
    }

    public static String removeSurroundingParenthesis(String source) {
        while (source.startsWith("(") && source.endsWith(")")) {
            source = source.substring(1, source.length() - 1);
        }

        return source;
    }

    public static Bounds removeSurroundingParenthesis(String source, Bounds bounds) {
        if (!bounds.isValid()) {
            return Bounds.EMPTY;
        }

        Bounds out = bounds.clone();

        out.setEnd(StringUtils.findNextNonWhitespaceIndex(source, bounds.getEnd() - 2, -1) + 1);
        out.setStart(StringUtils.findNextNonWhitespaceIndex(source, bounds.getStart() + 1));

        if (bounds.isValid() && !out.isValid()) {
            out.setStart(bounds.getEnd());
            out.setEnd(bounds.getEnd());
        }

        return out;
    }

    public static String[] parseNamedArgument(String input) {
        String[] values = input.split(":");

        if (values.length == 2) {
            values[0] = values[0].trim();
            values[1] = values[1].trim();

            if (Regex.matches(values[0], 0, Patterns.IDENTIFIER)) {
                return values;
            }
        }

        return null;
    }

    /**
     * Split the src by the commas. Makes sure not to split commas that
     * are within parentheses and quotes.
     *
     * @param src The String to split the commas from.
     * @return An array of Strings containing the Strings that were split.
     */
    public static String[] splitCommas(String src) {
        return splitCommas(src, 0);
    }

    public static String[] splitCommas(String src, int searchGenerics) {
        return splitCommas(src, searchGenerics, false);
    }

    public static String[] splitCommas(String src, int searchGenerics, boolean allowTrailing) {
        ArrayList<String> strs = new ArrayList<String>();

        int oldIndex = 0;
        int index = -1;

        StringBuilder builder = new StringBuilder();

        while ((index = SyntaxUtils.findCharInBaseScope(src, ',', index + 1, searchGenerics)) >= 0)//(index = Regex.indexOf(src, index + 1, ',', new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true })) > 0)
        {
            builder = new StringBuilder(src.substring(oldIndex, index));

            trimSurroundingWhitespace(builder);

            strs.add(builder.toString());

            oldIndex = index + 1;
        }

        builder = new StringBuilder(src.substring(oldIndex, src.length()));

        trimSurroundingWhitespace(builder);

        if (!allowTrailing || builder.toString().trim().length() > 0) {
            strs.add(builder.toString());
        }

        return strs.toArray(new String[0]);
    }

    public static String[] splitWhitespace(String src) {
        return splitWhitespace(src, 0);
    }

    public static String[] splitWhitespace(String src, int searchGenerics) {
        return splitWhitespace(src, searchGenerics, false);
    }

    public static String[] splitWhitespace(String src, int searchGenerics, boolean allowTrailing) {
        ArrayList<String> strs = new ArrayList<String>();

        int oldIndex = 0;
        int index = -1;

        StringBuilder builder = new StringBuilder();

        while ((index = SyntaxUtils.findCharInBaseScope(src, SyntaxUtils.WHITESPACE, index + 1, searchGenerics)) >= 0)//(index = Regex.indexOf(src, index + 1, ',', new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true })) > 0)
        {
            builder = new StringBuilder(src.substring(oldIndex, index));

            trimSurroundingWhitespace(builder);

            strs.add(builder.toString());

            oldIndex = index + 1;
        }

        builder = new StringBuilder(src.substring(oldIndex, src.length()));

        trimSurroundingWhitespace(builder);

        if (!allowTrailing || builder.toString().trim().length() > 0) {
            strs.add(builder.toString());
        }

        return strs.toArray(new String[0]);
    }

    public static String[] splitWords(String src) {
        return splitWords(src, 0);
    }

    public static String[] splitWords(String src, int searchGenerics) {
        return splitWords(src, searchGenerics, false);
    }

    public static String[] splitWords(String src, int searchGenerics, boolean allowTrailing) {
        ArrayList<String> strs = new ArrayList<String>();

        int oldIndex = 0;
        int index = -1;

        StringBuilder builder = new StringBuilder();

        while ((index = SyntaxUtils.findCharInBaseScope(src, SyntaxUtils.WHITESPACE, index + 1, searchGenerics)) >= 0)//(index = Regex.indexOf(src, index + 1, ',', new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true })) > 0)
        {
            builder = new StringBuilder(src.substring(oldIndex, index));

            trimSurroundingWhitespace(builder);

            strs.add(builder.toString());

            oldIndex = index + 1;
        }

        builder = new StringBuilder(src.substring(oldIndex, src.length()));

        trimSurroundingWhitespace(builder);

        if (!allowTrailing || builder.toString().trim().length() > 0) {
            strs.add(builder.toString());
        }

        return strs.toArray(new String[0]);
    }

    /**
     * Trim the surrounding whitespace off of the String's ends.
     *
     * @param str The String to trim the whitespace from.
     * @return The freshly trimmed String.
     */
    public static String trimSurroundingWhitespace(String str) {
        StringBuilder builder = new StringBuilder(str);

        trimSurroundingWhitespace(builder);

        return builder.toString();
    }

    /**
     * Trim the surrounding whitespace off of the StringBuilder's ends.
     *
     * @param builder The builder to trim the whitespace from.
     */
    public static void trimSurroundingWhitespace(StringBuilder builder) {
        while (builder.length() > 0 && (builder.charAt(0) == ' ' || builder.charAt(0) == '\n' || builder.charAt(0) == '\t')) {
            builder.deleteCharAt(0);
        }

        while (builder.length() > 0 && (builder.charAt(builder.length() - 1) == ' ' || builder.charAt(builder.length() - 1) == '\n' || builder.charAt(builder.length() - 1) == '\t')) {
            builder.deleteCharAt(builder.length() - 1);
        }
    }

    /**
     * Trim the given statement to strictly an identifier.<br>
     * <br>
     * For example: A call of "<code>trimToIdentifier("**identifer322&amp;+")</code>"
     * would return "<code>identifier322</code>"
     *
     * @param statement The statement to trim down.
     * @return The trimmed down identifier.
     */
    public static String trimToIdentifier(String statement) {
        Matcher matcher = Patterns.IDENTIFIER_BOUNDARIES.matcher(statement);

        matcher.find();

        int start = matcher.start();

        matcher.find();

        int end = matcher.start();

        return statement.substring(start, end);
    }

    /**
     * Get whether the given String only contains chars of the
     * type in the given needles array.
     *
     * @param haystack The String to test.
     * @param needles  The chars that the String can contain.
     * @return Whether or not the String passes the test.
     */
    public static boolean containsOnly(String haystack, char needles[]) {
        for (int i = 0; i < haystack.length(); i++) {
            char c = haystack.charAt(i);

            if (!containsChar(needles, c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Search for the given char 'c' in the given array.
     *
     * @param array The array to search for 'c' in.
     * @param c     The char to search for.
     * @return Whether or not the array contains the 'c' char.
     */
    public static boolean containsChar(char array[], char c) {
        return searchChar(array, c) >= 0;
    }

    /**
     * Search for the given char 'c' in the given arrays.
     *
     * @param arrays The arrays to search for 'c' in.
     * @param c      The char to search for.
     * @return Whether or not the arrays contains the 'c' char.
     */
    public static boolean containsChar(char arrays[][], char c) {
        for (char array[] : arrays) {
            if (containsChar(array, c)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Search for the char at the given index in the given source String
     * in the given array.
     *
     * @param source The String to search through.
     * @param array  The array to search for 'c' in.
     * @return Whether or not the array contains the 'c' char.
     */
    public static boolean containsChar(CharSequence source, char array[]) {
        for (int i = 0; i < source.length(); i++) {
            if (containsChar(source, array, i)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Search for the char at the given index in the given source String
     * in the given array.
     *
     * @param source The String to search through.
     * @param array  The array to search for 'c' in.
     * @param index  The index to get the char from the source String at.
     * @return Whether or not the array contains the 'c' char.
     */
    public static boolean containsChar(CharSequence source, char array[], int index) {
        if (index < 0 || index >= source.length()) {
            return false;
        }

        return searchChar(array, source.charAt(index)) >= 0;
    }

    /**
     * Search for the given char 'c' in the given array, if it is found,
     * return the index at which it was found.
     *
     * @param array The array to search for 'c' in.
     * @param c     The char to search for.
     * @return The index in the array of the occurrence of char 'c', if
     * it was found in the array.
     */
    public static int searchChar(char array[], char c) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == c) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Search for the given String 's' in the given array.
     *
     * @param s     The String to search for.
     * @param array The array to search for 's' in.
     * @return Whether or not the array contains the 's' String.
     */
    public static boolean containsString(String s, String... array) {
        return containsElement(s, array);
    }

    /**
     * Search for the given Element 'element' in the given array.
     *
     * @param element The Element to search for.
     * @param array   The array to search for 'element' in.
     * @return Whether or not the array contains the 'element' String.
     */
    public static <E> boolean containsElement(E element, E... array) {
        return searchElement(element, array) >= 0;
    }

    /**
     * Search for the given Element 'element' in the given array, if it is found,
     * return the index at which it was found.
     *
     * @param element The Element to search for.
     * @param array   The array to search for 'element' in.
     * @return The index in the array of the occurrence of String 'element', if
     * it was found in the array.
     */
    public static <E> int searchElement(E element, E... array) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i].equals(element)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Search for the given String 's' in the given list.
     *
     * @param array The list to search for 's' in.
     * @param s     The String to search for.
     * @return Whether or not the array contains the 'c' String.
     */
    public static boolean containsString(ArrayList<String> array, String s) {
        return searchString(array, s) >= 0;
    }

    /**
     * Search for the given String 's' in the given list, if it is found,
     * return the index at which it was found.
     *
     * @param array The list to search for 's' in.
     * @param s     The String to search for.
     * @return The index in the array of the occurrence of String 's', if
     * it was found in the array.
     */
    public static int searchString(ArrayList<String> array, String s) {
        for (int i = array.size() - 1; i >= 0; i--) {
            if (array.get(i).equals(s)) {
                return i;
            }
        }

        return -1;
    }

//	public String joinIdentifiers(String statement)
//	{
//		String data[][] = splitIdentifiers(statement);
//		
//		
//	}

//	public static String[][] splitIdentifiers(String statement)
//	{
//		ArrayList<String> identifiers = new ArrayList<String>();
//		ArrayList<String> delimiters  = new ArrayList<String>();
//		
//		Matcher matcher = Patterns.IDENTIFIER.matcher(statement);
//		
//		int prev = 0;
//		
//		while (matcher.find())
//		{
//			String text = matcher.group();
//			
//			if (SyntaxUtils.isValidIdentifier(text))
//			{
//				identifiers.add(text);
//			}
//			else if (prev)
//			{
//				delimiters.add(text);
//			}
//		}
//		
//		String returnData[][] = new String[2][];
//		
//		returnData[0] = identifiers.toArray(new String[0]);
//		returnData[1] = delimiters.toArray(new String[0]);
//		
//		return returnData;
//	}

    /**
     * Find the String value of the data that is in between the last
     * two words in the array.<br>
     * <br>
     * For example: A call of <code>findLastMissingString([this, is, a, test],
     * "this.is-&gt;a==test")</code> would return "=="
     *
     * @param words     The list of words that lead of up the desired value.
     * @param statement The String to search the words with.
     * @return The data that was found.
     */
    public static String findLastMissingString(ArrayList<String> words, String statement) {
        int index = 0;

        for (int i = 0; i < words.size() - 1; i++) {
            String word = words.get(i);

            index = statement.indexOf(word, index);

            index += word.length();
        }

        String lastWord = words.get(words.size() - 1);

        String value = statement.substring(index, statement.indexOf(lastWord, index));

        return value;
    }

    /**
     * Search backwards for the index of the next available match of the
     * needle within the haystack String, if a match exists. The search
     * starts at the given index.
     *
     * @param haystack The String to search through.
     * @param needle   The String to search for.
     * @param start    The index to start the reverse search for.
     * @return The next available index.
     */
    public static int reverseIndexOf(String haystack, String needle, int start) {
        while (start >= needle.length() - 1) {
            if (reverseMatches(haystack, needle, start)) {
                return start - needle.length() + 1;
            }

            start--;
        }

        return -1;
    }

    /**
     * Get whether or not the needle matches the haystack at the given
     * index.<br>
     * <br>
     * For example: A call of <code>reverseMatches("this will work",
     * "will", 8)</code> would return true.
     *
     * @param haystack The String to search for the needle in.
     * @param needle   The String to search in the haystack for.
     * @param index    The index to search for the match at.
     * @return Whether or not there is a match at the given index.
     */
    public static boolean reverseMatches(String haystack, String needle, int index) {
        for (int i = 0; i < needle.length(); i++) {
            if (haystack.charAt(index - i) != needle.charAt(needle.length() - i - 1)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Generate a modified version of the given String in which for every
     * character after the "after" char type that is the same type as the
     * given "removeType" char type will be removed from the String.<br>
     * <br>
     * For example: Calling "<code>removeTypeAfterChar("this is an example", 's', ' ')</code>"
     * would return a String "thisisan example"
     *
     * @param str        The String to generate a modified version of.
     * @param after      The char type to search for the remove type directly
     *                   after.
     * @param removeType The type of char to remove.
     * @return The newly formatted String.
     */
    public static String removeTypeAfterChar(String str, char after, char removeType) {
        StringBuilder builder = new StringBuilder(str);

        int index = builder.indexOf(after + "");

        while (index >= 0) {
            char c = builder.charAt(++index);

            while (c == removeType) {
                builder.deleteCharAt(index);

                c = builder.charAt(index);
            }

            index = builder.indexOf(after + "", index);
        }

        return builder.toString();
    }

    /**
     * Increase the given String array size by one and insert the given
     * String into the newly added index.
     *
     * @param array The array to expand.
     * @param str   The String to add at the end.
     * @return The updated/expanded String array.
     */
    public static String[] appendString(String array[], String str) {
        return appendElement(array, new String[array.length + 1], str);
//		
//		String temp[] = array;
//		
//		array = new String[temp.length + 1];
//		
//		System.arraycopy(temp, 0, array, 0, temp.length);
//		
//		array[temp.length] = str;
//		
//		return array;
    }

    /**
     * Increase the given Element array size by one and insert the given
     * Element into the newly added index.
     *
     * @param array The array to expand.
     * @param str   The Element to add at the end.
     * @return The updated/expanded Element array.
     */
    public static <E> E[] appendElement(E array[], E dest[], E str) {
        System.arraycopy(array, 0, dest, 0, array.length);

        dest[array.length] = str;

        return dest;
    }

    public static boolean isSurroundedByQuotes(String input) {
        return input.length() >= 2 && input.charAt(0) == '"' && input.charAt(input.length() - 1) == '"';
    }

    /**
     * Remove the surrounding double quotes from the given input String.<br>
     * For example:
     * <blockquote><pre>
     * String s = "\"C:/myfile/test\"";
     * String out = removeSurroundingQuotes(s);</pre></blockquote>
     * The out String would contain the data "C:/myfile/test" without
     * the surrounding quotes.
     *
     * @param input The String to remove the surrounding quotes from.
     * @return The String without the surrounding quotes.
     */
    public static String removeSurroundingQuotes(String input) {
        while (isSurroundedByQuotes(input)) {
            input = input.substring(1, input.length() - 1);
        }

        return input;
    }
}
