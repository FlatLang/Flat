#ifndef FILE_Time_FATHOM
#define FILE_Time_FATHOM

typedef struct Time Time;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include <Fathom.h>

CCLASS_CLASS
(
	Time
)


Time* fathom_Time_Time(ExceptionData* exceptionData);
void fathom_del_Time(Time** this, ExceptionData* exceptionData);
long_long fathom_Time_currentTimeMillis(ExceptionData* exceptionData);
#endif