#include <precompiled.h>
#include <nova/datastruct/nova_datastruct_Nova_BinaryTree.h>



nova_datastruct_Extension_VTable_BinaryTree nova_datastruct_Extension_VTable_BinaryTree_val =
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
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void nova_datastruct_Nova_BinaryTree_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_Nova_BinaryTree* nova_datastruct_Nova_BinaryTree_Nova_construct(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* nova_datastruct_Nova_BinaryTree_Nova_data)
{
	CCLASS_NEW(nova_datastruct_Nova_BinaryTree, this,);
	this->vtable = &nova_datastruct_Extension_VTable_BinaryTree_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_Tree_Nova_super((nova_datastruct_Nova_Tree*)this, exceptionData);
	nova_datastruct_Nova_BinaryTree_0_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_Nova_BinaryTree_1_Nova_this(this, exceptionData, nova_datastruct_Nova_BinaryTree_Nova_data);
	}
	
	return this;
}

void nova_datastruct_Nova_BinaryTree_Nova_destroy(nova_datastruct_Nova_BinaryTree** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_datastruct_Nova_BinaryTree_1_Nova_this(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* nova_datastruct_Nova_BinaryTree_Nova_data)
{
	nova_datastruct_Nova_BinaryTree_Nova_addNodes(this, exceptionData, nova_datastruct_Nova_BinaryTree_Nova_data);
}

nova_datastruct_Nova_BinaryTree* nova_datastruct_Nova_BinaryTree_Nova_addNode(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Comparable* nova_datastruct_Nova_BinaryTree_Nova_data)
{
	if (nova_datastruct_Nova_BinaryTree_Accessor_Nova_root(this, exceptionData) == (nova_datastruct_Nova_BinaryNode*)nova_null)
	{
		nova_datastruct_Nova_BinaryTree_Mutator_Nova_root(this, exceptionData, (nova_datastruct_Nova_BinaryNode*)(nova_datastruct_Nova_BinaryNode_1_Nova_construct(0, exceptionData, (nova_datastruct_Nova_Comparable*)(nova_datastruct_Nova_BinaryTree_Nova_data))));
	}
	else
	{
		nova_datastruct_Nova_BinaryNode_Nova_addChild(nova_datastruct_Nova_BinaryTree_Accessor_Nova_root(this, exceptionData), exceptionData, (nova_datastruct_Nova_Comparable*)(nova_datastruct_Nova_BinaryTree_Nova_data));
	}
	return this;
}

nova_datastruct_Nova_BinaryTree* nova_datastruct_Nova_BinaryTree_Nova_addNodes(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* nova_datastruct_Nova_BinaryTree_Nova_data)
{
	nova_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_datastruct_Nova_Comparable* l1_Nova_d = (nova_datastruct_Nova_Comparable*)nova_null;
	
	nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((nova_datastruct_Nova_BinaryTree_Nova_data)), exceptionData));
	while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_d = (nova_datastruct_Nova_Comparable*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_datastruct_Nova_BinaryTree_Nova_addNode(this, exceptionData, l1_Nova_d);
	}
	return this;
}

nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryTree_Accessor_Nova_root(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_datastruct_Nova_BinaryNode*)((nova_datastruct_Nova_Tree*)this)->nova_datastruct_Nova_Tree_Nova_root;
}

nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryTree_Mutator_Nova_root(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryTree_Nova_node)
{
	((nova_datastruct_Nova_Tree*)this)->nova_datastruct_Nova_Tree_Nova_root = (nova_datastruct_Nova_Node*)(nova_datastruct_Nova_BinaryTree_Nova_node);
	return nova_datastruct_Nova_BinaryTree_Nova_node;
}

void nova_datastruct_Nova_BinaryTree_0_Nova_super(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

