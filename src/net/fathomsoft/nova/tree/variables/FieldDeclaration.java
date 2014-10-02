package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.Assignment;
import net.fathomsoft.nova.tree.InstanceDeclaration;
import net.fathomsoft.nova.tree.LocalDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.SyntaxTree;
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
 * @version	v0.2.34 Oct 1, 2014 at 9:51:33 PM
 */
public class FieldDeclaration extends InstanceDeclaration
{
	private String   initializationValue;
	private String[] extraDeclarations;
	
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
		
		extraDeclarations = new String[0];
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
		
		var.cloneTo(n);
		
		n.iterateWords(preStatement, data, require);
		
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

	private Bounds findExtraDeclarations(String statement)
	{
		String declarations[] = StringUtils.splitCommas(statement);
		
		if (declarations.length > 1)
		{
			extraDeclarations = new String[declarations.length - 1];
			
			System.arraycopy(declarations, 1, extraDeclarations, 0, declarations.length - 1);
			
			return new Bounds(declarations[0].length(), statement.length());
		}
		
		return Bounds.EMPTY;
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
			
			parent.addChildAfter(previous, field);
			
			previous = field;
		}
		
		super.onAdded(parent);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableDeclaration#validate(int)
	 */
	@Override
	public Node validate(int phase)
	{
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			if (initializationValue != null)
			{
				Assignment assignment = Assignment.decodeStatement(getParentClass().getAssignmentMethodNode(), getName() + " = " + initializationValue, getLocationIn(), true);
				
				getParentClass().addFieldInitialization(assignment);
			}
		}
		
		return super.validate(phase);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public FieldDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		FieldDeclaration node = new FieldDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link FieldDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FieldDeclaration cloneTo(FieldDeclaration node)
	{
		super.cloneTo(node);
		
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