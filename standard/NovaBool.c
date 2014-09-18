#include <precompiled.h>
#include "NovaBool.h"


nova_VTable_Bool nova_VTable_Bool_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

Bool* nova_0_Bool_construct(Bool* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Bool, this,);
	this->vtable = &nova_VTable_Bool_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Bool_super(this, 0);
	
	{
		nova_Bool_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_Bool(Bool** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_Bool_this(Bool* this, ExceptionData* exceptionData)
{
}

void nova_Bool_super(Bool* this, ExceptionData* exceptionData)
{
}
