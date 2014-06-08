#ifndef FILE_Thread_NOVA
#define FILE_Thread_NOVA

typedef struct Thread Thread;

#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
#include "DivideByZeroException.h"
#include <Thread.h>
#include "IO.h"
#include "GC.h"

CCLASS_CLASS
(
	Thread, 
	
	struct Private* prv;
)

Thread* nova_Thread_Thread(ExceptionData* exceptionData, long_long nova_0_millis, String* nova_0_word);
void nova_del_Thread(Thread** this, ExceptionData* exceptionData);
void nova_Thread_start(Thread* this, ExceptionData* exceptionData);
void nova_Thread_join(Thread* this, ExceptionData* exceptionData);
void nova_Thread_sleep(ExceptionData* exceptionData, long_long nova_0_millis);
void nova_Thread_run(Thread* this, ExceptionData* exceptionData);

#endif