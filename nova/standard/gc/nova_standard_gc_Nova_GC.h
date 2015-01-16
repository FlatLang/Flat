#pragma once
#ifndef FILE_nova_standard_gc_Nova_GC_NOVA
#define FILE_nova_standard_gc_Nova_GC_NOVA

typedef struct nova_standard_gc_Nova_GC nova_standard_gc_Nova_GC;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/standard/exception/nova_standard_exception_Nova_ExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_Nova_Exception.h>
#include <nova/standard/exception/nova_standard_exception_Nova_DivideByZeroException.h>
#include <nova/standard/io/nova_standard_io_Nova_Console.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Number.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Byte.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Short.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Int.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Long.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Float.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Double.h>
#include <nova/standard/primitive/nova_standard_primitive_Nova_Null.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Char.h>
#include <nova/standard/primitive/nova_standard_primitive_Nova_Bool.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Array.h>
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>
#include <nova/standard/gc/NativeGC.h>

typedef struct nova_standard_gc_VTable_GC
{
	long (*nova_standard_Nova_Object_virtual1_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual0_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_Nova_Object_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
} nova_standard_gc_VTable_GC;

extern nova_standard_gc_VTable_GC nova_standard_gc_VTable_GC_val;

CCLASS_CLASS
(
	nova_standard_gc_Nova_GC, 
	
	nova_standard_gc_VTable_GC* vtable;
)

void nova_standard_gc_Nova_GCNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_gc_Nova_GC* nova_standard_gc_Nova_GC_2_Nova_construct(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_gc_Nova_GC_Nova_destroy(nova_standard_gc_Nova_GC** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_gc_Nova_GC_Nova_init(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_gc_Nova_GC_Nova_collect(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_gc_Nova_GC_Nova_enableIncremental(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_gc_Nova_GC_Nova_dump(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_gc_Nova_GC_2_Nova_this(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
int nova_standard_gc_Nova_GC_Accessor_Nova_freeBytes(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
int nova_standard_gc_Nova_GC_Accessor_Nova_totalBytes(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
int nova_standard_gc_Nova_GC_Accessor_Nova_heapSize(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
int nova_standard_gc_Nova_GC_Accessor_Nova_bytesSinceGC(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_gc_Nova_GC_Nova_super(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
