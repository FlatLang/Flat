#pragma once
#ifndef FILE_ExceptionStability_NOVA
#define FILE_ExceptionStability_NOVA

typedef struct stabilitytest_NovaExceptionStability stabilitytest_NovaExceptionStability;

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
#include <nova/standard/primitive/nova_standard_primitive_NovaChar.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaBool.h>
#include <nova/standard/gc/nova_standard_gc_NovaGC.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <stabilitytest/stabilitytest_NovaStabilityTest.h>
#include <stabilitytest/stabilitytest_NovaStabilityTestException.h>

typedef struct nova_VTable_stabilitytest_NovaExceptionStability
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_stabilitytest_NovaExceptionStability;

CCLASS_CLASS
(
	stabilitytest_NovaExceptionStability, 
	
	nova_VTable_stabilitytest_NovaExceptionStability* vtable;
)

void stabilitytest_NovaExceptionStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
stabilitytest_NovaExceptionStability* stabilitytest_NovaExceptionStability_Nova0_construct(stabilitytest_NovaExceptionStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_ExceptionStability(stabilitytest_NovaExceptionStability** this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaExceptionStability_static_Novatest(stabilitytest_NovaExceptionStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void stabilitytest_NovaExceptionStability_Novathis(stabilitytest_NovaExceptionStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaExceptionStability_Novasuper(stabilitytest_NovaExceptionStability* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif