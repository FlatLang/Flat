#include <precompiled.h>
#include "NovaUnstableException.h"




UnstableException* nova_UnstableException_construct(UnstableException* this, ExceptionData* exceptionData)
{
	this = (UnstableException*)1;
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
