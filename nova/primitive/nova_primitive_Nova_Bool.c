#include <precompiled.h>
#include <nova/primitive/nova_primitive_Nova_Bool.h>



nova_primitive_Extension_VTable_Bool nova_primitive_Extension_VTable_Bool_val =
{
	{
		(int(*)(nova_datastruct_Nova_Comparable*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_primitive_Nova_Bool_0_Nova_compareTo,
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
	nova_primitive_Nova_Bool_3_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	nova_primitive_Nova_Bool_0_Nova_compareTo,
};


void nova_primitive_Nova_Bool_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_primitive_Nova_Bool* nova_primitive_Nova_Bool_Nova_construct(nova_primitive_Nova_Bool* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_primitive_Nova_Bool_Nova_value)
{
	CCLASS_NEW(nova_primitive_Nova_Bool, this,);
	this->vtable = &nova_primitive_Extension_VTable_Bool_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_primitive_Nova_Primitive_Nova_super((nova_primitive_Nova_Primitive*)this, exceptionData);
	nova_primitive_Nova_Bool_0_Nova_super(this, exceptionData);
	
	{
		nova_primitive_Nova_Bool_1_Nova_this(this, exceptionData, nova_primitive_Nova_Bool_Nova_value);
	}
	
	return this;
}

void nova_primitive_Nova_Bool_Nova_destroy(nova_primitive_Nova_Bool** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE(*this);
}

void nova_primitive_Nova_Bool_1_Nova_this(nova_primitive_Nova_Bool* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_primitive_Nova_Bool_Nova_value)
{
	this->nova_primitive_Nova_Bool_Nova_value = nova_primitive_Nova_Bool_Nova_value;
}

nova_Nova_String* nova_primitive_Nova_Bool_2_Nova_toString(nova_primitive_Nova_Bool* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_primitive_Nova_Bool_Nova_value)
{
	if (nova_primitive_Nova_Bool_Nova_value)
	{
		return nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("true"));
	}
	return nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("false"));
}

nova_Nova_String* nova_primitive_Nova_Bool_3_Nova_toString(nova_primitive_Nova_Bool* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_primitive_Nova_Bool_2_Nova_toString(0, exceptionData, this->nova_primitive_Nova_Bool_Nova_value);
}

char nova_primitive_Nova_Bool_0_Nova_compareTo(nova_primitive_Nova_Bool* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_primitive_Nova_Bool_Nova_other)
{
	return this->nova_primitive_Nova_Bool_Nova_value == nova_primitive_Nova_Bool_Nova_other;
}

void nova_primitive_Nova_Bool_0_Nova_super(nova_primitive_Nova_Bool* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_primitive_Nova_Bool_Nova_value = 0;
}

