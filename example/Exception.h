#ifndef FILE_Exception_FATHOM
#define FILE_Exception_FATHOM

typedef struct Exception Exception;

#include <CClass.h>
#include <ExceptionHandler.h>
#include <windows.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"

CCLASS_CLASS
(
Exception
)

Exception* fathom_Exception_Exception(ExceptionData* exceptionData);
void fathom_del_Exception(Exception** this, ExceptionData* exceptionData);
#endif