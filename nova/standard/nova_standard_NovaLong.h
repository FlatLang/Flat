#pragma once
#ifndef FILE_Long_NOVA
#define FILE_Long_NOVA

typedef struct nova_standard_NovaLong nova_standard_NovaLong;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_NovaException.h>
#include <nova/standard/exception/nova_standard_exception_NovaDivideByZeroException.h>
#include <nova/standard/nova_standard_NovaNull.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <nova/standard/nova_standard_NovaConsole.h>
#include <nova/standard/nova_standard_NovaGC.h>
#include <nova/standard/nova_standard_NovaNumber.h>
#include <nova/standard/nova_standard_NovaByte.h>
#include <nova/standard/nova_standard_NovaShort.h>
#include <nova/standard/nova_standard_NovaInt.h>
#include <nova/standard/nova_standard_NovaLong.h>
#include <nova/standard/nova_standard_NovaFloat.h>
#include <nova/standard/nova_standard_NovaDouble.h>
#include <nova/standard/nova_standard_NovaChar.h>
#include <nova/standard/nova_standard_NovaBool.h>

typedef struct nova_VTable_nova_standard_NovaLong
{
	nova_standard_NovaString* (*nova_standard_NovaLong_Novavirtual3_toString)(nova_standard_NovaLong*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	int (*nova_standard_NovaNumber_static_Novavirtual0_numDigits)(nova_standard_NovaNumber*, nova_standard_exception_NovaExceptionData*, int);
	nova_standard_NovaString* (*nova_standard_NovaNumber_static_Novavirtual1_toString)(nova_standard_NovaNumber*, nova_standard_exception_NovaExceptionData*, int);
} nova_VTable_nova_standard_NovaLong;

CCLASS_CLASS
(
	nova_standard_NovaLong, 
	
	nova_VTable_nova_standard_NovaLong* vtable;
	long_long nova_standard_NovaLong_Novavalue;
)

nova_standard_NovaLong* nova_standard_NovaLong_Novaconstruct(nova_standard_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novavalue);
void nova_del_Long(nova_standard_NovaLong** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaLong_Novathis(nova_standard_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novavalue);
int nova_standard_NovaLong_static_NovanumDigits(nova_standard_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novanumber);
nova_standard_NovaString* nova_standard_NovaLong_static_Novanull2_toString(nova_standard_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novavalue);
nova_standard_NovaString* nova_standard_NovaLong_Novanull3_toString(nova_standard_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaLong_Novasuper(nova_standard_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif