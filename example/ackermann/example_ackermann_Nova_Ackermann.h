#pragma once
#ifndef FILE_example_ackermann_Nova_Ackermann_NOVA
#define FILE_example_ackermann_Nova_Ackermann_NOVA

typedef struct example_ackermann_Nova_Ackermann example_ackermann_Nova_Ackermann;


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
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_IntRange.h>
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>


typedef struct example_ackermann_Extension_VTable_Ackermann example_ackermann_Extension_VTable_Ackermann;
struct example_ackermann_Extension_VTable_Ackermann
{
	nova_Interface_VTable itable;
	long_long (*nova_standard_Nova_Object_virtual1_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual1_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_operators_Nova_Equals_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
};

extern example_ackermann_Extension_VTable_Ackermann example_ackermann_Extension_VTable_Ackermann_val;


CCLASS_CLASS
(
	example_ackermann_Nova_Ackermann, 
	
	example_ackermann_Extension_VTable_Ackermann* vtable;
)

void example_ackermann_Nova_AckermannNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
example_ackermann_Nova_Ackermann* example_ackermann_Nova_Ackermann_Nova_Ackermann(example_ackermann_Nova_Ackermann* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_ackermann_Nova_Ackermann_Nova_destroy(example_ackermann_Nova_Ackermann** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_ackermann_Nova_Ackermann_Nova_main(example_ackermann_Nova_Ackermann* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_ackermann_Nova_Ackermann_Nova_args);
int example_ackermann_Nova_Ackermann_Nova_run(example_ackermann_Nova_Ackermann* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_ackermann_Nova_Ackermann_Nova_m, int example_ackermann_Nova_Ackermann_Nova_n);
int example_ackermann_Nova_Ackermann_Nova_run2(example_ackermann_Nova_Ackermann* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_ackermann_Nova_Ackermann_Nova_m, int example_ackermann_Nova_Ackermann_Nova_n);
void example_ackermann_Nova_Ackermann_0_Nova_this(example_ackermann_Nova_Ackermann* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_ackermann_Nova_Ackermann_Nova_super(example_ackermann_Nova_Ackermann* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
