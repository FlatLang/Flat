package flat.tree.variables;

import flat.TestContext;
import flat.ValidationResult;
import flat.tree.*;
import flat.tree.InstanceDeclaration;
import flat.tree.MethodList.SearchFilter;
import flat.tree.annotations.Annotation;
import flat.util.*;
import flat.tree.Node;
import flat.util.Bounds;

import java.util.ArrayList;

/**
 * Declaration extension that represents the declaration of a field
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:12:04 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class FieldDeclaration extends InstanceDeclaration implements ShorthandAccessible
{
	public boolean twoWayBinding;
	
	public Object initializationValue;
	public String accessorValue;
	
	public FieldDeclaration genericOverload;
	public ArrayList<FieldDeclaration> correspondingPrimitiveOverloads;
	
	/**
	 * Declares that a variable can be viewed from anywhere, but not
	 * modified.
	 */
	public static final int	VISIBLE	= 4;
	
	/**
	 * @see Node#Node(Node, Location)
	 */
	public FieldDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		correspondingPrimitiveOverloads = new ArrayList<>();
	}
	
	@Override
	public boolean alreadyDecoded()
	{
		return accessorValue == null;
	}
	
	@Override
	public boolean isLocal()
	{
		return false;
	}
	
	@Override
	public boolean isTwoWayBinding()
	{
		return twoWayBinding;
	}
	
	@Override
	public void setTwoWayBinding(boolean twoWayBinding)
	{
		this.twoWayBinding = twoWayBinding;
	}
	
	@Override
	public FlatMethodDeclaration addDefaultAccessor()
	{
		AccessorMethod method = decodeAccessor();
		
		Return returned = Return.decodeStatement(method, "return null", getLocationIn(), true);
		
		returned.getValueNode().replaceWith(generateDefaultValue(returned, getLocationIn()));
		
		method.addChild(returned);
		
		addChild(method);
		
		return method;
	}
	
	@Override
	public FlatMethodDeclaration addDefaultMutator()
	{
		return addDefaultMutator(null);
	}
	
	public FlatMethodDeclaration addDefaultMutator(Value type)
	{
		MutatorMethod method = decodeMutator(type);
		
		addChild(method);
		method.onAfterDecoded();
		
		return method;
	}
	
	@Override
	public void addDisabledAccessor()
	{
		addChild(MutatorMethod.decodeStatement(this, "no get", getLocationIn(), true));
	}
	
	@Override
	public void addDisabledMutator()
	{
		addChild(MutatorMethod.decodeStatement(this, "no set", getLocationIn(), true));
	}
	
	public String getShorthandAccessor()
	{
		return accessorValue;
	}
	
	@Override
	public void setShorthandAccessor(String shorthand)
	{
		this.accessorValue = shorthand;
	}
	
	@Override
	public void addChild(Node node)
	{
		if (node instanceof AccessorMethod || node instanceof MutatorMethod)
		{
			getParentClass(true).addChild(node);
		}
		else
		{
			super.addChild(node);
		}
	}
	
	@Override
	public void onReplaced(Node parent, Node replacement)
	{
		super.onReplaced(parent, replacement);
		
		correspondingPrimitiveOverloads.forEach(x -> {
			x.genericOverload = (FieldDeclaration)replacement;
		});
	}
	
	public boolean isPrimitiveOverload()
	{
		return genericOverload != null;
	}
	
	@Override
	public boolean isTangible()
	{
		return isPrimitiveOverload() ? genericOverload.isTangible() : parent != null && (!containsAccessorMethod() || containsInstance(getAccessorMethod())) && super.isTangible();
	}
	
	public boolean allowsPropertyMethods()
	{
		return true;
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
	 * @see InstanceDeclaration#isVisibilityValid()
	 */
	@Override
	public boolean isVisibilityValid()
	{
		int visibility = getVisibility();
		
		return super.isVisibilityValid() || visibility == VISIBLE;
	}
	
	/**
	 * @see InstanceDeclaration#getVisibilityText()
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
	
	@Override
	public void setDataType(byte type)
	{
		super.setDataType(type);
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
		SyntaxUtils.LiteralNameData literalNameData = SyntaxUtils.getFirstLiteralNameData(statement);

		if (literalNameData != null) {
			statement = statement.replace('`' + literalNameData.literalName + '`', literalNameData.validName);
		}

		FieldDeclaration n = new FieldDeclaration(parent, location);
		n.setLiteralNameData(literalNameData);
		
		Bounds extraDeclarations = n.findExtraDeclarations(statement);
		
		if (extraDeclarations.isValid())
		{
			statement = extraDeclarations.extractPreString(statement);
		}
		
		Bounds localDeclaration = new Bounds(0, statement.length());
		String declaration = statement;
		
		int accessorIndex = n.getShorthandAccessorIndex(statement);
		
		if (accessorIndex > 0)
		{
			accessorIndex = n.twoWayBinding ? accessorIndex - 1 : accessorIndex;
			declaration = declaration.substring(0, accessorIndex).trim();
			
			localDeclaration.setEnd(declaration.length());
		}
		else
		{
			localDeclaration = n.findPreAssignment(statement);
			
			declaration = localDeclaration.extractString(statement);
		}
		
		FieldData data = new FieldData(localDeclaration);
		
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
	 * @see Node#interactWord(java.lang.String, Bounds, java.lang.String, java.lang.String, Node.ExtraData)
	 */
	@Override
	public boolean interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		FieldData data = (FieldData)extra;
		
		if (!setAttribute(word, extra.getWordNumber()))
		{
			if (data.localDeclaration.getStart() == -1)
			{
				data.localDeclaration.setStart(bounds.getStart());
			}
		}

		return true;
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
	 * @see Node#onAdded(Node)
	 */
	@Override
	public void onAdded(Node parent)
	{
		FieldDeclaration previous = this;
		
		for (int i = 0; i < extraDeclarations.length; i++)
		{
			String name = extraDeclarations[i];
			
			FieldDeclaration field = FieldDeclaration.decodeStatement(getParent(), generateFlatType() + " " + name, getLocationIn(), true);
			cloneAnnotationsTo(field);
			
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
	 * @see VariableDeclaration#validate(int)
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
			//decodeArrowBinding();
		}
		else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			MutatorMethod mutatorMethod = getMutatorMethod();

			if (mutatorMethod != null) {
				mutatorMethod.setVisibility(getVisibility());
				mutatorMethod.setStatic(isStatic());

				getAnnotations().forEach(a -> {
					mutatorMethod.addAnnotation((Annotation)a.clone(mutatorMethod, a.getLocationIn()));
				});

				mutatorMethod.setType(getTypeValue());

				if (mutatorMethod.getParameterList().getNumVisibleChildren() > 0) {
					mutatorMethod.getParameterList().getVisibleChild(0).setType(getTypeValue());
				}
			}
		}
		else if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (getParentClass().isPrimitiveType())
			{
				setVisibility(PUBLIC);
			}
			
			if (genericOverload != null && genericOverload.initializationValue instanceof Value && initializationValue == null)
			{
				genericOverload.addInitializationToCorrespondingPrimitiveOverload(this);
			}
		}
		
		return result;
	}
	
	public static class InitializationContext
	{
		public Node[] parents;
		public ClassDeclaration[] classes;
		
		public InitializationContext(Node[] parents, ClassDeclaration[] classes)
		{
			this.parents = parents;
			this.classes = classes;
		}
	}
	
	public InitializationContext getInitializationContext()
	{
		Node[] parents = new Node[] { null };
		
		ClassDeclaration[] classes = new ClassDeclaration[] { getParentClass() };
		
		if (isStatic())
		{
			parents[0] = getParentClass().getStaticAssignmentBlock();
		}
		else
		{
			if (getParentClass() instanceof Trait)
			{
				Trait i = (Trait)getParentClass();
				
				ArrayList<Node> tempParents = new ArrayList<>();
				ArrayList<ClassDeclaration> temp = new ArrayList<>();
				
				for (ClassDeclaration c : i.implementingClasses)
				{
					if (!c.containsField(getName(), false))
					{
						temp.add(c);
						tempParents.add(c.getAssignmentMethodNode());
					}
				}
				
				classes = temp.toArray(new ClassDeclaration[0]);
				parents = tempParents.toArray(new Node[0]);
			}
			else
			{
				parents[0] = getParentClass().getAssignmentMethodNode();
			}
		}
		
		return new InitializationContext(parents, classes);
	}
	
	public void decodeInitializationValue()
	{
		if (initializationValue instanceof String)
		{
			InitializationContext context = getInitializationContext();
			
			Node[] parents = context.parents;
			ClassDeclaration[] classes = context.classes;
			
			if (parents.length > 0)
			{
				if (isPrimitive() && initializationValue.equals("null")) {
					setDataType(POINTER);
				}

				Assignment assignment = Assignment.decodeStatement(parents[0], getName() + " = " + initializationValue, getLocationIn(), true);
				
				if (assignment != null)
				{
					if (getTypeClass().isPrimitiveOverload() && assignment.getAssignmentNode().getReturnedNode() instanceof Instantiation)
					{
						Instantiation instantiation = (Instantiation)assignment.getAssignmentNode().getReturnedNode();
						
						instantiation.setTypeValue(getTypeClass().getName());
						instantiation.getGenericTypeArgumentList().replaceWith(getGenericTypeArgumentList().clone(parent, getLocationIn()));
					}
					
					assignment.onAfterDecoded();
					
					for (int i = 0; i < classes.length; i++)
					{
						if (i > 0)
						{
							assignment = assignment.clone(parents[i], getLocationIn(), true, true);
						}
						
						classes[i].addFieldInitialization(assignment);
					}
					
					initializationValue = assignment.getAssignmentNode();
					
					for (FieldDeclaration f : correspondingPrimitiveOverloads)
					{
						addInitializationToCorrespondingPrimitiveOverload(f);
					}
				}
			}
		}
	}
	
	public void addInitializationToCorrespondingPrimitiveOverload(FieldDeclaration corresponding)
	{
		AssignmentMethod method = corresponding.getParentClass().getAssignmentMethodNode();
		
		Assignment value = (Assignment)((Node)initializationValue).getAncestorOfType(Assignment.class).clone(method, corresponding.getLocationIn(), true, true);
		value.getAssignedNode().declaration = corresponding;
		getParentClass().replacePrimitiveGenerics(method.getParentClass().primitiveOverloadTypes, this, value.getAssignmentNode().getReturnedNode());
		method.addChild(value);
		value.onAfterDecoded();
	}
	
	@Override
	public Node getParseContext()
	{
//		if (getParentClass().isPrimitiveOverload())
//		{
//			return (Node)getProperty("genericOverload");
//		}
		
		return this;
	}
	
	@Override
	public Value decodeAccessorValue()
	{
		if (getParentClass().isPrimitiveOverload())
		{
			BodyMethodDeclaration accessor = decodeShorthandAccessor();
			BodyMethodDeclaration reference = ((FieldDeclaration)getProperty("genericOverload")).getAccessorMethod();
			
			SyntaxUtils.parseConvertedContentsTo(reference.getScope(), reference, accessor, accessor);
			
			addChild(accessor);
			
			return ((Return)accessor.getScope().getLastChild()).getValueNode();
		}
		else
		{
			return ShorthandAccessible.super.decodeAccessorValue();
		}
	}
	
	@Override
	public void decodeMutatorValue(Value value, Value context)
	{
		if (getParentClass().isPrimitiveOverload())
		{
			BodyMethodDeclaration mutator = decodeShorthandMutator();
			BodyMethodDeclaration reference = ((FieldDeclaration)getProperty("genericOverload")).getMutatorMethod();
			
			SyntaxUtils.parseConvertedContentsTo(reference.getScope(), reference, mutator, mutator);
			
			addChild(mutator);
		}
		else
		{
			ShorthandAccessible.super.decodeMutatorValue(value, context);
		}
	}
	
	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public FieldDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		FieldDeclaration node = new FieldDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public FieldDeclaration cloneTo(FieldDeclaration node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link FieldDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FieldDeclaration cloneTo(FieldDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.initializationValue = initializationValue;
		node.accessorValue = accessorValue;
		node.twoWayBinding = twoWayBinding;
		
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
	
	@Override
	public String toString()
	{
		return generateFlatInput().toString();
	}
}