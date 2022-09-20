package flat.validator;

import flat.Flat;
import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.MethodList.SearchFilter;
import flat.tree.annotations.Annotation;
import flat.tree.annotations.OverrideAnnotation;
import flat.tree.annotations.RequireGenericTypeAnnotation;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.generics.GenericTypeParameterList;
import flat.util.Bounds;
import flat.util.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassDeclarationValidator extends InstanceDeclarationValidator
{
	public boolean abstractValue;
	
	public ClassInstanceDeclarationValidator classInstanceDeclaration;
	public ClassDeclarationValidator functionMap, propertyMap, encapsulatingClass;
	
	public ValueValidator[] primitiveOverloadTypes, originalPrimitiveOverloadTypes;
	public ClassDeclarationValidator genericOverload;
	public ArrayList<ClassDeclarationValidator> primitiveOverloads;
	
	private ExtendedClass	extendedClass;
	
	public ArrayBracketOverloadValidator arrayBracketOverloadValidator;
	
	public static final String IDENTIFIER          = "class";
	public static final String ABSTRACT_IDENTIFIER = "abstract";
	
	/**
	 * Instantiate and initialize default values for a class node.
	 * 
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public ClassDeclarationValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		primitiveOverloads = new ArrayList<>();
		
		setType("class");
		
		FieldList                         fields          = new FieldList(this, Location.INVALID);
		MethodList                        constructors    = new MethodList(this, Location.INVALID);
		MethodList                        destructors     = new MethodList(this, Location.INVALID);
		MethodList                        methods         = new MethodList(this, Location.INVALID);
		MethodList                        propertyMethods = new MethodList(this, Location.INVALID);
		MethodList                        hiddenMethods   = new MethodList(this, Location.INVALID);
		MethodList                        virtualMethods  = new MethodList(this, Location.INVALID);
		ExternalTypeList externalTypes   = new ExternalTypeList(this, Location.INVALID);
		FieldList                         externalFields  = new FieldList(this, Location.INVALID);
		TypeList<StaticBlock> staticBlocks    = new TypeList<StaticBlock>(this, Location.INVALID);
		VTableList                        vtables         = new VTableList(this, Location.INVALID);
		GenericTypeParameterList declaration     = new GenericTypeParameterList(this, Location.INVALID);
		TypeList<TraitImplementation> interfaces      = new TypeList<TraitImplementation>(this, locationIn);
		TypeList<ExternalCodeBlock>       blocks          = new TypeList<>(this, locationIn);
		
		addChild(fields, this);
		addChild(constructors, this);
		addChild(destructors, this);
		addChild(methods, this);
		addChild(propertyMethods, this);
		addChild(hiddenMethods, this);
		addChild(virtualMethods, this);
		addChild(externalTypes, this);
		addChild(externalFields, this);
		addChild(staticBlocks, this);
		addChild(vtables, this);
		addChild(declaration, this);
		addChild(interfaces, this);
		addChild(blocks, this);
	}
	
	public ClassDeclarationValidator getRootOverloadedClass()
	{
		if (genericOverload != null)
		{
			return genericOverload.getRootOverloadedClass();
		}
		
		return this;
	}
	
	@Override
	public byte getDataType()
	{
		return POINTER;
	}
	
	@Override
	public boolean isPointer()
	{
		return true;
	}
	
	public TypeList<ClassDeclarationValidator> getInnerClasses()
	{
		return getInnerClasses(true);
	}
	
	public TypeList<ClassDeclarationValidator> getInnerClasses(boolean recursive)
	{
		return addInnerClasses(recursive, new TypeList<>(this, Location.INVALID));
	}
	
	private TypeList<ClassDeclarationValidator> addInnerClasses(boolean recursive, TypeList<ClassDeclarationValidator> list)
	{
		for (ClassDeclarationValidator c : getFileDeclaration().getClassDeclarations())
		{
			if (c.encapsulatingClass == this && c.isUserMade())
			{
				list.addChild(list.getNumChildren(), c, list, false);
				
				if (recursive)
				{
					c.addInnerClasses(true, list);
				}
			}
		}
		
		return list;
	}
	
	public boolean encapsulates(ClassDeclarationValidator other)
	{
		return encapsulates(other, false);
	}
	
	public boolean encapsulates(ClassDeclarationValidator other, boolean inclusive)
	{
		return (inclusive && this == other) || other.encapsulatingClass == this || other.encapsulatingClass != null && encapsulates(other.encapsulatingClass);
	}
	
	public ClassDeclarationValidator[] getEncapsulatedClasses()
	{
		ArrayList<ClassDeclarationValidator> classes = new ArrayList<>();
		
		for (ClassDeclarationValidator c : getFileDeclaration().getClassDeclarations())
		{
			if (encapsulates(c))
			{
				classes.add(c);
			}
		}
		
		return classes.toArray(new ClassDeclarationValidator[0]);
	}
	
	@Override
	public boolean isImmutable()
	{
		return getImmutableAnnotation() != null && getImmutableAnnotation().appliedClassIsImmutable();
	}
	
	/**
	 * @see NodeValidator#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 14;
	}
	
	/**
	 * Get the FieldList instance that contains the list of fields
	 * that this class node contains.
	 * 
	 * @return The FieldList for this class node.
	 */
	public FieldList getFieldList()
	{
		return (FieldList)getChild(super.getNumDefaultChildren() + 0);
	}
	
	/**
	 * Get the MethodList instance that contains the list of
	 * constructors that this class node contains.
	 * 
	 * @return The MethodList for constructors of this class node.
	 */
	public MethodList getConstructorList()
	{
		return (MethodList)getChild(super.getNumDefaultChildren() + 1);
	}
	
	/**
	 * Get the MethodList instance that contains the list of
	 * destructors that this class node contains.
	 * 
	 * @return The MethodList for destructors of this class node.
	 */
	public MethodList getDestructorList()
	{
		return (MethodList)getChild(super.getNumDefaultChildren() + 2);
	}
	
	public Destructor getDestructor()
	{
		return getDestructorList().getNumVisibleChildren() > 0 ? (Destructor)getDestructorList().getVisibleChild(0) : null;
	}
	
	/**
	 * Get the MethodList instance that contains the list of methods
	 * that this class node contains.
	 * 
	 * @return The MethodList for this class node.
	 */
	public MethodList getMethodList()
	{
		return (MethodList)getChild(super.getNumDefaultChildren() + 3);
	}
	
	/**
	 * Get the MethodList instance that contains the list of methods
	 * that this class node contains.
	 * 
	 * @return The MethodList for this class node.
	 */
	public MethodList getPropertyMethodList()
	{
		return (MethodList)getChild(super.getNumDefaultChildren() + 4);
	}
	
	/**
	 * Get the MethodList instance that contains the list of methods
	 * that this class node contains.
	 *
	 * @return The MethodList for this class node.
	 */
	public MethodList getHiddenMethodList()
	{
		return (MethodList)getChild(super.getNumDefaultChildren() + 5);
	}
	
	/**
	 * Get the MethodList instance that contains the list of methods
	 * that this class node contains.
	 *
	 * @return The MethodList for this class node.
	 */
	public MethodList getVirtualMethodList()
	{
		return (MethodList)getChild(super.getNumDefaultChildren() + 6);
	}
	
	/**
	 * Get the ExternalTypeList instance that contains the list of
	 * external types that this class node contains.
	 * 
	 * @return The ExternalTypeList for this class node.
	 */
	public ExternalTypeList getExternalTypeListNode()
	{
		return (ExternalTypeList)getChild(super.getNumDefaultChildren() + 7);
	}
	
	/**
	 * Get the FieldList instance that contains the list of external
	 * fields that this class node contains.
	 * 
	 * @return The external FieldList for this class node.
	 */
	public FieldList getExternalFieldsListNode()
	{
		return (FieldList)getChild(super.getNumDefaultChildren() + 8);
	}
	
	/**
	 * Get the AssignmentMethod instance that contains the list of
	 * default field assignments and vtable assignment.
	 * 
	 * @return The AssignmentMethod for this class node.
	 */
	public AssignmentMethodValidator getAssignmentMethodNode()
	{
		for (MethodDeclarationValidator method : getHiddenMethodList())
		{
			if (method instanceof AssignmentMethodValidator)
			{
				return (AssignmentMethodValidator)method;
			}
		}
		
		return null;
	}
	
	public StaticBlock getStaticAssignmentBlock()
	{
		return getStaticBlockList().getVisibleChild(0);
	}
	
	/**
	 * Get the List that contains the StaticBlocks for the specified
	 * Class.
	 * 
	 * @return The StaticBlock List.
	 */
	public TypeList<StaticBlock> getStaticBlockList()
	{
		return (TypeList<StaticBlock>)getChild(super.getNumDefaultChildren() + 9);
	}
	
	/**
	 * Get the VTableNode instance that contains the list of pointers
	 * to the virtual methods of a class, if any virtual methods, or
	 * any methods of the class are overridden..
	 * 
	 * @return The VTableNode for this class node.
	 */
	public VTableList getVTableNodes()
	{
		return (VTableList)getChild(super.getNumDefaultChildren() + 10);
	}
	
	private GenericTypeParameterList getGenericTypeParameterDeclarationNode()
	{
		return (GenericTypeParameterList)getChild(super.getNumDefaultChildren() + 11);
	}
	
	public GenericTypeParameterList getGenericTypeParameterDeclaration()
	{
		return getGenericTypeParameterDeclarationNode();
	}
	
	public TypeList<TraitImplementation> getInterfacesImplementationList()
	{
		return (TypeList<TraitImplementation>)getChild(super.getNumDefaultChildren() + 12);
	}
	
	public TypeList<ExternalCodeBlock> getExternalCodeBlocks()
	{
		return (TypeList<ExternalCodeBlock>)getChild(super.getNumDefaultChildren() + 13);
	}
	
	/**
	 * Get whether or not the class contains any virtual methods. For
	 * more information on what virtual methods are, see {@link #getVirtualMethods()}
	 * 
	 * @return Whether or not the class contains any virtual methods.
	 */
	public boolean containsVirtualMethods()
	{
		return getVirtualMethods().length > 0;
	}
	
	/**
	 * Get an array containing all of the virtual methods that this class
	 * and its ancestors contain.<br><br>
	 * <b>What are virtual methods?</b><br>
	 * Virtual methods are methods that have been overridden, or override
	 * a method.
	 * 
	 * @return An array containing all of the virtual methods that the
	 * 		class and its ancestors contain.
	 */
	public FlatMethodDeclarationValidator[] getVirtualMethods()
	{
		ArrayList<FlatMethodDeclarationValidator> methods = new ArrayList<>();
		
		addInterfaceVirtualMethods(methods, this);
		addExtensionVirtualMethods(methods, this);
		
		return methods.toArray(new FlatMethodDeclarationValidator[0]);
	}
	
	/**
	 * Get an array containing all of the virtual methods that this class
	 * implements. For more information on what virtual methods are, see
	 * {@link #getVirtualMethods()}
	 * 
	 * @return An array containing all of the virtual methods that the
	 * 		class and its ancestors contain.
	 */
	public FlatMethodDeclarationValidator[] getInterfaceVirtualMethods()
	{
		return getInterfaceVirtualMethods(true);
	}
	
	public FlatMethodDeclarationValidator[] getInterfaceVirtualMethods(boolean checkAncestors)
	{
		ArrayList<FlatMethodDeclarationValidator> methods = new ArrayList<>();
		
		addInterfaceVirtualMethods(methods, this, checkAncestors);
		
		return methods.toArray(new FlatMethodDeclarationValidator[0]);
	}
	
	/**
	 * Get an array containing all of the virtual methods that this class
	 * and its extensions contain. For more information on what virtual
	 * methods are, see {@link #getVirtualMethods()}
	 * 
	 * @return An array containing all of the virtual methods that the
	 * 		class and its ancestors contain.
	 */
	public FlatMethodDeclarationValidator[] getExtensionVirtualMethods()
	{
		return getExtensionVirtualMethods(true);
	}
	
	public FlatMethodDeclarationValidator[] getExtensionVirtualMethods(boolean checkAncestors)
	{
		ArrayList<FlatMethodDeclarationValidator> methods = new ArrayList<>();
		
		addExtensionVirtualMethods(methods, this, checkAncestors);
		
		return methods.toArray(new FlatMethodDeclarationValidator[0]);
	}
	
	/**
	 * Add all of the virtual methods that the specified class implements
	 * to the given method list.
	 * 
	 * @param methods The list of methods to add the found virtual method
	 * 		data to.
	 */
	private void addInterfaceVirtualMethods(ArrayList<FlatMethodDeclarationValidator> methods, ClassDeclarationValidator context)
	{
		addInterfaceVirtualMethods(methods, context, true);
	}
	
	private void addInterfaceVirtualMethods(ArrayList<FlatMethodDeclarationValidator> methods, ClassDeclarationValidator context, boolean checkAncestors)
	{
		if (checkAncestors && getExtendedClassDeclaration() != null)
		{
			getExtendedClassDeclaration().addInterfaceVirtualMethods(methods, this);
		}
		
		if (checkAncestors)
		{
			getInterfacesImplementationList().forEachVisibleChild(x -> {
				((ValueValidator)x).getTypeClass().addInterfaceVirtualMethods(methods, context);
			});
		}
		
		addVirtualMethods(methods, getMethodList(), true, true, false);
		addVirtualMethods(methods, getPropertyMethodList(), true, true, false);
	}
	
	/**
	 * Add all of the virtual methods that the specified class and its
	 * extensions contain to the given method list.
	 * 
	 * @param methods The list of methods to add the found virtual method
	 * 		data to.
	 */
	private void addExtensionVirtualMethods(ArrayList<FlatMethodDeclarationValidator> methods, ClassDeclarationValidator context)
	{
		addExtensionVirtualMethods(methods, context, true);
	}
	
	private void addExtensionVirtualMethods(ArrayList<FlatMethodDeclarationValidator> methods, ClassDeclarationValidator context, boolean checkAncestors)
	{
		if (checkAncestors && getExtendedClassDeclaration() != null)
		{
			getExtendedClassDeclaration().addExtensionVirtualMethods(methods, this);
		}
		
		for (Trait t : getImplementedInterfaces())
		{
			if (t.getExtendedClassDeclaration() != null && t.getExtendedClassDeclaration().isRelatedTo(getExtendedClassDeclaration()))
			{
				((ClassDeclarationValidator)t).addVirtualMethods(methods, t.getMethodList(), false, false, true);
				((ClassDeclarationValidator)t).addVirtualMethods(methods, t.getPropertyMethodList(), false, false, true);
			}
		}
		
		addVirtualMethods(methods, getMethodList(), false, false, true);
		addVirtualMethods(methods, getPropertyMethodList(), false, false, true);
	}
	
	private void addVirtualMethods(ArrayList<FlatMethodDeclarationValidator> methods, MethodList list, boolean interfaceOnly, boolean implementation, boolean excludeInterfaces)
	{
		SearchFilter filter = new SearchFilter();
		filter.allowMoreParameters = false;
		filter.requireExactMatch = true;
		
		for (int i = 0; i < list.getNumVisibleChildren(); i++)
		{
			MethodDeclarationValidator m = list.getChild(i);
			
			if (!m.isExternal())
			{
				FlatMethodDeclarationValidator method = (FlatMethodDeclarationValidator)m;
				
				if (method.getParentClass() == this && method.isVirtual() && (!implementation || method instanceof BodyMethodDeclarationValidator))
				{
					/*if (interfaceOnly)
					{
						method = method.getVirtualMethod();
					}*/
					
					boolean trait = method.getRootDeclaration().getParentClass() instanceof Trait;
					
					if (!excludeInterfaces || !trait)
					{
						if (!interfaceOnly || trait)
						{
							FlatMethodDeclarationValidator existing = getMethod(method, methods, filter);
							
							if (existing != null)
							{
								int index = methods.indexOf(existing);
								
								methods.set(index, method);
							}
							else
							{
								methods.add(method);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Check to see whether or not the given methods list already contains
	 * a compatible method with the given 'method' method.
	 * 
	 * @param method The method to see if the methods list contains.
	 * @param methods The list of methods to search through.
	 * @return Whether or not the given method is contained within the
	 * 		methods list.
	 */
	private boolean containsMethod(FlatMethodDeclarationValidator method, ArrayList<FlatMethodDeclarationValidator> methods)
	{
		return getMethod(method, methods) != null;
	}
	
	private FlatMethodDeclarationValidator getMethod(FlatMethodDeclarationValidator method, ArrayList<FlatMethodDeclarationValidator> methods)
	{
		return getMethod(method, methods, null);
	}
	
	private FlatMethodDeclarationValidator getMethod(FlatMethodDeclarationValidator method, ArrayList<FlatMethodDeclarationValidator> methods, SearchFilter filter)
	{
		for (FlatMethodDeclarationValidator m : methods)
		{
			// TODO: need to make this more strict.
			if (m.getName().equals(method.getName()) && m.areCompatibleParameterTypes(new GenericCompatible[] { this }, false, filter, method.getParameterList().getTypes()))// method.areCompatibleParameterTypes(m.getParameterList().getTypes()))
			{
				return m;
			}
		}
		
		return null;
	}
	
	public boolean isAbstract()
	{
		return abstractValue;
	}
	
	/**
	 * @see ValueValidator#isExternalType()
	 */
	@Override
	public boolean isExternalType()
	{
		return false;
	}
	
	public boolean isRelatedTo(ClassDeclarationValidator node)
	{
		return node == null || getClassRelationBase(node) != null;
	}
	
	public ClassDeclarationValidator getClassRelationBase(ClassDeclarationValidator node)
	{
		if (isOfType(node))
		{
			return node;
		}
		else if (node.isOfType(this))
		{
			return this;
		}
		
		return null;
	}
	
	public boolean hasCommonAncestor(ClassDeclarationValidator node)
	{
		return getCommonAncestor(node) != null;
	}
	
	public ClassDeclarationValidator getCommonAncestor(ClassDeclarationValidator node)
	{
		if (node == null) return null;
		
		ClassDeclarationValidator current = this;
		
		while (current != null)
		{
			if (this.isOfType(current) && node.isOfType(current))
			{
				return current;
			}
			
			current = current.getExtendedClassDeclaration();
		}
		
		return null;
	}
	
	/**
	 * Get the Interfaces that this class node implements.<br>
	 * <br>
	 * For instance: "public class ClassName implements Interface1, Interface2"
	 * The "Interface1" and "Interface2" are the names of the interfaces that the
	 * "ClassName" class implements.<br>
	 * 
	 * @return The Interfaces instances that the class implements.
	 */
	public Trait[] getImplementedInterfaces()
	{
		return getImplementedInterfaces(true);
	}
	
	public Trait[] getImplementedInterfaces(boolean checkAncestors)
	{
		TypeList<TraitImplementation> list = getInterfacesImplementationList();
		
		ArrayList<Trait> array = new ArrayList<>();
		
		for (int i = 0; i < list.getNumVisibleChildren(); i++)
		{
			String type = list.getVisibleChild(i).getType();
			
			ClassDeclarationValidator clazz = SyntaxUtils.getImportedClass(getFileDeclaration(), type);
			
			if (clazz instanceof Trait)
			{
				if (clazz != this)// && !clazz.isPropertyTrue("functionMap"))
				{
					if (!array.contains(clazz)) {
						array.add((Trait) clazz);
					}
				}
			}
			else
			{
				SyntaxMessage.error("Class '" + type + "' is not a trait", this);
			}
		}
		
		if (checkAncestors)
		{
			for (int i = array.size() - 1; i >= 0; i--)
			{
				for (Trait inter : array.get(i).getImplementedInterfaces())
				{
					if (inter != this)
					{
						if (!array.contains(inter)) {
							array.add(inter);
						}
					}
				}
			}
			
			if (getExtendedClassDeclaration() != null)
			{
				for (Trait t : getExtendedClassDeclaration().getImplementedInterfaces(true))
				{
					if (t != this)
					{
						if (!array.contains(t)) {
							array.add(t);
						}
					}
				}
			}
		}
		
		return array.toArray(new Trait[0]);
	}
	
	public boolean implementsInterface(ClassDeclarationValidator clazz)
	{
		return implementsInterface(clazz, true);
	}
	
	public boolean implementsInterface(ClassDeclarationValidator clazz, boolean checkAncestor)
	{
		for (Trait i : getImplementedInterfaces(checkAncestor))
		{
			if (clazz == i)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public int getDistanceFrom(ClassDeclarationValidator node)
	{
		if (node == null)
		{
			return -1;
		}
		if (node instanceof Trait)
		{
			return getDistanceFromTrait((Trait)node);
		}
		
		int distance = 0;
		
		ClassDeclarationValidator clazz = this;
		
		if (SyntaxUtils.arePrimitiveTypesCompatible(node.getType(), getType()))
		{
			return SyntaxUtils.getPrimitiveDistance(node.getType(), getType());
		}
		
		while (clazz != null)
		{
			if (clazz == node)
			{
				return distance;
			}
			
			clazz = clazz.getExtendedClassDeclaration();
			distance++;
		}
		
		if (node.getClassLocation().equals("flat/Object"))
		{
			return distance;
		}
		
		return -1;
	}
	
	public int getDistanceFromTrait(Trait trait)
	{
		return getDistanceFromTrait(trait, new HashSet<>());
	}
	
	public int getDistanceFromTrait(Trait trait, HashSet<ClassDeclarationValidator> searched)
	{
		ClassDeclarationValidator clazz = this;
		
		if (clazz == trait)
		{
			return 0;
		}
		if (clazz.implementsInterface(trait, false))
		{
			return 1;
		}
		
		ArrayList<Integer> distances = new ArrayList<>();
		
		ClassDeclarationValidator extended = clazz.getExtendedClassDeclaration();
		
		if (extended != null && !searched.contains(extended))
		{
			searched.add(extended);
			
			distances.add(extended.getDistanceFromTrait(trait, searched));
		}
		
		for (Trait t : getImplementedInterfaces(false))
		{
			if (!searched.contains(t))
			{
				searched.add(t);
				
				distances.add(t.getDistanceFromTrait(trait, searched));
			}
		}
		
		OptionalInt max = distances.stream().mapToInt(Integer::intValue).max();
		
		if (max.isPresent())
		{
			int value = max.getAsInt();
			
			if (value >= 0)
			{
				return value + 1;
			}
		}
		
		return -1;
	}
	
	public boolean isOfType(String classLocation)
	{
		return isOfType(getProgram().getClassDeclaration(classLocation));
	}
	
	/**
	 * Check whether or not the specified ClassDeclaration extends the given
	 * ClassDeclaration.
	 * 
	 * @param node The ClassDeclaration to check if the specified Class
	 * 		extends.
	 * @return Whether or not the specified ClassDeclaration extends the given
	 * 		ClassDeclaration.
	 */
	public boolean isOfType(ClassDeclarationValidator node)
	{
		if (getDistanceFrom(node) >= 0)
		{
			return true;
		}
		else if (isPrimitiveOverload())
		{
			return genericOverload.isOfType(node);
		}
		
		return false;
	}
	
	public boolean doesOverrideMethod(FlatMethodDeclarationValidator method)
	{
		return getOverridingMethod(method) != null;
	}
	
	public boolean doesOverrideMethod(FlatMethodDeclarationValidator method, boolean checkAncestor)
	{
		return getOverridingMethod(method, checkAncestor) != null;
	}
	
	public FlatMethodDeclarationValidator getOverridingMethod(FlatMethodDeclarationValidator method)
	{
		return getOverridingMethod(method, true);
	}
	
	public FlatMethodDeclarationValidator getOverridingMethod(FlatMethodDeclarationValidator method, boolean checkAncestor)
	{
		for (FlatMethodDeclarationValidator m : getMethods())
		{
			if (m.getOverriddenMethod() == method)
			{
				return m;
			}
		}
		
		if (checkAncestor && doesExtendClass())
		{
			return getExtendedClassDeclaration().getOverridingMethod(method, checkAncestor);
		}
		
		return null;
	}
	
	public AbstractMethodDeclarationValidator[] getAbstractMethods()
	{
		return getAbstractMethods(true, true);
	}
	
	public AbstractMethodDeclarationValidator[] getAbstractMethods(boolean checkInterfaces, boolean checkAncestor)
	{
		ArrayList<AbstractMethodDeclarationValidator> methods = new ArrayList<AbstractMethodDeclarationValidator>();
		
		addAbstractMethods(methods, checkInterfaces, checkAncestor);
		
		return methods.toArray(new AbstractMethodDeclarationValidator[0]);
	}
	
	private void addAbstractMethods(ArrayList<AbstractMethodDeclarationValidator> methods, boolean checkInterfaces, boolean checkAncestor)
	{
		MethodList list = getMethodList();
		
		for (int i = 0; i < list.getNumVisibleChildren(); i++)
		{
			MethodDeclarationValidator method = (MethodDeclarationValidator)list.getChild(i);
			
			if (method instanceof AbstractMethodDeclarationValidator)
			{
				methods.add((AbstractMethodDeclarationValidator)method);
			}
		}
		
		if (checkInterfaces)
		{
			for (ClassDeclarationValidator i : getImplementedInterfaces())
			{
				i.addAbstractMethods(methods, checkInterfaces, checkAncestor);
			}
		}
		if (checkAncestor)
		{
			if (doesExtendClass())
			{
				getExtendedClassDeclaration().addAbstractMethods(methods, checkInterfaces, checkAncestor);
			}
		}
	}
	
	public BodyMethodDeclarationValidator[] getBodyMethods()
	{
		return getBodyMethods(true, true);
	}
	
	public BodyMethodDeclarationValidator[] getBodyMethods(boolean checkInterfaces, boolean checkAncestor)
	{
		ArrayList<BodyMethodDeclarationValidator> methods = new ArrayList<BodyMethodDeclarationValidator>();
		
		addBodyMethods(methods, checkInterfaces, checkAncestor);
		
		return methods.toArray(new BodyMethodDeclarationValidator[0]);
	}
	
	private void addBodyMethods(ArrayList<BodyMethodDeclarationValidator> methods, boolean checkInterfaces, boolean checkAncestor)
	{
		MethodList list = getMethodList();
		
		for (int i = 0; i < list.getNumVisibleChildren(); i++)
		{
			MethodDeclarationValidator method = (MethodDeclarationValidator)list.getChild(i);
			
			if (method instanceof BodyMethodDeclarationValidator)
			{
				methods.add((BodyMethodDeclarationValidator)method);
			}
		}
		
		if (checkInterfaces)
		{
			for (ClassDeclarationValidator i : getImplementedInterfaces())
			{
				i.addBodyMethods(methods, checkInterfaces, checkAncestor);
			}
		}
		if (checkAncestor)
		{
			if (doesExtendClass())
			{
				getExtendedClassDeclaration().addBodyMethods(methods, checkInterfaces, checkAncestor);
			}
		}
	}
	
	public FlatMethodDeclarationValidator[] getMethods()
	{
		return getMethods(true, true);
	}
	
	public FlatMethodDeclarationValidator[] getMethods(boolean checkInterfaces, boolean checkAncestor)
	{
		ArrayList<FlatMethodDeclarationValidator> methods = new ArrayList<FlatMethodDeclarationValidator>();
		
		addMethods(methods, checkInterfaces, checkAncestor);
		
		return methods.toArray(new FlatMethodDeclarationValidator[0]);
	}
	
	private void addMethods(ArrayList<FlatMethodDeclarationValidator> methods, boolean checkInterfaces, boolean checkAncestor)
	{
		MethodList list = getMethodList();
		
		for (int i = 0; i < list.getNumVisibleChildren(); i++)
		{
			MethodDeclarationValidator method = list.getChild(i);
			
			if (method instanceof FlatMethodDeclarationValidator)
			{
				methods.add((FlatMethodDeclarationValidator)method);
			}
		}
		
		if (checkInterfaces)
		{
			for (ClassDeclarationValidator i : getImplementedInterfaces())
			{
				i.addMethods(methods, checkInterfaces, checkAncestor);
			}
		}
		if (checkAncestor)
		{
			if (doesExtendClass())
			{
				getExtendedClassDeclaration().addMethods(methods, checkInterfaces, checkAncestor);
			}
		}
	}
	
	public boolean doesExtendClass()
	{
		return extendedClass != null && getExtendedClassLocation() != null;
	}
	
	/**
	 * Get the class that this class node extends.<br>
	 * <br>
	 * For instance: "public class ClassName extends SuperClass"
	 * The "SuperClass" is a name of the class that the "ClassName" class
	 * extends.<br>
	 * <br>
	 * A class node can only extend one class.
	 * 
	 * @return The ClassDeclaration instance of the class that is extended.
	 */
	public ClassDeclarationValidator getExtendedClassDeclaration()
	{
		if (extendedClass == null || getExtendedClassLocation() == null)
		{
			return null;
		}
		
		return SyntaxUtils.getImportedClass(getFileDeclaration(), getExtendedClassLocation());
	}
	
	public ExtendedClass getExtendedClass()
	{
		return extendedClass;
	}
	
	public String getExtendedClassName()
	{
		return extendedClass.getTypeClassName();
	}
	
	/**
	 * Get the location of the class that this node extends.
	 * 
	 * @return The location of the class that this node extends.
	 */
	public String getExtendedClassLocation()
	{
		return extendedClass != null ? extendedClass.getTypeClassLocation() : null;
	}
	
	/**
	 * Set the class that this class node will extend.<br>
	 * <br>
	 * For instance: "public class ClassName extends SuperClass"
	 * The "SuperClass" is a class that is extended by the "ClassName"
	 * class.<br>
	 * <br>
	 * A class node can only extend one class.
	 * 
	 * @param extendedClass The name of the class that is to be
	 * 		extended.
	 */
	public void setExtendedClass(ExtendedClass extendedClass)
	{
		this.extendedClass = extendedClass;
	}
	
	public ClassDeclarationValidator[] getClassHierarchy()
	{
		return getClassHierarchy(true);
	}
	
	public ClassDeclarationValidator[] getClassHierarchy(boolean inclusive)
	{
		return getClassHierarchy(true, false);
	}
	
	public ClassDeclarationValidator[] getClassHierarchy(boolean inclusive, boolean interfaces)
	{
		ArrayList<ClassDeclarationValidator> list = new ArrayList<>();
		
		ClassDeclarationValidator current = inclusive ? this : getExtendedClassDeclaration();
		
		if (!inclusive && interfaces)
		{
			getInterfacesImplementationList().forEachVisibleListChild(i -> list.add(i.getTypeClass()));
		}
		
		while (current != null)
		{
			list.add(current);
			
			if (interfaces)
			{
				current.getInterfacesImplementationList().forEachVisibleListChild(i -> list.add(i.getTypeClass()));
			}
			
			current = current.getExtendedClassDeclaration();
		}
		
		return list.toArray(new ClassDeclarationValidator[0]);
	}
	
	/**
	 * Get an ArrayList instance that contains all of the interfaces
	 * that are implemented by this class node.
	 * 
	 * @return An ArrayList instance that contains the interface names.
	 */
	public String[] getImplementedClassNames()
	{
		TypeList<TraitImplementation> list = getInterfacesImplementationList();
		
		String[] implementedClasses = new String[list.getNumVisibleChildren()];
		
		for (int i = 0; i < implementedClasses.length; i++)
		{
			implementedClasses[i] = list.getVisibleChild(i).getType();
		}
		
		return implementedClasses;
	}
	
	/**
	 * Get whether or not the ClassDeclaration contains an ExternalType with
	 * the specified type name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	external type FILE;
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getType("FILE")</code>" would
	 * return the ExternalType for the "<code>FILE</code>" external
	 * type.
	 * 
	 * @param typeName The name of the external type to search for.
	 * @return Whether or not the ClassDeclaration contains the Method with
	 * 		the specified name.
	 */
	public boolean containsExternalType(String typeName)
	{
		return getExternalTypeListNode().containsType(typeName) || encapsulatingClass != null && encapsulatingClass.containsExternalType(typeName);
	}
	
	/**
	 * Get the ClassDeclaration's ExternalType with the specified type.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	external type FILE;
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getType("FILE")</code>" would
	 * return the ExternalType for the "<code>FILE</code>" external
	 * type.
	 * 
	 * @param typeName The name of the external type to search for.
	 * @return The ExternalType for the external type, if it exists.
	 */
	public ExternalType getExternalType(String typeName)
	{
		return getExternalTypeListNode().getType(typeName);
	}
	
	/**
	 * Get whether or not the ClassDeclaration contains the Field with the
	 * specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	private int age;
	 * 	private String name;
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>containsField("age")</code>" would return true.
	 * 
	 * @param fieldName The name of the field to search for.
	 * @return Whether or not the ClassDeclaration contains the Field with
	 * 		the specified name.
	 */
	public boolean containsField(String fieldName)
	{
		return getField(fieldName) != null;
	}
	
	public boolean containsField(String fieldName, boolean checkAncestor)
	{
		return getField(fieldName, checkAncestor) != null;
	}
	
	public void addFieldInitialization(AssignmentValidator assignmentValidator)
	{
		FieldDeclaration field = (FieldDeclaration) assignmentValidator.getAssignedNode().getDeclaration();
		
		if (field.isStatic())
		{
			getStaticAssignmentBlock().addChild(assignmentValidator);
		}
		else
		{
			getAssignmentMethodNode().addChild(assignmentValidator);
		}
	}
	
	/**
	 * Get the ClassDeclaration's Field with the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	private int age;
	 * 	private String name;
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getField("age")</code>" would return the
	 * Field for the "<code>age</code>" int field.
	 * 
	 * @param fieldName The name of the field to search for.
	 * @return The Field for the field, if it exists.
	 */
	public FieldDeclaration getField(String fieldName)
	{
		return getField(fieldName, true);
	}
	
	/**
	 * Get the ClassDeclaration's Field with the specified name.
	 * 
	 * @param fieldName The name of the field to search for.
	 * @param checkAncestor Whether or not to check the ancestor class
	 * 		if the field is not found.
	 * @return
	 */
	public FieldDeclaration getField(String fieldName, boolean checkAncestor)
	{
		FieldDeclaration field = getFieldList().getField(fieldName);
		
		if (field == null && checkAncestor)
		{
			field = getExternalFieldsListNode().getField(fieldName);
		
			if (field == null && checkAncestor && getExtendedClassDeclaration() != null)
			{
				field = getExtendedClassDeclaration().getField(fieldName);
				
				if (field != null)
				{
					ClassDeclarationValidator c = field.getTypeClass();
					
					if (c != null && c.isPrimitiveOverload())
					{
						getFileDeclaration().addImport(c.getClassLocation());
					}
				}
			}
			
			if (field == null)
			{
				for (Trait i : getImplementedInterfaces(true))
				{
					field = i.getField(fieldName, false);
					
					if (field != null)
					{
						return field;
					}
				}
			}
		}
		
		return field;
	}
	
	public void removeNonConcreteProperties()
	{
		Consumer<NodeValidator> remove = x -> {
			if (x instanceof VariableDeclaration && !((VariableDeclaration)x).isTangible())// && x instanceof ClassInstanceDeclaration == false)
			{
				x.detach(x.parent, false, true);
			}
		};
		
		getFieldList().getPublicFieldList().forEachChild(remove);
		getFieldList().getPrivateFieldList().forEachChild(remove);
		getFieldList().getPublicStaticFieldList().forEachChild(remove);
		getFieldList().getPrivateStaticFieldList().forEachChild(remove);
	}
	
	/**
	 * Get the method with the given methodName and parameterTypes. If a
	 * method with the specified name and parameter types does not exist,
	 * null is returned.
	 * 
	 * @param methodName The name of the method to search for.
	 * @param parameterTypes An array of types that the parameters of the
	 * 		searching method must be compatible with.
	 * @return The compatible method with the given method name.
	 */
	public MethodDeclarationValidator getMethod(GenericCompatible context, String methodName, ValueValidator... parameterTypes)
	{
		return getMethod(new GenericCompatible[] { context }, methodName, parameterTypes);
	}
	
	public MethodDeclarationValidator getMethod(GenericCompatible context, String methodName, MethodCallArgumentListValidator arguments)
	{
		return getMethod(context, methodName, SearchFilter.getDefault(), arguments);
	}
		
	public MethodDeclarationValidator getMethod(GenericCompatible context, String methodName, SearchFilter filter, MethodCallArgumentListValidator arguments)
	{
		if (!arguments.containsNamedArguments())
		{
			return getMethod(context, methodName, filter, arguments.getTypes());
		}
		
		MethodDeclarationValidator methods[] = getMethods(methodName, filter);
			
		ArrayList<MethodDeclarationValidator> compatible = checkCompatible(methods, arguments);
		
		if (compatible.size() > 1)
		{
			for (int i = compatible.size() - 1; i >= 0; i--)
			{
				final int index = i;
				
				if (compatible.stream().anyMatch(x -> x instanceof FlatMethodDeclarationValidator && ((FlatMethodDeclarationValidator)x).getOverriddenMethod() == compatible.get(index)))
				{
					compatible.remove(index);
				}
			}
			
			if (compatible.size() > 1)
			{
				boolean searching = true;
				
				while (searching)
				{
					searching = false;
					
					outer:
					for (int i = compatible.size() - 1; i >= 0; i--)
					{
						for (int j = compatible.size() - 1; j >= 0; j--)
						{
							MethodDeclarationValidator method1 = compatible.get(i);
							MethodDeclarationValidator method2 = compatible.get(j);
							
							if (method1.getDeclaringClass() != method2.getDeclaringClass())
							{
								if (method1.getDeclaringClass().isOfType(method2.getDeclaringClass()))
								{
									compatible.remove(j);
								}
								else
								{
									compatible.remove(i);
								}
								
								searching = true;
								
								break outer;
							}
						}
					}
				}
				
				if (compatible.size() > 1) {
					if (context instanceof AccessibleValidator) {
						AccessibleValidator a = (AccessibleValidator) context;

						if (!a.isAccessedWithinStaticContext()) {
							java.util.List<MethodDeclarationValidator> toRemove = compatible.stream()
								.filter(InstanceDeclarationValidator::isStatic)
								.collect(Collectors.toList());

							for (MethodDeclarationValidator methodDeclaration : toRemove) {
								compatible.remove(methodDeclaration);
							}
						}
					}
				}

				if (compatible.size() > 1)
				{
					checkCompatible(methods, arguments);
					SyntaxMessage.error("Ambiguous method call to '" + methodName + "'", this);//(Node)context);
					
					return null;
				}
			}
		}
		
		if (compatible.size() == 1)
		{
			return compatible.get(0);
		}
		else
		{
			return null;
		}
	}
	
	public ArrayList<MethodDeclarationValidator> checkCompatible(MethodDeclarationValidator[] methods, MethodCallArgumentListValidator arguments)
	{
		ArrayList<MethodDeclarationValidator> compatible = new ArrayList<>();
		
		for (MethodDeclarationValidator method : methods)
		{
			FlatMethodDeclarationValidator flatMethod = (FlatMethodDeclarationValidator)method;

			boolean valid = true;

			for (int i = 0; i < arguments.getNumVisibleChildren(); i++)
			{
				if (arguments.getArgumentName(i) != null && flatMethod.getParameter(arguments.getArgumentName(i)) == null)
				{
					valid = false;
					
					break;
				}
			}
			
			if (valid)
			{
				ValueValidator[] values = arguments.getArgumentsInOrder(flatMethod);

				if (values.length > flatMethod.getParameterList().getNumParameters()) {
					valid = false;
				} else {
					int count = Math.max(values.length, flatMethod.getParameterList().getNumParameters());

					for (int i = 0; i < count; i++) {
						if (i >= values.length) {
							if (!flatMethod.getParameterList().getParameter(i).isOptional()) {
								valid = false;

								break;
							}
						} else if (!SyntaxUtils.isTypeCompatible(null, flatMethod.getParameter(i), values[i].getReturnedNode())) {
							valid = false;

							break;
						}
					}
				}
			}
			
			if (valid)
			{
				compatible.add(flatMethod);
			}
		}
		
		return compatible;
	}
	
	public ArrayAccessorMethodValidator getArrayAccessorMethod()
	{
		ArrayList<MethodDeclarationValidator> methods = getArrayBracketOverload().getParentClass().getMethodList().filterVisibleListChildren(x -> x instanceof ArrayAccessorMethodValidator);
		
		return methods.size() > 0 ? (ArrayAccessorMethodValidator)methods.get(0) : null;
	}
	
	public ArrayMutatorMethodValidator getArrayMutatorMethod()
	{
		ArrayList<MethodDeclarationValidator> methods = getArrayBracketOverload().getParentClass().getMethodList().filterVisibleListChildren(x -> x instanceof ArrayMutatorMethodValidator);
		
		return methods.size() > 0 ? (ArrayMutatorMethodValidator)methods.get(0) : null;
	}
	
	public boolean containsArrayBracketOverload()
	{
		return getArrayBracketOverload() != null;
	}
	
	public ArrayBracketOverloadValidator getArrayBracketOverload()
	{
		if (arrayBracketOverloadValidator != null)
		{
			return arrayBracketOverloadValidator;
		}
		
		return doesExtendClass() ? getExtendedClassDeclaration().getArrayBracketOverload() : null;
	}
	
	public ValueValidator getValueFromPrimitiveOverloadParameter(int index)
	{
		int position = 0;
		int aligned = 0;
		
		GenericTypeParameterList params = genericOverload.getGenericTypeParameterDeclaration();
		
		for (int current = 0; current < params.getNumParameters(); current++)
		{
			if (current == index)
			{
				return primitiveOverloadTypes[position];
			}
			
			if (getGenericTypeParameterDeclaration().getNumParameters() >= aligned && getGenericTypeParameter(aligned).getName().equals(params.getParameter(current).getName()))
			{
				aligned++;
			}
			else
			{
				position++;
			}
		}
		
		return null;
	}
	
	public MethodDeclarationValidator getMethod(GenericCompatible[] contexts, String methodName, ValueValidator... parameterTypes)
	{
		return getMethod(contexts, methodName, SearchFilter.getDefault(), parameterTypes);
	}
	
	/**
	 * Get the method with the given methodName and parameterTypes. If a
	 * method with the specified name and parameter types does not exist,
	 * null is returned.
	 * 
	 * @param methodName The name of the method to search for.
	 * @param parameterTypes An array of types that the parameters of the
	 * 		searching method must be compatible with.
	 * @return The compatible method with the given method name.
	 */
	public MethodDeclarationValidator getMethod(GenericCompatible[] contexts, String methodName, SearchFilter filter, ValueValidator[] parameterTypes)
	{
		return getMethod(contexts, methodName, filter, parameterTypes, false);
	}
	
	public MethodDeclarationValidator getMethod(GenericCompatible context, String methodName, SearchFilter filter, ValueValidator[] parameterTypes, boolean reverse)
	{
		return getMethod(new GenericCompatible[] { context }, methodName, filter, parameterTypes, reverse);
	}
	
	public MethodDeclarationValidator getMethod(GenericCompatible context, String methodName, SearchFilter filter, ValueValidator[] parameterTypes)
	{
		return getMethod(new GenericCompatible[] { context }, methodName, filter, parameterTypes, false);
	}
	
	public MethodDeclarationValidator getMethod(GenericCompatible[] contexts, String methodName, SearchFilter filter, ValueValidator[] parameterTypes, boolean reverse)
	{
		return getMethod(contexts, methodName, filter, parameterTypes, reverse, true);
	}
	
	public MethodDeclarationValidator getMethod(GenericCompatible[] contexts, String methodName, SearchFilter filter, ValueValidator[] parameterTypes, boolean reverse, boolean filterOverrides)
	{
		ArrayList<MethodDeclarationValidator> methods = new ArrayList<>(Arrays.asList(getMethods(contexts, methodName, filter, parameterTypes, reverse)));
		
		if (filterOverrides)
		{
			methods = ClassDeclarationValidator.filterOverrides(methods);
		}
		
		if (methods.size() == 1)
		{
			return methods.get(0);
		}
		
		return null;
	}
	
	public static ArrayList<MethodDeclarationValidator> filterOverrides(ArrayList<MethodDeclarationValidator> methods)
	{
		methods = filterPrimitiveOverloads(methods);
		
		ArrayList<MethodDeclarationValidator> nonOverride = new ArrayList<>();
		
		for (MethodDeclarationValidator m : methods)
		{
			if (m instanceof FlatMethodDeclarationValidator)
			{
				FlatMethodDeclarationValidator method = (FlatMethodDeclarationValidator)m;
				
				if (!methods.stream().anyMatch(x -> {
					if (x instanceof FlatMethodDeclarationValidator)
					{
						return ((FlatMethodDeclarationValidator)x).overrides(method);
					}
					
					return false;
				}))
				{
					nonOverride.add(method);
				}
			}
			else
			{
				nonOverride.add(m);
			}
		}
		
		return nonOverride;
	}
	
	public static ArrayList<MethodDeclarationValidator> filterPrimitiveOverloads(ArrayList<MethodDeclarationValidator> methods)
	{
		ArrayList<MethodDeclarationValidator> nonOverload = new ArrayList<>();
		
		for (MethodDeclarationValidator m : methods)
		{
			if (m instanceof FlatMethodDeclarationValidator)
			{
				FlatMethodDeclarationValidator method = (FlatMethodDeclarationValidator)m;
				
				if (!method.isPrimitiveOverload())
				{
					nonOverload.add(method);
				}
			}
			else
			{
				nonOverload.add(m);
			}
		}
		
		if (nonOverload.size() > 0)
		{
			methods = nonOverload;
		}
		
		return methods;
	}
	
	public MethodDeclarationValidator[] getMethods(GenericCompatible[] contexts, String methodName, SearchFilter filter, ValueValidator[] parameterTypes, boolean reverse)
	{
		MethodDeclarationValidator methods[] = getMethods(methodName, parameterTypes.length, filter);
		
		ArrayList<MethodDeclarationValidator> compatible = new ArrayList<>();
		
		for (MethodDeclarationValidator method : methods)
		{
			if (method.areCompatibleParameterTypes(contexts, false, filter, parameterTypes, reverse))// && SyntaxUtils.isTypeCompatible(getProgram(), method.getType(), returnType))
			{
				compatible.add(method);
			}
		}
		
		compatible = filterPrimitiveOverloads(compatible);
		
		int max = -1;
		int maxI = -1;
		SyntaxUtils.ValueDistance distance = new SyntaxUtils.ValueDistance(Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		ArrayList<MethodDeclarationValidator> list = new ArrayList<>();
		
		for (int i = 0; i < compatible.size(); i++)
		{
			MethodDeclarationValidator method = compatible.get(i);
			
			int count = method.getParameterList().getNumRequiredParameters();
			
			if (count > max)
			{
				max = method.getParameterList().getNumRequiredParameters();
				maxI = i;
				distance = SyntaxUtils.getParametersDistance(method.getParameterList().getTypes(), parameterTypes);
				
				list = new ArrayList<>();
				list.add(method);
			}
			else if (count == max)
			{
				SyntaxUtils.ValueDistance dist = SyntaxUtils.getParametersDistance(method.getParameterList().getTypes(), parameterTypes);
				
				if (((dist.a < distance.a || dist.a == distance.a && dist.b < distance.b) && dist.genericDistance == distance.genericDistance || dist.genericDistance < distance.genericDistance) ||
					(dist.a == distance.a && dist.b == distance.b && dist.genericDistance == distance.genericDistance && dist.exactTypeDistance < distance.exactTypeDistance))
				{
					maxI = i;
					distance = dist;
					
					list = new ArrayList<>();
					list.add(method);
				}
				else if (dist.a == distance.a && dist.b == distance.b && dist.genericDistance == distance.genericDistance)
				{
					boolean valid = true;
					
					for (MethodDeclarationValidator m : list)
					{
						if (!(m instanceof FlatMethodDeclarationValidator && method instanceof FlatMethodDeclarationValidator &&
							((FlatMethodDeclarationValidator)m).containsOverridingMethod((FlatMethodDeclarationValidator)method)))
						{
							valid = false;
						}
					}

					if (valid)
					{
						list.add(method);
					}
				}
			}
		}
		
		if (maxI >= 0)
		{
			return list.toArray(new MethodDeclarationValidator[0]);
		}
		
		return new MethodDeclarationValidator[0];
	}
	
	/**
	 * Get all of the methods that have the given methodName and the given
	 * number of parameters.
	 * 
	 * @param methodName The name of the methods to search for.
	 * @param numParams The number of parameters that the methods can
	 * 		have.
	 * @return An array of methods that are compatible with the given
	 * 		data.
	 */
	public MethodDeclarationValidator[] getMethods(String methodName, int numParams)
	{
		return getMethods(methodName, numParams, SearchFilter.getDefault());
	}
	
	/**
	 * Get all of the methods that have the given methodName and the given
	 * number of parameters.
	 * 
	 * @param methodName The name of the methods to search for.
	 * @param numParams The number of parameters that the methods can
	 * 		have.
	 * @return An array of methods that are compatible with the given
	 * 		data.
	 */
	public MethodDeclarationValidator[] getMethods(String methodName, int numParams, SearchFilter filter) {
		MethodDeclarationValidator[] methods = getMethods(methodName, filter);

		return Arrays.stream(methods)
			.filter(method -> !filter.requireEqualParameterCount || numParams == method.getParameterList().getNumParameters())
			.filter(method ->
				(numParams >= method.getParameterList().getNumRequiredParameters() && numParams <= method.getParameterList().getNumVisibleChildren()) ||
				(filter.allowMoreParameters && numParams > method.getParameterList().getNumRequiredParameters())
			)
			.toArray(MethodDeclarationValidator[]::new);
	}
	
	/**
	 * Get whether or not the ClassDeclaration contains the Method with the
	 * specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	public void doSomething()
	 * 	{
	 * 		...
	 * 	}
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>containsMethod("doSomething")</code>" would
	 * return true.
	 * 
	 * @param methodName The name of the method to search for.
	 * @return Whether or not the ClassDeclaration contains the Method with
	 * 		the specified name.
	 */
	public boolean containsMethod(String methodName)
	{
		return getMethods(methodName).length > 0;
	}
	
	/**
	 * Get the ClassDeclaration's Method with the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	public void doSomething()
	 * 	{
	 * 		...
	 * 	}
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getMethod("doSomething")</code>" would
	 * return the Method for the "<code>doSomething</code>" method.
	 * 
	 * @param methodName The name of the method to search for.
	 * @return The Method for the method, if it exists.
	 */
	public MethodDeclarationValidator[] getMethods(String methodName)
	{
		return getMethods(methodName, SearchFilter.getDefault());
	}
	
	/**
	 * Get the ClassDeclaration's Method with the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	public void doSomething()
	 * 	{
	 * 		...
	 * 	}
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getMethod("doSomething")</code>" would
	 * return the Method for the "<code>doSomething</code>" method.
	 * 
	 * @param methodName The name of the method to search for.
	 * @return The Method for the method, if it exists.
	 */
	public MethodDeclarationValidator[] getMethods(String methodName, SearchFilter filter)
	{
		if (methodName == null)
		{
			return new MethodDeclarationValidator[0];
		}
		
		if (filter.className == null) filter.className = getName();
		
		ArrayList<MethodDeclarationValidator> output = new ArrayList<>();
		
		if (methodName.equals(InitializationMethodValidator.SUPER_IDENTIFIER))
		{
			if (filter.checkAncestor && getExtendedClassDeclaration() != null)
			{
				addMethods(output, getExtendedClassDeclaration().getMethods(InitializationMethodValidator.IDENTIFIER));
			}
			
			return output.toArray(new MethodDeclarationValidator[0]);
		}
		
		addMethods(output, getMethodList().getMethods(methodName, filter));
		addMethods(output, getVirtualMethodList().getMethods(methodName, filter));
		
		if (filter.checkProperties)
		{
			addMethods(output, getPropertyMethodList().getMethods(methodName, filter));
		}
		
		if (filter.checkConstructors && methodName.equals(filter.className))
		{
			addMethods(output, getConstructorList().getMethods(/*Constructor.IDENTIFIER*/methodName, filter));
		}
		else
		{
			addMethods(output, getConstructorList().getMethods(methodName, filter));
		}
		
		if (filter.checkAncestor && getExtendedClassDeclaration() != null)
		{
			/*if (methodName.equals(getName()) && getConstructorList().getNumVisibleChildren() == 0)
			{
				methodName = getExtendedClassName();
			}*/
			
			addMethods(output, getExtendedClassDeclaration().getMethods(methodName, filter));
		}
		
		if (filter.checkInterfaces)
		{
			boolean before = filter.checkAncestor;
			filter.checkAncestor = false;
			
			for (Trait inter : getImplementedInterfaces(false))
			{
				addMethods(output, inter.getMethods(methodName, filter));
			}
			
			filter.checkAncestor = before;
		}
		
		return output.toArray(new MethodDeclarationValidator[0]);
	}
	
	/**
	 * Add the methods in the src array to the given dst array list.
	 * 
	 * @param dst The array list to add the given src methods to.
	 * @param src The array containing the methods to add to the list.
	 */
	private void addMethods(ArrayList<MethodDeclarationValidator> dst, MethodDeclarationValidator src[])
	{
		for (MethodDeclarationValidator method : src)
		{
			dst.add(method);
		}
	}
	
	/**
	 * Get the Declaration child of the specified ClassDeclaration that has
	 * the given name. The Declaration can either be a field or
	 * method.
	 * 
	 * @param name The name of the declaration node to search for.
	 * @return The Declaration instance that was found, if any.
	 */
	public InstanceDeclarationValidator getDeclaration(String name)
	{
		FieldDeclaration field = getField(name);
		
		if (field != null)
		{
			return field;
		}
		
		MethodDeclarationValidator methods[] = getMethods(name);
		
		if (methods.length > 0)
		{
			return methods[0];
		}
		
		return null;
	}
	
	@Override
	public ClassDeclarationValidator getDeclaringClass()
	{
		return this;
	}
	
	public String getClassLocation()
	{
		return getClassLocation(true);
	}
	
	public String getClassLocation(boolean checkExternal)
	{
		Package p = getFileDeclaration().getPackage();
		
		String extension = "";
		String name = getName();
		
		ClassDeclarationValidator fileClass = getFileDeclaration().getClassDeclaration();
		
		if (fileClass != null && fileClass != this)
		{
			name = fileClass.getName() + "." + name;
		}
		if (checkExternal && getFileDeclaration().isExternalFile())
		{
			extension += "." + getFileDeclaration().getExternalExtension();
		}

		if (p.isDefaultPackage()) {
			return name + extension;
		}
		
		return p.getLocation() + "/" + name + extension;
	}
	
	public Package getPackage()
	{
		return getFileDeclaration().getPackage();
	}
	
	/**
	 * @see InstanceDeclarationValidator#setAttribute(String)
	 */
	@Override
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	/**
	 * @see InstanceDeclarationValidator#setAttribute(String, int)
	 */
	@Override
	public boolean setAttribute(String attribute, int argNum)
	{
		if (super.setAttribute(attribute, argNum))
		{
			return true;
		}
		
		if (attribute.equals(IDENTIFIER))
		{
			return false;
		}
		else if (attribute.equals(ABSTRACT_IDENTIFIER))
		{
			abstractValue = true;
			
			return true;
		}
		else
		{
			return false;
		}
		
//		return true;
	}

	/**
	 * @see NodeValidator#addChild(NodeValidator)
	 */
	@Override
	public void addChild(NodeValidator child)
	{
		if (child instanceof MethodDeclarationValidator)
		{
			if (child instanceof Constructor)
			{
				getConstructorList().addChild(child);
			}
			else if (child instanceof Destructor)
			{
				getDestructorList().addChild(child);
			}
			else if (child instanceof AssignmentMethodValidator)
			{
				getHiddenMethodList().addChild(child);
			}
			else if (child instanceof VirtualMethodDeclarationValidator)
			{
				getVirtualMethodList().addChild(child);
			}
			else if (child instanceof PropertyMethodValidator)
			{
				PropertyMethodValidator propertyMethod = (PropertyMethodValidator)child;
				
				for (ClassDeclarationValidator c : primitiveOverloads)
				{
					propertyMethod.convertToClass(c, c.primitiveOverloadTypes, propertyMethod.getMethodGenericTypeParameterDeclaration().getTypes());
				}
				
				getPropertyMethodList().addChild(child);
			}
//			else if (child instanceof ClosureVariable)
//			{
//				getFieldList().addChild(child);
//			}
			else
			{
				getMethodList().addChild(child);
			}
		}
		else if (child instanceof ExternalType)
		{
			getExternalTypeListNode().addChild(child);
		}
		else if (child instanceof ExternalCodeBlock)
		{
			getExternalCodeBlocks().addChild(child);
		}
		else if (child instanceof FieldDeclaration)
		{
			FieldDeclaration field = (FieldDeclaration)child;
			
			if (field.isExternal())
			{
				getExternalFieldsListNode().addChild(field);
			}
			else
			{
				getFieldList().addChild(field);
			}
		}
		else if (child instanceof StaticBlock)
		{
			getStaticBlockList().addChild(child);
		}
		else if (child instanceof GenericTypeParameterList)
		{
			super.addChild(child);
		}
		else if (child instanceof Annotation)
		{
			
		}
		else if (child instanceof ArrayBracketOverloadValidator)
		{
			arrayBracketOverloadValidator = (ArrayBracketOverloadValidator)child;
			arrayBracketOverloadValidator.setTemporaryParent(this);
		}
		else if (child instanceof ClassDeclarationValidator)
		{
			getFileDeclaration().addChild(child);
			
			((ClassDeclarationValidator)child).encapsulatingClass = this;
			
			if (getProgram().getPhase() > SyntaxTree.PHASE_CLASS_DECLARATION) // if added as inner class, re-run class declaration validation
			{
				child.validate(SyntaxTree.PHASE_CLASS_DECLARATION);
			}
		}
		else
		{
			SyntaxMessage.error("Unexpected node type of '" + child.getClass().getSimpleName() + "' within class " + getName(), child);
		}
	}
	
	public ClosureVariable[] getPublicClosureVariables()
	{
		ArrayList<ClosureVariable> vars = new ArrayList<>();
		
//		for (MethodDeclaration m : getMethodList())
//		{
//			if (m instanceof ClosureVariable && m.getVisibility() != InstanceDeclaration.PRIVATE)
//			{
//				vars.add((ClosureVariable)m);
//			}
//		}
		
		return vars.toArray(new ClosureVariable[0]);
	}
	
	public ClosureVariable[] getPrivateClosureVariables()
	{
		ArrayList<ClosureVariable> vars = new ArrayList<>();
		
//		for (MethodDeclaration m : getMethodList())
//		{
//			if (m instanceof ClosureVariable && m.getVisibility() == InstanceDeclaration.PRIVATE)
//			{
//				vars.add((ClosureVariable)m);
//			}
//		}
		
		return vars.toArray(new ClosureVariable[0]);
	}
	
	public ClosureVariable getClosureVariable(String name)
	{
//		for (MethodDeclaration m : getMethodList())
//		{
//			if (m instanceof ClosureVariable && m.getName().equals(name))
//			{
//				return (ClosureVariable)m;
//			}
//		}
		
		return null;
	}
	
	/**
	 * @see ValueValidator#setType(String)
	 */
	@Override
	public boolean setType(String type)
	{
		return setType(type, true, false);
	}
	
	/**
	 * Get whether or not the class contains any private fields.
	 * 
	 * @return Whether or not the class contains private data.
	 */
	public boolean containsPrivateData(boolean checkAncestor)
	{
		return containsStaticPrivateData(checkAncestor) || containsNonStaticPrivateData(checkAncestor);
	}
	
	/**
	 * Get whether or not the class contains any fields.
	 * 
	 * @return Whether or not the class contains data.
	 */
	public boolean containsData(boolean checkAncestor)
	{
		return containsNonStaticData(checkAncestor) || containsPrivateData(checkAncestor);
	}
	
	/**
	 * Get whether or not the class contains any private static fields.
	 * 
	 * @return Whether or not the class contains private static data.
	 */
	public boolean containsStaticPrivateData()
	{
		return containsStaticPrivateData(true);
	}
	
	/**
	 * Get whether or not the class contains any private static fields.
	 * 
	 * @param checkAncestor Whether or not to check the ancestor class
	 * 		if the data is not found.
	 * @return Whether or not the class contains private static data.
	 */
	public boolean containsStaticPrivateData(boolean checkAncestor)
	{
		StaticFieldList staticPrivateFields = getFieldList().getPrivateStaticFieldList();
		
		if (staticPrivateFields.getNumChildren() > 0)
		{
			return true;
		}
		
		if (checkAncestor && getExtendedClassLocation() != null)
		{
			return getExtendedClassDeclaration().containsStaticPrivateData();
		}
		
		return false;
	}
	
//	/**
//	 * Get whether or not the class contains any fields.
//	 * 
//	 * @return Whether or not the class contains data.
//	 */
//	public boolean containsStaticData()
//	{
//		PublicFieldList staticFields = getFieldList().getPublicStaticFieldList();
//		
//		return staticFields.getNumChildren() > 0 || containsStaticPrivateData();
//	}

	@Override
	public boolean isUserMade(boolean checkAncestor)
	{
		return !isPrimitiveOverload() && super.isUserMade(checkAncestor);
	}
	
	/**
	 * Get whether or not the class contains any private non-static
	 * fields.
	 * 
	 * @return Whether or not the class contains private non-static data.
	 */
	public boolean containsNonStaticPrivateData()
	{
		return containsNonStaticPrivateData(true);
	}

	/**
	 * Get whether or not the class contains any private non-static
	 * fields.
	 * 
	 * @param checkAncestor Whether or not to check the ancestor class
	 * 		if the data is not found.
	 * @return Whether or not the class contains private non-static data.
	 */
	public boolean containsNonStaticPrivateData(boolean checkAncestor)
	{
		InstanceFieldList privateFields = getFieldList().getPrivateFieldList();
		
		if (privateFields.getNumVisibleChildren() > 0)
		{
			return true;
		}
		
		if (checkAncestor && getExtendedClassLocation() != null)
		{
			return getExtendedClassDeclaration().containsNonStaticPrivateData();
		}
		
		return false;
	}

	/**
	 * Get whether or not the class contains any non-static fields.
	 * 
	 * @return Whether or not the class contains non-static data.
	 */
	public boolean containsNonStaticData()
	{
		return containsNonStaticData(true);
	}
	
	/**
	 * Get whether or not the class contains any non-static fields.
	 * 
	 * @param checkAncestor Whether or not to check the ancestor class
	 * 		if the data is not found.
	 * @return Whether or not the class contains non-static data.
	 */
	public boolean containsNonStaticData(boolean checkAncestor)
	{
		InstanceFieldList fields = getFieldList().getPublicFieldList();
		
		if (fields.getNumChildren() > 0 || containsNonStaticPrivateData(checkAncestor))
		{
			return true;
		}
		
		if (checkAncestor && getExtendedClassLocation() != null)
		{
			return getExtendedClassDeclaration().containsNonStaticData();
		}
		
		return false;
	}
	
	public MethodDeclarationValidator[] getVisibleNativeMethods()
	{
		ArrayList<MethodDeclarationValidator> methods = new ArrayList<>();
		
		MethodList lists[] = new MethodList[] { getMethodList(), getConstructorList() };
		
		for (MethodList list : lists)
		{
			for (int i = 0; i < list.getNumVisibleChildren(); i++)
			{
				MethodDeclarationValidator method = list.getVisibleChild(i);
				
				if (!method.isExternal() && !(method instanceof AssignmentMethodValidator) &&
						!(method instanceof InitializationMethodValidator) &&
						method.getVisibility() == PUBLIC && method.getRootDeclaration().getParentClass() == this)
				{
					methods.add(method);
				}
			}
		}
		
		return methods.toArray(new MethodDeclarationValidator[0]);
	}
	
	public boolean containsComplexConstructors()
	{
		for (MethodDeclarationValidator c : getConstructorList())
		{
			if (c.getParameterList().getNumParameters() > 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public Constructor[] getComplexConstructors()
	{
		ArrayList<Constructor> cs = new ArrayList<Constructor>();
		
		for (MethodDeclarationValidator c : getConstructorList())
		{
			if (c.getParameterList().getNumParameters() > 0)
			{
				cs.add((Constructor)c);
			}
		}
		
		return cs.toArray(new Constructor[0]);
	}
	
	public boolean containsSimilarConstructor(Constructor c, boolean checkAncestors)
	{
		for (MethodDeclarationValidator m : getConstructorList())
		{
			if (m.areCompatibleParameterTypes(getContext(), c.getParameterList().getTypes()))
			{
				return true;
			}
		}
		
		if (checkAncestors && doesExtendClass())
		{
			return getExtendedClassDeclaration().containsSimilarConstructor(c, checkAncestors);
		}
		
		return false;
	}
	
	/**
	 * Decode the given statement into a ClassDeclaration, if possible. If it is
	 * not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public class ClassName</li>
	 * 	<li>private static class ClassName</li>
	 * 	<li>public abstract class ClassName</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to translate into a ClassDeclaration
	 * 		if possible.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ClassDeclaration.
	 */
	public static ClassDeclarationValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		return decodeStatement(parent, statement, location, require, new ClassData());
	}
	
	public static ClassDeclarationValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require, ClassData data)
	{
		int index = SyntaxUtils.findStringInBaseScope(statement, IDENTIFIER);
		
		// If contains 'class' in the statement.
		if (index >= 0 && IDENTIFIER.equals(StringUtils.findNextWord(statement, index)))
		{
			if (index > 0 && !StringUtils.isWhitespace(statement.charAt(index - 1)))
			{
				return null;
			}
			else if (index + IDENTIFIER.length() < statement.length() && !StringUtils.isWhitespace(statement.charAt(index + IDENTIFIER.length())))
			{
				return null;
			}
			
			ClassDeclarationValidator n = new ClassDeclarationValidator(parent, location);
			n.setVisibility(PUBLIC);
			
			GenericTypeParameterList.searchGenerics(statement, data);
			
			n.iterateWords(statement, data, require);
			
			if (data.getGenericsRemaining() > 0)
			{
				Location newLoc = n.getLocationIn().asNew();
				Bounds   b      = data.getSkipBounds(data.getNumSkipBounds() - data.getGenericsRemaining());
				
				newLoc.addBounds(b.getStart(), b.getEnd());
				
				SyntaxMessage.error("Invalid generic type declaration", n, newLoc);
			}
			
			// TODO: Check for the standard library version of Object.
			if (n.extendedClass == null && !n.getClassLocation(false).equals("flat/Object"))
			{
				ExtendedClass extended = ExtendedClass.decodeStatement(n, "Object", n.getLocationIn().asNew(), require);
				
				n.setExtendedClass(extended);
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see NodeValidator#interactWord(String, Bounds, String, String, NodeValidator.ExtraData)
	 */
	@Override
	public boolean interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		ClassData data = (ClassData)extra;
		
		if (data.extending || data.implementing)
		{
			if (data.extending)
			{
				ExtendedClass extended = ExtendedClass.decodeStatement(this, word, getLocationIn().asNew(), extra.require);
				
				if (extended == null)
				{
					SyntaxMessage.queryError("Could not decode class extension '" + word + "'", this, extra.require);
					
					return false;
				}
				
				setExtendedClass(extended);
				
				data.extending = false;
				
				if (data.getRightAdjacentSkipBounds() != null)
				{
					extended.decodeGenericTypeArguments(data.statement, data.getRightAdjacentSkipBounds());
					
					data.decrementGenericsRemaining();
				}
			}
			else
			{
				if (word.length() > 0)
				{
					Location loc = new Location(0, 0, bounds.getStart(), bounds.getEnd());
					
					TraitImplementation i = TraitImplementation.decodeStatement(this, word, loc, extra.require);
					
					getInterfacesImplementationList().addChild(i);
					
					if (data.getRightAdjacentSkipBounds() != null)
					{
						i.decodeGenericTypeArguments(data.statement, data.getRightAdjacentSkipBounds());
						
						data.decrementGenericsRemaining();
					}
				}
				else
				{
					
				}
			}
		}
		else
		{
			if (word.equals("extends"))
			{
				data.extending = true;
			}
			else if (word.equals("implements"))
			{
				data.implementing = true;
			}
			else
			{
				setAttribute(word, extra.getWordNumber());
				boolean checkGenerics = false;
				
				if (!data.isFirstWord() && data.getPreviousWord().equals(IDENTIFIER)) {
					setName(word);
					setType(word);
					checkGenerics = true;
				} else if (word.equals(IDENTIFIER)) {
					if (parent instanceof FileDeclaration && (data.isLastWord() || Stream.of("extends", "implements").anyMatch(w -> w.equals(data.getNextWord())))) {
						String className = parent.getFileDeclaration(true).getName();

						setName(className);
						setType(className);
						checkGenerics = true;
					}
				}

				if (checkGenerics && data.getRightAdjacentSkipBounds() != null)
				{
					getGenericTypeParameterDeclaration().decodeGenericTypeParameters(data.statement, data.getRightAdjacentSkipBounds());

					for (GenericTypeParameter param : getGenericTypeParameterDeclaration())
					{
						decodeGenericTypeArguments(param.getName());

						data.decrementGenericsRemaining();
					}
				}
			}
		}

		return true;
	}
	
	public boolean containsGenericTypeParameter(String parameterName)
	{
		return getGenericTypeParameterDeclaration().containsParameter(parameterName);
	}
	
	public boolean doesExtendClass(String classLocation)
	{
		return doesExtendClass(getProgram().getClassDeclaration(classLocation));
	}
	
	public boolean doesExtendClass(ClassDeclarationValidator clazz)
	{
		return getExtendedClass(clazz) != null;
	}
	
	public ExtendedClass getExtendedClass(ClassDeclarationValidator clazz)
	{
		ClassDeclarationValidator prev = this;
		ClassDeclarationValidator extension = getExtendedClassDeclaration();
		
		while (extension != null)
		{
			if (extension == clazz)
			{
				return prev.getExtendedClass();
			}
			
			prev = extension;
			extension = extension.getExtendedClassDeclaration();
		}
		
		return null;
	}
	
	public GenericTypeParameter getGenericTypeParameter(String parameterName, GenericCompatible value)
	{
		/*Node context = (Node)((Value)value).getContext();
		
		if (context != null)
		{
			if (context.getParentMethod() != null)
			{
				FlatMethodDeclaration method = context.getParentMethod();
				
				GenericTypeParameter param = method.getGenericTypeParameter(parameterName);
				
				if (param != null)
				{
					return param;
				}
			}
			
			//clazz = context.getAncestorOfType(VariableDeclaration.class, true).getParentClass(true);
		}*/
		
		GenericTypeParameter param = getGenericTypeParameter(parameterName);
		
		if (param == null && encapsulatingClass != null && !isPropertyTrue("functionMap") && !isPropertyTrue("propertyMap") && !isPrimitiveOverload())
		{
			param = encapsulatingClass.getGenericTypeParameter(parameterName, value);
		}
		
		if (param == null)
		{
			ClassDeclarationValidator clazz = this;
			
			if (clazz != this && this.isOfType(clazz))
			{
				if (this.doesExtendClass(clazz))
				{
					return clazz.getGenericTypeParameter(parameterName);
				}
				else
				{
					for (TraitImplementation i : getInterfacesImplementationList())
					{
						GenericTypeArgumentList args = i.getGenericTypeArgumentList();
						
						for (int index = 0; index < args.getNumVisibleChildren(); index++)
						{
							GenericTypeArgument arg = args.getVisibleChild(index);
							
							if (arg.getType().equals(parameterName))
							{
								return getGenericTypeParameter(parameterName);
							}
						}
					}
				}
			}
			else
			{
				if (value instanceof Variable)
				{
					ClassDeclarationValidator declClass = ((Variable) value).getDeclaration().getParentClass();
					
					if (declClass != this)
					{
						return declClass.getGenericTypeParameter(parameterName);
					}
				}
				
				return clazz.getGenericTypeParameter(parameterName);
			}
		}
		
		return param;
	}
	
	public GenericTypeParameter getGenericTypeParameter(String parameterName)
	{
		for (GenericTypeParameter param : getGenericTypeParameterDeclaration())
		{
			if (param.getName().equals(parameterName))
			{
				return param;
			}
		}
		
		return null;
	}
	
	public GenericTypeParameter getGenericTypeParameter(int index)
	{
		return getGenericTypeParameterDeclaration().getVisibleChild(index);
	}
	
	public String generateTemporaryMethodName()
	{
		return generateTemporaryMethodName("temp");
	}
	
	public String generateTemporaryMethodName(String prefix)
	{
		String name = prefix;
		int    i    = 0;
		
		while (containsMethod(name))
		{
			name = prefix + i++;
		}
		
		return name;
	}
	
	public AnonymousCompilerMethodValidator generateAnonymousFunction()
	{
		AnonymousCompilerMethodValidator func = new AnonymousCompilerMethodValidator(this, getLocationIn());
		
		func.setName("generated" + getProgram().getUniqueId(), true);
		
		addChild(func);
		
		return func;
	}
	
	public ClassDeclarationValidator getExistingConvertedPrimitiveClass(ValueValidator[] args)
	{
		for (ClassDeclarationValidator converted : primitiveOverloads)
		{
			if (converted.primitiveOverloadTypes.length == args.length)
			{
				boolean compatible = true;
				
				for (int i = 0; i < args.length; i++)
				{
					ValueValidator required = converted.primitiveOverloadTypes[i];
					ValueValidator arg = args[i].getReturnedNode();
					
					if (arg.getDataType() != required.getDataType() ||
						arg.isPrimitive() && !arg.getType().equals(required.getType()))
					{
						compatible = false;
					}
				}
				
				if (compatible)
				{
					return converted;
				}
			}
		}
		
		return null;
	}
	
	public ValueValidator[] getClassGenericValues(ClassDeclarationValidator required)
	{
		if (this == required)
		{
			return getGenericTypeArgumentList().getTypes();
		}
		
		if (doesExtendClass(required))
		{
			return getExtendedClass(required).getGenericTypeArgumentList().getTypes();
		}
		
		return null;
	}
	
	public ValueValidator[] getConvertedTypes(ValueValidator[] args)
	{
		GenericTypeParameterList params = getGenericTypeParameterDeclaration();
		ValueValidator[] types = new ValueValidator[args.length];
		
		boolean isPrimitive = false;
		
		for (int i = 0; i < types.length; i++)
		{
			if (i >= params.getNumParameters())
			{
				types[i] = args[i];
				
				continue;
			}
			
			GenericTypeParameter param = params.getParameter(i);
			
			if (i < args.length && !param.isPrimitiveType() && args[i].isPrimitiveType())//param.getDataType() > args.getVisibleChild(i).getDataType())
			{
				types[i] = args[i];
				
				isPrimitive = true;
			}
			else
			{
				types[i] = param;
			}
		}
		
		if (isPrimitive)
		{
			return types;
		}
		
		return null;
	}
	
	public ClassDeclarationValidator getConvertedPrimitiveClass(ValueValidator[] args)
	{
		if (!Flat.PRIMITIVE_OVERLOADS) {
			return null;
		}

		if (args.length > 0)
		{
			if (isPrimitiveOverload())
			{
				ValueValidator[] originalArgs = new ValueValidator[originalPrimitiveOverloadTypes.length];
				
				int position = 0;
				
				for (int i = 0; i < originalArgs.length; i++)
				{
					if (originalPrimitiveOverloadTypes[i] instanceof GenericTypeParameter == false)
					{
						originalArgs[i] = (ValueValidator)originalPrimitiveOverloadTypes[i].clone(this, originalPrimitiveOverloadTypes[i].getLocationIn(), true, true);
					}
					else
					{
						originalArgs[i] = args[position++];
					}
				}
				
				ClassDeclarationValidator c = genericOverload.getConvertedPrimitiveClass(originalArgs);
				
				if (c == this)
				{
					return null;
				}
				
				return c;
			}
			
			ClassDeclarationValidator c = getExistingConvertedPrimitiveClass(args);
			
			if (c != null)
			{
				return c;
			}
			
			ValueValidator[] types = getConvertedTypes(args);
			
			if (types != null)
			{
				return convertToPrimitive(types);
			}
		}
		
		return null;
	}
	
	public boolean replacePrimitiveGenerics(final ValueValidator[] types, ValueValidator value)
	{
		return replacePrimitiveGenerics(types, value, value, true);
	}
	
	public boolean replacePrimitiveGenerics(GenericTypeParameterList params, final ValueValidator[] types, ValueValidator value)
	{
		return replacePrimitiveGenerics(params, types, value, value, true);
	}
	
	public boolean replacePrimitiveGenerics(final ValueValidator[] types, ValueValidator original, ValueValidator value)
	{
		return replacePrimitiveGenerics(types, original, value, false);
	}
	
	public boolean replacePrimitiveGenerics(final ValueValidator[] types, ValueValidator original, ValueValidator value, boolean allowSame)
	{
		return replacePrimitiveGenerics(getGenericTypeParameterDeclaration(), types, original, value, allowSame);
	}
	
	public static boolean shallowReplaceGenerics(GenericTypeParameterList params, final ValueValidator[] types, ValueValidator original, ValueValidator value, boolean allowSame)
	{
		GenericTypeParameter genParam = original.getGenericTypeParameter();
		GenericTypeParameter valParam = value.getGenericTypeParameter();
		
		if (genParam != null && (allowSame || genParam != valParam))
		{
			if (genParam.getParent() == params)
			{
				if (types.length <= genParam.getIndex()) {
					return false;
				}

				ValueValidator type = types[genParam.getIndex()];
				
				if (type.isPrimitive())
				{
					int dimensions = original.getArrayDimensions();
					
					value.setType(type);
					value.setArrayDimensions(dimensions);
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean replacePrimitiveGenerics(GenericTypeParameterList params, final ValueValidator[] types, ValueValidator original, ValueValidator value, boolean allowSame)
	{
		if (!Flat.PRIMITIVE_OVERLOADS) {
			return false;
		}

		boolean changed = false;
		
		if (shallowReplaceGenerics(params, types, original, value, allowSame))
		{
			changed = true;
		}
		else
		{
			GenericTypeArgumentList originalArgs = original.getGenericTypeArgumentList();
			GenericTypeArgumentList args = value.getGenericTypeArgumentList();
			
			if (args != null)
			{
				for (int i = 0; i < Math.min(args.getNumVisibleChildren(), originalArgs.getNumVisibleChildren()); i++)
				{
					changed |= replacePrimitiveGenerics(params, types, originalArgs.getVisibleChild(i), args.getVisibleChild(i), allowSame);
				}
				
//				if (changed)
				if (value.getTypeClass() != null)
				{
					ClassDeclarationValidator converted = value.getTypeClass().getConvertedPrimitiveClass(args.getTypes());
					
					if (converted != null)
					{
						value.getFileDeclaration().addImport(converted.getClassLocation());
						
						value.setType(converted);
						
						changed = true;
					}
				}
			}
		}
		
		if (value instanceof ClosureDeclarationValidator)
		{
			changed |= replacePrimitiveGenerics(params, types, (ClosureDeclarationValidator)original, (ClosureDeclarationValidator)value, allowSame);
		}
		
		return changed;
	}
	
	public boolean replacePrimitiveGenerics(ValueValidator[] types, ClosureDeclarationValidator original, ClosureDeclarationValidator value)
	{
		return replacePrimitiveGenerics(types, original, value, false);
	}
	
	public boolean replacePrimitiveGenerics(ValueValidator[] types, ClosureDeclarationValidator original, ClosureDeclarationValidator value, boolean allowSame)
	{
		return replacePrimitiveGenerics(getGenericTypeParameterDeclaration(), types, original, value, allowSame);
	}
	
	public static boolean replacePrimitiveGenerics(GenericTypeParameterList params, ValueValidator[] types, ClosureDeclarationValidator original, ClosureDeclarationValidator value, boolean allowSame)
	{
		if (!Flat.PRIMITIVE_OVERLOADS) {
			return false;
		}

		value.register();
		
		ParameterList<ValueValidator> originalClosureParams = original.getParameterList();
		ParameterList<ValueValidator> closureParams = value.getParameterList();
		
		boolean changed = false;
		
		for (int n = 0; n < originalClosureParams.getNumParameters(); n++)
		{
			changed |= replacePrimitiveGenerics(params, types, originalClosureParams.getParameter(n), closureParams.getParameter(n), allowSame);
		}
		
		return changed;
	}
	
	private void cloneMethods(final ValueValidator[] types, MethodList methods, ClassDeclarationValidator addTo)
	{
		methods.forEachVisibleListChild(method -> {
			if (method instanceof ExternalMethodDeclarationValidator)
			{
				ExternalMethodDeclarationValidator clone = (ExternalMethodDeclarationValidator)method.clone(addTo, method.getLocationIn(), true, true);
				
				ParameterList original = method.getParameterList();
				
				for (int i = 0; i < clone.getParameterList().getNumVisibleChildren(); i++)
				{
					replacePrimitiveGenerics(types, original.getParameter(i), clone.getParameter(i));
				}
				
				addTo.addChild(clone);
			}
			else if (method instanceof InitializationMethodValidator == false && method instanceof Destructor == false && method instanceof AssignmentMethodValidator == false)
			{
				FlatMethodDeclarationValidator flatMethod = (FlatMethodDeclarationValidator)method;
				
				if (!flatMethod.isPrimitiveOverload())
				{
					flatMethod.convertToClass(addTo, types, flatMethod.getMethodGenericTypeParameterDeclaration().getTypes());
				}
			}
		});
	}
	
	private void setMethodReferences(MethodList methods)
	{
		methods.forEachVisibleListChild(x -> {
			if (x instanceof FlatMethodDeclarationValidator && x.isInstance())
			{
				FlatMethodDeclarationValidator method = (FlatMethodDeclarationValidator)x;
				
				method.getParameterList().getReferenceParameter().setTypeValue(getName());
			}
		});
	}
	
	private void setMethodReferences()
	{
		setMethodReferences(getConstructorList());
		setMethodReferences(getDestructorList());
		setMethodReferences(getMethodList());
		setMethodReferences(getPropertyMethodList());
		setMethodReferences(getHiddenMethodList());
		setMethodReferences(getVirtualMethodList());
	}
	
	public ValueValidator[] convertImplementationTypes(final ValueValidator[] types, GenericTypeArgumentList args)
	{
		ValueValidator[] converted = new ValueValidator[args.getNumVisibleChildren()];
		
		for (int i = 0; i < args.getNumVisibleChildren(); i++)
		{
			GenericTypeArgument arg = args.getVisibleChild(i);
			GenericTypeParameter param = arg.getGenericTypeParameter();
			
			if (param != null && param.getParentClass() == this)
			{
				converted[i] = types[param.getIndex()];
			}
			else
			{
				converted[i] = arg.clone(arg.getParent(), arg.getLocationIn(), true, true);
				replacePrimitiveGenerics(types, converted[i]);
			}
		}
		
		return converted;
	}
	
	public String formatGenericArguments(ValueValidator[] args)
	{
		if (args.length > 0)
		{
			return String.join(", ", Arrays.stream(args).map(x -> x.getFlatType(this)).collect(Collectors.toList()));
		}
		
		return "";
	}
	
	public void addConvertedImplementations(ClassDeclarationValidator c, final ValueValidator[] types)
	{
		ExtendedClass extended = getExtendedClass();
		
		ValueValidator[] extendedValues = convertImplementationTypes(types, extended.getGenericTypeArgumentList());
		
		c.setExtendedClass(ExtendedClass.decodeStatement(c, extended.getType(), extended.getLocationIn(), true));
		c.getExtendedClass().decodeGenericTypeArguments(formatGenericArguments(extendedValues));
		
		replacePrimitiveGenerics(types, c.getExtendedClass());
		
		TypeList<TraitImplementation> traits = getInterfacesImplementationList();
		
		for (int i = 0; i < traits.getNumVisibleChildren(); i++)
		{
			TraitImplementation trait = traits.getVisibleChild(i);
			
			ValueValidator[] traitValues = convertImplementationTypes(types, trait.getGenericTypeArgumentList());
			
			TraitImplementation t = TraitImplementation.decodeStatement(this, trait.getType(), c.getLocationIn(), true);
			t.decodeGenericTypeArguments(formatGenericArguments(traitValues));
			
			replacePrimitiveGenerics(types, t);
			
			c.getInterfacesImplementationList().addChild(t);
			
			t.onAfterDecoded();
		}
	}

	public ClassDeclarationValidator convertToPrimitive(final ValueValidator[] types)
	{
		ClassDeclarationValidator c = clone(getParent(), getLocationIn(), false, true);

		primitiveOverloads.add(c);
		
		c.genericOverload = this;
		c.originalPrimitiveOverloadTypes = new ValueValidator[types.length];
		c.primitiveOverloadTypes = new ValueValidator[types.length];
		
		for (int i = 0; i < types.length; i++)
		{
			//IValue value = new IValue(types[i].getParent(), types[i].getLocationIn());
			c.primitiveOverloadTypes[i] = (ValueValidator)types[i].clone(this, types[i].getLocationIn(), true, true);//types[i].cloneTo(value, true, true);
			c.originalPrimitiveOverloadTypes[i] = (ValueValidator)types[i].clone(this, types[i].getLocationIn(), true, true);
		}

		String name = c.getName();
		
		GenericTypeParameterList params = c.getGenericTypeParameterDeclaration();
		
		for (int i = 0; i < types.length; i++)
		{
			if (types[i] instanceof GenericTypeParameter)
			{
				GenericTypeParameter param = (GenericTypeParameter)types[i].clone(params, getLocationIn(), true, true);//new GenericTypeParameter(params, getLocationIn());
//				param.setType(types[i]);
				
				params.addChild(param);
			}
			else
			{
				name += (i + 1) + types[i].getType();
			}
		}
		
		c.setName(name);
		c.setTypeValue(name);
		addChild(c);

		addConvertedImplementations(c, types);
		
		if (getProgram().getPhase() > SyntaxTree.PHASE_INSTANCE_DECLARATIONS || getProgram().getTree().finishedPhase)
		{
			c.convertProperties();
		}
		
		return c;
	}
	
	public void convertProperties()
	{
		if (Flat.PRIMITIVE_OVERLOADS) {
			return;
		}
		if (isPrimitiveOverload())
		{
			if (genericOverload.arrayBracketOverloadValidator != null)
			{
				arrayBracketOverloadValidator = genericOverload.arrayBracketOverloadValidator.clone(this, genericOverload.arrayBracketOverloadValidator.getLocationIn(), true, true);
			}
			
			final int phase = getProgram().getPhase();
			final boolean finished = getProgram().getTree().finishedPhase;
			
			genericOverload.getFieldList().forEachChild(list ->
			{
				list.forEachChild(node ->
				{
					FieldDeclaration field = (FieldDeclaration)node;
					
					if (field.isUserMade())
					{
						FieldDeclaration clone = field.clone(this, field.getLocationIn(), true, true);
						clone.setProperty("userMade", false);
						clone.removeAnnotationOfType(OverrideAnnotation.class, false, false);
						clone.removeAnnotationOfType(RequireGenericTypeAnnotation.class, false, false);
						clone.accessorValue = null;
						clone.initializationValue = null;
						clone.genericOverload = field;
						field.correspondingPrimitiveOverloads.add(clone);
						clone.setProperty("genericOverload", field);
						
						genericOverload.replacePrimitiveGenerics(originalPrimitiveOverloadTypes, field, clone);
						
						clone.onAfterDecoded();
						addChild(clone);
					}
				});
			});
			
			getHiddenMethodList().slaughterEveryLastVisibleChild();
			getDestructorList().slaughterEveryLastVisibleChild();
			
			genericOverload.cloneMethods(originalPrimitiveOverloadTypes, genericOverload.getConstructorList(), this);
			genericOverload.cloneMethods(originalPrimitiveOverloadTypes, genericOverload.getMethodList(), this);
			genericOverload.cloneMethods(originalPrimitiveOverloadTypes, genericOverload.getPropertyMethodList(), this);
			genericOverload.cloneMethods(originalPrimitiveOverloadTypes, genericOverload.getHiddenMethodList(), this);
			
			validate(SyntaxTree.PHASE_CLASS_DECLARATION);
			
			setMethodReferences();
			
			if (phase > SyntaxTree.PHASE_INSTANCE_DECLARATIONS || finished)
			{
				if (phase > SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
				{
					getConstructorList().forEachFlatMethod(x -> x.checkOverrides());
					getMethodList().forEachFlatMethod(x -> x.checkOverrides());
					getPropertyMethodList().forEachFlatMethod(x -> x.checkOverrides());
				}
				
				for (int i = SyntaxTree.PHASE_INSTANCE_DECLARATIONS; i <=SyntaxTree.PHASE_INSTANCE_DECLARATIONS/* phase || i == phase && finished*/; i++)
				{
					SyntaxTree.validateNodes(this, i);
				}
			}
		}
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
		
		if (arrayBracketOverloadValidator != null)
		{
			arrayBracketOverloadValidator.validate(phase);
		}
		
		if (phase == SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			getStaticBlockList().addChild(StaticBlock.generateEmptyBlock(getStaticBlockList(), Location.INVALID));
			
			if (!isPropertyTrue("functionMap"))
			{
				validateDeclaration(phase);
			}
			
			if (extendedClass != null && getFileDeclaration().containsImport(getExtendedClassLocation()))
			{
				getFileDeclaration().getImport(getExtendedClassLocation()).markUsed();
			}
			
			for (GenericTypeParameter param : getGenericTypeParameterDeclaration())
			{
				ClassDeclarationValidator typeClass = SyntaxUtils.getImportedClass(getFileDeclaration(), param.getDefaultType());

				if (typeClass == null) {
					SyntaxMessage.error("Invalid generic type parameter default type '" + param.getDefaultType() + "'", param);
					result.errorOccurred = true;
					return result;
				}
				
				if (typeClass.isPrimitive())
				{
					
				}
			}
			
			addAssignmentMethods();
			
			if (!isPrimitiveOverload())
			{
//				generateFunctionMap();
//				generatePropertyMap();
			}
		}
		else if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			validateFields(phase);
			validateMethods(phase);
		}
		else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			validateMethods(phase);
		}
		else if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			
		}
		
		return result;
	}
	
	public boolean isPrimitiveOverload()
	{
		return genericOverload != null;
	}
	
	public void addFunctionMapFunctions()
	{
		if (functionMap != null)
		{
			functionMap.addFunctionMapFunctions(this);
		}
	}
	
	public void addPropertyMapFunctions()
	{
		if (propertyMap != null)
		{
			propertyMap.addPropertyMapProperties(this);
		}
	}
	
	private void addMapImport(ClassDeclarationValidator c, String type)
	{
		if (c != null && !c.getClassLocation().equals("flat/Object"))
		{
			String importLocation = c.getClassLocation() + "." + c.getName() + type;
			
			getFileDeclaration().addImport(importLocation);
		}
	}
	
	public ClassDeclarationValidator generateMap(String type)
	{
		String lower = Character.toLowerCase(type.charAt(0)) + type.substring(1);
		
		ClassDeclarationValidator funMap = getProgram().getClassDeclaration("flat/meta/" + type);
		
		if (this instanceof Trait || getExtendedClassDeclaration() != null && !getExtendedClassDeclaration().isOfType(funMap))
		{
			addMapImport(getExtendedClassDeclaration(), type);
			
			String extensionName = !getExtendedClassName().equals("Object") ? getExtendedClassName() : "";
			
			String classType = this instanceof Trait ? "trait" : "class";
			
			String declaration = classType + " " + /*getClassLocation().replace('/', '_').replace('.', '_')*/ getName() + type + " extends " + extensionName + type;
			
			if (getImplementedInterfaces().length > 0)
			{
				declaration += " implements ";
				
				int i = 0;
				
				for (Trait t : getImplementedInterfaces())
				{
					addMapImport(t, type);
					
					if (i++ > 0)
					{
						declaration += ", ";
					}
					
					declaration += t.getName() + type;
				}
			}
			
			ClassDeclarationValidator c = (ClassDeclarationValidator)SyntaxTree.decodeStatement(this, declaration, Location.INVALID, true, new Class[] {
				ClassDeclarationValidator.class,
				Trait.class,
			});
			
			if (c == null)
			{
				SyntaxMessage.error("Could not generate " + type + " for class '" + getName() + "'", this);
			}
			
			c.setProperty("userMade", false);
			c.setProperty(lower, true);
			c.setProperty("correspondingClass", this);
			
			addChild(c);
			
			return c;
		}
		
		return null;
	}
	
	private void generateFunctionMap()
	{
		if (functionMap == null && !isPropertyTrue("functionMap") && !isPropertyTrue("propertyMap") && !getFileDeclaration().isExternalFile())
		{
			functionMap = generateMap("FunctionMap");
		}
	}
	
	public void generatePropertyMap()
	{
		if (propertyMap == null && !isPropertyTrue("functionMap") && !isPropertyTrue("propertyMap") && !getFileDeclaration().isExternalFile())
		{
			propertyMap = generateMap("PropertyMap");
			
			if (propertyMap != null)
			{
				propertyMap.setProperty("functionMap", true);
			}
		}
	}
	
	private void addFunctionMapFunctions(ClassDeclarationValidator reference)
	{
		Consumer<MethodDeclarationValidator> addFunction = m -> {
			if (m instanceof FlatMethodDeclarationValidator && m.isUserMade() && m instanceof Destructor == false && !m.isExternalType())
			{
				addFunctionMapFunction(reference, (FlatMethodDeclarationValidator)m);
			}
		};
		
		reference.getConstructorList().forEach(addFunction);
		reference.getMethodList().forEach(addFunction);
		reference.getDestructorList().forEach(addFunction);
	}
	
	private void addPropertyMapProperties(ClassDeclarationValidator reference)
	{
		Consumer<MethodDeclarationValidator> addProperty = m -> {
			if (m instanceof PropertyMethodValidator && m.isUserMade() && !m.isExternalType())
			{
				addPropertyMapProperty(reference, (PropertyMethodValidator)m);
			}
		};
		Consumer<NodeValidator> addField = m -> {
			if (m instanceof FieldDeclaration && m.isUserMade() && !((FieldDeclaration)m).isExternalType())
			{
				addPropertyMapProperty(reference, (FieldDeclaration)m);
			}
		};
		
		reference.getPropertyMethodList().forEach(addProperty);
		reference.getFieldList().getPublicFieldList().forEachChild(addField);
		reference.getFieldList().getPublicStaticFieldList().forEachChild(addField);
	}
	
	private void addPropertyMapProperty(ClassDeclarationValidator reference, FieldDeclaration field)
	{
		addMapFunction(reference, field, field.getName(), new TypeList<Parameter>(field, field.getLocationIn()), ClassDeclarationValidator::decodeMapPropertyAccess);
	}
	
	private void addPropertyMapProperty(ClassDeclarationValidator reference, PropertyMethodValidator property)
	{
		
	}
	
	private void addFunctionMapFunction(ClassDeclarationValidator reference, FlatMethodDeclarationValidator function)
	{
		if (function instanceof AbstractMethodDeclarationValidator)
		{
			return;
		}
		
		String functionName = function instanceof Constructor ? "construct" : (function instanceof Destructor ? "destroy" : function.getName());
		
		addMapFunction(reference, function, functionName, function.getParameterList(), ClassDeclarationValidator::decodeMapFunctionAccess);
	}
	
	private static IdentifierValidator decodeMapFunctionAccess(InstanceDeclarationValidator function, IdentifierValidator accessing, String arguments)
	{
		return MethodCall.decodeStatement(accessing, function.getName() + arguments, function.getLocationIn(), true, false, (FlatMethodDeclarationValidator)function);
	}
	
	private static IdentifierValidator decodeMapPropertyAccess(InstanceDeclarationValidator field, IdentifierValidator accessing, String arguments)
	{
		return (IdentifierValidator)SyntaxTree.decodeAccessible(accessing, field.getName(), field.getLocationIn(), true, false);
	}
	
	@FunctionalInterface
	private interface QuadFunction<A, B, C, D>
	{
		D call(A a, B b, C c);
	}
	
	private void addMapFunction(ClassDeclarationValidator reference, InstanceDeclarationValidator instance, String functionName, TypeList<Parameter> parameterList, QuadFunction<InstanceDeclarationValidator, IdentifierValidator, String, IdentifierValidator> generator)
	{
//		ArrayList<String> genericParameters = new ArrayList<>();
		String parameters = "(";
		
		if (!instance.isStatic())
		{
			String paramName = "reference";
			
			while (paramName.equals(instance.getName()) || (instance instanceof FlatMethodDeclarationValidator &&
				((FlatMethodDeclarationValidator)instance).getParameter(paramName) != null))
			{
				paramName += "_";
			}
			
			parameters += reference.generateFlatType() + " " + paramName;
		}
		
		for (Parameter p : parameterList)
		{
			if (parameters.length() > 1)
			{
				parameters += ", ";
			}
			
			/*if (p.isGenericType() && !genericParameters.contains(p.getType()))
			{
				genericParameters.add(p.getType());
			}
			else */if (p.isExternalType())
			{
				return;
			}
			
			if (p.isOptional())
			{
				parameters += "passing ";
			}
			
			parameters += p.generateFlatInput(new StringBuilder(), true, true, false);
			
			if (p.isOptional())
			{
				parameters += " = " + (p.isPrimitive() ? "0" : "null");
			}
		}
		
		parameters += ")";
		
		String returnType = instance.getType() != null ? " -> " + instance.getFlatType() : "";
		
//		if (instance.isGenericType() && !genericParameters.contains(instance.getType()))
//		{
//			genericParameters.add(instance.getType());
//		}
		
		GenericTypeParameterList referenceParameters = reference.getGenericTypeParameterDeclaration();
		
		String genericParams = "";
		
		if (instance instanceof FlatMethodDeclarationValidator)
		{
			GenericTypeParameterList instanceParameters = ((FlatMethodDeclarationValidator)instance).getMethodGenericTypeParameterDeclaration();
			
			if (instanceParameters.getNumParameters() > 0 || referenceParameters.getNumParameters() > 0)
			{
				for (int i = 0; i < instanceParameters.getNumParameters(); i++)
				{
					if (i > 0)
					{
						genericParams += ", ";
					}
					
					genericParams += instanceParameters.getParameter(i).getType();
				}
			}
		}
		
		if (genericParams.length() > 0 || referenceParameters.getNumParameters() > 0)
		{
			for (int i = 0; i < referenceParameters.getNumParameters(); i++)
			{
				if (genericParams.length() > 0)
				{
					genericParams += ", ";
				}
				
				genericParams += referenceParameters.getParameter(i).getType();
			}
		}
		
		if (genericParams.length() > 0)
		{
			genericParams = "<" + genericParams + ">";
		}
		
		String staticValue = instance.isStatic() ? "static " : "";
		String visibility = instance.getVisibilityText();
		visibility = visibility.endsWith("visible") ? "public" : visibility;
		
		BodyMethodDeclarationValidator method = BodyMethodDeclarationValidator.decodeStatement(this, visibility + " " + staticValue + functionName + genericParams + parameters + returnType, instance.getLocationIn(), true);
		
		if (method == null)
		{
			BodyMethodDeclarationValidator.decodeStatement(this, visibility + " " + staticValue + functionName + genericParams + parameters + returnType, instance.getLocationIn(), true);
			SyntaxMessage.error("Could not generate function map handle for function '" + reference.getClassLocation() + "." + instance.getName() + "'", instance);
		}
		
		method.onAfterDecoded();
		
		Parameter referenceParameter = null;
		
		if (!instance.isStatic())
		{
			referenceParameter = method.getParameter("reference");
		}
		
		String arguments = "(";
		
		int i = instance.isStatic() ? 0 : 1;
		
		for (Parameter p : parameterList)
		{
			if (arguments.length() > 1)
			{
				arguments += ", ";
			}
			
			arguments += p.getName();
			
//			if (p instanceof ClosureDeclaration)
//			{
//				ClosureDeclaration closure = (ClosureDeclaration)p;
//				
//				((ClosureDeclaration)method.getParameterList().getParameter(i)).id = closure.id;
////				method.getParameterList().getParameter(i).replaceWith(closure.clone(method.getParameterList(), function.getLocationIn(), true, true));
//			}
			
			i++;
		}
		
		arguments += ")";
		
		IdentifierValidator accessing = null;
		IdentifierValidator call = null;
		NodeValidator nodeValidator = null;
		
		if (instance instanceof Constructor)
		{
			method.setDataType(ValueValidator.POINTER);
			
			call = Instantiation.decodeStatement(method, instance.getName() + genericParams + arguments, instance.getLocationIn(), true, false, (Constructor)instance);
			nodeValidator = call;
		}
		else
		{
			if (!instance.isStatic())
			{
				referenceParameter.setDataType(ValueValidator.POINTER);
				
				accessing = method.getParameter("reference").generateUsableVariable(method, instance.getLocationIn());
			}
			else
			{
				accessing = StaticClassReference.decodeStatement(method, instance.getDeclaringClass().getName(), instance.getLocationIn(), true);
			}
			
			call = generator.call(instance, accessing, genericParams + arguments);
		}
		
		if (call == null)
		{
			SyntaxMessage.error("Could not generate function map handle delegate call for function '" + reference.getClassLocation() + "." + instance.getName() + "'", instance);
		}
		
		if (instance instanceof Constructor == false)
		{
			accessing.setAccessedNode(call);
			nodeValidator = accessing;
		}
		
		if (method.getType() != null)
		{
			Return r = new Return(method, instance.getLocationIn());
			
			r.getReturnValues().addChild(nodeValidator);
			
			nodeValidator = r;
		}
		
		method.addChild(nodeValidator);
		
		if (instance instanceof FlatMethodDeclarationValidator)
		{
			method.setProperty("correspondingFunction", instance);
		}
		else if (instance instanceof FieldDeclaration)
		{
			method.setProperty("correspondingField", instance);
		}
		
		addChild(method);
	}
	
	@Override
	public void prePreGenerationValidation()
	{
		if (isPrimitiveType())
		{
			InstanceFieldList publicFields  = getFieldList().getPublicFieldList();
			InstanceFieldList privateFields = getFieldList().getPrivateFieldList();
			
			publicFields.inheritChildren(privateFields);
			
			for (MethodDeclarationValidator method : getConstructorList())
			{
				method.setVisibility(PUBLIC);
			}
		}
	}
	
	/**
	 * Make sure that the Class is a valid declaration.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	private void validateDeclaration(int phase)
	{
		validateImplementations(phase);
		validateExtension(phase);
	}
	
	/**
	 * Make sure that the fields are all valid.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	private void validateFields(int phase)
	{
		
	}
	
	private void validateConstructors(int phase)
	{
		/*if (doesExtendClass() && getExtendedClassDeclaration().containsComplexConstructors())
		{
			Constructor[] cs = getExtendedClassDeclaration().getComplexConstructors();
			
			for (Constructor c : cs)
			{
				if (!containsSimilarConstructor(c, false))
				{
					Value[] types = c.getParameterList().getTypes();
					
					for (Value type : types)
					{
						if (!getFileDeclaration().containsImport(type.getTypeClassLocation()))
						{
							getFileDeclaration().addImport(type.getTypeClassLocation());
						}
					}
					
					Constructor con = Constructor.decodeStatement(this, c.generateFlatInput(false).toString(), Location.INVALID, true);
					//con.setVisibility(PRIVATE);
					
					for (int i = 0; i < c.getParameterList().getNumParameters(); i++)
					{
						Parameter param = c.getParameter(i);
						
						if (param.isGenericType())
						{
							con.getParameter(i).setType(param.getGenericReturnType());
						}
					}
					
					addChild(con);
				}
			}
			
//			if (!containsConstructor())
//			{
//				SyntaxMessage.error("Must define a constructor for class '" + getName() + "' because it's super class '" + getExtendedClassName() + "' contains a complex constructor.", this);
//			}
		}*/
		
		if (!containsConstructor())
		{
			addDefaultConstructor();
		}
	}
	
	public void decodeShorthandActions()
	{
		getMethodList().forEachFlatMethod(x -> x.decodeShorthandAction());
		getConstructorList().forEachFlatMethod(x -> x.decodeShorthandAction());
		getHiddenMethodList().forEachFlatMethod(x -> x.decodeShorthandAction());
		getVirtualMethodList().forEachFlatMethod(x -> x.decodeShorthandAction());
		getPropertyMethodList().forEachFlatMethod(x -> x.decodeShorthandAction());
		getDestructorList().forEachFlatMethod(x -> x.decodeShorthandAction());
		
		getFieldList().forEachChild(fields -> {
			fields.forEachChild(f -> {
				if (f instanceof ShorthandAccessible)
				{
					ShorthandAccessible field = (ShorthandAccessible)f;
					
					field.decodeArrowBinding();
				}
			});
		});
	}
	
	public void decodeFieldInitializations()
	{
		getFieldList().forEachChild(fields -> {
			fields.forEachChild(f -> {
				if (f instanceof FieldDeclaration)
				{
					FieldDeclaration field = (FieldDeclaration)f;
					
					field.decodeInitializationValue();
				}
			});
		});
	}
	
	private void addFieldsFromInterface(Trait i)
	{
		Consumer<NodeValidator> convertField = n -> {
			FieldDeclaration field = (FieldDeclaration)n;
			
			if (field.isPropertyTrue("addedDefaultInterfaceFunctions") || (field.isUserMade() || field.containsProperty("genericOverload")) && !field.containsAccessorMethod() && !field.containsMutatorMethod() && field.getShorthandAccessor() == null)
			{
				FieldDeclaration current = getField(field.getName(), false);
				
				if (current != null)
				{
					if (current.getVisibility() != field.getVisibility() && !(current.getVisibility() == PUBLIC && field.getVisibility() == FieldDeclaration.VISIBLE))
					{
						SyntaxMessage.error("Trait field '" + field.getName() + "' implementation in class '" + getClassLocation() + "' has to be " + field.getVisibilityText(), this, false);
					}
					else if (field.getVisibility() == PUBLIC && (current.getMutatorMethod() == null || current.getMutatorMethod().isDisabled()))
					{
						SyntaxMessage.error("Trait field '" + field.getName() + "' implementation in class '" + getClassLocation() + "' cannot hide mutator function", this, false);
					}
				}
				else
				{
					FieldDeclaration clone = field.clone(this, Location.INVALID, true, true);
					
					clone.setTwoWayBinding(true);//field.getVisibility() == PUBLIC);
					clone.setShorthandAccessor(field.getName());
					clone.setProperty("inheritedFromTrait", true);
					
					if (field.isGenericType())
					{
						clone.setType(field.getGenericTypeParameter().getCorrespondingArgument(clone), clone);
					}
					else
					{
//						Stack<IValue> path = SyntaxUtils.performWalk(this, field.getParentClass(), new Stack<>());
//						
//						while (!path.isEmpty())
//						{
//							
//						}
						
						clone.replaceGenericArguments(field);
					}
					
					addChild(clone);
					
					if (!getFileDeclaration().containsImport(field.getTypeClassLocation()))
					{
						getFileDeclaration().addImport(field.getTypeClassLocation());
					}
					
					ValueValidator type = clone;
					
					field.importGenericArgumentTypesTo(getFileDeclaration());

//					if (field.getVisibility() == FieldDeclaration.VISIBLE && !field.isGenericType())
//					{
//						type = field.getClonedFlatTypeValue(clone);
//						
//						if (type.getGenericTypeArgumentList() != null)
//						{
//							for (GenericTypeArgument arg : type.getGenericTypeArgumentList())
//							{
//								if (!arg.isGenericType())
//								{
//									if (!getFileDeclaration().containsImport(arg.getTypeClassLocation()))
//									{
//										getFileDeclaration().addImport(arg.getTypeClassLocation());
//									}
//								}
//							}
//						}
//					}
					
					if (!isPrimitiveOverload())
					{
						clone.decodeArrowBinding(type);
					}

//					if (!clone.isTwoWayBinding())
//					{
//						MutatorMethod method = (MutatorMethod)clone.addDefaultMutator(field.getClonedFlatTypeValue(clone));
//						
//						Assignment assignment = Assignment.generateDefault(method, method.getLocationIn());
//						
//						assignment.getAssigneeNodes().addChild(field.generateUsableVariable(assignment, assignment.getLocationIn()));
//						assignment.addChild(SyntaxTree.decodeIdentifier(assignment, "value", assignment.getLocationIn(), true));
//						
//						method.addChild(assignment);
//					}
				}
			}
		};
		
		i.getFieldList().getPublicFieldList().forEachVisibleChild(convertField);
		i.getFieldList().getPrivateFieldList().forEachVisibleChild(convertField);
		
		for (Trait extended : i.getImplementedInterfaces(false))
		{
			addFieldsFromInterface(extended);
		}
	}
	
	public void autoAddInterfaceFieldOverrides()
	{
		for (Trait i : getImplementedInterfaces(false))
		{
			addFieldsFromInterface(i);
		}
	}
	
	public void checkShorthandActionOverrides()
	{
		getFieldList().forEachChild(fields -> {
			fields.forEachChild(f -> {
				if (f instanceof FieldDeclaration)
				{
					FieldDeclaration field = (FieldDeclaration)f;
					
					if (field.getAccessorMethod() != null)
						field.getAccessorMethod().checkOverrides();
					if (field.getMutatorMethod() != null)
						field.getMutatorMethod().checkOverrides();
				}
			});
		});
		
		getMethodList().forEachFlatMethod(x -> x.checkOverrides());
		getConstructorList().forEachFlatMethod(x -> x.checkOverrides());
		getHiddenMethodList().forEachFlatMethod(x -> x.checkOverrides());
		getVirtualMethodList().forEachFlatMethod(x -> x.checkOverrides());
		getPropertyMethodList().forEachFlatMethod(x -> x.checkOverrides());
		getDestructorList().forEachFlatMethod(x -> x.checkOverrides());
	}
	
	public void searchVirtualDeclarations()
	{
		getMethodList().forEachFlatMethod(x -> x.searchVirtualMethodDeclaration());
		getConstructorList().forEachFlatMethod(x -> x.searchVirtualMethodDeclaration());
		getHiddenMethodList().forEachFlatMethod(x -> x.searchVirtualMethodDeclaration());
		getVirtualMethodList().forEachFlatMethod(x -> x.searchVirtualMethodDeclaration());
		getPropertyMethodList().forEachFlatMethod(x -> x.searchVirtualMethodDeclaration());
		getDestructorList().forEachFlatMethod(x -> x.searchVirtualMethodDeclaration());
	}
	
	public void updateGenericParameters()
	{
		getMethodList().forEachFlatMethod(x -> x.updateGenericParameters());
		getConstructorList().forEachFlatMethod(x -> x.updateGenericParameters());
		getHiddenMethodList().forEachFlatMethod(x -> x.updateGenericParameters());
		getVirtualMethodList().forEachFlatMethod(x -> x.updateGenericParameters());
		getPropertyMethodList().forEachFlatMethod(x -> x.updateGenericParameters());
		getDestructorList().forEachFlatMethod(x -> x.updateGenericParameters());
		
		if (functionMap != null)
		{
			functionMap.updateFunctionMapGenericParameters();
		}
	}
	
	private void updateFunctionMapGenericParameters()
	{
		Consumer<FlatMethodDeclarationValidator> func = method -> {
			FlatMethodDeclarationValidator corresponding = (FlatMethodDeclarationValidator)method.getProperty("correspondingFunction");
			
			if (corresponding != null)
			{
				ParameterList params = corresponding.getParameterList();
				
				int offset = method.isStatic() ? 0 : 1;
				
				for (int i = 0; i < params.getNumParameters(); i++)
				{
					ValueValidator param = params.getParameter(i);
					
					if (param.getType() != null)
					{
						method.getParameter(i + offset).setDataType(param.getDataType());
					}
				}
				
				if (corresponding.getType() != null)
				{
					method.setDataType(corresponding.getDataType());
				}
				
//				if (getScope().getNumVisibleChildren() > 0)
//				{
//					Node n = getScope().getVisibleChild(0);
//					
//					if (n instanceof Return)
//					{
//						Return r = (Return)n;
//						
//						if (r.getValueNode().getReturnedNode().isPrimitive() && !isPrimitive())
//						{
//							r.getValueNode().replaceWithAutoboxedValue();
//						}
//					}
//				}
			}
		};
		
		getMethodList().forEachFlatMethod(x -> func.accept(x));
		getConstructorList().forEachFlatMethod(x -> func.accept(x));
		getHiddenMethodList().forEachFlatMethod(x -> func.accept(x));
		getVirtualMethodList().forEachFlatMethod(x -> func.accept(x));
		getPropertyMethodList().forEachFlatMethod(x -> func.accept(x));
		getDestructorList().forEachFlatMethod(x -> func.accept(x));
	}
	
	public void checkMapOverrides()
	{
		if (functionMap != null)
		{
			functionMap.getMethodList().forEachFlatMethod(x -> x.checkOverrides());
			functionMap.getConstructorList().forEachFlatMethod(x -> x.checkOverrides());
		}
		if (propertyMap != null)
		{
			propertyMap.getMethodList().forEachFlatMethod(x -> x.checkOverrides());
			propertyMap.getConstructorList().forEachFlatMethod(x -> x.checkOverrides());
			propertyMap.getPropertyMethodList().forEachFlatMethod(x -> x.checkOverrides());
		}
	}
	
	public void addDefaultConstructor()
	{
		if (getFileDeclaration().isExternalFile()) {
			return;
		}
		addChild(Constructor.decodeStatement(this, "public construct()", Location.INVALID, true));
	}
	
	public void addDefaultDestructor()
	{
		if (getFileDeclaration().isExternalFile()) {
			return;
		}
		addChild(Destructor.decodeStatement(this, "public destroy()", Location.INVALID, true));
	}
	
	public void addAssignmentMethods()
	{
		AssignmentMethodValidator assignments = new AssignmentMethodValidator(this, Location.INVALID);
		addChild(assignments);
	}
	
	@Override
	public ClassDeclarationValidator getTypeClass(boolean checkCast, boolean defaultGenericType)
	{
		return this;
	}
	
	/**
	 * Make sure that the methods are all valid
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	private void validateMethods(int phase)
	{
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			validateConstructors(phase);
			
			if (!containsDestructor())
			{
				addDefaultDestructor();
			}
		}
		else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			ArrayList<FlatMethodDeclarationValidator> errors = new ArrayList<>();
			
			if (!isAbstract())
			{
				if (doesExtendClass() && getExtendedClassDeclaration() != null)
				{
					for (AbstractMethodDeclarationValidator method : getExtendedClassDeclaration().getAbstractMethods())
					{
						if (!doesOverrideMethod(method) && method.isUserMade())
						{
							doesOverrideMethod(method);
							errors.add(method);
						}
					}
				}
			
				ClassDeclarationValidator clazz = this;
				
				while (clazz != null)
				{
					TypeList<TraitImplementation> interfaces = clazz.getInterfacesImplementationList();
					
					if (interfaces.getNumVisibleChildren() > 0)
					{
						for (TraitImplementation inter : interfaces)
						{
							for (FlatMethodDeclarationValidator method : inter.getTypeClass().getMethods())
							{
								if (method instanceof BodyMethodDeclarationValidator == false && method.isUserMade() && !doesOverrideMethod(method))
								{
									doesOverrideMethod(method);
									errors.add(method);
								}
							}
						}
					}
					
					clazz = clazz.getParentClass();
				}
			}
			
			if (errors.size() > 0)
			{
				for (int i = 0; i < errors.size(); i++)
				{
					FlatMethodDeclarationValidator method = errors.get(i);
					
					String type = null;
					
					if (method instanceof AbstractMethodDeclarationValidator)
					{
						type = "abstract";
					}
					else
					{
						type = "interface";
					}
					
					SyntaxMessage.error("Class " + getName() + " must implement " + type + " method " + method.getName(), this, i == errors.size() - 1);
				}
			}
		}
	}
	
	private void validateExtension(int phase)
	{
		ExtendedClass extended = getExtendedClass();
		
		if (extended != null)
		{
			if (encapsulatingClass == null || phase >= SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
			{
				ClassDeclarationValidator clazz = SyntaxUtils.getImportedClass(getFileDeclaration(), extended.getType());
				
				if (clazz == null)
				{
					SyntaxUtils.getImportedClass(getFileDeclaration(), extended.getType());
					SyntaxMessage.error("Class '" + extended.getType() + "' is not imported", this);
				}
				else if (clazz instanceof Trait)
				{
					SyntaxMessage.error("Cannot extend trait '" + extended.getType() + "'", this);
				}
				
				GenericTypeArgumentList existing = extended.getGenericTypeArgumentList();
				GenericTypeParameterList required = extended.getTypeClass().getGenericTypeParameterDeclaration();
				
				for (int i = existing.getNumVisibleChildren(); i < required.getNumParameters(); i++)
				{
					GenericTypeArgument arg = new GenericTypeArgument(existing, Location.INVALID);
					
					arg.setType(required.getParameter(i).getDefaultType());
					
					existing.addChild(arg);
				}
				
				// TODO: Add extension reference here
			}
		}
	}
	
	/**
	 * Validate that all of the implemented classes have been declared
	 * and that they are valid interfaces.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	private void validateImplementations(int phase)
	{
		for (String implementedClass : getImplementedClassNames())
		{
			ClassDeclarationValidator clazz = SyntaxUtils.getImportedClass(getFileDeclaration(), implementedClass);
			
			if (clazz == null)
			{
				SyntaxMessage.error("Class '" + implementedClass + "' not declared", this);
			}
			else if (clazz instanceof Trait == false)
			{
				SyntaxMessage.error("Class '" + implementedClass + "' is not an interface and therefore cannot be implemented", this);
			}
			
			Trait i = (Trait)clazz;
			
			i.addImplementingClass(this);
		}
	}
	
	/**
	 * Get whether or not the class contains a constructor implementation
	 * or not.
	 * 
	 * @return Whether or not the class contains a constructor
	 * 		implementation or not.
	 */
	public boolean containsConstructor()
	{
		return containsMethod(getName(), true, getName());
	}
	
	/**
	 * Get whether or not the class contains a default constructor
	 * implementation or not.
	 * 
	 * @return Whether or not the class contains a default constructor
	 * 		implementation or not.
	 */
	public boolean containsDefaultConstructor()
	{
		MethodList constructors = getConstructorList();
		
		for (int i = 0; i < constructors.getNumChildren(); i++)
		{
			Constructor method = (Constructor)constructors.getChild(i);
			
			// TODO: this is a fragile way of checking.
			if (method.getParameterList().getNumChildren() == 1)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Get whether or not the class contains a destructor implementation
	 * or not.
	 * 
	 * @return Whether or not the class contains a destructor
	 * 		implementation or not.
	 */
	public boolean containsDestructor()
	{
		return containsMethod(Destructor.IDENTIFIER, false, null);
	}
	
	/**
	 * Get whether or not the Flat class contains a method with the
	 * given specifications.
	 * 
	 * @param methodName The name of the method to search for.
	 * @param staticVal Whether or not the method is static.
	 * @param type The return type of the method.
	 * @return Whether or not the class contains the method.
	 */
	public boolean containsMethod(String methodName, boolean staticVal, String type)
	{
		return containsMethod(getConstructorList(), methodName, staticVal, type) ||
				containsMethod(getDestructorList(), methodName, staticVal, type) ||
				containsMethod(getMethodList(), methodName, staticVal, type);
	}
	
	/**
	 * Get whether or not the Flat class contains a method with the
	 * given specifications.
	 * 
	 * @param root The root Node to search for the method from.
	 * @param methodName The name of the method to search for.
	 * @param staticVal Whether or not the method is static.
	 * @param type The return type of the method.
	 * @return Whether or not the class contains the method.
	 */
	public boolean containsMethod(NodeValidator root, String methodName, boolean staticVal, String type)
	{
		for (int i = 0; i < root.getNumChildren(); i++)
		{
			MethodDeclarationValidator methodDeclaration = (MethodDeclarationValidator)root.getChild(i);
			
			if (methodDeclaration.isStatic() == staticVal && methodDeclaration.getName() != null && methodDeclaration.getName().equals(methodName) && (methodDeclaration.getType() == null && type == null || methodDeclaration.getType().equals(type)))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Generate a ClassDeclaration with the given parent and location for
	 * temporary use.
	 * 
	 * @param parent The node to set as the ClassDeclaration parent.
	 * @param locationIn The location to set as the ClassDeclaration location.
	 * @return The generated temporary ClassDeclaration.
	 */
	public static ClassDeclarationValidator generateTemporaryClass(NodeValidator parent, Location locationIn)
	{
		ClassDeclarationValidator methodDeclaration = decodeStatement(parent, "public class Temp", locationIn, true);
		
		return methodDeclaration;
	}
	
	public static ClassDeclarationValidator generateTemporaryHierarchy(Flat controller)
	{
		FileDeclaration f = FileDeclaration.generateTemporaryHierarchy(controller);
		
		ClassDeclarationValidator clazz = generateTemporaryClass(f, Location.INVALID);
		f.addChild(clazz);
		
		return clazz;
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public ClassDeclarationValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ClassDeclarationValidator node = new ClassDeclarationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public ClassDeclarationValidator cloneTo(ClassDeclarationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ClassDeclarationValidator} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClassDeclarationValidator cloneTo(ClassDeclarationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.extendedClass = extendedClass;
		node.abstractValue = abstractValue;
		node.genericOverload = genericOverload;

		if (classInstanceDeclaration != null) {
			node.classInstanceDeclaration = classInstanceDeclaration.clone(node, Location.INVALID, true, true);
		}
		
		if (arrayBracketOverloadValidator != null)
		{
			node.arrayBracketOverloadValidator = arrayBracketOverloadValidator.clone(node, arrayBracketOverloadValidator.getLocationIn(), true, true);
		}
		
		return node;
	}
	
	/**
	 * Implementation of the ExtraData for this class.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.13 Jun 11, 2014 at 8:31:46 PM
	 * @version	v0.2.29 Jun 11, 2014 at 8:31:46 PM
	 */
	public static class ClassData extends DeclarationData
	{
		private boolean	extending, implementing, isInterface;
		
		public ClassData()
		{
			this(false, false, false);
		}
		
		public ClassData(boolean extending, boolean implementing, boolean isInterface)
		{
			this.extending = extending;
			this.implementing = implementing;
			this.isInterface = isInterface;
		}
	}
	
	/**
	 * Test the ClassDeclaration class type to make sure everything
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
		String s = "";
		
		s += getVisibilityText() + " class " + getName();
		
		s += getGenericTypeParameterDeclaration().toString();
		
		return s;
	}
}