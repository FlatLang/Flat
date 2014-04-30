#include "Thread.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <Thread.h>
#include "IO.h"

CCLASS_PRIVATE
(
	FATHOM_THREAD_HANDLE handle;
	long_long fathom_millis;
	String* fathom_word;
)

typedef struct DataStruct DataStruct;

struct DataStruct
{
	Thread* instance;
};

Thread* fathom_Thread_Thread(ExceptionData* exceptionData, long_long fathom_millis_165, String* fathom_word_165)
{
	CCLASS_NEW(Thread, this);
	
	this->prv->fathom_millis = 0;
	this->prv->fathom_word = 0;
	{
		this->prv->fathom_millis = fathom_millis_165;
		this->prv->fathom_word = fathom_word_165;
	}
	
	return this;
}

void fathom_del_Thread(Thread** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	fathom_del_String(&(*this)->prv->fathom_word, exceptionData);
	free((*this)->prv);
	
	{
	}
	free(*this);
}

void fathom_Thread_start(Thread* this, ExceptionData* exceptionData)
{
	DataStruct* data = (DataStruct*)malloc(sizeof(DataStruct));
	
	data->instance   = this;
	
	lib_fathom_thread_create(&this->prv->handle, lib_fathom_thread_run, (FATHOM_THREAD_FUNC_ARG)data);
}

void fathom_Thread_join(Thread* this, ExceptionData* exceptionData)
{
	lib_fathom_thread_join(this->prv->handle);
}

void fathom_Thread_sleep(Thread* this, ExceptionData* exceptionData, long_long fathom_millis_174)
{
	lib_fathom_thread_sleep(fathom_millis_174);
}

void fathom_Thread_run(Thread* this, ExceptionData* exceptionData)
{
	int fathom_i_177;
	
	for (fathom_i_177 = 0; fathom_i_177 < 10; fathom_i_177++)
	{
		if (fathom_i_177 % 2 == 0)
		{
			fathom_IO_println(exceptionData, fathom_String_String(exceptionData, "Tick"));
		}
		else
		{
			fathom_IO_println(exceptionData, this->prv->fathom_word);
		}
		fathom_Thread_sleep(this, exceptionData, this->prv->fathom_millis);
	}
}

FATHOM_THREAD_FUNC lib_fathom_thread_run(FATHOM_THREAD_FUNC_ARG arg)
{
	DataStruct* data = (DataStruct*)arg;
	
	Thread* this = data->instance;
	ExceptionData* exceptionData = 0;
	TRY
	{
		fathom_ExceptionData_addCode(exceptionData, exceptionData, 1);
		
		{
			fathom_Thread_run(this, exceptionData);
		}
	}
	CATCH (1)
	{
		{
			fathom_IO_println(exceptionData, fathom_String_String(exceptionData, "An error has occurred..."));
		}
	}
	FINALLY
	{
	}
	END_TRY;
	free(data);
	return 0;
}
