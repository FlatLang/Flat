#pragma once
#ifndef FILE_Math_NOVA
#define FILE_Math_NOVA

typedef struct Math Math;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaObject.h"
#include "NovaString.h"
#include "NovaIO.h"
#include "NovaInteger.h"
#include "NovaLong.h"
#include "NovaDouble.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"
#include <math.h>
#include <time.h>

Math* nova_Math_Math(ExceptionData* exceptionData);
void nova_del_Math(Math** this, ExceptionData* exceptionData);
int nova_Math_random(ExceptionData* exceptionData, long_long nova_0_range);
double nova_Math_abs(ExceptionData* exceptionData, double nova_0_number);
double nova_Math_sqrt(ExceptionData* exceptionData, double nova_0_number);
double nova_Math_pow(ExceptionData* exceptionData, double nova_0_base, double nova_0_power);
double nova_Math_sin(ExceptionData* exceptionData, double nova_0_number);
double nova_Math_cos(ExceptionData* exceptionData, double nova_0_number);
double nova_Math_tan(ExceptionData* exceptionData, double nova_0_number);
double nova_Math_asin(ExceptionData* exceptionData, double nova_0_number);
double nova_Math_acos(ExceptionData* exceptionData, double nova_0_number);
double nova_Math_atan(ExceptionData* exceptionData, double nova_0_number);
long_long nova_Math_round(ExceptionData* exceptionData, double nova_0_number);
long_long nova_Math_floor(ExceptionData* exceptionData, double nova_0_number);
long_long nova_Math_ceil(ExceptionData* exceptionData, double nova_0_number);

#endif