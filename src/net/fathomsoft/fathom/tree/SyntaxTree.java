package net.fathomsoft.fathom.tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fathomsoft.fathom.error.SyntaxError;
import net.fathomsoft.fathom.util.FileUtils;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.Stack;

/**
 * Class that deconstructs a source code file and builds a Tree out of
 * the statements.
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:00:15 PM
 * @since	v0.1
 * @version	Jan 5, 2014 at 9:00:15 PM
 * @version	v0.1
 */
public class SyntaxTree
{
	private int					statementStartIndex, statementEndIndex, oldStatementStartIndex;
	private int					lineNumber;
	
	private String				source, output;
	
	private Matcher				statementStartMatcher, lineBeginningMatcher;
	
	private Pattern				statementStartPattern, lineBeginningPattern;
	
	private TreeNode			currentNode;
	private FileNode			root;
	
	private Stack<TreeNode>		parentStack;
	
	private static final char	STATEMENT_END_CHARS[] = new char[] { '{', ';' };
	
	/**
	 * Create a SyntaxTree instance from the source code in the specified
	 * File.
	 * 
	 * @param file The file containing the Foxy source code.
	 * @throws IOException Thrown if there was an error finding the
	 * 		file or reading from it.
	 */
	public SyntaxTree(File file) throws IOException
	{
		String filename = file.getName();
		
		String source = FileUtils.readFile(file);
		
		init(filename, source);
	}
	
	/**
	 * Generate a SyntaxTree instance given the name of the file and the
	 * source within it.
	 * 
	 * @param filename The name of the file containing the source.
	 * @param source The source code inside the file.
	 */
	public SyntaxTree(String filename, String source)
	{
		init(filename, source);
	}
	
	/**
	 * Generate the SyntaxTree given the name of the file and the
	 * source within it.
	 * 
	 * @param filename The name of the file containing the source.
	 * @param source The source code inside the file.
	 */
	private void init(String filename, String source)
	{
		this.source           = source;
		
		parentStack           = new Stack<TreeNode>();
		
		statementStartPattern = Patterns.STATEMENT_START;
//		statementEndPattern   = Patterns.STATEMENT_END;
		lineBeginningPattern  = Patterns.NEXT_TEXT_LINE;
		
		statementStartMatcher = statementStartPattern.matcher(source);
//		statementEndMatcher   = statementEndPattern.matcher(source);
		lineBeginningMatcher  = lineBeginningPattern.matcher(source);
		
		statementStartIndex   = 0;
		lineNumber            = 1;
		
		root                  = new FileNode();
		root.setName(FileUtils.removeFileExtension(filename));
		
		parentStack.push(root);
		
		currentNode           = getNextStatement();
		root.addChild(currentNode);
		
		// Decode all of the statements in the source text.
		while (currentNode != null)
		{
			TreeNode parentNode = null;
			
			if (!parentStack.isEmpty())
			{
				parentNode = parentStack.peek();
				
				parentNode.addChild(currentNode);
			}
			
			if (/*!statementEndMatcher.hitEnd()*/statementEndIndex >= 0 && nextChar(statementEndIndex) == '{')
			{
				parentStack.push(currentNode);
			}
			
			updateParents(oldStatementStartIndex, statementStartIndex);
			
			currentNode = getNextStatement();
		}
	}
	
	/**
	 * Get the next non-whitespace character on the right.
	 * 
	 * @param index The index to start the search at.
	 * @return The index of the first non-whitespace character after the
	 * 		index.
	 */
	private char nextChar(int index)
	{
		int i = nextCharIndex(index);
		
		if (i < 0)
		{
			return 0;
		}
		
		return source.charAt(i);
	}
	
	/**
	 * Get the next non-whitespace character on the right.
	 * 
	 * @param index The index to start the search at.
	 * @return The index of the first non-whitespace character after the
	 * 		index.
	 */
	private int nextNonWhitespaceIndexOnTheLeft(int index)
	{
		return nextCharIndex(index, -1);
	}
	
	/**
	 * Get the next non-whitespace character on the right.
	 * 
	 * @param index The index to start the search at.
	 * @return The index of the first non-whitespace character after the
	 * 		index.
	 */
	private int nextCharIndex(int index)
	{
		return nextCharIndex(index, 1);
	}
	
