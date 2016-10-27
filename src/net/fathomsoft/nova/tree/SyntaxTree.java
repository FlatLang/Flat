package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.annotations.Annotation;
import net.fathomsoft.nova.tree.exceptionhandling.*;
import net.fathomsoft.nova.tree.lambda.LambdaExpression;
import net.fathomsoft.nova.tree.lambda.LambdaMethodDeclaration;
import net.fathomsoft.nova.tree.match.Case;
import net.fathomsoft.nova.tree.match.Default;
import net.fathomsoft.nova.tree.match.Fallthrough;
import net.fathomsoft.nova.tree.match.Match;
import net.fathomsoft.nova.tree.variables.*;
import net.fathomsoft.nova.util.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * Class that deconstructs a source code file and builds a Tree out of
 * the statements.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:15 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
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
	
	private static final Class<?> PRE_VALUE_DECODE[] = new Class<?>[]
	{
		Annotation.class, IfStatement.class, ElseStatement.class, Loop.class,
		Case.class, Match.class, Default.class, Fallthrough.class, Priority.class,
		Return.class, Assignment.class, IntRange.class, TernaryOperation.class,
		Instantiation.class, BinaryOperation.class, Super.class
	};
	
	public static final Class<?> SCOPE_CHILD_DECODE[] = new Class<?>[]
	{
		Annotation.class, Break.class, Case.class, Match.class, Default.class,
		Fallthrough.class, Continue.class, ExceptionHandler.class, Assignment.class,
		Instantiation.class, ElseStatement.class, IfStatement.class,
		Until.class, Loop.class, Array.class, UnaryOperation.class, Cast.class,
		MethodCall.class, LocalDeclaration.class, ExternalCodeBlock.class, Array.class
	};
	
	public static final Class<?> FIELD_SCOPE_CHILD_DECODE[] = new Class<?>[]
	{
		AccessorMethod.class, MutatorMethod.class
	};
	
	public static final Class<?> ARRAY_BRACKET_OVERLOAD_DECODE[] = new Class<?>[]
	{
		ArrayAccessorMethod.class, ArrayMutatorMethod.class
	};
	
	public static final Class<?> FIRST_PASS_CLASSES[] = new Class<?>[]
	{
		Annotation.class, Import.class, ClassDeclaration.class, Interface.class, ExtensionDeclaration.class,
		Package.class, ExternalCodeBlock.class
	};
	
	public static final Class<?> SECOND_PASS_CLASSES[] = new Class<?>[]
	{
		ArrayBracketOverload.class, Annotation.class, StaticBlock.class,
		AbstractMethodDeclaration.class, ExternalMethodDeclaration.class, ExtensionMethodDeclaration.class,
		ExtensionFieldDeclaration.class, Destructor.class, Constructor.class, BodyMethodDeclaration.class,
		ExternalType.class, FieldDeclaration.class, ExternalCodeBlock.class
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
			
			root.prePreGenerationValidation();
			
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
		
		if (phase == PHASE_INSTANCE_DECLARATIONS)
		{
			root.decodeShorthandActions = true;
			
			root.forEachVisibleListChild(file -> Arrays.stream(file.getClassDeclarations()).forEach(c -> c.decodeShorthandActions()));
		}
	}
	
	/**
	 * Search for the main method, if one exists, in the compiling
	 * program. For more details on what the main method looks like, see
	 * {@link net.fathomsoft.nova.util.SyntaxUtils#isMainMethod(BodyMethodDeclaration)}.
	 * 
	 * @return The Method representation of the main method.
	 */
	public NovaMethodDeclaration getMainMethod(String mainClass)
	{
		MethodDeclaration main = null;
		
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
								if (mainClass == null || methodDeclaration.getParentClass().getClassLocation().equals(mainClass))
								{
									if (main != null)
									{
										SyntaxMessage.error("Multiple main methods found. Please specify which one you want to run by passing a -main argument to the compiler", main);
									}
									
									main = methodDeclaration;
								}
							}
						}
					}
				}
			}
		}
		
		return (NovaMethodDeclaration)main;
	}
    
	/**
	 * Traverse through the tree and validate each node.
	 * 
	 * @param root The Node to validate, then validate the children.
	 */
	public ValidationResult validateNodes(Node root)
	{
		return validateNodes(root, phase);
	}
	
	/**
	 * Traverse through the tree and validate each node.
	 *
	 * @param root The Node to validate, then validate the children.
	 */
	public static ValidationResult validateNodes(Node root, int phase)
	{
		ValidationResult result = null;
		
		try
		{
			result = root.validate(phase);
			
			if (!result.continueValidation || result.skipCycle)
			{
				return result.skippedCycle();
			}
			
			int temp = result.nextIncrement;
			
			root = result.returnedNode;
			
			int i = 0;
			
			while (i < root.getNumChildren())
			{
				Node child = root.getChild(i);
				
				result = validateNodes(child, phase);
				
				if (!result.continueValidation)
				{
					return result;
				}
				
				i += result.increment();
			}
			
			result.nextIncrement = temp;
		}
		catch (SyntaxErrorException e)
		{
			result = new ValidationResult(root).errorOccurred();
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
		
		char[] quotes = new char[] { '"', '\'' };
		int offset = 0;
		int start  = StringUtils.findStrings(source, starts, 0, 1, quotes).getStart();
		
		while (start >= 0)
		{
			int end = StringUtils.findStrings(source, ends, start, 1, false).getEnd();
			
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
			start   = StringUtils.findStrings(source, starts, end, 1, quotes).getStart();
		}

		offset = 0;
		starts = new String[] { "//" };
		start  = StringUtils.findStrings(builder, starts, 0, 1, quotes).getStart();
		
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
			start   = StringUtils.findStrings(builder, starts, start, 1, quotes).getStart();
		}
		
		return builder.toString().trim();
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
		return decodeStatement(parent, statement, location, require, types, true);
	}
	
	public static Node decodeStatement(Node parent, String statement, Location location, boolean require, Class<?> types[], boolean checkType)
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
				
				if      (type == LocalDeclaration.class) node = LocalDeclaration.decodeStatement(parent, statement, location, require, true, checkType);
				else if (node == null && type == ElseStatement.class) node = ElseStatement.decodeStatement(parent, statement, location, require);
				else if (node == null && type == AbstractMethodDeclaration.class) node = AbstractMethodDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == AccessorMethod.class) node = AccessorMethod.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ArrayAccess.class) node = ArrayAccess.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ArrayBracketOverload.class) node = ArrayBracketOverload.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ArrayAccessorMethod.class) node = ArrayAccessorMethod.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ArrayMutatorMethod.class) node = ArrayMutatorMethod.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Assignment.class) node = Assignment.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Array.class) node = Array.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Case.class) node = Case.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Cast.class) node = Cast.decodeStatement(parent, statement, location, require);
				else if (node == null && type == BinaryOperation.class) node = BinaryOperation.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Break.class) node = Break.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Continue.class) node = Continue.decodeStatement(parent, statement, location, require);
				else if (node == null && type == MethodCall.class) node = MethodCall.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ClassDeclaration.class) node = ClassDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Constructor.class) node = Constructor.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Default.class) node = Default.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Destructor.class) node = Destructor.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ExtensionDeclaration.class) node = ExtensionDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ExternalCodeBlock.class) node = ExternalCodeBlock.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ExternalMethodDeclaration.class) node = ExternalMethodDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ExternalType.class) node = ExternalType.decodeStatement(parent, statement, location, require);
				else if (node == null && type == IfStatement.class) node = IfStatement.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Import.class) node = Import.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Instantiation.class) node = Instantiation.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Interface.class) node = Interface.decodeStatement(parent, statement, location, require);
				else if (node == null && type == IntRange.class) node = IntRange.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Literal.class) node = Literal.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Loop.class) node = Loop.decodeStatement(parent, statement, location, require);
				else if (node == null && type == MutatorMethod.class) node = MutatorMethod.decodeStatement(parent, statement, location, require);
				else if (node == null && type == BodyMethodDeclaration.class) node = BodyMethodDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ExtensionFieldDeclaration.class) node = ExtensionFieldDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ExtensionMethodDeclaration.class) node = ExtensionMethodDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Package.class) node = Package.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Priority.class) node = Priority.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Return.class) node = Return.decodeStatement(parent, statement, location, require);
				else if (node == null && type == UnaryOperation.class) node = UnaryOperation.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Fallthrough.class) node = Fallthrough.decodeStatement(parent, statement, location, require);
				else if (node == null && type == FieldDeclaration.class) node = FieldDeclaration.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Catch.class) node = Catch.decodeStatement(parent, statement, location, require);
				else if (node == null && type == ExceptionHandler.class) node = ExceptionHandler.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Finally.class) node = Finally.decodeStatement(parent, statement, location, require);
				else if (node == null && type == StaticBlock.class) node = StaticBlock.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Super.class) node = Super.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Match.class) node = Match.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Throw.class) node = Throw.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Try.class) node = Try.decodeStatement(parent, statement, location, require);
				else if (node == null && type == TernaryOperation.class) node = TernaryOperation.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Until.class) node = Until.decodeStatement(parent, statement, location, require);
				else if (node == null && type == Annotation.class) node = Annotation.decodeStatement(parent, statement, location, require);
				
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
		else if (type.isAssignableFrom(ArrayBracketOverload.class) && (node = ArrayBracketOverload.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ArrayAccessorMethod.class) && (node = ArrayAccessorMethod.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ArrayMutatorMethod.class) && (node = ArrayMutatorMethod.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Assignment.class) && (node = Assignment.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Array.class) && (node = Array.decodeStatement(parent, statement, location, require)) != null);
//		else if (type.isAssignableFrom(Bool.class) && (node = Bool.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Break.class) && (node = Break.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Case.class) && (node = Case.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Continue.class) && (node = Continue.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(BinaryOperation.class) && (node = BinaryOperation.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ClassDeclaration.class) && (node = ClassDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Constructor.class) && (node = Constructor.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Default.class) && (node = Default.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Destructor.class) && (node = Destructor.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ElseStatement.class) && (node = ElseStatement.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ExtensionDeclaration.class) && (node = ExtensionDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ExternalCodeBlock.class) && (node = ExternalCodeBlock.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ExternalMethodDeclaration.class) && (node = ExternalMethodDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ExternalType.class) && (node = ExternalType.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Fallthrough.class) && (node = Fallthrough.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Import.class) && (node = Import.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Interface.class) && (node = Interface.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(IntRange.class) && (node = IntRange.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Literal.class) && (node = Literal.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(LocalDeclaration.class) && (node = LocalDeclaration.decodeStatement(parent, statement, location, require)) != null);
//		else if (type.isAssignableFrom(Null.class) && (node = Null.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(MethodCall.class) && (node = MethodCall.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(BodyMethodDeclaration.class) && (node = BodyMethodDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ExtensionFieldDeclaration.class) && (node = ExtensionFieldDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ExtensionMethodDeclaration.class) && (node = ExtensionMethodDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Priority.class) && (node = Priority.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Return.class) && (node = Return.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(UnaryOperation.class) && (node = UnaryOperation.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(FieldDeclaration.class) && (node = FieldDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Catch.class) && (node = Catch.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(ExceptionHandler.class) && (node = ExceptionHandler.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Finally.class) && (node = Finally.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(StaticBlock.class) && (node = StaticBlock.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Super.class) && (node = Super.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Match.class) && (node = Match.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Throw.class) && (node = Throw.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(Try.class) && (node = Try.decodeStatement(parent, statement, location, require)) != null);
		else if (type.isAssignableFrom(TernaryOperation.class) && (node = TernaryOperation.decodeStatement(parent, statement, location, require)) != null);
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
		else if (type2.isAssignableFrom(ExtensionMethodDeclaration.class) == declaration && type.isAssignableFrom(ExtensionMethodDeclaration.class) && (node = ExtensionMethodDeclaration.decodeStatement(parent, statement, location, require)) != null);
		else if (type2.isAssignableFrom(ExtensionFieldDeclaration.class) == declaration && type.isAssignableFrom(ExtensionFieldDeclaration.class) && (node = ExtensionFieldDeclaration.decodeStatement(parent, statement, location, require)) != null);
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
		return decodeScopeContents(parent, statement, location, require, true);
	}
	
	private static int findAccessInBaseScopeIndex(String statement)
	{
		int index = SyntaxUtils.findCharInBaseScope(statement, '[');
		
		if (index >= 0)
		{
			Matcher access = Patterns.ARRAY_ACCESS.matcher(statement);
			
			if (access.find())
			{
				if (index != access.start())
				{
					index = Math.min(access.start(), index) + 1;
					int index2 = findAccessInBaseScopeIndex(statement.substring(index));
					
					if (index2 > 0)
					{
						return index2 + index;
					}
				}
				else
				{
					return index;
				}
			}
		}
		
		return -1;
	}
	
	private static boolean endsWithArrayAccess(String statement, int index)
	{
		int start = index;
		
		while (index >= start)
		{
			index = StringUtils.findEndingMatch(statement, index, '[', ']');
			
			if (index == statement.length() - 1)
			{
				return true;
			}
			
			index = StringUtils.findNextNonWhitespaceIndex(statement, index + 1);
		}
		
		return false;
	}
	
	private static Object getArrayAccessOrNode(Node parent, String statement, Location location, boolean require)
	{
		if (Regex.matches(statement, 0, Patterns.CONTROL_STRUCTURES))
		{
			return null;
		}
		
		String accessString = null;
		
		int index = findAccessInBaseScopeIndex(statement);
		
		if (index > 0 && endsWithArrayAccess(statement, index))
		{
			Node n = Instantiation.decodeStatement(parent, statement, location, require);
			
			if (n == null)
			{
				n = Assignment.decodeStatement(parent, statement, location, require);
				
				if (n == null)
				{
					n = BinaryOperation.decodeStatement(parent, statement, location, require);
				}
			}
			
			if (n != null)
			{
				return n;
			}
			
			accessString = statement.substring(index).trim();
			statement = statement.substring(0, index);
			
			boolean safeNavigation = statement.endsWith("?");
			
			if (safeNavigation)
			{
				statement = statement.substring(0, statement.length() - 1);
			}
			
			return new Object[] { accessString, statement, safeNavigation };
		}
		
		return null;
	}
	
	public static Node decodeScopeContents(Node parent, String statement, Location location, boolean require, boolean checkType)
	{
		if (statement.length() <= 0)
		{
			return null;
		}
		
		boolean safeNavigation = false;
		String accessString = null;
		
		Object o = getArrayAccessOrNode(parent, statement, location, require);
		
		if (o instanceof Node)
		{
			return (Node)o;
		}
		else if (o != null)
		{
			Object[] s = (Object[])o;
			
			accessString = (String)s[0];
			statement = (String)s[1];
			safeNavigation = (Boolean)s[2];
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
					node = decodeStatement(parent, statement, location, false, SCOPE_CHILD_DECODE, checkType);
					
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
		
		if (accessString != null && node instanceof Value)
		{
			Node access = ArrayAccess.decodeStatement(node, accessString, location, require);
			
			if (access instanceof ArrayAccess)
			{
				((Value)node).getReturnedNode().arrayAccess = (ArrayAccess)access;
			}
			else
			{
				((Accessible)((Value)node).getReturnedNode()).setAccessedNode((MethodCall)access);
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
		return decodeIdentifierAccess(parent, statement, location, require, validateAccess, true);
	}
	
	public static Accessible decodeIdentifierAccess(Node parent, String statement, Location location, boolean require, boolean validateAccess, boolean requireDot)
	{
		Accessible root = null;
		Accessible node = null;
		
		boolean safeNavigation = false;
		int offset = 0;
		int index  = SyntaxUtils.findDotOperator(statement);
		
		safeNavigation = index > 0 && statement.charAt(index - 1) == '?';
		
		if (requireDot && index < 0)
		{
			return null;
		}
		
		String current = statement.substring(offset, index);
		
		while (current != null)
		{
			if (current.length() == 0)
			{
				return null;
			}
			
			if (safeNavigation)
			{
				current = current.substring(0, current.length() - 1);
			}
			
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
			
			if (safeNavigation)
			{
				node.setSafeNavigation(true);
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
			parent   = node.getReturnedNode();
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
			
			safeNavigation = index > 0 && statement.charAt(index - 1) == '?';
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
		String accessString = null;
		
		Object o = getArrayAccessOrNode(parent, statement, location, require);
		
		if (o instanceof Node)
		{
			return (Accessible)o;
		}
		else if (o != null)
		{
			Object[] s = (Object[])o;
			
			accessString = (String)s[0];
			statement = (String)s[1];
		}
		
		Accessible node = (Accessible)decodeStatementOfType(parent, statement, location, require, Super.class);
		
		if (node == null)
		{
			node = decodeVariable(parent, statement, location, validateAccess);
			
			if (node == null)
			{
				node = (Accessible)Literal.decodeStatement(parent, statement, location, require, true);
				
				if (node == null)
				{
					node = (Accessible)decodeStatementOfType(parent, statement, location, require, Priority.class);
				}
			}
		}
		
		if (accessString != null && node instanceof Value)
		{
			Node access = ArrayAccess.decodeStatement((Node)node, accessString, location, require);
			
			if (access instanceof ArrayAccess)
			{
				node.getReturnedNode().arrayAccess = (ArrayAccess)access;
			}
			else
			{
				((Accessible)node.getReturnedNode()).setAccessedNode((MethodCall)access);
			}
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
//		Identifier node = Super.decodeStatement(parent, statement, location, false);
//		
//		if (node == null)
//		{
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
//		}
		
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
		
		if (statement.equals(LambdaExpression.UNNAMED_PARAMETER))// && parent instanceof Identifier == false)
		{
			if (parent.getParentMethod(true) instanceof LambdaMethodDeclaration)
			{
				LambdaMethodDeclaration method = (LambdaMethodDeclaration)parent.getParentMethod(true);
				
				return method.getNextUnnamedParameter();
			}
		}
		if (SyntaxUtils.isValidIdentifier(statement))
		{
			node = checkForVariableAccess(parent, statement, validateAccess);
			
			if (node == null)
			{
				node = checkForLocalVariable(parent, statement);
				
				if (node == null)
				{
					ClassDeclaration clazz = parent.getParentClass(true);
					
					if (clazz != null)
					{
						node = clazz.getField(statement);
						
						if (node == null && clazz instanceof ExtensionDeclaration)
						{
							NovaMethodDeclaration method = parent.getParentMethod(true);
							
							if (method != null && node == null)
							{
								node = method.getParameterList().getReferenceParameter().getTypeClass().getField(statement);
							}
						}
					}
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
			Function<ClassDeclaration, FieldDeclaration> getField = (clazz) -> {
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
			};
			
			for (ClassDeclaration c : id.toValue().getExtensionClasses())
			{
				FieldDeclaration field = getField.apply(c);
				
				if (field != null)
				{
					return field;
				}
			}
			
			return getField.apply(((Value)id).getTypeClass());
		}
		
		return null;
	}
	
	private static FieldDeclaration validateFieldAccess(Node parent, ClassDeclaration accessingClass, FieldDeclaration field)
	{
		if (field != null)
		{
			if (field.getDeclaringClass().isPrimitiveType())
			{
				return field;
			}
			else if (SyntaxUtils.isVisible(parent.getParentClass(true), field))
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
		
		return checkForLocalVariable(parent, statement, node.getScope());
	}
	
	/**
	 * Check to see if the given statement is a local variable.
	 * 
	 * @param parent The parent of the given statement
	 * @param statement The statement containing the variable name.
	 * @param scope The scope to search for the variable from.
	 * @return The found local variable declaration. If the local
	 * 		variable was not found, null is returned.
	 */
	private static VariableDeclaration checkForLocalVariable(Node parent, String statement, Scope scope)
	{
		VariableDeclaration variable = parent.searchVariable(parent, scope, statement);
		
		if (variable != null)
		{
			return variable;
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
		return decodeValue(parent, statement, location, require, true);
	}
	
	public static Value decodeValue(Node parent, String statement, Location location, boolean require, boolean checkType)
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
		
		Node node = decodeScopeContents(parent, statement, location, require, checkType);
		
		/*if (node instanceof Variable)
		{
			if (((Variable)node).getDeclaration() instanceof ClosureDeclaration)
			{
				return new Closure((Variable)node);
			}
		}*/
		
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
			FileDeclaration child = root.getChild(i);
			
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