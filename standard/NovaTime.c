#include <precompiled.h>
#include "NovaTime.h"




Time* nova_Time_Time(ExceptionData* exceptionData)
{
	Time* this = (Time*)1;
	
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

long_long nova_static_Time_currentTimeMillis(Time* this, ExceptionData* exceptionData)
{
	return currentTimeMillis();
}
