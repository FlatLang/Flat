#ifndef FILE_Thread_NOVA
#define FILE_Thread_NOVA

typedef struct Thread Thread;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"
#include <Thread.h>
#include "IO.h"

CCLASS_CLASS
(
	Thread, 
	
	struct Private* prv;
)


Thread* nova_Thread_Thread(ExceptionData* exceptionData, long_long nova_Thread_millis_112, String* nova_Thread_word_112);
void nova_del_Thread(Thread** this, ExceptionData* exceptionData);
void nova_Thread_start(Thread* this, ExceptionData* exceptionData);
void nova_Thread_join(Thread* this, ExceptionData* exceptionData);
void nova_Thread_sleep(ExceptionData* exceptionData, long_long nova_Thread_millis_160);
void nova_Thread_run(Thread* this, ExceptionData* exceptionData);
FATHOM_THREAD_FUNC lib_fathom_thread_run(FATHOM_THREAD_FUNC_ARG arg);
#endif