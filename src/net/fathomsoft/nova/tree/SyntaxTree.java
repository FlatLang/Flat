package net.fathomsoft.nova.tree;

import java.io.File;
import java.io.IOException;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.exceptionhandling.Catch;
import net.fathomsoft.nova.tree.exceptionhandling.ExceptionHandler;
import net.fathomsoft.nova.tree.exceptionhandling.Finally;
import net.fathomsoft.nova.tree.exceptionhandling.Throw;
import net.fathomsoft.nova.tree.exceptionhandling.Try;
import net.fathomsoft.nova.tree.variables.Array;
import net.fathomsoft.nova.tree.variables.ArrayAccess;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.tree.variables.VariableDeclarationList;
import net.fathomsoft.nova.util.FileUtils;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Class that deconstructs a source code file and builds a Tree out of
 * the statements.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:15 PM
 * @version	v0.2.36 Oct 13, 2014 at 12:16:42 AM
 */
public class SyntaxTree
{
	private int						phase;
	
	private Nova					controller;
	
	private Program					root;
	
	private TreeGenerator			generators[];
	
	private boolean					useThreads;
	
	public static final int			PHASE_CLASS_DECLARATION = 1, PHASE_INSTANCE_DECLARATIONS = 2,
									PHASE_METHOD_CONTENTS = 3, PHASE_PRE_GENERATION = 4;
	
	private static final Class<?>	PRE_VALUE_DECODE[] = new Class<?>[]
	{
		IfStatement.class, ElseStatement.class, Priority.class, Return.class,
		Assignment.class, BinaryOperation.class
	};
	
	private static final Class<?>	SCOPE_CHILD_DECODE[] = new Class<?>[]
	{
		Break.class, Continue.class, ExceptionHandler.class, Assignment.class,
		Instantiation.class, ArrayAccess.class, ElseStatement.class,
		IfStatement.class, Until.class, Loop.class, Array.class,
		UnaryOperation.class, Cast.class, MethodCall.class, LocalDeclaration.class
	};
	
	public static final Class<?>	FIRST_PASS_CLASSES[] = new Class<?>[]
	{
		Import.class, ClassDeclaration.class, Package.class
	};
	
	public static final Class<?>	SECOND_PASS_CLASSES[] = new Class<?>[]
	{
		StaticBlock.class, AbstractMethodDeclaration.class, ExternalMethodDeclaration.class,
		Destructor.class, Constructor.class, BodyMethodDeclaration.class, ExternalType.class,
		FieldDeclaration.class
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
		this.useThreads = !controller.isFlagEnabled(Nova.SINGLE_THREAD);
		this.controller = controller;
		
		root = new Program(controller, this);
		
		controller.log("Generating syntax tree...");
		
		for (int i = 0; i < sources.length; i++)
		{
			sources[i] = removeComments(sources[i]);
		}
		
		initTreeGenerators(files, sources);
		
		try
		{
			phase(PHASE_CLASS_DECLARATION);
			phase(PHASE_INSTANCE_DECLARATIONS);
			phase(PHASE_METHOD_CONTENTS);
			
			phase = PHASE_PRE_GENERATION;
			
			validateNodes(root);
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
			TreeGenerator generator = new TreeGenerator(files[i], sources[i], this);
			
			generators[i] = generator;
		}
	}
	
	/**
	 * Get the phase that the SyntaxTree is generating in currently.
	 * <ul>
	 * 	<li><b>Phase 1</b>: Decode imports and class headers</li>
	 * 	<li><b>Phase 2</b>: Decode fields, external types, and method declarations</li>
	 * 	<li><b>Phase 3</b>: Decode method contents</li>
	 * </ul>
	 * 
	 * @return The phase (1, 2, or 3) that the SyntaxTree is currently
	 * 		generating in.
	 */
	public int getPhase()
	{
		return phase;
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
		
		if (useThreads)
		{
			Thread threads[] = new Thread[generators.length];
			
			for (int i = 0; i < generators.length; i++)
			{
				Thread generator = new Thread(generators[i]);
				generator.setName(generators[i].getFile().getName() + " Generator");
				
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
		
		if (phase == PHASE_CLASS_DECLARATION)
		{
			root.addAutoImports();
		}
		
		validateNodes(root);
	}
	
	/**
	 * Search for the main method, if one exists, in the compiling
	 * program. For more details on what the main method looks like, see
	 * {@link net.fathomsoft.nova.util.SyntaxUtils#isMainMethod(BodyMethodDeclaration)}.
	 * 
	 * @return The Method representation of the main method.
	 */
	public MethodDeclaration getMainMethod()
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
					
					MethodList methods = classDeclaration.getMethodList();
					
					for (int k = 0; k < methods.getNumChildren(); k++)
					{
						MethodDeclaration methodDeclaration = (MethodDeclaration)methods.getChild(k);
						
						if (methodDeclaration.containsBody())
						{
							if (SyntaxUtils.isMainMethod((BodyMethodDeclaration)methodDeclaration))
							{
								return methodDeclaration;
							}
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
	public ValidationResult validateNodes(Node root)
	{
		ValidationResult result = null;
		
		try
		{
			result = root.validate(phase);
			
			if (!result.continueValidation || result.skipCycle)
			{
				return result.skippedCycle();
			}
			
			root = result.returnedNode;
			
			for (int i = 0; i < root.getNumChildren(); i++)
			{
				Node child = root.getChild(i);
				
				result = validateNodes(child);
				
				if (!result.continueValidation)
				{
					return result;
				}
			}
		}
		catch (SyntaxErrorException e)
		{
			
		}
		
		return result;
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
			int end = StringUtils.findStrings(source, ends, start).getEnd();
			
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
			start   = StringUtils.findStrings(source, starts, end).getStart();
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
			start   = StringUtils.findStrings(builder, starts, start).getStart();
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
	 * @return The Node constructed from the statement, if any.
	 */
	public static Node decodeStatement(Node parent, String statement, Location location, Class<?> types[])
	{
		return decodeStatement(parent, statement, location, true, types);
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
	 * @param types The types of Nodes to try to decode, in the given
	 * 		order.
	 * @return The Node constructed from the statement, if any.
	 */
	public static Node decodeStatement(Node parent, String statement, Location location, boolean require, Class<?> types[])
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
				
				if      (type == LocalDeclaration.class) node = LocalDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ElseStatement.class) node = ElseStatement.decodeStatement(parent, statement, location, require);
				else if (node == null && type == AbstractMethodDeclaration.class) node = AbstractMethodDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ArrayAccess.class) node = ArrayAccess.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Assignment.class) node = Assignment.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Array.class) node = Array.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Cast.class) node = Cast.decodeStatement(parent, statement, location, require);
				else if (node == null && type == BinaryOperation.class) node = BinaryOperation.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Break.class) node = Break.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Continue.class) node = Continue.decodeStatement(parent, statement, location, require);
				else if (node == null && type == MethodCall.class) node = MethodCall.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ClassDeclaration.class) node = ClassDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Constructor.class) node = Constructor.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Destructor.class) node = Destructor.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ExternalMethodDeclaration.class) node = ExternalMethodDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ExternalType.class) node = ExternalType.decodeStatement(parent, statement, location, require);
				else if (node == null && type == IfStatement.class) node = IfStatement.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Import.class) node = Import.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Instantiation.class) node = Instantiation.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Literal.class) node = Literal.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Loop.class) node = Loop.decodeStatement(parent, statement, location, require);
				else if (node == null && type == BodyMethodDeclaration.class) node = BodyMethodDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Package.class) node = Package.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Priority.class) node = Priority.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Return.class) node = Return.decodeStatement(parent, statement, location, require);
				else if (node == null && type == UnaryOperation.class) node = UnaryOperation.decodeStatement(parent, statement, location, require);
				else if (node == null && type == FieldDeclaration.class) node = FieldDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Catch.class) node = Catch.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ExceptionHandler.class) node = ExceptionHandler.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Finally.class) node = Finally.decodeStatement(parent, statement, location, require);
				else if (node == null && type == StaticBlock.class) node = StaticBlock.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Throw.class) node = Throw.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Try.class) node = Try.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Until.class) node = Until.decodeStatement(parent, statement, location, require);
				
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
		if (require)
		{
			syntaxError(statement, parent, location);
		}
		
		return node;
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
	 * @param type The type of Node to try to decode.
	 * @return The Node constructed from the statement, if any.
	 */
	public static Node decodeStatementOfType(Node parent, String statement, Location location, boolean require, Class<?> type)
	{
		Node node = null;

		if      (type.isAssignableFrom(Loop.class) && (node = Loop.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Instantiation.class) && (node = Instantiation.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(IfStatement.class) && (node = IfStatement.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(AbstractMethodDeclaration.class) && (node = AbstractMethodDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Cast.class) && (node = Cast.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ArrayAccess.class) && (node = ArrayAccess.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Assignment.class) && (node = Assignment.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Array.class) && (node = Array.decodeStatement(parent, statement, location, require)) != null);
//		else if (type.isAssignableFrom(Bool.class) && (node = Bool.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Break.class) && (node = Break.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Continue.class) && (node = Continue.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(BinaryOperation.class) && (node = BinaryOperation.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ClassDeclaration.class) && (node = ClassDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Constructor.class) && (node = Constructor.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Destructor.class) && (node = Destructor.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ElseStatement.class) && (node = ElseStatement.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ExternalMethodDeclaration.class) && (node = ExternalMethodDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ExternalType.class) && (node = ExternalType.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Import.class) && (node = Import.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Literal.class) && (node = Literal.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(LocalDeclaration.class) && (node = LocalDeclaration.decodeStatement(parent, statement, location, require)) != null);
//		else if (type.isAssignableFrom(Null.class) && (node = Null.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(MethodCall.class) && (node = MethodCall.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(BodyMethodDeclaration.class) && (node = BodyMethodDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Priority.class) && (node = Priority.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Return.class) && (node = Return.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(UnaryOperation.class) && (node = UnaryOperation.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(FieldDeclaration.class) && (node = FieldDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Catch.class) && (node = Catch.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ExceptionHandler.class) && (node = ExceptionHandler.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Finally.class) && (node = Finally.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(StaticBlock.class) && (node = StaticBlock.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Throw.class) && (node = Throw.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Try.class) && (node = Try.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Until.class) && (node = Until.decodeStatement(parent, statement, location, require)) != null);
		
		return node;
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
	 * @param declaration Whether or not to search for a declaration type.
	 * @return The Node constructed from the statement, if any.
	 */
	public static Node decodeVariable(Node parent, String statement, Location location, boolean require, boolean declaration)
	{
		Class<?> type  = Identifier.class;
		Class<?> type2 = VariableDeclaration.class;
		
		Node node = null;

		if      (type2.isAssignableFrom(AbstractMethodDeclaration.class) == declaration && type.isAssignableFrom(AbstractMethodDeclaration.class) && (node = AbstractMethodDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(ArrayAccess.class) == declaration && type.isAssignableFrom(ArrayAccess.class) && (node = ArrayAccess.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(Array.class) == declaration && type.isAssignableFrom(Array.class) && (node = Array.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(ClassDeclaration.class) == declaration && type.isAssignableFrom(ClassDeclaration.class) && (node = ClassDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(Closure.class) == declaration && type.isAssignableFrom(Closure.class) && (node = Closure.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(Constructor.class) == declaration && type.isAssignableFrom(Constructor.class) && (node = Constructor.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(Destructor.class) == declaration && type.isAssignableFrom(Destructor.class) && (node = Destructor.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(ExternalMethodDeclaration.class) == declaration && type.isAssignableFrom(ExternalMethodDeclaration.class) && (node = ExternalMethodDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(Instantiation.class) == declaration && type.isAssignableFrom(Instantiation.class) && (node = Instantiation.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(LocalDeclaration.class) == declaration && type.isAssignableFrom(LocalDeclaration.class) && (node = LocalDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(MethodCall.class) == declaration && type.isAssignableFrom(MethodCall.class) && (node = MethodCall.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(BodyMethodDeclaration.class) == declaration && type.isAssignableFrom(BodyMethodDeclaration.class) && (node = BodyMethodDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(FieldDeclaration.class) == declaration && type.isAssignableFrom(FieldDeclaration.class) && (node = FieldDeclaration.decodeStatement(parent, statement, location, require)) != null);
		
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
	 * @return The Node representation of the given statement.
	 */
	public static Node decodeScopeContents(Node parent, String statement, Location location)
	{
		return SyntaxTree.decodeScopeContents(parent, statement, location, true);
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
	 * @return The Node representation of the given statement.
	 */
	public static Node decodeScopeContents(Node parent, String statement, Location location, boolean require)
	{
		if (statement.length() <= 0)
		{
			return null;
		}
		
		Node node = Literal.decodeStatement(parent, statement, location, require, true);
		
		if (node == null)
		{
			node = decodeStatement(parent, statement, location, false, PRE_VALUE_DECODE);
			
			if (node == null)
			{
				node = (Node)decodeIdentifierAccess(parent, statement, location, false);
				
				if (node == null)
				{
					node = decodeStatement(parent, statement, location, false, SCOPE_CHILD_DECODE);
					
					if (node == null)
					{
						node = decodeVariable(parent, statement, location);
						
						if (node == null && require)
						{
							syntaxError(statement, parent, location);
						}
					}
				}
			}
		}
		
		return node;
	}
	
	/**
	 * Method that tries to decode an identifier access expression.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.children.add(new Node())</pre></blockquote>
	 * The above expression is an identifier access because it uses the
	 * dot operator to access another node.
	 * 
	 * @param parent The parent of the statement to decode.
	 * @param statement The statement to decode as an identifier access.
	 * @param location The location in the source where this statement is.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 */
	public static Accessible decodeIdentifierAccess(Node parent, String statement, Location location, boolean require)
	{
		return decodeIdentifierAccess(parent, statement, location, require, true);
	}
	
	/**
	 * Method that tries to decode an identifier access expression.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.children.add(new Node())</pre></blockquote>
	 * The above expression is an identifier access because it uses the
	 * dot operator to access another node.
	 * 
	 * @param parent The parent of the statement to decode.
	 * @param statement The statement to decode as an identifier access.
	 * @param location The location in the source where this statement is.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param validateAccess Whether or not to check if method call can be
	 * 		accessed legally.
	 */
	public static Accessible decodeIdentifierAccess(Node parent, String statement, Location location, boolean require, boolean validateAccess)
	{
		Accessible root = null;
		Accessible node = null;
		
		int offset = 0;
		int index  = SyntaxUtils.findDotOperator(statement);
		
		if (index < 0)
		{
			return null;
		}
		
		String current = statement.substring(offset, index);
		
		while (current != null)
		{
			node = decodeAccessible(parent, current, location, require, validateAccess);
			
			if (node == null)
			{
				if (!require)
				{
					return null;
				}
				
				Location currentLoc = location.asNew();
				currentLoc.setBounds(offset, index);
				currentLoc.setLineNumber(location.getLineNumber());
				
				syntaxError(current, parent, currentLoc);
			}
			
			if (root == null)
			{
				root = node;
				
				((Node)node).setLocationIn(location);
			}
			else
			{
				parent.addChild((Node)node);
				
				if (index < 0)
				{
					return root;
				}
			}
			
			location = location.asNew();
			parent   = ((Node)node);
			offset   = index + 1;
			index    = SyntaxUtils.findDotOperator(statement, offset);
			
			if (index > 0)
			{
				current = statement.substring(offset, index);
			}
			else
			{
				current = statement.substring(offset, statement.length());
			}
		}
		
		// Should never reach here...
		return null;
	}
	
	/**
	 * Decode the value of the given statement. Can be nodes such as
	 * Variables, ExternalTypes, Fields, etc. May also
	 * be just a plain old Value describing the return type of the
	 * statement. For example: a static class's method call.
	 * "<u><code>Math.sin(number)</code></u>" in this instance, Math
	 * will be the Identifier returned. In most other instances a
	 * Variable variation will be returned.
	 * 
	 * @param parent The parent node of the current statement to decode.
	 * @param statement The statement to decode.
	 * @param location The location of the statement.
	 * @return The Identifier representation of the given statement.
	 */
	private static Identifier decodeVariable(Node parent, String statement, Location location)
	{
		return decodeVariable(parent, statement, location, true);
	}
	
	/**
	 * Decode the value of the given statement. Can be nodes such as
	 * Variables, ExternalTypes, Fields, etc. May also
	 * be just a plain old Value describing the return type of the
	 * statement. For example: a static class's method call.
	 * "<u><code>Math.sin(number)</code></u>" in this instance, Math
	 * will be the Identifier returned. In most other instances a
	 * Variable variation will be returned.
	 * 
	 * @param parent The parent node of the current statement to decode.
	 * @param statement The statement to decode.
	 * @param location The location of the statement.
	 * @param validateAccess Whether or not to check if method call can be
	 * 		accessed legally.
	 * @return The Identifier representation of the given statement.
	 */
	private static Identifier decodeVariable(Node parent, String statement, Location location, boolean validateAccess)
	{
		Identifier node = decodeIdentifier(parent, statement, location, validateAccess);
		
		if (node == null)
		{
			Node n = decodeVariable(parent, statement, location, false, false);
			
			if (n != null && !(n instanceof Identifier))
			{
				return null;
			}
			
			node = (Identifier)n;
		}
		
		return node;
	}
	
	private static Accessible decodeAccessible(Node parent, String statement, Location location, boolean require, boolean validateAccess)
	{
		Accessible node = decodeVariable(parent, statement, location, validateAccess);
		
		if (node == null)
		{
			node = (Accessible)decodeStatementOfType(parent, statement, location, require, Priority.class);
		}
		
		return node;
	}
	
	/**
	 * If you see this method, then I have failed you.
	 * 
	 * @param parent The parent node of the current statement to decode.
	 * @param statement The statement to decode.
	 * @param location The location of the statement.
	 * @param validateAccess Whether or not to check if method call can be
	 * 		accessed legally.
	 * @return The Identifier representation of the given statement.
	 */
	public static Identifier decodeIdentifier(Node parent, String statement, Location location, boolean validateAccess)
	{
		Identifier node = SyntaxTree.getUsableExistingNode(parent, statement, location, validateAccess);
		
		if (node == null)
		{
			if (parent instanceof ExternalType)
			{
				ExternalType type = (ExternalType)parent;
				
				type.setType(statement);
				
				IIdentifier id = new IIdentifier(type, type.getLocationIn());
				id.setName(statement);
				
				node = id;
			}
			else if (parent.getFileDeclaration().containsImport(statement, false) || parent.getFileDeclaration().containsClass(statement))
			{
				ClassDeclaration clazz = SyntaxUtils.getImportedClass(parent.getFileDeclaration(), statement);
				
				if (clazz != null)
				{
					StaticClassReference reference = StaticClassReference.decodeStatement(parent, statement, location, true);
					
					node = reference;
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
	 * @param location The location of the newly generated usable variable.
	 * @return The Identifier that is found, if any.
	 */
	public static Variable getUsableExistingNode(Node parent, String statement, Location location)
	{
		return getUsableExistingNode(parent, statement, location, true);
	}
	
	/**
	 * Try to find an existing node from the given statement. This method
	 * searches through fields and local variables.
	 * 
	 * @param parent The parent Node to use as our context.
	 * @param statement The statement to check for an existing node from.
	 * @param location The location of the newly generated usable variable.
	 * @param validateAccess Whether or not to check if method call can be
	 * 		accessed legally.
	 * @return The Identifier that is found, if any.
	 */
	public static Variable getUsableExistingNode(Node parent, String statement, Location location, boolean validateAccess)
	{
		VariableDeclaration declaration = findDeclaration(parent, statement, validateAccess);
		
		if (declaration != null)
		{
			return declaration.generateUsableVariable(parent, location);
		}
		
		return null;
	}
	
	/**
	 * Try to find an existing Node's declaration from the given
	 * statement. This method searches through fields and local variables.
	 * 
	 * @param parent The parent Node to use as our context.
	 * @param statement The statement to check for an existing declaration
	 * 		from.
	 * @return The VariableDeclaration that is found, if any.
	 */
	public static VariableDeclaration findDeclaration(Node parent, String statement)
	{
		return findDeclaration(parent, statement, true);
	}
	
	/**
	 * Try to find an existing Node's declaration from the given
	 * statement. This method searches through fields and local variables.
	 * 
	 * @param parent The parent Node to use as our context.
	 * @param statement The statement to check for an existing declaration
	 * 		from.
	 * @param validateAccess Whether or not to check if method call can be
	 * 		accessed legally.
	 * @return The VariableDeclaration that is found, if any.
	 */
	public static VariableDeclaration findDeclaration(Node parent, String statement, boolean validateAccess)
	{
		VariableDeclaration node = null;
		
		if (SyntaxUtils.isValidIdentifier(statement))
		{
			node = checkForVariableAccess(parent, statement, validateAccess);
			
			if (node == null)
			{
				node = checkForLocalVariable(parent, statement);
				
				if (node == null)
				{
					node = parent.getParentClass(true).getField(statement);
				}
			}
		}
		
		return node;
	}
	
	/**
	 * Check to see if the data is being accessed from a variable.
	 * 
	 * @param parent The parent of the given statement.
	 * @param statement The statement to decode as a field.
	 * @param validateAccess Whether or not to check if method call can be
	 * 		accessed legally.
	 * @return The found Field. If the Field was not found, null is
	 * 		returned.
	 */
	private static FieldDeclaration checkForVariableAccess(Node parent, String statement, boolean validateAccess)
	{
		if (!(parent instanceof Accessible) || !((Accessible)parent).canAccess())
		{
			return null;
		}
		
		Accessible id = (Accessible)parent;
		
//		if (parent instanceof Variable)
//		{
//			id = ((Variable)parent).getTypeClass();
//		}
//		else if (parent instanceof MethodCall)
//		{
//			id = (MethodCall)parent;
//		}
//		else if (parent instanceof StaticClassReference)
//		{
//			id = (StaticClassReference)parent;
//		}
		
		if (id != null)
		{
			ClassDeclaration clazz = ((Value)id).getTypeClass();
			
			if (clazz == null)
			{
				return null;
			}
			
			FieldDeclaration field = clazz.getField(statement);
			
			if (validateAccess)
			{
				return validateFieldAccess(parent, clazz, field);
			}
			
			return field;
		}
		
		return null;
	}
	
	private static FieldDeclaration validateFieldAccess(Node parent, ClassDeclaration accessingClass, FieldDeclaration field)
	{
		if (field != null)
		{
			if (SyntaxUtils.isVisible(accessingClass, field))
			{
				return field;
			}
			else
			{
				SyntaxMessage.error("Field '" + field.getName() + "' is not accessible", parent);
			}
		}
		
		return null;
	}
	
	/**
	 * Check to see if the given statement is a local variable.
	 * 
	 * @param parent The parent of the given statement
	 * @param statement The statement containing the variable name.
	 * @return The found local variable declaration. If the local
	 * 		variable was not found, null is returned.
	 */
	private static VariableDeclaration checkForLocalVariable(Node parent, String statement)
	{
		Node node = parent.getAncestorWithScope();
		
		if (node == null)
		{
			return null;
		}
		
		return checkForLocalVariable(parent, statement, node.getScope().getID());
	}
	
	/**
	 * Check to see if the given statement is a local variable.
	 * 
	 * @param parent The parent of the given statement
	 * @param statement The statement containing the variable name.
	 * @param scopeID The scope to search for the variable from.
	 * @return The found local variable declaration. If the local
	 * 		variable was not found, null is returned.
	 */
	private static VariableDeclaration checkForLocalVariable(Node parent, String statement, int scopeID)
	{
		Node scopeNode = parent.getAncestorWithScope();
		
		while (scopeNode != null)
		{
			VariableDeclarationList variables = scopeNode.getScope().getVariableList();
			VariableDeclaration     variable  = variables.getVariable(statement, scopeID);
			
			if (variable != null)
			{
				return variable;
			}
			
			if (scopeNode instanceof NovaMethodDeclaration)
			{
				NovaMethodDeclaration method   = (NovaMethodDeclaration)scopeNode;
				Parameter            parameter = method.getParameter(statement);
				
				if (parameter != null)
				{
					method.getParameterList().validateAccess(parameter, parent);
					
					return parameter;
				}
			}
			
			scopeNode = scopeNode.getParent().getAncestorWithScope();
		}
		
		return null;
	}
	
	/**
	 * Decode the given statement into a Value Node.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.children.add(new Node())</pre></blockquote>
	 * The above expression would be returned in a Value Node.
	 * 
	 * @param parent The parent of the statement to decode.
	 * @param statement The statement to decode as a Value Node.
	 * @param location The location in the source where this statement is.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 */
	public static Value decodeValue(Node parent, String statement, Location location, boolean require)
	{
//		Value value = Literal.decodeStatement(parent, statement, location, require, true);
//		
//		if (value == null)
//		{
//			value = decodeIdentifierAccess(parent, statement, location, true);
//			
//			if (value == null)
//			{
//				value = decodeIdentifier(parent, statement, location);
//				
//				if (value == null)
//				{
//					value = (Value)decodeStatementOfType(parent, statement, location, require, Value.class);
//				}
//			}
//		}
		
		Node node = decodeScopeContents(parent, statement, location, require);
		
		if (node instanceof Value)
		{
			return (Value)node;
		}
		else if (node != null)
		{
			node.rollback();
			
			SyntaxMessage.queryError("Invalid operation '" + statement + "'", parent, location, require);
		}
		
		return null;
	}
	
	/**
	 * Output a generic error stating that the given syntax could not
	 * be decoded.
	 * 
	 * @param syntax The syntax that caused the error.
	 * @param parent The most recent parent of the given syntax.
	 * @param location The Location that the syntax is located at.
	 */
	private static void syntaxError(String syntax, Node parent, Location location)
	{
		String type = "syntax";
		
		if (SyntaxUtils.isValidIdentifier(syntax))
		{
			type = "identifier";
		}
		
		syntaxError(syntax, parent, location, type);
	}
	
	/**
	 * Output a generic error stating that the given syntax could not
	 * be decoded.
	 * 
	 * @param syntax The syntax that caused the error.
	 * @param parent The most recent parent of the given syntax.
	 * @param location The Location that the syntax is located at.
	 * @param type The type of element that caused the error.
	 */
	private static void syntaxError(String syntax, Node parent, Location location, String type)
	{
		if (syntax.equals(ParameterList.OBJECT_REFERENCE_IDENTIFIER))
		{
			ParameterList.contextError(parent, location);
		}
		
		SyntaxMessage.error("Could not decode " + type + " '" + syntax + "'", parent, location);
	}
	
	/**
	 * Generate the C Header output from the data contained within the
	 * syntax tree.
	 */
	public void generateCHeaderOutput()
	{
		root.generateCHeader(new StringBuilder());
	}
	
	/**
	 * Generate the C Source output from the data contained within the
	 * syntax tree.
	 */
	public void generateCSourceOutput()
	{
		root.generateCSource(new StringBuilder());
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
			
			headers[i] = child.generateCHeader().toString();
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
			
			sources[i] = child.generateCSource().toString();
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
			FileDeclaration child = (FileDeclaration)root.getChild(i);
			
			sources[i] = child;
		}
		
		return sources;
	}
	
	/**
	 * Get the root Program of the tree. The root of the Syntax tree
	 * will be a Program.
	 * 
	 * @return The root Program instance.
	 */
	public Program getRoot()
	{
		return root;
	}
	
	/**
	 * Add the given FileDeclaration to the SyntaxTree.
	 * 
	 * @param file The FileDeclaration to add.
	 */
	public void addFile(FileDeclaration file)
	{
		root.addChild(file);
	}
	
	/**
	 * Test the SyntaxTree class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}