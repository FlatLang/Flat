#include <precompiled.h>

#include "NovaTime.h"

Time* nova_Time_Time(ExceptionData* exceptionData)
{
	Time* this = NULL;
	
	{
	}
	
	return this;
}

void nova_del_Time(Time** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

long_long nova_Time_currentTimeMillis(ExceptionData* exceptionData)
{
	return currentTimeMillis();
}
