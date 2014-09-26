#pragma once
#ifndef FILE_HashMap_NOVA
#define FILE_HashMap_NOVA

typedef struct nova_standard_datastruct_NovaHashMap nova_standard_datastruct_NovaHashMap;

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
#include <HashMap.h>

typedef struct nova_VTable_nova_standard_datastruct_NovaHashMap
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_datastruct_NovaHashMap;

CCLASS_CLASS
(
	nova_standard_datastruct_NovaHashMap, 
	
	nova_VTable_nova_standard_datastruct_NovaHashMap* vtable;
	struct Private* prv;
)

nova_standard_datastruct_NovaHashMap* nova_standard_datastruct_NovaHashMap_Novanull0_construct(nova_standard_datastruct_NovaHashMap* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_HashMap(nova_standard_datastruct_NovaHashMap** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_datastruct_NovaHashMap_Novathis(nova_standard_datastruct_NovaHashMap* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_datastruct_NovaHashMap_Novaput(nova_standard_datastruct_NovaHashMap* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novakey, nova_standard_NovaObject* l0_Novavalue);
nova_standard_NovaObject* nova_standard_datastruct_NovaHashMap_Novaget(nova_standard_datastruct_NovaHashMap* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novakey);
void nova_standard_datastruct_NovaHashMap_Novasuper(nova_standard_datastruct_NovaHashMap* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif