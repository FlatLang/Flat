#include <precompiled.h>
#include <nova/standard/math/calculus/nova_standard_math_calculus_Nova_Calculus.h>

nova_standard_math_calculus_Extension_VTable_Calculus nova_standard_math_calculus_Extension_VTable_Calculus_val =
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
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_math_calculus_Nova_CalculusNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_math_calculus_Nova_Calculus* nova_standard_math_calculus_Nova_Calculus_Nova_Calculus(nova_standard_math_calculus_Nova_Calculus* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_math_calculus_Nova_Calculus, this,);
	this->vtable = &nova_standard_math_calculus_Extension_VTable_Calculus_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_calculus_Nova_Calculus_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_calculus_Nova_Calculus_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_math_calculus_Nova_Calculus_Nova_destroy(nova_standard_math_calculus_Nova_Calculus** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_standard_math_Nova_NumericStatement* nova_standard_math_calculus_Nova_Calculus_Nova_derivative(nova_standard_math_calculus_Nova_Calculus* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_math_Nova_NumericStatement* nova_standard_math_calculus_Nova_Calculus_Nova_statement)
{
	return (nova_standard_math_Nova_NumericStatement*)nova_null;
}

void nova_standard_math_calculus_Nova_Calculus_0_Nova_this(nova_standard_math_calculus_Nova_Calculus* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_math_calculus_Nova_Calculus_Nova_super(nova_standard_math_calculus_Nova_Calculus* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

