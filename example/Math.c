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

double fathom_Math_sqrt(ExceptionData* exceptionData, double fathom_number_81)
{
	return sqrt(fathom_number_81);
}

double fathom_Math_pow(ExceptionData* exceptionData, double fathom_base_87, double fathom_power_87)
{
	return pow(fathom_base_87, fathom_power_87);
}

double fathom_Math_sin(ExceptionData* exceptionData, double fathom_number_117)
{
	return sin(fathom_number_117);
}

double fathom_Math_cos(ExceptionData* exceptionData, double fathom_number_126)
{
	return cos(fathom_number_126);
}

double fathom_Math_tan(ExceptionData* exceptionData, double fathom_number_138)
{
	return tan(fathom_number_138);
}

double fathom_Math_asin(ExceptionData* exceptionData, double fathom_number_141)
{
	return asin(fathom_number_141);
}

double fathom_Math_acos(ExceptionData* exceptionData, double fathom_number_144)
{
	return acos(fathom_number_144);
}

double fathom_Math_atan(ExceptionData* exceptionData, double fathom_number_147)
{
	return atan(fathom_number_147);
}

int fathom_Math_floor(ExceptionData* exceptionData, double fathom_number_150)
{
	return floor(fathom_number_150);
}

int fathom_Math_ceil(ExceptionData* exceptionData, double fathom_number_153)
{
	return ceil(fathom_number_153);
}
