#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_Iterable.h>

nova_standard_datastruct_list_Extension_VTable_Iterable nova_standard_datastruct_list_Extension_VTable_Iterable_val =
{
	{
		0,
		(nova_standard_datastruct_list_Nova_Iterator*(*)(nova_standard_datastruct_list_Nova_Iterable*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_Iterable_Accessor_Nova_iterator,
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
	nova_standard_datastruct_list_Nova_Iterable_Accessor_Nova_iterator,
};




void nova_standard_datastruct_list_Nova_IterableNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_list_Nova_Iterator* nova_standard_datastruct_list_Nova_Iterable_Accessor_Nova_iterator(nova_standard_datastruct_list_Nova_Iterable* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_datastruct_list_Nova_Iterator*)nova_null;
}


nova_standard_datastruct_list_Nova_Iterator* nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator(nova_standard_datastruct_list_Nova_Iterable* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)(this), exceptionData);
}

