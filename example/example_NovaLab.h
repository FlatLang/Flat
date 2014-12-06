#pragma once
#ifndef FILE_example_NovaLab_NOVA
#define FILE_example_NovaLab_NOVA

typedef struct example_NovaLab example_NovaLab;

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
#include <nova/standard/math/nova_standard_math_NovaMath.h>
#include <nova/standard/star/nova_standard_star_NovaWindow.h>

typedef struct nova_VTable_example_NovaLab
{
	long (*nova_standard_NovaObject_virtual0_Nova_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_virtual0_Nova_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_virtual0_Nova_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_example_NovaLab;

extern nova_VTable_example_NovaLab nova_VTable_example_NovaLab_val;

CCLASS_CLASS
(
	example_NovaLab, 
	
	nova_VTable_example_NovaLab* vtable;
)

void example_NovaLabNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
example_NovaLab* example_NovaLab_0_Nova_construct(example_NovaLab* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaLab_Nova_destroy(example_NovaLab** this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaLab_Nova_main(example_NovaLab* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Nova_args);
void example_NovaLab_Nova_this(example_NovaLab* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaLab_Nova_super(example_NovaLab* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif
