package flat.js.nodewriters;

import flat.tree.*;
import flat.tree.Package;
import flat.tree.annotations.*;
import flat.tree.exceptionhandling.*;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.lambda.LambdaMethodDeclaration;
import flat.tree.match.*;
import flat.tree.variables.*;

public class Writer
{
	public static NodeWriter getWriter(final Node node)
	{
		if (node instanceof CommentAnnotation)
		{
			return new CommentAnnotationWriter()
			{
				@Override
				public CommentAnnotation node()
				{
					return (CommentAnnotation)node;
				}
			};
		}
		if (node instanceof Annotation)
		{
			return new AnnotationWriter()
			{
				@Override
				public Annotation node()
				{
					return (Annotation)node;
				}
			};
		}
		else if (node instanceof DefaultArgument)
		{
			return new DefaultArgumentWriter()
			{
				@Override
				public DefaultArgument node()
				{
					return (DefaultArgument)node;
				}
			};
		}
		else if (node instanceof ExternalCodeBlock)
		{
			return new ExternalCodeBlockWriter()
			{
				@Override
				public ExternalCodeBlock node()
				{
					return (ExternalCodeBlock)node;
				}
			};
		}
		else if (node instanceof InitializationMethod)
		{
			return new InitializationMethodWriter()
			{
				@Override
				public InitializationMethod node()
				{
					return (InitializationMethod)node;
				}
			};
		}
		else if (node instanceof Super)
		{
			return new SuperWriter()
			{
				@Override
				public Super node()
				{
					return (Super)node;
				}
			};
		}
		else if (node instanceof ExceptionDeclaration)
		{
			return new ExceptionDeclarationWriter()
			{
				@Override
				public ExceptionDeclaration node()
				{
					return (ExceptionDeclaration)node;
				}
			};
		}
		else if (node instanceof MutatorMethod)
		{
			return new MutatorMethodWriter()
			{
				@Override
				public MutatorMethod node()
				{
					return (MutatorMethod)node;
				}
			};
		}
		else if (node instanceof AccessorMethod)
		{
			return new AccessorMethodWriter()
			{
				@Override
				public AccessorMethod node()
				{
					return (AccessorMethod)node;
				}
			};
		}
		else if (node instanceof Array)
		{
			return new ArrayWriter()
			{
				@Override
				public Array node()
				{
					return (Array)node;
				}
			};
		}
		else if (node instanceof ArrayAccess)
		{
			return new ArrayAccessWriter()
			{
				@Override
				public ArrayAccess node()
				{
					return (ArrayAccess)node;
				}
			};
		}
		else if (node instanceof AssignmentMethod)
		{
			return new AssignmentMethodWriter()
			{
				@Override
				public AssignmentMethod node()
				{
					return (AssignmentMethod)node;
				}
			};
		}
		else if (node instanceof Assignment)
		{
			return new AssignmentWriter()
			{
				@Override
				public Assignment node()
				{
					return (Assignment)node;
				}
			};
		}
		else if (node instanceof BinaryOperation)
		{
			return new BinaryOperationWriter()
			{
				@Override
				public BinaryOperation node()
				{
					return (BinaryOperation)node;
				}
			};
		}
		else if (node instanceof Break)
		{
			return new BreakWriter()
			{
				@Override
				public Break node()
				{
					return (Break)node;
				}
			};
		}
		else if (node instanceof Case)
		{
			return new CaseWriter()
			{
				@Override
				public Case node()
				{
					return (Case)node;
				}
			};
		}
		else if (node instanceof Cast)
		{
			return new CastWriter()
			{
				@Override
				public Cast node()
				{
					return (Cast)node;
				}
			};
		}
		else if (node instanceof Catch)
		{
			return new CatchWriter()
			{
				@Override
				public Catch node()
				{
					return (Catch)node;
				}
			};
		}
		else if (node instanceof ClosureContextDeclaration)
		{
			return new ClosureContextDeclarationWriter()
			{
				@Override
				public ClosureContextDeclaration node()
				{
					return (ClosureContextDeclaration)node;
				}
			};
		}
		else if (node instanceof ClosureContext)
		{
			return new ClosureContextWriter()
			{
				@Override
				public ClosureContext node()
				{
					return (ClosureContext)node;
				}
			};
		}
		else if (node instanceof ClosureDeclaration)
		{
			return new ClosureDeclarationWriter()
			{
				@Override
				public ClosureDeclaration node()
				{
					return (ClosureDeclaration)node;
				}
			};
		}
		else if (node instanceof ClosureParameterList)
		{
			return new ClosureParameterListWriter()
			{
				@Override
				public ClosureParameterList node()
				{
					return (ClosureParameterList)node;
				}
			};
		}
		else if (node instanceof Closure)
		{
			return new ClosureWriter()
			{
				@Override
				public Closure node()
				{
					return (Closure)node;
				}
			};
		}
		else if (node instanceof Constructor)
		{
			return new ConstructorWriter()
			{
				@Override
				public Constructor node()
				{
					return (Constructor)node;
				}
			};
		}
		else if (node instanceof Continue)
		{
			return new ContinueWriter()
			{
				@Override
				public Continue node()
				{
					return (Continue)node;
				}
			};
		}
		else if (node instanceof DefaultParameterInitialization)
		{
			return new DefaultParameterInitializationWriter()
			{
				@Override
				public DefaultParameterInitialization node()
				{
					return (DefaultParameterInitialization)node;
				}
			};
		}
		else if (node instanceof Default)
		{
			return new DefaultWriter()
			{
				@Override
				public Default node()
				{
					return (Default)node;
				}
			};
		}
		else if (node instanceof Destructor)
		{
			return new DestructorWriter()
			{
				@Override
				public Destructor node()
				{
					return (Destructor)node;
				}
			};
		}
		else if (node instanceof Dimensions)
		{
			return new DimensionsWriter()
			{
				@Override
				public Dimensions node()
				{
					return (Dimensions)node;
				}
			};
		}
		else if (node instanceof ElseStatement)
		{
			return new ElseStatementWriter()
			{
				@Override
				public ElseStatement node()
				{
					return (ElseStatement)node;
				}
			};
		}
		else if (node instanceof ExtensionVTable)
		{
			return new ExtensionVTableWriter()
			{
				@Override
				public ExtensionVTable node()
				{
					return (ExtensionVTable)node;
				}
			};
		}
		else if (node instanceof ExternalMethodDeclaration)
		{
			return new ExternalMethodDeclarationWriter()
			{
				@Override
				public ExternalMethodDeclaration node()
				{
					return (ExternalMethodDeclaration)node;
				}
			};
		}
		else if (node instanceof Fallthrough)
		{
			return new FallthroughWriter()
			{
				@Override
				public Fallthrough node()
				{
					return (Fallthrough)node;
				}
			};
		}
		else if (node instanceof FieldDeclaration)
		{
			return new FieldDeclarationWriter()
			{
				@Override
				public FieldDeclaration node()
				{
					return (FieldDeclaration)node;
				}
			};
		}
		else if (node instanceof FileDeclaration)
		{
			return new FileDeclarationWriter()
			{
				@Override
				public FileDeclaration node()
				{
					return (FileDeclaration)node;
				}
			};
		}
		else if (node instanceof Finally)
		{
			return new FinallyWriter()
			{
				@Override
				public Finally node()
				{
					return (Finally)node;
				}
			};
		}
		else if (node instanceof ForEachLoop)
		{
			return new ForEachLoopWriter()
			{
				@Override
				public ForEachLoop node()
				{
					return (ForEachLoop)node;
				}
			};
		}
		else if (node instanceof ForLoop)
		{
			return new ForLoopWriter()
			{
				@Override
				public ForLoop node()
				{
					return (ForLoop)node;
				}
			};
		}
		else if (node instanceof ImportList)
		{
			return new ImportListWriter()
			{
				@Override
				public ImportList node()
				{
					return (ImportList)node;
				}
			};
		}
		else if (node instanceof Import)
		{
			return new ImportWriter()
			{
				@Override
				public Import node()
				{
					return (Import)node;
				}
			};
		}
		else if (node instanceof InstanceFieldList)
		{
			return new InstanceFieldListWriter()
			{
				@Override
				public InstanceFieldList node()
				{
					return (InstanceFieldList)node;
				}
			};
		}
		else if (node instanceof Instantiation)
		{
			return new InstantiationWriter()
			{
				@Override
				public Instantiation node()
				{
					return (Instantiation)node;
				}
			};
		}
		else if (node instanceof ClassDeclaration)
		{
			return new ClassDeclarationWriter()
			{
				@Override
				public ClassDeclaration node()
				{
					return (ClassDeclaration)node;
				}
			};
		}
		else if (node instanceof LambdaMethodDeclaration)
		{
			return new LambdaMethodDeclarationWriter()
			{
				@Override
				public LambdaMethodDeclaration node()
				{
					return (LambdaMethodDeclaration)node;
				}
			};
		}
		else if (node instanceof Literal)
		{
			return new LiteralWriter()
			{
				@Override
				public Literal node()
				{
					return (Literal)node;
				}
			};
		}
		else if (node instanceof MatchCase)
		{
			return new MatchCaseWriter()
			{
				@Override
				public MatchCase node()
				{
					return (MatchCase)node;
				}
			};
		}
		else if (node instanceof Match)
		{
			return new MatchWriter()
			{
				@Override
				public Match node()
				{
					return (Match)node;
				}
			};
		}
		else if (node instanceof MethodCallArgumentList)
		{
			return new MethodCallArgumentListWriter()
			{
				@Override
				public MethodCallArgumentList node()
				{
					return (MethodCallArgumentList)node;
				}
			};
		}
		else if (node instanceof MethodCall)
		{
			return new MethodCallWriter()
			{
				@Override
				public MethodCall node()
				{
					return (MethodCall)node;
				}
			};
		}
		else if (node instanceof MethodList)
		{
			return new MethodListWriter()
			{
				@Override
				public MethodList node()
				{
					return (MethodList)node;
				}
			};
		}
		else if (node instanceof FlatParameterList)
		{
			return new FlatParameterListWriter()
			{
				@Override
				public FlatParameterList node()
				{
					return (FlatParameterList)node;
				}
			};
		}
		else if (node instanceof Operator)
		{
			return new OperatorWriter()
			{
				@Override
				public Operator node()
				{
					return (Operator)node;
				}
			};
		}
		else if (node instanceof Package)
		{
			return new PackageWriter()
			{
				@Override
				public Package node()
				{
					return (Package)node;
				}
			};
		}
		else if (node instanceof ParameterList)
		{
			return new ParameterListWriter()
			{
				@Override
				public ParameterList node()
				{
					return (ParameterList)node;
				}
			};
		}
		else if (node instanceof Parameter)
		{
			return new ParameterWriter()
			{
				@Override
				public Parameter node()
				{
					return (Parameter)node;
				}
			};
		}
		else if (node instanceof Priority)
		{
			return new PriorityWriter()
			{
				@Override
				public Priority node()
				{
					return (Priority)node;
				}
			};
		}
		else if (node instanceof Program)
		{
			return new ProgramWriter()
			{
				@Override
				public Program node()
				{
					return (Program)node;
				}
			};
		}
		else if (node instanceof PropertyMethod)
		{
			return new PropertyMethodWriter()
			{
				@Override
				public PropertyMethod node()
				{
					return (PropertyMethod)node;
				}
			};
		}
		else if (node instanceof Repeat)
		{
			return new RepeatWriter()
			{
				@Override
				public Repeat node()
				{
					return (Repeat)node;
				}
			};
		}
		else if (node instanceof Return)
		{
			return new ReturnWriter()
			{
				@Override
				public Return node()
				{
					return (Return)node;
				}
			};
		}
		else if (node instanceof Scope)
		{
			return new ScopeWriter()
			{
				@Override
				public Scope node()
				{
					return (Scope)node;
				}
			};
		}
		else if (node instanceof Skeleton)
		{
			return new SkeletonWriter()
			{
				@Override
				public Skeleton node()
				{
					return (Skeleton)node;
				}
			};
		}
		else if (node instanceof StaticBlock)
		{
			return new StaticBlockWriter()
			{
				@Override
				public StaticBlock node()
				{
					return (StaticBlock)node;
				}
			};
		}
		else if (node instanceof StaticClassReference)
		{
			return new StaticClassReferenceWriter()
			{
				@Override
				public StaticClassReference node()
				{
					return (StaticClassReference)node;
				}
			};
		}
		else if (node instanceof ReifiedParameterAccess)
		{
			return new ReifiedParameterAccessWriter()
			{
				@Override
				public ReifiedParameterAccess node()
				{
					return (ReifiedParameterAccess)node;
				}
			};
		}
		else if (node instanceof StaticFieldList)
		{
			return new StaticFieldListWriter()
			{
				@Override
				public StaticFieldList node()
				{
					return (StaticFieldList)node;
				}
			};
		}
		else if (node instanceof FieldList)
		{
			return new FieldListWriter()
			{
				@Override
				public FieldList node()
				{
					return (FieldList)node;
				}
			};
		}
		else if (node instanceof TernaryOperation)
		{
			return new TernaryOperationWriter()
			{
				@Override
				public TernaryOperation node()
				{
					return (TernaryOperation)node;
				}
			};
		}
		else if (node instanceof Throw)
		{
			return new ThrowWriter()
			{
				@Override
				public Throw node()
				{
					return (Throw)node;
				}
			};
		}
		else if (node instanceof Try)
		{
			return new TryWriter()
			{
				@Override
				public Try node()
				{
					return (Try)node;
				}
			};
		}
		else if (node instanceof TypeList)
		{
			return new TypeListWriter()
			{
				@Override
				public TypeList node()
				{
					return (TypeList)node;
				}
			};
		}
		else if (node instanceof UnaryOperation)
		{
			return new UnaryOperationWriter()
			{
				@Override
				public UnaryOperation node()
				{
					return (UnaryOperation)node;
				}
			};
		}
		else if (node instanceof Until)
		{
			return new UntilWriter()
			{
				@Override
				public Until node()
				{
					return (Until)node;
				}
			};
		}
		else if (node instanceof VariableDeclarationList)
		{
			return new VariableDeclarationListWriter()
			{
				@Override
				public VariableDeclarationList node()
				{
					return (VariableDeclarationList)node;
				}
			};
		}
		else if (node instanceof Variable)
		{
			return new VariableWriter()
			{
				@Override
				public Variable node()
				{
					return (Variable)node;
				}
			};
		}
		else if (node instanceof VirtualMethodDeclaration)
		{
			return new VirtualMethodDeclarationWriter()
			{
				@Override
				public VirtualMethodDeclaration node()
				{
					return (VirtualMethodDeclaration)node;
				}
			};
		}
		else if (node instanceof LocalDeclaration)
		{
			return new LocalDeclarationWriter()
			{
				@Override
				public LocalDeclaration node()
				{
					return (LocalDeclaration)node;
				}
			};
		}
		else if (node instanceof VTable)
		{
			return new VTableWriter()
			{
				@Override
				public VTable node()
				{
					return (VTable)node;
				}
			};
		}
		else if (node instanceof WhileLoop)
		{
			return new WhileLoopWriter()
			{
				@Override
				public WhileLoop node()
				{
					return (WhileLoop)node;
				}
			};
		}
		else if (node instanceof Loop)
		{
			return new LoopWriter()
			{
				@Override
				public Loop node()
				{
					return (Loop)node;
				}
			};
		}
		else if (node instanceof ArgumentList)
		{
			return new ArgumentListWriter()
			{
				@Override
				public ArgumentList node()
				{
					return (ArgumentList)node;
				}
			};
		}
		else if (node instanceof List)
		{
			return new ListWriter()
			{
				@Override
				public List node()
				{
					return (List)node;
				}
			};
		}
		else if (node instanceof ExceptionHandler)
		{
			return new ExceptionHandlerWriter()
			{
				@Override
				public ExceptionHandler node()
				{
					return (ExceptionHandler)node;
				}
			};
		}
		else if (node instanceof IfStatement)
		{
			return new IfStatementWriter()
			{
				@Override
				public IfStatement node()
				{
					return (IfStatement)node;
				}
			};
		}
		else if (node instanceof ControlStatement)
		{
			return new ControlStatementWriter()
			{
				@Override
				public ControlStatement node()
				{
					return (ControlStatement)node;
				}
			};
		}
		else if (node instanceof GenericTypeParameter)
		{
			return new GenericTypeParameterWriter()
			{
				@Override
				public GenericTypeParameter node()
				{
					return (GenericTypeParameter)node;
				}
			};
		}
		else if (node instanceof IValue)
		{
			return new IValueWriter()
			{
				@Override
				public IValue node()
				{
					return (IValue)node;
				}
			};
		}
		else if (node instanceof ExtensionMethodDeclaration)
		{
			return new ExtensionMethodDeclarationWriter()
			{
				@Override
				public ExtensionMethodDeclaration node()
				{
					return (ExtensionMethodDeclaration)node;
				}
			};
		}
		else if (node instanceof AnonymousCompilerMethod)
		{
			return new AnonymousCompilerMethodWriter()
			{
				@Override
				public AnonymousCompilerMethod node()
				{
					return (AnonymousCompilerMethod)node;
				}
			};
		}
		else if (node instanceof BodyMethodDeclaration)
		{
			return new BodyMethodDeclarationWriter()
			{
				@Override
				public BodyMethodDeclaration node()
				{
					return (BodyMethodDeclaration)node;
				}
			};
		}
		else if (node instanceof AbstractMethodDeclaration)
		{
			return new AbstractMethodDeclarationWriter()
			{
				@Override
				public AbstractMethodDeclaration node()
				{
					return (AbstractMethodDeclaration)(AbstractMethodDeclaration)node;
				}
			};
		}
		else if (node instanceof FlatMethodDeclaration)
		{
			return new FlatMethodDeclarationWriter()
			{
				@Override
				public FlatMethodDeclaration node()
				{
					return (FlatMethodDeclaration)node;
				}
			};
		}
		else if (node instanceof MethodDeclaration)
		{
			return new MethodDeclarationWriter()
			{
				@Override
				public MethodDeclaration node()
				{
					return (MethodDeclaration)node;
				}
			};
		}
		else if (node instanceof InstanceDeclaration)
		{
			return new InstanceDeclarationWriter()
			{
				@Override
				public InstanceDeclaration node()
				{
					return (InstanceDeclaration)node;
				}
			};
		}
		else if (node instanceof VariableDeclaration)
		{
			return new VariableDeclarationWriter()
			{
				@Override
				public VariableDeclaration node()
				{
					return (VariableDeclaration)node;
				}
			};
		}
		else if (node instanceof IIdentifier)
		{
			return new IIdentifierWriter()
			{
				@Override
				public IIdentifier node()
				{
					return (IIdentifier)node;
				}
			};
		}
		else if (node instanceof Identifier)
		{
			return new IdentifierWriter()
			{
				@Override
				public Identifier node()
				{
					return (Identifier)node;
				}
			};
		}
		else if (node instanceof Value)
		{
			return new ValueWriter()
			{
				@Override
				public Value node()
				{
					return (Value)node;
				}
			};
		}

		return new NodeWriter()
		{
			@Override
			public Node node()
			{
				return (Node)node;
			}
		};
	}

	public static MutatorMethodWriter getWriter(final MutatorMethod node)
	{
		return (MutatorMethodWriter)getWriter((Node)node);
	}

	public static AccessorMethodWriter getWriter(final AccessorMethod node)
	{
		return (AccessorMethodWriter)getWriter((Node)node);
	}

	public static AccessibleWriter getWriter(final Accessible node)
	{
		return (AccessibleWriter)getWriter((Node)node);
	}

	public static IdentifierWriter getWriter(final Identifier node)
	{
		return (IdentifierWriter)getWriter((Node)node);
	}

	public static AbstractMethodDeclarationWriter getWriter(final AbstractMethodDeclaration node)
	{
		return (AbstractMethodDeclarationWriter)getWriter((Node)node);
	}

	public static AnnotationWriter getWriter(final Annotation node)
	{
		return (AnnotationWriter)getWriter((Node)node);
	}

	public static ArgumentListWriter getWriter(final ArgumentList node)
	{
		return (ArgumentListWriter)getWriter((Node)node);
	}

	public static ArrayAccessWriter getWriter(final ArrayAccess node)
	{
		return (ArrayAccessWriter)getWriter((Node)node);
	}

	public static ArrayWriter getWriter(final Array node)
	{
		return (ArrayWriter)getWriter((Node)node);
	}

	public static AssignmentMethodWriter getWriter(final AssignmentMethod node)
	{
		return (AssignmentMethodWriter)getWriter((Node)node);
	}

	public static AssignmentWriter getWriter(final Assignment node)
	{
		return (AssignmentWriter)getWriter((Node)node);
	}

	public static BinaryOperationWriter getWriter(final BinaryOperation node)
	{
		return (BinaryOperationWriter)getWriter((Node)node);
	}

	public static BodyMethodDeclarationWriter getWriter(final BodyMethodDeclaration node)
	{
		return (BodyMethodDeclarationWriter)getWriter((Node)node);
	}

	public static BreakWriter getWriter(final Break node)
	{
		return (BreakWriter)getWriter((Node)node);
	}

	public static CaseWriter getWriter(final Case node)
	{
		return (CaseWriter)getWriter((Node)node);
	}

	public static CatchWriter getWriter(final Catch node)
	{
		return (CatchWriter)getWriter((Node)node);
	}

	public static ClassDeclarationWriter getWriter(final ClassDeclaration node)
	{
		return (ClassDeclarationWriter)getWriter((Node)node);
	}

	public static ClosureContextDeclarationWriter getWriter(final ClosureContextDeclaration node)
	{
		return (ClosureContextDeclarationWriter)getWriter((Node)node);
	}

