#pragma once
#ifndef FILE_nova_time_Nova_Date_NOVA
#define FILE_nova_time_Nova_Date_NOVA

typedef struct nova_time_Nova_Date nova_time_Nova_Date;


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
#include <nova/time/NativeDate.h>


typedef struct nova_time_Extension_VTable_Date nova_time_Extension_VTable_Date;
struct nova_time_Extension_VTable_Date
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
};

extern nova_time_Extension_VTable_Date nova_time_Extension_VTable_Date_val;


CCLASS_CLASS
(
	nova_time_Nova_Date, 
	
	nova_time_Extension_VTable_Date* vtable;
	int nova_time_Nova_Date_Nova_year;
	int nova_time_Nova_Date_Nova_month;
	int nova_time_Nova_Date_Nova_day;
	int nova_time_Nova_Date_Nova_hour;
	int nova_time_Nova_Date_Nova_minute;
	int nova_time_Nova_Date_Nova_second;
)

void nova_time_Nova_Date_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
nova_time_Nova_Date* nova_time_Nova_Date_Nova_construct(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_time_Nova_Date_Nova_destroy(nova_time_Nova_Date** this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_time_Nova_Date_0_Nova_this(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_time_Nova_Date_Nova_decodeDate(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_time_Nova_Date_Nova_prototype, nova_Nova_String* nova_time_Nova_Date_Nova_date);
void nova_time_Nova_Date_Nova_updateTime(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_Nova_String* nova_time_Nova_Date_0_Nova_formatDate(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_time_Nova_Date_Nova_str);
nova_Nova_String* nova_time_Nova_Date_1_Nova_formatDate(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_time_Nova_Date_Nova_str, int nova_time_Nova_Date_Nova_first, int nova_time_Nova_Date_Nova_second, int nova_time_Nova_Date_Nova_third, int nova_time_Nova_Date_Nova_fourth, int nova_time_Nova_Date_Nova_fifth, int nova_time_Nova_Date_Nova_sixth);
void nova_time_Nova_Date_Nova_super(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
