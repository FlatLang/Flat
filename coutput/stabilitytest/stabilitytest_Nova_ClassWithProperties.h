#pragma once
#ifndef FILE_stabilitytest_Nova_ClassWithProperties_NOVA
#define FILE_stabilitytest_Nova_ClassWithProperties_NOVA

typedef struct stabilitytest_Nova_ClassWithProperties stabilitytest_Nova_ClassWithProperties;


#include <Nova.h>
#include <ExceptionHandler.h>
#include <InterfaceVTable.h>
#include <nova/exception/nova_exception_Nova_ExceptionData.h>
#include <nova/exception/nova_exception_Nova_Exception.h>
#include <nova/exception/nova_exception_Nova_DivideByZeroException.h>
#include <nova/io/nova_io_Nova_Console.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Number.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Byte.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Short.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Int.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Long.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Float.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Double.h>
#include <nova/primitive/nova_primitive_Nova_Null.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Char.h>
#include <nova/primitive/nova_primitive_Nova_Bool.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Array.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_CharArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_DoubleArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntRange.h>
#include <nova/thread/nova_thread_Nova_Thread.h>
#include <nova/thread/async/nova_thread_async_Nova_Async.h>
#include <nova/gc/nova_gc_Nova_GC.h>
#include <nova/math/nova_math_Nova_Math.h>
#include <nova/nova_Nova_Object.h>
#include <nova/nova_Nova_String.h>
#include <nova/nova_Nova_System.h>
#include <nova/nova_Nova_Class.h>


typedef struct stabilitytest_Extension_VTable_ClassWithProperties stabilitytest_Extension_VTable_ClassWithProperties;
struct stabilitytest_Extension_VTable_ClassWithProperties
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
};

extern stabilitytest_Extension_VTable_ClassWithProperties stabilitytest_Extension_VTable_ClassWithProperties_val;


CCLASS_CLASS
(
	stabilitytest_Nova_ClassWithProperties, 
	
	stabilitytest_Extension_VTable_ClassWithProperties* vtable;
	struct Private* prv;
)

void stabilitytest_Nova_ClassWithProperties_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
stabilitytest_Nova_ClassWithProperties* stabilitytest_Nova_ClassWithProperties_Nova_construct(stabilitytest_Nova_ClassWithProperties* this, nova_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_ClassWithProperties_Nova_destroy(stabilitytest_Nova_ClassWithProperties** this, nova_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_ClassWithProperties_0_Nova_this(stabilitytest_Nova_ClassWithProperties* this, nova_exception_Nova_ExceptionData* exceptionData);
int stabilitytest_Nova_ClassWithProperties_Accessor_Nova_prop1(stabilitytest_Nova_ClassWithProperties* this, nova_exception_Nova_ExceptionData* exceptionData);
int stabilitytest_Nova_ClassWithProperties_Mutator_Nova_prop1(stabilitytest_Nova_ClassWithProperties* this, nova_exception_Nova_ExceptionData* exceptionData, int stabilitytest_Nova_ClassWithProperties_Nova_value);
int stabilitytest_Nova_ClassWithProperties_Accessor_Nova_prop2(stabilitytest_Nova_ClassWithProperties* this, nova_exception_Nova_ExceptionData* exceptionData);
int stabilitytest_Nova_ClassWithProperties_Mutator_Nova_prop2(stabilitytest_Nova_ClassWithProperties* this, nova_exception_Nova_ExceptionData* exceptionData, int stabilitytest_Nova_ClassWithProperties_Nova_value);
void stabilitytest_Nova_ClassWithProperties_Nova_super(stabilitytest_Nova_ClassWithProperties* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
