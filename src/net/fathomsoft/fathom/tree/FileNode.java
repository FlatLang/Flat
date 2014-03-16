package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.Fathom;
import net.fathomsoft.fathom.error.SyntaxMessage;

/**
 * Class used to organize the Files that are fed to the compiler.
 * Each file has a file name that is stored in the name field variable
 * of this class.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 18, 2014 at 8:57:00 PM
 * @since	v0.1
 * @version	Feb 18, 2014 at 8:57:00 PM
 * @version	v0.1
 */
public class FileNode extends IdentifierNode
{
	//TODO: package name here?
	
	public FileNode()
	{
		ImportListNode imports = new ImportListNode();
		
		super.addChild(imports);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.IdentifierNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return "";
	}
	
	public ImportListNode getImportListNode()
	{
		return (ImportListNode)getChild(0);
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
		
		ImportNode cclassImport = new ImportNode();
		cclassImport.setImportLocation("CClass");
		
		builder.append(cclassImport.generateCHeaderOutput());
		
		if (getImportListNode().getChildren().size() <= 0)
		{
			builder.append('\n');
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateCHeaderOutput());
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
		
		ImportNode cclassImport = new ImportNode();
		cclassImport.setImportLocation("CClass");
		
		ImportNode thisImport = new ImportNode();
		thisImport.setImportLocation(getName());
		
		builder.append(cclassImport.generateCHeaderOutput());
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
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public FileNode clone()
	{
		FileNode clone = new FileNode();
		clone.setName(getName());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}