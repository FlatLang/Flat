#include <precompiled.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Queue.h>



nova_datastruct_list_Extension_VTable_Queue nova_datastruct_list_Extension_VTable_Queue_val =
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
	nova_datastruct_list_Nova_Queue_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


CCLASS_PRIVATE
(
	nova_datastruct_list_Nova_Array* nova_datastruct_list_Nova_Queue_Nova_data;
	
)



void nova_datastruct_list_Nova_Queue_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_list_Nova_Queue* nova_datastruct_list_Nova_Queue_0_Nova_construct(nova_datastruct_list_Nova_Queue* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_datastruct_list_Nova_Queue, this);
	this->vtable = &nova_datastruct_list_Extension_VTable_Queue_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_list_Nova_Queue_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_list_Nova_Queue_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_datastruct_list_Nova_Queue* nova_datastruct_list_Nova_Queue_1_Nova_construct(nova_datastruct_list_Nova_Queue* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* nova_datastruct_list_Nova_Queue_Nova_data)
{
	CCLASS_NEW(nova_datastruct_list_Nova_Queue, this);
	this->vtable = &nova_datastruct_list_Extension_VTable_Queue_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_list_Nova_Queue_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_list_Nova_Queue_1_Nova_this(this, exceptionData, nova_datastruct_list_Nova_Queue_Nova_data);
	}
	
	return this;
}

void nova_datastruct_list_Nova_Queue_Nova_destroy(nova_datastruct_list_Nova_Queue** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_datastruct_list_Nova_Array_Nova_destroy(&(*this)->prv->nova_datastruct_list_Nova_Queue_Nova_data, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_datastruct_list_Nova_Queue_0_Nova_this(nova_datastruct_list_Nova_Queue* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_datastruct_list_Nova_Queue_Nova_data = nova_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData);
}

void nova_datastruct_list_Nova_Queue_1_Nova_this(nova_datastruct_list_Nova_Queue* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* nova_datastruct_list_Nova_Queue_Nova_data)
{
	this->prv->nova_datastruct_list_Nova_Queue_Nova_data = nova_datastruct_list_Nova_Queue_Nova_data;
}

nova_Nova_Object* nova_datastruct_list_Nova_Queue_Nova_dequeue(nova_datastruct_list_Nova_Queue* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_Nova_Object*)nova_datastruct_list_Nova_Array_Nova_remove(this->prv->nova_datastruct_list_Nova_Queue_Nova_data, exceptionData, 0);
}

void nova_datastruct_list_Nova_Queue_Nova_enqueue(nova_datastruct_list_Nova_Queue* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_list_Nova_Queue_Nova_element)
{
	nova_datastruct_list_Nova_Array_0_Nova_add(this->prv->nova_datastruct_list_Nova_Queue_Nova_data, exceptionData, nova_datastruct_list_Nova_Queue_Nova_element);
}

nova_Nova_String* nova_datastruct_list_Nova_Queue_0_Nova_toString(nova_datastruct_list_Nova_Queue* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_Nova_String* l1_Nova_s = (nova_Nova_String*)nova_null;
	int l1_Nova_i = 0;
	
	l1_Nova_s = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""));
	l1_Nova_i = nova_datastruct_list_Nova_Queue_Accessor_Nova_size(this, exceptionData) - 1;
	while (l1_Nova_i >= 0)
	{
		if (l1_Nova_i < nova_datastruct_list_Nova_Queue_Accessor_Nova_size(this, exceptionData) - 1)
		{
			l1_Nova_s = (nova_Nova_String*)(nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(l1_Nova_s), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", "))));
		}
		l1_Nova_s = (nova_Nova_String*)(nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(l1_Nova_s), exceptionData, nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(this->prv->nova_datastruct_list_Nova_Queue_Nova_data), exceptionData, l1_Nova_i--)), exceptionData)));
	}
	return l1_Nova_s;
}

int nova_datastruct_list_Nova_Queue_Accessor_Nova_size(nova_datastruct_list_Nova_Queue* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_datastruct_list_Nova_Queue_Nova_data->nova_datastruct_list_Nova_Array_Nova_position;
}


char nova_datastruct_list_Nova_Queue_Accessor_Nova_empty(nova_datastruct_list_Nova_Queue* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (char)nova_datastruct_list_Nova_Queue_Accessor_Nova_size(this, exceptionData) <= 0;
}


void nova_datastruct_list_Nova_Queue_Nova_super(nova_datastruct_list_Nova_Queue* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_datastruct_list_Nova_Queue_Nova_data = (nova_datastruct_list_Nova_Array*)nova_null;
}

