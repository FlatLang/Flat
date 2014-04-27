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

CCLASS_CLASS
(
Math
)

Math* fathom_Math_Math(ExceptionData* exceptionData);
void fathom_del_Math(Math** this, ExceptionData* exceptionData);
double fathom_Math_sqrt(ExceptionData* exceptionData, double fathom_number_8);
double fathom_Math_pow(ExceptionData* exceptionData, double fathom_base_32, double fathom_power_32);
double fathom_Math_sin(ExceptionData* exceptionData, double fathom_number_40);
double fathom_Math_cos(ExceptionData* exceptionData, double fathom_number_43);
double fathom_Math_tan(ExceptionData* exceptionData, double fathom_number_46);
double fathom_Math_asin(ExceptionData* exceptionData, double fathom_number_49);
double fathom_Math_acos(ExceptionData* exceptionData, double fathom_number_55);
double fathom_Math_atan(ExceptionData* exceptionData, double fathom_number_60);
int fathom_Math_floor(ExceptionData* exceptionData, double fathom_number_69);
int fathom_Math_ceil(ExceptionData* exceptionData, double fathom_number_107);
extern Math* __static__Math;

#endif