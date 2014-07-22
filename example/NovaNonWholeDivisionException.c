#include <precompiled.h>
#include "NovaNonWholeDivisionException.h"




NonWholeDivisionException* nova_NonWholeDivisionException_NonWholeDivisionException(ExceptionData* exceptionData)
{
	NonWholeDivisionException* this = (NonWholeDivisionException*)1;
	
	{
	}
	
	return this;
}

void nova_del_NonWholeDivisionException(NonWholeDivisionException** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}
