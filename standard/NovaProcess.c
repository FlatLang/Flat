#include <precompiled.h>
#include "NovaProcess.h"


nova_VTable_Process nova_VTable_Process_val =
{
	nova_2_Object_toString,
	nova_2_Object_equals,
};

Process* nova_Process_construct(Process* this, ExceptionData* exceptionData, StreamReader* nova_0_reader)
{
	CCLASS_NEW(Process, this,);
	
	this->nova_Process_reader = (StreamReader*)0;
	this->vtable = &nova_VTable_Process_val;
	{
		this->nova_Process_reader = nova_0_reader;
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
