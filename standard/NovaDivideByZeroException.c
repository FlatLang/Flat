#include <precompiled.h>

#include "NovaDivideByZeroException.h"

DivideByZeroException* nova_DivideByZeroException_DivideByZeroException(ExceptionData* exceptionData)
{
	DivideByZeroException* this = NULL;
	
	{
	}
	
	return this;
}

void nova_del_DivideByZeroException(DivideByZeroException** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}
