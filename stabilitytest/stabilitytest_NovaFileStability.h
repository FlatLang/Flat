#pragma once
#ifndef FILE_FileStability_NOVA
#define FILE_FileStability_NOVA

typedef struct stabilitytest_NovaFileStability stabilitytest_NovaFileStability;

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
#include <nova/standard/io/nova_standard_io_NovaFile.h>
#include <nova/standard/time/nova_standard_time_NovaTime.h>
#include <stabilitytest/stabilitytest_NovaStabilityTest.h>

typedef struct nova_VTable_stabilitytest_NovaFileStability
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_stabilitytest_NovaFileStability;

CCLASS_CLASS
(
	stabilitytest_NovaFileStability, 
	
	nova_VTable_stabilitytest_NovaFileStability* vtable;
)

void stabilitytest_NovaFileStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
stabilitytest_NovaFileStability* stabilitytest_NovaFileStability_Nova0_construct(stabilitytest_NovaFileStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_FileStability(stabilitytest_NovaFileStability** this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaFileStability_static_Novatest(stabilitytest_NovaFileStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void stabilitytest_NovaFileStability_Novathis(stabilitytest_NovaFileStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaFileStability_Novasuper(stabilitytest_NovaFileStability* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif