#include <precompiled.h>
#include <nova/standard/primitive/nova_standard_primitive_Nova_Null.h>


nova_standard_primitive_VTable_Null nova_standard_primitive_VTable_Null_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_primitive_Nova_Null_Nova_toString,
	nova_standard_Nova_String_Nova_equals,
	nova_standard_primitive_Nova_Null_Nova_concat,
};
void nova_standard_primitive_Nova_NullNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_Nova_Null* nova_standard_primitive_Nova_Null_2_Nova_construct(nova_standard_primitive_Nova_Null* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_primitive_Nova_Null, this,);
	this->vtable = &nova_standard_primitive_VTable_Null_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_String_Nova_super((nova_standard_Nova_String*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_primitive_Nova_Null_2_Nova_super(this, exceptionData);
	
	{
		nova_standard_primitive_Nova_Null_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_primitive_Nova_Null_Nova_destroy(nova_standard_primitive_Nova_Null** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_standard_Nova_String* nova_standard_primitive_Nova_Null_Nova_toString(nova_standard_primitive_Nova_Null* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "null");
}

nova_standard_Nova_String* nova_standard_primitive_Nova_Null_Nova_concat(nova_standard_primitive_Nova_Null* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_other)
{
	return nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "null"), exceptionData, l0_Nova_other);
}

void nova_standard_primitive_Nova_Null_2_Nova_this(nova_standard_primitive_Nova_Null* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_primitive_Nova_Null_2_Nova_super(nova_standard_primitive_Nova_Null* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

