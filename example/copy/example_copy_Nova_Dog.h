#pragma once
#ifndef FILE_example_copy_Nova_Dog_NOVA
#define FILE_example_copy_Nova_Dog_NOVA

typedef struct example_copy_Nova_Dog example_copy_Nova_Dog;


#include <Nova.h>
#include <ExceptionHandler.h>
#include <InterfaceVTable.h>
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
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_Array.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_IntArray.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_CharArray.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_DoubleArray.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_IntRange.h>
#include <nova/standard/thread/nova_standard_thread_Nova_Thread.h>
#include <nova/standard/thread/async/nova_standard_thread_async_Nova_Async.h>
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>


typedef struct example_copy_Extension_VTable_Dog example_copy_Extension_VTable_Dog;
struct example_copy_Extension_VTable_Dog
{
	nova_Interface_VTable itable;
	long_long (*nova_standard_Nova_Object_virtual1_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual1_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_operators_Nova_Equals_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
};

extern example_copy_Extension_VTable_Dog example_copy_Extension_VTable_Dog_val;


CCLASS_CLASS
(
	example_copy_Nova_Dog, 
	
	example_copy_Extension_VTable_Dog* vtable;
)

void example_copy_Nova_Dog_Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
example_copy_Nova_Dog* example_copy_Nova_Dog_Nova_Dog(example_copy_Nova_Dog* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_copy_Nova_Dog_Nova_a, int example_copy_Nova_Dog_Nova_b);
void example_copy_Nova_Dog_Nova_destroy(example_copy_Nova_Dog** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_copy_Nova_Dog_Nova_this(example_copy_Nova_Dog* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_copy_Nova_Dog_Nova_a, int example_copy_Nova_Dog_Nova_b);
void example_copy_Nova_Dog_Nova_super(example_copy_Nova_Dog* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
