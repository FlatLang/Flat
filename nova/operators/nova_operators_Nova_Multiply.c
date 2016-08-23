#include <precompiled.h>
#include <nova/operators/nova_operators_Nova_Multiply.h>



nova_operators_Extension_VTable_Multiply nova_operators_Extension_VTable_Multiply_val =
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
		0,
		0,
		0,
	},
	nova_operators_Nova_Multiply_virtual1_Nova_multiply,
};


void nova_operators_Nova_Multiply_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}


nova_Nova_Object* nova_operators_Nova_Multiply_virtual1_Nova_multiply(nova_operators_Nova_Multiply* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_operators_Nova_Multiply_Nova_value)
{
	return this->vtable->itable.nova_operators_Nova_Multiply_virtual1_Nova_multiply((nova_operators_Nova_Multiply*)(this), exceptionData, nova_operators_Nova_Multiply_Nova_value);
}

