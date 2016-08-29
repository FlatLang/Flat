#pragma once
#ifndef FILE_nova_datastruct_Nova_Comparable_NOVA
#define FILE_nova_datastruct_Nova_Comparable_NOVA

typedef struct nova_datastruct_Nova_Comparable nova_datastruct_Nova_Comparable;


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


typedef struct nova_datastruct_Extension_VTable_Comparable nova_datastruct_Extension_VTable_Comparable;
struct nova_datastruct_Extension_VTable_Comparable
{
	nova_Interface_VTable itable;
	int (*nova_datastruct_Nova_Comparable_virtual0_Nova_compareTo)(nova_datastruct_Nova_Comparable*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
};

extern nova_datastruct_Extension_VTable_Comparable nova_datastruct_Extension_VTable_Comparable_val;


CCLASS_CLASS
(
	nova_datastruct_Nova_Comparable, 
	
	nova_datastruct_Extension_VTable_Comparable* vtable;
)

void nova_datastruct_Nova_Comparable_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
int nova_datastruct_Nova_Comparable_0_Nova_compareTo(nova_datastruct_Nova_Comparable* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_Comparable_Nova_other);int nova_datastruct_Nova_Comparable_virtual0_Nova_compareTo(nova_datastruct_Nova_Comparable* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_Comparable_Nova_other);

#endif
