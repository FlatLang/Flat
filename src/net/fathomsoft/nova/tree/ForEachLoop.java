package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TargetC;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.tree.variables.VariableDeclarationList;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

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
	
	/**
	 * Instantiate a new ForLoop and initialize its default values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ForEachLoop(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ArgumentList argumentsNode = new ArgumentList(this, locationIn);
		
		addChild(argumentsNode, this);
	}
	
	@Override
	public boolean pendingScopeFragment(Node node)
	{
		return getScope().getNumVisibleChildren() == 1;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
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
		if (StringUtils.findNextWord(statement).equals(IDENTIFIER))
		{
			ForEachLoop n      = new ForEachLoop(parent, location);
			Bounds      bounds = SyntaxUtils.findParenthesesBounds(n, statement);
			
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
					String type = range.getStartValue().getType();
					
					ForLoop loop = ForLoop.decodeStatement(parent, "for (" + type + " " + name + " = " + range.getStartValue().generateNovaInput() + "; " + name + " < " + range.getEndValue().generateNovaInput() + "; " + name + "++)" + statement.substring(bounds.getEnd() + 1), location, require);
					
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
		Assignment assignment = Assignment.decodeStatement(getScope(), getVariable().generateNovaInput() + " = " + getIterator().generateNovaInput() + ".next", getIterator().getLocationIn().asNew(), require);
		
		if (assignment == null)
		{
			return SyntaxMessage.queryError("Could not decode assignment portion of Iterator for foreach loop", this, require);
		}
		
		getScope().addChild(getScope().getNumDecodedChildren(), assignment);
		
		return true;
	}
	
	private boolean decodeIteratorAssignment(boolean require)
	{
		Assignment assignment = Assignment.decodeStatement(getParent().getAncestorWithScope().getScope(),
				getIterator().generateNovaInput() + " = " + getIteratorValue().toValue().generateNovaInput(),
				getIterator().getLocationIn().asNew(),
				require);
		
		if (assignment == null)
		{
			return SyntaxMessage.queryError("Could not decode assignment portion of Iterator for foreach loop", this, require);
		}
		
		getParent().getAncestorWithScope().getScope().addChild(assignment);
		
		//getArgumentList().addChild(assignment);
		
		//getParent().getAncestorWithScope().getScope().getVariableList().addChild(assignment.getAssignedNode().getDeclaration());
		
		return true;
	}
	
	private boolean setupVariables()
	{
		LocalDeclaration decl = getVariableDeclaration();
		
		getArgumentList().replace(decl, decl.generateUsableVariable(getArgumentList(), decl.getLocationIn()));
		
		VariableDeclarationList variables = getParent().getAncestorWithScope().getScope().getVariableList();
		
		variables.addChild(decl);
		
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
		
		Value value = SyntaxTree.decodeValue(this, arguments[1], newLoc, require);
		
		if (value == null)
		{
			return null;
		}
		
		if (!decodeVariable(arguments[0], newLoc, require))
		{
			return null;
		}
		if (value instanceof IntRange)
		{
			return value;
		}
		
		value = decodeValue(arguments[1], value, newLoc, require);
		
		if (value != null)
		{
			decodeIterator(value, newLoc, require);
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
		ClassDeclaration iterator = getProgram().getClassDeclaration("nova/datastruct/list/Iterator");
		ClassDeclaration valueType = value.getReturnedNode().getTypeClass();
		
		if (valueType == null)
		{
			return null;
		}
		
		if (!valueType.isOfType(iterator))
		{
			ClassDeclaration iterable = getProgram().getClassDeclaration("nova/datastruct/list/Iterable");
			
			if (!value.getReturnedNode().getTypeClass().isOfType(iterable))
			{
				SyntaxMessage.queryError("Type of '" + argument + "' must be Iterable or Iterator", this, require);
				
				return null;
			}
			
			value = SyntaxTree.decodeValue(this, "(" + value.generateNovaInput() + ").iterator", location, require);
		}
		
		return value;
	}
	
	private boolean decodeIterator(Value value, Location location, boolean require)
	{
		Accessible identifier = (Accessible)value;
		
		Variable iteratorCall = (Variable)identifier.getReturnedNode();
		
		getFileDeclaration().addImport(iteratorCall.getTypeClassLocation());
		
		addChild(value, this);
		
		String s = identifier.getRootAccessNode().toValue().generateNovaInput().toString();
		
		Variable next = (Variable)((Accessible)SyntaxTree.decodeValue(this, s + ".next", location, require)).getLastAccessed();
		
		String type = next.getNovaType(next);//.getIntelligentGenericTypeArgument(0);
		
		String declaration = type + " " + getIdentifier().getName();
		
		LocalDeclaration variable = LocalDeclaration.decodeStatement(this, declaration, location, require);
		
		if (variable == null || !variable.validateDeclaration())
		{
			return false;
		}
		
		getArgumentList().replace(getIdentifier(), variable);
		getArgumentList().addChild(getParent().getAncestorWithScope().getScope().registerLocalVariable(value.getReturnedNode(), this, true));
		
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
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ForEachLoop clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ForEachLoop node = new ForEachLoop(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ForEachLoop cloneTo(ForEachLoop node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link ForEachLoop} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ForEachLoop cloneTo(ForEachLoop node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
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
	
	public String toString()
	{
		String s = "for (" + getVariable().getName() + " in " + getIterator().generateNovaInput() + ")";
		
		
		
		return s;
	}
	
	@Override
	public TargetC.TargetNode getTarget()
	{
		return TargetC.TARGET_FOR_EACH_LOOP;
	}
}