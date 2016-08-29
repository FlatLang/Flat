#include <precompiled.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Stack.h>



nova_datastruct_list_Extension_VTable_Stack nova_datastruct_list_Extension_VTable_Stack_val =
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
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


CCLASS_PRIVATE
(
	nova_datastruct_list_Nova_ListNode* nova_datastruct_list_Nova_Stack_Nova_top;
	
)


void nova_datastruct_list_Nova_Stack_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_list_Nova_Stack* nova_datastruct_list_Nova_Stack_Nova_construct(nova_datastruct_list_Nova_Stack* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_datastruct_list_Nova_Stack, this);
	this->vtable = &nova_datastruct_list_Extension_VTable_Stack_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_list_Nova_Stack_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_list_Nova_Stack_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_datastruct_list_Nova_Stack_Nova_destroy(nova_datastruct_list_Nova_Stack** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_datastruct_list_Nova_ListNode_Nova_destroy(&(*this)->prv->nova_datastruct_list_Nova_Stack_Nova_top, exceptionData);
	NOVA_FREE((*this)->prv);
	
	
	NOVA_FREE(*this);
}

void nova_datastruct_list_Nova_Stack_Nova_push(nova_datastruct_list_Nova_Stack* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_list_Nova_Stack_Nova_data)
{
	nova_datastruct_list_Nova_ListNode* l1_Nova_node = (nova_datastruct_list_Nova_ListNode*)nova_null;
	
	l1_Nova_node = nova_datastruct_list_Nova_ListNode_Nova_construct(0, exceptionData, nova_datastruct_list_Nova_Stack_Nova_data);
	l1_Nova_node->nova_datastruct_list_Nova_ListNode_Nova_next = this->prv->nova_datastruct_list_Nova_Stack_Nova_top;
	this->prv->nova_datastruct_list_Nova_Stack_Nova_top = l1_Nova_node;
	this->nova_datastruct_list_Nova_Stack_Nova_size++;
}

nova_Nova_Object* nova_datastruct_list_Nova_Stack_Nova_pop(nova_datastruct_list_Nova_Stack* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_Nova_Object* l1_Nova_data = (nova_Nova_Object*)nova_null;
	
	if (nova_datastruct_list_Nova_Stack_Accessor_Nova_empty(this, exceptionData))
	{
		THROW(3, nova_datastruct_list_Nova_EmptyStackException_0_Nova_construct(0, exceptionData));
	}
	l1_Nova_data = this->prv->nova_datastruct_list_Nova_Stack_Nova_top->nova_datastruct_list_Nova_ListNode_Nova_data;
	this->prv->nova_datastruct_list_Nova_Stack_Nova_top = this->prv->nova_datastruct_list_Nova_Stack_Nova_top->nova_datastruct_list_Nova_ListNode_Nova_next;
	this->nova_datastruct_list_Nova_Stack_Nova_size--;
	return (nova_Nova_Object*)l1_Nova_data;
}

void nova_datastruct_list_Nova_Stack_0_Nova_this(nova_datastruct_list_Nova_Stack* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

char nova_datastruct_list_Nova_Stack_Accessor_Nova_empty(nova_datastruct_list_Nova_Stack* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->nova_datastruct_list_Nova_Stack_Nova_size <= 0;
}


void nova_datastruct_list_Nova_Stack_Nova_super(nova_datastruct_list_Nova_Stack* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_datastruct_list_Nova_Stack_Nova_size = 0;
	this->prv->nova_datastruct_list_Nova_Stack_Nova_top = (nova_datastruct_list_Nova_ListNode*)nova_null;
}

