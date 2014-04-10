/**
 * The Fathom Programming Language. Write Unbelievable Code.
 *  Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.Fathom;
import net.fathomsoft.fathom.error.SyntaxMessage;

/**
 * Class used to organize the Files that are fed to the compiler.
 * Each file has a file name that is stored in the name field variable
 * of this class.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Feb 18, 2014 at 8:57:00 PM
 * @version	v0.2 Apr 2, 2014 at 7:26:44 PM
 */
public class FileNode extends IdentifierNode
{
	//TODO: package name here?
	
	/**
	 * The default imports that each file uses.
	 */
	private static final String DEFAULT_IMPORTS[];
	
	/**
	 * Initialize the defaultImports constant.
	 */
	static
	{
		DEFAULT_IMPORTS = new String[]
		{
			"CClass.h",
			"ExceptionHandler.h",
			"ExceptionData"
		};
	}
	
	/**
	 * Instantiate and initialize the default values.
	 */
	public FileNode()
	{
		ImportListNode imports = new ImportListNode();
		
		super.addChild(imports);
		
		addDefaultImportNodes();
	}
	
	/**
	 * Get the ImportListNode that contains all of the imports used within
	 * the file.
	 * 
	 * @return The ImportListNode instance.
	 */
	public ImportListNode getImportListNode()
	{
		return (ImportListNode)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.IdentifierNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return "";
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof ImportNode)
		{
			getImportListNode().addChild(child);
		}
		else if (child instanceof ClassNode)
		{
			super.addChild(child);
		}
		else
		{
			SyntaxMessage.error("Unexpected statement", child.getLocationIn());
			
			//super.addChild(child);
		}
	}

	/**
	 * @see net.fathomsoft.fathom.tree.IdentifierNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		String definitionName = "FILE_" + getName() + "_" + Fathom.LANGUAGE_NAME.toUpperCase();
		
		builder.append("#ifndef ").append(definitionName).append('\n');
		builder.append("#define ").append(definitionName).append("\n\n");
		
		builder.append(generateDummyTypes()).append('\n');
		
		ImportListNode imports = getImportListNode();
		
		builder.append(imports.generateCHeaderOutput());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != imports)
			{
				builder.append(child.generateCHeaderOutput());
			}
		}
		
		builder.append("#endif");
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.IdentifierNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		ImportNode thisImport = new ImportNode();
		thisImport.setImportLocation(getName());
		
		builder.append(thisImport.generateCSourceOutput());
		
		if (getImportListNode().getChildren().size() <= 0)
		{
			builder.append('\n');
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateCSourceOutput());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
	}
	
	/**
	 * Add all of the default imports that each file must include. The
	 * default imports are included within the {@link #DEFAULT_IMPORTS}
	 * array.
	 */
	private void addDefaultImportNodes()
	{
		for (String importLoc : DEFAULT_IMPORTS)
		{
			ImportNode importNode = new ImportNode();
			
			if (importLoc.endsWith(".h"))
			{
				importLoc = importLoc.substring(0, importLoc.length() - 2);
				
				importNode.setExternal(true);
			}
			
			importNode.setImportLocation(importLoc);
			
			addChild(importNode);
		}
	}
	
	/**
	 * Generate dummy class declarations for each of the imported files.
	 * This is needed in a situation when a class imports a class that
	 * in returns needs to import the respective one. In other words,
	 * the chicken vs egg scenario.
	 * 
	 * @return The generated code used for generating the dummy class
	 * 		types.
	 */
	private String generateDummyTypes()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof ClassNode)
			{
				ClassNode clazz = (ClassNode)child;
				
				builder.append("typedef struct ").append(clazz.getName()).append(' ').append(clazz.getName()).append(';').append('\n');
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public FileNode clone()
	{
		FileNode node = new FileNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given FileNde with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FileNode clone(FileNode node)
	{
		super.clone(node);
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}