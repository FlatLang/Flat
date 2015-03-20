#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Stack.h>


nova_standard_datastruct_Extension_VTable_Stack nova_standard_datastruct_Extension_VTable_Stack_val =
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
	},
	nova_standard_Nova_Object_3_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	nova_standard_datastruct_Nova_ListNode* nova_standard_datastruct_Nova_Stack_Nova_top;
	
)
void nova_standard_datastruct_Nova_StackNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Stack* nova_standard_datastruct_Nova_Stack_2_Nova_construct(nova_standard_datastruct_Nova_Stack* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Stack, this);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Stack_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_Stack_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Stack_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_Stack_Nova_destroy(nova_standard_datastruct_Nova_Stack** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_Nova_ListNode_Nova_destroy(&(*this)->prv->nova_standard_datastruct_Nova_Stack_Nova_top, exceptionData);
	NOVA_FREE((*this)->prv);
	
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_Stack_Nova_push(nova_standard_datastruct_Nova_Stack* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data)
{
	nova_standard_datastruct_Nova_ListNode* l1_Nova_node;
	
	l1_Nova_node = nova_standard_datastruct_Nova_ListNode_Nova_construct(0, exceptionData, l0_Nova_data);
	l1_Nova_node->nova_standard_datastruct_Nova_ListNode_Nova_next = this->prv->nova_standard_datastruct_Nova_Stack_Nova_top;
	this->prv->nova_standard_datastruct_Nova_Stack_Nova_top = l1_Nova_node;
	this->nova_standard_datastruct_Nova_Stack_Nova_size++;
}

nova_standard_Nova_Object* nova_standard_datastruct_Nova_Stack_Nova_pop(nova_standard_datastruct_Nova_Stack* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_Object* l1_Nova_data;
	
	if (nova_standard_datastruct_Nova_Stack_Nova_isEmpty(this, exceptionData))
	{
		THROW(4, nova_standard_datastruct_Nova_EmptyStackException_0_Nova_construct(0, exceptionData));
	}
	l1_Nova_data = (nova_standard_Nova_Object*)(this->prv->nova_standard_datastruct_Nova_Stack_Nova_top->nova_standard_datastruct_Nova_ListNode_Nova_data);
	this->prv->nova_standard_datastruct_Nova_Stack_Nova_top = this->prv->nova_standard_datastruct_Nova_Stack_Nova_top->nova_standard_datastruct_Nova_ListNode_Nova_next;
	this->nova_standard_datastruct_Nova_Stack_Nova_size--;
	return (nova_standard_Nova_Object*)l1_Nova_data;
}

char nova_standard_datastruct_Nova_Stack_Nova_isEmpty(nova_standard_datastruct_Nova_Stack* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->nova_standard_datastruct_Nova_Stack_Nova_size <= 0;
}

void nova_standard_datastruct_Nova_Stack_2_Nova_this(nova_standard_datastruct_Nova_Stack* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_datastruct_Nova_Stack_Nova_super(nova_standard_datastruct_Nova_Stack* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_Stack_Nova_size = 0;
	this->prv->nova_standard_datastruct_Nova_Stack_Nova_top = (nova_standard_datastruct_Nova_ListNode*)nova_null;
}