	public static ClosureContextWriter getWriter(final ClosureContext node)
	{
		return (ClosureContextWriter)getWriter((Node)node);
	}

	public static ClosureDeclarationWriter getWriter(final ClosureDeclaration node)
	{
		return (ClosureDeclarationWriter)getWriter((Node)node);
	}

	public static ClosureParameterListWriter getWriter(final ClosureParameterList node)
	{
		return (ClosureParameterListWriter)getWriter((Node)node);
	}

	public static ClosureWriter getWriter(final Closure node)
	{
		return (ClosureWriter)getWriter((Node)node);
	}

	public static ConstructorWriter getWriter(final Constructor node)
	{
		return (ConstructorWriter)getWriter((Node)node);
	}

	public static ContinueWriter getWriter(final Continue node)
	{
		return (ContinueWriter)getWriter((Node)node);
	}

	public static ControlStatementWriter getWriter(final ControlStatement node)
	{
		return (ControlStatementWriter)getWriter((Node)node);
	}

	public static DefaultParameterInitializationWriter getWriter(final DefaultParameterInitialization node)
	{
		return (DefaultParameterInitializationWriter)getWriter((Node)node);
	}

	public static DefaultWriter getWriter(final Default node)
	{
		return (DefaultWriter)getWriter((Node)node);
	}

