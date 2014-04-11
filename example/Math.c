#include "Math.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include <math.h>

Math* __static__Math;

Math* new_Math(ExceptionData* __FATHOM__exception_data);
void del_Math(Math* __o__, ExceptionData* __FATHOM__exception_data);
static double __FATHOM__sqrt(Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
static double __FATHOM__pow(Math* __o__, ExceptionData* __FATHOM__exception_data, double base, double power);
static double __FATHOM__sin(Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
static double __FATHOM__cos(Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
static double __FATHOM__tan(Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
static double __FATHOM__asin(Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
static double __FATHOM__acos(Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
static double __FATHOM__atan(Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
static int __FATHOM__floor(Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
static int __FATHOM__ceil(Math* __o__, ExceptionData* __FATHOM__exception_data, double number);

NO_PRIVATE

Math* new_Math(ExceptionData* __FATHOM__exception_data)
{
	NEW(Math, __o__);
	
	__o__->sqrt = __FATHOM__sqrt;
	__o__->pow = __FATHOM__pow;
	__o__->sin = __FATHOM__sin;
	__o__->cos = __FATHOM__cos;
	__o__->tan = __FATHOM__tan;
	__o__->asin = __FATHOM__asin;
	__o__->acos = __FATHOM__acos;
	__o__->atan = __FATHOM__atan;
	__o__->floor = __FATHOM__floor;
	__o__->ceil = __FATHOM__ceil;
	
	{
	}
	
	return __o__;
}

void del_Math(Math* __o__, ExceptionData* __FATHOM__exception_data)
{
	if (!__o__)
	{
		return;
	}
	
	
	{
	}
	free(__o__);
}

static double __FATHOM__sqrt(Math* __o__, ExceptionData* __FATHOM__exception_data, double number)
{
	return sqrt(number);
}

static double __FATHOM__pow(Math* __o__, ExceptionData* __FATHOM__exception_data, double base, double power)
{
	return pow(base, power);
}

static double __FATHOM__sin(Math* __o__, ExceptionData* __FATHOM__exception_data, double number)
{
	return sin(number);
}

static double __FATHOM__cos(Math* __o__, ExceptionData* __FATHOM__exception_data, double number)
{
	return cos(number);
}

static double __FATHOM__tan(Math* __o__, ExceptionData* __FATHOM__exception_data, double number)
{
	return tan(number);
}

static double __FATHOM__asin(Math* __o__, ExceptionData* __FATHOM__exception_data, double number)
{
	return asin(number);
}

static double __FATHOM__acos(Math* __o__, ExceptionData* __FATHOM__exception_data, double number)
{
	return acos(number);
}

static double __FATHOM__atan(Math* __o__, ExceptionData* __FATHOM__exception_data, double number)
{
	return atan(number);
}

static int __FATHOM__floor(Math* __o__, ExceptionData* __FATHOM__exception_data, double number)
{
	return floor(number);
}

static int __FATHOM__ceil(Math* __o__, ExceptionData* __FATHOM__exception_data, double number)
{
	return ceil(number);
}
