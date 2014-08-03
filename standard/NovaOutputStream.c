#include <precompiled.h>
#include "NovaOutputStream.h"


nova_VTable_OutputStream nova_VTable_OutputStream_val =
{
	nova_2_Object_toString,
	nova_2_Object_equals,
};
CCLASS_PRIVATE
(
	File* nova_OutputStream_stream;
	
)

OutputStream* nova_OutputStream_construct(OutputStream* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(OutputStream, this);
	
	this->prv->nova_OutputStream_stream = (File*)0;
	this->vtable = &nova_VTable_OutputStream_val;
	{
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


