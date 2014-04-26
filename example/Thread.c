#include "Thread.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include <windows.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <thread.h>

CCLASS_PRIVATE
(
FATHOM_THREAD_HANDLE* fathom_handle;
)

Thread* fathom_Thread_Thread(ExceptionData* exceptionData)
{
CCLASS_NEW(Thread, this);

this->prv->fathom_handle = 0;
{
}

return this;
}

void fathom_del_Thread(Thread** this, ExceptionData* exceptionData)
{
if (!*this)
{
return;
}


free((*this)->prv);

{
}
free(*this);
}

void fathom_Thread_start(Thread* this, ExceptionData* exceptionData)
{
}

void fathom_Thread_run(Thread* this, ExceptionData* exceptionData)
{
}
