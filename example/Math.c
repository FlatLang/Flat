#include "Math.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include <math.h>

Math* __static__Math;



Math* fathom_Math_Math(ExceptionData* exceptionData)
{
	NEW(Math, reference,);
	
	{
	}
	
	return reference;
}

void fathom_del_Math(Math** reference, ExceptionData* exceptionData)
{
	if (!*reference)
	{
		return;
	}
	
	
	{
	}
	free(*reference);
}

double fathom_Math_sqrt(Math* reference, ExceptionData* exceptionData, double fathom_number_102)
{
	return sqrt(fathom_number_102);
}

double fathom_Math_pow(Math* reference, ExceptionData* exceptionData, double fathom_base_105, double fathom_power_105)
{
	return pow(fathom_base_105, fathom_power_105);
}

double fathom_Math_sin(Math* reference, ExceptionData* exceptionData, double fathom_number_108)
{
	return sin(fathom_number_108);
}

double fathom_Math_cos(Math* reference, ExceptionData* exceptionData, double fathom_number_111)
{
	return cos(fathom_number_111);
}

double fathom_Math_tan(Math* reference, ExceptionData* exceptionData, double fathom_number_114)
{
	return tan(fathom_number_114);
}

double fathom_Math_asin(Math* reference, ExceptionData* exceptionData, double fathom_number_117)
{
	return asin(fathom_number_117);
}

double fathom_Math_acos(Math* reference, ExceptionData* exceptionData, double fathom_number_120)
{
	return acos(fathom_number_120);
}

double fathom_Math_atan(Math* reference, ExceptionData* exceptionData, double fathom_number_123)
{
	return atan(fathom_number_123);
}

int fathom_Math_floor(Math* reference, ExceptionData* exceptionData, double fathom_number_126)
{
	return floor(fathom_number_126);
}

int fathom_Math_ceil(Math* reference, ExceptionData* exceptionData, double fathom_number_129)
{
	return ceil(fathom_number_129);
}
