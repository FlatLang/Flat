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

int nova_Math_abs(ExceptionData* exceptionData, int nova_Math_number_26)
{
	return abs(nova_Math_number_26);
}

double nova_Math_sqrt(ExceptionData* exceptionData, double nova_Math_number_32)
{
	return sqrt(nova_Math_number_32);
}

int nova_Math_pow(ExceptionData* exceptionData, int nova_Math_base_39, int nova_Math_power_39)
{
	return pow(nova_Math_base_39, nova_Math_power_39);
}

double nova_Math_sin(ExceptionData* exceptionData, double nova_Math_number_46)
{
	return sin(nova_Math_number_46);
}

double nova_Math_cos(ExceptionData* exceptionData, double nova_Math_number_54)
{
	return cos(nova_Math_number_54);
}

double nova_Math_tan(ExceptionData* exceptionData, double nova_Math_number_62)
{
	return tan(nova_Math_number_62);
}

double nova_Math_asin(ExceptionData* exceptionData, double nova_Math_number_74)
{
	return asin(nova_Math_number_74);
}

double nova_Math_acos(ExceptionData* exceptionData, double nova_Math_number_94)
{
	return acos(nova_Math_number_94);
}

double nova_Math_atan(ExceptionData* exceptionData, double nova_Math_number_106)
{
	return atan(nova_Math_number_106);
}

long_long nova_Math_round(ExceptionData* exceptionData, double nova_Math_number_111)
{
	return nova_Math_floor(exceptionData, nova_Math_number_111 + 0.5);
}

long_long nova_Math_floor(ExceptionData* exceptionData, double nova_Math_number_118)
{
	return floor(nova_Math_number_118);
}

long_long nova_Math_ceil(ExceptionData* exceptionData, double nova_Math_number_124)
{
	return ceil(nova_Math_number_124);
}
