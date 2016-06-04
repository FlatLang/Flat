#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_ListNode.h>

nova_standard_datastruct_Extension_VTable_ListNode nova_standard_datastruct_Extension_VTable_ListNode_val =
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_datastruct_Nova_ListNodeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_ListNode* nova_standard_datastruct_Nova_ListNode_Nova_construct(nova_standard_datastruct_Nova_ListNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_ListNode_Nova_data)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_ListNode, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_ListNode_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_ListNode_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_ListNode_Nova_this(this, exceptionData, nova_standard_datastruct_Nova_ListNode_Nova_data);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_ListNode_Nova_destroy(nova_standard_datastruct_Nova_ListNode** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_standard_datastruct_Nova_ListNode_Nova_destroy(&(*this)->nova_standard_datastruct_Nova_ListNode_Nova_next, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_ListNode_Nova_this(nova_standard_datastruct_Nova_ListNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_ListNode_Nova_data)
{
	this->nova_standard_datastruct_Nova_ListNode_Nova_data = nova_standard_datastruct_Nova_ListNode_Nova_data;
}

void nova_standard_datastruct_Nova_ListNode_Nova_super(nova_standard_datastruct_Nova_ListNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_ListNode_Nova_data = (nova_standard_Nova_Object*)nova_null;
	this->nova_standard_datastruct_Nova_ListNode_Nova_next = (nova_standard_datastruct_Nova_ListNode*)nova_null;
}

