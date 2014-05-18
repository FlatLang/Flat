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
double fathom_Math_sqrt(ExceptionData* exceptionData, double fathom_number_67);
double fathom_Math_pow(ExceptionData* exceptionData, double fathom_base_77, double fathom_power_77);
double fathom_Math_sin(ExceptionData* exceptionData, double fathom_number_82);
double fathom_Math_cos(ExceptionData* exceptionData, double fathom_number_85);
double fathom_Math_tan(ExceptionData* exceptionData, double fathom_number_92);
double fathom_Math_asin(ExceptionData* exceptionData, double fathom_number_102);
double fathom_Math_acos(ExceptionData* exceptionData, double fathom_number_105);
double fathom_Math_atan(ExceptionData* exceptionData, double fathom_number_108);
long_long fathom_Math_round(ExceptionData* exceptionData, double fathom_number_111);
long_long fathom_Math_floor(ExceptionData* exceptionData, double fathom_number_114);
long_long fathom_Math_ceil(ExceptionData* exceptionData, double fathom_number_117);
#endif