#include <precompiled.h>
#include "NovaThreadImplementation.h"


nova_VTable_ThreadImplementation nova_VTable_ThreadImplementation_val =
{
	nova_0_ThreadImplementation_run,
};
CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_Thread_handle;
	
	int nova_ThreadImplementation_times;
	int nova_ThreadImplementation_millis;
	
)

ThreadImplementation* nova_ThreadImplementation_construct(ThreadImplementation* this, ExceptionData* exceptionData, int nova_0_times, int nova_0_millis)
{
	CCLASS_NEW(ThreadImplementation, this);
	this->vtable = &nova_VTable_ThreadImplementation_val;
	nova_Object_super((Object*)this, 0);
	nova_Thread_super((Thread*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Thread_this((Thread*)(this), exceptionData);
	nova_ThreadImplementation_super(this, 0);
	
	{
		nova_ThreadImplementation_this(this, exceptionData, nova_0_times, nova_0_millis);
	}
	
	return this;
}

void nova_del_ThreadImplementation(ThreadImplementation** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_ThreadImplementation_this(ThreadImplementation* this, ExceptionData* exceptionData, int nova_0_times, int nova_0_millis)
{
	this->prv->nova_ThreadImplementation_times = nova_0_times;
	this->prv->nova_ThreadImplementation_millis = nova_0_millis;
}

void nova_0_ThreadImplementation_run(ThreadImplementation* this, ExceptionData* exceptionData)
{
	int nova_1_i;
	
	nova_1_i = 0;
	for (; nova_1_i < this->prv->nova_ThreadImplementation_times; nova_1_i++)
	{
		nova_static_Thread_sleep(0, exceptionData, (long_long)(this->prv->nova_ThreadImplementation_millis));
	}
}

void nova_ThreadImplementation_super(ThreadImplementation* this, ExceptionData* exceptionData)
{
	this->prv->nova_ThreadImplementation_times = 0;
	this->prv->nova_ThreadImplementation_millis = 0;
}
