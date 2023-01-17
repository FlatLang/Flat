package flat.tree;

import flat.Flat;
import flat.TestContext;
import flat.ValidationResult;
import flat.tree.variables.FieldDeclaration;
import flat.util.Location;
import flat.util.SyntaxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * {@link ClassDeclaration} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.38 Nov 26, 2014 at 7:17:45 PM
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class Trait extends ClassDeclaration
{
	public static final String IDENTIFIER = "trait";
	
	public final ArrayList<ClassDeclaration> implementingClasses = new ArrayList<>();
	
	/**
	 * @see Node#Node(Node, Location)
	 */
	public Trait(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public void addImplementingClass(ClassDeclaration clazz)
	{
		implementingClasses.add(clazz);
	}
	
	/**
	 * @see ClassDeclaration#isAbstract()
	 */
	@Override
	public boolean isAbstract()
	{
		return true;
	}
	
	/**
	 * @see ClassDeclaration#getExtendedClassLocation()
	 */
	@Override
	public String getExtendedClassLocation()
	{
		String extended = super.getExtendedClassLocation();
		
		return extended != null ? (!extended.equals("flat/Object") ? extended : null) : null;
	}
	
	/**
	 * Decode the given statement into a {@link Trait} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public interface TestInterface</li>
	 * 	<li>public interface Stream</li>
	 * 	<li>public interface InterfaceName</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link Trait} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link Trait}.
	 */
	public static Trait decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		int index = SyntaxUtils.findStringInBaseScope(statement, IDENTIFIER);
		
		if (index >= 0)
		{
			statement = statement.substring(0, index) + ClassDeclaration.IDENTIFIER + statement.substring(index + IDENTIFIER.length());
			
			ClassData data = new ClassData(false, false, true);
			
			ClassDeclaration clazz = decodeStatement(parent, statement, location, require, data);
			
			if (clazz != null)
			{
				Trait n = new Trait(parent, location);
				
				clazz.cloneTo(n);
				//n.setExtendedClass(null);
				
				return n;
			}
		}
		
		return null;
	}
	
	@Override
	public MethodDeclaration[] getMethods(String methodName, MethodList.SearchFilter filter)
	{
		MethodDeclaration[] methods = super.getMethods(methodName, filter);
		
		if (filter.checkAncestor && (!Flat.objectClassType.equals("trait") || doesExtendClass()))
		{
			ClassDeclaration obj = getExtendedClass().getTypeClass();//getProgram().getClassDeclaration("flat/Object");
			
			boolean before = filter.checkInterfaces;
			
			if (obj.getClassLocation().equals("flat/Object") && obj.implementsInterface(this, false))
			{
				filter.checkInterfaces = false;
			}
			else
			{
				for (Trait i : obj.getImplementedInterfaces())
				{
					if (i == this)
					{
						return methods;
					}
				}
			}
			
			MethodDeclaration[] extra = obj.getMethods(methodName, filter);
			
			if (obj.getClassLocation().equals("flat/Object") && obj.implementsInterface(this, false))
			{
				filter.checkInterfaces = before;
			}
			
			if (extra.length > 0)
			{
				ArrayList<MethodDeclaration> list = new ArrayList<>();
				
				for (MethodDeclaration method : methods)
				{
					list.add(method);
				}
				
				for (MethodDeclaration method : extra)
				{
					list.add(method);
				}
				
				return list.toArray(new MethodDeclaration[0]);
			}
		}
		
		return methods;
	}
	
	@Override
	public boolean isRelatedTo(ClassDeclaration node)
	{
		return true;
	}
	
	@Override
	public void addDefaultConstructor()
	{
		if (isPropertyTrue("functionMap"))
		{
			super.addDefaultConstructor();
		}
	}
	
	@Override
	public void addDefaultDestructor()
	{
		if (isPropertyTrue("functionMap"))
		{
			super.addDefaultDestructor();
		}
	}
	
	@Override
	public void addAssignmentMethods()
	{
		if (isPropertyTrue("functionMap"))
		{
			super.addAssignmentMethods();
		}
	}
	
	@Override
	public void autoAddInterfaceFieldOverrides()
	{
		if (isPropertyTrue("functionMap"))
		{
			super.autoAddInterfaceFieldOverrides();
		}
	}
	
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
			Consumer<Node> addDefaultPropertyFunctions = n -> {
				if (n.isUserMade() || n.containsProperty("genericOverload"))
				{
					FieldDeclaration field = (FieldDeclaration)n;
					
					if (field.getShorthandAccessor() == null && !field.containsAccessorMethod() && !field.containsMutatorMethod() && !isPrimitiveOverload())
					{
						field.addDefaultAccessor();

//						if (field.getVisibility() == PUBLIC)
//						{
						field.addDefaultMutator();
//						}
//						else
//						{
//							field.addDisabledMutator();
//						}
						
						field.setProperty("addedDefaultInterfaceFunctions", true);
					}
				}
			};
			
			getFieldList().getPublicFieldList().forEachVisibleChild(addDefaultPropertyFunctions);
			getFieldList().getPrivateFieldList().forEachVisibleChild(addDefaultPropertyFunctions);
		}
		if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			getPropertyMethodList().forEachFlatMethod(m -> {
				m.setVisibility(PUBLIC);
				
				if (m.getVirtualMethod() != null)
				{
					m.getVirtualMethod().setVisibility(PUBLIC);
				}
				
				Arrays.stream(m.getOverridingMethods()).forEach(x -> x.setVisibility(PUBLIC));
			});
			getFieldList().getPrivateStaticFieldList().forEachVisibleChild(x -> {
				FieldDeclaration field = (FieldDeclaration)x;
				
				field.setVisibility(PUBLIC);
				getFieldList().getPublicStaticFieldList().addChild(field);
			});
		}
		
		return result;
	}
	
	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public Trait clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Trait node = new Trait(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public Trait cloneTo(Trait node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Trait} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Trait cloneTo(Trait node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link Trait} class type to make sure everything
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