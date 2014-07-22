package net.fathomsoft.nova.tree;

import java.io.File;
import java.util.regex.Matcher;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.util.FileUtils;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Stack;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Class that is used to generate syntax tree data for a given file
 * and source data.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.1 Apr 29, 2014 at 8:04:48 PM
 * @version	v0.2.17 Jul 22, 2014 at 4:24:45 PM
 */
public class TreeGenerator implements Runnable
{
	private int			statementStartIndex, statementEndIndex, oldStatementStartIndex;
	private int			lineNumber;
	
	private Matcher		statementStartMatcher;
	
	private String		source;
	
	private File		file;
	
	private SyntaxTree	tree;
	
	private Nova		controller;
	
	private Stack<Node>	parentStack;
	private Stack<Node>	pendingScopeFragment;
	
	private static final char		EITHER_STATEMENT_END_CHARS[] = new char[] { '\n', ';', '{', '}' };
	
	private static final Class<?>	FIRST_PASS_CLASSES[] = new Class<?>[]
	{
		Import.class, ClassDeclaration.class
	};
	
	private static final Class<?>	SECOND_PASS_CLASSES[] = new Class<?>[]
	{
		ExternalMethodDeclaration.class, Destructor.class, Constructor.class,
		MethodDeclaration.class, ExternalType.class, FieldDeclaration.class
	};
	
	/**
	 * Create a tree generator instance with the given filename and
	 * source data.
	 * 
	 * @param file The file to generate the tree for.
	 * @param source The source within the file to generate the tree
	 * 		for.
	 */
	public TreeGenerator(File file, String source, SyntaxTree tree)
	{
		this.file       = file;
		this.source     = source;
		this.tree       = tree;
		
		this.controller = tree.getRoot().getController();
		
		statementStartMatcher  = Patterns.STATEMENT_START.matcher(source);
		
		statementStartIndex    = 0;
		statementEndIndex      = 0;
	}
	
