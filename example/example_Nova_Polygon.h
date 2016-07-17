#pragma once
#ifndef FILE_example_Nova_Polygon_NOVA
#define FILE_example_Nova_Polygon_NOVA

typedef struct example_Nova_Polygon example_Nova_Polygon;


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
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_IntRange.h>
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>


typedef struct example_Extension_VTable_Polygon example_Extension_VTable_Polygon;
struct example_Extension_VTable_Polygon
{
	nova_Interface_VTable itable;
	int (*example_Nova_Polygon_virtual1_Nova_numberSides)(example_Nova_Polygon*, nova_standard_exception_Nova_ExceptionData*);
	double (*example_Nova_Polygon_virtual1_Nova_calculateArea)(example_Nova_Polygon*, nova_standard_exception_Nova_ExceptionData*);
};

extern example_Extension_VTable_Polygon example_Extension_VTable_Polygon_val;


CCLASS_CLASS
(
	example_Nova_Polygon, 
	
	example_Extension_VTable_Polygon* vtable;
)

void example_Nova_PolygonNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
int example_Nova_Polygon_0_Nova_numberSides(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData);double example_Nova_Polygon_0_Nova_calculateArea(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData);int example_Nova_Polygon_virtual1_Nova_numberSides(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
double example_Nova_Polygon_virtual1_Nova_calculateArea(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
