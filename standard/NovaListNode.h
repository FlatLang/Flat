#pragma once
#ifndef FILE_ListNode_NOVA
#define FILE_ListNode_NOVA

typedef struct ListNode ListNode;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaObject.h>
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>


CCLASS_CLASS
(
	ListNode, 
	
	struct Private* prv;
)

ListNode* nova_ListNode_ListNode(ExceptionData* exceptionData, Object* nova_0_data);
void nova_del_ListNode(ListNode** this, ExceptionData* exceptionData);
Object* nova_ListNode_getData(ListNode* this, ExceptionData* exceptionData);
ListNode* nova_ListNode_getNext(ListNode* this, ExceptionData* exceptionData);
void nova_ListNode_setNext(ListNode* this, ExceptionData* exceptionData, ListNode* nova_0_next);

#endif