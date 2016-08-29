#include <precompiled.h>
#include <nova/math/nova_math_Nova_InvalidNumericStatementException.h>



nova_math_Extension_VTable_InvalidNumericStatementException nova_math_Extension_VTable_InvalidNumericStatementException_val =
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


void nova_math_Nova_InvalidNumericStatementException_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_Nova_InvalidNumericStatementException* nova_math_Nova_InvalidNumericStatementException_Nova_construct(nova_math_Nova_InvalidNumericStatementException* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_math_Nova_InvalidNumericStatementException_Nova_message)
{
	CCLASS_NEW(nova_math_Nova_InvalidNumericStatementException, this,);
	this->vtable = &nova_math_Extension_VTable_InvalidNumericStatementException_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_exception_Nova_Exception_Nova_super((nova_exception_Nova_Exception*)this, exceptionData);
	nova_math_Nova_InvalidNumericStatementException_0_Nova_super(this, exceptionData);
	
	{
		nova_math_Nova_InvalidNumericStatementException_4_Nova_this(this, exceptionData, nova_math_Nova_InvalidNumericStatementException_Nova_message);
	}
	
	return this;
}

void nova_math_Nova_InvalidNumericStatementException_Nova_destroy(nova_math_Nova_InvalidNumericStatementException** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_math_Nova_InvalidNumericStatementException_4_Nova_this(nova_math_Nova_InvalidNumericStatementException* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_math_Nova_InvalidNumericStatementException_Nova_message)
{
	nova_exception_Nova_Exception_4_Nova_this((nova_exception_Nova_Exception*)(this), exceptionData, nova_math_Nova_InvalidNumericStatementException_Nova_message);
}

void nova_math_Nova_InvalidNumericStatementException_0_Nova_super(nova_math_Nova_InvalidNumericStatementException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

