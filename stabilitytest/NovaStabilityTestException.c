#include <precompiled.h>
#include "NovaStabilityTestException.h"




StabilityTestException* nova_StabilityTestException_construct(StabilityTestException* this, ExceptionData* exceptionData)
{
	this = (StabilityTestException*)1;
	
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
