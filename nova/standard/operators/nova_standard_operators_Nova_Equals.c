#include <precompiled.h>
#include <nova/standard/operators/nova_standard_operators_Nova_Equals.h>



nova_standard_operators_Extension_VTable_Equals nova_standard_operators_Extension_VTable_Equals_val =
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
	nova_standard_operators_Nova_Equals_virtual0_Nova_equals,
};


void nova_standard_operators_Nova_Equals_Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}


char nova_standard_operators_Nova_Equals_virtual0_Nova_equals(nova_standard_operators_Nova_Equals* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_operators_Nova_Equals_Nova_another)
{
	return this->vtable->itable.nova_standard_operators_Nova_Equals_virtual0_Nova_equals((nova_standard_operators_Nova_Equals*)(this), exceptionData, nova_standard_operators_Nova_Equals_Nova_another);
}

