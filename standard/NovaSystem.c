#include <precompiled.h>
#include "NovaSystem.h"




System* nova_System_System(ExceptionData* exceptionData)
{
	System* this = (System*)1;
	
	{
	}
	
	return this;
}

void nova_del_System(System** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_System_exit(System* this, ExceptionData* exceptionData, int nova_0_code)
{
	exit(nova_0_code);
}
