#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Node.h>

nova_standard_datastruct_Extension_VTable_Node nova_standard_datastruct_Extension_VTable_Node_val =
{
	{
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_datastruct_Nova_Node_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_datastruct_Nova_Node_0_Nova_inorder,
	nova_standard_datastruct_Nova_Node_0_Nova_postorder,
};



void nova_standard_datastruct_Nova_Node_1_Nova_preorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_Nova_array);
void nova_standard_datastruct_Nova_NodeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Node* nova_standard_datastruct_Nova_Node_2_Nova_construct(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
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

nova_standard_datastruct_Nova_Node* nova_standard_datastruct_Nova_Node_3_Nova_construct(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_Node_Nova_data)
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

nova_standard_datastruct_Nova_Node* nova_standard_datastruct_Nova_Node_4_Nova_construct(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_Nova_Node_Nova_numChildren)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Node, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Node_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Node_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Node_3_Nova_this(this, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_construct(0, exceptionData, nova_standard_datastruct_Nova_Node_Nova_numChildren)));
	}
	
	return this;
}

nova_standard_datastruct_Nova_Node* nova_standard_datastruct_Nova_Node_5_Nova_construct(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_Node_Nova_data, int nova_standard_datastruct_Nova_Node_Nova_numChildren)
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
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_Node_2_Nova_this(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_Nova_Node_3_Nova_this(this, exceptionData, (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null));
}

void nova_standard_datastruct_Nova_Node_3_Nova_this(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_Node_Nova_data)
{
	this->nova_standard_datastruct_Nova_Node_Nova_children = nova_standard_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData);
	this->nova_standard_datastruct_Nova_Node_Nova_data = nova_standard_datastruct_Nova_Node_Nova_data;
}

void nova_standard_datastruct_Nova_Node_4_Nova_this(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_Nova_Node_Nova_numChildren)
{
	nova_standard_datastruct_Nova_Node_5_Nova_this(this, exceptionData, (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null), nova_standard_datastruct_Nova_Node_Nova_numChildren);
}

void nova_standard_datastruct_Nova_Node_5_Nova_this(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_Node_Nova_data, int nova_standard_datastruct_Nova_Node_Nova_numChildren)
{
	this->nova_standard_datastruct_Nova_Node_Nova_children = nova_standard_datastruct_list_Nova_Array_3_Nova_construct(0, exceptionData, nova_standard_datastruct_Nova_Node_Nova_numChildren);
	this->nova_standard_datastruct_Nova_Node_Nova_data = nova_standard_datastruct_Nova_Node_Nova_data;
}

nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_0_Nova_preorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_array;
	
	l1_Nova_array = nova_standard_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData);
	nova_standard_datastruct_Nova_Node_1_Nova_preorder(this, exceptionData, l1_Nova_array);
	return l1_Nova_array;
}

void nova_standard_datastruct_Nova_Node_1_Nova_preorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* nova_standard_datastruct_Nova_Node_Nova_array)
{
	int l2_Nova_i;
	
	nova_standard_datastruct_list_Nova_Array_0_Nova_add(nova_standard_datastruct_Nova_Node_Nova_array, exceptionData, this->nova_standard_datastruct_Nova_Node_Nova_data);
	l2_Nova_i = 0;
	for (; l2_Nova_i < this->nova_standard_datastruct_Nova_Node_Nova_children->nova_standard_datastruct_list_Nova_Array_Nova_size; l2_Nova_i++)
	{
		nova_standard_datastruct_Nova_Node* l2_Nova_child;
		
		l2_Nova_child = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_list_Nova_Array_Nova_get(this->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData, l2_Nova_i));
		if (l2_Nova_child != (nova_standard_datastruct_Nova_Node*)nova_null && l2_Nova_child != 0)
		{
			nova_standard_datastruct_Nova_Node_1_Nova_preorder(l2_Nova_child, exceptionData, nova_standard_datastruct_Nova_Node_Nova_array);
		}
	}
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Node_0_Nova_inorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_str;
	int l2_Nova_i;
	int l4_Nova_i;
	
	l1_Nova_str = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "");
	l2_Nova_i = 0;
	for (; l2_Nova_i < this->nova_standard_datastruct_Nova_Node_Nova_children->nova_standard_datastruct_list_Nova_Array_Nova_size / 2; l2_Nova_i++)
	{
		nova_standard_datastruct_Nova_Node* l2_Nova_child;
		nova_standard_Nova_String* nova_local_0;
		
		l2_Nova_child = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_list_Nova_Array_Nova_get(this->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData, l2_Nova_i));
		nova_local_0 = l2_Nova_child->vtable->nova_standard_datastruct_Nova_Node_virtual0_Nova_inorder(l2_Nova_child, exceptionData);
		l1_Nova_str = l1_Nova_str->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_str, exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", ")));
	}
	l1_Nova_str = l1_Nova_str->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_str, exceptionData, ((nova_standard_Nova_Object*)this->nova_standard_datastruct_Nova_Node_Nova_data)->vtable->nova_standard_Nova_Object_virtual0_Nova_toString(this->nova_standard_datastruct_Nova_Node_Nova_data, exceptionData));
	l4_Nova_i = this->nova_standard_datastruct_Nova_Node_Nova_children->nova_standard_datastruct_list_Nova_Array_Nova_size / 2;
	for (; l4_Nova_i < this->nova_standard_datastruct_Nova_Node_Nova_children->nova_standard_datastruct_list_Nova_Array_Nova_size; l4_Nova_i++)
	{
		nova_standard_datastruct_Nova_Node* l4_Nova_child;
		
		l4_Nova_child = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_list_Nova_Array_Nova_get(this->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData, l4_Nova_i));
		l1_Nova_str = l1_Nova_str->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_str, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", "), exceptionData, l4_Nova_child->vtable->nova_standard_datastruct_Nova_Node_virtual0_Nova_inorder(l4_Nova_child, exceptionData)));
	}
	return l1_Nova_str;
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Node_0_Nova_postorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_str;
	int l2_Nova_i;
	
	l1_Nova_str = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "");
	l2_Nova_i = 0;
	for (; l2_Nova_i < this->nova_standard_datastruct_Nova_Node_Nova_children->nova_standard_datastruct_list_Nova_Array_Nova_size; l2_Nova_i++)
	{
		nova_standard_datastruct_Nova_Node* l2_Nova_child;
		nova_standard_Nova_String* nova_local_0;
		
		l2_Nova_child = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_list_Nova_Array_Nova_get(this->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData, l2_Nova_i));
		nova_local_0 = l2_Nova_child->vtable->nova_standard_datastruct_Nova_Node_virtual0_Nova_postorder(l2_Nova_child, exceptionData);
		l1_Nova_str = l1_Nova_str->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_str, exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", ")));
	}
	l1_Nova_str = l1_Nova_str->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_str, exceptionData, ((nova_standard_Nova_Object*)this->nova_standard_datastruct_Nova_Node_Nova_data)->vtable->nova_standard_Nova_Object_virtual0_Nova_toString(this->nova_standard_datastruct_Nova_Node_Nova_data, exceptionData));
	return l1_Nova_str;
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Node_Nova_levelorder(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_str;
	nova_standard_datastruct_list_Nova_Queue* l1_Nova_queue;
	
	l1_Nova_str = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "");
	l1_Nova_queue = nova_standard_datastruct_list_Nova_Queue_2_Nova_construct(0, exceptionData);
	nova_standard_datastruct_list_Nova_Queue_Nova_enqueue(l1_Nova_queue, exceptionData, (nova_standard_Nova_Object*)(this));
	while (!nova_standard_datastruct_list_Nova_Queue_Accessor_Nova_empty(l1_Nova_queue, exceptionData))
	{
		nova_standard_datastruct_Nova_Node* l1_Nova_current;
		int l4_Nova_i;
		
		l1_Nova_current = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_list_Nova_Queue_Nova_dequeue(l1_Nova_queue, exceptionData));
		if (l1_Nova_str->nova_standard_Nova_String_Nova_size > 0)
		{
			l1_Nova_str = l1_Nova_str->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_str, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", "));
		}
		l1_Nova_str = l1_Nova_str->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_str, exceptionData, ((nova_standard_Nova_Object*)l1_Nova_current->nova_standard_datastruct_Nova_Node_Nova_data)->vtable->nova_standard_Nova_Object_virtual0_Nova_toString(l1_Nova_current->nova_standard_datastruct_Nova_Node_Nova_data, exceptionData));
		l4_Nova_i = 0;
		for (; l4_Nova_i < l1_Nova_current->nova_standard_datastruct_Nova_Node_Nova_children->nova_standard_datastruct_list_Nova_Array_Nova_size; l4_Nova_i++)
		{
			nova_standard_datastruct_Nova_Node* l4_Nova_node;
			
			l4_Nova_node = (nova_standard_datastruct_Nova_Node*)(nova_standard_datastruct_list_Nova_Array_Nova_get(l1_Nova_current->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData, l4_Nova_i));
			if (l4_Nova_node != (nova_standard_datastruct_Nova_Node*)nova_null)
			{
				nova_standard_datastruct_list_Nova_Queue_Nova_enqueue(l1_Nova_queue, exceptionData, (nova_standard_Nova_Object*)(l4_Nova_node));
			}
		}
	}
	return l1_Nova_str;
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Node_0_Nova_toString(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return ((nova_standard_Nova_Object*)this->nova_standard_datastruct_Nova_Node_Nova_data)->vtable->nova_standard_Nova_Object_virtual0_Nova_toString(this->nova_standard_datastruct_Nova_Node_Nova_data, exceptionData);
}

void nova_standard_datastruct_Nova_Node_Nova_super(nova_standard_datastruct_Nova_Node* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_Node_Nova_data = (nova_standard_Nova_Object*)nova_null;
	this->nova_standard_datastruct_Nova_Node_Nova_children = (nova_standard_datastruct_list_Nova_Array*)nova_null;
}

