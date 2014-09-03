#pragma once
#ifndef NATIVE_NOVA_THREAD
#define NATIVE_NOVA_THREAD

#include <time.h>

#ifdef USE_GC
#	undef GC_NO_THREAD_DECLS
#endif

#ifdef _WIN32
#	define NOVA_THREAD_FUNC DWORD WINAPI
#	define NOVA_THREAD_FUNC_TYPE LPTHREAD_START_ROUTINE
#	define NOVA_THREAD_FUNC_ARG LPVOID
#	define NOVA_THREAD_HANDLE HANDLE
#	define lib_nova_thread_join(_HANDLE_) WaitForSingleObject(_HANDLE_, INFINITE)
#	define lib_nova_thread_sleep(_MILLIS_) Sleep(_MILLIS_)
#	ifdef USE_GC
#		define new_thread_method CreateThread
#	else
#		define new_thread_method CreateThread
#	endif

#elif DEFINED(__APPLE__, __linux__)
#	define NOVA_THREAD_FUNC void*
#	define NOVA_THREAD_FUNC_TYPE void*
#	define NOVA_THREAD_FUNC_ARG void*
#	define NOVA_THREAD_HANDLE pthread_t
#	define lib_nova_thread_join(_HANDLE_) pthread_join(_HANDLE_, NULL)
#	define lib_nova_thread_sleep(_MILLIS_) thread_nanosleep(_MILLIS_ * 1000000)
#	ifdef USE_GC
#		define new_thread_method GC_pthread_create
#	else
#		define new_thread_method pthread_create
#	endif
#endif

void lib_nova_thread_create(NOVA_THREAD_HANDLE* handle, NOVA_THREAD_FUNC_TYPE func, NOVA_THREAD_FUNC_ARG arg);

#if DEFINED(__APPLE__, __linux__)
void thread_nanosleep(long_long nanos);
#endif

#include <NovaThread.h>
#include <NovaExceptionData.h>

//typedef struct Thread Thread;
typedef struct DataStruct DataStruct;
//typedef struct ExceptionData ExceptionData;



typedef void (*run_method)(void*, ExceptionData*);

struct DataStruct
{
	Thread* instance;
	run_method run_method;
};

NOVA_THREAD_FUNC lib_nova_thread_run(NOVA_THREAD_FUNC_ARG arg);
NOVA_THREAD_HANDLE* create_thread(Thread* this, run_method method, void* ref);

#endif
