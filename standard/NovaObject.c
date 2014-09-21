#include <precompiled.h>
#include "NovaObject.h"


nova_VTable_Object nova_VTable_Object_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

String* nova_Object_getHashCode(Object* this, ExceptionData* exceptionData);

Object* nova_0_Object_construct(Object* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Object, this,);
	this->vtable = &nova_VTable_Object_val;
	nova_Object_super(this, 0);
	
	{
		nova_Object_this(this, exceptionData);
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
	return nova_String_construct(0, exceptionData, hashCode(this));
}

String* nova_0_Object_toString(Object* this, ExceptionData* exceptionData)
{
	String* nova_local_0;
	
	nova_local_0 = nova_Object_getHashCode(this, exceptionData);
	return nova_0_String_concat(nova_String_construct(0, exceptionData, "[Object @"), exceptionData, nova_local_0->vtable->nova_virtual_0_concat(nova_local_0, exceptionData, nova_String_construct(0, exceptionData, "]")));
}

char nova_0_Object_equals(Object* this, ExceptionData* exceptionData, Object* nova_0_another)
{
	return this == nova_0_another;
}

void nova_Object_this(Object* this, ExceptionData* exceptionData)
{
}

void nova_Object_super(Object* this, ExceptionData* exceptionData)
{
}
