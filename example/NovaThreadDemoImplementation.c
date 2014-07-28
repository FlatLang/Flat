#include <precompiled.h>
#include "NovaThreadDemoImplementation.h"


nova_VTable_ThreadDemoImplementation nova_VTable_ThreadDemoImplementation_val =
{
	nova_ThreadDemoImplementation_run,
};
CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_Thread_handle;
	
	long_long nova_ThreadDemoImplementation_millis;
	String* nova_ThreadDemoImplementation_word;
	
)

ThreadDemoImplementation* nova_ThreadDemoImplementation_ThreadDemoImplementation(ExceptionData* exceptionData, long_long nova_0_millis, String* nova_0_word)
{
	CCLASS_NEW(ThreadDemoImplementation, this);
	
	this->prv->nova_ThreadDemoImplementation_millis = 0;
	this->prv->nova_ThreadDemoImplementation_word = (String*)0;
	this->vtable = &nova_VTable_ThreadDemoImplementation_val;
	{
		this->prv->nova_ThreadDemoImplementation_millis = nova_0_millis;
		this->prv->nova_ThreadDemoImplementation_word = nova_0_word;
	}
	
	return this;
}

void nova_del_ThreadDemoImplementation(ThreadDemoImplementation** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_del_String(&(*this)->prv->nova_ThreadDemoImplementation_word, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_ThreadDemoImplementation_run(ThreadDemoImplementation* this, ExceptionData* exceptionData)
{
	int nova_1_i;
	
	nova_1_i = 0;
	for (; nova_1_i < 10; nova_1_i++)
	{
		nova_static_Console_writeLine((Console*)(0), exceptionData, this->prv->nova_ThreadDemoImplementation_word);
		nova_static_Thread_sleep((Thread*)(this), exceptionData, this->prv->nova_ThreadDemoImplementation_millis);
	}
}
