package net.fathomsoft.nova.tree;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.exceptionhandling.Catch;
import net.fathomsoft.nova.tree.exceptionhandling.ExceptionHandler;
import net.fathomsoft.nova.tree.exceptionhandling.FinallyNode;
import net.fathomsoft.nova.tree.exceptionhandling.Throw;
import net.fathomsoft.nova.tree.exceptionhandling.Try;
import net.fathomsoft.nova.tree.variables.ArrayAccess;
import net.fathomsoft.nova.tree.variables.Array;
import net.fathomsoft.nova.tree.variables.Field;
import net.fathomsoft.nova.tree.variables.LocalVariable;
import net.fathomsoft.nova.tree.variables.VariableList;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.FileUtils;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.Stack;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Class that deconstructs a source code file and builds a Tree out of
 * the statements.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:15 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class SyntaxTree
{
	private int						phase;
	
	private Nova					controller;
	
	private Program				root;
	
	private TreeGenerator			generators[];
	
	private static final boolean	USE_THREADS = true;
	
	private static final char		EITHER_STATEMENT_END_CHARS[] = new char[] { '{', ';' };
	private static final char		SINGLE_STATEMENT_END_CHARS[] = new char[] { ';' };
	private static final char		SCOPE_STATEMENT_END_CHARS[]  = new char[] { '{', };
	
	private static final Class<?>	FIRST_PASS_CLASSES[] = new Class<?>[]
	{
		Import.class, ClassDeclaration.class
	};
	
	private static final Class<?>	SECOND_PASS_CLASSES[] = new Class<?>[]
	{
		Destructor.class, Constructor.class, Method.class, ExternalType.class,
		Field.class
	};
	
	private static final Class<?>	FILE_CHILD_DECODE[] = new Class<?>[]
	{
		Import.class, ClassDeclaration.class
	};
	
	private static final Class<?>	PRE_VALUE_DECODE[] = new Class<?>[]
	{
		Priority.class, Return.class, Assignment.class, BinaryOperator.class
	};
	
	private static final Class<?>	SCOPE_CHILD_DECODE[] = new Class<?>[]
	{
		ExceptionHandler.class, Assignment.class, Instantiation.class,
		ElseStatement.class, IfStatement.class, Loop.class, ArrayAccess.class,
		UnaryOperation.class, MethodCall.class, LocalDeclaration.class
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
	public SyntaxTree(File files[], Nova controller) throws IOException
	{
		String filenames[] = new String[files.length];
		String sources[]   = new String[files.length];
		
		for (int i = 0; i < files.length; i++)
		{
			filenames[i] = files[i].getName();
			sources[i]   = FileUtils.readFile(files[i]);
		}
		
		generate(files, sources, controller);
	}
	
	/**
	 * Generate a SyntaxTree instance given the name of the file and the
	 * source within it.
	 * 
	 * @param files The files containing the Foxy source code.
	 * @param sources The source codes inside the files.
	 * @param controller The controller of the compiling program.
	 */
	public SyntaxTree(File files[], String sources[], Nova controller)
	{
		generate(files, sources, controller);
	}
	
	/**
	 * Generate the SyntaxTree given the name of the file and the
	 * source within it.
	 * 
	 * @param files The files containing the sources.
	 * @param sources The source codes inside the files.
	 * @param controller The controller of the compiling program.
	 */
	private void generate(File files[], String sources[], Nova controller)
	{
		this.controller = controller;
		
		root = new Program(controller);
		
		controller.log("Generating syntax tree...");
		
		for (int i = 0; i < sources.length; i++)
		{
			sources[i] = removeComments(sources[i]);
		}
		
		initTreeGenerators(files, sources);
		
		try
		{
			phase(1);
			
			try
			{
				root.validateClasses(phase);
			}
			catch (SyntaxErrorException e)
			{
				
			}
			
			phase(2);
			
			try
			{
				root.validateFields(phase);
				root.validateMethods(phase);
			}
			catch (SyntaxErrorException e)
			{
				
			}
			
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
	 * @param files The files to generate trees for.
	 * @param sources The sources within the files to generate the trees
	 * 		for.
	 */
	private void initTreeGenerators(File files[], String sources[])
	{
		generators = new TreeGenerator[files.length];
		
		for (int i = 0; i < generators.length; i++)
		{
			TreeGenerator generator = new TreeGenerator(files[i], sources[i]);
			
			generators[i] = generator;
		}
	}
	
	/**
	 * Generate the SyntaxTree data for the specified phase. A phase
	 * specified what section of code that the compiler is decoding.<br>
	 * For instance:
	 * <ul>
	 * 	<li><b>Phase 1</b>: Decode imports and class headers</li>
	 * 	<li><b>Phase 2</b>: Decode fields, external types, and method declarations</li>
	 * 	<li><b>Phase 3</b>: Decode method contents</li>
	 * </ul>
	 * 
	 * @param phase The phase to start generating.
	 */
	private void phase(int phase) throws InterruptedException
	{
		if (controller.containsErrors())
		{
			return;
		}
		
		this.phase = phase;
		
		if (USE_THREADS)
		{
			Thread threads[] = new Thread[generators.length];
			
			for (int i = 0; i < generators.length; i++)
			{
				Thread generator = new Thread(generators[i]);
				generator.setName(generators[i].file.getName() + " Generator");
				
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
	 * {@link net.fathomsoft.nova.util.SyntaxUtils#isMainMethod(Method)}.
	 * 
	 * @return The MethodNode representation of the main method.
	 */
	public Method getMainMethod()
	{
		for (int i = 0; i < root.getNumChildren(); i++)
		{
			Node child = root.getChild(i);
			
			for (int j = 0; j < child.getNumChildren(); j++)
			{
				Node child2 = child.getChild(j);
				
				if (child2 instanceof ClassDeclaration)
				{
					ClassDeclaration classDeclaration = (ClassDeclaration)child2;
					
					MethodList methods = classDeclaration.getMethodListNode();
					
					for (int k = 0; k < methods.getNumChildren(); k++)
					{
						Method method = (Method)methods.getChild(k);
						
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
	 * Traverse through the tree and validate each node.
	 * 
	 * @param root The Node to validate, then validate the children.
	 */
	private void validateNodes(Node root)
	{
		root = root.validate(phase);
		
		if (root == null)
		{
			return;
		}
		
		for (int i = 0; i < root.getNumChildren(); i++)
		{
			Node child = root.getChild(i);
			
			validateNodes(child);
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
//		Pattern p = Patterns.COMMENT;
//		
//		Matcher m = p.matcher(source);
//		
		StringBuilder builder = new StringBuilder(source);
//		
//		int offset = 0;
//		
//		while (m.find())
//		{
//			builder.delete(m.start() - offset, m.end() - offset);
//			
//			int numLines = StringUtils.numNewLines(m.start(), m.end(), source);
//			
//			for (int i = 0; i < numLines; i++)
//			{
//				builder.insert(m.start() - offset, '\n');
//			}
//			
//			offset += m.end() - m.start() - numLines;
//		}
		
		String starts[] = new String[] { "/*" };
		String ends[]   = new String[] { "*/" };
		
		int offset = 0;
		
		int start  = StringUtils.findStrings(source, starts).getStart();
		
		while (start >= 0)
		{
			int end = StringUtils.findStrings(source, start, ends).getEnd();
			
			if (end < 0)
			{
				builder.delete(start - offset, builder.length());
				
				break;
			}
			
			builder.delete(start - offset, end - offset);
			
			int numLines = StringUtils.numNewLines(start + 2, end, source);
			
			for (int i = 0; i < numLines; i++)
			{
				builder.insert(start - offset, '\n');
			}
			
			offset += end - start - numLines;
			
			start = StringUtils.findStrings(source, end, starts).getStart();
		}

		offset = 0;
		
		starts = new String[] { "//" };
		
		start  = StringUtils.findStrings(builder, starts).getStart();
		
		while (start >= 0)
		{
			int end = builder.indexOf("\n", start);

			if (end <= 0)
			{
				builder.delete(start, builder.length());
				
				break;
			}
			
			builder.delete(start, end);
			
			offset += end - start + 1;

			start   = StringUtils.findStrings(builder, start, starts).getStart();
		}
		
		return builder.toString();
	}
	
	/**
	 * Decode the specific statement into its correct Node value. If
	 * the statement does not translate into a Node, a syntax error
	 * has occurred. 
	 * 
	 * @param parent The Parent Node of the current statement to be
	 * 		decoded.
	 * @param statement The statement to be decoded into a Node.
	 * @param location The Location in the source text where the statement
	 * 		is located at.
	 * @param types The types of Nodes to try to decode, in the given
	 * 		order.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The Node constructed from the statement, if any.
	 */
	public static Node decodeStatement(Node parent, String statement, Location location, boolean scope, Class<?> types[])
	{
		return decodeStatement(parent, statement, location, true, scope, types);
	}
	
	/**
	 * Decode the specific statement into its correct Node value. If
	 * the statement does not translate into a Node, a syntax error
	 * has occurred. 
	 * 
	 * @param parent The Parent Node of the current statement to be
	 * 		decoded.
	 * @param statement The statement to be decoded into a Node.
	 * @param location The Location in the source text where the statement
	 * 		is located at.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @param types The types of Nodes to try to decode, in the given
	 * 		order.
	 * @return The Node constructed from the statement, if any.
	 */
	public static Node decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope, Class<?> types[])
	{
		Node node = null;
		
//		try
//		{
			for (Class<?> type : types)
			{
//				Class<Node> a = (Class<Node>)type;
//				
//				Method m = a.getMethod("decodeStatement", Node.class, String.class, Location.class);
//				
//				node = (Node)m.invoke(a, parent, statement, location);
				
				if      (type == LocalDeclaration.class) node = LocalDeclaration.decodeStatement(parent, statement, location, require, scope);
				else if (type == IfStatement.class) node = IfStatement.decodeStatement(parent, statement, location, require, scope);
				else if (type == ElseStatement.class) node = ElseStatement.decodeStatement(parent, statement, location, require, scope);
				else if (type == ArrayAccess.class) node = ArrayAccess.decodeStatement(parent, statement, location, require, scope);
				else if (type == Assignment.class) node = Assignment.decodeStatement(parent, statement, location, require, scope);
				else if (type == Array.class) node = Array.decodeStatement(parent, statement, location, require, scope);
				else if (type == BinaryOperator.class) node = BinaryOperator.decodeStatement(parent, statement, location, require, scope);
				else if (type == ClassDeclaration.class) node = ClassDeclaration.decodeStatement(parent, statement, location, require, scope);
				else if (type == Constructor.class) node = Constructor.decodeStatement(parent, statement, location, require, scope);
				else if (type == Destructor.class) node = Destructor.decodeStatement(parent, statement, location, require, scope);
				else if (type == ExternalType.class) node = ExternalType.decodeStatement(parent, statement, location, require, scope);
				else if (type == IfStatement.class) node = IfStatement.decodeStatement(parent, statement, location, require, scope);
				else if (type == Import.class) node = Import.decodeStatement(parent, statement, location, require, scope);
				else if (type == Instantiation.class) node = Instantiation.decodeStatement(parent, statement, location, require, scope);
				else if (type == Literal.class) node = Literal.decodeStatement(parent, statement, location, require, scope);
				else if (type == Loop.class) node = Loop.decodeStatement(parent, statement, location, require, scope);
				else if (type == MethodCall.class) node = MethodCall.decodeStatement(parent, statement, location, require, scope);
				else if (type == Method.class) node = Method.decodeStatement(parent, statement, location, require, scope);
				else if (type == Priority.class) node = Priority.decodeStatement(parent, statement, location, require, scope);
				else if (type == Return.class) node = Return.decodeStatement(parent, statement, location, require, scope);
				else if (type == UnaryOperation.class) node = UnaryOperation.decodeStatement(parent, statement, location, require, scope);
				else if (type == Field.class) node = Field.decodeStatement(parent, statement, location, require, scope);
				else if (type == Catch.class) node = Catch.decodeStatement(parent, statement, location, require, scope);
				else if (type == ExceptionHandler.class) node = ExceptionHandler.decodeStatement(parent, statement, location, require, scope);
				else if (type == FinallyNode.class) node = FinallyNode.decodeStatement(parent, statement, location, require, scope);
				else if (type == Throw.class) node = Throw.decodeStatement(parent, statement, location, require, scope);
				else if (type == Try.class) node = Try.decodeStatement(parent, statement, location, require, scope);
				
				if (node != null)
				{
					return node;
				}
			}
//		}
//		catch (SecurityException e)
//		{
//			e.printStackTrace();
//		}
//		catch (NoSuchMethodException e)
//		{
//			e.printStackTrace();
//		}
//		catch (IllegalArgumentException e)
//		{
//			e.printStackTrace();
//		}
//		catch (IllegalAccessException e)
//		{
//			e.printStackTrace();
//		}
//		catch (InvocationTargetException e)
//		{
//			e.printStackTrace();
//		}
		
		node = ExternalStatement.decodeStatement(parent, statement, location, require, scope);
		
//		if (require)
//		{
//			SyntaxMessage.error("Unknown statement", parent, location);
//		}
		
		return node;
	}
	
	/**
	 * Decode a String that was found within a scope. That is, a method
	 * or scopes within a method: for loops, while loops, if statements,
	 * etc.
	 * 
	 * @param parent The parent node of the current statement to decode.
	 * @param statement The statement to decode.
	 * @param location The location of the statement.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The Node representation of the given statement.
	 */
	public static Node decodeScopeContents(Node parent, String statement, Location location, boolean scope)
	{
		return SyntaxTree.decodeScopeContents(parent, statement, location, true, scope);
	}
	
	/**
	 * Decode a String that was found within a scope. That is, a method
	 * or scopes within a method: for loops, while loops, if statements,
	 * etc.
	 * 
	 * @param parent The parent node of the current statement to decode.
	 * @param statement The statement to decode.
	 * @param location The location of the statement.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The Node representation of the given statement.
	 */
	public static Node decodeScopeContents(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (SyntaxUtils.isLiteral(statement))
		{
			return Literal.decodeStatement(parent, statement, location, require, scope);
		}
		
		try
		{
			Node test = decodeStatement(parent, statement, location, require, scope, PRE_VALUE_DECODE);
		
			if (test != null)
			{
				return test;
			}
		}
		catch (SyntaxErrorException e)
		{
			if (require)
			{
				throw e;
			}
		}
		
		Identifier root = decodeIdentifierAccess(parent, statement, location);
		
		if (root != null)
		{
			return root;
		}
		
		Node node = decodeStatement(parent, statement, location, require, scope, SCOPE_CHILD_DECODE);
		
		if (node == null)
		{
			node = decodeIdentifier(parent, statement, location);
		}
		
		if (node == null && require)
		{
			SyntaxMessage.error("Could not decode syntax '" + statement + "'", parent, location);
		}
		
		return node;
	}
	
	/**
	 * Method that tries to decode an identifier access expression.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.children.add(new Node());</pre></blockquote>
	 * The above expression is an identifier access because it uses the
	 * dot operator to access another node.
	 * 
	 * @param parent The parent of the statement to decode.
	 * @param statement The statement to decode as an identifier access.
	 * @param location The location in the source where this statement is.
	 */
	private static Identifier decodeIdentifierAccess(Node parent, String statement, Location location)
	{
		Identifier root = null;
		Identifier node = null;
		
		int offset = 0;
		int index  = SyntaxUtils.findDotOperator(statement);
		
		if (index < 0)
		{
			return null;
		}
		
		String current = statement.substring(offset, index);
		
		while (current != null)
		{
			node = decodeIdentifier(parent, current, location);
			
			if (node == null)
			{
				Location currentLoc = new Location(location);
				currentLoc.setBounds(offset, index);
				currentLoc.setLineNumber(location.getLineNumber());
				
				SyntaxMessage.error("Could not decode syntax '" + current + "'", parent, currentLoc);
			}
			
			location = new Location(location);
			
			if (root == null)
			{
				root = node;
			}
			else
			{
				parent.addChild(node);
				
				if (index < 0)
				{
					break;
				}
			}
			
			parent = node;
			
			offset = index + 1;
			index  = SyntaxUtils.findDotOperator(statement, offset);
			
			if (index > 0)
			{
				current = statement.substring(offset, index);
			}
			else
			{
				current = statement.substring(offset, statement.length());
			}
		}
		
		return root;
	}
	
	/**
	 * Decode the value of the given statement. Can be nodes such as
	 * VariableNodes, ExternalTypesNodes, FieldNodes, etc. May also
	 * be just a plain old ValueNode describing the return type of the
	 * statement. For example: a static class's method call.
	 * <code>Math.sin(number)</code> in this instance, Math will be the
	 * IdentifierNode returned. In most other instances a VariableNode
	 * variation will be returned.
	 * 
	 * @param parent The parent node of the current statement to decode.
	 * @param statement The statement to decode.
	 * @param location The location of the statement.
	 * @return The IdentifierNode representation of the given statement.
	 */
	private static Identifier decodeIdentifier(Node parent, String statement, Location location)
	{
		Identifier node = (Identifier)decodeStatement(parent, statement, location, false, SCOPE_CHILD_DECODE);
		
		if (node == null)
		{
			node = SyntaxTree.getExistingNode(parent, statement);
			
			if (node != null)
			{
				node = node.clone(parent, location);
			}
			else if (parent instanceof ExternalType)
			{
				ExternalType type = (ExternalType)parent;
				
				type.setType(statement);
				
				Identifier id = new Identifier(type, type.getLocationIn());
				id.setName(statement);
				
				node = id;
			}
			else if (parent.getFileNode().containsImport(statement) || parent.getFileNode().containsClass(statement))
			{
				ClassDeclaration clazz = parent.getProgramNode().getClassNode(statement);
				
				if (clazz != null)
				{
					Identifier id = new Identifier(parent, location);
					id.setName(clazz.getName());
					id.setType(clazz.getName());
					
					return id;
				}
			}
		}
		
		return node;
	}
	
	/**
	 * Try to find an existing node from the given statement. This method
	 * searches through fields and local variables.
	 * 
	 * @param parent The parent Node to use as our context.
	 * @param statement The statement to check for an existing node from.
	 * @return The IdentifierNode that is found, if any.
	 */
	public static Variable getExistingNode(Node parent, String statement)
	{
		if (SyntaxUtils.isLiteral(statement))
		{
			return null;
		}
		else if (SyntaxUtils.isMethodCall(statement))
		{
			return null;
		}
		else if (SyntaxUtils.isValidIdentifier(statement))
		{
			ClassDeclaration clazz = null;
			
			if (parent instanceof LocalVariable || parent instanceof Field)
			{
				Variable var = (Variable)parent;
				
				clazz  = var.getProgramNode().getClassNode(var.getType());
			}
			
			if (clazz != null)
			{
				Variable var   = (Variable)parent;
				
				Field    field = clazz.getField(statement);
				
				if (field != null)
				{
					if (SyntaxUtils.isVisible(var, field))
					{
						return field;
					}
					else
					{
						SyntaxMessage.error("Field '" + field.getName() + "' is not accessible", parent);
					}
				}
			}
			
			Node scopeNode = parent.getAncestorWithScope();
			
			while (scopeNode != null)
			{
				VariableList variables = scopeNode.getScopeNode().getVariableListNode();
				
				Variable     variable  = variables.getVariable(statement);
				
				if (variable != null)
				{
					return variable;
				}
				
				if (scopeNode instanceof Method)
				{
					Method        method = (Method)scopeNode;
					ParameterList parameters = method.getParameterListNode();
					Parameter     parameter  = parameters.getParameterNode(statement);
					
					if (parameter != null)
					{
						return parameter;
					}
				}
				
				scopeNode = scopeNode.getParent().getAncestorWithScope();
			}
			
			if (parent instanceof ParameterList)
			{
				ParameterList parameters = (ParameterList)parent;
				
				Parameter     parameter  = parameters.getParameterNode(statement);
				
				if (parameter != null)
				{
					return parameter;
				}
			}
			
			clazz = (ClassDeclaration)parent.getAncestorOfType(ClassDeclaration.class, true);
			
			if (clazz != null)
			{
				Field field = clazz.getField(statement);
				
				if (field != null)
				{
					return field;
				}
			}
		}
		
		return null;
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
	 * Get the next non-whitespace character while traversing the String
	 * to the left.
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
	 * Get the next non-whitespace character while traversing the String
	 * to the right.
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
		String headers[] = new String[root.getNumChildren()];
		
		for (int i = 0; i < headers.length; i++)
		{
			Node child = root.getChild(i);
			
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
		String sources[] = new String[root.getNumChildren()];
		
		for (int i = 0; i < sources.length; i++)
		{
			Node child = root.getChild(i);
			
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
	public FileDeclaration[] getFiles()
	{
		FileDeclaration sources[] = new FileDeclaration[root.getNumChildren()];
		
		for (int i = 0; i < sources.length; i++)
		{
			FileDeclaration child = root.getChild(i).getFileNode();
			
			sources[i] = child;
		}
		
		return sources;
	}
	
	/**
	 * Get the root ProgramNode of the tree. The root of the Syntax tree
	 * will be a ProgramNode.
	 * 
	 * @return The root ProgramNode instance.
	 */
	public Program getRoot()
	{
		return root;
	}
	
	/**
	 * Class that is used to generate syntax tree data for a given file
	 * and source data.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.1 Apr 29, 2014 at 8:04:48 PM
	 * @version	v0.2.5 May 22, 2014 at 2:56:28 PM
	 */
	private class TreeGenerator implements Runnable
	{
		private int				statementStartIndex, statementEndIndex, oldStatementStartIndex;
		private int				lineNumber;
		
		private Matcher			statementStartMatcher;
		
		private String			source;
		
		private File			file;
		
		private Stack<Node>	parentStack;
		
		/**
		 * Create a tree generator instance with the given filename and
		 * source data.
		 * 
		 * @param file The file to generate the tree for.
		 * @param source The source within the file to generate the tree
		 * 		for.
		 */
		public TreeGenerator(File file, String source)
		{
			this.file   = file;
			this.source = source;
			
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
			lineNumber             = 0;
		}
		
		/**
		 * The method that is used to actually do the act of generating
		 * the tree data.
		 */
		public void run()
		{
			if (phase == 1)
			{
				phase1(file, source);
			}
			else if (phase == 2)
			{
				phase2(file, source);
			}
			else if (phase == 3)
			{
				phase3(file, source);
			}
		}
		
		/**
		 * Check whether or not the given node is external. If it is,
		 * treat it as such and then skip the search index to after the
		 * ending brace of the external statement.
		 * 
		 * @param node The Node to check whether or not is an
		 * 		ExternalStatementNode.
		 * @param source The source to traverse through.
		 */
		private void checkExternal(Node node, String source)
		{
			if (node instanceof ExternalStatement)
			{
				int  index    = statementEndIndex;
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
				}
				
				ExternalStatement external = (ExternalStatement)node;
				
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
		}
		
		/**
		 * Generate the syntax tree nodes for all of the class nodes and
		 * import nodes.
		 * 
		 * @param file The file that is generating the syntax tree.
		 * @param source The source text within the file.
		 */
		private void phase1(File file, String source)
		{
			Location location = new Location(1, 0, 0, 0);
			
			FileDeclaration fileDeclaration = new FileDeclaration(root, location);
			fileDeclaration.setFile(file);
			
			controller.log("Phase one for '" + fileDeclaration.getName() + "'...");
			
			root.addChild(fileDeclaration);
			
			init(source);
			
			traverseCode(fileDeclaration, source, 0, EITHER_STATEMENT_END_CHARS, FIRST_PASS_CLASSES, true);
		}
		
		/**
		 * Generate the syntax tree nodes for all of the field nodes and
		 * method nodes.
		 * 
		 * @param file The file that is generating the syntax tree.
		 * @param source The source text within the file.
		 */
		private void phase2(File file, String source)
		{
			String filename = file.getName();
			
			filename = FileUtils.removeFileExtension(filename);

			controller.log("Phase two for '" + filename + "'...");
			
			FileDeclaration fileDeclaration = root.getFile(filename);
			
			if (fileDeclaration == null)
			{
				return;
			}
			
			for (int i = 0; i < fileDeclaration.getNumChildren(); i++)
			{
				Node child = fileDeclaration.getChild(i);
				
				if (child instanceof ClassDeclaration)
				{
					ClassDeclaration node = (ClassDeclaration)child;
					
					// Finds the starting scope '{'
					int startingIndex = StringUtils.findNextNonWhitespaceIndex(source, node.getLocationIn().getEnd());
					// Finds the ending scope '}'
					int endingIndex   = StringUtils.findEndingMatch(source, startingIndex, '{', '}');
					
					int contentStart  = StringUtils.findNextNonWhitespaceIndex(source, startingIndex + 1);
					int contentEnd    = StringUtils.findNextNonWhitespaceIndex(source, endingIndex - 1, -1) + 1;
					
					// If there is no content to decode.
					if (contentStart >= contentEnd)
					{
						continue;
					}
					
					String subSource = source.substring(contentStart, contentEnd);
					
					init(subSource);
					updateLineNumber(node.getLocationIn().getEnd(), contentStart, source);
					
					traverseCode(node, subSource, contentStart, EITHER_STATEMENT_END_CHARS, SECOND_PASS_CLASSES, true);
				}
			}
		}
		
		/**
		 * Generate the syntax tree nodes for all of the method contents.
		 * 
		 * @param file The file that is generating the syntax tree.
		 * @param source The source text within the file.
		 */
		private void phase3(File file, String source)
		{
			String filename = file.getName();
			
			filename = FileUtils.removeFileExtension(filename);

			controller.log("Phase three for '" + filename + "'...");
			
			FileDeclaration  fileDeclaration  = root.getFile(filename);
			
			if (fileDeclaration == null)
			{
				return;
			}
			
			ClassDeclaration classDeclaration = fileDeclaration.getClassNode();
			
			MethodList methods = classDeclaration.getMethodListNode();
			decodeMethodContents(methods, source);
			
			MethodList constructors = classDeclaration.getConstructorListNode();
			decodeMethodContents(constructors, source);
			
			MethodList destructors = classDeclaration.getDestructorListNode();
			decodeMethodContents(destructors, source);
			
			try
			{
				validateNodes(fileDeclaration);
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
		private void decodeMethodContents(MethodList methods, String source)
		{
			for (int i = 0; i < methods.getNumChildren(); i++)
			{
				Method node = (Method)methods.getChild(i);
				
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
					String subSource = source.substring(contentStart, contentEnd);
					
					init(subSource);
					updateLineNumber(node.getLocationIn().getEnd(), contentStart, source);
					
					traverseCode(node, subSource, contentStart, EITHER_STATEMENT_END_CHARS, null, false);
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
		 * @param statementType The character array to use that determines what
		 * 		type of statements are searched for. Possible options include:
		 * 		<ul>
		 * 			<li>{@link #EITHER_STATEMENT_END_CHARS}</li>
		 * 			<li>{@link #SINGLE_STATEMENT_END_CHARS}</li>
		 * 			<li>{@link #SCOPE_STATEMENT_END_CHARS}</li>
		 * 		</ul>
		 * @param searchTypes The type of Nodes to try to decode.
		 * @param skipScopes Whether or not to skip the scopes of anything
		 * 		that contains a scope. If true, only decode the header.
		 */
		private void traverseCode(Node parent, String source, int offset, char statementType[], Class<?> searchTypes[], boolean skipScopes)
		{
			parentStack = new Stack<Node>();
			
			parentStack.push(parent);
			
			Node currentNode = getNextStatement(source, null, offset, statementType, searchTypes);
			
			// Decode all of the statements in the source text.
			while (currentNode != null)
			{
				updateTree(source, currentNode, skipScopes);
				
				currentNode = getNextStatement(source, currentNode, offset, statementType, searchTypes);
			}
		}
		
		/**
		 * Search for the next statement. If a statement is found, return
		 * it in a Node format, if not return null.
		 * 
		 * @param source The source String to search in.
		 * @param previous The previously decoded node.
		 * @param offset The offset in the source file that the statement is.
		 * @param statementType The character array to use that determines what
		 * 		type of statements are searched for. Possible options include:
		 * 		<ul>
		 * 			<li>{@link #EITHER_STATEMENT_END_CHARS}</li>
		 * 			<li>{@link #SINGLE_STATEMENT_END_CHARS}</li>
		 * 			<li>{@link #SCOPE_STATEMENT_END_CHARS}</li>
		 * 		</ul>
		 * @param searchTypes The type of Nodes to try to decode.
		 * @return The Node containing the information, or null if it is
		 * 		not found.
		 */
		private Node getNextStatement(String source, Node previous, int offset, char statementType[], Class<?> searchTypes[])
		{
			while ((statementEndIndex = Regex.indexOfExcludeTextAndParentheses(source, statementStartIndex, statementType)) >= 0 && !statementStartMatcher.hitEnd())
			{
				boolean scope = source.charAt(statementEndIndex) == '{';
				
				int newStatementStartIndex = 0;
				
				if (statementStartMatcher.find(statementEndIndex + 1))
				{
					newStatementStartIndex = StringUtils.findNextNonWhitespaceIndex(source, statementStartMatcher.start());
				}
				
				int      endBound   = nextNonWhitespaceIndexOnTheLeft(statementEndIndex - 1, source) + 1;
				int      offset2    = statementStartIndex;
				int      lineOffset = calculateOffset(endBound, source);
				String   statement  = source.substring(statementStartIndex, endBound);
				Location location   = new Location(lineNumber, lineOffset, offset2 + offset, offset2 + statement.length() + offset);
				
				adjustLocation(previous, location);
				
				Node node = decodeStatementAndCheck(statement, location, scope, searchTypes);
				
				updateLineNumber(statementStartIndex, newStatementStartIndex, source);
				
				checkExternal(node, source);
				
				oldStatementStartIndex = statementStartIndex;
				
				if (node instanceof ExternalStatement == false)
				{
					statementStartIndex = newStatementStartIndex;
				}
				
				if (node != null)
				{
					return node;
				}
				
				updateParents(source);
			}
			
			return null;
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
				return decodeStatement(statement, location, scope, searchTypes);
			}
			catch (SyntaxErrorException e)
			{
				if (scope && !parentStack.isEmpty())
				{
					Node parent = parentStack.peek();
					
					if (parent.getClassNode() == null)
					{
						parentStack.push(ClassDeclaration.generateTemporaryClass(parent, location));
					}
					else
					{
						parentStack.push(Method.generateTemporaryMethod(parent, location));
					}
				}
			}
			
			return null;
		}
		
		/**
		 * Update the tree's parent stack, indices, and line numbers, if
		 * necessary.
		 * 
		 * @param source The text that was just decoded.
		 * @param node The previously decoded node.
		 * @param skipScopes Whether or not to skip the scopes of anything
		 * 		that contains a scope. If true, only decode the header.
		 */
		private void updateTree(String source, Node node, boolean skipScopes)
		{
			if (skipScopes && (node.containsScope() || node instanceof ClassDeclaration))
			{
				skipScope(source);
			}
			
			if (!parentStack.isEmpty())
			{
				Node parentNode = parentStack.peek();
				
				parentNode.addChild(node);
			}
			
			if (statementEndIndex >= 0 && !skipScopes && source.charAt(statementEndIndex) == '{')
			{
				parentStack.push(node);
			}
			
			updateParents(source);
		}
		
		/**
		 * Skip the current scope that the source has encountered.
		 * 
		 * @param source The source to use as a reference.
		 */
		private void skipScope(String source)
		{
			int contentIndex = StringUtils.findNextNonWhitespaceIndex(source, statementEndIndex + 1);
			int endingIndex  = StringUtils.findNextNonWhitespaceIndex(source, StringUtils.findEndingMatch(source, statementEndIndex, '{', '}') + 1);
			
			updateLineNumber(contentIndex, endingIndex, source); 
			
//			int contentStart = StringUtils.findNextNonWhitespaceIndex(source, startingIndex + 1);
//			int contentEnd   = StringUtils.findNextNonWhitespaceIndex(source, endingIndex - 1, -1) + 1;
			
			statementStartIndex = endingIndex;// + 1;//StringUtils.findNextNonWhitespaceIndex(source, endingIndex + 1);
			
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
		 * @param source The source String to search in.
		 */
		private void updateParents(String source)
		{
			updateParents(oldStatementStartIndex, statementStartIndex, source);
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
				if (source.charAt(i) == '}' && !parentStack.isEmpty())
				{
					parentStack.pop();
				}
			}
		}
		
		/**
		 * Decode the specified statement String at the given Location into a
		 * Node instance.
		 * 
		 * @param statement The statement String to decode into a Node.
		 * @param location The location of the statement in the source text.
		 * @param searchTypes The type of Nodes to try to decode.
		 * @param scope Whether or not the given statement is the beginning of
		 * 		a scope.
		 * @return The result Node of decoding the statement String.
		 */
		private Node decodeStatement(String statement, Location location, boolean scope, Class<?> searchTypes[])
		{
			Node parent = null;
			
			if (!parentStack.isEmpty())
			{
				parent = parentStack.peek();
			}
			
			if (searchTypes != null)
			{
				return SyntaxTree.decodeStatement(parent, statement, location, scope, searchTypes);
			}
			else
			{
				return SyntaxTree.decodeScopeContents(parent, statement, location, true, scope);
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
		private void updateLineNumber(int start, int end, String source)
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
			
			Node scope = null;
			
			for (int i = 0; i < parentStack.size(); i++)
			{
				scope = parentStack.peek(i);
				
				if ((phase == 3 && scope instanceof Method) ||
						(phase == 1 && scope instanceof FileDeclaration))
				{
					break;
				}
				
				Location scopeLoc = scope.getLocationIn();
				
				location.setLineNumber(location.getLineNumber() - scopeLoc.getLineNumber());
			}
		}
	}
}