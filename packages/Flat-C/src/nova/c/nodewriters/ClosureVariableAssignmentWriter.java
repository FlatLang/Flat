package nova.c.nodewriters;

//import net.fathomsoft.nova.Nova;
//import net.fathomsoft.nova.tree.ClosureDeclaration;
//import net.fathomsoft.nova.tree.ClosureVariableAssignment;
//
//public abstract class ClosureVariableAssignmentWriter extends NodeWriter
//{
//	public abstract ClosureVariableAssignment node();
//
//	@Override
//	public StringBuilder generateSource(StringBuilder builder) {
//		boolean heap = node().declaration.requiresHeapAllocation && node().declaration.getRootDeclaration() instanceof ClosureDeclaration == false;
//
//		if (heap) {
//			return getWriter(node().declaration).generateValueAssignment(builder);
//		} else {
//			return builder;
//		}
//	}
//}