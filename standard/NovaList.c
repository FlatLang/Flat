#include <precompiled.h>
#include "NovaList.h"



CCLASS_PRIVATE
(
	ListNode* nova_List_start;
	ListNode* nova_List_current;
	
)

List* nova_List_List(ExceptionData* exceptionData)
{
	CCLASS_NEW(List, this);
	
	this->prv->nova_List_start = (Object*)0;
	this->prv->nova_List_current = (Object*)0;
	{
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

ListNode* nova_List_getFirst(List* this, ExceptionData* exceptionData)
{
	return this->prv->nova_List_start;
}

void nova_List_add(List* this, ExceptionData* exceptionData, Object* nova_0_data)
{
	ListNode* nova_1_node;
	
	nova_1_node = nova_ListNode_ListNode(exceptionData, nova_0_data);
	if (this->prv->nova_List_start == (Object*)0)
	{
		this->prv->nova_List_start = nova_1_node;
		this->prv->nova_List_current = nova_1_node;
	}
	else
	{
		nova_ListNode_setNext(this->prv->nova_List_current, exceptionData, nova_1_node);
	}
	this->prv->nova_List_current = nova_1_node;
}

void nova_List_remove(List* this, ExceptionData* exceptionData, Object* nova_0_data)
{
	ListNode* nova_1_prev;
	ListNode* nova_1_cur;
	
	if (nova_ListNode_getData(this->prv->nova_List_start, exceptionData) == nova_0_data)
	{
		this->prv->nova_List_start = nova_ListNode_getNext(this->prv->nova_List_start, exceptionData);
	}
	nova_1_prev = this->prv->nova_List_start;
	nova_1_cur = nova_ListNode_getNext(this->prv->nova_List_start, exceptionData);
	while (nova_1_cur != (Object*)0)
	{
		Object* nova_3_d;
		
		nova_3_d = nova_ListNode_getData(nova_1_cur, exceptionData);
		if (nova_3_d == nova_0_data)
		{
			nova_ListNode_setNext(nova_1_prev, exceptionData, nova_ListNode_getNext(nova_1_cur, exceptionData));
		}
		nova_1_cur = nova_ListNode_getNext(nova_1_cur, exceptionData);
	}
}
