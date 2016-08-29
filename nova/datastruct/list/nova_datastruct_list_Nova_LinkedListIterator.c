#include <precompiled.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_LinkedListIterator.h>



nova_datastruct_list_Extension_VTable_LinkedListIterator nova_datastruct_list_Extension_VTable_LinkedListIterator_val =
{
	{
		0,
		0,
		(nova_datastruct_list_Nova_Iterator*(*)(nova_datastruct_list_Nova_Iterator*, nova_exception_Nova_ExceptionData*))nova_datastruct_list_Nova_LinkedListIterator_0_Nova_reset,
		(char(*)(nova_datastruct_list_Nova_Iterator*, nova_exception_Nova_ExceptionData*))nova_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext,
		(nova_Nova_Object*(*)(nova_datastruct_list_Nova_Iterator*, nova_exception_Nova_ExceptionData*))nova_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next,
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
	nova_datastruct_list_Nova_LinkedListIterator_0_Nova_reset,
	nova_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext,
	nova_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next,
};


CCLASS_PRIVATE
(
	nova_datastruct_list_Nova_LinkedList* nova_datastruct_list_Nova_LinkedListIterator_Nova_list;
	
)



void nova_datastruct_list_Nova_LinkedListIterator_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_list_Nova_LinkedListIterator* nova_datastruct_list_Nova_LinkedListIterator_Nova_construct(nova_datastruct_list_Nova_LinkedListIterator* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_LinkedList* nova_datastruct_list_Nova_LinkedListIterator_Nova_list)
{
	CCLASS_NEW(nova_datastruct_list_Nova_LinkedListIterator, this);
	this->vtable = &nova_datastruct_list_Extension_VTable_LinkedListIterator_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_list_Nova_LinkedListIterator_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_list_Nova_LinkedListIterator_Nova_this(this, exceptionData, nova_datastruct_list_Nova_LinkedListIterator_Nova_list);
	}
	
	return this;
}

void nova_datastruct_list_Nova_LinkedListIterator_Nova_destroy(nova_datastruct_list_Nova_LinkedListIterator** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_datastruct_list_Nova_LinkedList_Nova_destroy(&(*this)->prv->nova_datastruct_list_Nova_LinkedListIterator_Nova_list, exceptionData);
	NOVA_FREE((*this)->prv);
	nova_datastruct_list_Nova_ListNode_Nova_destroy(&(*this)->nova_datastruct_list_Nova_LinkedListIterator_Nova_position, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_datastruct_list_Nova_LinkedListIterator_Nova_this(nova_datastruct_list_Nova_LinkedListIterator* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_LinkedList* nova_datastruct_list_Nova_LinkedListIterator_Nova_list)
{
	this->prv->nova_datastruct_list_Nova_LinkedListIterator_Nova_list = nova_datastruct_list_Nova_LinkedListIterator_Nova_list;
	nova_datastruct_list_Nova_Iterator_virtual0_Nova_reset((nova_datastruct_list_Nova_Iterator*)(this), exceptionData);
}

nova_datastruct_list_Nova_Iterator* nova_datastruct_list_Nova_LinkedListIterator_0_Nova_reset(nova_datastruct_list_Nova_LinkedListIterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_datastruct_list_Nova_LinkedListIterator_Nova_position = nova_datastruct_list_Nova_LinkedList_Accessor_Nova_first(this->prv->nova_datastruct_list_Nova_LinkedListIterator_Nova_list, exceptionData);
	return (nova_datastruct_list_Nova_Iterator*)this;
}

char nova_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_datastruct_list_Nova_LinkedListIterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_datastruct_list_Nova_LinkedListIterator_Nova_position != (nova_datastruct_list_Nova_ListNode*)nova_null;
}


nova_Nova_Object* nova_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_datastruct_list_Nova_LinkedListIterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(this), exceptionData))
	{
		nova_Nova_Object* l2_Nova_data = (nova_Nova_Object*)nova_null;
		
		l2_Nova_data = this->nova_datastruct_list_Nova_LinkedListIterator_Nova_position->nova_datastruct_list_Nova_ListNode_Nova_data;
		this->nova_datastruct_list_Nova_LinkedListIterator_Nova_position = this->nova_datastruct_list_Nova_LinkedListIterator_Nova_position->nova_datastruct_list_Nova_ListNode_Nova_next;
		return (nova_Nova_Object*)l2_Nova_data;
	}
	THROW(2, nova_datastruct_list_Nova_NoSuchElementException_0_Nova_construct(0, exceptionData));
	return (nova_Nova_Object*)(nova_Nova_Object*)nova_null;
}


void nova_datastruct_list_Nova_LinkedListIterator_Nova_super(nova_datastruct_list_Nova_LinkedListIterator* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_datastruct_list_Nova_LinkedListIterator_Nova_position = (nova_datastruct_list_Nova_ListNode*)nova_null;
	this->prv->nova_datastruct_list_Nova_LinkedListIterator_Nova_list = (nova_datastruct_list_Nova_LinkedList*)nova_null;
}

