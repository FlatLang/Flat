#include <precompiled.h>
#include "NovaBool.h"




Bool* nova_Bool_Bool(ExceptionData* exceptionData)
{
	Bool* this = (Bool*)1;
	
	{
	}
	
	return this;
}

void nova_del_Bool(Bool** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}
