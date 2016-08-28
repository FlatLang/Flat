#include <precompiled.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Iterable.h>



nova_datastruct_list_Extension_VTable_Iterable nova_datastruct_list_Extension_VTable_Iterable_val =
{
	{
		0,
		(nova_datastruct_list_Nova_Iterator*(*)(nova_datastruct_list_Nova_Iterable*, nova_exception_Nova_ExceptionData*))nova_datastruct_list_Nova_Iterable_Accessor_Nova_iterator,
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
	nova_datastruct_list_Nova_Iterable_Accessor_Nova_iterator,
};




void nova_datastruct_list_Nova_Iterable_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_list_Nova_Iterator* nova_datastruct_list_Nova_Iterable_Accessor_Nova_iterator(nova_datastruct_list_Nova_Iterable* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_datastruct_list_Nova_Iterator*)(nova_datastruct_list_Nova_Iterator*)nova_null;
}


nova_datastruct_list_Nova_Iterator* nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator(nova_datastruct_list_Nova_Iterable* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)(this), exceptionData);
}

