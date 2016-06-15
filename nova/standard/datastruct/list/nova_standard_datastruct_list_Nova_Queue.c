#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_Queue.h>

nova_standard_datastruct_list_Extension_VTable_Queue nova_standard_datastruct_list_Extension_VTable_Queue_val =
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
	nova_standard_datastruct_list_Nova_Queue_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Queue_Nova_data;
	
)



void nova_standard_datastruct_list_Nova_QueueNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_list_Nova_Queue* nova_standard_datastruct_list_Nova_Queue_0_Nova_construct(nova_standard_datastruct_list_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_Queue, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_Queue_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Queue_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_Queue_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_list_Nova_Queue_Nova_destroy(nova_standard_datastruct_list_Nova_Queue** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_list_Nova_Array_Nova_destroy(&(*this)->prv->nova_standard_datastruct_list_Nova_Queue_Nova_data, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_list_Nova_Queue_0_Nova_this(nova_standard_datastruct_list_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_list_Nova_Queue_Nova_data = nova_standard_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData);
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Queue_Nova_dequeue(nova_standard_datastruct_list_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_list_Nova_Array_Nova_remove(this->prv->nova_standard_datastruct_list_Nova_Queue_Nova_data, exceptionData, 0);
}

void nova_standard_datastruct_list_Nova_Queue_Nova_enqueue(nova_standard_datastruct_list_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Queue_Nova_element)
{
	nova_standard_datastruct_list_Nova_Array_0_Nova_add(this->prv->nova_standard_datastruct_list_Nova_Queue_Nova_data, exceptionData, nova_standard_datastruct_list_Nova_Queue_Nova_element);
}

nova_standard_Nova_String* nova_standard_datastruct_list_Nova_Queue_1_Nova_toString(nova_standard_datastruct_list_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_s;
	int l2_Nova_i;
	
	l1_Nova_s = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "");
	l2_Nova_i = nova_standard_datastruct_list_Nova_Queue_Accessor_Nova_size(this, exceptionData) - 1;
	for (; l2_Nova_i >= 0; l2_Nova_i--)
	{
		nova_standard_Nova_Object* nova_local_0;
		
		if (l2_Nova_i < nova_standard_datastruct_list_Nova_Queue_Accessor_Nova_size(this, exceptionData) - 1)
		{
			l1_Nova_s = l1_Nova_s->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_s, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "));
		}
		nova_local_0 = nova_standard_datastruct_list_Nova_Array_Nova_get(this->prv->nova_standard_datastruct_list_Nova_Queue_Nova_data, exceptionData, l2_Nova_i);
		l1_Nova_s = l1_Nova_s->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_s, exceptionData, ((nova_standard_Nova_Object*)nova_local_0)->vtable->nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)(nova_local_0), exceptionData));
	}
	return l1_Nova_s;
}

int nova_standard_datastruct_list_Nova_Queue_Accessor_Nova_size(nova_standard_datastruct_list_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_standard_datastruct_list_Nova_Queue_Nova_data->nova_standard_datastruct_list_Nova_Array_Nova_size;
}


char nova_standard_datastruct_list_Nova_Queue_Accessor_Nova_empty(nova_standard_datastruct_list_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char)nova_standard_datastruct_list_Nova_Queue_Accessor_Nova_size(this, exceptionData) <= 0;
}


void nova_standard_datastruct_list_Nova_Queue_Nova_super(nova_standard_datastruct_list_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_list_Nova_Queue_Nova_data = (nova_standard_datastruct_list_Nova_Array*)nova_null;
}

