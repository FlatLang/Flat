#include <precompiled.h>
#include "NovaMathDemo.h"




MathDemo* nova_MathDemo_MathDemo(ExceptionData* exceptionData)
{
	MathDemo* this = (MathDemo*)1;
	
	{
	}
	
	return this;
}

void nova_del_MathDemo(MathDemo** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_MathDemo_main(MathDemo* this, ExceptionData* exceptionData, String** nova_0_args)
{
	int nova_1_iterations;
	long_long nova_1_start;
	int nova_1_i;
	long_long nova_1_time;
	
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "Beginning benchmark..."));
	nova_1_iterations = 999999;
	nova_1_start = nova_static_Time_currentTimeMillis((Time*)(0), exceptionData);
	nova_1_i = 0;
	for (; nova_1_i < nova_1_iterations; nova_1_i++)
	{
		TRY
		{
			nova_ExceptionData_addCode(exceptionData, exceptionData, 1);
			
			{
				nova_static_Math_sin((Math*)(0), exceptionData, (double)(nova_1_i));
			}
		}
		CATCH (1)
		{
		}
		FINALLY
		{
		}
		END_TRY;
	}
	nova_1_time = nova_static_Time_currentTimeMillis((Time*)(0), exceptionData) - nova_1_start;
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_concat(nova_Integer_toString((Integer*)(nova_Integer_Integer(exceptionData, nova_1_iterations)), exceptionData), exceptionData, nova_String_concat(nova_String_String(exceptionData, (char*)(" iterations of Math.sin() in ")), exceptionData, nova_String_concat(nova_Long_toString((Long*)(nova_Long_Long(exceptionData, nova_1_time)), exceptionData), exceptionData, nova_String_String(exceptionData, (char*)("ms"))))));
	nova_static_Console_waitForEnter((Console*)(0), exceptionData);
}
