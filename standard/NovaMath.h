#pragma once
#ifndef FILE_Math_NOVA
#define FILE_Math_NOVA

typedef struct Math Math;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaObject.h>
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include "NovaMath.h"
#include <NovaConsole.h>
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaShort.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <math.h>

typedef struct nova_VTable_Math
{
	String* (*nova_virtual_4_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Math;

CCLASS_CLASS
(
	Math, 
	
	nova_VTable_Math* vtable;
)

Math* nova_Math_Math(Math* this, ExceptionData* exceptionData);
void nova_del_Math(Math** this, ExceptionData* exceptionData);
int nova_static_Math_random(Math* this, ExceptionData* exceptionData, long_long nova_0_range);
double nova_static_Math_abs(Math* this, ExceptionData* exceptionData, double nova_0_number);
double nova_static_Math_sqrt(Math* this, ExceptionData* exceptionData, double nova_0_number);
double nova_static_Math_pow(Math* this, ExceptionData* exceptionData, double nova_0_base, double nova_0_power);
double nova_static_Math_sin(Math* this, ExceptionData* exceptionData, double nova_0_number);
double nova_static_Math_cos(Math* this, ExceptionData* exceptionData, double nova_0_number);
double nova_static_Math_tan(Math* this, ExceptionData* exceptionData, double nova_0_number);
double nova_static_Math_asin(Math* this, ExceptionData* exceptionData, double nova_0_number);
double nova_static_Math_acos(Math* this, ExceptionData* exceptionData, double nova_0_number);
double nova_static_Math_atan(Math* this, ExceptionData* exceptionData, double nova_0_number);
long_long nova_static_Math_round(Math* this, ExceptionData* exceptionData, double nova_0_number);
long_long nova_static_Math_floor(Math* this, ExceptionData* exceptionData, double nova_0_number);
long_long nova_static_Math_ceil(Math* this, ExceptionData* exceptionData, double nova_0_number);

#endif