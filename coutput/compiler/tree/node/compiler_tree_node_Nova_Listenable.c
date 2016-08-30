#include <precompiled.h>
#include <compiler/tree/node/compiler_tree_node_Nova_Listenable.h>



compiler_tree_node_Extension_VTable_Listenable compiler_tree_node_Extension_VTable_Listenable_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	compiler_tree_node_Nova_Listenable_virtual1_Nova_onAdded,
};


CCLASS_PRIVATE
(
	nova_datastruct_list_Nova_Array* compiler_tree_node_Nova_Listenable_Nova_listeners;
	
)
void compiler_tree_node_Nova_Listenable_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}


void compiler_tree_node_Nova_Listenable_virtual1_Nova_onAdded(compiler_tree_node_Nova_Listenable* this, nova_exception_Nova_ExceptionData* exceptionData, compiler_tree_node_Nova_Node* compiler_tree_node_Nova_Listenable_Nova_parent)
{
	this->vtable->itable.compiler_tree_node_Nova_Listenable_virtual1_Nova_onAdded((compiler_tree_node_Nova_Listenable*)(this), exceptionData, compiler_tree_node_Nova_Listenable_Nova_parent);
}

