#pragma once
#ifndef NATIVE_NOVA_THREAD
#define NATIVE_NOVA_THREAD

#ifdef USE_GC
#	undef GC_NO_THREAD_DECLS
#endif

#ifdef _WIN32
#	define NOVA_THREAD_FUNC DWORD WINAPI
#	define NOVA_THREAD_FUNC_TYPE LPTHREAD_START_ROUTINE
#	define NOVA_THREAD_FUNC_ARG LPVOID
#	define NOVA_THREAD_HANDLE HANDLE
#	define lib_nova_thread_join(_HANDLE_) WaitForSingleObject(_HANDLE_, INFINITE)
#	define lib_nova_thread_cancel(_HANDLE_) TerminateThread(_HANDLE_, 0)
#	define lib_nova_thread_sleep(_MILLIS_) Sleep(_MILLIS_)
#	ifdef USE_GC
#		define new_thread_method CreateThread
#	else
#		define new_thread_method CreateThread
#	endif

#elif defined(__APPLE__) || defined(__linux__)
#	define NOVA_THREAD_FUNC void*
#	define NOVA_THREAD_FUNC_TYPE void*
#	define NOVA_THREAD_FUNC_ARG void*
#	define NOVA_THREAD_HANDLE pthread_t
#	define lib_nova_thread_join(_HANDLE_) pthread_join(_HANDLE_, NULL)
#	define lib_nova_thread_cancel(_HANDLE_) pthread_cancel(_HANDLE_)
#	define lib_nova_thread_sleep(_MILLIS_) thread_nanosleep(_MILLIS_ * 1000000)
#	ifdef USE_GC
#		define new_thread_method GC_pthread_create
#	else
#		define new_thread_method pthread_create
#	endif
#	include <fcntl.h>
#	include <semaphore.h>
#endif

#include <time.h>
#include <Nova.h>

void lib_nova_thread_create(NOVA_THREAD_HANDLE* handle, NOVA_THREAD_FUNC_TYPE func, NOVA_THREAD_FUNC_ARG arg);

#if defined(__APPLE__) || defined(__linux__)
void thread_nanosleep(long_long nanos);
#endif

//#include <nova/thread/nova_thread_Nova_Thread.h>
//#include <nova/exception/nova_exception_Nova_ExceptionData.h>
#include <Nova.h>

typedef struct DataStruct DataStruct;

typedef void (*run_method)(void*, void*);

struct DataStruct
{
	nova_thread_Nova_Thread* instance;
	void* context;
	run_method run_method;
};

NOVA_THREAD_FUNC lib_nova_thread_run(NOVA_THREAD_FUNC_ARG arg);
NOVA_THREAD_HANDLE* create_thread(nova_thread_Nova_Thread* this, run_method method, void* ref, void* context);

int nova_create_semaphore();
int nova_close_semaphore();

long_long nova_current_thread_id();

#ifdef _WIN32
    extern HANDLE nova_thread_semaphore;
#elif defined(__APPLE__) || defined(__linux__)
    extern sem_t* nova_thread_semaphore;
#endif

#endif