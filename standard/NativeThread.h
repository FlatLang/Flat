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

FATHOM_THREAD_FUNC lib_nova_thread_run(FATHOM_THREAD_FUNC_ARG arg);
FATHOM_THREAD_HANDLE* create_thread(Thread* this, run_method run_method, void* ref);
