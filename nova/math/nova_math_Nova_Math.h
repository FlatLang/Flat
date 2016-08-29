#pragma once
#ifndef FILE_nova_math_Nova_Math_NOVA
#define FILE_nova_math_Nova_Math_NOVA

typedef struct nova_math_Nova_Math nova_math_Nova_Math;


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
#include <math.h>


typedef struct nova_math_Extension_VTable_Math nova_math_Extension_VTable_Math;
struct nova_math_Extension_VTable_Math
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
};

extern nova_math_Extension_VTable_Math nova_math_Extension_VTable_Math_val;


CCLASS_CLASS
(
	nova_math_Nova_Math, 
	
	nova_math_Extension_VTable_Math* vtable;
)
extern double nova_math_Nova_Math_Nova_PI;

void nova_math_Nova_Math_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
nova_math_Nova_Math* nova_math_Nova_Math_Nova_construct(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_math_Nova_Math_Nova_destroy(nova_math_Nova_Math** this, nova_exception_Nova_ExceptionData* exceptionData);
long_long nova_math_Nova_Math_Nova_max(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_math_Nova_Math_Nova_a, long_long nova_math_Nova_Math_Nova_b);
long_long nova_math_Nova_Math_Nova_min(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_math_Nova_Math_Nova_a, long_long nova_math_Nova_Math_Nova_b);
char nova_math_Nova_Math_Nova_sign(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_math_Nova_Math_Nova_num);
int nova_math_Nova_Math_Nova_random(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_math_Nova_Math_Nova_range);
long_long nova_math_Nova_Math_0_Nova_abs(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_math_Nova_Math_Nova_number);
double nova_math_Nova_Math_1_Nova_abs(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_math_Nova_Math_Nova_number);
double nova_math_Nova_Math_Nova_sqrt(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_math_Nova_Math_Nova_number);
double nova_math_Nova_Math_Nova_pow(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_math_Nova_Math_Nova_base, double nova_math_Nova_Math_Nova_power);
double nova_math_Nova_Math_Nova_sin(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_math_Nova_Math_Nova_number);
double nova_math_Nova_Math_Nova_cos(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_math_Nova_Math_Nova_number);
double nova_math_Nova_Math_Nova_tan(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_math_Nova_Math_Nova_number);
double nova_math_Nova_Math_Nova_asin(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_math_Nova_Math_Nova_number);
double nova_math_Nova_Math_Nova_acos(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_math_Nova_Math_Nova_number);
double nova_math_Nova_Math_Nova_atan(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_math_Nova_Math_Nova_number);
long_long nova_math_Nova_Math_Nova_round(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_math_Nova_Math_Nova_number);
long_long nova_math_Nova_Math_Nova_floor(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_math_Nova_Math_Nova_number);
long_long nova_math_Nova_Math_Nova_ceil(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_math_Nova_Math_Nova_number);
void nova_math_Nova_Math_0_Nova_this(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_math_Nova_Math_Nova_super(nova_math_Nova_Math* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
