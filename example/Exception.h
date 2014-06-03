#ifndef FILE_Exception_NOVA
#define FILE_Exception_NOVA

typedef struct Exception Exception;

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

CCLASS_CLASS
(
	Exception
)


Exception* nova_Exception_Exception(ExceptionData* exceptionData);
void nova_del_Exception(Exception** this, ExceptionData* exceptionData);
#endif