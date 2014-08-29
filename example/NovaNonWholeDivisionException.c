#include <precompiled.h>
#include "NovaNonWholeDivisionException.h"




NonWholeDivisionException* nova_NonWholeDivisionException_construct(NonWholeDivisionException* this, ExceptionData* exceptionData)
{
	this = (NonWholeDivisionException*)1;
	nova_Object_super((Object*)this, 0);
	nova_Exception_super((Exception*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Exception_this((Exception*)(this), exceptionData);
	nova_NonWholeDivisionException_super(this, 0);
	
	{
		nova_NonWholeDivisionException_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_NonWholeDivisionException(NonWholeDivisionException** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_NonWholeDivisionException_this(NonWholeDivisionException* this, ExceptionData* exceptionData)
{
}

void nova_NonWholeDivisionException_super(NonWholeDivisionException* this, ExceptionData* exceptionData)
{
}
