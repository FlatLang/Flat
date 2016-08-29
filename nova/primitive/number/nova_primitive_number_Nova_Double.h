#pragma once
#ifndef FILE_nova_primitive_number_Nova_Double_NOVA
#define FILE_nova_primitive_number_Nova_Double_NOVA

typedef struct nova_primitive_number_Nova_Double nova_primitive_number_Nova_Double;


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
#include <nova/datastruct/nova_datastruct_Nova_Comparable.h>
#include <nova/primitive/number/nova_primitive_number_Nova_RealNumber.h>


typedef struct nova_primitive_number_Extension_VTable_Double nova_primitive_number_Extension_VTable_Double;
struct nova_primitive_number_Extension_VTable_Double
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	int (*nova_primitive_number_Nova_Number_virtual0_Nova_numDigits)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
	double (*nova_datastruct_Nova_Comparable_virtual0_Nova_compareTo)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
	double (*nova_operators_Nova_Multiply_virtual1_Nova_multiply)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
};

extern nova_primitive_number_Extension_VTable_Double nova_primitive_number_Extension_VTable_Double_val;


CCLASS_CLASS
(
	nova_primitive_number_Nova_Double, 
	
	nova_primitive_number_Extension_VTable_Double* vtable;
	double nova_primitive_number_Nova_Double_Nova_value;
)

void nova_primitive_number_Nova_Double_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
nova_primitive_number_Nova_Double* nova_primitive_number_Nova_Double_Nova_construct(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_value);
void nova_primitive_number_Nova_Double_Nova_destroy(nova_primitive_number_Nova_Double** this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_primitive_number_Nova_Double_Nova_this(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_value);
int nova_primitive_number_Nova_Double_0_Nova_numDigits(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_number);
nova_Nova_String* nova_primitive_number_Nova_Double_Nova_genString(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, char* nova_primitive_number_Nova_Double_Nova_buffer, int nova_primitive_number_Nova_Double_Nova_lastIndex);
char* nova_primitive_number_Nova_Double_Nova_genBuffer(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_value);
int nova_primitive_number_Nova_Double_Nova_repetition(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, char* nova_primitive_number_Nova_Double_Nova_buffer, int nova_primitive_number_Nova_Double_Nova_start);
int nova_primitive_number_Nova_Double_Nova_lastSignificantDigit(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, char* nova_primitive_number_Nova_Double_Nova_buffer, int nova_primitive_number_Nova_Double_Nova_start);
nova_Nova_String* nova_primitive_number_Nova_Double_2_Nova_toString(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_value);
nova_Nova_String* nova_primitive_number_Nova_Double_3_Nova_toString(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData);
double nova_primitive_number_Nova_Double_Nova_parseDouble(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_primitive_number_Nova_Double_Nova_str);
double nova_primitive_number_Nova_Double_0_Nova_compareTo(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_other);
double nova_primitive_number_Nova_Double_0_Nova_multiply(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_value);
void nova_primitive_number_Nova_Double_2_Nova_super(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
