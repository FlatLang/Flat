package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.MethodList.SearchFilter;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Declaration extension that represents the declaration of a field
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:12:04 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class FieldDeclaration extends InstanceDeclaration
{
	private boolean  tangible;
	
	private String   initializationValue;
	
	/**
	 * Declares that a variable can be viewed from anywhere, but not
	 * modified.
	 */
	public static final int	VISIBLE	= 4;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public FieldDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		tangible = true;
	}
	
	@Override
	public void addChild(Node node)
	{
		if (node instanceof AccessorMethod || node instanceof MutatorMethod)
		{
			getParentClass().addChild(node);
		}
		else
		{
			super.addChild(node);
		}
	}
	
	@Override
	public boolean isTangible()
	{
		return tangible && super.isTangible();
	}
	
	private boolean containsInstance(Node parent)
	{
		for (int i = 0; i < parent.getNumChildren(); i++)
		{
			Node child = parent.getChild(i);
			
			if (child instanceof Variable)
			{
				Variable var = (Variable)child;
				
				if (var.getDeclaration() == this)
				{
					return true;
				}
			}
			
			if (containsInstance(child))
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean isAccessible()
	{
		if (containsAccessorMethod() && getAccessorMethod().isDisabled())
		{
			return false;
		}
		
		return super.isAccessible();
	}
	
	@Override
	public boolean isSettable()
	{
		if (containsMutatorMethod() && getMutatorMethod().isDisabled())
		{
			return false;
		}
		
		return super.isSettable();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclaration#isVisibilityValid()
	 */
	@Override
	public boolean isVisibilityValid()
	{
		int visibility = getVisibility();
		
		return super.isVisibilityValid() || visibility == VISIBLE;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclaration#getVisibilityText()
	 */
	@Override
	public String getVisibilityText()
	{
		int visibility = getVisibility();
		
		if (visibility == VISIBLE)
		{
			return "visible";
		}
		
		return super.getVisibilityText();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.InstanceDeclaration#setAttribute(java.lang.String, int)
	 */
	@Override
	public boolean setAttribute(String attribute, int argNum)
	{
		if (super.setAttribute(attribute, argNum))
		{
			return true;
		}
		
		if (argNum == 0)
		{
			if (attribute.equals("visible"))
			{
				setVisibility(VISIBLE);
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public void setDataType(byte type)
	{
		super.setDataType(type);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		if (isStatic() && (getVisibility() == PUBLIC || getVisibility() == VISIBLE))
		{
			builder.append("extern ");
		}
		
		return generateCSource(builder);
	}
	
	/**
	 * Decode the given statement into a Field instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public static constant int uuid</li>
	 * 	<li>private Person p</li>
	 * 	<li>private String name</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Field instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Field.
	 */
	public static FieldDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		FieldDeclaration n = new FieldDeclaration(parent, location);
		
		Bounds extraDeclarations = n.findExtraDeclarations(statement);
		
		if (extraDeclarations.isValid())
		{
			statement = extraDeclarations.extractPreString(statement);
		}
		
		Bounds localDeclaration = n.findPreAssignment(statement);
		
		FieldData data = new FieldData(localDeclaration);
		
		String declaration = localDeclaration.extractString(statement);
		localDeclaration.setStart(-1);
		
		// Find the localDeclaration bounds.
		n.iterateWords(declaration, Patterns.IDENTIFIER_BOUNDARIES, data, require);
		
		if (data.localDeclaration.getStart() < 0 || data.localDeclaration.getEnd() < 0)
		{
			return null;
		}
		
		String preStatement   = statement.substring(0, data.localDeclaration.getStart());
		String localStatement = statement.substring(data.localDeclaration.getStart(), data.localDeclaration.getEnd());
		
		LocalDeclaration var  = LocalDeclaration.decodeStatement(n, localStatement, location.asNew(), require);
		
		if (var == null)
		{
			return null;
		}
		
		var.extraDeclarations = n.extraDeclarations;
		var.cloneTo(n);
		
		n.iterateWords(preStatement, data, require);
		n.setLocationIn(location);
		
		return n;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, net.fathomsoft.nova.util.Bounds, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		FieldData data = (FieldData)extra;
		
		if (!setAttribute(word, extra.getWordNumber()))
		{
			if (data.localDeclaration.getStart() == -1)
			{
				data.localDeclaration.setStart(bounds.getStart());
			}
		}
	}
	
	private Bounds findPreAssignment(String statement)
	{
		int index = SyntaxUtils.findCharInBaseScope(statement, '=');
		
		if (index >= 0)
		{
			initializationValue = statement.substring(StringUtils.findNextNonWhitespaceIndex(statement, index + 1));
			
//			Bounds bounds     = StringUtils.findNextWordBounds(statement, index - 1, -1);
//			String assignment = statement.substring(bounds.getStart());
//			
//			if (Assignment.decodeStatement(getParent(), assignment, Location.INVALID, false, false) == null)
//			{
//				SyntaxMessage.error("Incorrect field definition", this);
//			}
			
			return new Bounds(0, StringUtils.findNextNonWhitespaceIndex(statement, index - 1, -1) + 1);
		}
		
		return new Bounds(0, statement.length());
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#onAdded(Node)
	 */
	@Override
	public void onAdded(Node parent)
	{
		FieldDeclaration previous = this;
		
		for (int i = 0; i < extraDeclarations.length; i++)
		{
			String name = extraDeclarations[i];
			
			FieldDeclaration field = FieldDeclaration.decodeStatement(getParent(), generateNovaType() + " " + name, getLocationIn(), true);
			
			field.setVisibility(getVisibility());
			field.setVolatile(isVolatile());
			field.setStatic(isStatic());
			
			parent.addChildAfter(previous, field);
			
			previous = field;
		}
		
		extraDeclarations = new String[0];
		
		super.onAdded(parent);
	}
	
	public boolean containsAccessorMethod()
	{
		return getAccessorMethod() != null;
	}
	
	public AccessorMethod getAccessorMethod()
	{
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			if (getVisibleChild(i) instanceof AccessorMethod)
			{
				return (AccessorMethod)getVisibleChild(i);
			}
		}
		
		MethodDeclaration methods[] = getParentClass().getPropertyMethodList().getMethods(getName(), SearchFilter.getDefault());
		
		if (methods.length > 0)
		{
			for (MethodDeclaration method : methods)
			{
				if (method instanceof AccessorMethod)
				{
					return (AccessorMethod)method;
				}
			}
		}
		
		return null;
	}
	
	public boolean containsMutatorMethod()
	{
		return getMutatorMethod() != null;
	}
	
	public MutatorMethod getMutatorMethod()
	{
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			if (getVisibleChild(i) instanceof MutatorMethod)
			{
				return (MutatorMethod)getVisibleChild(i);
			}
		}
		
		MethodDeclaration methods[] = getParentClass().getPropertyMethodList().getMethods(getName(), SearchFilter.getDefault());
		
		if (methods.length > 0)
		{
			for (MethodDeclaration method : methods)
			{
				if (method instanceof MutatorMethod)
				{
					return (MutatorMethod)method;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableDeclaration#validate(int)
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			if (initializationValue != null)
			{
				Node parent = null;
				
				if (isStatic())
				{
					parent = getParentClass().getStaticAssignmentBlock();
				}
				else
				{
					parent = getParentClass().getAssignmentMethodNode();
				}
				
				Assignment assignment = Assignment.decodeStatement(parent, getName() + " = " + initializationValue, getLocationIn(), true);
				
				getParentClass().addFieldInitialization(assignment);
			}
		}
		else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (containsAccessorMethod())
			{
				if (!containsInstance(getAccessorMethod()))
				{
					tangible = false;
				}
			}
		}
		else if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (getParentClass().isPrimitiveType())
			{
				setVisibility(PUBLIC);
			}
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public FieldDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		FieldDeclaration node = new FieldDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public FieldDeclaration cloneTo(FieldDeclaration node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link FieldDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FieldDeclaration cloneTo(FieldDeclaration node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		node.tangible = tangible;
		node.initializationValue = initializationValue;
		
		return node;
	}
	
	/**
	 * Implementation of the ExtraData for this class.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.13 Jun 11, 2014 at 8:31:46 PM
	 * @version	v0.2.13 Jun 11, 2014 at 8:31:46 PM
	 */
	private static class FieldData extends ExtraData
	{
		private Bounds	localDeclaration;
		
		public FieldData()
		{
			this(Bounds.EMPTY.clone());
		}
		
		public FieldData(Bounds localDeclaration)
		{
			this.localDeclaration = localDeclaration;
		}
	}
	
	/**
	 * Test the FieldDeclaration class type to make sure everything
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