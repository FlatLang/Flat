#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_Array.h>

nova_standard_datastruct_list_Extension_VTable_Array nova_standard_datastruct_list_Extension_VTable_Array_val =
{
	{
		0,
		(nova_standard_datastruct_list_Nova_Iterator*(*)(nova_standard_datastruct_list_Nova_Iterable*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_Array_Accessor_Nova_iterator,
		0,
		0,
		0,
		(void(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure3_Nova_func nova_standard_datastruct_list_Nova_List_Nova_func, void* nova_standard_datastruct_list_Nova_List_ref_Nova_func))nova_standard_datastruct_list_Nova_Array_Nova_forEach,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc nova_standard_datastruct_list_Nova_List_Nova_mapFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_mapFunc))nova_standard_datastruct_list_Nova_Array_Nova_map,
		(char(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure9_Nova_anyFunc nova_standard_datastruct_list_Nova_List_Nova_anyFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_anyFunc))nova_standard_datastruct_list_Nova_Array_Nova_any,
		(char(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure12_Nova_allFunc nova_standard_datastruct_list_Nova_List_Nova_allFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_allFunc))nova_standard_datastruct_list_Nova_Array_Nova_all,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc nova_standard_datastruct_list_Nova_List_Nova_filterFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_filterFunc))nova_standard_datastruct_list_Nova_Array_Nova_filter,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, int))nova_standard_datastruct_list_Nova_Array_Nova_take,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, int))nova_standard_datastruct_list_Nova_Array_Nova_skip,
		(nova_standard_Nova_Object*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_Array_Nova_first,
		(nova_standard_Nova_Object*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_Array_Nova_last,
		(nova_standard_Nova_Object*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure18_Nova_func nova_standard_datastruct_list_Nova_List_Nova_func, void* nova_standard_datastruct_list_Nova_List_ref_Nova_func))nova_standard_datastruct_list_Nova_Array_Nova_firstWhere,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_Array_Nova_reverse,
		(nova_standard_Nova_String*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_String*))nova_standard_datastruct_list_Nova_Array_Nova_join,
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
	nova_standard_datastruct_list_Nova_Array_Nova_map,
	nova_standard_datastruct_list_Nova_Array_Nova_forEach,
	nova_standard_datastruct_list_Nova_Array_Nova_any,
	nova_standard_datastruct_list_Nova_Array_Nova_all,
	nova_standard_datastruct_list_Nova_Array_Nova_filter,
	nova_standard_datastruct_list_Nova_Array_Nova_take,
	nova_standard_datastruct_list_Nova_Array_Nova_skip,
	nova_standard_datastruct_list_Nova_Array_Nova_first,
	nova_standard_datastruct_list_Nova_Array_Nova_last,
	nova_standard_datastruct_list_Nova_Array_Nova_firstWhere,
	nova_standard_datastruct_list_Nova_Array_Nova_reverse,
	nova_standard_datastruct_list_Nova_Array_Nova_join,
	nova_standard_datastruct_list_Nova_Array_Accessor_Nova_iterator,
};


CCLASS_PRIVATE
(
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

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_0_Nova_construct(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_Array, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_Array_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_1_Nova_construct(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_size)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_Array, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_Array_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_Array_1_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_size);
	}
	
	return this;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_2_Nova_construct(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object** nova_standard_datastruct_list_Nova_Array_Nova_data, int nova_standard_datastruct_list_Nova_Array_Nova_size)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_Array, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_Array_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_Array_2_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_data, nova_standard_datastruct_list_Nova_Array_Nova_size);
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
	
	
	
	nova_standard_Nova_String_Nova_destroy(&(*this)->nova_standard_datastruct_list_Nova_Array_Nova_stringsOnly, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_list_Nova_Array_0_Nova_this(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array_1_Nova_this(this, exceptionData, 10);
}

void nova_standard_datastruct_list_Nova_Array_1_Nova_this(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_size)
{
	this->nova_standard_datastruct_list_Nova_Array_Nova_size = 0;
	this->nova_standard_datastruct_list_Nova_Array_Nova_position = 0;
	this->nova_standard_datastruct_list_Nova_Array_Nova_capacity = 0;
	nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_size);
}

void nova_standard_datastruct_list_Nova_Array_2_Nova_this(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object** nova_standard_datastruct_list_Nova_Array_Nova_data, int nova_standard_datastruct_list_Nova_Array_Nova_size)
{
	int l4_Nova_i;
	
	nova_standard_datastruct_list_Nova_Array_1_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_size);
	l4_Nova_i = 0;
	for (; l4_Nova_i < nova_standard_datastruct_list_Nova_Array_Nova_size; l4_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_data[l4_Nova_i]);
	}
}

void nova_standard_datastruct_list_Nova_Array_0_Nova_add(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_element)
{
	if (this->nova_standard_datastruct_list_Nova_Array_Nova_position >= this->nova_standard_datastruct_list_Nova_Array_Nova_capacity)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_increaseSize(this, exceptionData);
	}
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[this->nova_standard_datastruct_list_Nova_Array_Nova_position++] = nova_standard_datastruct_list_Nova_Array_Nova_element;
	this->nova_standard_datastruct_list_Nova_Array_Nova_size = (int)(nova_standard_math_Nova_Math_Nova_max(0, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_position, this->nova_standard_datastruct_list_Nova_Array_Nova_size));
}

void nova_standard_datastruct_list_Nova_Array_1_Nova_add(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_element)
{
	if (nova_standard_datastruct_list_Nova_Array_Nova_index >= this->nova_standard_datastruct_list_Nova_Array_Nova_capacity)
	{
		nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_index + 1);
	}
	nova_standard_datastruct_list_Nova_Array_0_Nova_add(this, exceptionData, (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null));
	nova_standard_datastruct_list_Nova_Array_Nova_shiftRight(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_index, this->nova_standard_datastruct_list_Nova_Array_Nova_position);
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index] = nova_standard_datastruct_list_Nova_Array_Nova_element;
	if (nova_standard_datastruct_list_Nova_Array_Nova_index >= this->nova_standard_datastruct_list_Nova_Array_Nova_position - 1)
	{
		this->nova_standard_datastruct_list_Nova_Array_Nova_position = nova_standard_datastruct_list_Nova_Array_Nova_index + 1;
	}
	this->nova_standard_datastruct_list_Nova_Array_Nova_size = (int)(nova_standard_math_Nova_Math_Nova_max(0, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_position, this->nova_standard_datastruct_list_Nova_Array_Nova_size));
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_remove(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index)
{
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_element = this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index];
	nova_standard_datastruct_list_Nova_Array_Nova_shiftLeft(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_index + 1, this->nova_standard_datastruct_list_Nova_Array_Nova_position--);
	return l1_Nova_element;
}

void nova_standard_datastruct_list_Nova_Array_Nova_shiftRight(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_left, int nova_standard_datastruct_list_Nova_Array_Nova_right)
{
	int l2_Nova_i;
	
	l2_Nova_i = nova_standard_datastruct_list_Nova_Array_Nova_right - 1;
	for (; l2_Nova_i > nova_standard_datastruct_list_Nova_Array_Nova_left; l2_Nova_i--)
	{
		this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i] = this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i - 1];
	}
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_left] = (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null);
}

void nova_standard_datastruct_list_Nova_Array_Nova_shiftLeft(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_left, int nova_standard_datastruct_list_Nova_Array_Nova_right)
{
	int l2_Nova_i;
	
	l2_Nova_i = nova_standard_datastruct_list_Nova_Array_Nova_left - 1;
	for (; l2_Nova_i < nova_standard_datastruct_list_Nova_Array_Nova_right - 1; l2_Nova_i++)
	{
		this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i] = this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i + 1];
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
	nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(this, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_capacity + 3);
}

void nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_size)
{
	int l1_Nova_offset;
	nova_standard_Nova_Object** l1_Nova_tmp;
	int l2_Nova_i;
	
	l1_Nova_offset = nova_standard_datastruct_list_Nova_Array_Nova_size - this->nova_standard_datastruct_list_Nova_Array_Nova_capacity;
	this->nova_standard_datastruct_list_Nova_Array_Nova_capacity = nova_standard_datastruct_list_Nova_Array_Nova_size;
	l1_Nova_tmp = (nova_standard_Nova_Object**)((nova_standard_Nova_Object**)NOVA_MALLOC(sizeof(nova_standard_Nova_Object) * this->nova_standard_datastruct_list_Nova_Array_Nova_capacity));
	arrayCopy(l1_Nova_tmp, 0, this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data, 0, (int)(this->nova_standard_datastruct_list_Nova_Array_Nova_size), this->nova_standard_datastruct_list_Nova_Array_Nova_capacity, 4);
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data = l1_Nova_tmp;
	l2_Nova_i = this->nova_standard_datastruct_list_Nova_Array_Nova_capacity - l1_Nova_offset;
	for (; l2_Nova_i < this->nova_standard_datastruct_list_Nova_Array_Nova_capacity; l2_Nova_i++)
	{
		this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i] = (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null);
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
	int l2_Nova_i;
	
	l1_Nova_array = (nova_standard_Nova_Object**)((nova_standard_Nova_Object**)NOVA_MALLOC(sizeof(nova_standard_Nova_Object) * this->nova_standard_datastruct_list_Nova_Array_Nova_size));
	l2_Nova_i = 0;
	for (; l2_Nova_i < this->nova_standard_datastruct_list_Nova_Array_Nova_size; l2_Nova_i++)
	{
		l1_Nova_array[l2_Nova_i] = this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i];
	}
	return l1_Nova_array;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_Nova_map(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure3_Nova_mapFunc nova_standard_datastruct_list_Nova_Array_Nova_mapFunc, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_mapFunc)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_array;
	int l1_Nova_i;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData);
	l1_Nova_i = 0;
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)(this), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_array, exceptionData, (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Array_Nova_mapFunc(nova_standard_datastruct_list_Nova_Array_ref_Nova_mapFunc, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element), l1_Nova_i++, this)));
	}
	return l1_Nova_array;
}

void nova_standard_datastruct_list_Nova_Array_Nova_forEach(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure6_Nova_func nova_standard_datastruct_list_Nova_Array_Nova_func, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_func)
{
	int l2_Nova_i;
	
	l2_Nova_i = 0;
	for (; l2_Nova_i < this->nova_standard_datastruct_list_Nova_Array_Nova_size; l2_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_Nova_func(nova_standard_datastruct_list_Nova_Array_ref_Nova_func, exceptionData, this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i], l2_Nova_i, this);
	}
}

char nova_standard_datastruct_list_Nova_Array_Nova_any(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure9_Nova_anyFunc nova_standard_datastruct_list_Nova_Array_Nova_anyFunc, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_anyFunc)
{
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)(this), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (nova_standard_datastruct_list_Nova_Array_Nova_anyFunc(nova_standard_datastruct_list_Nova_Array_ref_Nova_anyFunc, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element)))
		{
			return 1;
		}
	}
	return 0;
}

