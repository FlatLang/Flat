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
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_Iterator.h>


typedef struct nova_standard_datastruct_list_Extension_VTable_Iterable nova_standard_datastruct_list_Extension_VTable_Iterable;
extern nova_standard_datastruct_list_Extension_VTable_Iterable nova_standard_datastruct_list_Extension_VTable_Iterable_val;


CCLASS_CLASS
(
	nova_standard_datastruct_list_Nova_Iterable, 
	
	nova_standard_datastruct_list_Extension_VTable_Iterable* vtable;
	nova_standard_datastruct_list_Nova_Iterator* nova_standard_datastruct_list_Nova_Iterable_Nova_iterator;
)

void nova_standard_datastruct_list_Nova_IterableNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
