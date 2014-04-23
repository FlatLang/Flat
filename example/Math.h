#ifndef FILE_Math_FATHOM
#define FILE_Math_FATHOM

typedef struct Math Math;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include <math.h>

CLASS
(
	Math, 
	
	, struct Private* prv;
)

Math* fathom_Math_Math(ExceptionData* exceptionData);
void fathom_del_Math(Math** reference, ExceptionData* exceptionData);
double fathom_Math_sqrt(Math* reference, ExceptionData* exceptionData, double fathom_number_102);
double fathom_Math_pow(Math* reference, ExceptionData* exceptionData, double fathom_base_105, double fathom_power_105);
double fathom_Math_sin(Math* reference, ExceptionData* exceptionData, double fathom_number_108);
double fathom_Math_cos(Math* reference, ExceptionData* exceptionData, double fathom_number_111);
double fathom_Math_tan(Math* reference, ExceptionData* exceptionData, double fathom_number_114);
double fathom_Math_asin(Math* reference, ExceptionData* exceptionData, double fathom_number_117);
double fathom_Math_acos(Math* reference, ExceptionData* exceptionData, double fathom_number_120);
double fathom_Math_atan(Math* reference, ExceptionData* exceptionData, double fathom_number_123);
int fathom_Math_floor(Math* reference, ExceptionData* exceptionData, double fathom_number_126);
int fathom_Math_ceil(Math* reference, ExceptionData* exceptionData, double fathom_number_129);
extern Math* __static__Math;

#endif