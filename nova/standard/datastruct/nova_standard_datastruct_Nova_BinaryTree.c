#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_BinaryTree.h>

nova_standard_datastruct_Extension_VTable_BinaryTree nova_standard_datastruct_Extension_VTable_BinaryTree_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryTree_Mutator_Nova_root(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryTree_Nova_node);
void nova_standard_datastruct_Nova_BinaryTreeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_BinaryTree* nova_standard_datastruct_Nova_BinaryTree_Nova_BinaryTree(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_BinaryTree_Nova_data)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_BinaryTree, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_BinaryTree_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Tree_Nova_super((nova_standard_datastruct_Nova_Tree*)this, exceptionData);
	nova_standard_datastruct_Nova_BinaryTree_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_BinaryTree_1_Nova_this(this, exceptionData, nova_standard_datastruct_Nova_BinaryTree_Nova_data);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_BinaryTree_Nova_destroy(nova_standard_datastruct_Nova_BinaryTree** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_BinaryTree_1_Nova_this(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_BinaryTree_Nova_data)
{
	nova_standard_datastruct_Nova_BinaryTree_Nova_addNodes(this, exceptionData, nova_standard_datastruct_Nova_BinaryTree_Nova_data);
}

nova_standard_datastruct_Nova_BinaryTree* nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Comparable* nova_standard_datastruct_Nova_BinaryTree_Nova_data)
{
	if (nova_standard_datastruct_Nova_BinaryTree_Accessor_Nova_root(this, exceptionData) == (nova_standard_datastruct_Nova_BinaryNode*)nova_null)
	{
		nova_standard_datastruct_Nova_BinaryTree_Mutator_Nova_root(this, exceptionData, nova_standard_datastruct_Nova_BinaryNode_1_Nova_BinaryNode(0, exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_datastruct_Nova_BinaryTree_Nova_data)));
	}
	else
	{
		nova_standard_datastruct_Nova_BinaryNode_Nova_addChild(nova_standard_datastruct_Nova_BinaryTree_Accessor_Nova_root(this, exceptionData), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_datastruct_Nova_BinaryTree_Nova_data));
	}
	return this;
}

nova_standard_datastruct_Nova_BinaryTree* nova_standard_datastruct_Nova_BinaryTree_Nova_addNodes(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_BinaryTree_Nova_data)
{
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_datastruct_Nova_Comparable* l1_Nova_d = (nova_standard_datastruct_Nova_Comparable*)nova_null;
	
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((nova_standard_datastruct_Nova_BinaryTree_Nova_data)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_d = (nova_standard_datastruct_Nova_Comparable*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(this, exceptionData, l1_Nova_d);
	}
	return this;
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryTree_Accessor_Nova_root(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_datastruct_Nova_BinaryNode*)((nova_standard_datastruct_Nova_Tree*)this)->nova_standard_datastruct_Nova_Tree_Nova_root;
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryTree_Mutator_Nova_root(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryTree_Nova_node)
{
	((nova_standard_datastruct_Nova_Tree*)this)->nova_standard_datastruct_Nova_Tree_Nova_root = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_Nova_BinaryTree_Nova_node);
	return nova_standard_datastruct_Nova_BinaryTree_Nova_node;
}

void nova_standard_datastruct_Nova_BinaryTree_0_Nova_super(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

