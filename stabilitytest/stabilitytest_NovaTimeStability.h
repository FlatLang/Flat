#pragma once
#ifndef FILE_TimeStability_NOVA
#define FILE_TimeStability_NOVA

typedef struct stabilitytest_NovaTimeStability stabilitytest_NovaTimeStability;

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
#include <nova/standard/time/nova_standard_time_NovaTime.h>
#include <nova/standard/thread/nova_standard_thread_NovaThread.h>
#include <stabilitytest/stabilitytest_NovaStabilityTest.h>

typedef struct nova_VTable_stabilitytest_NovaTimeStability
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_stabilitytest_NovaTimeStability;

CCLASS_CLASS
(
	stabilitytest_NovaTimeStability, 
	
	nova_VTable_stabilitytest_NovaTimeStability* vtable;
)

void stabilitytest_NovaTimeStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
stabilitytest_NovaTimeStability* stabilitytest_NovaTimeStability_Nova0_construct(stabilitytest_NovaTimeStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_TimeStability(stabilitytest_NovaTimeStability** this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaTimeStability_static_Novatest(stabilitytest_NovaTimeStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void stabilitytest_NovaTimeStability_Novathis(stabilitytest_NovaTimeStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaTimeStability_Novasuper(stabilitytest_NovaTimeStability* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif