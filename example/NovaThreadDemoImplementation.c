#include <precompiled.h>
#include "NovaThreadDemoImplementation.h"


nova_VTable_ThreadDemoImplementation nova_VTable_ThreadDemoImplementation_val =
{
	nova_0_ThreadDemoImplementation_run,
};
CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_Thread_handle;
	
	long_long nova_ThreadDemoImplementation_millis;
	String* nova_ThreadDemoImplementation_word;
	
)

ThreadDemoImplementation* nova_ThreadDemoImplementation_construct(ThreadDemoImplementation* this, ExceptionData* exceptionData, long_long nova_0_millis, String* nova_0_word)
{
	CCLASS_NEW(ThreadDemoImplementation, this);
	this->vtable = &nova_VTable_ThreadDemoImplementation_val;
	nova_Object_super((Object*)this, 0);
	nova_Thread_super((Thread*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Thread_this((Thread*)(this), exceptionData);
	nova_ThreadDemoImplementation_super(this, 0);
	
	{
		nova_ThreadDemoImplementation_this(this, exceptionData, nova_0_millis, nova_0_word);
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

void nova_ThreadDemoImplementation_this(ThreadDemoImplementation* this, ExceptionData* exceptionData, long_long nova_0_millis, String* nova_0_word)
{
	this->prv->nova_ThreadDemoImplementation_millis = nova_0_millis;
	this->prv->nova_ThreadDemoImplementation_word = nova_0_word;
}

void nova_0_ThreadDemoImplementation_run(ThreadDemoImplementation* this, ExceptionData* exceptionData)
{
	int nova_1_i;
	
	nova_1_i = 0;
	for (; nova_1_i < 10; nova_1_i++)
	{
		nova_static_0_Console_writeLine(0, exceptionData, this->prv->nova_ThreadDemoImplementation_word);
		nova_static_Thread_sleep((Thread*)(this), exceptionData, this->prv->nova_ThreadDemoImplementation_millis);
	}
}

void nova_ThreadDemoImplementation_super(ThreadDemoImplementation* this, ExceptionData* exceptionData)
{
	this->prv->nova_ThreadDemoImplementation_millis = 0;
	this->prv->nova_ThreadDemoImplementation_word = (String*)0;
}
