#include <precompiled.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>



nova_standard_math_Extension_VTable_Math nova_standard_math_Extension_VTable_Math_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


double nova_standard_math_Nova_Math_Nova_PI;
void nova_standard_math_Nova_Math_Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
		nova_standard_math_Nova_Math_Nova_PI = (double)(3.141592653);
	}
}

nova_standard_math_Nova_Math* nova_standard_math_Nova_Math_Nova_Math(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_math_Nova_Math, this,);
	this->vtable = &nova_standard_math_Extension_VTable_Math_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_Nova_Math_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_Nova_Math_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_math_Nova_Math_Nova_destroy(nova_standard_math_Nova_Math** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

long_long nova_standard_math_Nova_Math_Nova_max(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, long_long nova_standard_math_Nova_Math_Nova_a, long_long nova_standard_math_Nova_Math_Nova_b)
{
	return nova_standard_math_Nova_Math_Nova_a > nova_standard_math_Nova_Math_Nova_b ? nova_standard_math_Nova_Math_Nova_a : nova_standard_math_Nova_Math_Nova_b;
}

long_long nova_standard_math_Nova_Math_Nova_min(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, long_long nova_standard_math_Nova_Math_Nova_a, long_long nova_standard_math_Nova_Math_Nova_b)
{
	return nova_standard_math_Nova_Math_Nova_a < nova_standard_math_Nova_Math_Nova_b ? nova_standard_math_Nova_Math_Nova_a : nova_standard_math_Nova_Math_Nova_b;
}

char nova_standard_math_Nova_Math_Nova_sign(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, long_long nova_standard_math_Nova_Math_Nova_num)
{
	if (nova_standard_math_Nova_Math_Nova_num > 0)
	{
		return 1;
	}
	else if (nova_standard_math_Nova_Math_Nova_num < 0)
	{
		return -1;
	}
	return 0;
}

int nova_standard_math_Nova_Math_Nova_random(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, long_long nova_standard_math_Nova_Math_Nova_range)
{
	return (int)rand() % nova_standard_math_Nova_Math_Nova_range;
}

long_long nova_standard_math_Nova_Math_0_Nova_abs(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, long_long nova_standard_math_Nova_Math_Nova_number)
{
	return (long_long)llabs(nova_standard_math_Nova_Math_Nova_number);
}

double nova_standard_math_Nova_Math_1_Nova_abs(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_math_Nova_Math_Nova_number)
{
	return (double)fabs(nova_standard_math_Nova_Math_Nova_number);
}

double nova_standard_math_Nova_Math_Nova_sqrt(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_math_Nova_Math_Nova_number)
{
	return (double)sqrt(nova_standard_math_Nova_Math_Nova_number);
}

double nova_standard_math_Nova_Math_Nova_pow(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_math_Nova_Math_Nova_base, double nova_standard_math_Nova_Math_Nova_power)
{
	return (double)pow(nova_standard_math_Nova_Math_Nova_base, nova_standard_math_Nova_Math_Nova_power);
}

double nova_standard_math_Nova_Math_Nova_sin(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_math_Nova_Math_Nova_number)
{
	return (double)sin(nova_standard_math_Nova_Math_Nova_number);
}

double nova_standard_math_Nova_Math_Nova_cos(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_math_Nova_Math_Nova_number)
{
	return (double)cos(nova_standard_math_Nova_Math_Nova_number);
}

double nova_standard_math_Nova_Math_Nova_tan(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_math_Nova_Math_Nova_number)
{
	return (double)tan(nova_standard_math_Nova_Math_Nova_number);
}

double nova_standard_math_Nova_Math_Nova_asin(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_math_Nova_Math_Nova_number)
{
	return (double)asin(nova_standard_math_Nova_Math_Nova_number);
}

double nova_standard_math_Nova_Math_Nova_acos(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_math_Nova_Math_Nova_number)
{
	return (double)acos(nova_standard_math_Nova_Math_Nova_number);
}

double nova_standard_math_Nova_Math_Nova_atan(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_math_Nova_Math_Nova_number)
{
	return (double)atan(nova_standard_math_Nova_Math_Nova_number);
}

long_long nova_standard_math_Nova_Math_Nova_round(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_math_Nova_Math_Nova_number)
{
	return nova_standard_math_Nova_Math_Nova_floor(0, exceptionData, nova_standard_math_Nova_Math_Nova_number + 0.5);
}

long_long nova_standard_math_Nova_Math_Nova_floor(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_math_Nova_Math_Nova_number)
{
	return (long_long)floor(nova_standard_math_Nova_Math_Nova_number);
}

long_long nova_standard_math_Nova_Math_Nova_ceil(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_math_Nova_Math_Nova_number)
{
	return (long_long)ceil(nova_standard_math_Nova_Math_Nova_number);
}

void nova_standard_math_Nova_Math_0_Nova_this(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_math_Nova_Math_Nova_super(nova_standard_math_Nova_Math* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

