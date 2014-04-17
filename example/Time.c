#include "Time.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include <stdio.h>
#include <Fathom.h>

Time* __static__Time;

Time* new_Time(ExceptionData* __FATHOM__exception_data);
void del_Time(Time** __o__, ExceptionData* __FATHOM__exception_data);
static long_long __FATHOM__currentTimeMillis(Time* __o__, ExceptionData* __FATHOM__exception_data);

NO_PRIVATE

Time* new_Time(ExceptionData* __FATHOM__exception_data)
{
	NEW(Time, __o__);
	
	__o__->currentTimeMillis = __FATHOM__currentTimeMillis;
	
	{
	}
	
	return __o__;
}

void del_Time(Time** __o__, ExceptionData* __FATHOM__exception_data)
{
	if (!*__o__)
	{
		return;
	}
	
	
	{
	}
	free(*__o__);
}

static long_long __FATHOM__currentTimeMillis(Time* __o__, ExceptionData* __FATHOM__exception_data)
{
	return currentTimeMillis();
}
