#include <precompiled.h>
#include <nova/exception/nova_exception_Nova_DivideByZeroException.h>



nova_exception_Extension_VTable_DivideByZeroException nova_exception_Extension_VTable_DivideByZeroException_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void nova_exception_Nova_DivideByZeroException_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_exception_Nova_DivideByZeroException* nova_exception_Nova_DivideByZeroException_Nova_construct(nova_exception_Nova_DivideByZeroException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_exception_Nova_DivideByZeroException, this,);
	this->vtable = &nova_exception_Extension_VTable_DivideByZeroException_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_exception_Nova_Exception_Nova_super((nova_exception_Nova_Exception*)this, exceptionData);
	nova_exception_Nova_DivideByZeroException_0_Nova_super(this, exceptionData);
	
	{
		nova_exception_Nova_DivideByZeroException_3_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_exception_Nova_DivideByZeroException_Nova_destroy(nova_exception_Nova_DivideByZeroException** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_exception_Nova_DivideByZeroException_3_Nova_this(nova_exception_Nova_DivideByZeroException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_exception_Nova_DivideByZeroException_0_Nova_super(nova_exception_Nova_DivideByZeroException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

