#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_LinkedList.h>

nova_standard_datastruct_list_Extension_VTable_LinkedList nova_standard_datastruct_list_Extension_VTable_LinkedList_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	nova_standard_datastruct_list_Nova_ListNode* nova_standard_datastruct_list_Nova_LinkedList_Nova_start;
	nova_standard_datastruct_list_Nova_ListNode* nova_standard_datastruct_list_Nova_LinkedList_Nova_current;
	
)



void nova_standard_datastruct_list_Nova_LinkedListNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedList_0_Nova_construct(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_LinkedList, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_LinkedList_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_LinkedList_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_LinkedList_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_list_Nova_LinkedList_Nova_destroy(nova_standard_datastruct_list_Nova_LinkedList** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_list_Nova_ListNode_Nova_destroy(&(*this)->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start, exceptionData);
	nova_standard_datastruct_list_Nova_ListNode_Nova_destroy(&(*this)->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_current, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedList_Nova_add(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_LinkedList_Nova_data)
{
	nova_standard_datastruct_list_Nova_ListNode* l1_Nova_node;
	
	l1_Nova_node = nova_standard_datastruct_list_Nova_ListNode_Nova_construct(0, exceptionData, nova_standard_datastruct_list_Nova_LinkedList_Nova_data);
	if (this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start == (nova_standard_datastruct_list_Nova_ListNode*)nova_null)
	{
		this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start = l1_Nova_node;
		this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_current = l1_Nova_node;
	}
	else
	{
		this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_current->nova_standard_datastruct_list_Nova_ListNode_Nova_next = l1_Nova_node;
	}
	this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_current = l1_Nova_node;
	return this;
}

nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedList_Nova_remove(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_LinkedList_Nova_data)
{
	nova_standard_datastruct_list_Nova_ListNode* l1_Nova_prev;
	nova_standard_datastruct_list_Nova_ListNode* l1_Nova_cur;
	
	if (this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start->nova_standard_datastruct_list_Nova_ListNode_Nova_data == nova_standard_datastruct_list_Nova_LinkedList_Nova_data)
	{
		this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start = this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start->nova_standard_datastruct_list_Nova_ListNode_Nova_next;
	}
	l1_Nova_prev = this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start;
	l1_Nova_cur = this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start->nova_standard_datastruct_list_Nova_ListNode_Nova_next;
	while (l1_Nova_cur != (nova_standard_datastruct_list_Nova_ListNode*)nova_null)
	{
		nova_standard_Nova_Object* l2_Nova_d;
		
		l2_Nova_d = l1_Nova_cur->nova_standard_datastruct_list_Nova_ListNode_Nova_data;
		if (l2_Nova_d == nova_standard_datastruct_list_Nova_LinkedList_Nova_data)
		{
			l1_Nova_prev->nova_standard_datastruct_list_Nova_ListNode_Nova_next = l1_Nova_cur->nova_standard_datastruct_list_Nova_ListNode_Nova_next;
		}
		l1_Nova_cur = l1_Nova_cur->nova_standard_datastruct_list_Nova_ListNode_Nova_next;
	}
	return this;
}

void nova_standard_datastruct_list_Nova_LinkedList_0_Nova_this(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

nova_standard_datastruct_list_Nova_LinkedListIterator* nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_iterator(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_construct(0, exceptionData, this);
}


nova_standard_datastruct_list_Nova_ListNode* nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_first(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start;
}


void nova_standard_datastruct_list_Nova_LinkedList_Nova_super(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start = (nova_standard_datastruct_list_Nova_ListNode*)nova_null;
	this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_current = (nova_standard_datastruct_list_Nova_ListNode*)nova_null;
}

