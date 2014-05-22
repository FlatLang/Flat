#ifndef FILE_Math_NOVA
#define FILE_Math_NOVA

typedef struct Math Math;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include <math.h>

CCLASS_CLASS
(
	Math
)


Math* nova_Math_Math(ExceptionData* exceptionData);
void nova_del_Math(Math** this, ExceptionData* exceptionData);
double nova_Math_sqrt(ExceptionData* exceptionData, double nova_Math_number_13);
double nova_Math_pow(ExceptionData* exceptionData, double nova_Math_base_18, double nova_Math_power_18);
double nova_Math_sin(ExceptionData* exceptionData, double nova_Math_number_21);
double nova_Math_cos(ExceptionData* exceptionData, double nova_Math_number_24);
double nova_Math_tan(ExceptionData* exceptionData, double nova_Math_number_27);
double nova_Math_asin(ExceptionData* exceptionData, double nova_Math_number_30);
double nova_Math_acos(ExceptionData* exceptionData, double nova_Math_number_33);
double nova_Math_atan(ExceptionData* exceptionData, double nova_Math_number_36);
long_long nova_Math_round(ExceptionData* exceptionData, double nova_Math_number_41);
long_long nova_Math_floor(ExceptionData* exceptionData, double nova_Math_number_44);
long_long nova_Math_ceil(ExceptionData* exceptionData, double nova_Math_number_94);
#endif