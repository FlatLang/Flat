#include "Math.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <math.h>

Math* __static__Math;

Math* fathom_Math_Math(ExceptionData* exceptionData)
{
CCLASS_NEW(Math, this,);

{
}

return this;
}

void fathom_del_Math(Math** this, ExceptionData* exceptionData)
{
if (!*this)
{
return;
}


{
}
free(*this);
}

double fathom_Math_sqrt(ExceptionData* exceptionData, double fathom_number_8)
{
return sqrt(fathom_number_8);
}

double fathom_Math_pow(ExceptionData* exceptionData, double fathom_base_32, double fathom_power_32)
{
return pow(fathom_base_32, fathom_power_32);
}

double fathom_Math_sin(ExceptionData* exceptionData, double fathom_number_40)
{
return sin(fathom_number_40);
}

double fathom_Math_cos(ExceptionData* exceptionData, double fathom_number_43)
{
return cos(fathom_number_43);
}

double fathom_Math_tan(ExceptionData* exceptionData, double fathom_number_46)
{
return tan(fathom_number_46);
}

double fathom_Math_asin(ExceptionData* exceptionData, double fathom_number_49)
{
return asin(fathom_number_49);
}

double fathom_Math_acos(ExceptionData* exceptionData, double fathom_number_55)
{
return acos(fathom_number_55);
}

double fathom_Math_atan(ExceptionData* exceptionData, double fathom_number_60)
{
return atan(fathom_number_60);
}

int fathom_Math_floor(ExceptionData* exceptionData, double fathom_number_69)
{
return floor(fathom_number_69);
}

int fathom_Math_ceil(ExceptionData* exceptionData, double fathom_number_107)
{
return ceil(fathom_number_107);
}
