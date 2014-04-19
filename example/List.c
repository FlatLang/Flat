#include "List.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "ListNode.h"

List* new_List(ExceptionData* __FATHOM__exception_data);
void del_List(List** __o__, ExceptionData* __FATHOM__exception_data);
static void __FATHOM__add(List* __o__, ExceptionData* __FATHOM__exception_data, Object* data);
static void __FATHOM__remove(List* __o__, ExceptionData* __FATHOM__exception_data, Object* data);

PRIVATE
(
	ListNode* start;
	ListNode* current;
)

List* new_List(ExceptionData* __FATHOM__exception_data)
{
	NEW(List, __o__);
	
	__o__->add = __FATHOM__add;
	__o__->remove = __FATHOM__remove;
	
	__o__->prv->start = 0;
	__o__->prv->current = 0;
	{
	}
	
	return __o__;
}

void del_List(List** __o__, ExceptionData* __FATHOM__exception_data)
{
	if (!*__o__)
	{
		return;
	}
	
	del_ListNode(&(*__o__)->prv->start, __FATHOM__exception_data);
	del_ListNode(&(*__o__)->prv->current, __FATHOM__exception_data);
	free((*__o__)->prv);
	
	{
	}
	free(*__o__);
}

static void __FATHOM__add(List* __o__, ExceptionData* __FATHOM__exception_data, Object* data)
{
	ListNode* node;
	
	node = ->new_ListNode(__FATHOM__exception_data, data);
	if (__o__->prv->start == 0)
	{
		__o__->prv->start = node;
		__o__->prv->current = node;
	}
	else
	{
		__o__->prv->current->setNext(__o__->prv->current, __FATHOM__exception_data, node);
	}
	__o__->prv->current = node;
}

static void __FATHOM__remove(List* __o__, ExceptionData* __FATHOM__exception_data, Object* data)
{
	ListNode* prev;
	ListNode* cur;
	
	if (__o__->prv->start->getData(__o__->prv->start, __FATHOM__exception_data) == data)
	{
		__o__->prv->start = __o__->prv->start->getNext(__o__->prv->start, __FATHOM__exception_data);
	}
	prev = __o__->prv->start;
	cur = __o__->prv->start->getNext(__o__->prv->start, __FATHOM__exception_data);
	while (cur != 0)
	{
		Object* d;
		
		d = cur->getData(cur, __FATHOM__exception_data);
		if (d == data)
		{
			prev->setNext(prev, __FATHOM__exception_data, cur->getNext(cur, __FATHOM__exception_data));
		}
		cur = cur->getNext(cur, __FATHOM__exception_data);
	}
}
