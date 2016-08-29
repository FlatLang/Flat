#include <precompiled.h>
#include <nova/math/nova_math_Nova_ArithmeticSequence.h>



nova_math_Extension_VTable_ArithmeticSequence nova_math_Extension_VTable_ArithmeticSequence_val =
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


void nova_math_Nova_ArithmeticSequence_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_Nova_ArithmeticSequence* nova_math_Nova_ArithmeticSequence_Nova_construct(nova_math_Nova_ArithmeticSequence* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_math_Nova_ArithmeticSequence, this,);
	this->vtable = &nova_math_Extension_VTable_ArithmeticSequence_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_math_Nova_Sequence_Nova_super((nova_math_Nova_Sequence*)this, exceptionData);
	nova_math_Nova_ArithmeticSequence_0_Nova_super(this, exceptionData);
	
	{
		nova_math_Nova_ArithmeticSequence_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_math_Nova_ArithmeticSequence_Nova_destroy(nova_math_Nova_ArithmeticSequence** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_math_Nova_ArithmeticSequence_0_Nova_this(nova_math_Nova_ArithmeticSequence* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_math_Nova_ArithmeticSequence_0_Nova_super(nova_math_Nova_ArithmeticSequence* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

