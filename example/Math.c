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

double fathom_Math_sqrt(ExceptionData* exceptionData, double fathom_number_6)
{
	return sqrt(fathom_number_6);
}

double fathom_Math_pow(ExceptionData* exceptionData, double fathom_base_14, double fathom_power_14)
{
	return pow(fathom_base_14, fathom_power_14);
}

double fathom_Math_sin(ExceptionData* exceptionData, double fathom_number_19)
{
	return sin(fathom_number_19);
}

double fathom_Math_cos(ExceptionData* exceptionData, double fathom_number_22)
{
	return cos(fathom_number_22);
}

double fathom_Math_tan(ExceptionData* exceptionData, double fathom_number_25)
{
	return tan(fathom_number_25);
}

double fathom_Math_asin(ExceptionData* exceptionData, double fathom_number_93)
{
	return asin(fathom_number_93);
}

double fathom_Math_acos(ExceptionData* exceptionData, double fathom_number_97)
{
	return acos(fathom_number_97);
}

double fathom_Math_atan(ExceptionData* exceptionData, double fathom_number_100)
{
	return atan(fathom_number_100);
}

int fathom_Math_floor(ExceptionData* exceptionData, double fathom_number_103)
{
	return floor(fathom_number_103);
}

int fathom_Math_ceil(ExceptionData* exceptionData, double fathom_number_106)
{
	return ceil(fathom_number_106);
}
