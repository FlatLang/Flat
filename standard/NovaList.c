#include <precompiled.h>
#include "NovaList.h"


nova_VTable_List nova_VTable_List_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};
CCLASS_PRIVATE
(
	ListNode* nova_List_start;
	ListNode* nova_List_current;
	
)

List* nova_0_List_construct(List* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(List, this);
	this->vtable = &nova_VTable_List_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_List_super(this, 0);
	
	{
		nova_List_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_List(List** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ListNode(&(*this)->prv->nova_List_start, exceptionData);
	nova_del_ListNode(&(*this)->prv->nova_List_current, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

ListNode* nova_List_E(List* this, ExceptionData* exceptionData)
{
	return this->prv->nova_List_start;
}

void nova_List_add(List* this, ExceptionData* exceptionData, Object* nova_0_data)
{
	ListNode* nova_1_node;
	
	nova_1_node = nova_ListNode_construct(0, exceptionData, nova_0_data);
	if (this->prv->nova_List_start == (ListNode*)0)
	{
		this->prv->nova_List_start = nova_1_node;
		this->prv->nova_List_current = nova_1_node;
	}
	else
	{
		this->prv->nova_List_current->nova_ListNode_next = nova_1_node;
	}
	this->prv->nova_List_current = nova_1_node;
}

void nova_List_remove(List* this, ExceptionData* exceptionData, Object* nova_0_data)
{
	ListNode* nova_1_prev;
	ListNode* nova_1_cur;
	
	if (this->prv->nova_List_start->nova_ListNode_data == nova_0_data)
	{
		this->prv->nova_List_start = this->prv->nova_List_start->nova_ListNode_next;
	}
	nova_1_prev = this->prv->nova_List_start;
	nova_1_cur = this->prv->nova_List_start->nova_ListNode_next;
	while (nova_1_cur != (ListNode*)0)
	{
		Object* nova_3_d;
		
		nova_3_d = nova_1_cur->nova_ListNode_data;
		if (nova_3_d == nova_0_data)
		{
			nova_1_prev->nova_ListNode_next = nova_1_cur->nova_ListNode_next;
		}
		nova_1_cur = nova_1_cur->nova_ListNode_next;
	}
}

void nova_List_this(List* this, ExceptionData* exceptionData)
{
}

void nova_List_super(List* this, ExceptionData* exceptionData)
{
	this->prv->nova_List_start = (ListNode*)0;
	this->prv->nova_List_current = (ListNode*)0;
}
