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
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"
#include <math.h>

CCLASS_CLASS
(
	Math
)


Math* nova_Math_Math(ExceptionData* exceptionData);
void nova_del_Math(Math** this, ExceptionData* exceptionData);
int nova_Math_abs(ExceptionData* exceptionData, int nova_Math_number_26);
double nova_Math_sqrt(ExceptionData* exceptionData, double nova_Math_number_32);
int nova_Math_pow(ExceptionData* exceptionData, int nova_Math_base_39, int nova_Math_power_39);
double nova_Math_sin(ExceptionData* exceptionData, double nova_Math_number_46);
double nova_Math_cos(ExceptionData* exceptionData, double nova_Math_number_54);
double nova_Math_tan(ExceptionData* exceptionData, double nova_Math_number_62);
double nova_Math_asin(ExceptionData* exceptionData, double nova_Math_number_74);
double nova_Math_acos(ExceptionData* exceptionData, double nova_Math_number_94);
double nova_Math_atan(ExceptionData* exceptionData, double nova_Math_number_106);
long_long nova_Math_round(ExceptionData* exceptionData, double nova_Math_number_111);
long_long nova_Math_floor(ExceptionData* exceptionData, double nova_Math_number_118);
long_long nova_Math_ceil(ExceptionData* exceptionData, double nova_Math_number_124);
#endif