#include <precompiled.h>
#include "NovaArray.h"


nova_VTable_Array nova_VTable_Array_val =
{
	nova_4_Object_toString,
	nova_2_Object_equals,
};

Array* nova_Array_construct(Array* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Array, this,);
	
	this->nova_Array_length = 0;
	this->vtable = &nova_VTable_Array_val;
	{
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
