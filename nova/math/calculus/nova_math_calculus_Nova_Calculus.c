#include <precompiled.h>
#include <nova/math/calculus/nova_math_calculus_Nova_Calculus.h>



nova_math_calculus_Extension_VTable_Calculus nova_math_calculus_Extension_VTable_Calculus_val =
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


void nova_math_calculus_Nova_Calculus_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_calculus_Nova_Calculus* nova_math_calculus_Nova_Calculus_Nova_construct(nova_math_calculus_Nova_Calculus* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_math_calculus_Nova_Calculus, this,);
	this->vtable = &nova_math_calculus_Extension_VTable_Calculus_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_math_calculus_Nova_Calculus_Nova_super(this, exceptionData);
	
	{
		nova_math_calculus_Nova_Calculus_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_math_calculus_Nova_Calculus_Nova_destroy(nova_math_calculus_Nova_Calculus** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_math_Nova_NumericStatement* nova_math_calculus_Nova_Calculus_Nova_derivative(nova_math_calculus_Nova_Calculus* this, nova_exception_Nova_ExceptionData* exceptionData, nova_math_Nova_NumericStatement* nova_math_calculus_Nova_Calculus_Nova_statement)
{
	return (nova_math_Nova_NumericStatement*)nova_null;
}

void nova_math_calculus_Nova_Calculus_0_Nova_this(nova_math_calculus_Nova_Calculus* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_math_calculus_Nova_Calculus_Nova_super(nova_math_calculus_Nova_Calculus* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

