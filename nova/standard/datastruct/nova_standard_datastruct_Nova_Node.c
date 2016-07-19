#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Node.h>

nova_standard_datastruct_Extension_VTable_Node nova_standard_datastruct_Extension_VTable_Node_val =
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
	nova_standard_datastruct_Nova_Node_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



char nova_standard_datastruct_Nova_Node_Nova_notNull(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Node* nova_standard_datastruct_Nova_Node_Nova_value);
void nova_standard_datastruct_Nova_Node_1_Nova_preorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_Nova_array);
nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_1_Nova_inorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_Nova_array);
void nova_standard_datastruct_Nova_Node_1_Nova_postorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_Nova_array);
void nova_standard_datastruct_Nova_Node_1_Nova_levelorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_Nova_array);
void nova_standard_datastruct_Nova_NodeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Node* nova_standard_datastruct_Nova_Node_0_Nova_Node(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Node, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Node_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Node_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Node_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_standard_datastruct_Nova_Node* nova_standard_datastruct_Nova_Node_1_Nova_Node(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_Node_Nova_data)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Node, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Node_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Node_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Node_3_Nova_this(this, exceptionData, nova_standard_datastruct_Nova_Node_Nova_data);
	}
	
	return this;
}

nova_standard_datastruct_Nova_Node* nova_standard_datastruct_Nova_Node_2_Nova_Node(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_Nova_Node_Nova_numChildren)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Node, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Node_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Node_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Node_3_Nova_this(this, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, nova_standard_datastruct_Nova_Node_Nova_numChildren)));
	}
	
	return this;
}

nova_standard_datastruct_Nova_Node* nova_standard_datastruct_Nova_Node_3_Nova_Node(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_Node_Nova_data, int nova_standard_datastruct_Nova_Node_Nova_numChildren)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Node, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Node_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Node_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Node_5_Nova_this(this, exceptionData, nova_standard_datastruct_Nova_Node_Nova_data, nova_standard_datastruct_Nova_Node_Nova_numChildren);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_Node_Nova_destroy(nova_standard_datastruct_Nova_Node** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_standard_datastruct_list_Nova_Array_Nova_destroy(&(*this)->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_Node_2_Nova_this(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_Nova_Node_3_Nova_this(this, exceptionData, (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null));
}

void nova_standard_datastruct_Nova_Node_3_Nova_this(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_Node_Nova_data)
{
	this->nova_standard_datastruct_Nova_Node_Nova_children = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	this->nova_standard_datastruct_Nova_Node_Nova_data = nova_standard_datastruct_Nova_Node_Nova_data;
}

void nova_standard_datastruct_Nova_Node_4_Nova_this(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_Nova_Node_Nova_numChildren)
{
	nova_standard_datastruct_Nova_Node_5_Nova_this(this, exceptionData, (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null), nova_standard_datastruct_Nova_Node_Nova_numChildren);
}

void nova_standard_datastruct_Nova_Node_5_Nova_this(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_Node_Nova_data, int nova_standard_datastruct_Nova_Node_Nova_numChildren)
{
	this->nova_standard_datastruct_Nova_Node_Nova_children = nova_standard_datastruct_list_Nova_Array_1_Nova_Array(0, exceptionData, nova_standard_datastruct_Nova_Node_Nova_numChildren);
	this->nova_standard_datastruct_Nova_Node_Nova_data = nova_standard_datastruct_Nova_Node_Nova_data;
}

char nova_standard_datastruct_Nova_Node_Nova_notNull(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Node* nova_standard_datastruct_Nova_Node_Nova_value)
{
	return nova_standard_datastruct_Nova_Node_Nova_value != (nova_standard_datastruct_Nova_Node*)nova_null;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_0_Nova_preorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_array = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	nova_standard_datastruct_Nova_Node_1_Nova_preorder(this, exceptionData, l1_Nova_array);
	return l1_Nova_array;
}

void nova_standard_datastruct_Nova_Node_1_Nova_preorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_Nova_array)
{
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_datastruct_Nova_Node* l1_Nova_child = (nova_standard_datastruct_Nova_Node*)nova_null;
	
	nova_standard_datastruct_list_Nova_Array_0_Nova_add(nova_standard_datastruct_Nova_Node_Nova_array, exceptionData, this->nova_standard_datastruct_Nova_Node_Nova_data);
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(this->nova_standard_datastruct_Nova_Node_Nova_children), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&nova_standard_datastruct_Nova_Node_Nova_notNull, this))), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_child = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_standard_datastruct_Nova_Node_1_Nova_preorder(l1_Nova_child, exceptionData, nova_standard_datastruct_Nova_Node_Nova_array);
	}
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_0_Nova_inorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_array = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	return nova_standard_datastruct_Nova_Node_1_Nova_inorder(this, exceptionData, l1_Nova_array);
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_1_Nova_inorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_Nova_array)
{
	int l1_Nova_half = 0;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_datastruct_Nova_Node* l1_Nova_child1 = (nova_standard_datastruct_Nova_Node*)nova_null;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_1 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_datastruct_Nova_Node* l2_Nova_child = (nova_standard_datastruct_Nova_Node*)nova_null;
	
	l1_Nova_half = (int)(nova_standard_math_Nova_Math_Nova_ceil(0, exceptionData, this->nova_standard_datastruct_Nova_Node_Nova_children->nova_standard_datastruct_list_Nova_Array_Nova_count / 2.0));
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_take((nova_standard_datastruct_list_Nova_List*)(this->nova_standard_datastruct_Nova_Node_Nova_children), exceptionData, l1_Nova_half)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&nova_standard_datastruct_Nova_Node_Nova_notNull, this))), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_child1 = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_standard_datastruct_Nova_Node_1_Nova_inorder(l1_Nova_child1, exceptionData, nova_standard_datastruct_Nova_Node_Nova_array);
	}
	nova_standard_datastruct_list_Nova_Array_0_Nova_add(nova_standard_datastruct_Nova_Node_Nova_array, exceptionData, this->nova_standard_datastruct_Nova_Node_Nova_data);
	nova_local_1 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_skip((nova_standard_datastruct_list_Nova_List*)(this->nova_standard_datastruct_Nova_Node_Nova_children), exceptionData, l1_Nova_half)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&nova_standard_datastruct_Nova_Node_Nova_notNull, this))), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData))
	{
		l2_Nova_child = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_1), exceptionData));
		nova_standard_datastruct_Nova_Node_1_Nova_inorder(l2_Nova_child, exceptionData, nova_standard_datastruct_Nova_Node_Nova_array);
	}
	return nova_standard_datastruct_Nova_Node_Nova_array;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_0_Nova_postorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_array = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	nova_standard_datastruct_Nova_Node_1_Nova_postorder(this, exceptionData, l1_Nova_array);
	return l1_Nova_array;
}

