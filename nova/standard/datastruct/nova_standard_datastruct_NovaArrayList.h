#pragma once
#ifndef FILE_ArrayList_NOVA
#define FILE_ArrayList_NOVA

typedef struct nova_standard_datastruct_NovaArrayList nova_standard_datastruct_NovaArrayList;

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

typedef struct nova_VTable_nova_standard_datastruct_NovaArrayList
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_datastruct_NovaArrayList;

CCLASS_CLASS
(
	nova_standard_datastruct_NovaArrayList, 
	
	nova_VTable_nova_standard_datastruct_NovaArrayList* vtable;
	int nova_standard_datastruct_NovaArrayList_Novasize;
	struct Private* prv;
)

nova_standard_datastruct_NovaArrayList* nova_standard_datastruct_NovaArrayList_Novaconstruct(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_ArrayList(nova_standard_datastruct_NovaArrayList** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_datastruct_NovaArrayList_Novathis(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_datastruct_NovaArrayList_Novaadd(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novaelement);
void nova_standard_datastruct_NovaArrayList_NovaincreaseSize(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaObject* nova_standard_datastruct_NovaArrayList_Novaget(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaindex);
void nova_standard_datastruct_NovaArrayList_Novasuper(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif