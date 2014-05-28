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

double nova_Math_abs(ExceptionData* exceptionData, double nova_Math_number_104)
{
	return abs(nova_Math_number_104);
}

double nova_Math_sqrt(ExceptionData* exceptionData, double nova_Math_number_107)
{
	return sqrt(nova_Math_number_107);
}

double nova_Math_pow(ExceptionData* exceptionData, double nova_Math_base_113, double nova_Math_power_113)
{
	return pow(nova_Math_base_113, nova_Math_power_113);
}

double nova_Math_sin(ExceptionData* exceptionData, double nova_Math_number_131)
{
	return sin(nova_Math_number_131);
}

double nova_Math_cos(ExceptionData* exceptionData, double nova_Math_number_139)
{
	return cos(nova_Math_number_139);
}

double nova_Math_tan(ExceptionData* exceptionData, double nova_Math_number_143)
{
	return tan(nova_Math_number_143);
}

double nova_Math_asin(ExceptionData* exceptionData, double nova_Math_number_151)
{
	return asin(nova_Math_number_151);
}

double nova_Math_acos(ExceptionData* exceptionData, double nova_Math_number_159)
{
	return acos(nova_Math_number_159);
}

double nova_Math_atan(ExceptionData* exceptionData, double nova_Math_number_171)
{
	return atan(nova_Math_number_171);
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
