#ifndef FILE_DivideByZeroException_NOVA
#define FILE_DivideByZeroException_NOVA

typedef struct DivideByZeroException DivideByZeroException;

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

DivideByZeroException* nova_DivideByZeroException_DivideByZeroException(ExceptionData* exceptionData);
void nova_del_DivideByZeroException(DivideByZeroException** this, ExceptionData* exceptionData);

#endif