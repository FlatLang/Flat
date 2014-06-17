#include <precompiled.h>

#include "NovaMath.h"

Math* nova_Math_Math(ExceptionData* exceptionData)
{
	Math* this = NULL;
	
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
	NOVA_FREE(*this);
}

int nova_Math_random(ExceptionData* exceptionData, long_long nova_0_range)
{
	return rand() % nova_0_range;
}

double nova_Math_abs(ExceptionData* exceptionData, double nova_0_number)
{
	return abs(nova_0_number);
}

double nova_Math_sqrt(ExceptionData* exceptionData, double nova_0_number)
{
	return sqrt(nova_0_number);
}

double nova_Math_pow(ExceptionData* exceptionData, double nova_0_base, double nova_0_power)
{
	return pow(nova_0_base, nova_0_power);
}

double nova_Math_sin(ExceptionData* exceptionData, double nova_0_number)
{
	return sin(nova_0_number);
}

double nova_Math_cos(ExceptionData* exceptionData, double nova_0_number)
{
	return cos(nova_0_number);
}

double nova_Math_tan(ExceptionData* exceptionData, double nova_0_number)
{
	return tan(nova_0_number);
}

double nova_Math_asin(ExceptionData* exceptionData, double nova_0_number)
{
	return asin(nova_0_number);
}

double nova_Math_acos(ExceptionData* exceptionData, double nova_0_number)
{
	return acos(nova_0_number);
}

double nova_Math_atan(ExceptionData* exceptionData, double nova_0_number)
{
	return atan(nova_0_number);
}

long_long nova_Math_round(ExceptionData* exceptionData, double nova_0_number)
{
	return floor(nova_0_number + 0.5);
}

long_long nova_Math_floor(ExceptionData* exceptionData, double nova_0_number)
{
	return floor(nova_0_number);
}

long_long nova_Math_ceil(ExceptionData* exceptionData, double nova_0_number)
{
	return ceil(nova_0_number);
}
