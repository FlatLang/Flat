#pragma once
#ifndef FILE_ArrayList_NOVA
#define FILE_ArrayList_NOVA

typedef struct nova_standard_datastruct_NovaArrayList nova_standard_datastruct_NovaArrayList;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_NovaException.h>
#include <nova/standard/exception/nova_standard_exception_NovaDivideByZeroException.h>
#include <nova/standard/io/nova_standard_io_NovaConsole.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaNumber.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaByte.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaShort.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaInt.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaLong.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaFloat.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaDouble.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaNull.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaChar.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaBool.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaArray.h>
#include <nova/standard/gc/nova_standard_gc_NovaGC.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>

typedef struct nova_VTable_nova_standard_datastruct_NovaArrayList
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
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

void nova_standard_datastruct_NovaArrayListNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_datastruct_NovaArrayList* nova_standard_datastruct_NovaArrayList_Nova0_construct(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_ArrayList(nova_standard_datastruct_NovaArrayList** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_datastruct_NovaArrayList_Novathis(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_datastruct_NovaArrayList_Nova0_add(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novaelement);
void nova_standard_datastruct_NovaArrayList_Nova1_add(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaindex, nova_standard_NovaObject* l0_Novaelement);
nova_standard_NovaObject* nova_standard_datastruct_NovaArrayList_Novaremove(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaindex);
void nova_standard_datastruct_NovaArrayList_Novaswap(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaindex1, int l0_Novaindex2);
void nova_standard_datastruct_NovaArrayList_NovaincreaseSize(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaObject* nova_standard_datastruct_NovaArrayList_Novaget(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaindex);
void nova_standard_datastruct_NovaArrayList_Novasuper(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif