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

double nova_Math_sqrt(ExceptionData* exceptionData, double nova_Math_number_13)
{
	return sqrt(nova_Math_number_13);
}

double nova_Math_pow(ExceptionData* exceptionData, double nova_Math_base_18, double nova_Math_power_18)
{
	return pow(nova_Math_base_18, nova_Math_power_18);
}

double nova_Math_sin(ExceptionData* exceptionData, double nova_Math_number_21)
{
	return sin(nova_Math_number_21);
}

double nova_Math_cos(ExceptionData* exceptionData, double nova_Math_number_24)
{
	return cos(nova_Math_number_24);
}

double nova_Math_tan(ExceptionData* exceptionData, double nova_Math_number_27)
{
	return tan(nova_Math_number_27);
}

double nova_Math_asin(ExceptionData* exceptionData, double nova_Math_number_30)
{
	return asin(nova_Math_number_30);
}

double nova_Math_acos(ExceptionData* exceptionData, double nova_Math_number_33)
{
	return acos(nova_Math_number_33);
}

double nova_Math_atan(ExceptionData* exceptionData, double nova_Math_number_36)
{
	return atan(nova_Math_number_36);
}

long_long nova_Math_round(ExceptionData* exceptionData, double nova_Math_number_41)
{
	return nova_Math_floor(exceptionData, nova_Math_number_41 + 0.5);
}

long_long nova_Math_floor(ExceptionData* exceptionData, double nova_Math_number_44)
{
	return floor(nova_Math_number_44);
}

long_long nova_Math_ceil(ExceptionData* exceptionData, double nova_Math_number_94)
{
	return ceil(nova_Math_number_94);
}
