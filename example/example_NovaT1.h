#pragma once
#ifndef FILE_T1_NOVA
#define FILE_T1_NOVA

typedef struct example_NovaT1 example_NovaT1;

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
#include <nova/standard/datastruct/nova_standard_datastruct_NovaArray.h>
#include <nova/standard/gc/nova_standard_gc_NovaGC.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>

typedef struct nova_VTable_example_NovaT1
{
	long (*nova_standard_NovaObject_virtual0_NovagetHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_virtual0_NovatoString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_virtual0_Novaequals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	int (*example_NovaT1_static_Accessor_Novaind)(example_NovaT1*, nova_standard_exception_NovaExceptionData*);
} nova_VTable_example_NovaT1;

CCLASS_CLASS
(
	example_NovaT1, 
	
	nova_VTable_example_NovaT1* vtable;
)

void example_NovaT1Nova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
example_NovaT1* example_NovaT1_0_Novaconstruct(example_NovaT1* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_T1(example_NovaT1** this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaT1_Novathis(example_NovaT1* this, nova_standard_exception_NovaExceptionData* exceptionData);
int example_NovaT1_static_Accessor_Novaind(example_NovaT1* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaT1_Novasuper(example_NovaT1* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif