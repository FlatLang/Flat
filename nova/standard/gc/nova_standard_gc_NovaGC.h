#pragma once
#ifndef FILE_GC_NOVA
#define FILE_GC_NOVA

typedef struct nova_standard_gc_NovaGC nova_standard_gc_NovaGC;

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
#include <nova/standard/primitive/nova_standard_primitive_NovaChar.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaBool.h>
#include <nova/standard/gc/nova_standard_gc_NovaGC.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <gc.h>

typedef struct nova_VTable_nova_standard_gc_NovaGC
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_gc_NovaGC;

CCLASS_CLASS
(
	nova_standard_gc_NovaGC, 
	
	nova_VTable_nova_standard_gc_NovaGC* vtable;
)

nova_standard_gc_NovaGC* nova_standard_gc_NovaGC_Nova0_construct(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_GC(nova_standard_gc_NovaGC** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_gc_NovaGC_static_Novainit(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
long_long nova_standard_gc_NovaGC_static_NovagetFreeBytes(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
long_long nova_standard_gc_NovaGC_static_NovagetTotalBytes(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
long_long nova_standard_gc_NovaGC_static_NovagetHeapSize(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
long_long nova_standard_gc_NovaGC_static_NovagetBytesSinceGC(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_gc_NovaGC_static_Novacollect(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_gc_NovaGC_static_NovaenableIncremental(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_gc_NovaGC_static_Novadump(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_gc_NovaGC_Novathis(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_gc_NovaGC_Novasuper(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif