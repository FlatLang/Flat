#include <precompiled.h>
#include "NovaUnstableException.h"




UnstableException* nova_UnstableException_construct(UnstableException* this, ExceptionData* exceptionData)
{
	this = (UnstableException*)1;
	
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
