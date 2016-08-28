#include <precompiled.h>
#include <nova/datastruct/nova_datastruct_Nova_BinaryNode.h>



nova_datastruct_Extension_VTable_BinaryNode nova_datastruct_Extension_VTable_BinaryNode_val =
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
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_datastruct_Nova_Node_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};



nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Nova_getNode(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_BinaryNode_Nova_index);
void nova_datastruct_Nova_BinaryNode_Nova_setNode(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_BinaryNode_Nova_index, nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Nova_data);
nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Mutator_Nova_left(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Nova_value);
nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Mutator_Nova_right(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Nova_value);
void nova_datastruct_Nova_BinaryNode_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_0_Nova_BinaryNode(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_datastruct_Nova_BinaryNode, this,);
	this->vtable = &nova_datastruct_Extension_VTable_BinaryNode_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_Node_Nova_super((nova_datastruct_Nova_Node*)this, exceptionData);
	nova_datastruct_Nova_BinaryNode_0_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_Nova_BinaryNode_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_1_Nova_BinaryNode(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Comparable* nova_datastruct_Nova_BinaryNode_Nova_data)
{
	CCLASS_NEW(nova_datastruct_Nova_BinaryNode, this,);
	this->vtable = &nova_datastruct_Extension_VTable_BinaryNode_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_Node_Nova_super((nova_datastruct_Nova_Node*)this, exceptionData);
	nova_datastruct_Nova_BinaryNode_0_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_Nova_BinaryNode_0_Nova_this(this, exceptionData, nova_datastruct_Nova_BinaryNode_Nova_data);
	}
	
	return this;
}

nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_2_Nova_BinaryNode(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Comparable* nova_datastruct_Nova_BinaryNode_Nova_data, int nova_datastruct_Nova_BinaryNode_Nova_size)
{
	CCLASS_NEW(nova_datastruct_Nova_BinaryNode, this,);
	this->vtable = &nova_datastruct_Extension_VTable_BinaryNode_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_Node_Nova_super((nova_datastruct_Nova_Node*)this, exceptionData);
	nova_datastruct_Nova_BinaryNode_0_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_Nova_BinaryNode_1_Nova_this(this, exceptionData, nova_datastruct_Nova_BinaryNode_Nova_data, nova_datastruct_Nova_BinaryNode_Nova_size);
	}
	
	return this;
}

void nova_datastruct_Nova_BinaryNode_Nova_destroy(nova_datastruct_Nova_BinaryNode** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_datastruct_Nova_BinaryNode_Nova_this(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_datastruct_Nova_Node_3_Nova_this((nova_datastruct_Nova_Node*)(this), exceptionData, (nova_Nova_Object*)((nova_Nova_Object*)nova_null));
}

void nova_datastruct_Nova_BinaryNode_0_Nova_this(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Comparable* nova_datastruct_Nova_BinaryNode_Nova_data)
{
	nova_datastruct_Nova_BinaryNode_1_Nova_this(this, exceptionData, nova_datastruct_Nova_BinaryNode_Nova_data, 2);
}

void nova_datastruct_Nova_BinaryNode_1_Nova_this(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Comparable* nova_datastruct_Nova_BinaryNode_Nova_data, int nova_datastruct_Nova_BinaryNode_Nova_size)
{
	nova_datastruct_Nova_Node_5_Nova_this((nova_datastruct_Nova_Node*)(this), exceptionData, (nova_Nova_Object*)(nova_datastruct_Nova_BinaryNode_Nova_data), nova_datastruct_Nova_BinaryNode_Nova_size);
}

nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Nova_getNode(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_BinaryNode_Nova_index)
{
	if (this->nova_datastruct_Nova_Node_Nova_children->nova_datastruct_list_Nova_Array_Nova_count <= nova_datastruct_Nova_BinaryNode_Nova_index)
	{
		return (nova_datastruct_Nova_BinaryNode*)nova_null;
	}
	return (nova_datastruct_Nova_BinaryNode*)(nova_datastruct_Nova_BinaryNode*)nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(this->nova_datastruct_Nova_Node_Nova_children), exceptionData, nova_datastruct_Nova_BinaryNode_Nova_index);
}

