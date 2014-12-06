#pragma once
#ifndef FILE_StabilityTestException_NOVA
#define FILE_StabilityTestException_NOVA

typedef struct stabilitytest_NovaStabilityTestException stabilitytest_NovaStabilityTestException;

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

typedef struct nova_VTable_stabilitytest_NovaStabilityTestException
{
	long (*nova_standard_NovaObject_virtual0_NovagetHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_virtual0_NovatoString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_virtual0_Novaequals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_stabilitytest_NovaStabilityTestException;

CCLASS_CLASS
(
	stabilitytest_NovaStabilityTestException, 
	
	nova_VTable_stabilitytest_NovaStabilityTestException* vtable;
	nova_standard_NovaString* nova_standard_exception_NovaException_Novamessage;
)

void stabilitytest_NovaStabilityTestExceptionNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
stabilitytest_NovaStabilityTestException* stabilitytest_NovaStabilityTestException_0_Novaconstruct(stabilitytest_NovaStabilityTestException* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_StabilityTestException(stabilitytest_NovaStabilityTestException** this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaStabilityTestException_Novathis(stabilitytest_NovaStabilityTestException* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaStabilityTestException_Novasuper(stabilitytest_NovaStabilityTestException* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif