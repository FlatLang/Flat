#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Tree.h>

nova_standard_datastruct_Extension_VTable_Tree nova_standard_datastruct_Extension_VTable_Tree_val =
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


void nova_standard_datastruct_Nova_TreeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Tree* nova_standard_datastruct_Nova_Tree_Nova_Tree(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Tree, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Tree_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Tree_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Tree_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_Tree_Nova_destroy(nova_standard_datastruct_Nova_Tree** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_Nova_Node_Nova_destroy(&(*this)->nova_standard_datastruct_Nova_Tree_Nova_root, exceptionData);
	
	NOVA_FREE(*this);
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Tree_Nova_preorder(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_Nova_Node_0_Nova_preorder(this->nova_standard_datastruct_Nova_Tree_Nova_root, exceptionData);
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Tree_Nova_inorder(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_Nova_Node_0_Nova_inorder(this->nova_standard_datastruct_Nova_Tree_Nova_root, exceptionData);
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Tree_Nova_postorder(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_Nova_Node_0_Nova_postorder(this->nova_standard_datastruct_Nova_Tree_Nova_root, exceptionData);
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Tree_Nova_levelorder(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_Nova_Node_0_Nova_levelorder(this->nova_standard_datastruct_Nova_Tree_Nova_root, exceptionData);
}

void nova_standard_datastruct_Nova_Tree_2_Nova_this(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_datastruct_Nova_Tree_Nova_super(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_Tree_Nova_root = (nova_standard_datastruct_Nova_Node*)nova_null;
}

