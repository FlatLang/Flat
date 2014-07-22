#include <precompiled.h>
#include "NovaThreadImplementation.h"


nova_VTable_ThreadImplementation nova_VTable_ThreadImplementation_val =
{
	nova_ThreadImplementation_run,
};
CCLASS_PRIVATE
(
	FATHOM_THREAD_HANDLE* nova_Thread_handle;
	
)

ThreadImplementation* nova_ThreadImplementation_ThreadImplementation(ExceptionData* exceptionData)
{
	CCLASS_NEW(ThreadImplementation, this);
	
	this->vtable = &nova_VTable_ThreadImplementation_val;
	{
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

void nova_ThreadImplementation_run(ThreadImplementation* this, ExceptionData* exceptionData)
{
	int nova_1_i;
	
	nova_1_i = 0;
	for (; nova_1_i < 10; nova_1_i++)
	{
		nova_static_Thread_sleep((Thread*)(0), exceptionData, (long_long)(10));
	}
}
