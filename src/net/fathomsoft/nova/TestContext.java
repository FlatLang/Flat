package net.fathomsoft.nova;

import net.fathomsoft.nova.tree.BodyMethodDeclaration;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Program;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.27 Aug 6, 2014 at 3:25:42 PM
 * @version	v0.2.27 Aug 7, 2014 at 1:32:02 AM
 */
public class TestContext
{
	private Nova					controller;
	
	public Program					program;
	
	public ClassDeclaration			clazz;
	
	public BodyMethodDeclaration	method;
	
	public TestContext()
	{
		controller = Nova.generateTemporaryController();
		
		reset();
	}
	
	public void reset()
	{
		method  = BodyMethodDeclaration.generateTemporaryHierarchy(controller);
		clazz   = method.getParentClass();
		program = clazz.getProgram();
	}
}