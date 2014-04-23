#ifndef FILE_Time_FATHOM
#define FILE_Time_FATHOM

typedef struct Time Time;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include <Fathom.h>

CLASS
(
	Time, 
	
	, struct Private* prv;
)

Time* fathom_Time_Time(ExceptionData* exceptionData);
void fathom_del_Time(Time** reference, ExceptionData* exceptionData);
long_long fathom_Time_currentTimeMillis(Time* reference, ExceptionData* exceptionData);
extern Time* __static__Time;

#endif