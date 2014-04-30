#ifndef FILE_Thread_FATHOM
#define FILE_Thread_FATHOM

typedef struct Thread Thread;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <Thread.h>
#include "IO.h"

CCLASS_CLASS
(
	Thread, 
	
	struct Private* prv;
)


Thread* fathom_Thread_Thread(ExceptionData* exceptionData, long_long fathom_millis_165, String* fathom_word_165);
void fathom_del_Thread(Thread** this, ExceptionData* exceptionData);
void fathom_Thread_start(Thread* this, ExceptionData* exceptionData);
void fathom_Thread_join(Thread* this, ExceptionData* exceptionData);
void fathom_Thread_sleep(Thread* this, ExceptionData* exceptionData, long_long fathom_millis_174);
void fathom_Thread_run(Thread* this, ExceptionData* exceptionData);
FATHOM_THREAD_FUNC lib_fathom_thread_run(FATHOM_THREAD_FUNC_ARG arg);
#endif