#ifndef FILE_Math_FATHOM
#define FILE_Math_FATHOM

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


Math* fathom_Math_Math(ExceptionData* exceptionData);
void fathom_del_Math(Math** this, ExceptionData* exceptionData);
double fathom_Math_sqrt(ExceptionData* exceptionData, double fathom_number_14);
double fathom_Math_pow(ExceptionData* exceptionData, double fathom_base_20, double fathom_power_20);
double fathom_Math_sin(ExceptionData* exceptionData, double fathom_number_27);
double fathom_Math_cos(ExceptionData* exceptionData, double fathom_number_34);
double fathom_Math_tan(ExceptionData* exceptionData, double fathom_number_41);
double fathom_Math_asin(ExceptionData* exceptionData, double fathom_number_79);
double fathom_Math_acos(ExceptionData* exceptionData, double fathom_number_83);
double fathom_Math_atan(ExceptionData* exceptionData, double fathom_number_91);
long_long fathom_Math_round(ExceptionData* exceptionData, double fathom_number_97);
long_long fathom_Math_floor(ExceptionData* exceptionData, double fathom_number_102);
long_long fathom_Math_ceil(ExceptionData* exceptionData, double fathom_number_110);
#endif