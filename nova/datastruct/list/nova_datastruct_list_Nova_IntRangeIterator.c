#include <precompiled.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntRangeIterator.h>



nova_datastruct_list_Extension_VTable_IntRangeIterator nova_datastruct_list_Extension_VTable_IntRangeIterator_val =
{
	{
		0,
		0,
		(nova_datastruct_list_Nova_Iterator*(*)(nova_datastruct_list_Nova_Iterator*, nova_exception_Nova_ExceptionData*))nova_datastruct_list_Nova_IntRangeIterator_0_Nova_reset,
		(char(*)(nova_datastruct_list_Nova_Iterator*, nova_exception_Nova_ExceptionData*))nova_datastruct_list_Nova_IntRangeIterator_Accessor_Nova_hasNext,
		(nova_Nova_Object*(*)(nova_datastruct_list_Nova_Iterator*, nova_exception_Nova_ExceptionData*))nova_datastruct_list_Nova_IntRangeIterator_Accessor_Nova_next,
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
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	nova_datastruct_list_Nova_IntRangeIterator_0_Nova_reset,
	nova_datastruct_list_Nova_IntRangeIterator_Accessor_Nova_hasNext,
	nova_datastruct_list_Nova_IntRangeIterator_Accessor_Nova_next,
};


CCLASS_PRIVATE
(
	nova_datastruct_list_Nova_IntRange* nova_datastruct_list_Nova_IntRangeIterator_Nova_range;
	
)



void nova_datastruct_list_Nova_IntRangeIterator_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_list_Nova_IntRangeIterator* nova_datastruct_list_Nova_IntRangeIterator_Nova_construct(nova_datastruct_list_Nova_IntRangeIterator* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_IntRange* nova_datastruct_list_Nova_IntRangeIterator_Nova_range)
{
	CCLASS_NEW(nova_datastruct_list_Nova_IntRangeIterator, this);
	this->vtable = &nova_datastruct_list_Extension_VTable_IntRangeIterator_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_list_Nova_IntRangeIterator_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_list_Nova_IntRangeIterator_Nova_this(this, exceptionData, nova_datastruct_list_Nova_IntRangeIterator_Nova_range);
	}
	
	return this;
}

void nova_datastruct_list_Nova_IntRangeIterator_Nova_destroy(nova_datastruct_list_Nova_IntRangeIterator** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_datastruct_list_Nova_IntRange_Nova_destroy(&(*this)->prv->nova_datastruct_list_Nova_IntRangeIterator_Nova_range, exceptionData);
	NOVA_FREE((*this)->prv);
	
	
	NOVA_FREE(*this);
}

void nova_datastruct_list_Nova_IntRangeIterator_Nova_this(nova_datastruct_list_Nova_IntRangeIterator* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_IntRange* nova_datastruct_list_Nova_IntRangeIterator_Nova_range)
{
	this->prv->nova_datastruct_list_Nova_IntRangeIterator_Nova_range = nova_datastruct_list_Nova_IntRangeIterator_Nova_range;
	nova_datastruct_list_Nova_Iterator_virtual0_Nova_reset((nova_datastruct_list_Nova_Iterator*)(this), exceptionData);
}

nova_datastruct_list_Nova_Iterator* nova_datastruct_list_Nova_IntRangeIterator_0_Nova_reset(nova_datastruct_list_Nova_IntRangeIterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_datastruct_list_Nova_IntRangeIterator_Nova_position = this->prv->nova_datastruct_list_Nova_IntRangeIterator_Nova_range->nova_datastruct_list_Nova_IntRange_Nova_start;
	return (nova_datastruct_list_Nova_Iterator*)this;
}

char nova_datastruct_list_Nova_IntRangeIterator_Accessor_Nova_hasNext(nova_datastruct_list_Nova_IntRangeIterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->nova_datastruct_list_Nova_IntRangeIterator_Nova_position < this->prv->nova_datastruct_list_Nova_IntRangeIterator_Nova_range->nova_datastruct_list_Nova_IntRange_Nova_end;
}


int nova_datastruct_list_Nova_IntRangeIterator_Accessor_Nova_next(nova_datastruct_list_Nova_IntRangeIterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(this), exceptionData))
	{
		return this->nova_datastruct_list_Nova_IntRangeIterator_Nova_position++;
	}
	THROW(2, nova_datastruct_list_Nova_NoSuchElementException_0_Nova_construct(0, exceptionData));
	return (int)nova_null;
}


void nova_datastruct_list_Nova_IntRangeIterator_Nova_super(nova_datastruct_list_Nova_IntRangeIterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_datastruct_list_Nova_IntRangeIterator_Nova_position = 0;
	this->prv->nova_datastruct_list_Nova_IntRangeIterator_Nova_range = (nova_datastruct_list_Nova_IntRange*)nova_null;
}

