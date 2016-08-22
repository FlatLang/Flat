#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_Array.h>

typedef struct
{
	/* E value */ nova_standard_Nova_Object** nova_standard_datastruct_list_Nova_Array_Nova_value;
} Context1;


nova_standard_datastruct_list_Extension_VTable_Array nova_standard_datastruct_list_Extension_VTable_Array_val =
{
	{
		0,
		(nova_standard_datastruct_list_Nova_Iterator*(*)(nova_standard_datastruct_list_Nova_Iterable*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_Array_Accessor_Nova_iterator,
		0,
		0,
		0,
		(nova_standard_datastruct_list_Nova_Array*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_Array_Nova_toArray,
		(char(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_datastruct_list_Nova_Array_Nova_contains,
		(void(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure3_Nova_func nova_standard_datastruct_list_Nova_List_Nova_func, void* nova_standard_datastruct_list_Nova_List_ref_Nova_func, void* func_context))nova_standard_datastruct_list_Nova_Array_0_Nova_forEach,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc nova_standard_datastruct_list_Nova_List_Nova_mapFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_mapFunc, void* mapFunc_context))nova_standard_datastruct_list_Nova_Array_0_Nova_map,
		(char(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure9_Nova_anyFunc nova_standard_datastruct_list_Nova_List_Nova_anyFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_anyFunc, void* anyFunc_context))nova_standard_datastruct_list_Nova_Array_0_Nova_any,
		(char(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure12_Nova_allFunc nova_standard_datastruct_list_Nova_List_Nova_allFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_allFunc, void* allFunc_context))nova_standard_datastruct_list_Nova_Array_0_Nova_all,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc nova_standard_datastruct_list_Nova_List_Nova_filterFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_filterFunc, void* filterFunc_context))nova_standard_datastruct_list_Nova_Array_0_Nova_filter,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, int))nova_standard_datastruct_list_Nova_Array_0_Nova_take,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, int))nova_standard_datastruct_list_Nova_Array_0_Nova_skip,
		(nova_standard_Nova_Object*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure18_Nova_func nova_standard_datastruct_list_Nova_List_Nova_func, void* nova_standard_datastruct_list_Nova_List_ref_Nova_func, void* func_context))nova_standard_datastruct_list_Nova_Array_0_Nova_firstWhere,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_Array_0_Nova_reverse,
		(nova_standard_Nova_String*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_String*))nova_standard_datastruct_list_Nova_Array_0_Nova_join,
		0,
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_datastruct_list_Nova_Array_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_datastruct_list_Nova_Array_0_Nova_get,
	nova_standard_datastruct_list_Nova_Array_Nova_contains,
	nova_standard_datastruct_list_Nova_Array_Nova_toArray,
	nova_standard_datastruct_list_Nova_Array_0_Nova_map,
	nova_standard_datastruct_list_Nova_Array_0_Nova_forEach,
	nova_standard_datastruct_list_Nova_Array_0_Nova_any,
	nova_standard_datastruct_list_Nova_Array_0_Nova_all,
	nova_standard_datastruct_list_Nova_Array_0_Nova_filter,
	nova_standard_datastruct_list_Nova_Array_0_Nova_take,
	nova_standard_datastruct_list_Nova_Array_0_Nova_skip,
	nova_standard_datastruct_list_Nova_Array_0_Nova_firstWhere,
	nova_standard_datastruct_list_Nova_Array_0_Nova_reverse,
	nova_standard_datastruct_list_Nova_Array_0_Nova_join,
	nova_standard_datastruct_list_Nova_Array_Accessor_Nova_iterator,
	nova_standard_datastruct_list_Nova_Array_Accessor_Nova_first,
	nova_standard_datastruct_list_Nova_Array_Accessor_Nova_last,
};



void nova_standard_datastruct_list_Nova_Array_Nova_shiftRight(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_left, int nova_standard_datastruct_list_Nova_Array_Nova_right);
void nova_standard_datastruct_list_Nova_Array_Nova_shiftLeft(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_left, int nova_standard_datastruct_list_Nova_Array_Nova_right);
void nova_standard_datastruct_list_Nova_Array_0_Nova_increaseSize(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_count);
char nova_standard_datastruct_list_Nova_Array_Nova_testLambda25(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova__1, Context1* context);



void nova_standard_datastruct_list_Nova_Array_Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_0_Nova_Array(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_Array, this,);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_Array_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_1_Nova_Array(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_count)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_Array, this,);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_Array_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_Array_1_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_count);
	}
	
	return this;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_2_Nova_Array(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object** nova_standard_datastruct_list_Nova_Array_Nova_data, int nova_standard_datastruct_list_Nova_Array_Nova_count)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_Array, this,);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_Array_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_Array_2_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_data, nova_standard_datastruct_list_Nova_Array_Nova_count);
	}
	
	return this;
}

void nova_standard_datastruct_list_Nova_Array_Nova_destroy(nova_standard_datastruct_list_Nova_Array** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_list_Nova_Array_0_Nova_this(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array_1_Nova_this(this, exceptionData, 10);
}

void nova_standard_datastruct_list_Nova_Array_1_Nova_this(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_count)
{
	this->nova_standard_datastruct_list_Nova_Array_Nova_count = (int)(0);
	this->nova_standard_datastruct_list_Nova_Array_Nova_position = (int)(0);
	this->nova_standard_datastruct_list_Nova_Array_Nova_capacity = (int)(0);
	nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_count);
}

void nova_standard_datastruct_list_Nova_Array_2_Nova_this(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object** nova_standard_datastruct_list_Nova_Array_Nova_data, int nova_standard_datastruct_list_Nova_Array_Nova_count)
{
	int l4_Nova_i = 0;
	
	nova_standard_datastruct_list_Nova_Array_1_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_count);
	l4_Nova_i = (int)0;
	for (; l4_Nova_i < (int)nova_standard_datastruct_list_Nova_Array_Nova_count; l4_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_data[l4_Nova_i]);
	}
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_Nova_addAll(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_Nova_data)
{
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_Nova_Object* l1_Nova_d = (nova_standard_Nova_Object*)nova_null;
	
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this->nova_standard_datastruct_list_Nova_Array_Nova_data)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_d = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(this, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_d));
	}
	return this;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_0_Nova_add(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_element)
{
	if (this->nova_standard_datastruct_list_Nova_Array_Nova_position >= this->nova_standard_datastruct_list_Nova_Array_Nova_capacity)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_increaseSize(this, exceptionData);
	}
	this->nova_standard_datastruct_list_Nova_Array_Nova_data[this->nova_standard_datastruct_list_Nova_Array_Nova_position++] = nova_standard_datastruct_list_Nova_Array_Nova_element;
	this->nova_standard_datastruct_list_Nova_Array_Nova_count = (int)(nova_standard_math_Nova_Math_Nova_max(0, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_position, this->nova_standard_datastruct_list_Nova_Array_Nova_count));
	return this;
}

void nova_standard_datastruct_list_Nova_Array_1_Nova_add(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_element)
{
	if (nova_standard_datastruct_list_Nova_Array_Nova_index >= this->nova_standard_datastruct_list_Nova_Array_Nova_capacity)
	{
		nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_index + 1);
	}
	nova_standard_datastruct_list_Nova_Array_0_Nova_add(this, exceptionData, (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null));
	nova_standard_datastruct_list_Nova_Array_Nova_shiftRight(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_index, this->nova_standard_datastruct_list_Nova_Array_Nova_position);
	this->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index] = nova_standard_datastruct_list_Nova_Array_Nova_element;
	if (nova_standard_datastruct_list_Nova_Array_Nova_index >= this->nova_standard_datastruct_list_Nova_Array_Nova_position - 1)
	{
		this->nova_standard_datastruct_list_Nova_Array_Nova_position = nova_standard_datastruct_list_Nova_Array_Nova_index + 1;
	}
	this->nova_standard_datastruct_list_Nova_Array_Nova_count = (int)(nova_standard_math_Nova_Math_Nova_max(0, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_position, this->nova_standard_datastruct_list_Nova_Array_Nova_count));
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_remove(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index)
{
	nova_standard_Nova_Object* l1_Nova_element = (nova_standard_Nova_Object*)nova_null;
	
	l1_Nova_element = this->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index];
	nova_standard_datastruct_list_Nova_Array_Nova_shiftLeft(this, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_index + 1, this->nova_standard_datastruct_list_Nova_Array_Nova_position--);
	return l1_Nova_element;
}

void nova_standard_datastruct_list_Nova_Array_Nova_shiftRight(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_left, int nova_standard_datastruct_list_Nova_Array_Nova_right)
{
	int l1_Nova_i = 0;
	
	l1_Nova_i = nova_standard_datastruct_list_Nova_Array_Nova_right - 1;
	while (l1_Nova_i > nova_standard_datastruct_list_Nova_Array_Nova_left)
	{
		this->nova_standard_datastruct_list_Nova_Array_Nova_data[l1_Nova_i] = this->nova_standard_datastruct_list_Nova_Array_Nova_data[l1_Nova_i - 1];
		l1_Nova_i--;
	}
	this->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_left] = (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null);
}

