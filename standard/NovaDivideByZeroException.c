#include <precompiled.h>
#include "NovaDivideByZeroException.h"




DivideByZeroException* nova_DivideByZeroException_construct(DivideByZeroException* this, ExceptionData* exceptionData)
{
	this = (DivideByZeroException*)1;
	
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
