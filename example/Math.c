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

double nova_Math_abs(ExceptionData* exceptionData, double nova_Math_number_52)
{
	return abs(nova_Math_number_52);
}

double nova_Math_sqrt(ExceptionData* exceptionData, double nova_Math_number_58)
{
	return sqrt(nova_Math_number_58);
}

double nova_Math_pow(ExceptionData* exceptionData, double nova_Math_base_64, double nova_Math_power_64)
{
	return pow(nova_Math_base_64, nova_Math_power_64);
}

double nova_Math_sin(ExceptionData* exceptionData, double nova_Math_number_72)
{
	return sin(nova_Math_number_72);
}

double nova_Math_cos(ExceptionData* exceptionData, double nova_Math_number_77)
{
	return cos(nova_Math_number_77);
}

double nova_Math_tan(ExceptionData* exceptionData, double nova_Math_number_86)
{
	return tan(nova_Math_number_86);
}

double nova_Math_asin(ExceptionData* exceptionData, double nova_Math_number_97)
{
	return asin(nova_Math_number_97);
}

double nova_Math_acos(ExceptionData* exceptionData, double nova_Math_number_132)
{
	return acos(nova_Math_number_132);
}

double nova_Math_atan(ExceptionData* exceptionData, double nova_Math_number_135)
{
	return atan(nova_Math_number_135);
}

long nova_Math_round(ExceptionData* exceptionData, double nova_Math_number_138)
{
	return nova_Math_floor(exceptionData, nova_Math_number_138 + 0.5);
}

long nova_Math_floor(ExceptionData* exceptionData, double nova_Math_number_159)
{
	return floor(nova_Math_number_159);
}

long nova_Math_ceil(ExceptionData* exceptionData, double nova_Math_number_173)
{
	return ceil(nova_Math_number_173);
}
