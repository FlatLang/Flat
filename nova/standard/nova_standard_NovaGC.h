#pragma once
#ifndef FILE_GC_NOVA
#define FILE_GC_NOVA

typedef struct nova_standard_NovaGC nova_standard_NovaGC;

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
#include <gc.h>

typedef struct nova_VTable_nova_standard_NovaGC
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_NovaGC;

CCLASS_CLASS
(
	nova_standard_NovaGC, 
	
	nova_VTable_nova_standard_NovaGC* vtable;
)

nova_standard_NovaGC* nova_standard_NovaGC_Novanull0_construct(nova_standard_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_GC(nova_standard_NovaGC** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaGC_static_Novainit(nova_standard_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
long_long nova_standard_NovaGC_static_NovagetFreeBytes(nova_standard_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
long_long nova_standard_NovaGC_static_NovagetTotalBytes(nova_standard_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
long_long nova_standard_NovaGC_static_NovagetHeapSize(nova_standard_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
long_long nova_standard_NovaGC_static_NovagetBytesSinceGC(nova_standard_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaGC_static_Novacollect(nova_standard_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaGC_static_NovaenableIncremental(nova_standard_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaGC_static_Novadump(nova_standard_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaGC_Novathis(nova_standard_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaGC_Novasuper(nova_standard_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif