#include "Math.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"
#include <math.h>

Math* nova_Math_Math(ExceptionData* exceptionData)
{
	CCLASS_NEW(Math, this,);
	
	{
	}
	
	return this;
}

void nova_del_Math(Math** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}

double nova_Math_abs(ExceptionData* exceptionData, double nova_Math_number_130)
{
	return abs(nova_Math_number_130);
}

double nova_Math_sqrt(ExceptionData* exceptionData, double nova_Math_number_136)
{
	return sqrt(nova_Math_number_136);
}

double nova_Math_pow(ExceptionData* exceptionData, double nova_Math_base_143, double nova_Math_power_143)
{
	return pow(nova_Math_base_143, nova_Math_power_143);
}

double nova_Math_sin(ExceptionData* exceptionData, double nova_Math_number_150)
{
	return sin(nova_Math_number_150);
}

double nova_Math_cos(ExceptionData* exceptionData, double nova_Math_number_159)
{
	return cos(nova_Math_number_159);
}

double nova_Math_tan(ExceptionData* exceptionData, double nova_Math_number_167)
{
	return tan(nova_Math_number_167);
}

double nova_Math_asin(ExceptionData* exceptionData, double nova_Math_number_174)
{
	return asin(nova_Math_number_174);
}

double nova_Math_acos(ExceptionData* exceptionData, double nova_Math_number_177)
{
	return acos(nova_Math_number_177);
}

double nova_Math_atan(ExceptionData* exceptionData, double nova_Math_number_180)
{
	return atan(nova_Math_number_180);
}

long nova_Math_round(ExceptionData* exceptionData, double nova_Math_number_183)
{
	return nova_Math_floor(exceptionData, nova_Math_number_183 + 0.5);
}

long nova_Math_floor(ExceptionData* exceptionData, double nova_Math_number_186)
{
	return floor(nova_Math_number_186);
}

long nova_Math_ceil(ExceptionData* exceptionData, double nova_Math_number_189)
{
	return ceil(nova_Math_number_189);
}
