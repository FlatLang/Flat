#include <precompiled.h>
#include "NovaTimeStability.h"


nova_VTable_TimeStability nova_VTable_TimeStability_val =
{
	nova_2_Object_toString,
	nova_2_Object_equals,
};

TimeStability* nova_TimeStability_TimeStability(TimeStability* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(TimeStability, this,);
	
	this->vtable = &nova_VTable_TimeStability_val;
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
	
	nova_static_1_Console_write(0, exceptionData, nova_String_String(0, exceptionData, (char*)("Checking Time.nova... ")));
	nova_1_start = nova_static_Time_currentTimeMillis(0, exceptionData);
	nova_static_Thread_sleep(0, exceptionData, (long_long)(100));
	nova_1_time = (long_long)(nova_static_Time_currentTimeMillis(0, exceptionData) - nova_1_start);
	if (nova_1_time >= 100 && nova_1_time < 130)
	{
		nova_static_1_Console_writeLine(0, exceptionData, nova_String_String(0, exceptionData, (char*)("OK")));
	}
	else
	{
		nova_2_StabilityTest_fail(nova_0_program, exceptionData, nova_String_concat(nova_String_String(0, exceptionData, (char*)("Failed; expected 100ms, found ")), exceptionData, nova_String_concat(nova_2_Long_toString(nova_Long_Long(0, exceptionData, nova_1_time), exceptionData), exceptionData, nova_String_String(0, exceptionData, (char*)("ms")))));
	}
}
