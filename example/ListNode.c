#include "ListNode.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"

PRIVATE
(
	Object* fathom_data;
	ListNode* fathom_next;
)

ListNode* fathom_ListNode_ListNode(ExceptionData* exceptionData, Object* fathom_data_154)
{
	NEW(ListNode, reference);
	
	reference->prv->fathom_data = 0;
	reference->prv->fathom_next = 0;
	{
		reference->prv->fathom_data = fathom_data_154;
	}
	
	return reference;
}

void fathom_del_ListNode(ListNode** reference, ExceptionData* exceptionData)
{
	if (!*reference)
	{
		return;
	}
	
	fathom_del_Object(&(*reference)->prv->fathom_data, exceptionData);
	fathom_del_ListNode(&(*reference)->prv->fathom_next, exceptionData);
	free((*reference)->prv);
	
	{
	}
	free(*reference);
}

Object* fathom_ListNode_getData(ListNode* reference, ExceptionData* exceptionData)
{
	return reference->prv->fathom_data;
}

ListNode* fathom_ListNode_getNext(ListNode* reference, ExceptionData* exceptionData)
{
	return reference->prv->fathom_next;
}

void fathom_ListNode_setNext(ListNode* reference, ExceptionData* exceptionData, ListNode* fathom_next_163)
{
	reference->prv->fathom_next = fathom_next_163;
}
