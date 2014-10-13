#pragma once
#ifndef FILE_Spider_NOVA
#define FILE_Spider_NOVA

typedef struct example_NovaSpider example_NovaSpider;

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

typedef struct nova_VTable_example_NovaSpider
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*example_NovaAnimal_Novavirtual0_toString)(example_NovaAnimal*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	int (*example_NovaSpider_Novavirtual_getNumLegs)(example_NovaSpider*, nova_standard_exception_NovaExceptionData*);
	int (*example_NovaSpider_Novavirtual_getNumEyes)(example_NovaSpider*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*example_NovaSpider_Novavirtual_getDescription)(example_NovaSpider*, nova_standard_exception_NovaExceptionData*);
} nova_VTable_example_NovaSpider;

CCLASS_CLASS
(
	example_NovaSpider, 
	
	nova_VTable_example_NovaSpider* vtable;
)

void example_NovaSpiderNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
example_NovaSpider* example_NovaSpider_Nova0_construct(example_NovaSpider* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_Spider(example_NovaSpider** this, nova_standard_exception_NovaExceptionData* exceptionData);
int example_NovaSpider_NovagetNumLegs(example_NovaSpider* this, nova_standard_exception_NovaExceptionData* exceptionData);
int example_NovaSpider_NovagetNumEyes(example_NovaSpider* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* example_NovaSpider_NovagetDescription(example_NovaSpider* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaSpider_Novathis(example_NovaSpider* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_NovaSpider_Novasuper(example_NovaSpider* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif