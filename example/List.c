#include "List.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
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

void nova_List_add(List* this, ExceptionData* exceptionData, Object* nova_List_data_68)
{
	ListNode* nova_List_node_68;
	
	nova_List_node_68 = nova_ListNode_ListNode(exceptionData, nova_List_data_68);
	if (this->prv->nova_List_start == 0)
	{
		this->prv->nova_List_start = nova_List_node_68;
		this->prv->nova_List_current = nova_List_node_68;
	}
	else
	{
		ListNode* nova_List_current;
	}
	this->prv->nova_List_current = nova_List_node_68;
}

void nova_List_remove(List* this, ExceptionData* exceptionData, Object* nova_List_data_73)
{
	ListNode* nova_List_prev_73;
	ListNode* nova_List_cur_73;
	
	if (nova_ListNode_getData(this->prv->nova_List_start, exceptionData) == nova_List_data_73)
	{
		this->prv->nova_List_start = nova_ListNode_getNext(this->prv->nova_List_start, exceptionData);
	}
	nova_List_prev_73 = this->prv->nova_List_start;
	nova_List_cur_73 = nova_ListNode_getNext(this->prv->nova_List_start, exceptionData);
	while (nova_List_cur_73 != 0)
	{
		Object* nova_List_d_223;
		
		nova_List_d_223 = nova_ListNode_getData(nova_List_cur_73, exceptionData);
		if (nova_List_d_223 == nova_List_data_73)
		{
			nova_ListNode_setNext(nova_List_prev_73, exceptionData, nova_ListNode_getNext(nova_List_cur_73, exceptionData));
		}
		nova_List_cur_73 = nova_ListNode_getNext(nova_List_cur_73, exceptionData);
	}
}
