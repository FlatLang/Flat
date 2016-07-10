#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Comparable.h>

nova_standard_datastruct_Extension_VTable_Comparable nova_standard_datastruct_Extension_VTable_Comparable_val =
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
		0,
	},
	nova_standard_datastruct_Nova_Comparable_virtual0_Nova_compareTo,
};


void nova_standard_datastruct_Nova_ComparableNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}


int nova_standard_datastruct_Nova_Comparable_virtual0_Nova_compareTo(nova_standard_datastruct_Nova_Comparable* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_Comparable_Nova_other)
{
	return this->vtable->itable.nova_standard_datastruct_Nova_Comparable_virtual0_Nova_compareTo((nova_standard_datastruct_Nova_Comparable*)(this), exceptionData, nova_standard_datastruct_Nova_Comparable_Nova_other);
}

