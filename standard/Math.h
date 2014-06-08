#ifndef FILE_Math_NOVA
#define FILE_Math_NOVA

typedef struct Math Math;

#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
#include "DivideByZeroException.h"
#include <math.h>

Math* nova_Math_Math(ExceptionData* exceptionData);
void nova_del_Math(Math** this, ExceptionData* exceptionData);
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