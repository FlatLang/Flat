#include "NativeThread.h"

#ifdef _WIN32
	HANDLE nova_thread_semaphore;
#elif defined(__APPLE__) || defined(__linux__)
	sem_t* nova_thread_semaphore;
#endif

void lib_nova_thread_create(NOVA_THREAD_HANDLE* handle, NOVA_THREAD_FUNC_TYPE func, NOVA_THREAD_FUNC_ARG arg)
{
#if defined(_WIN32)
	DWORD id;

	/*
	BDW-GC:
	LPSECURITY_ATTRIBUTES // lpThreadAttributes
	GC_WIN32_SIZE_T // dwStackSize
	LPTHREAD_START_ROUTINE // lpStartAddress
	LPVOID // lpParameter
	DWORD // dwCreationFlags
	LPDWORD // lpThreadId

	WIN32:
	_In_opt_   LPSECURITY_ATTRIBUTES lpThreadAttributes,
	_In_       SIZE_T dwStackSize,
	_In_       LPTHREAD_START_ROUTINE lpStartAddress,
	_In_opt_   LPVOID lpParameter,
	_In_       DWORD dwCreationFlags,
	_Out_opt_  LPDWORD lpThreadId
	*/

	*handle = (NOVA_THREAD_HANDLE*)new_thread_method(0, 0, *func, arg, 0, &id);
#elif defined(__APPLE__) || defined(__linux__)
	new_thread_method(handle, NULL, func, arg);
#endif
}

#if defined(__APPLE__) || defined(__linux__)

void thread_nanosleep2(struct timespec req);

void thread_nanosleep(long_long nanos)
{
	struct timespec req;

	req.tv_sec  = nanos / 1000000000;
	req.tv_nsec = nanos % 1000000000;

	thread_nanosleep2(req);
}

void thread_nanosleep2(struct timespec req)
{
	struct timespec rem;

	if (nanosleep(&req, &rem) < 0)
	{
		thread_nanosleep2(rem);
	}
}

#endif

NOVA_THREAD_FUNC lib_nova_thread_run(NOVA_THREAD_FUNC_ARG arg)
{
	DataStruct* data = (DataStruct*)arg;
	
	data->run_method(data->instance, data->context);
	
	// NOVA_FREE(data);

	return 0;
}

NOVA_THREAD_HANDLE* create_thread(nova_thread_Nova_Thread* this, run_method method, void* ref, void* context)
{
	NOVA_THREAD_HANDLE* handle;

	DataStruct* data = (DataStruct*)NOVA_MALLOC(sizeof(DataStruct));
	
	data->instance   = this;
	data->context    = context;
	data->run_method = method;
	
	handle = (NOVA_THREAD_HANDLE*)NOVA_MALLOC(sizeof(NOVA_THREAD_HANDLE));
	
	lib_nova_thread_create(handle, lib_nova_thread_run, (NOVA_THREAD_FUNC_ARG)data);
	
	return handle;
}

int nova_create_semaphore() {
#ifdef _WIN32
	nova_thread_semaphore = CreateSemaphore(0, 1, 1, 0);
#elif defined(__APPLE__) || defined(__linux__)
	if ((nova_thread_semaphore = sem_open("nova_thread_semaphore", O_CREAT, 0644, 1)) == SEM_FAILED) {
		printf("Failed to init semaphore");
		
		return -1;
	}
#endif
	
	return 0;
}

int nova_close_semaphore() {
#if defined(__APPLE__) || defined(__linux__)
	if (sem_close(nova_thread_semaphore) == -1) {
		printf("Failed to close semaphore");
		
		return -1;
	}
	if (sem_unlink("nova_thread_semaphore") == -1) {
		printf("Failed to unlink semaphore");
		
		return -1;
	}
#endif
	
	return 0;
}

long_long nova_current_thread_id() {
#ifdef _WIN32
	return GetCurrentThreadId();
#elif defined(__APPLE__)
	uint64_t id;
	
	pthread_threadid_np(NULL, &id);
	
	return id;
#else
	pthread_t ptid = pthread_self();
	uint64_t id = 0;
	
	memcpy(&id, &ptid, sizeof(id) < sizeof(ptid) ? sizeof(id) : sizeof(ptid));

	return id;
#endif
}