#ifndef FILE_Thread_FATHOM
#define FILE_Thread_FATHOM

typedef struct Thread Thread;

#include <CClass.h>
#include <ExceptionHandler.h>
#include <windows.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <thread.h>

CCLASS_CLASS
(
Thread, 

struct Private* prv;
)

Thread* fathom_Thread_Thread(ExceptionData* exceptionData);
void fathom_del_Thread(Thread** this, ExceptionData* exceptionData);
void fathom_Thread_start(Thread* this, ExceptionData* exceptionData);
void fathom_Thread_run(Thread* this, ExceptionData* exceptionData);
#endif