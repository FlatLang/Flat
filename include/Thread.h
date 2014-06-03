#ifndef LIB_FATHOM_THREAD
#define LIB_FATHOM_THREAD

#include <stdlib.h>
#include <gc.h>

#if defined(_WIN32)
	#include <windows.h>
	
	#define FATHOM_THREAD_FUNC DWORD WINAPI
	#define FATHOM_THREAD_FUNC_TYPE LPTHREAD_START_ROUTINE
	#define FATHOM_THREAD_FUNC_ARG LPVOID
	#define FATHOM_THREAD_HANDLE HANDLE
	#define lib_fathom_thread_join(_HANDLE_) WaitForSingleObject(_HANDLE_, INFINITE)
	#define lib_fathom_thread_sleep(_MILLIS_) Sleep(_MILLIS_)
	
#elif defined(__APPLE__) || defined(__linux__)
	#include <pthread.h>
	#include <unistd.h>
	
	#define FATHOM_THREAD_FUNC void*
	#define FATHOM_THREAD_FUNC_TYPE void*
	#define FATHOM_THREAD_FUNC_ARG void*
	#define FATHOM_THREAD_HANDLE pthread_t
	#define lib_fathom_thread_join(_HANDLE_) pthread_join(_HANDLE_, NULL)
	#define lib_fathom_thread_sleep(_MILLIS_) usleep(_MILLIS_ * 1000)
#endif

void lib_fathom_thread_create(FATHOM_THREAD_HANDLE* handle, FATHOM_THREAD_FUNC_TYPE func, FATHOM_THREAD_FUNC_ARG arg);

#endif