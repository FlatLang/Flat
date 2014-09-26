#pragma once
#ifndef FILE_Math_NOVA
#define FILE_Math_NOVA

typedef struct nova_standard_NovaMath nova_standard_NovaMath;

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
#include <math.h>

typedef struct nova_VTable_nova_standard_NovaMath
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_NovaMath;

CCLASS_CLASS
(
	nova_standard_NovaMath, 
	
	nova_VTable_nova_standard_NovaMath* vtable;
)
extern double nova_standard_NovaMath_static_NovaPI;

void MathNova_init_static();
nova_standard_NovaMath* nova_standard_NovaMath_Novaconstruct(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_Math(nova_standard_NovaMath** this, nova_standard_exception_NovaExceptionData* exceptionData);
int nova_standard_NovaMath_static_Novarandom(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novarange);
double nova_standard_NovaMath_static_Novaabs(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber);
double nova_standard_NovaMath_static_Novasqrt(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber);
double nova_standard_NovaMath_static_Novapow(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novabase, double l0_Novapower);
double nova_standard_NovaMath_static_Novasin(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber);
double nova_standard_NovaMath_static_Novacos(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber);
double nova_standard_NovaMath_static_Novatan(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber);
double nova_standard_NovaMath_static_Novaasin(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber);
double nova_standard_NovaMath_static_Novaacos(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber);
double nova_standard_NovaMath_static_Novaatan(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber);
long_long nova_standard_NovaMath_static_Novaround(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber);
long_long nova_standard_NovaMath_static_Novafloor(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber);
long_long nova_standard_NovaMath_static_Novaceil(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber);
void nova_standard_NovaMath_Novathis(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaMath_Novasuper(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif