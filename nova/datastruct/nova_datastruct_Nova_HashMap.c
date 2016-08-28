#include <precompiled.h>
#include <nova/datastruct/nova_datastruct_Nova_HashMap.h>

typedef struct
{
} Context1;
typedef struct
{
} Context2;
typedef struct
{
} Context3;
typedef struct
{
} Context4;
typedef struct
{
	/* K key */ nova_Nova_Object** nova_datastruct_Nova_HashMap_Nova_key;
} Context5;
typedef struct
{
	/* Int bucketSize */ int* nova_datastruct_Nova_HashMap_Nova_bucketSize;
} Context6;


nova_datastruct_Extension_VTable_HashMap nova_datastruct_Extension_VTable_HashMap_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		(nova_datastruct_list_Nova_Array*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*))nova_datastruct_Nova_HashMap_Nova_toArray,
		(char(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_datastruct_Nova_HashMap_Nova_contains,
		(void(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure3_Nova_func nova_datastruct_list_Nova_List_Nova_func, void* nova_datastruct_list_Nova_List_ref_Nova_func, void* func_context))nova_datastruct_Nova_HashMap_Nova_forEach,
		(nova_datastruct_list_Nova_List*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure6_Nova_mapFunc nova_datastruct_list_Nova_List_Nova_mapFunc, void* nova_datastruct_list_Nova_List_ref_Nova_mapFunc, void* mapFunc_context))nova_datastruct_Nova_HashMap_Nova_map,
		(char(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure9_Nova_anyFunc nova_datastruct_list_Nova_List_Nova_anyFunc, void* nova_datastruct_list_Nova_List_ref_Nova_anyFunc, void* anyFunc_context))nova_datastruct_Nova_HashMap_Nova_any,
		(char(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure12_Nova_allFunc nova_datastruct_list_Nova_List_Nova_allFunc, void* nova_datastruct_list_Nova_List_ref_Nova_allFunc, void* allFunc_context))nova_datastruct_Nova_HashMap_Nova_all,
		(nova_datastruct_list_Nova_List*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure15_Nova_filterFunc nova_datastruct_list_Nova_List_Nova_filterFunc, void* nova_datastruct_list_Nova_List_ref_Nova_filterFunc, void* filterFunc_context))nova_datastruct_Nova_HashMap_Nova_filter,
		(nova_datastruct_list_Nova_List*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, int))nova_datastruct_Nova_HashMap_Nova_take,
		(nova_datastruct_list_Nova_List*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, int))nova_datastruct_Nova_HashMap_Nova_skip,
		(nova_Nova_Object*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure18_Nova_func nova_datastruct_list_Nova_List_Nova_func, void* nova_datastruct_list_Nova_List_ref_Nova_func, void* func_context))nova_datastruct_Nova_HashMap_Nova_firstWhere,
		(nova_datastruct_list_Nova_List*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*))nova_datastruct_Nova_HashMap_Nova_reverse,
		(nova_Nova_String*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_Nova_String*))nova_datastruct_Nova_HashMap_Nova_join,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	nova_datastruct_Nova_HashMap_Nova_toArray,
	nova_datastruct_Nova_HashMap_Nova_contains,
	nova_datastruct_Nova_HashMap_Nova_any,
	nova_datastruct_Nova_HashMap_Nova_all,
	nova_datastruct_Nova_HashMap_Nova_map,
	nova_datastruct_Nova_HashMap_Nova_filter,
	nova_datastruct_Nova_HashMap_Nova_join,
	nova_datastruct_Nova_HashMap_Nova_skip,
	nova_datastruct_Nova_HashMap_Nova_take,
	nova_datastruct_Nova_HashMap_Nova_reverse,
	nova_datastruct_Nova_HashMap_Nova_firstWhere,
	nova_datastruct_Nova_HashMap_Nova_forEach,
	nova_datastruct_Nova_HashMap_0_Nova_put,
};


CCLASS_PRIVATE
(
	nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova_buckets;
	int nova_datastruct_Nova_HashMap_Nova_bucketSize;
	
)

nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova_getBucket(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key);
nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova_getPair(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key);
nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_testLambda33(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova__1, int nova_datastruct_Nova_HashMap_Nova__2, nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_Nova__3, Context1* context);
nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_testLambda34(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova__1, int nova_datastruct_Nova_HashMap_Nova__2, nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_Nova__3, Context2* context);
nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_testLambda35(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova__1, int nova_datastruct_Nova_HashMap_Nova__2, nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_Nova__3, Context3* context);
nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_testLambda36(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova__1, int nova_datastruct_Nova_HashMap_Nova__2, nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_Nova__3, Context4* context);
char nova_datastruct_Nova_HashMap_Nova_testLambda37(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova_x, int nova_datastruct_Nova_HashMap_Nova__2, nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova__3, Context5* context);
nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_testLambda38(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova__1, int nova_datastruct_Nova_HashMap_Nova__2, nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova__3, Context6* context);
void nova_datastruct_Nova_HashMap_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_0_Nova_HashMap(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_datastruct_Nova_HashMap, this);
	this->vtable = &nova_datastruct_Extension_VTable_HashMap_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_HashMap_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_Nova_HashMap_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_1_Nova_HashMap(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_HashMap_Nova_bucketCount, int nova_datastruct_Nova_HashMap_Nova_bucketSize)
{
	CCLASS_NEW(nova_datastruct_Nova_HashMap, this);
	this->vtable = &nova_datastruct_Extension_VTable_HashMap_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_HashMap_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_Nova_HashMap_1_Nova_this(this, exceptionData, nova_datastruct_Nova_HashMap_Nova_bucketCount, nova_datastruct_Nova_HashMap_Nova_bucketSize);
	}
	
	return this;
}

void nova_datastruct_Nova_HashMap_Nova_destroy(nova_datastruct_Nova_HashMap** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_datastruct_list_Nova_Array_Nova_destroy(&(*this)->prv->nova_datastruct_Nova_HashMap_Nova_buckets, exceptionData);
	
	NOVA_FREE((*this)->prv);
	
	
	NOVA_FREE(*this);
}

void nova_datastruct_Nova_HashMap_0_Nova_this(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_datastruct_Nova_HashMap_1_Nova_this(this, exceptionData, 5, 5);
}

void nova_datastruct_Nova_HashMap_1_Nova_this(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_HashMap_Nova_bucketCount, int nova_datastruct_Nova_HashMap_Nova_bucketSize)
{
	Context6 contextArg42 = 
	{
		&nova_datastruct_Nova_HashMap_Nova_bucketSize,
	};
	
	this->prv->nova_datastruct_Nova_HashMap_Nova_buckets = (nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Array_0_Nova_map((nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Array_1_Nova_Array(0, exceptionData, nova_datastruct_Nova_HashMap_Nova_bucketCount)), exceptionData, (nova_datastruct_list_Nova_Array_closure3_Nova_mapFunc)&nova_datastruct_Nova_HashMap_Nova_testLambda38, this, &contextArg42));
	this->prv->nova_datastruct_Nova_HashMap_Nova_bucketSize = nova_datastruct_Nova_HashMap_Nova_bucketSize;
}

nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova_toArray(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	Context1 contextArg37 = 
	{
	};
	
	return (nova_datastruct_list_Nova_Array*)nova_datastruct_list_Nova_List_virtual0_Nova_map((nova_datastruct_list_Nova_List*)(this), exceptionData, (nova_datastruct_list_Nova_List_closure6_Nova_mapFunc)&nova_datastruct_Nova_HashMap_Nova_testLambda33, this, &contextArg37);
}

char nova_datastruct_Nova_HashMap_Nova_contains(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova_value)
{
	nova_datastruct_Nova_Pair* l1_Nova_pair = (nova_datastruct_Nova_Pair*)nova_null;
	
	l1_Nova_pair = nova_datastruct_Nova_HashMap_Nova_getPair(this, exceptionData, (nova_Nova_Object*)(nova_datastruct_Nova_HashMap_Nova_value->nova_datastruct_Nova_Pair_Nova_key));
	if (l1_Nova_pair != (nova_datastruct_Nova_Pair*)nova_null)
	{
		return (nova_Nova_Object*)l1_Nova_pair->nova_datastruct_Nova_Pair_Nova_value == (nova_Nova_Object*)nova_datastruct_Nova_HashMap_Nova_value->nova_datastruct_Nova_Pair_Nova_value;
	}
	return 0;
}

