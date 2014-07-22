#include <precompiled.h>
#include "NovaIntegerTest.h"




IntegerTest* nova_IntegerTest_IntegerTest(ExceptionData* exceptionData)
{
	IntegerTest* this = (IntegerTest*)1;
	
	{
	}
	
	return this;
}

void nova_del_IntegerTest(IntegerTest** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_IntegerTest_main(IntegerTest* this, ExceptionData* exceptionData, String** nova_0_args)
{
	char nova_1_c;
	
	nova_1_c = 'y';
	while (nova_1_c == 'y' || nova_1_c == 'Y')
	{
		long_long nova_2_start;
		int nova_2_i;
		long_long nova_2_end;
		
		nova_2_start = nova_static_Time_currentTimeMillis((Time*)(0), exceptionData);
		nova_2_i = 0;
		for (; nova_2_i < 999999; nova_2_i++)
		{
			nova_static_Long_toAString((Long*)(0), exceptionData, (long_long)(nova_2_i));
		}
		nova_2_end = nova_static_Time_currentTimeMillis((Time*)(0), exceptionData);
		nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_concat(nova_String_String(exceptionData, (char*)("Time taken: ")), exceptionData, nova_String_concat(nova_Long_toString((Long*)(nova_Long_Long(exceptionData, (nova_2_end - nova_2_start))), exceptionData), exceptionData, nova_String_String(exceptionData, (char*)("ms")))));
		nova_static_Console_write((Console*)(0), exceptionData, nova_String_String(exceptionData, "Run again? (Y/N) "));
		nova_1_c = nova_static_Console_readChar((Console*)(0), exceptionData);
	}
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "\nFinished"));
	nova_static_Console_waitForEnter((Console*)(0), exceptionData);
}
