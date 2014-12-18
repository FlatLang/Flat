#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_ArrayList.h>


nova_VTable_nova_standard_datastruct_Nova_ArrayList nova_VTable_nova_standard_datastruct_Nova_ArrayList_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
CCLASS_PRIVATE
(
	int nova_standard_datastruct_Nova_ArrayList_Nova_bufferSize;
	nova_standard_Nova_Object** nova_standard_datastruct_Nova_ArrayList_Nova_data;
	
)

void nova_standard_datastruct_Nova_ArrayList_Nova_shiftRight(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_left, int l0_Nova_right);
void nova_standard_datastruct_Nova_ArrayList_Nova_shiftLeft(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_left, int l0_Nova_right);
void nova_standard_datastruct_Nova_ArrayList_0_Nova_increaseSize(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_datastruct_Nova_ArrayList_1_Nova_increaseSize(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_size);

void nova_standard_datastruct_Nova_ArrayListNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_ArrayList* nova_standard_datastruct_Nova_ArrayList_2_Nova_construct(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_ArrayList, this);
	this->vtable = &nova_VTable_nova_standard_datastruct_Nova_ArrayList_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_ArrayList_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_ArrayList_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_standard_datastruct_Nova_ArrayList* nova_standard_datastruct_Nova_ArrayList_3_Nova_construct(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_size)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_ArrayList, this);
	this->vtable = &nova_VTable_nova_standard_datastruct_Nova_ArrayList_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_ArrayList_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_ArrayList_3_Nova_this(this, exceptionData, l0_Nova_size);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_ArrayList_Nova_destroy(nova_standard_datastruct_Nova_ArrayList** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE((*this)->prv);
	
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_ArrayList_2_Nova_this(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_Nova_ArrayList_3_Nova_this(this, exceptionData, 10);
}

void nova_standard_datastruct_Nova_ArrayList_3_Nova_this(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_size)
{
	nova_standard_datastruct_Nova_ArrayList_1_Nova_increaseSize(this, exceptionData, l0_Nova_size);
}

void nova_standard_datastruct_Nova_ArrayList_0_Nova_add(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_element)
{
	if (this->nova_standard_datastruct_Nova_ArrayList_Nova_size >= this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_bufferSize)
	{
		nova_standard_datastruct_Nova_ArrayList_0_Nova_increaseSize(this, exceptionData);
	}
	this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[this->nova_standard_datastruct_Nova_ArrayList_Nova_size++] = l0_Nova_element;
}

void nova_standard_datastruct_Nova_ArrayList_1_Nova_add(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_index, nova_standard_Nova_Object* l0_Nova_element)
{
	if (l0_Nova_index >= this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_bufferSize)
	{
		nova_standard_datastruct_Nova_ArrayList_1_Nova_increaseSize(this, exceptionData, l0_Nova_index + 1);
	}
	nova_standard_datastruct_Nova_ArrayList_0_Nova_add(this, exceptionData, (nova_standard_Nova_Object*)nova_null);
	nova_standard_datastruct_Nova_ArrayList_Nova_shiftRight(this, exceptionData, l0_Nova_index, this->nova_standard_datastruct_Nova_ArrayList_Nova_size);
	this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l0_Nova_index] = l0_Nova_element;
}

nova_standard_Nova_Object* nova_standard_datastruct_Nova_ArrayList_Nova_remove(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_index)
{
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_element = (nova_standard_Nova_Object*)(this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l0_Nova_index]);
	nova_standard_datastruct_Nova_ArrayList_Nova_shiftLeft(this, exceptionData, l0_Nova_index + 1, this->nova_standard_datastruct_Nova_ArrayList_Nova_size--);
	return (nova_standard_Nova_Object*)l1_Nova_element;
}

void nova_standard_datastruct_Nova_ArrayList_Nova_shiftRight(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_left, int l0_Nova_right)
{
	int l1_Nova_i;
	
	l1_Nova_i = l0_Nova_right - 1;
	for (; l1_Nova_i > l0_Nova_left; l1_Nova_i--)
	{
		this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l1_Nova_i] = this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l1_Nova_i - 1];
	}
	this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l0_Nova_left] = (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null);
}

void nova_standard_datastruct_Nova_ArrayList_Nova_shiftLeft(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_left, int l0_Nova_right)
{
	int l1_Nova_i;
	
	l1_Nova_i = l0_Nova_left - 1;
	for (; l1_Nova_i < l0_Nova_right - 1; l1_Nova_i++)
	{
		this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l1_Nova_i] = this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l1_Nova_i + 1];
	}
	this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l0_Nova_right - 1] = (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null);
}

void nova_standard_datastruct_Nova_ArrayList_Nova_swap(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_index1, int l0_Nova_index2)
{
	nova_standard_Nova_Object* l1_Nova_temp;
	
	l1_Nova_temp = (nova_standard_Nova_Object*)(this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l0_Nova_index1]);
	this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l0_Nova_index1] = this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l0_Nova_index2];
	this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l0_Nova_index2] = (nova_standard_Nova_Object*)(l1_Nova_temp);
}

void nova_standard_datastruct_Nova_ArrayList_0_Nova_increaseSize(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_Nova_ArrayList_1_Nova_increaseSize(this, exceptionData, this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_bufferSize + 3);
}

void nova_standard_datastruct_Nova_ArrayList_1_Nova_increaseSize(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_size)
{
	nova_standard_Nova_Object** l1_Nova_tmp;
	
	this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_bufferSize = l0_Nova_size;
	l1_Nova_tmp = (nova_standard_Nova_Object**)NOVA_MALLOC(sizeof(nova_standard_Nova_Object) * this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_bufferSize);
	arrayCopy(l1_Nova_tmp, (int)(0), this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data, (int)(0), (int)(this->nova_standard_datastruct_Nova_ArrayList_Nova_size), (int)(this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_bufferSize), (int)(4));
	this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data = (nova_standard_Nova_Object**)(l1_Nova_tmp);
}

nova_standard_Nova_Object* nova_standard_datastruct_Nova_ArrayList_Nova_get(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_index)
{
	return this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l0_Nova_index];
}

void nova_standard_datastruct_Nova_ArrayList_Nova_set(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_index, nova_standard_Nova_Object* l0_Nova_value)
{
	this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l0_Nova_index] = l0_Nova_value;
}

nova_standard_Nova_Object** nova_standard_datastruct_Nova_ArrayList_Nova_toArray(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_Object** l1_Nova_array;
	int l1_Nova_i;
	
	l1_Nova_array = (nova_standard_Nova_Object**)NOVA_MALLOC(sizeof(nova_standard_Nova_Object) * this->nova_standard_datastruct_Nova_ArrayList_Nova_size);
	l1_Nova_i = 0;
	for (; l1_Nova_i < this->nova_standard_datastruct_Nova_ArrayList_Nova_size; l1_Nova_i++)
	{
		l1_Nova_array[l1_Nova_i] = (nova_standard_Nova_Object*)(this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data[l1_Nova_i]);
	}
}

char nova_standard_datastruct_Nova_ArrayList_Accessor_Nova_empty(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->nova_standard_datastruct_Nova_ArrayList_Nova_size <= 0;
}


void nova_standard_datastruct_Nova_ArrayList_Nova_super(nova_standard_datastruct_Nova_ArrayList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_ArrayList_Nova_size = 0;
	this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_bufferSize = 0;
	this->prv->nova_standard_datastruct_Nova_ArrayList_Nova_data = (nova_standard_Nova_Object**)nova_null;
}

