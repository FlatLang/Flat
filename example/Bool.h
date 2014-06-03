#ifndef FILE_Bool_NOVA
#define FILE_Bool_NOVA

typedef struct Bool Bool;

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
	Bool
)


Bool* nova_Bool_Bool(ExceptionData* exceptionData);
void nova_del_Bool(Bool** this, ExceptionData* exceptionData);
#endif