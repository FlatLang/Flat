#include <precompiled.h>
#include "NovaThread.h"

typedef void (*nova_1_0_run)(void*, ExceptionData*);

nova_VTable_Thread nova_VTable_Thread_val =
{
	nova_Thread_run,
};
CCLASS_PRIVATE
(
	FATHOM_THREAD_HANDLE* nova_Thread_handle;
	long_long nova_Thread_millis;
	String* nova_Thread_word;
	
)

void nova_Thread_startRun(Thread* this, ExceptionData* exceptionData);

Thread* nova_Thread_Thread(ExceptionData* exceptionData, long_long nova_0_millis, String* nova_0_word)
{
	CCLASS_NEW(Thread, this);
	
	this->prv->nova_Thread_handle = (Object*)0;
	this->prv->nova_Thread_millis = (Object*)0;
	this->prv->nova_Thread_word = (Object*)0;
	this->vtable = &nova_VTable_Thread_val;
	{
		this->prv->nova_Thread_millis = nova_0_millis;
		this->prv->nova_Thread_word = nova_0_word;
	}
	
	return this;
}

void nova_del_Thread(Thread** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	nova_del_String(&(*this)->prv->nova_Thread_word, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_Thread_start(Thread* this, ExceptionData* exceptionData)
{
	this->prv->nova_Thread_handle = create_thread(this, (nova_1_0_run)&nova_Thread_startRun, this);
}

void nova_Thread_join(Thread* this, ExceptionData* exceptionData)
{
	lib_fathom_thread_join(*this->prv->nova_Thread_handle);
}

void nova_static_Thread_sleep(Thread* this, ExceptionData* exceptionData, long_long nova_0_millis)
{
	lib_fathom_thread_sleep(nova_0_millis);
}

void nova_Thread_run(Thread* this, ExceptionData* exceptionData)
{
	int nova_1_i;
	
	nova_1_i = 0;
	for (; nova_1_i < 10; nova_1_i++)
	{
		nova_static_Console_writeLine((Object*)0, exceptionData, this->prv->nova_Thread_word);
		nova_static_Thread_sleep(this, exceptionData, this->prv->nova_Thread_millis);
	}
}

void nova_Thread_startRun(Thread* this, ExceptionData* exceptionData)
{
	TRY
	{
		nova_ExceptionData_addCode(exceptionData, exceptionData, 1);
		
		{
			this->vtable->nova_virtual_run(this, exceptionData);
		}
	}
	CATCH (1)
	{
		nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_String(exceptionData, "An error has occurred..."));
	}
	FINALLY
	{
	}
	END_TRY;
}
