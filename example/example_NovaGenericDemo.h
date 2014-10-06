#pragma once
#ifndef FILE_GenericDemo_NOVA
#define FILE_GenericDemo_NOVA

typedef struct example_NovaGenericDemo example_NovaGenericDemo;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_NovaException.h>
#include <nova/standard/exception/nova_standard_exception_NovaDivideByZeroException.h>
#include <nova/standard/io/nova_standard_io_NovaConsole.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaNumber.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaByte.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaShort.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaInt.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaLong.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaFloat.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaDouble.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaNull.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaChar.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaBool.h>
#include <nova/standard/gc/nova_standard_gc_NovaGC.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaStack.h>

typedef struct nova_VTable_example_NovaGenericDemo
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_example_NovaGenericDemo;

CCLASS_CLASS
(
	example_NovaGenericDemo, 
	
	nova_VTable_example_NovaGenericDemo* vtable;
)

void example_NovaGenericDemoNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
example_NovaGenericDemo* example_NovaGenericDemo_Nova0_construct(example_NovaGenericDemo* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_GenericDemo(example_NovaGenericDemo** this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaGenericDemo_static_Novamain(example_NovaGenericDemo* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs);
void example_NovaGenericDemo_Novathis(example_NovaGenericDemo* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaGenericDemo_Novasuper(example_NovaGenericDemo* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif