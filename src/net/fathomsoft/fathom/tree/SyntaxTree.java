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
package net.fathomsoft.fathom.tree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fathomsoft.fathom.Fathom;
import net.fathomsoft.fathom.tree.exceptionhandling.CatchNode;
import net.fathomsoft.fathom.tree.exceptionhandling.FinallyNode;
import net.fathomsoft.fathom.tree.exceptionhandling.TryNode;
import net.fathomsoft.fathom.tree.variables.FieldNode;
import net.fathomsoft.fathom.util.FileUtils;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.Stack;
import net.fathomsoft.fathom.util.StringUtils;
import net.fathomsoft.fathom.util.SyntaxUtils;

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
	private int						statementStartIndex, statementEndIndex, oldStatementStartIndex;
	private int						lineNumber;
	
	private long					flags;
	
	private Matcher					statementStartMatcher, lineBeginningMatcher;
	
	private Pattern					statementStartPattern, lineBeginningPattern;
	
	private TreeNode				currentNode;
	private ProgramNode				root;
	
	private Stack<TreeNode>			parentStack;
	
	private static final char		EITHER_STATEMENT_END_CHARS[] = new char[] { '{', ';' };
	private static final char		SINGLE_STATEMENT_END_CHARS[] = new char[] { ';' };
	private static final char		SCOPE_STATEMENT_END_CHARS[] = new char[] { '{', };
	
	private static final Class<?>	FIRST_PASS_CLASSES[] = new Class<?>[]
	{
		ImportNode.class, ClassNode.class
	};
	
	private static final Class<?>	SECOND_PASS_CLASSES[] = new Class<?>[]
	{
		DestructorNode.class, ConstructorNode.class, MethodNode.class, FieldNode.class
	};
	
	private static final Class<?>	THIRD_PASS_CLASSES[] = new Class<?>[]
	{
		
	};
	
	/**
	 * Create a SyntaxTree instance from the source code in the specified
	 * File.
	 * 
	 * @param files The files containing the Foxy source code.
	 * @param flags The compiler's flags.
	 * @throws IOException Thrown if there was an error finding the
	 * 		files or reading from it.
	 */
	public SyntaxTree(File files[], long flags) throws IOException
	{
		String filenames[] = new String[files.length];
		String sources[]   = new String[files.length];
		
		for (int i = 0; i < files.length; i++)
		{
			filenames[i] = files[i].getName();
			sources[i]   = FileUtils.readFile(files[i]);
		}
		
		init(filenames, sources, flags);
	}
	
	/**
	 * Generate a SyntaxTree instance given the name of the file and the
	 * source within it.
	 * 
	 * @param filenames The names of the files containing the source.
	 * @param sources The source codes inside the files.
	 * @param flags The compiler's flags.
	 */
	public SyntaxTree(String filenames[], String sources[], long flags)
	{
		init(filenames, sources, flags);
	}
	
	/**
	 * Generate the SyntaxTree given the name of the file and the
	 * source within it.
	 * 
	 * @param filenames The names of the files containing the source.
	 * @param sources The source codes inside the files.
	 * @param flags The compiler's flags.
	 */
	private void init(String filenames[], String sources[], long flags)
	{
		this.flags = flags;
		
		root = new ProgramNode();
		
		Fathom.log(flags, "Generating syntax tree...");
		
		for (int i = 0; i < sources.length; i++)
		{
			sources[i] = removeComments(sources[i]);
		}
		
		for (int i = 0; i < filenames.length; i++)
		{
			firstPass(filenames[i], sources[i]);
		}
		for (int i = 0; i < filenames.length; i++)
		{
			secondPass(filenames[i], sources[i]);
		}
		for (int i = 0; i < filenames.length; i++)
		{
			thirdPass(filenames[i], sources[i]);
		}
	}
	
	/**
	 * Initialize the Syntax Tree for the specified source with the
	 * specified file name.
	 * 
	 * @param source The code that is going to generate a syntax tree.
	 */
	private void init(String source)
	{
		statementStartPattern  = Patterns.STATEMENT_START;
//		statementEndPattern    = Patterns.STATEMENT_END;
		lineBeginningPattern   = Patterns.NEXT_TEXT_LINE;
		
		statementStartMatcher  = statementStartPattern.matcher(source);
//		statementEndMatcher    = statementEndPattern.matcher(source);
		lineBeginningMatcher   = lineBeginningPattern.matcher(source);
		
		statementStartIndex    = 0;
		statementEndIndex      = 0;
		oldStatementStartIndex = 0;
		lineNumber             = 1;
	}
	
	private void firstPass(String filename, String source)
	{
		filename = FileUtils.removeFileExtension(filename);
		
		Fathom.log(flags, "First pass for '" + filename + "'...");
		
		FileNode fileNode = new FileNode();
		fileNode.setName(filename);
		
		root.addChild(fileNode);
		
		traverseCode(fileNode, source, 0, EITHER_STATEMENT_END_CHARS, FIRST_PASS_CLASSES, true);
	}
	
	private void secondPass(String filename, String source)
	{
		filename = FileUtils.removeFileExtension(filename);

		Fathom.log(flags, "Second pass for '" + filename + "'...");
		
		FileNode fileNode = root.getFile(filename);
		
		for (int i = 0; i < fileNode.getChildren().size(); i++)
		{
			TreeNode child = fileNode.getChild(i);
			
			if (child instanceof ClassNode)
			{
				ClassNode node    = (ClassNode)child;
				
				int startingIndex = StringUtils.findNextNonWhitespaceIndex(source, node.getLocationIn().getBounds().getEnd());
				int endingIndex   = StringUtils.findEndingMatch(source, startingIndex, '{', '}');
				
				int contentStart  = StringUtils.findNextNonWhitespaceIndex(source, startingIndex + 1);
				int contentEnd    = StringUtils.findNextNonWhitespaceIndex(source, endingIndex - 1, -1) + 1;
				
				String subSource  = source.substring(contentStart, contentEnd);
				
				traverseCode(node, subSource, contentStart, EITHER_STATEMENT_END_CHARS, SECOND_PASS_CLASSES, true);
			}
		}
	}
	
	private void thirdPass(String filename, String source)
	{
		filename = FileUtils.removeFileExtension(filename);

		Fathom.log(flags, "Third pass for '" + filename + "'...");
		
		FileNode fileNode = root.getFile(filename);
		
		for (int i = 0; i < fileNode.getChildren().size(); i++)
		{
			TreeNode child = fileNode.getChild(i);
			
			if (child instanceof ClassNode)
			{
				ClassNode classNode = (ClassNode)child;
				
				MethodListNode methods = classNode.getMethodListNode();
				decodeMethodContents(methods, source);
				
				MethodListNode constructors = classNode.getConstructorListNode();
				decodeMethodContents(constructors, source);
				
				MethodListNode destructors = classNode.getDestructorListNode();
				decodeMethodContents(destructors, source);
			}
		}
		
		checkForErrors(fileNode);
	}
	
	private void decodeMethodContents(MethodListNode methods, String source)
	{
		for (TreeNode child : methods.getChildren())
		{
			MethodNode node   = (MethodNode)child;
			
			int startingIndex = StringUtils.findNextNonWhitespaceIndex(source, node.getLocationIn().getBounds().getEnd());
			int endingIndex   = StringUtils.findEndingMatch(source, startingIndex, '{', '}');
			
			int contentStart  = StringUtils.findNextNonWhitespaceIndex(source, startingIndex + 1);
			int contentEnd    = StringUtils.findNextNonWhitespaceIndex(source, endingIndex - 1, -1) + 1;
			
			if (contentStart < contentEnd)
			{
				String subSource  = source.substring(contentStart, contentEnd);
				
				traverseCode(node, subSource, contentStart, EITHER_STATEMENT_END_CHARS, THIRD_PASS_CLASSES, false);
			}
		}
	}
	
	/**
	 * 
	 * 
	 * @param parent 
	 * @param source 
	 * @param offset 
	 * @param statementType The character array to use that determines what
	 * 		type of statements are searched for. Possible options include:
	 * 		<ul>
	 * 			<li>{@link #EITHER_STATEMENT_END_CHARS}</li>
	 * 			<li>{@link #SINGLE_STATEMENT_END_CHARS}</li>
	 * 			<li>{@link #SCOPE_STATEMENT_END_CHARS}</li>
	 * 		</ul>
	 * @param searchTypes The type of TreeNodes to try to decode.
	 * @param skipScopes 
	 */
	private void traverseCode(TreeNode parent, String source, int offset, char statementType[], Class<?> searchTypes[], boolean skipScopes)
	{
		init(source);
		
		parentStack = new Stack<TreeNode>();
		
		parentStack.push(parent);
		
		currentNode = getNextStatement(source, offset, statementType, searchTypes);
		
		// Decode all of the statements in the source text.
		while (currentNode != null)
		{
			if (skipScopes && (currentNode.containsScope() || currentNode instanceof ClassNode))
			{
				int startingIndex = StringUtils.findNextNonWhitespaceIndex(source, statementEndIndex);
				int endingIndex   = StringUtils.findEndingMatch(source, startingIndex, '{', '}');
				
//				int contentStart  = StringUtils.findNextNonWhitespaceIndex(source, startingIndex + 1);
//				int contentEnd    = StringUtils.findNextNonWhitespaceIndex(source, endingIndex - 1, -1) + 1;
//				
//				if (contentStart >= contentEnd)
//				{
//					System.err.println("Something went wrong...");
//					
//					System.exit(1);
//				}
				
				statementStartIndex    = endingIndex;
				oldStatementStartIndex = statementStartIndex;
			}
			
			if (!parentStack.isEmpty())
			{
				TreeNode parentNode = parentStack.peek();
				
				parentNode.addChild(currentNode);
			}
			
			if (statementEndIndex >= 0 && !skipScopes && nextChar(statementEndIndex, source) == '{')
			{
//				if (currentNode.containsScope())
//				{
//					currentNode = currentNode.getScopeNode();
//				}
				
				parentStack.push(currentNode);
			}
			
			updateParents(oldStatementStartIndex, statementStartIndex, source);
			
			currentNode = getNextStatement(source, offset, statementType, searchTypes);
		}
	}
	
	/**
	 * Search for the main method, if one exists, in the compiling
	 * program. For more details on what the main method looks like, see
	 * {@link net.fathomsoft.fathom.util.SyntaxUtils#isMainMethod(MethodNode)}.
	 * 
	 * @return The MethodNode representation of the main method.
	 */
	public MethodNode getMainMethod()
	{
		for (int i = 0; i < root.getChildren().size(); i++)
		{
			TreeNode child = root.getChild(i);
			
			for (int j = 0; j < child.getChildren().size(); j++)
			{
				TreeNode child2 = child.getChild(j);
				
				if (child2 instanceof ClassNode)
				{
					ClassNode classNode = (ClassNode)child2;
					
					MethodListNode methods = classNode.getMethodListNode();
					
					for (int k = 0; k < methods.getChildren().size(); k++)
					{
						MethodNode method = (MethodNode)methods.getChild(k);
						
						if (SyntaxUtils.isMainMethod(method))
						{
							return method;
						}
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * After the SyntaxTree has been created, further check the the tree
	 * for inconsistencies and errors/warnings.
	 */
	private void checkForErrors()
	{
		checkForErrors(root);
	}
	
	/**
	 * After the SyntaxTree has been created, further check the the tree
	 * for inconsistencies and errors/warnings.
	 * 
	 * @param root The node to start the search for errors at.
	 */
	private void checkForErrors(TreeNode root)
	{
		if (root instanceof ClassNode)
		{
			ClassNode node = (ClassNode)root;
			
			if (!node.containsConstructor())
			{
				ConstructorNode defaultConstructor = new ConstructorNode();
				defaultConstructor.setName(node.getName());
				defaultConstructor.setType(node.getName());
				defaultConstructor.setVisibility(FieldNode.PUBLIC);
				
				node.addChild(defaultConstructor);
			}
			if (!node.containsDestructor())
			{
				DestructorNode defaultDestructor = new DestructorNode();
				defaultDestructor.setName(node.getName());
				defaultDestructor.setType("void");
				defaultDestructor.setVisibility(FieldNode.PUBLIC);
				
				node.addChild(defaultDestructor);
			}
		}
		else if (root instanceof TryNode)
		{
			TreeNode parent = root.getParent();
			
			FinallyNode finallyNode = null;
			
			for (int i = 0; i < parent.getChildren().size() && finallyNode == null; i++)
			{
				TreeNode child = parent.getChild(i);
				
				if (child == root)
				{
					i++;
					
					int     insertIndex = -1;
					
					while (i < parent.getChildren().size() && insertIndex == -1)
					{
						child = parent.getChild(i);
						
						// If the current child is a catch node.
						if (child instanceof CatchNode == false)
						{
							// If there already is a finally node.
							if (child instanceof FinallyNode)
							{
								insertIndex = -2;
							}
							// If there was not finally node.
							else
							{
								insertIndex = i;
							}
						}
						
						i++;
					}
					
					// If there does not already exist a finally node.
					if (insertIndex != -2)
					{
						if (insertIndex < 0)
						{
							insertIndex = i;
						}
						
						finallyNode = new FinallyNode();
						parent.addChild(insertIndex, finallyNode);
					}
				}
			}
		}
//		else if (root instanceof MethodCallNode)
//		{
//			MethodCallNode node = (MethodCallNode)root;
//			
//			FileNode fileNode = (FileNode)root.getAncestorOfType(FileNode.class);
//			
//			int dot = node.getName().indexOf('.');
//			
//			if (dot > 0)
//			{
//				String cFile = node.getName().substring(0, dot);
//				
//				if (fileNode.getImportListNode().contains(cFile))
//				{
//					node.setName(node.getName().substring(dot + 1));
//				}
//			}
//		}
		
		for (int i = 0; i < root.getChildren().size(); i++)
		{
			TreeNode child = root.getChild(i);
			
			checkForErrors(child);
		}
	}
	
	/**
	 * Remove the comments from the specified source String.
	 * 
	 * @param source The source String to remove the comments from.
	 * @return A new String without any comments.
	 */
	private String removeComments(String source)
	{
		Pattern p = Patterns.COMMENT;
		
		Matcher m = p.matcher(source);
		
		StringBuilder builder = new StringBuilder(source);
		
		int offset = 0;
		
		while (m.find())
		{
			builder.delete(m.start() - offset, m.end() - offset);
			
			offset += m.end() - m.start();
		}
		
		return builder.toString();
	}

	/**
	 * Generate the C Header output from the data contained within the
	 * syntax tree.
	 */
	public void generateCHeaderOutput()
	{
		root.generateCHeaderOutput();
	}
	
	/**
	 * Generate the C Source output from the data contained within the
	 * syntax tree.
	 */
	public void generateCSourceOutput()
	{
		root.generateCSourceOutput();
	}
	
	/**
	 * Generate the C Source and Header output from the data contained
	 * within the syntax tree.
	 */
	public void generateCOutput()
	{
		generateCHeaderOutput();
		generateCSourceOutput();
	}

	/**
	 * Format the C Header output to follow syntactical rules.
	 */
	public void formatCHeaderOutput()
	{
		root.formatCHeaderOutput();
	}
	
	/**
	 * Format the C Source output to follow syntactical rules.
	 */
	public void formatCSourceOutput()
	{
		root.formatCSourceOutput();
	}
	
	/**
	 * Format the C Header and Source output to follow syntactical rules.
	 */
	public void formatCOutput()
	{
		formatCHeaderOutput();
		formatCSourceOutput();
	}
	
	/**
	 * Get the next non-whitespace character on the right.
	 * 
	 * @param index The index to start the search at.
	 * @param source The source String to search in.
	 * @return The index of the first non-whitespace character after the
	 * 		index.
	 */
	private char nextChar(int index, String source)
	{
		int i = nextCharIndex(index, source);
		
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
	 * @param source The source String to search in.
	 * @return The index of the first non-whitespace character after the
	 * 		index.
	 */
	private int nextNonWhitespaceIndexOnTheLeft(int index, String source)
	{
		return nextCharIndex(index, source, -1);
	}
	
	/**
	 * Get the next non-whitespace character on the right.
	 * 
	 * @param index The index to start the search at.
	 * @param source The source String to search in.
	 * @return The index of the first non-whitespace character after the
	 * 		index.
	 */
	private int nextCharIndex(int index, String source)
	{
		return nextCharIndex(index, source, 1);
	}
	
	/**
	 * Get the next non-whitespace character in the specified direction.
	 * 
	 * @param index The index to start the search at.
	 * @param source The source String to search in.
	 * @param direction The direction to move the index in.
	 * @return The index of the first non-whitespace character after the
	 * 		index.
	 */
	private int nextCharIndex(int index, String source, int direction)
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
	 * Calculate the horizontal offset from which the statement starts at.
	 * 
	 * @param statementStart The index of the first character of the
	 * 		statement within the source code.
	 * @param source The source String to search in.
	 * @return The horizontal offset of the statement.
	 */
	private int calculateOffset(int statementStart, String source)
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
	 * @param source The source String to search in.
	 */
	private void updateLineNumber(int start, int statementStart, String source)
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
	 * @param source The source String to search in.
	 */
	private void updateParents(int start, int statementStart, String source)
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
	 * @param source The source String to search in.
	 * @param offset 
	 * @param statementType The character array to use that determines what
	 * 		type of statements are searched for. Possible options include:
	 * 		<ul>
	 * 			<li>{@link #EITHER_STATEMENT_END_CHARS}</li>
	 * 			<li>{@link #SINGLE_STATEMENT_END_CHARS}</li>
	 * 			<li>{@link #SCOPE_STATEMENT_END_CHARS}</li>
	 * 		</ul>
	 * @param searchTypes The type of TreeNodes to try to decode.
	 * @return The TreeNode containing the information, or null if it is
	 * 		not found.
	 */
	private TreeNode getNextStatement(String source, int offset, char statementType[], Class<?> searchTypes[])
	{
		if ((statementEndIndex = Regex.indexOfExcludeTextAndParentheses(source, statementStartIndex, statementType)) >= 0 && !statementStartMatcher.hitEnd())
		{
			statementEndIndex = nextNonWhitespaceIndexOnTheLeft(statementEndIndex - 1, source) + 1;
			
			int newStatementStartIndex = 0;
			
			// TODO: Remember the first statementEndIndex for the find method call.
			if (statementStartMatcher.find(nextCharIndex(statementEndIndex, source) + 1))
			{
				newStatementStartIndex = statementStartMatcher.start();
			}
				
			int      offset2   = statementStartIndex;//calculateOffset(statementStartIndex, source);
			
			String   statement = source.substring(statementStartIndex, statementEndIndex);
			
			Location location  = new Location(lineNumber, offset2 + offset, offset2 + statement.length() + offset);
			
			TreeNode node      = decodeStatement(statement, location, searchTypes);
			
			updateLineNumber(statementStartIndex, newStatementStartIndex, source);
				
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
	 * @param searchTypes The type of TreeNodes to try to decode.
	 * @return The result TreeNode of decoding the statement String.
	 */
	private TreeNode decodeStatement(String statement, Location location, Class<?> searchTypes[])
	{
		TreeNode parent = null;
		
		if (!parentStack.isEmpty())
		{
			parent = parentStack.peek();
		}
		
		if (searchTypes.length > 0)
		{
			return TreeNode.decodeStatement(parent, statement, location, searchTypes);
		}
		else
		{
			return TreeNode.decodeStatement(parent, statement, location);
		}
	}
	
	/**
	 * Get the C Header output text (destination text) from the Syntax
	 * tree.
	 * 
	 * @return The C Header output text after compilation.
	 */
	public String[] getCHeaderOutput()
	{
		String headers[] = new String[root.getChildren().size()];
		
		for (int i = 0; i < headers.length; i++)
		{
			TreeNode child = root.getChild(i);
			
			headers[i] = child.generateCHeaderOutput();
		}
		
		return headers;
	}
	
	/**
	 * Get the C Source output text (destination text) from the Syntax
	 * tree.
	 * 
	 * @return The C Source output text after compilation.
	 */
	public String[] getCSourceOutput()
	{
		String sources[] = new String[root.getChildren().size()];
		
		for (int i = 0; i < sources.length; i++)
		{
			TreeNode child = root.getChild(i);
			
			sources[i] = child.generateCSourceOutput();
		}
		
		return sources;
	}
	
	/**
	 * Get the root ProgramNode of the tree. The root of the Syntax tree
	 * will be a ProgramNode.
	 * 
	 * @return The root ProgramNode instance.
	 */
	public ProgramNode getRoot()
	{
		return root;
	}
}
