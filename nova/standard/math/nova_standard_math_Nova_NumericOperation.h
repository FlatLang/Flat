#pragma once
#ifndef FILE_nova_standard_math_Nova_NumericOperation_NOVA
#define FILE_nova_standard_math_Nova_NumericOperation_NOVA

typedef struct nova_standard_math_Nova_NumericOperation nova_standard_math_Nova_NumericOperation;


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
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Bounds.h>
#include <nova/standard/math/nova_standard_math_Nova_NumericOperand.h>
#include <nova/standard/math/nova_standard_math_Nova_InvalidNumericStatementException.h>


typedef struct nova_standard_math_Extension_VTable_NumericOperation nova_standard_math_Extension_VTable_NumericOperation;
struct nova_standard_math_Extension_VTable_NumericOperation
{
	nova_Interface_VTable itable;
	long (*nova_standard_Nova_Object_virtual0_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_math_Nova_NumericOperation_virtual_Nova_toString)(nova_standard_math_Nova_NumericOperation*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_Nova_Object_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
};

extern nova_standard_math_Extension_VTable_NumericOperation nova_standard_math_Extension_VTable_NumericOperation_val;


CCLASS_CLASS
(
	nova_standard_math_Nova_NumericOperation, 
	
	nova_standard_math_Extension_VTable_NumericOperation* vtable;
	struct Private* prv;
)

void nova_standard_math_Nova_NumericOperationNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_math_Nova_NumericOperation* nova_standard_math_Nova_NumericOperation_Nova_construct(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operation);
nova_standard_math_Nova_NumericOperation* nova_standard_math_Nova_NumericOperation_0_Nova_construct(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_left, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operator, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_right);
nova_standard_math_Nova_NumericOperation* nova_standard_math_Nova_NumericOperation_2_Nova_construct(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_math_Nova_NumericOperation_Nova_data);
void nova_standard_math_Nova_NumericOperation_Nova_destroy(nova_standard_math_Nova_NumericOperation** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_math_Nova_NumericOperation_Nova_this(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operation);
void nova_standard_math_Nova_NumericOperation_0_Nova_this(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_left, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operator, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_right);
void nova_standard_math_Nova_NumericOperation_1_Nova_this(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_math_Nova_NumericOperand* nova_standard_math_Nova_NumericOperation_Nova_left, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operator, nova_standard_math_Nova_NumericOperand* nova_standard_math_Nova_NumericOperation_Nova_right);
nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_toString(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_math_Nova_NumericOperation_2_Nova_this(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_math_Nova_NumericOperation_Nova_data);
void nova_standard_math_Nova_NumericOperation_2_Nova_super(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
