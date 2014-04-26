#include "Time.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include <windows.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <Fathom.h>

Time* __static__Time;

Time* fathom_Time_Time(ExceptionData* exceptionData)
{
CCLASS_NEW(Time, this,);

{
}

return this;
}

void fathom_del_Time(Time** this, ExceptionData* exceptionData)
{
if (!*this)
{
return;
}


{
}
free(*this);
}

long_long fathom_Time_currentTimeMillis(ExceptionData* exceptionData)
{
return currentTimeMillis();
}
