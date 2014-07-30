#include <precompiled.h>
#include "NovaObject.h"


nova_VTable_Object nova_VTable_Object_val =
{
	nova_4_Object_toString,
	nova_2_Object_equals,
};

String* nova_Object_getHashCode(Object* this, ExceptionData* exceptionData);

Object* nova_Object_Object(Object* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Object, this,);
	
	this->vtable = &nova_VTable_Object_val;
	{
	}
	
	return this;
}

void nova_del_Object(Object** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

String* nova_Object_getHashCode(Object* this, ExceptionData* exceptionData)
{
	return nova_String_String(0, exceptionData, hashCode(this));
}

String* nova_4_Object_toString(Object* this, ExceptionData* exceptionData)
{
	return nova_String_concat(nova_String_String(0, exceptionData, (char*)("[Object @")), exceptionData, nova_String_concat(nova_Object_getHashCode(this, exceptionData), exceptionData, nova_String_String(0, exceptionData, (char*)("]"))));
}

char nova_2_Object_equals(Object* this, ExceptionData* exceptionData, Object* nova_0_another)
{
	return this == nova_0_another;
}
