#include <precompiled.h>
#include <nova/standard/math/nova_standard_math_Nova_Sequence.h>

nova_standard_math_Extension_VTable_Sequence nova_standard_math_Extension_VTable_Sequence_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


int nova_standard_math_Nova_Sequence_Nova_INFINITE;
void nova_standard_math_Nova_SequenceNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
		nova_standard_math_Nova_Sequence_Nova_INFINITE = (int)(-1);
	}
}

nova_standard_math_Nova_Sequence* nova_standard_math_Nova_Sequence_Nova_Sequence(nova_standard_math_Nova_Sequence* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double* nova_standard_math_Nova_Sequence_Nova_values)
{
	CCLASS_NEW(nova_standard_math_Nova_Sequence, this,);
	this->vtable = &nova_standard_math_Extension_VTable_Sequence_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_Nova_Sequence_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_Nova_Sequence_1_Nova_this(this, exceptionData, nova_standard_math_Nova_Sequence_Nova_values);
	}
	
	return this;
}

void nova_standard_math_Nova_Sequence_Nova_destroy(nova_standard_math_Nova_Sequence** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_math_Nova_Sequence_1_Nova_this(nova_standard_math_Nova_Sequence* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double* nova_standard_math_Nova_Sequence_Nova_values)
{
	this->nova_standard_math_Nova_Sequence_Nova_values = nova_standard_math_Nova_Sequence_Nova_values;
}

double nova_standard_math_Nova_Sequence_Nova_sum(nova_standard_math_Nova_Sequence* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_math_Nova_Sequence_Nova_num)
{
	double l1_Nova_value;
	int l2_Nova_i;
	
	l1_Nova_value = (double)(0);
	l2_Nova_i = (int)(0);
	for (; l2_Nova_i < nova_standard_math_Nova_Sequence_Nova_num; l2_Nova_i++)
	{
		l1_Nova_value = l1_Nova_value + this->nova_standard_math_Nova_Sequence_Nova_values[l2_Nova_i];
	}
	return l1_Nova_value;
}

void nova_standard_math_Nova_Sequence_Nova_super(nova_standard_math_Nova_Sequence* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_math_Nova_Sequence_Nova_values = 0;
}

