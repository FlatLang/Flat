#include "Math.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <math.h>

Math* __static__Math;

Math* fathom_Math_Math(ExceptionData* exceptionData)
{
	CCLASS_NEW(Math, this,);
	
	{
	}
	
	return this;
}

void fathom_del_Math(Math** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}

double fathom_Math_sqrt(ExceptionData* exceptionData, double fathom_number_95)
{
	return sqrt(fathom_number_95);
}

double fathom_Math_pow(ExceptionData* exceptionData, double fathom_base_103, double fathom_power_103)
{
	return pow(fathom_base_103, fathom_power_103);
}

double fathom_Math_sin(ExceptionData* exceptionData, double fathom_number_108)
{
	return sin(fathom_number_108);
}

double fathom_Math_cos(ExceptionData* exceptionData, double fathom_number_111)
{
	return cos(fathom_number_111);
}

double fathom_Math_tan(ExceptionData* exceptionData, double fathom_number_114)
{
	return tan(fathom_number_114);
}

double fathom_Math_asin(ExceptionData* exceptionData, double fathom_number_117)
{
	return asin(fathom_number_117);
}

double fathom_Math_acos(ExceptionData* exceptionData, double fathom_number_120)
{
	return acos(fathom_number_120);
}

double fathom_Math_atan(ExceptionData* exceptionData, double fathom_number_123)
{
	return atan(fathom_number_123);
}

int fathom_Math_floor(ExceptionData* exceptionData, double fathom_number_126)
{
	return floor(fathom_number_126);
}

int fathom_Math_ceil(ExceptionData* exceptionData, double fathom_number_129)
{
	return ceil(fathom_number_129);
}
