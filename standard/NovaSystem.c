#include <precompiled.h>
#include "NovaSystem.h"


nova_VTable_System nova_VTable_System_val =
{
	nova_Object_toString,
	nova_Object_equals,
};

System* nova_System_System(ExceptionData* exceptionData)
{
	CCLASS_NEW(System, this,);
	
	this->vtable = &nova_VTable_System_val;
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