	/**
	 * Get the next non-whitespace character in the specified direction.
	 * 
	 * @param index The index to start the search at.
	 * @param direction The direction to move the index in.
	 * @return The index of the first non-whitespace character after the
	 * 		index.
	 */
	private int nextCharIndex(int index, int direction)
	{
		for (int i = index; i < source.length(); i += direction)
		{
			char c = source.charAt(i);
			
			if (c != ' ' && c != '\t' && c != '\n')
			{
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Format the text to follow syntactical rules.
	 * 
	 * @param text The String to format.
	 * @return The formatted version of the String.
	 */
	public String formatText(String text)
	{
		StringBuilder builder       = new StringBuilder();
		
		StringBuilder tabs          = new StringBuilder();
		
		Matcher       formatMatcher = Patterns.FORMATTER_PATTERN.matcher(text);
		
		int           lastIndex     = 0;
		
		while (formatMatcher.find())
		{
			char nextc = 0;
			char c     = text.charAt(formatMatcher.start());
			
			if (formatMatcher.start() < text.length() - 1)
			{
				nextc = text.charAt(formatMatcher.start() + 1);
			}
			
			if (c == '{')
			{
				tabs.append('\t');
			}
			else if (c == '}')
			{
				if (tabs.length() > 0)
				{
					tabs.deleteCharAt(0);
				}
			}
			else if (c == '\n')
			{
				String substring = text.substring(lastIndex, formatMatcher.start());
				
				builder.append(tabs).append(substring).append('\n');
				
				if (substring.startsWith("{") || substring.startsWith("("))
				{
					builder.deleteCharAt(builder.length() - substring.length() - 2);
				}
				
				lastIndex = formatMatcher.start() + 1;
				
				if (nextc == '(')
				{
					tabs.append('\t');
				}
				else if (nextc == ')')
				{
					if (tabs.length() > 0)
					{
						tabs.deleteCharAt(0);
					}
				}
			}
		}
		
		builder.append(tabs).append(text.substring(lastIndex, text.length()));
		
		return builder.toString();
	}
	
	/**
	 * Calculate the horizontal offset from which the statement starts at.
	 * 
	 * @param statementStart The index of the first character of the
	 * 		statement within the source code.
	 * @return The horizontal offset of the statement.
	 */
	private int calculateOffset(int statementStart)
	{
		for (int i = statementStart - 1; i >= 0; i--)
		{
			if (source.charAt(i) == '\n')
			{
				return statementStart - i - 1;
			}
		}
		
		return 0;
	}
	
	/**
	 * Check to see if the character is a new-line character. If so,
	 * increment the line-number variable.
	 * 
	 * @param start The index to start the search at.
	 * @param statementStart The index to end the search at.
	 */
	private void updateLineNumber(int start, int statementStart)
	{
		for (int i = start; i < statementStart; i++)
		{
			if (source.charAt(i) == '\n')
			{
				lineNumber++;
			}
		}
	}
	
	/**
	 * Check to see if an ending curly brace has been parsed. If so,
	 * pop the last parent off the stack.
	 * 
	 * @param start The index to start the search at.
	 * @param statementStart The index to end the search at.
	 */
	private void updateParents(int start, int statementStart)
	{
		for (int i = start; i < statementStart; i++)
		{
			if (source.charAt(i) == '}')
			{
				parentStack.pop();
			}
		}
	}
	
	/**
	 * Search for the next statement. If a statement is found, return
	 * it in a TreeNode format, if not return null.
	 * 
	 * @return The TreeNode containing the information, or null if it is
	 * 		not found.
	 */
	private TreeNode getNextStatement()
	{
		if ((statementEndIndex = Regex.indexOfExcludeText(source, statementStartIndex, STATEMENT_END_CHARS)) >= 0 && !statementStartMatcher.hitEnd())
		{
			statementEndIndex = nextNonWhitespaceIndexOnTheLeft(statementEndIndex - 1) + 1;
			
			int newStatementStartIndex = 0;
			
			// TODO: Remember the first statementEndIndex for the find method call.
			if (statementStartMatcher.find(nextCharIndex(statementEndIndex) + 1))
			{
				newStatementStartIndex = statementStartMatcher.start();
			}
				
			int      offset    = calculateOffset(statementStartIndex);
			
			Location location  = new Location(lineNumber, offset);
			
			String   statement = source.substring(statementStartIndex, statementEndIndex);
			
			TreeNode node      = decodeStatement(statement, location);
			
			updateLineNumber(statementStartIndex, newStatementStartIndex);
				
			oldStatementStartIndex = statementStartIndex;
			
			statementStartIndex    = newStatementStartIndex;
			
			return node;
		}
		
		return null;
	}
	
	/**
	 * Decode the specified statement String at the given Location into a
	 * TreeNode instance.
	 * 
	 * @param statement The statement String to decode into a TreeNode.
	 * @param location The location of the statement in the source text.
	 * @return The result TreeNode of decoding the statement String.
	 */
	private TreeNode decodeStatement(String statement, Location location)
	{
		TreeNode parent = null;
		
		if (!parentStack.isEmpty())
		{
			parent = parentStack.peek();
		}
		
		TreeNode node = TreeNode.decodeStatement(parent, statement, location);
		
		return node;
	}
	
	/**
	 * Get the output text (destination text) from the Syntax tree.
	 * 
	 * @return The output text after compilation.
	 */
	public String getOutput()
	{
		return output;
	}
	
	/**
	 * Get the root TreeNode of the tree. The root of the Syntax tree
	 * will be a FileNode, or FileListNode instance.
	 * 
	 * @return The root TreeNode instance.
	 */
	public TreeNode getRoot()
	{
		return root;
	}
}