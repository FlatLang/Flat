package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
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
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
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
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#isInstance()
	 */
	@Override
	public boolean isInstance()
	{
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#generateCTypeName(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCTypeName(StringBuilder builder)
	{
		return generateCTypeClassName(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		if (isVisibilityValid())
		{
			if (getVisibility() == InstanceDeclaration.PRIVATE)
			{
				return builder;
			}
		}
		
		if (isReference())
		{
			SyntaxMessage.error("Constructor cannot return a reference", this);
		}
		
		return generateCSourcePrototype(builder).append('\n');
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		generateCSourceSignature(builder).append('\n');
		
		builder.append('{').append('\n');
		
		ClassDeclaration classDeclaration = getParentClass();
		
		if (classDeclaration.containsNonStaticData() || classDeclaration.containsVirtualMethods())
		{
			builder.append("CCLASS_NEW(").append(getTypeClass().generateCSourceName()).append(", ").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
			
			if (!classDeclaration.containsNonStaticPrivateData())
			{
				builder.append(",");
			}
			
			builder.append(");");
		}
		else
		{
			builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(" = ").append(generateCTypeCast()).append("1").append(';');
		}
		
		builder.append('\n');
		
		if (getParentClass().containsVirtualMethods())
		{
			VTable table = getParentClass().getVTableNode();
			
			builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append("->").append(VTable.IDENTIFIER).append(" = &").append(table.generateCSourceName()).append(";\n");
		}
		
		{
			Stack<AssignmentMethod> calls = new Stack<AssignmentMethod>();
			
			ClassDeclaration extended = getParentClass().getExtendedClass();
			
			while (extended != null)
			{
				calls.push(extended.getAssignmentMethodNode());
				
				extended = extended.getExtendedClass();
			}
			
			while (!calls.isEmpty())
			{
				calls.pop().generateCMethodCall(builder, true);
			}
		}
		
		// Generate super calls.
		{
			Stack<MethodCall> calls = new Stack<MethodCall>();
			
			addSuperCallFor(calls, this);
			
			while (!calls.isEmpty())
			{
				calls.pop().generateCSource(builder);
			}
		}
		
		getParentClass().getAssignmentMethodNode().generateCMethodCall(builder);
		
		builder.append('\n');
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child != getParameterList())
			{
				child.generateCSource(builder);
			}
		}
		
		builder.append('\n');
		
		builder.append("return ").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(';').append('\n');
		
		builder.append('}').append('\n');
		
		return builder;
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
		MethodDeclaration method = BodyMethodDeclaration.decodeStatement(parent, statement, location, false);
		
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
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#onAdded(net.fathomsoft.nova.tree.Node)
	 */
	@Override
	public void onAdded(Node parent)
	{
		initMethod = new InitializationMethod(getParent(), Location.INVALID);
		
		initMethod.createFrom(this);
		
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
			initMethod.getScope().inheritChildren(getScope());
			
			String args = generateParameterOutput(this);
			
			MethodCall init = MethodCall.decodeStatement(this, "this(" + args + ")", Location.INVALID, true);
			addChild(init);
			
			result.returnedNode = initMethod;
			
			return result;
		}
		
		return result;
	}
	
	private void addSuperCallFor(Stack<MethodCall> constructorCalls, Constructor current)
	{
		ClassDeclaration clazz = current.getParentClass().getExtendedClass();
		
		if (clazz == null)
		{
			return;
		}
		
		Constructor c = (Constructor)clazz.getMethod(IDENTIFIER, getParameterList().getTypes());
		
		if (c == null)
		{
			c = (Constructor)clazz.getMethod(IDENTIFIER);
			
			if (c == null)
			{
				SyntaxMessage.error("Cant", this);
			}
		}
		
		String args = generateParameterOutput(this, c);
		
		MethodCall sup = MethodCall.decodeStatement(current, "super(" + args + ")", Location.INVALID, true);
		
		constructorCalls.push(sup);
		
		addSuperCallFor(constructorCalls, c);
	}
	
	private String generateParameterOutput(NovaMethodDeclaration method)
	{
		return generateParameterOutput(method, null);
	}
	
	private String generateParameterOutput(NovaMethodDeclaration method, Constructor inherited)
	{
		StringBuilder args = new StringBuilder();
		
		if (inherited == null || method.areCompatibleParameterTypes(inherited.getParameterList().getTypes()))
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
	 * @param The data that will specify if there was an error.
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
	public Constructor clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Constructor node = new Constructor(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Constructor} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Constructor cloneTo(Constructor node)
	{
		super.cloneTo(node);
		
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