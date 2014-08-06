#include <precompiled.h>
#include "NovaMath.h"


nova_VTable_Math nova_VTable_Math_val =
{
	nova_4_Object_toString,
	nova_2_Object_equals,
};

Math* nova_Math_construct(Math* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Math, this,);
	
	this->vtable = &nova_VTable_Math_val;
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

int nova_static_Math_random(Math* this, ExceptionData* exceptionData, long_long nova_0_range)
{
	return rand() % nova_0_range;
}

double nova_static_Math_abs(Math* this, ExceptionData* exceptionData, double nova_0_number)
{
	return abs(nova_0_number);
}

double nova_static_Math_sqrt(Math* this, ExceptionData* exceptionData, double nova_0_number)
{
	return sqrt(nova_0_number);
}

double nova_static_Math_pow(Math* this, ExceptionData* exceptionData, double nova_0_base, double nova_0_power)
{
	return pow(nova_0_base, nova_0_power);
}

double nova_static_Math_sin(Math* this, ExceptionData* exceptionData, double nova_0_number)
{
	return sin(nova_0_number);
}

double nova_static_Math_cos(Math* this, ExceptionData* exceptionData, double nova_0_number)
{
	return cos(nova_0_number);
}

double nova_static_Math_tan(Math* this, ExceptionData* exceptionData, double nova_0_number)
{
	return tan(nova_0_number);
}

double nova_static_Math_asin(Math* this, ExceptionData* exceptionData, double nova_0_number)
{
	return asin(nova_0_number);
}

double nova_static_Math_acos(Math* this, ExceptionData* exceptionData, double nova_0_number)
{
	return acos(nova_0_number);
}

double nova_static_Math_atan(Math* this, ExceptionData* exceptionData, double nova_0_number)
{
	return atan(nova_0_number);
}

long_long nova_static_Math_round(Math* this, ExceptionData* exceptionData, double nova_0_number)
{
	return nova_static_Math_floor((Math*)0, exceptionData, nova_0_number + 0.5);
}

long_long nova_static_Math_floor(Math* this, ExceptionData* exceptionData, double nova_0_number)
{
	return floor(nova_0_number);
}

long_long nova_static_Math_ceil(Math* this, ExceptionData* exceptionData, double nova_0_number)
{
	return ceil(nova_0_number);
}
