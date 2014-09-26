#include <precompiled.h>
#include <nova/standard/nova_standard_NovaThread.h>

typedef void (*l0_Nova1_run)(void*, nova_standard_exception_NovaExceptionData*);

nova_VTable_nova_standard_NovaThread nova_VTable_nova_standard_NovaThread_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
};
CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_standard_NovaThread_Novahandle;
	
)

void nova_standard_NovaThread_NovastartRun(nova_standard_NovaThread* this, nova_standard_exception_NovaExceptionData* exceptionData);

nova_standard_NovaThread* nova_standard_NovaThread_Novanull0_construct(nova_standard_NovaThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_NovaThread, this);
	this->vtable = &nova_VTable_nova_standard_NovaThread_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaThread_Novasuper(this, 0);
	
	{
		nova_standard_NovaThread_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Thread(nova_standard_NovaThread** this, nova_standard_exception_NovaExceptionData* exceptionData)
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

void nova_standard_NovaThread_Novastart(nova_standard_NovaThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_NovaThread_Novahandle = create_thread(this, (l0_Nova1_run)&nova_standard_NovaThread_NovastartRun, this);
}

void nova_standard_NovaThread_Novajoin(nova_standard_NovaThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	lib_nova_thread_join(*this->prv->nova_standard_NovaThread_Novahandle);
}

void nova_standard_NovaThread_static_Novasleep(nova_standard_NovaThread* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novamillis)
{
	lib_nova_thread_sleep(l0_Novamillis);
}

void nova_standard_NovaThread_Novarun(nova_standard_NovaThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaThread_NovastartRun(nova_standard_NovaThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	TRY
	{
		nova_standard_exception_NovaExceptionData_NovaaddCode(exceptionData, exceptionData, 1);
		
		{
			nova_standard_NovaThread_Novarun(this, exceptionData);
		}
	}
	CATCH (1)
	{
		nova_standard_NovaConsole_static_Novanull0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "An error has occurred..."));
	}
	FINALLY
	{
	}
	END_TRY;
}

void nova_standard_NovaThread_Novathis(nova_standard_NovaThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaThread_Novasuper(nova_standard_NovaThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_NovaThread_Novahandle = (NOVA_THREAD_HANDLE*)nova_null;
}