char nova_datastruct_Nova_HashMap_Nova_any(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_HashMap_closure3_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context)
{
	nova_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_datastruct_list_Nova_Array* l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)nova_null;
	
	nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((this->prv->nova_datastruct_Nova_HashMap_Nova_buckets)), exceptionData));
	while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		nova_datastruct_list_Nova_ArrayIterator* nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
		nova_datastruct_Nova_Pair* l2_Nova_pair = (nova_datastruct_Nova_Pair*)nova_null;
		
		l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((l1_Nova_bucket)), exceptionData));
		while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData))
		{
			l2_Nova_pair = (nova_datastruct_Nova_Pair*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData));
			if (l2_Nova_pair != (nova_datastruct_Nova_Pair*)nova_null && l2_Nova_pair != 0)
			{
				if (nova_datastruct_Nova_HashMap_Nova_func(nova_datastruct_Nova_HashMap_ref_Nova_func, exceptionData, l2_Nova_pair, func_context))
				{
					return 1;
				}
			}
		}
	}
	return 0;
}

char nova_datastruct_Nova_HashMap_Nova_all(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_HashMap_closure6_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context)
{
	nova_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_datastruct_list_Nova_Array* l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)nova_null;
	
	nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((this->prv->nova_datastruct_Nova_HashMap_Nova_buckets)), exceptionData));
	while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		nova_datastruct_list_Nova_ArrayIterator* nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
		nova_datastruct_Nova_Pair* l2_Nova_pair = (nova_datastruct_Nova_Pair*)nova_null;
		
		l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((l1_Nova_bucket)), exceptionData));
		while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData))
		{
			l2_Nova_pair = (nova_datastruct_Nova_Pair*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData));
			if (l2_Nova_pair != (nova_datastruct_Nova_Pair*)nova_null && l2_Nova_pair != 0)
			{
				if (!nova_datastruct_Nova_HashMap_Nova_func(nova_datastruct_Nova_HashMap_ref_Nova_func, exceptionData, l2_Nova_pair, func_context))
				{
					return 0;
				}
			}
		}
	}
	return 1;
}

nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova_map(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_HashMap_closure9_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context)
{
	nova_datastruct_list_Nova_Array* l1_Nova_array = (nova_datastruct_list_Nova_Array*)nova_null;
	int l1_Nova_i = 0;
	nova_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_datastruct_list_Nova_Array* l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)nova_null;
	
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("in map")));
	l1_Nova_array = nova_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	l1_Nova_i = (int)(0);
	nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((this->prv->nova_datastruct_Nova_HashMap_Nova_buckets)), exceptionData));
	while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		nova_datastruct_list_Nova_ArrayIterator* nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
		nova_datastruct_Nova_Pair* l2_Nova_pair = (nova_datastruct_Nova_Pair*)nova_null;
		
		l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((l1_Nova_bucket)), exceptionData));
		while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData))
		{
			l2_Nova_pair = (nova_datastruct_Nova_Pair*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData));
			if (l2_Nova_pair != (nova_datastruct_Nova_Pair*)nova_null && l2_Nova_pair != 0)
			{
				nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("here... with ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((l2_Nova_pair)), exceptionData)), exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("")))));
				nova_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_array, exceptionData, (nova_Nova_Object*)(nova_datastruct_Nova_HashMap_Nova_func(nova_datastruct_Nova_HashMap_ref_Nova_func, exceptionData, l2_Nova_pair, l1_Nova_i++, this, func_context)));
			}
		}
	}
	return l1_Nova_array;
}

nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova_filter(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_HashMap_closure12_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context)
{
	nova_datastruct_list_Nova_Array* l1_Nova_array = (nova_datastruct_list_Nova_Array*)nova_null;
	int l1_Nova_i = 0;
	nova_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_datastruct_list_Nova_Array* l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)nova_null;
	
	l1_Nova_array = nova_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	l1_Nova_i = (int)(0);
	nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((this->prv->nova_datastruct_Nova_HashMap_Nova_buckets)), exceptionData));
	while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		nova_datastruct_list_Nova_ArrayIterator* nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
		nova_datastruct_Nova_Pair* l2_Nova_pair = (nova_datastruct_Nova_Pair*)nova_null;
		
		l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((l1_Nova_bucket)), exceptionData));
		while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData))
		{
			l2_Nova_pair = (nova_datastruct_Nova_Pair*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData));
			if (l2_Nova_pair != (nova_datastruct_Nova_Pair*)nova_null && l2_Nova_pair != 0)
			{
				if (nova_datastruct_Nova_HashMap_Nova_func(nova_datastruct_Nova_HashMap_ref_Nova_func, exceptionData, l2_Nova_pair, l1_Nova_i++, this, func_context))
				{
					nova_datastruct_list_Nova_Array_0_Nova_add((nova_datastruct_list_Nova_Array*)(l1_Nova_array), exceptionData, (nova_Nova_Object*)(l2_Nova_pair));
				}
			}
		}
	}
	return l1_Nova_array;
}

nova_Nova_String* nova_datastruct_Nova_HashMap_Nova_join(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_datastruct_Nova_HashMap_Nova_delimiter)
{
	int l1_Nova_i = 0;
	nova_Nova_String* l1_Nova_output = (nova_Nova_String*)nova_null;
	nova_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_datastruct_list_Nova_Array* l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)nova_null;
	
	l1_Nova_i = (int)(0);
	l1_Nova_output = nova_Nova_String_1_Nova_String(0, exceptionData, (char*)(""));
	nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((this->prv->nova_datastruct_Nova_HashMap_Nova_buckets)), exceptionData));
	while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		nova_datastruct_list_Nova_ArrayIterator* nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
		nova_datastruct_Nova_Pair* l2_Nova_pair = (nova_datastruct_Nova_Pair*)nova_null;
		
		l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((l1_Nova_bucket)), exceptionData));
		while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData))
		{
			l2_Nova_pair = (nova_datastruct_Nova_Pair*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData));
			if (l2_Nova_pair != (nova_datastruct_Nova_Pair*)nova_null && l2_Nova_pair != 0)
			{
				if (l1_Nova_i > 0)
				{
					l1_Nova_output = (nova_Nova_String*)(nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(l1_Nova_output), exceptionData, nova_datastruct_Nova_HashMap_Nova_delimiter));
				}
				l1_Nova_output = (nova_Nova_String*)(nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(l1_Nova_output), exceptionData, nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(l2_Nova_pair), exceptionData)));
			}
		}
	}
	return l1_Nova_output;
}

nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova_skip(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_HashMap_Nova_num)
{
	Context2 contextArg38 = 
	{
	};
	
	return (nova_datastruct_Nova_Pair*)nova_datastruct_list_Nova_List_virtual0_Nova_skip((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_map((nova_datastruct_list_Nova_List*)(this), exceptionData, (nova_datastruct_list_Nova_List_closure6_Nova_mapFunc)&nova_datastruct_Nova_HashMap_Nova_testLambda34, this, &contextArg38)), exceptionData, nova_datastruct_Nova_HashMap_Nova_num);
}

nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova_take(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_HashMap_Nova_num)
{
	Context3 contextArg39 = 
	{
	};
	
	return (nova_datastruct_Nova_Pair*)nova_datastruct_list_Nova_List_virtual0_Nova_take((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_map((nova_datastruct_list_Nova_List*)(this), exceptionData, (nova_datastruct_list_Nova_List_closure6_Nova_mapFunc)&nova_datastruct_Nova_HashMap_Nova_testLambda35, this, &contextArg39)), exceptionData, nova_datastruct_Nova_HashMap_Nova_num);
}

nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova_reverse(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	Context4 contextArg40 = 
	{
	};
	
	return (nova_datastruct_list_Nova_Array*)nova_datastruct_list_Nova_List_virtual0_Nova_reverse((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_map((nova_datastruct_list_Nova_List*)(this), exceptionData, (nova_datastruct_list_Nova_List_closure6_Nova_mapFunc)&nova_datastruct_Nova_HashMap_Nova_testLambda36, this, &contextArg40)), exceptionData);
}

nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova_firstWhere(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_HashMap_closure15_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context)
{
	nova_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_datastruct_list_Nova_Array* l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)nova_null;
	
	nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((this->prv->nova_datastruct_Nova_HashMap_Nova_buckets)), exceptionData));
	while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		nova_datastruct_list_Nova_ArrayIterator* nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
		nova_datastruct_Nova_Pair* l2_Nova_pair = (nova_datastruct_Nova_Pair*)nova_null;
		
		l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((l1_Nova_bucket)), exceptionData));
		while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData))
		{
			l2_Nova_pair = (nova_datastruct_Nova_Pair*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData));
			if (l2_Nova_pair != (nova_datastruct_Nova_Pair*)nova_null && l2_Nova_pair != 0)
			{
				if (nova_datastruct_Nova_HashMap_Nova_func(nova_datastruct_Nova_HashMap_ref_Nova_func, exceptionData, l2_Nova_pair, func_context))
				{
					return l2_Nova_pair;
				}
			}
		}
	}
	return (nova_datastruct_Nova_Pair*)(nova_datastruct_Nova_Pair*)nova_null;
}

