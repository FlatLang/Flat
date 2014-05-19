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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fathomsoft.fathom.Fathom;
import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.exceptionhandling.CatchNode;
import net.fathomsoft.fathom.tree.exceptionhandling.FinallyNode;
import net.fathomsoft.fathom.tree.exceptionhandling.TryNode;
import net.fathomsoft.fathom.tree.variables.FieldNode;
import net.fathomsoft.fathom.tree.variables.LocalVariableNode;
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
 * @since	v0.1 Jan 5, 2014 at 9:00:15 PM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class SyntaxTree
{
	private int						phase;
	
	private Fathom					controller;
	
	private ProgramNode				root;
	
	private TreeGenerator			generators[];
	
	private static final boolean	USE_THREADS = true;
	
	private static final char		EITHER_STATEMENT_END_CHARS[] = new char[] { '{', ';' };
	private static final char		SINGLE_STATEMENT_END_CHARS[] = new char[] { ';' };
	private static final char		SCOPE_STATEMENT_END_CHARS[]  = new char[] { '{', };
	
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
	 * @param controller The controller of the compiling program.
	 * @throws IOException Thrown if there was an error finding the
	 * 		files or reading from it.
	 */
	public SyntaxTree(File files[], Fathom controller) throws IOException
	{
		String filenames[] = new String[files.length];
		String sources[]   = new String[files.length];
		
		for (int i = 0; i < files.length; i++)
		{
			filenames[i] = files[i].getName();
			sources[i]   = FileUtils.readFile(files[i]);
		}
		
		generate(filenames, sources, controller);
	}
	
	/**
	 * Generate a SyntaxTree instance given the name of the file and the
	 * source within it.
	 * 
	 * @param filenames The names of the files containing the source.
	 * @param sources The source codes inside the files.
	 * @param controller The controller of the compiling program.
	 */
	public SyntaxTree(String filenames[], String sources[], Fathom controller)
	{
		generate(filenames, sources, controller);
	}
	
	/**
	 * Generate the SyntaxTree given the name of the file and the
	 * source within it.
	 * 
	 * @param filenames The names of the files containing the source.
	 * @param sources The source codes inside the files.
	 * @param controller The controller of the compiling program.
	 */
	private void generate(String filenames[], String sources[], Fathom controller)
	{
		this.controller = controller;
		
		root = new ProgramNode(controller);
		
		controller.log("Generating syntax tree...");
		
		for (int i = 0; i < sources.length; i++)
		{
			sources[i] = removeComments(sources[i]);
		}
		
		initTreeGenerators(filenames, sources);
		
		try
		{
			phase(1);
			
			root.validateClasses();
			
			phase(2);
			
			root.validateFields();
			root.validateMethods();
			
			phase(3);
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Initialize the TreeGenerator objects that will be used to generate
	 * the trees concurrently (if threads are used).
	 * 
	 * @param filenames The names of the files to generate trees for.
	 * @param sources The sources within the files to generate the trees
	 * 		for.
	 */
	private void initTreeGenerators(String filenames[], String sources[])
	{
		generators = new TreeGenerator[filenames.length];
		
		for (int i = 0; i < generators.length; i++)
		{
			TreeGenerator generator = new TreeGenerator(filenames[i], sources[i]);
			
			generators[i] = generator;
		}
	}
	
	/**
	 * Generate the SyntaxTree data for the specified phase.
	 * 
	 * @param phase The phase to start generating.
	 */
	private void phase(int phase) throws InterruptedException
	{
		this.phase       = phase;
		
		if (USE_THREADS)
		{
			Thread threads[] = new Thread[generators.length];
			
			for (int i = 0; i < generators.length; i++)
			{
				Thread generator = new Thread(generators[i]);
				generator.setName(generators[i].filename + " Generator");
				
				generator.start();
				
				threads[i] = generator;
			}
			
			for (int i = 0; i < generators.length; i++)
			{
				Thread generator = threads[i];
				
				generator.join();
			}
		}
		else
		{
			for (int i = 0; i < generators.length; i++)
			{
				TreeGenerator generator = generators[i];
				
				generator.run();
			}
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
//		if (root instanceof ClassNode)
//		{
//			ClassNode node = (ClassNode)root;
//			
//			if (!node.containsConstructor())
//			{
//				ConstructorNode defaultConstructor = new ConstructorNode();
//				defaultConstructor.setName(node.getName());
//				defaultConstructor.setType(node.getName());
//				defaultConstructor.setVisibility(FieldNode.PUBLIC);
//				
//				node.addChild(defaultConstructor);
//			}
//			if (!node.containsDestructor())
//			{
//				DestructorNode defaultDestructor = new DestructorNode();
//				defaultDestructor.setName(node.getName());
//				defaultDestructor.setType("void");
//				defaultDestructor.setVisibility(FieldNode.PUBLIC);
//				
//				node.addChild(defaultDestructor);
//			}
//		}
		/*else */if (root instanceof TryNode)
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
		else if (root.containsScope())
		{
			if (root instanceof LoopNode || root instanceof IfStatementNode || root instanceof ElseStatementNode)
			{
				ScopeNode scope = root.getScopeNode();
				
				if (scope.getChildren().size() <= 1)
				{
					root.detach();
					
					return;
				}
			}
		}
		else if (root instanceof BinaryOperatorNode)
		{
			root.validate();
		}
		else if (root instanceof LocalVariableNode)
		{
			root.validate();
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
		root.generateCHeader();
	}
	
	/**
	 * Generate the C Source output from the data contained within the
	 * syntax tree.
	 */
	public void generateCSourceOutput()
	{
		root.generateCSource();
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
		for (int i = index; i < source.length() && i >= 0; i += direction)
		{
			char c = source.charAt(i);
			
			if (c != ' ' && c != '\t' && c != '\n' && c != '\r')
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
			
			headers[i] = child.generateCHeader();
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
			
			sources[i] = child.generateCSource();
		}
		
		return sources;
	}
	
	/**
	 * Get the filenames of all the files, in order of the way they
	 * are in the tree structure
	 * 
	 * @return The filenames of all the files, in order of the way they
	 * 		are in the tree structure.
	 */
	public String[] getFilenames()
	{
		String sources[] = new String[root.getChildren().size()];
		
		for (int i = 0; i < sources.length; i++)
		{
			FileNode child = root.getChild(i).getFileNode();
			
			sources[i] = child.getName();
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
	
	/**
	 * Class that is used to generate syntax tree data for a given file
	 * and source data.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.1 Apr 29, 2014 at 8:04:48 PM
	 * @version	v0.2.2 Apr 29, 2014 at 8:04:48 PM
	 */
	private class TreeGenerator implements Runnable
	{
		private int				statementStartIndex, statementEndIndex, oldStatementStartIndex;
		private int				lineNumber;
		
		private Matcher			statementStartMatcher;
		
		private String			filename, source;
		
		private TreeNode		currentNode;
		
		private Stack<TreeNode>	parentStack;
		
		/**
		 * Create a tree generator instance with the given filename and
		 * source data.
		 * 
		 * @param filename The name of the file to generate the tree for.
		 * @param source The source within the file to generate the tree
		 * 		for.
		 */
		public TreeGenerator(String filename, String source)
		{
			this.filename = filename;
			this.source   = source;
			
			init(source);
		}
		
		/**
		 * Initialize the data back to default values before any
		 * traversing of the code is done.
		 * 
		 * @param source The source that is about to be traversed.
		 */
		private void init(String source)
		{
			statementStartMatcher  = Patterns.STATEMENT_START.matcher(source);
			
			statementStartIndex    = 0;
			statementEndIndex      = 0;
			oldStatementStartIndex = 0;
			lineNumber             = 1;
		}
		
		/**
		 * The method that is used to actually do the act of generating
		 * the tree data.
		 */
		public void run()
		{
			if (phase == 1)
			{
				phase1(filename, source);
			}
			else if (phase == 2)
			{
				phase2(filename, source);
			}
			else if (phase == 3)
			{
				phase3(filename, source);
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
			while ((statementEndIndex = Regex.indexOfExcludeTextAndParentheses(source, statementStartIndex, statementType)) >= 0 && !statementStartMatcher.hitEnd())
			{
				statementEndIndex = nextNonWhitespaceIndexOnTheLeft(statementEndIndex - 1, source) + 1;
				
				int newStatementStartIndex = 0;
				
				if (statementStartMatcher.find(StringUtils.findNextNonWhitespaceIndex(source, statementEndIndex) + 1))
				{
					newStatementStartIndex = StringUtils.findNextNonWhitespaceIndex(source, statementStartMatcher.start());
				}
				
				int      offset2    = statementStartIndex;
				int      lineOffset = calculateOffset(statementStartIndex, source);
				
				String   statement  = source.substring(statementStartIndex, statementEndIndex);
				
				Location location   = new Location(lineNumber, lineOffset, offset2 + offset, offset2 + statement.length() + offset);
				
				TreeNode node       = decodeStatement(statement, location, searchTypes);

				updateLineNumber(statementStartIndex, newStatementStartIndex, source);
				
				if (node instanceof ExternalStatementNode)
				{
					int  index    = StringUtils.findNextNonWhitespaceIndex(source, statementEndIndex);
					int  endIndex = 0;
					
					char c        = source.charAt(index);
					
					if (c == '{')
					{
						endIndex = StringUtils.findEndingMatch(source, index, '{', '}');
						index    = StringUtils.findNextNonWhitespaceIndex(source, index + 1);
					}
					else
					{
						endIndex = source.indexOf(';', index);
					}
					
					endIndex = StringUtils.findNextNonWhitespaceIndex(source, endIndex - 1, -1) + 1;
					
					if (endIndex <= 0)
					{
						SyntaxMessage.error("External statement missing closing curly brace or semi-colon", controller);
						
						return null;
					}
					
					ExternalStatementNode external = (ExternalStatementNode)node;
					
					if (index < endIndex)
					{
						String data = source.substring(index, endIndex);
						
						external.setData(data);
					}
					else
					{
						external.setData("");
					}
					
					skipScope(source);
				}
				
				oldStatementStartIndex = statementStartIndex;
				
				if (node instanceof ExternalStatementNode == false)
				{
					statementStartIndex = newStatementStartIndex;
				}
				
				if (node != null)
				{
					return node;
				}
			}
			
			return null;
		}
		
		/**
		 * Check to see if the character is a new-line character. If so,
		 * increment the line-number variable.
		 * 
		 * @param start The index to start the search at.
		 * @param statementStart The index to end the search at.
		 * @param source The source String to search in.
		 */
		private void updateLineNumber(int start, int statementStart, String source) throws StringIndexOutOfBoundsException
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
		 * Generate the syntax tree nodes for all of the class nodes and
		 * import nodes.
		 * 
		 * @param filename The name of the file that is generating the syntax
		 * 		tree.
		 * @param source The source text within the file.
		 */
		private void phase1(String filename, String source)
		{
			filename = FileUtils.removeFileExtension(filename);
			
			controller.log("Phase one for '" + filename + "'...");
			
			FileNode fileNode = new FileNode();
			fileNode.setName(filename);
			
			root.addChild(fileNode);
			
			traverseCode(fileNode, source, 0, EITHER_STATEMENT_END_CHARS, FIRST_PASS_CLASSES, true);
		}
		
		/**
		 * Generate the syntax tree nodes for all of the field nodes and
		 * method nodes.
		 * 
		 * @param filename The name of the file that is generating the syntax
		 * 		tree.
		 * @param source The source text within the file.
		 */
		private void phase2(String filename, String source)
		{
			filename = FileUtils.removeFileExtension(filename);

			controller.log("Phase two for '" + filename + "'...");
			
			FileNode fileNode = root.getFile(filename);
			
			for (int i = 0; i < fileNode.getChildren().size(); i++)
			{
				TreeNode child = fileNode.getChild(i);
				
				if (child instanceof ClassNode)
				{
					ClassNode node    = (ClassNode)child;
					
					int startingIndex = StringUtils.findNextNonWhitespaceIndex(source, node.getLocationIn().getEnd());
					int endingIndex   = StringUtils.findEndingMatch(source, startingIndex, '{', '}');
					
					int contentStart  = StringUtils.findNextNonWhitespaceIndex(source, startingIndex + 1);
					int contentEnd    = StringUtils.findNextNonWhitespaceIndex(source, endingIndex - 1, -1) + 1;
					
					// If there is no content to decode.
					if (contentStart >= contentEnd)
					{
						continue;
					}
					
					String subSource  = source.substring(contentStart, contentEnd);
					
					traverseCode(node, subSource, contentStart, EITHER_STATEMENT_END_CHARS, SECOND_PASS_CLASSES, true);
				}
			}
		}
		
		/**
		 * Generate the syntax tree nodes for all of the method contents.
		 * 
		 * @param filename The name of the file that is generating the syntax
		 * 		tree.
		 * @param source The source text within the file.
		 */
		private void phase3(String filename, String source)
		{
			filename = FileUtils.removeFileExtension(filename);

			controller.log("Phase three for '" + filename + "'...");
			
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
		
		/**
		 * Decode all of the method contents.
		 * 
		 * @param methods The list of methods to decode.
		 * @param source The source text to decode.
		 */
		private void decodeMethodContents(MethodListNode methods, String source)
		{
			for (TreeNode child : methods.getChildren())
			{
				if (!child.getLocationIn().isValid())
				{
					continue;
				}
				
				MethodNode node   = (MethodNode)child;
				
				int startingIndex = StringUtils.findNextNonWhitespaceIndex(source, node.getLocationIn().getEnd());
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
		 * Traverse the given source code and generate the tree along with the
		 * data and parameters that are given.
		 * 
		 * @param parent The TreeNode to stem all of the following decoded
		 * 		data from.
		 * @param source The text to decode.
		 * @param offset The character offset within the file's source text
		 * 		overall.
		 * @param statementType The character array to use that determines what
		 * 		type of statements are searched for. Possible options include:
		 * 		<ul>
		 * 			<li>{@link #EITHER_STATEMENT_END_CHARS}</li>
		 * 			<li>{@link #SINGLE_STATEMENT_END_CHARS}</li>
		 * 			<li>{@link #SCOPE_STATEMENT_END_CHARS}</li>
		 * 		</ul>
		 * @param searchTypes The type of TreeNodes to try to decode.
		 * @param skipScopes Whether or not to skip the scopes of anything
		 * 		that contains a scope. If true, only decode the header.
		 */
		private void traverseCode(TreeNode parent, String source, int offset, char statementType[], Class<?> searchTypes[], boolean skipScopes)
		{
			init(source);
			
			if (parent.getLocationIn() != null)
			{
				lineNumber = parent.getLineNumber();
			}
			
			parentStack = new Stack<TreeNode>();
			
			parentStack.push(parent);
			
			currentNode = getNextStatement(source, offset, statementType, searchTypes);
			
			// Decode all of the statements in the source text.
			while (currentNode != null)
			{
				if (skipScopes && (currentNode.containsScope() || currentNode instanceof ClassNode))
				{
					skipScope(source);
				}
				
				if (!parentStack.isEmpty())
				{
					TreeNode parentNode = parentStack.peek();
					
					parentNode.addChild(currentNode);
				}
				
				if (statementEndIndex >= 0 && !skipScopes && nextChar(statementEndIndex, source) == '{')
				{
					parentStack.push(currentNode);
				}
				
				updateParents(oldStatementStartIndex, statementStartIndex, source);
				
				currentNode = getNextStatement(source, offset, statementType, searchTypes);
			}
		}
		
		/**
		 * Skip the current scope that the source has encountered.
		 */
		private void skipScope(String source)
		{
			int startingIndex = StringUtils.findNextNonWhitespaceIndex(source, statementEndIndex);
			int endingIndex   = StringUtils.findEndingMatch(source, startingIndex, '{', '}');

			updateLineNumber(statementEndIndex, endingIndex, source);
			
//			int contentStart  = StringUtils.findNextNonWhitespaceIndex(source, startingIndex + 1);
//			int contentEnd    = StringUtils.findNextNonWhitespaceIndex(source, endingIndex - 1, -1) + 1;
			
			statementStartIndex = StringUtils.findNextNonWhitespaceIndex(source, endingIndex + 1);
			
			if (statementStartIndex < 0)
			{
				statementStartIndex = source.length();
			}
			
			oldStatementStartIndex = statementStartIndex;
			statementEndIndex      = statementStartIndex;
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
	}
}