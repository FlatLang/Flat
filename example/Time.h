#ifndef FILE_Time_FATHOM
#define FILE_Time_FATHOM

typedef struct Time Time;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <Fathom.h>

CCLASS_CLASS
(
Time
)

Time* fathom_Time_Time(ExceptionData* exceptionData);
void fathom_del_Time(Time** this, ExceptionData* exceptionData);
long_long fathom_Time_currentTimeMillis(ExceptionData* exceptionData);
extern Time* __static__Time;

#endif