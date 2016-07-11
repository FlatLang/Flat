#pragma once
#ifndef FILE_nova_standard_Nova_System_NOVA
#define FILE_nova_standard_Nova_System_NOVA

typedef struct nova_standard_Nova_System nova_standard_Nova_System;


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
#include <nova/standard/io/nova_standard_io_Nova_StreamReader.h>
#include <nova/standard/io/nova_standard_io_Nova_File.h>
#include <nova/standard/time/nova_standard_time_Nova_Time.h>
#include <nova/standard/process/nova_standard_process_Nova_Process.h>
#include <nova/standard/NativeSystem.h>


typedef struct nova_standard_Extension_VTable_System nova_standard_Extension_VTable_System;
struct nova_standard_Extension_VTable_System
{
	nova_Interface_VTable itable;
	long_long (*nova_standard_Nova_Object_virtual0_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual2_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_operators_Nova_Equals_virtual2_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
};

extern nova_standard_Extension_VTable_System nova_standard_Extension_VTable_System_val;


CCLASS_CLASS
(
	nova_standard_Nova_System, 
	
	nova_standard_Extension_VTable_System* vtable;
)

void nova_standard_Nova_SystemNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_Nova_System* nova_standard_Nova_System_Nova_System(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_Nova_System_Nova_destroy(nova_standard_Nova_System** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_Nova_System_0_Nova_exit(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_Nova_System_Nova_code);
void nova_standard_Nova_System_1_Nova_exit(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_Nova_System_Nova_code, nova_standard_Nova_String* nova_standard_Nova_System_Nova_message);
void nova_standard_Nova_System_2_Nova_exit(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_Nova_System_Nova_code, nova_standard_Nova_String* nova_standard_Nova_System_Nova_message, char nova_standard_Nova_System_Nova_log);
nova_standard_process_Nova_Process* nova_standard_Nova_System_Nova_execute(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_Nova_System_Nova_command);
void nova_standard_Nova_System_0_Nova_this(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_Nova_System_Nova_super(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
