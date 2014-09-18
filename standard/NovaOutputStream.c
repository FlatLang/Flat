#include <precompiled.h>
#include "NovaOutputStream.h"


nova_VTable_OutputStream nova_VTable_OutputStream_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};
CCLASS_PRIVATE
(
	File* nova_OutputStream_stream;
	
)

OutputStream* nova_0_OutputStream_construct(OutputStream* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(OutputStream, this);
	this->vtable = &nova_VTable_OutputStream_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_OutputStream_super(this, 0);
	
	{
		nova_OutputStream_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_OutputStream(OutputStream** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_File(&(*this)->prv->nova_OutputStream_stream, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}



void nova_OutputStream_this(OutputStream* this, ExceptionData* exceptionData)
{
}

void nova_OutputStream_super(OutputStream* this, ExceptionData* exceptionData)
{
	this->prv->nova_OutputStream_stream = (File*)nova_null;
}
