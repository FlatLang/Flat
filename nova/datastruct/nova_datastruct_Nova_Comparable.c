#include <precompiled.h>
#include <nova/datastruct/nova_datastruct_Nova_Comparable.h>



nova_datastruct_Extension_VTable_Comparable nova_datastruct_Extension_VTable_Comparable_val =
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
	nova_datastruct_Nova_Comparable_virtual0_Nova_compareTo,
};


void nova_datastruct_Nova_Comparable_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}


int nova_datastruct_Nova_Comparable_virtual0_Nova_compareTo(nova_datastruct_Nova_Comparable* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_Comparable_Nova_other)
{
	return this->vtable->itable.nova_datastruct_Nova_Comparable_virtual0_Nova_compareTo((nova_datastruct_Nova_Comparable*)(this), exceptionData, nova_datastruct_Nova_Comparable_Nova_other);
}

