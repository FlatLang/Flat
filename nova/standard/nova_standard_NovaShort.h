#pragma once
#ifndef FILE_Short_NOVA
#define FILE_Short_NOVA

typedef struct nova_standard_NovaShort nova_standard_NovaShort;

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

typedef struct nova_VTable_nova_standard_NovaShort
{
	nova_standard_NovaString* (*nova_standard_NovaShort_Novavirtual2_toString)(nova_standard_NovaShort*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	int (*nova_standard_NovaShort_static_Novavirtual0_numDigits)(nova_standard_NovaShort*, nova_standard_exception_NovaExceptionData*, short);
	nova_standard_NovaString* (*nova_standard_NovaShort_static_Novavirtual1_toString)(nova_standard_NovaShort*, nova_standard_exception_NovaExceptionData*, short);
} nova_VTable_nova_standard_NovaShort;

CCLASS_CLASS
(
	nova_standard_NovaShort, 
	
	nova_VTable_nova_standard_NovaShort* vtable;
	short nova_standard_NovaShort_Novavalue;
)

nova_standard_NovaShort* nova_standard_NovaShort_Novaconstruct(nova_standard_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novavalue);
void nova_del_Short(nova_standard_NovaShort** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaShort_Novathis(nova_standard_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novavalue);
int nova_standard_NovaShort_static_Novanull0_numDigits(nova_standard_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novanumber);
nova_standard_NovaString* nova_standard_NovaShort_static_Novanull1_toString(nova_standard_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novavalue);
nova_standard_NovaString* nova_standard_NovaShort_Novanull2_toString(nova_standard_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaShort_Novasuper(nova_standard_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif