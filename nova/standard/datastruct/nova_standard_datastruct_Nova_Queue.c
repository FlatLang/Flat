#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Queue.h>


nova_standard_datastruct_Extension_VTable_Queue nova_standard_datastruct_Extension_VTable_Queue_val =
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_datastruct_Nova_Queue_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	nova_standard_datastruct_Nova_ArrayList* nova_standard_datastruct_Nova_Queue_Nova_data;
	
)


void nova_standard_datastruct_Nova_QueueNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Queue* nova_standard_datastruct_Nova_Queue_2_Nova_construct(nova_standard_datastruct_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Queue, this);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Queue_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_Queue_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Queue_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_Queue_Nova_destroy(nova_standard_datastruct_Nova_Queue** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_Nova_ArrayList_Nova_destroy(&(*this)->prv->nova_standard_datastruct_Nova_Queue_Nova_data, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_Queue_2_Nova_this(nova_standard_datastruct_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_Nova_Queue_Nova_data = nova_standard_datastruct_Nova_ArrayList_2_Nova_construct(0, exceptionData);
}

nova_standard_Nova_Object* nova_standard_datastruct_Nova_Queue_Nova_dequeue(nova_standard_datastruct_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_Nova_ArrayList_Nova_remove(this->prv->nova_standard_datastruct_Nova_Queue_Nova_data, exceptionData, 0);
}

void nova_standard_datastruct_Nova_Queue_Nova_enqueue(nova_standard_datastruct_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_element)
{
	nova_standard_datastruct_Nova_ArrayList_0_Nova_add(this->prv->nova_standard_datastruct_Nova_Queue_Nova_data, exceptionData, l0_Nova_element);
}

char nova_standard_datastruct_Nova_Queue_Nova_isEmpty(nova_standard_datastruct_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char)nova_standard_datastruct_Nova_Queue_Accessor_Nova_size(this, exceptionData) <= 0;
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Queue_0_Nova_toString(nova_standard_datastruct_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_s;
	int l1_Nova_i;
	
	l1_Nova_s = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "");
	l1_Nova_i = nova_standard_datastruct_Nova_Queue_Accessor_Nova_size(this, exceptionData) - 1;
	for (; l1_Nova_i >= 0; l1_Nova_i--)
	{
		nova_standard_Nova_Object* nova_local_0;
		
		if (l1_Nova_i < nova_standard_datastruct_Nova_Queue_Accessor_Nova_size(this, exceptionData) - 1)
		{
			l1_Nova_s = l1_Nova_s->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_s, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", "));
		}
		nova_local_0 = (nova_standard_Nova_Object*)(nova_standard_datastruct_Nova_ArrayList_Nova_get(this->prv->nova_standard_datastruct_Nova_Queue_Nova_data, exceptionData, l1_Nova_i));
		l1_Nova_s = l1_Nova_s->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_s, exceptionData, nova_local_0->vtable->nova_standard_Nova_Object_virtual0_Nova_toString(nova_local_0, exceptionData));
	}
	return l1_Nova_s;
}

int nova_standard_datastruct_Nova_Queue_Accessor_Nova_size(nova_standard_datastruct_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_standard_datastruct_Nova_Queue_Nova_data->nova_standard_datastruct_Nova_ArrayList_Nova_size;
}


void nova_standard_datastruct_Nova_Queue_Nova_super(nova_standard_datastruct_Nova_Queue* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_Nova_Queue_Nova_data = (nova_standard_datastruct_Nova_ArrayList*)nova_null;
}

