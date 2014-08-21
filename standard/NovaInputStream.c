#include <precompiled.h>
#include "NovaInputStream.h"


nova_VTable_InputStream nova_VTable_InputStream_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};
CCLASS_PRIVATE
(
	File* nova_InputStream_stream;
	
)

InputStream* nova_2_InputStream_construct(InputStream* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(InputStream, this);
	this->vtable = &nova_VTable_InputStream_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_InputStream_super(this, 0);
	
	{
		nova_InputStream_this(this, exceptionData);
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



void nova_InputStream_this(InputStream* this, ExceptionData* exceptionData)
{
}

void nova_InputStream_super(InputStream* this, ExceptionData* exceptionData)
{
	this->prv->nova_InputStream_stream = (File*)0;
}
