#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_BinaryNode.h>


nova_VTable_nova_standard_datastruct_Nova_BinaryNode nova_VTable_nova_standard_datastruct_Nova_BinaryNode_val =
{
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};

void nova_standard_datastruct_Nova_BinaryNode_Mutator_Nova_left(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_BinaryNode* l0_Nova_data);
void nova_standard_datastruct_Nova_BinaryNode_Mutator_Nova_right(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_BinaryNode* l0_Nova_data);
void nova_standard_datastruct_Nova_BinaryNodeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_2_Nova_construct(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_BinaryNode, this,);
	this->vtable = &nova_VTable_nova_standard_datastruct_Nova_BinaryNode_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Node_Nova_super((nova_standard_datastruct_Nova_Node*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_Node_Nova_this((nova_standard_datastruct_Nova_Node*)(this), exceptionData);
	nova_standard_datastruct_Nova_BinaryNode_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_BinaryNode_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_3_Nova_construct(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_BinaryNode, this,);
	this->vtable = &nova_VTable_nova_standard_datastruct_Nova_BinaryNode_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Node_Nova_super((nova_standard_datastruct_Nova_Node*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_Node_0_Nova_this((nova_standard_datastruct_Nova_Node*)(this), exceptionData, l0_Nova_data);
	nova_standard_datastruct_Nova_BinaryNode_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_BinaryNode_0_Nova_this(this, exceptionData, l0_Nova_data);
	}
	
	return this;
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_4_Nova_construct(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data, int l0_Nova_size)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_BinaryNode, this,);
	this->vtable = &nova_VTable_nova_standard_datastruct_Nova_BinaryNode_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Node_Nova_super((nova_standard_datastruct_Nova_Node*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_Node_2_Nova_this((nova_standard_datastruct_Nova_Node*)(this), exceptionData, l0_Nova_data, l0_Nova_size);
	nova_standard_datastruct_Nova_BinaryNode_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_BinaryNode_1_Nova_this(this, exceptionData, l0_Nova_data, l0_Nova_size);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_BinaryNode_Nova_destroy(nova_standard_datastruct_Nova_BinaryNode** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_BinaryNode_Nova_this(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_Nova_BinaryNode_0_Nova_this(this, exceptionData, (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null));
}

void nova_standard_datastruct_Nova_BinaryNode_0_Nova_this(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data)
{
	nova_standard_datastruct_Nova_BinaryNode_1_Nova_this(this, exceptionData, l0_Nova_data, 2);
}

void nova_standard_datastruct_Nova_BinaryNode_1_Nova_this(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data, int l0_Nova_size)
{
	nova_standard_datastruct_Nova_Node_2_Nova_this((nova_standard_datastruct_Nova_Node*)(this), exceptionData, l0_Nova_data, l0_Nova_size);
}

void nova_standard_datastruct_Nova_BinaryNode_Nova_addChild(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data)
{
	nova_standard_datastruct_Nova_Comparable* l1_Nova_temp;
	
	l1_Nova_temp = (nova_standard_datastruct_Nova_Comparable*)this->nova_standard_datastruct_Nova_Node_Nova_data;
	if (nova_standard_datastruct_Nova_Comparable_Nova_compareTo(l1_Nova_temp, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_construct(0, exceptionData, (int)l0_Nova_data))) < 0)
	{
		if (nova_standard_datastruct_Nova_BinaryNode_Accessor_Nova_left(this, exceptionData) == (nova_standard_datastruct_Nova_BinaryNode*)nova_null)
		{
			nova_standard_datastruct_Nova_BinaryNode_Mutator_Nova_left(this, exceptionData, nova_standard_datastruct_Nova_BinaryNode_3_Nova_construct(0, exceptionData, l0_Nova_data));
		}
		else
		{
			nova_standard_datastruct_Nova_BinaryNode_Accessor_Nova_left(this, exceptionData);
		}
	}
	else
	{
		if (nova_standard_datastruct_Nova_BinaryNode_Accessor_Nova_right(this, exceptionData) == (nova_standard_datastruct_Nova_BinaryNode*)nova_null)
		{
			nova_standard_datastruct_Nova_BinaryNode_Mutator_Nova_right(this, exceptionData, nova_standard_datastruct_Nova_BinaryNode_3_Nova_construct(0, exceptionData, l0_Nova_data));
		}
		else
		{
			nova_standard_datastruct_Nova_BinaryNode_Accessor_Nova_right(this, exceptionData);
		}
	}
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_Accessor_Nova_left(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_datastruct_Nova_BinaryNode*)((nova_standard_datastruct_Nova_Node*)nova_standard_datastruct_Nova_ArrayList_Nova_get(this->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData, 0));
}

void nova_standard_datastruct_Nova_BinaryNode_Mutator_Nova_left(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_BinaryNode* l0_Nova_data)
{
	nova_standard_datastruct_Nova_ArrayList_Nova_set(this->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData, 0, (nova_standard_Nova_Object*)(l0_Nova_data));
}

nova_standard_datastruct_Nova_BinaryNode* nova_standard_datastruct_Nova_BinaryNode_Accessor_Nova_right(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_datastruct_Nova_BinaryNode*)((nova_standard_datastruct_Nova_Node*)nova_standard_datastruct_Nova_ArrayList_Nova_get(this->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData, 1));
}

void nova_standard_datastruct_Nova_BinaryNode_Mutator_Nova_right(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_BinaryNode* l0_Nova_data)
{
	nova_standard_datastruct_Nova_ArrayList_Nova_set(this->nova_standard_datastruct_Nova_Node_Nova_children, exceptionData, 1, (nova_standard_Nova_Object*)(l0_Nova_data));
}

void nova_standard_datastruct_Nova_BinaryNode_Nova_super(nova_standard_datastruct_Nova_BinaryNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

