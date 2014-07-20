#include <precompiled.h>
#include "NovaTimeStability.h"




TimeStability* nova_TimeStability_TimeStability(ExceptionData* exceptionData)
{
	TimeStability* this = (TimeStability*)1;
	
	{
	}
	
	return this;
}

void nova_del_TimeStability(TimeStability** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_TimeStability_test(TimeStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	long_long nova_1_start;
	long_long nova_1_time;
	
	nova_static_Console_write((Object*)0, exceptionData, nova_String_String(exceptionData, "Checking Time.nova... "));
	nova_1_start = nova_static_Time_currentTimeMillis((Object*)0, exceptionData);
	nova_static_Thread_sleep((Object*)0, exceptionData, 100);
	nova_1_time = nova_static_Time_currentTimeMillis((Object*)0, exceptionData) - nova_1_start;
	if (nova_1_time >= 100 && nova_1_time < 130)
	{
		nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_String(exceptionData, "OK"));
	}
	else
	{
		nova_StabilityTest_fail(nova_0_program, exceptionData, nova_String_concat(nova_String_String(exceptionData, "Failed; expected 100ms, found "), exceptionData, nova_String_concat(nova_Long_toString(nova_Long_Long(exceptionData, nova_1_time), exceptionData), exceptionData, nova_String_String(exceptionData, "ms"))));
	}
}
