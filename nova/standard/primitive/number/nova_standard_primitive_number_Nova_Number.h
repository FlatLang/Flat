#pragma once
#ifndef FILE_nova_standard_primitive_number_Nova_Number_NOVA
#define FILE_nova_standard_primitive_number_Nova_Number_NOVA

typedef struct nova_standard_primitive_number_Nova_Number nova_standard_primitive_number_Nova_Number;

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
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Array.h>
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>
#include <nova/standard/primitive/nova_standard_primitive_Nova_Primitive.h>
#include <nova/standard/operators/nova_standard_operators_Nova_Multipliable.h>


typedef struct nova_standard_primitive_number_Extension_VTable_Number nova_standard_primitive_number_Extension_VTable_Number;
struct nova_standard_primitive_number_Extension_VTable_Number
{
	nova_Interface_VTable itable;
	long (*nova_standard_Nova_Object_virtual3_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual0_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_Nova_Object_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
	int (*nova_standard_primitive_number_Nova_Number_virtual0_Nova_numDigits)(nova_standard_primitive_number_Nova_Number*, nova_standard_exception_Nova_ExceptionData*, nova_standard_primitive_number_Nova_Number*);
};

extern nova_standard_primitive_number_Extension_VTable_Number nova_standard_primitive_number_Extension_VTable_Number_val;


CCLASS_CLASS
(
	nova_standard_primitive_number_Nova_Number, 
	
	nova_standard_primitive_number_Extension_VTable_Number* vtable;
)

void nova_standard_primitive_number_Nova_NumberNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_primitive_number_Nova_Number* nova_standard_primitive_number_Nova_Number_7_Nova_construct(nova_standard_primitive_number_Nova_Number* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_primitive_number_Nova_Number_Nova_destroy(nova_standard_primitive_number_Nova_Number** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
int nova_standard_primitive_number_Nova_Number_0_Nova_numDigits(nova_standard_primitive_number_Nova_Number* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Number* l0_Nova_number);
void nova_standard_primitive_number_Nova_Number_7_Nova_this(nova_standard_primitive_number_Nova_Number* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_primitive_number_Nova_Number_2_Nova_super(nova_standard_primitive_number_Nova_Number* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
