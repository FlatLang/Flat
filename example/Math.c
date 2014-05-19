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

double fathom_Math_sqrt(ExceptionData* exceptionData, double fathom_number_14)
{
	return sqrt(fathom_number_14);
}

double fathom_Math_pow(ExceptionData* exceptionData, double fathom_base_20, double fathom_power_20)
{
	return pow(fathom_base_20, fathom_power_20);
}

double fathom_Math_sin(ExceptionData* exceptionData, double fathom_number_27)
{
	return sin(fathom_number_27);
}

double fathom_Math_cos(ExceptionData* exceptionData, double fathom_number_34)
{
	return cos(fathom_number_34);
}

double fathom_Math_tan(ExceptionData* exceptionData, double fathom_number_41)
{
	return tan(fathom_number_41);
}

double fathom_Math_asin(ExceptionData* exceptionData, double fathom_number_79)
{
	return asin(fathom_number_79);
}

double fathom_Math_acos(ExceptionData* exceptionData, double fathom_number_83)
{
	return acos(fathom_number_83);
}

double fathom_Math_atan(ExceptionData* exceptionData, double fathom_number_91)
{
	return atan(fathom_number_91);
}

long_long fathom_Math_round(ExceptionData* exceptionData, double fathom_number_97)
{
	return fathom_Math_floor(exceptionData, fathom_number_97 + 0.5);
}

long_long fathom_Math_floor(ExceptionData* exceptionData, double fathom_number_102)
{
	return floor(fathom_number_102);
}

long_long fathom_Math_ceil(ExceptionData* exceptionData, double fathom_number_110)
{
	return ceil(fathom_number_110);
}
