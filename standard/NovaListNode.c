#include <precompiled.h>
#include "NovaListNode.h"


nova_VTable_ListNode nova_VTable_ListNode_val =
{
	nova_2_Object_toString,
	nova_2_Object_equals,
};
CCLASS_PRIVATE
(
	Object* nova_ListNode_data;
	ListNode* nova_ListNode_next;
	
)

ListNode* nova_ListNode_construct(ListNode* this, ExceptionData* exceptionData, Object* nova_0_data)
{
	CCLASS_NEW(ListNode, this);
	
	this->prv->nova_ListNode_data = (Object*)0;
	this->prv->nova_ListNode_next = (ListNode*)0;
	this->vtable = &nova_VTable_ListNode_val;
	{
		this->prv->nova_ListNode_data = nova_0_data;
	}
	
	return this;
}

void nova_del_ListNode(ListNode** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_Object(&(*this)->prv->nova_ListNode_data, exceptionData);
	nova_del_ListNode(&(*this)->prv->nova_ListNode_next, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

Object* nova_ListNode_getData(ListNode* this, ExceptionData* exceptionData)
{
	return this->prv->nova_ListNode_data;
}

ListNode* nova_ListNode_getNext(ListNode* this, ExceptionData* exceptionData)
{
	return this->prv->nova_ListNode_next;
}

void nova_ListNode_setNext(ListNode* this, ExceptionData* exceptionData, ListNode* nova_0_next)
{
	this->prv->nova_ListNode_next = nova_0_next;
}
