#ifndef FILE_NotEvenNumberException_FATHOM
#define FILE_NotEvenNumberException_FATHOM

typedef struct NotEvenNumberException NotEvenNumberException;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"

CCLASS_CLASS
(
NotEvenNumberException
)

NotEvenNumberException* fathom_NotEvenNumberException_NotEvenNumberException(ExceptionData* exceptionData);
void fathom_del_NotEvenNumberException(NotEvenNumberException** this, ExceptionData* exceptionData);
#endif