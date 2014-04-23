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
	
	, struct Private* prv;
)

ListNode* fathom_ListNode_ListNode(ExceptionData* exceptionData, Object* fathom_data_154);
void fathom_del_ListNode(ListNode** reference, ExceptionData* exceptionData);
Object* fathom_ListNode_getData(ListNode* reference, ExceptionData* exceptionData);
ListNode* fathom_ListNode_getNext(ListNode* reference, ExceptionData* exceptionData);
void fathom_ListNode_setNext(ListNode* reference, ExceptionData* exceptionData, ListNode* fathom_next_163);
#endif