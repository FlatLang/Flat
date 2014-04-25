#ifndef FILE_Math_FATHOM
#define FILE_Math_FATHOM

typedef struct Math Math;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <math.h>

CLASS
(
Math
)

Math* fathom_Math_Math(ExceptionData* exceptionData);
void fathom_del_Math(Math** this, ExceptionData* exceptionData);
double fathom_Math_sqrt(ExceptionData* exceptionData, double fathom_number_19);
double fathom_Math_pow(ExceptionData* exceptionData, double fathom_base_22, double fathom_power_22);
double fathom_Math_sin(ExceptionData* exceptionData, double fathom_number_25);
double fathom_Math_cos(ExceptionData* exceptionData, double fathom_number_28);
double fathom_Math_tan(ExceptionData* exceptionData, double fathom_number_31);
double fathom_Math_asin(ExceptionData* exceptionData, double fathom_number_34);
double fathom_Math_acos(ExceptionData* exceptionData, double fathom_number_37);
double fathom_Math_atan(ExceptionData* exceptionData, double fathom_number_40);
int fathom_Math_floor(ExceptionData* exceptionData, double fathom_number_43);
int fathom_Math_ceil(ExceptionData* exceptionData, double fathom_number_46);
extern Math* __static__Math;

#endif