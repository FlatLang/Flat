#include "ListNode.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"

ListNode* new_ListNode(ExceptionData* __FATHOM__exception_data, Object* data);
void del_ListNode(ListNode** __o__, ExceptionData* __FATHOM__exception_data);
static Object* __FATHOM__getData(ListNode* __o__, ExceptionData* __FATHOM__exception_data);
static ListNode* __FATHOM__getNext(ListNode* __o__, ExceptionData* __FATHOM__exception_data);
static void __FATHOM__setNext(ListNode* __o__, ExceptionData* __FATHOM__exception_data, ListNode* next);

PRIVATE
(
	Object* data;
	ListNode* next;
)

ListNode* new_ListNode(ExceptionData* __FATHOM__exception_data, Object* data)
{
	NEW(ListNode, __o__);
	
	__o__->getData = __FATHOM__getData;
	__o__->getNext = __FATHOM__getNext;
	__o__->setNext = __FATHOM__setNext;
	
	__o__->prv->data = 0;
	__o__->prv->next = 0;
	{
		__o__->prv->data = data;
	}
	
	return __o__;
}

void del_ListNode(ListNode** __o__, ExceptionData* __FATHOM__exception_data)
{
	if (!*__o__)
	{
		return;
	}
	
	del_Object(&(*__o__)->prv->data, __FATHOM__exception_data);
	del_ListNode(&(*__o__)->prv->next, __FATHOM__exception_data);
	free((*__o__)->prv);
	
	{
	}
	free(*__o__);
}

static Object* __FATHOM__getData(ListNode* __o__, ExceptionData* __FATHOM__exception_data)
{
	return __o__->prv->data;
}

static ListNode* __FATHOM__getNext(ListNode* __o__, ExceptionData* __FATHOM__exception_data)
{
	return __o__->prv->next;
}

static void __FATHOM__setNext(ListNode* __o__, ExceptionData* __FATHOM__exception_data, ListNode* next)
{
	__o__->prv->next = next;
}
