#include "ListNode.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"

CCLASS_PRIVATE
(
	Object* nova_data;
	ListNode* nova_next;
)

ListNode* nova_ListNode_ListNode(ExceptionData* exceptionData, Object* nova_data_93)
{
	CCLASS_NEW(ListNode, this);
	
	this->prv->nova_data = 0;
	this->prv->nova_next = 0;
	{
		this->prv->nova_data = nova_data_93;
	}
	
	return this;
}

void nova_del_ListNode(ListNode** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_Object(&(*this)->prv->nova_data, exceptionData);
	nova_del_ListNode(&(*this)->prv->nova_next, exceptionData);
	free((*this)->prv);
	
	{
	}
	free(*this);
}

Object* nova_ListNode_getData(ListNode* this, ExceptionData* exceptionData)
{
	return this->prv->nova_data;
}

ListNode* nova_ListNode_getNext(ListNode* this, ExceptionData* exceptionData)
{
	return this->prv->nova_next;
}

void nova_ListNode_setNext(ListNode* this, ExceptionData* exceptionData, ListNode* nova_next_128)
{
	this->prv->nova_next = nova_next_128;
}
