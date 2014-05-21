#include "List.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include "ListNode.h"

CCLASS_PRIVATE
(
	ListNode* nova_start;
	ListNode* nova_current;
)

List* nova_List_List(ExceptionData* exceptionData)
{
	CCLASS_NEW(List, this);
	
	this->prv->nova_start = 0;
	this->prv->nova_current = 0;
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
	
	nova_del_ListNode(&(*this)->prv->nova_start, exceptionData);
	nova_del_ListNode(&(*this)->prv->nova_current, exceptionData);
	free((*this)->prv);
	
	{
	}
	free(*this);
}

ListNode* nova_List_getFirst(List* this, ExceptionData* exceptionData)
{
	return this->prv->nova_start;
}

void nova_List_add(List* this, ExceptionData* exceptionData, Object* nova_data_118)
{
	ListNode* nova_node_118;
	
	nova_node_118 = nova_ListNode_ListNode(exceptionData, nova_data_118);
	if (this->prv->nova_start == 0)
	{
		this->prv->nova_start = nova_node_118;
		this->prv->nova_current = nova_node_118;
	}
	else
	{
		ListNode* nova_current;
	}
	this->prv->nova_current = nova_node_118;
}

void nova_List_remove(List* this, ExceptionData* exceptionData, Object* nova_data_121)
{
	ListNode* nova_prev_121;
	ListNode* nova_cur_121;
	
	if (nova_ListNode_getData(this->prv->nova_start, exceptionData) == nova_data_121)
	{
		this->prv->nova_start = nova_ListNode_getNext(this->prv->nova_start, exceptionData);
	}
	nova_prev_121 = this->prv->nova_start;
	nova_cur_121 = nova_ListNode_getNext(this->prv->nova_start, exceptionData);
	while (nova_cur_121 != 0)
	{
		Object* nova_d_200;
		
		nova_d_200 = nova_ListNode_getData(nova_cur_121, exceptionData);
		if (nova_d_200 == nova_data_121)
		{
			nova_ListNode_setNext(nova_prev_121, exceptionData, nova_ListNode_getNext(nova_cur_121, exceptionData));
		}
		nova_cur_121 = nova_ListNode_getNext(nova_cur_121, exceptionData);
	}
}
