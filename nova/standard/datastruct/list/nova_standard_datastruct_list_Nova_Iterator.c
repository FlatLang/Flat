#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_Iterator.h>

nova_standard_datastruct_list_Extension_VTable_Iterator nova_standard_datastruct_list_Extension_VTable_Iterator_val =
{
	{
		0,
		0,
		0,
		(char(*)(nova_standard_datastruct_list_Nova_Iterator*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_Iterator_Accessor_Nova_hasNext,
		(nova_standard_Nova_Object*(*)(nova_standard_datastruct_list_Nova_Iterator*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_Iterator_Accessor_Nova_next,
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
	nova_standard_datastruct_list_Nova_Iterator_virtual0_Nova_reset,
	nova_standard_datastruct_list_Nova_Iterator_Accessor_Nova_hasNext,
	nova_standard_datastruct_list_Nova_Iterator_Accessor_Nova_next,
};





void nova_standard_datastruct_list_Nova_IteratorNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}


char nova_standard_datastruct_list_Nova_Iterator_Accessor_Nova_hasNext(nova_standard_datastruct_list_Nova_Iterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return 0;
}


nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Iterator_Accessor_Nova_next(nova_standard_datastruct_list_Nova_Iterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_Nova_Object*)nova_null;
}


nova_standard_datastruct_list_Nova_Iterator* nova_standard_datastruct_list_Nova_Iterator_virtual0_Nova_reset(nova_standard_datastruct_list_Nova_Iterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.nova_standard_datastruct_list_Nova_Iterator_virtual0_Nova_reset((nova_standard_datastruct_list_Nova_Iterator*)(this), exceptionData);
}

char nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext(nova_standard_datastruct_list_Nova_Iterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(this), exceptionData);
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next(nova_standard_datastruct_list_Nova_Iterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(this), exceptionData);
}

