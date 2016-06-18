#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_LinkedListIterator.h>

nova_standard_datastruct_list_Extension_VTable_LinkedListIterator nova_standard_datastruct_list_Extension_VTable_LinkedListIterator_val =
{
	{
		0,
		(nova_standard_datastruct_list_Nova_Iterator*(*)(nova_standard_datastruct_list_Nova_Iterator*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_LinkedListIterator_0_Nova_reset,
		(char(*)(nova_standard_datastruct_list_Nova_Iterator*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext,
		0,
		(nova_standard_Nova_Object*(*)(nova_standard_datastruct_list_Nova_Iterator*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next,
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_datastruct_list_Nova_LinkedListIterator_0_Nova_reset,
	nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext,
	nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next,
};


CCLASS_PRIVATE
(
	nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_list;
	
)



void nova_standard_datastruct_list_Nova_LinkedListIteratorNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_list_Nova_LinkedListIterator* nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_construct(nova_standard_datastruct_list_Nova_LinkedListIterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_list)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_LinkedListIterator, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_LinkedListIterator_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_list);
	}
	
	return this;
}

void nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_destroy(nova_standard_datastruct_list_Nova_LinkedListIterator** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_list_Nova_LinkedList_Nova_destroy(&(*this)->prv->nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_list, exceptionData);
	NOVA_FREE((*this)->prv);
	nova_standard_datastruct_list_Nova_ListNode_Nova_destroy(&(*this)->nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_position, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_this(nova_standard_datastruct_list_Nova_LinkedListIterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_list)
{
	this->prv->nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_list = nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_list;
	this->vtable->nova_standard_datastruct_list_Nova_LinkedListIterator_virtual0_Nova_reset(this, exceptionData);
}

nova_standard_datastruct_list_Nova_Iterator* nova_standard_datastruct_list_Nova_LinkedListIterator_0_Nova_reset(nova_standard_datastruct_list_Nova_LinkedListIterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_position = this->prv->nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_list->vtable->nova_standard_datastruct_list_Nova_LinkedList_Accessor0_Nova_first(this->prv->nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_list, exceptionData);
	return (nova_standard_datastruct_list_Nova_Iterator*)this;
}

char nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_standard_datastruct_list_Nova_LinkedListIterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_position != (nova_standard_datastruct_list_Nova_ListNode*)nova_null;
}


nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_standard_datastruct_list_Nova_LinkedListIterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (this->vtable->nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(this, exceptionData))
	{
		nova_standard_Nova_Object* l2_Nova_data;
		
		l2_Nova_data = this->nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_position->nova_standard_datastruct_list_Nova_ListNode_Nova_data;
		this->nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_position = this->nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_position->nova_standard_datastruct_list_Nova_ListNode_Nova_next;
		return l2_Nova_data;
	}
	THROW(2, nova_standard_datastruct_list_Nova_NoSuchElementException_1_Nova_construct(0, exceptionData));
	return (nova_standard_Nova_Object*)nova_null;
}


void nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_super(nova_standard_datastruct_list_Nova_LinkedListIterator* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_position = (nova_standard_datastruct_list_Nova_ListNode*)nova_null;
	this->prv->nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_list = (nova_standard_datastruct_list_Nova_LinkedList*)nova_null;
}

