#pragma once
#ifndef NATIVE_NOVA_THREAD
#define NATIVE_NOVA_THREAD

#include <Thread.h>

typedef struct Thread Thread;
typedef struct DataStruct DataStruct;
typedef struct ExceptionData ExceptionData;

typedef void (*run_method)(void*, ExceptionData*);

struct DataStruct
{
	Thread* instance;
	run_method run_method;
};

NOVA_THREAD_FUNC lib_nova_thread_run(NOVA_THREAD_FUNC_ARG arg);
NOVA_THREAD_HANDLE* create_thread(Thread* this, run_method method, void* ref);

#endif
