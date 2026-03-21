package nova.c.nodewriters;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.Package;
import net.fathomsoft.nova.tree.variables.FieldList;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;
import net.fathomsoft.nova.util.Location;

import java.io.PrintWriter;

public abstract class FirstClassClosureDeclarationWriter extends ClosureDeclarationWriter
{
	public abstract FirstClassClosureDeclaration node();
	
	@Override
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
	{
		return getWriter(node().reference).generateSourceName(builder, uniquePrefix);
	}
}