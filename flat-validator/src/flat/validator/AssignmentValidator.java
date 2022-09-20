package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.error.UnimplementedOperationException;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.Variable;
import flat.tree.variables.VariableDeclaration;
import flat.tree.variables.VariableDeclarationList;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import java.util.ArrayList;

public class AssignmentValidator extends ValueValidator
{
	public boolean wasDeclaration;
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public AssignmentValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ArgumentListValidator assignees = new ArgumentListValidator(this, locationIn.asNew());
		
		addChild(assignees);
	}
	
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	public ArgumentListValidator getAssigneeNodes()
	{
		return (ArgumentListValidator)getChild(super.getNumDefaultChildren() + 0);
	}
	
	public int getNumAssignees()
	{
		return getAssigneeNodes().getNumVisibleChildren();
	}
	
	public boolean containsMultipleAssignees()
	{
		return getNumAssignees() > 1;
	}
	
	/**
	 * Get the node that is having its value modified. In other words,
	 * the left hand value of the equation. For instance, in the
	 * statement: "<code>Int j = 35</code>" <u><code>Int j</code></u> is
	 * the left hand value of the equation.
	 * 
	 * @return The node that represents the variable that is being
	 * 		assigned.
	 */
	public ValueValidator getAssigneeNode()
	{
		return (ValueValidator)getAssigneeNodes().getVisibleChild(0);
	}
	
	public void setAssigneeNode(ValueValidator assignee)
	{
		if (getNumAssignees() <= 0)
		{
			getAssigneeNodes().addChild(assignee);
		}
		else
		{
			getAssigneeNodes().replace(getAssigneeNode(), assignee);
		}
	}
	
	/**
	 * Get the node that is having its value modified. In other words,
	 * the left hand value of the equation. For instance, in the
	 * statement: "<code>int j = 35</code>" <u><code>int j</code></u> is
	 * the left hand value of the equation.
	 * 
	 * @return The node that represents the variable that is being
	 * 		assigned.
	 */
	public AccessibleValidator getAssignee()
	{
		return getAssigneeNode() instanceof AccessibleValidator ? (AccessibleValidator)getAssigneeNode() : null;
	}
	
	public ValueValidator getAssignedNodeValue()
	{
		AccessibleValidator last = getAssignee().getLastAccessedNode();
		
		if (last == null)
		{
			last = (AccessibleValidator)getAssigneeNode();
		}
		
		return (ValueValidator)last;
	}
	
	public Variable getAssignedNode()
	{
		return (Variable)getAssignedNodeValue();
	}
	
	@Override
	public boolean setType(String type, boolean require, boolean checkType, boolean checkDataType)
	{
		return getAssignmentNode().setType(type, require, checkType, checkDataType);
	}
	
	@Override
	public ValueValidator[] getTypes()
	{
		return getAssignmentNode().getTypes();
	}
	
	@Override
	public String getType(boolean checkCast)
	{
		return getAssignmentNode().getReturnedNode().getType(checkCast);
	}
	
	@Override
	public Type getTypeObject()
	{
		return getAssignmentNode().getReturnedNode().getTypeObject();
	}
	
	
	@Override
	public GenericTypeParameter getGenericTypeParameter(boolean checkArray)
	{
		return getAssignmentNode().getReturnedNode().getGenericTypeParameter(checkArray);
	}
	
	@Override
	public ValueValidator getReturnedNode()
	{
		return getAssignmentNode().getReturnedNode();
	}
	
	@Override
	public ValueValidator getFlatTypeValue(ValueValidator context)
	{
		return getAssignmentNode().getReturnedNode().getFlatTypeValue(getAssignmentNode().getReturnedNode());
	}
	
	@Override
	public String getTypeStringValue()
	{
		return getAssignmentNode().getTypeStringValue();
	}
	
	@Override
	public void setTypeValue(String type)
	{
		getAssignmentNode().setTypeValue(type);
	}
	
	@Override
	public int getArrayDimensions()
	{
		return getAssignmentNode().getArrayDimensions() - getArrayAccessDimensions();
	}
	
	@Override
	public void setArrayDimensions(int arrayDimensions)
	{
		getAssignmentNode().setArrayDimensions(arrayDimensions);
	}
	
	@Override
	public byte getDataType(boolean checkGeneric)
	{
		return getDataType(checkGeneric, true);
	}

	public byte getDataType(boolean checkGeneric, boolean checkCast)
	{
		return getAssignmentNode().getDataType(checkGeneric, checkCast);
	}
	
	@Override
	public void setDataType(byte type)
	{
		getAssignmentNode().setDataType(type);
	}
	
	/**
	 * Get the node that is being used to set the value of the assignee
	 * node. In other words, the right hand value of the equation. For
	 * instance, in the statement: "<code>int j = 35</code>"
	 * <u><code>35</code></u> is the right hand value of the equation.
	 * 
	 * @return The node that represents the value that the assignee
	 * 		variable is being assigned to.
	 */
	public ValueValidator getAssignmentNode()
	{
		return (ValueValidator)getChild(super.getNumDefaultChildren() + 1);
	}
	
	/**
	 * @see NodeValidator#generateFlatInput(StringBuilder, boolean)
	 */
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		return getAssigneeNode().generateFlatInput(builder, outputChildren).append(" = ").append(getAssignmentNode().generateFlatInput(outputChildren));
	}
	
	public static AssignmentValidator generateDefault(NodeValidator parent, Location location)
	{
		AssignmentValidator n = new AssignmentValidator(parent, location);
		
		
		
		return n;
	}
	
	@Override
	public NodeValidator getDecodedParent()
	{
		return getReturnedNode();
	}
	
	/**
	 * Decode the given statement into an Assignment if possible. If
	 * it is not possible, then null is returned.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>Int variableName = 45</li>
	 * 	<li>personsName = "Bob"</li>
	 * 	<li>Person myPeep = new Person(54)</li>
	 * 	<li>area = width * height / 2</li>
	 * 	<li>Int newSize = array.getSize() + 5</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to decode into an Assignment.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The new Assignment if it decodes properly. If not,
	 * 		it returns null.
	 */
	public static AssignmentValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		return decodeStatement(parent, statement, location, require, true);
	}
	
	/**
	 * Decode the given statement into an Assignment if possible. If
	 * it is not possible, then null is returned.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>Int variableName = 45</li>
	 * 	<li>personsName = "Bob"</li>
	 * 	<li>Person myPeep = new Person(54)</li>
	 * 	<li>area = width * height / 2</li>
	 * 	<li>Int newSize = array.getSize() + 5</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to decode into an Assignment.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param addDeclaration Whether or not to add the declaration to the
	 * 		nearest scope, if the left hand value of the equation is a
	 * 		variable declaration.
	 * @return The new Assignment if it decodes properly. If not,
	 * 		it returns null.
	 */
	public static AssignmentValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require, boolean addDeclaration)
	{
		return decodeStatement(parent, statement, location, require, addDeclaration, null, null);
	}
	
	/**
	 * Decode the given statement into an Assignment if possible. If
	 * it is not possible, then null is returned.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>Int variableName = 45</li>
	 * 	<li>personsName = "Bob"</li>
	 * 	<li>Person myPeep = new Person(54)</li>
	 * 	<li>area = width * height / 2</li>
	 * 	<li>Int newSize = array.getSize() + 5</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to decode into an Assignment.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param addDeclaration Whether or not to add the declaration to the
	 * 		nearest scope, if the left hand value of the equation is a
	 * 		variable declaration.
	 * @return The new Assignment if it decodes properly. If not,
	 * 		it returns null.
	 */
	public static AssignmentValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require, boolean addDeclaration, ValueValidator[] assignees, NodeValidator assignment)
	{
		return decodeStatement(parent, statement, location, require, addDeclaration, assignees, assignment, true);
	}
	
	public static AssignmentValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require, boolean addDeclaration, ValueValidator[] assignees, NodeValidator assignment, boolean checkType)
	{
		if (!SyntaxUtils.isVariableAssignment(statement))
		{
			return null;
		}
		
		AssignmentValidator n    = new AssignmentValidator(parent, location);
		
		int equalsIndex = SyntaxUtils.findCharInBaseScope(statement, '=');
		int endIndex    = StringUtils.findNextNonWhitespaceIndex(statement, equalsIndex - 1, -1) + 1;
		
		String variable = statement.substring(0, endIndex);
		
		Location varLoc = location.asNew();
		varLoc.getBounds().setEnd(varLoc.getStart() + endIndex);
		
		String[] assigneesStr = StringUtils.splitCommas(variable, 1);
		
//		if (!n.decodeAssignee(assignees[0], varLoc, require, addDeclaration))
//		{
//			return null;
//		}
		
		for (int i = 0; i < assigneesStr.length; i++)
		{
			if (!n.decodeAssignee(assigneesStr[i], varLoc, require, addDeclaration, assignees, checkType))
			{
				SyntaxMessage.queryError("Could not parse assigned value", n, require);
				
				return null;
			}
		}
		
		int rhsIndex = StringUtils.findNextNonWhitespaceIndex(statement, equalsIndex + 1);
		
		if (rhsIndex > 0)
		{
			// Right-hand side of the equation.
			String rhs = statement.substring(rhsIndex);
			
			Location newLoc = location.asNew();
			newLoc.setBounds(location.getStart() + rhsIndex, location.getStart() + statement.length());
			
			if (assignment == null)
			{
				assignment = n.decodeRightHandSide(n, rhs, newLoc, require);
			}
			
			if (assignment == null)
			{
				if (addDeclaration)
				{
					n.removeDeclaration();
				}
				
				return null;
			}
			
			n.addChild(assignment);
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public boolean onAfterDecoded()
	{
		if (super.onAfterDecoded())
		{
			getAssignmentNode().convertToPrimitiveType();
			
			if (getAssignedNodeValue() instanceof Variable)
			{
				getAssignedNode().getDeclaration().convertToPrimitiveType();
				
				if (getAssignedNode().getDeclaration() instanceof ArrayAccessorMethodValidator)
				{
					return true;
				}
			}
			
			if (getAssignedNodeValue() instanceof Variable)
			{
				getAssignedNode().getDeclaration().onAfterDecoded();
			}
			
			if (getAssignedNodeValue().getType() != null && getAssignedNodeValue().isImmutable() && getAssignedNodeValue() instanceof Variable)
			{
				if (getAssignedNode().getImmutableAnnotation() != null)
				{
					getAssignedNode().getImmutableAnnotation().convertAssignment(getAssignmentNode());
				}
			}
			
			validateCompatible();
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onAdded(NodeValidator parent)
	{
		if (wasDeclaration) {
			if (
				parent instanceof Scope == false && !(parent instanceof ArgumentListValidator && parent.parent instanceof ForLoop) ||
					getAssignmentNode() instanceof ControlStatement
			) {
				getAssignedNode().getDeclaration().setProperty("requiresPrecedingDeclaration", true);
			}
		}
		if (getAssignedNodeValue() instanceof Variable && getAssignedNode().getDeclaration() instanceof ArrayAccessorMethodValidator)
		{
			Variable assigned = getAssignedNode();
			
			SyntaxUtils.replaceArrayAccessWithSetter(assigned, getAssignmentNode());
			
			replaceWith(getAssigneeNode());
		}
		else
		{
			super.onAdded(parent);
		}
	}
	
	private boolean decodeAssignee(String assignee, Location loc, boolean require, boolean addDeclaration, ValueValidator[] assignees, boolean checkType)
	{
		if (assignees == null || assignees.length <= 0)
		{
			ValueValidator varNode = LocalDeclarationValidator.decodeStatement(this, assignee, loc, false);
			
			if (varNode == null)
			{
				varNode = SyntaxTree.decodeValue(this, assignee, loc, require, checkType);
				
				if (varNode == null)
				{
					return SyntaxMessage.queryError("Undeclared variable '" + assignee + "'", this, loc, require);
				}
			}
			
			if (varNode.getReturnedNode() instanceof VariableDeclaration)
			{
				if (addDeclaration)
				{
					varNode = addDeclaration((VariableDeclaration)varNode);
				}
				
				wasDeclaration = true;
			}
			else if (varNode.getReturnedNode() instanceof Variable)
			{
				validateAuthorization((Variable)varNode.getReturnedNode());
			}
			
			if (varNode instanceof CastValidator)
			{
				return false;
			}
			
			getAssigneeNodes().addChild(varNode);
		}
		else
		{
			for (ValueValidator assign : assignees)
			{
				getAssigneeNodes().addChild(assign);
				
				assign.onAfterDecoded();
			}
		}
		
		return true;
	}
	
	private boolean checkImplicitType(ValueValidator assigned, ValueValidator assignment, boolean require)
	{
		LocalDeclarationValidator declaration = null;
		
		if (assigned instanceof Variable)
		{
			if (((Variable)assigned).getDeclaration() instanceof LocalDeclarationValidator)
			{
				declaration = (LocalDeclarationValidator)((Variable)assigned).getDeclaration();
			}
		}
		else if (assigned instanceof LocalDeclarationValidator)
		{
			declaration = (LocalDeclarationValidator)assigned;
		}
		
		if (declaration != null)
		{
			if (declaration.isImplicit())
			{
				ValueValidator previousType = declaration.getTypeValue();

				if (declaration.getImplicitType() == null)
				{
					/*IValue value = new IValue(getParent(), getLocationIn());
					value.setType(assignment.getFlatType());*/
					
					declaration.setImplicitType(assignment);
				} else if (previousType == null || previousType.getTypeClass() == null) {
					return SyntaxMessage.queryError("Implicit type for '" + declaration.getName() + "' cannot be determined", this, require);
				} else if (previousType.getArrayDimensions() != assignment.getArrayDimensions())
				{
					return SyntaxMessage.queryError("Incompatible array assignment. Assigned node has " + declaration.getArrayDimensions() + " dimensions, when assignment has " + assignment.getArrayDimensions() + " dimensions", this, require);
				}
				else if (previousType.getTypeClass().hasCommonAncestor(assignment.getTypeClass()))
				{
					ClassDeclarationValidator base = previousType.getTypeClass().getCommonAncestor(assignment.getTypeClass());
					
//					Value value = declaration.getTypeValue();
//					value.setType(base.generateFlatType().toString());
					
					// Add the common ancestor type to the scope
					getAncestorWithScope().getScope().addImplicitVariableAssignment(declaration, base, assignment);
				}
				else
				{
					previousType.getTypeClass();
					assignment.getTypeClass();
					return SyntaxMessage.queryError("Incompatible implicit type '" + declaration.getType() + "' being assigned to type '" + assignment.getType() + "'", this, require);
				}
				
				//Value assignmentType = assignment.getFlatTypeValue(assignment);
				declaration.setType(assignment);//assignment.getFlatType());
				//declaration.setType(assignmentType.getType(), true, false, false);
				declaration.correspondingImplicit = assignment;
				
				if (assignment.getType() != null && (assignment.isPrimitive() && assignment.getType().equals("Byte") || assignment.getType().equals("Short")))
				{
					declaration.setType("Int");
				}

				declaration.onAfterDecoded();

				checkPrimitive(getAssignmentNode());

//				GenericTypeArgumentList args = assignment.getGenericTypeArgumentList();
//				
//				declaration.getGenericTypeArgumentList().slaughterEveryLastChild();
//				
//				if (args != null && args.getNumVisibleChildren() > 0)
//				{
//					for (GenericTypeArgument arg : assignment.getGenericTypeArgumentList())
//					{
//						declaration.getGenericTypeArgumentList().addChild(arg.clone(null, declaration.getLocationIn()));
//					}
//				}
//				else
//				{
//					declaration.addDefaultGenericTypeArguments();
//				}
			}
		}
		
		return true;
	}
	
	private boolean validateCompatible()
	{
		ValueValidator returnedLeft  = getAssigneeNode().getReturnedNode();
		ValueValidator returnedRight = getAssignmentNode().getReturnedNode();
		
		if (!checkImplicitType(returnedLeft, returnedRight, true))
		{
			return false;
		}
		if (returnedLeft.getTypeObject() instanceof FunctionType)
		{
			return true;
		}

		returnedLeft  = getAssigneeNode().getReturnedNode();
		returnedRight = getAssignmentNode().getReturnedNode();
		
		String leftType  = returnedLeft.getFlatType();
		String rightType = returnedRight.getFlatType();
		
		if (!Literal.isNullLiteral(returnedRight))
		{
			if (rightType != null && !rightType.equals(leftType))
			{
				ClassDeclarationValidator left  = returnedLeft.getTypeClass();
				ClassDeclarationValidator right = returnedRight.getTypeClass();
				
				if (!returnedLeft.isExternalType() && !returnedRight.isExternalType() &&
						(left == null || right == null ||
						(returnedLeft.isPrimitive() && (!left.isOfType(right) && !SyntaxUtils.arePrimitiveTypesCompatible(leftType, rightType)) ||
						!returnedLeft.isPrimitive() && !right.isOfType(left))) &&
						!(left instanceof Trait && right.getClassLocation().equals("flat/Object")))
				{
					if (right != null)
					{
						right.isOfType(left);
					}
					SyntaxUtils.arePrimitiveTypesCompatible(leftType, rightType);
					returnedLeft.getFlatType();
					returnedRight.getFlatType();
					returnedLeft.getTypeClass();
					returnedRight.getTypeClass();
					SyntaxMessage.error("Type '" + returnedRight.getType() + "' is not compatible with type '" + returnedLeft.getType() + "'", this);
				}
			}
			
			/*if (!SyntaxUtils.isTypeCompatible(returnedRight.getContext(), returnedLeft, returnedRight, true))
			{
				SyntaxUtils.isTypeCompatible(returnedRight.getContext(), returnedLeft, returnedRight, true);
			}
			
			return SyntaxUtils.isTypeCompatible(returnedRight.getContext(), returnedLeft, returnedRight, true);*/
		}
		
		return true;
	}
	
	/**
	 * @see NodeValidator#validate(int)
	 */
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (containsMultipleAssignees())
			{
				ValueValidator assignment = getAssignmentNode().getReturnedNode();
				
				if (!(assignment instanceof MethodCall))
				{
					SyntaxMessage.error("Multiple assignments to single value is not implemented yet", this, false);
					
					return result.errorOccurred(this);
				}
				
				ArrayList<GenericCompatible> contexts = new ArrayList<GenericCompatible>();
				
				for (int i = 0; i < getAssigneeNodes().getNumVisibleChildren(); i++)
				{
					NodeValidator nodeValidator = getAssigneeNodes().getVisibleChild(i);
					
					if (nodeValidator instanceof Variable)
					{
						contexts.add(((Variable) nodeValidator).getDeclaration());
					}
					else if (nodeValidator instanceof GenericCompatible)
					{
						contexts.add((GenericCompatible) nodeValidator);
					}
					else
					{
						throw new UnimplementedOperationException("type not implemented.");
					}
				}
				
				if (!SyntaxUtils.areTypesCompatible(contexts.toArray(new GenericCompatible[0]), assignment.getTypes(), getAssigneeNodes().getTypes()))
				{
					SyntaxMessage.error("Invalid types to be assigned from method call", this, false);

					return result.errorOccurred(this);
				}
			}
			
			ValueValidator returned = getAssigneeNode().getReturnedNode();
			
			if (returned instanceof Variable)
			{
				Variable var = (Variable)returned;
				
				if (var.getDeclaration() instanceof FieldDeclaration)
				{
					FieldDeclaration field   = (FieldDeclaration)var.getDeclaration();
					MutatorMethodValidator mutator = field.getMutatorMethod();
					
					if (mutator != null && getParentMethod() != mutator)
					{
						if (!mutator.isDisabled())
						{
							String text = field.getName() + "(" + getAssignmentNode().generateFlatInput() + ")";
							
							MethodCall call = MethodCall.decodeStatement(getParent(), text, getLocationIn(), true, false, mutator);
							
							call.getArgumentList().getVisibleChild(0).replaceWith(getAssignmentNode());
							
							ValueValidator replace = call;
							
							if (getAssignedNode().isAccessed())
							{
								getAssignedNode().toValue().replaceWith(call);
								
								replace = getAssignee().toValue();
							}
							
							replaceWith(replace);
							
							result.returnedNodeValidator = call;
							
							return result;
						}
						else
						{
							SyntaxMessage.error("The field '" + field.getName() + "' is not settable", this, false);
							
							getParent().removeChild(this);
							
							result.skipCycle = true;
							
							return result.errorOccurred();
						}
					}
				}
			}
			
			if (getAssigneeNode() instanceof Variable)
			{
				SyntaxUtils.checkVolatile((Variable)getAssigneeNode());
			}
		}
		
		return result;
	}
	
	@Override
	public void onChildReplaced(NodeValidator old, NodeValidator replacement)
	{
		ValueValidator assignee = getAssigneeNode();

		if (assignee instanceof Variable || assignee instanceof Literal)
		{
			Variable v = getAssignedNode();

			if (v.declaration instanceof LocalDeclarationValidator)
			{
				LocalDeclarationValidator local = (LocalDeclarationValidator)v.declaration;

				if (local.isImplicit() && !replacement.containsProperty("methodCall"))
				{
					local.implicitType = ((ValueValidator)replacement).getReturnedNode();
					local.setType(local.implicitType);
					local.onAfterDecoded();
				}
			}
		}

		super.onChildReplaced(old, replacement);
	}
	
	@Override
	public void rollback()
	{
		removeDeclaration();
		
		super.rollback();
	}
	
	/**
	 * Validate that the assignment is authorized to assign a value
	 * to the given variable in the assignments location. The variable
	 * is not authorized to be modified under the following condition:<br>
	 * <u>The variable cannot be modified if it is <b>private</b> or
	 * <b>visible</b> and is not contained within the same class as the
	 * assignment.</u>
	 * 
	 * @param var The Variable to validate.
	 */
	private void validateAuthorization(Variable var)
	{
		IdentifierValidator id = var.getLastAccessedNode();
		
		if (id instanceof Variable)
		{
			VariableDeclaration declaration = ((Variable)id).getDeclaration();
			
			if (id.isAccessed() && declaration instanceof FieldDeclaration)
			{
				FieldDeclaration field = (FieldDeclaration)declaration;
				
				if (field.getVisibility() == FieldDeclaration.VISIBLE)
				{
					ClassDeclarationValidator declaringClass = field.getParentClass();
					ClassDeclarationValidator thisClass      = getParentClass();
					
					if (declaringClass != thisClass)// && !(id instanceof ArrayAccess))
					{
						SyntaxMessage.error("The value of the field '" + field.getName() + "' cannot be modified", id);
					}
				}
			}
		}
	}
	
	/**
	 * Add the variable declaration to the parent scope if the assignment
	 * was also a declaration.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * int i = 42;
	 * 
	 * // Scenario 2
	 * i = 43;</pre></blockquote>
	 * Scenario 1 includes a declaration and therefore, this method would
	 * add that declaration to its parent scope and return a clone of the
	 * declared Variable. On the other hand, scenario 2 does not
	 * include a declaration and therefore simply does nothing in this
	 * method (Returns the given Variable).
	 * 
	 * @param var The Variable to check whether or not declares a
	 * 		variable.
	 * @return If a variable is declared, this returns a clone of the
	 * 		declared variable. If not, this simply returns the given
	 * 		Variable instance.
	 */
	private Variable addDeclaration(VariableDeclaration var)
	{
		NodeValidator scopeNodeValidator = getParent().getAncestorWithScope();
		
		NodeValidator original = scopeNodeValidator;
		
		if ((scopeNodeValidator instanceof ControlStatement || scopeNodeValidator instanceof WhileLoop) && scopeNodeValidator.isDecoding()) {
			scopeNodeValidator = scopeNodeValidator.getParent().getAncestorWithScope();
			var.setProperty("requiresPrecedingDeclaration", true);
		}

		var.setProperty("fromAssignment", true);

		scopeNodeValidator.getScope().addChild(var);
		
		Location newLoc = new Location(getLocationIn());
		Variable newVar = var.generateUsableVariable(this, newLoc);
		
		var.setLocationIn(getLocationIn());
		
		if (scopeNodeValidator != original)
		{
			((LocalDeclarationValidator)var).setScopeID(original.getScope().id);
		}
		
		return newVar;
	}
	
	private void removeDeclaration()
	{
		if (!(getAssigneeNode() instanceof IdentifierValidator))
		{
			return;
		}
		
		VariableDeclarationList list = getParent().getAncestorWithScope().getScope().getVariableList();
		
		list.removeChildWithName(((IdentifierValidator)getAssigneeNode()).getName());
	}
	
	/**
	 * Decode the right hand side of an assignment into an Node if
	 * possible. If it is not possible, then null is returned. The right
	 * hand side of an assignment must represent a value.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>45</li>
	 * 	<li>"Bob"</li>
	 * 	<li>new Person(54)</li>
	 * 	<li>width * height / 2</li>
	 * 	<li>array.getSize() + 5</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param rhs The right hand side to decode into an Assignment.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The new Node if it decodes properly. If not,
	 * 		it returns null.
	 */
	public ValueValidator decodeRightHandSide(NodeValidator parent, String rhs, Location location, boolean require)
	{
		NodeValidator child = SyntaxTree.decodeScopeContents(parent, rhs, location, require);
		
		if (child == null)
		{
			child = SyntaxTree.decodeValue(parent, rhs, location, require);
		}
		
		if (!(child instanceof ValueValidator))
		{
			SyntaxMessage.queryError("Could not decode assignment right-hand side '" + rhs + "'", parent, location, require);
			
			return null;
		}
		
		ValueValidator value = (ValueValidator)child;
		
		return checkPrimitive(value);
	}

	private ValueValidator checkPrimitive(ValueValidator value) {
		if (getAssignedNodeValue().getReturnedNode().getType() != null)
		{
			if (getAssignedNodeValue().getReturnedNode().isPrimitive())
			{
				if (!value.getReturnedNode().isPrimitive() && getParentMethod() != null && getParentMethod().getProperty("array") == null)
				{
					return SyntaxUtils.unboxPrimitive(value, value.getReturnedNode().getFlatType(value, false));
				}
			}
			else if (value.getReturnedNode().isPrimitive())
			{
				ValueValidator v = SyntaxUtils.autoboxPrimitive(value, value.getReturnedNode().getType());

				if (value.getParent().containsChild(value, false)) {
					value.replaceWith(v);
				}
			}
		}

		return value;
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public AssignmentValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		AssignmentValidator node = new AssignmentValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public AssignmentValidator cloneTo(AssignmentValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link AssignmentValidator} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public AssignmentValidator cloneTo(AssignmentValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the Assignment class type to make sure everything
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