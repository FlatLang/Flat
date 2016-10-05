package net.fathomsoft.nova;

import net.fathomsoft.nova.tree.FileDeclaration;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.util.Command;
import net.fathomsoft.nova.util.CommandListener;
import net.fathomsoft.nova.util.FileUtils;
import net.fathomsoft.nova.util.StringUtils;

import java.io.File;
import java.io.IOException;

import static java.util.Arrays.stream;

public abstract class CompileEngine
{
	public Nova controller;
	
	public SyntaxTree tree;
	
	public CompileEngine(Nova controller)
	{
		this.controller = controller;
		this.tree = controller.getTree();
	}
	
	/**
	 * Compile the generated code into an executable file.
	 */
	public abstract void compile();
	
	public boolean checkArgument(String arg)
	{
		return false;
	}
	
	public boolean checkArgument(String arg, String[] args, int index)
	{
		return checkArgument(arg);
	}
}