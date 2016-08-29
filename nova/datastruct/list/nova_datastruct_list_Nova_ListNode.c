#include <precompiled.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_ListNode.h>



nova_datastruct_list_Extension_VTable_ListNode nova_datastruct_list_Extension_VTable_ListNode_val =
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


void nova_datastruct_list_Nova_ListNode_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_list_Nova_ListNode* nova_datastruct_list_Nova_ListNode_Nova_construct(nova_datastruct_list_Nova_ListNode* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_list_Nova_ListNode_Nova_data)
{
	CCLASS_NEW(nova_datastruct_list_Nova_ListNode, this,);
	this->vtable = &nova_datastruct_list_Extension_VTable_ListNode_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_list_Nova_ListNode_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_list_Nova_ListNode_Nova_this(this, exceptionData, nova_datastruct_list_Nova_ListNode_Nova_data);
	}
	
	return this;
}

void nova_datastruct_list_Nova_ListNode_Nova_destroy(nova_datastruct_list_Nova_ListNode** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_datastruct_list_Nova_ListNode_Nova_destroy(&(*this)->nova_datastruct_list_Nova_ListNode_Nova_next, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_datastruct_list_Nova_ListNode_Nova_this(nova_datastruct_list_Nova_ListNode* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_list_Nova_ListNode_Nova_data)
{
	this->nova_datastruct_list_Nova_ListNode_Nova_data = nova_datastruct_list_Nova_ListNode_Nova_data;
}

nova_datastruct_list_Nova_ListNode* nova_datastruct_list_Nova_ListNode_Nova_clone(nova_datastruct_list_Nova_ListNode* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_datastruct_list_Nova_ListNode* l1_Nova_clone = (nova_datastruct_list_Nova_ListNode*)nova_null;
	
	l1_Nova_clone = nova_datastruct_list_Nova_ListNode_Nova_construct(0, exceptionData, this->nova_datastruct_list_Nova_ListNode_Nova_data);
	l1_Nova_clone->nova_datastruct_list_Nova_ListNode_Nova_next = this->nova_datastruct_list_Nova_ListNode_Nova_next;
	return l1_Nova_clone;
}

void nova_datastruct_list_Nova_ListNode_Nova_super(nova_datastruct_list_Nova_ListNode* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_datastruct_list_Nova_ListNode_Nova_data = (nova_Nova_Object*)nova_null;
	this->nova_datastruct_list_Nova_ListNode_Nova_next = (nova_datastruct_list_Nova_ListNode*)nova_null;
}

