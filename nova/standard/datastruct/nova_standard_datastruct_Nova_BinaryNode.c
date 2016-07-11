#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_BinaryNode.h>

nova_standard_datastruct_Extension_VTable_BinaryNode nova_standard_datastruct_Extension_VTable_BinaryNode_val =
{
	{
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
		0,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_datastruct_Nova_Node_2_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_Nova_getNode(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_Nova_BinaryNode_Nova_index);
void nova_standard_datastruct_Nova_BinaryNode_Nova_setNode(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_Nova_BinaryNode_Nova_index, nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_Nova_data);
void nova_standard_datastruct_Nova_BinaryNode_Mutator_Nova_left(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_Nova_value);
void nova_standard_datastruct_Nova_BinaryNode_Mutator_Nova_right(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_Nova_value);
void nova_standard_datastruct_Nova_BinaryNodeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_0_Nova_BinaryNode(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_BinaryNode, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_BinaryNode_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Node_Nova_super((nova_standard_datastruct_Nova_Node*)this, exceptionData);
	nova_standard_datastruct_Nova_BinaryNode_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_BinaryNode_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_1_Nova_BinaryNode(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Comparable* nova_standard_datastruct_Nova_BinaryNode_Nova_data)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_BinaryNode, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_BinaryNode_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Node_Nova_super((nova_standard_datastruct_Nova_Node*)this, exceptionData);
	nova_standard_datastruct_Nova_BinaryNode_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_BinaryNode_0_Nova_this(this, exceptionData, nova_standard_datastruct_Nova_BinaryNode_Nova_data);
	}
	
	return this;
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_2_Nova_BinaryNode(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Comparable* nova_standard_datastruct_Nova_BinaryNode_Nova_data, int nova_standard_datastruct_Nova_BinaryNode_Nova_size)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_BinaryNode, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_BinaryNode_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Node_Nova_super((nova_standard_datastruct_Nova_Node*)this, exceptionData);
	nova_standard_datastruct_Nova_BinaryNode_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_BinaryNode_1_Nova_this(this, exceptionData, nova_standard_datastruct_Nova_BinaryNode_Nova_data, nova_standard_datastruct_Nova_BinaryNode_Nova_size);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_BinaryNode_Nova_destroy(nova_standard_datastruct_Nova_BinaryNode** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_BinaryNode_Nova_this(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_Nova_Node_3_Nova_this((nova_standard_datastruct_Nova_Node*)(this), exceptionData, (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null));
}

void nova_standard_datastruct_Nova_BinaryNode_0_Nova_this(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Comparable* nova_standard_datastruct_Nova_BinaryNode_Nova_data)
{
	nova_standard_datastruct_Nova_BinaryNode_1_Nova_this(this, exceptionData, nova_standard_datastruct_Nova_BinaryNode_Nova_data, 2);
}

void nova_standard_datastruct_Nova_BinaryNode_1_Nova_this(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Comparable* nova_standard_datastruct_Nova_BinaryNode_Nova_data, int nova_standard_datastruct_Nova_BinaryNode_Nova_size)
{
	nova_standard_datastruct_Nova_Node_5_Nova_this((nova_standard_datastruct_Nova_Node*)(this), exceptionData, (nova_standard_Nova_Object*)(nova_standard_datastruct_Nova_BinaryNode_Nova_data), nova_standard_datastruct_Nova_BinaryNode_Nova_size);
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_Nova_getNode(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_Nova_BinaryNode_Nova_index)
{
	if (this->nova_standard_datastruct_Nova_Node_Nova_children->nova_standard_datastruct_list_Nova_Array_Nova_size <= nova_standard_datastruct_Nova_BinaryNode_Nova_index)
	{
		return (nova_standard_datastruct_Nova_BinaryNode*)nova_null;
	}
	return (nova_standard_datastruct_Nova_BinaryNode*)nova_standard_datastruct_list_Nova_Array_Nova_get(this->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData, nova_standard_datastruct_Nova_BinaryNode_Nova_index);
}

void nova_standard_datastruct_Nova_BinaryNode_Nova_setNode(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_Nova_BinaryNode_Nova_index, nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_Nova_data)
{
	if (this->nova_standard_datastruct_Nova_Node_Nova_children->nova_standard_datastruct_list_Nova_Array_Nova_size <= nova_standard_datastruct_Nova_BinaryNode_Nova_index)
	{
		nova_standard_datastruct_list_Nova_Array_1_Nova_add(this->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData, nova_standard_datastruct_Nova_BinaryNode_Nova_index, (nova_standard_Nova_Object*)(nova_standard_datastruct_Nova_BinaryNode_Nova_data));
	}
	else
	{
		nova_standard_datastruct_list_Nova_Array_Nova_set(this->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData, nova_standard_datastruct_Nova_BinaryNode_Nova_index, (nova_standard_Nova_Object*)(nova_standard_datastruct_Nova_BinaryNode_Nova_data));
	}
}

void nova_standard_datastruct_Nova_BinaryNode_Nova_addChild(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Comparable* nova_standard_datastruct_Nova_BinaryNode_Nova_data)
{
	if (nova_standard_datastruct_Nova_Comparable_virtual0_Nova_compareTo((nova_standard_datastruct_Nova_Comparable*)(this->nova_standard_datastruct_Nova_Node_Nova_data), exceptionData, (nova_standard_Nova_Object*)(nova_standard_datastruct_Nova_BinaryNode_Nova_data)) >= 0)
	{
		if (nova_standard_datastruct_Nova_BinaryNode_Accessor_Nova_left(this, exceptionData) == (nova_standard_datastruct_Nova_BinaryNode*)nova_null)
		{
			nova_standard_datastruct_Nova_BinaryNode_Mutator_Nova_left(this, exceptionData, nova_standard_datastruct_Nova_BinaryNode_1_Nova_BinaryNode(0, exceptionData, nova_standard_datastruct_Nova_BinaryNode_Nova_data));
		}
		else
		{
			nova_standard_datastruct_Nova_BinaryNode_Nova_addChild(nova_standard_datastruct_Nova_BinaryNode_Accessor_Nova_left(this, exceptionData), exceptionData, nova_standard_datastruct_Nova_BinaryNode_Nova_data);
		}
	}
	else
	{
		if (nova_standard_datastruct_Nova_BinaryNode_Accessor_Nova_right(this, exceptionData) == (nova_standard_datastruct_Nova_BinaryNode*)nova_null)
		{
			nova_standard_datastruct_Nova_BinaryNode_Mutator_Nova_right(this, exceptionData, nova_standard_datastruct_Nova_BinaryNode_1_Nova_BinaryNode(0, exceptionData, nova_standard_datastruct_Nova_BinaryNode_Nova_data));
		}
		else
		{
			nova_standard_datastruct_Nova_BinaryNode_Nova_addChild(nova_standard_datastruct_Nova_BinaryNode_Accessor_Nova_right(this, exceptionData), exceptionData, nova_standard_datastruct_Nova_BinaryNode_Nova_data);
		}
	}
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_Accessor_Nova_left(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_Nova_BinaryNode_Nova_getNode(this, exceptionData, 0);
}

void nova_standard_datastruct_Nova_BinaryNode_Mutator_Nova_left(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_Nova_value)
{
	nova_standard_datastruct_Nova_BinaryNode_Nova_setNode(this, exceptionData, 0, nova_standard_datastruct_Nova_BinaryNode_Nova_value);
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_Accessor_Nova_right(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_Nova_BinaryNode_Nova_getNode(this, exceptionData, 1);
}

void nova_standard_datastruct_Nova_BinaryNode_Mutator_Nova_right(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_Nova_value)
{
	nova_standard_datastruct_Nova_BinaryNode_Nova_setNode(this, exceptionData, 1, nova_standard_datastruct_Nova_BinaryNode_Nova_value);
}

void nova_standard_datastruct_Nova_BinaryNode_0_Nova_super(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

