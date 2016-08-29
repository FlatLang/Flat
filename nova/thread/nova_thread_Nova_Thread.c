#include <precompiled.h>
#include <nova/thread/nova_thread_Nova_Thread.h>


typedef struct nova_exception_Nova_ExceptionData nova_exception_Nova_ExceptionData;

typedef void (*nova_thread_Nova_Thread_closure1_Nova_run)(void*, nova_exception_Nova_ExceptionData*, void*);

nova_thread_Extension_VTable_Thread nova_thread_Extension_VTable_Thread_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	nova_thread_Nova_Thread_0_Nova_run,
};


CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_thread_Nova_Thread_Nova_handle;
	
)

void nova_thread_Nova_Thread_Nova_startRun(nova_thread_Nova_Thread* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_thread_Nova_Thread_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_thread_Nova_Thread* nova_thread_Nova_Thread_Nova_construct(nova_thread_Nova_Thread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_thread_Nova_Thread, this);
	this->vtable = &nova_thread_Extension_VTable_Thread_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_thread_Nova_Thread_Nova_super(this, exceptionData);
	
	{
		nova_thread_Nova_Thread_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_thread_Nova_Thread_Nova_destroy(nova_thread_Nova_Thread** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_thread_Nova_Thread_Nova_start(nova_thread_Nova_Thread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_thread_Nova_Thread_Nova_handle = create_thread(this, (nova_thread_Nova_Thread_closure1_Nova_run)&nova_thread_Nova_Thread_Nova_startRun, this, nova_null);
}

void nova_thread_Nova_Thread_Nova_join(nova_thread_Nova_Thread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	lib_nova_thread_join(*this->prv->nova_thread_Nova_Thread_Nova_handle);
}

void nova_thread_Nova_Thread_Nova_kill(nova_thread_Nova_Thread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	lib_nova_thread_cancel(*this->prv->nova_thread_Nova_Thread_Nova_handle);
}

void nova_thread_Nova_Thread_Nova_sleep(nova_thread_Nova_Thread* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_thread_Nova_Thread_Nova_millis)
{
	lib_nova_thread_sleep(nova_thread_Nova_Thread_Nova_millis);
}

void nova_thread_Nova_Thread_0_Nova_run(nova_thread_Nova_Thread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_thread_Nova_Thread_Nova_startRun(nova_thread_Nova_Thread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	TRY
	{
		novaEnv.nova_exception_ExceptionData.addCode(exceptionData, exceptionData, 1);
		
		{
			nova_thread_Nova_Thread_virtual0_Nova_run((nova_thread_Nova_Thread*)(this), exceptionData);
		}
	}
	CATCH (1)
	{
		nova_exception_Nova_Exception* l2_Nova_e = (nova_exception_Nova_Exception*)nova_null;
		
		l2_Nova_e = (nova_exception_Nova_Exception*)exceptionData->nova_exception_Nova_ExceptionData_Nova_thrownException;
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("An error has occurred...")));
	}
	FINALLY
	{
	}
	END_TRY;
}

void nova_thread_Nova_Thread_2_Nova_this(nova_thread_Nova_Thread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_thread_Nova_Thread_Nova_super(nova_thread_Nova_Thread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_thread_Nova_Thread_Nova_handle = 0;
}

void nova_thread_Nova_Thread_virtual0_Nova_run(nova_thread_Nova_Thread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->vtable->nova_thread_Nova_Thread_virtual0_Nova_run((nova_thread_Nova_Thread*)(this), exceptionData);
}

