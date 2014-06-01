#ifndef FILE_Time_NOVA
#define FILE_Time_NOVA

typedef struct Time Time;

#include <gc.h>
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

CCLASS_CLASS
(
	Time
)


Time* nova_Time_Time(ExceptionData* exceptionData);
void nova_del_Time(Time** this, ExceptionData* exceptionData);
long nova_Time_currentTimeMillis(ExceptionData* exceptionData);
#endif