	/**
	 * Initialize the data back to default values before any
	 * traversing of the code is done.
	 * 
	 * @param node 
	 * @param offset
	 */
	private void init(Node node, int offset)
	{
		parentStack          = new Stack<Node>();
		pendingScopeFragment = new Stack<Node>();
		
		parentStack.push(node);
		
		statementStartMatcher.reset();
		statementStartMatcher.find();
		
		statementStartIndex    = offset;
		statementEndIndex      = offset;
		oldStatementStartIndex = 0;
		lineNumber             = 0;
		
		updateLineNumber(node.getLocationIn().getEnd(), offset);
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
	 * The method that is used to actually do the act of generating
	 * the tree data.
	 */
	public void run()
	{
		int phase = tree.getPhase();
		
		if (phase == SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			phase1(file);
		}
		else if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			phase2(file);
		}
		else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			phase3(file);
		}
	}
	
	/**
	 * Get the File instance that this generator is generating from.
	 * 
	 * @return The File instance.
	 */
	public File getFile()
	{
		return file;
	}
	
	/**
	 * Generate the syntax tree nodes for all of the class nodes and
	 * import nodes.
	 * 
	 * @param file The file that is generating the syntax tree.
	 * @param source The source text within the file.
	 */
	private void phase1(File file)
	{
		Location location = new Location(1, 0, 0, 0);
		
		FileDeclaration fileDeclaration = new FileDeclaration(tree.getRoot(), location);
		fileDeclaration.setFile(file);
		
		controller.log("Phase one for '" + fileDeclaration.getName() + "'...");
		
		tree.addFile(fileDeclaration);
		
		traverseCode(fileDeclaration, 0, FIRST_PASS_CLASSES, true);
	}
	
	/**
	 * Generate the syntax tree nodes for all of the field nodes and
	 * method nodes.
	 * 
	 * @param file The file that is generating the syntax tree.
	 * @param source The source text within the file.
	 */
	private void phase2(File file)
	{
		String filename = FileUtils.removeFileExtension(file.getName());

		controller.log("Phase two for '" + filename + "'...");
		
		FileDeclaration fileDeclaration = tree.getRoot().getFile(filename);
		
		ClassDeclaration node = fileDeclaration.getClassDeclaration();
		
		// Finds the starting scope '{'
		int startingIndex = StringUtils.findNextNonWhitespaceIndex(source, node.getLocationIn().getEnd());
		// Finds the ending scope '}'
		int endingIndex   = StringUtils.findEndingMatch(source, startingIndex, '{', '}');
		
		int contentStart  = StringUtils.findNextNonWhitespaceIndex(source, startingIndex + 1);
		int contentEnd    = StringUtils.findNextNonWhitespaceIndex(source, endingIndex - 1, -1) + 1;
		
		// If there is no content to decode.
		if (contentStart >= contentEnd)
		{
			return;
		}
		
		traverseCode(node, contentStart, SECOND_PASS_CLASSES, true);
	}
	
	/**
	 * Generate the syntax tree nodes for all of the method contents.
	 * 
	 * @param file The file that is generating the syntax tree.
	 * @param source The source text within the file.
	 */
	private void phase3(File file)
	{
		String filename = FileUtils.removeFileExtension(file.getName());

		controller.log("Phase three for '" + filename + "'...");
		
		FileDeclaration  fileDeclaration  = tree.getRoot().getFile(filename);
		ClassDeclaration classDeclaration = fileDeclaration.getClassDeclaration();
		
		decodeMethodContents(classDeclaration.getMethodList());
		decodeMethodContents(classDeclaration.getConstructorList());
		decodeMethodContents(classDeclaration.getDestructorList());
		
		try
		{
			tree.validateNodes(fileDeclaration);
		}
		catch (SyntaxErrorException e)
		{
			
		}
	}
	
	/**
	 * Decode all of the method contents.
	 * 
	 * @param methods The list of methods to decode.
	 * @param source The source text to decode.
	 */
	private void decodeMethodContents(MethodList methods)
	{
		for (int i = 0; i < methods.getNumChildren(); i++)
		{
			MethodDeclaration node = (MethodDeclaration)methods.getChild(i);
			
			if (!node.getLocationIn().getBounds().isValid() || node.isExternal())
			{
				continue;
			}
			
			int startingIndex = StringUtils.findNextNonWhitespaceIndex(source, node.getLocationIn().getEnd());
			int endingIndex   = StringUtils.findEndingMatch(source, startingIndex, '{', '}');
			
			int contentStart  = StringUtils.findNextNonWhitespaceIndex(source, startingIndex + 1);
			int contentEnd    = StringUtils.findNextNonWhitespaceIndex(source, endingIndex - 1, -1) + 1;
			
			if (contentStart < contentEnd)
			{
				traverseCode(node, contentStart, null, false);
			}
		}
	}
	
	/**
	 * Traverse the given source code and generate the tree along with the
	 * data and parameters that are given.
	 * 
	 * @param parent The Node to stem all of the following decoded
	 * 		data from.
	 * @param source The text to decode.
	 * @param offset The character offset within the file's source text
	 * 		overall.
	 * @param searchTypes The type of Nodes to try to decode.
	 * @param skipScopes Whether or not to skip the scopes of anything
	 * 		that contains a scope. If true, only decode the header.
	 */
	private void traverseCode(Node parent, int offset, Class<?> searchTypes[], boolean skipScopes)
	{
		init(parent, offset);
		
		Node currentNode = getNextStatement(null, offset, searchTypes);
		
		// Decode all of the statements in the source text.
		while (currentNode != null)
		{
			updateTree(currentNode, skipScopes);
			
			if (parentStack.isEmpty())
			{
				break;
			}
			
			currentNode = getNextStatement(currentNode, offset, searchTypes);
		}
	}
	
	/**
	 * Search for the next statement. If a statement is found, return
	 * it in a Node format, if not return null.
	 * 
	 * @param source The source String to search in.
	 * @param previous The previously decoded node.
	 * @param offset The offset in the source file that the statement is.
	 * @param searchTypes The type of Nodes to try to decode.
	 * @return The Node containing the information, or null if it is
	 * 		not found.
	 */
	private Node getNextStatement(Node previous, int offset, Class<?> searchTypes[])
	{
		while ((statementEndIndex = calculateStatementEnd(statementStartIndex)) >= 0 && !statementStartMatcher.hitEnd() && !parentStack.isEmpty())
		{
			char endChar = '\0';
			
			int newStatementStartIndex = 0;
			
			if (statementEndIndex < source.length())
			{
				endChar = source.charAt(statementEndIndex);
				
				if (statementStartMatcher.find(statementEndIndex + 1))
				{
					newStatementStartIndex = StringUtils.findNextNonWhitespaceIndex(source, statementStartMatcher.start());
				}
			}
			
			boolean  scope      = endChar == '{';
			int      endBound   = StringUtils.findNextNonWhitespaceIndex(source, statementEndIndex - 1, -1) + 1;
			int      lineOffset = calculateOffset(endBound);
			String   statement  = source.substring(statementStartIndex, endBound);
			Location location   = new Location(lineNumber, lineOffset, statementStartIndex, endBound);
			
			adjustLocation(previous, location);
			
			Node node = decodeStatementAndCheck(statement, location, scope, searchTypes);
			
			checkScopeFragment(node, endChar, scope);
			
			updateLineNumber(statementStartIndex, newStatementStartIndex);
			
			oldStatementStartIndex = statementStartIndex;
			statementStartIndex    = newStatementStartIndex;
			
			if (node != null)
			{
				return node;
			}
			
			updateParents();
		}
		
		return null;
	}
	
	/**
	 * Calculate the index in which the next statement end is located at,
	 * after the given currentEnd.
	 * 
	 * @param source The source code to search through.
	 * @param currentEnd The index to search after.
	 * @return The new statement end index.
	 */
	private int calculateStatementEnd(int currentEnd)
	{
		currentEnd = SyntaxUtils.findCharInBaseScope(source, EITHER_STATEMENT_END_CHARS, currentEnd);
		
		if (currentEnd < 0)
		{
			if (StringUtils.findNextNonWhitespaceIndex(source, statementEndIndex + 1) < 0)
			{
				return -1;
			}
			
			return source.length();
		}
		else if (source.charAt(currentEnd) == ';')
		{
			return currentEnd;
		}
		
		int prevCharIndex = StringUtils.findNextNonWhitespaceIndex(source, currentEnd - 1, -1);
		int nextCharIndex = StringUtils.findNextNonWhitespaceIndex(source, currentEnd + 1);
		
		return calculateReturnValue(currentEnd, nextCharIndex, prevCharIndex);
	}
	
	/**
	 * Calculate the statement end's return value.
	 * 
	 * @param currentEnd The current end value of the statement.
	 * @param nextCharIndex The next char to the right of the current end.
	 * @param prevCharIndex The next char to the left of the current end.
	 * @return The return value of the statement end.
	 */
	private int calculateReturnValue(int currentEnd, int nextCharIndex, int prevCharIndex)
	{
		if (nextCharIndex < 0)
		{
			return -1;
		}
		
		if (source.charAt(nextCharIndex) == '{')
		{
			return nextCharIndex;
		}
		else if (checkStatementContinuation(prevCharIndex, nextCharIndex))
		{
			return calculateStatementEnd(currentEnd + 1);
		}
		
		return currentEnd;
	}
	
	/**
	 * Check whether or not to continue the search for the end of the
	 * statement.
	 * 
	 * @param nextCharIndex The next char to the right of the current end.
	 * @param prevCharIndex The next char to the left of the current end.
	 * @return Whether or not to continue the search for the end of the
	 * 		statement.
	 */
	private boolean checkStatementContinuation(int prevCharIndex, int nextCharIndex)
	{
		boolean left  = StringUtils.containsChar(source, StringUtils.STMT_CONT_CHARS, prevCharIndex) && !UnaryOperation.isUnaryOperator(source, prevCharIndex, -1);
		boolean right = StringUtils.containsChar(source, StringUtils.STMT_CONT_CHARS, nextCharIndex) && (!UnaryOperation.isUnaryOperator(source, nextCharIndex) || StringUtils.getGroupedSymbols(source, nextCharIndex).equals("-"));
		
		String prevWord = StringUtils.findNextWord(source, prevCharIndex, -1);
		
		return left ^ right || prevWord.equals("return");
	}
	
	/**
	 * Check the given Node to see if it is pending a scope fragment or
	 * not. For more information on what a scope fragment is, see
	 * {@link net.fathomsoft.nova.tree.Node#decodeScopeFragment(String, Bounds)}
	 * 
	 * @param node The Node to check.
	 * @param endChar The character that ends the statement of the given
	 * 		Node.
	 * @param scope Whether or not the Node starts a scope.
	 */
	private void checkScopeFragment(Node node, char endChar, boolean scope)
	{
		if (node == null || scope || pendingScopeFragment.check() == node || endChar == ';')
		{
			return;
		}
		
		if (node.pendingScopeFragment())
		{
			pendingScopeFragment.push(node);
		}
	}
	
	/**
	 * Try to decode the statement. If there is a syntax error within
	 * the given statement, then the method will try to compensate for
	 * error.
	 * 
	 * @param statement The statement to try to decode.
	 * @param location The location of the statement in the source
	 * 		file.
	 * @param scope Whether or not the statement starts off a scope.
	 * @param searchTypes The types of nodes that the statement could
	 * 		possibly be.
	 */
	private Node decodeStatementAndCheck(String statement, Location location, boolean scope, Class<?> searchTypes[])
	{
		try
		{
			return decodeStatement(statement, location, searchTypes);
		}
		catch (SyntaxErrorException e)
		{
			if (scope && !parentStack.isEmpty())
			{
				Node parent = parentStack.peek();
				
				if (parent.getParentClass(true) == null)
				{
					parentStack.push(ClassDeclaration.generateTemporaryClass(parent, location));
				}
				else
				{
					parentStack.push(MethodDeclaration.generateTemporaryMethod(parent, location));
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Update the tree's parent stack, indices, and line numbers, if
	 * necessary.
	 * 
	 * @param node The previously decoded node.
	 * @param skipScopes Whether or not to skip the scopes of anything
	 * 		that contains a scope. If true, only decode the header.
	 */
	private void updateTree(Node node, boolean skipScopes)
	{
		if (skipScopes && (node.containsScope() || node instanceof ClassDeclaration))
		{
			skipScope();
		}
		
		if (!parentStack.isEmpty())
		{
			parentStack.peek().addChild(node);
		}
		
		if ((statementEndIndex >= 0 && statementEndIndex < source.length() && !skipScopes && source.charAt(statementEndIndex) == '{') || (pendingScopeFragment.check() == node && node.pendingScopeFragment()))
		{
			parentStack.push(node);
		}
		
		updateParents();
	}
	
	/**
	 * Skip the current scope that the source has encountered.
	 */
	private void skipScope()
	{
		int contentIndex = StringUtils.findNextNonWhitespaceIndex(source, statementEndIndex + 1);
		int endingIndex  = StringUtils.findNextNonWhitespaceIndex(source, StringUtils.findEndingMatch(source, statementEndIndex, '{', '}') + 1);
		
		updateLineNumber(contentIndex, endingIndex);
		
		statementStartIndex = endingIndex;
		
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
	 */
	private void updateParents()
	{
		updateParents(oldStatementStartIndex, statementStartIndex);
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
		if (parentStack.isEmpty())
		{
			return;
		}
		
		if (!checkPendingScopeFragment())
		{
			for (int i = start; i < statementStart; i++)
			{
				if (source.charAt(i) == '}')
				{
					parentStack.pop();
					
					checkPendingScopeFragment();
				}
			}
		}
	}
	
	/**
	 * Check whether or not a pending scope fragment has been completed.
	 * 
	 * @return Whether or not a pending scope fragment has been completed.
	 */
	private boolean checkPendingScopeFragment()
	{
		if (!pendingScopeFragment.isEmpty())
		{
			Node node = pendingScopeFragment.peek();
			
			if (!node.pendingScopeFragment() && node == parentStack.peek())
			{
				parentStack.pop();
				pendingScopeFragment.pop();
				
				checkPendingScopeFragment();
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Decode the specified statement String at the given Location into a
	 * Node instance.
	 * 
	 * @param statement The statement String to decode into a Node.
	 * @param location The location of the statement in the source text.
	 * @param searchTypes The type of Nodes to try to decode.
	 * @return The result Node of decoding the statement String.
	 */
	private Node decodeStatement(String statement, Location location, Class<?> searchTypes[])
	{
		Node parent = parentStack.check();
		
		if (searchTypes != null)
		{
			return SyntaxTree.decodeStatement(parent, statement, location, searchTypes);
		}
		else
		{
			return SyntaxTree.decodeScopeContents(parent, statement, location, true);
		}
	}
	
	/**
	 * Update the number of new lines that exist between (inclusive)
	 * the bounds of [start, end].
	 * 
	 * @param start The starting index to begin the search for new
	 * 		lines at.
	 * @param end The ending index to end the search for new lines at.
	 */
	private void updateLineNumber(int start, int end)
	{
		lineNumber += StringUtils.numNewLines(start, end, source);
	}
	
	/**
	 * Adjust the given location to be relative within its parent
	 * scope.
	 * 
	 * @param node The previously decoded node.
	 * @param location The Location to adjust.
	 */
	private void adjustLocation(Node node, Location location)
	{
		if (node == null)
		{
			return;
		}
		
		int  phase = tree.getPhase();
		Node scope = null;
		
		for (int i = 0; i < parentStack.size(); i++)
		{
			scope = parentStack.peek(i);
			
			if ((phase == SyntaxTree.PHASE_METHOD_CONTENTS && scope instanceof MethodDeclaration) ||
					(phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS && scope instanceof ClassDeclaration) ||
					(phase == SyntaxTree.PHASE_CLASS_DECLARATION && scope instanceof FileDeclaration))
			{
				break;
			}
			
			Location scopeLoc = scope.getLocationIn();
			
			location.setLineNumber(location.getLineNumber() - scopeLoc.getLineNumber());
		}
	}
	
	/**
	 * Test the TreeGenerator class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test()
	{
		
		
		return null;
	}
}