	public static DestructorWriter getWriter(final Destructor node)
	{
		return (DestructorWriter)getWriter((Node)node);
	}

	public static DimensionsWriter getWriter(final Dimensions node)
	{
		return (DimensionsWriter)getWriter((Node)node);
	}

	public static ElseStatementWriter getWriter(final ElseStatement node)
	{
		return (ElseStatementWriter)getWriter((Node)node);
	}

	public static ExceptionHandlerWriter getWriter(final ExceptionHandler node)
	{
		return (ExceptionHandlerWriter)getWriter((Node)node);
	}

	public static ExtensionVTableWriter getWriter(final ExtensionVTable node)
	{
		return (ExtensionVTableWriter)getWriter((Node)node);
	}

	public static ExternalMethodDeclarationWriter getWriter(final ExternalMethodDeclaration node)
	{
		return (ExternalMethodDeclarationWriter)getWriter((Node)node);
	}

	public static FallthroughWriter getWriter(final Fallthrough node)
	{
		return (FallthroughWriter)getWriter((Node)node);
	}

	public static FieldDeclarationWriter getWriter(final FieldDeclaration node)
	{
		return (FieldDeclarationWriter)getWriter((Node)node);
	}

	public static FileDeclarationWriter getWriter(final FileDeclaration node)
	{
		return (FileDeclarationWriter)getWriter((Node)node);
	}

