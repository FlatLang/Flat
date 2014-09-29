#pragma once
#ifndef FILE_Long_NOVA
#define FILE_Long_NOVA

typedef struct nova_standard_primitive_number_NovaLong nova_standard_primitive_number_NovaLong;

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

typedef struct nova_VTable_nova_standard_primitive_number_NovaLong
{
	nova_standard_NovaString* (*nova_standard_primitive_number_NovaLong_Novavirtual3_toString)(nova_standard_primitive_number_NovaLong*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	int (*nova_standard_primitive_number_NovaNumber_static_Novavirtual0_numDigits)(nova_standard_primitive_number_NovaNumber*, nova_standard_exception_NovaExceptionData*, int);
	nova_standard_NovaString* (*nova_standard_primitive_number_NovaNumber_static_Novavirtual1_toString)(nova_standard_primitive_number_NovaNumber*, nova_standard_exception_NovaExceptionData*, int);
} nova_VTable_nova_standard_primitive_number_NovaLong;

CCLASS_CLASS
(
	nova_standard_primitive_number_NovaLong, 
	
	nova_VTable_nova_standard_primitive_number_NovaLong* vtable;
	long_long nova_standard_primitive_number_NovaLong_Novavalue;
)

nova_standard_primitive_number_NovaLong* nova_standard_primitive_number_NovaLong_Novaconstruct(nova_standard_primitive_number_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novavalue);
void nova_del_Long(nova_standard_primitive_number_NovaLong** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_primitive_number_NovaLong_Novathis(nova_standard_primitive_number_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novavalue);
int nova_standard_primitive_number_NovaLong_static_NovanumDigits(nova_standard_primitive_number_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novanumber);
nova_standard_NovaString* nova_standard_primitive_number_NovaLong_static_Nova2_toString(nova_standard_primitive_number_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novavalue);
nova_standard_NovaString* nova_standard_primitive_number_NovaLong_Nova3_toString(nova_standard_primitive_number_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_primitive_number_NovaLong_Novasuper(nova_standard_primitive_number_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif