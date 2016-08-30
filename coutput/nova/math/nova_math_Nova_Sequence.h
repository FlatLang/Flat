#pragma once
#ifndef FILE_nova_math_Nova_Sequence_NOVA
#define FILE_nova_math_Nova_Sequence_NOVA

typedef struct nova_math_Nova_Sequence nova_math_Nova_Sequence;


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


typedef struct nova_math_Extension_VTable_Sequence nova_math_Extension_VTable_Sequence;
struct nova_math_Extension_VTable_Sequence
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
};

extern nova_math_Extension_VTable_Sequence nova_math_Extension_VTable_Sequence_val;


CCLASS_CLASS
(
	nova_math_Nova_Sequence, 
	
	nova_math_Extension_VTable_Sequence* vtable;
	nova_datastruct_list_Nova_DoubleArray* nova_math_Nova_Sequence_Nova_values;
)
extern int nova_math_Nova_Sequence_Nova_INFINITE;

void nova_math_Nova_Sequence_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
nova_math_Nova_Sequence* nova_math_Nova_Sequence_Nova_construct(nova_math_Nova_Sequence* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_DoubleArray* nova_math_Nova_Sequence_Nova_values);
void nova_math_Nova_Sequence_Nova_destroy(nova_math_Nova_Sequence** this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_math_Nova_Sequence_1_Nova_this(nova_math_Nova_Sequence* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_DoubleArray* nova_math_Nova_Sequence_Nova_values);
double nova_math_Nova_Sequence_Nova_sum(nova_math_Nova_Sequence* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_math_Nova_Sequence_Nova_num);
void nova_math_Nova_Sequence_Nova_super(nova_math_Nova_Sequence* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
