#ifndef FILE_DivideByZeroException_FATHOM
#define FILE_DivideByZeroException_FATHOM

typedef struct DivideByZeroException DivideByZeroException;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"

CCLASS_CLASS
(
	DivideByZeroException
)


DivideByZeroException* fathom_DivideByZeroException_DivideByZeroException(ExceptionData* exceptionData);
void fathom_del_DivideByZeroException(DivideByZeroException** this, ExceptionData* exceptionData);
#endif