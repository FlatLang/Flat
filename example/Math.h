#ifndef FILE_Math_FATHOM
#define FILE_Math_FATHOM

typedef struct Math Math;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "String.h"
#include <math.h>

CLASS
(
	Math, 
	
	FUNC(double, sqrt, Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
	FUNC(double, pow, Math* __o__, ExceptionData* __FATHOM__exception_data, double base, double power);
	FUNC(double, sin, Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
	FUNC(double, cos, Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
	FUNC(double, tan, Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
	FUNC(double, asin, Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
	FUNC(double, acos, Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
	FUNC(double, atan, Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
	FUNC(int, floor, Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
	FUNC(int, ceil, Math* __o__, ExceptionData* __FATHOM__exception_data, double number);
)

Math* new_Math(ExceptionData* __FATHOM__exception_data);
void del_Math(Math** __o__, ExceptionData* __FATHOM__exception_data);
extern Math* __static__Math;

#endif