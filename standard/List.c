#include "List.h"
#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
#include "DivideByZeroException.h"
#include "ListNode.h"

CCLASS_PRIVATE
(
	ListNode* nova_List_start;
	ListNode* nova_List_current;
	
)


List* nova_List_List(ExceptionData* exceptionData)
{
	CCLASS_NEW(List, this);
	
	this->prv->nova_List_start = 0;
	this->prv->nova_List_current = 0;
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
	free((*this)->prv);
	
	{
	}
	free(*this);
}

ListNode* nova_List_getFirst(List* this, ExceptionData* exceptionData)
{
	return this->prv->nova_List_start;
}

void nova_List_add(List* this, ExceptionData* exceptionData, Object* nova_0_data)
{
	ListNode* nova_290_node;
	
	nova_290_node = nova_ListNode_ListNode(exceptionData, nova_0_data);
	if (this->prv->nova_List_start == 0)
	{
		this->prv->nova_List_start = nova_290_node;
		this->prv->nova_List_current = nova_290_node;
	}
	else
	{
		ListNode* nova_List_current;
	}
	this->prv->nova_List_current = nova_290_node;
}

void nova_List_remove(List* this, ExceptionData* exceptionData, Object* nova_0_data)
{
	ListNode* nova_293_prev;
	ListNode* nova_293_cur;
	
	if (nova_ListNode_getData(this->prv->nova_List_start, exceptionData) == nova_0_data)
	{
		this->prv->nova_List_start = nova_ListNode_getNext(this->prv->nova_List_start, exceptionData);
	}
	nova_293_prev = this->prv->nova_List_start;
	nova_293_cur = nova_ListNode_getNext(this->prv->nova_List_start, exceptionData);
	while (nova_293_cur != 0)
	{
		Object* nova_376_d;
		
		nova_376_d = nova_ListNode_getData(nova_293_cur, exceptionData);
		if (nova_376_d == nova_0_data)
		{
			nova_ListNode_setNext(nova_293_prev, exceptionData, nova_ListNode_getNext(nova_293_cur, exceptionData));
		}
		nova_293_cur = nova_ListNode_getNext(nova_293_cur, exceptionData);
	}
}
