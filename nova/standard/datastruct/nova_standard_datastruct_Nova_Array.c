#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Array.h>


nova_VTable_nova_standard_datastruct_Nova_Array nova_VTable_nova_standard_datastruct_Nova_Array_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
void nova_standard_datastruct_Nova_ArrayNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Array* nova_standard_datastruct_Nova_Array_Nova_construct(nova_standard_datastruct_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_length)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Array, this,);
	this->vtable = &nova_VTable_nova_standard_datastruct_Nova_Array_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_Array_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Array_Nova_this(this, exceptionData, l0_Nova_length);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_Array_Nova_destroy(nova_standard_datastruct_Nova_Array** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_Array_Nova_this(nova_standard_datastruct_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_length)
{
	this->nova_standard_datastruct_Nova_Array_Nova_length = l0_Nova_length;
	*this->nova_standard_datastruct_Nova_Array_Nova_array = (void*)NOVA_MALLOC(sizeof(void) * l0_Nova_length);
}

void nova_standard_datastruct_Nova_Array_Nova_super(nova_standard_datastruct_Nova_Array* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_Array_Nova_length = 0;
	this->nova_standard_datastruct_Nova_Array_Nova_array = 0;
}