void nova_datastruct_Nova_BinaryNode_Nova_setNode(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_BinaryNode_Nova_index, nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Nova_data)
{
	if (this->nova_datastruct_Nova_Node_Nova_children->nova_datastruct_list_Nova_Array_Nova_count <= nova_datastruct_Nova_BinaryNode_Nova_index)
	{
		nova_datastruct_list_Nova_Array_1_Nova_add(this->nova_datastruct_Nova_Node_Nova_children, exceptionData, nova_datastruct_Nova_BinaryNode_Nova_index, (nova_Nova_Object*)(nova_datastruct_Nova_BinaryNode_Nova_data));
	}
	else
	{
		nova_datastruct_list_Nova_Array_Nova_set(this->nova_datastruct_Nova_Node_Nova_children, exceptionData, nova_datastruct_Nova_BinaryNode_Nova_index, (nova_Nova_Object*)(nova_datastruct_Nova_BinaryNode_Nova_data));
	}
}

void nova_datastruct_Nova_BinaryNode_Nova_addChild(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Comparable* nova_datastruct_Nova_BinaryNode_Nova_data)
{
	if (nova_datastruct_Nova_Comparable_virtual0_Nova_compareTo((nova_datastruct_Nova_Comparable*)(this->nova_datastruct_Nova_Node_Nova_data), exceptionData, (nova_Nova_Object*)(nova_datastruct_Nova_BinaryNode_Nova_data)) >= 0)
	{
		if (nova_datastruct_Nova_BinaryNode_Accessor_Nova_left(this, exceptionData) == (nova_datastruct_Nova_BinaryNode*)nova_null)
		{
			nova_datastruct_Nova_BinaryNode_Mutator_Nova_left(this, exceptionData, nova_datastruct_Nova_BinaryNode_1_Nova_BinaryNode(0, exceptionData, nova_datastruct_Nova_BinaryNode_Nova_data));
		}
		else
		{
			nova_datastruct_Nova_BinaryNode_Nova_addChild(nova_datastruct_Nova_BinaryNode_Accessor_Nova_left(this, exceptionData), exceptionData, nova_datastruct_Nova_BinaryNode_Nova_data);
		}
	}
	else
	{
		if (nova_datastruct_Nova_BinaryNode_Accessor_Nova_right(this, exceptionData) == (nova_datastruct_Nova_BinaryNode*)nova_null)
		{
			nova_datastruct_Nova_BinaryNode_Mutator_Nova_right(this, exceptionData, nova_datastruct_Nova_BinaryNode_1_Nova_BinaryNode(0, exceptionData, nova_datastruct_Nova_BinaryNode_Nova_data));
		}
		else
		{
			nova_datastruct_Nova_BinaryNode_Nova_addChild(nova_datastruct_Nova_BinaryNode_Accessor_Nova_right(this, exceptionData), exceptionData, nova_datastruct_Nova_BinaryNode_Nova_data);
		}
	}
}

nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Accessor_Nova_left(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_datastruct_Nova_BinaryNode_Nova_getNode(this, exceptionData, 0);
}

nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Mutator_Nova_left(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Nova_value)
{
	nova_datastruct_Nova_BinaryNode_Nova_setNode(this, exceptionData, 0, nova_datastruct_Nova_BinaryNode_Nova_value);
	return nova_datastruct_Nova_BinaryNode_Nova_value;
}

nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Accessor_Nova_right(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_datastruct_Nova_BinaryNode_Nova_getNode(this, exceptionData, 1);
}

nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Mutator_Nova_right(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryNode_Nova_value)
{
	nova_datastruct_Nova_BinaryNode_Nova_setNode(this, exceptionData, 1, nova_datastruct_Nova_BinaryNode_Nova_value);
	return nova_datastruct_Nova_BinaryNode_Nova_value;
}

void nova_datastruct_Nova_BinaryNode_0_Nova_super(nova_datastruct_Nova_BinaryNode* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

