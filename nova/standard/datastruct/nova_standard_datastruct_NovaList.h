#pragma once
#ifndef FILE_List_NOVA
#define FILE_List_NOVA

typedef struct nova_standard_datastruct_NovaList nova_standard_datastruct_NovaList;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_NovaException.h>
#include <nova/standard/exception/nova_standard_exception_NovaDivideByZeroException.h>
#include <nova/standard/nova_standard_NovaNull.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <nova/standard/nova_standard_NovaConsole.h>
#include <nova/standard/nova_standard_NovaGC.h>
#include <nova/standard/nova_standard_NovaNumber.h>
#include <nova/standard/nova_standard_NovaByte.h>
#include <nova/standard/nova_standard_NovaShort.h>
#include <nova/standard/nova_standard_NovaInt.h>
#include <nova/standard/nova_standard_NovaLong.h>
#include <nova/standard/nova_standard_NovaFloat.h>
#include <nova/standard/nova_standard_NovaDouble.h>
#include <nova/standard/nova_standard_NovaChar.h>
#include <nova/standard/nova_standard_NovaBool.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaListNode.h>

typedef struct nova_VTable_nova_standard_datastruct_NovaList
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_datastruct_NovaList;

CCLASS_CLASS
(
	nova_standard_datastruct_NovaList, 
	
	nova_VTable_nova_standard_datastruct_NovaList* vtable;
	struct Private* prv;
)

nova_standard_datastruct_NovaList* nova_standard_datastruct_NovaList_Novanull0_construct(nova_standard_datastruct_NovaList* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_List(nova_standard_datastruct_NovaList** this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_datastruct_NovaListNode* nova_standard_datastruct_NovaList_NovaE(nova_standard_datastruct_NovaList* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_datastruct_NovaList_Novaadd(nova_standard_datastruct_NovaList* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novadata);
void nova_standard_datastruct_NovaList_Novaremove(nova_standard_datastruct_NovaList* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novadata);
void nova_standard_datastruct_NovaList_Novathis(nova_standard_datastruct_NovaList* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_datastruct_NovaList_Novasuper(nova_standard_datastruct_NovaList* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif