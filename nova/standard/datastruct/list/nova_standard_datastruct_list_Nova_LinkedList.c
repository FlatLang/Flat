#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_LinkedList.h>

nova_standard_datastruct_list_Extension_VTable_LinkedList nova_standard_datastruct_list_Extension_VTable_LinkedList_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		(void(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure3_Nova_func nova_standard_datastruct_list_Nova_List_Nova_func, void* nova_standard_datastruct_list_Nova_List_ref_Nova_func))nova_standard_datastruct_list_Nova_LinkedList_0_Nova_forEach,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc nova_standard_datastruct_list_Nova_List_Nova_mapFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_mapFunc))nova_standard_datastruct_list_Nova_LinkedList_0_Nova_map,
		(char(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure9_Nova_anyFunc nova_standard_datastruct_list_Nova_List_Nova_anyFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_anyFunc))nova_standard_datastruct_list_Nova_LinkedList_0_Nova_any,
		(char(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure12_Nova_allFunc nova_standard_datastruct_list_Nova_List_Nova_allFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_allFunc))nova_standard_datastruct_list_Nova_LinkedList_0_Nova_all,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc nova_standard_datastruct_list_Nova_List_Nova_filterFunc, void* nova_standard_datastruct_list_Nova_List_ref_Nova_filterFunc))nova_standard_datastruct_list_Nova_LinkedList_0_Nova_filter,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, int))nova_standard_datastruct_list_Nova_LinkedList_0_Nova_take,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, int))nova_standard_datastruct_list_Nova_LinkedList_0_Nova_skip,
		(nova_standard_Nova_Object*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_LinkedList_Accessor0_Nova_first,
		(nova_standard_Nova_Object*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_LinkedList_0_Nova_last,
		(nova_standard_Nova_Object*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_datastruct_list_Nova_List_closure18_Nova_func nova_standard_datastruct_list_Nova_List_Nova_func, void* nova_standard_datastruct_list_Nova_List_ref_Nova_func))nova_standard_datastruct_list_Nova_LinkedList_0_Nova_firstWhere,
		(nova_standard_datastruct_list_Nova_List*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*))nova_standard_datastruct_list_Nova_LinkedList_0_Nova_reverse,
		(nova_standard_Nova_String*(*)(nova_standard_datastruct_list_Nova_List*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_String*))nova_standard_datastruct_list_Nova_LinkedList_0_Nova_join,
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
	nova_standard_datastruct_list_Nova_LinkedList_0_Nova_map,
	nova_standard_datastruct_list_Nova_LinkedList_0_Nova_forEach,
	nova_standard_datastruct_list_Nova_LinkedList_0_Nova_any,
	nova_standard_datastruct_list_Nova_LinkedList_0_Nova_all,
	nova_standard_datastruct_list_Nova_LinkedList_0_Nova_filter,
	nova_standard_datastruct_list_Nova_LinkedList_0_Nova_take,
	nova_standard_datastruct_list_Nova_LinkedList_0_Nova_skip,
	nova_standard_datastruct_list_Nova_LinkedList_Accessor0_Nova_first,
	nova_standard_datastruct_list_Nova_LinkedList_0_Nova_last,
	nova_standard_datastruct_list_Nova_LinkedList_0_Nova_firstWhere,
	nova_standard_datastruct_list_Nova_LinkedList_0_Nova_reverse,
	nova_standard_datastruct_list_Nova_LinkedList_0_Nova_join,
};


CCLASS_PRIVATE
(
	nova_standard_datastruct_list_Nova_ListNode* nova_standard_datastruct_list_Nova_LinkedList_Nova_start;
	nova_standard_datastruct_list_Nova_ListNode* nova_standard_datastruct_list_Nova_LinkedList_Nova_current;
	
)



