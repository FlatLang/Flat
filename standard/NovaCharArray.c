#include <precompiled.h>
#include "NovaCharArray.h"




CharArray* nova_CharArray_CharArray(ExceptionData* exceptionData)
{
	CCLASS_NEW(CharArray, this,);
	
	{
	}
	
	return this;
}

void nova_del_CharArray(CharArray** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}