void nova_datastruct_Nova_HashMap_Nova_forEach(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_HashMap_closure18_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context)
{
	int l1_Nova_i = 0;
	nova_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_datastruct_list_Nova_Array* l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)nova_null;
	
	l1_Nova_i = (int)(0);
	nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((this->prv->nova_datastruct_Nova_HashMap_Nova_buckets)), exceptionData));
	while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		nova_datastruct_list_Nova_ArrayIterator* nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
		nova_datastruct_Nova_Pair* l2_Nova_pair = (nova_datastruct_Nova_Pair*)nova_null;
		
		l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_local_1 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((l1_Nova_bucket)), exceptionData));
		while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData))
		{
			l2_Nova_pair = (nova_datastruct_Nova_Pair*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData));
			if (l2_Nova_pair != (nova_datastruct_Nova_Pair*)nova_null && l2_Nova_pair != 0)
			{
				nova_datastruct_Nova_HashMap_Nova_func(nova_datastruct_Nova_HashMap_ref_Nova_func, exceptionData, l2_Nova_pair, l1_Nova_i++, this, func_context);
			}
		}
	}
}

nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova_getBucket(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key)
{
	return (nova_datastruct_list_Nova_Array*)nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(this->prv->nova_datastruct_Nova_HashMap_Nova_buckets), exceptionData, (int)((long_long)(nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong((nova_Nova_Object*)(nova_datastruct_Nova_HashMap_Nova_key), exceptionData)) & (this->prv->nova_datastruct_Nova_HashMap_Nova_buckets->nova_datastruct_list_Nova_Array_Nova_count - 1)));
}

nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_0_Nova_put(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_value)
{
	nova_datastruct_list_Nova_Array* l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)nova_null;
	
	l1_Nova_bucket = nova_datastruct_Nova_HashMap_Nova_getBucket(this, exceptionData, nova_datastruct_Nova_HashMap_Nova_key);
	nova_datastruct_list_Nova_Array_0_Nova_add((nova_datastruct_list_Nova_Array*)(l1_Nova_bucket), exceptionData, (nova_Nova_Object*)(nova_datastruct_Nova_Pair_Nova_Pair(0, exceptionData, (nova_Nova_Object*)(nova_datastruct_Nova_HashMap_Nova_key), (nova_Nova_Object*)(nova_datastruct_Nova_HashMap_Nova_value))));
	return this;
}

nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova_getPair(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key)
{
	Context5 contextArg41 = 
	{
		&nova_datastruct_Nova_HashMap_Nova_key,
	};
	
	return (nova_datastruct_Nova_Pair*)nova_datastruct_list_Nova_Array_virtual_Accessor_Nova_first((nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_List_virtual0_Nova_filter((nova_datastruct_list_Nova_List*)(nova_datastruct_Nova_HashMap_Nova_getBucket(this, exceptionData, nova_datastruct_Nova_HashMap_Nova_key)), exceptionData, (nova_datastruct_list_Nova_List_closure15_Nova_filterFunc)&nova_datastruct_Nova_HashMap_Nova_testLambda37, this, &contextArg41)), exceptionData);
}

nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_get(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key)
{
	nova_datastruct_Nova_Pair* l1_Nova_pair = (nova_datastruct_Nova_Pair*)nova_null;
	
	l1_Nova_pair = nova_datastruct_Nova_HashMap_Nova_getPair(this, exceptionData, nova_datastruct_Nova_HashMap_Nova_key);
	if (l1_Nova_pair != (nova_datastruct_Nova_Pair*)nova_null)
	{
		return (nova_Nova_Object*)l1_Nova_pair->nova_datastruct_Nova_Pair_Nova_value;
	}
	return (nova_Nova_Object*)nova_null;
}

nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_remove(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key)
{
	nova_datastruct_list_Nova_Array* l1_Nova_bucket = (nova_datastruct_list_Nova_Array*)nova_null;
	int l1_Nova_i = 0;
	nova_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_datastruct_Nova_Pair* l1_Nova_pair = (nova_datastruct_Nova_Pair*)nova_null;
	
	l1_Nova_bucket = nova_datastruct_Nova_HashMap_Nova_getBucket(this, exceptionData, nova_datastruct_Nova_HashMap_Nova_key);
	l1_Nova_i = (int)(0);
	nova_local_0 = (nova_datastruct_list_Nova_ArrayIterator*)(nova_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_datastruct_list_Nova_Iterable*)((l1_Nova_bucket)), exceptionData));
	while (nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_pair = (nova_datastruct_Nova_Pair*)(nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (nova_operators_Nova_Equals_virtual0_Nova_equals((nova_operators_Nova_Equals*)(l1_Nova_pair->nova_datastruct_Nova_Pair_Nova_key), exceptionData, (nova_Nova_Object*)(nova_datastruct_Nova_HashMap_Nova_key)))
		{
			nova_datastruct_list_Nova_Array_Nova_remove((nova_datastruct_list_Nova_Array*)(l1_Nova_bucket), exceptionData, l1_Nova_i);
			return (nova_Nova_Object*)l1_Nova_pair->nova_datastruct_Nova_Pair_Nova_value;
		}
		l1_Nova_i++;
	}
	return (nova_Nova_Object*)nova_null;
}

char nova_datastruct_Nova_HashMap_Nova_containsKey(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key)
{
	return nova_datastruct_Nova_HashMap_Nova_getPair(this, exceptionData, nova_datastruct_Nova_HashMap_Nova_key) != (nova_datastruct_Nova_Pair*)nova_null;
}

nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_testLambda33(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova__1, int nova_datastruct_Nova_HashMap_Nova__2, nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_Nova__3, Context1* context)
{
	return (nova_Nova_Object*)nova_datastruct_Nova_HashMap_Nova__1;
}

nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_testLambda34(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova__1, int nova_datastruct_Nova_HashMap_Nova__2, nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_Nova__3, Context2* context)
{
	return (nova_Nova_Object*)nova_datastruct_Nova_HashMap_Nova__1;
}

nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_testLambda35(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova__1, int nova_datastruct_Nova_HashMap_Nova__2, nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_Nova__3, Context3* context)
{
	return (nova_Nova_Object*)nova_datastruct_Nova_HashMap_Nova__1;
}

nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_testLambda36(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova__1, int nova_datastruct_Nova_HashMap_Nova__2, nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_Nova__3, Context4* context)
{
	return (nova_Nova_Object*)nova_datastruct_Nova_HashMap_Nova__1;
}

char nova_datastruct_Nova_HashMap_Nova_testLambda37(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova_x, int nova_datastruct_Nova_HashMap_Nova__2, nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova__3, Context5* context)
{
	return nova_datastruct_Nova_HashMap_Nova_x != 0 && nova_datastruct_Nova_HashMap_Nova_x != (nova_datastruct_Nova_Pair*)nova_null && nova_operators_Nova_Equals_virtual0_Nova_equals((nova_operators_Nova_Equals*)(nova_datastruct_Nova_HashMap_Nova_x->nova_datastruct_Nova_Pair_Nova_key), exceptionData, (nova_Nova_Object*)((*context->nova_datastruct_Nova_HashMap_Nova_key)));
}

nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_testLambda38(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova__1, int nova_datastruct_Nova_HashMap_Nova__2, nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova__3, Context6* context)
{
	return (nova_Nova_Object*)nova_datastruct_list_Nova_Array_1_Nova_Array(0, exceptionData, (*context->nova_datastruct_Nova_HashMap_Nova_bucketSize));
}

void nova_datastruct_Nova_HashMap_Nova_super(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_datastruct_Nova_HashMap_Nova_size = 0;
	this->prv->nova_datastruct_Nova_HashMap_Nova_buckets = (nova_datastruct_list_Nova_Array*)nova_null;
	this->prv->nova_datastruct_Nova_HashMap_Nova_bucketSize = 0;
}

nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_virtual1_Nova_put(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_value)
{
	return this->vtable->nova_datastruct_Nova_HashMap_virtual1_Nova_put((nova_datastruct_Nova_HashMap*)(this), exceptionData, nova_datastruct_Nova_HashMap_Nova_key, nova_datastruct_Nova_HashMap_Nova_value);
}