void nova_standard_datastruct_list_Nova_LinkedListNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedList_0_Nova_construct(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_LinkedList, this);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_LinkedList_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_LinkedList_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_LinkedList_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_list_Nova_LinkedList_Nova_destroy(nova_standard_datastruct_list_Nova_LinkedList** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_list_Nova_ListNode_Nova_destroy(&(*this)->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start, exceptionData);
	nova_standard_datastruct_list_Nova_ListNode_Nova_destroy(&(*this)->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_current, exceptionData);
	NOVA_FREE((*this)->prv);
	
	
	NOVA_FREE(*this);
}

nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedList_Nova_add(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_LinkedList_Nova_data)
{
	nova_standard_datastruct_list_Nova_ListNode* l1_Nova_node;
	
	l1_Nova_node = nova_standard_datastruct_list_Nova_ListNode_Nova_construct(0, exceptionData, nova_standard_datastruct_list_Nova_LinkedList_Nova_data);
	if (this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start == (nova_standard_datastruct_list_Nova_ListNode*)nova_null)
	{
		this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start = l1_Nova_node;
		this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_current = l1_Nova_node;
	}
	else
	{
		this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_current->nova_standard_datastruct_list_Nova_ListNode_Nova_next = l1_Nova_node;
	}
	this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_current = l1_Nova_node;
	this->nova_standard_datastruct_list_Nova_LinkedList_Nova_size++;
	return this;
}

nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedList_Nova_remove(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_LinkedList_Nova_data)
{
	nova_standard_datastruct_list_Nova_ListNode* l1_Nova_prev;
	nova_standard_datastruct_list_Nova_ListNode* l1_Nova_cur;
	
	if (this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start->nova_standard_datastruct_list_Nova_ListNode_Nova_data == nova_standard_datastruct_list_Nova_LinkedList_Nova_data)
	{
		this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start = this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start->nova_standard_datastruct_list_Nova_ListNode_Nova_next;
	}
	l1_Nova_prev = this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start;
	l1_Nova_cur = this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start->nova_standard_datastruct_list_Nova_ListNode_Nova_next;
	while (l1_Nova_cur != (nova_standard_datastruct_list_Nova_ListNode*)nova_null)
	{
		nova_standard_Nova_Object* l2_Nova_d;
		
		l2_Nova_d = l1_Nova_cur->nova_standard_datastruct_list_Nova_ListNode_Nova_data;
		if (l2_Nova_d == nova_standard_datastruct_list_Nova_LinkedList_Nova_data)
		{
			l1_Nova_prev->nova_standard_datastruct_list_Nova_ListNode_Nova_next = l1_Nova_cur->nova_standard_datastruct_list_Nova_ListNode_Nova_next;
			this->nova_standard_datastruct_list_Nova_LinkedList_Nova_size--;
		}
		l1_Nova_cur = l1_Nova_cur->nova_standard_datastruct_list_Nova_ListNode_Nova_next;
	}
	return this;
}

nova_standard_Nova_Object** nova_standard_datastruct_list_Nova_LinkedList_Nova_toArray(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_Object** l1_Nova_array;
	int l1_Nova_i;
	nova_standard_datastruct_list_Nova_LinkedListIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_array = (nova_standard_Nova_Object**)((nova_standard_Nova_Object**)NOVA_MALLOC(sizeof(nova_standard_Nova_Object) * this->nova_standard_datastruct_list_Nova_LinkedList_Nova_size));
	l1_Nova_i = 0;
	nova_local_0 = nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_iterator(this, exceptionData);
	while (nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_local_0, exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_local_0, exceptionData));
		l1_Nova_array[l1_Nova_i++] = (nova_standard_Nova_Object*)(l1_Nova_element);
	}
	return l1_Nova_array;
}

nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedList_0_Nova_map(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_LinkedList_closure3_Nova_mapFunc nova_standard_datastruct_list_Nova_LinkedList_Nova_mapFunc, void* nova_standard_datastruct_list_Nova_LinkedList_ref_Nova_mapFunc)
{
	nova_standard_datastruct_list_Nova_LinkedList* l1_Nova_array;
	int l1_Nova_i;
	nova_standard_datastruct_list_Nova_LinkedListIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_LinkedList_0_Nova_construct(0, exceptionData);
	l1_Nova_i = 0;
	nova_local_0 = nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_iterator(this, exceptionData);
	while (nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_local_0, exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_local_0, exceptionData));
		nova_standard_datastruct_list_Nova_LinkedList_Nova_add(l1_Nova_array, exceptionData, (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_LinkedList_Nova_mapFunc(nova_standard_datastruct_list_Nova_LinkedList_ref_Nova_mapFunc, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element), l1_Nova_i++, this)));
	}
	return l1_Nova_array;
}

void nova_standard_datastruct_list_Nova_LinkedList_0_Nova_forEach(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_LinkedList_closure6_Nova_func nova_standard_datastruct_list_Nova_LinkedList_Nova_func, void* nova_standard_datastruct_list_Nova_LinkedList_ref_Nova_func)
{
	int l1_Nova_i;
	nova_standard_datastruct_list_Nova_LinkedListIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_i = 0;
	nova_local_0 = nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_iterator(this, exceptionData);
	while (nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_local_0, exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_local_0, exceptionData));
		nova_standard_datastruct_list_Nova_LinkedList_Nova_func(nova_standard_datastruct_list_Nova_LinkedList_ref_Nova_func, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element), l1_Nova_i++, this);
	}
}

char nova_standard_datastruct_list_Nova_LinkedList_0_Nova_any(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_LinkedList_closure9_Nova_anyFunc nova_standard_datastruct_list_Nova_LinkedList_Nova_anyFunc, void* nova_standard_datastruct_list_Nova_LinkedList_ref_Nova_anyFunc)
{
	nova_standard_datastruct_list_Nova_LinkedListIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	nova_local_0 = nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_iterator(this, exceptionData);
	while (nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_local_0, exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_local_0, exceptionData));
		if (nova_standard_datastruct_list_Nova_LinkedList_Nova_anyFunc(nova_standard_datastruct_list_Nova_LinkedList_ref_Nova_anyFunc, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element)))
		{
			return 1;
		}
	}
	return 0;
}

char nova_standard_datastruct_list_Nova_LinkedList_0_Nova_all(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_LinkedList_closure12_Nova_allFunc nova_standard_datastruct_list_Nova_LinkedList_Nova_allFunc, void* nova_standard_datastruct_list_Nova_LinkedList_ref_Nova_allFunc)
{
	nova_standard_datastruct_list_Nova_LinkedListIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	nova_local_0 = nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_iterator(this, exceptionData);
	while (nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_local_0, exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_local_0, exceptionData));
		if (!nova_standard_datastruct_list_Nova_LinkedList_Nova_allFunc(nova_standard_datastruct_list_Nova_LinkedList_ref_Nova_allFunc, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element)))
		{
			return 0;
		}
	}
	return 1;
}

nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedList_0_Nova_filter(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_LinkedList_closure15_Nova_filterFunc nova_standard_datastruct_list_Nova_LinkedList_Nova_filterFunc, void* nova_standard_datastruct_list_Nova_LinkedList_ref_Nova_filterFunc)
{
	nova_standard_datastruct_list_Nova_LinkedList* l1_Nova_filtered;
	nova_standard_datastruct_list_Nova_LinkedListIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_filtered = nova_standard_datastruct_list_Nova_LinkedList_0_Nova_construct(0, exceptionData);
	nova_local_0 = nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_iterator(this, exceptionData);
	while (nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_local_0, exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_local_0, exceptionData));
		if (nova_standard_datastruct_list_Nova_LinkedList_Nova_filterFunc(nova_standard_datastruct_list_Nova_LinkedList_ref_Nova_filterFunc, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element)))
		{
			nova_standard_datastruct_list_Nova_LinkedList_Nova_add(l1_Nova_filtered, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element));
		}
	}
	return l1_Nova_filtered;
}

nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedList_0_Nova_take(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_LinkedList_Nova_howMany)
{
	nova_standard_datastruct_list_Nova_LinkedList* l1_Nova_list;
	nova_standard_datastruct_list_Nova_LinkedListIterator* nova_local_0;
	nova_standard_Nova_Object* l2_Nova_element;
	
	if (nova_standard_datastruct_list_Nova_LinkedList_Nova_howMany > this->nova_standard_datastruct_list_Nova_LinkedList_Nova_size)
	{
		nova_standard_datastruct_list_Nova_LinkedList_Nova_howMany = this->nova_standard_datastruct_list_Nova_LinkedList_Nova_size;
	}
	l1_Nova_list = nova_standard_datastruct_list_Nova_LinkedList_0_Nova_construct(0, exceptionData);
	nova_local_0 = nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_iterator(this, exceptionData);
	while (nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_local_0, exceptionData))
	{
		l2_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_local_0, exceptionData));
		if (l1_Nova_list->nova_standard_datastruct_list_Nova_LinkedList_Nova_size == nova_standard_datastruct_list_Nova_LinkedList_Nova_howMany)
		{
			break;
		}
		nova_standard_datastruct_list_Nova_LinkedList_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(l2_Nova_element));
	}
	return l1_Nova_list;
}

nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedList_0_Nova_skip(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_list_Nova_LinkedList_Nova_howMany)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list;
	int l1_Nova_i;
	nova_standard_datastruct_list_Nova_LinkedListIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_list = nova_standard_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData);
	l1_Nova_i = 0;
	nova_local_0 = nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_iterator(this, exceptionData);
	while (nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_local_0, exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_local_0, exceptionData));
		if (l1_Nova_i++ > nova_standard_datastruct_list_Nova_LinkedList_Nova_howMany)
		{
			nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element));
		}
	}
	return (nova_standard_datastruct_list_Nova_LinkedList*)l1_Nova_list;
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_LinkedList_0_Nova_first(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_Nova_Object*)nova_standard_datastruct_list_Nova_LinkedList_Accessor0_Nova_first(this, exceptionData);
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_LinkedList_0_Nova_last(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_Nova_Object*)this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_current;
}

nova_standard_Nova_Object* nova_standard_datastruct_list_Nova_LinkedList_0_Nova_firstWhere(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_LinkedList_closure18_Nova_func nova_standard_datastruct_list_Nova_LinkedList_Nova_func, void* nova_standard_datastruct_list_Nova_LinkedList_ref_Nova_func)
{
	nova_standard_datastruct_list_Nova_LinkedListIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	nova_local_0 = nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_iterator(this, exceptionData);
	while (nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_local_0, exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_local_0, exceptionData));
		if (nova_standard_datastruct_list_Nova_LinkedList_Nova_func(nova_standard_datastruct_list_Nova_LinkedList_ref_Nova_func, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_element)))
		{
			return (nova_standard_Nova_Object*)l1_Nova_element;
		}
	}
	return (nova_standard_Nova_Object*)nova_null;
}

