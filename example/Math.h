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
double nova_Math_pow(ExceptionData* exceptionData, double nova_Math_base_64, double nova_Math_power_64);
double nova_Math_sin(ExceptionData* exceptionData, double nova_Math_number_72);
double nova_Math_cos(ExceptionData* exceptionData, double nova_Math_number_77);
double nova_Math_tan(ExceptionData* exceptionData, double nova_Math_number_86);
double nova_Math_asin(ExceptionData* exceptionData, double nova_Math_number_97);
double nova_Math_acos(ExceptionData* exceptionData, double nova_Math_number_132);
double nova_Math_atan(ExceptionData* exceptionData, double nova_Math_number_135);
long nova_Math_round(ExceptionData* exceptionData, double nova_Math_number_138);
long nova_Math_floor(ExceptionData* exceptionData, double nova_Math_number_159);
long nova_Math_ceil(ExceptionData* exceptionData, double nova_Math_number_173);
#endif