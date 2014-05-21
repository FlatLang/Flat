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
double nova_Math_sqrt(ExceptionData* exceptionData, double nova_number_53);
double nova_Math_pow(ExceptionData* exceptionData, double nova_base_56, double nova_power_56);
double nova_Math_sin(ExceptionData* exceptionData, double nova_number_59);
double nova_Math_cos(ExceptionData* exceptionData, double nova_number_62);
double nova_Math_tan(ExceptionData* exceptionData, double nova_number_65);
double nova_Math_asin(ExceptionData* exceptionData, double nova_number_68);
double nova_Math_acos(ExceptionData* exceptionData, double nova_number_75);
double nova_Math_atan(ExceptionData* exceptionData, double nova_number_87);
long_long nova_Math_round(ExceptionData* exceptionData, double nova_number_98);
long_long nova_Math_floor(ExceptionData* exceptionData, double nova_number_106);
long_long nova_Math_ceil(ExceptionData* exceptionData, double nova_number_109);
#endif