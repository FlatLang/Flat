#include <precompiled.h>
#include <nova/datastruct/nova_datastruct_Nova_Tree.h>



nova_datastruct_Extension_VTable_Tree nova_datastruct_Extension_VTable_Tree_val =
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


void nova_datastruct_Nova_Tree_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_Nova_Tree* nova_datastruct_Nova_Tree_Nova_construct(nova_datastruct_Nova_Tree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_datastruct_Nova_Tree, this,);
	this->vtable = &nova_datastruct_Extension_VTable_Tree_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_Tree_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_Nova_Tree_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_datastruct_Nova_Tree_Nova_destroy(nova_datastruct_Nova_Tree** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_datastruct_Nova_Node_Nova_destroy(&(*this)->nova_datastruct_Nova_Tree_Nova_root, exceptionData);
	
	NOVA_FREE(*this);
}

nova_datastruct_list_Nova_Array* nova_datastruct_Nova_Tree_Nova_preorder(nova_datastruct_Nova_Tree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_datastruct_list_Nova_Array*)nova_datastruct_Nova_Node_0_Nova_preorder(this->nova_datastruct_Nova_Tree_Nova_root, exceptionData);
}

nova_datastruct_list_Nova_Array* nova_datastruct_Nova_Tree_Nova_inorder(nova_datastruct_Nova_Tree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_datastruct_list_Nova_Array*)nova_datastruct_Nova_Node_0_Nova_inorder(this->nova_datastruct_Nova_Tree_Nova_root, exceptionData);
}

nova_datastruct_list_Nova_Array* nova_datastruct_Nova_Tree_Nova_postorder(nova_datastruct_Nova_Tree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_datastruct_list_Nova_Array*)nova_datastruct_Nova_Node_0_Nova_postorder(this->nova_datastruct_Nova_Tree_Nova_root, exceptionData);
}

nova_datastruct_list_Nova_Array* nova_datastruct_Nova_Tree_Nova_levelorder(nova_datastruct_Nova_Tree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_datastruct_list_Nova_Array*)nova_datastruct_Nova_Node_0_Nova_levelorder(this->nova_datastruct_Nova_Tree_Nova_root, exceptionData);
}

void nova_datastruct_Nova_Tree_2_Nova_this(nova_datastruct_Nova_Tree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_datastruct_Nova_Tree_Nova_super(nova_datastruct_Nova_Tree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_datastruct_Nova_Tree_Nova_root = (nova_datastruct_Nova_Node*)nova_null;
}

