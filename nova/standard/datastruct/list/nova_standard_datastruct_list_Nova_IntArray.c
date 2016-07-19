#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_IntArray.h>

nova_standard_datastruct_list_Extension_VTable_IntArray nova_standard_datastruct_list_Extension_VTable_IntArray_val =
{
	{
		0,
		(nova_standard_datastruct_list_Nova_Iterator*(*)(nova_standard_datastruct_list_Nova_Iterable*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_IntArray_Accessor_Nova_iterator,
		0,
		0,
		0,
		(nova_standard_Nova_Object**(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_Array_Nova_toArray,
		(void(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure3_Nova_func nova_standard_datastruct_list_Nova_List_Nova_func, void* nova_standard_datastruct_list_Nova_List_ref_Nova_func))nova_standard_datastruct_list_Nova_IntArray_Nova_forEach,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc nova_standard_datastruct_list_Nova_List_Nova_mapFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_mapFunc))nova_standard_datastruct_list_Nova_IntArray_Nova_map,
		(char(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure9_Nova_anyFunc nova_standard_datastruct_list_Nova_List_Nova_anyFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_anyFunc))nova_standard_datastruct_list_Nova_IntArray_Nova_any,
		(char(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure12_Nova_allFunc nova_standard_datastruct_list_Nova_List_Nova_allFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_allFunc))nova_standard_datastruct_list_Nova_IntArray_Nova_all,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc nova_standard_datastruct_list_Nova_List_Nova_filterFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_filterFunc))nova_standard_datastruct_list_Nova_IntArray_Nova_filter,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, int))nova_standard_datastruct_list_Nova_IntArray_Nova_take,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, int))nova_standard_datastruct_list_Nova_IntArray_Nova_skip,
		(nova_standard_Nova_Object*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure18_Nova_func nova_standard_datastruct_list_Nova_List_Nova_func, void* nova_standard_datastruct_list_Nova_List_ref_Nova_func))nova_standard_datastruct_list_Nova_IntArray_Nova_firstWhere,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_IntArray_Nova_reverse,
		(nova_standard_Nova_String*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_String*))nova_standard_datastruct_list_Nova_IntArray_Nova_join,
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
	nova_standard_datastruct_list_Nova_Array_Nova_toArray,
	nova_standard_datastruct_list_Nova_IntArray_Nova_map,
	nova_standard_datastruct_list_Nova_IntArray_Nova_forEach,
	nova_standard_datastruct_list_Nova_IntArray_Nova_any,
	nova_standard_datastruct_list_Nova_IntArray_Nova_all,
	nova_standard_datastruct_list_Nova_IntArray_Nova_filter,
	nova_standard_datastruct_list_Nova_IntArray_Nova_take,
	nova_standard_datastruct_list_Nova_IntArray_Nova_skip,
	nova_standard_datastruct_list_Nova_IntArray_Nova_firstWhere,
	nova_standard_datastruct_list_Nova_IntArray_Nova_reverse,
	nova_standard_datastruct_list_Nova_IntArray_Nova_join,
	nova_standard_datastruct_list_Nova_IntArray_Accessor_Nova_iterator,
	nova_standard_datastruct_list_Nova_IntArray_Accessor_Nova_first,
	nova_standard_datastruct_list_Nova_IntArray_Accessor_Nova_last,
};


CCLASS_PRIVATE
(
	nova_standard_Nova_Object** nova_standard_datastruct_list_Nova_Array_Nova_data;
	
)



void nova_standard_datastruct_list_Nova_IntArrayNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_list_Nova_IntArray* nova_standard_datastruct_list_Nova_IntArray_0_Nova_IntArray(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_IntArray, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_IntArray_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_super((nova_standard_datastruct_list_Nova_Array*)this, exceptionData);
	nova_standard_datastruct_list_Nova_IntArray_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_IntArray_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_standard_datastruct_list_Nova_IntArray* nova_standard_datastruct_list_Nova_IntArray_1_Nova_IntArray(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_IntArray_Nova_count)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_IntArray, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_IntArray_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_super((nova_standard_datastruct_list_Nova_Array*)this, exceptionData);
	nova_standard_datastruct_list_Nova_IntArray_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_IntArray_1_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_IntArray_Nova_count);
	}
	
	return this;
}

nova_standard_datastruct_list_Nova_IntArray* nova_standard_datastruct_list_Nova_IntArray_2_Nova_IntArray(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int* nova_standard_datastruct_list_Nova_IntArray_Nova_data, int nova_standard_datastruct_list_Nova_IntArray_Nova_count)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_IntArray, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_IntArray_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_super((nova_standard_datastruct_list_Nova_Array*)this, exceptionData);
	nova_standard_datastruct_list_Nova_IntArray_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_IntArray_2_Nova_this(this, exceptionData, nova_standard_datastruct_list_Nova_IntArray_Nova_data, nova_standard_datastruct_list_Nova_IntArray_Nova_count);
	}
	
	return this;
}

void nova_standard_datastruct_list_Nova_IntArray_Nova_destroy(nova_standard_datastruct_list_Nova_IntArray** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_list_Nova_IntArray_0_Nova_this(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array_0_Nova_this((nova_standard_datastruct_list_Nova_Array*)(this), exceptionData);
}

void nova_standard_datastruct_list_Nova_IntArray_1_Nova_this(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_IntArray_Nova_count)
{
	nova_standard_datastruct_list_Nova_Array_1_Nova_this((nova_standard_datastruct_list_Nova_Array*)(this), exceptionData, nova_standard_datastruct_list_Nova_IntArray_Nova_count);
}

void nova_standard_datastruct_list_Nova_IntArray_2_Nova_this(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int* nova_standard_datastruct_list_Nova_IntArray_Nova_data, int nova_standard_datastruct_list_Nova_IntArray_Nova_count)
{
	nova_standard_datastruct_list_Nova_Array_2_Nova_this((nova_standard_datastruct_list_Nova_Array*)(this), exceptionData, (nova_standard_Nova_Object**)&(nova_standard_datastruct_list_Nova_IntArray_Nova_data), nova_standard_datastruct_list_Nova_IntArray_Nova_count);
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_list_Nova_IntArray_Nova_map(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_IntArray_closure3_Nova_mapFunc nova_standard_datastruct_list_Nova_IntArray_Nova_mapFunc, void* nova_standard_datastruct_list_Nova_IntArray_ref_Nova_mapFunc)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_array = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	int l1_Nova_i = 0;
	nova_standard_datastruct_list_Nova_IntArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)nova_null;
	int l1_Nova_element = 0;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	l1_Nova_i = (int)(0);
	nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (int)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_array, exceptionData, (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_IntArray_Nova_mapFunc(nova_standard_datastruct_list_Nova_IntArray_ref_Nova_mapFunc, exceptionData, l1_Nova_element, l1_Nova_i++, this)));
	}
	return l1_Nova_array;
}

void nova_standard_datastruct_list_Nova_IntArray_Nova_forEach(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_IntArray_closure6_Nova_func nova_standard_datastruct_list_Nova_IntArray_Nova_func, void* nova_standard_datastruct_list_Nova_IntArray_ref_Nova_func)
{
	int l2_Nova_i = 0;
	
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)this->nova_standard_datastruct_list_Nova_Array_Nova_count; l2_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_IntArray_Nova_func(nova_standard_datastruct_list_Nova_IntArray_ref_Nova_func, exceptionData, (int)nova_standard_datastruct_list_Nova_Array_Nova_get((nova_standard_datastruct_list_Nova_Array*)(this), exceptionData, l2_Nova_i), l2_Nova_i, this);
	}
}

char nova_standard_datastruct_list_Nova_IntArray_Nova_any(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_IntArray_closure9_Nova_anyFunc nova_standard_datastruct_list_Nova_IntArray_Nova_anyFunc, void* nova_standard_datastruct_list_Nova_IntArray_ref_Nova_anyFunc)
{
	nova_standard_datastruct_list_Nova_IntArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)nova_null;
	int l1_Nova_element = 0;
	
	nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (int)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (nova_standard_datastruct_list_Nova_IntArray_Nova_anyFunc(nova_standard_datastruct_list_Nova_IntArray_ref_Nova_anyFunc, exceptionData, l1_Nova_element))
		{
			return 1;
		}
	}
	return 0;
}

char nova_standard_datastruct_list_Nova_IntArray_Nova_all(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_IntArray_closure12_Nova_allFunc nova_standard_datastruct_list_Nova_IntArray_Nova_allFunc, void* nova_standard_datastruct_list_Nova_IntArray_ref_Nova_allFunc)
{
	nova_standard_datastruct_list_Nova_IntArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)nova_null;
	int l1_Nova_element = 0;
	
	nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (int)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (!nova_standard_datastruct_list_Nova_IntArray_Nova_allFunc(nova_standard_datastruct_list_Nova_IntArray_ref_Nova_allFunc, exceptionData, l1_Nova_element))
		{
			return 0;
		}
	}
	return 1;
}

nova_standard_datastruct_list_Nova_IntArray* nova_standard_datastruct_list_Nova_IntArray_Nova_filter(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_IntArray_closure15_Nova_filterFunc nova_standard_datastruct_list_Nova_IntArray_Nova_filterFunc, void* nova_standard_datastruct_list_Nova_IntArray_ref_Nova_filterFunc)
{
	nova_standard_datastruct_list_Nova_IntArray* l1_Nova_filtered = (nova_standard_datastruct_list_Nova_IntArray*)nova_null;
	int l1_Nova_i = 0;
	nova_standard_datastruct_list_Nova_IntArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)nova_null;
	int l1_Nova_element = 0;
	
	l1_Nova_filtered = nova_standard_datastruct_list_Nova_IntArray_0_Nova_IntArray(0, exceptionData);
	l1_Nova_i = (int)(0);
	nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (int)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (nova_standard_datastruct_list_Nova_IntArray_Nova_filterFunc(nova_standard_datastruct_list_Nova_IntArray_ref_Nova_filterFunc, exceptionData, l1_Nova_element, l1_Nova_i++, this))
		{
			nova_standard_datastruct_list_Nova_Array_0_Nova_add((nova_standard_datastruct_list_Nova_Array*)(l1_Nova_filtered), exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, l1_Nova_element)));
		}
	}
	return l1_Nova_filtered;
}

nova_standard_datastruct_list_Nova_IntArray* nova_standard_datastruct_list_Nova_IntArray_Nova_take(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_IntArray_Nova_howMany)
{
	nova_standard_datastruct_list_Nova_IntArray* l1_Nova_list = (nova_standard_datastruct_list_Nova_IntArray*)nova_null;
	int l3_Nova_i = 0;
	
	if (nova_standard_datastruct_list_Nova_IntArray_Nova_howMany > this->nova_standard_datastruct_list_Nova_Array_Nova_count)
	{
		nova_standard_datastruct_list_Nova_IntArray_Nova_howMany = this->nova_standard_datastruct_list_Nova_Array_Nova_count;
	}
	l1_Nova_list = nova_standard_datastruct_list_Nova_IntArray_0_Nova_IntArray(0, exceptionData);
	l3_Nova_i = (int)0;
	for (; l3_Nova_i < (int)nova_standard_datastruct_list_Nova_IntArray_Nova_howMany; l3_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_add((nova_standard_datastruct_list_Nova_Array*)(l1_Nova_list), exceptionData, this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l3_Nova_i]);
	}
	return l1_Nova_list;
}

nova_standard_datastruct_list_Nova_IntArray* nova_standard_datastruct_list_Nova_IntArray_Nova_skip(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_IntArray_Nova_howMany)
{
	nova_standard_datastruct_list_Nova_IntArray* l1_Nova_list = (nova_standard_datastruct_list_Nova_IntArray*)nova_null;
	int l2_Nova_i = 0;
	
	l1_Nova_list = nova_standard_datastruct_list_Nova_IntArray_0_Nova_IntArray(0, exceptionData);
	l2_Nova_i = (int)nova_standard_datastruct_list_Nova_IntArray_Nova_howMany;
	for (; l2_Nova_i < (int)this->nova_standard_datastruct_list_Nova_Array_Nova_count; l2_Nova_i++)
	{
		nova_standard_datastruct_list_Nova_Array_0_Nova_add((nova_standard_datastruct_list_Nova_Array*)(l1_Nova_list), exceptionData, this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i]);
	}
	return l1_Nova_list;
}

int nova_standard_datastruct_list_Nova_IntArray_Nova_firstWhere(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_IntArray_closure18_Nova_func nova_standard_datastruct_list_Nova_IntArray_Nova_func, void* nova_standard_datastruct_list_Nova_IntArray_ref_Nova_func)
{
	nova_standard_datastruct_list_Nova_IntArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)nova_null;
	int l1_Nova_element = 0;
	
	nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (int)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (nova_standard_datastruct_list_Nova_IntArray_Nova_func(nova_standard_datastruct_list_Nova_IntArray_ref_Nova_func, exceptionData, l1_Nova_element))
		{
			return l1_Nova_element;
		}
	}
	return (int)nova_null;
}

nova_standard_datastruct_list_Nova_IntArray* nova_standard_datastruct_list_Nova_IntArray_Nova_reverse(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_IntArray* l1_Nova_array = (nova_standard_datastruct_list_Nova_IntArray*)nova_null;
	int l1_Nova_i = 0;
	nova_standard_datastruct_list_Nova_IntArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)nova_null;
	int l1_Nova_element = 0;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_IntArray_1_Nova_IntArray(0, exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_count);
	l1_Nova_array->nova_standard_datastruct_list_Nova_Array_Nova_count = this->nova_standard_datastruct_list_Nova_Array_Nova_count;
	l1_Nova_i = (int)(0);
	nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (int)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_standard_datastruct_list_Nova_Array_Nova_set((nova_standard_datastruct_list_Nova_Array*)(l1_Nova_array), exceptionData, this->nova_standard_datastruct_list_Nova_Array_Nova_count - ++l1_Nova_i, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, l1_Nova_element)));
	}
	return l1_Nova_array;
}

nova_standard_Nova_String* nova_standard_datastruct_list_Nova_IntArray_Nova_join(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_datastruct_list_Nova_IntArray_Nova_delimiter)
{
	nova_standard_Nova_String* l1_Nova_str = (nova_standard_Nova_String*)nova_null;
	char l1_Nova_passed = 0;
	nova_standard_datastruct_list_Nova_IntArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)nova_null;
	int l1_Nova_element = 0;
	
	l1_Nova_str = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "");
	l1_Nova_passed = 0;
	nova_local_0 = (nova_standard_datastruct_list_Nova_IntArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((this)), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_element = (int)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (l1_Nova_passed)
		{
			l1_Nova_str = (nova_standard_Nova_String*)(nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(l1_Nova_str), exceptionData, nova_standard_datastruct_list_Nova_IntArray_Nova_delimiter));
		}
		else
		{
			l1_Nova_passed = 1;
		}
		l1_Nova_str = (nova_standard_Nova_String*)(nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(l1_Nova_str), exceptionData, nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, l1_Nova_element)));
	}
	return l1_Nova_str;
}

nova_standard_datastruct_list_Nova_IntArrayIterator* nova_standard_datastruct_list_Nova_IntArray_Accessor_Nova_iterator(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_IntArrayIterator(0, exceptionData, this);
}


int nova_standard_datastruct_list_Nova_IntArray_Accessor_Nova_first(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (this->nova_standard_datastruct_list_Nova_Array_Nova_count > 0)
	{
		return (int)this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[0];
	}
	return (int)nova_null;
}


int nova_standard_datastruct_list_Nova_IntArray_Accessor_Nova_last(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (this->nova_standard_datastruct_list_Nova_Array_Nova_count > 0)
	{
		return (int)this->prv->nova_standard_datastruct_list_Nova_Array_Nova_data[this->nova_standard_datastruct_list_Nova_Array_Nova_count - 1];
	}
	return (int)nova_null;
}

void nova_standard_datastruct_list_Nova_IntArray_0_Nova_super(nova_standard_datastruct_list_Nova_IntArray* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

