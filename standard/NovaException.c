#include <precompiled.h>
#include "NovaException.h"


nova_VTable_Exception nova_VTable_Exception_val =
{
	nova_2_Object_toString,
	nova_2_Object_equals,
};

Exception* nova_Exception_construct(Exception* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Exception, this,);
	
	this->vtable = &nova_VTable_Exception_val;
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
