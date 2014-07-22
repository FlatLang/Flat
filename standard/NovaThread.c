#include <precompiled.h>
#include "NovaThread.h"

typedef void (*nova_1_0_run)(void*, ExceptionData*);

nova_VTable_Thread nova_VTable_Thread_val =
{
	nova_Object_toString,
	nova_Object_equals,
};
CCLASS_PRIVATE
(
	FATHOM_THREAD_HANDLE* nova_Thread_handle;
	
)

void nova_Thread_startRun(Thread* this, ExceptionData* exceptionData);

Thread* nova_Thread_Thread(ExceptionData* exceptionData)
{
	CCLASS_NEW(Thread, this);
	
	this->prv->nova_Thread_handle = (FATHOM_THREAD_HANDLE*)0;
	this->vtable = &nova_VTable_Thread_val;
	{
	}
	
	return this;
}

void nova_del_Thread(Thread** this, ExceptionData* exceptionData)
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
}

void nova_Thread_startRun(Thread* this, ExceptionData* exceptionData)
{
	TRY
	{
		nova_ExceptionData_addCode(exceptionData, exceptionData, 1);
		
		{
			nova_Thread_run(this, exceptionData);
		}
	}
	CATCH (1)
	{
		nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "An error has occurred..."));
	}
	FINALLY
	{
	}
	END_TRY;
}
