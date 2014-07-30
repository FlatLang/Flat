#include <precompiled.h>
#include "NovaInputStream.h"


nova_VTable_InputStream nova_VTable_InputStream_val =
{
	nova_2_Object_toString,
	nova_2_Object_equals,
};
CCLASS_PRIVATE
(
	File* nova_InputStream_stream;
	
)

InputStream* nova_InputStream_InputStream(InputStream* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(InputStream, this);
	
	this->prv->nova_InputStream_stream = (File*)0;
	this->vtable = &nova_VTable_InputStream_val;
	{
	}
	
	return this;
}

void nova_del_InputStream(InputStream** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_File(&(*this)->prv->nova_InputStream_stream, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}


