#include "List.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "ListNode.h"

PRIVATE
(
	ListNode* fathom_start;
	ListNode* fathom_current;
)

List* fathom_List_List(ExceptionData* exceptionData)
{
	NEW(List, reference);
	
	reference->prv->fathom_start = 0;
	reference->prv->fathom_current = 0;
	{
	}
	
	return reference;
}

void fathom_del_List(List** reference, ExceptionData* exceptionData)
{
	if (!*reference)
	{
		return;
	}
	
	fathom_del_ListNode(&(*reference)->prv->fathom_start, exceptionData);
	fathom_del_ListNode(&(*reference)->prv->fathom_current, exceptionData);
	free((*reference)->prv);
	
	{
	}
	free(*reference);
}

ListNode* fathom_List_getFirst(List* reference, ExceptionData* exceptionData)
{
	return reference->prv->fathom_start;
}

void fathom_List_add(List* reference, ExceptionData* exceptionData, Object* fathom_data_149)
{
	ListNode* fathom_node_149;
	
	fathom_node_149 = fathom_ListNode_ListNode(exceptionData, fathom_data_149);
	if (reference->prv->fathom_start == 0)
	{
		reference->prv->fathom_start = fathom_node_149;
		reference->prv->fathom_current = fathom_node_149;
	}
	else
	{
		fathom_ListNode_setNext(reference->prv->fathom_current, exceptionData, fathom_node_149);
	}
	reference->prv->fathom_current = fathom_node_149;
}

void fathom_List_remove(List* reference, ExceptionData* exceptionData, Object* fathom_data_152)
{
	ListNode* fathom_prev_152;
	ListNode* fathom_cur_152;
	
	if (fathom_ListNode_getData(reference->prv->fathom_start, exceptionData) == fathom_data_152)
	{
		reference->prv->fathom_start = fathom_ListNode_getNext(reference->prv->fathom_start, exceptionData);
	}
	fathom_prev_152 = reference->prv->fathom_start;
	fathom_cur_152 = fathom_ListNode_getNext(reference->prv->fathom_start, exceptionData);
	while (fathom_cur_152 != 0)
	{
		Object* fathom_d_194;
		
		fathom_d_194 = fathom_ListNode_getData(fathom_cur_152, exceptionData);
		if (fathom_d_194 == fathom_data_152)
		{
			fathom_ListNode_setNext(fathom_prev_152, exceptionData, fathom_ListNode_getNext(fathom_cur_152, exceptionData));
		}
		fathom_cur_152 = fathom_ListNode_getNext(fathom_cur_152, exceptionData);
	}
}
