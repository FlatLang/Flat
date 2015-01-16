#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Tree.h>


nova_standard_datastruct_VTable_Tree nova_standard_datastruct_VTable_Tree_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
void nova_standard_datastruct_Nova_TreeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Tree* nova_standard_datastruct_Nova_Tree_2_Nova_construct(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Tree, this,);
	this->vtable = &nova_standard_datastruct_VTable_Tree_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
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

nova_standard_Nova_String* nova_standard_datastruct_Nova_Tree_Nova_preorder(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_Nova_Tree_Nova_root->vtable->nova_standard_datastruct_Nova_Node_virtual0_Nova_preorder(this->nova_standard_datastruct_Nova_Tree_Nova_root, exceptionData);
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Tree_Nova_inorder(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_Nova_Tree_Nova_root->vtable->nova_standard_datastruct_Nova_Node_virtual0_Nova_inorder(this->nova_standard_datastruct_Nova_Tree_Nova_root, exceptionData);
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Tree_Nova_postorder(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_Nova_Tree_Nova_root->vtable->nova_standard_datastruct_Nova_Node_virtual0_Nova_postorder(this->nova_standard_datastruct_Nova_Tree_Nova_root, exceptionData);
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Tree_Nova_levelorder(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	THROW(5, nova_standard_exception_Nova_UnimplementedOperationException_1_Nova_construct(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Level order has not been implemented yet... hold on.")));
}

void nova_standard_datastruct_Nova_Tree_2_Nova_this(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_datastruct_Nova_Tree_Nova_super(nova_standard_datastruct_Nova_Tree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_Tree_Nova_root = (nova_standard_datastruct_Nova_Node*)nova_null;
}

