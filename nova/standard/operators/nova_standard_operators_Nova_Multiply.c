#include <precompiled.h>
#include <nova/standard/operators/nova_standard_operators_Nova_Multiply.h>

nova_standard_operators_Extension_VTable_Multiply nova_standard_operators_Extension_VTable_Multiply_val =
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
	nova_standard_operators_Nova_Multiply_virtual1_Nova_multiply,
};


void nova_standard_operators_Nova_MultiplyNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}


nova_standard_Nova_Object* nova_standard_operators_Nova_Multiply_virtual1_Nova_multiply(nova_standard_operators_Nova_Multiply* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_operators_Nova_Multiply_Nova_value)
{
	return this->vtable->itable.nova_standard_operators_Nova_Multiply_virtual1_Nova_multiply((nova_standard_operators_Nova_Multiply*)(this), exceptionData, nova_standard_operators_Nova_Multiply_Nova_value);
}