	public static FinallyWriter getWriter(final Finally node)
	{
		return (FinallyWriter)getWriter((Node)node);
	}

	public static ForEachLoopWriter getWriter(final ForEachLoop node)
	{
		return (ForEachLoopWriter)getWriter((Node)node);
	}

	public static ForLoopWriter getWriter(final ForLoop node)
	{
		return (ForLoopWriter)getWriter((Node)node);
	}

	public static IfStatementWriter getWriter(final IfStatement node)
	{
		return (IfStatementWriter)getWriter((Node)node);
	}

	public static IIdentifierWriter getWriter(final IIdentifier node)
	{
		return (IIdentifierWriter)getWriter((Node)node);
	}

	public static ImportListWriter getWriter(final ImportList node)
	{
		return (ImportListWriter)getWriter((Node)node);
	}

	public static ImportWriter getWriter(final Import node)
	{
		return (ImportWriter)getWriter((Node)node);
	}

	public static InstanceDeclarationWriter getWriter(final InstanceDeclaration node)
	{
		return (InstanceDeclarationWriter)getWriter((Node)node);
	}

	public static InstanceFieldListWriter getWriter(final InstanceFieldList node)
	{
		return (InstanceFieldListWriter)getWriter((Node)node);
	}

	public static InstantiationWriter getWriter(final Instantiation node)
	{
		return (InstantiationWriter)getWriter((Node)node);
	}

