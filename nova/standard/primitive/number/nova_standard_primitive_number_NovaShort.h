#pragma once
#ifndef FILE_Short_NOVA
#define FILE_Short_NOVA

typedef struct nova_standard_primitive_number_NovaShort nova_standard_primitive_number_NovaShort;

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

typedef struct nova_VTable_nova_standard_primitive_number_NovaShort
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	int (*nova_standard_primitive_number_NovaShort_static_Novavirtual0_numDigits)(nova_standard_primitive_number_NovaShort*, nova_standard_exception_NovaExceptionData*, short);
	nova_standard_NovaString* (*nova_standard_primitive_number_NovaShort_static_Novavirtual0_toString)(nova_standard_primitive_number_NovaShort*, nova_standard_exception_NovaExceptionData*, short);
} nova_VTable_nova_standard_primitive_number_NovaShort;

CCLASS_CLASS
(
	nova_standard_primitive_number_NovaShort, 
	
	nova_VTable_nova_standard_primitive_number_NovaShort* vtable;
	short nova_standard_primitive_number_NovaShort_Novavalue;
)

void nova_standard_primitive_number_NovaShortNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_Short(nova_standard_primitive_number_NovaShort** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_primitive_number_NovaShort_Novathis(nova_standard_primitive_number_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novavalue);
int nova_standard_primitive_number_NovaShort_static_Nova0_numDigits(nova_standard_primitive_number_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novanumber);
nova_standard_NovaString* nova_standard_primitive_number_NovaShort_static_Nova0_toString(nova_standard_primitive_number_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novavalue);
void nova_standard_primitive_number_NovaShort_Novasuper(nova_standard_primitive_number_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif