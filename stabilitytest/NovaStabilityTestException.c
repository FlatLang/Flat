#include <precompiled.h>
#include "NovaStabilityTestException.h"


nova_VTable_StabilityTestException nova_VTable_StabilityTestException_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

StabilityTestException* nova_0_StabilityTestException_construct(StabilityTestException* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(StabilityTestException, this,);
	this->vtable = &nova_VTable_StabilityTestException_val;
	nova_Object_super((Object*)this, 0);
	nova_Exception_super((Exception*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Exception_this((Exception*)(this), exceptionData);
	nova_StabilityTestException_super(this, 0);
	
	{
		nova_StabilityTestException_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_StabilityTestException(StabilityTestException** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_StabilityTestException_this(StabilityTestException* this, ExceptionData* exceptionData)
{
}

void nova_StabilityTestException_super(StabilityTestException* this, ExceptionData* exceptionData)
{
}
