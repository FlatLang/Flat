#include <precompiled.h>
#include "NovaDivideByZeroException.h"


nova_VTable_DivideByZeroException nova_VTable_DivideByZeroException_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

DivideByZeroException* nova_DivideByZeroException_construct(DivideByZeroException* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(DivideByZeroException, this,);
	this->vtable = &nova_VTable_DivideByZeroException_val;
	nova_Object_super((Object*)this, 0);
	nova_Exception_super((Exception*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Exception_this((Exception*)(this), exceptionData);
	nova_DivideByZeroException_super(this, 0);
	
	{
		nova_DivideByZeroException_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_DivideByZeroException(DivideByZeroException** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_DivideByZeroException_this(DivideByZeroException* this, ExceptionData* exceptionData)
{
}

void nova_DivideByZeroException_super(DivideByZeroException* this, ExceptionData* exceptionData)
{
}
