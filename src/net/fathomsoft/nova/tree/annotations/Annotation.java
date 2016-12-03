package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.Value;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 */
public class Annotation extends Node
{
	public HashMap<String, Object> parameters = new HashMap<>();
	
	public static final HashMap<String, Constructor> MODIFIERS = new HashMap<>();
	
	static
	{
		Class<?>[] classes = new Class<?>[] {
			AbstractAnnotation.class,
			NativeAnnotation.class,
			ObsoleteAnnotation.class,
			OverrideAnnotation.class,
			PrivateAnnotation.class,
			PublicAnnotation.class,
			StaticAnnotation.class,
			VisibleAnnotation.class,
			ImmutableAnnotation.class,
			FinalAnnotation.class
		};
		
		Arrays.stream(classes).forEach(c -> {
			try
			{
				java.lang.reflect.Constructor constructor = c.getConstructor(Node.class, Location.class);
				
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
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Annotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
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
				value = ((Node)param.getValue()).generateNovaInput().toString();
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
	public String toString()
	{
		String name = getClass().getSimpleName();
		name = name.substring(0, name.length() - "Annotation".length());
		
		String parameters = writeParameters();
		
		return "[" + name + (parameters.length() > 0 ? " " + parameters : "") + "]";
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
		
		Value value = SyntaxTree.decodeValue(getParent(), assignment, getLocationIn(), true);
		
		if (value == null)
		{
			return SyntaxMessage.queryError("Invalid annotation parameter value '" + assignment + "'", this, true);
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
	
	public Annotation getAnnotationOfType(Class type)
	{
		if (getAnnotations() != null)
		{
			for (Annotation a : getAnnotations())
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
		
		return this;
	}
	
	/**
	 * Decode the given statement into a {@link Annotation} instance, if
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
	 * 		{@link Annotation} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link Annotation}.
	 */
	public static Annotation decodeStatement(Node parent, String statement, Location location, boolean require)
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
					
					Annotation n = RequireGenericTypeAnnotation.decodeStatement(parent, name, arguments, location, require);
					
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
									n = FinalAnnotation.decodeStatement(parent, name, arguments, location, require);
									
									if (n == null)
									{
										n = StaticAnnotation.decodeStatement(parent, name, arguments, location, require);
										
										if (n == null)
										{
											n = AbstractAnnotation.decodeStatement(parent, name, arguments, location, require);
											
											if (n == null)
											{
												n = ImmutableAnnotation.decodeStatement(parent, name, arguments, location, require);
												
												if (n == null)
												{
													n = KeepWhitespaceAnnotation.decodeStatement(parent, name, arguments, location, require);
													
													if (n == null)
													{
														n = ObsoleteAnnotation.decodeStatement(parent, name, arguments, location, require);
														
														if (n == null)
														{
															n = OverrideAnnotation.decodeStatement(parent, name, arguments, location, require);
															
															if (n == null)
															{
																n = TargetAnnotation.decodeStatement(parent, name, arguments, location, require);
																
																if (n == null)
																{
																	n = NativeAnnotation.decodeStatement(parent, name, arguments, location, require);
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
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);

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
		getProgram().addPendingAnnotation(this);
		
		return super.onAfterDecoded();
	}
	
	@Override
	public void onAdded(Node parent)
	{
		if (parent instanceof Annotation == false)
		{
			onApplied(parent);
		}
		
		super.onAdded(parent);
	}
	
	public final boolean onApplied(Node appliedTo)
	{
		return onApplied(appliedTo, true);
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
	
	public boolean invalidAppliedTo(Node node, boolean require)
	{
		return SyntaxMessage.queryError(getClass().getSimpleName() + " is applied to an invalid type '" + node.getClass().getSimpleName() + "'", node, require);
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
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Annotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Annotation node = new Annotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Annotation cloneTo(Annotation node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Annotation} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Annotation cloneTo(Annotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link Annotation} class type to make sure everything
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