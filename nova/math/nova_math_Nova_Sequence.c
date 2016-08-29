#include <precompiled.h>
#include <nova/math/nova_math_Nova_Sequence.h>



nova_math_Extension_VTable_Sequence nova_math_Extension_VTable_Sequence_val =
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


int nova_math_Nova_Sequence_Nova_INFINITE;
void nova_math_Nova_Sequence_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
		nova_math_Nova_Sequence_Nova_INFINITE = (int)(-1);
	}
}

nova_math_Nova_Sequence* nova_math_Nova_Sequence_Nova_construct(nova_math_Nova_Sequence* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_DoubleArray* nova_math_Nova_Sequence_Nova_values)
{
	CCLASS_NEW(nova_math_Nova_Sequence, this,);
	this->vtable = &nova_math_Extension_VTable_Sequence_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_math_Nova_Sequence_Nova_super(this, exceptionData);
	
	{
		nova_math_Nova_Sequence_1_Nova_this(this, exceptionData, nova_math_Nova_Sequence_Nova_values);
	}
	
	return this;
}

void nova_math_Nova_Sequence_Nova_destroy(nova_math_Nova_Sequence** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_datastruct_list_Nova_DoubleArray_Nova_destroy(&(*this)->nova_math_Nova_Sequence_Nova_values, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_math_Nova_Sequence_1_Nova_this(nova_math_Nova_Sequence* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_DoubleArray* nova_math_Nova_Sequence_Nova_values)
{
	this->nova_math_Nova_Sequence_Nova_values = nova_math_Nova_Sequence_Nova_values;
}

double nova_math_Nova_Sequence_Nova_sum(nova_math_Nova_Sequence* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_math_Nova_Sequence_Nova_num)
{
	double l1_Nova_value = 0;
	int l2_Nova_i = 0;
	
	l1_Nova_value = (double)(0);
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)nova_math_Nova_Sequence_Nova_num; l2_Nova_i++)
	{
		l1_Nova_value = l1_Nova_value + (double)(intptr_t)nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(this->nova_math_Nova_Sequence_Nova_values), exceptionData, l2_Nova_i);
	}
	return l1_Nova_value;
}

void nova_math_Nova_Sequence_Nova_super(nova_math_Nova_Sequence* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_math_Nova_Sequence_Nova_values = (nova_datastruct_list_Nova_DoubleArray*)nova_null;
}