void nova_standard_datastruct_Nova_Node_1_Nova_postorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_Nova_array)
{
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
	nova_standard_datastruct_Nova_Node* l1_Nova_child = (nova_standard_datastruct_Nova_Node*)nova_null;
	
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(this->nova_standard_datastruct_Nova_Node_Nova_children), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&nova_standard_datastruct_Nova_Node_Nova_notNull, this))), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l1_Nova_child = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		nova_standard_datastruct_Nova_Node_1_Nova_postorder(l1_Nova_child, exceptionData, nova_standard_datastruct_Nova_Node_Nova_array);
	}
	nova_standard_datastruct_list_Nova_Array_0_Nova_add(nova_standard_datastruct_Nova_Node_Nova_array, exceptionData, this->nova_standard_datastruct_Nova_Node_Nova_data);
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_0_Nova_levelorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_array = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	nova_standard_datastruct_Nova_Node_1_Nova_levelorder(this, exceptionData, l1_Nova_array);
	return l1_Nova_array;
}

void nova_standard_datastruct_Nova_Node_1_Nova_levelorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_Nova_array)
{
	nova_standard_datastruct_list_Nova_Queue* l1_Nova_queue = (nova_standard_datastruct_list_Nova_Queue*)nova_null;
	
	l1_Nova_queue = nova_standard_datastruct_list_Nova_Queue_Nova_Queue(0, exceptionData);
	nova_standard_datastruct_list_Nova_Queue_Nova_enqueue(l1_Nova_queue, exceptionData, (nova_standard_Nova_Object*)(this));
	while (!nova_standard_datastruct_list_Nova_Queue_Accessor_Nova_empty(l1_Nova_queue, exceptionData))
	{
		nova_standard_datastruct_Nova_Node* l1_Nova_current = (nova_standard_datastruct_Nova_Node*)nova_null;
		nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)nova_null;
		nova_standard_datastruct_Nova_Node* l2_Nova_child = (nova_standard_datastruct_Nova_Node*)nova_null;
		
		l1_Nova_current = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_list_Nova_Queue_Nova_dequeue(l1_Nova_queue, exceptionData));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(nova_standard_datastruct_Nova_Node_Nova_array, exceptionData, l1_Nova_current->nova_standard_datastruct_Nova_Node_Nova_data);
		nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)((nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(l1_Nova_current->nova_standard_datastruct_Nova_Node_Nova_children), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&nova_standard_datastruct_Nova_Node_Nova_notNull, this))), exceptionData));
		while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
		{
			l2_Nova_child = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
			nova_standard_datastruct_list_Nova_Queue_Nova_enqueue(l1_Nova_queue, exceptionData, (nova_standard_Nova_Object*)(l2_Nova_child));
		}
	}
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Node_0_Nova_toString(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_Nova_String*)(nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)(this->nova_standard_datastruct_Nova_Node_Nova_data), exceptionData));
}

void nova_standard_datastruct_Nova_Node_Nova_super(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_Node_Nova_data = (nova_standard_Nova_Object*)nova_null;
	this->nova_standard_datastruct_Nova_Node_Nova_children = (nova_standard_datastruct_list_Nova_Array*)nova_null;
}

