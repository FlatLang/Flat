#pragma once
#ifndef FILE_Double_NOVA
#define FILE_Double_NOVA

typedef struct nova_standard_primitive_number_NovaDouble nova_standard_primitive_number_NovaDouble;

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

typedef struct nova_VTable_nova_standard_primitive_number_NovaDouble
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	int (*nova_standard_primitive_number_NovaDouble_static_Novavirtual0_numDigits)(nova_standard_primitive_number_NovaDouble*, nova_standard_exception_NovaExceptionData*, double);
	nova_standard_NovaString* (*nova_standard_primitive_number_NovaDouble_static_Novavirtual0_toString)(nova_standard_primitive_number_NovaDouble*, nova_standard_exception_NovaExceptionData*, double);
} nova_VTable_nova_standard_primitive_number_NovaDouble;

CCLASS_CLASS
(
	nova_standard_primitive_number_NovaDouble, 
	
	nova_VTable_nova_standard_primitive_number_NovaDouble* vtable;
	double nova_standard_primitive_number_NovaDouble_Novavalue;
)

void nova_standard_primitive_number_NovaDoubleNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_Double(nova_standard_primitive_number_NovaDouble** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_primitive_number_NovaDouble_Novathis(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novavalue);
int nova_standard_primitive_number_NovaDouble_static_Nova0_numDigits(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber);
nova_standard_NovaString* nova_standard_primitive_number_NovaDouble_static_Nova0_toString(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novavalue);
double nova_standard_primitive_number_NovaDouble_static_NovaparseDouble(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastr);
void nova_standard_primitive_number_NovaDouble_Novasuper(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif