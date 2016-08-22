#pragma once
#ifndef FILE_nova_standard_datastruct_list_Nova_Iterable_NOVA
#define FILE_nova_standard_datastruct_list_Nova_Iterable_NOVA

typedef struct nova_standard_datastruct_list_Nova_Iterable nova_standard_datastruct_list_Nova_Iterable;


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
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_IntArray.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_CharArray.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_DoubleArray.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_IntRange.h>
#include <nova/standard/thread/nova_standard_thread_Nova_Thread.h>
#include <nova/standard/thread/async/nova_standard_thread_async_Nova_Async.h>
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_Iterator.h>


typedef struct nova_standard_datastruct_list_Extension_VTable_Iterable nova_standard_datastruct_list_Extension_VTable_Iterable;
struct nova_standard_datastruct_list_Extension_VTable_Iterable
{
	nova_Interface_VTable itable;
	nova_standard_datastruct_list_Nova_Iterator* (*nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator)(nova_standard_datastruct_list_Nova_Iterable*, nova_standard_exception_Nova_ExceptionData*);
};

extern nova_standard_datastruct_list_Extension_VTable_Iterable nova_standard_datastruct_list_Extension_VTable_Iterable_val;


CCLASS_CLASS
(
	nova_standard_datastruct_list_Nova_Iterable, 
	
	nova_standard_datastruct_list_Extension_VTable_Iterable* vtable;
)

void nova_standard_datastruct_list_Nova_Iterable_Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_datastruct_list_Nova_Iterator* nova_standard_datastruct_list_Nova_Iterable_Accessor_Nova_iterator(nova_standard_datastruct_list_Nova_Iterable* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_datastruct_list_Nova_Iterator* nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator(nova_standard_datastruct_list_Nova_Iterable* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
