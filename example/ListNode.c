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
	Object* fathom_data;
	ListNode* fathom_next;
)

ListNode* fathom_ListNode_ListNode(ExceptionData* exceptionData, Object* fathom_data_57)
{
	CCLASS_NEW(ListNode, this);
	
	this->prv->fathom_data = 0;
	this->prv->fathom_next = 0;
	{
		this->prv->fathom_data = fathom_data_57;
	}
	
	return this;
}

void fathom_del_ListNode(ListNode** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	fathom_del_Object(&(*this)->prv->fathom_data, exceptionData);
	fathom_del_ListNode(&(*this)->prv->fathom_next, exceptionData);
	free((*this)->prv);
	
	{
	}
	free(*this);
}

Object* fathom_ListNode_getData(ListNode* this, ExceptionData* exceptionData)
{
	return this->prv->fathom_data;
}

ListNode* fathom_ListNode_getNext(ListNode* this, ExceptionData* exceptionData)
{
	return this->prv->fathom_next;
}

void fathom_ListNode_setNext(ListNode* this, ExceptionData* exceptionData, ListNode* fathom_next_101)
{
	this->prv->fathom_next = fathom_next_101;
}
