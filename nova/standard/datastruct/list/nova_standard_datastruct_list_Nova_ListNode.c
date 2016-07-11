#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_ListNode.h>

nova_standard_datastruct_list_Extension_VTable_ListNode nova_standard_datastruct_list_Extension_VTable_ListNode_val =
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


void nova_standard_datastruct_list_Nova_ListNodeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_list_Nova_ListNode* nova_standard_datastruct_list_Nova_ListNode_Nova_ListNode(nova_standard_datastruct_list_Nova_ListNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_ListNode_Nova_data)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_ListNode, this,);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_ListNode_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_ListNode_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_ListNode_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_ListNode_Nova_data);
	}
	
	return this;
}

void nova_standard_datastruct_list_Nova_ListNode_Nova_destroy(nova_standard_datastruct_list_Nova_ListNode** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_standard_datastruct_list_Nova_ListNode_Nova_destroy(&(*this)->nova_standard_datastruct_list_Nova_ListNode_Nova_next, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_list_Nova_ListNode_Nova_this(nova_standard_datastruct_list_Nova_ListNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_ListNode_Nova_data)
{
	this->nova_standard_datastruct_list_Nova_ListNode_Nova_data = nova_standard_datastruct_list_Nova_ListNode_Nova_data;
}

nova_standard_datastruct_list_Nova_ListNode* nova_standard_datastruct_list_Nova_ListNode_Nova_clone(nova_standard_datastruct_list_Nova_ListNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_ListNode* l1_Nova_clone;
	
	l1_Nova_clone = nova_standard_datastruct_list_Nova_ListNode_Nova_ListNode(0, exceptionData, this->nova_standard_datastruct_list_Nova_ListNode_Nova_data);
	l1_Nova_clone->nova_standard_datastruct_list_Nova_ListNode_Nova_next = this->nova_standard_datastruct_list_Nova_ListNode_Nova_next;
	return l1_Nova_clone;
}

void nova_standard_datastruct_list_Nova_ListNode_Nova_super(nova_standard_datastruct_list_Nova_ListNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_list_Nova_ListNode_Nova_data = (nova_standard_Nova_Object*)nova_null;
	this->nova_standard_datastruct_list_Nova_ListNode_Nova_next = (nova_standard_datastruct_list_Nova_ListNode*)nova_null;
}

