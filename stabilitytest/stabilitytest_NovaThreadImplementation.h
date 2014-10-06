#pragma once
#ifndef FILE_ThreadImplementation_NOVA
#define FILE_ThreadImplementation_NOVA

typedef struct stabilitytest_NovaThreadImplementation stabilitytest_NovaThreadImplementation;

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
#include <nova/standard/gc/nova_standard_gc_NovaGC.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <nova/standard/thread/nova_standard_thread_NovaThread.h>

typedef struct nova_VTable_stabilitytest_NovaThreadImplementation
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	void (*stabilitytest_NovaThreadImplementation_Novavirtual_run)(stabilitytest_NovaThreadImplementation*, nova_standard_exception_NovaExceptionData*);
} nova_VTable_stabilitytest_NovaThreadImplementation;

CCLASS_CLASS
(
	stabilitytest_NovaThreadImplementation, 
	
	nova_VTable_stabilitytest_NovaThreadImplementation* vtable;
	struct Private* prv;
)

void stabilitytest_NovaThreadImplementationNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
stabilitytest_NovaThreadImplementation* stabilitytest_NovaThreadImplementation_Novaconstruct(stabilitytest_NovaThreadImplementation* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novatimes, int l0_Novamillis);
void nova_del_ThreadImplementation(stabilitytest_NovaThreadImplementation** this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaThreadImplementation_Novathis(stabilitytest_NovaThreadImplementation* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novatimes, int l0_Novamillis);
void stabilitytest_NovaThreadImplementation_Novarun(stabilitytest_NovaThreadImplementation* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaThreadImplementation_Novasuper(stabilitytest_NovaThreadImplementation* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif