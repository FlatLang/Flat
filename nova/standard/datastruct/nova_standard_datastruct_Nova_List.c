#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_List.h>


nova_standard_datastruct_VTable_List nova_standard_datastruct_VTable_List_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
CCLASS_PRIVATE
(
	nova_standard_datastruct_Nova_ListNode* nova_standard_datastruct_Nova_List_Nova_start;
	nova_standard_datastruct_Nova_ListNode* nova_standard_datastruct_Nova_List_Nova_current;
	
)
void nova_standard_datastruct_Nova_ListNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_List* nova_standard_datastruct_Nova_List_2_Nova_construct(nova_standard_datastruct_Nova_List* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_List, this);
	this->vtable = &nova_standard_datastruct_VTable_List_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_List_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_List_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_List_Nova_destroy(nova_standard_datastruct_Nova_List** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_Nova_ListNode_Nova_destroy(&(*this)->prv->nova_standard_datastruct_Nova_List_Nova_start, exceptionData);
	nova_standard_datastruct_Nova_ListNode_Nova_destroy(&(*this)->prv->nova_standard_datastruct_Nova_List_Nova_current, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

nova_standard_datastruct_Nova_ListNode* nova_standard_datastruct_Nova_List_Nova_getFirst(nova_standard_datastruct_Nova_List* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_standard_datastruct_Nova_List_Nova_start;
}

void nova_standard_datastruct_Nova_List_Nova_add(nova_standard_datastruct_Nova_List* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data)
{
	nova_standard_datastruct_Nova_ListNode* l1_Nova_node;
	
	l1_Nova_node = nova_standard_datastruct_Nova_ListNode_Nova_construct(0, exceptionData, l0_Nova_data);
	if (this->prv->nova_standard_datastruct_Nova_List_Nova_start == (nova_standard_datastruct_Nova_ListNode*)nova_null)
	{
		this->prv->nova_standard_datastruct_Nova_List_Nova_start = l1_Nova_node;
		this->prv->nova_standard_datastruct_Nova_List_Nova_current = l1_Nova_node;
	}
	else
	{
		this->prv->nova_standard_datastruct_Nova_List_Nova_current->nova_standard_datastruct_Nova_ListNode_Nova_next = l1_Nova_node;
	}
	this->prv->nova_standard_datastruct_Nova_List_Nova_current = l1_Nova_node;
}

void nova_standard_datastruct_Nova_List_Nova_remove(nova_standard_datastruct_Nova_List* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data)
{
	nova_standard_datastruct_Nova_ListNode* l1_Nova_prev;
	nova_standard_datastruct_Nova_ListNode* l1_Nova_cur;
	
	if (this->prv->nova_standard_datastruct_Nova_List_Nova_start->nova_standard_datastruct_Nova_ListNode_Nova_data == l0_Nova_data)
	{
		this->prv->nova_standard_datastruct_Nova_List_Nova_start = this->prv->nova_standard_datastruct_Nova_List_Nova_start->nova_standard_datastruct_Nova_ListNode_Nova_next;
	}
	l1_Nova_prev = this->prv->nova_standard_datastruct_Nova_List_Nova_start;
	l1_Nova_cur = this->prv->nova_standard_datastruct_Nova_List_Nova_start->nova_standard_datastruct_Nova_ListNode_Nova_next;
	while (l1_Nova_cur != (nova_standard_datastruct_Nova_ListNode*)nova_null)
	{
		nova_standard_Nova_Object* l2_Nova_d;
		
		l2_Nova_d = (nova_standard_Nova_Object*)(l1_Nova_cur->nova_standard_datastruct_Nova_ListNode_Nova_data);
		if (l2_Nova_d == l0_Nova_data)
		{
			l1_Nova_prev->nova_standard_datastruct_Nova_ListNode_Nova_next = l1_Nova_cur->nova_standard_datastruct_Nova_ListNode_Nova_next;
		}
		l1_Nova_cur = l1_Nova_cur->nova_standard_datastruct_Nova_ListNode_Nova_next;
	}
}

void nova_standard_datastruct_Nova_List_2_Nova_this(nova_standard_datastruct_Nova_List* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_datastruct_Nova_List_Nova_super(nova_standard_datastruct_Nova_List* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_Nova_List_Nova_start = (nova_standard_datastruct_Nova_ListNode*)nova_null;
	this->prv->nova_standard_datastruct_Nova_List_Nova_current = (nova_standard_datastruct_Nova_ListNode*)nova_null;
}

