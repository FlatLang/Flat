#include <precompiled.h>
#include <nova/operators/nova_operators_Nova_Equals.h>



nova_operators_Extension_VTable_Equals nova_operators_Extension_VTable_Equals_val =
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
	nova_operators_Nova_Equals_virtual0_Nova_equals,
};


void nova_operators_Nova_Equals_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}


char nova_operators_Nova_Equals_virtual0_Nova_equals(nova_operators_Nova_Equals* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_operators_Nova_Equals_Nova_another)
{
	return this->vtable->itable.nova_operators_Nova_Equals_virtual0_Nova_equals((nova_operators_Nova_Equals*)(this), exceptionData, nova_operators_Nova_Equals_Nova_another);
}

