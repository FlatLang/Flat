#pragma once
#ifndef FILE_StabilityExceptionHandler_NOVA
#define FILE_StabilityExceptionHandler_NOVA

typedef struct stabilitytest_NovaStabilityExceptionHandler stabilitytest_NovaStabilityExceptionHandler;

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
#include <nova/standard/thread/nova_standard_thread_NovaUncaughtExceptionHandler.h>
#include <nova/standard/thread/nova_standard_thread_NovaThread.h>
#include <stabilitytest/stabilitytest_NovaStabilityTest.h>

typedef struct nova_VTable_stabilitytest_NovaStabilityExceptionHandler
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	void (*stabilitytest_NovaStabilityExceptionHandler_Novavirtual0_uncaughtException)(stabilitytest_NovaStabilityExceptionHandler*, nova_standard_exception_NovaExceptionData*, nova_standard_thread_NovaThread*, nova_standard_exception_NovaException*);
} nova_VTable_stabilitytest_NovaStabilityExceptionHandler;

CCLASS_CLASS
(
	stabilitytest_NovaStabilityExceptionHandler, 
	
	nova_VTable_stabilitytest_NovaStabilityExceptionHandler* vtable;
	struct Private* prv;
)

void stabilitytest_NovaStabilityExceptionHandlerNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
stabilitytest_NovaStabilityExceptionHandler* stabilitytest_NovaStabilityExceptionHandler_Novaconstruct(stabilitytest_NovaStabilityExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void nova_del_StabilityExceptionHandler(stabilitytest_NovaStabilityExceptionHandler** this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaStabilityExceptionHandler_Novathis(stabilitytest_NovaStabilityExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void stabilitytest_NovaStabilityExceptionHandler_Nova0_uncaughtException(stabilitytest_NovaStabilityExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_thread_NovaThread* l0_Novathread, nova_standard_exception_NovaException* l0_Novaexception);
void stabilitytest_NovaStabilityExceptionHandler_Novasuper(stabilitytest_NovaStabilityExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif