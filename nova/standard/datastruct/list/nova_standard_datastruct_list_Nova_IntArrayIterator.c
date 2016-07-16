#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_IntArrayIterator.h>

nova_standard_datastruct_list_Extension_VTable_IntArrayIterator nova_standard_datastruct_list_Extension_VTable_IntArrayIterator_val =
{
	{
		0,
		0,
		(nova_standard_datastruct_list_Nova_Iterator*(*)(nova_standard_datastruct_list_Nova_Iterator*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_IntArrayIterator_0_Nova_reset,
		(char(*)(nova_standard_datastruct_list_Nova_Iterator*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_IntArrayIterator_Accessor_Nova_hasNext,
		(nova_standard_Nova_Object*(*)(nova_standard_datastruct_list_Nova_Iterator*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_IntArrayIterator_Accessor_Nova_next,
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_datastruct_list_Nova_IntArrayIterator_0_Nova_reset,
	nova_standard_datastruct_list_Nova_IntArrayIterator_Accessor_Nova_hasNext,
	nova_standard_datastruct_list_Nova_IntArrayIterator_Accessor_Nova_next,
};


CCLASS_PRIVATE
(
	nova_standard_datastruct_list_Nova_IntArray* nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_array;
	
)



void nova_standard_datastruct_list_Nova_IntArrayIteratorNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_list_Nova_IntArrayIterator* nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_IntArrayIterator(nova_standard_datastruct_list_Nova_IntArrayIterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_IntArray* nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_array)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_IntArrayIterator, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_IntArrayIterator_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_array);
	}
	
	return this;
}

void nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_destroy(nova_standard_datastruct_list_Nova_IntArrayIterator** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_list_Nova_IntArray_Nova_destroy(&(*this)->prv->nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_array, exceptionData);
	NOVA_FREE((*this)->prv);
	
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_this(nova_standard_datastruct_list_Nova_IntArrayIterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_IntArray* nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_array)
{
	this->prv->nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_array = nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_array;
	nova_standard_datastruct_list_Nova_Iterator_virtual0_Nova_reset((nova_standard_datastruct_list_Nova_Iterator*)(this), exceptionData);
}

nova_standard_datastruct_list_Nova_Iterator* nova_standard_datastruct_list_Nova_IntArrayIterator_0_Nova_reset(nova_standard_datastruct_list_Nova_IntArrayIterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_position = (int)(0);
	return (nova_standard_datastruct_list_Nova_Iterator*)this;
}

char nova_standard_datastruct_list_Nova_IntArrayIterator_Accessor_Nova_hasNext(nova_standard_datastruct_list_Nova_IntArrayIterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->prv->nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_array->nova_standard_datastruct_list_Nova_Array_Nova_count > this->nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_position;
}


int nova_standard_datastruct_list_Nova_IntArrayIterator_Accessor_Nova_next(nova_standard_datastruct_list_Nova_IntArrayIterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(this), exceptionData))
	{
		return (int)nova_standard_datastruct_list_Nova_Array_Nova_get((nova_standard_datastruct_list_Nova_Array*)(this->prv->nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_array), exceptionData, this->nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_position++);
	}
	THROW(2, nova_standard_datastruct_list_Nova_NoSuchElementException_0_Nova_NoSuchElementException(0, exceptionData));
	return (int)nova_null;
}


void nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_super(nova_standard_datastruct_list_Nova_IntArrayIterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_position = 0;
	this->prv->nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_array = (nova_standard_datastruct_list_Nova_IntArray*)nova_null;
}

