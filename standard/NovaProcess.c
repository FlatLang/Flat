#include <precompiled.h>
#include "NovaProcess.h"


nova_VTable_Process nova_VTable_Process_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

Process* nova_Process_construct(Process* this, ExceptionData* exceptionData, StreamReader* nova_0_reader)
{
	CCLASS_NEW(Process, this,);
	this->vtable = &nova_VTable_Process_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Process_super(this, 0);
	
	{
		nova_Process_this(this, exceptionData, nova_0_reader);
	}
	
	return this;
}

void nova_del_Process(Process** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_StreamReader(&(*this)->nova_Process_reader, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_Process_this(Process* this, ExceptionData* exceptionData, StreamReader* nova_0_reader)
{
	this->nova_Process_reader = nova_0_reader;
}

void nova_Process_super(Process* this, ExceptionData* exceptionData)
{
	this->nova_Process_reader = (StreamReader*)nova_null;
}
