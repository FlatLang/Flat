#include "NativeThread.h"

NOVA_THREAD_FUNC lib_nova_thread_run(NOVA_THREAD_FUNC_ARG arg)
{
	DataStruct* data = (DataStruct*)arg;

	Thread* this = data->instance;
	ExceptionData* exceptionData = 0;
	
	data->run_method(this, exceptionData);

	NOVA_FREE(data);

	return 0;
}

NOVA_THREAD_HANDLE* create_thread(Thread* this, run_method method, void* ref)
{
	NOVA_THREAD_HANDLE* handle;

	DataStruct* data = (DataStruct*)malloc(sizeof(DataStruct));

	data->instance   = ref;
	data->run_method = method;

	handle = (NOVA_THREAD_HANDLE*)malloc(sizeof(NOVA_THREAD_HANDLE));
	
	lib_nova_thread_create(handle, lib_nova_thread_run, (NOVA_THREAD_FUNC_ARG)data);

	return handle;
}
