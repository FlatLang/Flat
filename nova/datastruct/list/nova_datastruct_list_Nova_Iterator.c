#include <precompiled.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Iterator.h>



nova_datastruct_list_Extension_VTable_Iterator nova_datastruct_list_Extension_VTable_Iterator_val =
{
	{
		0,
		0,
		0,
		(char(*)(nova_datastruct_list_Nova_Iterator*, nova_exception_Nova_ExceptionData*))nova_datastruct_list_Nova_Iterator_Accessor_Nova_hasNext,
		(nova_Nova_Object*(*)(nova_datastruct_list_Nova_Iterator*, nova_exception_Nova_ExceptionData*))nova_datastruct_list_Nova_Iterator_Accessor_Nova_next,
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
	nova_datastruct_list_Nova_Iterator_virtual0_Nova_reset,
	nova_datastruct_list_Nova_Iterator_Accessor_Nova_hasNext,
	nova_datastruct_list_Nova_Iterator_Accessor_Nova_next,
};





void nova_datastruct_list_Nova_Iterator_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}


char nova_datastruct_list_Nova_Iterator_Accessor_Nova_hasNext(nova_datastruct_list_Nova_Iterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return 0;
}


nova_Nova_Object* nova_datastruct_list_Nova_Iterator_Accessor_Nova_next(nova_datastruct_list_Nova_Iterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_Nova_Object*)(nova_Nova_Object*)nova_null;
}


nova_datastruct_list_Nova_Iterator* nova_datastruct_list_Nova_Iterator_virtual0_Nova_reset(nova_datastruct_list_Nova_Iterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.nova_datastruct_list_Nova_Iterator_virtual0_Nova_reset((nova_datastruct_list_Nova_Iterator*)(this), exceptionData);
}

char nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext(nova_datastruct_list_Nova_Iterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(this), exceptionData);
}

nova_Nova_Object* nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next(nova_datastruct_list_Nova_Iterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(this), exceptionData);
}

