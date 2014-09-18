#include <precompiled.h>
#include "NovaStack.h"


nova_VTable_Stack nova_VTable_Stack_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};
CCLASS_PRIVATE
(
	ListNode* nova_Stack_top;
	
)

Stack* nova_0_Stack_construct(Stack* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Stack, this);
	this->vtable = &nova_VTable_Stack_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Stack_super(this, 0);
	
	{
		nova_Stack_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_Stack(Stack** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ListNode(&(*this)->prv->nova_Stack_top, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_Stack_push(Stack* this, ExceptionData* exceptionData, Object* nova_0_data)
{
	ListNode* nova_1_node;
	
	nova_1_node = nova_ListNode_construct(0, exceptionData, nova_0_data);
	nova_1_node->nova_ListNode_next = this->prv->nova_Stack_top;
	this->prv->nova_Stack_top = nova_1_node;
}

Object* nova_Stack_pop(Stack* this, ExceptionData* exceptionData)
{
	Object* nova_1_data;
	
	if (nova_Stack_isEmpty(this, exceptionData))
	{
		THROW(2);
	}
	nova_1_data = this->prv->nova_Stack_top->nova_ListNode_data;
	this->prv->nova_Stack_top = this->prv->nova_Stack_top->nova_ListNode_next;
	return nova_1_data;
}

char nova_Stack_isEmpty(Stack* this, ExceptionData* exceptionData)
{
	return this->prv->nova_Stack_top == (ListNode*)nova_null;
}

void nova_Stack_this(Stack* this, ExceptionData* exceptionData)
{
}

void nova_Stack_super(Stack* this, ExceptionData* exceptionData)
{
	this->prv->nova_Stack_top = (ListNode*)nova_null;
}
