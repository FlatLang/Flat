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

double nova_Math_sqrt(ExceptionData* exceptionData, double nova_number_53)
{
	return sqrt(nova_number_53);
}

double nova_Math_pow(ExceptionData* exceptionData, double nova_base_56, double nova_power_56)
{
	return pow(nova_base_56, nova_power_56);
}

double nova_Math_sin(ExceptionData* exceptionData, double nova_number_59)
{
	return sin(nova_number_59);
}

double nova_Math_cos(ExceptionData* exceptionData, double nova_number_62)
{
	return cos(nova_number_62);
}

double nova_Math_tan(ExceptionData* exceptionData, double nova_number_65)
{
	return tan(nova_number_65);
}

double nova_Math_asin(ExceptionData* exceptionData, double nova_number_68)
{
	return asin(nova_number_68);
}

double nova_Math_acos(ExceptionData* exceptionData, double nova_number_75)
{
	return acos(nova_number_75);
}

double nova_Math_atan(ExceptionData* exceptionData, double nova_number_87)
{
	return atan(nova_number_87);
}

long_long nova_Math_round(ExceptionData* exceptionData, double nova_number_98)
{
	return nova_Math_floor(exceptionData, nova_number_98 + 0.5);
}

long_long nova_Math_floor(ExceptionData* exceptionData, double nova_number_106)
{
	return floor(nova_number_106);
}

long_long nova_Math_ceil(ExceptionData* exceptionData, double nova_number_109)
{
	return ceil(nova_number_109);
}
