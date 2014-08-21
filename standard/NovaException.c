#include <precompiled.h>
#include "NovaException.h"


nova_VTable_Exception nova_VTable_Exception_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

Exception* nova_0_Exception_construct(Exception* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Exception, this,);
	this->vtable = &nova_VTable_Exception_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Exception_super(this, 0);
	
	{
		nova_Exception_this(this, exceptionData);
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

void nova_Exception_this(Exception* this, ExceptionData* exceptionData)
{
}

void nova_Exception_super(Exception* this, ExceptionData* exceptionData)
{
}
