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
	},
	nova_standard_Nova_Object_3_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



void nova_standard_datastruct_Nova_BinaryTree_Mutator_Nova_root(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_BinaryNode* l0_Nova_node);
void nova_standard_datastruct_Nova_BinaryTreeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_BinaryTree* nova_standard_datastruct_Nova_BinaryTree_2_Nova_construct(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_BinaryTree, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_BinaryTree_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Tree_Nova_super((nova_standard_datastruct_Nova_Tree*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_Tree_2_Nova_this((nova_standard_datastruct_Nova_Tree*)(this), exceptionData);
	nova_standard_datastruct_Nova_BinaryTree_2_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_BinaryTree_2_Nova_this(this, exceptionData);
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

void nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data)
{
	if (nova_standard_datastruct_Nova_BinaryTree_Accessor_Nova_root(this, exceptionData) == (nova_standard_datastruct_Nova_BinaryNode*)nova_null)
	{
		nova_standard_datastruct_Nova_BinaryTree_Mutator_Nova_root(this, exceptionData, nova_standard_datastruct_Nova_BinaryNode_3_Nova_construct(0, exceptionData, l0_Nova_data));
	}
	else
	{
		nova_standard_datastruct_Nova_BinaryNode_Nova_addChild(nova_standard_datastruct_Nova_BinaryTree_Accessor_Nova_root(this, exceptionData), exceptionData, l0_Nova_data);
	}
}

void nova_standard_datastruct_Nova_BinaryTree_2_Nova_this(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryTree_Accessor_Nova_root(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_datastruct_Nova_BinaryNode*)((nova_standard_datastruct_Nova_Tree*)this)->nova_standard_datastruct_Nova_Tree_Nova_root;
}

void nova_standard_datastruct_Nova_BinaryTree_Mutator_Nova_root(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_BinaryNode* l0_Nova_node)
{
	((nova_standard_datastruct_Nova_Tree*)this)->nova_standard_datastruct_Nova_Tree_Nova_root = (nova_standard_datastruct_Nova_Node*)(l0_Nova_node);
}

void nova_standard_datastruct_Nova_BinaryTree_2_Nova_super(nova_standard_datastruct_Nova_BinaryTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

