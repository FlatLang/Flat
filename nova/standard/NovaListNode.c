#include <precompiled.h>
#include "NovaListNode.h"


nova_VTable_ListNode nova_VTable_ListNode_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

ListNode* nova_ListNode_construct(ListNode* this, ExceptionData* exceptionData, Object* nova_0_data)
{
	CCLASS_NEW(ListNode, this,);
	this->vtable = &nova_VTable_ListNode_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_ListNode_super(this, 0);
	
	{
		nova_ListNode_this(this, exceptionData, nova_0_data);
	}
	
	return this;
}

void nova_del_ListNode(ListNode** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_del_ListNode(&(*this)->nova_ListNode_next, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_ListNode_this(ListNode* this, ExceptionData* exceptionData, Object* nova_0_data)
{
	this->nova_ListNode_data = nova_0_data;
}

void nova_ListNode_super(ListNode* this, ExceptionData* exceptionData)
{
	this->nova_ListNode_data = (Object*)nova_null;
	this->nova_ListNode_next = (ListNode*)nova_null;
}