	public static IValueWriter getWriter(final IValue node)
	{
		return (IValueWriter)getWriter((Node)node);
	}

	public static GenericTypeParameterWriter getWriter(final GenericTypeParameter node)
	{
		return (GenericTypeParameterWriter) getWriter((Node)node);
	}

	public static LambdaMethodDeclarationWriter getWriter(final LambdaMethodDeclaration node)
	{
		return (LambdaMethodDeclarationWriter)getWriter((Node)node);
	}

	public static ListWriter getWriter(final List node)
	{
		return (ListWriter)getWriter((Node)node);
	}

	public static LiteralWriter getWriter(final Literal node)
	{
		return (LiteralWriter)getWriter((Node)node);
	}

	public static LocalDeclarationWriter getWriter(final LocalDeclaration node)
	{
		return (LocalDeclarationWriter)getWriter((Node)node);
	}

	public static LoopWriter getWriter(final Loop node)
	{
		return (LoopWriter)getWriter((Node)node);
	}

	public static MatchCaseWriter getWriter(final MatchCase node)
	{
		return (MatchCaseWriter)getWriter((Node)node);
	}

	public static MatchWriter getWriter(final Match node)
	{
		return (MatchWriter)getWriter((Node)node);
	}

	public static MethodCallArgumentListWriter getWriter(final MethodCallArgumentList node)
	{
		return (MethodCallArgumentListWriter)getWriter((Node)node);
	}

	public static MethodCallWriter getWriter(final MethodCall node)
	{
		return (MethodCallWriter)getWriter((Node)node);
	}

	public static MethodDeclarationWriter getWriter(final MethodDeclaration node)
	{
		return (MethodDeclarationWriter)getWriter((Node)node);
	}

	public static MethodListWriter getWriter(final MethodList node)
	{
		return (MethodListWriter)getWriter((Node)node);
	}

	public static FlatMethodDeclarationWriter getWriter(final FlatMethodDeclaration node)
	{
		return (FlatMethodDeclarationWriter)getWriter((Node)node);
	}

