#pragma once
#ifndef FILE_example_Nova_Polygon_NOVA
#define FILE_example_Nova_Polygon_NOVA

typedef struct example_Nova_Polygon example_Nova_Polygon;


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


typedef struct example_Extension_VTable_Polygon example_Extension_VTable_Polygon;
struct example_Extension_VTable_Polygon
{
	nova_Interface_VTable itable;
	int (*example_Nova_Polygon_virtual1_Nova_numberSides)(example_Nova_Polygon*, nova_exception_Nova_ExceptionData*);
	double (*example_Nova_Polygon_virtual1_Nova_calculateArea)(example_Nova_Polygon*, nova_exception_Nova_ExceptionData*);
};

extern example_Extension_VTable_Polygon example_Extension_VTable_Polygon_val;


CCLASS_CLASS
(
	example_Nova_Polygon, 
	
	example_Extension_VTable_Polygon* vtable;
)

void example_Nova_Polygon_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
int example_Nova_Polygon_0_Nova_numberSides(example_Nova_Polygon* this, nova_exception_Nova_ExceptionData* exceptionData);double example_Nova_Polygon_0_Nova_calculateArea(example_Nova_Polygon* this, nova_exception_Nova_ExceptionData* exceptionData);int example_Nova_Polygon_virtual1_Nova_numberSides(example_Nova_Polygon* this, nova_exception_Nova_ExceptionData* exceptionData);
double example_Nova_Polygon_virtual1_Nova_calculateArea(example_Nova_Polygon* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
