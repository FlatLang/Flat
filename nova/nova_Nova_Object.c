#include <precompiled.h>
#include <nova/nova_Nova_Object.h>



nova_Extension_VTable_Object nova_Extension_VTable_Object_val =
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
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};





void nova_Nova_Object_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_Nova_Object* nova_Nova_Object_Nova_construct(nova_Nova_Object* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_Nova_Object, this,);
	this->vtable = &nova_Extension_VTable_Object_val;
	nova_Nova_Object_Nova_super(this, exceptionData);
	
	{
		nova_Nova_Object_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_Nova_Object_Nova_destroy(nova_Nova_Object** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_Nova_String* nova_Nova_Object_0_Nova_toString(nova_Nova_Object* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("[Object @")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((nova_Nova_Object_Accessor_Nova_hashCode(this, exceptionData))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("]"))));
}

char nova_Nova_Object_0_Nova_equals(nova_Nova_Object* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_Nova_Object_Nova_another)
{
	return this == nova_Nova_Object_Nova_another;
}

void nova_Nova_Object_0_Nova_this(nova_Nova_Object* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

nova_Nova_String* nova_Nova_Object_Accessor_Nova_hashCode(nova_Nova_Object* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_1_Nova_construct(0, exceptionData, hashCode(this));
}


long_long nova_Nova_Object_Accessor_Nova_hashCodeLong(nova_Nova_Object* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (long_long)strtol(hashCode(this), NULL, 16);
}


void nova_Nova_Object_Nova_super(nova_Nova_Object* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

nova_Nova_String* nova_Nova_Object_virtual1_Nova_toString(nova_Nova_Object* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(this), exceptionData);
}

long_long nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong(nova_Nova_Object* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong((nova_Nova_Object*)(this), exceptionData);
}

