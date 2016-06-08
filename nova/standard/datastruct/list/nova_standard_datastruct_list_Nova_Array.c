#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_Array.h>

nova_standard_datastruct_list_Extension_VTable_Array nova_standard_datastruct_list_Extension_VTable_Array_val =
{
	{
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		(void(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure3_Nova_func nova_standard_datastruct_list_Nova_List_Nova_func, void* nova_standard_datastruct_list_Nova_List_ref_Nova_func))nova_standard_datastruct_list_Nova_Array_Nova_forEach,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure6_Nova_filterFunc nova_standard_datastruct_list_Nova_List_Nova_filterFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_filterFunc))nova_standard_datastruct_list_Nova_Array_Nova_filter,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, int))nova_standard_datastruct_list_Nova_Array_Nova_take,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, int))nova_standard_datastruct_list_Nova_Array_Nova_skip,
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
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_datastruct_list_Nova_Array_Nova_forEach,
	nova_standard_datastruct_list_Nova_Array_Nova_filter,
	nova_standard_datastruct_list_Nova_Array_Nova_take,
	nova_standard_datastruct_list_Nova_Array_Nova_skip,
};


CCLASS_PRIVATE
(
	int nova_standard_datastruct_list_Nova_Array_Nova_bufferSize;
	nova_standard_Nova_Object** nova_standard_datastruct_list_Nova_Array_Nova_data;
	
)

void nova_standard_datastruct_list_Nova_Array_Nova_shiftRight(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_left, int nova_standard_datastruct_list_Nova_Array_Nova_right);
void nova_standard_datastruct_list_Nova_Array_Nova_shiftLeft(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_left, int nova_standard_datastruct_list_Nova_Array_Nova_right);
void nova_standard_datastruct_list_Nova_Array_0_Nova_increaseSize(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_size);


void nova_standard_datastruct_list_Nova_ArrayNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_2_Nova_construct(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_Array, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_Array_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_Array_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_3_Nova_construct(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_size)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_Array, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_Array_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_Array_3_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_size);
	}
	
	return this;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_4_Nova_construct(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object** nova_standard_datastruct_list_Nova_Array_Nova_data, int nova_standard_datastruct_list_Nova_Array_Nova_size)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_Array, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_Array_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_Array_4_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_data, nova_standard_datastruct_list_Nova_Array_Nova_size);
	}
	
	return this;
}

void nova_standard_datastruct_list_Nova_Array_Nova_destroy(nova_standard_datastruct_list_Nova_Array** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE((*this)->prv);
	
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_list_Nova_Array_2_Nova_this(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array_3_Nova_this(this, exceptionData, 10);
}

void nova_standard_datastruct_list_Nova_Array_3_Nova_this(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_size)
{
	nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_size);
}

void nova_standard_datastruct_list_Nova_Array_4_Nova_this(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object** nova_standard_datastruct_list_Nova_Array_Nova_data, int nova_standard_datastruct_list_Nova_Array_Nova_size)
{
	int l3_Nova_i;
	
	nova_standard_datastruct_list_Nova_Array_3_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_size);
	l3_Nova_i = 0;
	for (; l3_Nova_i < nova_standard_datastruct_list_Nova_Array_Nova_size; l3_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_data[l3_Nova_i]);
	}
}

void nova_standard_datastruct_list_Nova_Array_0_Nova_add(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_element)
{
	if (this->nova_standard_datastruct_list_Nova_Array_Nova_size >= this->prv->nova_standard_datastruct_list_Nova_Array_Nova_bufferSize)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_increaseSize(this, exceptionData);
	}
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[this->nova_standard_datastruct_list_Nova_Array_Nova_size++] = nova_standard_datastruct_list_Nova_Array_Nova_element;
}

