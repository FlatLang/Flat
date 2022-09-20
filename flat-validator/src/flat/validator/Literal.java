package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.annotations.KeepWhitespaceAnnotation;
import flat.util.Location;
import flat.util.Patterns;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import java.util.regex.Matcher;

public class Literal extends IValueValidator implements AccessibleValidator
{
	public boolean safeNavigation;
	public boolean chainNavigation;

	public String	value;
	
	public static final String	NULL_IDENTIFIER     = "null";
	public static final String	TRUE_IDENTIFIER     = "true";
	public static final String	FALSE_IDENTIFIER    = "false";
	
	public static final String  GARBAGE_IDENTIFIER  = "flat_garbageData";
	
//	public static final String	C_NULL_OUTPUT		= "(Object*)0";
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public Literal(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public boolean isSafeNavigation()
	{
		return safeNavigation;
	}
	
	@Override
	public void setSafeNavigation(boolean safeNavigation)
	{
		this.safeNavigation = safeNavigation;
	}

	@Override
	public boolean isChainNavigation()
	{
		return chainNavigation;
	}

	@Override
	public void setChainNavigation(boolean chainNavigation)
	{
		this.chainNavigation = chainNavigation;
	}

	@Override
	public boolean isInstance() {
		return false; // FIXME
	}

	@Override
	public boolean isGenericType(boolean checkArray, boolean checkCast)
	{
		if (!isNullLiteral())
		{
			return false;
		}
		
		return super.isGenericType(checkArray, checkCast);
	}
	
	@Override
	public String getGenericReturnType()
	{
		if (isGenericType())
		{
			return getGenericTypeParameter().getDefaultType();
		}
		
		return getType();
	}
	
	@Override
	public String getType()
	{
		CastValidator castValidator = getCast();
		
		if (castValidator != null)
		{
			return castValidator.getType();
		}
		
		return super.getType();
	}
	
	/**
	 * Get the value of the literal.
	 * 
	 * @return The value of the literal.
	 */
	public String getValue()
	{
		return value;
	}
	
	/**
	 * Set the value of the literal. If the literal's value is set within
	 * an external context, the value will be generated in a c syntax sort
	 * of way.
	 * 
	 * @param value The value to set the literal as.
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	
	/**
	 * @see NodeValidator#generateFlatInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		if (false && !isWithinExternalContext() && isStringInstantiation())
		{
			builder.append("String(").append(value).append(')');
		}
		else
		{
			builder.append(value);
		}
		
		if (outputChildren && doesAccess())
		{
			generateAccessedNode(builder, safeNavigation);
		}
		
		return builder;
	}
	
	/**
	 * Get the data type that the literal represents.<br>
	 * see {@link ValueValidator#getDataType()} for
	 * more information on what possible data types there are.
	 * 
	 * @return The data type that the literal represents.
	 */
	public byte getDataType(boolean checkGeneric)
	{
		return getDataType(checkGeneric, true);
	}

	public byte getDataType(boolean checkGeneric, boolean checkCast)
	{
		if (SyntaxUtils.isStringLiteral(value) && (!isStringInstantiation() || isWithinExternalContext()))
		{
			return 1;
		}
		
		return super.getDataType(checkGeneric, checkCast);
	}
	
	@Override
	public ValueValidator getReturnedNode()
	{
		return AccessibleValidator.super.getReturnedNode();
	}
	
	/**
	 * Get whether or not the value of the literal is an
	 * instantiation of a String from a String constructor.
	 * 
	 * @return Whether or not the value of the literal is an
	 * 		instantiation of a String from a String constructor.
	 */
	public boolean isStringInstantiation()
	{
		if (SyntaxUtils.isStringLiteral(value))
		{
			if (getParent() instanceof MethodCallArgumentListValidator)
			{
				MethodCall node = (MethodCall)getAncestorOfType(MethodCall.class);
				
				if ("String".equals(node.getName()))
				{
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public static boolean isNullLiteral(NodeValidator nodeValidator)
	{
		return nodeValidator instanceof Literal && ((Literal) nodeValidator).isNullLiteral();
	}
	
	public boolean isNullLiteral()
	{
		return value.equals(NULL_IDENTIFIER);
	}
	
	@Override
	public ValueValidator replaceWithDefaultLiteralValue(ValueValidator type)
	{
		Literal v = generateDefaultValue(parent, getLocationIn(), type);
		
		value = v.value;
		setType(v.getType());
		
		return this;
	}
	
	public static Literal generateDefaultValue(NodeValidator parent, Location location, ValueValidator value)
	{
		Literal n = new Literal(parent, location);
		
		if (value.isPrimitive())
		{
			switch (value.getTypeClass().getName())
			{
				case "Bool":
					n.value = "false";
					break;
				default:
					n.value = "0";
					break;
			}
			
			n.setType(value.getType());
		}
		else
		{
			n.value = NULL_IDENTIFIER;
			n.setType("Object");
		}
		
		return n;
	}
	
	public static Literal generateDefault(NodeValidator parent, Location location)
	{
		Literal n = new Literal(parent, location);
		n.value = NULL_IDENTIFIER;
		n.setType(SyntaxUtils.getLiteralTypeName(parent, NULL_IDENTIFIER));
		
		return n;
	}
	
	/**
	 * @see #decodeStatement(NodeValidator, String, Location, boolean)
	 * 
	 * @param mustBeLiteral Whether or not the statement must be a literal
	 * 		to decode.
	 */
	public static ValueValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require, boolean mustBeLiteral)
	{
		if (mustBeLiteral && !SyntaxUtils.isLiteral(parent, statement))
		{
			RegexLiteral regex = RegexLiteral.decodeStatement(parent, statement, location, require);
			
			if (regex != null)
			{
				return regex;
			}
			
			return null;
		}
		
		return decodeStatement(parent, statement, location, require);
	}
	
	/**
	 * Decode the given statement into a Literal instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>123</li>
	 * 	<li>-321</li>
	 * 	<li>121.32</li>
	 * 	<li>'a'</li>
	 * 	<li>'\''</li>
	 * 	<li>"Text String"</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Literal instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Literal.
	 */
	public static ValueValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		String literalType = SyntaxUtils.getLiteralTypeName(parent, statement);
		
		if (literalType != null || parent.isWithinExternalContext())
		{
			if (SyntaxUtils.isNumber(statement))
			{
				statement = SyntaxUtils.removeUnderscores(statement);
			}
			
			Literal n = new Literal(parent, location);
			n.setValue(statement);
			
			if (literalType != null)
			{
				n.setType(literalType);
				
				if (literalType.equals("String"))
				{
					statement = formatMultilineString(statement);
					n.setValue(statement);

					String expression = formatStringExpressions(statement);
					
					if (!expression.equals(statement))
					{
						return SyntaxTree.decodeValue(parent, expression, location, require);
					}
				}
			}
			
			return n;
		}
		else if (require)
		{
			SyntaxMessage.error("Could not decode literal '" + statement + "'", parent, location);
		}
		
		return null;
	}
	
	public static String formatMultilineString(String statement)
	{
		if (!statement.contains("\n")) {
			return statement;
		}

		String tabCount = "";

		if (statement.charAt(statement.length() - 2) == '|') {
			int index = statement.lastIndexOf('\n', statement.length() - 3);
			int count = statement.length() - 3 - index;

			tabCount = "[ \\t]{0," + count + "}";

			statement = statement.substring(0, index) + '"';
		}
		if (statement.startsWith("\"|")) {
			statement = statement.replaceAll("^\"\\|[ \\t\\r]*\\n" + tabCount, "\"");
		}

		return statement.replaceAll("[\\n\\r]" + tabCount, "\\\\n");
	}
	
	public Instantiation getStringInstantiation()
	{
		return Instantiation.decodeStatement(this.getParent(), "String(" + this.value + ")", this.getLocationIn(), true);
	}
	
	private static String insertExpression(String str, int index, int offset, int end, int lastEnd, int endOffset, StringBuilder builder)
	{
		String expression = str.substring(index + offset, end).trim();
		
		if (index > 1)
		{
			builder.append(str.substring(Math.max(lastEnd + endOffset, 0), index)).append("\" + ");
		}
		
		builder.append('(').append(expression).append(')');
		
		if (end < str.length() - (endOffset))
		{
			builder.append(" + \"");
		}
		else
		{
			return builder.toString();
		}
		
		return null;
	}
	
	private static String formatStringExpressions(String str)
	{
		int index = str.indexOf("#");
		int end = 0;
		int lastEnd = -1;
		int lastOffset = 0;
		
		StringBuilder builder = new StringBuilder();

		outer:
		while (index >= 0 && end >= 0)
		{
			while (StringUtils.isCharacterEscaped(str, index)) {
				index = str.indexOf("#", index + 1);

				if (index == -1) {
					break outer;
				}
			}

			if (index + 1 < str.length())
			{
				int offset = 0;
				
				if (str.charAt(index + 1) == '{')
				{
					end = SyntaxUtils.findCharInBaseScope(str, '}', index + 2);
					
					offset = 2;
				}
				else
				{
					end = SyntaxUtils.findStatementEnd(str, index + 1);
					
					offset = 1;
				}
				
				if (end > index)
				{
					String s = insertExpression(str, index, offset, end, lastEnd, lastOffset, builder);
					
					if (s != null)
					{
						return s;
					}
					
					lastOffset = offset - 1;
				}
				
				index = str.indexOf("#", end + 1);
				
				lastEnd = end;
			}
			else
			{
				break;
			}
		}
		
		if (end > 0)
		{
			builder.append(str.substring(end + lastOffset));
			
			return builder.toString();
		}
		
		return str;
	}
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (getType().equals("String"))
		{
			KeepWhitespaceAnnotation annotation = (KeepWhitespaceAnnotation)getAnnotationOfType(KeepWhitespaceAnnotation.class, true);
			
			if (annotation != null)
			{
				if (annotation.indent != null)
				{
					if (annotation.indent.equals("baseline"))
					{
						Matcher matcher = Patterns.TABS.matcher(value);
						
						int min = Integer.MAX_VALUE;
						char c = 0;
						
						while (matcher.find())
						{
							int tabCount = matcher.end() - matcher.start() - 1;
							
							if (tabCount < min)
							{
								min = tabCount;
							}
							
							c = value.charAt(matcher.start() + 1);
						}
						
						if (c != 0)
						{
							value = value.replaceAll("\n[" + c + "]{0," + min + "}", "\n");
						}
					}
				}
				
				value = value.replaceAll("[\n]", "\\\\n");
			}
		}
		
		if (isNullLiteral())
		{
			ValueValidator operand = getOppositeBinaryOperand();
			
			if (operand != null)
			{
				setType(operand.getReturnedNode().getFlatTypeValue(operand.getReturnedNode()));
			}
			else if (parent.getParentMethod() != null)
			{
				if (getAncestorOfType(Return.class) != null)
				{
					if (parent.getParentMethod().isPrimitiveOverload())
					{
						if (parent.getAncestorOfType(MethodCallArgumentListValidator.class, true) == null)
						{
							ValueValidator def = generateDefaultValue(parent, getLocationIn(), parent.getParentMethod());
							
							replaceWith(def);
							
							result.returnedNodeValidator = def;
						}
					}
					
					if (result.returnedNodeValidator == this)
					{
						getParentMethod().cloneTo(this, false, false);
					}
				}
				
				if (result.returnedNodeValidator == this && parent.getParentMethod().isPrimitiveOverload())
				{
					NodeValidator base = parent.getBaseNode();
					
					if (base instanceof AssignmentValidator)
					{
						AssignmentValidator a = (AssignmentValidator)base;
						
						if (a.getAssignedNodeValue().getReturnedNode().isPrimitive())
						{
							ValueValidator def = generateDefaultValue(parent, getLocationIn(), a.getAssignedNodeValue().getReturnedNode());
							
							ValueValidator v = this;
							
							if (parent instanceof CastValidator)
							{
								v = (ValueValidator)parent;
							}
							
							v.replaceWith(def);
							
							result.returnedNodeValidator = def;
						}
					}
				}
			}
		}
		
		if (result.returnedNodeValidator == this)
		{
			result.returnedNodeValidator = checkSafeNavigation();
		}
		
		return result;
	}
	
	public ValueValidator getOppositeBinaryOperand()
	{
		if (getParent() instanceof BinaryOperationValidator)
		{
			BinaryOperationValidator node = (BinaryOperationValidator)getParent();
			
			ValueValidator side = null;
			
			if (!node.isComparison())
			{
				if (node.getParent() instanceof BinaryOperationValidator)
				{
					node = (BinaryOperationValidator)node.getParent();
					
					side = node.getLeftOperand();
				}
			}
			else
			{
				if (node.getLeftOperand() == this)
				{
					side = node.getRightOperand();
				}
				else
				{
					side = node.getLeftOperand();
				}
			}
			
			return side;
		}
		
		return null;
	}
	
	@Override
	public FileDeclaration getReferenceFile(boolean checkCast)
	{
		if (isNullLiteral())
		{
			ValueValidator operand = getOppositeBinaryOperand();
			
			if (operand != null && !isNullLiteral(operand))
			{
				return operand.getReturnedNode().getReferenceFile(checkCast);
			}
		}
		
		return super.getReferenceFile(checkCast);
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public Literal clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Literal node = new Literal(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public Literal cloneTo(Literal node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Literal} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Literal cloneTo(Literal node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.value = value;
		node.safeNavigation = safeNavigation;
		node.chainNavigation = chainNavigation;


		return node;
	}
	
	/**
	 * Test the Literal class type to make sure everything
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