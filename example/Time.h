#ifndef FILE_Time_FATHOM
#define FILE_Time_FATHOM

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include <Fathom.h>

typedef struct ExceptionData ExceptionData;

CLASS
(
Time, 

FUNC(long_long, currentTimeMillis, Time* __o__, ExceptionData* __FATHOM__exception_data);
)

Time* new_Time(ExceptionData* __FATHOM__exception_data);
void del_Time(Time* __o__, ExceptionData* __FATHOM__exception_data);
extern Time* __static__Time;

#endif