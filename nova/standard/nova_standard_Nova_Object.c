#include <precompiled.h>
#include <nova/standard/nova_standard_Nova_Object.h>

nova_standard_Extension_VTable_Object nova_standard_Extension_VTable_Object_val =
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
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_Nova_ObjectNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_Nova_Object* nova_standard_Nova_Object_0_Nova_construct(nova_standard_Nova_Object* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_Nova_Object, this,);
	this->vtable = &nova_standard_Extension_VTable_Object_val;
	nova_standard_Nova_Object_Nova_super(this, exceptionData);
	
	{
		nova_standard_Nova_Object_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_Nova_Object_Nova_destroy(nova_standard_Nova_Object** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_standard_Nova_String* nova_standard_Nova_Object_Nova_getHashCode(nova_standard_Nova_Object* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_Nova_String_2_Nova_construct(0, exceptionData, (char*)(hashCode(this)));
}

long nova_standard_Nova_Object_0_Nova_getHashCodeLong(nova_standard_Nova_Object* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (long)strtol(hashCode(this), NULL, (int)(16));
}

nova_standard_Nova_String* nova_standard_Nova_Object_1_Nova_toString(nova_standard_Nova_Object* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* nova_local_0;
	
	nova_local_0 = nova_standard_Nova_Object_Nova_getHashCode(this, exceptionData);
	return nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "[Object @"), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "]")));
}

char nova_standard_Nova_Object_0_Nova_equals(nova_standard_Nova_Object* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_Nova_Object_Nova_another)
{
	return this == nova_standard_Nova_Object_Nova_another;
}

void nova_standard_Nova_Object_0_Nova_this(nova_standard_Nova_Object* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_Nova_Object_Nova_super(nova_standard_Nova_Object* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

