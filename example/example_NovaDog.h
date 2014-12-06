#pragma once
#ifndef FILE_example_NovaDog_NOVA
#define FILE_example_NovaDog_NOVA

typedef struct example_NovaDog example_NovaDog;

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
#include <example/example_NovaAnimal.h>

typedef struct nova_VTable_example_NovaDog
{
	long (*nova_standard_NovaObject_virtual0_NovagetHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*example_NovaAnimal_virtual0_NovatoString)(example_NovaAnimal*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_virtual0_Novaequals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	int (*example_NovaDog_virtual_NovagetNumLegs)(example_NovaDog*, nova_standard_exception_NovaExceptionData*);
	int (*example_NovaDog_virtual_NovagetNumEyes)(example_NovaDog*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*example_NovaDog_virtual_NovagetDescription)(example_NovaDog*, nova_standard_exception_NovaExceptionData*);
} nova_VTable_example_NovaDog;

CCLASS_CLASS
(
	example_NovaDog, 
	
	nova_VTable_example_NovaDog* vtable;
)

void example_NovaDogNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
example_NovaDog* example_NovaDog_0_Novaconstruct(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaDog_Novadestroy(example_NovaDog** this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaDog_Novathis(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData);
int example_NovaDog_NovagetNumLegs(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData);
int example_NovaDog_NovagetNumEyes(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* example_NovaDog_NovagetDescription(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaDog_Novasuper(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif