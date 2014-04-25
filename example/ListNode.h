#ifndef FILE_ListNode_FATHOM
#define FILE_ListNode_FATHOM

typedef struct ListNode ListNode;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"

CLASS
(
ListNode, 

struct Private* prv;
)

ListNode* fathom_ListNode_ListNode(ExceptionData* exceptionData, Object* fathom_data_89);
void fathom_del_ListNode(ListNode** this, ExceptionData* exceptionData);
Object* fathom_ListNode_getData(ListNode* this, ExceptionData* exceptionData);
ListNode* fathom_ListNode_getNext(ListNode* this, ExceptionData* exceptionData);
void fathom_ListNode_setNext(ListNode* this, ExceptionData* exceptionData, ListNode* fathom_next_130);
#endif