#include <precompiled.h>
#include "NovaExceptionHandlingDemo.h"




int nova_static_ExceptionHandlingDemo_divide(ExceptionHandlingDemo* this, ExceptionData* exceptionData, int nova_0_num, int nova_0_den);

ExceptionHandlingDemo* nova_ExceptionHandlingDemo_ExceptionHandlingDemo(ExceptionData* exceptionData)
{
	ExceptionHandlingDemo* this = (ExceptionHandlingDemo*)1;
	
	{
	}
	
	return this;
}

void nova_del_ExceptionHandlingDemo(ExceptionHandlingDemo** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_ExceptionHandlingDemo_main(ExceptionHandlingDemo* this, ExceptionData* exceptionData, String** nova_0_args)
{
	TRY
	{
		nova_ExceptionData_addCode(exceptionData, exceptionData, 5);
		
		{
			int nova_2_result;
			
			nova_2_result = nova_static_ExceptionHandlingDemo_divide((ExceptionHandlingDemo*)0, exceptionData, 100, 5);
			nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_concat(nova_String_String(exceptionData, (char*)("After working example: ")), exceptionData, nova_Integer_toString((Integer*)(nova_Integer_Integer(exceptionData, nova_2_result)), exceptionData)));
			nova_2_result = nova_static_ExceptionHandlingDemo_divide((ExceptionHandlingDemo*)0, exceptionData, 100, 3);
			nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "this output will not show."));
		}
	}
	CATCH (5)
	{
		nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "You used the divide() method incorrectly."));
	}
	FINALLY
	{
		nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "Exiting the try block."));
	}
	END_TRY;
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "After the try block."));
	nova_static_Console_waitForEnter((Console*)(0), exceptionData);
}

int nova_static_ExceptionHandlingDemo_divide(ExceptionHandlingDemo* this, ExceptionData* exceptionData, int nova_0_num, int nova_0_den)
{
	int nova_zero_check4;
	
	if (nova_0_num % nova_0_den != 0)
	{
		THROW(5);
	}
	nova_zero_check4 = nova_0_den;
	if (nova_zero_check4 == 0)
	{
		THROW(3);
	}
	return nova_0_num / nova_zero_check4;
}
