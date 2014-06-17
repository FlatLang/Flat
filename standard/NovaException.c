#include <precompiled.h>

#include "NovaException.h"

Exception* nova_Exception_Exception(ExceptionData* exceptionData)
{
	Exception* this = NULL;
	
	{
	}
	
	return this;
}

void nova_del_Exception(Exception** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}
