#include <precompiled.h>
#include <nova/standard/primitive/nova_standard_primitive_Nova_Bool.h>

nova_standard_primitive_Extension_VTable_Bool nova_standard_primitive_Extension_VTable_Bool_val =
{
	{
		(int(*)(nova_standard_datastruct_Nova_Comparable*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_primitive_Nova_Bool_0_Nova_compareTo,
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
	nova_standard_primitive_Nova_Bool_3_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_primitive_Nova_Bool_0_Nova_compareTo,
};


void nova_standard_primitive_Nova_BoolNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_Nova_Bool* nova_standard_primitive_Nova_Bool_1_Nova_construct(nova_standard_primitive_Nova_Bool* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_primitive_Nova_Bool_Nova_value)
{
	CCLASS_NEW(nova_standard_primitive_Nova_Bool, this,);
	this->vtable = &nova_standard_primitive_Extension_VTable_Bool_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_primitive_Nova_Primitive_Nova_super((nova_standard_primitive_Nova_Primitive*)this, exceptionData);
	nova_standard_primitive_Nova_Bool_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_primitive_Nova_Bool_1_Nova_this(this, exceptionData, nova_standard_primitive_Nova_Bool_Nova_value);
	}
	
	return this;
}

void nova_standard_primitive_Nova_Bool_Nova_destroy(nova_standard_primitive_Nova_Bool** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_primitive_Nova_Bool_1_Nova_this(nova_standard_primitive_Nova_Bool* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_primitive_Nova_Bool_Nova_value)
{
	this->nova_standard_primitive_Nova_Bool_Nova_value = nova_standard_primitive_Nova_Bool_Nova_value;
}

nova_standard_Nova_String* nova_standard_primitive_Nova_Bool_2_Nova_toString(nova_standard_primitive_Nova_Bool* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_primitive_Nova_Bool_Nova_value)
{
	if (nova_standard_primitive_Nova_Bool_Nova_value)
	{
		return nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "true");
	}
	return nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "false");
}

nova_standard_Nova_String* nova_standard_primitive_Nova_Bool_3_Nova_toString(nova_standard_primitive_Nova_Bool* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_primitive_Nova_Bool_2_Nova_toString(this, exceptionData, this->nova_standard_primitive_Nova_Bool_Nova_value);
}

char nova_standard_primitive_Nova_Bool_0_Nova_compareTo(nova_standard_primitive_Nova_Bool* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_Nova_Bool* nova_standard_primitive_Nova_Bool_Nova_prim)
{
	char l1_Nova_other;
	
	l1_Nova_other = nova_standard_primitive_Nova_Bool_Nova_prim->nova_standard_primitive_Nova_Bool_Nova_value;
	return this->nova_standard_primitive_Nova_Bool_Nova_value == l1_Nova_other;
}

void nova_standard_primitive_Nova_Bool_0_Nova_super(nova_standard_primitive_Nova_Bool* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_primitive_Nova_Bool_Nova_value = 0;
}

