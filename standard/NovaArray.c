#include <precompiled.h>
#include "NovaArray.h"




Array* nova_Array_Array(ExceptionData* exceptionData)
{
	CCLASS_NEW(Array, this,);
	
	this->nova_Array_length = 0;
	{
	}
	
	return this;
}

void nova_del_Array(Array** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}
