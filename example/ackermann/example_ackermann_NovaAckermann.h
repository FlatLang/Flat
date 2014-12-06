#pragma once
#ifndef FILE_example_ackermann_NovaAckermann_NOVA
#define FILE_example_ackermann_NovaAckermann_NOVA

typedef struct example_ackermann_NovaAckermann example_ackermann_NovaAckermann;

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

typedef struct nova_VTable_example_ackermann_NovaAckermann
{
	long (*nova_standard_NovaObject_virtual0_NovagetHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_virtual0_NovatoString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_virtual0_Novaequals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_example_ackermann_NovaAckermann;

CCLASS_CLASS
(
	example_ackermann_NovaAckermann, 
	
	nova_VTable_example_ackermann_NovaAckermann* vtable;
)

void example_ackermann_NovaAckermannNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
example_ackermann_NovaAckermann* example_ackermann_NovaAckermann_0_Novaconstruct(example_ackermann_NovaAckermann* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_ackermann_NovaAckermann_Novadestroy(example_ackermann_NovaAckermann** this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_ackermann_NovaAckermann_static_Novamain(example_ackermann_NovaAckermann* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs);
int example_ackermann_NovaAckermann_static_Novarun(example_ackermann_NovaAckermann* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novam, int l0_Novan);
int example_ackermann_NovaAckermann_static_Novarun2(example_ackermann_NovaAckermann* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novam, int l0_Novan);
void example_ackermann_NovaAckermann_Novathis(example_ackermann_NovaAckermann* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_ackermann_NovaAckermann_Novasuper(example_ackermann_NovaAckermann* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif