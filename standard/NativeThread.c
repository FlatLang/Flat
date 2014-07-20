#include "NativeThread.h"

FATHOM_THREAD_FUNC lib_nova_thread_run(FATHOM_THREAD_FUNC_ARG arg)
{
	DataStruct* data = (DataStruct*)arg;

	Thread* this = data->instance;
	ExceptionData* exceptionData = 0;

	data->run_method(this, exceptionData);

	NOVA_FREE(data);

	return 0;
}

FATHOM_THREAD_HANDLE* create_thread(Thread* this, run_method run_method, void* ref)
{
	FATHOM_THREAD_HANDLE* handle;

	DataStruct* data = (DataStruct*)malloc(sizeof(DataStruct));

	data->instance   = ref;
	data->run_method = run_method;

	handle = (FATHOM_THREAD_HANDLE*)malloc(sizeof(FATHOM_THREAD_HANDLE));

	lib_fathom_thread_create(handle, lib_nova_thread_run, (FATHOM_THREAD_FUNC_ARG)data);

	return handle;
}