nova_standard_datastruct_list_Nova_LinkedList* nova_standard_datastruct_list_Nova_LinkedList_0_Nova_reverse(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_LinkedList* l1_Nova_list;
	nova_standard_datastruct_list_Nova_ListNode* l1_Nova_prev;
	nova_standard_datastruct_list_Nova_ListNode* l1_Nova_current;
	nova_standard_datastruct_list_Nova_ListNode* l1_Nova_next;
	
	l1_Nova_list = nova_standard_datastruct_list_Nova_LinkedList_0_Nova_construct(0, exceptionData);
	l1_Nova_prev = (nova_standard_datastruct_list_Nova_ListNode*)((nova_standard_Nova_Object*)nova_null);
	l1_Nova_current = (nova_standard_datastruct_list_Nova_ListNode*)((nova_standard_Nova_Object*)nova_null);
	l1_Nova_next = (nova_standard_datastruct_list_Nova_ListNode*)((nova_standard_Nova_Object*)nova_null);
	if (this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start != (nova_standard_datastruct_list_Nova_ListNode*)nova_null)
	{
		l1_Nova_current = nova_standard_datastruct_list_Nova_ListNode_Nova_clone(this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start, exceptionData);
	}
	while (l1_Nova_current != (nova_standard_datastruct_list_Nova_ListNode*)nova_null)
	{
		l1_Nova_next = l1_Nova_current->nova_standard_datastruct_list_Nova_ListNode_Nova_next;
		l1_Nova_current->nova_standard_datastruct_list_Nova_ListNode_Nova_next = (nova_standard_datastruct_list_Nova_ListNode*)((nova_standard_Nova_Object*)nova_null);
		if (l1_Nova_next != (nova_standard_datastruct_list_Nova_ListNode*)nova_null)
		{
			l1_Nova_next = nova_standard_datastruct_list_Nova_ListNode_Nova_clone(l1_Nova_next, exceptionData);
		}
		if (l1_Nova_prev != (nova_standard_datastruct_list_Nova_ListNode*)nova_null)
		{
			l1_Nova_current->nova_standard_datastruct_list_Nova_ListNode_Nova_next = nova_standard_datastruct_list_Nova_ListNode_Nova_clone(l1_Nova_prev, exceptionData);
		}
		l1_Nova_prev = l1_Nova_current;
		l1_Nova_current = l1_Nova_next;
	}
	l1_Nova_list->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start = l1_Nova_prev;
	return l1_Nova_list;
}

nova_standard_Nova_String* nova_standard_datastruct_list_Nova_LinkedList_0_Nova_join(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_datastruct_list_Nova_LinkedList_Nova_delimiter)
{
	nova_standard_Nova_String* l1_Nova_str;
	char l1_Nova_passed;
	nova_standard_datastruct_list_Nova_LinkedListIterator* nova_local_0;
	nova_standard_Nova_Object* l1_Nova_element;
	
	l1_Nova_str = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "");
	l1_Nova_passed = 0;
	nova_local_0 = nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_iterator(this, exceptionData);
	while (nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_local_0, exceptionData))
	{
		l1_Nova_element = (nova_standard_Nova_Object*)(nova_standard_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_local_0, exceptionData));
		if (l1_Nova_passed)
		{
			l1_Nova_str = nova_standard_Nova_String_0_Nova_concat(l1_Nova_str, exceptionData, nova_standard_datastruct_list_Nova_LinkedList_Nova_delimiter);
		}
		else
		{
			l1_Nova_passed = 1;
		}
		l1_Nova_str = nova_standard_Nova_String_0_Nova_concat(l1_Nova_str, exceptionData, nova_standard_Nova_Object_1_Nova_toString(l1_Nova_element, exceptionData));
	}
	return l1_Nova_str;
}

void nova_standard_datastruct_list_Nova_LinkedList_0_Nova_this(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

nova_standard_datastruct_list_Nova_LinkedListIterator* nova_standard_datastruct_list_Nova_LinkedList_Accessor_Nova_iterator(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_construct(0, exceptionData, this);
}


nova_standard_datastruct_list_Nova_ListNode* nova_standard_datastruct_list_Nova_LinkedList_Accessor0_Nova_first(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start;
}


void nova_standard_datastruct_list_Nova_LinkedList_Nova_super(nova_standard_datastruct_list_Nova_LinkedList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_list_Nova_LinkedList_Nova_size = 0;
	this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_start = (nova_standard_datastruct_list_Nova_ListNode*)nova_null;
	this->prv->nova_standard_datastruct_list_Nova_LinkedList_Nova_current = (nova_standard_datastruct_list_Nova_ListNode*)nova_null;
}

