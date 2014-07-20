#include <precompiled.h>
#include "NovaStabilityTestException.h"




StabilityTestException* nova_StabilityTestException_StabilityTestException(ExceptionData* exceptionData)
{
	StabilityTestException* this = (StabilityTestException*)1;
	
	{
	}
	
	return this;
}

void nova_del_StabilityTestException(StabilityTestException** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}
