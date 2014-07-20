#include <precompiled.h>
#include "NovaUnstableException.h"




UnstableException* nova_UnstableException_UnstableException(ExceptionData* exceptionData)
{
	UnstableException* this = (UnstableException*)1;
	
	{
	}
	
	return this;
}

void nova_del_UnstableException(UnstableException** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}