void nova_standard_datastruct_list_Nova_Array_Nova_shiftLeft(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_left, int nova_standard_datastruct_list_Nova_Array_Nova_right)
{
	int l2_Nova_i = 0;
	
	nova_standard_datastruct_list_Nova_Array_Nova_left--;
	nova_standard_datastruct_list_Nova_Array_Nova_right--;
	l2_Nova_i = (int)nova_standard_datastruct_list_Nova_Array_Nova_left;
	for (; l2_Nova_i < (int)nova_standard_datastruct_list_Nova_Array_Nova_right; l2_Nova_i++)
	{
		this->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i] = this->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i + 1];
	}
	this->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_right] = (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null);
}

void nova_standard_datastruct_list_Nova_Array_Nova_swap(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index1, int nova_standard_datastruct_list_Nova_Array_Nova_index2)
{
	nova_standard_Nova_Object* l1_Nova_temp = (nova_standard_Nova_Object*)nova_null;
	
	l1_Nova_temp = this->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index1];
	this->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index1] = this->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index2];
	this->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index2] = l1_Nova_temp;
}

void nova_standard_datastruct_list_Nova_Array_0_Nova_increaseSize(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(this, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_capacity + 3);
}

void nova_standard_datastruct_list_Nova_Array_1_Nova_increaseSize(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_count)
{
	int l1_Nova_offset = 0;
	nova_standard_Nova_Object** l1_Nova_tmp = (nova_standard_Nova_Object**)nova_null;
	int l2_Nova_i = 0;
	
	l1_Nova_offset = nova_standard_datastruct_list_Nova_Array_Nova_count - this->nova_standard_datastruct_list_Nova_Array_Nova_capacity;
	this->nova_standard_datastruct_list_Nova_Array_Nova_capacity = nova_standard_datastruct_list_Nova_Array_Nova_count;
	
	l1_Nova_tmp = (nova_standard_Nova_Object**)((nova_standard_Nova_Object**)NOVA_MALLOC(sizeof(nova_standard_Nova_Object) * this->nova_standard_datastruct_list_Nova_Array_Nova_capacity));
	arrayCopy(l1_Nova_tmp, 0, this->nova_standard_datastruct_list_Nova_Array_Nova_data, 0, (int)(this->nova_standard_datastruct_list_Nova_Array_Nova_count), this->nova_standard_datastruct_list_Nova_Array_Nova_capacity, 4);
	this->nova_standard_datastruct_list_Nova_Array_Nova_data = l1_Nova_tmp;
	l2_Nova_i = (int)(this->nova_standard_datastruct_list_Nova_Array_Nova_capacity - l1_Nova_offset);
	for (; l2_Nova_i < (int)this->nova_standard_datastruct_list_Nova_Array_Nova_capacity; l2_Nova_i++)
	{
		this->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i] = (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null);
	}
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_0_Nova_get(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index)
{
	return this->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index];
}

void nova_standard_datastruct_list_Nova_Array_Nova_set(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_value)
{
	this->nova_standard_datastruct_list_Nova_Array_Nova_data[nova_standard_datastruct_list_Nova_Array_Nova_index] = nova_standard_datastruct_list_Nova_Array_Nova_value;
}

char nova_standard_datastruct_list_Nova_Array_Nova_contains(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova_value)
{
	Context1 contextArg28 = 
	{
		&nova_standard_datastruct_list_Nova_Array_Nova_value,
	};
	
	return nova_standard_datastruct_list_Nova_List_virtual0_Nova_any((nova_standard_datastruct_list_Nova_List*)(this), exceptionData, (nova_standard_datastruct_list_Nova_List_closure9_Nova_anyFunc)&nova_standard_datastruct_list_Nova_Array_Nova_testLambda25, this, &contextArg28);
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_Nova_toArray(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_array = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	int l2_Nova_i = 0;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_Array_1_Nova_Array(0, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_count);
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)this->nova_standard_datastruct_list_Nova_Array_Nova_count; l2_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_Nova_set(l1_Nova_array, exceptionData, l2_Nova_i, this->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i]);
	}
	return l1_Nova_array;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_0_Nova_map(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure3_Nova_mapFunc nova_standard_datastruct_list_Nova_Array_Nova_mapFunc, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_mapFunc, void* mapFunc_context)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_array = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	int l1_Nova_i = 0;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_Nova_Object* l1_Nova_element = (nova_standard_Nova_Object*)nova_null;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	l1_Nova_i = (int)(0);
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_array, exceptionData, (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Array_Nova_mapFunc(nova_standard_datastruct_list_Nova_Array_ref_Nova_mapFunc, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element), l1_Nova_i++, this, mapFunc_context)));
	}
	return l1_Nova_array;
}

