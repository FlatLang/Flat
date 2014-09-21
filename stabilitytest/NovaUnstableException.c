#include <precompiled.h>
#include "NovaUnstableException.h"


nova_VTable_UnstableException nova_VTable_UnstableException_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

UnstableException* nova_0_UnstableException_construct(UnstableException* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(UnstableException, this,);
	this->vtable = &nova_VTable_UnstableException_val;
	nova_Object_super((Object*)this, 0);
	nova_Exception_super((Exception*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Exception_this((Exception*)(this), exceptionData);
	nova_UnstableException_super(this, 0);
	
	{
		nova_UnstableException_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_UnstableException(UnstableException** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_UnstableException_this(UnstableException* this, ExceptionData* exceptionData)
{
}

void nova_UnstableException_super(UnstableException* this, ExceptionData* exceptionData)
{
}
