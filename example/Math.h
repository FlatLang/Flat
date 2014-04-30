#ifndef FILE_Math_FATHOM
#define FILE_Math_FATHOM

typedef struct Math Math;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <math.h>

CCLASS_CLASS
(
	Math
)


Math* fathom_Math_Math(ExceptionData* exceptionData);
void fathom_del_Math(Math** this, ExceptionData* exceptionData);
double fathom_Math_sqrt(ExceptionData* exceptionData, double fathom_number_81);
double fathom_Math_pow(ExceptionData* exceptionData, double fathom_base_87, double fathom_power_87);
double fathom_Math_sin(ExceptionData* exceptionData, double fathom_number_117);
double fathom_Math_cos(ExceptionData* exceptionData, double fathom_number_126);
double fathom_Math_tan(ExceptionData* exceptionData, double fathom_number_138);
double fathom_Math_asin(ExceptionData* exceptionData, double fathom_number_141);
double fathom_Math_acos(ExceptionData* exceptionData, double fathom_number_144);
double fathom_Math_atan(ExceptionData* exceptionData, double fathom_number_147);
int fathom_Math_floor(ExceptionData* exceptionData, double fathom_number_150);
int fathom_Math_ceil(ExceptionData* exceptionData, double fathom_number_153);
extern Math* __static__Math;

#endif