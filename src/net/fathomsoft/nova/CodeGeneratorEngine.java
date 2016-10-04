package net.fathomsoft.nova;

import net.fathomsoft.nova.tree.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class CodeGeneratorEngine
{
	public Nova controller;
	
	public SyntaxTree tree;
	
	public String				mainClass;
	
	public CodeGeneratorEngine(Nova controller)
	{
		this.controller = controller;
		this.tree = controller.getTree();
	}
	
	/**
	 * Generate the C Source and Header output from the data contained
	 * within the syntax tree.
	 */
	public abstract void generateOutput();
	
	public abstract void formatOutput();
	
	public abstract void writeFiles();
	
	/**
	 * Insert the main method into the correct file. Also set up the
	 * initialization for the program within the main method.
	 */
	public abstract void insertMainMethod();
}