char nova_standard_datastruct_list_Nova_Array_Nova_all(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure12_Nova_allFunc nova_standard_datastruct_list_Nova_Array_Nova_allFunc, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_allFunc)
{
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)(this), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (!nova_standard_datastruct_list_Nova_Array_Nova_allFunc(nova_standard_datastruct_list_Nova_Array_ref_Nova_allFunc, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element)))
		{
			return 0;
		}
	}
	return 1;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_Nova_filter(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure15_Nova_filterFunc nova_standard_datastruct_list_Nova_Array_Nova_filterFunc, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_filterFunc)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_filtered;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_filtered = nova_standard_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData);
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)(this), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (nova_standard_datastruct_list_Nova_Array_Nova_filterFunc(nova_standard_datastruct_list_Nova_Array_ref_Nova_filterFunc, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element)))
		{
			nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_filtered, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element));
		}
	}
	return l1_Nova_filtered;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_Nova_take(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_howMany)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list;
	int l3_Nova_i;
	
	if (nova_standard_datastruct_list_Nova_Array_Nova_howMany > this->nova_standard_datastruct_list_Nova_Array_Nova_size)
	{
		nova_standard_datastruct_list_Nova_Array_Nova_howMany = this->nova_standard_datastruct_list_Nova_Array_Nova_size;
	}
	l1_Nova_list = nova_standard_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData);
	l3_Nova_i = 0;
	for (; l3_Nova_i < nova_standard_datastruct_list_Nova_Array_Nova_howMany; l3_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l3_Nova_i]);
	}
	return l1_Nova_list;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_Nova_skip(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_howMany)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list;
	int l2_Nova_i;
	
	l1_Nova_list = nova_standard_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData);
	l2_Nova_i = nova_standard_datastruct_list_Nova_Array_Nova_howMany;
	for (; l2_Nova_i < this->nova_standard_datastruct_list_Nova_Array_Nova_size; l2_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i]);
	}
	return l1_Nova_list;
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_first(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (this->nova_standard_datastruct_list_Nova_Array_Nova_size > 0)
	{
		return this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[0];
	}
	return (nova_standard_Nova_Object*)nova_null;
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_last(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (this->nova_standard_datastruct_list_Nova_Array_Nova_size > 0)
	{
		return this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[this->nova_standard_datastruct_list_Nova_Array_Nova_size - 1];
	}
	return (nova_standard_Nova_Object*)nova_null;
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_firstWhere(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure18_Nova_func nova_standard_datastruct_list_Nova_Array_Nova_func, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_func)
{
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)(this), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (nova_standard_datastruct_list_Nova_Array_Nova_func(nova_standard_datastruct_list_Nova_Array_ref_Nova_func, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element)))
		{
			return (nova_standard_Nova_Object*)l1_Nova_element;
		}
	}
	return (nova_standard_Nova_Object*)nova_null;
}

long nova_standard_datastruct_list_Nova_Array_Nova_sumSize(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	long l1_Nova_sum;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_value;
	
	l1_Nova_sum = (long)(0);
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)(this), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_value = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		l1_Nova_sum = l1_Nova_sum + ((nova_standard_Nova_String*)l1_Nova_value)->nova_standard_Nova_String_Nova_size;
	}
	return l1_Nova_sum;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_Nova_reverse(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_array;
	int l1_Nova_i;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_Array_1_Nova_construct(0, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_size);
	l1_Nova_array->nova_standard_datastruct_list_Nova_Array_Nova_size = this->nova_standard_datastruct_list_Nova_Array_Nova_size;
	l1_Nova_i = 0;
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)(this), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		l1_Nova_array->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[this->nova_standard_datastruct_list_Nova_Array_Nova_size - ++l1_Nova_i] = (nova_standard_Nova_Object*)(l1_Nova_element);
	}
	return l1_Nova_array;
}

nova_standard_Nova_String* nova_standard_datastruct_list_Nova_Array_Nova_join(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_datastruct_list_Nova_Array_Nova_delimiter)
{
	nova_standard_Nova_String* l1_Nova_str;
	char l1_Nova_passed;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_str = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "");
	l1_Nova_passed = 0;
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)(this), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (l1_Nova_passed)
		{
			l1_Nova_str = (nova_standard_Nova_String*)(nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(l1_Nova_str), exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_delimiter));
		}
		else
		{
			l1_Nova_passed = 1;
		}
		l1_Nova_str = (nova_standard_Nova_String*)(nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(l1_Nova_str), exceptionData, nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)(l1_Nova_element), exceptionData)));
	}
	return l1_Nova_str;
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
	this->nova_standard_datastruct_list_Nova_Array_Nova_capacity = 0;
	this->nova_standard_datastruct_list_Nova_Array_Nova_size = 0;
	this->nova_standard_datastruct_list_Nova_Array_Nova_position = 0;
	this->nova_standard_datastruct_list_Nova_Array_Nova_stringsOnly = (nova_standard_Nova_String*)nova_null;
	this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data = (nova_standard_Nova_Object**)nova_null;
	this->nova_standard_datastruct_list_Nova_Array_Nova_stringsOnly = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "hey");
}

