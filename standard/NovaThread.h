#pragma once
#ifndef FILE_Thread_NOVA
#define FILE_Thread_NOVA

typedef struct Thread Thread;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaObject.h"
#include "NovaString.h"
#include "NovaMath.h"
#include "NovaIO.h"
#include "NovaInteger.h"
#include "NovaLong.h"
#include "NovaDouble.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"
#include <Thread.h>
#include "NovaIO.h"
#include "NovaGC.h"

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