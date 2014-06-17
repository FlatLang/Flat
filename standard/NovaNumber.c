#include <precompiled.h>

#include "NovaNumber.h"

Number* nova_Number_Number(ExceptionData* exceptionData)
{
	Number* this = NULL;
	
	{
	}
	
	return this;
}

void nova_del_Number(Number** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_Number_numDigits(ExceptionData* exceptionData, int nova_0_number)
{
}

String* nova_Number_toAString(ExceptionData* exceptionData, int nova_0_value)
{
}
