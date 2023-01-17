package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.variables.Variable;
import flat.tree.variables.VariableDeclaration;
import flat.util.*;

/**
 * Loop extension that represents the declaration of a "foreach loop"
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.45 Jan 5, 2014 at 9:55:15 PM
 * @version	v0.2.45 Oct 13, 2014 at 12:16:42 AM
 */
public class ForEachLoop extends Loop
{
	public static final String	IDENTIFIER = "for";
	
	public Value givenIteratorValue;
	
	public LocalDeclaration elementDeclaration;
	
	/**
	 * Instantiate a new ForLoop and initialize its default values.
	 * 
	 * @see Node#Node(Node, Location)
	 */
	public ForEachLoop(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ArgumentList argumentsNode = new ArgumentList(this, locationIn);
		
		addChild(argumentsNode, this);
	}
	
	@Override
	public VariableDeclaration searchVariable(Node parent, Scope scope, String name, boolean checkAncestors)
	{
		if (elementDeclaration != null && name.equals(elementDeclaration.getName()))
		{
			return elementDeclaration;
		}
		
		return super.searchVariable(parent, scope, name, checkAncestors);
	}
	
	@Override
	public boolean pendingScopeFragment(Node node)
	{
		return getScope().getNumVisibleChildren() == 1;
	}
	
	/**
	 * @see Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	/**
	 * Get the ArgumentList instance that contains the initialization,
	 * condition, and update nodes that instruct the for loop.
	 * 
	 * @return The ArgumentList instance containing the arguments of
	 * 		the for loop.
	 */
	public ArgumentList getArgumentList()
	{
		return (ArgumentList)getChild(1);
	}
	
	/**
	 * Get the Variable that describes the variable section of the foreach
	 * loop. For instance: "for (person in people)" the "person" part
	 * is the variable.
	 * 
	 * @return The Variable instance that describes the variable section
	 * 		of the foreach loop.
	 */
	public Variable getVariable()
	{
		return (Variable)getArgumentList().getChild(0);
	}
	
	/**
	 * Get the identifier that was first decoded in order to
	 * transform it into the variable declaration.
	 */
	private Identifier getIdentifier()
	{
		return (Identifier)getArgumentList().getChild(0);
	}

	/**
	 * Get the variable declaration that was first decoded in order to
	 * transform it into the variable.
	 */
	private LocalDeclaration getVariableDeclaration()
	{
		return (LocalDeclaration)getArgumentList().getChild(0);
	}
	
	/**
	 * Get the Value that describes the Iterator that is going to be
	 * iterated over. For instance: "for (person in people)" the
	 * "people" part is the Iterator.
	 * 
	 * @return The Value instance that describes the Iterator section of
	 * 		the foreach loop.
	 */
	public Variable getIterator()
	{
		return (Variable)getArgumentList().getChild(1);
	}
	
	public Accessible getIteratorValue()
	{
		return (Accessible)getVisibleChild(0);
	}
	
	public Value getHasNextCheck()
	{
		return (Value)getArgumentList().getChild(2);
	}
	
	public Assignment getIteratorAssignment()
	{
		return (Assignment)getParent().getAncestorWithScope().getScope().getChildBefore(this);
	}
	
	public Assignment getNextAssignment()
	{
		return (Assignment)getScope().getVisibleChild(0);
	}
	
	/**
	 * Decode the given statement into a ForLoop instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>for (person in people)</li>
	 * 	<li>for (int in ints)</li>
	 * 	<li>for (element in array)</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ForLoop instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ForLoop.
	 */
	public static Loop decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.startsWith(IDENTIFIER) && StringUtils.findNextWord(statement).equals(IDENTIFIER))
		{
			ForEachLoop n      = new ForEachLoop(parent, location);
			Bounds      bounds = SyntaxUtils.findParenthesesBounds(n, statement);

			if (!bounds.isValid()) {
				SyntaxMessage.queryError("Invalid foreach loop definition", n, require);

				return null;
			}
			
			if (!bounds.extractPreString(statement).trim().equals(IDENTIFIER))
			{
				SyntaxMessage.queryError("Invalid foreach loop definition", n, require);
				
				return null;
			}
			
			bounds = StringUtils.removeSurroundingParenthesis(statement, bounds);
			
			if (bounds.getStart() >= 0)
			{
				String contents = statement.substring(bounds.getStart(), bounds.getEnd());
				
				Value iterator = n.decodeArguments(contents, bounds, require);
				
				if (iterator instanceof IntRange)
				{
					IntRange range = (IntRange)iterator;

					String name = n.getIdentifier().getName();
					String type = SyntaxUtils.getValueInCommon(range.getStartValue(), range.getEndValue()).generateFlatType(parent.getParentMethod(true)).toString();

					String forLoopDeclaration = "for (" + type + " " + name + " = " + range.getStartValue().generateFlatInput() + "; " + name + " < " + range.getEndValue().generateFlatInput() + "; " + name + "++)" + statement.substring(bounds.getEnd() + 1);

					ForLoop loop = ForLoop.decodeStatement(parent, forLoopDeclaration, location, require);

					return loop;
				}
				else if (iterator != null)
				{
					if (n.setupVariables())
					{
						if (n.decodeHasNextCheck(require))
						{
							if (n.decodeVariableAssignment(require))
							{
								if (n.decodeIteratorAssignment(require))
								{
									if (n.decodeScopeFragment(statement, bounds))
									{
										n.removeChild(n.getIteratorValue().toValue());
										
										return n;
									}
								}
							}
						}
					}
				}
			}
			else
			{
				SyntaxMessage.error("Foreach loop missing arguments", n);
			}
		}
		
		return null;
	}
	
	private boolean decodeHasNextCheck(boolean require)
	{
		Identifier node = SyntaxTree.decodeIdentifier(getIterator(), "hasNext", getIterator().getLocationIn().asNew(), false);
		
		if (node == null)
		{
			return SyntaxMessage.queryError("Could not decode hasNext portion of Iterator for foreach loop", this, require);
		}
		
		Identifier hasNext = getIterator().getDeclaration().generateUsableVariable(getIterator().getParent(), getIterator().getLocationIn().asNew());
		hasNext.setAccessedNode(node);
		
		getArgumentList().addChild(hasNext);
		
		return true;
	}
	
	private boolean decodeVariableAssignment(boolean require)
	{
		Assignment assignment = Assignment.decodeStatement(getScope(), getVariable().generateFlatInput() + " = " + getIterator().generateFlatInput() + ".stepNext", getIterator().getLocationIn().asNew(), require);
		
		if (assignment == null)
		{
			return SyntaxMessage.queryError("Could not decode assignment portion of Iterator for foreach loop", this, require);
		}
		
		assignment.setProperty("userMade", false);
		assignment.wasDeclaration = true;

		getScope().addChild(getScope().getNumDecodedChildren(), assignment);
		
		return true;
	}
	
	private boolean decodeIteratorAssignment(boolean require)
	{
		Assignment assignment = Assignment.decodeStatement(getParent().getAncestorWithScope().getScope(),
				getIterator().generateFlatInput() + " = " + getIteratorValue().toValue().generateFlatInput(),
				getIterator().getLocationIn().asNew(),
				require);
		
		if (assignment == null)
		{
			return SyntaxMessage.queryError("Could not decode assignment portion of Iterator for foreach loop", this, require);
		}
		
		assignment.getAssignedNode().declaration.setProperty("userMade", false);
		assignment.setProperty("userMade", false);
		assignment.wasDeclaration = true;
		
		getParent().getAncestorWithScope().getScope().addChild(assignment);
		
		//getArgumentList().addChild(assignment);
		
		//getParent().getAncestorWithScope().getScope().getVariableList().addChild(assignment.getAssignedNode().getDeclaration());
		
		return true;
	}
	
	private boolean setupVariables()
	{
		LocalDeclaration decl = getVariableDeclaration();
		decl.setProperty("forLoopVariable", true);
		decl.setProperty("userMade", false);
		
		getArgumentList().replace(decl, decl.generateUsableVariable(getArgumentList(), decl.getLocationIn()));
		
		decl.parent = this;
		elementDeclaration = decl;
		
//		VariableDeclarationList variables = getParent().getAncestorWithScope().getScope().getVariableList();
//		
//		variables.addChild(decl);
		
		decl.setScopeID(getScope().getID());
		//variables.addChild(getIterator().getDeclaration());
		
		return true;
	}
	
	private Value decodeArguments(String contents, Bounds bounds, boolean require)
	{
		Location newLoc = new Location(getLocationIn());
		newLoc.addBounds(bounds.getStart(), bounds.getEnd());
		
		// TODO: This is not strict enough.
		String arguments[] = contents.split("\\s+in\\s+");
		
		if (arguments.length < 2)
		{
			return null;
		}
		
		givenIteratorValue = SyntaxTree.decodeValue(this, arguments[1], newLoc, require);
		
		if (givenIteratorValue == null)
		{
			return null;
		}
		
		if (!decodeVariable(arguments[0], newLoc, require))
		{
			return null;
		}
		if (givenIteratorValue instanceof IntRange)
		{
			return givenIteratorValue;
		}
		
		Value value = decodeValue(arguments[1], givenIteratorValue, newLoc, require);
		
		if (value != null)
		{
			if (!decodeIterator(value, newLoc, require))
			{
				return null;
			}
		}
		
		return value;
	}
	
	private boolean decodeVariable(String argument, Location location, boolean require)
	{
		if (!Patterns.IDENTIFIER.matcher(argument).matches())
		{
			return false;
		}

		Identifier variable = new IIdentifier(this, location);
		
		variable.setName(argument);
		
		getArgumentList().addChild(variable);
		
		return true;
	}
	
	private Value decodeValue(String argument, Value value, Location location, boolean require)
	{
		ClassDeclaration iterator = getProgram().getClassDeclaration("flat/datastruct/list/Iterator");
		ClassDeclaration valueType = value.getReturnedNode().getTypeClass();
		
		if (valueType == null)
		{
			return null;
		}
		
		if (!valueType.isOfType(iterator))
		{
			ClassDeclaration iterable = getProgram().getClassDeclaration("flat/datastruct/list/Iterable");
			
			if (!value.getReturnedNode().getTypeClass().isOfType(iterable))
			{
				SyntaxMessage.queryError("Type of '" + argument + "' must be Iterable or Iterator", this, require);
				
				return null;
			}
			
			value = SyntaxTree.decodeValue(this, "(" + value.generateFlatInput() + ").iterator", location, require);
		}
		
		return value;
	}
	
	private boolean decodeIterator(Value value, Location location, boolean require)
	{
		Accessible identifier = (Accessible)value;
		
		Variable iteratorCall = (Variable)identifier.getReturnedNode();
		
		iteratorCall.importGenericArgumentTypesTo(getFileDeclaration());
		
		getFileDeclaration().addImport(iteratorCall.getTypeClassLocation());
		
		addChild(value, this);
		
		String s = identifier.getRootAccessNode().toValue().generateFlatInput().toString();

		Accessible nextVarAccessor = (Accessible)SyntaxTree.decodeValue(this, s + ".stepNext", location, require);

		if (nextVarAccessor == null) {
			return false;
		}

		Variable next =  (Variable)nextVarAccessor.getLastAccessed();
		
		getFileDeclaration().addImport(next.getTypeClassLocation());
		
		String type = next.getFlatType(next);//.getIntelligentGenericTypeArgument(0);
		
		String declaration = type + " " + getIdentifier().getName();
		
		LocalDeclaration variable = LocalDeclaration.decodeStatement(this, declaration, location, require);
		
		if (variable == null || !variable.validateDeclaration(require))
		{
			return false;
		}
		
		getArgumentList().replace(getIdentifier(), variable);
		Variable local = getParent().getAncestorWithScope().getScope().registerLocalVariable(value.getReturnedNode(), this, true);
		
		local.declaration.setProperty("userMade", false);
		
		getArgumentList().addChild(local);
		
		return true;
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		return result;
	}
	
	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public ForEachLoop clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ForEachLoop node = new ForEachLoop(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public ForEachLoop cloneTo(ForEachLoop node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ForEachLoop} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ForEachLoop cloneTo(ForEachLoop node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.givenIteratorValue = givenIteratorValue;
		
		return node;
	}
	
	/**
	 * Test the ForLoop class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray)
	{
		builder.append("for (").append(getVariable().getName()).append(" in ").append(givenIteratorValue.generateFlatInput()).append(")");
		
		if (outputChildren)
		{
			getScope().generateFlatInput(builder);
		}
		
		return builder;
	}
	
	public String toString()
	{
		String s = "for (" + getVariable().getName() + " in " + getIterator().generateFlatInput() + ")";
		
		
		
		return s;
	}
}