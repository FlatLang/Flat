#include "Thread.h"
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"
#include <Thread.h>
#include "IO.h"

CCLASS_PRIVATE
(
	FATHOM_THREAD_HANDLE handle;
	long nova_Thread_millis;
	String* nova_Thread_word;
	
)

typedef struct DataStruct DataStruct;

struct DataStruct
{
	Thread* instance;
};

Thread* nova_Thread_Thread(ExceptionData* exceptionData, long_long nova_0_millis, String* nova_0_word)
{
	CCLASS_NEW(Thread, this);
	
	this->prv->nova_Thread_millis = 0;
	this->prv->nova_Thread_word = 0;
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
	free((*this)->prv);
	
	{
	}
	free(*this);
}

void nova_Thread_start(Thread* this, ExceptionData* exceptionData)
{
	DataStruct* data = (DataStruct*)malloc(sizeof(DataStruct));
	
	data->instance   = this;
	
	lib_fathom_thread_create(&this->prv->handle, lib_fathom_thread_run, (FATHOM_THREAD_FUNC_ARG)data);
}

void nova_Thread_join(Thread* this, ExceptionData* exceptionData)
{
	lib_fathom_thread_join(this->prv->handle);
}

void nova_Thread_sleep(ExceptionData* exceptionData, long_long nova_0_millis)
{
	lib_fathom_thread_sleep(nova_0_millis);
}

void nova_Thread_run(Thread* this, ExceptionData* exceptionData)
{
	int nova_255_i;
	
	nova_255_i = 0;
	
	for (; nova_255_i < 10; nova_255_i++)
	{
		nova_IO_println(exceptionData, this->prv->nova_Thread_word);
		nova_Thread_sleep(exceptionData, this->prv->nova_Thread_millis);
	}
}

FATHOM_THREAD_FUNC lib_fathom_thread_run(FATHOM_THREAD_FUNC_ARG arg)
{
	DataStruct* data = (DataStruct*)arg;
	
	Thread* this = data->instance;
	ExceptionData* exceptionData = 0;
	TRY
	{
		nova_ExceptionData_addCode(exceptionData, exceptionData, 1);
		
		{
			nova_Thread_run(this, exceptionData);
		}
	}
	CATCH (1)
	{
		{
			nova_IO_println(exceptionData, nova_String_String(exceptionData, "An error has occurred..."));
		}
	}
	FINALLY
	{
	}
	END_TRY;
	free(data);
	return 0;
}
