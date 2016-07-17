#pragma once
#ifndef FILE_example_Nova_FileTest_NOVA
#define FILE_example_Nova_FileTest_NOVA

typedef struct example_Nova_FileTest example_Nova_FileTest;


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
#include <nova/standard/io/nova_standard_io_Nova_File.h>


typedef struct example_Extension_VTable_FileTest example_Extension_VTable_FileTest;
struct example_Extension_VTable_FileTest
{
	nova_Interface_VTable itable;
	long_long (*nova_standard_Nova_Object_virtual1_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual1_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_operators_Nova_Equals_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
};

extern example_Extension_VTable_FileTest example_Extension_VTable_FileTest_val;


CCLASS_CLASS
(
	example_Nova_FileTest, 
	
	example_Extension_VTable_FileTest* vtable;
)

void example_Nova_FileTestNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
example_Nova_FileTest* example_Nova_FileTest_Nova_FileTest(example_Nova_FileTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_Nova_FileTest_Nova_destroy(example_Nova_FileTest** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_Nova_FileTest_Nova_main(example_Nova_FileTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_FileTest_Nova_args);
void example_Nova_FileTest_0_Nova_this(example_Nova_FileTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_Nova_FileTest_Nova_super(example_Nova_FileTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
