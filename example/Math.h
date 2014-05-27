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
double nova_Math_abs(ExceptionData* exceptionData, double nova_Math_number_52);
double nova_Math_sqrt(ExceptionData* exceptionData, double nova_Math_number_58);
double nova_Math_pow(ExceptionData* exceptionData, double nova_Math_base_105, double nova_Math_power_105);
double nova_Math_sin(ExceptionData* exceptionData, double nova_Math_number_122);
double nova_Math_cos(ExceptionData* exceptionData, double nova_Math_number_128);
double nova_Math_tan(ExceptionData* exceptionData, double nova_Math_number_138);
double nova_Math_asin(ExceptionData* exceptionData, double nova_Math_number_156);
double nova_Math_acos(ExceptionData* exceptionData, double nova_Math_number_161);
double nova_Math_atan(ExceptionData* exceptionData, double nova_Math_number_171);
long nova_Math_round(ExceptionData* exceptionData, double nova_Math_number_179);
long nova_Math_floor(ExceptionData* exceptionData, double nova_Math_number_183);
long nova_Math_ceil(ExceptionData* exceptionData, double nova_Math_number_186);
#endif