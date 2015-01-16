#pragma once
#ifndef FILE_nova_standard_time_Nova_Date_NOVA
#define FILE_nova_standard_time_Nova_Date_NOVA

typedef struct nova_standard_time_Nova_Date nova_standard_time_Nova_Date;

#include <Nova.h>
#include <ExceptionHandler.h>
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
#include <nova/standard/time/NativeDate.h>

typedef struct nova_standard_time_VTable_Date
{
	long (*nova_standard_Nova_Object_virtual1_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual0_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_Nova_Object_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
} nova_standard_time_VTable_Date;

extern nova_standard_time_VTable_Date nova_standard_time_VTable_Date_val;

CCLASS_CLASS
(
	nova_standard_time_Nova_Date, 
	
	nova_standard_time_VTable_Date* vtable;
	int nova_standard_time_Nova_Date_Nova_year;
	int nova_standard_time_Nova_Date_Nova_month;
	int nova_standard_time_Nova_Date_Nova_day;
	int nova_standard_time_Nova_Date_Nova_hour;
	int nova_standard_time_Nova_Date_Nova_minute;
	int nova_standard_time_Nova_Date_Nova_second;
)

void nova_standard_time_Nova_DateNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_time_Nova_Date* nova_standard_time_Nova_Date_2_Nova_construct(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_time_Nova_Date_Nova_destroy(nova_standard_time_Nova_Date** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_time_Nova_Date_2_Nova_this(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_time_Nova_Date_Nova_decodeDate(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_prototype, nova_standard_Nova_String* l0_Nova_date);
void nova_standard_time_Nova_Date_Nova_updateTime(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_Nova_String* nova_standard_time_Nova_Date_0_Nova_formatDate(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_str);
nova_standard_Nova_String* nova_standard_time_Nova_Date_1_Nova_formatDate(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_str, int l0_Nova_first, int l0_Nova_second, int l0_Nova_third, int l0_Nova_fourth, int l0_Nova_fifth, int l0_Nova_sixth);
void nova_standard_time_Nova_Date_Nova_super(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
