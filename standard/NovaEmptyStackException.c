#include <precompiled.h>
#include "NovaEmptyStackException.h"




EmptyStackException* nova_0_EmptyStackException_construct(EmptyStackException* this, ExceptionData* exceptionData)
{
	this = (EmptyStackException*)1;
	nova_Object_super((Object*)this, 0);
	nova_Exception_super((Exception*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Exception_this((Exception*)(this), exceptionData);
	nova_EmptyStackException_super(this, 0);
	
	{
		nova_EmptyStackException_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_EmptyStackException(EmptyStackException** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_EmptyStackException_this(EmptyStackException* this, ExceptionData* exceptionData)
{
}

void nova_EmptyStackException_super(EmptyStackException* this, ExceptionData* exceptionData)
{
}
