#ifndef FILE_ListNode_FATHOM
#define FILE_ListNode_FATHOM

typedef struct ListNode ListNode;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"

CLASS
(
	ListNode, 
	
	FUNC(Object*, getData, ListNode* __o__, ExceptionData* __FATHOM__exception_data);
	FUNC(ListNode*, getNext, ListNode* __o__, ExceptionData* __FATHOM__exception_data);
	FUNC(void, setNext, ListNode* __o__, ExceptionData* __FATHOM__exception_data, ListNode* next);
)

ListNode* new_ListNode(ExceptionData* __FATHOM__exception_data, Object* data);
void del_ListNode(ListNode** __o__, ExceptionData* __FATHOM__exception_data);
#endif