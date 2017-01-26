package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.tree.generics.GenericTypeParameterList;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Stack;

/**
 * MethodDeclaration extension that represents the declaration of a Constructor
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:50:47 PM
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class Constructor extends BodyMethodDeclaration
{
	public static final String	IDENTIFIER = "construct";
	
	private InitializationMethod	initMethod;
	
	/**
	 * Create a Constructor and initialize default values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Constructor(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public void searchVirtualMethodDeclaration()
	{
		
	}
	
	@Override
	public boolean isPure()
	{
		return true;
	}
	
	@Override
	public Value inferShorthandActionType(String action, Value contents)
	{
		return contents;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#isInstance()
	 */
	@Override
	public boolean isInstance()
	{
		return true;
	}
	
	@Override
	public boolean isSpecial()
	{
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.NovaMethodDeclaration#generateNovaInput(java.lang.StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return super.generateNovaInput(builder, outputChildren, false);
	}
	
	/**
	 * Decode the given statement into a Constructor instance, if
	 * possible. If it is not possible, this method returns null. A
	 * constructor must have the same name as the class that it is
	 * within. Constructors also do not have a return value.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public ClassName()</li>
	 * 	<li>private ClassName(int numChildren, String name)</li>
	 * 	<li>public ClassName(Node parent, Location location)</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Constructor instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Constructor.
	 */
	public static Constructor decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		BodyMethodDeclaration method = BodyMethodDeclaration.decodeStatement(parent, statement, location, false);
		
		if (method != null && method.getName().equals(IDENTIFIER))
		{
			Constructor n = new Constructor(parent, location);
			
			method.cloneTo(n);
			
			n.setName(IDENTIFIER);
			n.setType(n.getParentClass().getName(), true, false);
			n.setStatic(true);
			n.setDataType(POINTER);
			
			return n;
		}
		
		return null;
	}
	
	public ClassDeclaration getExistingConvertedPrimitiveClass(ClassDeclaration type, GenericTypeArgumentList args)
	{
		for (ClassDeclaration converted : type.primitiveOverloads)
		{
			GenericTypeParameterList params = converted.getGenericTypeParameterDeclaration();
			
			boolean compatible = true;
			
			for (int i = 0; i < args.getNumVisibleChildren(); i++)
			{
				GenericTypeParameter param = params.getParameter(i);
				Value arg = args.getVisibleChild(i).getReturnedNode();
				
				if (arg.getReturnedNode().getDataType() != param.getDataType() ||
					arg.getReturnedNode().getTypeClass() == null || !arg.getTypeClass().isOfType(param.getTypeClass()))
				{
					compatible = false;
				}
			}
			
			if (compatible)
			{
				return converted;
			}
		}
		
		return null;
	}
	
	public NovaMethodDeclaration getExistingConvertedPrimitiveMethod(ClassDeclaration type, GenericTypeArgumentList args)
	{
		return getExistingConvertedPrimitiveMethod(getExistingConvertedPrimitiveClass(type, args));
	}
	
	public NovaMethodDeclaration getExistingConvertedPrimitiveMethod(ClassDeclaration c)
	{
		if (c != null)
		{
			MethodList.SearchFilter filter = new MethodList.SearchFilter();
			filter.checkAncestor = false;
			filter.checkInterfaces = false;
			filter.checkConstructors = true;
			filter.checkProperties = false;
			
			return (NovaMethodDeclaration)c.getMethod((GenericCompatible)null, getName(), filter, getTypes());
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#onAdded(net.fathomsoft.nova.tree.Node)
	 */
	@Override
	public void onAdded(Node parent)
	{
		initMethod = new InitializationMethod(getParent(), Location.INVALID);
		
		initMethod.createFrom(this);
		
		setName(getParentClass().getName());
		
		getParentClass().addChild(initMethod);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.NovaMethodDeclaration#validate(int)
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			initMethod.getScope().slaughterEveryLastVisibleChild();
			initMethod.getScope().inheritChildren(getScope());
			initMethod.setLocationIn(getLocationIn());
			
			String args = generateParameterOutput(this);
			
			MethodCall init = MethodCall.decodeStatement(this, "this(" + args + ")", Location.INVALID, true);
			addChild(init);
			
			SyntaxTree.validateNodes(getParameterList(), phase);
			result.returnedNode = initMethod;
			
			return result;
		}
		
		return result;
	}
	
	@Override
	public void checkDataType(String type)
	{
		
	}
	
	public void addSuperCallFor(Stack<MethodCall> constructorCalls, Constructor current)
	{
		ClassDeclaration clazz = current.getParentClass().getExtendedClassDeclaration();
		
		if (clazz == null)
		{
			return;
		}
	}
	
	private String generateParameterOutput(NovaMethodDeclaration method)
	{
		return generateParameterOutput(method, null);
	}
	
	private String generateParameterOutput(NovaMethodDeclaration method, Constructor inherited)
	{
		StringBuilder args = new StringBuilder();
		
		if (inherited == null || method.areCompatibleParameterTypes(getContext(), inherited.getParameterList().getTypes()))
		{
			for (int i = 0; i < method.getParameterList().getNumVisibleChildren(); i++)
			{
				if (i > 0)
				{
					args.append(", ");
				}
				
				args.append(method.getParameter(i).getName());
			}
		}
		
		return args.toString();
	}
	
	/**
	 * Validate that the specified statement really is a Constructor
	 * declaration.
	 * 
	 * @param data The data that will specify if there was an error.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return Whether or not the statement is a Constructor declaration.
	 */
	private boolean validateDeclaration(ExtraData data, boolean require)
	{
		if (getType() != null)
		{
			return false;
		}
		else if (data.error != null)
		{
			return SyntaxMessage.queryError(data.error, this, require);
		}
		
		setName(getParentClass().getName());
		
		return setType(getName(), false);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, net.fathomsoft.nova.util.Bounds, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		super.interactWord(word, bounds, leftDelimiter, rightDelimiter, extra);
		
		if (extra.isLastWord())
		{
			if (!word.equals(IDENTIFIER))
			{
				extra.error = "Constructor must be named \"" + IDENTIFIER + '"';
			}
		}
		else if (word.equals("static"))
		{
			extra.error = "Constructor cannot be declared as static";
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Constructor clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Constructor node = new Constructor(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Constructor cloneTo(Constructor node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Constructor} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Constructor cloneTo(Constructor node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the Constructor class type to make sure everything
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