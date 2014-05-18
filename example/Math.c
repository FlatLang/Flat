#include "Math.h"
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

double fathom_Math_sqrt(ExceptionData* exceptionData, double fathom_number_133)
{
	return sqrt(fathom_number_133);
}

double fathom_Math_pow(ExceptionData* exceptionData, double fathom_base_141, double fathom_power_141)
{
	return pow(fathom_base_141, fathom_power_141);
}

double fathom_Math_sin(ExceptionData* exceptionData, double fathom_number_144)
{
	return sin(fathom_number_144);
}

double fathom_Math_cos(ExceptionData* exceptionData, double fathom_number_147)
{
	return cos(fathom_number_147);
}

double fathom_Math_tan(ExceptionData* exceptionData, double fathom_number_150)
{
	return tan(fathom_number_150);
}

double fathom_Math_asin(ExceptionData* exceptionData, double fathom_number_153)
{
	return asin(fathom_number_153);
}

double fathom_Math_acos(ExceptionData* exceptionData, double fathom_number_156)
{
	return acos(fathom_number_156);
}

double fathom_Math_atan(ExceptionData* exceptionData, double fathom_number_159)
{
	return atan(fathom_number_159);
}

long_long fathom_Math_round(ExceptionData* exceptionData, double fathom_number_162)
{
	return fathom_Math_floor(exceptionData, fathom_number_162 + 0.5);
}

long_long fathom_Math_floor(ExceptionData* exceptionData, double fathom_number_165)
{
	return floor(fathom_number_165);
}

long_long fathom_Math_ceil(ExceptionData* exceptionData, double fathom_number_168)
{
	return ceil(fathom_number_168);
}