void nova_standard_datastruct_list_Nova_Array_0_Nova_forEach(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure6_Nova_func nova_standard_datastruct_list_Nova_Array_Nova_func, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_func, void* func_context)
{
	int l2_Nova_i = 0;
	
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)this->nova_standard_datastruct_list_Nova_Array_Nova_count; l2_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_Nova_func(nova_standard_datastruct_list_Nova_Array_ref_Nova_func, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i], l2_Nova_i, this, func_context);
	}
}

char nova_standard_datastruct_list_Nova_Array_0_Nova_any(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure9_Nova_anyFunc nova_standard_datastruct_list_Nova_Array_Nova_anyFunc, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_anyFunc, void* anyFunc_context)
{
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_Nova_Object* l1_Nova_element = (nova_standard_Nova_Object*)nova_null;
	
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (nova_standard_datastruct_list_Nova_Array_Nova_anyFunc(nova_standard_datastruct_list_Nova_Array_ref_Nova_anyFunc, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element), anyFunc_context))
		{
			return 1;
		}
	}
	return 0;
}

char nova_standard_datastruct_list_Nova_Array_0_Nova_all(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure12_Nova_allFunc nova_standard_datastruct_list_Nova_Array_Nova_allFunc, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_allFunc, void* allFunc_context)
{
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_Nova_Object* l1_Nova_element = (nova_standard_Nova_Object*)nova_null;
	
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (!nova_standard_datastruct_list_Nova_Array_Nova_allFunc(nova_standard_datastruct_list_Nova_Array_ref_Nova_allFunc, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element), allFunc_context))
		{
			return 0;
		}
	}
	return 1;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_0_Nova_filter(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure15_Nova_filterFunc nova_standard_datastruct_list_Nova_Array_Nova_filterFunc, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_filterFunc, void* filterFunc_context)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_filtered = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	int l1_Nova_i = 0;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_Nova_Object* l1_Nova_element = (nova_standard_Nova_Object*)nova_null;
	
	l1_Nova_filtered = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	l1_Nova_i = (int)(0);
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (nova_standard_datastruct_list_Nova_Array_Nova_filterFunc(nova_standard_datastruct_list_Nova_Array_ref_Nova_filterFunc, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element), l1_Nova_i++, this, filterFunc_context))
		{
			nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_filtered, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element));
		}
	}
	return l1_Nova_filtered;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_0_Nova_take(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_howMany)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	int l2_Nova_i = 0;
	
	nova_standard_datastruct_list_Nova_Array_Nova_howMany = nova_standard_datastruct_list_Nova_Array_Nova_howMany > this->nova_standard_datastruct_list_Nova_Array_Nova_count ? this->nova_standard_datastruct_list_Nova_Array_Nova_count : nova_standard_datastruct_list_Nova_Array_Nova_howMany;
	l1_Nova_list = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)nova_standard_datastruct_list_Nova_Array_Nova_howMany; l2_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i]);
	}
	return l1_Nova_list;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_0_Nova_skip(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_howMany)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	int l2_Nova_i = 0;
	
	l1_Nova_list = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	l2_Nova_i = (int)nova_standard_datastruct_list_Nova_Array_Nova_howMany;
	for (; l2_Nova_i < (int)this->nova_standard_datastruct_list_Nova_Array_Nova_count; l2_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i]);
	}
	return l1_Nova_list;
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_0_Nova_firstWhere(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array_closure18_Nova_func nova_standard_datastruct_list_Nova_Array_Nova_func, void* nova_standard_datastruct_list_Nova_Array_ref_Nova_func, void* func_context)
{
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_Nova_Object* l1_Nova_element = (nova_standard_Nova_Object*)nova_null;
	
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (nova_standard_datastruct_list_Nova_Array_Nova_func(nova_standard_datastruct_list_Nova_Array_ref_Nova_func, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element), func_context))
		{
			return (nova_standard_Nova_Object*)l1_Nova_element;
		}
	}
	return (nova_standard_Nova_Object*)nova_null;
}

long_long nova_standard_datastruct_list_Nova_Array_Nova_sumSize(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	long_long l1_Nova_sum = 0;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_Nova_Object* l1_Nova_value = (nova_standard_Nova_Object*)nova_null;
	
	l1_Nova_sum = (long_long)(0);
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_value = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		l1_Nova_sum = l1_Nova_sum + ((nova_standard_Nova_String*)l1_Nova_value)->nova_standard_Nova_String_Nova_count;
	}
	return l1_Nova_sum;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_Array_0_Nova_reverse(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_array = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	int l1_Nova_i = 0;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_Nova_Object* l1_Nova_element = (nova_standard_Nova_Object*)nova_null;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_Array_1_Nova_Array(0, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_count);
	l1_Nova_array->nova_standard_datastruct_list_Nova_Array_Nova_count = this->nova_standard_datastruct_list_Nova_Array_Nova_count;
	l1_Nova_i = (int)(0);
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		l1_Nova_array->nova_standard_datastruct_list_Nova_Array_Nova_data[this->nova_standard_datastruct_list_Nova_Array_Nova_count - ++l1_Nova_i] = (nova_standard_Nova_Object*)(l1_Nova_element);
	}
	return l1_Nova_array;
}

nova_standard_Nova_String* nova_standard_datastruct_list_Nova_Array_0_Nova_join(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_datastruct_list_Nova_Array_Nova_delimiter)
{
	nova_standard_Nova_String* l1_Nova_str = (nova_standard_Nova_String*)nova_null;
	char l1_Nova_passed = 0;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_Nova_Object* l1_Nova_element = (nova_standard_Nova_Object*)nova_null;
	
	l1_Nova_str = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "");
	l1_Nova_passed = 0;
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (l1_Nova_passed)
		{
			l1_Nova_str = (nova_standard_Nova_String*)(nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(l1_Nova_str), exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_delimiter));
		}
		else
		{
			l1_Nova_passed = 1;
		}
		l1_Nova_str = (nova_standard_Nova_String*)(nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(l1_Nova_str), exceptionData, nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)(l1_Nova_element), exceptionData)));
	}
	return l1_Nova_str;
}

nova_standard_Nova_String* nova_standard_datastruct_list_Nova_Array_0_Nova_toString(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Array ["), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(this), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "]")));
}

char nova_standard_datastruct_list_Nova_Array_Nova_testLambda25(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Nova__1, Context1* context)
{
	return nova_standard_datastruct_list_Nova_Array_Nova__1 == (nova_standard_Nova_Object*)(*context->nova_standard_datastruct_list_Nova_Array_Nova_value);
}

char nova_standard_datastruct_list_Nova_Array_Accessor_Nova_empty(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->nova_standard_datastruct_list_Nova_Array_Nova_count <= 0;
}


nova_standard_datastruct_list_Nova_ArrayIterator* nova_standard_datastruct_list_Nova_Array_Accessor_Nova_iterator(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_list_Nova_ArrayIterator_Nova_ArrayIterator(0, exceptionData, this);
}


nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Accessor_Nova_first(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (this->nova_standard_datastruct_list_Nova_Array_Nova_count > 0)
	{
		return this->nova_standard_datastruct_list_Nova_Array_Nova_data[0];
	}
	return (nova_standard_Nova_Object*)nova_null;
}


nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_Accessor_Nova_last(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (this->nova_standard_datastruct_list_Nova_Array_Nova_count > 0)
	{
		return this->nova_standard_datastruct_list_Nova_Array_Nova_data[this->nova_standard_datastruct_list_Nova_Array_Nova_count - 1];
	}
	return (nova_standard_Nova_Object*)nova_null;
}

void nova_standard_datastruct_list_Nova_Array_Nova_super(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_list_Nova_Array_Nova_capacity = 0;
	this->nova_standard_datastruct_list_Nova_Array_Nova_count = 0;
	this->nova_standard_datastruct_list_Nova_Array_Nova_position = 0;
	this->nova_standard_datastruct_list_Nova_Array_Nova_data = (nova_standard_Nova_Object**)nova_null;
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_virtual1_Nova_get(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_Array_Nova_index)
{
	return this->vtable->nova_standard_datastruct_list_Nova_Array_virtual1_Nova_get((nova_standard_datastruct_list_Nova_Array*)(this), exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_index);
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_virtual_Accessor_Nova_first(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->nova_standard_datastruct_list_Nova_Array_virtual_Accessor_Nova_first((nova_standard_datastruct_list_Nova_Array*)(this), exceptionData);
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_Array_virtual_Accessor_Nova_last(nova_standard_datastruct_list_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->nova_standard_datastruct_list_Nova_Array_virtual_Accessor_Nova_last((nova_standard_datastruct_list_Nova_Array*)(this), exceptionData);
}

