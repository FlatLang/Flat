#include <precompiled.h>
#include "NovaArray.h"


nova_VTable_Array nova_VTable_Array_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

Array* nova_0_Array_construct(Array* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Array, this,);
	this->vtable = &nova_VTable_Array_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Array_super(this, 0);
	
	{
		nova_Array_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_Array(Array** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_Array_this(Array* this, ExceptionData* exceptionData)
{
}

void nova_Array_super(Array* this, ExceptionData* exceptionData)
{
	this->nova_Array_length = 0;
}
