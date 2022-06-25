package net.fathomsoft.nova;

import net.fathomsoft.nova.tree.BodyMethodDeclaration;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Import;
import net.fathomsoft.nova.tree.Program;
import net.fathomsoft.nova.util.Location;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.27 Aug 6, 2014 at 3:25:42 PM
 * @version	v0.2.31 Sep 24, 2014 at 4:41:04 PM
 */
public class TestContext
{
	private Nova					controller;
	
	public Program					program;
	
	public ClassDeclaration			clazz;
	
	public BodyMethodDeclaration	method;
	
	public TestContext()
	{
		controller = Nova.generateTemporaryController(new String[] {});
		
		reset();
	}
	
	public void importClass(String className)
	{
		Import importNode = Import.decodeStatement(clazz, "import \"" + className + "\"", Location.INVALID, true);
		
		clazz.getFileDeclaration().addChild(importNode);
	}
	
	public void reset()
	{
		method  = BodyMethodDeclaration.generateTemporaryHierarchy(controller);
		clazz   = method.getParentClass();
		program = clazz.getProgram();
	}
}