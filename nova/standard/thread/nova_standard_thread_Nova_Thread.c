#include <precompiled.h>
#include <nova/standard/thread/nova_standard_thread_Nova_Thread.h>
typedef struct nova_standard_exception_Nova_ExceptionData nova_standard_exception_Nova_ExceptionData;

typedef void (*nova_standard_thread_Nova_Thread_closure1_Nova_run)(void*, nova_standard_exception_Nova_ExceptionData*);

nova_standard_thread_Extension_VTable_Thread nova_standard_thread_Extension_VTable_Thread_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_thread_Nova_Thread_0_Nova_run,
};


CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_standard_thread_Nova_Thread_Nova_handle;
	
)

void nova_standard_thread_Nova_Thread_Nova_startRun(nova_standard_thread_Nova_Thread* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_thread_Nova_ThreadNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_thread_Nova_Thread* nova_standard_thread_Nova_Thread_2_Nova_construct(nova_standard_thread_Nova_Thread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_thread_Nova_Thread, this);
	this->vtable = &nova_standard_thread_Extension_VTable_Thread_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_thread_Nova_Thread_Nova_super(this, exceptionData);
	
	{
		nova_standard_thread_Nova_Thread_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_thread_Nova_Thread_Nova_destroy(nova_standard_thread_Nova_Thread** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_thread_Nova_Thread_Nova_start(nova_standard_thread_Nova_Thread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_thread_Nova_Thread_Nova_handle = create_thread(this, (nova_standard_thread_Nova_Thread_closure1_Nova_run)&nova_standard_thread_Nova_Thread_Nova_startRun, this);
}

void nova_standard_thread_Nova_Thread_Nova_join(nova_standard_thread_Nova_Thread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	lib_nova_thread_join(*this->prv->nova_standard_thread_Nova_Thread_Nova_handle);
}

void nova_standard_thread_Nova_Thread_Nova_kill(nova_standard_thread_Nova_Thread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	lib_nova_thread_cancel(*this->prv->nova_standard_thread_Nova_Thread_Nova_handle);
}

void nova_standard_thread_Nova_Thread_Nova_sleep(nova_standard_thread_Nova_Thread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, long nova_standard_thread_Nova_Thread_Nova_millis)
{
	lib_nova_thread_sleep((long_long)(nova_standard_thread_Nova_Thread_Nova_millis));
}

void nova_standard_thread_Nova_Thread_0_Nova_run(nova_standard_thread_Nova_Thread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_thread_Nova_Thread_Nova_startRun(nova_standard_thread_Nova_Thread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	TRY
	{
		novaEnv.nova_standard_exception_ExceptionData.addCode(exceptionData, exceptionData, 1);
		
		{
			this->vtable->nova_standard_thread_Nova_Thread_virtual0_Nova_run(this, exceptionData);
		}
	}
	CATCH (1)
	{
		nova_standard_exception_Nova_Exception* l2_Nova_e;
		
		l2_Nova_e = exceptionData->nova_standard_exception_Nova_ExceptionData_Nova_thrownException;
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "An error has occurred..."));
	}
	FINALLY
	{
	}
	END_TRY;
}

void nova_standard_thread_Nova_Thread_2_Nova_this(nova_standard_thread_Nova_Thread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_thread_Nova_Thread_Nova_super(nova_standard_thread_Nova_Thread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_thread_Nova_Thread_Nova_handle = 0;
}