	public static FlatParameterListWriter getWriter(final FlatParameterList node)
	{
		return (FlatParameterListWriter)getWriter((Node)node);
	}

	public static OperatorWriter getWriter(final Operator node)
	{
		return (OperatorWriter)getWriter((Node)node);
	}

	public static PackageWriter getWriter(final Package node)
	{
		return (PackageWriter)getWriter((Node)node);
	}

	public static ParameterListWriter getWriter(final ParameterList node)
	{
		return (ParameterListWriter)getWriter((Node)node);
	}

	public static ParameterWriter getWriter(final Parameter node)
	{
		return (ParameterWriter)getWriter((Node)node);
	}

	public static PriorityWriter getWriter(final Priority node)
	{
		return (PriorityWriter)getWriter((Node)node);
	}

	public static ProgramWriter getWriter(final Program node)
	{
		return (ProgramWriter)getWriter((Node)node);
	}

	public static PropertyMethodWriter getWriter(final PropertyMethod node)
	{
		return (PropertyMethodWriter)getWriter((Node)node);
	}

	public static RepeatWriter getWriter(final Repeat node)
	{
		return (RepeatWriter)getWriter((Node)node);
	}

	public static ReturnWriter getWriter(final Return node)
	{
		return (ReturnWriter)getWriter((Node)node);
	}

	public static ScopeWriter getWriter(final Scope node)
	{
		return (ScopeWriter)getWriter((Node)node);
	}

	public static SkeletonWriter getWriter(final Skeleton node)
	{
		return (SkeletonWriter)getWriter((Node)node);
	}

	public static StaticBlockWriter getWriter(final StaticBlock node)
	{
		return (StaticBlockWriter)getWriter((Node)node);
	}

	public static StaticClassReferenceWriter getWriter(final StaticClassReference node)
	{
		return (StaticClassReferenceWriter)getWriter((Node)node);
	}

	public static StaticFieldListWriter getWriter(final StaticFieldList node)
	{
		return (StaticFieldListWriter)getWriter((Node)node);
	}

	public static FieldListWriter getWriter(final FieldList node)
	{
		return (FieldListWriter)getWriter((Node)node);
	}

	public static TernaryOperationWriter getWriter(final TernaryOperation node)
	{
		return (TernaryOperationWriter)getWriter((Node)node);
	}

	public static ThrowWriter getWriter(final Throw node)
	{
		return (ThrowWriter)getWriter((Node)node);
	}

	public static TryWriter getWriter(final Try node)
	{
		return (TryWriter)getWriter((Node)node);
	}

	public static TypeListWriter getWriter(final TypeList node)
	{
		return (TypeListWriter)getWriter((Node)node);
	}

	public static UnaryOperationWriter getWriter(final UnaryOperation node)
	{
		return (UnaryOperationWriter)getWriter((Node)node);
	}

	public static UntilWriter getWriter(final Until node)
	{
		return (UntilWriter)getWriter((Node)node);
	}

	public static ValueWriter getWriter(final Value node)
	{
		return (ValueWriter)getWriter((Node)node);
	}

	public static VariableDeclarationListWriter getWriter(final VariableDeclarationList node)
	{
		return (VariableDeclarationListWriter)getWriter((Node)node);
	}

	public static VariableDeclarationWriter getWriter(final VariableDeclaration node)
	{
		return (VariableDeclarationWriter)getWriter((Node)node);
	}

	public static VariableWriter getWriter(final Variable node)
	{
		return (VariableWriter)getWriter((Node)node);
	}

	public static VirtualMethodDeclarationWriter getWriter(final VirtualMethodDeclaration node)
	{
		return (VirtualMethodDeclarationWriter)getWriter((Node)node);
	}

	public static VTableWriter getWriter(final VTable node)
	{
		return (VTableWriter)getWriter((Node)node);
	}

	public static WhileLoopWriter getWriter(final WhileLoop node)
	{
		return (WhileLoopWriter)getWriter((Node)node);
	}
}