void nova_standard_datastruct_list_Nova_Array_1_Nova_add(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_element)
{
	if (nova_standard_datastruct_list_Nova_Array_Nova_index >= this->prv->nova_standard_datastruct_list_Nova_Array_Nova_bufferSize)
	{
		nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_index + 1);
	}
	nova_standard_datastruct_list_Nova_Array_0_Nova_add(this, exceptionData, (nova_standard_Nova_Object*)nova_null);
	nova_standard_datastruct_list_Nova_Array_Nova_shiftRight(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_index, this->nova_standard_datastruct_list_Nova_Array_Nova_size);
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index] = nova_standard_datastruct_list_Nova_Array_Nova_element;
	if (nova_standard_datastruct_list_Nova_Array_Nova_index >= this->nova_standard_datastruct_list_Nova_Array_Nova_size - 1)
	{
		this->nova_standard_datastruct_list_Nova_Array_Nova_size = nova_standard_datastruct_list_Nova_Array_Nova_index + 1;
	}
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_remove(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index)
{
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_element = this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index];
	nova_standard_datastruct_list_Nova_Array_Nova_shiftLeft(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_index + 1, this->nova_standard_datastruct_list_Nova_Array_Nova_size--);
	return l1_Nova_element;
}

void nova_standard_datastruct_list_Nova_Array_Nova_shiftRight(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_left, int nova_standard_datastruct_list_Nova_Array_Nova_right)
{
	int l1_Nova_i;
	
	l1_Nova_i = nova_standard_datastruct_list_Nova_Array_Nova_right - 1;
	for (; l1_Nova_i > nova_standard_datastruct_list_Nova_Array_Nova_left; l1_Nova_i--)
	{
		this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l1_Nova_i] = this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l1_Nova_i - 1];
	}
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_left] = (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null);
}

void nova_standard_datastruct_list_Nova_Array_Nova_shiftLeft(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_left, int nova_standard_datastruct_list_Nova_Array_Nova_right)
{
	int l1_Nova_i;
	
	l1_Nova_i = nova_standard_datastruct_list_Nova_Array_Nova_left - 1;
	for (; l1_Nova_i < nova_standard_datastruct_list_Nova_Array_Nova_right - 1; l1_Nova_i++)
	{
		this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l1_Nova_i] = this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l1_Nova_i + 1];
	}
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_right - 1] = (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null);
}

void nova_standard_datastruct_list_Nova_Array_Nova_swap(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index1, int nova_standard_datastruct_list_Nova_Array_Nova_index2)
{
	nova_standard_Nova_Object* l1_Nova_temp;
	
	l1_Nova_temp = this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index1];
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index1] = this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index2];
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index2] = l1_Nova_temp;
}

void nova_standard_datastruct_list_Nova_Array_0_Nova_increaseSize(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(this, exceptionData, this->prv->nova_standard_datastruct_list_Nova_Array_Nova_bufferSize + 3);
}

void nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_size)
{
	int l1_Nova_offset;
	nova_standard_Nova_Object** l1_Nova_tmp;
	int l1_Nova_i;
	
	l1_Nova_offset = nova_standard_datastruct_list_Nova_Array_Nova_size - this->prv->nova_standard_datastruct_list_Nova_Array_Nova_bufferSize;
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_bufferSize = nova_standard_datastruct_list_Nova_Array_Nova_size;
	l1_Nova_tmp = (nova_standard_Nova_Object**)((nova_standard_Nova_Object**)NOVA_MALLOC(sizeof(nova_standard_Nova_Object) * this->prv->nova_standard_datastruct_list_Nova_Array_Nova_bufferSize));
	arrayCopy(l1_Nova_tmp, (int)(0), this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data, (int)(0), (int)(this->nova_standard_datastruct_list_Nova_Array_Nova_size), (int)(this->prv->nova_standard_datastruct_list_Nova_Array_Nova_bufferSize), (int)(4));
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data = l1_Nova_tmp;
	l1_Nova_i = this->prv->nova_standard_datastruct_list_Nova_Array_Nova_bufferSize - l1_Nova_offset;
	for (; l1_Nova_i < this->prv->nova_standard_datastruct_list_Nova_Array_Nova_bufferSize; l1_Nova_i++)
	{
		this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l1_Nova_i] = (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null);
	}
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_get(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index)
{
	return this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index];
}

void nova_standard_datastruct_list_Nova_Array_Nova_set(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_value)
{
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index] = nova_standard_datastruct_list_Nova_Array_Nova_value;
}

nova_standard_Nova_Object** nova_standard_datastruct_list_Nova_Array_Nova_toArray(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_Object** l1_Nova_array;
	int l1_Nova_i;
	
	l1_Nova_array = (nova_standard_Nova_Object**)((nova_standard_Nova_Object**)NOVA_MALLOC(sizeof(nova_standard_Nova_Object) * this->nova_standard_datastruct_list_Nova_Array_Nova_size));
	l1_Nova_i = 0;
	for (; l1_Nova_i < this->nova_standard_datastruct_list_Nova_Array_Nova_size; l1_Nova_i++)
	{
		l1_Nova_array[l1_Nova_i] = this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l1_Nova_i];
	}
}

void nova_standard_datastruct_list_Nova_Array_Nova_forEach(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure3_Nova_func nova_standard_datastruct_list_Nova_Array_Nova_func, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_func)
{
	int l1_Nova_i;
	
	l1_Nova_i = 0;
	for (; l1_Nova_i < this->nova_standard_datastruct_list_Nova_Array_Nova_size; l1_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_Nova_func(nova_standard_datastruct_list_Nova_Array_ref_Nova_func, exceptionData, this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l1_Nova_i], l1_Nova_i, this);
	}
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_Nova_filter(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure6_Nova_filterFunc nova_standard_datastruct_list_Nova_Array_Nova_filterFunc, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_filterFunc)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_filtered;
	int l1_Nova_i;
	
	l1_Nova_filtered = nova_standard_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData);
	l1_Nova_i = 0;
	for (; l1_Nova_i < this->nova_standard_datastruct_list_Nova_Array_Nova_size; l1_Nova_i++)
	{
		if (nova_standard_datastruct_list_Nova_Array_Nova_filterFunc(nova_standard_datastruct_list_Nova_Array_ref_Nova_filterFunc, exceptionData, this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l1_Nova_i]))
		{
			nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_filtered, exceptionData, this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l1_Nova_i]);
		}
	}
	return l1_Nova_filtered;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_Nova_take(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_howMany)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list;
	int l2_Nova_i;
	
	if (nova_standard_datastruct_list_Nova_Array_Nova_howMany > this->nova_standard_datastruct_list_Nova_Array_Nova_size)
	{
		nova_standard_datastruct_list_Nova_Array_Nova_howMany = this->nova_standard_datastruct_list_Nova_Array_Nova_size;
	}
	l1_Nova_list = nova_standard_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData);
	l2_Nova_i = 0;
	for (; l2_Nova_i < nova_standard_datastruct_list_Nova_Array_Nova_howMany; l2_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i]);
	}
	return l1_Nova_list;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_Nova_skip(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_howMany)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list;
	int l1_Nova_i;
	
	l1_Nova_list = nova_standard_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData);
	l1_Nova_i = nova_standard_datastruct_list_Nova_Array_Nova_howMany;
	for (; l1_Nova_i < this->nova_standard_datastruct_list_Nova_Array_Nova_size; l1_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l1_Nova_i]);
	}
	return l1_Nova_list;
}

char nova_standard_datastruct_list_Nova_Array_Accessor_Nova_empty(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->nova_standard_datastruct_list_Nova_Array_Nova_size <= 0;
}


nova_standard_datastruct_list_Nova_ArrayIterator* nova_standard_datastruct_list_Nova_Array_Accessor_Nova_iterator(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_list_Nova_ArrayIterator_Nova_construct(0, exceptionData, this);
}


void nova_standard_datastruct_list_Nova_Array_Nova_super(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_list_Nova_Array_Nova_size = 0;
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_bufferSize = 0;
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data = (nova_standard_Nova_Object**)nova_null;
}

