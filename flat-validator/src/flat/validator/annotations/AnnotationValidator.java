package flat.validator.annotations;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxErrorException;
import flat.error.SyntaxMessage;
import flat.tree.Node;
import flat.tree.SyntaxTree;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;
import flat.validator.NodeValidator;
import flat.validator.ValidationResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AnnotationValidator extends NodeValidator
{
	public boolean userAdded;
	
	public HashMap<String, Object> parameters = new HashMap<>();
	
	public static final HashMap<String, Constructor> MODIFIERS = new HashMap<>();
	
	static
	{
		Class<?>[] classes = new Class<?>[] {
			AbstractAnnotationValidator.class,
			NativeAnnotation.class,
			ObsoleteAnnotationValidator.class,
			OverrideAnnotationValidator.class,
			PrivateAnnotation.class,
			PublicAnnotation.class,
			StaticAnnotation.class,
			VisibleAnnotation.class,
			ImmutableAnnotationValidator.class,
			FinalAnnotationValidator.class,
			VarAnnotation.class,
			PassingAnnotationValidator.class,
			PureFunctionAnnotationValidator.class,
			ImpureFunctionAnnotationValidator.class,
			SyncAnnotationValidator.class,
			ThreadLocalAnnotationValidator.class,
			CompilerVisibleAnnotationValidator.class,
			ExternalNameAnnotationValidator.class,
			TestAnnotationValidator.class,
			TestableAnnotationValidator.class,
			InitTestAnnotationValidator.class,
			CleanTestAnnotationValidator.class,
			InitTestClassAnnotationValidator.class,
			CleanTestClassAnnotationValidator.class,
			TestSuccessAnnotationValidator.class,
			TestFailureAnnotationValidator.class,
			TestResultAnnotationValidator.class,
			ThisModifier.class,
			SuperParameterModifier.class,
			NamedArgumentModifier.class,
			AsyncAnnotation.class,
			AwaitAnnotationValidator.class,
			LazyAnnotation.class,
			InlineAnnotation.class
		};
		
		Arrays.stream(classes).forEach(c -> {
			try
			{
				Constructor constructor = c.getConstructor(Node.class, Location.class);
				
				ModifierAnnotation a = (ModifierAnnotation)constructor.newInstance(null, null);
				
				String[] aliases = a.getAliases();
				
				for (String alias : aliases)
				{
					MODIFIERS.put(alias, constructor);
				}
			}
			catch (NoSuchMethodException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
		});
	}
	/**
	 * @see Node#Node(Node, Location)
	 */
	public AnnotationValidator(Node temporaryParent, Location locationIn)
	{
		this(temporaryParent, locationIn, true);
	}
	
	public AnnotationValidator(Node temporaryParent, Location locationIn, boolean userAdded)
	{
		super(temporaryParent, locationIn);
		
		this.userAdded = userAdded;
	}
	
	public String[] defaultParameterNames()
	{
		return new String[0];
	}
	
	public String[][] defaultParameterTypes()
	{
		return new String[0][];
	}
	
	public String writeParameters()
	{
		String output = "";
		
		for (Map.Entry<String, Object> param : parameters.entrySet())
		{
			String value;
			
			if (param.getValue() instanceof Node)
			{
				value = ((Node)param.getValue()).generateFlatInput().toString();
			}
			else
			{
				value = param.getValue().toString();
			}
			
			if (output.length() > 0)
			{
				output += ", ";
			}
			
			output += param.getKey() + ": " + value;
		}
		
		return output.trim();
	}
	
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray)
	{
		if (this instanceof ModifierAnnotation) {
			ModifierAnnotation mod = (ModifierAnnotation) this;

			if (mod.getAliasUsed() != null) {
				return builder.append(mod.getAliasUsed());
			}
		}

		String name = getClass().getSimpleName();
		name = name.substring(0, name.length() - "Annotation".length());
		
		String parameters = writeParameters();
		
		return builder.append("[" + name + (parameters.length() > 0 ? " " + parameters : "") + "]");
	}
	
	@Override
	public String toString()
	{
		return generateFlatInput().toString();
	}
	
	public boolean parseParameter(String parameter, int index, boolean[] parsedExplicit)
	{
		int colon = SyntaxUtils.findCharInBaseScope(parameter, ':');
		
		String assigned;
		
		if (colon < 0 && parsedExplicit[0])
		{
			return SyntaxMessage.queryError("Must explicitly specify parameter name for '" + parameter + "'", this, true);
		}
		else if (colon < 0)
		{
			assigned = defaultParameterNames()[index];
		}
		else
		{
			assigned = parameter.substring(0, colon).trim();
		}
		
		String assignment = parameter.substring(colon + 1).trim();
		
		if (!SyntaxUtils.isValidIdentifier(assigned))
		{
			return SyntaxMessage.queryError("Invalid annotation parameter name '" + assigned + "'", this, true);
		}
		
		Object value;
		
		if (defaultParameterTypes()[index][0].equals("list"))
		{
			if (assignment.startsWith("["))
			{
				int end = StringUtils.findEndingMatch(assignment, 0, '[', ']');
				
				if (end != assignment.length() - 1)
				{
					return SyntaxMessage.queryError("Invalid annotation parameter value '" + assignment + "'", this, true);
				}
				
				value = StringUtils.splitCommas(assignment.substring(1, end).trim(), 1);
			}
			else
			{
				value = new String[] { assignment };
			}
		}
		else if (defaultParameterTypes()[index][0].equals("Identifier"))
		{
			if (SyntaxUtils.isValidIdentifier(assignment))
			{
				value = assignment;
			}
			else
			{
				return SyntaxMessage.queryError("Invalid annotation parameter value '" + assignment + "'", this, true);
			}
		}
		else
		{
			value = SyntaxTree.decodeValue(getParent(), assignment, getLocationIn(), true);
			
			if (value == null)
			{
				return SyntaxMessage.queryError("Invalid annotation parameter value '" + assignment + "'", this, true);
			}
		}
		
		parameters.put(assigned, value);
		
		return true;
	}
	
	public boolean parseParameters(String parameters)
	{
		final boolean[] parsedExplicit = new boolean[] { false };
		final int[] index = new int[] { 0 };
		
		return parameters.length() == 0 || Arrays.stream(StringUtils.splitCommas(parameters)).allMatch(parameter -> {
			return parseParameter(parameter, index[0]++, parsedExplicit);
		});
	}
	
	public AnnotationValidator getAnnotationOfType(Class type)
	{
		if (getAnnotations() != null)
		{
			for (AnnotationValidator a : getAnnotations())
			{
				if (type.isAssignableFrom(a.getClass()))
				{
					return a;
				}
				
				return a.getAnnotationOfType(type);
			}
		}
		
		return null;
	}
	
	@Override
	public boolean isDecoding()
	{
		return false;
	}
	
	@Override
	public Node detach()
	{
		if (getParent() != null)
		{
			getParent().removeAnnotation(this);
		}
		
		parent = null;
		
		return this;
	}
	
	/**
	 * Decode the given statement into a {@link AnnotationValidator} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>[RequireGenericType(E extends Number)]</li>
	 * 	<li>[RequireGenericType E extends Number]</li>
	 * 	<li>[SomeAnnotation]</li>
	 * 	<li>[SomethingWithParams(values=["thing1", "Thing2"], size=5)]</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link AnnotationValidator} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link AnnotationValidator}.
	 */
	public static AnnotationValidator decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.startsWith("["))
		{
			int index = StringUtils.findEndingMatch(statement, 0, '[', ']');
			
			if (index > 0)
			{
				statement = statement.substring(1, index).trim();
				
				int whitespaceIndex = StringUtils.findNextWhitespaceIndex(statement, 1);
				whitespaceIndex = whitespaceIndex < 0 ? statement.length() : whitespaceIndex;
				
				String name = statement.substring(0, whitespaceIndex);
				
				if (name != null)
				{
					String arguments = statement.substring(name.length());
					
					arguments = StringUtils.removeSurroundingParenthesis(arguments);
					
					AnnotationValidator n = RequireGenericTypeAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
					
					if (n == null)
					{
						n = PublicAnnotation.decodeStatement(parent, name, arguments, location, require);
						
						if (n == null)
						{
							n = VisibleAnnotation.decodeStatement(parent, name, arguments, location, require);
							
							if (n == null)
							{
								n = PrivateAnnotation.decodeStatement(parent, name, arguments, location, require);
								
								if (n == null)
								{
									n = FinalAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
									
									if (n == null)
									{
										n = StaticAnnotation.decodeStatement(parent, name, arguments, location, require);
										
										if (n == null)
										{
											n = AbstractAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
											
											if (n == null)
											{
												n = PassingAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
												
												if (n == null)
												{
													n = ImmutableAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
													
													if (n == null)
													{
														n = PureFunctionAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
														
														if (n == null)
														{
															n = ImpureFunctionAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
															
															if (n == null)
															{
																n = SyncAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																
																if (n == null)
																{
																	n = ThreadLocalAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																	
																	if (n == null)
																	{
																		n = CompilerVisibleAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																		
																		if (n == null)
																		{
																			n = ExternalNameAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																			
																			if (n == null)
																			{
																				n = TestAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																				
																				if (n == null)
																				{
																					n = TestableAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																					
																					if (n == null)
																					{
																						n = TestSuiteAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																						
																						if (n == null)
																						{
																							n = KeepWhitespaceAnnotation.decodeStatement(parent, name, arguments, location, require);
																							
																							if (n == null)
																							{
																								n = TestSuccessAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																								
																								if (n == null)
																								{
																									n = TestFailureAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																									
																									if (n == null)
																									{
																										n = TestResultAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																										
																										if (n == null)
																										{
																											n = InitTestAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																											
																											if (n == null)
																											{
																												n = CleanTestAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																												
																												if (n == null)
																												{
																													n = InitTestClassAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																													
																													if (n == null)
																													{
																														n = CleanTestClassAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																														
																														if (n == null)
																														{
																															n = ObsoleteAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																															
																															if (n == null)
																															{
																																n = OverrideAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																																
																																if (n == null)
																																{
																																	n = AutoFinalAnnotationValidator.decodeStatement(parent, name, arguments, location, require);
																																	
																																	if (n == null)
																																	{
																																		n = TargetAnnotation.decodeStatement(parent, name, arguments, location, require);

																																		if (n == null)
																																		{
																																			n = TargetRuntimeAnnotation.decodeStatement(parent, name, arguments, location, require);

																																			if (n == null)
																																			{
																																				n = ExpectCompileErrorAnnotation.decodeStatement(parent, name, arguments, location, require);

																																				if (n == null)
																																				{
																																					n = NativeAnnotation.decodeStatement(parent, name, arguments, location, require);

																																					if (n == null)
																																					{
																																						n = AsyncAnnotation.decodeStatement(parent, name, arguments, location, require);

																																						if (n == null)
																																						{
																																							n = AwaitAnnotationValidator.decodeStatement(parent, name, arguments, location, require);

																																							if (n == null)
																																							{
																																								n = LazyAnnotation.decodeStatement(parent, name, arguments, location, require);

																																								if (n == null)
																																								{
																																									n = InlineAnnotation.decodeStatement(parent, name, arguments, location, require);
																																								}
																																							}
																																						}
																																					}
																																				}
																																			}
																																		}
																																	}
																																}
																															}
																														}
																													}
																												}
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					
					return n;
				}
			}
		}
		
		return null;
	}
	
	public static String getFragment(String statement)
	{
		int index = StringUtils.findEndingMatch(statement, 0, '[', ']');
		
		return statement.substring(index + 1).trim();
	}
	
	public ValidationResult validate(Annotation node, int phase)
	{
		ValidationResult result = super.validate(node, phase);

		if (result.skipValidation())
		{
			return result;
		}
		
		try
		{
			if (getParent() == null)
			{
				missingExpression(true);
			}
		}
		catch (SyntaxErrorException e)
		{
			detach();
		}
		
		
		
		return result;
	}
	
	@Override
	public boolean onAfterDecoded()
	{
		getFileDeclaration().addPendingAnnotation(this);
		
		return super.onAfterDecoded();
	}
	
	@Override
	public void onAdded(Node parent)
	{
		if (parent instanceof AnnotationValidator == false)
		{
			onApplied(parent);
		}
		
		super.onAdded(parent);
	}
	
	public final boolean onApplied(Node appliedTo)
	{
		return onApplied(appliedTo, false);
	}
	
	public boolean onApplied(Node appliedTo, boolean throwError)
	{
		return true;
	}
	
	@Override
	public boolean onNextStatementDecoded(Node next)
	{
		if (next != null)
		{
			if (getParent() != null)
			{
//				getLocationIn().setLineNumber(getLocationIn().getLineNumber() - getParent().getLineNumber());
			}
			//if (next instanceof Annotation)
			{
				next.addAnnotation(this);
			}
		}
		
		return super.onNextStatementDecoded(next);
	}
	
	public boolean duplicateApplication(Node node, boolean require)
	{
		if (require)
		{
			SyntaxMessage.error("Duplicate '" + getClass().getName() + "' annotation applied to expression", node);
		}
		
		return false;
	}
	
	public boolean checkDuplicate(Node next, boolean throwError)
	{
		if (next.containsAnnotationOfType(getClass(), false, false))
		{
			duplicateApplication(next, throwError);
			
			return true;
		}
		
		return false;
	}
	
	public boolean invalidApplication(Node node, boolean require)
	{
		if (require)
		{
			SyntaxMessage.error(getClass().getSimpleName() + " is applied to an invalid type '" + node.getClass().getSimpleName() + "'", node);
		}
		
		return false;
	}
	
	public boolean invalidArgument(String name, boolean require)
	{
		return SyntaxMessage.queryError(getClass().getSimpleName() + " given invalid argument '" + name + "'", this, require);
	}
	
	public boolean missingExpression(boolean require)
	{
		return SyntaxMessage.queryError(getClass().getSimpleName() + " is missing an expression to be applied to", this, require);
	}
	
	public boolean requiresArguments(boolean require)
	{
		return SyntaxMessage.queryError("Missing annotation arguments", this, require);
	}
	
	public boolean tooManyArguments(boolean require)
	{
		return SyntaxMessage.queryError("Too many arguments given to annotation", this, require);
	}
	
	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public AnnotationValidator clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		AnnotationValidator node = new AnnotationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public AnnotationValidator cloneTo(AnnotationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link AnnotationValidator} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public AnnotationValidator cloneTo(AnnotationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.parameters = new HashMap<>();
		
		for (Map.Entry<String, Object> entry : parameters.entrySet())
		{
			node.parameters.put(entry.getKey(), entry.getValue());
		}
		
		return node;
	}
	
	/**
	 * Test the {@link AnnotationValidator} class type to make sure everything
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
