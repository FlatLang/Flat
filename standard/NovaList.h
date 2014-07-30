#pragma once
#ifndef FILE_List_NOVA
#define FILE_List_NOVA

typedef struct List List;

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
#include <NovaShort.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaListNode.h>

typedef struct nova_VTable_List
{
	String* (*nova_virtual_4_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_List;

CCLASS_CLASS
(
	List, 
	
	nova_VTable_List* vtable;
	struct Private* prv;
)

List* nova_List_List(List* this, ExceptionData* exceptionData);
void nova_del_List(List** this, ExceptionData* exceptionData);
ListNode* nova_List_getFirst(List* this, ExceptionData* exceptionData);
void nova_List_add(List* this, ExceptionData* exceptionData, Object* nova_0_data);
void nova_List_remove(List* this, ExceptionData* exceptionData, Object* nova_0_data);

#endif