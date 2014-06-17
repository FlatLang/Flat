#ifndef LIB_FATHOM_THREAD
#define LIB_FATHOM_THREAD

#include "precompiled.h"

#ifdef _WIN32
#	define FATHOM_THREAD_FUNC DWORD WINAPI
#	define FATHOM_THREAD_FUNC_TYPE LPTHREAD_START_ROUTINE
#	define FATHOM_THREAD_FUNC_ARG LPVOID
#	define FATHOM_THREAD_HANDLE HANDLE
#	define lib_fathom_thread_join(_HANDLE_) WaitForSingleObject(_HANDLE_, INFINITE)
#	define lib_fathom_thread_sleep(_MILLIS_) Sleep(_MILLIS_)
#	define new_thread CreateThread

#elif defined(__APPLE__) || defined(__linux__)
#	define FATHOM_THREAD_FUNC void*
#	define FATHOM_THREAD_FUNC_TYPE void*
#	define FATHOM_THREAD_FUNC_ARG void*
#	define FATHOM_THREAD_HANDLE pthread_t
#	define lib_fathom_thread_join(_HANDLE_) pthread_join(_HANDLE_, NULL)
#	define lib_fathom_thread_sleep(_MILLIS_) usleep(_MILLIS_ * 1000)
#	define new_thread pthread_create
#endif

#ifdef USE_GC
#	undef GC_NO_THREAD_DECLS

#	undef new_thread

#	ifdef _WIN32
#		define new_thread GC_CreateThread
#	endif
#endif

void lib_fathom_thread_create(FATHOM_THREAD_HANDLE* handle, FATHOM_THREAD_FUNC_TYPE func, FATHOM_THREAD_FUNC_ARG arg);

#endif
