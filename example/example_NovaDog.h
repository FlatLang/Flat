#pragma once
#ifndef FILE_Dog_NOVA
#define FILE_Dog_NOVA

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
#include <nova/standard/nova_standard_NovaMath.h>
#include <example/example_NovaAnimal.h>

typedef struct nova_VTable_example_NovaDog
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*example_NovaAnimal_Novavirtual0_toString)(example_NovaAnimal*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	int (*example_NovaDog_Novavirtual0_getNumLegs)(example_NovaDog*, nova_standard_exception_NovaExceptionData*);
	int (*example_NovaDog_Novavirtual0_getNumEyes)(example_NovaDog*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*example_NovaDog_Novavirtual0_getDescription)(example_NovaDog*, nova_standard_exception_NovaExceptionData*);
} nova_VTable_example_NovaDog;

CCLASS_CLASS
(
	example_NovaDog, 
	
	nova_VTable_example_NovaDog* vtable;
)

void example_NovaDogNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
example_NovaDog* example_NovaDog_Nova0_construct(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_Dog(example_NovaDog** this, nova_standard_exception_NovaExceptionData* exceptionData);
int example_NovaDog_Nova0_getNumLegs(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData);
int example_NovaDog_Nova0_getNumEyes(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* example_NovaDog_Nova0_getDescription(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaDog_Novathis(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaDog_Novasuper(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif