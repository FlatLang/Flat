#pragma once
#ifndef FILE_ThreadStability_NOVA
#define FILE_ThreadStability_NOVA

typedef struct stabilitytest_NovaThreadStability stabilitytest_NovaThreadStability;

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
#include <nova/standard/thread/nova_standard_thread_NovaThread.h>
#include <stabilitytest/stabilitytest_NovaStabilityTest.h>
#include <stabilitytest/stabilitytest_NovaStabilityExceptionHandler.h>
#include <stabilitytest/stabilitytest_NovaThreadImplementation.h>

typedef struct nova_VTable_stabilitytest_NovaThreadStability
{
	long (*nova_standard_NovaObject_virtual0_NovagetHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_virtual0_NovatoString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_virtual0_Novaequals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_stabilitytest_NovaThreadStability;

CCLASS_CLASS
(
	stabilitytest_NovaThreadStability, 
	
	nova_VTable_stabilitytest_NovaThreadStability* vtable;
)

void stabilitytest_NovaThreadStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
stabilitytest_NovaThreadStability* stabilitytest_NovaThreadStability_0_Novaconstruct(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_ThreadStability(stabilitytest_NovaThreadStability** this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaThreadStability_static_Novatest(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void stabilitytest_NovaThreadStability_Novathis(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaThreadStability_Novasuper(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif