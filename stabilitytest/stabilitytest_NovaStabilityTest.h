#pragma once
#ifndef FILE_StabilityTest_NOVA
#define FILE_StabilityTest_NOVA

typedef struct stabilitytest_NovaStabilityTest stabilitytest_NovaStabilityTest;

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
#include <nova/standard/time/nova_standard_time_NovaTime.h>
#include <stabilitytest/stabilitytest_NovaTimeStability.h>
#include <stabilitytest/stabilitytest_NovaFileStability.h>
#include <stabilitytest/stabilitytest_NovaThreadStability.h>
#include <stabilitytest/stabilitytest_NovaExceptionStability.h>
#include <stabilitytest/stabilitytest_NovaSyntaxStability.h>
#include <stabilitytest/stabilitytest_NovaClosureStability.h>
#include <stabilitytest/stabilitytest_NovaPolymorphismStability.h>
#include <stabilitytest/stabilitytest_NovaUnstableException.h>
#include <stabilitytest/stabilitytest_NovaNetworkStability.h>

typedef struct nova_VTable_stabilitytest_NovaStabilityTest
{
	long (*nova_standard_NovaObject_virtual0_NovagetHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_virtual0_NovatoString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_virtual0_Novaequals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_stabilitytest_NovaStabilityTest;

CCLASS_CLASS
(
	stabilitytest_NovaStabilityTest, 
	
	nova_VTable_stabilitytest_NovaStabilityTest* vtable;
)

void stabilitytest_NovaStabilityTestNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
stabilitytest_NovaStabilityTest* stabilitytest_NovaStabilityTest_0_Novaconstruct(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_StabilityTest(stabilitytest_NovaStabilityTest** this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaStabilityTest_static_Novamain(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs);
int stabilitytest_NovaStabilityTest_NovarunTests(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaStabilityTest_0_Novafail(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaStabilityTest_1_Novafail(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novamessage);
void stabilitytest_NovaStabilityTest_Novathis(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaStabilityTest_Novasuper(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif